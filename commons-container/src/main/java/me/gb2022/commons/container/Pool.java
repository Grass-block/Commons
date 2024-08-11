package me.gb2022.commons.container;

import java.util.ArrayList;
import java.util.List;

public class Pool<E> {
    private final List<E> container;
    protected final PoolResourceHandler<E> handler;
    private final PoolConfig config;


    public Pool(PoolConfig config, PoolResourceHandler<E> handler) {
        this.config = config;

        this.container = new ArrayList<>(config.initial);
        this.handler = handler;

        alloc(this.config.initial);
    }

    public static PoolConfig cfg(int initial, float minFactor, float maxFactor, int minBatchSize, int maxBatchSize) {
        return new PoolConfig(initial, minFactor, maxFactor, minBatchSize, maxBatchSize);
    }

    private void alloc(int amount) {
        for (int i = 0; i < amount; i++) {
            this.container.add(this.handler.create());
        }
    }

    public E create() {
        if (this.container.size() < this.config.initial * this.config.minFactor) {
            this.alloc(this.config.minBatchSize);
        }
        if (this.container.size() > this.config.initial * this.config.maxFactor) {
            for (int i = 0; i < this.config.maxBatchSize; i++) {
                this.handler.release(this.container.remove(0));
            }
        }

        E item = null;

        while (item == null) {
            item = this.container.remove(0);
        }

        this.handler.onPoolAllocated(item);

        return item;
    }

    public void release(E item) {
        if (this.container.size() > this.config.initial * this.config.maxFactor) {
            for (int i = 0; i < this.config.maxBatchSize; i++) {
                this.handler.release(this.container.remove(0));
            }
        }

        this.handler.onReleaseToPool(item);
        this.container.add(item);
    }

    public interface PoolResourceHandler<E> {
        E create();

        void release(E item);

        default void onReleaseToPool(E item) {
        }

        default void onPoolAllocated(E item) {
        }

    }

    public static final class PoolConfig {
        private final int initial;
        private final float minFactor;
        private final float maxFactor;
        private final int minBatchSize;
        private final int maxBatchSize;

        public PoolConfig(int initial, float minFactor, float maxFactor, int minBatchSize, int maxBatchSize) {
            this.initial = initial;
            this.minFactor = minFactor;
            this.maxFactor = maxFactor;
            this.minBatchSize = minBatchSize;
            this.maxBatchSize = maxBatchSize;
        }
    }
}
