package ieci.tecdoc.core.search;

import java.util.Date;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.textutil.TextParserX;
import java.util.ArrayList;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;

public final class SearchUtil
{

   private SearchUtil()
   {
   }
   
   public static String getSqlSyntaxValue(int dbEngine, int dataType, Object data)
                        throws Exception
   {  
      String val;
       
      switch(dataType)
      {
         case SearchDataType.SHORT_TEXT:
         case SearchDataType.LONG_TEXT:
         
            val = getTextSqlSyntaxValue((String)data);
            break;
         
         case SearchDataType.SHORT_INTEGER:
         
            val = getShortIntegerSqlSyntaxValue(((Short)data).shortValue());
            break;
            
         case SearchDataType.LONG_INTEGER:
         
            val = getLongIntegerSqlSyntaxValue(((Integer)data).intValue());
            break;
            
         case SearchDataType.SHORT_DECIMAL:
         
            val = getShortDecimalSqlSyntaxValue(((Float)data).floatValue());
            break;
            
         case SearchDataType.LONG_DECIMAL:
         
            val = getLongDecimalSqlSyntaxValue(((Double)data).doubleValue());
            break;
            
         case SearchDataType.DATE:
         
            val = getDateSqlSyntaxValue(dbEngine, (Date)data);
            break;
            
         case SearchDataType.TIME:            
         case SearchDataType.DATE_TIME:
         
            val = getDateTimeSqlSyntaxValue(dbEngine, (Date)data);
            break; 
            
         default:
         
             throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                       SearchError.EM_INVALID_PARAM);      
            
      }
      
      return val;

   }

   public static String getDateSqlSyntaxValue(int dbEngine, Date data)
                        throws Exception
   {  
       
      String  val; 
      boolean onlyDate = true; 
      
      val = DbUtil.getNativeDateTimeSyntax(dbEngine, data, onlyDate);
      
      return val;      

   }
   
   public static String getDateTimeSqlSyntaxValue(int dbEngine, Date data)
                        throws Exception
   {   
           
      String  val; 
      boolean onlyDate = false; 
      
      val = DbUtil.getNativeDateTimeSyntax(dbEngine, data, onlyDate);
      
      return val;      

   }
   
   public static String getTextSqlSyntaxValue(String data)
                        throws Exception
   {     

      String val;
      
      val = "'" + data + "'";
      
      return val;      

   }
   
   public static String getShortIntegerSqlSyntaxValue(short data)
                        throws Exception
   { 
      return Short.toString(data); 
   }
   
   public static String getLongIntegerSqlSyntaxValue(int data)
                        throws Exception
   { 
      return Integer.toString(data); 
   }
   
   public static String getShortDecimalSqlSyntaxValue(float data)
                        throws Exception
   { 
      return Float.toString(data); 
   }
   
   public static String getLongDecimalSqlSyntaxValue(double data)
                        throws Exception
   { 
      return Double.toString(data); 
   }
   
   public static Object parseSearchValue(TextParserX parser, int dataType, String val)
                        throws Exception
   { 
       
      switch(dataType)
      {
         case SearchDataType.SHORT_TEXT:
                  
            return parser.parseShortText(val);
            
         case SearchDataType.LONG_TEXT:
         
            return parser.parseLongText(val);
         
         case SearchDataType.SHORT_INTEGER:
         
            return new Short(parser.parseShortInteger(val));
            
         case SearchDataType.LONG_INTEGER:
         
            return new Integer(parser.parseLongInteger(val));
            
         case SearchDataType.SHORT_DECIMAL:
         
            return new Float(parser.parseShortDecimal(val));
            
         case SearchDataType.LONG_DECIMAL:
         
            return new Double(parser.parseLongDecimal(val));
            
         case SearchDataType.DATE:
         
            return parser.parseDateTime(val, true);
         
         case SearchDataType.TIME:
            
               return parser.parseTime(val);
         
         case SearchDataType.DATE_TIME:
         
            return parser.parseDateTime(val, false);
            
         default:
         
             throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                       SearchError.EM_INVALID_PARAM);      
            
      }

   }
   
   public static ArrayList parseSearchValues(TextParserX parser, int dataType, String[] vals)
                           throws Exception
   {  
      String    val; //, parseVal;
      int       numVals, i;
      ArrayList al = new ArrayList();
            
      numVals = vals.length;
      
      for(i = 0; i < numVals; i++)
      {
         val = vals[i];
         
         al.add(parseSearchValue(parser, dataType, val));      
      }
      
      return al;
   }
   
   public static IeciTdShortTextArrayList getDefaultOprs (int searchDataType, boolean isRelational)
                                          throws Exception
   {  
      IeciTdShortTextArrayList oprs = new IeciTdShortTextArrayList();
       
      switch(searchDataType)
      {
         case SearchDataType.SHORT_TEXT:
         case SearchDataType.SHORT_INTEGER:
         case SearchDataType.LONG_INTEGER:
         case SearchDataType.SHORT_DECIMAL:
         case SearchDataType.LONG_DECIMAL:
         case SearchDataType.DATE_TIME:
         case SearchDataType.DATE:         
         
            oprs.add(SearchOpr.EQUAL);
            oprs.add(SearchOpr.DISTINCT);
            oprs.add(SearchOpr.GREATER);
            oprs.add(SearchOpr.GREATER_EQUAL);
            oprs.add(SearchOpr.LOWER);
            oprs.add(SearchOpr.LOWER_EQUAL);
            oprs.add(SearchOpr.BETWEEN);
            oprs.add(SearchOpr.OR);
            
            
            if (searchDataType == SearchDataType.SHORT_TEXT)
               oprs.add(SearchOpr.LIKE);
                        
            if (isRelational) {
	            oprs.add(SearchOpr.IS_NULL);
	            oprs.add(SearchOpr.IS_NOT_NULL);
            }
            
            break;
         
         case SearchDataType.LONG_TEXT:
         
            // No sacamos el operador LIKE para texto largos por hacerlo igual que el cliente InvesDoc
            // de microsoft (cliente/servidor). Los campos largos sólo deben aparecer en el formulario
            // de búsqueda si son documentales, en cuyo caso, aparecerán los operadores de búsqueda 
            // documental
            //oprs.add(SearchOpr.LIKE);
            break;        
            
         default:
         
             throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                       SearchError.EM_INVALID_PARAM);      
            
      }
      
      return oprs;

   }

   public static int convertDBTypeToSearchDataType(int dbType)
                     throws Exception
   {
      int searchType;

      switch(dbType)
      {
         case DbDataType.SHORT_TEXT:

            searchType = SearchDataType.SHORT_TEXT;
            break;

         case DbDataType.LONG_TEXT:

            searchType = SearchDataType.LONG_TEXT;
            break;

         case DbDataType.SHORT_INTEGER:

            searchType = SearchDataType.SHORT_INTEGER;
            break;

         case DbDataType.LONG_INTEGER:

            searchType = SearchDataType.LONG_INTEGER;
            break;

         case DbDataType.SHORT_DECIMAL:

            searchType = SearchDataType.SHORT_DECIMAL;
            break;

         case DbDataType.LONG_DECIMAL:

            searchType = SearchDataType.LONG_DECIMAL;
            break;

         case DbDataType.DATE_TIME:

            searchType = SearchDataType.DATE_TIME;
            break;

         default:
            throw new Exception();


      }

      return searchType;

   }
   

} // class
