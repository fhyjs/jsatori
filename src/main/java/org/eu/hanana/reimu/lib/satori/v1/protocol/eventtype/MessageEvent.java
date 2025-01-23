package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
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
    public Mono<List<Message>> reply(SatoriClient satoriClient, String msg){
        return satoriClient.getClientApi().getMessageApi().create(login,getChannel().id,msg);
    }
}
