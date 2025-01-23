package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import org.eu.hanana.reimu.lib.satori.connection.NettyHttpClient;
import org.eu.hanana.reimu.lib.satori.util.NetUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.IMessageApi;
import org.eu.hanana.reimu.lib.satori.v1.common.ApiMethod;
import org.eu.hanana.reimu.lib.satori.v1.common.DefaultApiData;
import org.eu.hanana.reimu.lib.satori.v1.protocol.Message;
import reactor.core.publisher.Mono;

public class MessageApi implements IMessageApi {
    public static final ApiMethod<DefaultApiData> AM_Create = ApiMethod.parse("message.create", DefaultApiData.class);
    protected SatoriClient client;
    @Override
    public Mono<Message> create(String channel_id, String content) {
        NetUtil.send(client.getHttpClient(),client.baseHttpUrl.toString(),AM_Create,new DefaultApiData().putHeader("channel_id",channel_id).putHeader("content",content));
    }

    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
    }
}
