package me.gb2022.commons.event;

public interface Cancellable {
    void setCancel(boolean cancel);

    void cancel();

    boolean isCancelled();
}
