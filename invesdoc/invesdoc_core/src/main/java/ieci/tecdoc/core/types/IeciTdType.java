
package ieci.tecdoc.core.types;

import java.util.Date;

public final class IeciTdType
{
   public static final int SHORT_TEXT    = 1;   
   public static final int LONG_TEXT     = 2;   
   public static final int SHORT_INTEGER = 3;   
   public static final int LONG_INTEGER  = 4;   
   public static final int SHORT_DECIMAL = 5;
   public static final int LONG_DECIMAL  = 6;
   public static final int DATE_TIME     = 7;
   public static final int LONG_BIN      = 8;
   
   public static final String NULL_SHORT_TEXT    = null;   
   public static final String NULL_LONG_TEXT     = null;   
   public static final short  NULL_SHORT_INTEGER = Short.MIN_VALUE;
   public static final int    NULL_LONG_INTEGER  = Integer.MIN_VALUE;
   public static final float  NULL_SHORT_DECIMAL = Float.MIN_VALUE;
   public static final double NULL_LONG_DECIMAL  = Double.MIN_VALUE;
   public static final Date   NULL_DATE_TIME     = null;
   
   public static final String NULL_GUID = "IECI_TECDOC_NULL_GUID";
   
   private IeciTdType()
   {
   }
 
   public static boolean isNullShortText(String value)
   {
      return (value == NULL_SHORT_TEXT);
   }
   
   public static boolean isNullLongText(String value)
   {
      return (value == NULL_LONG_TEXT);
   }
   
   public static boolean isNullShortInteger(short value)
   {
      return (value == NULL_SHORT_INTEGER);
   }
   
   public static boolean isNullLongInteger(int value)
   {
      return (value == NULL_LONG_INTEGER);
   }
   
   public static boolean isNullShortDecimal(float value)
   {
      return (value == NULL_SHORT_DECIMAL);
   }
   
   public static boolean isNullLongDecimal(double value)
   {
      return (value == NULL_LONG_DECIMAL);
   }
   
   public static boolean isNullDateTime(Date value)
   {
      return (value == NULL_DATE_TIME);
   }
   
} // class
