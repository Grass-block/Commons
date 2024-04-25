package me.gb2022.commons.math;

public interface LinearInterpolation {
    static double do1(double a, double b, double t) {
        return a + (b - a) * t;
    }

    static double reverse1(double a, double b, double x) {
        return (x - a) / (b - a);
    }

    static double revers1Abs(double a, double b, double x) {
        return (x - a) / Math.abs(b - a);
    }

    static double do2(double _00, double _01, double _10, double _11, double xt, double yt) {
        double _0z = do1(_00, _10, xt);
        double _1z = do1(_01, _11, xt);
        return do1(_0z, _1z, yt);
    }

    static double do3(double _000, double _001, double _010, double _011, double _100, double _101, double _110, double _111, double xt, double yt, double zt) {
        double[][][] c = new double[2][2][2];
        c[0][0][0] = _000;
        c[0][0][1] = _001;
        c[0][1][0] = _010;
        c[0][1][1] = _011;
        c[1][0][0] = _100;
        c[1][0][1] = _101;
        c[1][1][0] = _110;
        c[1][1][1] = _111;
        double accum = 0.0;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    accum += (i * xt + (1 - i) * (1 - xt)) * (j * yt + (1 - j) * (1 - yt)) * (k * zt + (1 - k) * (1 - zt)) * c[i][j][k];
        return accum;
    }

    static org.joml.Vector3d do3(org.joml.Vector3d a, org.joml.Vector3d b, double t) {
        return new org.joml.Vector3d(do1(a.x, b.x, t), do1(a.y, b.y, t), do1(a.z, b.z, t));
    }
}
