package me.gb2022.commons.system;

import java.nio.file.Path;

public interface SystemPath {
    static String runtime(){
        return System.getProperty("user.dir");
    }

    static Path runtimePath(){
        return Path.of(runtime());
    }
}
