package org.eu.hanana.reimu.lib.satori.v1.common.api;

import org.eu.hanana.reimu.lib.satori.v1.common.FormDataApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Channel;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ApiStatus.Experimental
public interface IResourceApi {
    Mono<Map<String,String>> upload_create(IUserId userId, List<FormDataApiData.FormDataEntity> files);
    String get_resource_url( String url);
    Mono<byte[]> download_resource(IUserId userId, String url);
}
