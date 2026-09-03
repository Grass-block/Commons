package me.gb2022.commons.memory;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BufferAllocator {
    private final AtomicLong alloc;
    private final AtomicLong instances;
    private final AtomicLong leaked;
    private final AtomicLong leakedInstances;
    private final int maxAllocateInstance;
    private final int maxAllocateCapacity;

    private final Map<Buffer, ByteBuffer> lookups = new HashMap<>();

    protected BufferAllocator(int maxAllocateInstance, int maxAllocateCapacity) {
        this.alloc = new AtomicLong(0);
        this.instances = new AtomicLong(0);
        this.leaked = new AtomicLong(0);
        this.leakedInstances = new AtomicLong(0);
        this.maxAllocateInstance = maxAllocateInstance;
        this.maxAllocateCapacity = maxAllocateCapacity;
    }

    public BufferAllocator() {
        this(4096, 16777216);
    }

    private <T extends Buffer> T addRecord(T buffer) {
        return buffer;
    }

    private void removeRecord(Buffer buffer) {
    }


    //----[alloc]----
    private ByteBuffer allocate0(int size) {
        this.checkSize();
        this.alloc.addAndGet(size);
        this.instances.incrementAndGet();
        return this.allocateBuffer(size);
    }

    public final ByteBuffer allocByteBuffer(int size) {
        return this.addRecord(allocate0(size));
    }

    public final ShortBuffer allocShortBuffer(int size) {
        return this.addRecord(allocate0(size * 2).asShortBuffer());
    }

    public final IntBuffer allocIntBuffer(int size) {
        return this.addRecord(allocate0(size * 4).asIntBuffer());
    }

    public final FloatBuffer allocFloatBuffer(int size) {
        return this.addRecord(allocate0(size * 4).asFloatBuffer());
    }

    public final LongBuffer allocLongBuffer(int size) {
        return this.addRecord(allocate0(size * 8).asLongBuffer());
    }

    public final DoubleBuffer allocDoubleBuffer(int size) {
        return this.addRecord(allocate0(size * 8).asDoubleBuffer());
    }


    //----[free]----
    private void free0(Buffer buffer, int dataBytes) {
        this.alloc.addAndGet((long) -buffer.capacity() * dataBytes);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
        this.removeRecord(buffer);
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
        free0(buffer, 1);
    }

    public final void free(ShortBuffer buffer) {
        free0(buffer, 2);
    }

    public final void free(IntBuffer buffer) {
        free0(buffer, 4);
    }

    public final void free(FloatBuffer buffer) {
        free0(buffer, 4);
    }

    public final void free(LongBuffer buffer) {
        free0(buffer, 8);
    }

    public final void free(DoubleBuffer buffer) {
        free0(buffer, 8);
    }


    //----[unexpected]----
    private void freeUnexpected0(Buffer buffer, int dataBytes) {
        this.leaked.addAndGet((long) buffer.capacity() * dataBytes);
        this.leakedInstances.incrementAndGet();
        this.alloc.addAndGet((long) -buffer.capacity() * dataBytes);
        this.instances.decrementAndGet();
        this.freeBuffer(buffer);
        this.removeRecord(buffer);
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
        freeUnexpected0(buffer, 1);
    }

    public final void freeUnexpected(ShortBuffer buffer) {
        freeUnexpected0(buffer, 2);
    }

    public final void freeUnexpected(IntBuffer buffer) {
        freeUnexpected0(buffer, 4);
    }

    public final void freeUnexpected(FloatBuffer buffer) {
        freeUnexpected0(buffer, 4);
    }

    public final void freeUnexpected(LongBuffer buffer) {
        freeUnexpected0(buffer, 8);
    }

    public final void freeUnexpected(DoubleBuffer buffer) {
        freeUnexpected0(buffer, 8);
    }


    public abstract ByteBuffer allocateBuffer(int size);

    public abstract void freeBuffer(Buffer buffer);

    public long hashcode(Buffer buffer) {
        return buffer.hashCode();
    }

    public final long getAllocSize() {
        return this.alloc.longValue();
    }

    public final long getAllocInstances() {
        return this.instances.longValue();
    }

    public final long getLeakInstances() {
        return this.leakedInstances.longValue();
    }

    public final long getLeaked() {
        return this.leaked.longValue();
    }

    public int getMaxAllocateCapacity() {
        return this.maxAllocateCapacity;
    }

    public int getMaxAllocateInstance() {
        return this.maxAllocateInstance;
    }


    public final void checkSize() {
        if (this.instances.get() > this.maxAllocateInstance) {
            throw new Error("off heap overflowed(%d buffers)".formatted(this.instances.get()));
        } else if (this.alloc.get() > this.maxAllocateCapacity) {
            throw new Error("off heap overflowed(%d bytes)".formatted(this.alloc.get()));
        }
    }

    public final String toString() {
        return "%dMB[%d](%dmb-%d leaked)".formatted(
                this.getAllocSize(),
                this.getAllocInstances(),
                this.getLeaked(),
                this.getLeakInstances()
        );
    }

    public void clear() {
        this.alloc.set(0);
        this.instances.set(0);
    }

    @Override
    protected void finalize() {
        this.clear();
    }
}
