//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.gb2022.commons.threading;

import me.gb2022.commons.timer.Timer;
import sun.misc.Unsafe;

public abstract class LoopTickingThread implements Runnable {
    protected Timer timer;
    private ThreadState state;
    private boolean running;

    public LoopTickingThread() {
        this.state = ThreadState.CONSTRUCTED;
        this.running = true;
    }

    public void init() {
    }

    public void shortTick() {
    }

    public void tick() {
    }

    public void stop() {
    }

    public boolean onException(Exception exception) {
        this.stop();
        return true;
    }

    public boolean onError(Error error) {
        this.stop();
        return true;
    }

    public boolean monitor() {
        return false;
    }

    public final void run() {
        Error e;
        try {
            this.state = ThreadState.INITIALIZING;
            this.init();
            this.state = ThreadState.INITIALIZED;
        } catch (Exception var6) {
            if (this.onException(var6)) {
                this.state = ThreadState.INITIALIZE_FAILED;
                return;
            }
        } catch (Error var7) {
            e = var7;
            if (this.onError(e)) {
                this.state = ThreadState.INITIALIZE_FAILED;
                return;
            }

            return;
        }

        while (this.isRunning()) {
            try {
                this.shortTick();
                this.getTimer().advanceTime();

                for (int i = 0; i < this.getTimer().ticks; ++i) {
                    this.tick();
                }
            } catch (Exception var4) {
                if (this.onException(var4)) {
                    this.state = ThreadState.RUNTIME_FAILED;
                    return;
                }
            } catch (Error var5) {
                e = var5;
                if (this.onError(e)) {
                    this.state = ThreadState.RUNTIME_FAILED;
                    return;
                }
            }
        }

        try {
            this.state = ThreadState.TERMINATING;
            this.stop();
            this.state = ThreadState.TERMINATED;
        } catch (Exception var2) {
            this.state = ThreadState.TERMINATING_FAILED;
            this.onException(var2);
        } catch (Error var3) {
            e = var3;
            this.state = ThreadState.TERMINATING_FAILED;
            this.onError(e);
        }

    }

    public ThreadState getState() {
        return this.state;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Timer getTimer() {
        return this.timer;
    }
}
