
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.*;

public final class AcsDaoObjHdrTbl
{
   
   // **************************************************************************
   
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

   public static void deleteRow(int id) throws Exception
   {
      DbDeleteFns.delete(TN, getDefaultQual(id));
   }
   
   
   // **************************************************************************      
   
   public static boolean isObjOwner(int objId, int ownerId, int ownerType)
                         throws Exception
   {  
      String qual;
      
      qual = "WHERE " + CD_ID.getName() + "= " + objId + 
             " AND " + CD_OWNERID.getName() + "= " + ownerId +
             " AND " + CD_OWNERTYPE.getName() + "= " + ownerType;
             
      return DbSelectFns.rowExists(TN, qual);      
   }
   
   public static AcsDaoObjHdrRecOwner selectRow(int id)            
                                      throws Exception
   { 

      AcsDaoObjHdrRecOwner rec = new AcsDaoObjHdrRecOwner();  

      DbSelectFns.select(TN, OWNERCN, getDefaultQual(id), rec);

      return rec;

   }
   
// **************************************************************************
   public static void createTable() throws Exception
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
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true, false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false, false);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3,false, false);
      indexDef4 = new DbIndexDef(indexName4,colNamesIndex4,false, false);
      indexDef5 = new DbIndexDef(indexName5,colNamesIndex5,false, false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
      DbTableFns.createIndex(TN,indexDef3);
      DbTableFns.createIndex(TN,indexDef4);
      DbTableFns.createIndex(TN,indexDef5);
      
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
      indexName = TN + "4";
      dropIndex(TN,indexName);
      indexName = TN + "5";
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
