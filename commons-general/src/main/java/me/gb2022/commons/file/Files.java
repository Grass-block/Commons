package me.gb2022.commons.file;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Consumer;

public interface Files {
    static void copy(InputStream from, OutputStream dest) {
        try {
            from.transferTo(dest);
            from.close();
            dest.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void copy(InputStream from, File dest) {
        try {
            copy(from, new FileOutputStream(dest));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static File createFile(String path) {
        File f = new File(path);
        f.getParentFile().mkdirs();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }

    static File createFile(Path path) {
        return createFile(path.toAbsolutePath().toString());
    }

    static InputStream readFile(String path, InputStream template) {
        File f = createFile(path);
        if (f.length() == 0) {
            copy(template, f);
        }
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void readFile(String path, InputStream template, Consumer<InputStream> input) {
        InputStream stream = readFile(path, template);
        input.accept(stream);
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static InputStream readFile(Path path, InputStream template) {
        File f = createFile(path);
        if (f.length() == 0) {
            copy(template, f);
        }
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void readFile(Path path, InputStream template, Consumer<InputStream> input) {
        InputStream stream = readFile(path, template);
        input.accept(stream);
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static InputStream readFile(File f, InputStream template) {
        createFile(f.getAbsolutePath());
        if (f.length() == 0) {
            copy(template, f);
        }
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void readFile(File path, InputStream template, Consumer<InputStream> input) {
        InputStream stream = readFile(path, template);
        input.accept(stream);
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
