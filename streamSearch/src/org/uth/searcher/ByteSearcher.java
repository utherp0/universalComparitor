package org.uth.searcher;

import java.util.ArrayList;
import java.util.List;

public class ByteSearcher
{
  public ByteSearcher()
  {
    
  }
  
  private List<byte[]> _terms = new ArrayList<byte[]>();
  
  public void clearTerms()
  {
    _terms = new ArrayList<byte[]>();
  }
  
  public void addTerm( byte[] term )
  {
    _terms.add(term);
  }
  
  public List<Result> search( byte[] contents, boolean exactOnly )
  {
    List<Result> matched = new ArrayList<Result>();
    
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
      for( byte[] term : _terms )
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
          
          Result result = null;
          
          if( exactOnly && exactMatch )
          {
            result = new Result( exactMatch, term.length, startPoint, matchCount, (contiguous > maxContiguous ? contiguous : maxContiguous), term );
          }
          else if( !exactOnly )
          {
            result = new Result( exactMatch, term.length, startPoint, matchCount, (contiguous > maxContiguous ? contiguous : maxContiguous), term );
          }
          
          if( result != null ) matched.add(result);
        }
      }
    }
    
    return matched;
  }
}
