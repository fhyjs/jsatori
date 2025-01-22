package org.eu.hanana.reimu.lib.satori.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;

public class WsUtil {
    public static void send(Channel channel,Signal signal){
        channel.writeAndFlush(new TextWebSocketFrame(signal.serializeStr()));
    }
}
