package es.ieci.tecdoc.fwktd.sir.wsclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.enums.ValuedEnum;
import org.junit.Assert;

import org.apache.commons.codec.binary.Base64;

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
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.PageInfoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

public class TestUtils {

	public static CriteriosVO createCriteriosVO() {

		return new CriteriosVO()
			.addCriterioVO(
					new CriterioVO(CriterioEnum.ASIENTO_CODIGO_ASUNTO,
							OperadorCriterioEnum.EQUAL, "000"))
			.addCriterioVO(
					new CriterioVO(
							CriterioEnum.ASIENTO_ESTADO,
							OperadorCriterioEnum.IN,
							new EstadoAsientoRegistralEnum[] {
									EstadoAsientoRegistralEnum.PENDIENTE_ENVIO,
									EstadoAsientoRegistralEnum.ENVIADO }))
			.addOrderBy(
					CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_ORIGEN)
			.addOrderBy(
					CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_DESTINO)
			.setPageInfo(new PageInfo(1, 50));
	}
	
	public static EstadoAsientoRegistralDTO createEstadoAsientoRegistralDTO(){
		EstadoAsientoRegistralDTO result = new EstadoAsientoRegistralDTO();
		result.setEstado(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue());
		return result;
	}

	public static TrazabilidadDTO createTrazabilidadDTO(String id) {

		TrazabilidadDTO trazaDTO = new TrazabilidadDTO();

	    trazaDTO.setCodigoError("CODIGO_ERROR" + id);
	    trazaDTO.setCodigoEstado("CODIGO_ESTADO" + id);
	    trazaDTO.setCodigoIntercambio("CODIGO_INTERCAMBIO" + id);
	    trazaDTO.setCodigoNodo("CODIGO_NODO" + id);
	    trazaDTO.setCodigoEntidadRegistralDestino("CODIGO_OFICINA_REGISTRO_DESTINO" + id);
	    trazaDTO.setCodigoEntidadRegistralOrigen("CODIGO_OFICINA_REGISTRO_ORIGEN" + id);
	    trazaDTO.setCodigoUnidadTramitacionDestino("CODIGO_UnidadTramitacion_DESTINO" + id);
	    trazaDTO.setCodigoUnidadTramitacionOrigen("CODIGO_UnidadTramitacion_ORIGEN" + id);
	    trazaDTO.setDescripcionErrorAlternativa("DESCRIPCION_ERROR_ALTERNATIVA" + id);
	    trazaDTO.setDescripcionEntidadRegistralDestino("DESCRIPCION_OFICINA_REGISTRO_DESTINO" + id);
	    trazaDTO.setDescripcionEntidadRegistralOrigen("DESCRIPCION_OFICINA_REGISTRO_ORIGEN" + id);
	    trazaDTO.setDescripcionUnidadTramitacionDestino("DESCRIPCION_UnidadTramitacion_DESTINO" + id);
	    trazaDTO.setDescripcionUnidadTramitacionOrigen("DESCRIPCION_UnidadTramitacion_ORIGEN" + id);
	    trazaDTO.setFechaAlta(DateUtils.toXMLGregorianCalendar(new Date()));
	    trazaDTO.setFechaModificacion(DateUtils.toXMLGregorianCalendar(new Date()));
	    trazaDTO.setMotivoRechazo("MOTIVO_RECHAZO" + id);
	    trazaDTO.setNombreFicheroIntercambio("NOMBRE_FICHERO_INTERCAMBIO" + id);
	    trazaDTO.setRegistro(true);
	    trazaDTO.setTamanyoDocs(1024);

	    return trazaDTO;
	}

	public static InfoBAsientoRegistralVO createInfoBAsientoRegistralVO(String id) {

		InfoBAsientoRegistralVO asiento = new InfoBAsientoRegistralVO();

	    asiento.setAplicacion("APLICACION_" + id);
	    asiento.setCodigoAsunto("CODIGO_ASUNTO_" + id);
	    asiento.setCodigoEntidadRegistral("CODIGO_ENTIDAD_REGISTRAL_" + id);
	    asiento.setCodigoEntidadRegistralDestino("CODIGO_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setCodigoEntidadRegistralInicio("CODIGO_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setCodigoEntidadRegistralOrigen("CODIGO_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setCodigoError("CODIGO_ERROR_" + id);
	    asiento.setCodigoUnidadTramitacionDestino("CODIGO_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setCodigoUnidadTramitacionOrigen("CODIGO_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setContactoUsuario("CONTACTO_USUARIO_" + id);
	    asiento.setDescripcionEntidadRegistralDestino("DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setDescripcionEntidadRegistralInicio("DESCRIPCION_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setDescripcionEntidadRegistralOrigen("DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setDescripcionError("DESCRIPCION_ERROR_" + id);
	    asiento.setDescripcionUnidadTramitacionDestino("DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setDescripcionUnidadTramitacionOrigen("DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
	    asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO_Y_ACK);
	    asiento.setExpone("EXPONE " + id);
	    asiento.setFechaEnvio(new Date());
	    asiento.setFechaEstado(new Date());
	    asiento.setFechaRecepcion(new Date());
	    asiento.setFechaRegistro(new Date());
	    asiento.setFechaRegistroInicial(new Date());
	    asiento.setId(id);
	    asiento.setIdentificadorIntercambio("IDENTIFICADOR_INTERCAMBIO_" + id);
	    asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
	    asiento.setNombreUsuario("NOMBRE_USUARIO_" + id);
	    asiento.setNumeroExpediente("NUMERO_EXPEDIENTE_" + id);
	    asiento.setNumeroRegistro("NUMERO_REGISTRO_" + id);
	    asiento.setNumeroRegistroInicial("NUMERO_REGISTRO_INICIAL_" + id);
	    asiento.setNumeroReintentos(2);
	    asiento.setNumeroTransporte("NUMERO_TRANSPORTE_" + id);
	    asiento.setObservacionesApunte("OBSERVACIONES_" + id);
	    asiento.setReferenciaExterna("REFERENCIA_EXTERNA_" + id);
	    asiento.setResumen("RESUMEN " + id);
	    asiento.setSolicita("SOLICITA " + id);
	    asiento.setTimestampRegistro("***timestamp***".getBytes());
	    asiento.setTimestampRegistroInicial("***timestamp***".getBytes());
	    asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA);
	    asiento.setTipoTransporte(TipoTransporteEnum.BUROFAX);

		return asiento;
	}

	public static AsientoRegistralDTO createAsientoRegistralDTO(String id) {

		AsientoRegistralDTO asiento = new AsientoRegistralDTO();

	    asiento.setAplicacion("APLICACION_" + id);
	    asiento.setCodigoAsunto("CODIGO_ASUNTO_" + id);
	    asiento.setCodigoEntidadRegistral("CODIGO_ENTIDAD_REGISTRAL_" + id);
	    asiento.setCodigoEntidadRegistralDestino("CODIGO_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setCodigoEntidadRegistralInicio("CODIGO_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setCodigoEntidadRegistralOrigen("CODIGO_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setCodigoError("CODIGO_ERROR_" + id);
	    asiento.setCodigoUnidadTramitacionDestino("CODIGO_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setCodigoUnidadTramitacionOrigen("CODIGO_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setContactoUsuario("CONTACTO_USUARIO_" + id);
	    asiento.setDescripcionEntidadRegistralDestino("DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setDescripcionEntidadRegistralInicio("DESCRIPCION_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setDescripcionEntidadRegistralOrigen("DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setDescripcionError("DESCRIPCION_ERROR_" + id);
	    asiento.setDescripcionUnidadTramitacionDestino("DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setDescripcionUnidadTramitacionOrigen("DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA.getValue());
	    asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO_Y_ACK.getValue());
	    asiento.setExpone("EXPONE " + id);
	    asiento.setFechaEnvio(DateUtils.toXMLGregorianCalendar(new Date()));
	    asiento.setFechaEstado(DateUtils.toXMLGregorianCalendar(new Date()));
	    asiento.setFechaRecepcion(DateUtils.toXMLGregorianCalendar(new Date()));
	    asiento.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
	    asiento.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(new Date()));
	    asiento.setId(id);
	    asiento.setIdentificadorIntercambio("IDENTIFICADOR_INTERCAMBIO_" + id);
	    asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());
	    asiento.setNombreUsuario("NOMBRE_USUARIO_" + id);
	    asiento.setNumeroExpediente("NUMERO_EXPEDIENTE_" + id);
	    asiento.setNumeroRegistro("NUMERO_REGISTRO_" + id);
	    asiento.setNumeroRegistroInicial("NUMERO_REGISTRO_INICIAL_" + id);
	    asiento.setNumeroReintentos(2);
	    asiento.setNumeroTransporte("NUMERO_TRANSPORTE_" + id);
	    asiento.setObservacionesApunte("OBSERVACIONES_" + id);
	    asiento.setReferenciaExterna("REFERENCIA_EXTERNA_" + id);
	    asiento.setResumen("RESUMEN " + id);
	    asiento.setSolicita("SOLICITA " + id);
	    asiento.setTimestampRegistro("***timestamp***".getBytes());
	    asiento.setTimestampRegistroInicial("***timestamp***".getBytes());
	    asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
	    asiento.setTipoTransporte(TipoTransporteEnum.BUROFAX.getValue());

	    asiento.getAnexos().add(createAnexoDTO(id + "_1", id));
	    asiento.getAnexos().add(createAnexoDTO(id + "_2", id));

	    asiento.getInteresados().add(createInteresadoDTO(id + "_1", id));
	    asiento.getInteresados().add(createInteresadoDTO(id + "_2", id));

		return asiento;
	}

	public static AsientoRegistralDTO createAsientoRegistralDTO(String id, AsientoRegistralFormDTO asientoFormDTO) {

		AsientoRegistralDTO asiento = new AsientoRegistralDTO();

	    asiento.setId(id);
	    asiento.setIdentificadorIntercambio("IDENTIFICADOR_INTERCAMBIO_" + id);
	    asiento.setAplicacion("APLICACION_" + id);
	    asiento.setEstado(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue());
	    asiento.setFechaEstado(DateUtils.toXMLGregorianCalendar(new Date()));

	    asiento.setCodigoAsunto(asientoFormDTO.getCodigoAsunto());
	    asiento.setCodigoEntidadRegistral(asientoFormDTO.getCodigoEntidadRegistral());
	    asiento.setCodigoEntidadRegistralDestino(asientoFormDTO.getCodigoEntidadRegistralDestino());
	    asiento.setCodigoEntidadRegistralInicio(asientoFormDTO.getCodigoEntidadRegistralInicio());
	    asiento.setCodigoEntidadRegistralOrigen(asientoFormDTO.getCodigoEntidadRegistralOrigen());
	    asiento.setCodigoUnidadTramitacionDestino(asientoFormDTO.getCodigoUnidadTramitacionDestino());
	    asiento.setCodigoUnidadTramitacionOrigen(asientoFormDTO.getCodigoUnidadTramitacionOrigen());
	    asiento.setContactoUsuario(asientoFormDTO.getContactoUsuario());
	    asiento.setDescripcionEntidadRegistralDestino(asientoFormDTO.getDescripcionEntidadRegistralDestino());
	    asiento.setDescripcionEntidadRegistralInicio(asientoFormDTO.getDescripcionEntidadRegistralInicio());
	    asiento.setDescripcionEntidadRegistralOrigen(asientoFormDTO.getDescripcionEntidadRegistralOrigen());
	    asiento.setDescripcionUnidadTramitacionDestino(asientoFormDTO.getDescripcionUnidadTramitacionDestino());
	    asiento.setDescripcionUnidadTramitacionOrigen(asientoFormDTO.getDescripcionUnidadTramitacionOrigen());
	    asiento.setDocumentacionFisica(asientoFormDTO.getDocumentacionFisica());
	    asiento.setExpone(asientoFormDTO.getExpone());
	    asiento.setFechaRegistro(asientoFormDTO.getFechaRegistro());
	    asiento.setFechaRegistroInicial(asientoFormDTO.getFechaRegistroInicial());
	    asiento.setIndicadorPrueba(asientoFormDTO.getIndicadorPrueba());
	    asiento.setNombreUsuario(asientoFormDTO.getNombreUsuario());
	    asiento.setNumeroExpediente(asientoFormDTO.getNumeroExpediente());
	    asiento.setNumeroRegistro(asientoFormDTO.getNumeroRegistro());
	    asiento.setNumeroRegistroInicial(asientoFormDTO.getNumeroRegistroInicial());
	    asiento.setNumeroTransporte(asientoFormDTO.getNumeroTransporte());
	    asiento.setObservacionesApunte(asientoFormDTO.getObservacionesApunte());
	    asiento.setReferenciaExterna(asientoFormDTO.getReferenciaExterna());
	    asiento.setResumen(asientoFormDTO.getResumen());
	    asiento.setSolicita(asientoFormDTO.getSolicita());
	    asiento.setTimestampRegistro(asientoFormDTO.getTimestampRegistro());
	    asiento.setTimestampRegistroInicial(asientoFormDTO.getTimestampRegistroInicial());
	    asiento.setTipoRegistro(asientoFormDTO.getTipoRegistro());
	    asiento.setTipoTransporte(asientoFormDTO.getTipoTransporte());

	    asiento.getAnexos().addAll(createAnexosDTO(id, asientoFormDTO.getAnexos()));
	    asiento.getInteresados().addAll(createInteresadosDTO(id, asientoFormDTO.getInteresados()));

		return asiento;
	}

	public static List<AnexoDTO> createAnexosDTO(String idAsientoRegistral, List<AnexoFormDTO> anexosFormDTO) {

		List<AnexoDTO> anexosDTO = new ArrayList<AnexoDTO>();

		if (anexosFormDTO != null) {
			int contador = 1;
			for (AnexoFormDTO anexoFormDTO : anexosFormDTO) {
				if (anexoFormDTO != null) {
					anexosDTO.add(createAnexoDTO(String.valueOf(contador++), idAsientoRegistral, anexoFormDTO));
				}
			}
		}

		return anexosDTO;
	}

	public static List<InteresadoDTO> createInteresadosDTO(String idAsientoRegistral, List<InteresadoFormDTO> interesadosFormDTO) {

		List<InteresadoDTO> interesadoDTO = new ArrayList<InteresadoDTO>();

		if (interesadosFormDTO != null) {
			int contador = 1;
			for (InteresadoFormDTO interesadoFormDTO : interesadosFormDTO) {
				if (interesadoFormDTO != null) {
					interesadoDTO.add(createInteresadoDTO(String.valueOf(contador++), idAsientoRegistral, interesadoFormDTO));
				}
			}
		}

		return interesadoDTO;
	}

	public static AnexoDTO createAnexoDTO(String id, String idAsientoRegistral) {

		AnexoDTO anexo = new AnexoDTO();

	    anexo.setCertificado("***certificado***".getBytes());
	    anexo.setFirma("***firma***".getBytes());
	    anexo.setHash("***hash***".getBytes());
	    anexo.setId(id);
	    anexo.setIdAsientoRegistral(idAsientoRegistral);
	    anexo.setIdentificadorFicheroFirmado(id);
	    anexo.setNombreFichero("NOMBRE_FICHERO_" + id + ".txt");
	    anexo.setObservaciones("OBSERVACIONES " + id);
	    anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
	    anexo.setTipoMIME("text/plain");
	    anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());

		return anexo;
	}

	public static AnexoDTO createAnexoDTO(String id, String idAsientoRegistral, AnexoFormDTO anexoFormDTO) {

		AnexoDTO anexo = new AnexoDTO();

	    anexo.setId(id);
	    anexo.setIdAsientoRegistral(idAsientoRegistral);
	    anexo.setCertificado(anexoFormDTO.getCertificado());
	    anexo.setFirma(anexoFormDTO.getFirma());
	    anexo.setHash("***hash***".getBytes());
	    anexo.setIdentificadorFicheroFirmado(id);
	    anexo.setNombreFichero(anexoFormDTO.getNombreFichero());
	    anexo.setObservaciones(anexoFormDTO.getObservaciones());
	    anexo.setTimestamp(anexoFormDTO.getTimestamp());
	    anexo.setTipoDocumento(anexoFormDTO.getTipoDocumento());
	    anexo.setTipoMIME(anexoFormDTO.getTipoMIME());
	    anexo.setValidacionOCSPCertificado(anexoFormDTO.getValidacionOCSPCertificado());
	    anexo.setValidezDocumento(anexoFormDTO.getValidezDocumento());

		return anexo;
	}

	public static InteresadoDTO createInteresadoDTO(String id, String idAsientoRegistral) {

		InteresadoDTO interesado = new InteresadoDTO();

	    interesado.setId(id);
	    interesado.setIdAsientoRegistral(idAsientoRegistral);

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("razonSocialInteresado " + id);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado " + id);
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico" + id + "@interesado.es");
		interesado.setTelefonoInteresado("999999999");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("nombreRepresentante " + id);
		interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante " + id);
		interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante " + id);
		interesado.setCodigoPaisRepresentante("0001");
		interesado.setCodigoProvinciaRepresentante("05");
		interesado.setCodigoMunicipioRepresentante("01544");
		interesado.setDireccionRepresentante("direccionRepresentante " + id);
		interesado.setCodigoPostalRepresentante("33004");
		interesado.setCorreoElectronicoRepresentante("correoElectronico" + id + "@representante.es");
		interesado.setTelefonoRepresentante("666666666");
		interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setObservaciones("observaciones " + id);

		return interesado;
	}

	public static InteresadoDTO createInteresadoDTO(String id, String idAsientoRegistral, InteresadoFormDTO interesadoFormDTO) {

		InteresadoDTO interesado = new InteresadoDTO();

	    interesado.setId(id);
	    interesado.setIdAsientoRegistral(idAsientoRegistral);

		interesado.setTipoDocumentoIdentificacionInteresado(interesadoFormDTO.getTipoDocumentoIdentificacionInteresado());
		interesado.setDocumentoIdentificacionInteresado(interesadoFormDTO.getDocumentoIdentificacionInteresado());
		interesado.setRazonSocialInteresado(interesadoFormDTO.getRazonSocialInteresado());
		interesado.setNombreInteresado(interesadoFormDTO.getNombreInteresado());
		interesado.setPrimerApellidoInteresado(interesadoFormDTO.getPrimerApellidoInteresado());
		interesado.setSegundoApellidoInteresado(interesadoFormDTO.getSegundoApellidoInteresado());
		interesado.setCodigoPaisInteresado(interesadoFormDTO.getCodigoPaisInteresado());
		interesado.setCodigoProvinciaInteresado(interesadoFormDTO.getCodigoProvinciaInteresado());
		interesado.setCodigoMunicipioInteresado(interesadoFormDTO.getCodigoMunicipioInteresado());
		interesado.setDireccionInteresado(interesadoFormDTO.getDireccionInteresado());
		interesado.setCodigoPostalInteresado(interesadoFormDTO.getCodigoPostalInteresado());
		interesado.setCorreoElectronicoInteresado(interesadoFormDTO.getCorreoElectronicoInteresado());
		interesado.setTelefonoInteresado(interesadoFormDTO.getTelefonoInteresado());
		interesado.setDireccionElectronicaHabilitadaInteresado(interesadoFormDTO.getDireccionElectronicaHabilitadaInteresado());
		interesado.setCanalPreferenteComunicacionInteresado(interesadoFormDTO.getCanalPreferenteComunicacionInteresado());

		interesado.setTipoDocumentoIdentificacionRepresentante(interesadoFormDTO.getTipoDocumentoIdentificacionRepresentante());
		interesado.setDocumentoIdentificacionRepresentante(interesadoFormDTO.getDocumentoIdentificacionRepresentante());
		interesado.setRazonSocialRepresentante(interesadoFormDTO.getRazonSocialRepresentante());
		interesado.setNombreRepresentante(interesadoFormDTO.getNombreRepresentante());
		interesado.setPrimerApellidoRepresentante(interesadoFormDTO.getPrimerApellidoRepresentante());
		interesado.setSegundoApellidoRepresentante(interesadoFormDTO.getSegundoApellidoRepresentante());
		interesado.setCodigoPaisRepresentante(interesadoFormDTO.getCodigoPaisRepresentante());
		interesado.setCodigoProvinciaRepresentante(interesadoFormDTO.getCodigoProvinciaRepresentante());
		interesado.setCodigoMunicipioRepresentante(interesadoFormDTO.getCodigoMunicipioRepresentante());
		interesado.setDireccionRepresentante(interesadoFormDTO.getDireccionRepresentante());
		interesado.setCodigoPostalRepresentante(interesadoFormDTO.getCodigoPostalRepresentante());
		interesado.setCorreoElectronicoRepresentante(interesadoFormDTO.getCorreoElectronicoRepresentante());
		interesado.setTelefonoRepresentante(interesadoFormDTO.getTelefonoRepresentante());
		interesado.setDireccionElectronicaHabilitadaRepresentante(interesadoFormDTO.getDireccionElectronicaHabilitadaRepresentante());
		interesado.setCanalPreferenteComunicacionRepresentante(interesadoFormDTO.getCanalPreferenteComunicacionRepresentante());

		interesado.setObservaciones(interesadoFormDTO.getObservaciones());

		return interesado;
	}

	public static AsientoRegistralFormVO createAsientoRegistralFormVO(String id) {

		AsientoRegistralFormVO asiento = new AsientoRegistralFormVO();

	    asiento.setCodigoAsunto("CODIGO_ASUNTO_" + id);
	    asiento.setCodigoEntidadRegistral("CODIGO_ENTIDAD_REGISTRAL_" + id);
	    asiento.setCodigoEntidadRegistralDestino("CODIGO_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setCodigoEntidadRegistralInicio("CODIGO_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setCodigoEntidadRegistralOrigen("CODIGO_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setCodigoUnidadTramitacionDestino("CODIGO_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setCodigoUnidadTramitacionOrigen("CODIGO_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setContactoUsuario("CONTACTO_USUARIO_" + id);
	    asiento.setDescripcionEntidadRegistralDestino("DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_" + id);
	    asiento.setDescripcionEntidadRegistralInicio("DESCRIPCION_ENTIDAD_REGISTRAL_INICIO_" + id);
	    asiento.setDescripcionEntidadRegistralOrigen("DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN_" + id);
	    asiento.setDescripcionUnidadTramitacionDestino("DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_" + id);
	    asiento.setDescripcionUnidadTramitacionOrigen("DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN_" + id);
	    asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
	    asiento.setExpone("EXPONE " + id);
	    asiento.setFechaRegistro(new Date());
	    asiento.setFechaRegistroInicial(new Date());
	    asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
	    asiento.setNombreUsuario("NOMBRE_USUARIO_" + id);
	    asiento.setNumeroExpediente("NUMERO_EXPEDIENTE_" + id);
	    asiento.setNumeroRegistro("NUMERO_REGISTRO_" + id);
	    asiento.setNumeroRegistroInicial("NUMERO_REGISTRO_INICIAL_" + id);
	    asiento.setNumeroTransporte("NUMERO_TRANSPORTE_" + id);
	    asiento.setObservacionesApunte("OBSERVACIONES_" + id);
	    asiento.setReferenciaExterna("REFERENCIA_EXTERNA_" + id);
	    asiento.setResumen("RESUMEN " + id);
	    asiento.setSolicita("SOLICITA " + id);
	    asiento.setTimestampRegistro("***timestamp***".getBytes());
	    asiento.setTimestampRegistroInicial("***timestamp***".getBytes());
	    asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA);
	    asiento.setTipoTransporte(TipoTransporteEnum.BUROFAX);

	    asiento.getAnexos().add(createAnexoFormVO(id + "_1"));
	    asiento.getAnexos().add(createAnexoFormVO(id + "_2"));

	    asiento.getInteresados().add(createInteresadoFormVO(id + "_1"));
	    asiento.getInteresados().add(createInteresadoFormVO(id + "_2"));

		return asiento;
	}

	public static AnexoFormVO createAnexoFormVO(String id) {

		AnexoFormVO anexo = new AnexoFormVO();

	    anexo.setCertificado("***certificado***".getBytes());
	    anexo.setCodigoFichero("CODIGO_FICHERO_" + id);
	    anexo.setCodigoFicheroFirmado(anexo.getCodigoFichero());
	    anexo.setContenido("***contenido***".getBytes());
	    anexo.setFirma("***firma***".getBytes());
	    anexo.setIdentificadorFicheroFirmado(null);
	    anexo.setNombreFichero("NOMBRE_FICHERO_" + id + ".txt");
	    anexo.setObservaciones("OBSERVACIONES " + id);
	    anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
	    anexo.setTipoMIME("text/plain");
	    anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA);

		return anexo;
	}

	public static InteresadoFormVO createInteresadoFormVO(String id) {

		InteresadoFormVO interesado = new InteresadoFormVO();

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("razonSocialInteresado " + id);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado " + id);
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico" + id + "@interesado.es");
		interesado.setTelefonoInteresado("999999999");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("nombreRepresentante " + id);
		interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante " + id);
		interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante " + id);
		interesado.setCodigoPaisRepresentante("0001");
		interesado.setCodigoProvinciaRepresentante("05");
		interesado.setCodigoMunicipioRepresentante("01544");
		interesado.setDireccionRepresentante("direccionRepresentante " + id);
		interesado.setCodigoPostalRepresentante("33004");
		interesado.setCorreoElectronicoRepresentante("correoElectronico" + id + "@representante.es");
		interesado.setTelefonoRepresentante("666666666");
		interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
		interesado.setObservaciones("observaciones " + id);

		return interesado;
	}

	public static AnexoVO createAnexoVO(String id, String idAsientoRegistral) {

		AnexoVO anexo = new AnexoVO();

	    anexo.setCertificado("***certificado***".getBytes());
	    anexo.setFirma("***firma***".getBytes());
	    anexo.setHash("***hash***".getBytes());
	    anexo.setId(id);
	    anexo.setIdAsientoRegistral(idAsientoRegistral);
	    anexo.setIdentificadorFicheroFirmado(id);
	    anexo.setNombreFichero("NOMBRE_FICHERO_" + id + ".txt");
	    anexo.setObservaciones("OBSERVACIONES " + id);
	    anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
	    anexo.setTipoMIME("text/plain");
	    anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA);

		return anexo;
	}

	public static InteresadoVO createInteresadoVO(String id, String idAsientoRegistral) {

		InteresadoVO interesado = new InteresadoVO();

	    interesado.setId(id);
	    interesado.setIdAsientoRegistral(idAsientoRegistral);

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("razonSocialInteresado " + id);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado " + id);
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico" + id + "@interesado.es");
		interesado.setTelefonoInteresado("999999999");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("nombreRepresentante " + id);
		interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante " + id);
		interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante " + id);
		interesado.setCodigoPaisRepresentante("0001");
		interesado.setCodigoProvinciaRepresentante("05");
		interesado.setCodigoMunicipioRepresentante("01544");
		interesado.setDireccionRepresentante("direccionRepresentante " + id);
		interesado.setCodigoPostalRepresentante("33004");
		interesado.setCorreoElectronicoRepresentante("correoElectronico" + id + "@representante.es");
		interesado.setTelefonoRepresentante("666666666");
		interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
		interesado.setObservaciones("observaciones " + id);

		return interesado;
	}

	public static void assertEquals(CriteriosVO criteriosVO, CriteriosDTO criteriosDTO) {

		Assert.assertNotNull("criteriosVO es nulo", criteriosVO);
		Assert.assertNotNull("criteriosDTO es nulo", criteriosDTO);

		assertEqualsCriterios(criteriosVO.getCriterios(), criteriosDTO.getCriterios());
		assertEqualsOrderBy(criteriosVO.getOrderBy(), criteriosDTO.getOrderBy());
		assertEquals(criteriosVO.getPageInfo(), criteriosDTO.getPageInfo());
	}

	public static void assertEqualsCriterios(List<CriterioVO> criteriosVO, List<CriterioDTO> criteriosDTO) {

		if (criteriosVO == null) {
			Assert.assertNull("criteriosVO es nulo y criteriosDTO no", criteriosDTO);
		} else {
			Assert.assertEquals(criteriosVO.size(), criteriosDTO.size());
			for (int i = 0; i < criteriosVO.size(); i++) {
				assertEquals(criteriosVO.get(i), criteriosDTO.get(i));
			}
		}
	}

	public static void assertEquals(CriterioVO criterioVO, CriterioDTO criterioDTO) {

		if (criterioVO == null) {
			Assert.assertNull("criterioVO es nulo y criterioDTO no", criterioDTO);
		} else {
			Assert.assertEquals(criterioVO.getNombre().getValue(), criterioDTO.getNombre());
			Assert.assertEquals(criterioVO.getOperador().getValue(), criterioDTO.getOperador());

			//Assert.assertEquals(criterioVO.getValor(), criterioDTO.getValor());
			if (criterioVO.getValor() instanceof ValuedEnum) {
				Assert.assertEquals(
						((ValuedEnum) criterioVO.getValor()).getValue(),
						criterioDTO.getValor().iterator().next());
			} else if (criterioVO.getValor() instanceof ValuedEnum[]) {
				Iterator<Object> it = criterioDTO.getValor().iterator();
				for (ValuedEnum enumerado : (ValuedEnum[])criterioVO.getValor()) {
					Assert.assertEquals(enumerado.getValue(), it.next());
				}
			} else if (criterioVO.getValor() instanceof StringValuedEnum) {
				Assert.assertEquals(
						((StringValuedEnum) criterioVO.getValor()).getValue(),
						criterioDTO.getValor().iterator().next());
			} else if (criterioVO.getValor() instanceof StringValuedEnum[]) {
				Iterator<Object> it = criterioDTO.getValor().iterator();
				for (StringValuedEnum enumerado : (StringValuedEnum[])criterioVO.getValor()) {
					Assert.assertEquals(enumerado.getValue(), it.next());
				}
			} else if (criterioVO.getValor() instanceof Object[]) {
				Iterator<Object> it = criterioDTO.getValor().iterator();
				for (Object obj : (Object[])criterioVO.getValor()) {
					Assert.assertEquals(obj, it.next());
				}
			} else {
				Assert.assertEquals(criterioVO.getValor(), criterioDTO
						.getValor().iterator().next());
			}
		}

	}

	public static void assertEqualsOrderBy(List<CriterioEnum> criteriosEnum, List<String> criterios) {

		if (criteriosEnum == null) {
			Assert.assertNull("criteriosEnum es nulo y criterios no", criterios);
		} else {
			Assert.assertEquals(criteriosEnum.size(), criterios.size());
			for (int i = 0; i < criteriosEnum.size(); i++) {
				assertEquals(criteriosEnum.get(i), criterios.get(i));
			}
		}
	}

	public static void assertEquals(CriterioEnum criterioEnum, String criterio) {

		if (criterioEnum == null) {
			Assert.assertNull("criterioEnum es nulo y criterio no", criterio);
		} else {
			Assert.assertEquals(criterioEnum.getValue(), criterio);
		}
	}

	public static void assertEquals(PageInfo pageInfo, PageInfoDTO pageInfoDTO) {

		if (pageInfo == null) {
			Assert.assertNull("pageInfo es nulo y pageInfoDTO no", pageInfoDTO);
		} else {
			Assert.assertEquals(pageInfo.getMaxNumItems(), pageInfoDTO.getMaxNumItems());
			Assert.assertEquals(pageInfo.getObjectsPerPage(), pageInfoDTO.getObjectsPerPage());
			Assert.assertEquals(pageInfo.getPageNumber(), pageInfoDTO.getPageNumber());
		}
	}

	public static void assertEqualsTrazas(List<TrazabilidadDTO> trazasDTO, List<TrazabilidadVO> trazasVO) {

		if (trazasDTO == null) {
			Assert.assertNull("trazasDTO es nulo y trazasVO no", trazasVO);
		} else {
			Assert.assertEquals(trazasDTO.size(), trazasVO.size());
			for (int i = 0; i < trazasDTO.size(); i++) {
				assertEquals(trazasDTO.get(i), trazasVO.get(i));
			}
		}
	}

	public static void assertEquals(TrazabilidadDTO trazaDTO, TrazabilidadVO trazaVO) {

		if (trazaDTO == null) {
			Assert.assertNull("trazaDTO es nulo y trazaVO no", trazaVO);
		} else {
		    Assert.assertEquals(trazaDTO.getCodigoError(), trazaVO.getCodigoError());
		    Assert.assertEquals(trazaDTO.getCodigoEstado(), trazaVO.getCodigoEstado());
		    Assert.assertEquals(trazaDTO.getCodigoIntercambio(), trazaVO.getCodigoIntercambio());
		    Assert.assertEquals(trazaDTO.getCodigoNodo(), trazaVO.getCodigoNodo());
		    Assert.assertEquals(trazaDTO.getCodigoEntidadRegistralDestino(), trazaVO.getCodigoEntidadRegistralDestino());
		    Assert.assertEquals(trazaDTO.getCodigoEntidadRegistralOrigen(), trazaVO.getCodigoEntidadRegistralOrigen());
		    Assert.assertEquals(trazaDTO.getCodigoUnidadTramitacionDestino(), trazaVO.getCodigoUnidadTramitacionDestino());
		    Assert.assertEquals(trazaDTO.getCodigoUnidadTramitacionOrigen(), trazaVO.getCodigoUnidadTramitacionOrigen());
		    Assert.assertEquals(trazaDTO.getDescripcionErrorAlternativa(), trazaVO.getDescripcionErrorAlternativa());
		    Assert.assertEquals(trazaDTO.getDescripcionEntidadRegistralDestino(), trazaVO.getDescripcionEntidadRegistralDestino());
		    Assert.assertEquals(trazaDTO.getDescripcionEntidadRegistralOrigen(), trazaVO.getDescripcionEntidadRegistralOrigen());
		    Assert.assertEquals(trazaDTO.getDescripcionUnidadTramitacionDestino(), trazaVO.getDescripcionUnidadTramitacionDestino());
		    Assert.assertEquals(trazaDTO.getDescripcionUnidadTramitacionOrigen(), trazaVO.getDescripcionUnidadTramitacionOrigen());
		    Assert.assertEquals(DateUtils.toDate(trazaDTO.getFechaAlta()).toString(), trazaVO.getFechaAlta().toString());
		    Assert.assertEquals(DateUtils.toDate(trazaDTO.getFechaModificacion()).toString(), trazaVO.getFechaModificacion().toString());
		    Assert.assertEquals(trazaDTO.getMotivoRechazo(), trazaVO.getMotivoRechazo());
		    Assert.assertEquals(trazaDTO.getNombreFicheroIntercambio(), trazaVO.getNombreFicheroIntercambio());
		    Assert.assertEquals(trazaDTO.isRegistro(), trazaVO.isRegistro());
		    Assert.assertEquals(trazaDTO.getTamanyoDocs(), trazaVO.getTamanyoDocs());

		}
	}

	public static void assertEqualsAsientos(List<AsientoRegistralDTO> asientosDTO, List<AsientoRegistralVO> asientosVO) {

		if (asientosDTO == null) {
			Assert.assertNull("asientosDTO es nulo y asientosVO no", asientosVO);
		} else {
			Assert.assertEquals(asientosDTO.size(), asientosVO.size());
			for (int i = 0; i < asientosDTO.size(); i++) {
				assertEquals(asientosDTO.get(i), asientosVO.get(i));
			}
		}
	}

	public static void assertEquals(AsientoRegistralFormVO asientoFormVO, AsientoRegistralVO asientoVO) {

		if (asientoFormVO == null) {
			Assert.assertNull("asientoFormVO es nulo y asientoVO no", asientoVO);
		} else {
			Assert.assertEquals(asientoFormVO.getCodigoAsunto(), asientoVO.getCodigoAsunto());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistral(), asientoVO.getCodigoEntidadRegistral());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralDestino(), asientoVO.getCodigoEntidadRegistralDestino());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralInicio(), asientoVO.getCodigoEntidadRegistralInicio());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralOrigen(), asientoVO.getCodigoEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormVO.getCodigoUnidadTramitacionDestino(), asientoVO.getCodigoUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormVO.getCodigoUnidadTramitacionOrigen(), asientoVO.getCodigoUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormVO.getContactoUsuario(), asientoVO.getContactoUsuario());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralDestino(), asientoVO.getDescripcionEntidadRegistralDestino());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralInicio(), asientoVO.getDescripcionEntidadRegistralInicio());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralOrigen(), asientoVO.getDescripcionEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormVO.getDescripcionUnidadTramitacionDestino(), asientoVO.getDescripcionUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormVO.getDescripcionUnidadTramitacionOrigen(), asientoVO.getDescripcionUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormVO.getDocumentacionFisica(), asientoVO.getDocumentacionFisica());
			Assert.assertEquals(asientoFormVO.getExpone(), asientoVO.getExpone());
			Assert.assertEquals(asientoFormVO.getFechaRegistro().toString(), asientoVO.getFechaRegistro().toString());
			Assert.assertEquals(asientoFormVO.getFechaRegistroInicial().toString(), asientoVO.getFechaRegistroInicial().toString());
			Assert.assertEquals(asientoFormVO.getIndicadorPrueba(), asientoVO.getIndicadorPrueba());
			Assert.assertEquals(asientoFormVO.getNombreUsuario(), asientoVO.getNombreUsuario());
			Assert.assertEquals(asientoFormVO.getNumeroExpediente(), asientoVO.getNumeroExpediente());
			Assert.assertEquals(asientoFormVO.getNumeroRegistro(), asientoVO.getNumeroRegistro());
			Assert.assertEquals(asientoFormVO.getNumeroRegistroInicial(), asientoVO.getNumeroRegistroInicial());
			Assert.assertEquals(asientoFormVO.getNumeroTransporte(), asientoVO.getNumeroTransporte());
			Assert.assertEquals(asientoFormVO.getObservacionesApunte(), asientoVO.getObservacionesApunte());
			Assert.assertEquals(asientoFormVO.getReferenciaExterna(), asientoVO.getReferenciaExterna());
			Assert.assertEquals(asientoFormVO.getResumen(), asientoVO.getResumen());
			Assert.assertEquals(asientoFormVO.getSolicita(), asientoVO.getSolicita());
			Assert.assertEquals(Base64.encodeBase64String(asientoFormVO.getTimestampRegistro()), Base64.encodeBase64String(asientoVO.getTimestampRegistro()));
			Assert.assertEquals(Base64.encodeBase64String(asientoFormVO.getTimestampRegistroInicial()), Base64.encodeBase64String(asientoVO.getTimestampRegistroInicial()));
			Assert.assertEquals(asientoFormVO.getTipoRegistro(), asientoVO.getTipoRegistro());
			Assert.assertEquals(asientoFormVO.getTipoTransporte(), asientoVO.getTipoTransporte());

			if (asientoFormVO.getAnexos() == null) {
				Assert.assertNull("asientoFormVO.getAnexos() es nulo y asientoVO.getAnexos() no", asientoVO.getAnexos());
			} else {
				Assert.assertEquals(asientoFormVO.getAnexos().size(), asientoVO.getAnexos().size());
				for (int i = 0; i < asientoFormVO.getAnexos().size(); i++) {
					assertEquals(asientoFormVO.getAnexos().get(i), asientoVO.getAnexos().get(i));
				}
			}

			if (asientoFormVO.getInteresados() == null) {
				Assert.assertNull("asientoFormVO.getInteresados() es nulo y asientoVO.getInteresados() no", asientoVO.getInteresados());
			} else {
				Assert.assertEquals(asientoFormVO.getInteresados().size(), asientoVO.getInteresados().size());
				for (int i = 0; i < asientoFormVO.getInteresados().size(); i++) {
					assertEquals(asientoFormVO.getInteresados().get(i), asientoVO.getInteresados().get(i));
				}
			}
		}
	}

	public static void assertEquals(InfoBAsientoRegistralDTO infoBAsientoDTO, InfoBAsientoRegistralVO infoBAsientoVO) {

		if (infoBAsientoDTO == null) {
			Assert.assertNull("infoBAsientoDTO es nulo y infoBAsientoVO no", infoBAsientoVO);
		} else {
			Assert.assertEquals(infoBAsientoDTO.getAplicacion(), infoBAsientoVO.getAplicacion());
			Assert.assertEquals(infoBAsientoDTO.getCodigoAsunto(), infoBAsientoVO.getCodigoAsunto());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistral(), infoBAsientoVO.getCodigoEntidadRegistral());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralDestino(), infoBAsientoVO.getCodigoEntidadRegistralDestino());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralInicio(), infoBAsientoVO.getCodigoEntidadRegistralInicio());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralOrigen(), infoBAsientoVO.getCodigoEntidadRegistralOrigen());
			Assert.assertEquals(infoBAsientoDTO.getCodigoError(), infoBAsientoVO.getCodigoError());
			Assert.assertEquals(infoBAsientoDTO.getCodigoUnidadTramitacionDestino(), infoBAsientoVO.getCodigoUnidadTramitacionDestino());
			Assert.assertEquals(infoBAsientoDTO.getCodigoUnidadTramitacionOrigen(), infoBAsientoVO.getCodigoUnidadTramitacionOrigen());
			Assert.assertEquals(infoBAsientoDTO.getContactoUsuario(), infoBAsientoVO.getContactoUsuario());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralDestino(), infoBAsientoVO.getDescripcionEntidadRegistralDestino());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralInicio(), infoBAsientoVO.getDescripcionEntidadRegistralInicio());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralOrigen(), infoBAsientoVO.getDescripcionEntidadRegistralOrigen());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionError(), infoBAsientoVO.getDescripcionError());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionUnidadTramitacionDestino(), infoBAsientoVO.getDescripcionUnidadTramitacionDestino());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionUnidadTramitacionOrigen(), infoBAsientoVO.getDescripcionUnidadTramitacionOrigen());
			Assert.assertEquals(infoBAsientoDTO.getDocumentacionFisica(), infoBAsientoVO.getDocumentacionFisica().getValue());
			Assert.assertEquals(infoBAsientoDTO.getEstado(), infoBAsientoVO.getEstado().getValue());
			Assert.assertEquals(infoBAsientoDTO.getExpone(), infoBAsientoVO.getExpone());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaEnvio()).toString(), infoBAsientoVO.getFechaEnvio().toString());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaEstado()).toString(), infoBAsientoVO.getFechaEstado().toString());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRecepcion()).toString(), infoBAsientoVO.getFechaRecepcion().toString());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRegistro()).toString(), infoBAsientoVO.getFechaRegistro().toString());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRegistroInicial()).toString(), infoBAsientoVO.getFechaRegistroInicial().toString());
			Assert.assertEquals(infoBAsientoDTO.getId(), infoBAsientoVO.getId());
			Assert.assertEquals(infoBAsientoDTO.getIdentificadorIntercambio(), infoBAsientoVO.getIdentificadorIntercambio());
			Assert.assertEquals(infoBAsientoDTO.getIndicadorPrueba(), infoBAsientoVO.getIndicadorPrueba().getValue());
			Assert.assertEquals(infoBAsientoDTO.getNombreUsuario(), infoBAsientoVO.getNombreUsuario());
			Assert.assertEquals(infoBAsientoDTO.getNumeroExpediente(), infoBAsientoVO.getNumeroExpediente());
			Assert.assertEquals(infoBAsientoDTO.getNumeroRegistro(), infoBAsientoVO.getNumeroRegistro());
			Assert.assertEquals(infoBAsientoDTO.getNumeroRegistroInicial(), infoBAsientoVO.getNumeroRegistroInicial());
			Assert.assertEquals(infoBAsientoDTO.getNumeroReintentos().intValue(), infoBAsientoVO.getNumeroReintentos());
			Assert.assertEquals(infoBAsientoDTO.getNumeroTransporte(), infoBAsientoVO.getNumeroTransporte());
			Assert.assertEquals(infoBAsientoDTO.getObservacionesApunte(), infoBAsientoVO.getObservacionesApunte());
			Assert.assertEquals(infoBAsientoDTO.getReferenciaExterna(), infoBAsientoVO.getReferenciaExterna());
			Assert.assertEquals(infoBAsientoDTO.getResumen(), infoBAsientoVO.getResumen());
			Assert.assertEquals(infoBAsientoDTO.getSolicita(), infoBAsientoVO.getSolicita());
			Assert.assertEquals(Base64.encodeBase64String(infoBAsientoDTO.getTimestampRegistro()), Base64.encodeBase64String(infoBAsientoVO.getTimestampRegistro()));
			Assert.assertEquals(Base64.encodeBase64String(infoBAsientoDTO.getTimestampRegistroInicial()), Base64.encodeBase64String(infoBAsientoVO.getTimestampRegistroInicial()));
			Assert.assertEquals(infoBAsientoDTO.getTipoRegistro(), infoBAsientoVO.getTipoRegistro().getValue());
			Assert.assertEquals(infoBAsientoDTO.getTipoTransporte(), infoBAsientoVO.getTipoTransporte().getValue());
		}
	}

	public static void assertEquals(AsientoRegistralDTO asientoDTO, AsientoRegistralVO asientoVO) {

		if (asientoDTO == null) {
			Assert.assertNull("asientoDTO es nulo y asientoVO no", asientoVO);
		} else {

			assertEquals((InfoBAsientoRegistralDTO)asientoDTO, (InfoBAsientoRegistralVO)asientoVO);

			if (asientoDTO.getAnexos() == null) {
				Assert.assertNull("asientoDTO.getAnexos() es nulo y asientoVO.getAnexos() no", asientoVO.getAnexos());
			} else {
				Assert.assertEquals(asientoDTO.getAnexos().size(), asientoVO.getAnexos().size());
				for (int i = 0; i < asientoDTO.getAnexos().size(); i++) {
					assertEquals(asientoDTO.getAnexos().get(i), asientoVO.getAnexos().get(i));
				}
			}

			if (asientoDTO.getInteresados() == null) {
				Assert.assertNull("asientoDTO.getInteresados() es nulo y asientoVO.getInteresados() no", asientoVO.getInteresados());
			} else {
				Assert.assertEquals(asientoDTO.getInteresados().size(), asientoVO.getInteresados().size());
				for (int i = 0; i < asientoDTO.getInteresados().size(); i++) {
					assertEquals(asientoDTO.getInteresados().get(i), asientoVO.getInteresados().get(i));
				}
			}
		}
	}

	public static void assertEquals(AnexoFormVO anexoFormVO, AnexoVO anexoVO) {

		if (anexoFormVO == null) {
			Assert.assertNull("anexoFormVO es nulo y anexoVO no", anexoVO);
		} else {
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getCertificado()), Base64.encodeBase64String(anexoVO.getCertificado()));
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getFirma()), Base64.encodeBase64String(anexoVO.getFirma()));
			Assert.assertEquals(anexoFormVO.getNombreFichero(), anexoVO.getNombreFichero());
			Assert.assertEquals(anexoFormVO.getObservaciones(), anexoVO.getObservaciones());
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getTimestamp()), Base64.encodeBase64String(anexoVO.getTimestamp()));
			Assert.assertEquals(anexoFormVO.getTipoDocumento(), anexoVO.getTipoDocumento());
			Assert.assertEquals(anexoFormVO.getTipoMIME(), anexoVO.getTipoMIME());
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoVO.getValidacionOCSPCertificado()));
			Assert.assertEquals(anexoFormVO.getValidezDocumento(), anexoVO.getValidezDocumento());
		}
	}

	public static void assertEquals(AnexoDTO anexoDTO, AnexoVO anexoVO) {

		if (anexoDTO == null) {
			Assert.assertNull("anexoDTO es nulo y anexoVO no", anexoVO);
		} else {
			Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getCertificado()), Base64.encodeBase64String(anexoVO.getCertificado()));
			Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getFirma()), Base64.encodeBase64String(anexoVO.getFirma()));
			Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getHash()), Base64.encodeBase64String(anexoVO.getHash()));
			Assert.assertEquals(anexoDTO.getId(), anexoVO.getId());
			Assert.assertEquals(anexoDTO.getIdAsientoRegistral(), anexoVO.getIdAsientoRegistral());
			Assert.assertEquals(anexoDTO.getIdentificadorFicheroFirmado(), anexoVO.getIdentificadorFicheroFirmado());
			Assert.assertEquals(anexoDTO.getNombreFichero(), anexoVO.getNombreFichero());
			Assert.assertEquals(anexoDTO.getObservaciones(), anexoVO.getObservaciones());
			Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getTimestamp()), Base64.encodeBase64String(anexoVO.getTimestamp()));
			Assert.assertEquals(anexoDTO.getTipoDocumento(), anexoVO.getTipoDocumento().getValue());
			Assert.assertEquals(anexoDTO.getTipoMIME(), anexoVO.getTipoMIME());
			Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoVO.getValidacionOCSPCertificado()));
			Assert.assertEquals(anexoDTO.getValidezDocumento(), anexoVO.getValidezDocumento().getValue());
		}
	}

	public static void assertEquals(AnexoVO anexo, AnexoVO anexoVO) {

		if (anexo == null) {
			Assert.assertNull("anexo es nulo y anexoVO no", anexoVO);
		} else {
			Assert.assertEquals(Base64.encodeBase64String(anexo.getCertificado()), Base64.encodeBase64String(anexoVO.getCertificado()));
			Assert.assertEquals(Base64.encodeBase64String(anexo.getFirma()), Base64.encodeBase64String(anexoVO.getFirma()));
			Assert.assertEquals(Base64.encodeBase64String(anexo.getHash()), Base64.encodeBase64String(anexoVO.getHash()));
			Assert.assertEquals(anexo.getId(), anexoVO.getId());
			Assert.assertEquals(anexo.getIdAsientoRegistral(), anexoVO.getIdAsientoRegistral());
			Assert.assertEquals(anexo.getIdentificadorFicheroFirmado(), anexoVO.getIdentificadorFicheroFirmado());
			Assert.assertEquals(anexo.getNombreFichero(), anexoVO.getNombreFichero());
			Assert.assertEquals(anexo.getObservaciones(), anexoVO.getObservaciones());
			Assert.assertEquals(Base64.encodeBase64String(anexo.getTimestamp()), Base64.encodeBase64String(anexoVO.getTimestamp()));
			Assert.assertEquals(anexo.getTipoDocumento(), anexoVO.getTipoDocumento());
			Assert.assertEquals(anexo.getTipoMIME(), anexoVO.getTipoMIME());
			Assert.assertEquals(Base64.encodeBase64String(anexo.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoVO.getValidacionOCSPCertificado()));
			Assert.assertEquals(anexo.getValidezDocumento(), anexoVO.getValidezDocumento());
		}
	}

	public static void assertEquals(InteresadoFormVO interesadoFormVO, InteresadoVO interesadoVO) {

		if (interesadoFormVO == null) {
			Assert.assertNull("interesadoFormVO es nulo y interesadoVO no", interesadoVO);
		} else {
			Assert.assertEquals(interesadoFormVO.getTipoDocumentoIdentificacionInteresado(), interesadoVO.getTipoDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormVO.getDocumentoIdentificacionInteresado(), interesadoVO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormVO.getRazonSocialInteresado(), interesadoVO.getRazonSocialInteresado());
			Assert.assertEquals(interesadoFormVO.getNombreInteresado(), interesadoVO.getNombreInteresado());
			Assert.assertEquals(interesadoFormVO.getPrimerApellidoInteresado(), interesadoVO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesadoFormVO.getSegundoApellidoInteresado(), interesadoVO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoPaisInteresado(), interesadoVO.getCodigoPaisInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoProvinciaInteresado(), interesadoVO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoMunicipioInteresado(), interesadoVO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesadoFormVO.getDireccionInteresado(), interesadoVO.getDireccionInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoPostalInteresado(), interesadoVO.getCodigoPostalInteresado());
			Assert.assertEquals(interesadoFormVO.getCorreoElectronicoInteresado(), interesadoVO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesadoFormVO.getTelefonoInteresado(), interesadoVO.getTelefonoInteresado());
			Assert.assertEquals(interesadoFormVO.getDireccionElectronicaHabilitadaInteresado(), interesadoVO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesadoFormVO.getCanalPreferenteComunicacionInteresado(), interesadoVO.getCanalPreferenteComunicacionInteresado());
			Assert.assertEquals(interesadoFormVO.getTipoDocumentoIdentificacionRepresentante(), interesadoVO.getTipoDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getDocumentoIdentificacionRepresentante(), interesadoVO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getRazonSocialRepresentante(), interesadoVO.getRazonSocialRepresentante());
			Assert.assertEquals(interesadoFormVO.getNombreRepresentante(), interesadoVO.getNombreRepresentante());
			Assert.assertEquals(interesadoFormVO.getPrimerApellidoRepresentante(), interesadoVO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesadoFormVO.getSegundoApellidoRepresentante(), interesadoVO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoPaisRepresentante(), interesadoVO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoProvinciaRepresentante(), interesadoVO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoMunicipioRepresentante(), interesadoVO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesadoFormVO.getDireccionRepresentante(), interesadoVO.getDireccionRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoPostalRepresentante(), interesadoVO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesadoFormVO.getCorreoElectronicoRepresentante(), interesadoVO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesadoFormVO.getTelefonoRepresentante(), interesadoVO.getTelefonoRepresentante());
			Assert.assertEquals(interesadoFormVO.getDireccionElectronicaHabilitadaRepresentante(), interesadoVO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesadoFormVO.getCanalPreferenteComunicacionRepresentante(), interesadoVO.getCanalPreferenteComunicacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getObservaciones(), interesadoVO.getObservaciones());
		}
	}

	public static void assertEquals(InteresadoDTO interesadoDTO, InteresadoVO interesadoVO) {

		if (interesadoDTO == null) {
			Assert.assertNull("interesadoDTO es nulo y interesadoVO no", interesadoVO);
		} else {
			Assert.assertEquals(interesadoDTO.getId(), interesadoVO.getId());
			Assert.assertEquals(interesadoDTO.getIdAsientoRegistral(), interesadoVO.getIdAsientoRegistral());
			Assert.assertEquals(interesadoDTO.getTipoDocumentoIdentificacionInteresado(), interesadoVO.getTipoDocumentoIdentificacionInteresado().getValue());
			Assert.assertEquals(interesadoDTO.getDocumentoIdentificacionInteresado(), interesadoVO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoDTO.getRazonSocialInteresado(), interesadoVO.getRazonSocialInteresado());
			Assert.assertEquals(interesadoDTO.getNombreInteresado(), interesadoVO.getNombreInteresado());
			Assert.assertEquals(interesadoDTO.getPrimerApellidoInteresado(), interesadoVO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesadoDTO.getSegundoApellidoInteresado(), interesadoVO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoPaisInteresado(), interesadoVO.getCodigoPaisInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoProvinciaInteresado(), interesadoVO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoMunicipioInteresado(), interesadoVO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesadoDTO.getDireccionInteresado(), interesadoVO.getDireccionInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoPostalInteresado(), interesadoVO.getCodigoPostalInteresado());
			Assert.assertEquals(interesadoDTO.getCorreoElectronicoInteresado(), interesadoVO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesadoDTO.getTelefonoInteresado(), interesadoVO.getTelefonoInteresado());
			Assert.assertEquals(interesadoDTO.getDireccionElectronicaHabilitadaInteresado(), interesadoVO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesadoDTO.getCanalPreferenteComunicacionInteresado(), interesadoVO.getCanalPreferenteComunicacionInteresado().getValue());
			Assert.assertEquals(interesadoDTO.getTipoDocumentoIdentificacionRepresentante(), interesadoVO.getTipoDocumentoIdentificacionRepresentante().getValue());
			Assert.assertEquals(interesadoDTO.getDocumentoIdentificacionRepresentante(), interesadoVO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoDTO.getRazonSocialRepresentante(), interesadoVO.getRazonSocialRepresentante());
			Assert.assertEquals(interesadoDTO.getNombreRepresentante(), interesadoVO.getNombreRepresentante());
			Assert.assertEquals(interesadoDTO.getPrimerApellidoRepresentante(), interesadoVO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesadoDTO.getSegundoApellidoRepresentante(), interesadoVO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoPaisRepresentante(), interesadoVO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoProvinciaRepresentante(), interesadoVO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoMunicipioRepresentante(), interesadoVO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesadoDTO.getDireccionRepresentante(), interesadoVO.getDireccionRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoPostalRepresentante(), interesadoVO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesadoDTO.getCorreoElectronicoRepresentante(), interesadoVO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesadoDTO.getTelefonoRepresentante(), interesadoVO.getTelefonoRepresentante());
			Assert.assertEquals(interesadoDTO.getDireccionElectronicaHabilitadaRepresentante(), interesadoVO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesadoDTO.getCanalPreferenteComunicacionRepresentante(), interesadoVO.getCanalPreferenteComunicacionRepresentante().getValue());
			Assert.assertEquals(interesadoDTO.getObservaciones(), interesadoVO.getObservaciones());
		}
	}

	public static void assertEquals(InteresadoVO interesado, InteresadoVO interesadoVO) {

		if (interesado == null) {
			Assert.assertNull("interesado es nulo y interesadoVO no", interesadoVO);
		} else {
			Assert.assertEquals(interesado.getId(), interesadoVO.getId());
			Assert.assertEquals(interesado.getIdAsientoRegistral(), interesadoVO.getIdAsientoRegistral());
			Assert.assertEquals(interesado.getTipoDocumentoIdentificacionInteresado(), interesadoVO.getTipoDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesado.getDocumentoIdentificacionInteresado(), interesadoVO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesado.getRazonSocialInteresado(), interesadoVO.getRazonSocialInteresado());
			Assert.assertEquals(interesado.getNombreInteresado(), interesadoVO.getNombreInteresado());
			Assert.assertEquals(interesado.getPrimerApellidoInteresado(), interesadoVO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesado.getSegundoApellidoInteresado(), interesadoVO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesado.getCodigoPaisInteresado(), interesadoVO.getCodigoPaisInteresado());
			Assert.assertEquals(interesado.getCodigoProvinciaInteresado(), interesadoVO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesado.getCodigoMunicipioInteresado(), interesadoVO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesado.getDireccionInteresado(), interesadoVO.getDireccionInteresado());
			Assert.assertEquals(interesado.getCodigoPostalInteresado(), interesadoVO.getCodigoPostalInteresado());
			Assert.assertEquals(interesado.getCorreoElectronicoInteresado(), interesadoVO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesado.getTelefonoInteresado(), interesadoVO.getTelefonoInteresado());
			Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaInteresado(), interesadoVO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesado.getCanalPreferenteComunicacionInteresado(), interesadoVO.getCanalPreferenteComunicacionInteresado());
			Assert.assertEquals(interesado.getTipoDocumentoIdentificacionRepresentante(), interesadoVO.getTipoDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesado.getDocumentoIdentificacionRepresentante(), interesadoVO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesado.getRazonSocialRepresentante(), interesadoVO.getRazonSocialRepresentante());
			Assert.assertEquals(interesado.getNombreRepresentante(), interesadoVO.getNombreRepresentante());
			Assert.assertEquals(interesado.getPrimerApellidoRepresentante(), interesadoVO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesado.getSegundoApellidoRepresentante(), interesadoVO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesado.getCodigoPaisRepresentante(), interesadoVO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesado.getCodigoProvinciaRepresentante(), interesadoVO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesado.getCodigoMunicipioRepresentante(), interesadoVO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesado.getDireccionRepresentante(), interesadoVO.getDireccionRepresentante());
			Assert.assertEquals(interesado.getCodigoPostalRepresentante(), interesadoVO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesado.getCorreoElectronicoRepresentante(), interesadoVO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesado.getTelefonoRepresentante(), interesadoVO.getTelefonoRepresentante());
			Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaRepresentante(), interesadoVO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesado.getCanalPreferenteComunicacionRepresentante(), interesadoVO.getCanalPreferenteComunicacionRepresentante());
			Assert.assertEquals(interesado.getObservaciones(), interesadoVO.getObservaciones());
		}
	}

	public static void assertEquals(AsientoRegistralFormVO asientoFormVO, AsientoRegistralFormDTO asientoFormDTO) {

		if (asientoFormVO == null) {
			Assert.assertNull("asientoFormVO es nulo y asientoFormDTO no", asientoFormDTO);
		} else {
			Assert.assertEquals(asientoFormVO.getCodigoAsunto(), asientoFormDTO.getCodigoAsunto());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistral(), asientoFormDTO.getCodigoEntidadRegistral());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralDestino(), asientoFormDTO.getCodigoEntidadRegistralDestino());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralInicio(), asientoFormDTO.getCodigoEntidadRegistralInicio());
			Assert.assertEquals(asientoFormVO.getCodigoEntidadRegistralOrigen(), asientoFormDTO.getCodigoEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormVO.getCodigoUnidadTramitacionDestino(), asientoFormDTO.getCodigoUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormVO.getCodigoUnidadTramitacionOrigen(), asientoFormDTO.getCodigoUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormVO.getContactoUsuario(), asientoFormDTO.getContactoUsuario());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralDestino(), asientoFormDTO.getDescripcionEntidadRegistralDestino());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralInicio(), asientoFormDTO.getDescripcionEntidadRegistralInicio());
			Assert.assertEquals(asientoFormVO.getDescripcionEntidadRegistralOrigen(), asientoFormDTO.getDescripcionEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormVO.getDescripcionUnidadTramitacionDestino(), asientoFormDTO.getDescripcionUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormVO.getDescripcionUnidadTramitacionOrigen(), asientoFormDTO.getDescripcionUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormVO.getDocumentacionFisica().getValue(), asientoFormDTO.getDocumentacionFisica());
			Assert.assertEquals(asientoFormVO.getExpone(), asientoFormDTO.getExpone());
			Assert.assertEquals(asientoFormVO.getFechaRegistro().toString(), DateUtils.toDate(asientoFormDTO.getFechaRegistro()).toString());
			Assert.assertEquals(asientoFormVO.getFechaRegistroInicial().toString(), DateUtils.toDate(asientoFormDTO.getFechaRegistroInicial()).toString());
			Assert.assertEquals(asientoFormVO.getIndicadorPrueba().getValue(), asientoFormDTO.getIndicadorPrueba());
			Assert.assertEquals(asientoFormVO.getNombreUsuario(), asientoFormDTO.getNombreUsuario());
			Assert.assertEquals(asientoFormVO.getNumeroExpediente(), asientoFormDTO.getNumeroExpediente());
			Assert.assertEquals(asientoFormVO.getNumeroRegistro(), asientoFormDTO.getNumeroRegistro());
			Assert.assertEquals(asientoFormVO.getNumeroRegistroInicial(), asientoFormDTO.getNumeroRegistroInicial());
			Assert.assertEquals(asientoFormVO.getNumeroTransporte(), asientoFormDTO.getNumeroTransporte());
			Assert.assertEquals(asientoFormVO.getObservacionesApunte(), asientoFormDTO.getObservacionesApunte());
			Assert.assertEquals(asientoFormVO.getReferenciaExterna(), asientoFormDTO.getReferenciaExterna());
			Assert.assertEquals(asientoFormVO.getResumen(), asientoFormDTO.getResumen());
			Assert.assertEquals(asientoFormVO.getSolicita(), asientoFormDTO.getSolicita());
			Assert.assertEquals(asientoFormVO.getTimestampRegistro(), asientoFormDTO.getTimestampRegistro());
			Assert.assertEquals(asientoFormVO.getTimestampRegistroInicial(), asientoFormDTO.getTimestampRegistroInicial());
			Assert.assertEquals(asientoFormVO.getTipoRegistro().getValue(), asientoFormDTO.getTipoRegistro());
			Assert.assertEquals(asientoFormVO.getTipoTransporte().getValue(), asientoFormDTO.getTipoTransporte());

			if (asientoFormVO.getAnexos() == null) {
				Assert.assertNull("asientoFormVO.getAnexos() es nulo y asientoFormDTO.getAnexos() no", asientoFormDTO.getAnexos());
			} else {
				Assert.assertEquals(asientoFormVO.getAnexos().size(), asientoFormDTO.getAnexos().size());
				for (int i = 0; i < asientoFormVO.getAnexos().size(); i++) {
					assertEquals(asientoFormVO.getAnexos().get(i), asientoFormDTO.getAnexos().get(i));
				}
			}

			if (asientoFormVO.getInteresados() == null) {
				Assert.assertNull("asientoFormVO.getInteresados() es nulo y asientoFormDTO.getInteresados() no", asientoFormDTO.getInteresados());
			} else {
				Assert.assertEquals(asientoFormVO.getInteresados().size(), asientoFormDTO.getInteresados().size());
				for (int i = 0; i < asientoFormVO.getInteresados().size(); i++) {
					assertEquals(asientoFormVO.getInteresados().get(i), asientoFormDTO.getInteresados().get(i));
				}
			}
		}
	}

	public static void assertEquals(AnexoFormVO anexoFormVO, AnexoFormDTO anexoFormDTO) {

		if (anexoFormVO == null) {
			Assert.assertNull("anexoFormVO es nulo y anexoFormDTO no", anexoFormDTO);
		} else {
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getCertificado()), Base64.encodeBase64String(anexoFormDTO.getCertificado()));
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getFirma()), Base64.encodeBase64String(anexoFormDTO.getFirma()));
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getContenido()), Base64.encodeBase64String(anexoFormDTO.getContenido()));
			Assert.assertEquals(anexoFormVO.getIdentificadorFicheroFirmado(), anexoFormDTO.getIdentificadorFicheroFirmado());
			Assert.assertEquals(anexoFormVO.getCodigoFichero(), anexoFormDTO.getCodigoFichero());
			Assert.assertEquals(anexoFormVO.getCodigoFicheroFirmado(), anexoFormDTO.getCodigoFicheroFirmado());
			Assert.assertEquals(anexoFormVO.getNombreFichero(), anexoFormDTO.getNombreFichero());
			Assert.assertEquals(anexoFormVO.getObservaciones(), anexoFormDTO.getObservaciones());
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getTimestamp()), Base64.encodeBase64String(anexoFormDTO.getTimestamp()));
			Assert.assertEquals(anexoFormVO.getTipoDocumento().getValue(), anexoFormDTO.getTipoDocumento());
			Assert.assertEquals(anexoFormVO.getTipoMIME(), anexoFormDTO.getTipoMIME());
			Assert.assertEquals(Base64.encodeBase64String(anexoFormVO.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoFormDTO.getValidacionOCSPCertificado()));
			Assert.assertEquals(anexoFormVO.getValidezDocumento().getValue(), anexoFormDTO.getValidezDocumento());
		}
	}

	public static void assertEquals(InteresadoFormVO interesadoFormVO, InteresadoFormDTO interesadoFormDTO) {

		if (interesadoFormVO == null) {
			Assert.assertNull("interesadoFormVO es nulo y interesadoFormDTO no", interesadoFormDTO);
		} else {
			Assert.assertEquals(interesadoFormVO.getTipoDocumentoIdentificacionInteresado().getValue(), interesadoFormDTO.getTipoDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormVO.getDocumentoIdentificacionInteresado(), interesadoFormDTO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormVO.getRazonSocialInteresado(), interesadoFormDTO.getRazonSocialInteresado());
			Assert.assertEquals(interesadoFormVO.getNombreInteresado(), interesadoFormDTO.getNombreInteresado());
			Assert.assertEquals(interesadoFormVO.getPrimerApellidoInteresado(), interesadoFormDTO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesadoFormVO.getSegundoApellidoInteresado(), interesadoFormDTO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoPaisInteresado(), interesadoFormDTO.getCodigoPaisInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoProvinciaInteresado(), interesadoFormDTO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoMunicipioInteresado(), interesadoFormDTO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesadoFormVO.getDireccionInteresado(), interesadoFormDTO.getDireccionInteresado());
			Assert.assertEquals(interesadoFormVO.getCodigoPostalInteresado(), interesadoFormDTO.getCodigoPostalInteresado());
			Assert.assertEquals(interesadoFormVO.getCorreoElectronicoInteresado(), interesadoFormDTO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesadoFormVO.getTelefonoInteresado(), interesadoFormDTO.getTelefonoInteresado());
			Assert.assertEquals(interesadoFormVO.getDireccionElectronicaHabilitadaInteresado(), interesadoFormDTO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesadoFormVO.getCanalPreferenteComunicacionInteresado().getValue(), interesadoFormDTO.getCanalPreferenteComunicacionInteresado());
			Assert.assertEquals(interesadoFormVO.getTipoDocumentoIdentificacionRepresentante().getValue(), interesadoFormDTO.getTipoDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getDocumentoIdentificacionRepresentante(), interesadoFormDTO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getRazonSocialRepresentante(), interesadoFormDTO.getRazonSocialRepresentante());
			Assert.assertEquals(interesadoFormVO.getNombreRepresentante(), interesadoFormDTO.getNombreRepresentante());
			Assert.assertEquals(interesadoFormVO.getPrimerApellidoRepresentante(), interesadoFormDTO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesadoFormVO.getSegundoApellidoRepresentante(), interesadoFormDTO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoPaisRepresentante(), interesadoFormDTO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoProvinciaRepresentante(), interesadoFormDTO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoMunicipioRepresentante(), interesadoFormDTO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesadoFormVO.getDireccionRepresentante(), interesadoFormDTO.getDireccionRepresentante());
			Assert.assertEquals(interesadoFormVO.getCodigoPostalRepresentante(), interesadoFormDTO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesadoFormVO.getCorreoElectronicoRepresentante(), interesadoFormDTO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesadoFormVO.getTelefonoRepresentante(), interesadoFormDTO.getTelefonoRepresentante());
			Assert.assertEquals(interesadoFormVO.getDireccionElectronicaHabilitadaRepresentante(), interesadoFormDTO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesadoFormVO.getCanalPreferenteComunicacionRepresentante().getValue(), interesadoFormDTO.getCanalPreferenteComunicacionRepresentante());
			Assert.assertEquals(interesadoFormVO.getObservaciones(), interesadoFormDTO.getObservaciones());
		}
	}

//	public static ValidacionAnexoDTO createValidacionAnexoDTO(String idAnexo, String idAsientoRegistral) {
//
//		ValidacionAnexoDTO validacionAnexoDTO = new ValidacionAnexoDTO();
//
//		validacionAnexoDTO.setAnexo(createAnexoDTO(idAnexo, idAsientoRegistral));
//		validacionAnexoDTO.setHashValidado(Boolean.TRUE);
//		validacionAnexoDTO.setValidacionCertificado(ValidacionCertificadoEnum.CERTIFICADO_VALIDO.getValue());
//		validacionAnexoDTO.setDescripcionErrorValidacionCertificado(null);
//		validacionAnexoDTO.setValidacionOCSPCertificado(Boolean.TRUE);
//		validacionAnexoDTO.setValidacionFirma(ValidacionFirmaEnum.FIRMA_VALIDA.getValue());
//
//		return validacionAnexoDTO;
//	}
//
//	public static void assertEquals(List<ValidacionAnexoDTO> validacionesAnexoDTO, List<ValidacionAnexoVO> validacionesAnexoVO) {
//
//		if (validacionesAnexoDTO == null) {
//			Assert.assertNull("validacionesAnexoDTO es nulo y validacionesAnexoVO no", validacionesAnexoVO);
//		} else {
//			Assert.assertEquals(validacionesAnexoDTO.size(), validacionesAnexoVO.size());
//			for (int i = 0; i < validacionesAnexoDTO.size(); i++) {
//				assertEquals(validacionesAnexoDTO.get(i), validacionesAnexoVO.get(i));
//			}
//		}
//	}
//
//	public static void assertEquals(ValidacionAnexoDTO validacionAnexoDTO, ValidacionAnexoVO validacionAnexoVO) {
//
//		if (validacionAnexoDTO == null) {
//			Assert.assertNull("validacionAnexoDTO es nulo y validacionAnexoVO no", validacionAnexoVO);
//		} else {
//			assertEquals(validacionAnexoDTO.getAnexo(), validacionAnexoVO.getAnexo());
//			Assert.assertEquals(validacionAnexoDTO.isHashValidado(), validacionAnexoVO.isHashValidado());
//			Assert.assertEquals(validacionAnexoDTO.getValidacionCertificado().intValue(), validacionAnexoVO.getValidacionCertificado().getValue());
//			Assert.assertEquals(validacionAnexoDTO.getDescripcionErrorValidacionCertificado(), validacionAnexoVO.getDescripcionErrorValidacionCertificado());
//			Assert.assertEquals(validacionAnexoDTO.isValidacionOCSPCertificado(), validacionAnexoVO.isValidacionOCSPCertificado());
//			Assert.assertEquals(validacionAnexoDTO.getValidacionFirma().intValue(), validacionAnexoVO.getValidacionFirma().getValue());
//		}
//
//	}

	public static InfoRechazoVO createInfoRechazoVO(TipoRechazoEnum tipoRechazo) {
		
		InfoRechazoVO infoRechazo = new InfoRechazoVO();
		
		infoRechazo.setTipoRechazo(tipoRechazo);
		infoRechazo.setDescripcion("Motivo del rechazo");
		infoRechazo.setUsuario("usuario");
		infoRechazo.setContacto("contacto");
		infoRechazo.setAplicacion("app1");
		
		return infoRechazo;
	}

	public static void assertEquals(InfoRechazoDTO infoRechazoDTO, InfoRechazoVO infoRechazoVO) {

		Assert.assertNotNull("infoRechazoDTO es nulo", infoRechazoDTO);
		Assert.assertNotNull("infoRechazoVO es nulo", infoRechazoVO);

		Assert.assertEquals(infoRechazoDTO.getAplicacion(), infoRechazoVO.getAplicacion());
		Assert.assertEquals(infoRechazoDTO.getContacto(), infoRechazoVO.getContacto());
		Assert.assertEquals(infoRechazoDTO.getDescripcion(), infoRechazoVO.getDescripcion());
		Assert.assertEquals(infoRechazoDTO.getTipoRechazo(), infoRechazoVO.getTipoRechazo().getValue());
		Assert.assertEquals(infoRechazoDTO.getUsuario(), infoRechazoVO.getUsuario());
	}
	
	public static void assertEquals(InfoReenvioDTO infoReenvioDTO, InfoReenvioVO infoReenvioVO) {

		Assert.assertNotNull("infoReenvioDTO es nulo", infoReenvioDTO);
		Assert.assertNotNull("infoReenvioVO es nulo", infoReenvioVO);

		Assert.assertEquals(infoReenvioDTO.getCodigoEntidadRegistralDestino(), infoReenvioVO.getCodigoEntidadRegistralDestino());
		Assert.assertEquals(infoReenvioDTO.getDescripcionEntidadRegistralDestino(), infoReenvioVO.getDescripcionEntidadRegistralDestino());
		Assert.assertEquals(infoReenvioDTO.getAplicacion(), infoReenvioVO.getAplicacion());
		Assert.assertEquals(infoReenvioDTO.getContacto(), infoReenvioVO.getContacto());
		Assert.assertEquals(infoReenvioDTO.getDescripcion(), infoReenvioVO.getDescripcion());
		Assert.assertEquals(infoReenvioDTO.getUsuario(), infoReenvioVO.getUsuario());
	}

}
