package uk.ac.kingston.programming.lama1.model;

/**
 *
 * @author lucas
 */
public class LibraryInfoSorterMaxScorePerDay implements LibraryInfoSorter{

    @Override
    public int sort(LibraryInfo l1, LibraryInfo l2) {
        if (l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay) == 0) {
            return l1.NoOfDays.compareTo(l2.NoOfDays);
        } else {
            return l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay);
        }
    }
}
