/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;

import com.rrrrr.hashcode.entities.Challange;
import com.rrrrr.hashcode.entities.Library;
import com.rrrrr.hashcode.entities.Proposal;
import java.util.List;

/**
 *
 * @author Madero Padero
 */
public class KnapsackSolver implements Solver{

    @Override
    public Proposal solve(Challange ch) {
        //start using knapknap
        Proposal p = new Proposal();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Proposal knapknap(Library excluded, List<Library> librariesRemaining, Proposal proposal,  int daysRemaining){
        
        if(daysRemaining-1 ==0){
            return proposal;
        }
        var clonedProposal = proposal.copy();
        
        
        
        throw new UnsupportedOperationException("Not yet supported.");
    }
    //step 1 - find the books which only one library has
    //step 2 - knapsack the libraries
    //reversed order fillup?
    //days remaining...
    
}
