package uk.ac.kingston.programming.hashcode.model;

/**
 *
 * @author lucas
 */
public class Challenge {
    public String filenameIn = "";
    public Solution[] solutions;
    
    public Challenge(String filenameIn, Solution[] solutions)
    {
        this.filenameIn = filenameIn;
        this.solutions = solutions;
    }
}
