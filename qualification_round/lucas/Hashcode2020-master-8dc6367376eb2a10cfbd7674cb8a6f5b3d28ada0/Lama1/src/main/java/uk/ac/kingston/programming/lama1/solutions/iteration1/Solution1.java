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
import uk.ac.kingston.programming.lama1.model.LibraryStock;
import uk.ac.kingston.programming.lama1.model.LibraryStockComparator;

/**
 *
 * @author Lama1
 */
public class Solution1 extends SolutionBase {
    
    private Integer score = 0;
    
    public Solution1()
    {
        super(App.Name + "_Solution1");
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
        
        var libraryStocks = new ArrayList<LibraryStock>();
        var libraryId = 0;
        for(var library : dataFileIn.Libraries)
        {
            var libraryStock = new LibraryStock();
            libraryStock.Id = libraryId;
            libraryStock.ShipPerDay = library.ShipPerDay;
            libraryStock.SignUpProcess = library.SignUpProcess;
            for(var bookOnOrder : bookOrder.Books)
            {
                for(var book : library.Books)
                {
                    if(Objects.equals(book, bookOnOrder.Id))
                    {
                        libraryStock.Score += bookOnOrder.Score;
                        libraryStock.Books.add(bookOnOrder);
                        break;
                    }
                }
            }
            libraryId++;
            Collections.sort(libraryStock.Books, new BookComparator());
            libraryStocks.add(libraryStock);
        }
        
        Collections.sort(libraryStocks, new LibraryStockComparator());
        
        dataFileOut.LibraryPacks = getLibraryPacks(dataFileIn, bookOrder, libraryStocks);
        dataFileOut.score = score;
        
        return dataFileOut;
    }

    private ArrayList<LibraryPack> getLibraryPacks(DataFileIn dataFileIn, BookOrder bookOrder, ArrayList<LibraryStock> libraryStocks) 
    {
        var libraryPacks = new ArrayList<LibraryPack>();
        var noOfDays = dataFileIn.NoDaysForScanning;
        var noOfDaysLeft = noOfDays;
        for(var libraryStock : libraryStocks)
        {
            noOfDaysLeft = noOfDaysLeft - libraryStock.SignUpProcess;
            var libraryPack = new LibraryPack();
            libraryPack.Id = libraryStock.Id;
            var numberOfBooks = libraryStock.Books.size();
            var shipPerDay = libraryStock.ShipPerDay;
            var daysItWillTake = numberOfBooks / shipPerDay;
            var daysCanProcess = (noOfDaysLeft < daysItWillTake) ? noOfDaysLeft: daysItWillTake;
            while(daysCanProcess > 0)
            {
                for(var d = 1; d <= shipPerDay; d++)
                {
                    if(libraryStock.Books.isEmpty())
                    {
                        break;
                    }
                    var book = libraryStock.Books.get(0);

                    Book bookOnOrder = null;
                    for(var i = 0; i < bookOrder.Books.size(); i++)
                    {
                        var b = bookOrder.Books.get(i);
                        if(Objects.equals(b.Id, book.Id))
                        {
                            bookOrder.Books.remove(b);
                            bookOnOrder = b;
                            break;
                        }
                    }
                    if(bookOnOrder != null)
                    {
                        score += book.Score;
                        libraryPack.BooksToSend.add(book.Id);
                    }
                    libraryStock.Books.remove(0);                    
                }
                daysCanProcess--;
            }
            if(libraryPack.BooksToSend.size() > 0)
            {
                libraryPacks.add(libraryPack);
            }
        }
        return libraryPacks;
    }
}
