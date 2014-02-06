package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoActDirTbl
{

   //~ Static fields/initializers ---------------------------------------------

   // **************************************************************************
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
   public static void incrementActDir(int volId)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_ACTDIR.getName() + "="
            + CD_ACTDIR.getName() + "+1, " + CD_NUMFILES.getName()
            + "= 0 WHERE " + CD_VOLID.getName() + "= " + volId;

      DbUtil.executeStatement(stmtText);

   }

   public static void incrementNumFiles(int volId, int actDir)
      throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_NUMFILES.getName() + "="
            + CD_NUMFILES.getName() + "+1 WHERE " + CD_VOLID.getName() + "="
            + volId + " AND " + CD_ACTDIR.getName() + "=" + actDir;

      DbUtil.executeStatement(stmtText);

   }

   //****************************************************************************
   public static FssDaoActDirRecA selectRecA(int volId)
      throws Exception
   {

      FssDaoActDirRecA rec = new FssDaoActDirRecA();

      DbSelectFns.select(TN, ACN, getDefaultQual(volId), rec);

      return rec;

   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "VOLID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,true);
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
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

}

// class
