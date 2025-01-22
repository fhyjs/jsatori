package org.eu.hanana.reimu.lib.satori.v1.client;

import org.eu.hanana.reimu.lib.satori.v1.common.SignalEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.AbstractSignalBody;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public class InternalSignalEventListener extends SignalEvent {
    public SatoriClient satoriClient;
    public InternalSignalEventListener(SatoriClient satoriClient){
        this.satoriClient=satoriClient;
    }
    @Override
    public boolean onReceive(Opcode opcode, AbstractSignalBody abstractSignalBody) {
        return false;
    }

    @Override
    public boolean onEvent(String type, SignalBodyEvent event) {
        if (type.equals("login-removed")){

            return true;
        }
        return false;
    }
}
