package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class NBTTagCompound extends NBTBase {
    private final Map<String, NBTBase> tagMap;
    
    public NBTTagCompound() {
        super();
        this.tagMap = new HashMap<>();
    }
    
    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        for (NBTBase nbtBase : this.tagMap.values()) {
            NBT.write0(nbtBase, dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.tagMap.clear();
        NBTBase namedTag;
        while ((namedTag = NBT.read0(dataInput)).getType() != NBTType.TAG_END) {
            this.tagMap.put(namedTag.getKey(), namedTag);
        }
    }
    
    @Override
    public NBTType getType() {
        return NBTType.TAG_COMPOUND;
    }
    

    @Override
    public String toString() {
        return this.tagMap.size() + " entries";
    }

	public boolean equalType(String string, int i) {
		return this.getByte(string)==i;
	}

    public Map<String, NBTBase> getTagMap() {
        return tagMap;
    }

    public boolean hasKey(final String string) {
        return this.tagMap.containsKey(string);
    }

    public void remove(String target) {
        this.tagMap.remove(target);
    }

    public void clear(){
        this.tagMap.clear();
    }


    //---[set]---
    public void setTag(final String string, final NBTBase value) {
        this.tagMap.put(string, value.setKey(string));
    }

    public void setCompoundTag(final String string, final NBTTagCompound value) {
        this.tagMap.put(string, value.setKey(string));
    }

    public void set(String id, Object value) {
        setTag(id, NBT.resolve(value));
    }

    public void setByte(final String string, final byte value) {
        this.tagMap.put(string, new NBTTagByte(value).setKey(string));
    }

    public void setIntArray(String string, int[] value) {
        this.tagMap.put(string, new NBTTagIntArray(value).setKey(string));
    }

    public void setShort(final String string, final short value) {
        this.tagMap.put(string, new NBTTagShort(value).setKey(string));
    }

    public void setInteger(final String string, final int value) {
        this.tagMap.put(string, new NBTTagInt(value).setKey(string));
    }

    public void setLong(final String string, final long value) {
        this.tagMap.put(string, new NBTTagLong(value).setKey(string));
    }

    public void setFloat(final String string, final float value) {
        this.tagMap.put(string, new NBTTagFloat(value).setKey(string));
    }

    public void setDouble(final String path,final double value){
        this.tagMap.put(path,new NBTTagDouble(value).setKey(path));
    }

    public void setBoolean(final String string, final boolean value) {
        this.setByte(string, (byte)(value ? 1 : 0));
    }

    public void setString(final String string1, final String value) {
        this.tagMap.put(string1, new NBTTagString(value).setKey(string1));
    }

    public void setByteArray(final String string, final byte[] value) {
        this.tagMap.put(string, new NBTTagByteArray(value).setKey(string));
    }

    public <T extends Enum<T>> void setEnum(String id, T value) {
        setInteger(id, value.ordinal());
    }

    public <T> void setSerializable(String id, T obj, NBTObjectWriter<T> writer) {
        setTag(id, writer.write(obj));
    }


    //---[get]---
    public NBTBase getTag(String id) {
        return this.tagMap.get(id);
    }

    public NBTTagCompound getCompoundTag(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagCompound();
        }
        return (NBTTagCompound) this.tagMap.get(string);
    }

    public NBTTagList getTagList(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagList();
        }
        return (NBTTagList) this.tagMap.get(string);
    }

    public byte getByte(String id) {
        return ((NBTTagByte)this.tagMap.get(id)).byteValue;
    }

    public short getShort(String id) {
        return ((NBTTagShort)this.tagMap.get(id)).shortValue;
    }

    public int getInteger(String id) {
        return ((NBTTagInt)this.tagMap.get(id)).intValue;
    }

    public long getLong(String id) {
        return ((NBTTagLong)this.tagMap.get(id)).longValue;
    }

    public float getFloat(String id) {
        return ((NBTTagFloat)this.tagMap.get(id)).floatValue;
    }

    public double getDouble(String path){
        return ((NBTTagDouble)this.tagMap.get(path)).doubleValue;
    }

    public String getString(String id) {
        return ((NBTTagString)this.tagMap.get(id)).stringValue;
    }

    public byte[] getByteArray(String id) {
        return ((NBTTagByteArray)this.tagMap.get(id)).byteArray;
    }

    public int[] getIntArray(String id) {
        return ((NBTTagIntArray)this.tagMap.get(id)).intArray;
    }

    public boolean getBoolean(String id) {
        return getByte(id) != 0;
    }

    public <T extends Enum<T>> T getEnum(String id, Class<T> type) {
        return type.getEnumConstants()[getInteger(id)];
    }

    public <T> T getSerializable(String id, NBTObjectReader<T> reader) {
        return reader.read(getTag(id));
    }
}
