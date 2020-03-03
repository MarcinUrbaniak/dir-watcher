package org.example.watcher.service;

import java.io.IOException;
import java.nio.file.*;

import static org.example.watcher.service.FileCopier.*;

public class Watcher {

    public static void watchDirectory() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("HOME").register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                copyFiles(event.context().toString());
                writeCount();
            }
            key.reset();
        }
    }
}
