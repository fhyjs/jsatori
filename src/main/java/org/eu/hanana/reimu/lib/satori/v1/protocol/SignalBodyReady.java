package org.eu.hanana.reimu.lib.satori.v1.protocol;

import java.util.ArrayList;
import java.util.List;

public class SignalBodyReady extends SignalBodyMeta{
    public List<Login> logins;
    public Login findBySn(int sn){
        for (Login login : new ArrayList<>(logins)) {
            if (login.sn==sn) return login;
        }
        return null;
    }
}
