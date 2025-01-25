package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Direction;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Order;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMessageApi {
    Mono<PagedData<Message>> create(IUserId userId, String channel_id, String content);
    Mono<Message> get(IUserId userId, String channel_id, String message_id);
    Mono<Void> delete(IUserId userId, String channel_id, String message_id);
    Mono<Void> update(IUserId userId, String channel_id, String message_id,String content);
    Mono<PagedData<Message>> list(IUserId userId, String channel_id, @Nullable String next, @Nullable Direction direction, int limit, @Nullable Order order);
}
