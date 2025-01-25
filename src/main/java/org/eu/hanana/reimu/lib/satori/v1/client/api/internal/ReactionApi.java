package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IReactionApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@Log4j2
public class ReactionApi implements IClientHolder, IReactionApi {
    private SatoriClient client;
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("reaction.create", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Delete = ApiMethod.parse("reaction.delete", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Clear = ApiMethod.parse("reaction.clear", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("reaction.list", DefaultApiData.class);
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Void> create(IUserId userId, String channel_id, String message_id, String emoji) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Create,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("message_id", message_id)
                                .putBodyVal("emoji", emoji)
                                .setAuthorization(client)
                                .setUserData(userId)
                );

                monoSink.success();
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> delete(IUserId userId, String channel_id, String message_id, String emoji, @Nullable String user_id) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Delete,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("message_id", message_id)
                                .putBodyVal("emoji", emoji)
                                .putBodyVal("user_id", user_id)
                                .setAuthorization(client)
                                .setUserData(userId)
                );

                monoSink.success();
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> clear(IUserId userId, String channel_id, String message_id, @Nullable String emoji) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Clear,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("message_id", message_id)
                                .putBodyVal("emoji", emoji)
                                .setAuthorization(client)
                                .setUserData(userId)
                );

                monoSink.success();
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<PagedData<User>> list(IUserId userId, String channel_id, String message_id, String emoji, @Nullable String next) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_List,
                        new DefaultJsonApiData()
                                .putBodyVal("channel_id", channel_id)
                                .putBodyVal("message_id", message_id)
                                .putBodyVal("emoji", emoji)
                                .putBodyVal("next", next)
                                .setAuthorization(client.getAuthenticator().getAuthenticatorToken())
                                .setUserData(userId)
                );

                monoSink.success(new PagedData<>(send,User.class));
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }
}
