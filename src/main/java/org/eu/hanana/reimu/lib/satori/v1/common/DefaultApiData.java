package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import reactor.netty.http.client.HttpClientForm;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultApiData implements IApiData{
    public Map<String,String> httpHeader = new HashMap<>();
    public String httpBody = "";
    public DefaultApiData putHeader(String name,String val){
        httpHeader.put(name,val);
        return this;
    }
    public DefaultApiData setUserData(IUserId userId){
        return this.setPlatform(userId.getPlatform())
                .setPlatformUser(userId.getUser().getId());
    }
    public DefaultApiData setPlatform(String platform){
        return putHeader("Satori-Platform",platform);
    }
    public DefaultApiData setAuthorization(String authorization){
        if (authorization!=null)
            return putHeader("Authorization","Bearer "+authorization);
        return this;
    }
    public DefaultApiData setAuthorization(IAuthorizationDataHolder authorization){
        return setAuthorization(authorization.getAuthorizationToken());
    }
    public DefaultApiData setPlatformUser(String user){
        return putHeader("Satori-User-ID",user);
    }
    @Override
    public Tuple2<Map<String,String>,String> getToSendData() {
        return Tuples.of(Collections.unmodifiableMap(httpHeader),httpBody);
    }

    @Override
    public void loadFromJson(Tuple2<Map<String,String>,String> data) {
        httpHeader.clear();
        httpHeader=data.getT1();
        httpBody=data.getT2();
    }
}
