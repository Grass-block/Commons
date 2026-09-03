package me.gb2022.commons.event;

public abstract class CancellableEvent implements Cancellable {
    private boolean cancelled;

    @Override
    public void setCancel(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
