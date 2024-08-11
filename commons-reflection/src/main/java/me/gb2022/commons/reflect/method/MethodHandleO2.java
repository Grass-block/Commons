package me.gb2022.commons.reflect.method;

public interface MethodHandleO2<O, A1, A2> extends MethodHandle {
    void invoke(O object, A1 arg1, A2 arg2);
}

