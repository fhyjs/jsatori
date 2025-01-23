package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public class GuildEvent extends SignalBodyEvent {
    public Guild getGuild() {
        return guild;
    }
}
