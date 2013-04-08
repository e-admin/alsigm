
package ieci.tecdoc.sgm.base.datetime;

public class DatePattern 
{
   private DatePattern()
   {
   }
   
   public static final String XML_TIMESTAMP_PATTERN = "dd/MM/yy HH:mm:ss";
   public static final String SOAP_DATETIME_PATTERN = "yyyy-MM-ddTHH:mm:ssZ"; 
   public static final String YEAR4_DATETIME_PATTERN = "yyyy"; 
   public static final String XML_DATE_PATTERN = "yyyy/MM/dd";
   public static final String SPANISH_DATE_PATTERN = "dd/MM/yyyy";
   public static final String XML_HOUR_PATTERN = "HH:mm:ss";
   public static final String POSTGRES_DATE_PATTERN = "yyyy-MM-dd"; 
   public static final String POSTGRES_DATE_PATTERN_EX = "yyyy-MM-dd HH:mm:SS"; 
   
}