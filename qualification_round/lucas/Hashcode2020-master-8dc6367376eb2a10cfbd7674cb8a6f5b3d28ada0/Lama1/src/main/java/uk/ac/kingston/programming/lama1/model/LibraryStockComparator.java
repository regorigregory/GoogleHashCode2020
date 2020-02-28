package uk.ac.kingston.programming.lama1.model;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class LibraryStockComparator implements Comparator<LibraryStock> {
    @Override
    public int compare(LibraryStock first, LibraryStock second) {
       return first.Score - second.Score;
    }
}