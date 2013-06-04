package org.uth.searcher;

import java.util.ArrayList;
import java.util.List;

public class IntSearcher
{
  public IntSearcher()
  {
    
  }
  
  private List<int[]> _terms = new ArrayList<int[]>();
  
  public void clearTerms()
  {
    _terms = new ArrayList<int[]>();
  }
  
  public void addTerm( int[] term )
  {
    _terms.add(term);
  }
  
  public List<IntResult> search( int[] contents, boolean exactOnly )
  {
    List<IntResult> matched = new ArrayList<IntResult>();
    
    if( !( exactOnly ) )
    {
      // Start case - overlap tokens to the first (x) bytes for partials
    }
    
    if( _terms.size() == 0 )
    {
      return matched;
    }
    
    for( int startPoint = 0; startPoint < contents.length; startPoint++ )
    {
      for( int[] term : _terms )
      {
        int matchCount = 0;
        int maxContiguous = 0;
        int contiguous = 0;
        
        for( int termPoint = 0; termPoint < term.length; termPoint++ )
        {
          if( startPoint + termPoint < contents.length )
          {
            if( term[termPoint] == contents[startPoint + termPoint])
            {
              contiguous++;
              matchCount++;
            }
            else
            {
              if(contiguous>maxContiguous)
              {
                maxContiguous = contiguous;
                contiguous = 0;
              }
            }
          }
        }
        
        if( matchCount != 0 )
        {
          boolean exactMatch = (matchCount == term.length);
          
          IntResult result = null;
          
          if( exactOnly && exactMatch )
          {
            result = new IntResult( exactMatch, term.length, startPoint, matchCount, (contiguous > maxContiguous ? contiguous : maxContiguous), term );
          }
          else if( !exactOnly )
          {
            result = new IntResult( exactMatch, term.length, startPoint, matchCount, (contiguous > maxContiguous ? contiguous : maxContiguous), term );
          }
          
          if( result != null ) matched.add(result);
        }
      }
    }
    
    return matched;
  }
}
