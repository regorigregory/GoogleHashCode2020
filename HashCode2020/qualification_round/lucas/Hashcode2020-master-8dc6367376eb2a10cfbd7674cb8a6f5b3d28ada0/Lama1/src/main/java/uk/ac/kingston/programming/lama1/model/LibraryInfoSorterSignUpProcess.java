package uk.ac.kingston.programming.lama1.model;

/**
 *
 * @author lucas
 */
public class LibraryInfoSorterSignUpProcess implements LibraryInfoSorter{

    @Override
    public int sort(LibraryInfo l1, LibraryInfo l2) {
        return l2.SignUpProcess.compareTo(l1.SignUpProcess);
    }
}
