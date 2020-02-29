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
import uk.ac.kingston.programming.lama1.model.BookOptions;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.LibraryInfoComparator;
import uk.ac.kingston.programming.lama1.model.LibraryInfoComparatorDaysTaken;
import uk.ac.kingston.programming.lama1.model.LibraryInfoComparatorWeighting;
import uk.ac.kingston.programming.lama1.model.LibraryListComparator;
import uk.ac.kingston.programming.lama1.model.OptimisedLibraryList;

/**
 *
 * @author Lama1
 */
public class Solution4 extends SolutionBase {
    
    private Integer score = 0;
    
    public Solution4()
    {
        super(App.Name + "_Solution4");
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
            libraryId++;
            Collections.sort(libraryInfo.Books, new BookComparator());
            
            libraryList.add(libraryInfo);
        }

        Collections.sort(libraryList, new LibraryListComparator());
        Collections.sort(bookOrder.Books, new BookComparator());
        
        var bookOptionsList = new ArrayList<BookOptions>();
        for(var bookOnOrder : bookOrder.Books)
        {
            var bookOptions = new BookOptions();
            bookOptions.book = bookOnOrder;
            for(var libraryInfo : libraryList)
            {
                if(libraryInfo.Books.contains(bookOnOrder))
                {
                    bookOptions.libraryList.add(libraryInfo);
                }
            }
            bookOptionsList.add(bookOptions);
        }
        
        var daysLeft = dataFileIn.NoDaysForScanning;
        var libraryPacks = new ArrayList<LibraryPack>();
        var libraryProcessedList = new ArrayList<LibraryInfo>();
        
        for(var bookOptions : bookOptionsList)
        {
            if(daysLeft <= 0)
            {
                break;
            }
            if(bookOrder.Books.contains(bookOptions.book))
            {
                LibraryInfo bestLibraryInfo = null;

                for(var possibleLibraryInfo : bookOptions.libraryList)
                {
                    if(!libraryProcessedList.contains(possibleLibraryInfo))
                    {
                        bestLibraryInfo = possibleLibraryInfo;
                        break;
                    }
                }

                if(bestLibraryInfo != null)
                {
                    var libraryPack = new LibraryPack();
                    libraryPack.Id = bestLibraryInfo.Id;
                    var done = false;
                    var b = 0;
                    daysLeft -= bestLibraryInfo.SignUpProcess;
                    bestLibraryInfo.NoDaysLeft = daysLeft;
                    while(bestLibraryInfo.NoDaysLeft > 0 || !done)
                    {
                        for(var i = 1; i <= bestLibraryInfo.ShipPerDay; i++)
                        {
                            if(b > bestLibraryInfo.Books.size()-1) 
                            {
                                done = true;
                                break;
                            }
                            var book = bestLibraryInfo.Books.get(b);
                            if(bookOrder.Books.contains(book))
                            {
                                bookOrder.Books.remove(book);
                                libraryPack.BooksToSend.add(book.Id);
                                score += book.Score;
                            }
                            b++;
                        }
                        bestLibraryInfo.NoDaysLeft--;
                        if(bestLibraryInfo.NoDaysLeft == 0)
                        {
                            done = true;
                        }
                    }
                    if(libraryPack.BooksToSend.size() > 0) {
                        libraryPacks.add(libraryPack);    
                    }
                    libraryProcessedList.add(bestLibraryInfo);                
                }                
            }
        }
        
        var scoreLeft = 0;
        for(var bookOnOrder : bookOrder.Books)
        {
            scoreLeft += bookOnOrder.Score;
        }
        System.out.println(dataFileIn.filename + " - " + scoreLeft);
        dataFileOut.LibraryPacks = libraryPacks;
        dataFileOut.score = score;
        return dataFileOut;
    }
}
