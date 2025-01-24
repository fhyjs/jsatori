package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IMessageApi;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

public class MessageApi implements IMessageApi, IClientHolder {
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("message.create", DefaultApiData.class);
    private static final Logger log = LogManager.getLogger(MessageApi.class);
    protected SatoriClient client;
    @Override
    public Mono<List<Message>> create(IUserId userId, String channel_id, String content) {
        // 发送 HTTP 请求并异步处理响应
        // 打印响应状态码
        //System.out.println(byteBufFlux.aggregate().block());
        //var block = send.doOnError(monoSink::error).block();
        //log.debug(block.getT2().block());
        //
        // 捕获异常并返回
        //objectMono.subscribe();
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Create,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("content", content)
                                .setAuthorization(client.getAuthenticator().getAuthenticatorToken())
                                .setUserData(userId)
                );
                // 打印响应状态码
                JsonArray asJsonArray = JsonParser.parseString(send).getAsJsonArray();
                ArrayList<Message> messages = new ArrayList<>();
                for (JsonElement jsonElement : asJsonArray) {
                    messages.add(new Gson().fromJson(jsonElement,Message.class));
                }
                monoSink.success(messages);
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }
}
