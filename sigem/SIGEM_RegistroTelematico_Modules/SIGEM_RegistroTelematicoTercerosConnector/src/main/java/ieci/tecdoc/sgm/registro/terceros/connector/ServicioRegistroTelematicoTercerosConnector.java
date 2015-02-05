package ieci.tecdoc.sgm.registro.terceros.connector;

import ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO;

/**
 * Interfaz para una conexión con terceros en registro telemático
 * @author iecisa
 *
 */
public interface ServicioRegistroTelematicoTercerosConnector {

	/**
	 * Método que busca un tercero y sus direcciones a partir de su <code>identificador</code> en un sistema externo.
	 * @param identificador
	 * @return TerceroVO tercero encontrado o null en caso de no encontrarse. El tercero lleva una lista de direcciones
	 */
	public TerceroVO buscarTercero(String identificador);

	/**
	 * Método que busca un tercero y sus direcciones a partir de su <code>identificador</code> en un sistema externo.
	 * @param entidad
	 * @param identificador
	 * @return TerceroVO tercero encontrado o null en caso de no encontrarse. El tercero lleva una lista de direcciones
	 */
	public TerceroVO buscarTerceroPorEntidad(String entidad,String identificador);

}
