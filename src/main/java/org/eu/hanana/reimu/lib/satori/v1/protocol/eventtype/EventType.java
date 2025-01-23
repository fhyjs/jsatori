package org.eu.hanana.reimu.lib.satori.v1.protocol.eventtype;

import lombok.RequiredArgsConstructor;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyEvent;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings({"unchecked","rawtypes"})
@RequiredArgsConstructor
public class EventType<T extends SignalBodyEvent> {
    //guild
    public static final EventType<GuildEvent> guild_added = ((EventType) Objects.requireNonNull(parse("guild-added")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildEvent.class)));
    public static final EventType<GuildEvent> guild_updated = ((EventType) Objects.requireNonNull(parse("guild-updated")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildEvent.class)));
    public static final EventType<GuildEvent> guild_removed = ((EventType) Objects.requireNonNull(parse("guild-removed")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildEvent.class)));
    public static final EventType<GuildEvent> guild_request = ((EventType) Objects.requireNonNull(parse("guild-request")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildEvent.class)));
    //message
    public static final EventType<MessageEvent> message_created = ((EventType) Objects.requireNonNull(parse("message-created")).setEventSupplier(new DefaultEventBodyBuilder<>(MessageEvent.class)));
    public static final EventType<MessageEvent> message_updated = ((EventType) Objects.requireNonNull(parse("message-updated")).setEventSupplier(new DefaultEventBodyBuilder<>(MessageEvent.class)));
    public static final EventType<MessageEvent> message_deleted = ((EventType) Objects.requireNonNull(parse("message-deleted")).setEventSupplier(new DefaultEventBodyBuilder<>(MessageEvent.class)));
    //guild-member
    public static final EventType<GuildMemberEvent> guild_member_added = ((EventType) Objects.requireNonNull(parse("guild-member-added")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildMemberEvent.class)));
    public static final EventType<GuildMemberEvent> guild_member_updated = ((EventType) Objects.requireNonNull(parse("guild-member-updated")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildMemberEvent.class)));
    public static final EventType<GuildMemberEvent> guild_member_removed = ((EventType) Objects.requireNonNull(parse("guild-member-removed")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildMemberEvent.class)));
    public static final EventType<GuildMemberEvent> guild_member_request = ((EventType) Objects.requireNonNull(parse("guild-member-request")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildMemberEvent.class)));
    //guild-role
    public static final EventType<GuildRoleEvent> guild_role_created = ((EventType) Objects.requireNonNull(parse("guild-role-created")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildRoleEvent.class)));
    public static final EventType<GuildRoleEvent> guild_role_updated = ((EventType) Objects.requireNonNull(parse("guild-role-updated")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildRoleEvent.class)));
    public static final EventType<GuildRoleEvent> guild_role_deleted = ((EventType) Objects.requireNonNull(parse("guild-role-deleted")).setEventSupplier(new DefaultEventBodyBuilder<>(GuildRoleEvent.class)));
    //login
    public static final EventType<LoginEvent> login_added = ((EventType) Objects.requireNonNull(parse("login-added")).setEventSupplier(new DefaultEventBodyBuilder<>(LoginEvent.class)));
    public static final EventType<LoginEvent> login_removed = ((EventType) Objects.requireNonNull(parse("login-removed")).setEventSupplier(new DefaultEventBodyBuilder<>(LoginEvent.class)));
    public static final EventType<LoginEvent> login_updated = ((EventType) Objects.requireNonNull(parse("login-updated")).setEventSupplier(new DefaultEventBodyBuilder<>(LoginEvent.class)));

    public final String namespace;
    public final String operation;
    @Nullable
    protected Function<SignalBodyEvent, T> eventTFunction = null;

    public EventType<T> setEventSupplier(@Nullable Function<?, ?> eventTFunction) {
        this.eventTFunction = (Function<SignalBodyEvent, T>) eventTFunction;
        return this;
    }

    public @Nullable T getEventObj(SignalBodyEvent eventBody) {
        if (eventTFunction != null) {
            return eventTFunction.apply(eventBody);
        }
        return null;
    }

    public static class DefaultEventBodyBuilder<R extends SignalBodyEvent> implements Function<SignalBodyEvent, R> {
        public final Class<R> tClass;

        public DefaultEventBodyBuilder(Class<R> clazz) {
            this.tClass = clazz;
        }

        @Override
        public R apply(SignalBodyEvent event) {
            R t;
            try {
                t = tClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Field[] fields = event.getClass().getFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                try {
                    Object value = field.get(event);
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return t;
        }
    }

    public static <T extends SignalBodyEvent> EventType<T> parse(String input) {
        int lastDashIndex = input.lastIndexOf('-');
        if (lastDashIndex != -1) {
            String beforeLastDash = input.substring(0, lastDashIndex);
            String afterLastDash = input.substring(lastDashIndex + 1);

            EventType<T> eventType = new EventType<>(beforeLastDash, afterLastDash);
            Class<?> clazz = EventType.class;
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isPublic(field.getModifiers())) {
                    continue;
                }
                try {
                    Object o = field.get(null);
                    if (eventType.equals(o)) {
                        return (EventType<T>) o;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return eventType;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return namespace + "-" + operation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventType<?> eventType) {
            return eventType.namespace.equals(this.namespace) && eventType.operation.equals(this.operation);
        }
        return false;
    }
}
