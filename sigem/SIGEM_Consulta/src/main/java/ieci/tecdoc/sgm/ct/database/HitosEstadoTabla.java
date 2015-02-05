/**
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * Hito del Estado de un Expediente.
 *
 */
public class HitosEstadoTabla extends HitoExpedienteImpl {

	private static final String NOMBRE_TABLA = "SGM_CTHITOESTEXP";

	private static final String CN_NUMEROEXPEDIENTE = "CNUMEXP";

	private static final String CN_GUID = "CGUID";

	private static final String CN_CODIGO = "CCOD";

	private static final String CN_FECHA = "CFECHA";

	private static final String CN_DESCRIPCION = "CDESCR";

	private static final String CN_INFORMACIONAUXILIAR = "CINFOAUX";

	private static final String NOMBRES_COLUMNAS = CN_NUMEROEXPEDIENTE + "," + CN_GUID
			+ "," + CN_CODIGO + "," + CN_FECHA + "," + CN_DESCRIPCION
			+ "," + CN_INFORMACIONAUXILIAR;

	/**
	 * Constructor de la clase HitoEstadoExpediente
	 */
	public HitosEstadoTabla() {
	}

	/**
	 * Devuelve el nombre de la tabla
	 *
	 * @return String Nombre de la tabla
	 */
	public String getNombreTabla() {

		return NOMBRE_TABLA;
	}

	/**
	 * Devuelve los nombres de las columnas
	 *
	 * @return String Nombres de las columnas
	 */
	public String getNombresColumnas() {

		return NOMBRES_COLUMNAS;
	}

	/**
	 * Devuelve el nombre de la columna Numero de Expediente
	 *
	 * @return String Nombre de la columna Numero de Expediente
	 */
	public String getNombreColumnaNumeroExpediente() {
		return CN_NUMEROEXPEDIENTE;
	}

	/**
	 * Devuelve el nombre de la columna Guid
	 *
	 * @return String Nombre de la columna Guid
	 */
	public String getNombreColumnaGuid() {
		return CN_GUID;
	}

	/**
	 * Devuelve el nombre de la columna Codigo
	 *
	 * @return String Nombre de la columna codigo
	 */
	public String getNombreColumnaCodigo() {
		return CN_CODIGO;
	}

	/**
	 * Devuelve el nombre de la columna Fecha
	 *
	 * @return String Nombre de la columna Fecha
	 */
	public String getNombreColumnaFecha() {
		return CN_FECHA;
	}

	/**
	 * Devuelve el nombre de la columna InformacionAuxiliar
	 *
	 * @return String Nombre de la columna InformacionAuxiliar
	 */
	public String getNombreColumnaInformacionAuxiliar() {
		return CN_INFORMACIONAUXILIAR;
	}


	/**
	 * Devuelve la clausula de consulta por el numero de expediente
	 *
	 * @param numeroExpediente
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero de expediente
	 */
	public String getClausulaPorNumeroExpediente(String numeroExpediente) {
		String clausula;

		clausula = "WHERE " + CN_NUMEROEXPEDIENTE + " = '" + DbUtil.replaceQuotes(numeroExpediente) + "'";

		return clausula;
	}

	/**
	 * Devuelve la clausula de consulta por el guid del hito
	 *
	 * @param guid Guid del hito
	 * @return String Clausula de consulta por numero de expediente
	 */
	public String getClausulaPorGuid(String guid) {
		String clausula;

		clausula = "WHERE " + CN_GUID + " = '" + DbUtil.replaceQuotes(guid) + "'";

		return clausula;
	}

	/*
	 * public void addExpediente(ExpedientesData documentRow) throws DbException {
	 *
	 * insert(documentRow); // updateBlob(documentRow, CN_CONTENT, //
	 * getDefaultQual(documentRow.getOid()));
	 *  }
	 *
	 * public ExpedientesData getExpediente(String guid) throws DbException {
	 * ExpedientesData documentRow = new ExpedientesData();
	 * select(getByGuidQual(guid), documentRow); return documentRow; }
	 *
	 * public void deleteExpediente(String oid) throws DbException {
	 *
	 * delete(getDefaultQual(oid)); }
	 *
	 * public boolean isValidOid(String oid) throws DbException {
	 *
	 * int count = 0;
	 *
	 * if (oid == null) return false; else { count =
	 * selectCount(getDefaultQual(oid)); if (count == 0) return false; else
	 * return true; } }
	 */

}