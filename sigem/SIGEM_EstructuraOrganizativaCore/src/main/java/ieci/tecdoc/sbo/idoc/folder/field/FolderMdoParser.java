
package ieci.tecdoc.sbo.idoc.folder.field;

import org.apache.log4j.Logger;

import java.util.Date;
import ieci.tecdoc.sbo.idoc.archive.base.*;
import ieci.tecdoc.core.textutil.TextParserX;
import java.util.ArrayList;

public class FolderMdoParser
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(FolderMdoParser.class);
   
   private FolderMdoParser()
   {      
   }
   
   public static String formatFieldValue(ArchiveToken arch, int fldId, Object val)
                        throws Exception
   {          
           
      String                 formatValue;      
      ArchiveTokenFlds       flds           = arch.getFlds();       
      ArchiveTokenFld        fld            = flds.findById(fldId);  
      int                    fldType        =  fld.getType();
      ArchiveTokenValidation archValidation = arch.getArchValidation();
      String                 fldFmtInfo     = null;
      int                    idx;
      
      if (archValidation != null)
      {
         idx = archValidation.findFldValidationIndex(fldId);        
         
         if (idx != -1) //tiene validación
         {
            fldFmtInfo = archValidation.getFldValidation(idx).getFmtInfo();
         }
      }    
      
      if ( val!=null )
      {
         formatValue = formatFieldValue(fldFmtInfo, fldType, val);
      }
      else
      {
         formatValue ="";
      }


      return formatValue;

   }
   
   public static String[] formatFieldValues(ArchiveToken arch, int fldId, ArrayList vals)
                          throws Exception
   {          
        
      String[]               formatValues = new String[vals.size()];   
      String                 formatValue;      
      ArchiveTokenFlds       flds           = arch.getFlds();
      ArchiveTokenFld        fld            = flds.findById(fldId);
      int                    fldType        = fld.getType();
      ArchiveTokenValidation archValidation = arch.getArchValidation();
      String                 fldFmtInfo     = null;
      int                    idx, i;
      
      if (archValidation != null)
      {
         idx = archValidation.findFldValidationIndex(fldId);
         if (idx != -1) //tiene validación
         {
            fldFmtInfo = archValidation.getFldValidation(idx).getFmtInfo();
         }
      }
      
      for(i = 0; i < vals.size(); i++)
      {
         formatValue = formatFieldValue(fldFmtInfo, fldType, vals.get(i));
         formatValues[i] = formatValue;
      }      
      
      return formatValues;

   }
   
   public static String formatFieldValue(String fldFmtInfo, int fldType, Object val)
                        throws Exception
   { 
      TextParserX   parser = null;       
      String        thousandSep = null;
      String        formatValue;
      String        aux, decPart;
      ArchiveNumFmt numFmt = null; 
      int           idx;
      
      parser = ArchiveUtil.createParser(fldFmtInfo, fldType);
      
      formatValue = formatFieldValue(parser, fldType, val);  
      
      if ( (fldFmtInfo != null) && (!formatValue.equals("")) )
      {
      
         if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
              (fldType == ArchiveFldType.LONG_INTEGER)  ||
              (fldType == ArchiveFldType.SHORT_DECIMAL) ||
              (fldType == ArchiveFldType.LONG_DECIMAL) )
         {         
            // Le ponemos los separadores de miles 
                 
            numFmt = ArchiveUtil.createNumFmt(fldFmtInfo);
     
            thousandSep = numFmt.getThousandSeparator();            
           
            if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
                 (fldType == ArchiveFldType.LONG_INTEGER) )
            {
               formatValue = appendThousandSep(formatValue, thousandSep);
            }
            else
            {
               idx         = formatValue.indexOf(numFmt.getDecimalSeparator());
               aux         = formatValue.substring(0,idx);
               decPart     = formatValue.substring(idx);
               formatValue = appendThousandSep(aux, thousandSep) + decPart;              
            }           
         }
                  
      }     
      
      if(logger.isDebugEnabled())
         logger.debug("formatValue: "+formatValue);
      
      return formatValue;

   }
   
   private static String appendThousandSep(String value, String thousandSep)
                         throws Exception
   { 
      String   numValue;
      String   aux;
      int      i, idx;
      
      aux = new String(value); 
               
      numValue = new String();
      
      for(i = 0; i < aux.length(); i++)
      {
         
         if ( ((aux.length()-i)%3 == 0) && (i != 0) )
         {
            numValue = numValue + thousandSep;
         }
                  
         numValue = numValue + aux.charAt(i); 
                  
      }
           
      return numValue;

   }
   
   private static String formatFieldValue(TextParserX parser, int fldType, Object val)
                         throws Exception
   { 
      
      String formatVal = null;
            
      switch(fldType)
      {
         case ArchiveFldType.SHORT_TEXT:
                  
            formatVal =  parser.formatShortText((String)val);
            break;
            
         case ArchiveFldType.LONG_TEXT:
         
            formatVal = parser.formatLongText((String)val);
            break;
         
         case ArchiveFldType.SHORT_INTEGER:
         
            short  shortValue = ((Short)val).shortValue(); 
         
            formatVal = parser.formatShortInteger(shortValue);
            break;
            
         case ArchiveFldType.LONG_INTEGER:
         
            int intValue = ((Integer)val).intValue(); 
         
            formatVal = parser.formatLongInteger(intValue);
            break;
            
         case ArchiveFldType.SHORT_DECIMAL:
         
            float floatValue = ((Float)val).floatValue();
         
            formatVal = parser.formatShortDecimal(floatValue);
            break;
            
         case ArchiveFldType.LONG_DECIMAL:
         
            double doubleValue = ((Double)val).doubleValue();  
         
            formatVal = parser.formatLongDecimal(doubleValue);
            break;
            
         case ArchiveFldType.DATE:
         
            formatVal = parser.formatDateTime((Date)val, true);
            break;
            
         case ArchiveFldType.DATE_TIME:         
                
            formatVal = parser.formatDateTime((Date)val, false);
            break;
            
         case ArchiveFldType.TIME:
           
            formatVal = parser.formatTime((Date)val);            
            
            break;
            
      }
      
      if(logger.isDebugEnabled())
         logger.debug("formatVal: "+formatVal);
      
      return formatVal;

   }
   
   public static Object parserFieldValue(ArchiveToken arch, int fldId, String val)
                        throws Exception
   {          
           
      Object                 parseValue     = null;      
      ArchiveTokenFlds       flds           = arch.getFlds();
      ArchiveTokenFld        fld            = flds.findById(fldId);
      int                    fldType        = fld.getType();
      ArchiveTokenValidation archValidation = arch.getArchValidation();
      String                 fldFmtInfo     = null;
      int                    idx;
      
      if (archValidation != null)
      {
         idx = archValidation.findFldValidationIndex(fldId);
         if (idx != -1) //tiene validación
         {
            fldFmtInfo = archValidation.getFldValidation(idx).getFmtInfo();
         }
      }
            
      parseValue = parserFieldValue(fldFmtInfo, fldType, val);
      
      return parseValue;

   }
   
   public static ArrayList parseFieldValues(ArchiveToken arch, int fldId,
                                            String[] vals)
                           throws Exception
   {          
        
      ArrayList              parseValues = new ArrayList();   
      Object                 parseValue;      
      ArchiveTokenFlds       flds           = arch.getFlds();
      ArchiveTokenFld        fld            = flds.findById(fldId);
      int                    fldType        = fld.getType();
      ArchiveTokenValidation archValidation = arch.getArchValidation();
      String                 fldFmtInfo     = null;
      int                    idx, i;
      
      if (archValidation != null)
      {
         idx = archValidation.findFldValidationIndex(fldId);
         if (idx != -1) //tiene validación
         {
            fldFmtInfo = archValidation.getFldValidation(idx).getFmtInfo();
         }
      }
      
      for(i = 0; i < vals.length; i++)
      {
         parseValue = parserFieldValue(fldFmtInfo, fldType, vals[i]);
         parseValues.add(parseValue);
      }      
      
      return parseValues;

   }   
   
   public static Object parserFieldValue(String fldFmtInfo, int fldType, String val)
                        throws Exception
   { 
      TextParserX   parser = null;      
      Object        value = null;
      int           fmtType; 
      char          thousandSep = ' ';
      ArchiveNumFmt numFmt = null; 
      String        parseValue = new String(val);
      int           idx;
      String        aux1, aux2;
      
      parser = ArchiveUtil.createParser(fldFmtInfo, fldType);
      
      if (fldFmtInfo != null)
      {
      
         if ( (fldType == ArchiveFldType.SHORT_INTEGER) ||
              (fldType == ArchiveFldType.LONG_INTEGER)  ||
              (fldType == ArchiveFldType.SHORT_DECIMAL) ||
              (fldType == ArchiveFldType.LONG_DECIMAL) )
         {         
            // Le quitamos los separadores de miles 
            numFmt = ArchiveUtil.createNumFmt(fldFmtInfo);
            thousandSep = numFmt.getThousandSeparator().charAt(0);     
            
            while ((idx = val.indexOf(thousandSep)) != -1)
            {              
               aux1 = val.substring(0,idx);
               aux2 = val.substring(idx+1);
               parseValue = aux1 + aux2;   
               val = parseValue;
            }
           
         }
         
      }      
       
      value =  parseFieldValue(parser, fldType, parseValue);
      
      return value;

   }
   
   private static Object parseFieldValue(TextParserX parser, int fldType, String val)
                         throws Exception
   { 
      Object parserVal = null;
      String dtVal     = null;
      
      switch(fldType)
      {
         case ArchiveFldType.SHORT_TEXT:
                  
            parserVal =  parser.parseShortText(val);
            break;
            
         case ArchiveFldType.LONG_TEXT:
         
            parserVal = parser.parseLongText(val);
            break;
         
         case ArchiveFldType.SHORT_INTEGER:
         
            parserVal = new Short(parser.parseShortInteger(val));
            break;
            
         case ArchiveFldType.LONG_INTEGER:
         
            parserVal = new Integer(parser.parseLongInteger(val));
            break;
            
         case ArchiveFldType.SHORT_DECIMAL:
         
            parserVal = new Float(parser.parseShortDecimal(val));
            break;
            
         case ArchiveFldType.LONG_DECIMAL:
         
            parserVal = new Double(parser.parseLongDecimal(val));
            break;
            
         case ArchiveFldType.DATE:
         
            parserVal = parser.parseDateTime(val, true);
            break;
            
         case ArchiveFldType.DATE_TIME:         
         
            parserVal =  parser.parseDateTime(val, false);
            break; 

         case ArchiveFldType.TIME:
         
            dtVal     =  ArchiveUtil.convertTimeStrToDateTimeStr(val);
            parserVal =  parser.parseDateTime(dtVal, false);
            break;        
            
      }
      
      return parserVal;

   }
   
} // class
