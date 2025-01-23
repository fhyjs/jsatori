package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.protocol.Guild;
import org.eu.hanana.reimu.lib.satori.v1.protocol.GuildMember;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;

public class GuildMemberEvent extends SignalBodyEvent {
    public Guild getGuild() {
        return guild;
    }
    public GuildMember getMember() {
        return member;
    }
    public User getUser() {
        return user;
    }
}
