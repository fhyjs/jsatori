package org.eu.hanana.reimu.lib.satori.v1.common;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PagedData<T> {
    public PagedData(String data,Class<T> tClass){
        this(JsonParser.parseString(data),tClass);
    }
    public PagedData(JsonElement data,Class<T> clazz){
        Gson gson = new Gson();
        if (data.isJsonArray()){
            for (JsonElement jsonElement : data.getAsJsonArray()) {
                this.data.add(gson.fromJson(jsonElement,clazz));
            }
            return;
        }
        JsonArray jsonArray = data.getAsJsonObject().get("data").getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            this.data.add(gson.fromJson(jsonElement,clazz));
        }
        JsonElement jsonElementNext = data.getAsJsonObject().get("next");
        JsonElement jsonElementPrev = data.getAsJsonObject().get("prev");
        if (jsonElementNext!=null) next=jsonElementNext.getAsString();
        if (jsonElementPrev!=null) prev=jsonElementPrev.getAsString();
    }
    public List<T> data=new ArrayList<>();
    @Nullable
    public String next;
    @Nullable
    public String prev;
    public boolean hasNext(){
        return next!=null;
    }
    public boolean hasPrev(){
        return prev!=null;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
