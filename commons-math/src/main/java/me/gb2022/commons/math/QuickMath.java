package me.gb2022.commons.math;

public class QuickMath {
    public static final int CACHE_SIZE = 1440;

    public static final double FULL_CIRCLE_RADIAN = 2 * Math.PI;

    public static final double[] SIN_TABLE = new double[CACHE_SIZE];
    public static final double[] COS_TABLE = new double[CACHE_SIZE];

    static {
        for (int i = 0; i < CACHE_SIZE; i++) {
            SIN_TABLE[i] = Math.sin((double) i / CACHE_SIZE * FULL_CIRCLE_RADIAN);
            COS_TABLE[i] = Math.cos((double) i / CACHE_SIZE * FULL_CIRCLE_RADIAN);
        }
    }

    public static double sin(double rad) {
        return SIN_TABLE[(int) (rad % FULL_CIRCLE_RADIAN)];
    }

    public static double cos(double rad) {
        return COS_TABLE[(int) (rad % FULL_CIRCLE_RADIAN)];
    }

    public static double tan(double rad) {
        return sin(rad) / cos(rad);
    }
}
