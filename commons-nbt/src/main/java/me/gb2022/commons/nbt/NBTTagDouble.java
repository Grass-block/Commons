package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagDouble extends NBTBase {
    public double doubleValue;

    public NBTTagDouble() {
        super();
    }

    public NBTTagDouble(final double double1) {
        super();
        this.doubleValue = double1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.doubleValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.doubleValue = dataInput.readDouble();
    }

    @Override
    public NBTType getType() {
        return NBTType.TAG_DOUBLE;
    }

    @Override
    public String toString() {
        return "" + this.doubleValue;
    }
}
