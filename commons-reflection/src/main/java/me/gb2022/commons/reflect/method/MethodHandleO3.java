package me.gb2022.commons.reflect.method;

public interface MethodHandleO3<O, A1, A2, A3> extends MethodHandle {
    void invoke(O object, A1 arg1, A2 arg2, A3 arg3);
}

