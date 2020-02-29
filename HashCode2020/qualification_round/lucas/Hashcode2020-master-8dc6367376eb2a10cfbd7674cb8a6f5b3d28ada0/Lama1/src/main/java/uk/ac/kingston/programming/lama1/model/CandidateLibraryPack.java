package uk.ac.kingston.programming.lama1.model;

import java.util.ArrayList;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;

/**
 *
 * @author lucas
 */
public class CandidateLibraryPack {
    public LibraryInfo Library = new LibraryInfo();
    public LibraryPack LibraryPack = new LibraryPack();
    public ArrayList<Book> BooksShipped = new ArrayList<Book>();
}
