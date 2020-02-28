/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.solutions;

import java.util.ArrayList;
import java.util.Objects;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.SolutionBase;
import uk.ac.kingston.programming.lama1.App;
import uk.ac.kingston.programming.lama1.logic.Permutations;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.BookOrder;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;

/**
 *
 * @author Lama1
 */
public class SolutionBigApe extends SolutionBase {
    
    private ArrayList<DataFileOut> solutions = new ArrayList<>();
    
    public SolutionBigApe()
    {
        super(App.Name + "_SoltutionBigApe");
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

            libraryList.add(libraryInfo);
            libraryId++;
        }
        
        while(!libraryList.isEmpty())
        {
            var candidateLibraries = new ArrayList<LibraryInfo>();
            
            for(int i = 0; i < 4; i++)
            {
                if(i == libraryList.size())                
                {
                    break;
                }
                candidateLibraries.add(libraryList.get(i));
            }
            
            var permutations = Permutations.getPermutations(candidateLibraries);
            
            for(var permutation : permutations)
            {
                System.out.println(permutation.size());
            }
            break;
//            for(var candidateLibrary : candidateLibraries)
//            {
//                libraryList.remove(candidateLibrary);
//            }
        }
        
        return dataFileOut;
    }
}
