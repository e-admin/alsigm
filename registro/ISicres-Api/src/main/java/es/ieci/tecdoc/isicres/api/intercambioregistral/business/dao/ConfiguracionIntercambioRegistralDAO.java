package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

/**
 * DAO para leer configuraciones de intercambio registral (mapeos de unidades administrativas
 * con entidades registrales o unidades de tramitación del módulo comun
 *
 *
 */
public interface ConfiguracionIntercambioRegistralDAO {


	/**
	 * Obtiene la entidad registral mapeada en el modulo comun para este <code>idScrOfic</code>
	 * @param idSrcOfic
	 * @return
	 */
	public EntidadRegistralVO getEntidadRegistralVOByIdScrOfic(String idOfic);

	/**
	 * Obtiene la unidad de tramitacion mapeada en el modulo comun para este <code>idScrOrgs</code>
	 * @param idScrOrgs
	 * @return
	 */
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(String idScrOrgs );

	/**
	 * Obtiene la unidad administrativa con la cual esta mapeada la siguiente
	 * entidad registral del modulo comun (mapeo inverso)
	 * @param codigo
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistral(String codigo);

	/**
	 * Obtiene la lista de unidades administrativas que están mapeadas con la entidad registral del modulo comun.
	 *
	 * @param codigo
	 * @return
	 */
	public List<UnidadAdministrativaIntercambioRegistralVO> getUnidadAdministrativaByCodidgoER(
			String codigo);
	/**
	 * Obtiene la unidad administrativa con la cual esta mapeada la siguiente
	 * unidad de tramitacion del modulo comun (mapeo inverso)
	 * @param codigoUnidadTramitacion
	 * @param codigoEntidadRegistral
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(String codigoUnidadTramitacion, String codigoEntidadRegistral);

	/**
	 * Obtiene la unidad administrativa (ScrOrgs) que tiene asociada la oficina <code>idOficina</code>
	 * @param idOficina
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByOficina(Integer idOficina);

	/**
	 * Obtiene la unidad de tramitación mapeada con la unidad administrativa con codigo <code>codeSrcOrgs</code>
	 * @param codeScrOrgs
	 * @return
	 */
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(
			String codeScrOrgs);
}
