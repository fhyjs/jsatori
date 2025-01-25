package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ObjectByName.Serializer.class)
public class Order extends ObjectByName{
    public static final Order asc = new Order("asc");
    public static final Order desc = new Order("desc");
    public Order(String name) {
        super(name);
    }
}
