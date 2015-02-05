package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;

/**
 * Interfaz para los managers que gestionen los contadores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ContadorManager {

	/**
	 * Obtiene el siguiente valor del contador asociado a la entidad registral.
	 * @param codigoEntidadRegistral Código de la entidad registral.
	 * @param tipoContador Tipo de contador.
	 * @return Valor del contador.
	 */
	public String updateContador(String codigoEntidadRegistral, TipoContadorEnum tipoContador);
}
