package me.gb2022.commons.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class DependencyInjector<I> {
    private final Map<Class<?>, BiFunction<String[], I, ?>> functions = new HashMap<>();

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public <T> void registerInjector(Class<T> type, BiFunction<String[], I, T> supplier) {
        this.functions.put(type, supplier);
    }

    public void inject(I object) {
        for (Field f : object.getClass().getDeclaredFields()) {
            if (!f.isAnnotationPresent(Inject.class)) {
                continue;
            }

            Inject a= f.getAnnotation(Inject.class);
            Class<?> type = f.getType();
            f.setAccessible(true);

            try {
                f.set(object, createInjection(type, object, a.value()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T createInjection(Class<T> type, I owner, String argument) {
        if (!this.hasInjector(type)) {
            throw new InjectionFailedException("no inject for type %s".formatted(type));
        }

        return type.cast(this.functions.get(type).apply(argument.split(";"), owner));
    }

    public boolean hasInjector(Class<?> type) {
        return this.functions.containsKey(type);
    }

    public static final class Builder<I> {
        private final DependencyInjector<I> injector = new DependencyInjector<I>();

        public <V> Builder<I> injector(Class<V> type, BiFunction<String[], I, V> supplier) {
            this.injector.registerInjector(type, supplier);
            return this;
        }

        public DependencyInjector<I> build() {
            return this.injector;
        }
    }
}
