package es.ieci.tecdoc.isicres.terceros.business.dao;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;

/**
 *
 * @author IECISA
 *
 */
public interface DireccionFisicaDao extends BaseDao<DireccionFisicaVO, String> {
	/**
	 * Elimina todas las direcciones asociadas al tercero validado
	 * <code>tercero</code>.
	 *
	 * @param tercero
	 */
	public void deleteAll(BaseTerceroVO tercero);

	/**
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void updateDireccionPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero);
}
