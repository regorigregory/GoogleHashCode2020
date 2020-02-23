/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

import java.util.LinkedList;

/**
 *
 * @author Madero Padero
 */
public class Challange {
    Catalogue ct;
    LinkedList<Library> libraries;
    int daysGivenToscan;

    public Challange(Catalogue ct, LinkedList<Library> libraries, int daysGivenToscan) {
        this.ct = ct;
        this.libraries = libraries;
        this.daysGivenToscan = daysGivenToscan;
    }

    public Catalogue getCt() {
        return ct;
    }

    public void setCt(Catalogue ct) {
        this.ct = ct;
    }

    public LinkedList<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(LinkedList<Library> libraries) {
        this.libraries = libraries;
    }

    public int getDaysGivenToscan() {
        return daysGivenToscan;
    }

    public void setDaysGivenToscan(int daysGivenToscan) {
        this.daysGivenToscan = daysGivenToscan;
    }
    
    
    
    
    
    
    
}