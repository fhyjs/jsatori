package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IGuildApi {
    Mono<Guild> get(IUserId userId, String guild_id);
    Mono<PagedData<Guild>> list(IUserId userId, @Nullable String next);
    Mono<Void> approve(IUserId userId,String message_id,boolean approve,@Nullable String comment);
}
