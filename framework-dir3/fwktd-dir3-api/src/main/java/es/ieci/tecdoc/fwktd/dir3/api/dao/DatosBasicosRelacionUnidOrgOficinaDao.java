package es.ieci.tecdoc.fwktd.dir3.api.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

/**
 * Interfaz de los DAOs de datos básicos de la relación entre unid. orgánicas y
 * las oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DatosBasicosRelacionUnidOrgOficinaDao extends
		BaseDao<DatosBasicosRelacionUnidOrgOficinaVO, String> {
	/**
	 * Obtiene el número de relaciones encontradas según los criterios de
	 * búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de oficinas encontradas.
	 */
	public int countRelacionesUnidOrgOficina(
			Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Realiza una búsqueda de la relaciones se
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Oficinas encontradas.
	 */
	public List<DatosBasicosRelacionUnidOrgOficinaVO> findRelacionesUnidOrgOficina(
			Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Borra la relación de la unid. orgánica y la oficina indicada como
	 * parámetro
	 *
	 * @param codOficina
	 * @param codUnidOrg
	 */
	public void deleteRelacionesUnidOrgOficina(String codOficina,
			String codUnidOrg);
}
