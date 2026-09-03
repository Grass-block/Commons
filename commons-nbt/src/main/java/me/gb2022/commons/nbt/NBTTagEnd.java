package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public final class NBTTagEnd extends NBTBase {
    public NBTTagEnd() {
        super();
    }

    @Override
    public void readTagContents(final DataInput dataInput) {
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) {
    }

    @Override
    public NBTType getType() {
        return NBTType.TAG_END;
    }

    @Override
    public String toString() {
        return "END";
    }
}
