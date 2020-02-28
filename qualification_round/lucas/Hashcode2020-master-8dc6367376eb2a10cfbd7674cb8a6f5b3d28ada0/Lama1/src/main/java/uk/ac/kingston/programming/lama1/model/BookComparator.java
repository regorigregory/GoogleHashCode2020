package uk.ac.kingston.programming.lama1.model;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class BookComparator implements Comparator<Book> {
    @Override
    public int compare(Book first, Book second) {
       return second.Score - first.Score;
    }
}