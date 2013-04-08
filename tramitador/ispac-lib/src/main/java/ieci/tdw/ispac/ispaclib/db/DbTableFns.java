package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import org.apache.log4j.Logger;


public final class DbTableFns 
{
	static Logger logger = Logger.getLogger(DbTableFns.class); 

	private DbTableFns()
	{
	}
 
	public static void createTable(DbCnt cnt, String tblName, DbColDef[] colDefs)
	throws ISPACException
	{
//		DbDAOUtils.executeCreate(cnt, composeCreateTableSQLSentence(cnt, tblName, colDefs));
//		DbDAOUtils.executeCreate(cnt, composeAddConstraintPrimeryKeySQLSentence(cnt, tblName, ICatalogAPI.ID_FIELD_NAME));
		createTable(cnt, tblName, colDefs, new String[]{ICatalogAPI.ID_FIELD_NAME});
	}
	

	public static void createTable(DbCnt cnt, String tblName, DbColDef[] colDefs, String[] fieldsPrimaryKey)
	throws ISPACException
	{
		DbDAOUtils.executeCreate(cnt, composeCreateTableSQLSentence(cnt, tblName, colDefs));
		DbDAOUtils.executeCreate(cnt, composeAddConstraintPrimeryKeySQLSentence(cnt, tblName, fieldsPrimaryKey));
	}

	public static void createTable(DbCnt cnt, String tblName, DbColDef[] colDefs, DbIndexDefinition[] indexDefs)
	throws Exception
	{
		createTable(cnt, tblName, colDefs);
		createIndices(cnt, tblName, indexDefs);
	}

	public static void dropTable(DbCnt cnt, String tblName)
	throws Exception
	{
		DbDAOUtils.executeCreate(cnt, getDropTableStmtText(tblName));
	}

	public static void createIndices(DbCnt cnt, String tblName, DbIndexDefinition[] indexDefs)
	throws Exception
	{
		int count, i;
		count = indexDefs.length;

		for (i = 0; i < count; i++) {
			createIndex(cnt, tblName, indexDefs[i]);
		}
	}

	public static void dropIndices(DbCnt cnt, String tblName, DbIndexDefinition[] indexDefs)
	throws Exception
	{
		int count, i;
		count = indexDefs.length;

		for (i = 0; i < count; i++) {
			dropIndex(cnt, tblName, indexDefs[i].getName());
		}
	}

	public static void createIndex(DbCnt cnt, String tblName, DbIndexDefinition indexDef)
	throws ISPACException
	{
		DbDAOUtils.executeCreate(cnt, composeCreateIndexSQLSentence(cnt, tblName, indexDef));
	}
   
	public static String composeCreateNormalSequenceSQLSentence(DbCnt cnt, String sqName)
	{   
		StringBuffer sql = new StringBuffer();
	   
		if (cnt.isSqlServerDbEngine()) {
			sql.append(" INSERT INTO SPAC_SQ_SEQUENCES VALUES('").append(sqName).append("', 0)"); 
		} else if (cnt.isOracleDbEngine()) {
			sql.append(" CREATE SEQUENCE ").append(sqName).append(" START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER");
		} else if (cnt.isDB2DbEngine()) {
			sql.append(" CREATE SEQUENCE ").append(sqName).append(" AS INTEGER NO CACHE ORDER");
		} else {//PostGre
			sql.append(" CREATE SEQUENCE ").append(sqName).append(" INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1");
		}
		
		return sql.toString();
	}
   
	public static String composeDropSequenceSQLSentence(DbCnt cnt, String sqName)
	{	
		StringBuffer sql = new StringBuffer();
	   
		if (cnt.isSqlServerDbEngine()) {
			sql.append(" DELETE FROM SPAC_SQ_SEQUENCES WHERE SEQUENCE_NAME = '").append(sqName).append("'"); 
		}
		else {
			sql.append(" DROP SEQUENCE ").append(sqName);
		}
		
		return sql.toString();
	}

	public static String composeAddConstraintPrimeryKeySQLSentence(DbCnt cnt, String tblName, String fieldName)
	{   
//		StringBuffer sql = new StringBuffer();
//	   
//		String constraintName = "PK_" + Math.abs(tblName.hashCode());
//	   
//		sql.append(" ALTER TABLE ")
//	   	   .append(tblName)
//	   	   .append(" ADD CONSTRAINT ")
//	   	   .append(constraintName)
//	   	   .append(" PRIMARY KEY (")
//	   	   .append(fieldName)
//	   	   .append(")");
//	   
//		return sql.toString();
		return composeAddConstraintPrimeryKeySQLSentence(cnt, tblName, new String[]{fieldName});
	}
	
	public static String composeAddConstraintPrimeryKeySQLSentence(DbCnt cnt, String tblName, String[] fieldsPrimaryKey)
	{   
		StringBuffer sql = new StringBuffer();
	   
		String fields = StringUtils.join(fieldsPrimaryKey, ',');
		
		String constraintName = "PK_" + Math.abs(tblName.hashCode());
	   
		sql.append(" ALTER TABLE ")
	   	   .append(tblName)
	   	   .append(" ADD CONSTRAINT ")
	   	   .append(constraintName)
	   	   .append(" PRIMARY KEY (")
	   	   .append(fields)
	   	   .append(")");
	   
		return sql.toString();
	}

	public static void createSequence(DbCnt cnt, String sqName)
	throws ISPACException
	{	
		DbDAOUtils.executeCreate(cnt, composeCreateNormalSequenceSQLSentence(cnt, sqName));
	}

	public static void dropSequence(DbCnt cnt, String sqName)
	throws Exception
	{
		DbDAOUtils.executeCreate(cnt, composeDropSequenceSQLSentence(cnt, sqName));
	}
   
	public static void dropIndex(DbCnt cnt, String tblName, String indexName)
	throws Exception
	{
		DbDAOUtils.executeCreate(cnt, getDropIndexStmtText(cnt, tblName, indexName));
	}
   
	public static void updateField(DbCnt cnt, String tblName, DbColDef colDef)
	throws Exception
	{	
		StringBuffer sql = new StringBuffer(DBUtil.updateColumn(cnt, tblName,colDef.getName(), getColumnSQLType(cnt, colDef)));
		DbDAOUtils.executeCreate(cnt, sql.toString());
	}
	
	public static void addField(DbCnt cnt, String tblName, DbColDef colDef)
	throws Exception
	{
		// StringBuffer sql = new StringBuffer(" ALTER TABLE ").append(tblName).append(" ADD COLUMN ").append(getColumnSQLToken(cnt,colDef));
		// En SQLServer es ADD, en Postgre COLUMN es opcional
		StringBuffer sql = new StringBuffer(" ALTER TABLE ").append(tblName).append(" ADD ").append(getColumnSQLToken(cnt,colDef));
		DbDAOUtils.executeCreate(cnt, sql.toString());
	}
	
	public static void dropField(DbCnt cnt, String tblName, DbColDef colDef)
	throws Exception
	{
		StringBuffer sql = new StringBuffer(" ALTER TABLE ").append(tblName).append(" DROP COLUMN ").append(colDef.getName());
		DbDAOUtils.executeCreate(cnt, sql.toString());
	}

	private static String composeCreateTableSQLSentence(DbCnt cnt, String tblName, DbColDef[] colDefs)
	throws ISPACException
	{

		StringBuffer tbdr;
		int count, i;
		DbColDef colDef;

		tbdr = new StringBuffer();

		tbdr.append("CREATE TABLE ").append(tblName).append(" (");

		count = colDefs.length;

		for (i = 0; i < count; i++) {
			
			colDef = colDefs[i];

			if (i > 0) tbdr.append(",");
         
			tbdr.append(getColumnSQLToken(cnt,colDef));
		}

		tbdr.append(")");

		return tbdr.toString();
	}
	
	private static  String getColumnSQLType(DbCnt cnt, DbColDef colDef)
	throws ISPACException
	{
		StringBuffer tbdr = new StringBuffer();
		
		int dataType = colDef.getDataType();
		
        tbdr.append(InternalDataType.getNativeDbTypeSQL(cnt, dataType));

        if (InternalDataType.SHORT_TEXT.isTypeOf(dataType)) {
              tbdr.append("(").append(colDef.getMaxLen()).append(")");
        }
        else if (InternalDataType.SHORT_INTEGER.isTypeOf(dataType)) {
	        if (!cnt.isOracleDbEngine() || (cnt.isOracleDbEngine() && colDef.getMaxLen() != 0)) {
	        	tbdr.append("(").append(colDef.getMaxLen()).append(")");
	        }
	    }
        else if (InternalDataType.SHORT_DECIMAL.isTypeOf(dataType)) {
        	if (!cnt.isOracleDbEngine() ||(cnt.isOracleDbEngine() && ( colDef.getMaxLen() != 0 && colDef.getPrecision() != 0))) {
        		tbdr.append("(").append(colDef.getMaxLen()).append(",").append(colDef.getPrecision()).append(")");
        	}
        }
        
        if (!colDef.isNullable() || StringUtils.equalsIgnoreCase(colDef.getName(),"id" ) || StringUtils.equalsNullEmpty(colDef.getName(), "numexp")) {
        	tbdr.append(" NOT NULL ");
        }
        
        return tbdr.toString();
	}
	
	private static  String getColumnSQLToken(DbCnt cnt, DbColDef colDef)
	throws ISPACException
	{
		StringBuffer tbdr = new StringBuffer();
        tbdr.append(colDef.getName())
        .append(" "+getColumnSQLType(cnt, colDef));
        return tbdr.toString();
        
	}
	
	private static String getDropTableStmtText(String tblName)
	{
		StringBuffer tbdr = new StringBuffer();
		tbdr.append("DROP TABLE ").append(tblName);

		return tbdr.toString();
	}

	private static String composeCreateIndexSQLSentence(DbCnt cnt, String tblName, DbIndexDefinition indexDef)
	throws ISPACException
	{
		StringBuffer tbdr;

		tbdr = new StringBuffer();

		if (indexDef.isUnique())
		{
				tbdr.append("CREATE UNIQUE INDEX ");
		}
		else
			tbdr.append("CREATE INDEX ");

		tbdr.append(indexDef.getName())
			.append(" ON ")
			.append(tblName)
			.append(" (")
			.append(indexDef.getColumnNames())
			.append(")");

		return tbdr.toString();
	}

	private static String getDropIndexStmtText(DbCnt cnt, String tblName, String indexName)
	{
		StringBuffer tbdr = new StringBuffer();
		
		tbdr.append("DROP INDEX ");
		if (cnt.isSqlServerDbEngine())
			tbdr.append(tblName).append(".").append(indexName);
		else
			tbdr.append(indexName);
		
		return tbdr.toString();
	}

	//TODO HECHO SOLO PARA POSTGRE
	public static boolean isTableCreated(DbCnt cnt, String tblName)
	throws ISPACException
	{
//		DbResultSet res = null;
//		boolean ret = true;
//		try{
//			//si no hay datos 
//			StringBuffer bfr = new StringBuffer(" select count(*) from pg_tables where schemaname='public' AND tablename='")
//			.append(tblName.toLowerCase()).append("' ");
//			
//			res = DbDAOUtils.executeQuery(cnt, bfr.toString());
//			if (res!=null && res.getResultSet().next())
//				ret = res.getResultSet().getInt(1)==1;
//			else 
//				ret = false;
//		
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//			return false;
//			
//		}finally{
//			if (res!=null)
//				res.close();
//		}
//		return ret;
		
		DbResultSet rs = null;
		boolean ret = false;
		try {
			rs = cnt.getTables(tblName);
			if (rs != null && rs.getResultSet().next())
				ret = true;
			else
				ret = false;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ret = false;

		} finally {
			if (rs != null)
				rs.close();
		}
		return ret;
	}
	
	public static boolean isTableFilled(DbCnt cnt, String tblName)
	throws ISPACException
	{
		DbResultSet res = null;
		boolean ret = false;
		
		try {
			//si no hay datos 
			StringBuffer bfr = new StringBuffer(" SELECT count(1) FROM  ");
			bfr.append(tblName);
			res = DbDAOUtils.executeQuery(cnt, bfr.toString());
			if (res!=null && res.getResultSet().next())
				ret = res.getResultSet().getInt(1)>0;
		}
		catch (Exception e) {
			logger.error(e.getMessage());	
		}
		finally {
			if (res!=null)
				res.close();
		}
		
		return ret;
	}
	
}