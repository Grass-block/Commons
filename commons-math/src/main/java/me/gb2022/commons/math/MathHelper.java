package me.gb2022.commons.math;

import java.util.Arrays;
import java.util.Random;

public interface MathHelper {

    static double clamp(double a, double min, double max) {
        if (a > max) {
            a = max;
        }
        if (a < min) {
            a = min;
        }
        return a;
    }

    static long rand3(long n, long n2, long n3) {
        long l = (n * 3129871) ^ n3 * 116129781L ^ n2;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    static long _rand2(long n, long n2) {
        long what = 1145141919810L;
        long l = (n * 3129871) ^ what * 116129781L ^ n2;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    static float rand2(long n, long n2) {
        return new Random(_rand2(n, n2)).nextFloat(0.0f, 1.0f);
    }

    static double scale(double x, double outputMin, double outputMax, double inputMin, double inputMax) {
        return (x - inputMin) / (inputMax - inputMin) * (outputMax - outputMin) + outputMin;
    }

    static double pow2(double a) {
        return a * a;
    }

    static double reflect(double y, double v) {
        return v - (y - v);
    }

    static int hex2Int(String str) {
        return Integer.parseInt(str, 16);
    }

    static double min3(double d0, double d1, double d2) {
        return Math.min(d0, Math.min(d1, d2));
    }

    static double max3(double d0, double d1, double d2) {
        return Math.max(d0, Math.max(d1, d2));
    }

    static double avg(double... data) {
        double result = 0;
        for (double d : data) {
            result += d;
        }
        return result / data.length;
    }

    static double median(double... data) {
        Arrays.sort(data);
        if (data.length % 2 == 0) {
            return MathHelper.avg(data[data.length / 2], data[data.length / 2 + 1]);
        } else {
            return data[(data.length) / 2];
        }
    }

    static double max(double... data) {
        Arrays.sort(data);
        return data[data.length - 1];
    }

    static double dist(org.joml.Vector3d v0, org.joml.Vector3d v1) {
        return Math.sqrt(pow2(v0.x - v1.x) + pow2(v0.y - v1.y) + pow2(v0.z - v1.z));
    }
}