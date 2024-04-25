package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase
{
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
    public byte getType() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "END";
    }
}
