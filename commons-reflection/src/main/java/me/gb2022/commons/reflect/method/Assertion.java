package me.gb2022.commons.reflect.method;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Assertion {
    Method getMethod() throws Exception;
}
