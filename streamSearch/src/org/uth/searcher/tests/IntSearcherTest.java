package org.uth.searcher.tests;

import java.util.List;

import org.uth.searcher.IntResult;
import org.uth.searcher.IntSearcher;

public class IntSearcherTest
{
  public static void main( String[] args )
  {
    if( args.length != 3 )
    {
      System.out.println( "Usage: java IntSearcherTest (terms,) content exact(true|false)");
      System.exit(0);
    }
    
    boolean exactMatch = ( "true".equalsIgnoreCase( args[2] ) );
    
    System.out.println( "Exact: " + exactMatch );
    
    new IntSearcherTest( args[0], args[1], exactMatch );
  }
  
  public IntSearcherTest( String terms, String content, boolean exactMatch )
  {
    IntSearcher searcher = new IntSearcher();
    
    String[] searchTerms = terms.split( "[,]" );
    
    for( String term : searchTerms )
    {
      searcher.addTerm(IntSearcherTest.convertTerm(term));
    }
    
    long searchStart = System.currentTimeMillis();
    List<IntResult> results = searcher.search(IntSearcherTest.convertTerm(content), exactMatch);
    long searchEnd = System.currentTimeMillis();
    
    System.out.println( "Search returned " + results.size() + " matches in " + ( searchEnd - searchStart) + "ms." );

    for( IntResult result : results )
    {
      String token = IntSearcherTest.generateToken(result.getToken());
      
      System.out.println( (result.isExactMatch() ? "[EXACT]":"[PARTIAL]") + " " + token + " Start:" + result.getStartPoint() + " Contig:" + result.getTokenContiguous() + " Match:" + result.getTokenMatch());
    }
  }
  
  private static String generateToken( int[] input )
  {
    StringBuffer output = null;
    
    for( int token : input )
    {
      if( output == null )
      {
        output = new StringBuffer( Integer.toString(token));
      }
      else
      {
        output.append( "." );
        output.append( Integer.toString(token));
      }
    }
    
    return output.toString();
  }
  
  private static int[] convertTerm( String inputTerm )
  {
    String[] components = inputTerm.split( "[.]" );
    
    int[] output = new int[components.length];
    
    int counter = 0;
    
    for( String component : components )
    {
      output[counter] = Integer.parseInt( component );
      counter++;
    }
    
    return output;
  }
}
