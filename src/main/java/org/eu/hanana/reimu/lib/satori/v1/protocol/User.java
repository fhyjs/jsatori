package org.eu.hanana.reimu.lib.satori.v1.protocol;

public class User {
    public String id;
    public String name;
    public String nick;
    public String avatar;
    public boolean is_bot = false;

    @Override
    public String toString() {
        return String.format("%s(%s)%s",name,id,is_bot?"*":"");
    }
}
