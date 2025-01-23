package org.eu.hanana.reimu.lib.satori.v1.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.Getter;
import org.eu.hanana.reimu.lib.satori.connection.AbstractWebSocketClientHandler;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyIdentify;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyReady;

public class AuthenticatorC extends AbstractWebSocketClientHandler {
    private final SignalBodyIdentify idBody;
    @Getter
    protected boolean authorized=false;
    @Getter
    protected SignalBodyReady readyData;
    protected Runnable callback;

    public String getAuthenticatorToken() {
        return idBody.token;
    }

    public void setSn(int sn){
        if (idBody!=null)
            idBody.sn=sn;
    }
    public AuthenticatorC(){
        this(new SignalBodyIdentify());
    }
    public AuthenticatorC(SignalBodyIdentify signalBodyIdentify){
        this.idBody=signalBodyIdentify;
    }
    public AuthenticatorC setCallback(Runnable runnable){
        this.callback=runnable;
        authorized=false;
        return this;
    }
    @Override
    public void onConnect(ChannelHandlerContext ctx) {
        super.onConnect(ctx);
        if (!authorized){
            ctx.channel().writeAndFlush(new TextWebSocketFrame(new Signal(Opcode.IDENTIFY,idBody).serializeStr()));
        }
    }

    @Override
    public void onMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        super.onMessage(ctx, msg);
        if (msg instanceof TextWebSocketFrame textWebSocketFrame) {
            Signal parse = Signal.parse(textWebSocketFrame.text());
            if (parse.op.equals(Opcode.READY)){
                authorized=true;
                this.readyData= (SignalBodyReady) parse.body;
                if (callback!=null) callback.run();
            }
        }
    }

}
