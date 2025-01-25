package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.FormDataApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.IResourceApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;

@Log4j2
public class ResourceApi implements IResourceApi, IClientHolder {
    private SatoriClient client;
    public static final ApiMethod<FormDataApiData> AM_upload_create = ApiMethod.parse("upload.create", FormDataApiData.class);
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Map<String, String>> upload_create(IUserId userId, List<FormDataApiData.FormDataEntity> files) {
        return Mono.create(monoSink -> {
            try {
                // 发送 HTTP 请求并异步处理响应
                FormDataApiData data = (FormDataApiData) new FormDataApiData()
                        .setAuthorization(client)
                        .setUserData(userId);
                files.forEach(data::putFile);
                var send = send(
                        client,
                        AM_upload_create,
                        data
                );

                //noinspection unchecked
                monoSink.success((Map<String, String>) new Gson().fromJson(send, TypeToken.getParameterized(Map.class,String.class,String.class)));
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }

    @Override
    public String get_resource_url(String url) {
        var f = false;
        for (String proxyUrl : client.loginData.proxy_urls) {
            if (url.startsWith(proxyUrl)) {
                f = true;
                break;
            }
        }
        if (url.startsWith("internal:")||f) {
            return client.baseHttpUrl+"proxy/"+url;
        }
        return url;
    }

    @Override
    public Mono< byte[]> download_resource(IUserId userId, String url) {
        url = get_resource_url(url);
        String finalUrl = url;
        return Mono.create(monoSink -> {
            try {
                client.getHttpClient().get(finalUrl).subscribe(objects -> {
                    monoSink.success(objects.getT2());
                }, throwable -> {
                    log.error(StringUtil.getFullErrorMessage(throwable));
                    monoSink.error(throwable);
                });
            } catch (Throwable e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }
}
