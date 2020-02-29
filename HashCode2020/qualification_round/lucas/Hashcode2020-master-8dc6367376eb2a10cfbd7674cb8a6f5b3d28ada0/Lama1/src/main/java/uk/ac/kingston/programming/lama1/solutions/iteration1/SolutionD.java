/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.hashcode.model.SolutionBase;
import uk.ac.kingston.programming.lama1.App;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookComparator;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class SolutionD extends SolutionBase implements Solver {
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public SolutionD()
    {
        super(App.Name + "_SolutionD");
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

        Collections.sort(bookOrder.Books, new BookComparator());
        
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
            libraryId++;
            Collections.sort(libraryInfo.Books, new BookComparator());
            
            libraryList.add(libraryInfo);
        }

        for(var book : bookOrder.Books)
        {
            for(var library : libraryList)
            {
                if(library.Books.contains(book))
                {
                    book.NoOfLibraries++;
                }
            }
        }
        
        bookOrder.Books.sort((p1, p2) -> {
            if (p2.Score.compareTo(p1.Score) == 0) {
                return p1.NoOfLibraries.compareTo(p2.NoOfLibraries);
            } else {
                return p2.Score.compareTo(p1.Score);
            } 
        });
        
        var booksInStock = new ArrayList<Book>();
        for(var book : bookOrder.Books)
        {
            if(book.NoOfLibraries > 0)
            {
                booksInStock.add(book);
            }
            System.out.println(book.Score + " - " + book.NoOfLibraries);
        }
        
        var libraryPacks = new ArrayList<LibraryPack>();
        var daysLeft = dataFileIn.NoDaysForScanning;
        var shippedBooks = new ArrayList<Book>();
        var attemptsToShipBook = 0;
        while(daysLeft > 0)
        {
            if(booksInStock.isEmpty())
            {
                break;
            }
            System.out.println(daysLeft + " - " + shippedBooks.size() + "/" + booksInStock.size());
            attemptsToShipBook++;
            var book = booksInStock.get(0);
            if(shippedBooks.contains(book))
            {
                booksInStock.remove(book);
                attemptsToShipBook = 0;
            }
            else
            {
                var possibleLibraries = new ArrayList<LibraryInfo>();
                for(var library : libraryList)
                {
                    if(library.Books.contains(book))
                    {
                        var noProcessingDays = library.Books.size() / library.ShipPerDay;
                        var daysAvailable = daysLeft - library.SignUpProcess;
                        if(daysAvailable < 1)
                        {
                            library.Weighting = -1;
                        }
                        else 
                        {
                            noProcessingDays = daysAvailable >= noProcessingDays ? daysAvailable : noProcessingDays;
                            var maxScore = 0;
                            var b = 0;
                            var done = false;
                            for(int p = 1; p <= noProcessingDays; p++)
                            {
                                for(int i = 1; i <= library.ShipPerDay; i++)
                                {
                                    if(b <= library.Books.size() -1)
                                    {
                                        var possibleBook = library.Books.get(b);
                                        if(!shippedBooks.contains(possibleBook))
                                        {
                                            maxScore += possibleBook.Score; 
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
                            library.Weighting = maxScore;
                        }
                        possibleLibraries.add(library);
                    }
                }
                if(possibleLibraries.size() > 0)
                {
                    possibleLibraries.sort((pl1, pl2) -> {
                        return pl2.Weighting.compareTo(pl1.Weighting);
                    });

                    var bestLibrary = possibleLibraries.get(0);                
                    libraryList.remove(bestLibrary);
                    var libraryPack = new LibraryPack();
                    libraryPack.Id = bestLibrary.Id;
                    var noProcessingDays = bestLibrary.Books.size() / bestLibrary.ShipPerDay;
                    var daysAvailable = daysLeft - bestLibrary.SignUpProcess;  
                    noProcessingDays = daysAvailable >= noProcessingDays ? daysAvailable : noProcessingDays;
                    if(noProcessingDays > 0)
                    {
                        daysLeft -= bestLibrary.SignUpProcess;
                        var b = 0;
                        var done = false;
                        for(int p = 1; p <= noProcessingDays; p++)
                        {
                            for(int i = 1; i <= bestLibrary.ShipPerDay; i++)
                            {
                                if(b <= bestLibrary.Books.size()-1)
                                {
                                    var bookToShip = bestLibrary.Books.get(b);
                                    if(!shippedBooks.contains(bookToShip))
                                    {
                                        libraryPack.BooksToSend.add(bookToShip.Id);    
                                        booksInStock.remove(bookToShip);
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
                }
                if(shippedBooks.contains(book))
                {
                    booksInStock.remove(book);
                    attemptsToShipBook = 0;
                }
                else if(attemptsToShipBook == 3)
                {
                    booksInStock.remove(book);
                    attemptsToShipBook = 0;
                }                
            }
        }
        
        dataFileOut.LibraryPacks = libraryPacks;
        dataFileOut.score = 0;
        for(var shippedBook : shippedBooks)
        {
            dataFileOut.score += shippedBook.Score;
        }
        var remainingScore = 0;
        for(var bookInStock : booksInStock)
        {
            remainingScore += bookInStock.Score;
        }
        
        System.out.println(dataFileIn.filename + " - " + dataFileOut.score + "/" + remainingScore);
        
        return dataFileOut;
    }

    @Override
    public void OnComplete(DataFileOut dataFileOut) {
        solutions.add(dataFileOut);
    }
}
