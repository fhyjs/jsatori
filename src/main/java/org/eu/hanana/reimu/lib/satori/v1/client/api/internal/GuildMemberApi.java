package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IGuildMemberApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildMember;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@SuppressWarnings("unchecked")
@Log4j2
public class GuildMemberApi implements IGuildMemberApi, IClientHolder {
    public static final ApiMethod<DefaultApiData> AM_Get = ApiMethod.parse("guild.member.get", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("guild.member.list", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Kick = ApiMethod.parse("guild.member.kick", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Mute = ApiMethod.parse("guild.member.mute", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Approve = ApiMethod.parse("guild.member.approve", DefaultApiData.class);
    private SatoriClient client;
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<GuildMember> get(IUserId userId, String guild_id, String user_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Get,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("user_id",user_id)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result, GuildMember.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> kick(IUserId userId, String guild_id, String user_id, boolean permanent) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Kick,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("user_id",user_id)
                        .putBodyVal("permanent",permanent)
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
    public Mono<Void> mute(IUserId userId, String guild_id, String user_id, long duration) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Mute,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("user_id",user_id)
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

    @Override
    public Mono<PagedData<GuildMember>> list(IUserId userId, String guild_id, @Nullable String next) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_List,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("next",next)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new PagedData<>(result,GuildMember.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }
}
