package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IReactionApi {
    Mono<Void> create(IUserId userId,String channel_id,String message_id,String emoji);
    Mono<Void> delete(IUserId userId, String channel_id, String message_id, String emoji, @Nullable String user_id);
    Mono<Void> clear(IUserId userId, String channel_id, String message_id,@Nullable String emoji);
    Mono<PagedData<User>> list(IUserId userId, String channel_id, String message_id,String emoji,@Nullable String next);
}
