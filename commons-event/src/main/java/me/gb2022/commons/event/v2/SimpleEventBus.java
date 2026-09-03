package me.gb2022.commons.event.v2;

import me.gb2022.commons.event.Cancellable;

import java.util.Comparator;
import java.util.List;

public class SimpleEventBus<E> extends EventBus<SimpleEventListener<E>, E> {
    public static final Comparator<SimpleEventListener<?>> COMPARATOR = (o1, o2) -> {
        var p1 = o1.priority();
        var p2 = o2.priority();

        var c0 = Integer.compare(p1, p2);

        if (c0 != 0) {
            return c0;
        }

        return Integer.compare(o1.hashCode(), o2.hashCode());
    };

    @Override
    public void sort(List<SimpleEventListener<E>> listeners) {
        listeners.sort(COMPARATOR);
    }

    @Override
    public boolean filter(SimpleEventListener<E> listener, E event) {
        if (!(event instanceof Cancellable c)) {
            return true;
        }

        if (c.isCancelled()) {
            return listener.ignoreCancel();
        }

        return true;
    }
}
