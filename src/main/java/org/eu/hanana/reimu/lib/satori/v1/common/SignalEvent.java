package org.eu.hanana.reimu.lib.satori.v1.common;

import org.eu.hanana.reimu.lib.satori.v1.protocol.AbstractSignalBody;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype.EventType;
import org.jetbrains.annotations.ApiStatus;

public abstract class SignalEvent implements CallbackWsReceiver.Callback {
    @Override
    @ApiStatus.Internal
    public boolean onReceive(Signal signal) {
        return onReceive0(signal.op,signal.body);
    }
    @ApiStatus.Internal
    public boolean onReceive0(Opcode opcode, AbstractSignalBody abstractSignalBody){
        if (opcode.equals(Opcode.EVENT)){
            SignalBodyEvent event = (SignalBodyEvent) abstractSignalBody;
            var parse = EventType.parse(event.type);
            if (parse != null) {
                return onEvent(parse,parse.getEventObj(event));
            }else {
                return onEvent(event.type,event);
            }
        }
        return onReceive(opcode,abstractSignalBody);
    }
    public abstract boolean onReceive(Opcode opcode, AbstractSignalBody abstractSignalBody);
    public abstract boolean onEvent(EventType<? extends SignalBodyEvent> type, SignalBodyEvent event);
    public boolean onEvent(String type, SignalBodyEvent event) {
        return false;
    }
}
