
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;


public final class AcsDaoObjHdrTbl
{
   
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String TN = "IUSEROBJHDR";

   public static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_EXTID1 = new DbColumnDef
   ("EXTID1", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_EXTID2 = new DbColumnDef
   ("EXTID2", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_EXTID3 = new DbColumnDef
   ("EXTID3", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_OWNERTYPE = new DbColumnDef
   ("OWNERTYPE", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_OWNERID = new DbColumnDef
   ("OWNERID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   public static final DbColumnDef CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   public static final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
   
   public static final DbColumnDef[] ACD = 
   {CD_ID, CD_PRODID, CD_TYPE, CD_EXTID1, CD_EXTID2, CD_EXTID3,
    CD_OWNERTYPE, CD_OWNERID, CD_CRTUSRID, CD_CRTTS, CD_UPDUSRID, CD_UPDTS};
     
   public static final String ACN = DbUtil.getColumnNames(ACD); 
   
   public static final String OWNERCN = DbUtil.getColumnNames
   (CD_OWNERTYPE, CD_OWNERID);
   
      
   // **************************************************************************

   public static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }   
   
   // **************************************************************************
   
   private AcsDaoObjHdrTbl()
   {
   }
   
   // **************************************************************************

   public static void deleteRow(DbConnection dbConn, int id) throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }
   
   
   // **************************************************************************      
   
   public static boolean isObjOwner(DbConnection dbConn, int objId, int ownerId, int ownerType)
                         throws Exception
   {  
      String qual;
      
      qual = "WHERE " + CD_ID.getName() + "= " + objId + 
             " AND " + CD_OWNERID.getName() + "= " + ownerId +
             " AND " + CD_OWNERTYPE.getName() + "= " + ownerType;
             
      return DbSelectFns.rowExists(dbConn, TN, qual);      
   }
   
   public static AcsDaoObjHdrRecOwner selectRow(DbConnection dbConn, int id)            
                                      throws Exception
   { 

      AcsDaoObjHdrRecOwner rec = new AcsDaoObjHdrRecOwner();  

      DbSelectFns.select(dbConn, TN, OWNERCN, getDefaultQual(id), rec);

      return rec;

   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3,indexName4,indexName5;
      String colNamesIndex,colNamesIndex2,colNamesIndex3,colNamesIndex4,colNamesIndex5;
      DbIndexDef indexDef,indexDef2,indexDef3,indexDef4,indexDef5;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "OWNERTYPE,OWNERID";
      indexName3 = TN + "3";
      colNamesIndex3 = "PRODID";
      indexName4 = TN + "4";
      colNamesIndex4 = "PRODID,TYPE";
      indexName5 = TN + "5";
      colNamesIndex5 = "PRODID,TYPE,EXTID1";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false);
      indexDef4 = new DbIndexDef(indexName4,colNamesIndex4,false);
      indexDef5 = new DbIndexDef(indexName5,colNamesIndex5,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
      DbTableFns.createIndex(dbConn, TN,indexDef4);
      DbTableFns.createIndex(dbConn, TN,indexDef5);
      
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
      indexName = TN + "4";
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "5";
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
