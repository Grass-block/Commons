package me.gb2022.commons.system;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public final class Console {
    public static final Console SYSTEM=new Console(System.in,System.out);

    private final InputStream in;
    private final PrintStream out;
    private final Scanner scanner;

    public Console(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.scanner=new Scanner(this.in);
    }

    public void print(Object... obj) {
        StringBuilder sb = new StringBuilder();
        for (Object o : obj) {
            sb.append(o);
        }
        this.out.println(sb);
    }

    public String input(Object... hint){
        print(hint);
        return this.scanner.next();
    }

    public InputStream getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
