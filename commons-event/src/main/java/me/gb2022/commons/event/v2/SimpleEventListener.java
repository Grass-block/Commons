package me.gb2022.commons.event.v2;

public interface SimpleEventListener<E> extends EventListener<E> {

    default int priority(){
        return 0;
    }

    default boolean ignoreCancel(){
        return false;
    }
}
