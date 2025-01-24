package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IGuildApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Channel;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@SuppressWarnings("unchecked")
public class GuildApi implements IGuildApi, IClientHolder {
    private static final Logger log = LogManager.getLogger(GuildApi.class);
    private SatoriClient client;
    public static final ApiMethod<DefaultApiData> AM_Get = ApiMethod.parse("guild.get", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("guild.list", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Approve = ApiMethod.parse("guild.approve", DefaultApiData.class);
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Guild> get(IUserId userId, String guild_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Get,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result, Guild.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<PagedData<Guild>> list(IUserId userId, @Nullable String next) {
        return Mono.create(monoSink -> {
            try {
                DefaultJsonApiData data = (DefaultJsonApiData) new DefaultJsonApiData()
                        .setUserData(userId)
                        .setAuthorization(client);
                if (next!=null) data.putBodyVal("next",next);
                var result = send(client,AM_List,data);
                monoSink.success((PagedData<Guild>) new Gson().fromJson(result, TypeToken.getParameterized(PagedData.class,Guild.class)));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> approve(IUserId userId, String message_id, boolean approve, @Nullable String comment) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Approve,new DefaultJsonApiData()
                        .putBodyVal("message_id",message_id)
                        .putBodyVal("approve",approve)
                        .putBodyVal("comment",comment)
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
