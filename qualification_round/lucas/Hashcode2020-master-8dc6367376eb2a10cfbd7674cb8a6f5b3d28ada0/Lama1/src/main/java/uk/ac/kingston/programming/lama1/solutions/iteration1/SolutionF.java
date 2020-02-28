/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions.iteration1;

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
import uk.ac.kingston.programming.lama1.model.LibraryInfo;
import uk.ac.kingston.programming.lama1.model.Solver;

/**
 *
 * @author Lama1
 */
public class SolutionF extends SolutionBase implements Solver, DataFileTransformer {
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public SolutionF()
    {
        super(App.Name + "_SolutionF");
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
        
        System.out.println(bookOrder.Books.size());
        
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
        
        System.out.println(libraryList.size());
        libraryList.sort((l1, l2) -> {
            if (l1.SignUpProcess.compareTo(l2.SignUpProcess) == 0) {
                return l2.MaxScore.compareTo(l1.MaxScore);
            } else {
                return l1.SignUpProcess.compareTo(l2.SignUpProcess);
            }             
        });
        
        var solversForSolution = new ArrayList<SolverForSolutionF>();
        for(int i = 0; i < 100000; i++)
        {
            solversForSolution.add(new SolverForSolutionF(dataFileIn, CreateDataFileOut(dataFileIn), bookOrder, (ArrayList<LibraryInfo>)libraryList.clone(), this));     
        }
       
       for(var solverForSolution : solversForSolution)
       {
           solverForSolution.start();
       }
       
       var total = 0;
       var previousTotal = 0;
       while(solutions.size() < solversForSolution.size() - 100)
       {
            try
            {
                total = solutions.size();
                if(total > 0)
                {
                    if(previousTotal == total)
                    {
                        break;
                    }
                    else
                    {
                        previousTotal = total;
                    }
                }
                TimeUnit.SECONDS.sleep(5);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
                System.out.println(ex.getMessage());
            }
       }
       
        try
        {
             TimeUnit.SECONDS.sleep(60);
        }
        catch(InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        }
        
       solutions.sort((s1, s2) -> {
           return s2.score.compareTo(s1.score);
       });

        for(var solution : solutions)
        {
            DataFileManager.save(solution, this);
        }
        return solutions.get(0);        
    }

    @Override
    public void OnComplete(DataFileOut dataFileOut) {
        solutions.add(dataFileOut);
    }
    
    @Override
    public void onSave(PrintWriter outputFile, DataFileOut dataFileOut) {
        System.out.println("Saving " + dataFileOut.getFilename());
        outputFile.println(dataFileOut.LibraryPacks.size());
        for(var libraryPack : dataFileOut.LibraryPacks)
        {
            outputFile.println(libraryPack.Id + " " + libraryPack.BooksToSend.size());
            var booksLine = "";
            for(int i = 0; i < libraryPack.BooksToSend.size(); i++)
            {
                var bookToSend = libraryPack.BooksToSend.get(i);
                booksLine = booksLine + bookToSend;
                if(i < libraryPack.BooksToSend.size()-1)
                {
                    booksLine = booksLine + " ";
                }
            }
            outputFile.println(booksLine);
        }
    }     

    @Override
    public void onLoad(BufferedReader bufferReader, DataFileIn dataFileIn) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
