package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

public final class UasDaoSysTbl
{

	//~ Static fields/initializers ---------------------------------------------
/* 
 * @SF-SEVILLA 
 * 02-may-2006 / antmaria
 */
	public static final String		    TN			    = DbConnectionConfig.getSchema() +  "IUSERUSERSYS";
	
	public static final DbColumnDef    CD_MAXBADCNTS   = new DbColumnDef("MAXBADCNTS",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_PWDVP	    = new DbColumnDef("PWDVP",
																		  DbDataType.LONG_DECIMAL,
																		  false);
	public static final DbColumnDef    CD_PWDMBC	    = new DbColumnDef("PWDMBC",
																		  DbDataType.SHORT_TEXT,
																		  1,
																		  false);
	public static final DbColumnDef    CD_PWDMINLEN    = new DbColumnDef("PWDMINLEN",
																		  DbDataType.SHORT_INTEGER,
																		  false);
	public static final DbColumnDef    CD_PWDEXPIREDAP = new DbColumnDef("PWDEXPIREDAP",
																		  DbDataType.LONG_DECIMAL,
																		  false);
	public static final DbColumnDef    CD_NUMPWDLOCK   = new DbColumnDef("NUMPWDLOCK",
																		  DbDataType.SHORT_INTEGER,
																		  true);
	public static final DbColumnDef    CD_UPDRID	    = new DbColumnDef("UPDRID",
																		  DbDataType.LONG_INTEGER,
																		  true);
	public static final DbColumnDef    CD_UPDDATE	    = new DbColumnDef("UPDDATE",
																		  DbDataType.DATE_TIME,
																		  true);
	
	public static final DbColumnDef [] ACD			    = { CD_MAXBADCNTS, CD_PWDVP, CD_PWDMBC, 
	      													CD_PWDMINLEN, CD_PWDEXPIREDAP, 
	      													CD_NUMPWDLOCK, CD_UPDRID, CD_UPDDATE };
	
	public static final String		    ACN			    = DbUtil.getColumnNames(ACD);
	
	public static final String		    OCN			    = DbUtil.getColumnNames(CD_MAXBADCNTS,
																				CD_PWDVP, CD_PWDMINLEN);

	
	//	~ Constructors -----------------------------------------------------------

	/**
	 * @autor IECISA
	 * 
	 * @since V1.0
	 */
	private UasDaoSysTbl(){}

	/**
	 * @autor IECISA
	 * 
	 * @return UasDaoSysRecO
	 * @throws Exception
	 * 
	 * @since V1.0
	 */
	//~ Methods ----------------------------------------------------------------

	public static UasDaoSysRecO selectRecO(String entidad)
									 throws Exception
	{

		UasDaoSysRecO rec = new UasDaoSysRecO();
		
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			DbSelectFns.select(dbConn, TN, OCN, null, rec);
		}catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}

		return rec;

	}
	
	public static void initContentsUserSysTbl(DbConnection dbConn) throws Exception
	{
	   String stmtText;
	   int    maxBadCnts = 3;
	   double  pwdvp = -1;
	   String pwdMbc = "N";
	   short  pwdMinLen = 3;
	   double pwdExpiredAp = -1;
	   short  numPwdLock = 0;
	   
      stmtText = "INSERT INTO " + TN + " (MAXBADCNTS,PWDVP,PWDMBC,PWDMINLEN,PWDEXPIREDAP,NUMPWDLOCK) VALUES( " +
                 maxBadCnts + "," + pwdvp + ",'" + pwdMbc + "'," + pwdMinLen + "," + pwdExpiredAp + ","+
                  numPwdLock + " )";
      
      DbUtil.executeStatement(dbConn, stmtText);
	}
	
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN,ACD);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.dropTable(dbConn,  TN);
   }
   
   
}
 // class
