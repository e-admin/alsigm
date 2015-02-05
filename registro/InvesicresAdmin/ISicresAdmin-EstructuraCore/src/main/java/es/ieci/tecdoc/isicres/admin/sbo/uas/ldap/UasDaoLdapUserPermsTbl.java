package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public final class UasDaoLdapUserPermsTbl
{

	//~ Static fields/initializers ---------------------------------------------
/*
 *@SF-SEVILLA
 *02-may-2006 / antmaria
 */
   public static final String	        TN			    = "IUSERUSERTYPE";

   public static final DbColumnDef    CD_USERID		    = new DbColumnDef("USERID",
																		  DbDataType.LONG_INTEGER,
																		  false);
   public static final DbColumnDef    CD_PRODID	    	= new DbColumnDef("PRODID",
																		  DbDataType.LONG_INTEGER,
																		  36,
																		  false);
   public static final DbColumnDef    CD_TYPE = new DbColumnDef("TYPE",
																		  DbDataType.LONG_INTEGER,
																		  254,
																		  false);

   public static final DbColumnDef [] ACD			    = { CD_USERID, CD_PRODID, CD_TYPE };

   public static final String		    ACN			    = DbUtil.getColumnNames(ACD);

	//~ Constructors -----------------------------------------------------------

   private UasDaoLdapUserPermsTbl(){}

	//~ Methods ----------------------------------------------------------------


	public static void setDefaultPerms(DbConnection dbConn, int userId,
			String entidad) throws Exception {

		UasDaoLdapUserPermsRecA colValues = new UasDaoLdapUserPermsRecA();
		colValues.m_userId = userId;

		colValues.m_prodId = 5;

		colValues.m_type = 1;

		DbInsertFns.insert(dbConn, TN, ACN, colValues);
	}

}// class
