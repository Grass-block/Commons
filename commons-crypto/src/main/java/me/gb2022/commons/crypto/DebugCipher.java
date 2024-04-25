package me.gb2022.commons.crypto;

public class DebugCipher implements CodecCipher {
    @Override
    public byte[] encode(byte[] data) {
        return data;
    }

    @Override
    public byte[] decode(byte[] data) {
        return data;
    }
}
