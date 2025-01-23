package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildRole;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;

public class GuildRoleEvent extends SignalBodyEvent {
    public Guild getGuild() {
        return guild;
    }
    public GuildRole getRole() {
        return role;
    }
}
