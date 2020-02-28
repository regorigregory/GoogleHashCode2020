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
public class Catalogue implements CanCopyMe{
    private LinkedList<Book> everyBook;
    
    public Catalogue(String[] bookValues) {
        LinkedList<Book> everyBook = new LinkedList<>();
        
        for(int i =0; i<bookValues.length; i++){
            int bookValue = Integer.parseInt(bookValues[i]);
            Book temp = new Book(i, bookValue);
            everyBook.add(temp);
        }
        
        
        this.everyBook = everyBook;
    }
    
    public CanCopyMe copyMe(){
        throw new UnsupportedOperationException("Yet to be implemented");
    }
    public Catalogue(LinkedList<Book> everyBook) {
        this.everyBook = everyBook;
    }
    
    public LinkedList<Book> getEveryBook() {
        return everyBook;
    }

    public void setEveryBook(LinkedList<Book> everyBook) {
        this.everyBook = everyBook;
    }
    
    
}
