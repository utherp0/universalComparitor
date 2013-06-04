package org.uth.searcher.tests;

import java.util.List;

import org.uth.searcher.ByteSearcher;
import org.uth.searcher.Result;

public class ByteSearcherTest
{
  public static void main( String[] args )
  {
    if( args.length != 3 )
    {
      System.out.println( "Usage: java ByteSearcherTest (terms,) content exact(true|false)");
      System.exit(0);
    }
    
    boolean exactMatch = ( "true".equalsIgnoreCase( args[2] ) );
    
    System.out.println( "Exact: " + exactMatch );
    
    new ByteSearcherTest( args[0], args[1], exactMatch );
  }
  
  public ByteSearcherTest( String terms, String content, boolean exactMatch )
  {
    ByteSearcher searcher = new ByteSearcher();
    
    String[] searchTerms = terms.split( "[,]" );
    
    for( String term : searchTerms )
    {
      searcher.addTerm(term.getBytes());
    }
    
    long searchStart = System.currentTimeMillis();
    List<Result> results = searcher.search(content.getBytes(), exactMatch);
    long searchEnd = System.currentTimeMillis();
    
    System.out.println( "Search returned " + results.size() + " matches in " + ( searchEnd - searchStart) + "ms." );

    for( Result result : results )
    {
      String token = new String( result.getToken() );
      
      System.out.println( (result.isExactMatch() ? "[EXACT]":"[PARTIAL]") + " " + token + " Start:" + result.getStartPoint() + " Contig:" + result.getTokenContiguous() + " Match:" + result.getTokenMatch());
    }
  }
}
