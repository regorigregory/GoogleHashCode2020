/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.hashcode.model.SolutionBase;
import uk.ac.kingston.programming.lama1.App;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookComparator;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;

/**
 *
 * @author Lama1
 */
public class Solution6 extends SolutionBase {
    
    private Integer score = 0;
    
    public Solution6()
    {
        super(App.Name + "_Solution6");
    }
    
    @Override
    public DataFileOut solve(DataFileIn dataFileIn)
    {
        var dataFileOut = CreateDataFileOut(dataFileIn);
        
        var bookOrder = new BookOrder();
        for(int i = 0; i < dataFileIn.BookScores.size(); i++)
        {
            var score = dataFileIn.BookScores.get(i);
            var book = new Book();
            book.Id = i;
            book.Score = score;
            bookOrder.Books.add(book);
        }
        
        bookOrder.Books.sort((b1, b2) -> {
            return b2.Score.compareTo(b1.Score);
        });
        
        var libraryList = new ArrayList<LibraryInfo>();
        var libraryId = 0;
        for(var library : dataFileIn.Libraries)
        {           
            var libraryInfo = new LibraryInfo();
            libraryInfo.Id = libraryId;
            libraryInfo.ShipPerDay = library.ShipPerDay;
            libraryInfo.SignUpProcess = library.SignUpProcess;
            
            for(var book : library.Books)
            {
                for(var bookOnOrder : bookOrder.Books)
                {
                    if(Objects.equals(book, bookOnOrder.Id))
                    {
                        libraryInfo.Books.add(bookOnOrder);
                        libraryInfo.MaxScore += bookOnOrder.Score;        
                        break;
                    }                
                }
            }
            
            libraryList.add(libraryInfo);
            libraryId++;
        }
        
        libraryList.sort((l1, l2) -> {
            if (l2.ShipPerDay.compareTo(l1.ShipPerDay) == 0) {
                return l1.SignUpProcess.compareTo(l2.SignUpProcess);
            } else {
                return l2.ShipPerDay.compareTo(l1.ShipPerDay);
            }             
        });
        
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
        
        return dataFileOut;
    }
}
