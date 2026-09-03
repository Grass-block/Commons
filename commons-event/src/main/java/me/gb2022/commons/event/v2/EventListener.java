package me.gb2022.commons.event.v2;

public interface EventListener<E> {
    void handle(E event);
}
