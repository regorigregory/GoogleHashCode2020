/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

/**
 *
 * @author Madero Padero
 */
public class Book implements Comparable{
    private int id;
    private int value;
    private int numberOfInstances = 0;
    private boolean sent = false;

    public Book(int id, int value) {
        this.id = id;
        this.value = value;
    }
    @Override
    public int compareTo(Object o){
        if (!(o instanceof Book)){
            throw new IllegalArgumentException("Trying to compare non-book object to book object.");
        }
        
        Book otherBook = (Book) o;
        double thisValue = this.getValue();//(this.numberOfInstances);
        //thisValue = thisValue*thisValue;
        double otherValue = otherBook.getValue();//(otherBook.numberOfInstances);
        //otherValue = otherValue*otherValue;
        return thisValue>otherValue ? -1 : thisValue==otherValue ? 0 : 1;
        
        
    }
    @Override
    public boolean equals(Object o){
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
        return this.getId()==otherBook.getId();
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
    public boolean isSent(){
        return this.sent;
    }
    public void send(){
        this.sent = true;
    }
    @Override
    public String toString(){
        return Integer.toString(this.getId());
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }
    
    public void incrementNumberOfInstances(){
        this.numberOfInstances+=1;
    }
}
