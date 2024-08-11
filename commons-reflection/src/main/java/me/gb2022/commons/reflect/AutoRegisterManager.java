package me.gb2022.commons.reflect;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AutoRegisterManager<I> {
    private final Map<String, Consumer<I>> attachHandlers = new HashMap<>();
    private final Map<String, Consumer<I>> detachHandlers = new HashMap<>();

    public AutoRegisterManager() {
        this.init();
    }

    public void registerHandler(String type, Consumer<I> attach, Consumer<I> detach) {
        this.attachHandlers.put(type, attach);
        this.detachHandlers.put(type, detach);
    }

    public void attach(I object) {
        Annotations.matchAnnotation(object, AutoRegister.class, (a) -> {
            for (String s : a.value()) {
                if (!this.attachHandlers.containsKey(s)) {
                    this.handleAttachFailed(object, s);
                    continue;
                }

                this.attachHandlers.get(s).accept(object);
            }
        });
    }

    public void detach(I object) {
        Annotations.matchAnnotation(object, AutoRegister.class, (a) -> {
            for (String s : a.value()) {
                if (!this.detachHandlers.containsKey(s)) {
                    this.handleAttachFailed(object, s);
                    continue;
                }

                this.detachHandlers.get(s).accept(object);
            }
        });
    }

    public void handleAttachFailed(I object, String type) {

    }

    public void handleDetachFailed(I object, String type) {

    }

    public void init() {
    }
}
