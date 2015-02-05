
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;


public final class AcsDaoObjPermTbl
{
   
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
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

   public static void deleteRow(DbConnection dbConn, int id) throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }
   
   // **************************************************************************   
    
   public static IeciTdLongIntegerArrayList selectPerms(DbConnection dbConn, int dstType, int dstId,
                                                        int objId)
                                          throws Exception
   {  
      
      IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();   
            
      DbSelectFns.select(dbConn, TN, CD_PERM.getName(),
                         getDefaultQual(dstType, dstId, objId), false, vals);

      return vals;
         
   }
   
   public static boolean permExists(DbConnection dbConn, int dstType, int dstId, int objId,
                                    int perm) throws Exception
   {  
      String qual;
      
      qual = "WHERE " + CD_DSTTYPE.getName() + "= " + dstType + 
             " AND " + CD_DSTID.getName() + "= " + dstId + 
             " AND " + CD_OBJID.getName() + "= " + objId +
             " AND " + CD_PERM.getName() + "= " + perm;
      
      return DbSelectFns.rowExists(dbConn, TN, qual);      
   }   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
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
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "3";
      dropIndex(dbConn, TN,indexName);
         
      DbTableFns.dropTable(dbConn, TN);      
      
   }
   
   private static void dropIndex(DbConnection dbConn, String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(dbConn, tblName,indexName);
      }
      catch(Exception e)
      {
         
      }
   }
   
} // class
