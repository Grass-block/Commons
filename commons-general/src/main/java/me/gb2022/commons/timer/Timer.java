//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.gb2022.commons.timer;

public class Timer {
    public static long last;
    public final float timeScale = 1.0F;
    public final float tps;
    public final float speed;
    private final float ticksPerSecond;
    public int ticks;
    public float interpolatedTime;
    public float passedTime = 0.0F;
    private long lastTime;

    public Timer(float ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
        this.lastTime = System.nanoTime();
        this.speed = 1.0F;
        this.tps = ticksPerSecond;
    }

    public static void startTiming() {
        last = System.currentTimeMillis();
    }

    public static long endTiming() {
        return System.currentTimeMillis() - last;
    }

    public void advanceTime() {
        long now = System.nanoTime();
        long passedNs = now - this.lastTime;
        this.lastTime = now;
        if (passedNs < 1L) {
            passedNs = 1L;
        }

        if (passedNs > 1000000000L) {
            passedNs = 1000000000L;
        }

        this.passedTime += (float) passedNs * this.timeScale * this.ticksPerSecond / 1.0E9F;
        this.ticks = (int) this.passedTime;

        if (this.ticks < 0) {
            this.ticks = 0;
        }
        if (this.ticks > 100) {
            this.ticks = 100;
        }


        this.passedTime -= (float) this.ticks;
        this.interpolatedTime = this.passedTime;
    }
}
