package es.ieci.tecdoc.fwktd.csv.api.dao;

import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

/**
 * Interfaz de los DAOs de aplicaciones.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AplicacionDao extends BaseDao<AplicacionVO, String> {

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
