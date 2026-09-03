package me.gb2022.commons.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList<T extends NBTBase> extends NBTBase {
    private List<T> tagList;
    private NBTType tagType;

    public NBTTagList() {
        super();
        this.tagList = new ArrayList<>();
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getType();
        } else {
            this.tagType = NBTType.TAG_BYTE;
        }
        dataOutput.writeByte(this.tagType.getValue());
        dataOutput.writeInt(this.tagList.size());
        for (NBTBase nbtBase : this.tagList) {
            nbtBase.writeTagContents(dataOutput);
        }
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.tagType = NBTType.from(dataInput.readByte());
        final int int1 = dataInput.readInt();
        this.tagList = new ArrayList<>();
        for (int i = 0; i < int1; ++i) {
            final NBTBase tagOfType = NBT.createTag(this.tagType);
            if (tagOfType != null) {
                tagOfType.readTagContents(dataInput);
            }
            this.tagList.add((T) tagOfType);
        }
    }

    @Override
    public NBTType getType() {
        return NBTType.TAG_LIST;
    }

    @Override
    public String toString() {
        return this.tagList.size() + " entries of type " + NBT.getTagName(this.tagType.getValue());
    }

    public void setTag(final T hm) {
        this.tagType = hm.getType();
        this.tagList.add(hm);
    }


    public List<T> getTagList() {
        return tagList;
    }

    public int size() {
        return this.tagList.size();
    }

    public void clear() {
        this.tagList.clear();
    }


    //---[add]---
    public void add(T tag) {
        this.tagList.add(tag);
    }

    public void set(int position, final T tag) {
        this.tagList.set(position, tag);
    }

    //---[get]---
    public T get(int position) {
        return this.tagList.get(position);
    }
}
