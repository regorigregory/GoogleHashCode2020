/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;

import com.rrrrr.hashcode.entities.Challange;
import com.rrrrr.hashcode.entities.Library;
import com.rrrrr.hashcode.entities.Proposal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Madero Padero
 */
public class DynamicProgrammingSolver implements Solver {

    Challange challange;

    @Override
    public Proposal solve(Challange challangeDataWrapper) {

        Proposal p = new Proposal();

        int numLibrariesSignedUpForScanning = 0;
        int daysLeftToScan = challangeDataWrapper.getDaysGivenToscan();
        int librariesLeftToCheck = challangeDataWrapper.getLibraries().size();
        while (daysLeftToScan > 0 && librariesLeftToCheck>0) {
            
            librariesLeftToCheck = challangeDataWrapper.getLibraries().size();
            Library.globalDaysRemaining = daysLeftToScan;
            //Uses Library.compareTo which dynamically calculates 1 library's max ;possible score
            //based on the books that has not been sent in the central repository
            LinkedList<Library> librariesStillAvailable = challangeDataWrapper.getLibraries();
            try {
               //librariesStillAvailable =  librariesStillAvailable.parallelStream().sorted().collect(Collectors.toCollection(LinkedList::new));
               Collections.sort(librariesStillAvailable);
            }catch (Exception e){
                boolean ehraw = true;
                e.printStackTrace();
            }
            
            Library tempLibraryPointer = challangeDataWrapper.getLibraries().remove(0);
            //register books in the catalogue as sent....
            
            p.getLibrariesToRegisterInOrder().add(tempLibraryPointer);
            p.getBooksPerLibraryToSend().add(tempLibraryPointer.getBooksToSend());

            daysLeftToScan = daysLeftToScan - tempLibraryPointer.getRegistrationDays();
            Library.globalDaysRemaining = daysLeftToScan;
            tempLibraryPointer.sendBooks();

            
        }
        
        System.out.println("The optimisation has finished, proposal is ready to be converted to a file.");
        
        return p;
    }

}
