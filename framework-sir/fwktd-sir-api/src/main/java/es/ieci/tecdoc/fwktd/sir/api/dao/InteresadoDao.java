package es.ieci.tecdoc.fwktd.sir.api.dao;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Interfaz de los DAOs de interesados de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface InteresadoDao extends
		BaseDao<InteresadoVO, String> {

	/**
	 * Elimina los interesados de un asiento registral.
	 *
	 * @param idAsientoRegistral
	 *            Identificador del asiento registral.
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral);

}
