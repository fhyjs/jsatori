package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IReactionApi;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IUserApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@Log4j2
public class UserApi implements IClientHolder, IUserApi {
    private SatoriClient client;
    public static final ApiMethod<DefaultApiData> AM_Get = ApiMethod.parse("user.get", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("friend.list", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Approve = ApiMethod.parse("friend.approve", DefaultApiData.class);
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<User> get(IUserId userId, String user_id) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Get,
                        new DefaultJsonApiData()
                                .putBodyVal("user_id", user_id)
                                .setAuthorization(client)
                                .setUserData(userId)
                );

                monoSink.success(new Gson().fromJson(send,User.class));
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<PagedData<User>> friend_list(IUserId userId, @Nullable String next) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_List,
                        new DefaultJsonApiData()
                                .putBodyVal("next", next)
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
    public Mono<Void> friend_approve(IUserId userId, String message_id, boolean approve, @Nullable String comment) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                var send = send(
                        client,
                        AM_Approve,
                        new DefaultJsonApiData()
                                .putBodyVal("message_id", message_id)
                                .putBodyVal("approve", approve)
                                .putBodyVal("comment", comment)
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
}
