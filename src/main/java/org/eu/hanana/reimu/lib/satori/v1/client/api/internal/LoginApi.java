package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IClientHolder;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultJsonApiData;
import org.eu.hanana.reimu.lib.satori.v1.common.api.ILoginApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Login;
import reactor.core.publisher.Mono;

import static org.eu.hanana.reimu.lib.satori.util.NetUtil.send;
@Log4j2
public class LoginApi implements ILoginApi, IClientHolder  {
    public static final ApiMethod<DefaultApiData> AM_Get = ApiMethod.parse("login.get", DefaultApiData.class);
    private SatoriClient client;
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }

    @Override
    public Mono<Login> get(IUserId userId) {
        return Mono.create(monoSink -> {
            try {
                var result = send(client,AM_Get,new DefaultJsonApiData()
                        .setUserData(userId)
                        .setAuthorization(client));
                monoSink.success(new Gson().fromJson(result, Login.class));
            } catch (Exception e) {
                log.error(StringUtil.getFullErrorMessage(e));
                monoSink.error(e);  // 捕获异常并返回
            }
        });
    }
}
