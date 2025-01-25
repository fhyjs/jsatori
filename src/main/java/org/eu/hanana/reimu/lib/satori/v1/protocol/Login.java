package org.eu.hanana.reimu.lib.satori.v1.protocol;

import java.util.List;

public class Login implements IUserId,ISerializableMsg{
    public int sn;
    public String platform;
    public String adapter;
    public User user;
    public List<String> features;
    public LoginStatus status;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Login login){
            return login.sn==sn;
        }
        return false;
    }
    public boolean isSame(Login login){
        if (login==null) return false;
        return this.serializeStr().equals(login.serializeStr());
    }
    @Override
    public String toString() {
        return String.format("[%s]%s:%s",status,user!=null?user.toString():null,platform==null?adapter:platform);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getPlatform() {
        return platform;
    }
}
