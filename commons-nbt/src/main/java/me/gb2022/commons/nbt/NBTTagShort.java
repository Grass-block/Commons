package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagShort extends NBTBase {
    public short shortValue;

    public NBTTagShort() {
        super();
    }

    public NBTTagShort(final short short1) {
        super();
        this.shortValue = short1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.shortValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.shortValue = dataInput.readShort();
    }

    @Override
    public NBTType getType() {
        return NBTType.TAG_SHORT;
    }

    @Override
    public String toString() {
        return "" + this.shortValue;
    }
}
