package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbEngine;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbInsertFns;
import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;



public final class FssDaoVolVolTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   public static final String        TN          =DbConnectionConfig.getSchema() + "IVOLVOLTBL";

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
   ("FILEVAL", DbDataType.LONG_BINARY, 2097152, false);
   
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
   public static void createTable(String entidad) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      String     stmt;
          
      indexName = TN + "1";
      colNamesIndex = "LOCID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      DbConnection dbConn=new DbConnection();
      try{
    	  dbConn.open(DBSessionManager.getSession(entidad));
   
          DbTableFns.createTable(dbConn, TN, ACD);   
          DbTableFns.createIndex(dbConn, TN, indexDef);  
          
          if (dbConn.getEngine() == DbEngine.ORACLE)
          {
             stmt = "CREATE INDEX IVOLVOLTBLFTS ON IVOLVOLTBL(FILEVAL) INDEXTYPE IS CTXSYS.CONTEXT";         
             DbUtil.executeStatement(dbConn, stmt);
          }   
    	  
      }catch (Exception e) {
		throw e;
	}finally{
		dbConn.close();
	}
         
   }
   /**
    * Crea politicas en la tabla correspondiente a un volumen de base dedatos
    * debe ejecutarse fuera de una transación.
    * 
    * @throws Exception
    */
   public static void createVolPolicy(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
		   dbConn.open(DBSessionManager.getSession(entidad));
	      if (dbConn.getEngine() == DbEngine.SQLSERVER)
	      {    
	         String stmt;
	         String indexName;
	         
	         indexName = TN + "1";
	         
	         stmt = "{Call sp_fulltext_table('" + TN + "','create''IDOCFTSCTLG','" + indexName + "')}";
		      DbUtil.executeStatement(dbConn, stmt);
		      stmt = "{Call sp_fulltext_column('" + TN + "','" + CD_FILEVAL + "','add',0x0409, '" + CD_FILEEXT + "')}";
		      DbUtil.executeStatement(dbConn, stmt);
		      stmt = "{Call sq_fulltext_table('" + TN + "','start_full')}";
		      DbUtil.executeStatement(dbConn, stmt);
		      stmt = "{Call sq_fulltext_table('" + TN + "','start_change_tracking')}";
		      DbUtil.executeStatement(dbConn, stmt);	      
		      stmt = "{Call sq_fulltext_table('" + TN + "','start_background_updateindex')}";
		      DbUtil.executeStatement(dbConn, stmt);
	      }
	   }catch (Exception e) {
	    		throw e;
	    	}finally{
	    		dbConn.close();
	    	}
   }
   
   public static void dropTable(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
	      String indexName;            
	      
	      indexName = TN + "1";      
	      dropIndex(TN,indexName, entidad);
	         
	      DbTableFns.dropTable(dbConn, TN);     
	   }
	   catch (Exception e) {
	   	throw e;
	   }finally{
	   	dbConn.close();
	   }
   }
   
   
   public static void insertRow(FssDaoVolVolRecAc rec, int fileSize, String entidad)
                      throws Exception
   {
      
	   DbConnection dbConn=new DbConnection();
	   try{
	   	  dbConn.open(DBSessionManager.getSession(entidad)); 
	      DbColumnDef   CD_FILEVALX = new DbColumnDef
	                                 ("FILEVAL", DbDataType.LONG_BINARY, fileSize, false);      
	      DbColumnDef[] ACDX        = {CD_LOCID, CD_EXTID1, CD_EXTID2, CD_EXTID3, CD_EXTID4, CD_FILEEXT, CD_FILEVALX};
	      String        ACNX        = DbUtil.getColumnNames(ACDX);
	      
	      DbInsertFns.insert(dbConn, TN, ACNX, rec);
	   }
	   catch (Exception e) {
	   	throw e;
	   }finally{
	   	dbConn.close();
	   }
   }
   
   public static FssDaoVolVolRecAc selectRecAc(String locId, String entidad)
                                   throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   FssDaoVolVolRecAc rec=null;
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
	   	rec = new FssDaoVolVolRecAc();
	   
	   	DbSelectFns.select(dbConn, TN, ACN, getDefaultQual(locId), (DbOutputRecord)rec);
	   }catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}
      return rec;

   }
   
   public static void deleteRow(String locId, String entidad)
   throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
	   	DbDeleteFns.delete(dbConn, TN, getDefaultQual(locId));
	   }
	   catch (Exception e) {
	   	throw e;
	   }finally{
	   	dbConn.close();
	   }
   }
   
   private static void dropIndex(String tblName, String indexName, String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
         DbTableFns.dropIndex(dbConn, tblName,indexName);
	   }
	   catch (Exception e) {
		   throw e;
	   }finally{
	   	dbConn.close();
	   }
   }



}
