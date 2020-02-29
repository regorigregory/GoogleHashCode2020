/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions;

import uk.ac.kingston.programming.lama1.solutions.iteration1.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.hashcode.model.SolutionBase;
import uk.ac.kingston.programming.lama1.App;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class Solution2E extends SolutionBase implements Solver {
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public Solution2E()
    {
        super(App.Name + "_Solution2e");
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
            return b1.Score.compareTo(b2.Score);
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
                        break;
                    }                
                }
            }
            libraryInfo.Books.sort((b1, b2) -> {
                return b2.Score.compareTo(b1.Score);
            });
            var daysLeft = dataFileIn.NoDaysForScanning - libraryInfo.SignUpProcess;
            var b = 0;
            var done = false;
            for(int d = 1; d <= daysLeft; d++)
            {
                for(int p = 1; p <= libraryInfo.ShipPerDay; p++)
                {
                    if(b == libraryInfo.Books.size())
                    {
                        done = true;
                        break;
                    }
                    libraryInfo.MaxScore += libraryInfo.Books.get(b).Score;
                    b++;
                }
                if(done)
                {
                    break;
                }
            }
            libraryList.add(libraryInfo);
            libraryId++;
        }
        
        libraryList.sort((l1, l2) -> {
            if (l2.ShipPerDay.compareTo(l1.ShipPerDay) == 0) {
                if(l1.SignUpProcess.compareTo(l2.SignUpProcess) == 0)
                {
                    return l2.MaxScore.compareTo(l1.MaxScore);    
                }
                else
                {
                    return l1.SignUpProcess.compareTo((l2.SignUpProcess));
                }
            } else {
                return l2.ShipPerDay.compareTo(l1.ShipPerDay);
            }  
        });
        
        var libraryPacks = new ArrayList<LibraryPack>();
        var daysLeft = dataFileIn.NoDaysForScanning;
        var booksShipped = new ArrayList<Book>();
        
        while(daysLeft >= 0)
        {
            if(libraryList.isEmpty())
            {
                break;
            }
            
            var library = libraryList.get(0);
            //System.out.println(library.Id + " - " + library.SignUpProcess + " - " + library.ShipPerDay + " - " + library.MaxScore + " - " + library.MaxNoDaysLeft);
            var libraryPack = new LibraryPack();
            var booksInThisRound = new ArrayList<Book>();
            libraryPack.Id = library.Id;
            
            var noOfProcessingDays = daysLeft - library.SignUpProcess + 1;
            var done = false;
            var b = 0;
            
            for(int p = 1; p <= noOfProcessingDays; p++)
            {
                for(int d = 1; d <= library.ShipPerDay; d++)
                {
                    if(b == library.Books.size())
                    {
                        done = true;
                        break;
                    }
                    var book = library.Books.get(b);
                    if(!booksShipped.contains(book))
                    {
                        libraryPack.BooksToSend.add(book.Id);
                        booksInThisRound.add(book);
                        booksShipped.add(book);
                    }
                    b++;
                }
                if(done)
                {
                    break;
                }
            }
            
            if(libraryPack.BooksToSend.size() > 0)
            {
                daysLeft -= library.SignUpProcess;
                libraryPacks.add(libraryPack);                
            }
            
            libraryList.remove(library);
            
            for(var lib : libraryList)
            {
                var daysLeftForThisLibrary = daysLeft - lib.SignUpProcess + 1;
                var lb = 0;
                var ldone = false;
                for(int d = 1; d <= daysLeftForThisLibrary; d++)
                {
                    for(int p = 1; p <= lib.ShipPerDay; p++)
                    {
                        if(lb == lib.Books.size())
                        {
                            ldone = true;
                            break;
                        }
                        lib.MaxScore += lib.Books.get(lb).Score;
                        lb++;
                    }
                    if(ldone)
                    {
                        break;
                    }
                } 
            }
            
            libraryList.sort((l1, l2) -> {
                if (l2.ShipPerDay.compareTo(l1.ShipPerDay) == 0) {
                    if(l1.SignUpProcess.compareTo(l2.SignUpProcess) == 0)
                    {
                        return l2.MaxScore.compareTo(l1.MaxScore);    
                    }
                    else
                    {
                        return l1.SignUpProcess.compareTo((l2.SignUpProcess));
                    }
                } else {
                    return l2.ShipPerDay.compareTo(l1.ShipPerDay);
                }  
            });            
        }
        
        for(var book : booksShipped)
        {
            dataFileOut.score += book.Score;
        }
        dataFileOut.LibraryPacks = libraryPacks;
        return dataFileOut;
    }

    @Override
    public void OnComplete(DataFileOut dataFileOut) {
        solutions.add(dataFileOut);
    }
}
