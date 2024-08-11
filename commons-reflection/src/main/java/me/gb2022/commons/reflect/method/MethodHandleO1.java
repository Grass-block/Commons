package me.gb2022.commons.reflect.method;

public interface MethodHandleO1<O, A1> extends MethodHandle {
    void invoke(O object, A1 arg);
}

