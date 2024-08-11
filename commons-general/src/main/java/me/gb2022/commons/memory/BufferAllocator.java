package me.gb2022.commons.memory;

import me.gb2022.commons.memory.backend.BufferAllocatorBackend;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BufferAllocator {
    private final AtomicInteger alloc;
    private final AtomicInteger instances;
    private final AtomicInteger leaked;
    private final AtomicInteger leakedInstances;
    private final int maxAllocateInstance;
    private final int maxAllocateCapacity;


    private final Map<Buffer, ByteBuffer> lookups = new HashMap<>();


     //private final BufferAllocatorBackend backend;

    protected BufferAllocator(int maxAllocateInstance, int maxAllocateCapacity) {
        this.alloc = new AtomicInteger(0);
        this.instances = new AtomicInteger(0);
        this.leaked = new AtomicInteger(0);
        this.leakedInstances = new AtomicInteger(0);
        this.maxAllocateInstance = maxAllocateInstance;
        this.maxAllocateCapacity = maxAllocateCapacity;
    }

    public BufferAllocator() {
        this(4096, 16777216);
    }

    public BufferAllocatorBackend getBackend() {
        return null;
    }

    public final ByteBuffer allocByteBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size);
    }

    public final ShortBuffer allocShortBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size * 2);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size * 2).asShortBuffer();
    }

    public final IntBuffer allocIntBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size * 4);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size * 4).asIntBuffer();
    }

    public final FloatBuffer allocFloatBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size * 4);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size * 4).asFloatBuffer();
    }

    public final LongBuffer allocLongBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size * 8);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size * 8).asLongBuffer();
    }

    public final DoubleBuffer allocDoubleBuffer(int size) {
        this.checkSize();
        this.alloc.addAndGet(size * 8);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size * 8).asDoubleBuffer();
    }

    public final void free(Buffer buffer) {
        if (buffer instanceof ByteBuffer) {
            this.free((ByteBuffer) buffer);
        }

        if (buffer instanceof ShortBuffer) {
            this.free((ShortBuffer) buffer);
        }

        if (buffer instanceof IntBuffer) {
            this.free((IntBuffer) buffer);
        }

        if (buffer instanceof LongBuffer) {
            this.free((LongBuffer) buffer);
        }

        if (buffer instanceof FloatBuffer) {
            this.free((FloatBuffer) buffer);
        }

        if (buffer instanceof DoubleBuffer) {
            this.free((DoubleBuffer) buffer);
        }

    }

    public final void free(ByteBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity());
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void free(ShortBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity() * 2);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void free(IntBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity() * 4);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void free(FloatBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity() * 4);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void free(LongBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity() * 8);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void free(DoubleBuffer buffer) {
        this.alloc.addAndGet(-buffer.capacity() * 8);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(Buffer buffer) {
        if (buffer instanceof ByteBuffer) {
            this.freeUnexpected((ByteBuffer) buffer);
        }

        if (buffer instanceof ShortBuffer) {
            this.freeUnexpected((ShortBuffer) buffer);
        }

        if (buffer instanceof IntBuffer) {
            this.freeUnexpected((IntBuffer) buffer);
        }

        if (buffer instanceof LongBuffer) {
            this.freeUnexpected((LongBuffer) buffer);
        }

        if (buffer instanceof FloatBuffer) {
            this.freeUnexpected((FloatBuffer) buffer);
        }

        if (buffer instanceof DoubleBuffer) {
            this.freeUnexpected((DoubleBuffer) buffer);
        }

    }

    public final void freeUnexpected(ByteBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity());
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity());
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(ShortBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity() * 2);
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity() * 2);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(IntBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity() * 4);
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity() * 4);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(FloatBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity() * 4);
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity() * 4);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(LongBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity() * 8);
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity() * 8);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public final void freeUnexpected(DoubleBuffer buffer) {
        this.leaked.addAndGet(-buffer.capacity() * 8);
        this.leakedInstances.decrementAndGet();
        this.alloc.addAndGet(-buffer.capacity() * 8);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
    }

    public abstract ByteBuffer allocateBuffer(int var1);

    public abstract void freeBuffer(Buffer buffer);

    public final long getAllocSize() {
        return this.alloc.intValue();
    }

    public final int getAllocInstances() {
        return this.instances.intValue();
    }

    public final int getLeakInstances() {
        return this.leakedInstances.intValue();
    }

    public final int getLeaked() {
        return this.leaked.intValue();
    }

    public int getMaxAllocateCapacity() {
        return this.maxAllocateCapacity;
    }

    public int getMaxAllocateInstance() {
        return this.maxAllocateInstance;
    }


    public abstract ByteBuffer create(int bytes);

    public abstract void delete(ByteBuffer buffer);


    public final void checkSize() {
        if (this.instances.get() > this.maxAllocateInstance) {
            throw new Error("off heap overflowed(%d buffers)".formatted(this.instances.get()));
        } else if (this.alloc.get() > this.maxAllocateCapacity) {
            throw new Error("off heap overflowed(%d bytes)".formatted(this.alloc.get()));
        }
    }

    public final String toString() {
        return "%dMB[%d](%dmb-%d leaked)".formatted(this.getAllocSize(), this.getAllocInstances(), this.getLeaked(), this.getLeakInstances());
    }
}
