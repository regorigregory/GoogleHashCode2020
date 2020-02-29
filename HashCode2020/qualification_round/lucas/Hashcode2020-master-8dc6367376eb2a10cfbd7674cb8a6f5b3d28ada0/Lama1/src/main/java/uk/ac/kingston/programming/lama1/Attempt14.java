/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1;

import java.util.ArrayList;
import uk.ac.kingston.programming.hashcode.MonkeyMagic;
import uk.ac.kingston.programming.hashcode.model.Challenge;
import uk.ac.kingston.programming.hashcode.model.Solution;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterMaxScore;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterMaxScorePerDay;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterMaxScorePerDay2;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterMaxScorePerDay3;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterMaxScorePerDay4;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay2;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay3;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterSignUpProcess;
import uk.ac.kingston.programming.lama1.solutions.Solution2D;
import uk.ac.kingston.programming.lama1.solutions.Solution2E2;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter2;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter3;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter4;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter5;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter6;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter7;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter8;

/**
 *
 * @author Lama1
 */
public class Attempt14 {
    
    public static void main(String[] args)
    {
        var challenges = new ArrayList<Challenge>();

//        challenges.add(new Challenge("b_read_on.txt", 
//            new Solution[] {
//                new SolutionLibrarySorter7("1-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay3(), 0, 1, 0, 1, 1),
//            }));
//        
//        challenges.add(new Challenge("c_incunabula.txt", 
//            new Solution[] {
//                new SolutionLibrarySorter("1x-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay3(), 1, 0)
//            }));
            
//        challenges.add(new Challenge("c_incunabula.txt", 
//            new Solution[] {
//                new SolutionLibrarySorter5(String.valueOf("301-MaxScorePerDay"), new LibraryInfoSorterMaxScorePerDay3(), 3, 301, 0)
//            }));
        
//        challenges.add(new Challenge("f_libraries_of_the_world.txt", 
//            new Solution[] { // 53 / 100
//                new SolutionLibrarySorter7(101 + "-51-100-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay3(), 2, 101, 0, 53, 100),
//            }));             
        

//        challenges.add(new Challenge("e_so_many_books.txt", 
//            new Solution[] {
//                new SolutionLibrarySorter3("19-LibraryInfoSorterShipPerDay2", new LibraryInfoSorterShipPerDay3(), 1, 21 , 4178348),
//            }));

        challenges.add(new Challenge("d_tough_choices.txt", 
            new Solution[] {
                //new Solution2D()
                new SolutionLibrarySorter7("19-LibraryInfoSorterShipPerDay2", new LibraryInfoSorterMaxScorePerDay(), 1, 3 , 0, 3, 3)
            }));
        
        MonkeyMagic.solve(challenges);        
    }
}
