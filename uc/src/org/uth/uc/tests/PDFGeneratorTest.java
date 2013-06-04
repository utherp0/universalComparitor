package org.uth.uc.tests;

import java.io.IOException;
import java.util.Map;

import org.uth.uc.generators.AspectNames;
import org.uth.uc.generators.PDFGenerator;

public class PDFGeneratorTest
{
  public static void main( String[] args )
  {
    if( args.length != 2 )
    {
      System.out.println( "Usage: java PDFGeneratorTest pdfFile displayContents(true|false)" );
      System.exit(0);
    }
    
    new PDFGeneratorTest( args[0], "true".equals( args[1] ) );
  }
  
  public PDFGeneratorTest( String pdfFile, boolean showContents )
  {
    try
    {
      PDFGenerator generator = new PDFGenerator();
      
      generator.setDocument(pdfFile);
      
      long testStart = System.currentTimeMillis();
      Map<String,String> data = generator.generate();
      long testEnd = System.currentTimeMillis();
      
      System.out.println( "Processed " + pdfFile + " in " + ( testEnd - testStart ) + "ms." );
      
      for( String key : data.keySet() )
      {
        String value = data.get(key);
        
        if( !showContents && AspectNames.CONTENTS.equals( key ))
        {
          System.out.println( key + " : " + value.length() + " chars" );
        }
        else
        {
          System.out.println( key + " : " + value );
        }
      }
    }
    catch( IOException exc )
    {
      System.out.println( "Test failed due to " + exc.toString());
      exc.printStackTrace();
    }
  }
}
