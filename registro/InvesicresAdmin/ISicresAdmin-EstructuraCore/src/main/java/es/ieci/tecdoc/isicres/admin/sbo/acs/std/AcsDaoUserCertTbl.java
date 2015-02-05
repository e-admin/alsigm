package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;


/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
public class AcsDaoUserCertTbl {
	public static final String TN = "IUSERUSERCERT";

	public static final DbColumnDef CD_ID = new DbColumnDef("ID",
			DbDataType.LONG_INTEGER, true);

	public static final DbColumnDef CD_CERT = new DbColumnDef("CERT",
			DbDataType.SHORT_TEXT, 254, true);

	public static final DbColumnDef CD_LDAPDN = new DbColumnDef("LDAPDN",
			DbDataType.SHORT_TEXT, 500, true);

	public static final DbColumnDef[] ACD = { CD_ID, CD_CERT, CD_LDAPDN };
	public static final String ACN = DbUtil.getColumnNames(ACD);
	
	public static void createTable(DbConnection dbConn) throws Exception {
		
		String indexName, indexName2;
		String colNamesIndex, colNamesIndex2;
		DbIndexDef indexDef, indexDef2 ;

		indexName = TN + "1";
		colNamesIndex = "ID";
		indexName2 = TN + "2";
		colNamesIndex2 = "CERT";

		indexDef = new DbIndexDef(indexName, colNamesIndex, false);
		indexDef2 = new DbIndexDef(indexName2, colNamesIndex2, false);

		DbTableFns.createTable(dbConn, TN, ACD);
		DbTableFns.createIndex(dbConn, TN, indexDef);
		DbTableFns.createIndex(dbConn, TN, indexDef2);
	}

}
