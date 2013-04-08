package es.ieci.tecdoc.isicres.admin.sbo.fss.core;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbEngine;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;



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
    	  dbConn.open(DBSessionManager.getSession());
   
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
		   dbConn.open(DBSessionManager.getSession());
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
	   	dbConn.open(DBSessionManager.getSession());
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
	   	  dbConn.open(DBSessionManager.getSession()); 
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
	   	dbConn.open(DBSessionManager.getSession());
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
	   	dbConn.open(DBSessionManager.getSession());
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
	   	dbConn.open(DBSessionManager.getSession());
         DbTableFns.dropIndex(dbConn, tblName,indexName);
	   }
	   catch (Exception e) {
		   throw e;
	   }finally{
	   	dbConn.close();
	   }
   }



}
