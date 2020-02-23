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
public class Proposal {

    private LinkedList<Library> librariesToRegisterInOrder;
    private LinkedList<LinkedList<Book>> booksPerLibraryToSend;

    public Proposal() {
        this.librariesToRegisterInOrder = new LinkedList<>();
        this.booksPerLibraryToSend = new LinkedList<>();
    }

    public Proposal(LinkedList<Library> librariesToRegisterInOrder, LinkedList<LinkedList<Book>> booksPerLibraryToSend) {
        this.librariesToRegisterInOrder = librariesToRegisterInOrder;
        this.booksPerLibraryToSend = booksPerLibraryToSend;
    }

    public LinkedList<Library> getLibrariesToRegisterInOrder() {
        return librariesToRegisterInOrder;
    }

    public void setLibrariesToRegisterInOrder(LinkedList<Library> librariesToRegisterInOrder) {
        this.librariesToRegisterInOrder = librariesToRegisterInOrder;
    }

    public LinkedList<LinkedList<Book>> getBooksPerLibraryToSend() {
        return booksPerLibraryToSend;
    }

    public void setBooksPerLibraryToSend(LinkedList<LinkedList<Book>> booksPerLibraryToSend) {
        this.booksPerLibraryToSend = booksPerLibraryToSend;
    }

}
