package org.eu.hanana.reimu.lib.satori.v1.client.api.internal;

import lombok.SneakyThrows;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.client.api.*;
import org.eu.hanana.reimu.lib.satori.v1.common.api.*;

import java.lang.reflect.Field;

public class ClientApi implements ISatoriApi, IClientHolder{
    private final MessageApi messageApi = new MessageApi();
    private final ChannelApi channelApi = new ChannelApi();
    private final GuildApi guildApi = new GuildApi();
    private final GuildMemberApi guildMemberApi = new GuildMemberApi();
    private final GuildRoleApi guildRoleApi = new GuildRoleApi();
    private final LoginApi loginApi = new LoginApi();
    private SatoriClient client;

    @Override
    public IChannelApi getChannelApi() {
        return channelApi;
    }

    @Override
    public IGuildApi getGuildApi() {
        return guildApi;
    }

    @Override
    public IGuildMemberApi getGuildMemberApi() {
        return guildMemberApi;
    }

    @Override
    public IGuildRoleApi getGuildRoleApi() {
        return guildRoleApi;
    }

    @Override
    public IInteractionApi InteractionApi() {
        return null;
    }

    @Override
    public ILoginApi getLoginApi() {
        return loginApi;
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

    @SneakyThrows
    @Override
    public void setClient(SatoriClient client) {
        this.client=client;
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object o = declaredField.get(this);
            if (o instanceof IClientHolder clientHolder){
                clientHolder.setClient(this.client);
            }
        }
    }
}
