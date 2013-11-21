package es.ieci.tecdoc.fwktd.sir.wsclient.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
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
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriterioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.HashMapString;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.PageInfoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.HashMapString.Item;
import es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * Implementación de mapeo de objetos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MapperImpl implements Mapper {

	/**
	 * Constructor.
	 */
	public MapperImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getCriteriosDTO(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public CriteriosDTO getCriteriosDTO(CriteriosVO criteriosVO) {

		CriteriosDTO criteriosDTO = null;

		if (criteriosVO != null) {
			criteriosDTO = new CriteriosDTO();
			criteriosDTO.getCriterios().addAll(getListaCriterioDTO(criteriosVO.getCriterios()));
			criteriosDTO.getOrderBy().addAll(getListaCriterios(criteriosVO.getOrderBy()));
			criteriosDTO.setPageInfo(getPageInfoDTO(criteriosVO.getPageInfo()));
		}

		return criteriosDTO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getTrazabilidadVOList(java.util.List)
	 */
	public List<TrazabilidadVO> getTrazabilidadVOList(
			List<TrazabilidadDTO> trazasDTO) {

		List<TrazabilidadVO> listaTrazabilidadVO = new ArrayList<TrazabilidadVO>();

		if (trazasDTO != null) {
			for (TrazabilidadDTO trazabilidadDTO : trazasDTO) {
				if (trazabilidadDTO != null) {
					listaTrazabilidadVO.add(getTrazabilidadVO(trazabilidadDTO));
				}
			}
		}

		return listaTrazabilidadVO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAsientoRegistralVOList(java.util.List)
	 */
	public List<AsientoRegistralVO> getAsientoRegistralVOList(
			List<AsientoRegistralDTO> asientosDTO) {

		List<AsientoRegistralVO> asientosVO = new ArrayList<AsientoRegistralVO>();

		if (asientosDTO != null) {
			for (AsientoRegistralDTO asientoDTO : asientosDTO) {
				if (asientoDTO != null) {
					asientosVO.add(getAsientoRegistralVO(asientoDTO));
				}
			}
		}

		return asientosVO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAsientoRegistralFormDTO(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
	 */
	public AsientoRegistralFormDTO getAsientoRegistralFormDTO(
			AsientoRegistralFormVO asientoFormVO) {

		AsientoRegistralFormDTO asientoFormDTO = null;

		if (asientoFormVO != null) {

			asientoFormDTO = new AsientoRegistralFormDTO();
			asientoFormDTO.setCodigoEntidadRegistral(asientoFormVO.getCodigoEntidadRegistral());
			asientoFormDTO.setCodigoEntidadRegistralOrigen(asientoFormVO.getCodigoEntidadRegistralOrigen());
			asientoFormDTO.setDescripcionEntidadRegistralOrigen(asientoFormVO.getDescripcionEntidadRegistralOrigen());
			asientoFormDTO.setCodigoEntidadRegistralInicio(asientoFormVO.getCodigoEntidadRegistralInicio());
			asientoFormDTO.setDescripcionEntidadRegistralInicio(asientoFormVO.getDescripcionEntidadRegistralInicio());
			asientoFormDTO.setNumeroRegistro(asientoFormVO.getNumeroRegistro());
			asientoFormDTO.setFechaRegistro(DateUtils.toXMLGregorianCalendar(asientoFormVO.getFechaRegistro()));
			asientoFormDTO.setTimestampRegistro(asientoFormVO.getTimestampRegistro());
			asientoFormDTO.setNumeroRegistroInicial(asientoFormVO.getNumeroRegistroInicial());
			asientoFormDTO.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(asientoFormVO.getFechaRegistroInicial()));
			asientoFormDTO.setTimestampRegistroInicial(asientoFormVO.getTimestampRegistroInicial());
			asientoFormDTO.setCodigoUnidadTramitacionOrigen(asientoFormVO.getCodigoUnidadTramitacionOrigen());
			asientoFormDTO.setDescripcionUnidadTramitacionOrigen(asientoFormVO.getDescripcionUnidadTramitacionOrigen());
			asientoFormDTO.setCodigoEntidadRegistralDestino(asientoFormVO.getCodigoEntidadRegistralDestino());
			asientoFormDTO.setDescripcionEntidadRegistralDestino(asientoFormVO.getDescripcionEntidadRegistralDestino());
			asientoFormDTO.setCodigoUnidadTramitacionDestino(asientoFormVO.getCodigoUnidadTramitacionDestino());
			asientoFormDTO.setDescripcionUnidadTramitacionDestino(asientoFormVO.getDescripcionUnidadTramitacionDestino());
			asientoFormDTO.setResumen(asientoFormVO.getResumen());
			asientoFormDTO.setCodigoAsunto(asientoFormVO.getCodigoAsunto());
			asientoFormDTO.setReferenciaExterna(asientoFormVO.getReferenciaExterna());
			asientoFormDTO.setNumeroExpediente(asientoFormVO.getNumeroExpediente());
			asientoFormDTO.setTipoTransporte(asientoFormVO.getTipoTransporte() != null ? asientoFormVO.getTipoTransporte().getValue() : null);
			asientoFormDTO.setNumeroTransporte(asientoFormVO.getNumeroTransporte());
			asientoFormDTO.setNombreUsuario(asientoFormVO.getNombreUsuario());
			asientoFormDTO.setContactoUsuario(asientoFormVO.getContactoUsuario());
			asientoFormDTO.setTipoRegistro(asientoFormVO.getTipoRegistro() != null ? asientoFormVO.getTipoRegistro().getValue() : null);
			asientoFormDTO.setDocumentacionFisica(asientoFormVO.getDocumentacionFisica() != null ? asientoFormVO.getDocumentacionFisica().getValue() : null);
			asientoFormDTO.setObservacionesApunte(asientoFormVO.getObservacionesApunte());
			asientoFormDTO.setIndicadorPrueba(asientoFormVO.getIndicadorPrueba() != null ? asientoFormVO.getIndicadorPrueba().getValue() : null);
			asientoFormDTO.setExpone(asientoFormVO.getExpone());
			asientoFormDTO.setSolicita(asientoFormVO.getSolicita());

			// Anexos
			asientoFormDTO.getAnexos().addAll(getListaAnexoFormDTO(asientoFormVO.getAnexos()));

			// Interesados
			asientoFormDTO.getInteresados().addAll(getListaInteresadoFormDTO(asientoFormVO.getInteresados()));
		}

		return asientoFormDTO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInfoBAsientoRegistralDTO(es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO)
	 */
	public InfoBAsientoRegistralDTO getInfoBAsientoRegistralDTO(
			InfoBAsientoRegistralVO infoBAsientoVO) {

		InfoBAsientoRegistralDTO infoBAsientoDTO = null;

		if (infoBAsientoVO != null) {
			infoBAsientoDTO = new InfoBAsientoRegistralDTO();
			infoBAsientoDTO.setId(infoBAsientoVO.getId());
			infoBAsientoDTO.setCodigoEntidadRegistral(infoBAsientoVO.getCodigoEntidadRegistral());
			infoBAsientoDTO.setCodigoEntidadRegistralOrigen(infoBAsientoVO.getCodigoEntidadRegistralOrigen());
			infoBAsientoDTO.setDescripcionEntidadRegistralOrigen(infoBAsientoVO.getDescripcionEntidadRegistralOrigen());
			infoBAsientoDTO.setNumeroRegistro(infoBAsientoVO.getNumeroRegistro());
			infoBAsientoDTO.setFechaRegistro(DateUtils.toXMLGregorianCalendar(infoBAsientoVO.getFechaRegistro()));
			infoBAsientoDTO.setTimestampRegistro(infoBAsientoVO.getTimestampRegistro());
			infoBAsientoDTO.setNumeroRegistroInicial(infoBAsientoVO.getNumeroRegistroInicial());
			infoBAsientoDTO.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(infoBAsientoVO.getFechaRegistroInicial()));
			infoBAsientoDTO.setTimestampRegistroInicial(infoBAsientoVO.getTimestampRegistroInicial());
			infoBAsientoDTO.setCodigoUnidadTramitacionOrigen(infoBAsientoVO.getCodigoUnidadTramitacionOrigen());
			infoBAsientoDTO.setDescripcionUnidadTramitacionOrigen(infoBAsientoVO.getDescripcionUnidadTramitacionOrigen());
			infoBAsientoDTO.setCodigoEntidadRegistralDestino(infoBAsientoVO.getCodigoEntidadRegistralDestino());
			infoBAsientoDTO.setDescripcionEntidadRegistralDestino(infoBAsientoVO.getDescripcionEntidadRegistralDestino());
			infoBAsientoDTO.setCodigoUnidadTramitacionDestino(infoBAsientoVO.getCodigoUnidadTramitacionDestino());
			infoBAsientoDTO.setDescripcionUnidadTramitacionDestino(infoBAsientoVO.getDescripcionUnidadTramitacionDestino());
			infoBAsientoDTO.setResumen(infoBAsientoVO.getResumen());
			infoBAsientoDTO.setCodigoAsunto(infoBAsientoVO.getCodigoAsunto());
			infoBAsientoDTO.setReferenciaExterna(infoBAsientoVO.getReferenciaExterna());
			infoBAsientoDTO.setNumeroExpediente(infoBAsientoVO.getNumeroExpediente());
			infoBAsientoDTO.setTipoTransporte(infoBAsientoVO.getTipoTransporte() != null ? infoBAsientoVO.getTipoTransporte().getValue() : null);
			infoBAsientoDTO.setNumeroTransporte(infoBAsientoVO.getNumeroTransporte());
			infoBAsientoDTO.setNombreUsuario(infoBAsientoVO.getNombreUsuario());
			infoBAsientoDTO.setContactoUsuario(infoBAsientoVO.getContactoUsuario());
			infoBAsientoDTO.setIdentificadorIntercambio(infoBAsientoVO.getIdentificadorIntercambio());
			infoBAsientoDTO.setEstado(infoBAsientoVO.getEstado() != null ? infoBAsientoVO.getEstado().getValue() : null);
			infoBAsientoDTO.setFechaEstado(DateUtils.toXMLGregorianCalendar(infoBAsientoVO.getFechaEstado()));
			infoBAsientoDTO.setFechaEnvio(DateUtils.toXMLGregorianCalendar(infoBAsientoVO.getFechaEnvio()));
			infoBAsientoDTO.setFechaRecepcion(DateUtils.toXMLGregorianCalendar(infoBAsientoVO.getFechaRecepcion()));
			infoBAsientoDTO.setNumeroReintentos(infoBAsientoVO.getNumeroReintentos());
			infoBAsientoDTO.setAplicacion(infoBAsientoVO.getAplicacion());
			infoBAsientoDTO.setTipoRegistro(infoBAsientoVO.getTipoRegistro() != null ? infoBAsientoVO.getTipoRegistro().getValue() : null);
			infoBAsientoDTO.setDocumentacionFisica(infoBAsientoVO.getDocumentacionFisica() != null ? infoBAsientoVO.getDocumentacionFisica().getValue() : null);
			infoBAsientoDTO.setObservacionesApunte(infoBAsientoVO.getObservacionesApunte());
			infoBAsientoDTO.setIndicadorPrueba(infoBAsientoVO.getIndicadorPrueba() != null ? infoBAsientoVO.getIndicadorPrueba().getValue() : null);
			infoBAsientoDTO.setCodigoEntidadRegistralInicio(infoBAsientoVO.getCodigoEntidadRegistralInicio());
			infoBAsientoDTO.setDescripcionEntidadRegistralInicio(infoBAsientoVO.getDescripcionEntidadRegistralInicio());
			infoBAsientoDTO.setExpone(infoBAsientoVO.getExpone());
			infoBAsientoDTO.setSolicita(infoBAsientoVO.getSolicita());
			infoBAsientoDTO.setCodigoError(infoBAsientoVO.getCodigoError());
			infoBAsientoDTO.setDescripcionError(infoBAsientoVO.getDescripcionError());
			infoBAsientoDTO.setDescripcionTipoAnotacion(infoBAsientoVO.getDescripcionTipoAnotacion());
		}

		return infoBAsientoDTO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAsientoRegistralVO(es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO)
	 */
	public AsientoRegistralVO getAsientoRegistralVO(
			AsientoRegistralDTO asientoDTO) {

		AsientoRegistralVO asientoVO = null;

		if (asientoDTO != null) {
			asientoVO = new AsientoRegistralVO();
			asientoVO.setId(asientoDTO.getId());
			asientoVO.setCodigoEntidadRegistral(asientoDTO.getCodigoEntidadRegistral());
			asientoVO.setCodigoEntidadRegistralOrigen(asientoDTO.getCodigoEntidadRegistralOrigen());
			asientoVO.setDescripcionEntidadRegistralOrigen(asientoDTO.getDescripcionEntidadRegistralOrigen());
			asientoVO.setNumeroRegistro(asientoDTO.getNumeroRegistro());
			asientoVO.setFechaRegistro(DateUtils.toDate(asientoDTO.getFechaRegistro()));
			asientoVO.setTimestampRegistro(asientoDTO.getTimestampRegistro());
			asientoVO.setNumeroRegistroInicial(asientoDTO.getNumeroRegistroInicial());
			asientoVO.setFechaRegistroInicial(DateUtils.toDate(asientoDTO.getFechaRegistroInicial()));
			asientoVO.setTimestampRegistroInicial(asientoDTO.getTimestampRegistroInicial());
			asientoVO.setCodigoUnidadTramitacionOrigen(asientoDTO.getCodigoUnidadTramitacionOrigen());
			asientoVO.setDescripcionUnidadTramitacionOrigen(asientoDTO.getDescripcionUnidadTramitacionOrigen());
			asientoVO.setCodigoEntidadRegistralDestino(asientoDTO.getCodigoEntidadRegistralDestino());
			asientoVO.setDescripcionEntidadRegistralDestino(asientoDTO.getDescripcionEntidadRegistralDestino());
			asientoVO.setCodigoUnidadTramitacionDestino(asientoDTO.getCodigoUnidadTramitacionDestino());
			asientoVO.setDescripcionUnidadTramitacionDestino(asientoDTO.getDescripcionUnidadTramitacionDestino());
			asientoVO.setResumen(asientoDTO.getResumen());
			asientoVO.setCodigoAsunto(asientoDTO.getCodigoAsunto());
			asientoVO.setReferenciaExterna(asientoDTO.getReferenciaExterna());
			asientoVO.setNumeroExpediente(asientoDTO.getNumeroExpediente());
			asientoVO.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(asientoDTO.getTipoTransporte()));
			asientoVO.setNumeroTransporte(asientoDTO.getNumeroTransporte());
			asientoVO.setNombreUsuario(asientoDTO.getNombreUsuario());
			asientoVO.setContactoUsuario(asientoDTO.getContactoUsuario());
			asientoVO.setIdentificadorIntercambio(asientoDTO.getIdentificadorIntercambio());
			asientoVO.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(asientoDTO.getEstado()));
			asientoVO.setFechaEstado(DateUtils.toDate(asientoDTO.getFechaEstado()));
			asientoVO.setFechaEnvio(DateUtils.toDate(asientoDTO.getFechaEnvio()));
			asientoVO.setFechaRecepcion(DateUtils.toDate(asientoDTO.getFechaRecepcion()));
			asientoVO.setNumeroReintentos(asientoDTO.getNumeroReintentos() != null ? asientoDTO.getNumeroReintentos() : 0);
			asientoVO.setAplicacion(asientoDTO.getAplicacion());
			asientoVO.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(asientoDTO.getTipoRegistro()));
			asientoVO.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(asientoDTO.getDocumentacionFisica()));
			asientoVO.setObservacionesApunte(asientoDTO.getObservacionesApunte());
			asientoVO.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(asientoDTO.getIndicadorPrueba()));
			asientoVO.setCodigoEntidadRegistralInicio(asientoDTO.getCodigoEntidadRegistralInicio());
			asientoVO.setDescripcionEntidadRegistralInicio(asientoDTO.getDescripcionEntidadRegistralInicio());
			asientoVO.setExpone(asientoDTO.getExpone());
			asientoVO.setSolicita(asientoDTO.getSolicita());
			asientoVO.setCodigoError(asientoDTO.getCodigoError());
			asientoVO.setDescripcionError(asientoDTO.getDescripcionError());
			asientoVO.setDescripcionTipoAnotacion(asientoDTO.getDescripcionTipoAnotacion());
			// Anexos
			asientoVO.getAnexos().addAll(getListaAnexoVO(asientoDTO.getAnexos()));

			// Interesados
			asientoVO.getInteresados().addAll(getListaInteresadoVO(asientoDTO.getInteresados()));
		}

		return asientoVO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAnexoFormDTO(es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO)
	 */
	public AnexoFormDTO getAnexoFormDTO(AnexoFormVO anexoFormVO) {

		AnexoFormDTO anexoFormDTO = null;

		if (anexoFormVO != null) {
			anexoFormDTO = new AnexoFormDTO();
			anexoFormDTO.setNombreFichero(anexoFormVO.getNombreFichero());
			anexoFormDTO.setValidezDocumento(anexoFormVO.getValidezDocumento() != null ? anexoFormVO.getValidezDocumento().getValue() : null);
			anexoFormDTO.setTipoDocumento(anexoFormVO.getTipoDocumento() != null ? anexoFormVO.getTipoDocumento().getValue() : null);
			anexoFormDTO.setCertificado(anexoFormVO.getCertificado());
			anexoFormDTO.setFirma(anexoFormVO.getFirma());
			anexoFormDTO.setTimestamp(anexoFormVO.getTimestamp());
			anexoFormDTO.setValidacionOCSPCertificado(anexoFormVO.getValidacionOCSPCertificado());
			anexoFormDTO.setTipoMIME(anexoFormVO.getTipoMIME());
			anexoFormDTO.setObservaciones(anexoFormVO.getObservaciones());
			anexoFormDTO.setContenido(anexoFormVO.getContenido());
			anexoFormDTO.setIdentificadorFicheroFirmado(anexoFormVO.getIdentificadorFicheroFirmado());
			anexoFormDTO.setCodigoFichero(anexoFormVO.getCodigoFichero());
			anexoFormDTO.setCodigoFicheroFirmado(anexoFormVO.getCodigoFicheroFirmado());
		}

		return anexoFormDTO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAnexoVO(es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO)
	 */
	public AnexoVO getAnexoVO(AnexoDTO anexoDTO) {

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

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getAnexoDTO(es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO)
	 */
	public AnexoDTO getAnexoDTO(AnexoVO anexoVO) {

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

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInteresadoFormDTO(es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO)
	 */
	public InteresadoFormDTO getInteresadoFormDTO(
			InteresadoFormVO interesadoFormVO) {

		InteresadoFormDTO interesadoFormDTO = null;

		if (interesadoFormVO != null) {
			interesadoFormDTO = new InteresadoFormDTO();
			interesadoFormDTO.setTipoDocumentoIdentificacionInteresado(
					interesadoFormVO.getTipoDocumentoIdentificacionInteresado() != null ? interesadoFormVO.getTipoDocumentoIdentificacionInteresado().getValue() : null);
			interesadoFormDTO.setDocumentoIdentificacionInteresado(interesadoFormVO.getDocumentoIdentificacionInteresado());
			interesadoFormDTO.setRazonSocialInteresado(interesadoFormVO.getRazonSocialInteresado());
			interesadoFormDTO.setNombreInteresado(interesadoFormVO.getNombreInteresado());
			interesadoFormDTO.setPrimerApellidoInteresado(interesadoFormVO.getPrimerApellidoInteresado());
			interesadoFormDTO.setSegundoApellidoInteresado(interesadoFormVO.getSegundoApellidoInteresado());
			interesadoFormDTO.setCodigoPaisInteresado(interesadoFormVO.getCodigoPaisInteresado());
			interesadoFormDTO.setCodigoProvinciaInteresado(interesadoFormVO.getCodigoProvinciaInteresado());
			interesadoFormDTO.setCodigoMunicipioInteresado(interesadoFormVO.getCodigoMunicipioInteresado());
			interesadoFormDTO.setDireccionInteresado(interesadoFormVO.getDireccionInteresado());
			interesadoFormDTO.setCodigoPostalInteresado(interesadoFormVO.getCodigoPostalInteresado());
			interesadoFormDTO.setCorreoElectronicoInteresado(interesadoFormVO.getCorreoElectronicoInteresado());
			interesadoFormDTO.setTelefonoInteresado(interesadoFormVO.getTelefonoInteresado());
			interesadoFormDTO.setDireccionElectronicaHabilitadaInteresado(interesadoFormVO.getDireccionElectronicaHabilitadaInteresado());
			interesadoFormDTO.setCanalPreferenteComunicacionInteresado(
					interesadoFormVO.getCanalPreferenteComunicacionInteresado() != null ? interesadoFormVO.getCanalPreferenteComunicacionInteresado().getValue() : null);
			interesadoFormDTO.setTipoDocumentoIdentificacionRepresentante(
					interesadoFormVO.getTipoDocumentoIdentificacionRepresentante() != null ? interesadoFormVO.getTipoDocumentoIdentificacionRepresentante().getValue() : null);
			interesadoFormDTO.setDocumentoIdentificacionRepresentante(interesadoFormVO.getDocumentoIdentificacionRepresentante());
			interesadoFormDTO.setRazonSocialRepresentante(interesadoFormVO.getRazonSocialRepresentante());
			interesadoFormDTO.setNombreRepresentante(interesadoFormVO.getNombreRepresentante());
			interesadoFormDTO.setPrimerApellidoRepresentante(interesadoFormVO.getPrimerApellidoRepresentante());
			interesadoFormDTO.setSegundoApellidoRepresentante(interesadoFormVO.getSegundoApellidoRepresentante());
			interesadoFormDTO.setCodigoPaisRepresentante(interesadoFormVO.getCodigoPaisRepresentante());
			interesadoFormDTO.setCodigoProvinciaRepresentante(interesadoFormVO.getCodigoProvinciaRepresentante());
			interesadoFormDTO.setCodigoMunicipioRepresentante(interesadoFormVO.getCodigoMunicipioRepresentante());
			interesadoFormDTO.setDireccionRepresentante(interesadoFormVO.getDireccionRepresentante());
			interesadoFormDTO.setCodigoPostalRepresentante(interesadoFormVO.getCodigoPostalRepresentante());
			interesadoFormDTO.setCorreoElectronicoRepresentante(interesadoFormVO.getCorreoElectronicoRepresentante());
			interesadoFormDTO.setTelefonoRepresentante(interesadoFormVO.getTelefonoRepresentante());
			interesadoFormDTO.setDireccionElectronicaHabilitadaRepresentante(interesadoFormVO.getDireccionElectronicaHabilitadaRepresentante());
			interesadoFormDTO.setCanalPreferenteComunicacionRepresentante(
					interesadoFormVO.getCanalPreferenteComunicacionRepresentante() != null ? interesadoFormVO.getCanalPreferenteComunicacionRepresentante().getValue() : null);
			interesadoFormDTO.setObservaciones(interesadoFormVO.getObservaciones());
		}

		return interesadoFormDTO;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInteresadoVO(es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO)
	 */
	public InteresadoVO getInteresadoVO(InteresadoDTO interesadoDTO) {

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

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInteresadoDTO(es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO)
	 */
	public InteresadoDTO getInteresadoDTO(InteresadoVO interesadoVO) {

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


	protected List<CriterioDTO> getListaCriterioDTO(List<CriterioVO> criteriosVO) {

		List<CriterioDTO> criteriosDTO = new ArrayList<CriterioDTO>();

		if (criteriosVO != null) {
			for (CriterioVO criterioVO : criteriosVO) {
				if (criterioVO != null) {
					criteriosDTO.add(getCriterioDTO(criterioVO));
				}
			}
		}

		return criteriosDTO;
	}

	protected CriterioDTO getCriterioDTO(CriterioVO criterioVO) {

		CriterioDTO criterioDTO = null;

		if (criterioVO != null) {
			criterioDTO = new CriterioDTO();
			criterioDTO.setNombre(criterioVO.getNombre().getValue());
			criterioDTO.setOperador(criterioVO.getOperador().getValue());
			
			//criterioDTO.setValor(criterioVO.getValor());
			if (OperadorCriterioEnum.IN.equals(criterioVO.getOperador())) {
				
				Object valor = criterioVO.getValor();
				if (valor instanceof ValuedEnum[]) {
					for (ValuedEnum enumerado : (ValuedEnum[])valor) {
						criterioDTO.getValor().add(enumerado.getValue());
					}
				} else if (valor instanceof StringValuedEnum[]) {
					for (StringValuedEnum enumerado : (StringValuedEnum[])valor) {
						criterioDTO.getValor().add(enumerado.getValue());
					}
				} else if (valor instanceof Object[]) {
					for (Object obj : (Object[])valor) {
						criterioDTO.getValor().add(obj);
					}
				} else {
					criterioDTO.getValor().add(criterioVO.getValor());
				}

			} else {
				Object valor = criterioVO.getValor();
				if (valor instanceof ValuedEnum) {
					criterioDTO.getValor().add(((ValuedEnum)valor).getValue());
				} else if (valor instanceof StringValuedEnum[]) {
					criterioDTO.getValor().add(((StringValuedEnum)valor).getValue());
				} else {
					criterioDTO.getValor().add(criterioVO.getValor());
				}
			}
		}

		return criterioDTO;
	}

	protected List<String> getListaCriterios(List<CriterioEnum> criterioEnumList) {

		List<String> listaCriterios = new ArrayList<String>();

		if (criterioEnumList != null) {
			for (CriterioEnum criterioEnum : criterioEnumList) {
				if (criterioEnum != null) {
					listaCriterios.add(criterioEnum.getValue());
				}
			}
		}

		return listaCriterios;
	}

	protected PageInfoDTO getPageInfoDTO(PageInfo pageInfo) {

		PageInfoDTO pageInfoDTO = null;

		if (pageInfo != null) {
			pageInfoDTO = new PageInfoDTO();
			pageInfoDTO.setMaxNumItems(pageInfo.getMaxNumItems());
			pageInfoDTO.setObjectsPerPage(pageInfo.getObjectsPerPage());
			pageInfoDTO.setPageNumber(pageInfo.getPageNumber());
		}

		return pageInfoDTO;
	}

	protected TrazabilidadVO getTrazabilidadVO(TrazabilidadDTO trazabilidadDTO) {

		TrazabilidadVO trazabilidadVO = null;

		if (trazabilidadDTO != null) {
			trazabilidadVO = new TrazabilidadVO();
			trazabilidadVO.setCodigoError(trazabilidadDTO.getCodigoError());
			trazabilidadVO.setDescripcionErrorAlternativa(trazabilidadDTO.getDescripcionErrorAlternativa());
			trazabilidadVO.setCodigoIntercambio(trazabilidadDTO.getCodigoIntercambio());
			trazabilidadVO.setCodigoEntidadRegistralOrigen(trazabilidadDTO.getCodigoEntidadRegistralOrigen());
			trazabilidadVO.setDescripcionEntidadRegistralOrigen(trazabilidadDTO.getDescripcionEntidadRegistralOrigen());
			trazabilidadVO.setCodigoEntidadRegistralDestino(trazabilidadDTO.getCodigoEntidadRegistralDestino());
			trazabilidadVO.setDescripcionEntidadRegistralDestino(trazabilidadDTO.getDescripcionEntidadRegistralDestino());
			trazabilidadVO.setCodigoUnidadTramitacionOrigen(trazabilidadDTO.getCodigoUnidadTramitacionOrigen());
			trazabilidadVO.setDescripcionUnidadTramitacionOrigen(trazabilidadDTO.getDescripcionUnidadTramitacionOrigen());
			trazabilidadVO.setCodigoUnidadTramitacionDestino(trazabilidadDTO.getCodigoUnidadTramitacionDestino());
			trazabilidadVO.setDescripcionUnidadTramitacionDestino(trazabilidadDTO.getDescripcionUnidadTramitacionDestino());
			trazabilidadVO.setCodigoEstado(trazabilidadDTO.getCodigoEstado());
			trazabilidadVO.setCodigoNodo(trazabilidadDTO.getCodigoNodo());
			trazabilidadVO.setNombreFicheroIntercambio(trazabilidadDTO.getNombreFicheroIntercambio());
			trazabilidadVO.setMotivoRechazo(trazabilidadDTO.getMotivoRechazo());
			trazabilidadVO.setFechaAlta(DateUtils.toDate(trazabilidadDTO.getFechaAlta()));
			trazabilidadVO.setFechaModificacion(DateUtils.toDate(trazabilidadDTO.getFechaModificacion()));
			trazabilidadVO.setRegistro(trazabilidadDTO.isRegistro());
			trazabilidadVO.setTamanyoDocs(trazabilidadDTO.getTamanyoDocs());
		}

		return trazabilidadVO;
	}

	protected List<AnexoFormDTO> getListaAnexoFormDTO(List<AnexoFormVO> listaAnexoFormVO) {

		List<AnexoFormDTO> listaAnexoFormDTO = new ArrayList<AnexoFormDTO>();

		if (listaAnexoFormVO != null) {
			for (AnexoFormVO anexoFormVO : listaAnexoFormVO) {
				if (anexoFormVO != null) {
					listaAnexoFormDTO.add(getAnexoFormDTO(anexoFormVO));
				}
			}
		}

		return listaAnexoFormDTO;
	}

	protected List<AnexoVO> getListaAnexoVO(List<AnexoDTO> listaAnexoDTO) {

		List<AnexoVO> listaAnexoVO = new ArrayList<AnexoVO>();

		if (listaAnexoDTO != null) {
			for (AnexoDTO anexoDTO : listaAnexoDTO) {
				if (anexoDTO != null) {
					listaAnexoVO.add(getAnexoVO(anexoDTO));
				}
			}
		}

		return listaAnexoVO;
	}

	protected List<InteresadoFormDTO> getListaInteresadoFormDTO(List<InteresadoFormVO> listaInteresadoFormVO) {

		List<InteresadoFormDTO> listaInteresadoFormDTO = new ArrayList<InteresadoFormDTO>();

		if (listaInteresadoFormVO != null) {
			for (InteresadoFormVO interesadoFormVO : listaInteresadoFormVO) {
				if (interesadoFormVO != null) {
					listaInteresadoFormDTO.add(getInteresadoFormDTO(interesadoFormVO));
				}
			}
		}

		return listaInteresadoFormDTO;
	}

	protected List<InteresadoVO> getListaInteresadoVO(List<InteresadoDTO> listaInteresadoDTO) {

		List<InteresadoVO> listaInteresadoVO = new ArrayList<InteresadoVO>();

		if (listaInteresadoDTO != null) {
			for (InteresadoDTO interesadoDTO : listaInteresadoDTO) {
				if (interesadoDTO != null) {
					listaInteresadoVO.add(getInteresadoVO(interesadoDTO));
				}
			}
		}

		return listaInteresadoVO;
	}

//	/**
//	 * {@inheritDoc}
//	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getListaValidacionAnexoVO(java.util.List)
//	 */
//	public List<ValidacionAnexoVO> getListaValidacionAnexoVO(
//			List<ValidacionAnexoDTO> validacionesAnexosDTO) {
//
//		List<ValidacionAnexoVO> validacionesAnexoVO = new ArrayList<ValidacionAnexoVO>();
//
//		if (validacionesAnexosDTO != null) {
//			for (ValidacionAnexoDTO validacionAnexoDTO : validacionesAnexosDTO) {
//				if (validacionAnexoDTO != null) {
//					validacionesAnexoVO.add(getValidacionAnexoVO(validacionAnexoDTO));
//				}
//			}
//		}
//
//		return validacionesAnexoVO;
//	}
//
//	protected ValidacionAnexoVO getValidacionAnexoVO(ValidacionAnexoDTO validacionAnexoDTO) {
//
//		ValidacionAnexoVO validacionAnexoVO = null;
//
//		if (validacionAnexoDTO != null) {
//			validacionAnexoVO = new ValidacionAnexoVO();
//
//			validacionAnexoVO.setAnexo(getAnexoVO(validacionAnexoDTO.getAnexo()));
//
//			if (validacionAnexoDTO.isHashValidado() != null) {
//				validacionAnexoVO.setHashValidado(validacionAnexoDTO.isHashValidado());
//			}
//
//			if (validacionAnexoDTO.getValidacionCertificado() != null) {
//				validacionAnexoVO.setValidacionCertificado(
//						ValidacionCertificadoEnum.getValidacionCertificado(
//								validacionAnexoDTO.getValidacionCertificado()));
//			}
//
//			validacionAnexoVO.setDescripcionErrorValidacionCertificado(
//					validacionAnexoDTO.getDescripcionErrorValidacionCertificado());
//
//			if (validacionAnexoDTO.isValidacionOCSPCertificado() != null) {
//				validacionAnexoVO.setValidacionOCSPCertificado(
//						validacionAnexoDTO.isValidacionOCSPCertificado());
//			}
//
//			if (validacionAnexoDTO.getValidacionFirma() != null) {
//				validacionAnexoVO.setValidacionFirma(
//						ValidacionFirmaEnum.getValidacionFirma(
//								validacionAnexoDTO.getValidacionFirma()));
//			}
//		}
//
//		return validacionAnexoVO;
//	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInfoRechazoDTO(es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO)
	 */
	public InfoRechazoDTO getInfoRechazoDTO(InfoRechazoVO infoRechazoVO) {

		InfoRechazoDTO infoRechazoDTO = null;

		if (infoRechazoVO != null) {
			infoRechazoDTO = new InfoRechazoDTO();
		    infoRechazoDTO.setAplicacion(infoRechazoVO.getAplicacion());
		    infoRechazoDTO.setContacto(infoRechazoVO.getContacto());
		    infoRechazoDTO.setDescripcion(infoRechazoVO.getDescripcion());
		    infoRechazoDTO.setTipoRechazo(infoRechazoVO.getTipoRechazo().getValue());
		    infoRechazoDTO.setUsuario(infoRechazoVO.getUsuario());
		}
		
		return infoRechazoDTO;
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.wsclient.mapper.Mapper#getInfoReenvioDTO(es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO)
	 */
	public InfoReenvioDTO getInfoReenvioDTO(InfoReenvioVO infoReenvioVO) {

		InfoReenvioDTO infoReenvioDTO = null;

		if (infoReenvioVO != null) {
			infoReenvioDTO = new InfoReenvioDTO();
		    infoReenvioDTO.setAplicacion(infoReenvioVO.getAplicacion());
		    infoReenvioDTO.setCodigoEntidadRegistralDestino(infoReenvioVO.getCodigoEntidadRegistralDestino());
		    infoReenvioDTO.setContacto(infoReenvioVO.getContacto());
		    infoReenvioDTO.setDescripcion(infoReenvioVO.getDescripcion());
		    infoReenvioDTO.setDescripcionEntidadRegistralDestino(infoReenvioVO.getDescripcionEntidadRegistralDestino());

		    infoReenvioDTO.setCodigoUnidadTramitacionDestino(infoReenvioVO.getCodigoUnidadTramitacionDestino());
		    infoReenvioDTO.setDescripcionUnidadTramitacionDestino(infoReenvioVO.getDescripcionUnidadTramitacionDestino());
		    infoReenvioDTO.setUsuario(infoReenvioVO.getUsuario());
		}
		
		return infoReenvioDTO;
	}
	
	public EstadoAsientoRegistraVO getEstadoAsientoRegistraVO(EstadoAsientoRegistralDTO estadoDTO){
		EstadoAsientoRegistraVO result = null;
		
		if (estadoDTO != null){
			result = new EstadoAsientoRegistraVO();
			EstadoAsientoRegistralEnum estado = EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(estadoDTO.getEstado());
			result.setEstado(estado);
			result.setObservaciones(estadoDTO.getObservaciones());
			
			result.setFechaEstado(DateUtils.toDate(estadoDTO.getFechaEstado()));
			result.setContactoUsuario(estadoDTO.getContactoUsuario());
			result.setNombreUsuario(estadoDTO.getNombreUsuario());
			
			Map<String, String> datosAdicionales = null;
			HashMapString infoAdicional = estadoDTO.getInformacionAdicional();
			
			if (infoAdicional != null) {
				datosAdicionales = new HashMap<String, String>();
				List<Item> items = infoAdicional.getItem();
				for (Iterator iterator = items.iterator(); iterator.hasNext();) {
					Item item = (Item) iterator.next();
					datosAdicionales.put(item.getKey(), item.getValue());
				}
					
			}
			
			result.setDatosAdicionales(datosAdicionales);
			
		}
		
		return result;
	}
}
