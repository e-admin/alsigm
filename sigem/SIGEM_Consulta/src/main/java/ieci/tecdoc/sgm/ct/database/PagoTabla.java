/**
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl;


/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * pagos.
 *
 */
public class PagoTabla extends PagoImpl {

	private static final String NOMBRE_TABLA = "SGMCTPAGO";

	private static final String CN_PAGOID = "PAGO_ID";

	private static final String CN_DOCUMENTOID = "DOCUMENTO_ID";

	private static final String CN_MENSAJE = "MENSAJE";

	private static final String CN_HITOID = "HITO_ID";

	private static final String CN_FECHAPAGO = "FECHA_PAGO";

	private static final String CN_EXPEDIENTE = "EXPEDIENTE";

	private static final String CN_ENTIDADEMISORAID = "ENTIDAD_EMISORA_ID";

	private static final String CN_AUTOLIQUIDACIONID = "AUTOLIQUIDACION_ID";

	private static final String CN_IMPORTE = "IMPORTE";


	private static final String NOMBRES_COLUMNAS = CN_PAGOID + "," + CN_DOCUMENTOID
			+ "," + CN_MENSAJE + "," + CN_HITOID + "," + CN_FECHAPAGO + "," + CN_EXPEDIENTE + "," +
			CN_ENTIDADEMISORAID + "," + CN_AUTOLIQUIDACIONID + "," + CN_IMPORTE;

	/**
	 * Constructor de la clase NotificacionTable
	 */
	public PagoTabla() {
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
	 * Devuelve el nombre de la columna Numero de entidad emisora
	 *
	 * @return String Nombre de la columna Numero de entidad emisora
	 */
	public String getEntidadEmisoraIdColumnName() {
		return CN_ENTIDADEMISORAID;
	}

	/**
	 * Devuelve el nombre de la columa Autoliquidacion
	 *
	 * @return String Nombre de la columna Autoliquidacion
	 */
	public String getAutoliquidacionIdColumnName() {
		return CN_AUTOLIQUIDACIONID;
	}

	/**
	 * Devuelve el nombre de la columna Importe
	 *
	 * @return String Nombre de la columna Importe
	 */
	public String getImporteColumnName() {
		return CN_IMPORTE;
	}

	/**
	 * Devuelve el nombre de la columna id del pago
	 *
	 * @return String Nombre de la columna id del pago
	 */
	public String getPagoIdColumnName() {
		return CN_PAGOID;
	}

	/**
	 * Devuelve el nombre de la columna id del documento
	 *
	 * @return String Nombre de la columna id del documento
	 */
	public String getDocumentoIdColumnName() {
		return CN_DOCUMENTOID;
	}

	/**
	 * Devuelve el nombre de la columna mensaje
	 *
	 * @return String Nombre de la columna mensaje
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
	 * Devuelve el nombre de la columna fecha del pago
	 *
	 * @return String Nombre de la columna fecha del pago
	 */
	public String getFechaPagoColumnName() {
		return CN_FECHAPAGO;
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
	 * Devuelve la clausula de consulta por id de pago
	 *
	 * @param pagoId
	 *            Valor del campo id del pago
	 * @return String Clausula de consulta por id del pago
	 */
	public String getClausulaPorPago(String pagoId) {
		String clausula;

		clausula = "WHERE " + CN_PAGOID + " = '" + DbUtil.replaceQuotes(pagoId) + "'";

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta por id del hito
	 *
	 * @param hitoId
	 *            Valor del campo id del hito
	 * @return String Clausula de consulta por id del hito
	 */
	public String getClausulaPorHito(String hitoId) {
		String clausula;

		clausula = "WHERE " + CN_HITOID + " = '" + DbUtil.replaceQuotes(hitoId) + "'";

		return clausula;
	}

	/**
	 * Devuelve la clausula de consulta por número de expediente
	 *
	 * @param expediente
	 *            Valor del campo número de expediente
	 * @return String Clausula de consulta por número de expediente
	 */
	public String getClausulaPorExpediente(String expediente) {
		String clausula;

		clausula = "WHERE " + CN_EXPEDIENTE + " = '" + DbUtil.replaceQuotes(expediente) + "'";

		return clausula;
	}

}