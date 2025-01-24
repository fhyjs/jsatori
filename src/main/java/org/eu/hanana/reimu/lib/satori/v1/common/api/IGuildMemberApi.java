package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildMember;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IGuildMemberApi {
    Mono<GuildMember> get(IUserId userId, String guild_id,String user_id);
    Mono<Void> kick(IUserId userId, String guild_id,String user_id,boolean permanent);
    Mono<Void> mute(IUserId userId, String guild_id,String user_id,long duration);
    Mono<Void> approve(IUserId userId, String message_id,boolean approve,@Nullable String comment);
    Mono<PagedData<GuildMember>> list(IUserId userId, String guild_id,@Nullable String next);
}
