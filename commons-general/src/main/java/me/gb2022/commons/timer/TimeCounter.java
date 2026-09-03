//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.gb2022.commons.timer;

public class TimeCounter {
    long start;
    long end;

    public TimeCounter() {
    }

    public void startTiming() {
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        this.end = System.currentTimeMillis();
    }

    public long getStartTime() {
        return this.start;
    }

    public long getEndTime() {
        return this.end;
    }

    public long getPassedTime() {
        return this.end - this.start;
    }
}
