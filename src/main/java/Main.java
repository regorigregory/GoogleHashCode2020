
import com.rrrrr.hashcode.entities.Challange;
import com.rrrrr.hashcode.entities.DataIO;
import com.rrrrr.hashcode.entities.Proposal;
import com.rrrrr.hashcode.entities.SimpleTimer;
import solvers.DynamicProgrammingSolver;
import solvers.Solver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Madero Padero
 */
public class Main {
    public static void main(String[] args){
        String filePath = "C:\\gdrive\\java_projects\\HashCode\\src\\main\\java\\data\\d.txt";
        String outPath = "C:\\gdrive\\java_projects\\HashCode\\src\\main\\java\\output";
        String outputFilename = "dynamic_d.txt";
        
        
        SimpleTimer t = new SimpleTimer();
        
        System.out.println("Hello world!");
        
        Challange currentChallange = DataIO.getChallange(filePath);
        
        Solver selectedSolver = new DynamicProgrammingSolver();
        t.timeIt();
        Proposal solversSolution = selectedSolver.solve(currentChallange);
        t.timeIt();
        
        DataIO.saveProposalToFile(solversSolution, outPath, outputFilename);
        
    
    }
}
