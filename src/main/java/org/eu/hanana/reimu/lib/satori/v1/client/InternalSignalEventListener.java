package org.eu.hanana.reimu.lib.satori.v1.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.v1.common.PagedData;
import org.eu.hanana.reimu.lib.satori.v1.common.SignalEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.*;
import org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype.EventType;
import org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype.MessageEvent;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.function.Consumer;

public class InternalSignalEventListener extends SignalEvent {
    private final Logger log = LogManager.getLogger(this);
    public SatoriClient satoriClient;
    public InternalSignalEventListener(SatoriClient satoriClient){
        this.satoriClient=satoriClient;
    }
    @Override
    public boolean onReceive(Opcode opcode, AbstractSignalBody abstractSignalBody) {
        return false;
    }

    @Override
    public boolean onEvent(final EventType<? extends SignalBodyEvent> type,final SignalBodyEvent event) {
        var flag = false;
        SignalBodyReady loginData = satoriClient.loginData;
        if (type.equals(EventType.message_created)&&event instanceof MessageEvent messageEvent){
            log.debug("Received msg {} from {} on {}",messageEvent.getMessage().content,messageEvent.getUser().name,messageEvent.login.getPlatform());
        }
        if (type.equals(EventType.login_removed)){
            loginData.logins.remove(loginData.findBySn(event.login.sn));
            flag = true;
        }else if (type.equals(EventType.login_added)){
            if (loginData.hasLoginWithSn(event.login.sn))
                loginData.logins.remove(loginData.findBySn(event.login.sn));
            loginData.logins.add(event.login);
            flag = true;
        }else if (type.equals(EventType.login_updated)){
            loginData.logins.remove(loginData.findBySn(event.login.sn));
            loginData.logins.add(event.login);
            flag = true;
        }if (type.namespace.equals("login")){
            logLogins();
        }
        return flag;
    }
    public void logLogins(){
        for (Login login : satoriClient.loginData.getSafeLogins()) {
            log.debug(login);
        }
    }
}
