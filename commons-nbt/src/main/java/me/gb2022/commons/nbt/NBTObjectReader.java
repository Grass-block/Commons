package me.gb2022.commons.nbt;

public interface NBTObjectReader<T> {
    T read(NBTBase tag);
}
