
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.*;

public final class AcsDaoGenPermTbl
{
   
   // **************************************************************************
   
   public static final String TN = "IUSERGENPERMS";

   public static final DbColumnDef CD_DSTTYPE = new DbColumnDef
   ("DSTTYPE", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_DSTID = new DbColumnDef
   ("DSTID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PERMS = new DbColumnDef
   ("PERMS", DbDataType.LONG_INTEGER, false);   
   
   public static final DbColumnDef[] ACD = 
   {CD_DSTTYPE, CD_DSTID, CD_PRODID, CD_PERMS};
  
   public static final String ACN = DbUtil.getColumnNames(ACD);
   
   // **************************************************************************
   
   public static String getDefaultQual(int dstType, int dstId, int prodId)
   {
      return "WHERE " + CD_DSTTYPE.getName() + "=" + dstType  
             + " AND " + CD_DSTID.getName() + "=" + dstId + " AND "
             + CD_PRODID.getName() + "=" + prodId ;
   }
   
   // **************************************************************************
   
   private AcsDaoGenPermTbl()
   {
   }  
   
   // **************************************************************************   
    
   public static int selectGenPerms(int dstType, int dstId, int prodId)
                     throws Exception
   {  
      
      int perms;   
            
      perms = DbSelectFns.selectLongInteger(TN, CD_PERMS.getName(),
                                            getDefaultQual(dstType, dstId, prodId));

      return perms;
         
   }   
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "DSTTYPE,DSTID";
      indexName2 = TN + "2";
      colNamesIndex2 = "DSTTYPE,DSTID,PRODID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false, false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,true, false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      dropIndex(TN,indexName);
      indexName = TN + "2";
      dropIndex(TN,indexName);
         
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
