package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.JsonElement;
import reactor.util.function.Tuple2;

import java.util.Map;

public interface IApiData {
    Tuple2<Map<String,String>,String> getToSendData();
    void loadFromJson(Tuple2<Map<String,String>,String> data);
}
