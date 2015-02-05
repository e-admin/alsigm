package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: EntidadBancariaTabla.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.pe.EntidadBancariaImpl;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class EntidadBancariaTabla extends
		EntidadBancariaImpl {

	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(EntidadBancariaTabla.class);

	 private static final String TABLE_NAME = 			"sgm_pe_entidades_colaboradoras";
	 private static final String CN_ID = 				"id_entidad";
	 private static final String CN_NOMBRE = 			"nombre";
	 private static final String ALL_COLUMN_NAMES = CN_ID + ","
	 												+ CN_NOMBRE;


	 public String getiDColumnName(){
		 return CN_ID;
	 }

	 public String getNombreColumnName(){
		 return CN_NOMBRE;
	 }

	 /**
	  * Devuelve el nombre de la tabla
	  * @return String Nombre de la tabla
	  */
	 public String getTableName() {
	      return TABLE_NAME;
	   }


	   /**
	* Devuelve los nombres de las columnas
	* @return String Nombres de las columnas
	*/
	   public String getAllColumnNames() {

	      return ALL_COLUMN_NAMES;
	   }

	/**
	 * Devuelve la clausula de consulta por referencia
	 * @param guid Valor del campo referencia
	 * @return String Clausula de consulta por referencia
	 */
	   public String getByGuidPK(String pcId) {
		   StringBuffer sbQual = new StringBuffer("WHERE ");
		   sbQual.append(CN_ID).append(" = '").append(DbUtil.replaceQuotes(pcId)).append("'");
		   return sbQual.toString();
	   }

	   public ArrayList buscarEntidadBancarias(DbConnection dbCon, String entidad) throws DbExcepcion{
			  DynamicTable tableInfo = new DynamicTable();
			  DynamicRows rowsInfo = new DynamicRows();
			  DynamicRow rowInfo = new DynamicRow();
			  EntidadBancariaTabla table = new EntidadBancariaTabla();
			  EntidadBancariaDatos expediente;
			  ArrayList list = new ArrayList();
			  int size;
			  boolean close = false;
			  DbConnection dbConn = null;

			  if (dbCon != null) dbConn = dbCon;
			  logger.debug("buscarEntidadBancariaes");
			  try {
				  if (dbConn == null) {
					  dbConn = new DbConnection();
					  dbConn.open(DBSessionManager.getSession(entidad));
//					  dbConn.open(Configuracion.getDatabaseConnection());
					  close = true;
				  }

				  tableInfo.setTableObject(table);
				  tableInfo.setClassName(EntidadBancariaTabla.class.getName());
				  tableInfo.setTablesMethod("getTableName");
				  tableInfo.setColumnsMethod("getAllColumnNames");
				  rowInfo.setClassName(EntidadBancariaDatos.class.getName());
			      rowInfo.setValuesMethod("loadAllValues");
			      rowsInfo.add(rowInfo);
			      DynamicFns.selectMultiple(dbConn, null, false, tableInfo, rowsInfo);
			      size = rowInfo.getRowCount();
			      for (int i = 0; i < size; i++) {
			    	  expediente = (EntidadBancariaDatos) rowInfo.getRow(i);
			    	  list.add(expediente);
			      }
			  }catch(Exception e){
				  logger.error("Error durante ejecución de sentencia.");
				  throw new DbExcepcion(DbCodigosError.EC_QUERY_EXCEPTION,e);
			  } finally {
			       try{
			    	   if(close){
			    		   if (dbConn.existConnection())
			    			   dbConn.close();
			    	   }
				   }catch(Exception ee){
					   throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, ee);
				   }
			  }
			  return list;
		  }
}
