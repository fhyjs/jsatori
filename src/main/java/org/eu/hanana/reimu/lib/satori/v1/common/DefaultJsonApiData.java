package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.*;
import lombok.AllArgsConstructor;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultJsonApiData extends DefaultApiData{
    public JsonElement dataBody= new JsonObject();
    public DefaultJsonApiData putBodyVal(String k,JsonElement v){
         dataBody.getAsJsonObject().add(k,v);
         return this;
    }
    public DefaultJsonApiData putBodyVal(String k,@Nullable Object v){
        if (v==null||k==null) return this;
        return this.putBodyVal(k,new Gson().toJsonTree(v));

    }
    @Override
    public Tuple2<Map<String,String>,String> getToSendData() {
        this.httpHeader.put("Content-Type","application/json");
        this.httpBody=dataBody.toString();
        return super.getToSendData();
    }

    @Override
    public void loadFromJson(Tuple2<Map<String,String>,String> data) {
        httpHeader.clear();
        httpHeader=data.getT1();
        httpBody=data.getT2();
        dataBody= new Gson().toJsonTree(httpBody);
    }
}
