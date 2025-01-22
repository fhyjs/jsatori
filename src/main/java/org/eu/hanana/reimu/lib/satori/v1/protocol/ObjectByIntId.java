package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.function.Function;

@JsonAdapter(ObjectByIntId.Serializer.class)
public abstract class ObjectByIntId implements ISerializableMsg{
    public final int id;
    public final String name;
    public ObjectByIntId(int id, String name){
        this.id=id;
        this.name=name;
    }
    @Override
    public String toString() {
        return String.format("%s(%d)",name,id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectByIntId objectByIntId){
            return objectByIntId.id==this.id;
        }
        return false;
    }
    protected static class Serializer implements JsonSerializer<ObjectByIntId>, JsonDeserializer<ObjectByIntId> {
        @Override
        public JsonElement serialize(ObjectByIntId src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.id);
        }

        @Override
        public ObjectByIntId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            int id = json.getAsInt();
            Class<?> clazz = ((Class<?>) typeOfT);
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                Object o = null;
                try {
                    o = field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (((ObjectByIntId) o).id==id) {
                    return (ObjectByIntId) o;
                }
            }
            try {
                return (ObjectByIntId) clazz.getConstructor(int.class,String.class).newInstance(id,"unknown");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
