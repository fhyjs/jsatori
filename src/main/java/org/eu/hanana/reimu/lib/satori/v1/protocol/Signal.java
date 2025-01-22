package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

@JsonAdapter(Signal.Serializer.class)
public class Signal implements ISerializableMsg{
    public Signal(Opcode opcode,AbstractSignalBody body){
        this.op=opcode;
        this.body=body;
    }
    public static Signal parse(String msg){
        return new Gson().fromJson(msg,Signal.class);
    }
    public Opcode op;
    public AbstractSignalBody body;
    protected static class Serializer implements JsonDeserializer<Signal> {

        @Override
        public Signal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            Opcode opcode = gson.fromJson(json.getAsJsonObject().get("op"), Opcode.class);
            AbstractSignalBody body1 = opcode.getBody(json.getAsJsonObject().get("body"));
            return new Signal(opcode,body1);
        }
    }
}
