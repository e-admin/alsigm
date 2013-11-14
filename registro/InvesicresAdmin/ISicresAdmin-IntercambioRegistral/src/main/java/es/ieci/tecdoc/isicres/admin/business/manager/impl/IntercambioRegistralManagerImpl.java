package es.ieci.tecdoc.isicres.admin.business.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.util.CollectionUtils;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;
import es.ieci.tecdoc.isicres.admin.business.dao.IntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.admin.business.util.DatosBasicosOficinaDCHelper;
import es.ieci.tecdoc.isicres.admin.business.util.DatosBasicosUnidadOrganicaDCHelper;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosOficinaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosUnidadOrganicaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;

/**
 *
 * @author iecisa
 *
 */
public class IntercambioRegistralManagerImpl implements IntercambioRegistralManager{

	protected ServicioConsultaDirectorioComun servicioConsultaDirectorioComun;
	protected IntercambioRegistralDAO intercambioRegistralDAO;
	protected DataFieldMaxValueIncrementer entidadRegistralIncrementer;
	protected DataFieldMaxValueIncrementer unidadRegistralIncrementer;

	private static final Logger logger = Logger.getLogger(IntercambioRegistralManagerImpl.class);

	public List getEntidadesRegistrales() throws ISicresAdminIntercambioRegistralException{
		return getIntercambioRegistralDAO().getEntidadesRegistrales();
	}

	public EntidadRegistralVO addEntidadRegistral (
			EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException{

		validarIdOficNoUtilizado(entidadRegistral.getIdOfic());
		int id = getEntidadRegistralIncrementer().nextIntValue();
		entidadRegistral.setId(id);
		return getIntercambioRegistralDAO().addEntidadRegistral(entidadRegistral);
	}

	public UnidadRegistralVO addUnidadRegistral(
			UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException{
		validarIdOrgsNoUtilizado(unidadRegistral.getId_orgs());
		int id = getUnidadRegistralIncrementer().nextIntValue();
		unidadRegistral.setId(id);
		return getIntercambioRegistralDAO().addUnidadRegistral(unidadRegistral);
	}

	private void validarIdOficNoUtilizado(int idOfic) throws ISicresAdminIntercambioRegistralException
	{
		List oficinaConEntidad = getIntercambioRegistralDAO().findEntitadaRegistralByOficina(idOfic);
		if(!CollectionUtils.isEmpty(oficinaConEntidad) || !CollectionUtils.isEmpty(oficinaConEntidad)){
			throw new ISicresAdminIntercambioRegistralException(ISicresAdminIntercambioRegistralException.EXC_ID_OFIC_YA_REGISTRADO);
		}
	}

	private void validarIdOrgsNoUtilizado(int idOrgs) throws ISicresAdminIntercambioRegistralException
	{
		List orgsConEntidad = getIntercambioRegistralDAO().getUnidadRegistralByIdOrgs(idOrgs);
		if(!CollectionUtils.isEmpty(orgsConEntidad) || !CollectionUtils.isEmpty(orgsConEntidad)){
			throw new ISicresAdminIntercambioRegistralException(ISicresAdminIntercambioRegistralException.EXC_ID_ORGS_YA_REGISTRADO);
		}
	}

	public void deleteEntidadRegistral(EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException{
		getIntercambioRegistralDAO().deleteEntidadRegistral(entidadRegistral);
	}

	public void deleteUnidadRegistral(UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException{
		getIntercambioRegistralDAO().deleteUnidadRegistral(unidadRegistral);
	}

	public EntidadRegistralVO getEntidadRegistral(int id) throws ISicresAdminIntercambioRegistralException{
		EntidadRegistralVO entidadResult = getIntercambioRegistralDAO().getEntidadRegistral(id);
		return entidadResult;
	}

	public EntidadRegistralVO getEntidadRegistralByIdOficina(int idOfic) throws ISicresAdminIntercambioRegistralException{
		List entidadResults = getIntercambioRegistralDAO().findEntitadaRegistralByOficina(idOfic);
		EntidadRegistralVO result = null;
		if(entidadResults!=null && entidadResults.size() >0){
			if(entidadResults.size() == 1){
				result = (EntidadRegistralVO) entidadResults.get(0);
			}else{
				logger.error("La oficina ["
						+ idOfic
						+ "] se encuentra asociada a mas de una Entidad Registral");
				throw new ISicresAdminIntercambioRegistralException(
						ISicresAdminIntercambioRegistralException.EXC_GENERIC_EXCEPCION);
			}
		}
		return result;
	}

	public UnidadRegistralVO getUnidadRegistral(int id) throws ISicresAdminIntercambioRegistralException{
		UnidadRegistralVO unidadResult = getIntercambioRegistralDAO().getUnidadRegistral(id);
		return unidadResult;
	}

	public EntidadRegistralVO updateEntidadRegistral(
			EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException{
		EntidadRegistralVO entidadResult = entidadRegistral;
		try{
			getIntercambioRegistralDAO().updateEntidadRegistral(entidadRegistral);
		}catch(Exception e){
			logger.error(e);
			throw new ISicresAdminIntercambioRegistralException(ISicresAdminIntercambioRegistralException.EXC_ERROR_NOT_UPDATE_DATA);
		}
		return entidadResult;
	}

	public UnidadRegistralVO updateUnidadRegistral(
			UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException{
		UnidadRegistralVO unidadResult = unidadRegistral;
		try{
			getIntercambioRegistralDAO().updateUnidadRegistral(unidadRegistral);
		}catch(Exception e){
			logger.error(e);
			throw new ISicresAdminIntercambioRegistralException(ISicresAdminIntercambioRegistralException.EXC_ERROR_NOT_UPDATE_DATA);
		}
		return unidadResult;
	}

	public UnidadRegistralVO getUnidadRegistralByIdOrgs(int idOrgs) throws ISicresAdminIntercambioRegistralException{
		List unidadesRegistralesResult = getIntercambioRegistralDAO().getUnidadRegistralByIdOrgs (idOrgs);
		UnidadRegistralVO result = null;
		if(unidadesRegistralesResult!=null && unidadesRegistralesResult.size() >0){
			if(unidadesRegistralesResult.size() == 1){
				result = (UnidadRegistralVO) unidadesRegistralesResult.get(0);
			}else{
				logger.error("La unidad administrativa ["
						+ idOrgs
						+ "] se encuentra asociada a mas de una Unidad Registral");
				throw new ISicresAdminIntercambioRegistralException(
						ISicresAdminIntercambioRegistralException.EXC_GENERIC_EXCEPCION);
			}
		}
		return result;
	}

	public List findUnidadesRegistrales(UnidadRegistralVO unidad){

		return getIntercambioRegistralDAO().findUnidadesRegistrales(unidad);

	}

	/**
	 * {@inheritDoc}
	 */
	public List<DatosBasicosOficinaDCVO> findOficinasDirectorioComun(
			Criterios<CriterioOficinaEnum> criteriosBusqueda) {
		List<DatosBasicosOficina> oficinas = getServicioConsultaDirectorioComun().findOficinas(criteriosBusqueda);
		return DatosBasicosOficinaDCHelper.getDatosBasicosOficinasDCVO(oficinas);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComun(
			Criterios<CriterioUnidadOrganicaEnum> criteriosBusqueda) {
		List<DatosBasicosUnidadOrganica> unidadesOrganicas = getServicioConsultaDirectorioComun().findUnidadesOrganicas(criteriosBusqueda);
		return DatosBasicosUnidadOrganicaDCHelper.getDatosBasicosOficinasDCVO(unidadesOrganicas);
	}

	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComunByCodEntidad(
			String codEntidad, String codUnidad, String nombreUnidad) {
		List<DatosBasicosUnidadOrganica> unidadesOrganicas = getServicioConsultaDirectorioComun()
				.findUnidadesOrganicasByEntidad(codEntidad, codUnidad, nombreUnidad);
		return DatosBasicosUnidadOrganicaDCHelper
				.getDatosBasicosOficinasDCVO(unidadesOrganicas);
	}


	public IntercambioRegistralDAO getIntercambioRegistralDAO() {
		return intercambioRegistralDAO;
	}

	public void setIntercambioRegistralDAO (
			IntercambioRegistralDAO intercambioRegistralDAO) {
		this.intercambioRegistralDAO = intercambioRegistralDAO;
	}

	public DataFieldMaxValueIncrementer getEntidadRegistralIncrementer() {
		return entidadRegistralIncrementer;
	}

	public void setEntidadRegistralIncrementer(
			DataFieldMaxValueIncrementer entidadRegistralIncrementer) {
		this.entidadRegistralIncrementer = entidadRegistralIncrementer;
	}

	public DataFieldMaxValueIncrementer getUnidadRegistralIncrementer() {
		return unidadRegistralIncrementer;
	}

	public void setUnidadRegistralIncrementer(
			DataFieldMaxValueIncrementer unidadRegistralIncrementer) {
		this.unidadRegistralIncrementer = unidadRegistralIncrementer;
	}

	public ServicioConsultaDirectorioComun getServicioConsultaDirectorioComun() {
		return servicioConsultaDirectorioComun;
	}

	public void setServicioConsultaDirectorioComun(
			ServicioConsultaDirectorioComun servicioConsultaDirectorioComun) {
		this.servicioConsultaDirectorioComun = servicioConsultaDirectorioComun;
	}



}

