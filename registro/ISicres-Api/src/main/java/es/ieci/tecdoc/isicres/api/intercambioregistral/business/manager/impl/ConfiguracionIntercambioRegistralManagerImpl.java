package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioRelacionUnidOrgOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosRelacionUnidOrgOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.ConfiguracionIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class ConfiguracionIntercambioRegistralManagerImpl implements
		ConfiguracionIntercambioRegistralManager {

	private static Logger logger = Logger.getLogger(ConfiguracionIntercambioRegistralManagerImpl.class);

	/**
	 * DAO para el acceso a los datos de configuración de intercambio registral (mapeo de entidades registrales y unidades de tramitacion)
	 */
	protected ConfiguracionIntercambioRegistralDAO configuracionIntercambioRegistralDAO;
	protected ServicioConsultaDirectorioComun servicioConsultaDirectorioComun;

	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByCodigosComunes(
			String codeEntidadRegistral, String codeUnidadTramitacion) {
		UnidadAdministrativaIntercambioRegistralVO unidadAdministrativa = null;

		if(codeUnidadTramitacion!=null)
		{
			unidadAdministrativa = getConfiguracionIntercambioRegistralDAO().getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(codeUnidadTramitacion, codeEntidadRegistral);
		}
		//Si con el de unidad de tramitación no obtuvimos nada, lo intentamos con el de entidad registral
		if(unidadAdministrativa==null)
		{
			unidadAdministrativa = getConfiguracionIntercambioRegistralDAO().getUnidadAdmimistrativaByCodigoEntidadRegistral(codeEntidadRegistral);
		}
		return unidadAdministrativa;
	}


	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(String codigoUnidadTramitacion, String codigoEntidadRegistral){
		return getConfiguracionIntercambioRegistralDAO().getUnidadAdmimistrativaByCodigoEntidadRegistralYUnidadTramitacion(codigoUnidadTramitacion, codigoEntidadRegistral);
	}


	public List<UnidadAdministrativaIntercambioRegistralVO> getUnidadAdmimistrativaByCodigoEntidadRegistral(
			String codigo){
		return getConfiguracionIntercambioRegistralDAO().getUnidadAdministrativaByCodidgoER(codigo);
	}


	public EntidadRegistralVO getEntidadRegistralVOByIdScrOfic(String idOfic) {
		return getConfiguracionIntercambioRegistralDAO().getEntidadRegistralVOByIdScrOfic(idOfic);
	}

	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(
			String idScrOrgs) {
		return getConfiguracionIntercambioRegistralDAO().getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(idScrOrgs);
	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager#buscarEntidadesRegistralesDCO(java.lang.String, java.lang.String)
	 */
	public List<EntidadRegistralDCO> buscarEntidadesRegistralesDCO(String code, String nombre){
		Criterios<CriterioOficinaEnum> criterios = new Criterios<CriterioOficinaEnum>();

		if(StringUtils.isNotEmpty(code))
		{
			criterios.addCriterio(new Criterio<CriterioOficinaEnum>(CriterioOficinaEnum.OFICINA_ID, OperadorCriterioEnum.LIKE, code));
		}
		if(StringUtils.isNotEmpty(nombre))
		{
			criterios.addCriterio(new Criterio<CriterioOficinaEnum>(CriterioOficinaEnum.OFICINA_NOMBRE, OperadorCriterioEnum.LIKE, nombre));
		}

		List<DatosBasicosOficina> listaEntidadesRegistralesDCO = getServicioConsultaDirectorioComun().findOficinas(criterios);

		return getListaEntidadesRegistralesVO(listaEntidadesRegistralesDCO);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager#buscarUnidadesTramitacionDCO(java.lang.String, java.lang.String)
	 */
	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCO(String code, String nombre){
		Criterios<CriterioUnidadOrganicaEnum> criterios = new Criterios<CriterioUnidadOrganicaEnum>();
		if(StringUtils.isNotEmpty(code))
		{
			criterios.addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(CriterioUnidadOrganicaEnum.UO_ID, OperadorCriterioEnum.LIKE, code));
		}
		if(StringUtils.isNotEmpty(nombre))
		{
			criterios.addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(CriterioUnidadOrganicaEnum.UO_NOMBRE, OperadorCriterioEnum.LIKE, nombre));
		}

		List<DatosBasicosUnidadOrganica> listaUnidadesTramitacionDCO = getServicioConsultaDirectorioComun().findUnidadesOrganicas(criterios);

		return getListaUnidadesTramitacionVO(listaUnidadesTramitacionDCO);
	}

	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCOByEntidad(String codeEntity, String code, String nombre){


		List<DatosBasicosUnidadOrganica> listaUnidadesTramitacionDCO = getServicioConsultaDirectorioComun().findUnidadesOrganicasByEntidad(codeEntity, code, nombre);

		return getListaUnidadesTramitacionVO(listaUnidadesTramitacionDCO);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager#existRelacionUnidOrgaOficina(java.lang.String, java.lang.String)
	 */
	public boolean existRelacionUnidOrgaOficina(String codeEntidadRegistral,
			String codeUnidadTramitacion) {
		boolean result = false;
		//consultamos
		DatosBasicosRelacionUnidOrgOficina relacion = getServicioConsultaDirectorioComun()
				.getDatosBasicosRelacionUnidOrgOficinaByCodes(
						codeEntidadRegistral, codeUnidadTramitacion);

		//si la consulta recupera información
		if (relacion != null) {
			//existe relación
			result = true;
		}

		return result;
	}

	private List<UnidadTramitacionDCO> getListaUnidadesTramitacionVO(
			List<DatosBasicosUnidadOrganica> listaUnidadesTramitacion) {
		ListIterator<DatosBasicosUnidadOrganica> itr = listaUnidadesTramitacion.listIterator();
		List<UnidadTramitacionDCO> listaUnidadesTramitacionDCO = new ArrayList<UnidadTramitacionDCO>();
		while(itr.hasNext())
		{
			DatosBasicosUnidadOrganica unidad = itr.next();
			listaUnidadesTramitacionDCO.add(getUnidadTramitacionVO(unidad));
		}
		return listaUnidadesTramitacionDCO;
	}

	private UnidadTramitacionDCO getUnidadTramitacionVO(DatosBasicosUnidadOrganica unidadOrganicaDCO){
		UnidadTramitacionDCO unidadTramitacionDCO = new UnidadTramitacionDCO();
		BeanUtils.copyProperties(unidadOrganicaDCO, unidadTramitacionDCO);
		return unidadTramitacionDCO;
	}

	private List<EntidadRegistralDCO> getListaEntidadesRegistralesVO(
			List<DatosBasicosOficina> listaEntidadesRegistrales) {
		ListIterator<DatosBasicosOficina> itr = listaEntidadesRegistrales.listIterator();
		List<EntidadRegistralDCO> listaEntidadesRegistralesDCO = new ArrayList<EntidadRegistralDCO>();
		while(itr.hasNext())
		{
			DatosBasicosOficina entidad = itr.next();
			listaEntidadesRegistralesDCO.add(getEntidadRegistralVO(entidad));
		}
		return listaEntidadesRegistralesDCO;
	}

	private EntidadRegistralDCO getEntidadRegistralVO(DatosBasicosOficina entidadRegistral){
		EntidadRegistralDCO entidadRegistralDCO = new EntidadRegistralDCO();
		BeanUtils.copyProperties(entidadRegistral, entidadRegistralDCO);
		return entidadRegistralDCO;
	}

	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(
			String codeScrOrgs) {
		return getConfiguracionIntercambioRegistralDAO().getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(codeScrOrgs);
	}



	public ConfiguracionIntercambioRegistralDAO getConfiguracionIntercambioRegistralDAO() {
		return configuracionIntercambioRegistralDAO;
	}

	public void setConfiguracionIntercambioRegistralDAO(
			ConfiguracionIntercambioRegistralDAO configuracionIntercambioRegistralDAO) {
		this.configuracionIntercambioRegistralDAO = configuracionIntercambioRegistralDAO;
	}


	public ServicioConsultaDirectorioComun getServicioConsultaDirectorioComun() {
		return servicioConsultaDirectorioComun;
	}


	public void setServicioConsultaDirectorioComun(
			ServicioConsultaDirectorioComun servicioConsultaDirectorioComun) {
		this.servicioConsultaDirectorioComun = servicioConsultaDirectorioComun;
	}


}
