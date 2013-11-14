package es.ieci.tecdoc.fwktd.sir.ws.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriterioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.PageInfoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * Implementación del el gestor de intercambio registral que utilizarán las
 * aplicaciones de registro de backoffice.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class IntercambioRegistralManagerImpl implements
		IntercambioRegistralManager {

	/**
	 * Servicio de intercambio registral.
	 */
	private ServicioIntercambioRegistral servicioIntercambioRegistral = null;

	/**
	 * Constructor.
	 */
	public IntercambioRegistralManagerImpl() {
		super();
	}

	public ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return servicioIntercambioRegistral;
	}

	public void setServicioIntercambioRegistral(
			ServicioIntercambioRegistral servicioIntercambioRegistral) {
		this.servicioIntercambioRegistral = servicioIntercambioRegistral;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#countAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.ws.dto.CriteriosDTO)
	 */
	public int countAsientosRegistrales(CriteriosDTO criterios) {
		return getServicioIntercambioRegistral().countAsientosRegistrales(
				getCriteriosVO(criterios));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#findAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.ws.dto.CriteriosDTO)
	 */
	public List<AsientoRegistralDTO> findAsientosRegistrales(
			CriteriosDTO criterios) {
		return getListaAsientoRegistralDTO(getServicioIntercambioRegistral()
				.findAsientosRegistrales(getCriteriosVO(criterios)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#getEstadoAsientoRegistral(java.lang.String)
	 */
	public EstadoAsientoRegistralDTO getEstadoAsientoRegistral(String id) {
		EstadoAsientoRegistralDTO result = null ;
		EstadoAsientoRegistraVO estado = getServicioIntercambioRegistral().getEstadoAsientoRegistral(id);
		result = getEstadoAsientoRegistralDTO(estado);
		return result;
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#getAsientoRegistral(java.lang.String)
	 */
	public AsientoRegistralDTO getAsientoRegistral(String id) {
		return getAsientoRegistralDTO(getServicioIntercambioRegistral()
				.getAsientoRegistral(id));
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO)
	 */
	public AsientoRegistralDTO saveAsientoRegistral(AsientoRegistralFormDTO asiento) {
		return getAsientoRegistralDTO(getServicioIntercambioRegistral()
				.saveAsientoRegistral(getAsientoRegistralFormVO(asiento)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#updateAsientoRegistral(es.ieci.tecdoc.fwktd.sir.ws.dto.InfoBAsientoRegistralDTO)
	 */
	public void updateAsientoRegistral(InfoBAsientoRegistralDTO infoBAsiento) {
		getServicioIntercambioRegistral().updateAsientoRegistral(
				getInfoBAsientoRegistralVO(infoBAsiento));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#deleteAsientoRegistral(java.lang.String)
	 */
	public void deleteAsientoRegistral(String id) {
		getServicioIntercambioRegistral().deleteAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#addAnexo(java.lang.String, es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO)
	 */
	public AnexoDTO addAnexo(String idAsientoRegistral, AnexoFormDTO anexo) {
		return getAnexoDTO(getServicioIntercambioRegistral().addAnexo(idAsientoRegistral, getAnexoFormVO(anexo)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#updateAnexo(es.ieci.tecdoc.fwktd.sir.ws.dto.AnexoDTO)
	 */
	public AnexoDTO updateAnexo(AnexoDTO anexo) {
		return getAnexoDTO(getServicioIntercambioRegistral().updateAnexo(getAnexoVO(anexo)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#removeAnexo(java.lang.String)
	 */
	public void removeAnexo(String idAnexo) {
		getServicioIntercambioRegistral().removeAnexo(idAnexo);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#addInteresado(java.lang.String, es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO)
	 */
	public InteresadoDTO addInteresado(String idAsientoRegistral, InteresadoFormDTO interesado) {
		return getInteresadoDTO(getServicioIntercambioRegistral()
				.addInteresado(idAsientoRegistral, getInteresadoFormVO(interesado)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#updateInteresado(es.ieci.tecdoc.fwktd.sir.ws.dto.InteresadoDTO)
	 */
	public InteresadoDTO updateInteresado(InteresadoDTO interesado) {
		return getInteresadoDTO(getServicioIntercambioRegistral()
				.updateInteresado(getInteresadoVO(interesado)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#removeInteresado(java.lang.String)
	 */
	public void removeInteresado(String idInteresado) {
		getServicioIntercambioRegistral().removeInteresado(idInteresado);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#getContenidoAnexo(java.lang.String)
	 */
	public byte[] getContenidoAnexo(String id) {
		return getServicioIntercambioRegistral().getContenidoAnexo(id);
	}

	public void setContenidoAnexo(String id, byte[] contenido) {
		getServicioIntercambioRegistral().setContenidoAnexo(id, contenido);
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#getHistoricoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadDTO> getHistoricoAsientoRegistral(String id) {
		return getListaTrazabilidadDTO(getServicioIntercambioRegistral()
				.getHistoricoAsientoRegistral(id));
	}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#getHistoricoMensajeIntercambioRegistral(java.lang.String)
	 */
	public List<TrazabilidadDTO> getHistoricoMensajeIntercambioRegistral(String id) {
		return getListaTrazabilidadDTO(getServicioIntercambioRegistral()
				.getHistoricoMensajeIntercambioRegistral(id));
	}
	
	public List<TrazabilidadDTO> getHistoricoCompletoAsientoRegistral(String id) {
		return getListaTrazabilidadDTO(getServicioIntercambioRegistral()
				.getHistoricoCompletoAsientoRegistral(id));
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#enviarAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
	 */
	public AsientoRegistralDTO enviarAsientoRegistral(AsientoRegistralFormDTO asientoForm) {
		return getAsientoRegistralDTO(getServicioIntercambioRegistral().enviarAsientoRegistral(getAsientoRegistralFormVO(asientoForm)));
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#enviarAsientoRegistral(java.lang.String)
	 */
	public void enviarAsientoRegistral(String id) {
		getServicioIntercambioRegistral().enviarAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#validarAsientoRegistral(java.lang.String, java.lang.String, javax.xml.datatype.XMLGregorianCalendar)
	 */
	public void validarAsientoRegistral(String id, String numeroRegistro,
			XMLGregorianCalendar fechaRegistro) {
		getServicioIntercambioRegistral().validarAsientoRegistral(id,
				numeroRegistro, DateUtils.toDate(fechaRegistro));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#reenviarAsientoRegistral(java.lang.String)
	 */
	public void reenviarAsientoRegistral(String id) {
		getServicioIntercambioRegistral().reenviarAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#reenviarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO)
	 */
	public void reenviarAsientoRegistral(String id, InfoReenvioDTO infoReenvio) {
		getServicioIntercambioRegistral().reenviarAsientoRegistral(id, getInfoReenvioVO(infoReenvio));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#rechazarAsientoRegistral(java.lang.String,
	 *      es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO)
	 */
	public void rechazarAsientoRegistral(String id, InfoRechazoDTO infoRechazo) {
		getServicioIntercambioRegistral().rechazarAsientoRegistral(id,
				getInfoRechazoVO(infoRechazo));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#anularAsientoRegistral(java.lang.String)
	 */
	public void anularAsientoRegistral(String id) {
		getServicioIntercambioRegistral().anularAsientoRegistral(id);
	}

	/*
	 * =======================================================================
	 * Métodos para convertir objetos DTO <-> VO
	 * =======================================================================
	 */

	protected static CriteriosVO getCriteriosVO(CriteriosDTO criteriosDTO) {

		CriteriosVO criteriosVO = null;

		if (criteriosDTO != null) {
			criteriosVO = new CriteriosVO();
			criteriosVO.setCriterios(getListaCriterioVO(criteriosDTO.getCriterios()));
			criteriosVO.setOrderBy(getListaCriterioEnum(criteriosDTO.getOrderBy()));
			criteriosVO.setPageInfo(getPageInfo(criteriosDTO.getPageInfo()));
		}

		return criteriosVO;
	}

	protected static List<CriterioVO> getListaCriterioVO(List<CriterioDTO> listaCriterioDTO) {

		List<CriterioVO> listaCriterioVO = null;

		if (listaCriterioDTO != null) {
			listaCriterioVO = new ArrayList<CriterioVO>();
			for (CriterioDTO criterioDTO : listaCriterioDTO) {
				if (criterioDTO != null) {
					listaCriterioVO.add(getCriterioVO(criterioDTO));
				}
			}
		}

		return listaCriterioVO;
	}

	protected static CriterioVO getCriterioVO(CriterioDTO criterioDTO) {

		CriterioVO criterioVO = null;

		if (criterioDTO != null) {
			criterioVO = new CriterioVO();
			criterioVO.setNombre(CriterioEnum.getCriterio(criterioDTO.getNombre()));
			criterioVO.setOperador(OperadorCriterioEnum.getOperadorCriterio(criterioDTO.getOperador()));

			//criterioVO.setValor(criterioDTO.getValor());
			if (!CollectionUtils.isEmpty(criterioDTO.getValor())) {
				if (OperadorCriterioEnum.IN.equals(criterioVO.getOperador())) {
					criterioVO.setValor(criterioDTO.getValor().toArray());
				} else {
					criterioVO.setValor(criterioDTO.getValor().get(0));
				}
			}
		}

		return criterioVO;
	}

	protected static List<CriterioEnum> getListaCriterioEnum(List<String> listaOrderBy) {

		List<CriterioEnum> listaCriterioEnum = null;

		if (listaOrderBy != null) {
			listaCriterioEnum = new ArrayList<CriterioEnum>();
			for (String orderBy : listaOrderBy) {
				CriterioEnum criterioEnum = CriterioEnum.getCriterio(orderBy);
				if (criterioEnum != null) {
					listaCriterioEnum.add(criterioEnum);
				}
			}
		}

		return listaCriterioEnum;
	}

	protected static PageInfo getPageInfo(PageInfoDTO pageInfoDTO) {

		PageInfo pageInfo = null;

		if (pageInfoDTO != null) {
			pageInfo = new PageInfo();
			pageInfo.setPageNumber(pageInfoDTO.getPageNumber());
			pageInfo.setObjectsPerPage(pageInfoDTO.getObjectsPerPage());
			pageInfo.setMaxNumItems(pageInfoDTO.getMaxNumItems());
		}

		return pageInfo;
	}

	protected static List<AsientoRegistralDTO> getListaAsientoRegistralDTO(
			List<AsientoRegistralVO> listaAsientoRegistralVO) {

		List<AsientoRegistralDTO> listaAsientoRegistralDTO = null;

		if (listaAsientoRegistralVO != null) {
			listaAsientoRegistralDTO = new ArrayList<AsientoRegistralDTO>();
			for (AsientoRegistralVO asientoRegistralVO : listaAsientoRegistralVO) {
				if (asientoRegistralVO != null) {
					listaAsientoRegistralDTO
							.add(getAsientoRegistralDTO(asientoRegistralVO));
				}
			}
		}

		return listaAsientoRegistralDTO;
	}

	protected static AsientoRegistralDTO getAsientoRegistralDTO(
			AsientoRegistralVO asientoRegistralVO) {

		AsientoRegistralDTO asientoRegistralDTO = null;

		if (asientoRegistralVO != null) {
			asientoRegistralDTO = new AsientoRegistralDTO();
			asientoRegistralDTO.setId(asientoRegistralVO.getId());
			asientoRegistralDTO.setCodigoEntidadRegistral(asientoRegistralVO.getCodigoEntidadRegistral());
			asientoRegistralDTO.setCodigoEntidadRegistralOrigen(asientoRegistralVO.getCodigoEntidadRegistralOrigen());
			asientoRegistralDTO.setDescripcionEntidadRegistralOrigen(asientoRegistralVO.getDescripcionEntidadRegistralOrigen());
			asientoRegistralDTO.setNumeroRegistro(asientoRegistralVO.getNumeroRegistro());
			asientoRegistralDTO.setFechaRegistro(DateUtils.toXMLGregorianCalendar(asientoRegistralVO.getFechaRegistro()));
			asientoRegistralDTO.setTimestampRegistro(asientoRegistralVO.getTimestampRegistro());
			asientoRegistralDTO.setNumeroRegistroInicial(asientoRegistralVO.getNumeroRegistroInicial());
			asientoRegistralDTO.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(asientoRegistralVO.getFechaRegistroInicial()));
			asientoRegistralDTO.setTimestampRegistroInicial(asientoRegistralVO.getTimestampRegistroInicial());
			asientoRegistralDTO.setCodigoUnidadTramitacionOrigen(asientoRegistralVO.getCodigoUnidadTramitacionOrigen());
			asientoRegistralDTO.setDescripcionUnidadTramitacionOrigen(asientoRegistralVO.getDescripcionUnidadTramitacionOrigen());
			asientoRegistralDTO.setCodigoEntidadRegistralDestino(asientoRegistralVO.getCodigoEntidadRegistralDestino());
			asientoRegistralDTO.setDescripcionEntidadRegistralDestino(asientoRegistralVO.getDescripcionEntidadRegistralDestino());
			asientoRegistralDTO.setCodigoUnidadTramitacionDestino(asientoRegistralVO.getCodigoUnidadTramitacionDestino());
			asientoRegistralDTO.setDescripcionUnidadTramitacionDestino(asientoRegistralVO.getDescripcionUnidadTramitacionDestino());
			asientoRegistralDTO.setResumen(asientoRegistralVO.getResumen());
			asientoRegistralDTO.setCodigoAsunto(asientoRegistralVO.getCodigoAsunto());
			asientoRegistralDTO.setReferenciaExterna(asientoRegistralVO.getReferenciaExterna());
			asientoRegistralDTO.setNumeroExpediente(asientoRegistralVO.getNumeroExpediente());
			asientoRegistralDTO.setTipoTransporte(
					asientoRegistralVO.getTipoTransporte() != null ? asientoRegistralVO.getTipoTransporte().getValue() : null);
			asientoRegistralDTO.setNumeroTransporte(asientoRegistralVO.getNumeroTransporte());
			asientoRegistralDTO.setNombreUsuario(asientoRegistralVO.getNombreUsuario());
			asientoRegistralDTO.setContactoUsuario(asientoRegistralVO.getContactoUsuario());
			asientoRegistralDTO.setIdentificadorIntercambio(asientoRegistralVO.getIdentificadorIntercambio());
			asientoRegistralDTO.setEstado(
					asientoRegistralVO.getEstado() != null ? asientoRegistralVO.getEstado().getValue() : null);
			asientoRegistralDTO.setFechaEstado(DateUtils.toXMLGregorianCalendar(asientoRegistralVO.getFechaEstado()));
			asientoRegistralDTO.setFechaEnvio(DateUtils.toXMLGregorianCalendar(asientoRegistralVO.getFechaEnvio()));
			asientoRegistralDTO.setFechaRecepcion(DateUtils.toXMLGregorianCalendar(asientoRegistralVO.getFechaRecepcion()));
			asientoRegistralDTO.setNumeroReintentos(asientoRegistralVO.getNumeroReintentos());
			asientoRegistralDTO.setAplicacion(asientoRegistralVO.getAplicacion());
			asientoRegistralDTO.setTipoRegistro(
					asientoRegistralVO.getTipoRegistro() != null ? asientoRegistralVO.getTipoRegistro().getValue() : null);
			asientoRegistralDTO.setDocumentacionFisica(
					asientoRegistralVO.getDocumentacionFisica() != null ? asientoRegistralVO.getDocumentacionFisica().getValue() : null);
			asientoRegistralDTO.setObservacionesApunte(asientoRegistralVO.getObservacionesApunte());
			asientoRegistralDTO.setIndicadorPrueba(
					asientoRegistralVO.getIndicadorPrueba() != null ? asientoRegistralVO.getIndicadorPrueba().getValue() : null);
			asientoRegistralDTO.setCodigoEntidadRegistralInicio(asientoRegistralVO.getCodigoEntidadRegistralInicio());
			asientoRegistralDTO.setDescripcionEntidadRegistralInicio(asientoRegistralVO.getDescripcionEntidadRegistralInicio());
			asientoRegistralDTO.setExpone(asientoRegistralVO.getExpone());
			asientoRegistralDTO.setSolicita(asientoRegistralVO.getSolicita());
			asientoRegistralDTO.setCodigoError(asientoRegistralVO.getCodigoError());
			asientoRegistralDTO.setDescripcionError(asientoRegistralVO.getDescripcionError());
			asientoRegistralDTO.setDescripcionTipoAnotacion(asientoRegistralVO.getDescripcionTipoAnotacion());

			// Anexos
			asientoRegistralDTO.getAnexos().addAll(getListaAnexoDTO(asientoRegistralVO.getAnexos()));

			// Interesados
			asientoRegistralDTO.getInteresados().addAll(getListaInteresadoDTO(asientoRegistralVO.getInteresados()));
		}

		return asientoRegistralDTO;
	}

	protected static AsientoRegistralFormVO getAsientoRegistralFormVO(
			AsientoRegistralFormDTO asientoRegistralFormDTO) {

		AsientoRegistralFormVO asientoRegistralFormVO = null;

		if (asientoRegistralFormDTO != null) {
			asientoRegistralFormVO = new AsientoRegistralFormVO();
			asientoRegistralFormVO.setCodigoEntidadRegistral(asientoRegistralFormDTO.getCodigoEntidadRegistral());
			asientoRegistralFormVO.setCodigoEntidadRegistralOrigen(asientoRegistralFormDTO.getCodigoEntidadRegistralOrigen());
			asientoRegistralFormVO.setDescripcionEntidadRegistralOrigen(asientoRegistralFormDTO.getDescripcionEntidadRegistralOrigen());
			asientoRegistralFormVO.setCodigoEntidadRegistralInicio(asientoRegistralFormDTO.getCodigoEntidadRegistralInicio());
			asientoRegistralFormVO.setDescripcionEntidadRegistralInicio(asientoRegistralFormDTO.getDescripcionEntidadRegistralInicio());
			asientoRegistralFormVO.setNumeroRegistro(asientoRegistralFormDTO.getNumeroRegistro());
			asientoRegistralFormVO.setFechaRegistro(DateUtils.toDate(asientoRegistralFormDTO.getFechaRegistro()));
			asientoRegistralFormVO.setTimestampRegistro(asientoRegistralFormDTO.getTimestampRegistro());
			asientoRegistralFormVO.setNumeroRegistroInicial(asientoRegistralFormDTO.getNumeroRegistroInicial());
			asientoRegistralFormVO.setFechaRegistroInicial(DateUtils.toDate(asientoRegistralFormDTO.getFechaRegistroInicial()));
			asientoRegistralFormVO.setTimestampRegistroInicial(asientoRegistralFormDTO.getTimestampRegistroInicial());
			asientoRegistralFormVO.setCodigoUnidadTramitacionOrigen(asientoRegistralFormDTO.getCodigoUnidadTramitacionOrigen());
			asientoRegistralFormVO.setDescripcionUnidadTramitacionOrigen(asientoRegistralFormDTO.getDescripcionUnidadTramitacionOrigen());
			asientoRegistralFormVO.setCodigoEntidadRegistralDestino(asientoRegistralFormDTO.getCodigoEntidadRegistralDestino());
			asientoRegistralFormVO.setDescripcionEntidadRegistralDestino(asientoRegistralFormDTO.getDescripcionEntidadRegistralDestino());
			asientoRegistralFormVO.setCodigoUnidadTramitacionDestino(asientoRegistralFormDTO.getCodigoUnidadTramitacionDestino());
			asientoRegistralFormVO.setDescripcionUnidadTramitacionDestino(asientoRegistralFormDTO.getDescripcionUnidadTramitacionDestino());
			asientoRegistralFormVO.setResumen(asientoRegistralFormDTO.getResumen());
			asientoRegistralFormVO.setCodigoAsunto(asientoRegistralFormDTO.getCodigoAsunto());
			asientoRegistralFormVO.setReferenciaExterna(asientoRegistralFormDTO.getReferenciaExterna());
			asientoRegistralFormVO.setNumeroExpediente(asientoRegistralFormDTO.getNumeroExpediente());
			asientoRegistralFormVO.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(asientoRegistralFormDTO.getTipoTransporte()));
			asientoRegistralFormVO.setNumeroTransporte(asientoRegistralFormDTO.getNumeroTransporte());
			asientoRegistralFormVO.setNombreUsuario(asientoRegistralFormDTO.getNombreUsuario());
			asientoRegistralFormVO.setContactoUsuario(asientoRegistralFormDTO.getContactoUsuario());
			asientoRegistralFormVO.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(asientoRegistralFormDTO.getTipoRegistro()));
			asientoRegistralFormVO.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(asientoRegistralFormDTO.getDocumentacionFisica()));
			asientoRegistralFormVO.setObservacionesApunte(asientoRegistralFormDTO.getObservacionesApunte());
			asientoRegistralFormVO.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(asientoRegistralFormDTO.getIndicadorPrueba()));
			asientoRegistralFormVO.setExpone(asientoRegistralFormDTO.getExpone());
			asientoRegistralFormVO.setSolicita(asientoRegistralFormDTO.getSolicita());

			asientoRegistralFormVO.setDescripcionTipoAnotacion(asientoRegistralFormDTO.getDescripcionTipoAnotacion());

			// Anexos
			asientoRegistralFormVO.getAnexos().addAll(getListaAnexoFormVO(asientoRegistralFormDTO.getAnexos()));

			// Interesados
			asientoRegistralFormVO.getInteresados().addAll(getListaInteresadoFormVO(asientoRegistralFormDTO.getInteresados()));
		}

		return asientoRegistralFormVO;
	}

	protected static InfoBAsientoRegistralVO getInfoBAsientoRegistralVO(InfoBAsientoRegistralDTO infoBAsientoRegistralDTO) {

		InfoBAsientoRegistralVO infoBAsientoRegistralVO = null;

		if (infoBAsientoRegistralDTO != null) {
			infoBAsientoRegistralVO = new InfoBAsientoRegistralVO();
			infoBAsientoRegistralVO.setId(infoBAsientoRegistralDTO.getId());
			infoBAsientoRegistralVO.setCodigoEntidadRegistral(infoBAsientoRegistralDTO.getCodigoEntidadRegistral());
			infoBAsientoRegistralVO.setCodigoEntidadRegistralOrigen(infoBAsientoRegistralDTO.getCodigoEntidadRegistralOrigen());
			infoBAsientoRegistralVO.setDescripcionEntidadRegistralOrigen(infoBAsientoRegistralDTO.getDescripcionEntidadRegistralOrigen());
			infoBAsientoRegistralVO.setNumeroRegistro(infoBAsientoRegistralDTO.getNumeroRegistro());
			infoBAsientoRegistralVO.setFechaRegistro(DateUtils.toDate(infoBAsientoRegistralDTO.getFechaRegistro()));
			infoBAsientoRegistralVO.setTimestampRegistro(infoBAsientoRegistralDTO.getTimestampRegistro());
			infoBAsientoRegistralVO.setNumeroRegistroInicial(infoBAsientoRegistralDTO.getNumeroRegistroInicial());
			infoBAsientoRegistralVO.setFechaRegistroInicial(DateUtils.toDate(infoBAsientoRegistralDTO.getFechaRegistroInicial()));
			infoBAsientoRegistralVO.setTimestampRegistroInicial(infoBAsientoRegistralDTO.getTimestampRegistroInicial());
			infoBAsientoRegistralVO.setCodigoUnidadTramitacionOrigen(infoBAsientoRegistralDTO.getCodigoUnidadTramitacionOrigen());
			infoBAsientoRegistralVO.setDescripcionUnidadTramitacionOrigen(infoBAsientoRegistralDTO.getDescripcionUnidadTramitacionOrigen());
			infoBAsientoRegistralVO.setCodigoEntidadRegistralDestino(infoBAsientoRegistralDTO.getCodigoEntidadRegistralDestino());
			infoBAsientoRegistralVO.setDescripcionEntidadRegistralDestino(infoBAsientoRegistralDTO.getDescripcionEntidadRegistralDestino());
			infoBAsientoRegistralVO.setCodigoUnidadTramitacionDestino(infoBAsientoRegistralDTO.getCodigoUnidadTramitacionDestino());
			infoBAsientoRegistralVO.setDescripcionUnidadTramitacionDestino(infoBAsientoRegistralDTO.getDescripcionUnidadTramitacionDestino());
			infoBAsientoRegistralVO.setResumen(infoBAsientoRegistralDTO.getResumen());
			infoBAsientoRegistralVO.setCodigoAsunto(infoBAsientoRegistralDTO.getCodigoAsunto());
			infoBAsientoRegistralVO.setReferenciaExterna(infoBAsientoRegistralDTO.getReferenciaExterna());
			infoBAsientoRegistralVO.setNumeroExpediente(infoBAsientoRegistralDTO.getNumeroExpediente());
			infoBAsientoRegistralVO.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(infoBAsientoRegistralDTO.getTipoTransporte()));
			infoBAsientoRegistralVO.setNumeroTransporte(infoBAsientoRegistralDTO.getNumeroTransporte());
			infoBAsientoRegistralVO.setNombreUsuario(infoBAsientoRegistralDTO.getNombreUsuario());
			infoBAsientoRegistralVO.setContactoUsuario(infoBAsientoRegistralDTO.getContactoUsuario());
			infoBAsientoRegistralVO.setIdentificadorIntercambio(infoBAsientoRegistralDTO.getIdentificadorIntercambio());
			infoBAsientoRegistralVO.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(infoBAsientoRegistralDTO.getEstado()));
			infoBAsientoRegistralVO.setFechaEstado(DateUtils.toDate(infoBAsientoRegistralDTO.getFechaEstado()));
			infoBAsientoRegistralVO.setFechaEnvio(DateUtils.toDate(infoBAsientoRegistralDTO.getFechaEnvio()));
			infoBAsientoRegistralVO.setFechaRecepcion(DateUtils.toDate(infoBAsientoRegistralDTO.getFechaRecepcion()));
			infoBAsientoRegistralVO.setNumeroReintentos(infoBAsientoRegistralDTO.getNumeroReintentos());
			infoBAsientoRegistralVO.setAplicacion(infoBAsientoRegistralDTO.getAplicacion());
			infoBAsientoRegistralVO.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(infoBAsientoRegistralDTO.getTipoRegistro()));
			infoBAsientoRegistralVO.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(infoBAsientoRegistralDTO.getDocumentacionFisica()));
			infoBAsientoRegistralVO.setObservacionesApunte(infoBAsientoRegistralDTO.getObservacionesApunte());
			infoBAsientoRegistralVO.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(infoBAsientoRegistralDTO.getIndicadorPrueba()));
			infoBAsientoRegistralVO.setCodigoEntidadRegistralInicio(infoBAsientoRegistralDTO.getCodigoEntidadRegistralInicio());
			infoBAsientoRegistralVO.setDescripcionEntidadRegistralInicio(infoBAsientoRegistralDTO.getDescripcionEntidadRegistralInicio());
			infoBAsientoRegistralVO.setExpone(infoBAsientoRegistralDTO.getExpone());
			infoBAsientoRegistralVO.setSolicita(infoBAsientoRegistralDTO.getSolicita());
			infoBAsientoRegistralVO.setCodigoError(infoBAsientoRegistralDTO.getCodigoError());
			infoBAsientoRegistralVO.setDescripcionError(infoBAsientoRegistralDTO.getDescripcionError());
		}

		return infoBAsientoRegistralVO;
	}

	protected static List<AnexoFormVO> getListaAnexoFormVO(List<AnexoFormDTO> listaAnexoFormDTO) {

		List<AnexoFormVO> listaAnexoFormVO = null;

		if (listaAnexoFormDTO != null) {
			listaAnexoFormVO = new ArrayList<AnexoFormVO>();
			for (AnexoFormDTO anexoFormDTO : listaAnexoFormDTO) {
				if (anexoFormDTO != null) {
					listaAnexoFormVO.add(getAnexoFormVO(anexoFormDTO));
				}
			}
		}

		return listaAnexoFormVO;
	}

	protected static AnexoFormVO getAnexoFormVO(AnexoFormDTO anexoFormDTO) {

		AnexoFormVO anexoFormVO = null;

		if (anexoFormDTO != null) {
			anexoFormVO = new AnexoFormVO();
			anexoFormVO.setNombreFichero(anexoFormDTO.getNombreFichero());
			anexoFormVO.setValidezDocumento(ValidezDocumentoEnum.getValidezDocumento(anexoFormDTO.getValidezDocumento()));
			anexoFormVO.setTipoDocumento(TipoDocumentoEnum.getTipoDocumento(anexoFormDTO.getTipoDocumento()));
			anexoFormVO.setCertificado(anexoFormDTO.getCertificado());
			anexoFormVO.setFirma(anexoFormDTO.getFirma());
			anexoFormVO.setTimestamp(anexoFormDTO.getTimestamp());
			anexoFormVO.setValidacionOCSPCertificado(anexoFormDTO.getValidacionOCSPCertificado());
			anexoFormVO.setTipoMIME(anexoFormDTO.getTipoMIME());
			anexoFormVO.setIdentificadorFicheroFirmado(anexoFormDTO.getIdentificadorFicheroFirmado());
			anexoFormVO.setObservaciones(anexoFormDTO.getObservaciones());
			anexoFormVO.setContenido(anexoFormDTO.getContenido());
			anexoFormVO.setCodigoFichero(anexoFormDTO.getCodigoFichero());
			anexoFormVO.setCodigoFicheroFirmado(anexoFormDTO.getCodigoFicheroFirmado());
		}

		return anexoFormVO;
	}

	protected static AnexoVO getAnexoVO(AnexoDTO anexoDTO) {

		AnexoVO anexoVO = null;

		if (anexoDTO != null) {
			anexoVO = new AnexoVO();
			anexoVO.setId(anexoDTO.getId());
			anexoVO.setIdAsientoRegistral(anexoDTO.getIdAsientoRegistral());
			anexoVO.setIdentificadorFichero(anexoDTO.getIdentificadorFichero());
			anexoVO.setNombreFichero(anexoDTO.getNombreFichero());
			anexoVO.setValidezDocumento(ValidezDocumentoEnum.getValidezDocumento(anexoDTO.getValidezDocumento()));
			anexoVO.setTipoDocumento(TipoDocumentoEnum.getTipoDocumento(anexoDTO.getTipoDocumento()));
			anexoVO.setCertificado(anexoDTO.getCertificado());
			anexoVO.setFirma(anexoDTO.getFirma());
			anexoVO.setTimestamp(anexoDTO.getTimestamp());
			anexoVO.setValidacionOCSPCertificado(anexoDTO.getValidacionOCSPCertificado());
			anexoVO.setHash(anexoDTO.getHash());
			anexoVO.setTipoMIME(anexoDTO.getTipoMIME());
			anexoVO.setIdentificadorFicheroFirmado(anexoDTO.getIdentificadorFicheroFirmado());
			anexoVO.setIdentificadorDocumentoFirmado(anexoDTO.getIdentificadorDocumentoFirmado());
			anexoVO.setObservaciones(anexoDTO.getObservaciones());
		}

		return anexoVO;
	}

	protected static List<AnexoDTO> getListaAnexoDTO(List<AnexoVO> listaAnexoVO) {

		List<AnexoDTO> listaAnexoDTO = null;

		if (listaAnexoVO != null) {
			listaAnexoDTO = new ArrayList<AnexoDTO>();
			for (AnexoVO anexoVO : listaAnexoVO) {
				if (anexoVO != null) {
					listaAnexoDTO.add(getAnexoDTO(anexoVO));
				}
			}
		}

		return listaAnexoDTO;
	}

	protected static AnexoDTO getAnexoDTO(AnexoVO anexoVO) {

		AnexoDTO anexoDTO = null;

		if (anexoVO != null) {
			anexoDTO = new AnexoDTO();
			anexoDTO.setId(anexoVO.getId());
			anexoDTO.setIdAsientoRegistral(anexoVO.getIdAsientoRegistral());
			anexoDTO.setIdentificadorFichero(anexoVO.getIdentificadorFichero());
			anexoDTO.setNombreFichero(anexoVO.getNombreFichero());
			anexoDTO.setValidezDocumento(
					anexoVO.getValidezDocumento() != null ? anexoVO.getValidezDocumento().getValue() : null);
			anexoDTO.setTipoDocumento(
					anexoVO.getTipoDocumento() != null ? anexoVO.getTipoDocumento().getValue() : null);
			anexoDTO.setCertificado(anexoVO.getCertificado());
			anexoDTO.setFirma(anexoVO.getFirma());
			anexoDTO.setTimestamp(anexoVO.getTimestamp());
			anexoDTO.setValidacionOCSPCertificado(anexoVO.getValidacionOCSPCertificado());
			anexoDTO.setHash(anexoVO.getHash());
			anexoDTO.setTipoMIME(anexoVO.getTipoMIME());
			anexoDTO.setIdentificadorFicheroFirmado(anexoVO.getIdentificadorFicheroFirmado());
			anexoDTO.setIdentificadorDocumentoFirmado(anexoVO.getIdentificadorDocumentoFirmado());
			anexoDTO.setObservaciones(anexoVO.getObservaciones());
		}

		return anexoDTO;
	}

	protected static List<InteresadoFormVO> getListaInteresadoFormVO(List<InteresadoFormDTO> listaInteresadoFormDTO) {

		List<InteresadoFormVO> listaInteresadoFormVO = null;

		if (listaInteresadoFormDTO != null) {
			listaInteresadoFormVO = new ArrayList<InteresadoFormVO>();
			for (InteresadoFormDTO interesadoFormDTO : listaInteresadoFormDTO) {
				if (interesadoFormDTO != null) {
					listaInteresadoFormVO.add(getInteresadoFormVO(interesadoFormDTO));
				}
			}
		}

		return listaInteresadoFormVO;
	}

	protected static InteresadoFormVO getInteresadoFormVO(InteresadoFormDTO interesadoFormDTO) {

		InteresadoFormVO interesadoFormVO = null;

		if (interesadoFormDTO != null) {
			interesadoFormVO = new InteresadoFormVO();
			interesadoFormVO.setTipoDocumentoIdentificacionInteresado(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoFormDTO.getTipoDocumentoIdentificacionInteresado()));
			interesadoFormVO.setDocumentoIdentificacionInteresado(interesadoFormDTO.getDocumentoIdentificacionInteresado());
			interesadoFormVO.setRazonSocialInteresado(interesadoFormDTO.getRazonSocialInteresado());
			interesadoFormVO.setNombreInteresado(interesadoFormDTO.getNombreInteresado());
			interesadoFormVO.setPrimerApellidoInteresado(interesadoFormDTO.getPrimerApellidoInteresado());
			interesadoFormVO.setSegundoApellidoInteresado(interesadoFormDTO.getSegundoApellidoInteresado());
			interesadoFormVO.setCodigoPaisInteresado(interesadoFormDTO.getCodigoPaisInteresado());
			interesadoFormVO.setCodigoProvinciaInteresado(interesadoFormDTO.getCodigoProvinciaInteresado());
			interesadoFormVO.setCodigoMunicipioInteresado(interesadoFormDTO.getCodigoMunicipioInteresado());
			interesadoFormVO.setDireccionInteresado(interesadoFormDTO.getDireccionInteresado());
			interesadoFormVO.setCodigoPostalInteresado(interesadoFormDTO.getCodigoPostalInteresado());
			interesadoFormVO.setCorreoElectronicoInteresado(interesadoFormDTO.getCorreoElectronicoInteresado());
			interesadoFormVO.setTelefonoInteresado(interesadoFormDTO.getTelefonoInteresado());
			interesadoFormVO.setDireccionElectronicaHabilitadaInteresado(interesadoFormDTO.getDireccionElectronicaHabilitadaInteresado());
			interesadoFormVO.setCanalPreferenteComunicacionInteresado(
					CanalNotificacionEnum.getCanalNotificacion(interesadoFormDTO.getCanalPreferenteComunicacionInteresado()));
			interesadoFormVO.setTipoDocumentoIdentificacionRepresentante(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoFormDTO.getTipoDocumentoIdentificacionRepresentante()));
			interesadoFormVO.setDocumentoIdentificacionRepresentante(interesadoFormDTO.getDocumentoIdentificacionRepresentante());
			interesadoFormVO.setRazonSocialRepresentante(interesadoFormDTO.getRazonSocialRepresentante());
			interesadoFormVO.setNombreRepresentante(interesadoFormDTO.getNombreRepresentante());
			interesadoFormVO.setPrimerApellidoRepresentante(interesadoFormDTO.getPrimerApellidoRepresentante());
			interesadoFormVO.setSegundoApellidoRepresentante(interesadoFormDTO.getSegundoApellidoRepresentante());
			interesadoFormVO.setCodigoPaisRepresentante(interesadoFormDTO.getCodigoPaisRepresentante());
			interesadoFormVO.setCodigoProvinciaRepresentante(interesadoFormDTO.getCodigoProvinciaRepresentante());
			interesadoFormVO.setCodigoMunicipioRepresentante(interesadoFormDTO.getCodigoMunicipioRepresentante());
			interesadoFormVO.setDireccionRepresentante(interesadoFormDTO.getDireccionRepresentante());
			interesadoFormVO.setCodigoPostalRepresentante(interesadoFormDTO.getCodigoPostalRepresentante());
			interesadoFormVO.setCorreoElectronicoRepresentante(interesadoFormDTO.getCorreoElectronicoRepresentante());
			interesadoFormVO.setTelefonoRepresentante(interesadoFormDTO.getTelefonoRepresentante());
			interesadoFormVO.setDireccionElectronicaHabilitadaRepresentante(interesadoFormDTO.getDireccionElectronicaHabilitadaRepresentante());
			interesadoFormVO.setCanalPreferenteComunicacionRepresentante(
					CanalNotificacionEnum.getCanalNotificacion(interesadoFormDTO.getCanalPreferenteComunicacionRepresentante()));
			interesadoFormVO.setObservaciones(interesadoFormDTO.getObservaciones());
		}

		return interesadoFormVO;
	}

	protected static InteresadoVO getInteresadoVO(InteresadoDTO interesadoDTO) {

		InteresadoVO interesadoVO = null;

		if (interesadoDTO != null) {
			interesadoVO = new InteresadoVO();
			interesadoVO.setId(interesadoDTO.getId());
			interesadoVO.setIdAsientoRegistral(interesadoDTO.getIdAsientoRegistral());
			interesadoVO.setTipoDocumentoIdentificacionInteresado(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoDTO.getTipoDocumentoIdentificacionInteresado()));
			interesadoVO.setDocumentoIdentificacionInteresado(interesadoDTO.getDocumentoIdentificacionInteresado());
			interesadoVO.setRazonSocialInteresado(interesadoDTO.getRazonSocialInteresado());
			interesadoVO.setNombreInteresado(interesadoDTO.getNombreInteresado());
			interesadoVO.setPrimerApellidoInteresado(interesadoDTO.getPrimerApellidoInteresado());
			interesadoVO.setSegundoApellidoInteresado(interesadoDTO.getSegundoApellidoInteresado());
			interesadoVO.setCodigoPaisInteresado(interesadoDTO.getCodigoPaisInteresado());
			interesadoVO.setCodigoProvinciaInteresado(interesadoDTO.getCodigoProvinciaInteresado());
			interesadoVO.setCodigoMunicipioInteresado(interesadoDTO.getCodigoMunicipioInteresado());
			interesadoVO.setDireccionInteresado(interesadoDTO.getDireccionInteresado());
			interesadoVO.setCodigoPostalInteresado(interesadoDTO.getCodigoPostalInteresado());
			interesadoVO.setCorreoElectronicoInteresado(interesadoDTO.getCorreoElectronicoInteresado());
			interesadoVO.setTelefonoInteresado(interesadoDTO.getTelefonoInteresado());
			interesadoVO.setDireccionElectronicaHabilitadaInteresado(interesadoDTO.getDireccionElectronicaHabilitadaInteresado());
			interesadoVO.setCanalPreferenteComunicacionInteresado(
					CanalNotificacionEnum.getCanalNotificacion(interesadoDTO.getCanalPreferenteComunicacionInteresado()));
			interesadoVO.setTipoDocumentoIdentificacionRepresentante(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoDTO.getTipoDocumentoIdentificacionRepresentante()));
			interesadoVO.setDocumentoIdentificacionRepresentante(interesadoDTO.getDocumentoIdentificacionRepresentante());
			interesadoVO.setRazonSocialRepresentante(interesadoDTO.getRazonSocialRepresentante());
			interesadoVO.setNombreRepresentante(interesadoDTO.getNombreRepresentante());
			interesadoVO.setPrimerApellidoRepresentante(interesadoDTO.getPrimerApellidoRepresentante());
			interesadoVO.setSegundoApellidoRepresentante(interesadoDTO.getSegundoApellidoRepresentante());
			interesadoVO.setCodigoPaisRepresentante(interesadoDTO.getCodigoPaisRepresentante());
			interesadoVO.setCodigoProvinciaRepresentante(interesadoDTO.getCodigoProvinciaRepresentante());
			interesadoVO.setCodigoMunicipioRepresentante(interesadoDTO.getCodigoMunicipioRepresentante());
			interesadoVO.setDireccionRepresentante(interesadoDTO.getDireccionRepresentante());
			interesadoVO.setCodigoPostalRepresentante(interesadoDTO.getCodigoPostalRepresentante());
			interesadoVO.setCorreoElectronicoRepresentante(interesadoDTO.getCorreoElectronicoRepresentante());
			interesadoVO.setTelefonoRepresentante(interesadoDTO.getTelefonoRepresentante());
			interesadoVO.setDireccionElectronicaHabilitadaRepresentante(interesadoDTO.getDireccionElectronicaHabilitadaRepresentante());
			interesadoVO.setCanalPreferenteComunicacionRepresentante(
					CanalNotificacionEnum.getCanalNotificacion(interesadoDTO.getCanalPreferenteComunicacionRepresentante()));
			interesadoVO.setObservaciones(interesadoDTO.getObservaciones());
		}

		return interesadoVO;
	}

	protected static List<InteresadoDTO> getListaInteresadoDTO(List<InteresadoVO> listaInteresadoVO) {

		List<InteresadoDTO> listaInteresadoDTO = null;

		if (listaInteresadoVO != null) {
			listaInteresadoDTO = new ArrayList<InteresadoDTO>();
			for (InteresadoVO interesadoVO : listaInteresadoVO) {
				if (interesadoVO != null) {
					listaInteresadoDTO.add(getInteresadoDTO(interesadoVO));
				}
			}
		}

		return listaInteresadoDTO;
	}

	protected static InteresadoDTO getInteresadoDTO(InteresadoVO interesadoVO) {

		InteresadoDTO interesadoDTO = null;

		if (interesadoVO != null) {
			interesadoDTO = new InteresadoDTO();
			interesadoDTO.setId(interesadoVO.getId());
			interesadoDTO.setIdAsientoRegistral(interesadoVO.getIdAsientoRegistral());
			interesadoDTO.setTipoDocumentoIdentificacionInteresado(
					interesadoVO.getTipoDocumentoIdentificacionInteresado() != null ? interesadoVO.getTipoDocumentoIdentificacionInteresado().getValue() : null);
			interesadoDTO.setDocumentoIdentificacionInteresado(interesadoVO.getDocumentoIdentificacionInteresado());
			interesadoDTO.setRazonSocialInteresado(interesadoVO.getRazonSocialInteresado());
			interesadoDTO.setNombreInteresado(interesadoVO.getNombreInteresado());
			interesadoDTO.setPrimerApellidoInteresado(interesadoVO.getPrimerApellidoInteresado());
			interesadoDTO.setSegundoApellidoInteresado(interesadoVO.getSegundoApellidoInteresado());
			interesadoDTO.setCodigoPaisInteresado(interesadoVO.getCodigoPaisInteresado());
			interesadoDTO.setCodigoProvinciaInteresado(interesadoVO.getCodigoProvinciaInteresado());
			interesadoDTO.setCodigoMunicipioInteresado(interesadoVO.getCodigoMunicipioInteresado());
			interesadoDTO.setDireccionInteresado(interesadoVO.getDireccionInteresado());
			interesadoDTO.setCodigoPostalInteresado(interesadoVO.getCodigoPostalInteresado());
			interesadoDTO.setCorreoElectronicoInteresado(interesadoVO.getCorreoElectronicoInteresado());
			interesadoDTO.setTelefonoInteresado(interesadoVO.getTelefonoInteresado());
			interesadoDTO.setDireccionElectronicaHabilitadaInteresado(interesadoVO.getDireccionElectronicaHabilitadaInteresado());
			interesadoDTO.setCanalPreferenteComunicacionInteresado(
					interesadoVO.getCanalPreferenteComunicacionInteresado() != null ? interesadoVO.getCanalPreferenteComunicacionInteresado().getValue() : null);
			interesadoDTO.setTipoDocumentoIdentificacionRepresentante(
					interesadoVO.getTipoDocumentoIdentificacionRepresentante() != null ? interesadoVO.getTipoDocumentoIdentificacionRepresentante().getValue() : null);
			interesadoDTO.setDocumentoIdentificacionRepresentante(interesadoVO.getDocumentoIdentificacionRepresentante());
			interesadoDTO.setRazonSocialRepresentante(interesadoVO.getRazonSocialRepresentante());
			interesadoDTO.setNombreRepresentante(interesadoVO.getNombreRepresentante());
			interesadoDTO.setPrimerApellidoRepresentante(interesadoVO.getPrimerApellidoRepresentante());
			interesadoDTO.setSegundoApellidoRepresentante(interesadoVO.getSegundoApellidoRepresentante());
			interesadoDTO.setCodigoPaisRepresentante(interesadoVO.getCodigoPaisRepresentante());
			interesadoDTO.setCodigoProvinciaRepresentante(interesadoVO.getCodigoProvinciaRepresentante());
			interesadoDTO.setCodigoMunicipioRepresentante(interesadoVO.getCodigoMunicipioRepresentante());
			interesadoDTO.setDireccionRepresentante(interesadoVO.getDireccionRepresentante());
			interesadoDTO.setCodigoPostalRepresentante(interesadoVO.getCodigoPostalRepresentante());
			interesadoDTO.setCorreoElectronicoRepresentante(interesadoVO.getCorreoElectronicoRepresentante());
			interesadoDTO.setTelefonoRepresentante(interesadoVO.getTelefonoRepresentante());
			interesadoDTO.setDireccionElectronicaHabilitadaRepresentante(interesadoVO.getDireccionElectronicaHabilitadaRepresentante());
			interesadoDTO.setCanalPreferenteComunicacionRepresentante(
					interesadoVO.getCanalPreferenteComunicacionRepresentante() != null ? interesadoVO.getCanalPreferenteComunicacionRepresentante().getValue() : null);
			interesadoDTO.setObservaciones(interesadoVO.getObservaciones());
		}

		return interesadoDTO;
	}

	protected static List<TrazabilidadDTO> getListaTrazabilidadDTO(
			List<TrazabilidadVO> listaTrazabilidadVO) {

		List<TrazabilidadDTO> listaTrazabilidadDTO = null;

		if (listaTrazabilidadVO != null) {
			listaTrazabilidadDTO = new ArrayList<TrazabilidadDTO>();
			for (TrazabilidadVO trazabilidadVO : listaTrazabilidadVO) {
				if (trazabilidadVO != null) {
					listaTrazabilidadDTO
							.add(getTrazabilidadDTO(trazabilidadVO));
				}
			}
		}

		return listaTrazabilidadDTO;
	}

	protected static TrazabilidadDTO getTrazabilidadDTO(TrazabilidadVO trazabilidadVO) {

		TrazabilidadDTO trazabilidadDTO = null;

		if (trazabilidadVO != null) {
			trazabilidadDTO = new TrazabilidadDTO();
			trazabilidadDTO.setCodigo(trazabilidadVO.getCodigo());
			trazabilidadDTO.setDescripcion(trazabilidadVO.getDescripcion());
			trazabilidadDTO.setCodigoError(trazabilidadVO.getCodigoError());
			trazabilidadDTO.setDescripcionErrorAlternativa(trazabilidadVO.getDescripcionErrorAlternativa());
			trazabilidadDTO.setCodigoIntercambio(trazabilidadVO.getCodigoIntercambio());
			trazabilidadDTO.setCodigoErrorServicio(trazabilidadVO.getCodigoErrorServicio());
			trazabilidadDTO.setDescripcionErrorServicio(trazabilidadVO.getDescripcionErrorServicio());
			trazabilidadDTO.setCodigoEntidadRegistralOrigen(trazabilidadVO.getCodigoEntidadRegistralOrigen());
			trazabilidadDTO.setDescripcionEntidadRegistralOrigen(trazabilidadVO.getDescripcionEntidadRegistralOrigen());
			trazabilidadDTO.setCodigoEntidadRegistralDestino(trazabilidadVO.getCodigoEntidadRegistralDestino());
			trazabilidadDTO.setDescripcionEntidadRegistralDestino(trazabilidadVO.getDescripcionEntidadRegistralDestino());
			trazabilidadDTO.setCodigoUnidadTramitacionOrigen(trazabilidadVO.getCodigoUnidadTramitacionOrigen());
			trazabilidadDTO.setDescripcionUnidadTramitacionOrigen(trazabilidadVO.getDescripcionUnidadTramitacionOrigen());
			trazabilidadDTO.setCodigoUnidadTramitacionDestino(trazabilidadVO.getCodigoUnidadTramitacionDestino());
			trazabilidadDTO.setDescripcionUnidadTramitacionDestino(trazabilidadVO.getDescripcionUnidadTramitacionDestino());
			trazabilidadDTO.setCodigoEstado(trazabilidadVO.getCodigoEstado());
			trazabilidadDTO.setCodigoNodo(trazabilidadVO.getCodigoNodo());
			trazabilidadDTO.setNombreFicheroIntercambio(trazabilidadVO.getNombreFicheroIntercambio());
			trazabilidadDTO.setMotivoRechazo(trazabilidadVO.getMotivoRechazo());
			trazabilidadDTO.setFechaAlta(DateUtils.toXMLGregorianCalendar(trazabilidadVO.getFechaAlta()));
			trazabilidadDTO.setFechaModificacion(DateUtils.toXMLGregorianCalendar(trazabilidadVO.getFechaModificacion()));
			trazabilidadDTO.setRegistro(trazabilidadVO.isRegistro());
			trazabilidadDTO.setTamanyoDocs(trazabilidadVO.getTamanyoDocs());
		}

		return trazabilidadDTO;
	}
	
	protected static EstadoAsientoRegistralDTO getEstadoAsientoRegistralDTO(EstadoAsientoRegistraVO estadoAsientoRegistraVO ){
		EstadoAsientoRegistralDTO result  = null ;
		
		if (estadoAsientoRegistraVO != null){
			result = new EstadoAsientoRegistralDTO();
			result.setEstado(estadoAsientoRegistraVO.getEstado().getValue());
			result.setObservaciones(estadoAsientoRegistraVO.getObservaciones());
			
			//TODO mapear esto
			estadoAsientoRegistraVO.getFechaEstado();
			estadoAsientoRegistraVO.getContactoUsuario();
			estadoAsientoRegistraVO.getNombreUsuario();
			estadoAsientoRegistraVO.getDatosAdicionales();
			
		}
			
		return result ; 
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#recibirFicheroIntercambio(java.lang.String, java.util.List)
	 */
	public AsientoRegistralDTO recibirFicheroIntercambio(
			String xmlFicheroIntercambio, List<byte[]> documentos) {
		return getAsientoRegistralDTO(getServicioIntercambioRegistral().recibirFicheroIntercambio(xmlFicheroIntercambio, documentos));
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#recibirMensaje(java.lang.String)
	 */
	public void recibirMensaje(String xmlMensaje) {
		getServicioIntercambioRegistral().recibirMensaje(xmlMensaje);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#procesarFicherosRecibidos()
	 */
	public void procesarFicherosRecibidos() {
		getServicioIntercambioRegistral().procesarFicherosRecibidos();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#comprobarTimeOutEnvios()
	 */
	public void comprobarTimeOutEnvios() {
		getServicioIntercambioRegistral().comprobarTimeOutEnvios();
	}

//	/**
//	 * {@inheritDoc}
//	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.IntercambioRegistralManager#validarAnexos(java.lang.String)
//	 */
//	public List<ValidacionAnexoDTO> validarAnexos(String id) {
//		return getListaValidacionAnexoDTO(getServicioIntercambioRegistral().validarAnexos(id));
//	}
//
//	protected static List<ValidacionAnexoDTO> getListaValidacionAnexoDTO(
//			List<ValidacionAnexoVO> listaValidacionAnexoVO) {
//
//		List<ValidacionAnexoDTO> listaValidacionAnexoDTO = null;
//
//		if (listaValidacionAnexoVO != null) {
//			listaValidacionAnexoDTO = new ArrayList<ValidacionAnexoDTO>();
//			for (ValidacionAnexoVO validacionAnexoVO : listaValidacionAnexoVO) {
//				if (validacionAnexoVO != null) {
//					listaValidacionAnexoDTO
//							.add(getValidacionAnexoDTO(validacionAnexoVO));
//				}
//			}
//		}
//
//		return listaValidacionAnexoDTO;
//	}
//
//	protected static ValidacionAnexoDTO getValidacionAnexoDTO(
//			ValidacionAnexoVO validacionAnexoVO) {
//
//		ValidacionAnexoDTO validacionAnexoDTO = null;
//
//		if (validacionAnexoVO != null) {
//			validacionAnexoDTO = new ValidacionAnexoDTO();
//			validacionAnexoDTO.setAnexo(getAnexoDTO(validacionAnexoVO.getAnexo()));
//			validacionAnexoDTO.setHashValidado(validacionAnexoVO.isHashValidado());
//			validacionAnexoDTO.setValidacionOCSPCertificado(validacionAnexoVO.isValidacionOCSPCertificado());
//			validacionAnexoDTO.setDescripcionErrorValidacionCertificado(
//					validacionAnexoVO.getDescripcionErrorValidacionCertificado());
//
//			if (validacionAnexoVO.getValidacionCertificado() != null) {
//				validacionAnexoDTO.setValidacionCertificado(
//						validacionAnexoVO.getValidacionCertificado().getValue());
//			}
//
//			if (validacionAnexoVO.getValidacionFirma() != null) {
//				validacionAnexoDTO.setValidacionFirma(
//						validacionAnexoVO.getValidacionFirma().getValue());
//			}
//		}
//
//		return validacionAnexoDTO;
//	}

	protected static InfoRechazoVO getInfoRechazoVO(InfoRechazoDTO infoRechazoDTO) {

		InfoRechazoVO infoRechazoVO = null;

		if (infoRechazoDTO != null) {
			infoRechazoVO = new InfoRechazoVO();
			infoRechazoVO.setAplicacion(infoRechazoDTO.getAplicacion());
			infoRechazoVO.setContacto(infoRechazoDTO.getContacto());
			infoRechazoVO.setDescripcion(infoRechazoDTO.getDescripcion());
			infoRechazoVO.setTipoRechazo(TipoRechazoEnum.getTipoRechazo(infoRechazoDTO.getTipoRechazo()));
			infoRechazoVO.setUsuario(infoRechazoDTO.getUsuario());
		}
		
		return infoRechazoVO;
	}

	protected static InfoReenvioVO getInfoReenvioVO(InfoReenvioDTO infoReenvioDTO) {

		InfoReenvioVO infoReenvioVO = null;

		if (infoReenvioDTO != null) {
			infoReenvioVO = new InfoReenvioVO();
			infoReenvioVO.setCodigoEntidadRegistralDestino(infoReenvioDTO.getCodigoEntidadRegistralDestino());
			infoReenvioVO.setDescripcionEntidadRegistralDestino(infoReenvioDTO.getDescripcionEntidadRegistralDestino());
			infoReenvioVO.setCodigoUnidadTramitacionDestino(infoReenvioDTO.getCodigoUnidadTramitacionDestino());
			infoReenvioVO.setDescripcionUnidadTramitacionDestino(infoReenvioDTO.getDescripcionUnidadTramitacionDestino());
			infoReenvioVO.setAplicacion(infoReenvioDTO.getAplicacion());
			infoReenvioVO.setContacto(infoReenvioDTO.getContacto());
			infoReenvioVO.setDescripcion(infoReenvioDTO.getDescripcion());
			infoReenvioVO.setUsuario(infoReenvioDTO.getUsuario());
		}
		
		return infoReenvioVO;
	}

}
