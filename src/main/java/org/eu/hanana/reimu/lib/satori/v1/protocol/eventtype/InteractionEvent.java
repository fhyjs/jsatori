package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.protocol.Argv;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Button;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public abstract class InteractionEvent extends SignalBodyEvent {
    public static class InteractionEventButton extends InteractionEvent{
        public Button getButton(){
            return this.button;
        }
    }
    public static class InteractionEventCommand extends InteractionEvent{
        public Argv getArgv(){
            return this.argv;
        }
        public Message getMessage(){
            return this.message;
        }
    }
}