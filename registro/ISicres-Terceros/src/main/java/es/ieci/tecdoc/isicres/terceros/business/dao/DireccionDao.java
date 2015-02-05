package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;

public interface DireccionDao extends BaseDao<BaseDireccionVO, String> {
	/**
	 * Elimina todas las direcciones asociadas al tercero validado
	 * <code>tercero</code>.
	 *
	 * @param tercero
	 */
	public void deleteAll(BaseTerceroVO tercero);

	/**
	 * Devuelve todas las direcciones telemáticas existentes.
	 *
	 * @return
	 */
	public List<DireccionTelematicaVO> getAllDireccionesTelematicas();

	/**
	 * Devuelve todas las direcciones físicas existentes.
	 *
	 * @return
	 */
	public List<DireccionFisicaVO> getAllDireccionesFisicas();

	/**
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void updateDireccionPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero);
}
