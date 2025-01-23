package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.*;

public class ClientApi implements IClientApi {
    private final MessageApi messageApi = new MessageApi();
    private SatoriClient client;

    @Override
    public IChannelApi getChannelApi() {
        return null;
    }

    @Override
    public IGuildApi getGuildApi() {
        return null;
    }

    @Override
    public IGuildMemberApi getGuildMemberApi() {
        return null;
    }

    @Override
    public IGuildRoleApi getGuildRoleApi() {
        return null;
    }

    @Override
    public IInteractionApi InteractionApi() {
        return null;
    }

    @Override
    public ILoginApi getCLoginApi() {
        return null;
    }

    @Override
    public IMessageApi getMessageApi() {
        return messageApi;
    }

    @Override
    public IReactionApi getReactionApi() {
        return null;
    }

    @Override
    public IUserApi getUserApi() {
        return null;
    }

    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
        this.messageApi.setClient(client);
    }
}
