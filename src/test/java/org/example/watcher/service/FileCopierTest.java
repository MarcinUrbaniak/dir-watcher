package org.example.watcher.service;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.watcher.service.FileCopier.writeCount;
import static org.junit.jupiter.api.Assertions.*;

class FileCopierTest {

    private static List<String> fileNameList = new ArrayList<>();
    private static List<String> dirNameList = new ArrayList<>();
    private static final String HOME = "HOME/";

    @BeforeAll
    public static void createFile() throws IOException {
        Directory.create();
        Collections.addAll(fileNameList, "even.jar", "odd.jar", "file.xml");

        fileNameList.forEach(fileName -> {
            try {
                Files.createFile(Paths.get(HOME + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long evenHour = 1582986486798L;
        long oddHour = 1582990086798L;
        String evenFileName = HOME + fileNameList.get(0);
        String oddFileName = HOME + fileNameList.get(1);

        setFileCreationHour(evenHour, evenFileName);
        setFileCreationHour(oddHour, oddFileName);
    }

    @Test
    public void copyFilesTest() {
        fileNameList.forEach(fileName -> {
            try {
                FileCopier.copyFiles(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        assertTrue((new File("TEST/" + fileNameList.get(0)).exists()));
        assertTrue((new File("DEV/" + fileNameList.get(1)).exists()));
        assertTrue((new File("DEV/" + fileNameList.get(2)).exists()));

        List<String> lines = new ArrayList<>();
        try {
            writeCount();
            lines = Files.readAllLines(Paths.get("HOME/count.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Katalog HOME, Licznik plikow: 3", lines.get(0));
        assertEquals("Katalog DEV, Licznik plikow: 2",lines.get(1));
        assertEquals("Katalog TEST, Licznik plikow: 1", lines.get(2));

    }

    @AfterAll
    public static void deleteFiles(){
        Collections.addAll(dirNameList, "HOME", "DEV", "TEST");
        dirNameList.forEach(dirName -> {

            try {
                Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                    public FileVisitResult postVisitDirectory(Path dir, IOException e)
                            throws IOException {
                        if (e != null) throw e;
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void setFileCreationHour(long evenHour, String fileName) throws IOException {
        FileTime fileTime = FileTime.fromMillis(evenHour);
        BasicFileAttributeView attributes = Files.getFileAttributeView(Paths.get(fileName), BasicFileAttributeView.class);
        attributes.setTimes(fileTime, fileTime, fileTime);
    }
}