package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;


public final class UasDaoSysTbl
{

	//~ Static fields/initializers ---------------------------------------------

	public static final String		    TN			    = "IUSERUSERSYS";
	
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
																		  false);
	public static final DbColumnDef    CD_FLAGS        = new DbColumnDef("FLAGS",
	                                                     DbDataType.LONG_INTEGER,
	                                                     false);
	public static final DbColumnDef    CD_UPDRID	      = new DbColumnDef("UPDRID",
																		  DbDataType.LONG_INTEGER,
																		  true);
	public static final DbColumnDef    CD_UPDDATE	    = new DbColumnDef("UPDDATE",
																		  DbDataType.DATE_TIME,
																		  true);
	
	public static final DbColumnDef [] ACD			    = { CD_MAXBADCNTS, CD_PWDVP, CD_PWDMBC, 
	      													CD_PWDMINLEN, CD_PWDEXPIREDAP, 
	      													CD_NUMPWDLOCK, CD_FLAGS, CD_UPDRID, CD_UPDDATE };
	
	public static final String		    ACN			    = DbUtil.getColumnNames(ACD);
	
	public static final String		    OCN			    = DbUtil.getColumnNames(CD_MAXBADCNTS,
																				CD_PWDVP, CD_PWDMINLEN,
																				CD_NUMPWDLOCK, CD_FLAGS);

	
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

	public static UasDaoSysRecO selectRecO()
									 throws Exception
	{

		UasDaoSysRecO rec = new UasDaoSysRecO();
		
		DbSelectFns.select(TN, OCN, null, rec);

		return rec;

	}
	
	public static void initContentsUserSysTbl() throws Exception
	{
	   String stmtText;
	   int    maxBadCnts = 3;
	   double  pwdvp = -1;
	   String pwdMbc = "N";
	   short  pwdMinLen = 3;
	   int    flags = 0;
	   double pwdExpiredAp = -1;
	   short  numPwdLock = 0;
	   
      stmtText = "INSERT INTO " + TN + " (MAXBADCNTS,PWDVP,PWDMBC,PWDMINLEN,PWDEXPIREDAP,NUMPWDLOCK,FLAGS) VALUES( " +
                 maxBadCnts + "," + pwdvp + ",'" + pwdMbc + "'," + pwdMinLen + "," + pwdExpiredAp + ","+
                  numPwdLock + "," + flags + " )";
      
      DbUtil.executeStatement(stmtText);
	}
	
// **************************************************************************
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN,ACD);
   }
   
   public static void dropTable() throws Exception
   {
      DbTableFns.dropTable(TN);
   }
   
   
}
 // class
