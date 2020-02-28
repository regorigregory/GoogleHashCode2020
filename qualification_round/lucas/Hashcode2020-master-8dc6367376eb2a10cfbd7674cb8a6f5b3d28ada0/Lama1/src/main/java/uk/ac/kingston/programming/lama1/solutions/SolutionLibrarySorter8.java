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
public class SolutionLibrarySorter8 extends SolutionBase {
    
    private final LibraryInfoSorter libraryInfoSorter;
    private int noOfCandidates = 0;
    private int scoreToBeat = 0;
    private String id = "";
    private int filter = 0;
    private int top;
    int sequenceNo;
    int noPermutations;
    
    public SolutionLibrarySorter8(String id, LibraryInfoSorter libraryInfoSorter, 
            int filter, int noOfCandidates, int scoreToBeat, int top, int sequenceNo, int noPermutations)
    {
        super(App.Name + "_SolutionLibrarySorter8");       
        this.id = id;
        this.libraryInfoSorter = libraryInfoSorter;
        this.noOfCandidates = noOfCandidates;
        this.scoreToBeat = scoreToBeat;
        this.filter = filter;
        this.top = top;
        this.sequenceNo = sequenceNo;
        this.noPermutations = noPermutations;
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
            else if(filter == 2)
            {
                if(library.ShipPerDay <= 5 || library.SignUpProcess >= 40)
                {
                    //libraryList.remove(library);
                }
            }
            else if(filter == 3)
            {
                if(library.ShipPerDay < 10000)
                {
                    //libraryList.remove(library);
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
        var libraryListPostProcessing = new ArrayList<LibraryInfo>();
        var libraryListPostProcessingNoProcessed = new ArrayList<LibraryInfo>();
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
                var library = candidateLibrary;
                library.FinalScore = 0;
                library.FinalNoOfDaysRequired = 0;
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
                    library.FinalNoOfDaysRequired++;
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
                            library.FinalScore+= book.Score;
                            candidateLibraryPack.LibraryPack.Score += book.Score;
                            candidateLibraryPack.LibraryPack.BooksToSend.add(book.Id);
                            candidateLibraryPack.BooksShipped.add(book);
                        }
                        b++;
                    }
                    if(done)
                    {
                        break;
                    }
                }
                candidateLibraryPack.LibraryPack.ScorePerBook = candidateLibraryPack.LibraryPack.Score / ((candidateLibraryPack.BooksShipped.size() == 0) ? 1 : candidateLibraryPack.BooksShipped.size());
                candidateLibraryPack.LibraryPack.ScorePerDay = candidateLibraryPack.LibraryPack.NoOfDays == 0 ? 
                        0 : (candidateLibraryPack.LibraryPack.Score / candidateLibraryPack.LibraryPack.NoOfDays) * 100;
                candidateLibraryPacks.add(candidateLibraryPack);
            }

            candidateLibraryPacks.sort((cl1, cl2) -> {
                if(cl2.LibraryPack.ScorePerDay.compareTo(cl1.LibraryPack.ScorePerDay) == 0)
                {
                    return cl2.LibraryPack.Score.compareTo(cl1.LibraryPack.Score);
                }
                else
                {
                    return cl2.LibraryPack.ScorePerDay.compareTo(cl1.LibraryPack.ScorePerDay);
                }
            });
            
            if(candidateLibraryPacks.size() > 0)
            {
                var bestLibraryPack = getBestCandidateLibraryPack(daysLeft, candidateLibraryPacks, libraryList, booksShipped);
                if(bestLibraryPack == null)
                {
                    bestLibraryPack = candidateLibraryPacks.get(0);
                }
                var library = bestLibraryPack.Library;
                var libraryPack = bestLibraryPack.LibraryPack;

                if(libraryPack.BooksToSend.size() > 0)
                {
                    daysLeft -= (library.SignUpProcess-1);
                    libraryPacks.add(libraryPack);
                    System.out.println("Id - " + library.Id + " ShipPerDay - " + library.ShipPerDay + " SignUpProcess - " + library.SignUpProcess + " No Books - " + library.Books.size() + " Shipped - " + bestLibraryPack.BooksShipped.size() + " Score - " + libraryPack.Score);
                    libraryListPostProcessing.add(library);
                }
                else
                {
                    libraryListPostProcessingNoProcessed.add(library);
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
                return libraryInfoSorter.sort(l1, l2);              
            });             
        }
        
        libraryListPostProcessing.sort((l1, l2) -> {
            return l1.SignUpProcess.compareTo(l2.SignUpProcess);
        });
        
        var libraryPacks2 = new ArrayList<LibraryPack>();
        var daysLeft2 = dataFileIn.NoDaysForScanning;
        var booksShipped2 = new ArrayList<Book>();
        var score2 = 0;

        while(daysLeft2 >= 0)
        {
            if(libraryListPostProcessing.isEmpty())
            {
                break;
            }
            
            var nextLibraries = new ArrayList<LibraryInfo>();
            for(int i = 0; i < noPermutations; i++)
            {
                if(i < libraryListPostProcessing.size())
                {
                    var nextLibrary = libraryListPostProcessing.get(i);
                    nextLibraries.add(nextLibrary);
                    libraryListPostProcessing.remove(nextLibrary);
                }
            }
            
            var permutations = Permutations.getPermutations(nextLibraries);

            var bestPermutationScore2 = 0;
            var bestPermutationLibraryPacks = new ArrayList<LibraryPack>();
            
            for(var permutation : permutations)
            {
                var permutationDaysLeft2 = daysLeft2;
                var permutationBooksShipped2 = (ArrayList<Book>)booksShipped2.clone();
                var permutationScore2 = score2;
                var permutationLibraryPacks = new ArrayList<LibraryPack>();
                for(var library : permutation)
                {
                    var libraryPack = new LibraryPack();
                    libraryPack.Id = library.Id;
                    libraryPack.SignUpProcess = library.SignUpProcess;
                    var noOfProcessingDays = permutationDaysLeft2 - library.SignUpProcess + 1;
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
                            if(!permutationBooksShipped2.contains(book))
                            {
                                permutationScore2 += book.Score;
                                libraryPack.Score += book.Score;
                                libraryPack.BooksToSend.add(book.Id);
                                permutationBooksShipped2.add(book);
                            }
                            b++;
                        }
                        if(done)
                        {
                            break;
                        }
                    } 
                    permutationLibraryPacks.add(libraryPack);
                }
                if(permutationScore2 > bestPermutationScore2)
                {
                    bestPermutationLibraryPacks = permutationLibraryPacks;
                    bestPermutationScore2 = permutationScore2;
                }
            }
            
            for(var libraryPack : bestPermutationLibraryPacks)
            {
                if(libraryPack.BooksToSend.size() > 0)
                {
                    libraryPacks2.add(libraryPack);
                    daysLeft2 -= (libraryPack.SignUpProcess-1);
                    score2 += libraryPack.Score;
                }                
            }
        }
        
        System.out.println("Id: " + id + " Books: " + (dataFileIn.NoBooks-booksShipped2.size()) + "/" + dataFileIn.NoBooks + "/" + booksShipped2.size() +
                " Libraries: " + (dataFileIn.NoLibraries-libraryPacks2.size()) + "/" + dataFileIn.NoLibraries + "/" + libraryPacks2.size() +
                " Score: " + (maxBookScore-score2) + "/" + maxBookScore + "/" + score2);
        
        System.out.println(libraryListPostProcessingNoProcessed.size());
        
        if(score2 > scoreToBeat)
        {
            dataFileOut.score += score2;
            dataFileOut.LibraryPacks = libraryPacks2;
            return dataFileOut;
        }
        
        return null;
    }
    
    private CandidateLibraryPack getBestCandidateLibraryPack(int daysLeft, ArrayList<CandidateLibraryPack> candidateLibraryPacks, ArrayList<LibraryInfo> libraryList, ArrayList<Book> booksShipped)
    {
        CandidateLibraryPack bestCandidateLibraryPack = null;
        var topCandidateLibraryPacks = new ArrayList<CandidateLibraryPack>();
        
        for(int i = 0; i < top; i++)
        {
            if(i < candidateLibraryPacks.size())
            {
                topCandidateLibraryPacks.add(candidateLibraryPacks.get(i));
            }
        }
        
        var topScore = 0;
        var i = 0;
        for(var candidateLibraryPack : topCandidateLibraryPacks)
        {
            var score = getLibraryScoreForSequence(daysLeft, candidateLibraryPack.Library, libraryList, booksShipped, sequenceNo, 0);
            if(score > topScore)
            {
                bestCandidateLibraryPack = candidateLibraryPack;
                topScore = score;
            }
        }
        
        return bestCandidateLibraryPack;
    }
    
    private int getLibraryScoreForSequence(int daysLeft, LibraryInfo library, ArrayList<LibraryInfo> libraryList, ArrayList<Book> booksShipped, int sequenceNo, int previousScore)
    {
        var score = previousScore;
        
        if(sequenceNo == 0)
        {
            return score;
        }
        
        var pretendLibraryList = (ArrayList<LibraryInfo>)libraryList.clone();
        var pretendBooksShipped = (ArrayList<Book>)booksShipped.clone();
        
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
                if(!pretendBooksShipped.contains(book))
                {
                    score += book.Score;
                    pretendBooksShipped.add(book);
                }
                b++;
            }
            if(done)
            {
                break;
            }
        }        
        
        daysLeft -= (library.SignUpProcess-1);

        pretendLibraryList.remove(library);
        
        for(var lib : pretendLibraryList)
        {
            var daysLeftForThisLibrary = daysLeft - lib.SignUpProcess + 1;
            var lb = 0;
            var ldone = false;
            var noOfDays2 = 0;
            for(int d = 1; d <= daysLeftForThisLibrary; d++)
            {
                noOfDays2++;
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
            lib.NoOfDays = noOfDays2 + lib.SignUpProcess;
            lib.MaxScorePerDay = noOfDays2 == 0 ? 0 : (lib.MaxScore / lib.NoOfDays) * 100;
        }

        pretendLibraryList.sort((l1, l2) -> {
            return libraryInfoSorter.sort(l1, l2);              
        });  
        
        if(pretendLibraryList.size() > 0)
        {
            score = getLibraryScoreForSequence(daysLeft, pretendLibraryList.get(0), pretendLibraryList, pretendBooksShipped, sequenceNo-1, score);
        }
        
        return score;
    }    
}
