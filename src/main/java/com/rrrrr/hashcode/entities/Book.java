/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

import java.util.Comparator;
import java.util.function.Function;

/**
 *
 * @author Madero Padero
 */
public class Book implements Comparable, CanCopyMe{
    
    public static CompareByInstanceCount COMPARE_BY_COUNT = new CompareByInstanceCount();
    
    
    private int id;
    private int value;
    private int numberOfInstances = 0;
    private boolean sent = false;

    public static double magic = 0.7;
    
    
    public Book(Book otherBook){
        this.id = otherBook.id;
        this.value = otherBook.value;
        this.numberOfInstances = otherBook.numberOfInstances;
        this.sent = otherBook.sent;
    }
    public Book(int id, int value) {
        this.id = id;
        this.value = value;
    }

      public CanCopyMe copyMe(){
        return new Book(this);
    }

    public static class CompareByInstanceCount implements Comparator {

        public int compare(Object o1, Object o2) {
            if (!((o1 instanceof Book) || (o2 instanceof Book))) {
                throw new IllegalArgumentException("Trying to compare non-book object to book object.");
            }

            Book b1 = (Book) o1;
            Book b2 = (Book) o2;

            double thisValue = b1.getNumberOfInstances();

            //thisValue = thisValue*thisValue;
            //double otherValue = otherBook.getValue()/Math.pow(otherBook.getNumberOfInstances(),comparatorRatio );
            double otherValue = b2.getNumberOfInstances();
            return thisValue > otherValue ? -1 : thisValue == otherValue ? 0 : 1;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Book)) {
            throw new IllegalArgumentException("Trying to compare non-book object to book object.");
        }

        Book otherBook = (Book) o;
        double thisValue = this.getValue()/(Math.pow(this.getNumberOfInstances(),magic));
        //double thisValue = this.getNumberOfInstances();

        //thisValue = thisValue*thisValue;
        double otherValue = otherBook.getValue()/Math.pow(otherBook.getNumberOfInstances(),magic );
        //double otherValue = otherBook.getNumberOfInstances();
        int valueA = this.getValue();
        int valueB = otherBook.getValue();

//        if (valueA != valueB) {
//        return valueA > valueB ? -1 : valueA == valueB ? 0 : 1;
//
//        }
        return thisValue > otherValue ? 1 : thisValue == otherValue ? 0 : -1;
//otherValue = otherValue*otherValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        Book otherBook = (Book) o;
        return this.getId() == otherBook.getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.value;
        return hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSent() {
        return this.sent;
    }

    public void send() {
        this.sent = true;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getId());
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }

    public void incrementNumberOfInstances() {
        this.numberOfInstances += 1;
    }

    static class CompareByNumberOfInstances implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Comparator thenComparing(Function keyExtractor, Comparator keyComparator) {
            return Comparator.super.thenComparing(keyExtractor, keyComparator); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
