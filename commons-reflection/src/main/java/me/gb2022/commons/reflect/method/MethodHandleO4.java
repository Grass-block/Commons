package me.gb2022.commons.reflect.method;

public interface MethodHandleO4<O, A1, A2, A3,A4> extends MethodHandle {
    void invoke(O object, A1 arg1, A2 arg2, A3 arg3,A4 arg4);
}

