package org.eu.hanana.reimu.lib.satori.v1.client.api;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;

public interface IClientApi {
    IChannelApi getChannelApi();
    IGuildApi getGuildApi();
    IGuildMemberApi getGuildMemberApi();
    IGuildRoleApi getGuildRoleApi();
    IInteractionApi InteractionApi();
    ILoginApi getCLoginApi();
    IMessageApi getMessageApi();
    IReactionApi getReactionApi();
    IUserApi getUserApi();
    void setClient(SatoriClient client);
}
