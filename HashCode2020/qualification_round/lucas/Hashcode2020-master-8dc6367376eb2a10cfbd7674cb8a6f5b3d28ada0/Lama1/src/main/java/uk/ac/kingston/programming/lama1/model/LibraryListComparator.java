/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.model;

import java.util.Comparator;

/**
 *
 * @author Lama1
 */
public class LibraryListComparator implements Comparator<LibraryInfo> {
    @Override
    public int compare(LibraryInfo first, LibraryInfo second) {
       return second.MaxScore - first.MaxScore;
    }
}