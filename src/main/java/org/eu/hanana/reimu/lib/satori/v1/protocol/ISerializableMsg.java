package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public interface ISerializableMsg {
    default String serializeStr(){
        return new Gson().toJson(this);
    }
    default JsonElement serialize(){
        return new Gson().toJsonTree(this);
    }
}
