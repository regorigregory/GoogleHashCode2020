/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

import java.util.ArrayList;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class SolverForSolution7 extends Thread {
    
    private DataFileIn dataFileIn;
    private DataFileOut dataFileOut;
    private ArrayList<LibraryInfo> libraryList;
    private Solver solver;

    public SolverForSolution7(DataFileIn dataFileIn, DataFileOut dataFileOut, ArrayList<LibraryInfo> libraryList, Solver solver)
    {
        this.dataFileIn = dataFileIn;
        this.dataFileOut = dataFileOut;
        this.libraryList = libraryList;
        this.solver = solver;
    }
    @Override
    public void run()
    {
        var libraryPacks = new ArrayList<LibraryPack>();
        var shippedBooks = new ArrayList<Book>();
        var daysLeft = dataFileIn.NoDaysForScanning;
        
        for(var library : libraryList)
        {
            if(daysLeft <= 0)
            {
                break;
            }
            var libraryPack = new LibraryPack();
            libraryPack.Id = library.Id;
            var noProcessingDays = library.Books.size() / library.ShipPerDay;
            var daysAvailable = daysLeft - library.SignUpProcess;  
            noProcessingDays = daysAvailable >= noProcessingDays ? daysAvailable : noProcessingDays;
            if(noProcessingDays > 0)
            {
                daysLeft -= library.SignUpProcess;
                var b = 0;
                var done = false;
                for(int p = 1; p <= noProcessingDays; p++)
                {
                    for(int i = 1; i <= library.ShipPerDay; i++)
                    {
                        if(b <= library.Books.size()-1)
                        {
                            var bookToShip = library.Books.get(b);
                            if(!shippedBooks.contains(bookToShip))
                            {
                                libraryPack.BooksToSend.add(bookToShip.Id);    
                                shippedBooks.add(bookToShip);                                    
                            }
                        }
                        else
                        {
                            done = true;
                            break;
                        }
                        b++;
                    }
                    if(done)
                    {
                        break;
                    }                            
                }                     
            }
            
            if(libraryPack.BooksToSend.size() > 0)
            {
                libraryPacks.add(libraryPack);
            }  
            else
            {
                daysLeft += library.SignUpProcess;
            }
        }
        
        for(var shippedBook : shippedBooks)
        {
            dataFileOut.score += shippedBook.Score;
        }
        dataFileOut.LibraryPacks = libraryPacks;
        
        solver.OnComplete(dataFileOut);
    }
}
