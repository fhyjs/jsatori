package org.eu.hanana.reimu.lib.satori.v1.protocol;

public class SignalBodyEvent extends AbstractSignalBody implements IHasSn{
    public int sn;
    public String type;
    public long timestamp;
    public Login login;
    public Argv argv;
    public Button button;
    public Channel channel;
    public Guild guild;
    public GuildMember member;
    public Message message;
    public User operator;
    public GuildRole role;
    public User user;

    @Override
    public int getSn() {
        return sn;
    }
}
