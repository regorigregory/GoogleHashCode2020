
import com.rrrrr.hashcode.entities.Challange;
import com.rrrrr.hashcode.entities.DataIO;
import com.rrrrr.hashcode.entities.Library;
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

    public static void main(String[] args) {
        String fileRoot = "C:\\gdrive\\java_projects\\HashCode\\src\\main\\java\\data\\";
        String outPath = "C:\\gdrive\\java_projects\\HashCode\\src\\main\\java\\output";

        //String[] fileNames = {"b.txt", "c_incunabula.txt", "d.txt", "e.txt", "f.txt"};
        String[] fileNames = {"b_read_on.txt" ,"c_incunabula.txt", "d_tough_choices.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt"};
        //String[] fileNames = {"b_read_on.txt"};

        for (String file : fileNames) {
            Library.globalID = 0;
            String filePath = fileRoot + file;
            String outputFilename = "v8_rev_comparators_" + file;

            SimpleTimer t = new SimpleTimer();

            Challange currentChallange = DataIO.getChallange(filePath);

            Solver selectedSolver = new DynamicProgrammingSolver();
            t.timeIt();
            Proposal solversSolution = selectedSolver.solve(currentChallange);
            t.timeIt();
            System.out.println("|----------------------- File " + outputFilename + " has been written, ready for submission---------------------|");

            DataIO.saveProposalToFile(solversSolution, outPath, outputFilename);

        }

    }

}
