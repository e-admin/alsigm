package es.ieci.tecdoc.fwktd.dir3.api.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

/**
 * Interfaz de los DAOs de datos básicos de unidades orgánicas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DatosBasicosUnidadOrganicaDao extends
		BaseDao<DatosBasicosUnidadOrganicaVO, String> {

	/**
	 * Obtiene el número de unidades orgánicas encontradas según los criterios
	 * de búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de unidades orgánicas encontradas.
	 */
	public int countUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios);

	/**
	 * Realiza una búsqueda de unidades orgánicas.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Unidades orgánicas encontradas.
	 */
	public List<DatosBasicosUnidadOrganicaVO> findUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios);

	public List<DatosBasicosUnidadOrganicaVO> findUnidadesOrganicasByEntidad(DatosBasicosRelacionUnidOrgOficinaVO relacion);

}
