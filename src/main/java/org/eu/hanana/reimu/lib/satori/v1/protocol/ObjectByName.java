package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

@JsonAdapter(ObjectByName.Serializer.class)
public abstract class ObjectByName implements ISerializableMsg{
    public final String name;
    public ObjectByName(String name){
        this.name=name;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectByName objectByName){
            return objectByName.name.equals(name);
        }
        return false;
    }
    protected static class Serializer implements JsonSerializer<ObjectByName>, JsonDeserializer<ObjectByName> {
        @Override
        public JsonElement serialize(ObjectByName src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.name);
        }

        @Override
        public ObjectByName deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsString();
            Class<?> clazz = ((Class<?>) typeOfT);
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())||!Modifier.isPublic(field.getModifiers())) {
                    continue;
                }
                Object o = null;
                try {
                    o = field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (((ObjectByName) o).name.equals(name)) {
                    return (ObjectByName) o;
                }
            }
            try {
                return (ObjectByName) clazz.getConstructor(String.class).newInstance(name);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
