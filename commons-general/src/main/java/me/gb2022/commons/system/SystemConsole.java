package me.gb2022.commons.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public interface SystemConsole {
    static void print(Object... obj) {
        Console.SYSTEM.print(obj);
    }

    static String input(Object... hint) {
        return Console.SYSTEM.input(hint);
    }

    static InputStream getIn() {
        return Console.SYSTEM.getIn();
    }

    static PrintStream getOut() {
        return Console.SYSTEM.getOut();
    }

    static Scanner getScanner() {
        return Console.SYSTEM.getScanner();
    }


    static Process execute(String... commandLine) {
        try {
            return Runtime.getRuntime().exec(commandLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Process execute(File dir, String... commandLine) {
        try {
            return Runtime.getRuntime().exec(commandLine, new String[0], dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
