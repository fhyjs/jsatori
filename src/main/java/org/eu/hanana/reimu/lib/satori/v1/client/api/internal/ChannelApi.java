package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IChannelApi;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Channel;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@SuppressWarnings("unchecked")
@Log4j2
public class ChannelApi implements IChannelApi, IClientHolder {
    private SatoriClient client;
    public static final ApiMethod<DefaultApiData> AM_Get = ApiMethod.parse("channel.get", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("channel.list", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("channel.create", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Update = ApiMethod.parse("channel.update", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Delete = ApiMethod.parse("channel.delete", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Mute = ApiMethod.parse("channel.mute", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_User_Channel_Create = ApiMethod.parse("user.channel.create", DefaultApiData.class);
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Channel> get(IUserId userId, String channel_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Get,new DefaultJsonApiData()
                        .putBodyVal("channel_id",channel_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result,Channel.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<PagedData<Channel>> list(IUserId userId, String guild_id, @Nullable String next) {
        return Mono.create(monoSink -> {
            try {
                DefaultJsonApiData data = (DefaultJsonApiData) new DefaultJsonApiData()
                        .putBodyVal("guild_id", guild_id)
                        .setUserData(userId)
                        .setAuthorization(client);
                if (next!=null) data.putBodyVal("next",next);
                var result = send(client,AM_List,data);
                monoSink.success((PagedData<Channel>) new Gson().fromJson(result,TypeToken.getParameterized(PagedData.class,Channel.class)));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Channel> create(IUserId userId, String guild_id, Channel data) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Create,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result,Channel.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Channel> userChannelCreate(IUserId userId, String user_id, @Nullable String guild_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_User_Channel_Create,new DefaultJsonApiData()
                        .putBodyVal("user_id",user_id)
                        .putBodyVal("guild_id",guild_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result,Channel.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> update(IUserId userId, String channel_id, Channel data) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Update,new DefaultJsonApiData()
                        .putBodyVal("channel_id",channel_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success();
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> delete(IUserId userId, String channel_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Delete,new DefaultJsonApiData()
                        .putBodyVal("channel_id",channel_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success();
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> mute(IUserId userId, String channel_id, long duration) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Mute,new DefaultJsonApiData()
                        .putBodyVal("channel_id",channel_id)
                        .putBodyVal("duration",duration)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success();
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }
}
