package me.gb2022.commons.reflect.method;

import me.gb2022.commons.container.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface MethodHandle {
    static <M extends MethodHandle> M select(Consumer<Context<M>> consumer) {
        Context<M> ctx = new Context<>();

        consumer.accept(ctx);

        for (Pair<Assertion, M> p : ctx.refs) {
            try {
                p.getLeft().getMethod();
                return p.getRight();
            } catch (Exception ignored) {
            }
        }
        if (ctx.dummy != null) {
            return ctx.dummy;
        }
        throw new AssertionError("No handler found for method!");
    }

    class Context<M> {
        private final List<Pair<Assertion, M>> refs = new ArrayList<>();
        private M dummy;

        public void attempt(Assertion assertion, M method) {
            this.refs.add(new Pair<>(assertion, method));
        }

        public void dummy(M func) {
            this.dummy = func;
        }
    }
}
