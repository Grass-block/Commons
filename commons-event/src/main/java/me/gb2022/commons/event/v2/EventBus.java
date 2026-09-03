package me.gb2022.commons.event.v2;

import me.gb2022.commons.event.Cancellable;
import me.gb2022.commons.event.EventCallException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class EventBus<L extends EventListener<E>, E> {
    private final List<L> listeners = new ArrayList<>();

    public void trigger(final E event) {
        var c = event instanceof Cancellable;

        for (final L listener : listeners) {
            if (!filter(listener, event)) {
                continue;
            }
            try {
                listener.handle(event);
            } catch (Exception e) {
                throw new EventCallException(listener, event, e);
            }
        }
    }

    public void bind(L listener) {
        if (this.listeners.contains(listener)) {
            return;
        }
        this.listeners.add(listener);
        sort(this.listeners);
    }

    public void unbind(L listener) {
        this.listeners.remove(listener);
        sort(this.listeners);
    }

    public void sort(List<L> listeners) {
        listeners.sort(Comparator.comparingInt(Object::hashCode));
    }

    public boolean filter(L listener, E event) {
        return !(event instanceof Cancellable) || !((Cancellable) event).isCancelled();
    }
}
