/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Madero Padero
 */
public class DataIO {

    public static long globalScore = 0;
    public static HashMap scoreLog = new HashMap();

    public static List<String> readChallangeFile(String challangeFilePathString) {
        Path challangeFilePath = Paths.get(challangeFilePathString);
        List<String> lines = new LinkedList<>();

        try {
            String line = "";
            lines = Files.readAllLines(challangeFilePath, StandardCharsets.US_ASCII);

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return lines;
    }

    public static Challange getChallange(String challangeFilePathString) {
        List<String> lines = readChallangeFile(challangeFilePathString);
        String[] firstLine = lines.get(0).split(" ");
        String[] bookScores = lines.get(1).split(" ");

        int numberOfBooks = Integer.parseInt(firstLine[0]);
        int numberOfLibraries = Integer.parseInt(firstLine[1]);

        byte[][] binaryCatalogue = new byte[numberOfLibraries][numberOfBooks];
        int[] instancesPerBooks = new int[numberOfBooks];
        int daysForScanning = Integer.parseInt(firstLine[2]);

        Catalogue cat = new Catalogue(bookScores);

        LinkedList<Library> libraries = new LinkedList<>();

        //Constructing libraries...
        for (int i = 2; i < lines.size(); i += 2) {
            String[] libraryDetails = lines.get(i).split(" ");
            if (libraryDetails.length == 1) {
                boolean ehraw = true;
                break;
            }
            int daysToSignUp = Integer.parseInt(libraryDetails[1]);
            int booksPerDay = Integer.parseInt(libraryDetails[2]);

            Library newLibrary = new Library(booksPerDay, daysToSignUp);
            //next line of the input is i+1             
            int booksInTheLibraryIndex = i + 1;
            String[] booksInTheLibrary = lines.get(booksInTheLibraryIndex).split(" ");

            for (String stringValue : booksInTheLibrary) {
                int nextBook = Integer.parseInt(stringValue);
                Book bookToAdd = cat.getEveryBook().get(nextBook);
                bookToAdd.incrementNumberOfInstances();
                newLibrary.getBooks().add(bookToAdd);
                binaryCatalogue[newLibrary.getId()][bookToAdd.getId()] = 1;
                instancesPerBooks[bookToAdd.getId()] += 1;
            }
            libraries.add(newLibrary);
        }
        //System.out.println("The challange file has been loaded.");
        return new Challange(cat, libraries, daysForScanning, binaryCatalogue, instancesPerBooks);
    }

    public static void saveProposalToFile(Proposal p, String savePath, String fileName) {
        //Output file structure

        try {

            LinkedList<Library> libs = p.getLibrariesToRegisterInOrder();
            LinkedList<LinkedList<Book>> booksPerLibrary = p.getBooksPerLibraryToSend();

            String numLibrariesToSignUp = Integer.toString(libs.size() - 1);

            StringBuilder output = new StringBuilder();
            output.append(numLibrariesToSignUp);

            long score = 0;
            List<Integer> scores = new LinkedList<>();

            for (int i = 0; i < libs.size() - 1; i++) {

                Library currentLibrary = libs.get(i);
                LinkedList<Book> tempBooksToSend = booksPerLibrary.get(i);
                output.append("\n" + currentLibrary.getId() + " " + tempBooksToSend.size());

                StringBuilder booksToSend = new StringBuilder();
                tempBooksToSend.stream().forEach(
                        b -> {

                            scores.add(b.getValue());
                            booksToSend.append(b.toString() + " ");
                        }
                );

                output.append("\n" + booksToSend.toString().trim());
            }
            Integer sum = scores.stream().reduce(Integer::sum).get();

            System.out.println(fileName+" : " + sum);
            scoreLog.put(fileName, sum);

            Path rootDir = Paths.get(savePath);
            if (!Files.isDirectory(rootDir)) {
                Files.createDirectories(rootDir);
            }

            Path fullPath = Paths.get(rootDir.toString(), sum.toString()+fileName);

            if (!Files.isRegularFile(fullPath)) {
                Files.createFile(fullPath);
            }
            Files.writeString(fullPath, output);

            //globalScore+=sum;
            //System.out.println("Filepath:"+fullPath.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
