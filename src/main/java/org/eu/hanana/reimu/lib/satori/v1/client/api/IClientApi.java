package org.eu.hanana.reimu.lib.satori.v1.client.api;

import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.common.api.*;
import org.jetbrains.annotations.ApiStatus;

public interface IClientApi extends IClientHolder{
    IChannelApi getChannelApi();
    IGuildApi getGuildApi();
    IGuildMemberApi getGuildMemberApi();
    IGuildRoleApi getGuildRoleApi();
    IInteractionApi InteractionApi();
    ILoginApi getCLoginApi();
    IMessageApi getMessageApi();
    IReactionApi getReactionApi();
    IUserApi getUserApi();
    @ApiStatus.Internal
    void setClient(SatoriClient client);
}
