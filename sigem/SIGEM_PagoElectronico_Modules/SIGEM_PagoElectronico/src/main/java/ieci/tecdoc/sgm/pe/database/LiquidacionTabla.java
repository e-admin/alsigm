package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: LiquidacionTabla.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.pe.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.pe.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.pe.LiquidacionImpl;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class LiquidacionTabla extends
		LiquidacionImpl {

	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(LiquidacionTabla.class);

	 private static final String TABLE_NAME = 			"sgm_pe_liquidaciones";

	 private static final String CN_REFERENCIA = 		"referencia";
	 private static final String CN_IDENTIDADEMISORA = 	"id_entidad_emisora";
	 private static final String CN_IDTASA = 			"id_tasa";
	 private static final String CN_EJERCICIO =			"ejercicio";
	 private static final String CN_VENCIMIENTO =		"vencimiento";
	 private static final String CN_DISCRIMINANTE_PERIODO="discriminante_periodo";
	 private static final String CN_REMESA =			"remesa";
	 private static final String CN_NIF =				"nif";
	 private static final String CN_IMPORTE =			"importe";
	 private static final String CN_NRC =				"nrc";
	 private static final String CN_ESTADO = 			"estado";
	 private static final String CN_NOMBRE = 			"nombre";
	 private static final String CN_DATOS_ESPECIFICOS =	"datos_especificos";
	 private static final String CN_INICIO_PERIODO =	"inicio_periodo";
	 private static final String CN_FIN_PERIODO =		"fin_periodo";
	 private static final String CN_SOLICITUD = 		"solicitud";
	 private static final String CN_FECHA_PAGO =		"fecha_pago";

	 private static final String ALL_COLUMN_NAMES = CN_REFERENCIA + ","
	 												+ CN_IDENTIDADEMISORA + ","
	 												+ CN_IDTASA + ","
	 												+ CN_EJERCICIO + ","
	 												+ CN_VENCIMIENTO + ","
	 												+ CN_DISCRIMINANTE_PERIODO + ","
	 												+ CN_REMESA + ","
	 												+ CN_NIF + ","
	 												+ CN_IMPORTE + ","
	 												+ CN_NRC + ","
	 												+ CN_ESTADO + ","
	 												+ CN_NOMBRE + ","
	 												+ CN_DATOS_ESPECIFICOS + ","
	 												+ CN_INICIO_PERIODO + ","
	 												+ CN_FIN_PERIODO + ","
	 												+ CN_SOLICITUD + ","
	 												+ CN_FECHA_PAGO;

	 private static final String ALL_COLUMN_NAMES_TABLA_MIXTA =  "B." + CN_REFERENCIA + ","
													+"B." + CN_IDENTIDADEMISORA + ","
													+"B." + CN_IDTASA + ","
													+"B." + CN_EJERCICIO + ","
													+"B." + CN_VENCIMIENTO + ","
													+"B." + CN_DISCRIMINANTE_PERIODO + ","
													+"B." + CN_REMESA + ","
													+"B." + CN_NIF + ","
													+"B." + CN_IMPORTE + ","
													+"B." + CN_NRC + ","
													+"B." + CN_ESTADO + ","
													+"B." + CN_NOMBRE + ","
													+"B." + CN_DATOS_ESPECIFICOS + ","
													+"B." + CN_INICIO_PERIODO + ","
													+"B." + CN_FIN_PERIODO + ","
													+"B." + CN_SOLICITUD + ","
													+"B." + CN_FECHA_PAGO;

	 private static final String UPDATE_COLUMN_NAMES = CN_IDENTIDADEMISORA + ","
														+ CN_IDTASA + ","
															+ CN_EJERCICIO + ","
															+ CN_VENCIMIENTO + ","
															+ CN_DISCRIMINANTE_PERIODO + ","
															+ CN_REMESA + ","
															+ CN_NIF + ","
															+ CN_IMPORTE + ","
															+ CN_NRC + ","
			 												+ CN_ESTADO + ","
			 												+ CN_NOMBRE + ","
			 												+ CN_DATOS_ESPECIFICOS + ","
			 												+ CN_INICIO_PERIODO + ","
			 												+ CN_FIN_PERIODO + ","
			 												+ CN_SOLICITUD + ","
			 												+ CN_FECHA_PAGO;

	 public String getReferenciaColumnaName(){
		 return CN_REFERENCIA;
	 }
	 public String getIdEntidadEmisoraColumnName(){
		 return CN_IDENTIDADEMISORA;
	 }
	 public String getIdTasaColumnaName(){
		 return CN_IDTASA;
	 }
	 public String getEjercicioColumnName(){
		 return CN_EJERCICIO;
	 }
	 public String getRemesaColumnName(){
		 return CN_REMESA;
	 }
	 public String getNifColumnName(){
		 return CN_NIF;
	 }
	 public String getImporteColumnName(){
		 return CN_IMPORTE;
	 }
	 public String getNRCColumnName(){
		 return CN_NRC;
	 }
	 public String getEstadoColumnName(){
		 return CN_ESTADO;
	 }
	 public String getNombreColumnName(){
		 return CN_NOMBRE;
	 }
	 public String getDatosEspecificosColumnName(){
		 return CN_DATOS_ESPECIFICOS;
	 }
	 public String getInicioPeridoColumnName(){
		 return CN_INICIO_PERIODO;
	 }
	 public String getFinPeridoColumnName(){
		 return CN_FIN_PERIODO;
	 }
	 public String getSolicitudColumnName(){
		 return CN_SOLICITUD;
	 }
	 public String getFechaPagoColumnName(){
		 return CN_FECHA_PAGO;
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

	   public String getUpdateColumnNames(){
		   return UPDATE_COLUMN_NAMES;
	   }

	   public String getAllColumnNamesTablaMixta() {

		      return ALL_COLUMN_NAMES_TABLA_MIXTA;
		   }
	/**
	 * Devuelve la clausula de consulta por referencia
	 * @param guid Valor del campo referencia
	 * @return String Clausula de consulta por referencia
	 */
	   public String getByGuidQual(String guid) {
		   String qual;

		   qual = "WHERE " + CN_REFERENCIA + " = '" + DbUtil.replaceQuotes(guid) + "'";

		   return qual;
	   }


	   public String getFindQual(CriterioBusquedaLiquidacion poCriterio){
		   return getFindQual(poCriterio,true);
	   }

	   public String getFindQual(CriterioBusquedaLiquidacion poCriterio,boolean incluirWhere){
		   String where=" AND ";
		   if(incluirWhere) where=" WHERE ";

		   StringBuffer sbQual = null;
		   if(poCriterio != null){
			   // referencia
			   if(	(poCriterio.getReferencia() != null)
						&&(!"".equals(poCriterio.getReferencia()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }
					   sbQual.append(getReferenciaColumnaName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getReferencia()))
					   			.append("' ");
				   }
			   // Entidad emisora
			   if(	(poCriterio.getIdEntidadEmisora() != null)
						&&(!"".equals(poCriterio.getIdEntidadEmisora()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getIdEntidadEmisoraColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getIdEntidadEmisora()))
					   			.append("' ");
				   }
			   // Ejercicio
			   if(	(poCriterio.getEjercicio() != null)
						&&(!"".equals(poCriterio.getEjercicio()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getEjercicioColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getEjercicio()))
					   			.append("' ");
				   }
			   // estado
			   if(	(poCriterio.getEstado() != null)
						&&(!"".equals(poCriterio.getEstado()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getEstadoColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getEstado()))
					   			.append("' ");
				   }
			   // tasa
			   if(	(poCriterio.getIdTasa() != null)
						&&(!"".equals(poCriterio.getIdTasa()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getIdTasaColumnaName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getIdTasa()))
					   			.append("' ");
				   }
			   // NIF
			   if(	(poCriterio.getNif() != null)
						&&(!"".equals(poCriterio.getNif()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getNifColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getNif()))
					   			.append("' ");
				   }
			   // NRC
			   if(	(poCriterio.getNrc() != null)
						&&(!"".equals(poCriterio.getNrc()))
				   ){
					   if(sbQual == null){
						   sbQual = new StringBuffer(where);
					   }else{
						   sbQual.append(" AND ");
					   }
					   sbQual.append(getNRCColumnName())
					   			.append(" = '")
					   			.append(DbUtil.replaceQuotes(poCriterio.getNrc()))
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

	   public String getClausulaMixta(String tipo) {
			TasaTabla tasasTabla = new TasaTabla();
			String nombreColumnaIdTasa = tasasTabla.getCodigoColumnName();
			String nombreColumnaIdEmisor = tasasTabla.getIdEntidadEmisoraColumnName();
			CriterioBusquedaTasa criterioTasa=new CriterioBusquedaTasa();
			criterioTasa.setTipo(tipo);
			String clausula = tasasTabla.getFindQual(criterioTasa) + " AND A."   + nombreColumnaIdTasa + " = B." + CN_IDTASA +
							" AND A."+nombreColumnaIdEmisor + " = B." + CN_IDENTIDADEMISORA + " ";

			return clausula;
		}


		public String getTablaMixta() {
			TasaTabla tasaTabla = new TasaTabla();
			String nombreTablas =  tasaTabla.getTableName() + "  A," + this.getTableName() + " B";

			return nombreTablas;
		}

		public String getClausulaRangoFechasPago(DbConnection dbConn,Calendar fechaPagoInicio,Calendar fechaPagoFin){
			Date fecha=null;
			String clausula="";

			try{
				fecha = fechaPagoInicio.getTime();
				if(fecha!=null)
					clausula+= " AND " + CN_FECHA_PAGO + " >= " + DbUtil.getNativeDateTimeSyntax(dbConn, fecha, true) + "";
			}catch(Exception e){
				logger.debug(e);
			}

			try{
				fecha = fechaPagoFin.getTime();
				if(fecha!=null)
					clausula+= " AND " + CN_FECHA_PAGO + " <= " + DbUtil.getNativeDateTimeSyntax(dbConn, fecha, true) + "";
			}catch(Exception e){
				logger.debug(e);
			}

			return clausula;
		}

	  public ArrayList buscarLiquidaciones(CriterioBusquedaLiquidacion poCriterio, DbConnection dbCon, String entidad) throws DbExcepcion{
		  DynamicTable tableInfo = new DynamicTable();
		  DynamicRows rowsInfo = new DynamicRows();
		  DynamicRow rowInfo = new DynamicRow();
		  LiquidacionTabla table = new LiquidacionTabla();
		  LiquidacionDatos expediente;
		  ArrayList list = new ArrayList();
		  int size;
		  boolean close = false;
		  DbConnection dbConn = null;

		  if (dbCon != null) dbConn = dbCon;
		  logger.debug("buscarLiquidaciones");
		  try {
			  if (dbConn == null) {
				  dbConn = new DbConnection();
				  dbConn.open(DBSessionManager.getSession(entidad));
//				  dbConn.open(Configuracion.getDatabaseConnection());
				  close = true;
			  }

			  tableInfo.setTableObject(table);
			  tableInfo.setClassName(LiquidacionTabla.class.getName());
			  tableInfo.setTablesMethod("getTableName");
			  tableInfo.setColumnsMethod("getAllColumnNames");
			  rowInfo.setClassName(LiquidacionDatos.class.getName());
		      rowInfo.setValuesMethod("loadAllValues");
		      rowsInfo.add(rowInfo);
		      DynamicFns.selectMultiple(dbConn, getFindQual(poCriterio), false, tableInfo, rowsInfo);
		      size = rowInfo.getRowCount();
		      for (int i = 0; i < size; i++) {
		    	  expediente = (LiquidacionDatos) rowInfo.getRow(i);
		    	  list.add(expediente);
		      }
		  }catch(Exception e){
			  logger.error("Error durante ejecución de sentencia.",e);
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
