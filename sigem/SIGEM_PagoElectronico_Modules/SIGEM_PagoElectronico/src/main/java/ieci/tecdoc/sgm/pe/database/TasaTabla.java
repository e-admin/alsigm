package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: TasaTabla.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.pe.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.pe.TasaImpl;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class TasaTabla extends
		TasaImpl {

	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(TasaTabla.class);

	 private static final String TABLE_NAME = 			"sgm_pe_tasas";
	 private static final String CN_CODIGO = 			"codigo";
	 private static final String CN_IDENTIDADEMISORA = 	"id_entidad_emisora";
	 private static final String CN_NOMBRE = 			"Nombre";
	 private static final String CN_TIPO =				"tipo";
	 private static final String CN_MODELO =			"modelo";
	 private static final String CN_CAPTURA = 			"captura";
	 private static final String CN_DATOS_ESPECIFICOS = "datos_especificos";
	 private static final String ALL_COLUMN_NAMES = CN_CODIGO + ","
	 												+ CN_IDENTIDADEMISORA + ","
	 												+ CN_NOMBRE + ","
	 												+ CN_TIPO + ","
	 												+ CN_MODELO + ","
	 												+ CN_CAPTURA + ","
	 												+ CN_DATOS_ESPECIFICOS;
	 public String getCodigoColumnName(){
		 return CN_CODIGO;
	 }
	 public String getIdEntidadEmisoraColumnName(){
		 return CN_IDENTIDADEMISORA;
	 }
	 public String getNombreColumnName(){
		 return CN_NOMBRE;
	 }
	 public String getTipoColumnName(){
		 return CN_TIPO;
	 }
	 public String getModeloColumnName(){
		 return CN_MODELO;
	 }
	 public String getDatosEspecificosColumnName(){
		 return CN_DATOS_ESPECIFICOS;
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
	   public String getByGuidPK(String codigo, String idEntidadEmisora) {
		   StringBuffer sbQual = new StringBuffer("WHERE ");
		   sbQual.append(CN_CODIGO).append(" = '").append(DbUtil.replaceQuotes(codigo)).append("'");
		   sbQual.append(" AND ").append(CN_IDENTIDADEMISORA).append(" = '").append(DbUtil.replaceQuotes(idEntidadEmisora)).append("'");
		   return sbQual.toString();
	   }

	   public String getFindQual(CriterioBusquedaTasa poCriterio){
		   StringBuffer sbQual = null;
		   if(poCriterio != null){
			   // referencia
			   if(	(poCriterio.getTipo()!= null)
						&&(!"".equals(poCriterio.getTipo()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(" WHERE ");
					   }
					   sbQual.append(getTipoColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getTipo()))
					   			.append("' ");
				   }
			   if(sbQual == null){
				   return "";
			   }else{
				   return sbQual.toString();
			   }
		   }else{
			   return "";
		   }
	   }

		  public ArrayList buscarTasas(CriterioBusquedaTasa poCriterio, DbConnection dbCon, String entidad) throws DbExcepcion{
			  DynamicTable tableInfo = new DynamicTable();
			  DynamicRows rowsInfo = new DynamicRows();
			  DynamicRow rowInfo = new DynamicRow();
			  TasaTabla table = new TasaTabla();
			  TasaDatos expediente;
			  ArrayList list = new ArrayList();
			  int size;
			  boolean close = false;
			  DbConnection dbConn = null;

			  if (dbCon != null) dbConn = dbCon;
			  logger.debug("buscarTasaes");
			  try {
				  if (dbConn == null) {
					  dbConn = new DbConnection();
//					  dbConn.open(Configuracion.getDatabaseConnection());
					  dbConn.open(DBSessionManager.getSession(entidad));
					  close = true;
				  }

				  tableInfo.setTableObject(table);
				  tableInfo.setClassName(TasaTabla.class.getName());
				  tableInfo.setTablesMethod("getTableName");
				  tableInfo.setColumnsMethod("getAllColumnNames");
				  rowInfo.setClassName(TasaDatos.class.getName());
			      rowInfo.setValuesMethod("loadAllValues");
			      rowsInfo.add(rowInfo);
			      DynamicFns.selectMultiple(dbConn, getFindQual(poCriterio), false, tableInfo, rowsInfo);
			      size = rowInfo.getRowCount();
			      for (int i = 0; i < size; i++) {
			    	  expediente = (TasaDatos) rowInfo.getRow(i);
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
