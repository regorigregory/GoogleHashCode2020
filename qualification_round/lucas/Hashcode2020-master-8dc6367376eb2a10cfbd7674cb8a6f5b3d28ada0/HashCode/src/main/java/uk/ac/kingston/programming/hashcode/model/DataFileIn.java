package uk.ac.kingston.programming.hashcode.model;

import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class DataFileIn {
    public String filename = "";
    public int NoBooks = 0;
    public int NoLibraries = 0;
    public int NoDaysForScanning = 0;
    public ArrayList<Integer> BookScores = new ArrayList<Integer>();
    public ArrayList<Library> Libraries = new ArrayList<Library>();
    
}
