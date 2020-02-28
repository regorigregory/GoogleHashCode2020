/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
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
public class Solution7 extends SolutionBase implements Solver {
    
    private Integer score = 0;
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public Solution7()
    {
        super(App.Name + "_Solution7");
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
                        libraryInfo.AvgScore = libraryInfo.MaxScore / libraryInfo.Books.size();
                        break;
                    }                
                }
            }
            
            libraryList.add(libraryInfo);
            libraryId++;
        }
        
        var libraryList2 = new ArrayList<LibraryInfo>();
        
        libraryList.sort((l1, l2) -> {
            return l2.MaxScore.compareTo(l1.MaxScore);
        });        
        
        for(var library : libraryList)
        {
            if(library.MaxScore >= 50000)
            {
                libraryList2.add(library);
            }
        }
        
        for(var library : libraryList2)
        {
            libraryList.remove(library);
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
        
        for(var library : libraryList)
        {
            libraryList2.add(library);
        }
        
        for(var library : libraryList2)
        {
            System.out.println(library.Id + " - " + library.ShipPerDay + "/" + library.SignUpProcess + "/" + library.MaxScore);    
        }
        
        var solversForSolution = new ArrayList<SolverForSolution7>();
        for(int i = 0; i < 1; i++)
        {
            solversForSolution.add(new SolverForSolution7(dataFileIn, dataFileOut, getRandomLibraryList(libraryList2, i), this));     
        }
       
       for(var solverForSolution : solversForSolution)
       {
           solverForSolution.start();
       }
       
       while(solutions.size() != solversForSolution.size())
       {
            try
            {
                System.out.println("Solutions " + solutions.size() / solversForSolution.size());
                TimeUnit.SECONDS.sleep(1);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
                System.out.println(ex.getMessage());
            }
       }
       
       solutions.sort((s1, s2) -> {
           return s2.score.compareTo(s1.score);
       });
       
       return solutions.get(0);
    }

    @Override
    public void OnComplete(DataFileOut dataFileOut) {
        solutions.add(dataFileOut);
    }
    
    public ArrayList<LibraryInfo> getRandomLibraryList(ArrayList<LibraryInfo> masterLibraryList, Integer offset)
    {
        var randomLibraryList = new ArrayList<LibraryInfo>();
        
        for(int i = 0; i < masterLibraryList.size()-offset; i++)
        {
            var l = i - offset;
            if(l < 0)
            {
                l = (masterLibraryList.size()) - offset;
            }
            randomLibraryList.add(masterLibraryList.get(l));
        }
        return randomLibraryList;
    }
}
