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
import java.util.List;
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
import uk.ac.kingston.programming.lama1.logic.Permutations;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.CandidateLibraryPack;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.LibraryInfoSorter;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class SolutionLibrarySorter3 extends SolutionBase {
    
    private final LibraryInfoSorter libraryInfoSorter;
    private int noOfCandidates = 0;
    private int scoreToBeat = 0;
    private String id = "";
    private int filter = 0;
    
    public SolutionLibrarySorter3(String id, LibraryInfoSorter libraryInfoSorter, int filter, int noOfCandidates, int scoreToBeat)
    {
        super(App.Name + "_SolutionLibrarySorter3");       
        this.id = id;
        this.libraryInfoSorter = libraryInfoSorter;
        this.noOfCandidates = noOfCandidates;
        this.scoreToBeat = scoreToBeat;
        this.filter = filter;
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
        
        if(dataFileIn.NoBooks != bookOrder.Books.size())
        {
            System.out.println("No of books out");
        }
        
        var maxBookScore = 0;
        for(var book : bookOrder.Books)
        {
            maxBookScore += book.Score;
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
        
        var libraryList2 = (ArrayList<LibraryInfo>)libraryList.clone();
        
        for(var library : libraryList2)
        {
            if(filter == 1)
            {
                if(library.SignUpProcess > 2)
                {
                    libraryList.remove(library);
                }
            }
        }
        
        libraryList.sort((l1, l2) -> {
            return libraryInfoSorter.sort(l1, l2);
        }); 
        
        if(dataFileIn.NoLibraries != libraryList.size())
        {
            System.out.println("No of libraries out");
        }
        
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
            
            for(int i = 0; i < noOfCandidates; i++)
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
                var booksShippedIncludingCandidateLibrary = (ArrayList<Book>)booksShipped.clone();
                var library = candidateLibrary;
                var candidateLibraryPack = new CandidateLibraryPack();
                candidateLibraryPack.LibraryPack.Id = library.Id;
                candidateLibraryPack.Library = library;

                var noOfProcessingDays = daysLeft - library.SignUpProcess + 1;
                var done = false;
                var b = 0;
                candidateLibraryPack.LibraryPack.NoOfDays = library.SignUpProcess;
                for(int p = 1; p <= noOfProcessingDays; p++)
                {
                    candidateLibraryPack.LibraryPack.NoOfDays++;
                    for(int d = 1; d <= library.ShipPerDay; d++)
                    {
                        if(b == library.Books.size())
                        {
                            done = true;
                            break;
                        }
                        var book = library.Books.get(b);
                        if(!booksShippedIncludingCandidateLibrary.contains(book))
                        {
                            candidateLibraryPack.LibraryPack.Score += book.Score;
                            candidateLibraryPack.LibraryPack.BooksToSend.add(book.Id);
                            candidateLibraryPack.BooksShipped.add(book);
                            booksShippedIncludingCandidateLibrary.add(book);
                        }
                        b++;
                    }
                    if(done)
                    {
                        break;
                    }
                }
                candidateLibraryPack.LibraryPack.ScorePerDay = candidateLibraryPack.LibraryPack.NoOfDays == 0 ? 
                        0 : (candidateLibraryPack.LibraryPack.Score / candidateLibraryPack.LibraryPack.NoOfDays) * 100;
                candidateLibraryPacks.add(candidateLibraryPack);
            }

            candidateLibraryPacks.sort((cl1, cl2) -> {
                Integer x1 = cl1.LibraryPack.Score / cl1.Library.SignUpProcess;
                Integer x2 = cl2.LibraryPack.Score / cl2.Library.SignUpProcess;
                return x2.compareTo(x1);
            });
            
            if(candidateLibraryPacks.size() > 0)
            {
                var bestLibraryPack = candidateLibraryPacks.get(0); //getBestCandidateLibraryPack(daysLeft, booksShipped, candidateLibraryPacks);
                var library = bestLibraryPack.Library;
                var libraryPack = bestLibraryPack.LibraryPack;

                if(libraryPack.BooksToSend.size() > 0)
                {
                    daysLeft -= (library.SignUpProcess-1);
                    libraryPacks.add(libraryPack);                
                    booksShipped.addAll(bestLibraryPack.BooksShipped);
                    score += libraryPack.Score; 
                    System.out.println("Id - " + library.Id + " ShipPerDay - " + library.ShipPerDay + " SignUpProcess - " + library.SignUpProcess + " No Books - " + library.Books.size() + " Shipped - " + bestLibraryPack.BooksShipped.size() + " Score - " + libraryPack.Score);
                }
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
                return libraryInfoSorter.sort(l1, l2);              
            });             
        }
        
        System.out.println("Id: " + id + " Books: " + (dataFileIn.NoBooks-booksShipped.size()) + "/" + dataFileIn.NoBooks + "/" + booksShipped.size() +
                " Libraries: " + (dataFileIn.NoLibraries-libraryPacks.size()) + "/" + dataFileIn.NoLibraries + "/" + libraryPacks.size() +
                " Score: " + (maxBookScore-score) + "/" + maxBookScore + "/" + score);
        
        if(score > scoreToBeat)
        {
            dataFileOut.score += score;
            dataFileOut.LibraryPacks = libraryPacks;
            return dataFileOut;
        }
        
        return null;
    }
    
    private CandidateLibraryPack getBestCandidateLibraryPack(Integer daysLeft, ArrayList<Book> booksShipped, ArrayList<CandidateLibraryPack> candidateLibraryPacks)
    {
        var topCandidateLibraryPacks = new ArrayList<CandidateLibraryPack>();
        for(int i = 0; i < 3; i++)
        {
            if(i < candidateLibraryPacks.size()-1)
            {
                topCandidateLibraryPacks.add(candidateLibraryPacks.get(i));
            }
        }
        var permutations = Permutations.getPermutations(topCandidateLibraryPacks);
        
        var bestLibraryPack = new CandidateLibraryPack();
        
        for(var permutation : permutations)
        {
            var score = getScore(daysLeft, (ArrayList<Book>)booksShipped.clone(), permutation);
        }
        return bestLibraryPack;
    }
    
    private Integer getScore(Integer daysLeft, ArrayList<Book> booksShipped, List<CandidateLibraryPack> candidateLibraryPacks)
    {
        var score = 0;
        var i = candidateLibraryPacks.size();
        for(var candidateLibraryPack: candidateLibraryPacks)
        {
            var library = candidateLibraryPack.Library;
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
                        score += book.Score;
                        booksShipped.add(book);
                    }
                    b++;
                }
                if(done)
                {
                    break;
                }
            } 
        }
        return score;
    }
}
