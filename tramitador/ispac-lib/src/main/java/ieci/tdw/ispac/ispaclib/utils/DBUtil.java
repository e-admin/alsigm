package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author Ildefonso Noreña
 *
 */
public class DBUtil {
	
	private static final Logger LOGGER = Logger.getLogger(DBUtil.class);
	

    /* =======================================================================
     * Patrones de fechas en base de datos
     * =======================================================================*/

    private final static String JAVA_DATE_PATTERN 						= "dd/MM/yyyy"; 
    private final static String JAVA_TIMESTAMP_PATTERN					= "dd/MM/yyyy HH:mm:ss";
	
	private final static String ORACLE_JAVA_DATE_PATTERN 				= "dd/MM/yyyy";
    private final static String ORACLE_SQL_DATE_PATTERN 				= "DD/MM/YYYY"; 
    private final static String ORACLE_JAVA_TIMESTAMP_PATTERN			= "dd/MM/yyyy HH:mm:ss";
    private final static String ORACLE_SQL_TIMESTAMP_PATTERN			= "DD/MM/YYYY HH24:MI:SS";

    private final static String SQLSERVER_JAVA_DATE_PATTERN 			= "dd/MM/yyyy";
    private final static String SQLSERVER_SQL_DATE_PATTERN 				= "103";
    private final static String SQLSERVER_JAVA_TIMESTAMP_PATTERN		= "dd/MM/yyyy HH:mm:ss";
    private final static String SQLSERVER_SQL_TIMESTAMP_PATTERN			= "103";

    private final static String POSTGRESQL_JAVA_DATE_PATTERN 			= "dd/MM/yyyy";
    private final static String POSTGRESQL_SQL_DATE_PATTERN 			= "DD/MM/YYYY";
    private final static String POSTGRESQL_JAVA_TIMESTAMP_PATTERN		= "dd/MM/yyyy HH:mm:ss";
    private final static String POSTGRESQL_SQL_TIMESTAMP_PATTERN		= "DD/MM/YYYY HH24:MI:SS";

    private final static String DB2_JAVA_DATE_PATTERN 					= "yyyy-MM-dd";
    //private final static String DB2_SQL_DATE_PATTERN 					= "YYYY-MM-DD HH24:MI:SS";
    private final static String DB2_JAVA_TIMESTAMP_PATTERN				= "yyyyMMddHHmmss";
    //private final static String DB2_SQL_TIMESTAMP_PATTERN				= "YYYY-MM-DD HH24:MI:SS";

    
    /* =======================================================================
     * Formateadores de fechas para las bases de datos
     * =======================================================================*/

    private final static SimpleDateFormat DATE_FORMAT 					= new SimpleDateFormat(JAVA_DATE_PATTERN);
    private final static SimpleDateFormat TIMESTAMP_FORMAT 				= new SimpleDateFormat(JAVA_TIMESTAMP_PATTERN);

    private final static SimpleDateFormat ORACLE_DATE_FORMAT 			= new SimpleDateFormat(ORACLE_JAVA_DATE_PATTERN);
    private final static SimpleDateFormat ORACLE_TIMESTAMP_FORMAT 		= new SimpleDateFormat(ORACLE_JAVA_TIMESTAMP_PATTERN);
    
    private final static SimpleDateFormat SQLSERVER_DATE_FORMAT			= new SimpleDateFormat(SQLSERVER_JAVA_DATE_PATTERN);
    private final static SimpleDateFormat SQLSERVER_TIMESTAMP_FORMAT	= new SimpleDateFormat(SQLSERVER_JAVA_TIMESTAMP_PATTERN);
    
    private final static SimpleDateFormat POSTGRESQL_DATE_FORMAT 		= new SimpleDateFormat(POSTGRESQL_JAVA_DATE_PATTERN);
    private final static SimpleDateFormat POSTGRESQL_TIMESTAMP_FORMAT 	= new SimpleDateFormat(POSTGRESQL_JAVA_TIMESTAMP_PATTERN);
    
    private final static SimpleDateFormat DB2_DATE_FORMAT 				= new SimpleDateFormat(DB2_JAVA_DATE_PATTERN);
    private final static SimpleDateFormat DB2_TIMESTAMP_FORMAT 			= new SimpleDateFormat(DB2_JAVA_TIMESTAMP_PATTERN);

    
    
    
    /*******BUSQUEDA DOCUMENTAL**********************************************/
    private final static String SQL_SERVER_TEXTS_JOIN = " AND ";
    private final static String ORACLE_TEXTS_JOIN = " AND ";
    private final static String POSTGRES_TEXTS_JOIN = " & ";
    private final static String DB2_TEXTS_JOIN = "%";
    
    
    
    //Dicionario por defecto 
    //TODO: Deberia de cargarse en un properties
    public final static String POSTGRES_DEFAULT_DICTIONARY = "public.default_spanish";
    
    
    public static String getToDateByBD(DbCnt cnt, String date) 
    		throws ISPACException {
    	try {
    		return getToDateByBD(cnt, DATE_FORMAT.parse(date));
    	} catch (ParseException e) {
    		throw new ISPACException("Error al parsear la fecha [" + date 
    				+ "] con el formato [" + DATE_FORMAT.toPattern() + "]", e);
    	}
    }

    public static String getSqlLimitBD(DbCnt cnt, String sqlBase, String order,
			int limit, String groupBy) {

    	// PostgreSQL -> LIMIT al final de la consulta
		if (cnt.isPostgreSQLDbEngine()) {

			if (StringUtils.isNotEmpty(groupBy)) {
				sqlBase += " GROUP BY " + groupBy;
			}
			if (StringUtils.isNotEmpty(order)) {
				sqlBase += " ORDER BY " + order;
			}
			if (limit > 0) {
				sqlBase += " LIMIT " + limit + " ";
			}
		}
		// DB2Db -> FETCH FIRST ROWS ONLY al final de la consulta
		else if (cnt.isDB2DbEngine()) {

			if (StringUtils.isNotEmpty(groupBy)) {
				sqlBase += " GROUP BY " + groupBy;
			}

			if (StringUtils.isNotEmpty(order)) {
				sqlBase += " ORDER BY " + order;
			}

			if (limit > 0) {
				sqlBase += " FETCH FIRST " + limit + " ROWS ONLY ";
			}
		}
		// Oracle -> se establece el ROWNUM con un SELECT * FROM sobre la consulta final 
		// SqlServer -> se establece SET ROWCOUNT en la conexión
		else if (cnt.isOracleDbEngine() || cnt.isSqlServerDbEngine()) {
			
			if (StringUtils.isNotEmpty(groupBy)) {
				sqlBase += " GROUP BY " + groupBy;
			}

			if (StringUtils.isNotEmpty(order)) {
				sqlBase += " ORDER BY " + order;
			}
		}

		return sqlBase;
	}
    
    /**
     * Devuelve la sentencia adecuada según la bbdd para modificar la columna de una tabla
     * @param cnt Conexión a la bbdd
     * @param tableName Nombre de la tabla
     * @param columnName Nombre de la columna
     * @param tipo Tipo nuevo de la columna
     * @return
     */
    public static String updateColumn(DbCnt cnt, String tableName, String columnName, String tipo){
    
    	String sentencia="";
    	if(cnt.isPostgreSQLDbEngine()){
    		sentencia="ALTER TABLE "+tableName+" ALTER COLUMN "+columnName+" TYPE "+tipo;
    	}
    	else if(cnt.isDB2DbEngine()){
    		sentencia= "ALTER TABLE "+tableName+" ALTER COLUMN "+columnName+" SET DATA TYPE "+tipo;
    	}
    	else if (cnt.isOracleDbEngine()){
    		sentencia= "ALTER TABLE "+tableName+" MODIFY "+columnName+" "+tipo;
    	}
    	else if(cnt.isSqlServerDbEngine()){
    		sentencia= "ALTER TABLE "+tableName+" ALTER COLUMN "+columnName+" "+tipo;
    	}
    	return sentencia;
    	
    }
    /**
     * Listado de sentencias a ejecutar para activar la busqueda documental a una tabla en una bbdd de sql server
     * @param cnt
     * @param nameTable
     * @return
     */
    public static String[] activateSearchInTable(DbCnt cnt,String nameField, String nameTable){
    	String [] sentencias=null;
    	String str = new String();
    	if(cnt.isSqlServerDbEngine()){
    		sentencias=new String [4];
    		str="execute sp_fulltext_table  '"+nameTable+"','create','nombrecatalogo','ID_TS'";
    		sentencias[0]=str;
    		str="execute sp_fulltext_column '"+nameTable+"', '"+nameField+"' , 'add' , '3082'";
			sentencias[1]=str;
    		str="execute sp_fulltext_table '"+nameTable+"','start_change_tracking'";
    		sentencias[2]=str;
    		str="execute sp_fulltext_table '"+nameTable+"','start_background_updateindex'";
    		sentencias[3]=str;
    	}
    	return sentencias;
    }
    
    public static String CheckFullTextInTable(DbCnt cnt){
    	
    	String sql="";
    	//Solo hay que realizar esta comprobación para las bbdd de sqlserver
    	if(cnt.isSqlServerDbEngine()){
    		
    		sql="exec sp_help_fulltext_tables ";
    	}
    	return sql;
    }
    

    public static String[] addDocumentarySearch(DbCnt cnt, String nameField, String nameTable) {
    		String [] sentencias=null;
			String str = new String();
			String nameSearch=nameField+"_ts";
			String nameIndex= nameField+"_index";
			if (cnt.isPostgreSQLDbEngine()) {
				nameField=nameField.toLowerCase();
				nameSearch=nameSearch.toLowerCase();
				
				str = "ALTER TABLE " + nameTable + " ADD COLUMN  "+  nameSearch +" tsvector";
				//Debido a que la busqueda documental se realiza de distinta manera a partir de la 8.3 se ha de comprobar.
				if(cnt.getMajorVersion()==8 && cnt.getMinorVersion()>=3){
					sentencias=new String[4];
					sentencias[0]=str;
					str="CREATE INDEX "+nameIndex +" ON "+nameTable+"  USING gist(to_tsvector('"+POSTGRES_DEFAULT_DICTIONARY+"', "+nameField+"))";
					sentencias[1]=str;
					str="CREATE TRIGGER "+nameTable+"_update_"+nameField +" BEFORE INSERT OR UPDATE ON "+nameTable+" FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('"+nameSearch+"','public.default_spanish' , '"+nameField+"')";
					sentencias[2]=str;
					str="UPDATE "+nameTable +" set "+nameSearch+"=to_tsvector('"+POSTGRES_DEFAULT_DICTIONARY+"' , "+nameField+")";
					sentencias[3]=str;
				}
				else {
					sentencias=new String[3];
					sentencias[0]=str;
					str="UPDATE "+nameTable +" set "+nameSearch+"=to_tsvector("+nameField+")";
					sentencias[1]=str;
					str="CREATE TRIGGER "+nameTable+"_update_"+nameField +" BEFORE INSERT OR UPDATE ON "+nameTable+" FOR EACH ROW EXECUTE PROCEDURE tsearch2('"+nameSearch+"', '"+nameField+"')";
					sentencias[2]=str;
				}

			}
			
			if(cnt.isOracleDbEngine()){
				sentencias=new String[1];

				if (cnt.getMajorVersion() >= 10) { // Oracle 10g+
					str="CREATE INDEX "+nameSearch+" ON "+nameTable+" ("+nameField+") INDEXTYPE IS CTXSYS.CONTEXT parameters ('sync (on commit)')";
				} else { // Oracle 9i
					str="CREATE INDEX "+nameSearch+" ON "+nameTable+" ("+nameField+") INDEXTYPE IS ctxsys.CONTEXT ";
				}
				
				sentencias[0]=str;
			}
			
			if(cnt.isSqlServerDbEngine()){

				sentencias=new String[1];
				str="execute sp_fulltext_column '"+nameTable+"', '"+nameField+"' , 'add' , '3082'";
				sentencias[0]=str;
				
		
			}
			
			if(cnt.isDB2DbEngine()){
				sentencias=new String[0];
			   /* sentencias[0]= " db2text CREATE INDEX DB2EXT."+nameSearch+"  FOR TEXT ON "+nameTable+"("+nameField+") FORMAT TEXT UPDATE FREQUENCY NONE UPDATE MINIMUM 1 REORGANIZE AUTOMATIC  CACHE TABLE ("+nameField+" AS "+nameField+") PERSISTENT  PCTFREE 1 MAXIMUM CACHE SIZE 9 INDEX CONFIGURATION ( TREATNUMBERSASWORDS 1 , INDEXSTOPWORDS 1) CONNECT TO PRUEBA ";
				sentencias[1]="ACTIVATE CACHE FOR INDEX DB2EXT."+nameSearch+" FOR TEXT";
				sentencias[2]="UPDATE INDEX  DB2EXT."+nameSearch+"  FOR TEXT";*/

			}
			return sentencias;
    }
    
    public static String[] deleteDocumentarySearch(DbCnt cnt, String nameField, String nameTable){

    		String [] sentencias=null;
			String str = new String();
			String nameSearch=" "+nameField+"_ts ";
			if (cnt.isPostgreSQLDbEngine()) {
				sentencias=new String[2];
				str = "DROP TRIGGER " + nameTable + "_update_"+nameField+" ON "+nameTable;
				sentencias[0]=str;
				str="ALTER TABLE "+nameTable +" DROP COLUMN "+nameSearch;
				sentencias[1]=str;
	
			}
			if(cnt.isOracleDbEngine()){
				sentencias=new String[1];
				
				str="DROP INDEX "+nameSearch;
				sentencias[0]=str;
			}
			
			if(cnt.isSqlServerDbEngine()){

				sentencias=new String[1];
				str="execute sp_fulltext_column '"+nameTable+"', '"+nameField+"' , 'drop' , '3082'";
				sentencias[0]=str;
			}
			
			if(cnt.isDB2DbEngine()){

				sentencias=new String[1];
				str="db2text \"DROP INDEX DB2EXT."+nameSearch+" FOR TEXT CONNECT TO PRUEBA\"";

				sentencias[0]=str;
			}
		return sentencias;
    	
    }
    
    public static String getToTimestampByBD(DbCnt cnt, String timestamp)
			throws ISPACException {
    	try {
    		return getToTimestampByBD(cnt, new Timestamp(TIMESTAMP_FORMAT.parse(timestamp).getTime()));
    	} catch (ParseException e) {
    		throw new ISPACException("Error al parsear la fecha [" + timestamp 
    				+ "] con el formato [" + TIMESTAMP_FORMAT.toPattern() + "]", e);
    	}
	}

    public static String getNativeTextsJoinSyntax(DbCnt cnt) throws ISPACException {

 	   String str = new String();

 	   if (cnt.isSqlServerDbEngine())
 		      str = SQL_SERVER_TEXTS_JOIN;
 		   else if (cnt.isOracleDbEngine())
 			   str = ORACLE_TEXTS_JOIN;
 		   else if (cnt.isPostgreSQLDbEngine()){
 			   str = POSTGRES_TEXTS_JOIN;
 		   }
 		   else if(cnt.isDB2DbEngine()){
 			   //TODO Db2 Por ahora no se utiliza, cuando se meta la búsqueda documental revisarlo
 			   str = DB2_TEXTS_JOIN;
 		   }
 		  

 	  return str;
 	}
    
    public static String formatValueField(DbCnt cnt, String valueField) throws ISPACException
    {
 	   
 	   //En db2 no se está utilizando la búsqueda documental.
 	   if(cnt.isDB2DbEngine()){
 		   return valueField;
 	   }
 	   
 	   String fvf = new String();
 	   
 	   valueField = valueField.trim();
 	   String [] words = valueField.split(" ",0);
 	   if (words != null)
 	   {
 		   String joinString = getNativeTextsJoinSyntax(cnt);
 		   
 		   for (int i=0; i<words.length; i++)
 		   {
 			   if (i > 0)
 				   fvf = fvf + joinString + words[i];
 			   else
 				   fvf = fvf + words[i];
 		   }
 	   }
 	   else
 		   fvf = valueField;
 	   
 	   return fvf;
    }
    
    public static String generateContainsValue(DbCnt cnt, String valueField) {
 		try {
 			// Añadido para tratar los espacios en blanco en las búsquedas
 			String formattedValueField = formatValueField(cnt, valueField);
 			
 			if (cnt.isPostgreSQLDbEngine())   
 				return "to_tsquery('" + formattedValueField + "')";
 			else if(cnt.isDB2DbEngine()){
 				return valueField;
 			}
 			else{
 				
 				return formattedValueField;
 			}
 		} catch (ISPACException e) {
 			LOGGER.error("Error al generar el valor incluido en el CONTAINS", e);
 			return null;
 		}
 	   }
    
    
    /**
     * 
     * @deprecated
     */
    public static String getContainsOperator(DbCnt cnt, String nameField, String valueField) {
    	return getContainsOperator(cnt, null, nameField, valueField);
    }
    
	public static String getContainsOperator(DbCnt cnt, String tableName,
			String nameField, String valueField) {
		
		String value = generateContainsValue(cnt, valueField);
		StringBuffer buf = new StringBuffer(" ");
		if (valueField != null) {

			StringBuffer qualifiedFieldName = new StringBuffer();
			if (StringUtils.isNotBlank(tableName)) {
				qualifiedFieldName.append(tableName).append(".");
			}
			qualifiedFieldName.append(nameField);

			if (cnt.isOracleDbEngine()) {
				buf.append("CONTAINS(").append(qualifiedFieldName)
						.append(",'" + value + "')>0");
			} else if (cnt.isPostgreSQLDbEngine()) {
				// columna de indice documental @@ to_tsquery valores
				buf.append(" " + nameField + "_ts @@ " + value);
			} else if (cnt.isSqlServerDbEngine()) {
				buf.append(" CONTAINS(").append(qualifiedFieldName)
						.append(",'" + value + "')");
			} else if (cnt.isDB2DbEngine()) {
				// TODO CAMBIAR POR CONTAINS CUANDO SE TENGA IMPLEMENTADO
				// ADDDOCUMENTARYSEARCH Y DELETE PARA DB2
				buf.append(" ").append(qualifiedFieldName).append(" LIKE '%")
						.append(DBUtil.replaceQuotes(value)).append("%' ");
			}
		}
		return buf.toString();
   }
   
    
    public static String getToDateByBD(DbCnt cnt, Date date) 
    		throws ISPACException {

    	if (cnt == null) {
    		throw new ISPACException("Conexión no establecida");
    	}
    	
        if (cnt.isOracleDbEngine()) {
            return new StringBuffer()
            	.append("TO_DATE('")
            	.append(ORACLE_DATE_FORMAT.format(date))
            	.append("', '")
            	.append(ORACLE_SQL_DATE_PATTERN)
            	.append("')")
            	.toString();
        } else if (cnt.isSqlServerDbEngine()) {
            return new StringBuffer()
            	.append("CONVERT(DATETIME, '")
            	.append(SQLSERVER_DATE_FORMAT.format(date))
            	.append("', ")
            	.append(SQLSERVER_SQL_DATE_PATTERN)
            	.append(")")
            	.toString();
        } else if (cnt.isPostgreSQLDbEngine()) {
            return new StringBuffer()
	        	.append("TO_DATE('")
	        	.append(POSTGRESQL_DATE_FORMAT.format(date))
	        	.append("', '")
	        	.append(POSTGRESQL_SQL_DATE_PATTERN)
	        	.append("')")
	        	.toString();
        } else if (cnt.isDB2DbEngine()) {
//            return new StringBuffer()
//	        	.append("DATE(TIMESTAMP_FORMAT('")
//	        	.append(DB2_DATE_FORMAT.format(date))
//	        	.append("', '")
//	        	.append(DB2_SQL_DATE_PATTERN)
//	        	.append("'))")
//	        	.toString();
			return new StringBuffer()
				.append("DATE('")
				.append(DB2_DATE_FORMAT.format(date))
				.append("')")
				.toString();
        	
        } else {
            throw new ISPACException("Tipo de Base de datos no permitido: '" 
            		+ cnt.getProductName() + "'");
        }
    }

    public static String getToTimestampByBD(DbCnt cnt, Timestamp timestamp) 
			throws ISPACException {

		if (cnt == null) {
			throw new ISPACException("Conexión no establecida");
		}

		if (cnt.isOracleDbEngine()) {
			return new StringBuffer()
				.append("TO_DATE('")
				.append(ORACLE_TIMESTAMP_FORMAT.format(timestamp))
				.append("', '")
				.append(ORACLE_SQL_TIMESTAMP_PATTERN)
				.append("')")
				.toString();
		} else if (cnt.isSqlServerDbEngine()) {
			return new StringBuffer()
				.append("CONVERT(DATETIME, '")
				.append(SQLSERVER_TIMESTAMP_FORMAT.format(timestamp))
				.append("', ")
				.append(SQLSERVER_SQL_TIMESTAMP_PATTERN)
				.append(")")
				.toString();
		} else if (cnt.isPostgreSQLDbEngine()) {
			return new StringBuffer()
				.append("TO_TIMESTAMP('")
				.append(POSTGRESQL_TIMESTAMP_FORMAT.format(timestamp))
				.append("', '")
				.append(POSTGRESQL_SQL_TIMESTAMP_PATTERN)
				.append("')")
				.toString();
		} else if (cnt.isDB2DbEngine()) {
//			return new StringBuffer()
//				.append("TIMESTAMP_FORMAT('")
//				.append(DB2_TIMESTAMP_FORMAT.format(timestamp))
//				.append("', '")
//				.append(DB2_SQL_TIMESTAMP_PATTERN)
//				.append("')")
//				.toString();
			return new StringBuffer()
				.append("TIMESTAMP('")
				.append(DB2_TIMESTAMP_FORMAT.format(timestamp))
				.append("')")
				.toString();
		} else {
			throw new ISPACException("Tipo de Base de datos no permitido: '"
					+ cnt.getProductName() + "'");
		}
	}
    
    public static String getToUpperSQL(String nameField){
    	return " UPPER(" + nameField + ") ";
    }
    
    public static String replaceQuotes(String data) {
    	return StringUtils.replace(data, "'", "''");
    }
    
    public static String[] replaceQuotes(String[] datas) {
    	
    	String[] aux = new String[datas.length];
    	
    	for (int i = 0; i < datas.length; i++) {	
    		aux[i] = replaceQuotes(datas[i]);
    	}
    	
    	return aux;
    }


   /* public static String addInResponsibleConditionAndDistinctOther(String field, String respString , String otherField) {
    	StringBuffer sql = new StringBuffer();

    	String [] resps = respString.split(",");
		if (!ArrayUtils.isEmpty(resps)) {
			sql.append(" ("); 
			for (int i = 0; i < resps.length; i++) {
				if (i > 0) {
					sql.append(" OR ");
				}
				sql.append("( "+ field+"="+resps[i]+" AND " +otherField+"!="+resps[i]+")");
				
			}
			sql.append(") ");
		}

		return sql.toString();
    }*/
    
    public static String addInResponsibleCondition(String field, String respString) {
		return addOrCondition(field, respString.split(","));
    }

    public static String addOrCondition(String field, String[] values) {
    	StringBuffer sql = new StringBuffer();

		if (!ArrayUtils.isEmpty(values)) {
			sql.append(" ("); 
			for (int i = 0; i < values.length; i++) {
				if (i > 0) {
					sql.append(" OR ");
				}
				sql.append(field).append("=").append(values[i]);
			}
			sql.append(") ");
		}

		return sql.toString();
    }

    /**
     * Responsabilidad principal al buscar un expediente.
     */
    public static String addAndInResponsibleCondition(String field, String respString) {
    	String sql=" ";
		if (StringUtils.isNotBlank(respString) && !Responsible.SUPERVISOR.equalsIgnoreCase(respString)) {
			//return " AND " + field + " IN (" + responsible + ") ";
			sql = addInResponsibleCondition(field, respString);
			if (StringUtils.isNotBlank(sql)) {
				sql = " AND " + sql;
			}
		}

		return sql;
    }
    
    /**
     * Responsabilidad principal y secundaria al buscar un expediente.
     */
    public static String addAndInResponsibleSecondaryCondition(String field, String fieldSec, String responsible) {
    	
		if (!responsible.equals(Responsible.SUPERVISOR)) {
			return " AND ( " + addInResponsibleCondition(field, responsible) 
				+ " OR " + addInResponsibleCondition(fieldSec, responsible) 
				+ ") ";
		}
		return " ";
    }

    public static String getFieldValue(Property fieldProperty, String value) {
    	if (fieldProperty.isNumeric()) {
    		return replaceQuotes(value);
    	} else {
    		return "'" + replaceQuotes(value) + "'";
    	}
    }
    
    public static String getFieldValue(Property fieldProperty, Object value) {
    	String[] values = null;
    	if (value instanceof Object[]){
    		values = ArrayUtils.toStringArray((Object[])value);
    	}else{
    		String st = "";
    		if (value != null){
    			st = value.toString(); 
    		}
    		values = new String[]{st};
    	}
		String _return = "";
		String separator = "";
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null){ 
				if (fieldProperty.isNumeric()) {
		    		_return += separator + replaceQuotes((String)values[i]);
		    	} else {
		    		_return += separator + "'" + replaceQuotes((String)values[i]) + "'";
		    	}
				separator = " , ";
			}
		}
   		return _return;
    }    
    
}