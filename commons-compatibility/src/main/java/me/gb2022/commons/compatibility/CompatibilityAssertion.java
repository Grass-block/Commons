package me.gb2022.commons.compatibility;

import java.lang.reflect.Method;

@SuppressWarnings("UnusedReturnValue")
@FunctionalInterface
public interface CompatibilityAssertion {
    void run() throws Throwable;

    @FunctionalInterface
    interface ClassAssertion extends CompatibilityAssertion {
        Class<?> get() throws Throwable;

        default void run() throws Throwable {
            get();
        }
    }

    @FunctionalInterface
    interface MethodAssertion extends CompatibilityAssertion {
        Method get() throws Throwable;

        default void run() throws Throwable {
            get();
        }
    }

    @FunctionalInterface
    interface ValueAssertion extends CompatibilityAssertion {
        boolean get() throws Throwable;

        default void run() throws Throwable {
            if(!get()){
                throw new APIIncompatibleException("Assertion failed");
            }
        }
    }
}
