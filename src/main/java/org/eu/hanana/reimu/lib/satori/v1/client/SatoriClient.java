package org.eu.hanana.reimu.lib.satori.v1.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.connection.WebSocketClient;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.util.WsUtil;
import org.eu.hanana.reimu.lib.satori.v1.common.SignalEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.*;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SatoriClient implements Closeable {
    private final Logger log = LogManager.getLogger(this);
    protected boolean closed;
    public final URI baseHttpUrl,baseWsUrl;
    public WebSocketClient webSocketClientEvent;
    private AuthenticatorC authenticator;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private LoginStatus status = LoginStatus.OFFLINE;
    protected long pingTime;
    public SignalBodyReady loginData;
    private boolean firstOpen=true;
    protected int sn=-1;
    public List<CallbackWsReceiver.Callback> events;
    public SatoriClient(String httpHost, String wsHost){
        closed=false;
        httpHost=httpHost.endsWith("/")?httpHost:httpHost+"/";
        wsHost=wsHost.endsWith("/")?wsHost:wsHost+"/";
        this.baseHttpUrl= URI.create(httpHost+getVersion()+"/");
        this.baseWsUrl= URI.create(wsHost+getVersion()+"/");
        firstOpen=true;
    }
    public static SatoriClient createSatoriClient(String host){
        var uri = URI.create(host);
        if (uri.getScheme()==null) uri=URI.create("http://"+host);
        if (uri.getScheme()==null) throw new IllegalArgumentException("Address format may wrong!");
        var baseAddress = String.format("%s:%d%s",uri.getHost(),uri.getPort(),uri.getPath());
        return new SatoriClient("http://"+baseAddress,"ws://"+baseAddress);
    }
    public SatoriClient addAuthenticator(AuthenticatorC authenticator){
        this.authenticator=authenticator;
        return this;
    }
    public void open(){
        this.open(firstOpen,false);
    }
    protected void open(boolean first,boolean sync){
        if (webSocketClientEvent!=null&&!webSocketClientEvent.group.isShutdown()){
            log.info("Shutting down the old connection before connecting...");
            webSocketClientEvent.group.shutdownGracefully();
        }
        webSocketClientEvent=new WebSocketClient(baseWsUrl+"events");
        log.info("Connecting to {},first:{},sync:{}",webSocketClientEvent.uri,first,sync);
        webSocketClientEvent.headers.add(authenticator.setCallback(this::onAuthenticate));
        authenticator.setSn(sn);
        webSocketClientEvent.open(sync);
        if (first) {
            status = LoginStatus.CONNECT;
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    SatoriClient.this.tick();
                }catch (Throwable throwable){
                    log.error(StringUtil.getFullErrorMessage(throwable));
                }
            }, 0, 500, TimeUnit.MILLISECONDS);
        }
        this.firstOpen=false;
    }
    public void tick() throws InterruptedException {
        pingTime+=500;
        if (isAuthorized()){
            if (pingTime>5000){
                WsUtil.send(webSocketClientEvent.getCh(), new Signal(Opcode.PING,null));
            }
        }
        if (pingTime>10000){
            status=LoginStatus.RECONNECT;
            log.warn("Request timeout: {}ms.Connected:{}.\n{}!",pingTime,webSocketClientEvent.isConnected(),status);
            if (webSocketClientEvent.isConnected()) {
                webSocketClientEvent.getCh().disconnect().sync();
                webSocketClientEvent.group.shutdownGracefully().sync();
            }
            open(false,true);
            pingTime=0;
        }
    }
    public boolean onReceive(Signal callback){
        var opcode = callback.op;
        var events = new ArrayList<>(this.events);
        AbstractSignalBody body = callback.body;
        if (body instanceof IHasSn isn) this.sn=isn.getSn();
        var handled = false;
        for (CallbackWsReceiver.Callback event : events) {
            event.onReceive(callback);
        }
        if (opcode.equals(Opcode.PONG)){
            log.debug("PONG");
            pingTime=0;
            handled=true;
        }else if (opcode.equals(Opcode.META)){
            log.debug("Update Meta");
            this.loginData.proxy_urls=((SignalBodyMeta) body).proxy_urls;
            handled=true;
        }
        return handled;
    }
    public void onAuthenticate(){
        log.info("Authenticated for {}",webSocketClientEvent.uri);
        this.loginData=authenticator.getReadyData();
        webSocketClientEvent.headers.add(new CallbackWsReceiver().setCallback(this::onReceive));
        status=LoginStatus.ONLINE;
    }
    public AuthenticatorC getAuthenticator() {
        return authenticator;
    }
    public boolean isAuthorized() {
        return getAuthenticator()!=null&&getAuthenticator().isAuthorized();
    }
    @Override
    public void close() {
        scheduler.close();
        webSocketClientEvent.group.shutdownGracefully();
    }

    public LoginStatus getStatus() {
        return status;
    }

    public String getVersion(){
        return "v1";
    }
}
