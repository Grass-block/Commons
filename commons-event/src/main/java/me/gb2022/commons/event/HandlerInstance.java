package me.gb2022.commons.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class HandlerInstance {
    private final Method method;
    private final Object handler;

    public HandlerInstance(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
        this.method.setAccessible(true);
    }

    public void call(Object event, Object... addition) {
        if (event instanceof Cancellable e2) {
            if (!this.ignoreCancel() && (e2.isCancelled())) {
                return;
            }
        }

        if (!shouldCall(event, addition)) {
            return;
        }
        try {
            this.method.invoke(this.handler, event);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new EventCallException(this.handler, event, e);
        }
    }

    public boolean ignoreCancel() {
        return false;
    }

    public abstract boolean shouldCall(Object event, Object... additions);

    public Object getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }
}
