package uk.ac.kingston.programming.hashcode.model;

import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;

/**
 *
 * @author lucas
 */
public abstract class SolutionBase implements Solution{

    private String name = "";
    
    public SolutionBase(String name)
    {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract DataFileOut solve(DataFileIn dataFileIn);
    
    public DataFileOut CreateDataFileOut(DataFileIn dataFileIn)
    {
        return new DataFileOut(dataFileIn.filename, getName());        
    }
}
