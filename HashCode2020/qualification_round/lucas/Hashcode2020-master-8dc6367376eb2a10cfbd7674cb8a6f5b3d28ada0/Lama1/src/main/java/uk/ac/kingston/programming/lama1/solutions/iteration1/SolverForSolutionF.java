/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

import java.util.ArrayList;
import java.util.Collections;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class SolverForSolutionF extends Thread {
    
    private DataFileIn dataFileIn;
    private DataFileOut dataFileOut;
    private BookOrder bookOrder;
    private ArrayList<LibraryInfo> libraryList;
    private Solver solver;

    public SolverForSolutionF(DataFileIn dataFileIn, DataFileOut dataFileOut, BookOrder bookOrder, ArrayList<LibraryInfo> libraryList, Solver solver)
    {
        this.dataFileIn = dataFileIn;
        this.dataFileOut = dataFileOut;
        this.bookOrder = bookOrder;
        this.libraryList = libraryList;
        this.solver = solver;
    }
    @Override
    public void run()
    {
        var libraryPacks = new ArrayList<LibraryPack>();
        var shippedBooks = new ArrayList<Book>();
        var daysLeft = dataFileIn.NoDaysForScanning;
        
        while(libraryList.size() > 0)
        {
            if(daysLeft <= 0)
            {
                break;
            }
            
            Collections.shuffle(libraryList);
            
            var library = libraryList.get(0);
            //System.out.println(library.Id + " - " + library.SignUpProcess + " - " + library.ShipPerDay + " - " + library.MaxScore + " - " + library.MaxNoDaysLeft);
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
            
            libraryList.remove(library);
        }
        
        for(var shippedBook : shippedBooks)
        {
            dataFileOut.score += shippedBook.Score;
        }
        
        System.out.println(shippedBooks.size());
        
        dataFileOut.LibraryPacks = libraryPacks;
        
        solver.OnComplete(dataFileOut);
    }
}
