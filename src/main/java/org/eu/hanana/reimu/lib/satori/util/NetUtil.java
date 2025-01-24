package org.eu.hanana.reimu.lib.satori.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.connection.NettyHttpClient;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.IApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.function.Tuple2;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NetUtil {
    private static final Logger log = LogManager.getLogger(NetUtil.class);

    public static void send(Channel channel, Signal signal){
        if (channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(signal.serializeStr()));
        }
    }
    public static <T extends IApiData> Flux<Tuple2<HttpClientResponse, byte[]>> send(NettyHttpClient httpClient, String baseUrl, ApiMethod<T> apiMethod, T apiData){
        if (httpClient.isDisposed()) {
            return null;
        }
        if (apiMethod.method.equals(ApiMethod.POST)){
            Tuple2<Map<String, String>, String> data = apiData.getToSendData();
            return httpClient.post(baseUrl+apiMethod,data.getT1(),data.getT2());
        }else {
            return httpClient.get(baseUrl+apiMethod+apiData.getToSendData());
        }
    }
    public static <T extends IApiData>String send(SatoriClient client,ApiMethod<T> apiMethod,T ad){
        // 发送 HTTP 请求并异步处理响应
        Flux<Tuple2<HttpClientResponse, byte[]>> send = send(
                client.getHttpClient(),
                client.baseHttpUrl.toString(),
                apiMethod,
                ad
        );
        AtomicBoolean finish = new AtomicBoolean(false);
        AtomicReference<String> resultStr = new AtomicReference<>();
        AtomicReference<HttpClientResponse> httpResponse = new AtomicReference<>();
        AtomicReference<Throwable> throwable = new AtomicReference<>();
        send.subscribe(result -> {
            httpResponse.set(result.getT1());
            resultStr.set(new String(result.getT2(), StandardCharsets.UTF_8));
            if (httpResponse.get().status().code() != 200) {
                throwable.set(new IllegalStateException(String.valueOf(httpResponse.get().status().code())));
                log.debug("Error sending data ,code:{},result:{}",httpResponse.get().status().code(),resultStr);
            }
            finish.set(true);
            //System.out.println(byteBufFlux.aggregate().block());
        }, throwable1 -> {
            throwable.set(throwable1);
            finish.set(true);
        });
        while (!finish.get()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (throwable.get()!=null){
            throw new RuntimeException(throwable.get());
        }
        return resultStr.get();
    }
}
