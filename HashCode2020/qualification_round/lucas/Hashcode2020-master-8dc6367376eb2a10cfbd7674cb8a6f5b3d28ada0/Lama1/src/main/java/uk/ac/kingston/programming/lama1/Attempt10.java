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
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterSignUpProcess;
import uk.ac.kingston.programming.lama1.solutions.Solution2D;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter;

/**
 *
 * @author Lama1
 */
public class Attempt10 {
    
    public static void main(String[] args)
    {
        var challenges = new ArrayList<Challenge>();

//        for(int noOfCandidates = 0; noOfCandidates <= 10; noOfCandidates++)
//        {
//            challenges.add(new Challenge("b_read_on.txt", 
//                new Solution[] {
//                    new SolutionLibrarySorter(String.valueOf(noOfCandidates+"-MaxScorePerDay"), new LibraryInfoSorterMaxScorePerDay(), 0, 5822900),
//                }));
//        }

//        for(int noOfCandidates = 1; noOfCandidates <= 1000; noOfCandidates+=100)
//        {
//            challenges.add(new Challenge("c_incunabula.txt", 
//                new Solution[] {
//                    new SolutionLibrarySorter(String.valueOf("301"+"-MaxScorePerDay"), new LibraryInfoSorterMaxScorePerDay(), 301, 5680183),
//                }));
//        }
//
//        for(int noOfCandidates = 1; noOfCandidates <= 1; noOfCandidates++)
//        {
//            challenges.add(new Challenge("d_tough_choices.txt", 
//                new Solution[] {
//                    new Solution2D()
//                }));
//        }
        
//        for(int noOfCandidates = 930; noOfCandidates <= 2000; noOfCandidates+=100)
//        {
//            challenges.add(new Challenge("f_libraries_of_the_world.txt", 
//                new Solution[] {
//                    new SolutionLibrarySorter(String.valueOf(noOfCandidates), new LibraryInfoSorterMaxScorePerDay(), 930, 4665378),
//                }));
//        }
        
//        for(int noOfCandidates = 1; noOfCandidates <= 1; noOfCandidates++)
//        {
//            challenges.add(new Challenge("e_so_many_books.txt", 
//                new Solution[] {
//                    new SolutionLibrarySorter("16-ShipPerDay", new LibraryInfoSorterShipPerDay(), 16 , 4161858),
//                }));
//        }
        
        MonkeyMagic.solve(challenges);        
    }
}
