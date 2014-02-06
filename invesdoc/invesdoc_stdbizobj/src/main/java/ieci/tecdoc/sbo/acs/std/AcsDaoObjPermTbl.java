
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

public final class AcsDaoObjPermTbl
{
   
   // **************************************************************************
   
   public static final String TN = "IUSEROBJPERM";

   public static final DbColumnDef CD_DSTTYPE = new DbColumnDef
   ("DSTTYPE", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_DSTID = new DbColumnDef
   ("DSTID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_OBJID = new DbColumnDef
   ("OBJID", DbDataType.LONG_INTEGER, false);  
   
   public static final DbColumnDef CD_PERM = new DbColumnDef
   ("APERM", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef[] ACD = 
   {CD_DSTTYPE, CD_DSTID, CD_OBJID, CD_PERM};   
   
   public static final String ACN = DbUtil.getColumnNames(ACD);  
   
   // **************************************************************************  

   public static String getDefaultQual(int dstType, int dstId, int objId)
   {
      return "WHERE " + CD_DSTTYPE.getName() + "= " + dstType + 
             " AND " + CD_DSTID.getName() + "= " + dstId + 
             " AND " + CD_OBJID.getName() + "= " + objId;
   }
   
   public static String getDefaultQual(int objId)
   {
      return "WHERE " + CD_OBJID.getName() + "= " + objId;
   }   
   
   // **************************************************************************
   
   private AcsDaoObjPermTbl()
   {
   }
   
   // **************************************************************************

   public static void deleteRow(int id) throws Exception
   {
      DbDeleteFns.delete(TN, getDefaultQual(id));
   }
   
   // **************************************************************************   
    
   public static IeciTdLongIntegerArrayList selectPerms(int dstType, int dstId,
                                                        int objId)
                                          throws Exception
   {  
      
      IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();   
            
      DbSelectFns.select(TN, CD_PERM.getName(),
                         getDefaultQual(dstType, dstId, objId), false, vals);

      return vals;
         
   }
   
   public static boolean permExists(int dstType, int dstId, int objId,
                                    int perm) throws Exception
   {  
      String qual;
      
      qual = "WHERE " + CD_DSTTYPE.getName() + "= " + dstType + 
             " AND " + CD_DSTID.getName() + "= " + dstId + 
             " AND " + CD_OBJID.getName() + "= " + objId +
             " AND " + CD_PERM.getName() + "= " + perm;
      
      return DbSelectFns.rowExists(TN, qual);      
   }   
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2,colNamesIndex3;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "DSTTYPE,DSTID";
      indexName2 = TN + "2";
      colNamesIndex2 = "OBJID";
      indexName3 = TN + "3";
      colNamesIndex3 = "DSTTYPE,DSTID,OBJID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false, false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false, false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false, false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
      DbTableFns.createIndex(TN,indexDef3);
      
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";
      dropIndex(TN,indexName);
      indexName = TN + "3";
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
