package me.gb2022.commons.memory;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;


public class PooledBufferAllocator extends BufferAllocator {
    private final int low;
    private final int high;
    private final int add;
    private final int remove;

    private final int pooledBufferSize;

    private final ArrayDeque<ByteBuffer> buffers = new ArrayDeque<>();

    public PooledBufferAllocator(int cap, int low, int high, int add, int remove, int pooledBufferSize) {
        this.low = low;
        this.high = high;
        this.add = add;
        this.remove = remove;
        this.pooledBufferSize = pooledBufferSize;

        for (int i = 0; i < cap; i++) {
            this.buffers.offer(this.getBackend().allocate(this.pooledBufferSize));
        }
    }

    @Override
    public ByteBuffer create(int bytes) {
        if (this.buffers.size() < this.low) {
            for (int i = 0; i < this.add; i++) {
                this.buffers.offer(this.getBackend().allocate(this.pooledBufferSize));
            }
        }

        return this.buffers.poll();
    }

    @Override
    public void delete(ByteBuffer buffer) {
        buffer.clear();
        this.buffers.offer(buffer);

        if (this.buffers.size() > this.high) {
            for (int i = 0; i < this.remove; i++) {
                this.getBackend().deallocate(this.buffers.poll());
            }
        }
    }

    @Override
    public void freeBuffer(Buffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer allocateBuffer(int var1) {
        throw new UnsupportedOperationException();
    }
}
