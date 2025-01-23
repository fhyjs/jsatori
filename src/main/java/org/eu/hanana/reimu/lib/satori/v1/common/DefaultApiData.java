package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DefaultApiData implements IApiData{
    public Map<String,String> httpHeader = new HashMap<>();
    public String httpBody = "";
    public DefaultApiData(){}
    public DefaultApiData putHeader(String name,String val){
        httpHeader.put(name,val);
        return this;
    }
    @Override
    public String getToSendData() {
        return new Gson().toJsonTree(this).toString();
    }

    @Override
    public void loadFromJson(String json) {
        httpHeader.clear();
        DefaultApiData abstractDefaultApiData = new Gson().fromJson(json, this.getClass());
        httpHeader.putAll(abstractDefaultApiData.httpHeader);
        httpBody= abstractDefaultApiData.httpBody;
    }
}
