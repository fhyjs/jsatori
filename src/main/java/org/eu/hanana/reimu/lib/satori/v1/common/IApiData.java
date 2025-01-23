package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.JsonElement;

public interface IApiData {
    String getToSendData();
    void loadFromJson(String data);
}
