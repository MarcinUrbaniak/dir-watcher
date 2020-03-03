package org.example.watcher.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Directory {

    public static void create() throws IOException {
        Set<String> paths = new HashSet<>();
        Collections.addAll(paths, "HOME", "DEV", "TEST");

        for (String stringPath : paths) {
            Path path = Paths.get(stringPath);
            if(!Files.isDirectory(path)) Files.createDirectory(path);
        }

        Path filePath = Paths.get("HOME/count.txt");
        if (!Files.exists(filePath)) Files.createFile(filePath);

        System.out.println("The file system is ready. Application started. Press Ctrl + C to finish.");
    }
}
