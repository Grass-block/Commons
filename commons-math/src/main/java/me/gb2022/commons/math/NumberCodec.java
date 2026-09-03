package me.gb2022.commons.math;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * code number to bytes,or decode number from bytes.
 *
 * @author GrassBlock2022
 */
public interface NumberCodec {
    ByteBuffer buffer = ByteBuffer.allocate(8);

    static byte[] split(short n) {
        return impl.getInstance().split(n);
    }

    static byte[] split(int n) {
        return impl.getInstance().split(n);
    }

    static byte[] split(long n) {
        return impl.getInstance().split(n);
    }

    static byte[] split(float n) {
        return impl.getInstance().split(n);
    }

    static byte[] split(double n) {
        return impl.getInstance().split(n);
    }


    static short asShort(byte[] arr) {
        return impl.getInstance().asShort(arr);
    }

    static int asInt(byte[] arr) {
        return impl.getInstance().asInt(arr);
    }

    static long asLong(byte[] arr) {
        return impl.getInstance().asLong(arr);
    }

    static float asFloat(byte[] arr) {
        return impl.getInstance().asFloat(arr);
    }

    static double asDouble(byte[] arr) {
        return impl.getInstance().asDouble(arr);
    }


    final class impl {
        private static final Map<Thread, impl> INSTANCES = new ConcurrentHashMap<>();
        ByteBuffer buffer = ByteBuffer.allocate(8);

        static impl getInstance() {
            return INSTANCES.computeIfAbsent(Thread.currentThread(), (s) -> new impl());
        }

        byte[] split(short n) {
            return buffer.clear().putShort(n).array();
        }

        byte[] split(int n) {
            return buffer.clear().putInt(n).array();
        }

        byte[] split(long n) {
            return buffer.clear().putLong(n).array();
        }

        byte[] split(float n) {
            return buffer.clear().putFloat(n).array();
        }

        byte[] split(double n) {
            return buffer.clear().putDouble(n).array();
        }


        short asShort(byte[] arr) {
            return buffer.clear().put(arr, 0, 2).flip().getShort();
        }

        int asInt(byte[] arr) {
            return buffer.clear().put(arr, 0, 4).flip().getInt();
        }

        long asLong(byte[] arr) {
            return buffer.clear().put(arr, 0, 8).flip().getInt();
        }

        float asFloat(byte[] arr) {
            return buffer.clear().put(arr, 0, 4).flip().getFloat();
        }

        double asDouble(byte[] arr) {
            return buffer.clear().put(arr, 0, 8).flip().getDouble();
        }
    }
}
