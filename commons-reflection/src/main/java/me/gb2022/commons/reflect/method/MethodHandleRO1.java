package me.gb2022.commons.reflect.method;

public interface MethodHandleRO1<O, R, A1> extends MethodHandleR {
    R invoke(O object, A1 arg);
}

