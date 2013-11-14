package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.ieci.tecdoc.common.keys.IDocKeys;

import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.util.mime.MimeUtil;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfiguration;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfigurationKeys;
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

	/**
	 *
	 */
	private static final int DIRECCION_INTERESADOS_IR_MAX_VALUE = 160;

	private static final Logger logger = Logger
			.getLogger(AsientoRegistralMapper.class);

	private static final String CODE_TIPO_DOCUMENTO_OTROS = "X";

	private static final int TIPO_DIR_TELEMATICA_TLF_FIJO = 1;
	private static final int TIPO_DIR_TELEMATICA_TLF_MOVIL = 5;
	private static final int TIPO_DIR_TELEMATICA_EMAIL = 2;
	private static final int TIPO_DIR_TELEMATICA_FAX = 3;
	private static final int TIPO_DIR_TELEMATICA_DEU = 4;
	private static final int TIPO_DIR_TELEMATICA_COMPARECENCIA_ELECTRONICA = 6;

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

		/* Aqui se setean los datos del tipo transporte.
		 *
		 * Si el tipo de transporte no está mapeado en el SIR entonces se establece como  comentario.
		 *
		 */
		if (StringUtils.isNotEmpty(infoRegistro.getCodigoTransporteSIR())){
			TipoTransporteEnum tipoTransporte = TipoTransporteEnum.getTipoTransporte(infoRegistro.getCodigoTransporteSIR());
			asientoRegistral.setTipoTransporte(tipoTransporte);
		} else {
			if (StringUtils.isNotEmpty(infoRegistro.getTipoTransporte())){
				StringBuffer comentarioRegistro = new StringBuffer();
				if(StringUtils.isNotEmpty(asientoRegistral.getObservacionesApunte())){
					// Obtenemos la información del campo comentario para añadir la información del transporte
					comentarioRegistro.append(asientoRegistral.getObservacionesApunte()).append("\n");
				}
				comentarioRegistro.append("Tipo de transporte: "+infoRegistro.getTipoTransporte());
				asientoRegistral.setObservacionesApunte(comentarioRegistro.toString());
			}
		}

		//Numero de transporte
		asientoRegistral.setNumeroTransporte(infoRegistro.getNumeroTransporte());

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

		//Seteamos el tipo de registro
		asientoRegistral.setTipoRegistro(infoRegistro.getTipoRegistro());

		// Si no tiene interesados validados realizamos las siguientes validaciones
		if (!setInteresados(asientoRegistral, infoRegistro)) {
			logger.info("El registro a enviar NO tiene terceros, o no son terceros validados");
			// se valida si el registro es de ENTRADA tiene que tener minimo un
			// interedado, en cambio si es de SALIDA, puede que no tenga
			// interesados pero se comprueba que la unid. origen este mapeada
			// contra el DCO.
			validateInteresadosYUnidTramitacionOrigen(asientoRegistral);
		} else {
			//validamos los interesados en caso de ser un registro de tipo Salida
			validateInteresadosRegistroSalida(asientoRegistral);
		}

		// ANEXOS
		setAnexos(asientoRegistral, infoRegistro);

		asientoRegistral
				.setDocumentacionFisica(getDocumentacionFisica(infoRegistro));
		validarAsientoRegistral(asientoRegistral);

		return asientoRegistral;
	}

	/**
	 * Método que valida los interesados en caso ser un registro de tipo Salida,
	 * y no poseer una unid. de tramitación de origen
	 *
	 * @param asientoRegistral
	 *            - informacion del registro
	 */
	private void validateInteresadosRegistroSalida(
			AsientoRegistralFormVO asientoRegistral) {
		if (asientoRegistral.getTipoRegistro() == TipoRegistroEnum.SALIDA
				&& StringUtils.isEmpty(asientoRegistral
						.getCodigoUnidadTramitacionOrigen())) {
			//se verifica que alguno de los interesados tenga como identificación "Código de Origen"
			if(!validateInteresadoTipoCodigoOrigen(asientoRegistral
					.getInteresados())){
				throw new IntercambioRegistralException(
						"El registro no tiene ni interesados válidos ni una Unidad Administrativa de Origen mapeada en el DCO.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_U_ORIGEN);
			}
		}
	}

	/**
	 * Método que comprueba si alguno de los interesados tiene como tipo de
	 * identificación "Código de origen"
	 *
	 * @param listaInteresados
	 *            - Listado de interesados del registro
	 * @return boolean TRUE: alguno de los interesados tiene como tipo de
	 *         identificación "Código de origen"/ FALSE: ninguno de los
	 *         interesados posee la identificacion "Código de origen"
	 */
	private boolean validateInteresadoTipoCodigoOrigen(
			List<InteresadoFormVO> listaInteresados) {
		boolean result = false;

		//recorremos los interesados
		for (Iterator<InteresadoFormVO> it = listaInteresados.iterator(); it.hasNext();) {
			InteresadoFormVO interesado = (InteresadoFormVO) it.next();
			//verificamos si el tipo de identificacion corresponde con codigo de origen
			if (interesado.getTipoDocumentoIdentificacionInteresado() == TipoDocumentoIdentificacionEnum.CODIGO_ORIGEN) {
				//retornamos true y salimos del bucle
				return true;
			}
		}

		return result;
	}

	/**
	 * se valida si el registro es de ENTRADA tiene que tener minimo un
	 * interedado, en cambio si es de SALIDA, puede que no tenga interesados
	 * pero se comprueba que la unid. origen este mapeada contra el DCO.
	 *
	 * @param asientoRegistral - Información del registro
	 */
	private void validateInteresadosYUnidTramitacionOrigen(
			AsientoRegistralFormVO asientoRegistral) {
		//si el registro es de entrada
		if (asientoRegistral.getTipoRegistro() == TipoRegistroEnum.ENTRADA) {
			throw new IntercambioRegistralException(
					"El registro debe tener al menos un interesados validados.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MINIMOS_INTERESADOS);
		} else {
			//si el registro es de SALIDA y la unid. de Origen no esta mapeada contra el DCO
			if (StringUtils.isEmpty(asientoRegistral
							.getCodigoUnidadTramitacionOrigen())) {
				throw new IntercambioRegistralException(
						"El registro no tiene ni interesados validados ni una Unidad Administrativa de Origen mapeada en el DCO.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_U_ORIGEN);
			}
			logger.info("El registro se envía igualmente porque tiene mapeada la UT="
					+ asientoRegistral.getCodigoUnidadTramitacionOrigen());
		}
	}

	/**
	 *
	 * Valida los campos del asiento registral
	 *
	 * @param asientoRegistral
	 */
	private void validarAsientoRegistral(AsientoRegistralFormVO asientoRegistral) {

		// 1.- Validar que el campo observaciones no sea superior de 50 caracteres
		validarObservaciones(asientoRegistral);

		// 2.- Validar que la dirección de los interesados no sea superior a 160 caracteres
		validarInteresados(asientoRegistral);

		// 3.- Validar que el campo EXPONE no es superior a 4000 caraceres
		validarCampoExpone(asientoRegistral);

		// 4.- Validar que el campo SOLICITA no es superior a 4000 caraceres
		validarCampoSolicita(asientoRegistral);


	}

	/**
	 * Valida el campo observaciones
	 * @param asientoRegistral
	 */
	private void validarObservaciones(AsientoRegistralFormVO asientoRegistral) {
		String observaciones = asientoRegistral.getObservacionesApunte();
		if (StringUtils.isNotEmpty(observaciones)
				&& observaciones.length() > IDocKeys.FIELD_COMENTARIOS_SIZE_IR_ENABLED) {
			throw new IntercambioRegistralException(
					"La longitud del campo Comentarios debe de ser menor de "
							+ IDocKeys.FIELD_COMENTARIOS_SIZE_IR_ENABLED
							+ " caracteres.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_LENGTH_COMENTARIOS);
		}
	}

	/**
	 * Valida los interesados
	 *
	 * @param asientoRegistral
	 */
	private void validarInteresados(AsientoRegistralFormVO asientoRegistral) {
		List<InteresadoFormVO> interesados = asientoRegistral.getInteresados();
		for (InteresadoFormVO interesadoFormVO : interesados) {
			if (interesadoFormVO.getDireccionInteresado() != null && interesadoFormVO.getDireccionInteresado().length()>DIRECCION_INTERESADOS_IR_MAX_VALUE){
				throw new IntercambioRegistralException(
						"La longitud de la direccion del interesado debe de ser menor de "
								+ IDocKeys.DIRECCION_INTERESADOS_IR_MAX_LENGTH
								+ " caracteres.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_DIRECCION_LENGTH);
			}
			if (interesadoFormVO.getDireccionRepresentante() != null && interesadoFormVO.getDireccionRepresentante().length()>160){
				throw new IntercambioRegistralException(
						"La longitud de la direccion del interesado debe de ser menor de "
								+ IDocKeys.DIRECCION_INTERESADOS_IR_MAX_LENGTH
								+ " caracteres.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_DIRECCION_LENGTH);
			}
		}
	}

	/**
	 * Valida el campo solicita
	 *
	 * @param asientoRegistral
	 */
	private void validarCampoSolicita(AsientoRegistralFormVO asientoRegistral) {
		if (StringUtils.isNotEmpty(asientoRegistral.getSolicita()) && asientoRegistral.getSolicita().length()>IDocKeys.FIELD_SOLICITA_SIZE_IR_ENABLED){
			throw new IntercambioRegistralException(
					"La longitud del campo Solicita debe de ser menor de "
							+ IDocKeys.FIELD_SOLICITA_SIZE_IR_ENABLED
							+ " caracteres.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_SOLICITA_LENGTH);
		}
	}

	/**
	 *
	 * Valida el campo Expone
	 *
	 * @param asientoRegistral
	 */
	private void validarCampoExpone(AsientoRegistralFormVO asientoRegistral) {
		if (StringUtils.isNotEmpty(asientoRegistral.getExpone()) && asientoRegistral.getExpone().length()>IDocKeys.FIELD_EXPONE_SIZE_IR_ENABLED){
			throw new IntercambioRegistralException(
					"La longitud del campo Expone debe de ser menor de "
							+ IDocKeys.FIELD_EXPONE_SIZE_IR_ENABLED
							+ " caracteres.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_EXPONE_LENGTH);
		}
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

			//validamos si la extensión del fichero es correcta para el intercambio
			validateExtensionFileByIntercambioReg(documento.getInfoDocumento()
					.getExtension());

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
				anexo.setCodigoFichero(documento.getDatosFirma().getIdAttachment()
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

	/**
	 * Método que comprueba si la extensión del fichero es válida para el
	 * intercambio registral. Las extensiones validas se obtienen del fichero
	 * intercambioRegistral.properties
	 *
	 * @param extension
	 */
	private void validateExtensionFileByIntercambioReg(String extension) {

		//obtenemos si la extensión es valida
		boolean isValidExtension = isValidExtension(extension);

		// si la extensión no es valida se eleva un error
		if(!isValidExtension){
			StringBuffer sb = new StringBuffer();
			sb.append("La extensión del fichero no es válida [")
					.append(extension).append("] para realizar el intercambio registral");

			throw new IntercambioRegistralException(
					sb.toString(),
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_EXTENSION_FILES);
		}
	}

	/**
	 * Método que indica si la extensión del fichero es válida según lo indicado en la configuración del IR
	 *
	 * @param extension
	 * @return boolean true: es válida, false: NO es válida
	 */
	private boolean isValidExtension(String extension) {
		// Obtenemos las extensiones validas para los intercambios registrales
		List<String> extensionesValidas = IntercambioRegistralConfiguration
				.getInstance().getExtensiones();
		//recorremos las extensiones validas
		for(Iterator<String> it = extensionesValidas.iterator(); it.hasNext();){
			String extensionValida = (String) it.next();
			//comprobamos si la extension del fichero es válida
			if(extensionValida.equalsIgnoreCase(extension)){
				//es correcta la extensión
				return true;
			}
		}
		return false;
	}

	/**
	 * Método que valida los anexos: tamaño del anexo y número total de anexos
	 *
	 * @param anexos
	 *            - Ficheros adjuntados
	 *
	 */
	private void validateAnexos(List<AnexoFormVO> anexos) {
		ListIterator<AnexoFormVO> itr = anexos.listIterator();
		// Obtenemos los criterios de configuración del fichero
		// intercambioRegistral.properties

		// Tamaño máximo por fichero
		Long maxSize = IntercambioRegistralConfiguration.getInstance()
				.getFileMaxSize();

		// Tamaño máiximo para el conjunto de ficheros
		Long maxSizeSetFiles = IntercambioRegistralConfiguration.getInstance()
				.getFilesSetMaxSize();

		// Número máximo de ficheros permitidos
		Integer maxFiles = IntercambioRegistralConfiguration.getInstance()
				.getFileMaxNum();

		// obtenemos el número total de ficheros a tener en cuenta para realizar
		// el IR
		int numAnexos = countAnexosIR(anexos);
		// Se valida el número máximo de ficheros para el intercambio registral
		if (numAnexos > maxFiles) {
			throw new IntercambioRegistralException(
					"No se permiten enviar más de " + maxFiles + " ficheros",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MAX_NUM_FICHEROS);
		}

		// obtenemos el tamaño total del conjunto de ficheros, además de ir
		// validando el tamaño de cada uno de los ficheros
		long sizeTotalFiles = getTotalSizeFilesAndValidateSizeFile(itr, maxSize);

		// comprobamos si el tamaño del conjunto de ficheros excede lo indicado
		// por configuracion
		if (sizeTotalFiles > maxSizeSetFiles) {
			throw new IntercambioRegistralException(
					"El conjunto de ficheros no puede superar los "
							+ maxSizeSetFiles + " Bytes",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MAX_SIZE_SET_FILES);
		}
	}

	/**
	 * Obtenemos el tamaño total del conjunto de ficheros, además se valida el
	 * tamaño de cada uno de los ficheros
	 *
	 * @param itr
	 *            - Listado de ficheros
	 * @param maxSize
	 *            - Tamaño máximo por fichero
	 * @return tamaño total del conjunto de ficheros
	 */
	private long getTotalSizeFilesAndValidateSizeFile(
			ListIterator<AnexoFormVO> itr, Long maxSize) {
		long sizeTotalFiles = 0;

		// Validamos el tamaño de los ficheros
		while (itr.hasNext()) {
			AnexoFormVO anexo = itr.next();
			// Se valida el tamaño máximo permitido por fichero
			long size = anexo.getContenido().length / 1024;

			// añadimos a la variable global de tamaño de ficheros el tamaño del
			// fichero si no es un fichero interno
			if (anexo.getTipoDocumento() != TipoDocumentoEnum.FICHERO_TECNICO_INTERNO) {
				sizeTotalFiles += size;
			}
			if (size > maxSize) {
				throw new IntercambioRegistralException(
						"Los ficheros no pueden superar los " + maxSize
								+ " Bytes",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_MAX_SIZE);
			}
		}
		return sizeTotalFiles;
	}

	/**
	 * Método que obtiene el número total de ficheros adjuntados para el IR
	 * @param anexos
	 * @return int - número total de ficheros a tener en cuenta en el IR
	 */
	public int countAnexosIR(List<AnexoFormVO> anexos){
		int result = 0;

		AnexoFormVO anexo = null;
		//recorremos el listado de ficheros
		for(Iterator<AnexoFormVO> it= anexos.iterator(); it.hasNext();){
			anexo = (AnexoFormVO) it.next();
			// si el documento es diferente a un fichero técnico interno se
			// contabiliza (los ficheros técnicos internos no se contabilizan
			// para realizar el IR)
			if(anexo.getTipoDocumento() != TipoDocumentoEnum.FICHERO_TECNICO_INTERNO){
				result++;
			}
		}

		return result;
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

				// validamos si el tipo de documentos es "Otros" no se envían
				// los datos
				if (!CODE_TIPO_DOCUMENTO_OTROS.equalsIgnoreCase(persona
						.getTipoDocumento())) {
					interesadoIntercambioRegistral
							.setDocumentoIdentificacionInteresado(persona
									.getDocumentoIdentificacion());

					interesadoIntercambioRegistral
							.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum
									.getTipoDocumentoIdentificacion(persona
											.getTipoDocumento()));
				}
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

					String canalNotificacion = null;
					if (direccionInteresado.getDireccionTelematicaInteresado()
							.getTipo().intValue() == TIPO_DIR_TELEMATICA_TLF_FIJO) {

						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_TLF_FIJO);
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);


						interesadoIntercambioRegistral
								.setTelefonoInteresado(direccionInteresado
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionInteresado
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_TLF_MOVIL) {
						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_TLF_MOVIL);
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

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
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_EMAIL);

					} else if (direccionInteresado
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_DEU) {
						interesadoIntercambioRegistral
								.setDireccionElectronicaHabilitadaInteresado(direccionInteresado
										.getDireccionTelematicaInteresado()
										.getDireccion());

						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_DEU);
						validateCanalNotificacionDireccionElectronicaHabilitada(canalNotificacion);
					} else if (direccionInteresado
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_COMPARECENCIA_ELECTRONICA) {
						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_COMPARECENCIA_ELECTRONICA);
					}

					if (StringUtils.isNotBlank(canalNotificacion)) {
						interesadoIntercambioRegistral
								.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum
										.getCanalNotificacion(canalNotificacion));
					}

				} else {
					if (direccionInteresado.getDomicilioInteresado() != null){
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
								.setCodigoPaisInteresado(direccionInteresado.getDomicilioInteresado().getPais());

						// Si los códigos están vacíos, concatenamos el nombre de provincia y ciudad a la dirección
						if (StringUtils.isEmpty(interesadoIntercambioRegistral.getCodigoProvinciaInteresado())){
							if (StringUtils.isNotBlank(direccionInteresado.getDomicilioInteresado().getNombreProvincia())){
								interesadoIntercambioRegistral.setDireccionInteresado(interesadoIntercambioRegistral.getDireccionInteresado()+", "+ direccionInteresado.getDomicilioInteresado().getNombreProvincia());
							}
						}

						if (StringUtils.isEmpty(interesadoIntercambioRegistral.getCodigoMunicipioInteresado())){
							if (StringUtils.isNotBlank(direccionInteresado.getDomicilioInteresado().getNombreCiudad())){
								interesadoIntercambioRegistral.setDireccionInteresado(interesadoIntercambioRegistral.getDireccionInteresado()+", "+ direccionInteresado.getDomicilioInteresado().getNombreCiudad());
							}
						}

						interesadoIntercambioRegistral
								.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);


					}
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


	private void validateCanalNotificacionDireccionElectronicaHabilitada(String canalNotificacion){
		if(StringUtils.isNotBlank(canalNotificacion)){
			if(CanalNotificacionEnum
					.getCanalNotificacion(canalNotificacion) == CanalNotificacionEnum.DIRECCION_POSTAL){
				throw new IntercambioRegistralException(
						"Error el canal de notificación configurado no es correcto para el tipo de dirección elegida.",
						IntercambioRegistralExceptionCodes.ERROR_VALIDACION_CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS);
			}
		}
	}

	/**
	 * Método que comprueba que el canal de notificacion indicado se corresponda con "Comparecencia electrónica"
	 * @param canalNotificacion
	 */
	private void validateCanalNotificacionComparecenciaElectronica(String canalNotificacion){
		if(StringUtils.isNotBlank(canalNotificacion)){
			if(CanalNotificacionEnum
					.getCanalNotificacion(canalNotificacion) != CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA){
				throw new IntercambioRegistralException(
						"Error el canal de notificación configurado no es correcto para el tipo de dirección elegida.",
						IntercambioRegistralExceptionCodes.ERROR_VALIDACION_CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS);
			}
		}
	}

	private void setInfoRepresentante(InfoRegistroInteresadoVO interesado,
			InteresadoFormVO interesadoIntercambioRegistral) {

		InfoRegistroPersonaFisicaOJuridicaVO representante = interesado.getInfoRepresentante();
		if(representante !=null){
			if (StringUtils.isEmpty(representante.getPrimerApellido())) {
				//Persona juridica
				interesadoIntercambioRegistral.setRazonSocialRepresentante(representante
						.getNombre());

				//validamos si el tipo de documentos es "Otros" no se envían los datos
				if (!CODE_TIPO_DOCUMENTO_OTROS.equalsIgnoreCase(representante
						.getTipoDocumento())) {
					interesadoIntercambioRegistral
							.setDocumentoIdentificacionRepresentante(representante
									.getDocumentoIdentificacion());

					interesadoIntercambioRegistral
							.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum
									.getTipoDocumentoIdentificacion(representante
											.getTipoDocumento()));
				}
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

				String canalNotificacion = null;
				if (direccionRepresentante.getDireccionTelematicaInteresado() != null) {
					if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_TLF_FIJO) {

						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_TLF_FIJO);

						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

						interesadoIntercambioRegistral
								.setTelefonoRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_TLF_MOVIL) {

						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_TLF_MOVIL);
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

						interesadoIntercambioRegistral
								.setTelefonoRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_EMAIL) {
						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_EMAIL);
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

						interesadoIntercambioRegistral
								.setCorreoElectronicoRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_DEU) {
						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_DEU);
						validateCanalNotificacionComparecenciaElectronica(canalNotificacion);

						interesadoIntercambioRegistral
								.setDireccionElectronicaHabilitadaRepresentante(direccionRepresentante
										.getDireccionTelematicaInteresado()
										.getDireccion());
					} else if (direccionRepresentante
							.getDireccionTelematicaInteresado().getTipo()
							.intValue() == TIPO_DIR_TELEMATICA_COMPARECENCIA_ELECTRONICA) {
						canalNotificacion = IntercambioRegistralConfiguration
								.getInstance()
								.getProperty(
										IntercambioRegistralConfigurationKeys.CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS
												+ TIPO_DIR_TELEMATICA_COMPARECENCIA_ELECTRONICA);
					}

					if (StringUtils.isNotBlank(canalNotificacion)) {
						interesadoIntercambioRegistral
								.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum
										.getCanalNotificacion(canalNotificacion));
					}

				} else {
					if (direccionRepresentante.getDomicilioInteresado()!=null){
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
								.setCodigoPaisRepresentante(direccionRepresentante.getDomicilioInteresado().getPais());

						// Si los códigos están vacíos, concatenamos el nombre de provincia y ciudad a la dirección
						if (StringUtils.isEmpty(interesadoIntercambioRegistral.getCodigoProvinciaRepresentante())){
							if (StringUtils.isNotBlank(direccionRepresentante.getDomicilioInteresado().getNombreProvincia())){
								interesadoIntercambioRegistral.setDireccionRepresentante(interesadoIntercambioRegistral.getDireccionRepresentante()+", "+ direccionRepresentante.getDomicilioInteresado().getNombreProvincia());
							}
						}

						if (StringUtils.isEmpty(interesadoIntercambioRegistral.getCodigoMunicipioInteresado())){
							if (StringUtils.isNotBlank(direccionRepresentante.getDomicilioInteresado().getNombreCiudad())){
								interesadoIntercambioRegistral.setDireccionRepresentante(interesadoIntercambioRegistral.getDireccionRepresentante()+", "+ direccionRepresentante.getDomicilioInteresado().getNombreCiudad());
							}
						}

						interesadoIntercambioRegistral
						.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL);
					}
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
