package org.example.watcher.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class FileCopier {

    private static Integer homeCounter = 0;
    private static Integer devCounter = 0;
    private static Integer testCounter = 0;

    private static final String HOME = "HOME";
    private static final String DEV = "DEV";
    private static final String TEST = "TEST";


    public static void copyFiles(String fileName) throws IOException {

        File incomingFile = new File( HOME + "/" + fileName);
        BasicFileAttributes attributes = Files.readAttributes(incomingFile.toPath(), BasicFileAttributes.class);
        int hourOfFileCreation = LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault()).getHour();

        File jarOddDestinationFile = new File(TEST + "/" + fileName);
        File xmlOrJarEvenDestinationFile = new File( DEV +"/" + fileName);


        System.out.println("We found a new file called "+ fileName);

        if((fileName.endsWith("jar") && isEven(hourOfFileCreation)) || fileName.endsWith("xml")) {
            Files.move(incomingFile.toPath(), xmlOrJarEvenDestinationFile.toPath());
            homeCounter++;
            devCounter++;
            System.out.println("The file was copied to " + DEV + " directory.");
        }else if(fileName.endsWith("jar") && !isEven(hourOfFileCreation)){
            Files.move(incomingFile.toPath(),jarOddDestinationFile.toPath());
            homeCounter++;
            testCounter++;
            System.out.println("The file was copied to " + TEST + " directory.");
        }
    }

    public static void writeCount() throws IOException {
        Path filePath = Paths.get("HOME/count.txt");
        Files.write(filePath, getCounterInformation());
    }

    private static boolean isEven(int hour){
        return hour % 2 == 0 ;
    }

    private static List<String> getCounterInformation(){
        List<String> counterList = new ArrayList<>();
        counterList.add("Katalog " + HOME + ", Licznik plikow: " + homeCounter.toString());
        counterList.add("Katalog " + DEV + ", Licznik plikow: " + devCounter.toString());
        counterList.add("Katalog " + TEST + ", Licznik plikow: " + testCounter.toString());

        return counterList;
    }
}
