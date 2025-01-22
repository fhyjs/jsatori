package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ObjectByIntId.Serializer.class)
public class LoginStatus extends ObjectByIntId{
    public static final LoginStatus OFFLINE    = new LoginStatus(0,"OFFLINE");
    public static final LoginStatus ONLINE     = new LoginStatus(1,"ONLINE");
    public static final LoginStatus CONNECT    = new LoginStatus(2,"CONNECT");
    public static final LoginStatus DISCONNECT = new LoginStatus(3,"DISCONNECT");
    public static final LoginStatus RECONNECT  = new LoginStatus(4,"RECONNECT");
    public LoginStatus(int id, String name) {
        super(id, name);
    }
}
