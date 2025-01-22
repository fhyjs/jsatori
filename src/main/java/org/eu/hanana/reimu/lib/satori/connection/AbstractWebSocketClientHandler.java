package org.eu.hanana.reimu.lib.satori.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public abstract class AbstractWebSocketClientHandler extends WebSocketClientHandler0{
    public AbstractWebSocketClientHandler() {
        super(null);
    }

    @Override
    public final void channelActive(ChannelHandlerContext ctx) {
        this.onConnect(ctx);
    }

    @Override
    public final void channelInactive(ChannelHandlerContext ctx) {
        this.onDisconnect(ctx);
    }

    @Override
    public final void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.onMessage(ctx, (WebSocketFrame) msg);
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.onException(ctx, cause);
    }
    public void onException(ChannelHandlerContext ctx, Throwable cause){};
    public void onMessage(ChannelHandlerContext ctx, WebSocketFrame msg){};
    public void onConnect(ChannelHandlerContext ctx){};
    public void onDisconnect(ChannelHandlerContext ctx){};
}
