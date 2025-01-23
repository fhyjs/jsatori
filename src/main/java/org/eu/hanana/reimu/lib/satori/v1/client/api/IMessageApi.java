package org.eu.hanana.reimu.lib.satori.v1.client.api;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import reactor.core.publisher.Mono;

public interface IMessageApi {
    void setClient(SatoriClient client);
    Mono<Message> create(String channel_id, String content);
}
