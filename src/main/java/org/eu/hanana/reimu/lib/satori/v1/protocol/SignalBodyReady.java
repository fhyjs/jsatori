package org.eu.hanana.reimu.lib.satori.v1.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignalBodyReady extends SignalBodyMeta{
    public List<Login> logins;

    public List<Login> getSafeLogins() {
        return Collections.unmodifiableList(logins);
    }

    public Login findBySn(int sn){
        for (Login login : new ArrayList<>(logins)) {
            if (login.sn==sn) return login;
        }
        return null;
    }
    public boolean hasLoginWithSn(int sn){
        return findBySn(sn)!=null;
    }
}
