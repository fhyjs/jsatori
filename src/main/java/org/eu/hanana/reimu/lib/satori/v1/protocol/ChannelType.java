package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ObjectByIntId.Serializer.class)
public class ChannelType extends ObjectByIntId{
    public static final ChannelType TEXT     = new ChannelType(0,"TEXT");
    public static final ChannelType DIRECT   = new ChannelType(1,"DIRECT");
    public static final ChannelType CATEGORY = new ChannelType(2,"CATEGORY");
    public static final ChannelType VOICE    = new ChannelType(3,"VOICE");
    public ChannelType(int id, String name) {
        super(id, name);
    }
}
