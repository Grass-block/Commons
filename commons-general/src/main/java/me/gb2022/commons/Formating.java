package me.gb2022.commons;

public interface Formating {
    String TIME_SHORT = "%d:%d:%d";
    String TIME_FULL = "%dh %dm %ds";

    static String formatDuringShort(long mss) {
        return formatDuring(mss, TIME_SHORT);
    }

    static String formatDuring(long mss, String pattern) {
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        return pattern.formatted(hours, minutes, seconds);
    }

    static String formatDuringFull(long mss) {
        return formatDuring(mss, TIME_FULL);
    }
}
