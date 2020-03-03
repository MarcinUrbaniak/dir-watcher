package org.example.watcher;

import org.example.watcher.service.Directory;

import java.io.IOException;

import static org.example.watcher.service.Watcher.watchDirectory;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Directory.create();
        watchDirectory();
    }
}
