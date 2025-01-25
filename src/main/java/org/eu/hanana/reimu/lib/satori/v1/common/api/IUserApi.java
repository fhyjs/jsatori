package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public interface IUserApi {
    Mono<User> get(IUserId userId,String user_id);
    Mono<PagedData<User>> friend_list(IUserId userId,@Nullable String next);
    Mono<Void> friend_approve(IUserId userId,String message_id , boolean approve,@Nullable String comment);
}
