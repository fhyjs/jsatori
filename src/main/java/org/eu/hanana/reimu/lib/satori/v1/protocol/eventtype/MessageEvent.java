package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.ISatoriApi;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Channel;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;
import org.eu.hanana.reimu.lib.satori.v1.protocol.User;
import reactor.core.publisher.Mono;

import java.util.List;

public class MessageEvent extends SignalBodyEvent {
    public Message getMessage(){
        return message;
    }
    public Channel getChannel(){
        return channel;
    }
    public User getUser(){
        return user;
    }
    public Mono<List<Message>> reply(ISatoriApi api, String msg){
        return api.getMessageApi().create(login,getChannel().id,msg);
    }
}
