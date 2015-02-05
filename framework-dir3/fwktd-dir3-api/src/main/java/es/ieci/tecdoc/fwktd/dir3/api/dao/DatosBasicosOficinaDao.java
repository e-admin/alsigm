package es.ieci.tecdoc.fwktd.dir3.api.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

/**
 * Interfaz de los DAOs de datos básicos de oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DatosBasicosOficinaDao extends
		BaseDao<DatosBasicosOficinaVO, String> {

	/**
	 * Obtiene el número de oficinas encontradas según los criterios de
	 * búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de oficinas encontradas.
	 */
	public int countOficinas(Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Realiza una búsqueda de oficinas.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Oficinas encontradas.
	 */
	public List<DatosBasicosOficinaVO> findOficinas(
			Criterios<CriterioOficinaEnum> criterios);

}
