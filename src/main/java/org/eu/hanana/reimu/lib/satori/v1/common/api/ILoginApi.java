package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Login;
import reactor.core.publisher.Mono;

public interface ILoginApi {
    Mono<Login> get(IUserId userId);
}
