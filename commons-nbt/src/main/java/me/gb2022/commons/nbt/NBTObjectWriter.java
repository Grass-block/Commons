package me.gb2022.commons.nbt;

public interface NBTObjectWriter<T> {
    NBTBase write(T object);
}
