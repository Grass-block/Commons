package me.gb2022.commons.math;

import java.util.*;
import java.util.function.Function;

public interface StatisticMath {
    //平均数
    static double average(double... data) {
        double sum = 0;
        for (double d : data) {
            sum += d;
        }
        return sum / data.length;
    }

    //分位数
    static double quantile(double percent, double... data) {
        int expectCountLeft = (int) (Math.floor(data.length * percent) + 1);
        int expectCountRight = (int) (Math.floor(data.length * (1 - percent)) + 1);
        Arrays.sort(data);

        if (Math.abs((data.length - expectCountLeft - expectCountRight)) % 2 == 1) {
            return data[expectCountLeft - 1];
        } else {
            return (data[expectCountLeft - 2] + data[expectCountLeft - 1]) / 2;
        }
    }

    //中位数
    static double median(double... data) {
        Arrays.sort(data);
        int dataLen = data.length;
        if (dataLen % 2 == 1) {
            return data[dataLen / 2 + 1];
        }
        return (data[dataLen / 2 - 1] + data[dataLen / 2]) / 2d;
    }

    //中位数(基于百分位数计算)
    static double quantileMedian(double... data) {
        return quantile(0.5, data);
    }

    //方差
    static double variance(double... data) {
        double avg = average(data);
        double sum = 0;
        for (double d : data) {
            double d2 = d - avg;
            sum += d2 * d2;
        }
        return sum / data.length;
    }

    //标准差
    static double standardDeviation(double... data) {
        return Math.sqrt(variance(data));
    }

    //极差
    static double range(double... data) {
        Arrays.sort(data);
        return data[data.length - 1] - data[0];
    }


    //频数
    static HashMap<Double, Integer> frequency(double... data) {
        HashMap<Double, Integer> cache = new HashMap<>();

        for (double d : data) {
            if (!cache.containsKey(d)) {
                cache.put(d, 1);
                continue;
            }
            cache.put(d, cache.get(d) + 1);
        }

        return cache;
    }

    //众数
    static double[] mode(double... data) {
        HashMap<Double, Integer> cache = frequency(data);

        List<Double> result = new ArrayList<>();

        double max = 0;
        for (double d : cache.keySet()) {
            int i = cache.get(d);
            if (i < max) {
                continue;
            }
            if (i == max) {
                result.add(d);
                continue;
            }
            if (i > max) {
                result.clear();
                result.add(d);
                max = i;
            }
        }
        Double[] r = result.toArray(new Double[0]);
        double[] _result = new double[r.length];
        for (int i = 0; i < r.length; i++) {
            _result[i] = r[i];
        }
        return _result;
    }


    //求和
    static double sigma(int base, int lim, Function<Integer, Double> function) {
        double d = 0;
        for (int i = base; i < lim; i++) {
            d += function.apply(i);
        }
        return d;
    }


    //应用
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入数据(逗号分隔)：");
            String[] raw = scanner.next().split(",");


            double[] data = new double[raw.length];
            for (int i = 0; i < raw.length; i++) {
                data[i] = Double.parseDouble(raw[i]);
            }

            Arrays.sort(data);

            System.out.println("数据: " + Arrays.toString(data));
            System.out.println("---------------------------------------------");
            System.out.println("众数: " + Arrays.toString(mode(data)));
            System.out.println("中位数: " + quantileMedian(data));
            System.out.println("25%位数: " + quantile(0.25, data));
            System.out.println("平均数: " + average(data));
            System.out.println("极差: " + range(data));
            System.out.println("方差: " + variance(data));
            System.out.println("标准差: " + standardDeviation(data));
            System.out.println("西格玛(求总和): " + sigma(0, data.length, (i) -> data[i]));
            System.out.printf("%n%n%n%n%n");
        }
    }
}