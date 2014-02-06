package ieci.tecdoc.sbo.fss.core;

import java.io.File;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInsertFns;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoVolVolTbl
{
   public static final String        TN          = "IVOLVOLTBL";

   public static final DbColumnDef   CD_LOCID = new DbColumnDef
   ("LOCID", DbDataType.SHORT_TEXT, 32, false);
   
   public static final DbColumnDef   CD_EXTID1 = new DbColumnDef
   ("EXTID1", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef   CD_EXTID2 = new DbColumnDef
   ("EXTID2", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef   CD_EXTID3 = new DbColumnDef
   ("EXTID3", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef   CD_EXTID4 = new DbColumnDef
   ("EXTID4", DbDataType.LONG_INTEGER, false);   
   
   public static final DbColumnDef   CD_FILEEXT = new DbColumnDef
   ("FILEEXT", DbDataType.SHORT_TEXT, 10, false);

   public static final DbColumnDef   CD_FILEVAL = new DbColumnDef
   ("FILEVAL", DbDataType.LONG_BIN, 2097152, false);
   
   public static final DbColumnDef[] ACD =
   {CD_LOCID, CD_EXTID1, CD_EXTID2, CD_EXTID3, CD_EXTID4, CD_FILEEXT, CD_FILEVAL};

   public static final String   ACN  = DbUtil.getColumnNames(ACD);
   
// **************************************************************************

   public static String getDefaultQual(String id)
   {
      return "WHERE " + CD_LOCID.getName() + "='" + id + "'";
   }

   // **************************************************************************

   private FssDaoVolVolTbl()
   {
   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      String     stmt;
          
      indexName = TN + "1";
      colNamesIndex = "LOCID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true, false);
      
      
      DbTableFns.createTable(TN, ACD);   
      DbTableFns.createIndex(TN, indexDef);  
      
      if (DbConnection.getEngine() == DbEngine.ORACLE)
      {
         String ver = DbConnection.getConnectionEngineVersion();
         if (ver.indexOf("10g") == -1)
            stmt = "CREATE INDEX IVOLVOLTBLFTS ON IVOLVOLTBL(FILEVAL) INDEXTYPE IS CTXSYS.CONTEXT";
         else
            stmt = "CREATE INDEX IVOLVOLTBLFTS ON IVOLVOLTBL(FILEVAL) INDEXTYPE IS CTXSYS.CONTEXT parameters('sync (on commit)')";
         
         DbUtil.executeStatement(stmt);
      }      
      
   }
   /**
    * Crea politicas en la tabla correspondiente a un volumen de base dedatos
    * debe ejecutarse fuera de una transación.
    * 
    * @throws Exception
    */
   public static void createVolPolicy() throws Exception
   {
      if (DbConnection.getEngine() == DbEngine.SQLSERVER)
      {    
         String stmt;
         String indexName;
         
         indexName = TN + "1";
         
         stmt = "{Call sp_fulltext_table('" + TN + "','create''IDOCFTSCTLG','" + indexName + "')}";
	      DbUtil.executeStatement(stmt);
	      stmt = "{Call sp_fulltext_column('" + TN + "','" + CD_FILEVAL + "','add',0x0409, '" + CD_FILEEXT + "')}";
	      DbUtil.executeStatement(stmt);
	      stmt = "{Call sq_fulltext_table('" + TN + "','start_full')}";
	      DbUtil.executeStatement(stmt);
	      stmt = "{Call sq_fulltext_table('" + TN + "','start_change_tracking')}";
	      DbUtil.executeStatement(stmt);	      
	      stmt = "{Call sq_fulltext_table('" + TN + "','start_background_updateindex')}";
	      DbUtil.executeStatement(stmt);
      }
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;            
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
         
      DbTableFns.dropTable(TN);     
      
   }
   
   
   public static void insertRow(FssDaoVolVolRecAc rec, int fileSize)
                      throws Exception
   {
      
      DbColumnDef   CD_FILEVALX = new DbColumnDef
                                 ("FILEVAL", DbDataType.LONG_BIN, fileSize, false);      
      DbColumnDef[] ACDX        = {CD_LOCID, CD_EXTID1, CD_EXTID2, CD_EXTID3, CD_EXTID4, CD_FILEEXT, CD_FILEVALX};
      String        ACNX        = DbUtil.getColumnNames(ACDX);
      
      DbInsertFns.insert(TN, ACNX, rec);
      
   }
   
   public static FssDaoVolVolRecAc selectRecAc(String locId)
                                   throws Exception
   {
      
      FssDaoVolVolRecAc rec = new FssDaoVolVolRecAc();

      DbSelectFns.select(TN, ACN, getDefaultQual(locId), rec);
      
      return rec;

   }
   
   public static void deleteRow(String locId)
   throws Exception
   {
      
      DbDeleteFns.delete(TN, getDefaultQual(locId));
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

   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static FssDaoVolVolRecAcFileSystem selectRecAcFileSystem(String locId, File destFile) throws Exception
   {
    
        FssDaoVolVolRecAcFileSystem rec = new FssDaoVolVolRecAcFileSystem(destFile);
        
        DbSelectFns.select(TN, ACN, getDefaultQual(locId), rec);
        
        return rec;
    
   }
   
   public static void insertRow(FssDaoVolVolRecAcFileSystem rec, int fileSize) throws Exception
   {
        DbColumnDef   CD_FILEVALX = new DbColumnDef
                      ("FILEVAL", DbDataType.LONG_BIN, fileSize, false);      
        DbColumnDef[] ACDX        = {CD_LOCID, CD_EXTID1, CD_EXTID2, CD_EXTID3, CD_EXTID4, CD_FILEEXT, CD_FILEVALX};
        String        ACNX        = DbUtil.getColumnNames(ACDX);
        
        DbInsertFns.insert(TN, ACNX, rec);
    
   }
}
