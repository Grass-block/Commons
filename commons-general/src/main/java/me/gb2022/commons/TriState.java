package me.gb2022.commons;

import java.util.function.BooleanSupplier;

public enum TriState {
    UNKNOWN,
    TRUE,
    FALSE;

    public static TriState byBoolean(final boolean value) {
        return value ? TRUE : FALSE;
    }

    public static TriState byBoolean(final Boolean value) {
        return value == null ? UNKNOWN : byBoolean(value);
    }

    public Boolean toBoolean() {
        return switch (this) {
            case TRUE -> Boolean.TRUE;
            case FALSE -> Boolean.FALSE;
            default -> null;
        };
    }

    public boolean toBooleanOrElse(final boolean other) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> other;
        };
    }

    public boolean toBooleanOrElseGet(final BooleanSupplier supplier) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> supplier.getAsBoolean();
        };
    }
}
