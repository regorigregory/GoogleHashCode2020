/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Madero Padero
 */
public class Library implements Comparable{
    
    
    public static long globalDaysRemaining;
    public static int globalID = 0;
    
       
    private int id;
    private int registrationDays;
    private int booksPerDay;

    private LinkedList<Book> books;
    private LinkedList<Book> booksToSend;

    private double currentlyPossibleMaxValue = 0;
    
    
    
    
    public Library(int booksPerDay, int registrationDays) {
        this.id = globalID;
        globalID++;
        this.booksPerDay = booksPerDay;
        this.books = new LinkedList<Book>();
        this.registrationDays = registrationDays;
    }
    public Library(int booksPerDay, int registrationDays, LinkedList<Book> books) {
        this.id = globalID;
        globalID++;
        this.booksPerDay = booksPerDay;
        this.books = books;
        this.registrationDays = registrationDays;
    }
    
    

    private double calculateMaxxPossibleGain() {
        
        LinkedList<Book> booksRemaining = this.getBooksRemaining();
        this.currentlyPossibleMaxValue = 0;
        this.booksToSend = new LinkedList<>();
        
        long realDaysRemaining = globalDaysRemaining - this.registrationDays;
        long maximumAmountOfBooks = realDaysRemaining * this.booksPerDay;

        if (maximumAmountOfBooks > booksRemaining.size()) {
            booksToSend = booksRemaining;
            this.currentlyPossibleMaxValue =getBooksValue(booksRemaining);
            return this.currentlyPossibleMaxValue;
        }

        long maxPossibleValue;
        int sentBookCounter = 0;

        while (realDaysRemaining > 0) {
            if (sentBookCounter == maximumAmountOfBooks) {
                return this.currentlyPossibleMaxValue;
            }
            
            for (int i = 0; i < this.booksPerDay; i++) {

                Book temp = booksRemaining.get(sentBookCounter);
                this.currentlyPossibleMaxValue += temp.getValue();
                booksToSend.add(temp);
                sentBookCounter++;

            }
            realDaysRemaining--;
        }
        return this.currentlyPossibleMaxValue;
    }
    public LinkedList<Book> getBooksRemaining(){
        LinkedList<Book> books = this.books.stream().filter(b->b.isSent() == false).collect(Collectors.toCollection(LinkedList::new));
        Collections.sort(books);
        return books;
    }
    
    public void sendBooks(){
        this.booksToSend.stream().forEach(b-> b.send());
    }
    
    
    public static double getBooksValue(List<Book> availableBooks) {
        double value = 0;
        for (Book b : availableBooks) {
            value += b.getValue()/b.getNumberOfInstances();

        }
        return value;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Library)){
            throw new IllegalArgumentException("Trying to compare non-library object to library object.");
        }
        
        Library otherBook = (Library) o;
        double thisValue = this.calculateMaxxPossibleGain();
        double otherValue = otherBook.calculateMaxxPossibleGain();
        
        return thisValue>otherValue ? 1 : thisValue==otherValue ? 0 : -1;
    }
    

    
    @Override
    public int hashCode() {
        long hash = 7;
        hash = 61 * hash + this.id;
        hash = 61 * hash + this.registrationDays;
        
        return (int) hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Library other = (Library) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistrationDays() {
        return registrationDays;
    }

    public void setRegistrationDays(int registrationDays) {
        this.registrationDays = registrationDays;
    }

    public int getBooksPerDay() {
        return booksPerDay;
    }

    public void setBooksPerDay(int booksPerDay) {
        this.booksPerDay = booksPerDay;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    public void setBooks(LinkedList<Book> books) {
        this.books = books;
    }


    public LinkedList<Book> getBooksToSend() {
        return booksToSend;
    }

    public void setBooksToSend(LinkedList<Book> booksToSend) {
        this.booksToSend = booksToSend;
    }

    public double getCurrentlyPossibleMaxValue() {
        return currentlyPossibleMaxValue;
    }

    public void setCurrentlyPossibleMaxValue(int currentlyPossibleMaxValue) {
        this.currentlyPossibleMaxValue = currentlyPossibleMaxValue;
    }
    
    
    
    
    

}
