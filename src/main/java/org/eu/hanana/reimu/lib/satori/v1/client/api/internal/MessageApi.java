package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
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
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.function.Tuple2;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Consumer;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

public class MessageApi implements IMessageApi {
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("message.create", DefaultApiData.class);
    private static final Logger log = LogManager.getLogger(MessageApi.class);
    protected SatoriClient client;
    @Override
    public Mono<Message> create(IUserId userId, String channel_id, String content) {
        Mono<Message> objectMono = Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                Mono<Tuple2<HttpClientResponse, ByteBufMono>> send = send(
                        client.getHttpClient(),
                        client.baseHttpUrl.toString(),
                        AM_Create,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("content", content)
                                .setAuthorization(client.getAuthenticator().getAuthenticatorToken())
                                .setUserData(userId)
                );
                assert send != null;

                send.flatMap(objects ->
                                // 获取 ByteBufMono 并转换为字符串，然后解析成 Message 对象
                                objects.getT2().asString(StandardCharsets.UTF_8)
                                        .map(response -> new Gson().fromJson(response, Message.class))
                        )
                        .doOnError(monoSink::error)  // 错误处理
                        .doOnTerminate(monoSink::success)  // 终止后调用成功回调
                        .subscribe(monoSink::success, monoSink::error);  // 确保触发异步结果
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
        objectMono.subscribe();
        return objectMono;
    }

    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }
}
