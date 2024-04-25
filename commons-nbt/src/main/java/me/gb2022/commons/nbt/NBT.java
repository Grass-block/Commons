package me.gb2022.commons.nbt;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface NBT {
    static NBTBase read(InputStream stream) {
        try {
            return read0(new DataInputStream(stream));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static NBTBase readZipped(InputStream stream) {
        try {
            GZIPInputStream zipInput = new GZIPInputStream(stream);
            NBTBase tag = read0(new DataInputStream(zipInput));
            zipInput.close();
            return tag;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void write(NBTBase tag, OutputStream stream) {
        try {
            write0(tag, new DataOutputStream(stream));
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void writeZipped(NBTBase tag, OutputStream stream) {
        try {
            GZIPOutputStream zipOutput = new GZIPOutputStream(stream);
            write(tag, new DataOutputStream(zipOutput));
            zipOutput.close();
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static NBTBase read0(final DataInput dataInput) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 == 0) {
            return new NBTTagEnd();
        }
        final NBTBase tagOfType = createTag(byte1);
        if (tagOfType != null) {
            tagOfType.key = dataInput.readUTF();
            tagOfType.readTagContents(dataInput);
        }
        return tagOfType;
    }

    static void write0(final NBTBase hm, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(hm.getType());
        if (hm.getType() == 0) {
            return;
        }
        dataOutput.writeUTF(hm.getKey());
        hm.writeTagContents(dataOutput);
    }

    static NBTBase createTag(final byte id) {
        return switch (id) {
            case 0 -> new NBTTagEnd();
            case 1 -> new NBTTagByte();
            case 2 -> new NBTTagShort();
            case 3 -> new NBTTagInt();
            case 4 -> new NBTTagLong();
            case 5 -> new NBTTagFloat();
            case 6 -> new NBTTagDouble();
            case 7 -> new NBTTagByteArray();
            case 8 -> new NBTTagString();
            case 9 -> new NBTTagList();
            case 10 -> new NBTTagCompound();
            case 11 -> new NBTTagIntArray();
            default -> null;
        };
    }

    static String getTagName(final byte id) {
        return switch (id) {
            case 0 -> "TAG_End";
            case 1 -> "TAG_Byte";
            case 2 -> "TAG_Short";
            case 3 -> "TAG_Int";
            case 4 -> "TAG_Long";
            case 5 -> "TAG_Float";
            case 6 -> "TAG_Double";
            case 7 -> "TAG_Byte_Array";
            case 8 -> "TAG_String";
            case 9 -> "TAG_List";
            case 10 -> "TAG_Compound";
            case 11 -> "TAG_Int_Array";
            default -> "UNKNOWN";
        };
    }
}
