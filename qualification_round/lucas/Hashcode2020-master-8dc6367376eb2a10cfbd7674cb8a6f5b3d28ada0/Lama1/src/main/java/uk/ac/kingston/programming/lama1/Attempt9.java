/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1;

import uk.ac.kingston.programming.hashcode.MonkeyMagic;
import uk.ac.kingston.programming.hashcode.model.Challenge;
import uk.ac.kingston.programming.hashcode.model.Solution;
import uk.ac.kingston.programming.lama1.solutions.Solution2A;
import uk.ac.kingston.programming.lama1.solutions.Solution2B;
import uk.ac.kingston.programming.lama1.solutions.Solution2C;
import uk.ac.kingston.programming.lama1.solutions.Solution2D;
import uk.ac.kingston.programming.lama1.solutions.Solution2E;
import uk.ac.kingston.programming.lama1.solutions.Solution2F;

/**
 *
 * @author Lama1
 */
public class Attempt9 {
    
    public static void main(String[] args)
    {
        var challenges = new Challenge[] {
            new Challenge("a_example.txt", new Solution[] {new Solution2A()}),
            new Challenge("b_read_on.txt", new Solution[] {new Solution2B()}),
            new Challenge("c_incunabula.txt", new Solution[] {new Solution2C()}),
            new Challenge("d_tough_choices.txt", new Solution[] {new Solution2D()}),
            new Challenge("e_so_many_books.txt", new Solution[] {new Solution2E()}),
            new Challenge("f_libraries_of_the_world.txt", new Solution[] {new Solution2F()})
        };
        
        MonkeyMagic.solve(challenges);        
    }
}
