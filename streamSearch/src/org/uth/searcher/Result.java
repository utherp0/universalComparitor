package org.uth.searcher;

public class Result
{
  private boolean _exactMatch = false;
  private int _tokenLength = 0;
  private int _tokenMatch = 0;
  private int _tokenContiguous = 0;
  private int _startPoint = 0;
  private byte[] _token = null;
    
  public Result( boolean exactMatch, int tokenLength, int startPoint, int tokenMatch, int tokenContiguous, byte[] token )
  {
    _exactMatch = exactMatch;
    _tokenLength = tokenLength;
    _startPoint = startPoint;
    _tokenMatch = tokenMatch;
    _tokenContiguous = tokenContiguous;
    _token = token;
  }
  
  public boolean isExactMatch() { return _exactMatch; }
  public int getTokenLength() { return _tokenLength; }
  public int getStartPoint() { return _startPoint; }
  public int getTokenMatch() { return _tokenMatch; }
  public int getTokenContiguous() { return _tokenContiguous; }
  public byte[] getToken() { return _token; }
}
