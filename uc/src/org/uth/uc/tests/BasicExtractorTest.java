package org.uth.uc.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import org.uth.uc.extractors.BasicExtractor;
import org.uth.uc.utils.DateFormatter;

public class BasicExtractorTest
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java BasicExtractorTest initialDataSet" );
      System.exit(0);
    }
    
    new BasicExtractorTest( args[0] );
  }
  
  private void log( String message )
  {
    Date now = new Date();
    
    String displayDate = DateFormatter.display(now);
    
    System.out.println( "[" + displayDate + "] " + message );
  }
  
  public BasicExtractorTest( String initialLoad )
  {
    // Initialise the extractor
    BasicExtractor extractor = new BasicExtractor();
    
    // Do the initial load
    int tokensAdded = extractor.addCoding( initialLoad );
    
    this.log( "Started - initial load " + tokensAdded + " tokens." );
    
    String command = "initialise";
    
    while( !"quit".equalsIgnoreCase( command ) )
    {    
      System.out.print( "Test Command: " );
    
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ));
  
      try
      {
        command = input.readLine();
        String[] commands = command.split( "[ ]" );
      
        switch( commands[0].toLowerCase() )
        {
          case "help" : 
            System.out.println( "Commands - help, quit, encodeAdd, encodeIgnore, list, listMirror,count,countmirror");
            break;
                        
          case "list" : 
            Map<Integer,String> decoder = extractor.getDecoder();
            
            for( Integer key : decoder.keySet())
            {
              String value = decoder.get(key);
              
              System.out.println( "  " + key.toString() + ":" + value );
            }
            System.out.println( "Decoder Count = " + (decoder.keySet()).size());
            break;
            
          case "listmirror" :
            Map<String,Integer> mirror = extractor.getMirror();
            
            for( String key : mirror.keySet())
            {
              Integer value = mirror.get(key);
              
              System.out.println( key + ":" + value.toString() );
            }
            System.out.println( "Mirror Count = " + (mirror.keySet()).size());
            break;
            
          case "encodeadd" :
            if( commands.length == 1 )
            {
              System.out.println( "You *must* provide an string to encode" );
            }
            
            String encoded = extractor.encode(command.substring(command.indexOf(" ")+1), true);
            System.out.println( "Encoded: " + encoded );
            break;
            
          case "encodeignore" :
            if( commands.length == 1 )
            {
              System.out.println( "You *must* provide an string to encode" );
            }
            
            String encodedignore = extractor.encode(command.substring(command.indexOf(" ")+1), false);
            System.out.println( "Encoded: " + encodedignore );
            break;
            
          case "count" :
            System.out.println( "Decoder token count :" + extractor.getDecoder().size());
            break;
            
          case "countmirror" :
            System.out.println( "Mirror token count :" + extractor.getMirror().size());
            break;
            
          case "quit" : System.exit(0);
                      
          default: System.out.println( "Unknown command - " + command );                
        }
      }
      catch( IOException exc )
      {
        this.log( "Exception occured - " + exc.getMessage() );   
      }
    }
  }
}
