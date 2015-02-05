/**
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.InteresadoImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * Fichero de un Hito.
 *
 */
public class FicheroHitoTabla extends InteresadoImpl {

	private static final String NOMBRE_TABLA = "SGM_CTFICHHITO";

	private static final String CN_GUIDHITO = "CGUIDHITO";

	private static final String CN_GUID = "CGUIDFICH";

	private static final String CN_TITULO = "CTITULO";

	private static final String NOMBRES_COLUMNAS = CN_GUIDHITO + "," + CN_GUID
			+ "," + CN_TITULO;

	/**
	 * Constructor de la clase FicheroHitoTabla
	 */
	public FicheroHitoTabla() {
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
	 * Devuelve el nombre de la columna NumeroExpediente
	 *
	 * @return String Nombre de la columna NumeroExpediente
	 */
	public String getNombreColumnaGuidHito() {
		return CN_GUIDHITO;
	}

	/**
	 * Devuelve el nombre de la columna NIF
	 *
	 * @return String Nombre de la columna NIF
	 */
	public String getNombreColumnaGuid() {
		return CN_GUID;
	}

	/**
	 * Devuelve el nombre de la columna Nombre
	 *
	 * @return String Nombre de la columna Nombre
	 */
	public String getNombreColumnaTitulo() {
		return CN_TITULO;
	}


	/**
	 * Devuelve la clausula de consulta por el Guid del Hito al que esta vinculado el fichero
	 *
	 * @param guidHito Valor del Guid del Hito que sera adjuntado a la clausula WHERE para
	 * realizar la sentencia.
	 * @return String Clausula de la sentencia SQL por Guid del Hito.
	 */
	public String getClausulaPorGuidHito(String guidHito) {

		String qual;

		qual = "WHERE " + CN_GUIDHITO + " = '" + DbUtil.replaceQuotes(guidHito) + "'";

		return qual;
	}

	/**
	 * Devuelve la clausula de consulta por el Guid del Fichero
	 *
	 * @param guid Valor del Guid del Fichero que sera adjuntado a la clausula WHERE para
	 * realizar la sentencia.
	 * @return String Clausula de la sentencia SQL por Guid del Fichero.
	 */
	public String getClausulaPorGuid(String guid) {

		String qual;

		qual = "WHERE " + CN_GUIDHITO + " = '" + DbUtil.replaceQuotes(guid) + "'";

		return qual;
	}

}