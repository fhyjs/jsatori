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
import org.eu.hanana.reimu.lib.satori.v1.common.api.IGuildRoleApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildRole;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@Log4j2
public class GuildRoleApi implements IClientHolder, IGuildRoleApi {
    public static final ApiMethod<DefaultApiData> AM_MemberRoleSet = ApiMethod.parse("guild.member.role.set", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_MemberRoleUnset = ApiMethod.parse("guild.member.role.unset", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_List = ApiMethod.parse("guild.role.list", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("guild.role.create", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Update = ApiMethod.parse("guild.role.update", DefaultApiData.class);
    public static final ApiMethod<DefaultApiData> AM_Delete = ApiMethod.parse("guild.role.delete", DefaultApiData.class);
    private SatoriClient client;
    private final Gson gson =new Gson();
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Void> guildMemberRoleSet(IUserId userId, String guild_id, String user_id, String role_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_MemberRoleSet,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("user_id",user_id)
                        .putBodyVal("role_id",role_id)
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
    public Mono<Void> guildMemberRoleUnset(IUserId userId, String guild_id, String user_id, String role_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_MemberRoleUnset,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("user_id",user_id)
                        .putBodyVal("role_id",role_id)
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
    public Mono<PagedData<GuildRole>> list(IUserId userId, String guild_id, String next) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_List,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("next",next)
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new PagedData<>(result,GuildRole.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<GuildRole> create(IUserId userId, String guild_id, GuildRole role) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Create,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("role",gson.toJson(role))
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(gson.fromJson(result,GuildRole.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public Mono<Void> update(IUserId userId, String guild_id, String role_id, GuildRole role) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Update,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("role_id",role_id)
                        .putBodyVal("role",gson.toJson(role))
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
    public Mono<Void> delete(IUserId userId, String guild_id, String role_id) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Delete,new DefaultJsonApiData()
                        .putBodyVal("guild_id",guild_id)
                        .putBodyVal("role_id",role_id)
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
