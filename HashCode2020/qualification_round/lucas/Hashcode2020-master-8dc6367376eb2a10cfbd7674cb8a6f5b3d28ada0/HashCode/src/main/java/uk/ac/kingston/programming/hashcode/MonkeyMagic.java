package uk.ac.kingston.programming.hashcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import uk.ac.kingston.programming.hashcode.model.Solution;
import uk.ac.kingston.programming.hashcode.data.DataFileManager;
import uk.ac.kingston.programming.hashcode.model.Challenge;
import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;
import uk.ac.kingston.programming.hashcode.model.DataFileTransformer;
import uk.ac.kingston.programming.hashcode.model.Library;

/**
 *
 * @author lucas
 */
public class MonkeyMagic extends Thread implements DataFileTransformer {
    
    private DataFileIn dataFileIn;
    private Solution solution;
    
    private MonkeyMagic() {}
    
    public MonkeyMagic(DataFileIn dataFileIn, Solution solution)
    {
        this.dataFileIn = dataFileIn;
        this.solution = solution;
    }
    
    @Override
    public void run()
    {
       System.out.println("Solution " + solution.getName() + " is running on " + dataFileIn.filename);
       long t = System.currentTimeMillis()/1000;
       
        var dataFileOut = solution.solve(dataFileIn);
        
        save(dataFileOut);
        
        System.out.println("Solution " + solution.getName() + " took " + (System.currentTimeMillis()/1000 - t) + " on " + dataFileIn.filename);
    }
    
    public static void solve(ArrayList<Challenge> challengesList)
    {
        var challenges = new Challenge[challengesList.size()];
        for(int i = 0; i < challenges.length; i++)
        {
            challenges[i] = challengesList.get(i);
        }
        solve(challenges);
    }
    
    public static void solve(Challenge[] challenges)
    {
        var greatApe = new MonkeyMagic();
        
        for(var challenge : challenges)
        {
            var dataFileIn = DataFileManager.open(challenge.filenameIn, greatApe); 
            
            if (dataFileIn != null)
            {
                for(var solution : challenge.solutions)
                {
                    var monkeyMagic = new MonkeyMagic(dataFileIn, solution);
                    monkeyMagic.start();
                }                
            }
        }
    }
    
    public void save(DataFileOut dataFileOut)
    {
        if(dataFileOut != null)
        {
            DataFileManager.save(dataFileOut, this);
        }
    }
    
    @Override
    public void onLoad(BufferedReader bufferReader, DataFileIn dataFileIn) throws IOException {
        System.out.println("Opening " + dataFileIn.filename);
        var libraryDetails = bufferReader.readLine().split(" ");
        dataFileIn.NoBooks = Integer.parseInt(libraryDetails[0]);
        dataFileIn.NoLibraries = Integer.parseInt(libraryDetails[1]);
        dataFileIn.NoDaysForScanning = Integer.parseInt(libraryDetails[2]);
        var bookScores = bufferReader.readLine().split(" ");
        for(var bookScore : bookScores)
        {
            dataFileIn.BookScores.add(Integer.parseInt(bookScore));
        }
        
        var details = bufferReader.readLine();
        do
        {
            var detailsSplit = details.split(" ");
            var library = new Library();
            library.NoBooks = Integer.parseInt(detailsSplit[0]);
            library.SignUpProcess = Integer.parseInt(detailsSplit[1]);
            library.ShipPerDay = Integer.parseInt(detailsSplit[2]);
            var books = bufferReader.readLine().split(" ");
            for(var book : books)
            {
                library.Books.add(Integer.parseInt(book));
            }
            dataFileIn.Libraries.add(library);
            details = bufferReader.readLine();
        } while(details != null);        
//        System.out.println("-----------------------");
//        System.out.println(dataFileIn.filename);
//        System.out.println(dataFileIn.NoBooks);
//        System.out.println(dataFileIn.NoLibraries);
//        System.out.println(dataFileIn.NoDaysForScanning);
//        System.out.println(dataFileIn.Libraries.size());
//        System.out.println("-----------------------");
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
}
