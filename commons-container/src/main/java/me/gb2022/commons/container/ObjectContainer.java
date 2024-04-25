package me.gb2022.commons.container;

public final class ObjectContainer<T> {
    private T t;

    public ObjectContainer(T t) {
        this.t = t;
    }

    public ObjectContainer() {
        this(null);
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }
}
