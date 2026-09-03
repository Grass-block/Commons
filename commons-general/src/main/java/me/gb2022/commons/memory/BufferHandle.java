package me.gb2022.commons.memory;

import java.nio.ByteBuffer;

public final class BufferHandle<H extends ByteBuffer> {
    private final H handle;
    private final int offset;
    private final int limit;

    public BufferHandle(H handle) {
        this.handle = handle;

        this.offset = handle.position();
        this.limit = handle.limit() - this.offset;
    }

    public static BufferHandle<ByteBuffer> wrap(ByteBuffer buffer) {
        return new BufferHandle<>(buffer);
    }

    public void position(int pos) {
        this.handle.position(this.offset + pos);
    }

    public int capacity() {
        return this.limit;
    }

    public H getHandle() {
        return handle;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public ByteBuffer slice(int base, int size) {
        return this.handle.slice(base + this.offset, size);
    }
}
