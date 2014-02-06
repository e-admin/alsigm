package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFldType;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenFld;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveNumFmt;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveUtil;
import ieci.tecdoc.core.search.SearchDataType;
import ieci.tecdoc.core.search.SearchUtil;
import ieci.tecdoc.core.textutil.TextParserX;
import java.util.ArrayList;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;

/**
 * Clase que recoge algunos métodos generales que son utilizados
 * en las búsquedas de carpetas de un archivador. Estos métodos
 * son métodos de formateo y métodos de conversión de tipos
 * 
 * @author 
 */

public final class FolderSearchUtil
{
   
   /**
    * Constructor
    */
   private FolderSearchUtil()
   {
   } 
      
   /**
    * Método que convierte un tipo de campo a un tipo de campo en la búsqueda 
    * @param fldType tipo de campo
    * @return tipo de campo en la búsqueda
    * @throws Exception
    */
   public static int convertFldTypeToSearchDataType(int fldType)
                     throws Exception
   {  
      int searchType;
      
      switch(fldType)
      {
         case ArchiveFldType.SHORT_TEXT:
            
            searchType = SearchDataType.SHORT_TEXT;
            break;
            
         case ArchiveFldType.LONG_TEXT:
                     
            searchType = SearchDataType.LONG_TEXT;
            break;
         
         case ArchiveFldType.SHORT_INTEGER:
                     
            searchType = SearchDataType.SHORT_INTEGER;
            break;
            
         case ArchiveFldType.LONG_INTEGER:
                    
            searchType = SearchDataType.LONG_INTEGER;
            break;
            
         case ArchiveFldType.SHORT_DECIMAL:
                     
            searchType = SearchDataType.SHORT_DECIMAL;
            break;
            
         case ArchiveFldType.LONG_DECIMAL:
                    
            searchType = SearchDataType.LONG_DECIMAL;
            break;
            
         case ArchiveFldType.DATE:
                    
            searchType = SearchDataType.DATE;
            break;
            
         case ArchiveFldType.TIME:   
         case ArchiveFldType.DATE_TIME:
         
            searchType = SearchDataType.DATE_TIME;
            break;
            
         default:
         
             throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                                       FolderSearchError.EM_INVALID_PARAM); 
             
            
      }
      
      return searchType;

   }
   
   /**
    * Devuelve los operadores de búsqueda para un determinado campo de un archivador
    * @param fld campo sobre el cual se van a obtener los operadores de búsqueda 
    * @return referencia a un objeto de tipo IeciTdShortTextArrayList. Este es una colección
    * con los operadores de búsqueda
    * @throws Exception
    * @see ArchiveTokenFld 
    * @see IeciTdShortTextArrayList 
    */
   public static IeciTdShortTextArrayList getDefaultOprs(ArchiveTokenFld fld)
                                          throws Exception
   {  
      IeciTdShortTextArrayList oprs;
      int                      searchDataType = convertFldTypeToSearchDataType(fld.getType());
      
      if (fld.isMult())
      {
         oprs = new IeciTdShortTextArrayList();
         
         oprs.add(FolderSearchOpr.IN_AND);
         oprs.add(FolderSearchOpr.IN_OR);
         
         if (fld.getType() == ArchiveFldType.SHORT_TEXT)
         {
            oprs.add(FolderSearchOpr.LIKE_AND);
            oprs.add(FolderSearchOpr.LIKE_OR);
         }
         
      }
      else
      {      
         oprs = SearchUtil.getDefaultOprs(searchDataType, fld.isRel ());
      
         if (fld.isDoc())
         {
            oprs.add(FolderSearchOpr.FULL_TEXT);
            oprs.add(FolderSearchOpr.FULL_TEXT_NOT);            
         }
      }
      
      return oprs;

   }
   
   /**
    * Chequea y formatea el valor que se va a utilizar en un determinado campo
    * para realizar la búsqueda
    * @param fldFmtInfo información del formato del campo; puede ser nulo
    * @param fldType tipo de campo
    * @param val valor del campo
    * @return valor formateado
    * @throws Exception
    */
   public static Object parseSearchValue(String fldFmtInfo, int fldType, String val)
                        throws Exception
   { 
      TextParserX             parser = null;     
      Object                  value = null;
      int                     fmtType; 
      char                    thousandSep = ' ';
      String                  parseValue = new String(val);
      int                     idx;
      String                  aux1, aux2;
      
      parser = ArchiveUtil.createParser(fldFmtInfo, fldType);
      
      if (fldFmtInfo != null)
      {
      
         if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
              (fldType == ArchiveFldType.LONG_INTEGER)  ||
              (fldType == ArchiveFldType.SHORT_DECIMAL) ||
              (fldType == ArchiveFldType.LONG_DECIMAL) )
         {         
            // Le quitamos los separadores de miles         
            thousandSep = getThousandSeparator(fldFmtInfo); 
            idx = val.indexOf(thousandSep);
            if (idx != -1)
            {
               aux1 = val.substring(0,idx);
               aux2 = val.substring(idx+1);
               parseValue = aux1 + aux2;
            }
           
         }
         
      }
     
      value = parseSearchValue(parser, fldType, parseValue);
      
      return value;

   }
   
   /**
    * Chequea y formatea los valores que se van a utilizar en un determinado campo 
    * para realizar la búsqueda 
    * @param fldFmtInfo información de formato del campo
    * @param fldType tipo de campo
    * @param vals valores del campo
    * @return valores formateados
    * @throws Exception
    */
   public static ArrayList parseSearchValues(String fldFmtInfo, int fldType, 
                                             String[] vals)
                           throws Exception
   { 
      TextParserX             parser = null;  
      Object                  value = null;
      char                    thousandSep = ' ';
      char                    decSep;
      String                  parseValue;
      int                     numVals, i;
      boolean                 replaceThousandSep = false;
      ArrayList               parseValues = new ArrayList();
      int                     idx;
      String                  aux1, aux2;
            
      parser = ArchiveUtil.createParser(fldFmtInfo, fldType);
      
      if (fldFmtInfo != null)
      {
      
         if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
              (fldType == ArchiveFldType.LONG_INTEGER)  ||
              (fldType == ArchiveFldType.SHORT_DECIMAL) ||
              (fldType == ArchiveFldType.LONG_DECIMAL) )
         {        
            thousandSep = getThousandSeparator(fldFmtInfo);
            replaceThousandSep = true;
         } 
         
      }
     
       
      numVals = vals.length;
      
      for(i = 0; i < numVals; i++)
      {
         parseValue = vals[i];
         
         if (replaceThousandSep)
         {          
            //Le quitamos los separadores de miles                  
            idx = vals[i].indexOf(thousandSep);
            if (idx != -1)
            {
               aux1 = vals[i].substring(0,idx);
               aux2 = vals[i].substring(idx+1);
               parseValue = aux1 + aux2;
            }
         }  

         value =  parseSearchValue(parser, fldType, parseValue);
         
         parseValues.add(value);      
      }
      
      return parseValues;

   }  
  
   private static Object parseSearchValue(TextParserX parser, int fldType, String val)
                        throws Exception
   { 
      int        searchDataType;
      Object     value = null;      
      String     parseValue = new String(val);      
     
      searchDataType = convertFldTypeToSearchDataType(fldType);

      if (fldType == ArchiveFldType.TIME)
      {  
         parseValue = ArchiveUtil.convertTimeStrToDateTimeStr(parseValue);
      }
       
      value =  SearchUtil.parseSearchValue(parser, searchDataType,
                                           parseValue);
      
      return value;

   }
   
   private static char getThousandSeparator(String fldFmtInfo)
                        throws Exception
   {       
      ArchiveNumFmt  numFmt = null; 
      String         sep;
     
      numFmt = ArchiveUtil.createNumFmt(fldFmtInfo);
     
      sep = numFmt.getThousandSeparator();
           
      return sep.charAt(0);
   } 
   

} // class
