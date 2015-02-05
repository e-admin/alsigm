package es.ieci.tecdoc.fwktd.sir.api.dao;

import java.util.Map;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;

/**
 * Interfaz de los DAOs de parámetros de configuración.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface ConfiguracionDao extends BaseDao<ConfiguracionVO, String> {

	/**
	 * Obtiene el valor de un parámetro de configuración.
	 * 
	 * @param nombre
	 *            Nombre del parámetro.
	 * @return Valor del parámetro de configuración.
	 */
	public String getValorConfiguracion(String nombre);

	/**
	 * Obtiene los valores de unos parámetros de configuración.
	 * 
	 * @param nombres
	 *            Nombres de parámetros de configuración.
	 * @return Valores de los parámetros de configuración.
	 */
	public Map<String, String> getValoresConfiguracion(String[] nombres);
}
