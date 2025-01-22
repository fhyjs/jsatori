package org.eu.hanana.reimu.lib.satori.v1.protocol;

import java.util.List;

public class Login {
    public int sn;
    public String platform;
    public String adapter;
    public User user;
    public List<String> features;
    public LoginStatus status;

    @Override
    public String toString() {
        return String.format("[%s]%s:%s",status,user!=null?user.toString():null,platform==null?adapter:platform);
    }
}
