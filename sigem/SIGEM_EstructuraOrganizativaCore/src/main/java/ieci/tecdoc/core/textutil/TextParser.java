
package ieci.tecdoc.core.textutil;

import java.util.Date;
import java.text.SimpleDateFormat;

public final class TextParser
{
   
   private char   m_decimalSeparator;
   private String m_datetimePattern;
   private String m_datePattern;
   private String m_timePattern;
   
   public TextParser()
   {
      m_decimalSeparator   = ',';   
      m_datetimePattern    = "dd-MM-yyyy HH:mm:ss";
      m_datePattern        = "dd-MM-yyyy"; 
      m_timePattern        = "HH:mm:ss";      
   }

   public void setDecimalSeparator(char sep)
   {
      m_decimalSeparator = sep;
   }
   
   public void setDateTimePattern(String pattern)
   {
      m_datetimePattern = pattern;
   }
   
   public void setDatePattern(String pattern)
   {
      m_datePattern = pattern;
   }
   
   public void setTimePattern(String pattern)
   {
      m_timePattern = pattern;
   }
   
   public short parseShortInteger(String str)
   {
      return Short.parseShort(str);
   }
   
   public int parseLongInteger(String str)
   {
      return Integer.parseInt(str);
   }
   
   public float parseShortDecimal(String str)
   {

      if (m_decimalSeparator != '.')
      {
         if (str.indexOf('.') != -1)
            throw new NumberFormatException("Input string: \"" + str + "\"");
         str = str.replace(m_decimalSeparator, '.');
      }
      
      return Float.parseFloat(str);
      
   }
   
   public double parseLongDecimal(String str)
   {

      if (m_decimalSeparator != '.')
      {
         if (str.indexOf('.') != -1)
            throw new NumberFormatException("Input string: \"" + str + "\"");
         str = str.replace(m_decimalSeparator, '.');
      }

      return Double.parseDouble(str);
  
   }
   
   public Date parseDateTime(String str, boolean onlyDate) throws Exception
   {
      
      SimpleDateFormat sdf; 

      if (onlyDate)
         sdf = new SimpleDateFormat(m_datePattern);
      else
         sdf = new SimpleDateFormat(m_datetimePattern);           
      
      sdf.setLenient(false);         
      
      return sdf.parse(str);
      
   }
   
   public Date parseTime(String str) throws Exception
   {
      
      SimpleDateFormat sdf; 

      sdf = new SimpleDateFormat(m_timePattern);
      
      sdf.setLenient(false);         
      
      return sdf.parse(str);
      
   }

   public String formatShortInteger(short data)
   {
      return Short.toString(data);
   }
   
   public String formatLongInteger(int data)
   {
      return Integer.toString(data);
   }
   
   public String formatShortDecimal(float data)
   {
      
      String str;
      
      str = Float.toString(data);
      
      if (m_decimalSeparator != '.')
         str = str.replace('.', m_decimalSeparator);
         
      return str;
      
   }
   
   public String formatLongDecimal(double data)
   {
      
      String str;
      
      str = Double.toString(data);
      
      if (m_decimalSeparator != '.')
         str = str.replace('.', m_decimalSeparator);
         
      return str;
      
   }

   public String formatDateTime(Date data, boolean onlyDate)
   {
      
      SimpleDateFormat sdf; 
      
      if (onlyDate)
         sdf = new SimpleDateFormat(m_datePattern);
      else
         sdf = new SimpleDateFormat(m_datetimePattern);           
      
      return sdf.format(data);
      
   }
   
   public String formatTime(Date data)
   {
      
      SimpleDateFormat sdf; 
      
      sdf = new SimpleDateFormat(m_timePattern);
           
      return sdf.format(data);
      
   }
   
} // class
