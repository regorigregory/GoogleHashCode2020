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
public class Library implements Comparable, CanCopyMe{
        //implements Comparable{
    
    public static double magic = 1;
    public static long globalDaysRemaining;
    public static int globalID = 0;
    public long localDaysRemining=0;
       
    private int id;
    private int signupTimeNeeded;
    private int booksPerDay;

    private LinkedList<Book> books;
    private LinkedList<Book> booksToSend;

    private double currentlyPossibleMaxValue = 0;
    private boolean hasRegistered;
    
    
    
    public Library(Library otherLibrary){
        this.id = otherLibrary.id;
        this.localDaysRemining = otherLibrary.localDaysRemining;
        this.booksPerDay = otherLibrary.booksPerDay;
        this.books = new LinkedList(otherLibrary.books);
        this.signupTimeNeeded = otherLibrary.signupTimeNeeded;

    }
    public Library(int booksPerDay, int registrationDays) {
        this.id = globalID;
        globalID++;
        this.booksPerDay = booksPerDay;
        this.books = new LinkedList<Book>();
        this.signupTimeNeeded = registrationDays;
        this.localDaysRemining = globalDaysRemaining;
    }
    public Library(int booksPerDay, int registrationDays, LinkedList<Book> books) {
        this.id = globalID;
        globalID++;
        this.booksPerDay = booksPerDay;
        this.books = books;
        this.signupTimeNeeded = registrationDays;
    }
    
    

    private double calculateMaxxPossibleGain() {
        
        LinkedList<Book> booksRemaining = this.getBooksRemaining();
        this.currentlyPossibleMaxValue = 0;
        this.booksToSend = new LinkedList<>();
        
        long realDaysRemaining = globalDaysRemaining - this.signupTimeNeeded;
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
            value += b.getValue();///b.getNumberOfInstances();

        }
        return value;
    }

 

    
    @Override
    public int hashCode() {
        long hash = 7;
        hash = 61 * hash + this.id;
        hash = 61 * hash + this.signupTimeNeeded;
        
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

    public int getSignupTimeNeeded() {
        return signupTimeNeeded;
    }

    public void setSignupTimeNeeded(int signupTimeNeeded) {
        this.signupTimeNeeded = signupTimeNeeded;
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

    public static long getGlobalDaysRemaining() {
        return globalDaysRemaining;
    }

    public static void setGlobalDaysRemaining(long globalDaysRemaining) {
        Library.globalDaysRemaining = globalDaysRemaining;
    }

    public static int getGlobalID() {
        return globalID;
    }

    public static void setGlobalID(int globalID) {
        Library.globalID = globalID;
    }

    public boolean isHasRegistered() {
        return hasRegistered;
    }

    public void setHasRegistered(boolean hasRegistered) {
        this.hasRegistered = hasRegistered;
    }
    
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Library)){
            throw new IllegalArgumentException("Trying to compare non-library object to library object.");
        }
        
        Library otherBook = (Library) o;
        double thisValue = this.calculateMaxxPossibleGain()/Math.pow(this.getSignupTimeNeeded(), magic);//(this.getSignupTimeNeeded()*this.getSignupTimeNeeded());
        double otherValue = otherBook.calculateMaxxPossibleGain()/Math.pow(otherBook.getSignupTimeNeeded(), magic);//(otherBook.getSignupTimeNeeded()*otherBook.getSignupTimeNeeded());
        
        return thisValue>otherValue ? -1 : thisValue==otherValue ? 0 : 1;
    }

    public static double getMagic() {
        return magic;
    }

    public static void setMagic(double magic) {
        Library.magic = magic;
    }

    public long getLocalDaysRemining() {
        return localDaysRemining;
    }

    public void setLocalDaysRemining(long localDaysRemining) {
        this.localDaysRemining = localDaysRemining;
    }
    
    
    
    
      public CanCopyMe copyMe(){
          return new Library(this);
      }
    
    
    
    
    

}
