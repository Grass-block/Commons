package me.gb2022.commons.event.v2;

import java.util.HashMap;
import java.util.Map;

public class SimpleEventManager<E> implements EventBusContainer<SimpleEventListener<E>, E> {
    private final Map<Class<? extends E>, SimpleEventBus<E>> listeners = new HashMap<>();

    public <E2 extends E> void bind(Class<E2> type, SimpleEventListener<E2> listener) {
        get(type).bind(listener);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <E2 extends E> EventBus<SimpleEventListener<E2>, E2> get(Class<E2> type) {
        return (EventBus) this.listeners.computeIfAbsent(type, (a) -> new SimpleEventBus<>());
    }
}
