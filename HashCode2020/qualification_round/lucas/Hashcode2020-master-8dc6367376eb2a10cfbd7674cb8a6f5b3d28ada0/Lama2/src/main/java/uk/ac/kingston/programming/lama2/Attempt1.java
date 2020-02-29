/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama2;

import uk.ac.kingston.programming.hashcode.MonkeyMagic;
import uk.ac.kingston.programming.hashcode.model.Challenge;
import uk.ac.kingston.programming.hashcode.model.Solution;
import uk.ac.kingston.programming.lama2.solutions.Solution1;

/**
 *
 * @author Lama1
 */
public class Attempt1 {
    public static void main(String[] args)
    {
        var challenges = new Challenge[] {
            new Challenge("a_example.in", new Solution[] {new Solution1()}),
            new Challenge("b_small.in", new Solution[] {new Solution1()}),
            new Challenge("c_medium.in", new Solution[] {new Solution1()}),
            new Challenge("d_quite_big.in", new Solution[] {new Solution1()}),
            new Challenge("e_also_big.in", new Solution[] {new Solution1()})
        };
        
        MonkeyMagic.solve(challenges);        
    } 
}
