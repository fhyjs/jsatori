package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.protocol.Login;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public class LoginEvent extends SignalBodyEvent {
    public Login getLogin() {
        return login;
    }
}
