package me.gb2022.commons.event;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

public class SimpleEventBus extends EventBus<EventHandler, SimpleEventBus.SimpleHandlerInstance> {
    @Override
    public SimpleHandlerInstance createHandlerInstance(Method method, EventHandler handler, Object listener) {
        return new SimpleHandlerInstance(listener, method);
    }

    @Override
    public Class<EventHandler> getHandlerAnnotationClass() {
        return EventHandler.class;
    }

    @Override
    public void execute(List<SimpleHandlerInstance> listeners, Object event, Object[] addition) {
        listeners.sort(Comparator.comparingInt(SimpleHandlerInstance::getPriority));
        for (SimpleHandlerInstance instance : listeners) {
            instance.call(event, addition);
        }
    }

    public static final class SimpleHandlerInstance extends HandlerInstance {
        private final int priority;
        private final boolean ignoreCancel;
        private final String[] additions;

        public SimpleHandlerInstance(Object handler, Method method) {
            super(handler, method);
            EventHandler handlerAnnotation = method.getAnnotation(EventHandler.class);
            this.priority = handlerAnnotation.priority();
            this.ignoreCancel = handlerAnnotation.ignoreCancel();

            SubscribedEvent event = method.getAnnotation(SubscribedEvent.class);

            if (event == null) {
                this.additions = null;
            } else {
                this.additions = event.value();
            }
        }

        @Override
        public boolean ignoreCancel() {
            return this.ignoreCancel;
        }

        @Override
        public boolean shouldCall(Object event, Object[] additions) {
            List<String> _additions = List.of(this.additions);
            for (Object o : additions) {
                if (!(o instanceof String s)) {
                    continue;
                }
                if (_additions.contains(s)) {
                    return true;
                }
            }
            return false;
        }

        public int getPriority() {
            return priority;
        }
    }
}
