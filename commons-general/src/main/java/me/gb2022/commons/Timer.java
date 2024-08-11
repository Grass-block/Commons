package me.gb2022.commons;

import me.gb2022.commons.container.ThreadLocalStorage;

public interface Timer {
    ThreadLocalStorage<Timer> INSTANCES = new ThreadLocalStorage<>(TimerInstance::new);

    @SuppressWarnings("UnusedReturnValue")
    static long restartTiming() {
        return getTimer().restart();
    }

    static long passedTime() {
        return getTimer().passed();
    }

    static Timer getTimer() {
        return INSTANCES.get();
    }

    long restart();

    long passed();

    class TimerInstance implements Timer {
        private long start = -1L;

        @Override
        public long restart() {
            long now = System.currentTimeMillis();
            long passed = now - this.start;
            this.start = now;
            return passed;
        }

        @Override
        public long passed() {
            return System.currentTimeMillis() - this.start;
        }
    }
}
