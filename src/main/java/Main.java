
import com.rrrrr.hashcode.entities.Book;
import com.rrrrr.hashcode.entities.Challange;
import com.rrrrr.hashcode.entities.DataIO;
import com.rrrrr.hashcode.entities.Library;
import com.rrrrr.hashcode.entities.Proposal;
import com.rrrrr.hashcode.entities.SimpleTimer;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import solvers.GreedySolver;
import solvers.KnapsackSolver;
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
        //String[] fileNames = {"b_read_on.txt" ,"c_incunabula.txt", "d_tough_choices.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt"};
        //String[] fileNames = {"b_read_on.txt"};
        String[] fileNames = {"f_libraries_of_the_world.txt"};
        //do anding....
        double[] bookMagics = {0.7, 0.8, 0.9, 1.1, 1.2};
        double[] libraryMagics ={0.875, 0.9, 0.925, 0.95};
        for (String file : fileNames) {

            for (Double bm : bookMagics) {
                for (Double lm : libraryMagics) {

                    Library.globalID = 0;
                    String filePath = fileRoot + file;
                    String outputFilename = "-" + lm.toString() + "-lm_" + bm.toString() + "-bm_" + file;
                    Book.magic = bm;
                    Library.magic = lm;
                    //current best :  5,034,656.00

//
//                SimpleTimer t = new SimpleTimer();
//
                    Challange currentChallange = DataIO.getChallange(filePath);
                    //int[] numberOfBooks = currentChallange.getNumberOfBooks();
                    //int min = Arrays.stream(numberOfBooks).min().getAsInt();
                    //System.out.println(min);

//
                    Solver selectedSolver = new GreedySolver();
//                //t.timeIt();
                    Proposal solversSolution = selectedSolver.solve(currentChallange);
//                //t.timeIt();
//                //.out.println("|----------------------- File " + outputFilename + " has been written, ready for submission---------------------|");
//
                    DataIO.saveProposalToFile(solversSolution, outPath, outputFilename);
                }

            }

        }

    }
}
