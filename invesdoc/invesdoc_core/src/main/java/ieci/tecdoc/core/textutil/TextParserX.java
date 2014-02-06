
package ieci.tecdoc.core.textutil;

import java.util.Date;
import java.text.SimpleDateFormat;
import ieci.tecdoc.core.types.IeciTdType;

/** Permite convertir un string a un tipo de base de datos y viceversa. */
   
public final class TextParserX
{
   
   private String m_nullTextString;  
   private String m_nullNumberString;
   private String m_nullDateTimeString;
   private char   m_decimalSeparator;
   private String m_datetimePattern;
   private String m_datePattern;
   private String m_timePattern;
   
   /** Construye un objeto de la clase. */      
   
   public TextParserX()
   {
      m_nullTextString     = "";
      m_nullNumberString  = "";     
      m_nullDateTimeString = "";
      m_decimalSeparator   = ',';   
      m_datetimePattern    = "dd-MM-yyyy HH:mm:ss";
      m_datePattern        = "dd-MM-yyyy"; 
      m_timePattern        = "HH:mm:ss";      
   }
   
   /** Establece el string que representa un texto nulo.
   * @param str El string mencionado. */
   
   public void setNullTextString(String str)
   {
      m_nullTextString = str;
   }
   
   /** Establece el string que representa un entero nulo.
   * @param str El string mencionado. */
   
   public void setNullNumberString(String str)
   {
      m_nullNumberString = str;
   }
   
   /** Establece el string que representa una fecha/hora nula.
   * @param str El string mencionado. */
   
   public void setNullDateTimeString(String str)
   {
      m_nullDateTimeString = str;
   }
   
   /** Establece el separador de decimales.
   * @param sep El separador mencionado. */
   
   public void setDecimalSeparator(char sep)
   {
      m_decimalSeparator = sep;
   }
   
   /** Establece el patrón de fecha/hora.
   * @param pattern El patrón mencionado. */
   
   public void setDateTimePattern(String pattern)
   {
      m_datetimePattern = pattern;
   }
   
   /** Establece el patrón de fecha.
   * @param pattern El patrón mencionado. */
   
   public void setDatePattern(String pattern)
   {
      m_datePattern = pattern;
   }
   
   public void setTimePattern(String pattern)
   {
      m_timePattern = pattern;
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * @return El resultado de la conversión. */
   
   public String parseShortText(String str)
   {
      if (str.equals(m_nullTextString))
         return IeciTdType.NULL_SHORT_TEXT;
      else
         return str;
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * @return El resultado de la conversión. */
   
   public String parseLongText(String str)
   {
      if (str.equals(m_nullTextString))
         return IeciTdType.NULL_LONG_TEXT;
      else
         return str;
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * <br> Dígitos y un eventual signo menos al principio.
   * @return El resultado de la conversión. */
   
   public short parseShortInteger(String str)
   {
      if (str.equals(m_nullNumberString))
         return IeciTdType.NULL_SHORT_INTEGER;
      else
         return Short.parseShort(str);
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * <br> Dígitos y un eventual signo menos al principio.
   * @return El resultado de la conversión. */
   
   public int parseLongInteger(String str)
   {
      if (str.equals(m_nullNumberString))
         return IeciTdType.NULL_LONG_INTEGER;
      else
         return Integer.parseInt(str);
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * <br> Dígitos, un eventual separador de decimales y un eventual signo (+/-)
   * al principio.
   * @return El resultado de la conversión. */
   
   public float parseShortDecimal(String str)
   {
      if (str.equals(m_nullNumberString))
         return IeciTdType.NULL_SHORT_DECIMAL;
      else
      {
         if (m_decimalSeparator != '.')
         {
            if (str.indexOf('.') != -1)
               throw new NumberFormatException("Input string: \"" + str + "\"");
            str = str.replace(m_decimalSeparator, '.');
         }
         return Float.parseFloat(str);
      }
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * <br> Dígitos, un eventual separador de decimales y un eventual signo (+/-)
   * al principio.
   * @return El resultado de la conversión. */
   
   public double parseLongDecimal(String str)
   {
      if (str.equals(m_nullNumberString))
         return IeciTdType.NULL_LONG_DECIMAL;
      else
      {
         if (m_decimalSeparator != '.')
         {
            if (str.indexOf('.') != -1)
               throw new NumberFormatException("Input string: \"" + str + "\"");
            str = str.replace(m_decimalSeparator, '.');
         }
         return Double.parseDouble(str);
      }      
   }
   
   /** Convierte un string al tipo especificado por el nombre del método.
   * @param str El string a convertir.
   * @param onlyDate Especifica si el string sólo contiene una fecha.
   * @return El resultado de la conversión.
   * @throws Exception si se produce algún error. */ 
   
   public Date parseDateTime(String str, boolean onlyDate) throws Exception
   {
      
      SimpleDateFormat sdf; 
      
      if (str.equals(m_nullDateTimeString))
         return IeciTdType.NULL_DATE_TIME;
      else
      {
         if (onlyDate)
            sdf = new SimpleDateFormat(m_datePattern);
         else
            sdf = new SimpleDateFormat(m_datetimePattern);           
         sdf.setLenient(false);         
         return sdf.parse(str);
      }
      
   }
   
   public Date parseTime(String str) throws Exception
   {
      
      SimpleDateFormat sdf; 

      sdf = new SimpleDateFormat(m_timePattern);
      
      sdf.setLenient(false);         
      
      return sdf.parse(str);
      
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatShortText(String data)
   {
      if (IeciTdType.isNullShortText(data))
         return m_nullTextString;
      else
         return data;
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatLongText(String data)
   {
      if (IeciTdType.isNullLongText(data))
         return m_nullTextString;
      else
         return data;
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatShortInteger(short data)
   {
      if (IeciTdType.isNullShortInteger(data))
         return m_nullNumberString;
      else
         return Short.toString(data);
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatLongInteger(int data)
   {
      if (IeciTdType.isNullLongInteger(data))
         return m_nullNumberString;
      else
         return Integer.toString(data);
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatShortDecimal(float data)
   {
      
      String str;
      
      if (IeciTdType.isNullShortDecimal(data))
         return m_nullNumberString;
      else
      {
         str = Float.toString(data);
         if (m_decimalSeparator != '.')
            str = str.replace('.', m_decimalSeparator);
         return str;
      }
      
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @return El resultado de la conversión. */
   
   public String formatLongDecimal(double data)
   {
      
      String str;
      
      if (IeciTdType.isNullLongDecimal(data))
         return m_nullNumberString;
      else
      {
         str = Double.toString(data);
         if (m_decimalSeparator != '.')
            str = str.replace('.', m_decimalSeparator);
         return str;
      }
      
   }
   
   /** Convierte a string el tipo especificado por el nombre del método.
   * @param data El dato a convertir.
   * @param onlyDate Especifica si se formatea sólo la fecha.
   * @return El resultado de la conversión. */
   
   public String formatDateTime(Date data, boolean onlyDate)
   {
      
      SimpleDateFormat sdf; 
      
      if (IeciTdType.isNullDateTime(data))
         return m_nullDateTimeString;
      else
      {
         if (onlyDate)
            sdf = new SimpleDateFormat(m_datePattern);
         else
            sdf = new SimpleDateFormat(m_datetimePattern);           
         return sdf.format(data);
      }
      
   }
   
   public String formatTime(Date data)
   {
      
      SimpleDateFormat sdf; 
      
      sdf = new SimpleDateFormat(m_timePattern);
           
      return sdf.format(data);
      
   }
   
} // class
