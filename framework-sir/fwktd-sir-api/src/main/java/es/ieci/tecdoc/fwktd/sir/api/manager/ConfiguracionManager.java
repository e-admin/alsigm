package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;

/**
 * Interfaz para los managers de parámetros de configuración.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ConfiguracionManager extends BaseManager<ConfiguracionVO, String> {

	/**
	 * Obtiene el valor de un parámetro de configuración.
	 * 
	 * @param nombre
	 *            Nombre del parámetro.
	 * @return Valor del parámetro de configuración.
	 */
	public String getValorConfiguracion(String nombre);

	/**
	 * Obtiene el valor de un parámetro de configuración. Comprueba los
	 * parámetros por orden de lista y devuelve el primer valor no vacío.
	 * 
	 * @param nombres
	 *            Nombres de parámetros.
	 * @param valorPorDefecto
	 *            Valor por defecto.
	 * @return Valor del parámetro de configuración.
	 */
	public String getValorConfiguracion(String[] nombres);

}
