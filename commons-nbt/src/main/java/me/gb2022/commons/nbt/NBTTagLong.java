package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagLong extends NBTBase
{
    public long longValue;
    
    public NBTTagLong() {
        super();
    }
    
    public NBTTagLong(final long long1) {
        super();
        this.longValue = long1;
    }
    
    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.longValue);
    }
    
    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.longValue = dataInput.readLong();
    }
    
    @Override
    public NBTType getType() {
        return NBTType.TAG_LONG;
    }
    
    @Override
    public String toString() {
        return "" + this.longValue;
    }
}
