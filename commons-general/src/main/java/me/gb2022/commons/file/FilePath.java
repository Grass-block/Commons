package me.gb2022.commons.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public final class FilePath {
    public static final FilePath RUNTIME = new FilePath(System.getProperty("user.dir"));
    public static final FilePath ROOT = new FilePath();

    private final String path;

    public FilePath(String path) {
        this.path = path;
    }

    public FilePath() {
        this.path = "/";
    }

    public String toString() {
        return this.path;
    }

    public FilePath append(String path) {
        return new FilePath(this.path + "/" + path);
    }

    public FilePath append(FilePath path) {
        return new FilePath(this.path + path.path);
    }


    public File file() {
        return new File(this.path);
    }

    public InputStream resource() {
        return FilePath.class.getResourceAsStream(this.path);
    }

    public InputStream zip(ZipFile zip) throws IOException {
        return zip.getInputStream(zip.getEntry(this.path));
    }

    public InputStream jar(JarFile jar) throws IOException {
        return jar.getInputStream(jar.getEntry(this.path));
    }
}
