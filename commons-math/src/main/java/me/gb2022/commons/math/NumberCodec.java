package me.gb2022.commons.math;

import java.nio.ByteBuffer;

/**
 * code number to bytes,or decode number from bytes.
 *
 * @author GrassBlock2022
 */
public interface NumberCodec {
    ByteBuffer buffer = ByteBuffer.allocate(8);

    static byte[] split(short n) {
        return buffer.clear().putShort(n).array();
    }

    static byte[] split(int n) {
        return buffer.clear().putInt(n).array();
    }

    static byte[] split(long n) {
        return buffer.clear().putLong(n).array();
    }

    static byte[] split(float n){
        return buffer.clear().putFloat(n).array();
    }

    static byte[] split(double n){
        return buffer.clear().putDouble(n).array();
    }


    static short asShort(byte[] arr){
        return buffer.clear().put(arr,0,2).flip().getShort();
    }

    static int asInt(byte[] arr){
        return buffer.clear().put(arr,0,4).flip().getInt();
    }

    static long asLong(byte[] arr){
        return buffer.clear().put(arr,0,8).flip().getInt();
    }

    static float asFloat(byte[] arr){
        return buffer.clear().put(arr,0,4).flip().getFloat();
    }

    static double asDouble(byte[] arr){
        return buffer.clear().put(arr,0,8).flip().getDouble();
    }
}
