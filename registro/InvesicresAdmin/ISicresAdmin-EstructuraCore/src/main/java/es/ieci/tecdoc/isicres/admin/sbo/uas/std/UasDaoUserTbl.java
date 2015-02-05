package es.ieci.tecdoc.isicres.admin.sbo.uas.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;



public final class UasDaoUserTbl
{

	//~ Static fields/initializers ---------------------------------------------
/* 
 * @SF-SEVILLA 
 * 02-may-2006 / antmaria
 */
	public static final String		    TN			    = DbConnectionConfig.getSchema() + "IUSERUSERHDR";
	
	public static final DbColumnDef    CD_ID		    = new DbColumnDef("ID",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_NAME		    = new DbColumnDef("NAME",
																		  DbDataType.SHORT_TEXT,
																		  32,
																		  false);
	public static final DbColumnDef    CD_PASSWORD     = new DbColumnDef("PASSWORD",
																		  DbDataType.SHORT_TEXT,
																		  68,
																		  false);
	public static final DbColumnDef    CD_DEPTID	    = new DbColumnDef("DEPTID",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_FLAGS		= new DbColumnDef("FLAGS",
		  																  DbDataType.LONG_INTEGER,
		  																  false);
	public static final DbColumnDef    CD_STAT		    = new DbColumnDef("STAT",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_NUMBADCNTS   = new DbColumnDef("NUMBADCNTS",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_REMARKS	    = new DbColumnDef("REMARKS",
																		  DbDataType.SHORT_TEXT,
																		  254,
																		  true);
	public static final DbColumnDef    CD_CRTRID	    = new DbColumnDef("CRTRID",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_CRTNDATE     = new DbColumnDef("CRTNDATE",
																		  DbDataType.DATE_TIME,
																		  false);
	public static final DbColumnDef    CD_UPDRID	    = new DbColumnDef("UPDRID",
																		  DbDataType.LONG_INTEGER,
																		  true);
	public static final DbColumnDef    CD_UPDATE	    = new DbColumnDef("UPDDATE",
																		  DbDataType.DATE_TIME,
																		  true);
	public static final DbColumnDef    CD_PWDLASTUPDTS = new DbColumnDef("PWDLASTUPDTS",
																		  DbDataType.LONG_DECIMAL,
																		  false);
	public static final DbColumnDef    CD_PWDMBC	    = new DbColumnDef("PWDMBC",
																		  DbDataType.SHORT_TEXT,
																		  1,
																		  false);
	public static final DbColumnDef    CD_PWDVPCHECK   = new DbColumnDef("PWDVPCHECK",
																		  DbDataType.SHORT_TEXT,
																		  1,
																		  false);
	
	public static final DbColumnDef [] ACD			    = { CD_ID, CD_NAME, CD_PASSWORD, CD_DEPTID, CD_FLAGS, 
	      													CD_STAT, CD_NUMBADCNTS, CD_REMARKS, CD_CRTRID, 
	      													CD_CRTNDATE, CD_UPDRID, CD_UPDATE, 
	      													CD_PWDLASTUPDTS, CD_PWDMBC, CD_PWDVPCHECK };
	
	public static final String		    ACN			    = DbUtil.getColumnNames(ACD);
	public static final String		    OCN			    = DbUtil.getColumnNames(CD_ID, CD_NAME, CD_PASSWORD,
																				CD_DEPTID, CD_STAT, CD_NUMBADCNTS,
																				CD_PWDLASTUPDTS, CD_PWDMBC, 
																				CD_PWDVPCHECK);
	
	public static final String		    UaCN		    = DbUtil.getColumnNames(CD_STAT, CD_NUMBADCNTS);
	public static final String		    UbCN		    = DbUtil.getColumnNames(CD_PASSWORD, CD_PWDLASTUPDTS,
																				CD_PWDMBC);

	//~ Constructors -----------------------------------------------------------

	/**
	 * @autor IECISA
	 * 
	 * @since V1.0
	 */
	private UasDaoUserTbl(){}

	/**
	 * @autor IECISA
	 * 
	 * @param id		Identificador del usuario
	 * @return String   Sentencia SQL
	 * 
	 * @since V1.0
	 */
	//~ Methods ----------------------------------------------------------------

	public static String getDefaultQual(int id)
	{

		return "WHERE "+CD_ID.getName()+"="+id;

	}
	
	//~ Update DDBB Methods ----------------------------------------------------------------
	
	/**
	 * @autor IECISA
	 * 
	 * @param NumBadCnts	Número de intentos de login que ha realizado el usuario
	 * @param id			Identificador del usuario
	 * @throws Exception	Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static void updateNumBadCnts(int NumBadCnts, int id)
						  throws Exception
	{

		DbUpdateFns.updateLongInteger(null, TN, CD_NUMBADCNTS.getName(), NumBadCnts, getDefaultQual(id));

	}

	/**
	 * @autor IECISA
	 * 
	 * @param rec			Objeto UasDaoUserRecUa con la información del usuario que debe
	 * 							ser actualizada
	 * @param id			Identificador del usuario
	 * @throws Exception	Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static void incrementNumBadCntsAndLocked(UasDaoUserRecUa rec, int id)
      throws Exception
    {
      
	   DbUpdateFns.update(null, TN, UaCN, rec, getDefaultQual(id));
	   
    }

   /**
    * @autor IECISA
    * 
    * @param rec			Objeto UasDaoUserRecUb con la información del usuario que debe
	* 							ser actualizada
    * @param id				Identificador del usuario
	* @throws Exception		Exception if the application business logic throws an exception
	* 
    * @since V1.0
    */
   public static void updateRow(UasDaoUserRecUb rec, int id)
      throws Exception
   {

      DbUpdateFns.update(null, TN, UbCN, rec, getDefaultQual(id));

   }
	
	//~ Query DDBB Methods ----------------------------------------------------------------
	
	/**
	 * @autor IECISA
	 * 
	 * @param id				Identificador del usuario
	 * @return UasDaoUserRecO 	Objeto UasDaoUserRecO con la información del usuario
	 * @throws Exception		Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static UasDaoUserRecO selectRecO(int id)
									  throws Exception
	{

		UasDaoUserRecO rec = new UasDaoUserRecO();
		
		DbSelectFns.select(null, TN, OCN, getDefaultQual(id), rec);

		return rec;

	}

	/**
	 * @autor IECISA
	 * 
	 * @param name				Login del usuario
	 * @return UasDaoUserRecO	Objeto UasDaoUserRecO con la información del usuario
	 * @throws Exception		Exception if the application business logic throws an exception
	 * 
	 * @since V1.0
	 */
	public static UasDaoUserRecO selectRecO(String name, String entidad)
									 throws Exception
	{

		
		UasDaoUserRecO rec  = new UasDaoUserRecO();
		String		   qual;
		
		qual = "WHERE "+CD_NAME.getName()+"='"+name+"'";
		
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			DbSelectFns.select(dbConn, TN, OCN, qual, rec);
		}catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}
		
		return rec;

	}
	
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2, colNamesIndex3;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "NAME";
      indexName3 = TN + "3";
      colNamesIndex3 = "DEPTID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2, true);
      indexDef3 = new DbIndexDef(indexName3,colNamesIndex3, false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;   
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      indexName3 = TN + "3";
      
      dropIndex(dbConn, TN,indexName);
      dropIndex(dbConn, TN,indexName2);
      dropIndex(dbConn, TN,indexName3);
         
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

}// class
