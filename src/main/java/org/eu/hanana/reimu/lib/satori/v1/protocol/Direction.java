package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ObjectByName.Serializer.class)
public class Direction extends ObjectByName{
    public static final Direction before = new Direction("before");
    public static final Direction after = new Direction("after");
    public static final Direction around = new Direction("around");
    public Direction(String name) {
        super(name);
    }
}
