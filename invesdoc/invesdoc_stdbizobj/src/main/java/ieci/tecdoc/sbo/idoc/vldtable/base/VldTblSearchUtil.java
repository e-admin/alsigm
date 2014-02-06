package ieci.tecdoc.sbo.idoc.vldtable.base;

import ieci.tecdoc.core.search.SearchDataType;
import ieci.tecdoc.core.db.DbDataType;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 12:54:52
 */
public class VldTblSearchUtil
{


   public VldTblSearchUtil()
   {
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

}
