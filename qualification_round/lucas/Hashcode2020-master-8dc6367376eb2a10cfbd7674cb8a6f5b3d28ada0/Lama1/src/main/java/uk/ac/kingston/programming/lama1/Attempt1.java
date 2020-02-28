/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1;

import uk.ac.kingston.programming.hashcode.MonkeyMagic;
import uk.ac.kingston.programming.hashcode.model.Challenge;
import uk.ac.kingston.programming.hashcode.model.Solution;
import uk.ac.kingston.programming.lama1.solutions.iteration1.Solution1;

/**
 *
 * @author Lama1
 */
public class Attempt1 {
    
    public static void main(String[] args)
    {
        var challenges = new Challenge[] {
            new Challenge("a_example.txt", new Solution[] {new Solution1()}),
            new Challenge("b_read_on.txt", new Solution[] {new Solution1()}),
            new Challenge("c_incunabula.txt", new Solution[] {new Solution1()}),
            new Challenge("d_tough_choices.txt", new Solution[] {new Solution1()}),
            new Challenge("e_so_many_books.txt", new Solution[] {new Solution1()}),
            new Challenge("f_libraries_of_the_world.txt", new Solution[] {new Solution1()})
        };
        
        MonkeyMagic.solve(challenges);        
    }
}
