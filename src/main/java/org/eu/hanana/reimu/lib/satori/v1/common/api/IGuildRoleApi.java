package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildRole;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IGuildRoleApi {
    Mono<Void> guildMemberRoleSet(IUserId userId,String guild_id,String user_id,String role_id);
    Mono<Void> guildMemberRoleUnset(IUserId userId,String guild_id,String user_id,String role_id);
    Mono<PagedData<GuildRole>> list(IUserId userId, String guild_id,@Nullable String next);
    Mono<GuildRole> create(IUserId userId, String guild_id, GuildRole role);
    Mono<Void> update(IUserId userId, String guild_id, String role_id,GuildRole role);
    Mono<Void> delete(IUserId userId, String guild_id, String role_id);
}
