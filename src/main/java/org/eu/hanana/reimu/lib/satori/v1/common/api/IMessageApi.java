package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMessageApi {
    Mono<List<Message>> create(IUserId userId, String channel_id, String content);
}
