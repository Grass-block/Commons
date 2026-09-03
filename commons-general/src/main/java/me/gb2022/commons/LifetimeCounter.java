package me.gb2022.commons;

public class LifetimeCounter {
    private RuntimeException lastAllocated;
    private RuntimeException lastReleased;
    private STATUS status;

    public LifetimeCounter() {
        this.status = LifetimeCounter.STATUS.BEFORE_INIT;
    }

    public void allocate() {
        if (this.status != LifetimeCounter.STATUS.BEFORE_INIT) {
            if(this.lastAllocated!=null){
                this.lastAllocated.printStackTrace();
            }

            throw new IllegalStateException("invalid status on init:" + this.status.name());
        } else {
            this.lastAllocated = new RuntimeException();
            this.status = LifetimeCounter.STATUS.ACTIVE;
        }
    }

    public void check() {
        if (this.status != LifetimeCounter.STATUS.ACTIVE) {
            this.lastAllocated = new RuntimeException();
            throw new IllegalStateException("invalid status on active:" + this.status.name());
        }
    }

    public void release() {
        if (this.status != LifetimeCounter.STATUS.ACTIVE) {
            if(this.lastReleased!=null){
                this.lastReleased.printStackTrace();
            }
            throw new IllegalStateException("invalid status on release:" + this.status.name());
        } else {
            this.lastReleased = new RuntimeException();
            this.status = LifetimeCounter.STATUS.RELEASED;
        }
    }

    public boolean isAllocated() {
        return this.status == LifetimeCounter.STATUS.ACTIVE;
    }

    public static enum STATUS {
        BEFORE_INIT,
        ACTIVE,
        RELEASED;

        private STATUS() {
        }
    }
}
