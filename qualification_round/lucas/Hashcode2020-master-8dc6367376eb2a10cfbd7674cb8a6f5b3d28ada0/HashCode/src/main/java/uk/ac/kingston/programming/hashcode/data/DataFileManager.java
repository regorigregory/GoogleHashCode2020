package uk.ac.kingston.programming.hashcode.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.DataFileTransformer;

/**
 *
 * @author lucas
 */
public final class DataFileManager {
    
    public static final String PATH_IN = "C:\\Code\\Gitlab\\Hashcode2020\\data\\in\\";
    public static final String PATH_OUT = "C:\\Code\\Gitlab\\Hashcode2020\\data\\out\\";
    
    public static DataFileIn open(String filename, DataFileTransformer dataFileTransformer)
    {
        var dataFileIn = new DataFileIn();
        dataFileIn.filename = filename;
        
        var fullPath = PATH_IN + filename;
        
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fullPath))) 
        {
            dataFileTransformer.onLoad(bufferReader, dataFileIn);
        } 
        catch(FileNotFoundException fnfe) 
        {
            System.out.println(fnfe.getMessage());
            return null;
        } 
        catch(IOException ioe) 
        {
            System.out.println(ioe.getMessage());
            return null;
        }
        return dataFileIn;
    }
    
    public static void save(DataFileOut dataFileOut, DataFileTransformer dataFileTransformer)
    {
        var fullPath = PATH_OUT + dataFileOut.getFilename();
        try (PrintWriter outputFile = new PrintWriter(fullPath)) {
            dataFileTransformer.onSave(outputFile, dataFileOut);
        }  
        catch(FileNotFoundException fnfe)
        {
            System.out.println(fnfe.getMessage());
        }        
    }
}
