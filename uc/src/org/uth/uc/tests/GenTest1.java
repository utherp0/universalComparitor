package org.uth.uc.tests;

import org.uth.uc.utils.WordCleanser;

public class GenTest1
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java GenTest1 string" );
      System.exit(0);
    }
    
    new GenTest1( args[0] );
  }

  public GenTest1( String input )
  {
    String working = input;
    
    working = WordCleanser.stripPunctuation(working);
    
    System.out.println( "Punc removed: (" + working + ")");
    
    working = WordCleanser.collapseSpacing(working);
    
    System.out.println( "Collapsed Spacing: (" + working + ")");
  }
}
