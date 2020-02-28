package uk.ac.kingston.programming.lama1.model;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class LibraryInfoComparatorWeighting implements Comparator<LibraryInfo> {
    @Override
    public int compare(LibraryInfo first, LibraryInfo second) {
       return second.Weighting - first.Weighting;
    }
}