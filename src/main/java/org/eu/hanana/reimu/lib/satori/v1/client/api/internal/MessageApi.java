package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.connection.NettyHttpClient;
import org.eu.hanana.reimu.lib.satori.util.NetUtil;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IMessageApi;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ByteBufFlux;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.function.Tuple2;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

public class MessageApi implements IMessageApi {
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("message.create", DefaultApiData.class);
    private static final Logger log = LogManager.getLogger(MessageApi.class);
    protected SatoriClient client;
    @Override
    public Mono<List<Message>> create(IUserId userId, String channel_id, String content) {
        Mono<List<Message>> objectMono = Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                Flux<Tuple2<HttpClientResponse, byte[]>> send = send(
                        client.getHttpClient(),
                        client.baseHttpUrl.toString(),
                        AM_Create,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("content", content)
                                .setAuthorization(client.getAuthenticator().getAuthenticatorToken())
                                .setUserData(userId)
                );
                send.subscribe(result -> {
                    HttpClientResponse httpResponse = result.getT1();
                    String resultStr = new String(result.getT2());

                    // 打印响应状态码
                    log.debug("Status Code: {},Data: '{}'", httpResponse.status().code(),resultStr);
                    JsonArray asJsonArray = JsonParser.parseString(resultStr).getAsJsonArray();
                    ArrayList<Message> messages = new ArrayList<>();
                    for (JsonElement jsonElement : asJsonArray) {
                        messages.add(new Gson().fromJson(jsonElement,Message.class));
                    }
                    monoSink.success(messages);
                    //System.out.println(byteBufFlux.aggregate().block());
                }, monoSink::error);

                //var block = send.doOnError(monoSink::error).block();
                //log.debug(block.getT2().block());
                //
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
        //objectMono.subscribe();
        return objectMono;
    }

    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }
}
