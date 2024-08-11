package me.gb2022.commons.memory.backend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class DirectBufferAllocatorBackend implements BufferAllocatorBackend {

    public ByteBuffer allocate(int size) {
        return ByteBuffer.allocateDirect(size);
    }

    public void deallocate(Buffer buffer) {
        try {
            Method m = Class.forName("sun.nio.ch.DirectBuffer").getMethod("cleaner");
            Method m2 = Class.forName("jdk.internal.ref.Cleaner").getMethod("clean");
            m.setAccessible(true);
            m2.setAccessible(true);
            m2.invoke(m.invoke(buffer));
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
