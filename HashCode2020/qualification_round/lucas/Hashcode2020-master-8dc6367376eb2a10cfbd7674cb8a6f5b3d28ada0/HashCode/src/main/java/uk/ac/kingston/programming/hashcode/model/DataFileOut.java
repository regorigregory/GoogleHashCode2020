package uk.ac.kingston.programming.hashcode.model;

import java.time.Instant;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class DataFileOut {
    public Integer score = 0;
    public String dataFileInFilename = "";
    public String solutionName = "";

    public ArrayList<LibraryPack> LibraryPacks = new ArrayList<>();
    
    public DataFileOut(String dataFileInFilename, String solutionName)
    {
        this.dataFileInFilename = dataFileInFilename;
        this.solutionName = solutionName;
    }
    
    public String getFilename()
    {
        return dataFileInFilename.replace(".in", "") + "_" + score + "_" + solutionName + "_" + Instant.now().getEpochSecond() + ".out";
    }
}
