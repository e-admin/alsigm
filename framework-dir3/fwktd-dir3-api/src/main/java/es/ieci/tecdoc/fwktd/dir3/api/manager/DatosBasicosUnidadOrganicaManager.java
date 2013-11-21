package es.ieci.tecdoc.fwktd.dir3.api.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

/**
 * Interfaz para los managers de datos básicos de unidades orgánicas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DatosBasicosUnidadOrganicaManager extends
		BaseManager<DatosBasicosUnidadOrganicaVO, String> {

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


	/**
	 * Realiza una búsqueda de unidades orgánicas según el código de unidad indicado
	 *
	 * @param codeUnidadOrganica - Código de unidad organica a buscar
	 * @return {@link DatosBasicosUnidadOrganicaVO} o NULO en caso de no encontrar nada
	 */
	public DatosBasicosUnidadOrganicaVO getDatosBasicosUnidadOrganicaByCode(
			String codeUnidadOrganica);
	/**
	 * Guarda los datos basicos de las unidades obtenidas del DCO
	 *
	 * @param organismosDCO
	 */
	public void saveDatosBasicosUnidadesOrganicas(OrganismosVO organismosDCO);

	/**
	 * Actualiza los datos basicos de las unidades obtenidas del DCO
	 * @param organismosDCO
	 */
	public void updateDatosBasicosUnidadesOrganicas(OrganismosVO organismosDCO);

	/**
	 * Realiza una búsqueda de unidades orgánicas. Según la relación con la entidad.
	 *
	 * @param relacion - Relación entre los parámetros de la unid. orgánica a buscar y la entidad.
	 *
	 * @return Unidades orgánicas encontradas.
	 */
	public List<DatosBasicosUnidadOrganicaVO> findUnidadesOrganicasByEntidad(
			DatosBasicosRelacionUnidOrgOficinaVO relacion);
}
