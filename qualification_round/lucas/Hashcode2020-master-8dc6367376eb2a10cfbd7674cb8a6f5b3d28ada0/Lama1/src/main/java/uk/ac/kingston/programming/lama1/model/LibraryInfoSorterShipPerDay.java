package uk.ac.kingston.programming.lama1.model;

/**
 *
 * @author lucas
 */
public class LibraryInfoSorterShipPerDay implements LibraryInfoSorter{

    @Override
    public int sort(LibraryInfo l1, LibraryInfo l2) {       
        if (l2.ShipPerDay.compareTo(l1.ShipPerDay) == 0) {
            if(l1.SignUpProcess.compareTo(l2.SignUpProcess) == 0)
            {
                return l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay);    
            }
            else
            {
                return l1.SignUpProcess.compareTo((l2.SignUpProcess));
            }
        } else {
            return l2.ShipPerDay.compareTo(l1.ShipPerDay);
        }
    }
}
