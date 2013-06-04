package org.uth.uc.extractors;

import java.util.HashMap;
import java.util.Map;

import org.uth.uc.utils.WordCleanser;

/**
 * An example extractor showing the basic approach for storing tokens/numbers
 * and encoding given strings using this decoder.
 * @author Ian Lawson <a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>
 *
 */
public class BasicExtractor
{
  public final static String STANDARD_DIVISOR = ".";
  public final static Integer UNKNOWN = new Integer(0);
  
  private String _divisor = STANDARD_DIVISOR;
  private Map<Integer,String> _decoder = null;
  private Map<String,Integer> _mirror = null;
  private int _endKey = 0;
  
  public BasicExtractor()
  {
    _decoder = new HashMap<Integer,String>();
    _mirror = new HashMap<String,Integer>();
  }
  
  /**
   * Mutator for the divisor. The divisor is the delimiter of the encoded tokens
   * in a returned encoding.
   * @param divisor divisor to store in the extractor
   */
  public void setDivisor( String divisor )
  {
    _divisor = divisor;
  }
  
  /**
   * This method allows for an offlined decoder to be restored into the extractor.
   * Note that a refresh of the mirror is called automatically when the stored decoder
   * is restored.
   * @param storedDecoder stored decoder.
   */
  public void restore( Map<Integer,String> storedDecoder )
  {
    _decoder = storedDecoder;
    this.refreshMirror();
  }
  
  /**
   * For two way lookup we store a mirrored version of the decoder. This method
   * refreshes the mirror using the current stored decoder.
   */
  public void refreshMirror()
  {
    _mirror = new HashMap<String,Integer>();
    
    for( Integer key : _decoder.keySet() )
    {
      String value = _decoder.get(key);
      
      _mirror.put(value, key);
    }
  }
  
  public Map<Integer,String> getDecoder()
  {
    return _decoder;
  }
  
  public Map<String,Integer> getMirror()
  {
    return _mirror;
  }
  
  public int addCoding( String input )
  {
    int localCount = 0;
    
    // Clean the string
    input = WordCleanser.stripPunctuation(input);
    input = WordCleanser.collapseSpacing(input);
    
    // Now do a word by word extraction and insertion based on decoder rules
    String tokens[] = input.toLowerCase().split( "[ ]" );
    
    for( String token : tokens )
    {
      if( !( _decoder.containsKey(token)))
      {
        _endKey++;
        _decoder.put( new Integer( _endKey ), token );
        
        localCount++;
      }
    }
    
    // Refresh the mirror cache
    this.refreshMirror();
    
    return localCount;
  }
  
  /**
   * Encode method. This takes a given input, converts all tokens into the 
   * numerical values provided by the decoder, and, driven by a boolean, either
   * sets unknown tokens to '0' *or* adds tokens from the input to the decoder
   * if they do not exist.
   * @param input tokens to encode
   * @param addTokens whether to add unknown tokens or replace with 0
   * @return encoded string
   */
  public String encode( String input, boolean addTokens )
  {
    StringBuffer output = null;
    
    // Clean the input
    input = WordCleanser.stripPunctuation(input);
    input = WordCleanser.stripPunctuation(input);
    
    String[] tokens = input.toLowerCase().split( "[ ]" );
    
    for( String token : tokens )
    {
      Integer addition = null;
      
      if( !_decoder.containsValue(token) && addTokens )
      {
        _endKey++;
        _decoder.put( new Integer(_endKey), token);
        
        this.refreshMirror();
        
        addition = new Integer( _endKey );
      }
      else if( !_decoder.containsValue(token))
      {
        addition = UNKNOWN;
      }
      else
      {
        addition = _mirror.get(token);
      }
      
      if( output == null )
      {
        output = new StringBuffer( addition.toString());
      }
      else
      {
        output.append( _divisor );
        output.append( addition.toString());
      }
    }
    
    return ( output == null ? null : output.toString() );
  }
}
