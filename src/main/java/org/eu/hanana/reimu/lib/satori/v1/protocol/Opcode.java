package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.util.function.Function;

@JsonAdapter(ObjectByIntId.Serializer.class)
public class Opcode extends ObjectByIntId{
    public static final Opcode EVENT    = new Opcode(0,"EVENT",(jsonElement)-> new Gson().fromJson(jsonElement, SignalBodyEvent.class));
    public static final Opcode PING     = new Opcode(1,"PING");
    public static final Opcode PONG     = new Opcode(2,"PONG");
    public static final Opcode IDENTIFY = new Opcode(3,"IDENTIFY");
    public static final Opcode READY    = new Opcode(4,"READY",(jsonElement)-> new Gson().fromJson(jsonElement, SignalBodyReady.class));
    public static final Opcode META     = new Opcode(5,"META",(jsonElement)-> new Gson().fromJson(jsonElement, SignalBodyMeta.class));
    protected final Function<JsonElement,AbstractSignalBody> bodySupplier;
    public Opcode(int id, String name, Function<JsonElement,AbstractSignalBody> bodySupplier){
        super(id,name);
        this.bodySupplier=bodySupplier;
    }
    public Opcode(int id, String name){
        this(id, name, (jsonElement) -> null);
    }

    public AbstractSignalBody getBody(JsonElement jsonElement) {
        return bodySupplier.apply(jsonElement);
    }
}
