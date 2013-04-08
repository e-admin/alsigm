package es.ieci.tecdoc.isicres.admin.sbo.fss.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public final class FssDaoActDirTbl
{

   //~ Static fields/initializers ---------------------------------------------

   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
	public static final String        TN          = "IVOLACTDIR";

   public static final DbColumnDef   CD_VOLID    = new DbColumnDef
   ("VOLID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_ACTDIR   = new DbColumnDef
   ("ACTDIR", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef   CD_NUMFILES = new DbColumnDef
   ("NUMFILES", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef[] ACD =
   {CD_VOLID, CD_ACTDIR, CD_NUMFILES};

   public static final String        ACN = DbUtil.getColumnNames(ACD);

   //~ Constructors -----------------------------------------------------------

   // **************************************************************************
   private FssDaoActDirTbl()
   {
   }

   //~ Methods ----------------------------------------------------------------

   // **************************************************************************
   private static String getDefaultQual(int volId)
   {

      return "WHERE " + CD_VOLID.getName() + "=" + volId;

   }

   //****************************************************************************
   public static void incrementActDir(DbConnection dbConn, int volId)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_ACTDIR.getName() + "="
            + CD_ACTDIR.getName() + "+1, " + CD_NUMFILES.getName()
            + "= 0 WHERE " + CD_VOLID.getName() + "= " + volId;

      DbUtil.executeStatement(dbConn, stmtText);

   }

   public static void incrementNumFiles(DbConnection dbConn, int volId, int actDir)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "+1 WHERE " + CD_VOLID.getName() + "="
            + volId + " AND " + CD_ACTDIR.getName() + "=" + actDir;

      DbUtil.executeStatement(dbConn, stmtText);

   }

   //****************************************************************************
   public static FssDaoActDirRecA selectRecA(DbConnection dbConn, int volId)
      throws Exception
   {

      FssDaoActDirRecA rec = new FssDaoActDirRecA();

      DbSelectFns.select(dbConn, TN, ACN, getDefaultQual(volId), rec);

      return rec;

   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "VOLID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
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

}

// class
