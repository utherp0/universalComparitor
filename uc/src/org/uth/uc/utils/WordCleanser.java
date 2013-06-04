package org.uth.uc.utils;

public class WordCleanser
{
  // Private constructor - force utils to be static
  private WordCleanser()
  {
    
  }
  
  public static String stripPunctuation( String input )
  {
    return input.replaceAll("[^a-zA-Z0-9]+", " ");  
  }
  
  public static String collapseSpacing( String input )
  {
    return input.replaceAll( "\\s+", " ");
  }
}
