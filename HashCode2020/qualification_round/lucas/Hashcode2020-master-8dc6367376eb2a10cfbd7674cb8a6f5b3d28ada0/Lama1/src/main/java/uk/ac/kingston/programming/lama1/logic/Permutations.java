package uk.ac.kingston.programming.lama1.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author lucas
 */
public class Permutations 
{       
    public static <E> List<List<E>> getPermutations(List<E> original) 
    {        
        if (original.isEmpty()) {
          List<List<E>> result = new ArrayList<>(); 
          result.add(new ArrayList<>()); 
          return result; 
        }
        
        E firstElement = original.remove(0);
        
        List<List<E>> returnValue = new ArrayList<>();
        List<List<E>> permutations = getPermutations(original);
        
        for (List<E> smallerPermutated : permutations) 
        {
          for (int index=0; index <= smallerPermutated.size(); index++) 
          {
            List<E> temp = new ArrayList<>(smallerPermutated);
            temp.add(index, firstElement);
            returnValue.add(temp);
          }
        }
        
        return returnValue;
   }
}
