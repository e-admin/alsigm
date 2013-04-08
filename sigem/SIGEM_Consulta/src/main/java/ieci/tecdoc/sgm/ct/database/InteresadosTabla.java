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
 * Interesados de Expedientes.
 *
 */
public class InteresadosTabla extends InteresadoImpl {

	private static final String NOMBRE_TABLA = "SGM_CTINTEXP";

	private static final String CN_NUMEROEXPEDIENTE = "CNUMEXP";

	private static final String CN_NIF = "CNIF";

	private static final String CN_NOMBRE = "CNOM";

	private static final String CN_PRINCIPAL = "CPRINCIPAL";

	private static final String CN_INFORMACIONAUXILIAR = "CINFOAUX";

	private static final String NOMBRES_COLUMNAS = CN_NUMEROEXPEDIENTE + "," + CN_NIF
			+ "," + CN_NOMBRE + "," + CN_PRINCIPAL + "," + CN_INFORMACIONAUXILIAR;

	/**
	 * Constructor de la clase InteresadosTabla
	 */
	public InteresadosTabla() {
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
	public String getNombreColumnaNumeroExpediente() {
		return CN_NUMEROEXPEDIENTE;
	}

	/**
	 * Devuelve el nombre de la columna NIF
	 *
	 * @return String Nombre de la columna NIF
	 */
	public String getNombreColumnaNIF() {
		return CN_NIF;
	}

	/**
	 * Devuelve el nombre de la columna Nombre
	 *
	 * @return String Nombre de la columna Nombre
	 */
	public String getNombreColumnaNombre() {
		return CN_NOMBRE;
	}

	/**
	 * Devuelve el nombre de la columna Principal
	 *
	 * @return String Nombre de la columna Principal
	 */
	public String getNombreColumnaPrincipal() {
		return CN_PRINCIPAL;
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
	 * @return String Clausula de consulta por numero de expediente.
	 */
	public String getClausulaPorNumeroExpediente (String numeroExpediente) {

		String qual;

		qual = "WHERE " + CN_NUMEROEXPEDIENTE + " = '" + DbUtil.replaceQuotes(numeroExpediente) + "'";

		return qual;
	}

	/**
	 * Devuelve la clausula de consulta por el NIF del interesado
	 *
	 * @param NIF Valor del NIF que sera adjuntado a la clausula WHERE para
	 * realizar la busqueda
	 * @return String Clausula de consulta por NIF.
	 */
	public String getClausulaPorNIF(String NIF) {

		String qual;

		qual = "WHERE " + CN_NIF + " = '" + DbUtil.replaceQuotes(NIF) + "'";

		return qual;
	}
}