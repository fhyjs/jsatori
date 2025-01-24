package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IGuildRoleApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildRole;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import reactor.core.publisher.Mono;

@Log4j2
public class GuildRoleApi implements IClientHolder, IGuildRoleApi {
    private SatoriClient client;
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Void> guildMemberRoleSet(IUserId userId, String guild_id, String user_id, String role_id) {
        return null;
    }

    @Override
    public Mono<Void> guildMemberRoleUnset(IUserId userId, String guild_id, String user_id, String role_id) {
        return null;
    }

    @Override
    public Mono<PagedData<GuildRole>> list(IUserId userId, String guild_id, String next) {
        return null;
    }

    @Override
    public Mono<GuildRole> create(IUserId userId, String guild_id, GuildRole role) {
        return null;
    }

    @Override
    public Mono<Void> update(IUserId userId, String guild_id, String role_id, GuildRole role) {
        return null;
    }

    @Override
    public Mono<Void> delete(IUserId userId, String guild_id, String role_id) {
        return null;
    }
}
