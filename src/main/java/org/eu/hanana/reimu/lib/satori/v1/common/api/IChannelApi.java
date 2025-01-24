package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Channel;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IChannelApi {
    Mono<Channel> get(IUserId userId,String channel_id);
    Mono<PagedData<Channel>> list(IUserId userId, String channel_id,@Nullable String next);
    Mono<Channel> create(IUserId userId, String channel_id,Channel data);
    Mono<Channel> userChannelCreate(IUserId userId, String user_id,@Nullable String guild_id);
    Mono<Void> update(IUserId userId, String channel_id,Channel data);
    Mono<Void> delete(IUserId userId, String channel_id);
    Mono<Void> mute(IUserId userId, String channel_id,long duration);
}
