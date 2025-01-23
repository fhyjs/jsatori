package org.eu.hanana.reimu.lib.satori.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.eu.hanana.reimu.lib.satori.connection.NettyHttpClient;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.IApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class NetUtil {
    public static void send(Channel channel,Signal signal){
        if (channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(signal.serializeStr()));
        }
    }
    public static <T extends IApiData> Mono<HttpClientResponse> send(NettyHttpClient httpClient, String baseUrl, ApiMethod<T> apiMethod, T apiData){
        if (httpClient.isDisposed()) {
            return null;
        }
        if (apiMethod.method.equals(ApiMethod.POST)){
            return httpClient.post(baseUrl+"/"+apiMethod,apiData.getToSendData());
        }else {
            return httpClient.get(baseUrl+"/"+apiMethod+apiData.getToSendData());
        }
    }
}
