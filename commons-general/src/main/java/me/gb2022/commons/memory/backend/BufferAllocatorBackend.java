package me.gb2022.commons.memory.backend;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public interface BufferAllocatorBackend {
    BufferAllocatorBackend DIRECT = new DirectBufferAllocatorBackend();
    BufferAllocatorBackend HEAP = new HeapBufferAllocatorBackend();

    ByteBuffer allocate(int size);

    void deallocate(Buffer buffer);

}
