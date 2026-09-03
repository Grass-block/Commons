package me.gb2022.commons.memory;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class HeapBufferAllocator extends BufferAllocator {

    @Override
    public ByteBuffer allocateBuffer(int size) {
        return ByteBuffer.allocate(size);
    }

    @Override
    public void freeBuffer(Buffer buffer) {
        buffer.clear();
    }
}
