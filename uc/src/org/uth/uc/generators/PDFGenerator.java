package org.uth.uc.generators;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.uth.uc.utils.DateFormatter;


public class PDFGenerator
{
  private PDDocument _currentDocument = null;
  private PDFTextStripper _stripper = null;
  
  public PDFGenerator() throws IOException
  {
    _stripper = new PDFTextStripper();
  }
  
  public void setDocument( String filePath ) throws IOException
  {
    File target = new File( filePath );
      
    _currentDocument = PDDocument.load(target);
  }
    
  public Map<String,String> generate() throws IOException
  {
    HashMap<String,String> _workingData = new HashMap<String,String>();
      
    if( _currentDocument == null )
    {
      return null;
    }
    
    String contents = _stripper.getText(_currentDocument );
      
    _workingData.put( AspectNames.CONTENTS, contents );
      
    // Deal with metadata
    PDDocumentInformation metadata = _currentDocument.getDocumentInformation();
      
    String author = metadata.getAuthor();
      
    if( author != null )
    {
      _workingData.put( AspectNames.AUTHOR, author );
    }
      
    String title = metadata.getTitle();
      
    if( title != null )
    {
      _workingData.put( AspectNames.DOCUMENT_TITLE, title );
    }
      
    Calendar modified = metadata.getModificationDate();
      
    if( modified != null ) 
    {
      String modifiedDate = DateFormatter.display(modified.getTime());
      
      _workingData.put(AspectNames.MODIFIED_DATE, modifiedDate );
    }
      
    Calendar creation = metadata.getCreationDate();
      
    if( creation != null )
    {
      String creationDate = DateFormatter.display(creation.getTime());
        
      _workingData.put(AspectNames.CREATION_DATE, creationDate );
    }
      
    return _workingData;      
  }  
}
