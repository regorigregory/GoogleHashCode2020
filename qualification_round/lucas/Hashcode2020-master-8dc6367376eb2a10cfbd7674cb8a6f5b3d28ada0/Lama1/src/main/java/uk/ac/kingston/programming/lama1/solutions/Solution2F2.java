/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions;

import uk.ac.kingston.programming.lama1.solutions.iteration1.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import uk.ac.kingston.programming.hashcode.data.DataFileManager;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.DataFileTransformer;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.hashcode.model.SolutionBase;
import uk.ac.kingston.programming.lama1.App;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.CandidateLibraryPack;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class Solution2F2 extends SolutionBase implements Solver {
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public Solution2F2()
    {
        super(App.Name + "_Solution2f2");       
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
            int noOfDays = 0;
            for(int d = 1; d <= daysLeft; d++)
            {
                noOfDays ++;
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
            libraryInfo.NoOfDays = noOfDays + libraryInfo.SignUpProcess;
            libraryInfo.MaxScorePerDay = noOfDays == 0 ? 0 : (libraryInfo.MaxScore / libraryInfo.NoOfDays) * 100;
            libraryList.add(libraryInfo);
            libraryId++;
        }
        
        libraryList.sort((l1, l2) -> {
            if (l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay) == 0) {
                return l1.NoOfDays.compareTo(l2.NoOfDays);
            } else {
                return l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay);
            }             
        }); 
        
        var libraryPacks = new ArrayList<LibraryPack>();
        var daysLeft = dataFileIn.NoDaysForScanning;
        var booksShipped = new ArrayList<Book>();
        var score = 0;
        while(daysLeft >= 0)
        {
            if(libraryList.isEmpty())
            {
                break;
            }
            
            var candidateLibraries = new ArrayList<LibraryInfo>();
            
            for(int i = 0; i < 9; i++)
            {
                if(i == libraryList.size())
                {
                    break;
                }
                candidateLibraries.add(libraryList.get(i));
            }
            
            var candidateLibraryPacks = new ArrayList<CandidateLibraryPack>();
            for(var candidateLibrary: candidateLibraries)
            {
                var library = candidateLibrary;
                var libraryPack = new CandidateLibraryPack();
                libraryPack.LibraryPack.Id = library.Id;
                libraryPack.Library = library;

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
                            libraryPack.LibraryPack.Score += book.Score;
                            libraryPack.LibraryPack.BooksToSend.add(book.Id);
                            libraryPack.BooksShipped.add(book);
                        }
                        b++;
                    }
                    if(done)
                    {
                        break;
                    }
                }
                candidateLibraryPacks.add(libraryPack);
            }

            candidateLibraryPacks.sort((cl1, cl2) -> {
                return cl2.LibraryPack.Score.compareTo(cl1.LibraryPack.Score);
            });
            
            if(candidateLibraryPacks.size() > 0)
            {
                var bestLibraryPack = candidateLibraryPacks.get(0);
                var library = bestLibraryPack.Library;
                var libraryPack = bestLibraryPack.LibraryPack;

                if(libraryPack.BooksToSend.size() > 0)
                {
                    daysLeft -= library.SignUpProcess;
                    libraryPacks.add(libraryPack);                
                }
                booksShipped.addAll(bestLibraryPack.BooksShipped);
                score += libraryPack.Score;
                libraryList.remove(library);   
            }
            
            for(var lib : libraryList)
            {
                var daysLeftForThisLibrary = daysLeft - lib.SignUpProcess + 1;
                var lb = 0;
                var ldone = false;
                var noOfDays = 0;
                for(int d = 1; d <= daysLeftForThisLibrary; d++)
                {
                    noOfDays++;
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
                lib.NoOfDays = noOfDays + lib.SignUpProcess;
                lib.MaxScorePerDay = noOfDays == 0 ? 0 : (lib.MaxScore / lib.NoOfDays) * 100;
            }
            
            libraryList.sort((l1, l2) -> {
                if (l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay) == 0) {
                    return l1.NoOfDays.compareTo(l2.NoOfDays);
                } else {
                    return l2.MaxScorePerDay.compareTo(l1.MaxScorePerDay);
                }              
            });             
        }
        
        dataFileOut.score += score;
        dataFileOut.LibraryPacks = libraryPacks;
        
        return dataFileOut;
    }

    @Override
    public void OnComplete(DataFileOut dataFileOut) {
        solutions.add(dataFileOut);
    }
}
