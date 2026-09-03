package me.gb2022.commons.event.v2;

public interface EventBusContainer<L extends EventListener<E>, E> {

    @SuppressWarnings("unchecked")
    default void trigger(E event) {
        get((Class<E>) event.getClass()).trigger(event);
    }

    <E2 extends E> EventBus<SimpleEventListener<E2>, E2> get(Class<E2> type);
}
