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
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterShipPerDay2;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorterSignUpProcess;
import uk.ac.kingston.programming.lama1.solutions.Solution2D;
import uk.ac.kingston.programming.lama1.solutions.Solution2E2;
import uk.ac.kingston.programming.lama1.solutions.SolutionLibrarySorter;

/**
 *
 * @author Lama1
 */
public class Attempt11 {
    
    public static void main(String[] args)
    {
        var challenges = new ArrayList<Challenge>();

        challenges.add(new Challenge("b_read_on.txt", 
            new Solution[] {
                new SolutionLibrarySorter("1-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay2(), 1, 0),
            }));

        challenges.add(new Challenge("c_incunabula.txt", 
            new Solution[] {
                new SolutionLibrarySorter("301"+"-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay2(), 301, 0),
            }));

        challenges.add(new Challenge("d_tough_choices.txt", 
            new Solution[] {
                new Solution2D()
            }));
        
        challenges.add(new Challenge("e_so_many_books.txt", 
            new Solution[] {
                new SolutionLibrarySorter("16-LibraryInfoSorterShipPerDay1", new LibraryInfoSorterShipPerDay2(), 16 , 0),
                new Solution2E2("16-LibraryInfoSorterShipPerDay2", new LibraryInfoSorterShipPerDay2(), 16 , 0)
            }));
       
        challenges.add(new Challenge("f_libraries_of_the_world.txt", 
            new Solution[] {
                new SolutionLibrarySorter("930-MaxScorePerDay2", new LibraryInfoSorterMaxScorePerDay2(), 930, 0),
            }));
        
        MonkeyMagic.solve(challenges);        
    }
}
