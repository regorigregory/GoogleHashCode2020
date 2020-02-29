package uk.ac.kingston.programming.lama1.solutions;

import java.util.ArrayList;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.LibraryPack;
import uk.ac.kingston.programming.lama1.model.Book;
import uk.ac.kingston.programming.lama1.model.LibraryInfo;

/**
 *
 * @author lucas
 */
public class SolutionBigApeSolver {

    private DataFileIn dataFileIn;
    private DataFileOut dataFileOut;
    private ArrayList<LibraryInfo> libraryList;
    private int daysLeft;
    
    public SolutionBigApeSolver(DataFileIn dataFileIn, DataFileOut dataFileOut, ArrayList<LibraryInfo> libraryList, int daysLeft)
    {
        this.dataFileIn = dataFileIn;
        this.dataFileOut = dataFileOut;
        this.libraryList = libraryList;
        this.daysLeft = 0;
    }
    
    public void compute()
    {
        var libraryPacks = new ArrayList<LibraryPack>();
        
        var booksShipped = new ArrayList<Book>();
        
        while(daysLeft >= 0)
        {
            if(libraryList.isEmpty())
            {
                break;
            }
            
            var library = libraryList.get(0);
            var libraryPack = new LibraryPack();
            libraryPack.Id = library.Id;
            
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
                        libraryPack.BooksToSend.add(book.Id);
                        booksShipped.add(book);
                    }
                    b++;
                }
                if(done)
                {
                    break;
                }
            }
            
            if(libraryPack.BooksToSend.size() > 0)
            {
                daysLeft -= library.SignUpProcess;
                libraryPacks.add(libraryPack);                
            }
            
            libraryList.remove(library);
        }
       
        booksShipped.forEach((book) -> {
            dataFileOut.score += book.Score;
        });        
        
        dataFileOut.LibraryPacks = libraryPacks;        
    }
}
