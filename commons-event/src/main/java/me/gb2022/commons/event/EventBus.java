package me.gb2022.commons.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventBus<H extends Annotation, I extends HandlerInstance> {
    private final HashMap<Object, HashMap<String, List<I>>> handlers = new HashMap<>(128);

    public abstract I createHandlerInstance(Method method, H handler, Object listener);

    public abstract Class<H> getHandlerAnnotationClass();

    public abstract void execute(List<I> listeners, Object event, Object[] addition);

    private Map<String, List<I>> getHandlerInstanceCache(Object key) {
        if (!this.handlers.containsKey(key)) {
            this.handlers.put(key, new HashMap<>(8));
        }
        return this.handlers.get(key);
    }

    public void callEvent(Object event, Object... addition) {
        List<I> handlers = new ArrayList<>(128);

        for (HashMap<String, List<I>> listenerHandlers : this.handlers.values()) {
            if (!listenerHandlers.containsKey(event.getClass().getName())) {
                continue;
            }
            handlers.addAll(listenerHandlers.get(event.getClass().getName()));
        }

        this.execute(handlers, event, addition);
    }

    public void registerEventListener(Object listener) {
        Map<String, List<I>> map = getHandlerInstanceCache(listener);

        for (Method m : listener.getClass().getMethods()) {
            H eventHandler = m.getAnnotation(this.getHandlerAnnotationClass());
            if (eventHandler == null) {
                continue;
            }
            if (Modifier.isStatic(m.getModifiers())) {
                continue;
            }
            String eid = m.getParameters()[0].getType().getName();
            I instance = createHandlerInstance(m, eventHandler, listener);
            if (!map.containsKey(eid)) {
                map.put(eid, new ArrayList<>());
            }
            map.get(eid).add(instance);
        }
    }

    public void unregisterEventListener(Object listener) {
        if (!this.handlers.containsKey(listener)) {
            return;
        }
        this.handlers.remove(listener);
    }

    public void registerEventListener(Class<?> clazz) {
        Map<String, List<I>> map = getHandlerInstanceCache(clazz);

        for (Method m : clazz.getMethods()) {
            H eventHandler = m.getAnnotation(getHandlerAnnotationClass());
            if (eventHandler == null) {
                continue;
            }
            if (!Modifier.isStatic(m.getModifiers())) {
                continue;
            }
            String eid = m.getParameters()[0].getType().getName();
            I instance = createHandlerInstance(m, eventHandler, null);
            if (!map.containsKey(eid)) {
                map.put(eid, new ArrayList<>());
            }
            map.get(eid).add(instance);
        }
    }

    public void unregisterEventListener(Class<?> clazz) {
        if (!this.handlers.containsKey(clazz)) {
            return;
        }
        this.handlers.remove(clazz);
    }
}
