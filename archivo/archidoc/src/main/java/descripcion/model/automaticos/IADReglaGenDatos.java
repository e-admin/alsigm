package descripcion.model.automaticos;

import java.util.Map;

import common.bi.ServiceSession;

/**
 * Interfaz para las clases que realicen los cálculos automáticos de los valores
 * de las fichas.
 */
public interface IADReglaGenDatos {

	/**
	 * Inicializa la clase con unos parámetros.
	 * 
	 * @param parametros
	 *            Parámetros de la clase.
	 */
	public void inicializa(Map parametros);

	/**
	 * Genera los datos automáticos.
	 * 
	 * @param session
	 *            Sesión de base de datos.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @throws ADReglaGenDatosException
	 *             si ocurre algún error.
	 */
	public void generacionDatosAutomaticos(ServiceSession session, String id)
			throws ADReglaGenDatosException;
}
