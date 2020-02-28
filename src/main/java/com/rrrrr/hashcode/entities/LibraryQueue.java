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
public abstract class LibraryQueue implements CanCopyMe{
    public int remainingDays;
    public LinkedList<Library> LibrariesInTheQueueu;
    public Catalogue catalogue;
    
    public abstract double getMaxPossibleScore();
    public abstract LibraryQueue getInstance();
    public LibraryQueue copyMe(){
        LibraryQueue newBorn = this.getInstance();
        newBorn.remainingDays = this.remainingDays;
        newBorn.LibrariesInTheQueueu = new LinkedList(this.LibrariesInTheQueueu);
        newBorn.catalogue = (Catalogue) this.catalogue.copyMe();
        throw new UnsupportedOperationException("Yet to be implemented");
    
    }
}
