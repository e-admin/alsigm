
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.*;

public final class AcsDaoUserTypeTbl
{
   
   // **************************************************************************
   
   public static final String TN = "IUSERUSERTYPE";

   public static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
  
   public static final DbColumnDef[] ACD = 
   {CD_USERID, CD_PRODID, CD_TYPE};  
   
   public static final String ACN = DbUtil.getColumnNames(ACD);
   
   // **************************************************************************

   public static String getDefaultQual(int userId)
   {
      return "WHERE " + CD_USERID.getName() + "= " + userId;
   }

   public static String getDefaultQual(int userId, int prodId)
   {
      return "WHERE " + CD_USERID.getName() + "=" + userId  
             + " AND " + CD_PRODID.getName() + "=" + prodId;
   }
      
   // **************************************************************************
   
   private AcsDaoUserTypeTbl()
   {
   }
   
   // **************************************************************************
    
   public static int selectType(int userId, int prodId)
                     throws Exception
   {  
      int type;      
      
      type = DbSelectFns.selectLongInteger(TN, CD_TYPE.getName(),
                                           getDefaultQual(userId, prodId));
    
      return type;
         
   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2,colNamesIndex3;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "USERID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID,PRODID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false, false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true, false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName,indexName2;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      
      dropIndex(TN,indexName);
      dropIndex(TN, indexName2);
         
      DbTableFns.dropTable(TN);      
      
   }
   
   private static void dropIndex(String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(tblName,indexName);
      }
      catch(Exception e)
      {
         
      }
   }
   
} // class
