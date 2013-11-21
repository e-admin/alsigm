package es.ieci.tecdoc.fwktd.dir3.api.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionesUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

public interface DatosBasicosRelacionUnidOrgOficinaManager extends
		BaseManager<DatosBasicosRelacionUnidOrgOficinaVO, String> {
	/**
	 * Obtiene el número de relaciones entre las oficinas y las unid. orgánicas
	 * encontradas según los criterios de búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de relaciones entre las oficinas y las unid. orgánicas
	 *         encontradas.
	 */
	public int countRelaciones(Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Realiza una búsqueda de relaciones entre las oficinas y las unid.
	 * orgánicas.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Relaciones entre las oficinas y las unid. orgánicas encontradas.
	 */
	public List<DatosBasicosRelacionUnidOrgOficinaVO> findRelaciones(
			Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Metodo que obtiene la información de relaciones entre las oficinas y las
	 * unid. orgánicas según el id de la oficina
	 *
	 * @param id
	 *            - id de la oficina a buscar
	 * @return {@link DatosBasicosRelacionUnidOrgOficinaVO} o NULO en caso de no
	 *         encontrar nada
	 */
	public DatosBasicosRelacionUnidOrgOficinaVO getRelacionesByOficinaAndUnidad(
			String codOficina, String codUnidad);

	/**
	 * Guarda los datos basicos de las relaciones entre las oficinas y las unid.
	 * orgánicas obtenidas del DCO
	 *
	 * @param oficinasDCO
	 */
	public void saveDatosBasicosRelacionesUnidOrgOficinaVO(
			RelacionesUnidOrgOficinaVO relacionesUnidOrgOficina);

	/**
	 * Actualiza los datos basicos de las relaciones entre las oficinas y las
	 * unid. orgánicas obtenidas del DCO
	 *
	 * @param oficinasDCO
	 */
	public void updateDatosBasicosRelacionesUnidOrgOficinaVO(
			RelacionesUnidOrgOficinaVO relacionesUnidOrgOficina);
}
