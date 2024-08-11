package me.gb2022.commons.memory.backend;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class HeapBufferAllocatorBackend implements BufferAllocatorBackend {

    @Override
    public ByteBuffer allocate(int size) {
        return ByteBuffer.allocate(size);
    }

    @Override
    public void deallocate(Buffer buffer) {
        buffer.clear();
    }
}
