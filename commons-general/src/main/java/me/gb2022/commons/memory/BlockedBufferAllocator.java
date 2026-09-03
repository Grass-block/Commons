package me.gb2022.commons.memory;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BlockedBufferAllocator extends BufferAllocator {
    private final ByteBuffer handle;
    private final long pointer;
    private final int blockSize;
    private final int blockCount;
    private final boolean[] blockStatus;
    private final AtomicInteger allocatableBlocks = new AtomicInteger();

    public BlockedBufferAllocator(ByteBuffer handle, int blockSize, int blockCount) {
        super(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.handle = handle;
        this.blockSize = blockSize;
        this.blockCount = blockCount;
        this.blockStatus = new boolean[blockCount];
        this.allocatableBlocks.set(blockCount);

        this.pointer = address(handle);
    }

    public BlockedBufferAllocator(BufferAllocator handle, int blockSize, int blockCount) {
        this(handle.allocByteBuffer(blockSize * blockCount), blockSize, blockCount);
    }


    private boolean attemptLock(int start, int length) {
        for (var p = start; p < start + length; p++) {
            if (this.blockStatus[p]) {
                for (var fp = start; fp < p; fp++) {
                    this.blockStatus[fp] = false;
                }

                return false;
            }

            this.blockStatus[p] = true;
        }

        return true;
    }

    @Override
    public ByteBuffer allocateBuffer(int size) {
        int slicesNeeded = (int) Math.ceil((double) size / this.blockSize);  // 需要的切片数

        for (int i = 0; i <= this.blockCount - slicesNeeded; i++) {
            if (attemptLock(i, slicesNeeded)) {
                this.allocatableBlocks.addAndGet(-slicesNeeded);

                int startPosition = i * this.blockSize;
                return wrap(this.pointer + startPosition, slicesNeeded * this.blockSize);
            }
        }
        throw new OutOfMemoryError("Not enough contiguous memory available");
    }

    public boolean testPreAllocate(int size) {
        int slicesNeeded = (int) Math.ceil((double) size / this.blockSize);  // 需要的切片数
        for (int i = 0; i <= this.blockCount - slicesNeeded; i++) {
            // 检查是否有连续的空闲切片
            boolean canAllocate = true;
            for (int j = 0; j < slicesNeeded; j++) {
                if (this.blockStatus[i + j]) {
                    canAllocate = false;
                    break;
                }
            }
            if (canAllocate) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void freeBuffer(Buffer buffer) {
        var addr = address(buffer);
        var offset = (int) (addr - this.pointer);
        var offsetBlock = offset / this.blockSize;
        var slices = (int) Math.ceil((double) buffer.capacity() / this.blockSize);


        if (offset < 0 || offsetBlock > this.blockCount) {
            throw new IllegalArgumentException("not aligned with pool buffer!(%s->%s)".formatted(addr, offset));
        }


        // 标记这些切片为已释放
        for (int i = 0; i < slices; i++) {
            this.blockStatus[offsetBlock + i] = false;
        }

        this.allocatableBlocks.addAndGet(slices);
    }

    public abstract long address(Buffer pointer);

    public abstract ByteBuffer wrap(long address, int size);

    public ByteBuffer getHandle() {
        return handle;
    }

    public long getPointer() {
        return pointer;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getAllocatableBlocks() {
        return allocatableBlocks.intValue();
    }
}
