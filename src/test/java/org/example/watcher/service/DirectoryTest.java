package org.example.watcher.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {

    @Test
    public void createDirectoryAndFile() throws IOException {
        File homeDir = new File("HOME");
        File testDir = new File("TEST");
        File devDir = new File("DEV");
        File counter = new File("HOME/count.txt");
        Directory.create();
        assertTrue(homeDir.exists());
        assertTrue(testDir.exists());
        assertTrue(devDir.exists());
        assertTrue(counter.exists());

    }
}