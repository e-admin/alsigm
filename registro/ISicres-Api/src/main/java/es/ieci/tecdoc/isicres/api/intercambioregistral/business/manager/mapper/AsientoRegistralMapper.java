package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;


import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.util.mime.MimeUtil;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfiguration;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralExceptionCodes;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.DocumentacionFisicaIntercambioRegistralEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroCamposExtendidosVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPageRepositoryVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPersonaFisicaOJuridicaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoRegistroEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

/**
 * Clase que mapea los datos entre el modelo de datos del módulo intermedio y el
 * modelo de ISicres
 *
 */
public class AsientoRegistralMapper {

	private static final Logger logger = Logger
			.getLogger(AsientoRegistralMapper.class);

	private static final int TIPO_DIR_TELEMATICA_TLF_FIJO = 1;
	private static final int TIPO_DIR_TELEMATICA_TLF_MOVIL = 5;
	private static final int TIPO_DIR_TELEMATICA_EMAIL = 2;
	private static final int TIPO_DIR_TELEMATICA_FAX = 3;
	private static final int TIPO_DIR_TELEMATICA_DEU = 4;

	private static final String EXTENSION_XSIG = "XSIG";
	private static final String EXTENSION_XADES = "XADES";



	/**
	 * Mapea los datos de un intercambio del módulo intermedio a los datos del
	 * modelo de ISicres para mostrarlo en la bandeja de entrada
	 *
	 * @param asientoRegistral
	 * @return
	 */
	public BandejaEntradaItemVO toBandejaEntradaItemVO(
			AsientoRegistralVO asientoRegistral) {
		BandejaEntradaItemVO bandejaEntradaItem = new BandejaEntradaItemVO();

		bandejaEntradaItem.setOrigen(asientoRegistral
				.getCodigoEntidadRegistralOrigen());
		bandejaEntradaItem.setOrigenName(asientoRegistral
				.getDescripcionEntidadRegistralOrigen());
		bandejaEntradaItem.setFechaEstado(asientoRegistral.getFechaEstado());
		bandejaEntradaItem.setFechaIntercambioRegistral(asientoRegistral
				.getFechaEnvio());
		bandejaEntradaItem
				.setFechaRegistro(asientoRegistral.getFechaRegistro());
		bandejaEntradaItem.setIdIntercambioRegistral(asientoRegistral
				.getIdentificadorIntercambio());
		// IdIntercambioInterno es el identificador del asiento registral
		bandejaEntradaItem.setIdIntercambioInterno(asientoRegistral
				.getIdAsLong());
		// se realiza la correspondendia de estados entre el sir y los tipos de
		// libro en sicres
		bandejaEntradaItem.setTipoLibro(TipoRegistroEnumVO
				.getTipoRegistroFromSIR(asientoRegistral.getTipoRegistro())
				.getValue());
		bandejaEntradaItem.setNumeroRegistroOriginal(asientoRegistral
				.getNumeroRegistro());
		bandejaEntradaItem
				.setDocumentacionFisicaIntercambioRegistral(DocumentacionFisicaIntercambioRegistralEnum
						.getDocumentacionFisica(asientoRegistral
								.getDocumentacionFisica().getValue()));

		if (EstadoAsientoRegistralEnum.RECIBIDO == asientoRegistral.getEstado()) {
			// Nosotros en realidad lo mostramos pendiente de aceptar
			bandejaEntradaItem
					.setEstado(EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE);
		} else if (EstadoAsientoRegistralEnum.REENVIADO == asientoRegistral
				.getEstado()) {
			//Estado reenviado
			bandejaEntradaItem
					.setEstado(EstadoIntercambioRegistralEntradaEnumVO.REENVIADO);
		} else {
			// En la bandeja de entrada sólo se consultan al CIR los pendientes
			// y los rechazados, así que si no es pendiente, será rechazado
			bandejaEntradaItem
					.setEstado(EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO);
		}

		return bandejaEntradaItem;
	}

	/**
	 * Mapea los datos de un registro a un intercambio registral del módulo
	 * intermedio
	 *
	 * @param infoRegistro
	 * @return
	 */
	public AsientoRegistralFormVO toAsientoRegistralFormVO(
			InfoRegistroVO infoRegistro) {
		AsientoRegistralFormVO asientoRegistral = null;

		asientoRegistral = new AsientoRegistralFormVO();
		// Seteamos info de registro

		//no se van a enviar estos datos ya que son opcionales
		//asientoRegistral.setContactoUsuario(infoRegistro.getContactoUsuario());
		//asientoRegistral.setNombreUsuario(infoRegistro.getNombreUsuario());
		asientoRegistral.setContactoUsuario(null);
		asientoRegistral.setNombreUsuario(null);

		asientoRegistral.setFechaRegistro(infoRegistro.getFechaRegistro());

		asientoRegistral
				.setNumeroExpediente(infoRegistro.getNumeroExpediente());
		asientoRegistral.setNumeroRegistro(infoRegistro.getNumeroRegistro());

		asientoRegistral.setObservacionesApunte(infoRegistro
				.getObservacionesApunte());
		asientoRegistral.setResumen(infoRegistro.getResumen());
		if (StringUtils.isEmpty(infoRegistro.getResumen())) {
			asientoRegistral.setResumen(infoRegistro.getDescripcionAsunto());
		}

		setCamposExtendidos(asientoRegistral, infoRegistro);

		//TODO aqui se setearia el asunto

		//TODO aqui se setearia los datos del tipo transporte

		// Seteamos la configuración de intercambio registral

		UnidadTramitacionIntercambioRegistralVO unidadTramitacionDestino = infoRegistro
				.getUnidadTramitacionDestino();
		asientoRegistral
				.setCodigoEntidadRegistralDestino(unidadTramitacionDestino
						.getCodeEntity());
		asientoRegistral
				.setDescripcionEntidadRegistralDestino(unidadTramitacionDestino
						.getNameEntity());
		if (unidadTramitacionDestino.getCodeTramunit() != null) {
			asientoRegistral
					.setCodigoUnidadTramitacionDestino(unidadTramitacionDestino
							.getCodeTramunit());
			asientoRegistral
					.setDescripcionUnidadTramitacionDestino(unidadTramitacionDestino
							.getNameTramunit());
		}

		EntidadRegistralVO entidadRegistralOrigen = infoRegistro
				.getEntidadRegistralOrigen();
		asientoRegistral.setCodigoEntidadRegistralOrigen(entidadRegistralOrigen
				.getCode());
		asientoRegistral
				.setDescripcionEntidadRegistralOrigen(entidadRegistralOrigen
						.getName());

		asientoRegistral.setCodigoEntidadRegistralInicio(entidadRegistralOrigen
				.getCode());
		asientoRegistral
				.setDescripcionEntidadRegistralInicio(entidadRegistralOrigen
						.getName());

		if (infoRegistro.getUnidadTramitacionOrigen() != null) {
			asientoRegistral.setCodigoUnidadTramitacionOrigen(infoRegistro
					.getUnidadTramitacionOrigen().getCodeTramunit());
			asientoRegistral.setDescripcionUnidadTramitacionOrigen(infoRegistro
					.getUnidadTramitacionOrigen().getNameTramunit());
		}

		asientoRegistral.setCodigoEntidadRegistral(entidadRegistralOrigen
				.getCode());

		// Si no tiene interesados validados validamos que tenga UNIDAD de
		// origen mapeada
		if (!setInteresados(asientoRegistral, infoRegistro)) {
			logger.info("El registro a enviar NO tiene terceros, o no son terceros validados");
			if (StringUtils.isEmpty(asientoRegistral
					.getCodigoUnidadTramitacionOrigen())) {
				throw new IntercambioRegistralException(
						"El registro no tiene ni interesados validados ni una Unidad Administrativa de Origen mapeada en el DCO.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_U_ORIGEN);
			}

			logger.info("El registro se envía igualmente porque tiene mapeada la UT="
					+ asientoRegistral.getCodigoUnidadTramitacionOrigen());

		}

		// ANEXOS
		setAnexos(asientoRegistral, infoRegistro);

		asientoRegistral
				.setDocumentacionFisica(getDocumentacionFisica(infoRegistro));

		return asientoRegistral;
	}

	/**
	 * Método que setea el tipo de asunto
	 *
	 * @param infoRegistro - {@link InfoRegistroVO} - Datos del registro
	 * @param asientoRegistral - {@link AsientoRegistralFormVO} - Formato de los datos de salida
	 */
	private void setAsunto(InfoRegistroVO infoRegistro,
			AsientoRegistralFormVO asientoRegistral) {
		// Seteamos el asunto en el campo comentario, debido a puede que el
		// código no exista en el entorno que lo recibe, produciendo diversos
		// errores de validación de datos
		StringBuffer comentarioRegistro = new StringBuffer();
		//validamos si los datos de observaciones vienen rellenos
		if(StringUtils.isNotEmpty(asientoRegistral.getObservacionesApunte())){
			// Obtenemos la información del campo comentario para añadir la información del transporte
			comentarioRegistro.append(asientoRegistral.getObservacionesApunte()).append("\n");
		}
		//comprobamos si el asunto esta rellenado
		if(StringUtils.isNotEmpty(infoRegistro.getCodigoAsunto())){
			comentarioRegistro.append("Cod. Asunto: ")
					.append(infoRegistro.getCodigoAsunto());
		}

		//asignamos los datos al campo observaciones del apunte
		asientoRegistral.setObservacionesApunte(comentarioRegistro.toString());
	}

	/**
	 * Método que añade la información del transporte al campo comentario (InfoRegistroVO.getObservacionesApunte()) del registro
	 *
	 * @param infoRegistro - {@link InfoRegistroVO}
	 * @param asientoRegistral - {@link AsientoRegistralFormVO}
	 *
	 */
	private void setDatosTransporte(InfoRegistroVO infoRegistro,
			AsientoRegistralFormVO asientoRegistral) {

		StringBuffer comentarioRegistro = new StringBuffer();
		//validamos si los datos de observaciones vienen rellenos
		if(StringUtils.isNotEmpty(asientoRegistral.getObservacionesApunte())){
			// Obtenemos la información del campo comentario para añadir la información del transporte
			comentarioRegistro.append(asientoRegistral.getObservacionesApunte()).append("\n");
		}

		//Seteamos los datos del transporte en el campo comentario del registro

		// Tipo transporte es opcional, no lo enviamos de momento
		if(StringUtils.isNotEmpty(infoRegistro.getTipoTransporte())){
			comentarioRegistro.append("Tipo de Transporte: ")
					.append(infoRegistro.getTipoTransporte()).append("\n");
		}

		//Numero de transporte
		if (StringUtils.isNotEmpty(infoRegistro.getNumeroTransporte())) {
			comentarioRegistro.append("Num. Transporte: ")
					.append(infoRegistro.getNumeroTransporte());
		}

		//asignamos los datos al campo observaciones del apunte
		asientoRegistral.setObservacionesApunte(comentarioRegistro.toString());

	}

	private void setAnexos(AsientoRegistralFormVO asientoRegistral,
			InfoRegistroVO infoRegistro) {
		List<AnexoFormVO> anexos = new ArrayList<AnexoFormVO>();
		List<InfoRegistroPageRepositoryVO> listaDocumetnos = infoRegistro
				.getListadoDocumentos();
		for (Iterator<InfoRegistroPageRepositoryVO> iterator = listaDocumetnos.iterator(); iterator.hasNext();) {
			InfoRegistroPageRepositoryVO documento = (InfoRegistroPageRepositoryVO) iterator
					.next();
			AnexoFormVO anexo = new AnexoFormVO();

			anexo.setContenido(documento.getContent());
			anexo.setNombreFichero(documento.getInfoDocumento().getNombre());
			// TODO validar ext
			anexo.setTipoMIME(MimeUtil.getExtensionMimeType(documento
					.getInfoDocumento().getExtension()));

			// comprobamos la extensión del fichero, para averiguar si es la firma del documento
			if (EXTENSION_XADES.equalsIgnoreCase(documento.getInfoDocumento()
					.getExtension())
					|| EXTENSION_XSIG.equalsIgnoreCase(documento.getInfoDocumento()
							.getExtension())) {
				//si es la firma del documento, el tipo de documento es de tipo técnico interno
				anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
			} else {
				//para cualquier otra extensión se considera documento de tipo documento adjunto
				anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
			}

			// comprobamos si el documento contiene firma
			if (documento.getDatosFirma() != null) {
				// si es asi, obtenemos el identificador del documento que
				// estamos trantando para
				// que el SIR relacione con el documento original
				anexo.setCodigoFichero(documento.getDatosFirma().getId()
						.toString());
			}


			if (documento.getDatosFirma() != null
					&& documento.getDatosFirma().getFirma() != null) {
				try {
					//asignamos el certificado
					anexo.setCertificado(documento
							.getDatosFirma().getCertificado().getBytes());

					// identificador del documento que se ha firmado
					anexo.setCodigoFicheroFirmado(documento.getDatosFirma()
							.getIdAttachmentFirmado().toString());

					if (documento.getDatosFirma().getSelloTiempo() != null) {
						anexo.setTimestamp(documento
								.getDatosFirma().getSelloTiempo().getBytes());
					}
					if (documento.getDatosFirma().getOcspValidation() != null) {
						anexo.setValidacionOCSPCertificado((documento.getDatosFirma()
										.getOcspValidation().getBytes()));
					}
				} catch (Exception e) {
					throw new IntercambioRegistralException(
							"Error al leer los ficheros electrónicos del registro.",
							IntercambioRegistralExceptionCodes.ERROR_CODE_LEER_ADJUNTOS);
				}
			}

			anexos.add(anexo);
		}
		asientoRegistral.setAnexos(anexos);
		validateAnexos(anexos);

	}

	private void validateAnexos(List<AnexoFormVO> anexos) {
		ListIterator<AnexoFormVO> itr = anexos.listIterator();
		Long maxSize = IntercambioRegistralConfiguration.getInstance()
				.getFileMaxSize();
		Integer maxFiles = IntercambioRegistralConfiguration.getInstance()
				.getFileMaxNum();
		if (anexos.size() > maxFiles) {
			throw new IntercambioRegistralException(
					"No se permiten enviar más de " + maxFiles + " ficheros",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MAX_NUM_FICHEROS);
		}
		while (itr.hasNext()) {
			AnexoFormVO anexo = itr.next();
			long size = anexo.getContenido().length / 1024;
			if (size > maxSize) {
				throw new IntercambioRegistralException(
						"Los ficheros no pueden superar los " + maxSize
								+ " Bytes",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MAX_SIZE);
			}
		}

	}

	private boolean setInteresados(AsientoRegistralFormVO asientoRegistral,
			InfoRegistroVO infoRegistro) {
		List<InteresadoFormVO> listaInteresados = new ArrayList<InteresadoFormVO>();
		boolean tieneTercerosNoValidados = false;
		boolean tieneInteresados = false;
		// Seteamos solicitantes
		for (InfoRegistroInteresadoVO interesado : infoRegistro
				.getInteresados()) {
			InteresadoFormVO interesadoIntercambioRegistral = new InteresadoFormVO();
			InfoRegistroPersonaFisicaOJuridicaVO persona = interesado
					.getInfoPersona();
			if (persona == null) {
				tieneTercerosNoValidados = true;

			} else if (StringUtils.isEmpty(persona.getPrimerApellido())) {
				// Persona jurídica
				interesadoIntercambioRegistral.setRazonSocialInteresado(persona
						.getNombre());
				interesadoIntercambioRegistral
						.setDocumentoIdentificacionInteresado(persona
								.getDocumentoIdentificacion());

				interesadoIntercambioRegistral
						.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum
								.getTipoDocumentoIdentificacion(persona
										.getTipoDocumento()));
			} else {
				// Persona física
				interesadoIntercambioRegistral.setNombreInteresado(persona
						.getNombre());
				interesadoIntercambioRegistral
						.setPrimerApellidoInteresado(persona
								.getPrimerApellido());
				interesadoIntercambioRegistral
						.setSegundoApellidoInteresado(persona
								.getSegundoApellido());
				interesadoIntercambioRegistral
						.setDocumentoIdentificacionInteresado(persona
								.getDocumentoIdentificacion());
				interesadoIntercambioRegistral
						.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum
								.getTipoDocumentoIdentificacion(persona
										.getTipoDocumento()));
			}

			if (interesado.getDireccion() != null) {
				InfoRegistroDireccionVO direccionInteresado = interesado
						.getDireccion();
				if (direccionInteresado.getDireccionTelematicaInteresado() != null) {
					interesadoIntercambioRegistral
							.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA);
					if (direccionInteresado.getDireccionTelematicaInteresado()
							.getTipo().intValue() == TIPO_DIR_TELEMATICA_TLF_FIJO
							|| direccionInteresado
									.getDireccionTelematicaInteresado()
									.getTipo().intValue() == TIPO_DIR_TELEMATICA_TLF_MOVIL) {
						interesadoIntercambioRegistral
								.setTelefonoInteresado(direccionInteresado
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionInteresado
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_EMAIL) {
						interesadoIntercambioRegistral
								.setCorreoElectronicoInteresado(direccionInteresado
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionInteresado
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_DEU) {
						interesadoIntercambioRegistral
								.setDireccionElectronicaHabilitadaInteresado(direccionInteresado
										.getDireccionTelematicaInteresado()
										.getDireccion());
						interesadoIntercambioRegistral
								.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
					}

				} else {
					interesadoIntercambioRegistral
							.setCodigoMunicipioInteresado(direccionInteresado
									.getDomicilioInteresado().getCiudad());
					interesadoIntercambioRegistral
							.setDireccionInteresado(direccionInteresado
									.getDomicilioInteresado().getDireccion());
					interesadoIntercambioRegistral
							.setCodigoPostalInteresado(direccionInteresado
									.getDomicilioInteresado().getCodigoPostal());
					interesadoIntercambioRegistral
							.setCodigoProvinciaInteresado(direccionInteresado
									.getDomicilioInteresado().getProvincia());
					interesadoIntercambioRegistral
							.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);

					interesadoIntercambioRegistral
							.setCodigoPaisInteresado("0724");
				}
			}

			//seteamos la información del representante
			setInfoRepresentante(interesado, interesadoIntercambioRegistral);

			tieneInteresados = true;
			listaInteresados.add(interesadoIntercambioRegistral);
		}
		asientoRegistral.setInteresados(listaInteresados);
		return !tieneTercerosNoValidados && tieneInteresados;
	}

	private void setInfoRepresentante(InfoRegistroInteresadoVO interesado,
			InteresadoFormVO interesadoIntercambioRegistral) {

		InfoRegistroPersonaFisicaOJuridicaVO representante = interesado.getInfoRepresentante();
		if(representante !=null){
			if (StringUtils.isEmpty(representante.getPrimerApellido())) {
				//Persona juridica
				interesadoIntercambioRegistral.setRazonSocialRepresentante(representante
						.getNombre());
				interesadoIntercambioRegistral
						.setDocumentoIdentificacionRepresentante(representante
								.getDocumentoIdentificacion());

				interesadoIntercambioRegistral
						.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum
								.getTipoDocumentoIdentificacion(representante
										.getTipoDocumento()));
			}else{
				//Persona fisica
				interesadoIntercambioRegistral.setNombreRepresentante(representante
						.getNombre());
				interesadoIntercambioRegistral
						.setPrimerApellidoRepresentante(representante
								.getPrimerApellido());
				interesadoIntercambioRegistral
						.setSegundoApellidoRepresentante(representante
								.getSegundoApellido());
				interesadoIntercambioRegistral
						.setDocumentoIdentificacionRepresentante(representante
								.getDocumentoIdentificacion());
				interesadoIntercambioRegistral
						.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum
								.getTipoDocumentoIdentificacion(representante
										.getTipoDocumento()));
			}

			//Dirección Representante
			if (interesado.getDireccionRepresentante() != null) {
				InfoRegistroDireccionVO direccionRepresentante = interesado
						.getDireccionRepresentante();
				if (direccionRepresentante.getDireccionTelematicaInteresado() != null) {
					interesadoIntercambioRegistral
							.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA);
					if (direccionRepresentante.getDireccionTelematicaInteresado()
							.getTipo().intValue() == TIPO_DIR_TELEMATICA_TLF_FIJO
							|| direccionRepresentante
									.getDireccionTelematicaInteresado()
									.getTipo().intValue() == TIPO_DIR_TELEMATICA_TLF_MOVIL) {
						interesadoIntercambioRegistral
								.setTelefonoRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_EMAIL) {
						interesadoIntercambioRegistral
								.setCorreoElectronicoRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_DEU) {
						interesadoIntercambioRegistral
								.setDireccionElectronicaHabilitadaRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
						interesadoIntercambioRegistral
								.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
					}

				} else {
					interesadoIntercambioRegistral
							.setCodigoMunicipioRepresentante(direccionRepresentante
									.getDomicilioInteresado().getCiudad());
					interesadoIntercambioRegistral
							.setDireccionRepresentante(direccionRepresentante
									.getDomicilioInteresado().getDireccion());
					interesadoIntercambioRegistral
							.setCodigoPostalRepresentante(direccionRepresentante
									.getDomicilioInteresado().getCodigoPostal());
					interesadoIntercambioRegistral
							.setCodigoProvinciaRepresentante(direccionRepresentante
									.getDomicilioInteresado().getProvincia());
					interesadoIntercambioRegistral
							.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL);

					interesadoIntercambioRegistral
							.setCodigoPaisRepresentante("0724");
				}
			}

		}
	}

	private void setCamposExtendidos(AsientoRegistralFormVO asientoRegistral,
			InfoRegistroVO infoRegistro) {
		// Campos extendidos
		if (!CollectionUtils.isEmpty(infoRegistro.getCamposExtendidos())) {
			ListIterator<InfoRegistroCamposExtendidosVO> camposExtendisoItr = infoRegistro
					.getCamposExtendidos().listIterator();
			Integer fldid;
			while (camposExtendisoItr.hasNext()) {

				InfoRegistroCamposExtendidosVO campoExtendido = camposExtendisoItr
						.next();
				fldid = campoExtendido.getFldid();
				if (fldid != null
						&& InfoRegistroCamposExtendidosVO.COMENTARIO_ID == fldid
								.intValue()) {
					asientoRegistral.setObservacionesApunte(campoExtendido
							.getValue());
				} else if (fldid != null
						&& InfoRegistroCamposExtendidosVO.SOLICITA_ID == fldid
								.intValue()) {
					asientoRegistral.setSolicita(campoExtendido.getValue());
				} else if (fldid != null
						&& InfoRegistroCamposExtendidosVO.EXPONE_ID == fldid
								.intValue()) {
					asientoRegistral.setExpone(campoExtendido.getValue());
				}
			}
		}

	}

	private DocumentacionFisicaEnum getDocumentacionFisica(
			InfoRegistroVO infoRegistro) {
		Integer trueValue = new Integer("1");
		Integer falseValue = new Integer("0");
		if (infoRegistro.getDocFisicaComplementaria() != null
				&& BooleanUtils.toBoolean(
						infoRegistro.getDocFisicaComplementaria(), trueValue,
						falseValue)) {
			return DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA;
		} else if (infoRegistro.getDocFisicaRequerida() != null
				&& BooleanUtils.toBoolean(infoRegistro.getDocFisicaRequerida(),
						trueValue, falseValue)) {
			return DocumentacionFisicaEnum.DOCUMENTACION_FISICA_REQUERIDA;
		} else {
			return DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA;
		}

	}

}
