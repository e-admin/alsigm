package ieci.tecdoc.sbo.idoc.archive.base;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.textutil.UtilX;
import java.text.StringCharacterIterator;
import ieci.tecdoc.core.textutil.TextParserX;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoRelFlds;
import java.util.Calendar;
import java.util.Date;

/**
 * Clse que recoge algunos métodos generales de utilidades dentro
 * del ámbito de los archivadors
 * @author
 */

public final class ArchiveUtil
{   
   private static final String DEFAULT_DATE_PATTERN      = "dd-MM-yyyy";
   private static final String DEFAULT_DATE_TO_FLD_TIME  = "30-12-1899";
   private static final int    DEFAULT_YEAR_TO_FLD_TIME  = 1899;
   private static final int    DEFAULT_MONTH_TO_FLD_TIME = 12;
   private static final int    DEFAULT_DAY_TO_FLD_TIME   = 30;
   
   /**
    * Constructor
    */
   private ArchiveUtil()
   {
   }
   
   /**
    * Devuelve el nombre de la tabla multivalor para un determinado campo de un archivador
    * según el tipo de este. El nombre de la tabla esta compuesto por un prefijo + un sufijo.
    * El prefijo es 'A' + idArch. El sufijo se calcula dependiendo del tipo de campo y puede ser
    * MFS, MFI, MFD o MFDT.  
    * @param tblPrefix prefijo de la tabla ('A' + idArch)
    * @param fldType tipo de campo
    * @return devuelve el nombre de la tabla
    * @throws Exception si no existe tipo de campo
    */
   public static String getMultFldTblName(String tblPrefix, int fldType)
                        throws Exception
   {  
      int    fldDbType = ArchiveFldType.getDbDataType(fldType);
      String tblName;
            
      tblName = DaoUtil.getMultFldTblName(tblPrefix, fldDbType);
      
      return tblName;

   }
   
   /**
    * Crea un formateador de fechas a partir de una información de formateo de un campo
    * @param fldFmtInfo información de formateo de un campo
    * @return referencia a un objeto de tipo ArchiveDTFmt que es un formateador de fechas
    * @throws Exception
    * @see ArchiveDTFmt
    */
   public static ArchiveDTFmt createDTFmt(String fldFmtInfo)
                              throws Exception
   { 
      ArchiveDTFmt            dtFmt = null; 
      char                    sep = ','; 
      int                     fmtType, dtType;
      String                  dateSep, timeSep, dateTimeSep;
      int                     dateFmt, timeFmt; 
      StringCharacterIterator iterator  = new StringCharacterIterator(fldFmtInfo);    
      
      fmtType = UtilX.parseInteger(iterator, sep); 
      
      if (fmtType != ArchiveBaseDefs.FLD_FMT_TYPE_DT)
      {
         throw new IeciTdException(ArchiveBaseError.EC_INVALID_PARAM, 
                                   ArchiveBaseError.EM_INVALID_PARAM); 
      }    
      
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      dtType = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      dateSep = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      timeSep = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      dateTimeSep = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      dateFmt = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      timeFmt = UtilX.parseInteger(iterator, sep);      
      
      dtFmt = new ArchiveDTFmt(dateSep, timeSep,dateTimeSep, 
                               dateFmt, timeFmt);
       
      return dtFmt;
   }
   
   
   /**
    * Crea un formateador de números a partir de una información de formateo de un campo
    * @param fldFmtInfo información de formateo de un campo
    * @return referencia a un objeto de tipo ArchiveNumFmt que es un formateador de fechas
    * @throws Exception
    * @see ArchiveNumFmt
    */   
   public static ArchiveNumFmt createNumFmt(String fldFmtInfo)
                               throws Exception
   { 
      ArchiveNumFmt           numFmt = null; 
      char                    sep = ','; 
      int                     fmtType, numType;
      String                  decimalSep, thousandSep;
      int                     numDecDigits; 
      StringCharacterIterator iterator  = new StringCharacterIterator(fldFmtInfo);    
      
      fmtType = UtilX.parseInteger(iterator, sep); 
      
      if (fmtType != ArchiveBaseDefs.FLD_FMT_TYPE_NUM)
      {
         throw new IeciTdException(ArchiveBaseError.EC_INVALID_PARAM, 
                                   ArchiveBaseError.EM_INVALID_PARAM); 
      }    
      
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      numType = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      thousandSep = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      decimalSep = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      numDecDigits = UtilX.parseInteger(iterator, sep);      
      
      numFmt = new ArchiveNumFmt(thousandSep, decimalSep, numDecDigits);
       
      return numFmt;
   }
   
   /**
    * 
    * @param fldFmtInfo
    * @param fldType
    * @return
    * @throws Exception
    * @see TextParserX
    */
   public static TextParserX createParser(String fldFmtInfo, int fldType)
                             throws Exception
   { 
      TextParserX    parser = new TextParserX();     
      int            fmtType; 
      ArchiveDTFmt   dtFmt  = null;
      ArchiveNumFmt  numFmt = null; 
      char           decSep;
      String         dateTimePattern;
      
      if (fldFmtInfo == null)
      {
         return parser;
      }
           
      if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
           (fldType == ArchiveFldType.LONG_INTEGER)  ||
           (fldType == ArchiveFldType.SHORT_DECIMAL) ||
           (fldType == ArchiveFldType.LONG_DECIMAL) )
      {
         numFmt = ArchiveUtil.createNumFmt(fldFmtInfo);
         
         if ((fldType == ArchiveFldType.SHORT_DECIMAL) ||
             (fldType == ArchiveFldType.LONG_DECIMAL) )
         {
            decSep = numFmt.getDecimalSeparator().charAt(0);
            parser.setDecimalSeparator(decSep);
         }
         
      }
      else if ( (fldType == ArchiveFldType.DATE) ||
                (fldType == ArchiveFldType.DATE_TIME) ||
                (fldType == ArchiveFldType.TIME) )
      {  
         dtFmt =  ArchiveUtil.createDTFmt(fldFmtInfo);
         
         if (fldType == ArchiveFldType.DATE)
            parser.setDatePattern(dtFmt.getDatePattern());
         else if (fldType == ArchiveFldType.TIME)
         {
            dateTimePattern = DEFAULT_DATE_PATTERN + " " + 
                              dtFmt.getTimePattern();
            parser.setDateTimePattern(dateTimePattern);
         }
         else
            parser.setDateTimePattern(dtFmt.getDateTimePattern());        
      } 
           
      return parser;

   }

   public static String convertTimeStrToDateTimeStr(String timeStr)
                        throws Exception
   {   
      String dtStr;
    
      dtStr = DEFAULT_DATE_TO_FLD_TIME + " " + timeStr;
      
      return dtStr;

   }

   public static Date convertTimeToDateTime(Date time) throws Exception
   {   
      Date     dt; 
      Calendar c;
      int      month;
		
		c = Calendar.getInstance();
		
		c.setTime(time);

      month = DEFAULT_MONTH_TO_FLD_TIME - 1; //Para el calendario, Enero es el mes 0
      c.set(DEFAULT_YEAR_TO_FLD_TIME, month, DEFAULT_DAY_TO_FLD_TIME);	
    
      dt = c.getTime();

      return dt;

   }
   
   public static DbColumnDef[] getRelFldColsDef(ArchiveTokenFlds relFlds)
                               throws Exception
   {  
      DbColumnDef[]   colsDef = null; 
      DbColumnDef     colDef  = null; 
      ArchiveTokenFld fld   = null; 
      int             fldType;   
      int             i;
      int             numCols;     
      
      numCols = relFlds.count();
      
      colsDef = new DbColumnDef[numCols]; 
      
      for(i = 0; i < numCols; i++)
      {
         fld = relFlds.get(i);
         
         fldType = fld.getType();
         
         colDef = new DbColumnDef(fld.getColName(), 
                                  ArchiveFldType.getDbDataType(fldType),
                                  fld.getLen(), fld.isNullable());
                                  
         colsDef[i] = colDef;
         
      }
      
      return colsDef;
      
   } 
   
   public static DaoRelFlds getDaoRelFlds(ArchiveTokenFlds relFlds)
                            throws Exception
   {  
      DaoRelFlds      daoRelFlds = new DaoRelFlds();       
      ArchiveTokenFld fld   = null;      
      int             i;
      int             numFlds;     
      
      numFlds = relFlds.count();
      
      for(i = 0; i < numFlds; i++)
      {
         fld = relFlds.get(i);
         
         daoRelFlds.add(fld.getId(), fld.getName(), fld.getColName()); 
      }
      
      return daoRelFlds;
      
   }     
   
} // class
