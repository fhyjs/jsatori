package org.eu.hanana.reimu.lib.satori.v1.common;

import org.eu.hanana.reimu.lib.satori.v1.client.CallbackWsReceiver;
import org.eu.hanana.reimu.lib.satori.v1.protocol.AbstractSignalBody;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public abstract class SignalEvent implements CallbackWsReceiver.Callback {
    @Override
    public final boolean onReceive(Signal signal) {
        return onReceive0(signal.op,signal.body);
    }
    public final boolean onReceive0(Opcode opcode, AbstractSignalBody abstractSignalBody){
        if (opcode.equals(Opcode.EVENT)) return onEvent(((SignalBodyEvent) abstractSignalBody).type, (SignalBodyEvent) abstractSignalBody);
        return onReceive(opcode,abstractSignalBody);
    }
    public abstract boolean onReceive(Opcode opcode, AbstractSignalBody abstractSignalBody);
    public abstract boolean onEvent(String type, SignalBodyEvent event);
}
