package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagByte extends NBTBase {
    public byte byteValue;

    public NBTTagByte() {
        super();
    }

    public NBTTagByte(final byte byte1) {
        super();
        this.byteValue = byte1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.byteValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.byteValue = dataInput.readByte();
    }

    @Override
    public NBTType getType() {
        return NBTType.TAG_BYTE;
    }

    @Override
    public String toString() {
        return "" + this.byteValue;
    }
}
