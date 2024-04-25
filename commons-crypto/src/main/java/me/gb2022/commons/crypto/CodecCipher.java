package me.gb2022.commons.crypto;

public interface CodecCipher {
    byte[] encode(byte[] data);

    byte[] decode(byte[] data);
}
