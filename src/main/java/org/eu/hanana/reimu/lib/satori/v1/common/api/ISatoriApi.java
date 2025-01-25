package org.eu.hanana.reimu.lib.satori.v1.common.api;

public interface ISatoriApi {
    IChannelApi getChannelApi();
    IGuildApi getGuildApi();
    IGuildMemberApi getGuildMemberApi();
    IGuildRoleApi getGuildRoleApi();
    IInteractionApi InteractionApi();
    ILoginApi getLoginApi();
    IMessageApi getMessageApi();
    IReactionApi getReactionApi();
    IUserApi getUserApi();
}
