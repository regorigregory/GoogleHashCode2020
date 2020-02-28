package uk.ac.kingston.programming.hashcode.model;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class DataFileOutComparator implements Comparator<DataFileOut> {
    @Override
    public int compare(DataFileOut first, DataFileOut second) {
       return second.score - first.score;
    }
}