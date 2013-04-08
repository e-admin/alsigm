package ieci.tecdoc.sgm.pe.database.exception;
/*
 * $Id: DocumentoIngresoLiquidacionTabla.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.pe.DocumentoIngresoLiquidacionImpl;

public class DocumentoIngresoLiquidacionTabla extends
		DocumentoIngresoLiquidacionImpl {

	 private static final String TABLE_NAME = 	"sgm_pe_dingreso_autoliquidacion";
	 private static final String CN_GUID = 		"guid";
	 private static final String CN_IDTASA = 	"idtasa";
	 private static final String CN_CLASE = 	"clase";
	 private static final String CN_NUMDOC = 	"numdoc";
	 private static final String CN_DOCINGRESO ="docingreso";
	 private static final String CN_PAGADO = 	"pagado";
	 private static final String CN_DESCRIPCION ="descripcion";
	 private static final String ALL_COLUMN_NAMES = CN_GUID + ","
	 												+ CN_IDTASA + ","
	 												+ CN_CLASE + ","
	 												+ CN_NUMDOC + ","
	 												+ CN_DOCINGRESO + ","
	 												+ CN_PAGADO + ","
	 												+ CN_DESCRIPCION;

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
	* Devuelve el nombre de la columna guid
	* @return String Nombre de la columna guid
	*/
	   public String getGuidColumnName(){
	     return CN_GUID;
	   }

	   /**
	* Devuelve el nombre de la columna clase
	* @return String Nombre de la columna clase
	*/
	   public String getClaseColumnName(){
		   return CN_CLASE;
	   }

	/**
	* Devuelve el nombre de la columna numdoc
	* @return String Nombre de la columna numdoc
	*/
	   public String getNumDocColumnName(){
		   return CN_NUMDOC;
	   }

	/**
	* Devuelve el nombre de la columna nifint
	* @return String Nombre de la columna nifint
	*/
	   public String getDocIngresoColumnName(){
		   return CN_DOCINGRESO;
	   }

	/**
	* Devuelve el nombre de la columna nifint
	* @return String Nombre de la columna nifint
	*/
	   public String getPagadoColumnName(){
		   return CN_PAGADO;
	   }

	/**
	* Devuelve el nombre de la columna nifint
	* @return String Nombre de la columna nifint
	*/
	   public String getDescripcionColumnName(){
		   return CN_DESCRIPCION;
	   }

	/**
	 * Devuelve la clausula de consulta por guid
	 * @param guid Valor del campo guid
	 * @return String Clausula de consulta por guid
	 */
	   public String getByGuidQual(String guid) {
		   String qual;

		   qual = "WHERE " + CN_GUID + " = '" + DbUtil.replaceQuotes(guid) + "'";

		   return qual;
	   }
}
