package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Meta;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyMeta;
import reactor.core.publisher.Mono;

public interface IMetaApi {
    Mono<Meta> meta(IUserId userId);
}
