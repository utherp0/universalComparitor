package org.uth.uc.utils;

import java.text.DateFormat;
import java.util.Date;

public class DateFormatter
{
  private DateFormatter() {}
  
  public static String display( Date input )
  {
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    return dateFormat.format(input);
  }
}
