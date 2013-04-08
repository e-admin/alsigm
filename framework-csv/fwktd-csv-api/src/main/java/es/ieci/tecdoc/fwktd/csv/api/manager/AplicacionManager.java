package es.ieci.tecdoc.fwktd.csv.api.manager;

import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

/**
 * Interfaz para los managers de aplicaciones.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AplicacionManager extends BaseManager<AplicacionVO, String> {

	/**
	 * Obtiene la información de la aplicación a partir del código.
	 *
	 * @param codigo
	 *            Código de la aplicación.
	 * @return Información de la aplicación.
	 */
	public AplicacionVO getAplicacionByCodigo(String codigo);

	/**
	 * Elimina la información de la aplicación a partir del código.
	 *
	 * @param codigo
	 *            Código de la aplicación.
	 */
	public void deleteAplicacionByCodigo(String codigo);
}
