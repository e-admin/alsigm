package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public interface ConfiguracionIntercambioRegistralManager {


	/**
	 * Obtiene la unidad administrativa a la que pertenecen los codigos del directorio comun pasados como parametro
	 * En caso de que este mapeada la unidad de tramitacion, se obtiene ese, si no, el de entidad registral.
	 * @param codeEntidadRegistral
	 * @param codeUnidadTramitacion
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByCodigosComunes(String codeEntidadRegistral, String codeUnidadTramitacion);


	/**
	 * Obtiene la unidad administrativa con la cual esta mapeada la siguiente
	 * unidad de tramitacion del modulo comun (mapeo inverso)
	 * @param codigoUnidadTramitacion
	 * @param codigoEntidadRegistral
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(String codigoUnidadTramitacion, String codigoEntidadRegistral);

	/**
	 * Obtiene la lista de unidades administrativas que están mapeadas con la entidad registral del modulo comun.
	 *
	 * @param codigo
	 * @return
	 */
	public List<UnidadAdministrativaIntercambioRegistralVO> getUnidadAdmimistrativaByCodigoEntidadRegistral(
			String codigo);
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
	 * Obtiene la unidad de tramitacion mapeada en el modulo comun para este <code>codeSrcOrgs</code>
	 * @param codeScrOrgs
	 * @return
	 */
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(String codeScrOrgs );

	/**
	 * Método de búsqueda de Entidades Registrales en el DCO
	 * @param code
	 * @param nombre
	 * @return Lista de <code>EntidadRegistralDCO</code> del DCO que concuerden con el code y nombre pasado
	 */
	public List<EntidadRegistralDCO> buscarEntidadesRegistralesDCO(String code, String nombre);

	/**
	 * Método de búsqueda de Unidades de Tramitación en el DCO
	 * @param code
	 * @param nombre
	 * @return Lista de <code>UnidadTramitacionDCO</code> del DCO que concuerden con el code y nombre pasado
	 */
	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCO(String code, String nombre);

	/**
	 * Método de búsqueda de Unidades de Tramitación en el DCO filtradas por entidad
	 *
	 * @param codeEntity - Código de la entidad
	 * @param code - Código de la unid.
	 * @param nombre - Nombre de la unid.
	 * @return Lista de <code>UnidadTramitacionDCO</code> del DCO que concuerden con el cod. de la entidad además de code y nombre indicado
	 */
	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCOByEntidad(String codeEntity, String code, String nombre);

	/**
	 * Método que comprueba la existencia de la relación entre la entidad y la unid. de tramitación
	 * @param codeEntidadRegistral
	 * @param codeUnidadTramitacion
	 * @return boolean TRUE: Existe la relación / False: No existe la relación
	 */
	public boolean existRelacionUnidOrgaOficina(String codeEntidadRegistral, String codeUnidadTramitacion);
}
