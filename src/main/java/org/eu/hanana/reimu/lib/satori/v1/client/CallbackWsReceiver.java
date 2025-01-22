package org.eu.hanana.reimu.lib.satori.v1.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.connection.AbstractWebSocketClientHandler;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Opcode;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Signal;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyReady;

public class CallbackWsReceiver extends AbstractWebSocketClientHandler {
    private static final Logger log = LogManager.getLogger(CallbackWsReceiver.class);
    protected Callback callback;

    public CallbackWsReceiver setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        super.onMessage(ctx, msg);
        if (msg instanceof TextWebSocketFrame textWebSocketFrame) {
            try {
                Signal parse = Signal.parse(textWebSocketFrame.text());
                var handled = callback.onReceive(parse);
                if (!handled){
                    log.warn("Unhandled Opcode:{}, data {}.",parse.op, parse.body !=null? parse.body.serializeStr():"NULL");
                }
            }catch (Throwable ignored){
                log.error(StringUtil.getFullErrorMessage(ignored));
            }

        }
    }
    public interface Callback{
        boolean onReceive(Signal signal);
    }
}
