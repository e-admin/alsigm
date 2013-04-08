/**
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.SubsanacionImpl;


/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * subsanaciones.
 *
 */
public class SubsanacionTabla extends SubsanacionImpl {

	private static final String NOMBRE_TABLA = "SGMCTSUBSANACION";

	private static final String CN_SUBSANACIONID = "SUBSANACION_ID";

	private static final String CN_DOCUMENTOID = "DOCUMENTO_ID";

	private static final String CN_MENSAJE = "MENSAJE";

	private static final String CN_HITOID = "HITO_ID";

	private static final String CN_FECHASUBSANACION = "FECHA_SUBSANACION";

	private static final String CN_EXPEDIENTE = "EXPEDIENTE";


	private static final String NOMBRES_COLUMNAS = CN_SUBSANACIONID + "," + CN_DOCUMENTOID
			+ "," + CN_MENSAJE + "," + CN_HITOID + "," + CN_FECHASUBSANACION + "," + CN_EXPEDIENTE;

	/**
	 * Constructor de la clase NotificacionTable
	 */
	public SubsanacionTabla() {
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
	 * Devuelve el nombre de la columna Id de Subsanacion
	 *
	 * @return String Nombre de la columna Id de Subsanacion
	 */
	public String getSubsanacionIdColumnName() {
		return CN_SUBSANACIONID;
	}

	/**
	 * Devuelve el nombre de la columa Id del documento
	 *
	 * @return String Nombre de la columna Id del documento
	 */
	public String getDocumentoIdColumnName() {
		return CN_DOCUMENTOID;
	}

	/**
	 * Devuelve el nombre de la columna Mensaje
	 *
	 * @return String Nombre de la columna Mensaje
	 */
	public String getMensajeColumnName() {
		return CN_MENSAJE;
	}

	/**
	 * Devuelve el nombre de la columna Id del hito
	 *
	 * @return String Nombre de la columna Id del hito
	 */
	public String getHitoIdColumnName() {
		return CN_HITOID;
	}

	/**
	 * Devuelve el nombre de la columna Fecha de subsanacion
	 *
	 * @return String Nombre de la columna Fecha de subsanacion
	 */
	public String getFechaSubsanacionColumnName() {
		return CN_FECHASUBSANACION;
	}

	/**
	 * Devuelve el nombre de la columna Número de expediente
	 *
	 * @return String Nombre de la columna Número de expediente
	 */
	public String getExpedienteColumnName() {
		return CN_EXPEDIENTE;
	}

	/**
	 * Devuelve la clausula de consulta por subsanacion
	 *
	 * @param subsanacion
	 *            Valor del campo subsanacion
	 * @return String Clausula de consulta por subsanacion
	 */
	public String getClausulaPorSubsanacion(String subsanacion) {
		String clausula;

		clausula = "WHERE " + CN_SUBSANACIONID + " = '" + DbUtil.replaceQuotes(subsanacion) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta por hito
	 *
	 * @param hito
	 *            Valor del campo hito
	 * @return String Clausula de consulta por hito
	 */
	public String getClausulaPorHito(String hito) {
		String clausula;

		clausula = "WHERE " + CN_HITOID + " = '" + DbUtil.replaceQuotes(hito) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta por expediente
	 *
	 * @param expediente
	 *            Valor del campo expediente
	 * @return String Clausula de consulta por expediente
	 */
	public String getClausulaPorExpediente(String expediente) {
		String clausula;

		clausula = "WHERE " + CN_EXPEDIENTE + " = '" + DbUtil.replaceQuotes(expediente) + "'";

		return clausula;
	}

}