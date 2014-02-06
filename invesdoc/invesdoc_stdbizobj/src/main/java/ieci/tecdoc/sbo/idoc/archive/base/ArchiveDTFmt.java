
package ieci.tecdoc.sbo.idoc.archive.base;

/**
 * <p>
 * Esta clase encapsula el patrón del formato de fecha y hora. La 
 * información de formato de un campo es una cadena con la siguiente
 * estructura:
 * </p>
 * <tt>x,y,"a","b","c",i,k</tt>
 * <p>
 * Donde:
 * </p>
 * <p>
 * <li>x es un identificador del formato de campo</li>
 * <li>y es el tipo de fecha</li>
 * <li>a es el carácter separador para la fecha</li>
 * <li>b es el carácter separador para la hora</li>
 * <li>c es el carácter separador para la fecha y la hora</li>
 * <li>i es un identificador del formato de fecha</li>
 * <li>k es un identificador del formato de hora</li>
 * </p>
 * @author 
 *
 */
public final class ArchiveDTFmt
{
   /** Formato de fecha con dia y mes simple */
   private final int  DATE_FMT_DMYYYY   = 1;
   private final int  DATE_FMT_DDMMYYYY = 2;
   private final int  TIME_FMT_HMS      = 1;
   private final int  TIME_FMT_HHMMSS   = 2;
      
   private String  m_dateSep;
   private String  m_timeSep;
   private String  m_dateTimeSep;
   private int     m_dateFmt;  
   private int     m_timeFmt;
      
   public ArchiveDTFmt()
   {
      m_dateSep     = null;     
      m_timeSep     = null;
      m_dateTimeSep = null;
      m_dateFmt     = 0;
      m_timeFmt     = 0;  
   }
   
   public ArchiveDTFmt(String dateSep, String timeSep, String dateTimeSep, 
                       int dateFmt, int timeFmt)
   {
      m_dateSep     = dateSep;     
      m_timeSep     = timeSep;
      m_dateTimeSep = dateTimeSep;
      m_dateFmt     = dateFmt;
      m_timeFmt     = timeFmt;     
   }
   
   public String getDatePattern()
   {
      StringBuffer  tbdr; 
      String        dayPattern, monthPattern; 
      
      tbdr = new StringBuffer();
            
      if (m_dateFmt == DATE_FMT_DMYYYY)
      {
         dayPattern   = "d";
         monthPattern = "M";
      }
      else
      {
         dayPattern   = "dd";
         monthPattern = "MM";
      }
      
      tbdr.append(dayPattern).append(m_dateSep);
      tbdr.append(monthPattern).append(m_dateSep);
      tbdr.append("yyyy");
      
      return tbdr.toString();
   }
   
   public String getTimePattern()
   {
      StringBuffer  tbdr; 
      String        hourPattern, minutePattern, secondPattern; 
      
      tbdr = new StringBuffer();
       
      if (m_timeFmt == TIME_FMT_HMS)
      {
         hourPattern   = "H";
         minutePattern = "m";
         secondPattern = "s";
      }
      else
      {
         hourPattern   = "HH";
         minutePattern = "mm";
         secondPattern = "ss";
      }
      
      tbdr.append(hourPattern).append(m_timeSep);
      tbdr.append(minutePattern).append(m_timeSep);
      tbdr.append(secondPattern);
      
      return tbdr.toString();
   }
   
   public String getDateTimePattern()
   {
      StringBuffer  tbdr;      
      
      tbdr = new StringBuffer();       
      
      
      tbdr.append(getDatePattern()).append(m_dateTimeSep);
      tbdr.append(getTimePattern());
            
      return tbdr.toString();
   }
   
} // class
