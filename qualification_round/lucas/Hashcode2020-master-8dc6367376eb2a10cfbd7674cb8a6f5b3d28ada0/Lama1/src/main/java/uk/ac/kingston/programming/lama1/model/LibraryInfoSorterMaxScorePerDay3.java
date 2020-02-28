package uk.ac.kingston.programming.lama1.model;

/**
 *
 * @author lucas
 */
public class LibraryInfoSorterMaxScorePerDay3 implements LibraryInfoSorter{

    @Override
    public int sort(LibraryInfo l1, LibraryInfo l2) {   
        Integer x1 = l1.ShipPerDay / l1.SignUpProcess;
        Integer x2 = l2.ShipPerDay / l2.SignUpProcess;        
        if (l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay) == 0) {
            if(x2.compareTo(x1) == 0)
            {
                return l2.MaxScore.compareTo(l1.MaxScore);
            }
            else
            {
                return x2.compareTo(x1);
            }
        } else {
            return l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay);
        }        
    }
}
