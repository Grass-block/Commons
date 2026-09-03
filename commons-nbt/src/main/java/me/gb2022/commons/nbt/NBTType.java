package me.gb2022.commons.nbt;

public enum NBTType {
    TAG_END(0),
    TAG_BYTE(1),
    TAG_SHORT(2),
    TAG_INT(3),
    TAG_LONG(4),
    TAG_FLOAT(5),
    TAG_DOUBLE(6),
    TAG_BYTE_ARRAY(7),
    TAG_STRING(8),
    TAG_LIST(9),
    TAG_COMPOUND(10),
    TAG_INT_ARRAY(11),
    ;
    private final int value;

    NBTType(int value) {
        this.value = value;
    }

    public static NBTType from(byte b) {
        return switch (b) {
            case 0 -> TAG_END;
            case 1 -> TAG_BYTE;
            case 2 -> TAG_SHORT;
            case 3 -> TAG_INT;
            case 4 -> TAG_LONG;
            case 5 -> TAG_FLOAT;
            case 6 -> TAG_DOUBLE;
            case 7 -> TAG_BYTE_ARRAY;
            case 8 -> TAG_STRING;
            case 9 -> TAG_LIST;
            case 10 -> TAG_COMPOUND;
            case 11 -> TAG_INT_ARRAY;
            default -> throw new IllegalStateException("Unexpected value: " + b);
        };
    }

    public byte getValue() {
        return value;
    }
}
