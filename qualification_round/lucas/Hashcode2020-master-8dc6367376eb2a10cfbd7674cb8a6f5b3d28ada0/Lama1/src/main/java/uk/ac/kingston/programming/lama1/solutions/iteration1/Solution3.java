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
import uk.ac.kingston.programming.lama1.model.LibraryInfoComparator;
import uk.ac.kingston.programming.lama1.model.LibraryStock;
import uk.ac.kingston.programming.lama1.model.LibraryStockComparator;

/**
 *
 * @author Lama1
 */
public class Solution3 extends SolutionBase {
    
    private Integer score = 0;
    
    public Solution3()
    {
        super(App.Name + "_Solution3");
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
            System.out.println(dataFileIn.filename + " - " + libraryId);
            libraryId++;
            Collections.sort(libraryInfo.Books, new BookComparator());
            
            libraryList.add(libraryInfo);
        }
        
        for(var libraryInfo : libraryList)
        {
            var noOfDaysLeft = dataFileIn.NoDaysForScanning - libraryInfo.SignUpProcess;
            var b = 0;
            var done = false;
            while(noOfDaysLeft > 0 || !done)
            {
                for(var i = 1; i <= libraryInfo.ShipPerDay; i++)
                {
                    if(b > libraryInfo.Books.size()-1) 
                    {
                        done = true;
                        break;
                    }
                    var book = libraryInfo.Books.get(b);
                    libraryInfo.MaxScore += book.Score;
                    b++;
                }
                noOfDaysLeft--;
                if(noOfDaysLeft == 0)
                {
                    done = true;
                }
            }
        }
        
        Collections.sort(libraryList, new LibraryInfoComparator());
        var noOfDaysLeft = dataFileIn.NoDaysForScanning;
        for(var libraryInfo : libraryList)
        {
            noOfDaysLeft -= libraryInfo.SignUpProcess;
            libraryInfo.NoDaysLeft = noOfDaysLeft;
        }
        
        dataFileOut.LibraryPacks = getLibraryPacks(dataFileIn, bookOrder, libraryList);
        dataFileOut.score = score;
        return dataFileOut;
    }

    private ArrayList<LibraryPack> getLibraryPacks(DataFileIn dataFileIn, BookOrder bookOrder, ArrayList<LibraryInfo> libraryList) {
        var libraryPacks = new ArrayList<LibraryPack>();
        
        for(var libraryInfo : libraryList)
        {
            var libraryPack = new LibraryPack();
            libraryPack.Id = libraryInfo.Id;
            var done = false;
            var b = 0;
            while(libraryInfo.NoDaysLeft > 0 || !done)
            {
                for(var i = 1; i <= libraryInfo.ShipPerDay; i++)
                {
                    if(b > libraryInfo.Books.size()-1) 
                    {
                        done = true;
                        break;
                    }
                    var book = libraryInfo.Books.get(b);
                    if(bookOrder.Books.contains(book))
                    {
                        bookOrder.Books.remove(book);
                        libraryPack.BooksToSend.add(book.Id);
                        score += book.Score;
                    }
                    b++;
                }
                libraryInfo.NoDaysLeft--;
                if(libraryInfo.NoDaysLeft == 0)
                {
                    done = true;
                }
            }    
            if(libraryPack.BooksToSend.size() > 0) {
                libraryPacks.add(libraryPack);    
            }
            
        }
        return libraryPacks;
    }
}
