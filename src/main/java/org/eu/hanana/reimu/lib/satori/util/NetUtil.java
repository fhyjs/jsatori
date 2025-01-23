package org.eu.hanana.reimu.lib.satori.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.eu.hanana.reimu.lib.satori.connection.NettyHttpClient;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.IApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.function.Tuple2;

import java.util.Map;

public class NetUtil {
    public static void send(Channel channel,Signal signal){
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
}
