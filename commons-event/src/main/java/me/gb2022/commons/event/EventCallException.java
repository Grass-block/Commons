package me.gb2022.commons.event;

public class EventCallException extends RuntimeException{
    private final Object handler;
    private final Object event;

    public EventCallException(Object handler, Object event, Exception source) {
        super(source);
        this.handler = handler;
        this.event = event;
    }

    public Object getHandler() {
        return handler;
    }

    public Object getEvent() {
        return event;
    }
}
