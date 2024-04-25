package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
    public String key;

    public NBTBase() {
        super();
        this.key = null;
    }

    public abstract void writeTagContents(final DataOutput dataOutput) throws IOException;

    public abstract void readTagContents(final DataInput dataInput) throws IOException;

    public abstract byte getType();

    public String getKey() {
        if (this.key == null) {
            return "";
        }
        return this.key;
    }

    public NBTBase setKey(final String string) {
        this.key = string;
        return this;
    }
}

