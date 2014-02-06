
package ieci.tecdoc.core.textutil;

import java.util.Date;
import java.text.SimpleDateFormat;

public final class Util
{
   
   private Util()
   {    
   }

   public static String formatShortInteger(short data)
   {
      return Short.toString(data);
   }
   
   public static String formatLongInteger(int data)
   {
      return Integer.toString(data);
   }
   
   public static String formatShortDecimal(float data)
   {
      
      String str;
      
      str = Float.toString(data);
      
      str = str.replace('.', ',');
         
      return str;
      
   }
   
   public static String formatLongDecimal(double data)
   {
      
      String str;
      
      str = Double.toString(data);

      str = str.replace('.', ',');
         
      return str;
      
   }

   public String formatDate(Date data)
   {
      
      SimpleDateFormat sdf; 
      
      sdf = new SimpleDateFormat("dd/MM/yyyy");         
      
      return sdf.format(data);
      
   }

   public String formatDateTime(Date data)
   {
      
      SimpleDateFormat sdf; 
      
      sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");           
      
      return sdf.format(data);
      
   }
   
} // class
