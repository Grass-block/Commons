//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.gb2022.commons.threading;

public enum ThreadState {
    CONSTRUCTED,
    INITIALIZING,
    INITIALIZED,
    RUNNING,
    TERMINATING,
    TERMINATED,
    INITIALIZE_FAILED,
    RUNTIME_FAILED,
    TERMINATING_FAILED;

    private ThreadState() {
    }
}
