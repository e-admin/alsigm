/**
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl;


/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * notificaciones.
 *
 */
public class NotificacionTabla extends NotificacionImpl {

	private static final String NOMBRE_TABLA = "SGMCTNOTIFICACION";

	private static final String CN_NOTIFICACIONID = "NOTIFICACION_ID";

	private static final String CN_FECHANOTIFICACION = "FECHA_NOTIFICACION";

	private static final String CN_DEU = "DEU";

	private static final String CN_SERVICIONOTIFICACIONESID = "SERVICIO_NOTIFICACIONES_ID";

	private static final String CN_EXPEDIENTE = "EXPEDIENTE";

	private static final String CN_DESCRIPCION = "DESCRIPCION";

	private static final String CN_HITOID = "HITO_ID";


	private static final String NOMBRES_COLUMNAS = CN_NOTIFICACIONID + "," + CN_FECHANOTIFICACION
			+ "," + CN_DEU + "," + CN_SERVICIONOTIFICACIONESID + "," + CN_EXPEDIENTE
			+ "," + CN_DESCRIPCION + "," + CN_HITOID;

	/**
	 * Constructor de la clase NotificacionTable
	 */
	public NotificacionTabla() {
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
	 * Devuelve el nombre de la columna Numero de notificacion
	 *
	 * @return String Nombre de la columna Numero de notificacion
	 */
	public String getNotificacionIdColumnName() {
		return CN_NOTIFICACIONID;
	}

	/**
	 * Devuelve el nombre de la columa Fecha de notificacion
	 *
	 * @return String Nombre de la columna Fecha de notificacion
	 */
	public String getFechaNotificacionColumnName() {
		return CN_FECHANOTIFICACION;
	}

	/**
	 * Devuelve el nombre de la columna DEU
	 *
	 * @return String Nombre de la columna DEU
	 */
	public String getDeuColumnName() {
		return CN_DEU;
	}

	/**
	 * Devuelve el nombre de la columna Servicio de notificaciones
	 *
	 * @return String Nombre de la columna Servicio de notificaciones
	 */
	public String getServicioNotificacionesIdColumnName() {
		return CN_SERVICIONOTIFICACIONESID;
	}

	/**
	 * Devuelve el nombre de la columna Expedeinte
	 *
	 * @return String Nombre de la columna Expedeinte
	 */
	public String getExpedienteColumnName() {
		return CN_EXPEDIENTE;
	}

	/**
	 * Devuelve el nombre de la columna Descripcion
	 *
	 * @return String Nombre de la columna Descripcion
	 */
	public String getDescripcionColumnName() {
		return CN_DESCRIPCION;
	}

	/**
	 * Devuelve el nombre de la columna Número de hito
	 *
	 * @return String Nombre de la columna Número de hito
	 */
	public String getHitoId() {
		return CN_HITOID;
	}


	/**
	 * Devuelve la clausula de consulta por el numero de expediente
	 *
	 * @param numero
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorExpediente(String expediente) {
		String clausula;

		clausula = "WHERE " + CN_EXPEDIENTE + " = '" + DbUtil.replaceQuotes(expediente) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta por el numero de hito
	 *
	 * @param numero
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorHito(String hitoId) {
		String clausula;

		clausula = "WHERE " + CN_HITOID + " = '" + DbUtil.replaceQuotes(hitoId) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta por el numero de notificacion
	 *
	 * @param numero
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorNotificacion(String notificacionId) {
		String clausula;

		clausula = "WHERE " + CN_NOTIFICACIONID + " = '" + DbUtil.replaceQuotes(notificacionId) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de ordenacion por fecha cuando se
	 * compone una sentencia de busqueda SQL contra la tabla de Expedientes
	 *
	 * @return String Clausula de ordenacion por fecha
	 */

	public String getOrdenacionPorFechaNotificacion() {
		String clausula;

		clausula = " ORDER BY " + CN_FECHANOTIFICACION;

		return clausula;
	}
}