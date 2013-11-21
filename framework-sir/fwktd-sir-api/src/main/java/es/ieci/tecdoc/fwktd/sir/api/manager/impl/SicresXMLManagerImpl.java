package es.ieci.tecdoc.fwktd.sir.api.manager.impl;


import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathConstants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.HashManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Anexo;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Interesado;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Mensaje;
import es.ieci.tecdoc.fwktd.sir.api.schema.Fichero_Intercambio_SICRES_3;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Indicador_PruebaType;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.exception.ValidacionException;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Implementación del manager de mensajes XML conforme a la normativa SICRES
 * 3.0.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SicresXMLManagerImpl implements SicresXMLManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SicresXMLManagerImpl.class);

	private static final String VALIDACION_DIRECTORIO_COMUN_PARAM_NAME = "validar.codigos.directorio.comun";
	private static final String MAX_TAMANO_ANEXOS_PARAM_NAME = "max.tamaño.anexos";
	private static final String MAX_NUM_ANEXOS_PARAM_NAME = "max.num.anexos";
	
	private static final String CODIGO_PAIS_ESPANA = "0724";
	private static final int LONGITUD_CODIGO_ENTIDAD_REGISTRAL = 21;
	private static final int LONGITUD_CODIGO_UNIDAD_TRAMITACION = 21;
	private static final int LONGITUD_IDENTIFICADOR_INTERCAMBIO = 33;
	private static final int LONGITUD_MAX_IDENTIFICADOR_FICHERO = 50;
	
	private static final String DEFAULT_FILE_EXTENSION = "bin";
	private static final int DEFAULT_MAX_NUM_FILES = 0; // Máximo número de documentos
	private static final long DEFAULT_MAX_FILE_SIZE = 0; // Máximo tamaño por documento (3MB)

	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 
	 * Map con los valores de los campos del xml que deben estar en formato base64 junto con su expresión xpath de seleccion
	 */
	private Map<String, String> base64Fields;

	/**
	 * Gestor de anexos.
	 */
	private AnexoManager anexoManager = null;

	/**
	 * Gestor de códigos hash.
	 */
	private HashManager hashManager = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;

	/**
	 * Servicio de consulta en el directorio común.
	 */
	private ServicioConsultaDirectorioComun servicioConsultaDirectorioComun;

	/**
	 * Indicador de prueba.
	 */
	private IndicadorPruebaEnum indicadorPrueba = IndicadorPruebaEnum.NORMAL;

	/**
	 * Indica si hay que validar, por defecto, los códigos contra el directorio
	 * común.
	 */
	private boolean defaultValidacionDirectorioComun = false;
	
	/**
	 * Constructor.
	 */
	public SicresXMLManagerImpl() {
		super();
		setupBase64Field();
	}

	public AnexoManager getAnexoManager() {
		return anexoManager;
	}

	public void setAnexoManager(AnexoManager anexoManager) {
		this.anexoManager = anexoManager;
	}

	public HashManager getHashManager() {
		return hashManager;
	}

	public void setHashManager(HashManager hashManager) {
		this.hashManager = hashManager;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(
			ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	public ServicioConsultaDirectorioComun getServicioConsultaDirectorioComun() {
		return servicioConsultaDirectorioComun;
	}

	public void setServicioConsultaDirectorioComun(
			ServicioConsultaDirectorioComun servicioConsultaDirectorioComun) {
		this.servicioConsultaDirectorioComun = servicioConsultaDirectorioComun;
	}

	public IndicadorPruebaEnum getIndicadorPrueba() {
		return indicadorPrueba;
	}

	public void setIndicadorPrueba(IndicadorPruebaEnum indicadorPrueba) {
		this.indicadorPrueba = indicadorPrueba;
	}

	public boolean isDefaultValidacionDirectorioComun() {
		return defaultValidacionDirectorioComun;
	}

	public void setDefaultValidacionDirectorioComun(
			boolean defaultValidacionDirectorioComun) {
		this.defaultValidacionDirectorioComun = defaultValidacionDirectorioComun;
	}
	
	public Map<String, String> getBase64Fields() {
		return base64Fields;
	}

	public void setBase64Fields(Map<String, String> base64Fields) {
		this.base64Fields = base64Fields;
	}
	
	protected void setupBase64Field(){
		LinkedHashMap base64Fields = new LinkedHashMap<String, String>();
		base64Fields.put("Hash", "//Hash/text()");
		base64Fields.put("Timestamp_Entrada", "//Timestamp_Entrada/text()");
		base64Fields.put("Certificado", "//Certificado/text()");
		base64Fields.put("Firma_Documento", "//Firma_Documento/text()");
		base64Fields.put("TimeStamp", "//TimeStamp/text()");
		base64Fields.put("Validacion_OCSP_Certificado", "//Validacion_OCSP_Certificado/text()");
		base64Fields.put("Anexo", "//Anexo/text()");
		setBase64Fields(base64Fields);
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager#validarFicheroIntercambio(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO)
	 */
	public void validarFicheroIntercambio(
			FicheroIntercambioVO ficheroIntercambio) {

		logger.info("Llamada a validarFicheroIntercambio");
		if(logger.isDebugEnabled()){
			logger.debug("Validando el fichero de intercambio: [{}]",
					ToStringLoggerApiHelper.toStringLogger(ficheroIntercambio));
		}

		Assert.notNull(ficheroIntercambio,
				"'ficheroIntercambio' must not be null");

		/*
		 * Validar los segmentos del fichero de intercambio
		 */

		validarSegmentoOrigen(ficheroIntercambio);
		validarSegmentoDestino(ficheroIntercambio);
		validarSegmentoControl(ficheroIntercambio);
		validarSegmentoInteresados(ficheroIntercambio);
		validarSegmentoAsunto(ficheroIntercambio);
		validarSegmentoAnexos(ficheroIntercambio);
		
		validarSegmentoFormularioGenerico(ficheroIntercambio);
		
		logger.info("Fichero de intercambio validado");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager#validarAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	public void validarAsientoRegistral(AsientoRegistralVO asiento) {

		logger.info("Llamada a validarAsientoRegistral");
		if(logger.isDebugEnabled()){
			logger.debug("Validando el asiento registral: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		Assert.notNull(asiento, "'asiento' must not be null");

		// Comprobar los datos de origen
		Assert.hasText(asiento.getCodigoEntidadRegistralOrigen(),
				"'codigoEntidadRegistralOrigen' must not be empty");
		Assert.hasText(asiento.getNumeroRegistro(),
				"'numeroRegistroEntrada' must not be empty");
		Assert.notNull(asiento.getFechaRegistro(),
				"'fechaEntrada' must not be null");

		// Comprobar los datos de destino
		Assert.hasText(asiento.getCodigoEntidadRegistralDestino(),
				"'codigoEntidadRegistralDestino' must not be empty");

		// Comprobar los datos de los interesados
		if (CollectionUtils.isNotEmpty(asiento.getInteresados())
				&& StringUtils.isBlank(asiento
						.getCodigoUnidadTramitacionOrigen())) {
			for (InteresadoVO interesado : asiento.getInteresados()) {

				Assert.isTrue(
						StringUtils.isNotBlank(interesado
								.getRazonSocialInteresado())
								|| (StringUtils.isNotBlank(interesado
										.getNombreInteresado()) && StringUtils
										.isNotBlank(interesado
												.getPrimerApellidoInteresado())),
						"'razonSocialInteresado' or ('nombreInteresado' and 'primerApellidoInteresado') must not be empty");

//				Assert.isTrue(
//						(interesado.getCanalPreferenteComunicacionInteresado() != null)
//								|| (interesado
//										.getCanalPreferenteComunicacionRepresentante() != null),
//						"'canalPreferenteComunicacionInteresado' or 'canalPreferenteComunicacionRepresentante' must not be null");

				if (interesado.getCanalPreferenteComunicacionInteresado() != null) {
					if (interesado.getCanalPreferenteComunicacionInteresado()
							.equals(CanalNotificacionEnum.DIRECCION_POSTAL)) {
						Assert.hasText(interesado.getCodigoPaisInteresado(),
								"'codigoPaisInteresado' must not be empty");
						Assert.hasText(interesado.getDireccionInteresado(),
								"'direccionInteresado' must not be empty");

						if (CODIGO_PAIS_ESPANA.equals(interesado
								.getCodigoPaisInteresado())) {
							Assert.isTrue(
									StringUtils.isNotBlank(interesado
											.getCodigoPostalInteresado())
											|| (StringUtils
													.isNotBlank(interesado
															.getCodigoProvinciaInteresado()) && StringUtils
													.isNotBlank(interesado
															.getCodigoMunicipioInteresado())),
									"'codigoPostalInteresado' or ('codigoProvinciaInteresado' and 'codigoMunicipioInteresado') must not be empty");
						}

					} else if (interesado
							.getCanalPreferenteComunicacionInteresado()
							.equals(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA)) {
						Assert.hasText(interesado
								.getDireccionElectronicaHabilitadaInteresado(),
								"'direccionElectronicaHabilitadaInteresado' must not be empty");
					}
				}

				if (interesado.getCanalPreferenteComunicacionRepresentante() != null) {
					if (interesado
							.getCanalPreferenteComunicacionRepresentante()
							.equals(CanalNotificacionEnum.DIRECCION_POSTAL)) {

						Assert.hasText(interesado.getCodigoPaisRepresentante(),
								"'codigoPaisRepresentante' must not be empty");
						Assert.hasText(interesado.getDireccionRepresentante(),
								"'direccionRepresentante' must not be empty");

						if (CODIGO_PAIS_ESPANA.equals(interesado
								.getCodigoPaisRepresentante())) {
							Assert.isTrue(
									StringUtils.isNotBlank(interesado
											.getCodigoPostalRepresentante())
											|| (StringUtils
													.isNotBlank(interesado
															.getCodigoProvinciaRepresentante()) && StringUtils
													.isNotBlank(interesado
															.getCodigoMunicipioRepresentante())),
									"'codigoPostalRepresentante' or ('codigoProvinciaRepresentante' and 'codigoMunicipioRepresentante') must not be empty");
						}

					} else if (interesado
							.getCanalPreferenteComunicacionRepresentante()
							.equals(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA)) {
						Assert.hasText(
								interesado
										.getDireccionElectronicaHabilitadaRepresentante(),
								"'direccionElectronicaHabilitadaRepresentante' must not be empty");
					}
				}
			}
		}

		// Comprobar los datos de asunto
		Assert.hasText(asiento.getResumen(), "'resumen' must not be empty");

		// Comprobar los datos de los anexos
		if (CollectionUtils.isNotEmpty(asiento.getAnexos())) {
			
			int numAdjuntos = 0; // Número de adjuntos: documentos de tipo
									// "02 - Documento Adjunto" que no son
									// firmas
			
			for (AnexoVO anexo : asiento.getAnexos()) {
				
				Assert.hasText(anexo.getNombreFichero(),
						"'nombreFichero' must not be empty");
				Assert.notNull(anexo.getTipoDocumento(),
						"'tipoDocumento' must not be null");
				Assert.notNull(anexo.getHash(), "'hash' must not be null");
				
				// Si en documento es de tipo "02 - Documento Adjunto"
				if (TipoDocumentoEnum.DOCUMENTO_ADJUNTO.equals(anexo.getTipoDocumento())) {
					numAdjuntos++;
				}
				
				// Comprobar si hay que aplicar filtro de tamaño de ficheros
				long maxFileSize = getMaxFileSize();
				if (maxFileSize > 0) {
					InfoDocumentoVO infoAnexo = getAnexoManager()
							.getInfoContenidoAnexo(anexo.getId());
					Assert.isTrue((infoAnexo == null)
							|| (infoAnexo.getTamano() <= getMaxFileSize()),
							"Attachment '" + anexo.getNombreFichero()
									+ "' is too big");
				}
			}
			
			// Comprobar si hay que aplicar filtro de número de ficheros
			int maxNumFiles = getMaxNumFiles();
			if (maxNumFiles > 0) {
				Assert.isTrue(numAdjuntos <= getMaxNumFiles(), "There are too many attachments [" + numAdjuntos + "]");
			}
		}

		// Comprobar los datos de internos o de control
		Assert.hasText(asiento.getIdentificadorIntercambio(),
				"'identificadorIntercambio' must not be empty");
		Assert.notNull(asiento.getTipoRegistro(),
				"'tipoRegistro' must not be null");
		Assert.notNull(asiento.getDocumentacionFisica(),
				"'documentacionFisica' must not be null");
		Assert.notNull(asiento.getIndicadorPrueba(),
				"'indicadorPrueba' must not be null");
		Assert.hasText(asiento.getCodigoEntidadRegistralInicio(),
				"'codigoEntidadRegistralInicio' must not be empty");

		logger.info("Asiento registral validado");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager#validarMensaje(es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO)
	 */
	public void validarMensaje(MensajeVO mensaje) {

		logger.info("Llamada a validarMensaje");
		if(logger.isDebugEnabled()){
			logger.debug("Validando el mensaje: [{}]", ToStringLoggerApiHelper.toStringLogger(mensaje));
		}

		Assert.notNull(mensaje, "'mensaje' must not be null");

		Assert.hasText(mensaje.getCodigoEntidadRegistralOrigen(),
				"'codigoEntidadRegistralOrigen' must not be empty");
		Assert.hasText(mensaje.getCodigoEntidadRegistralDestino(),
				"'codigoEntidadRegistralDestino' must not be empty");
		Assert.hasText(mensaje.getIdentificadorIntercambio(),
				"'identificadorIntercambio' must not be empty");
		Assert.notNull(mensaje.getTipoMensaje(),
				"'tipoMensaje' must not be null");

		logger.info("Mensaje validado");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager#createXMLFicheroIntercambio(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO,
	 *      boolean)
	 */
	public String createXMLFicheroIntercambio(AsientoRegistralVO asiento,
			boolean docsAttached) {

		Assert.notNull(asiento, "'asiento' must not be null");

		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");

		// Fichero_Intercambio_SICRES_3
		Element rootNode = doc.addElement("Fichero_Intercambio_SICRES_3");

		// De_Origen_o_Remitente
		addDatosOrigen(rootNode, asiento);

		// De_Destino
		addDatosDestino(rootNode, asiento);

		// De_Interesado
		addDatosInteresados(rootNode, asiento.getInteresados());

		// De_Asunto
		addDatosAsunto(rootNode, asiento);

		// De_Anexo
		addDatosAnexos(rootNode, asiento, docsAttached);

		// De_Internos_Control
		addDatosControl(rootNode, asiento);

		// De_Formulario_Generico
		addDatosFormularioGenerico(rootNode, asiento);

		return doc.asXML();
	}

	private static void addDatosOrigen(Element rootNode,
			AsientoRegistralVO asiento) {

		// De_Origen_o_Remitente
		Element rootElement = rootNode.addElement("De_Origen_o_Remitente");
		Element elem = null;

		// Codigo_Entidad_Registral_Origen
		if (StringUtils.isNotBlank(asiento.getCodigoEntidadRegistralOrigen())) {
			elem = rootElement.addElement("Codigo_Entidad_Registral_Origen");
			elem.addCDATA(asiento.getCodigoEntidadRegistralOrigen());
		}

		// Decodificacion_Entidad_Registral_Origen
		if (StringUtils.isNotBlank(asiento
				.getDescripcionEntidadRegistralOrigen())) {
			elem = rootElement
					.addElement("Decodificacion_Entidad_Registral_Origen");
			elem.addCDATA(asiento.getDescripcionEntidadRegistralOrigen());
		}

		// Numero_Registro_Entrada
		if (StringUtils.isNotBlank(asiento.getNumeroRegistro())) {
			elem = rootElement.addElement("Numero_Registro_Entrada");
			elem.addCDATA(asiento.getNumeroRegistro());
		}

		// Fecha_Hora_Entrada
		if (asiento.getFechaRegistro() != null) {
			elem = rootElement.addElement("Fecha_Hora_Entrada");
			elem.addCDATA(SDF.format(asiento.getFechaRegistro()));
		}

		// Timestamp_Entrada
		if (asiento.getTimestampRegistro() != null) {
			elem = rootElement.addElement("Timestamp_Entrada");
			
			elem.addCDATA(getBase64Sring(asiento.getTimestampRegistro()));
		}

		// Codigo_Unidad_Tramitacion_Origen
		if (StringUtils.isNotBlank(asiento.getCodigoUnidadTramitacionOrigen())) {
			elem = rootElement.addElement("Codigo_Unidad_Tramitacion_Origen");
			elem.addCDATA(asiento.getCodigoUnidadTramitacionOrigen());
		}

		// Decodificacion_Unidad_Tramitacion_Origen
		if (asiento.getDescripcionUnidadTramitacionOrigen() != null) {
			elem = rootElement
					.addElement("Decodificacion_Unidad_Tramitacion_Origen");
			elem.addCDATA(asiento.getDescripcionUnidadTramitacionOrigen());
		}
	}

	private static void addDatosDestino(Element rootNode,
			AsientoRegistralVO asiento) {

		// De_Destino
		Element rootElement = rootNode.addElement("De_Destino");
		Element elem = null;

		// Codigo_Entidad_Registral_Destino
		if (StringUtils.isNotBlank(asiento.getCodigoEntidadRegistralDestino())) {
			elem = rootElement.addElement("Codigo_Entidad_Registral_Destino");
			elem.addCDATA(asiento.getCodigoEntidadRegistralDestino());
		}

		// Decodificacion_Entidad_Registral_Destino
		if (StringUtils.isNotBlank(asiento
				.getDescripcionEntidadRegistralDestino())) {
			elem = rootElement
					.addElement("Decodificacion_Entidad_Registral_Destino");
			elem.addCDATA(asiento.getDescripcionEntidadRegistralDestino());
		}

		// Codigo_Unidad_Tramitacion_Destino
		if (StringUtils.isNotBlank(asiento.getCodigoUnidadTramitacionDestino())) {
			elem = rootElement.addElement("Codigo_Unidad_Tramitacion_Destino");
			elem.addCDATA(asiento.getCodigoUnidadTramitacionDestino());
		}

		// Decodificacion_Unidad_Tramitacion_Destino
		if (StringUtils.isNotBlank(asiento
				.getDescripcionUnidadTramitacionDestino())) {
			elem = rootElement
					.addElement("Decodificacion_Unidad_Tramitacion_Destino");
			elem.addCDATA(asiento.getDescripcionUnidadTramitacionDestino());
		}
	}
	
	protected static String getBase64Sring (byte[] dato){
		String result=null;
		
		result=Base64.encodeBase64String(dato);
				
		return result;
	}

	private static void addDatosInteresados(Element rootNode,
			List<InteresadoVO> interesados) {

		if (CollectionUtils.isNotEmpty(interesados)) {
			for (InteresadoVO interesado : interesados) {
				if (interesado != null) {

					// De_Interesado
					Element rootElement = rootNode.addElement("De_Interesado");
					Element elem = null;

					// Tipo_Documento_Identificacion_Interesado
					if (interesado.getTipoDocumentoIdentificacionInteresado() != null) {
						elem = rootElement
								.addElement("Tipo_Documento_Identificacion_Interesado");
						elem.addCDATA(interesado
								.getTipoDocumentoIdentificacionInteresado()
								.getValue());
					}

					// Documento_Identificacion_Interesado
					if (StringUtils.isNotBlank(interesado
							.getDocumentoIdentificacionInteresado())) {
						elem = rootElement
								.addElement("Documento_Identificacion_Interesado");
						elem.addCDATA(interesado
								.getDocumentoIdentificacionInteresado());
					}

					// Razon_Social_Interesado
					if (StringUtils.isNotBlank(interesado
							.getRazonSocialInteresado())) {
						elem = rootElement
								.addElement("Razon_Social_Interesado");
						elem.addCDATA(interesado.getRazonSocialInteresado());
					}

					// Nombre_Interesado
					if (StringUtils
							.isNotBlank(interesado.getNombreInteresado())) {
						elem = rootElement.addElement("Nombre_Interesado");
						elem.addCDATA(interesado.getNombreInteresado());
					}

					// Primer_Apellido_Interesado
					if (StringUtils.isNotBlank(interesado
							.getPrimerApellidoInteresado())) {
						elem = rootElement
								.addElement("Primer_Apellido_Interesado");
						elem.addCDATA(interesado.getPrimerApellidoInteresado());
					}

					// Segundo_Apellido_Interesado
					if (StringUtils.isNotBlank(interesado
							.getSegundoApellidoInteresado())) {
						elem = rootElement
								.addElement("Segundo_Apellido_Interesado");
						elem.addCDATA(interesado.getSegundoApellidoInteresado());
					}

					// Tipo_Documento_Identificacion_Representante
					if (interesado
							.getTipoDocumentoIdentificacionRepresentante() != null) {
						elem = rootElement
								.addElement("Tipo_Documento_Identificacion_Representante");
						elem.addCDATA(interesado
								.getTipoDocumentoIdentificacionRepresentante()
								.getValue());
					}

					// Documento_Identificacion_Representante
					if (StringUtils.isNotBlank(interesado
							.getDocumentoIdentificacionRepresentante())) {
						elem = rootElement
								.addElement("Documento_Identificacion_Representante");
						elem.addCDATA(interesado
								.getDocumentoIdentificacionRepresentante());
					}

					// Razon_Social_Representante
					if (StringUtils.isNotBlank(interesado
							.getRazonSocialRepresentante())) {
						elem = rootElement
								.addElement("Razon_Social_Representante");
						elem.addCDATA(interesado.getRazonSocialRepresentante());
					}

					// Nombre_Representante
					if (StringUtils.isNotBlank(interesado
							.getNombreRepresentante())) {
						elem = rootElement.addElement("Nombre_Representante");
						elem.addCDATA(interesado.getNombreRepresentante());
					}

					// Primer_Apellido_Representante
					if (StringUtils.isNotBlank(interesado
							.getPrimerApellidoRepresentante())) {
						elem = rootElement
								.addElement("Primer_Apellido_Representante");
						elem.addCDATA(interesado
								.getPrimerApellidoRepresentante());
					}

					// Segundo_Apellido_Representante
					if (StringUtils.isNotBlank(interesado
							.getSegundoApellidoRepresentante())) {
						elem = rootElement
								.addElement("Segundo_Apellido_Representante");
						elem.addCDATA(interesado
								.getSegundoApellidoRepresentante());
					}

					// Pais_Interesado
					if (StringUtils.isNotBlank(interesado
							.getCodigoPaisInteresado())) {
						elem = rootElement.addElement("Pais_Interesado");
						elem.addCDATA(interesado.getCodigoPaisInteresado());
					}

					// Provincia_Interesado
					if (StringUtils.isNotBlank(interesado
							.getCodigoProvinciaInteresado())) {
						elem = rootElement.addElement("Provincia_Interesado");
						elem.addCDATA(interesado.getCodigoProvinciaInteresado());
					}

					// Municipio_Interesado
					if (StringUtils.isNotBlank(interesado
							.getCodigoMunicipioInteresado())) {
						elem = rootElement.addElement("Municipio_Interesado");
						elem.addCDATA(interesado.getCodigoMunicipioInteresado());
					}

					// Direccion_Interesado
					if (StringUtils.isNotBlank(interesado
							.getDireccionInteresado())) {
						elem = rootElement.addElement("Direccion_Interesado");
						elem.addCDATA(interesado.getDireccionInteresado());
					}

					// Codigo_Postal_Interesado
					if (StringUtils.isNotBlank(interesado
							.getCodigoPostalInteresado())) {
						elem = rootElement
								.addElement("Codigo_Postal_Interesado");
						elem.addCDATA(interesado.getCodigoPostalInteresado());
					}

					// Correo_Electronico_Interesado
					if (StringUtils.isNotBlank(interesado
							.getCorreoElectronicoInteresado())) {
						elem = rootElement
								.addElement("Correo_Electronico_Interesado");
						elem.addCDATA(interesado
								.getCorreoElectronicoInteresado());
					}

					// Telefono_Contacto_Interesado
					if (StringUtils.isNotBlank(interesado
							.getTelefonoInteresado())) {
						elem = rootElement
								.addElement("Telefono_Contacto_Interesado");
						elem.addCDATA(interesado.getTelefonoInteresado());
					}

					// Direccion_Electronica_Habilitada_Interesado
					if (StringUtils.isNotBlank(interesado
							.getDireccionElectronicaHabilitadaInteresado())) {
						elem = rootElement
								.addElement("Direccion_Electronica_Habilitada_Interesado");
						elem.addCDATA(interesado
								.getDireccionElectronicaHabilitadaInteresado());
					}

					// Canal_Preferente_Comunicacion_Interesado
					if (interesado.getCanalPreferenteComunicacionInteresado() != null) {
						elem = rootElement
								.addElement("Canal_Preferente_Comunicacion_Interesado");
						elem.addCDATA(interesado
								.getCanalPreferenteComunicacionInteresado()
								.getValue());
					}

					// Pais_Representante
					if (StringUtils.isNotBlank(interesado
							.getCodigoPaisRepresentante())) {
						elem = rootElement.addElement("Pais_Representante");
						elem.addCDATA(interesado.getCodigoPaisRepresentante());
					}

					// Provincia_Representante
					if (StringUtils.isNotBlank(interesado
							.getCodigoProvinciaRepresentante())) {
						elem = rootElement
								.addElement("Provincia_Representante");
						elem.addCDATA(interesado
								.getCodigoProvinciaRepresentante());
					}

					// Municipio_Representante
					if (StringUtils.isNotBlank(interesado
							.getCodigoMunicipioRepresentante())) {
						elem = rootElement
								.addElement("Municipio_Representante");
						elem.addCDATA(interesado
								.getCodigoMunicipioRepresentante());
					}

					// Direccion_Representante
					if (StringUtils.isNotBlank(interesado
							.getDireccionRepresentante())) {
						elem = rootElement
								.addElement("Direccion_Representante");
						elem.addCDATA(interesado.getDireccionRepresentante());
					}

					// Codigo_Postal_Representante
					if (StringUtils.isNotBlank(interesado
							.getCodigoPostalRepresentante())) {
						elem = rootElement
								.addElement("Codigo_Postal_Representante");
						elem.addCDATA(interesado.getCodigoPostalRepresentante());
					}

					// Correo_Electronico_Representante
					if (StringUtils.isNotBlank(interesado
							.getCorreoElectronicoRepresentante())) {
						elem = rootElement
								.addElement("Correo_Electronico_Representante");
						elem.addCDATA(interesado
								.getCorreoElectronicoRepresentante());
					}

					// Telefono_Contacto_Representante
					if (StringUtils.isNotBlank(interesado
							.getTelefonoRepresentante())) {
						elem = rootElement
								.addElement("Telefono_Contacto_Representante");
						elem.addCDATA(interesado.getTelefonoRepresentante());
					}

					// Direccion_Electronica_Habilitada_Representante
					if (StringUtils.isNotBlank(interesado
							.getDireccionElectronicaHabilitadaRepresentante())) {
						elem = rootElement
								.addElement("Direccion_Electronica_Habilitada_Representante");
						elem.addCDATA(interesado
								.getDireccionElectronicaHabilitadaRepresentante());
					}

					// Canal_Preferente_Comunicacion_Representante
					if (interesado
							.getCanalPreferenteComunicacionRepresentante() != null) {
						elem = rootElement
								.addElement("Canal_Preferente_Comunicacion_Representante");
						elem.addCDATA(interesado
								.getCanalPreferenteComunicacionRepresentante()
								.getValue());
					}

					// Observaciones
					if (StringUtils.isNotBlank(interesado.getObservaciones())) {
						elem = rootElement.addElement("Observaciones");
						elem.addCDATA(interesado.getObservaciones());
					}
				}
			}
		}else{
			// De_Interesado es elemento obligatoria su presencia aunque sea vacio y no vengan interesados
			Element rootElement = rootNode.addElement("De_Interesado");
		}
	}

	private void addDatosAsunto(Element rootNode, AsientoRegistralVO asiento) {

		// De_Asunto
		Element rootElement = rootNode.addElement("De_Asunto");
		Element elem = null;

		// Resumen
		elem = rootElement.addElement("Resumen");
		if (StringUtils.isNotBlank(asiento.getResumen())) {
			elem.addCDATA(asiento.getResumen());
		}

		// Codigo_Asunto_Segun_Destino
		if (StringUtils.isNotBlank(asiento.getCodigoAsunto())) {
			elem = rootElement.addElement("Codigo_Asunto_Segun_Destino");
			elem.addCDATA(asiento.getCodigoAsunto());
		}

		// Referencia_Externa
		if (StringUtils.isNotBlank(asiento.getReferenciaExterna())) {
			elem = rootElement.addElement("Referencia_Externa");
			elem.addCDATA(asiento.getReferenciaExterna());
		}

		// Numero_Expediente
		if (StringUtils.isNotBlank(asiento.getNumeroExpediente())) {
			elem = rootElement.addElement("Numero_Expediente");
			elem.addCDATA(asiento.getNumeroExpediente());
		}
	}

	private void addDatosAnexos(Element rootNode, AsientoRegistralVO asiento,
			boolean docsAttached) {

		List<AnexoVO> anexos = asiento.getAnexos();
		if (CollectionUtils.isNotEmpty(anexos)) {
			int secuencia = 0;

			for (AnexoVO anexo : anexos) {
				if (anexo != null) {
					
					secuencia++;

					// De_Anexo
					Element rootElement = rootNode.addElement("De_Anexo");
					Element elem = null;

					// Nombre_Fichero_Anexado
					if (StringUtils.isNotBlank(anexo.getNombreFichero())) {
						elem = rootElement.addElement("Nombre_Fichero_Anexado");
						elem.addCDATA(anexo.getNombreFichero());
					}

					// Identificador_Fichero
					String identificadorFichero = anexo.getIdentificadorFichero();
					if (StringUtils.isBlank(identificadorFichero)) {
						
						// Generar un nuevo identificador de fichero
						identificadorFichero = generateIdentificadorFichero(asiento.getIdentificadorIntercambio(), secuencia, anexo);
						
						// Actualizar el identificador de fichero del XML
						anexo.setIdentificadorFichero(identificadorFichero);
						getAnexoManager().update(anexo);
					}
										
					elem = rootElement.addElement("Identificador_Fichero");
					elem.addCDATA(identificadorFichero);

					// Validez_Documento
					if (anexo.getValidezDocumento() != null) {
						elem = rootElement.addElement("Validez_Documento");
						elem.addCDATA(anexo.getValidezDocumento().getValue());
					}

					// Tipo_Documento
					if (anexo.getTipoDocumento() != null) {
						elem = rootElement.addElement("Tipo_Documento");
						elem.addCDATA(anexo.getTipoDocumento().getValue());
					}

					// Certificado
					if (anexo.getCertificado() != null) {
						elem = rootElement.addElement("Certificado");
						elem.addCDATA(getBase64Sring(anexo
								.getCertificado()));
					}

					// Firma_Documento
					if (anexo.getFirma() != null) {
						elem = rootElement.addElement("Firma_Documento");
						elem.addCDATA(getBase64Sring(anexo
								.getFirma()));
					}

					// TimeStamp
					if (anexo.getTimestamp() != null) {
						elem = rootElement.addElement("TimeStamp");
						elem.addCDATA(getBase64Sring(anexo
								.getTimestamp()));
					}

					// Validacion_OCSP_Certificado
					if (anexo.getValidacionOCSPCertificado() != null) {
						elem = rootElement
								.addElement("Validacion_OCSP_Certificado");
						elem.addCDATA(getBase64Sring(anexo
								.getValidacionOCSPCertificado()));
					}

					// Hash
					if (anexo.getHash() != null) {
						elem = rootElement.addElement("Hash");
						elem.addCDATA(getBase64Sring(anexo
								.getHash()));
					}

					// Tipo_MIME
					if (StringUtils.isNotBlank(anexo.getTipoMIME())
							&& (StringUtils.length(anexo.getTipoMIME()) <= 20)) {
						elem = rootElement.addElement("Tipo_MIME");
						elem.addCDATA(anexo.getTipoMIME());
					}

					// Anexo
					if (docsAttached) {
						elem = rootElement.addElement("Anexo");
						elem.addCDATA(getBase64Sring(getAnexoManager()
								.getContenidoAnexo(anexo.getId())));
					}

					// Identificador_Documento_Firmado
					String identificadorDocumentoFirmado = null;
					if (StringUtils.isNotBlank(anexo
							.getIdentificadorDocumentoFirmado())) {
						identificadorDocumentoFirmado = anexo.getIdentificadorDocumentoFirmado();
					} else if (StringUtils.isNotBlank(anexo
							.getIdentificadorFicheroFirmado())) {
						
						if (anexo.getIdentificadorFicheroFirmado().equals(
								anexo.getId())) {
							identificadorDocumentoFirmado = identificadorFichero;
						} else {
							identificadorDocumentoFirmado = getIdentificadorFicheroFirmado(
									asiento.getIdentificadorIntercambio(),
									anexo.getIdentificadorFicheroFirmado(),
									asiento.getAnexos());
						}
					}
					
					if (StringUtils.isNotBlank(identificadorDocumentoFirmado)) {
						elem = rootElement
								.addElement("Identificador_Documento_Firmado");
						elem.addCDATA(identificadorDocumentoFirmado);
					}

					// Observaciones
					if (StringUtils.isNotBlank(anexo.getObservaciones())) {
						elem = rootElement.addElement("Observaciones");
						elem.addCDATA(anexo.getObservaciones());
					}
				}
			}
		}
	}

	private String getIdentificadorFicheroFirmado(
			String identificadorIntercambio, String idAnexo,
			List<AnexoVO> anexos) {

		int secuencia = 0;
		for (AnexoVO anexo : anexos) {
			secuencia ++;
			if ((anexo != null) && StringUtils.equals(idAnexo, anexo.getId())) {
				return generateIdentificadorFichero(identificadorIntercambio,secuencia,anexo);
			}
			
		}

		return "";
	}
	
	/**
	 * Metodo que genera identificador de anxso según el patron
	 * identificadorIntercambio_01_secuencia.extension
	 * donde secuencia es cadena que repesenta secuencia en formato 0001 (leftpading con 0 y máximo de 4 caracteres)
	 * donde extesion es la extension del anexo
	 * @param identificadorIntercambio
	 * @param secuencia
	 * @param anexo
	 * @return
	 */
	protected  String generateIdentificadorFichero(String identificadorIntercambio,int secuencia,AnexoVO anexo){
		
		String result = new StringBuffer()
		.append(identificadorIntercambio)
		.append("_01_")
		.append(StringUtils.leftPad(
				String.valueOf(secuencia), 4, "0"))
		.append(".").append(getExtension(anexo)).toString();
		
		return result;
	}

	private static void addDatosControl(Element rootNode,
			AsientoRegistralVO asiento) {

		// De_Internos_Control
		Element rootElement = rootNode.addElement("De_Internos_Control");
		Element elem = null;

		// Tipo_Transporte_Entrada
		if (asiento.getTipoTransporte() != null) {
			elem = rootElement.addElement("Tipo_Transporte_Entrada");
			elem.addCDATA(asiento.getTipoTransporte().getValue());
		}

		// Numero_Transporte_Entrada
		if (StringUtils.isNotBlank(asiento.getNumeroTransporte())) {
			elem = rootElement.addElement("Numero_Transporte_Entrada");
			elem.addCDATA(asiento.getNumeroTransporte());
		}

		// Nombre_Usuario
		if (StringUtils.isNotBlank(asiento.getNombreUsuario())) {
			elem = rootElement.addElement("Nombre_Usuario");
			elem.addCDATA(asiento.getNombreUsuario());
		}

		// Contacto_Usuario
		if (StringUtils.isNotBlank(asiento.getContactoUsuario())) {
			elem = rootElement.addElement("Contacto_Usuario");
			elem.addCDATA(asiento.getContactoUsuario());
		}

		// Identificador_Intercambio
		if (StringUtils.isNotBlank(asiento.getIdentificadorIntercambio())) {
			elem = rootElement.addElement("Identificador_Intercambio");
			elem.addCDATA(asiento.getIdentificadorIntercambio());
		}

		// Aplicacion_Version_Emisora
		if (StringUtils.isNotBlank(asiento.getAplicacion())) {
			elem = rootElement.addElement("Aplicacion_Version_Emisora");
			elem.addCDATA(asiento.getAplicacion());
		}

		// Tipo_Anotacion
		elem = rootElement.addElement("Tipo_Anotacion");
		elem.addCDATA(asiento.getTipoAnotacion().getValue());

		if (StringUtils.isNotBlank(asiento.getDescripcionTipoAnotacion())) {
			elem = rootElement.addElement("Descripcion_Tipo_Anotacion");
			elem.addCDATA(asiento.getDescripcionTipoAnotacion());
		}

		// Tipo_Registro
		if (asiento.getTipoRegistro() != null) {
			elem = rootElement.addElement("Tipo_Registro");
			elem.addCDATA(asiento.getTipoRegistro().getValue());
		}

		// Documentacion_Fisica
		if (asiento.getDocumentacionFisica() != null) {
			elem = rootElement.addElement("Documentacion_Fisica");
			elem.addCDATA(asiento.getDocumentacionFisica().getValue());
		}

		// Observaciones_Apunte
		if (StringUtils.isNotBlank(asiento.getObservacionesApunte())) {
			elem = rootElement.addElement("Observaciones_Apunte");
			elem.addCDATA(asiento.getObservacionesApunte());
		}

		// Indicador_Prueba
		elem = rootElement.addElement("Indicador_Prueba");
		elem.addCDATA(String.valueOf(asiento.getIndicadorPrueba().getValue()));

		// Codigo_Entidad_Registral_Inicio
		if (StringUtils.isNotBlank(asiento.getCodigoEntidadRegistralInicio())) {
			elem = rootElement.addElement("Codigo_Entidad_Registral_Inicio");
			elem.addCDATA(asiento.getCodigoEntidadRegistralInicio());
		}

		// Decodificacion_Entidad_Registral_Inicio
		if (StringUtils.isNotBlank(asiento
				.getDescripcionEntidadRegistralInicio())) {
			elem = rootElement
					.addElement("Decodificacion_Entidad_Registral_Inicio");
			elem.addCDATA(asiento.getDescripcionEntidadRegistralInicio());
		}
	}

	private static void addDatosFormularioGenerico(Element rootNode,
			AsientoRegistralVO asiento) {

		// De_Formulario_Generico
		Element rootElement = rootNode.addElement("De_Formulario_Generico");
		Element elem = null;

		// Expone
		elem = rootElement.addElement("Expone");
		if (StringUtils.isNotBlank(asiento.getExpone())) {
			elem.addCDATA(asiento.getExpone());
		}

		// Solicita
		elem = rootElement.addElement("Solicita");
		if (StringUtils.isNotBlank(asiento.getSolicita())) {
			elem.addCDATA(asiento.getSolicita());
		}
	}

	/**
	 * Devuelve un XML con el mensaje de propósito general con el objetivo de
	 * realizar tareas de avisos y gestión de flujo del intercambio, conforme a
	 * la normativa SICRES 3.0.
	 * 
	 * @param mensaje
	 *            Información del mensaje.
	 * @return XML de mensaje en formato SICRES 3.0
	 */
	public String createXMLMensaje(MensajeVO mensaje) {

		Assert.notNull(mensaje, "'mensaje' must not be null");

		StringWriter stringWriter = new StringWriter();

		try {

			De_Mensaje msg = new De_Mensaje();
			msg.setCodigo_Entidad_Registral_Origen(mensaje
					.getCodigoEntidadRegistralOrigen());
			msg.setCodigo_Entidad_Registral_Destino(mensaje
					.getCodigoEntidadRegistralDestino());
			msg.setIdentificador_Intercambio(mensaje
					.getIdentificadorIntercambio());
			msg.setDescripcion_Mensaje(mensaje.getDescripcionMensaje());

			if (mensaje.getNumeroRegistroEntradaDestino() != null) {
				msg.setNumero_Registro_Entrada_Destino(mensaje
						.getNumeroRegistroEntradaDestino());
			}

			msg.setCodigo_Error(mensaje.getCodigoError());

			// Identificadores de ficheros
			if (mensaje.getIdentificadoresFicheros() != null) {
				msg.setIdentificador_Fichero(mensaje
						.getIdentificadoresFicheros().toArray(new String[0]));
			}

			// Fecha y hora de entrada en destino
			if (mensaje.getFechaEntradaDestino() != null) {
				msg.setFecha_Hora_Entrada_Destino(SDF.format(mensaje
						.getFechaEntradaDestino()));
			}

			// Tipo de mensaje
			if (mensaje.getTipoMensaje() != null) {
				msg.setTipo_Mensaje(mensaje.getTipoMensaje().getValue());
			}

			// Indicador de prueba
			if (mensaje.getIndicadorPrueba() != null) {
				msg.setIndicador_Prueba(Indicador_PruebaType.fromValue(mensaje
						.getIndicadorPrueba().getValue()));
			}

			msg.marshal(stringWriter);

		} catch (Exception e) {
			logger.error(
					"Error al crear el XML del mensaje: [" + ToStringLoggerApiHelper.toStringLogger(mensaje) + "]", e);
			throw new SIRException("error.sir.crearXMLMensaje", null,
					e.getMessage());
		}

		return stringWriter.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager#parseXMLFicheroIntercambio(java.lang.String)
	 */
	public FicheroIntercambioVO parseXMLFicheroIntercambio(String xml) {

		FicheroIntercambioVO ficheroIntercambio = null;

		logger.info("Parseando el XML del fichero de intercambio...");
		logger.debug("XML del fichero de intercambio a parsear: {}", xml);

		try {

			Fichero_Intercambio_SICRES_3 ficheroIntercambioSICRES3 = Fichero_Intercambio_SICRES_3
					.unmarshal(new StringReader(xml));
			if (ficheroIntercambioSICRES3 != null) {
				ficheroIntercambio = new FicheroIntercambioVO();
				ficheroIntercambio
						.setFicheroIntercambio(ficheroIntercambioSICRES3);
						
				//Realizamos una validación de los campos del xml que deben estar en base64 en caso de estar presentes
				validateBase64Fields(xml);
			}

		} catch (Throwable e) {
			logger.error(
					"Error al parsear el XML del fichero de intercambio: ["
							+ xml + "]", e);
			throw new ValidacionException(ErroresEnum.ERROR_0037, e);
		}

		if (logger.isDebugEnabled()){
			logger.debug("Información obtenida: {}", ToStringLoggerApiHelper.toStringLogger(ficheroIntercambio));
		}

		return ficheroIntercambio;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.ws.manager.XMLParseManager#parseMensaje(java.lang.String)
	 */
	public MensajeVO parseXMLMensaje(String xml) {

		MensajeVO mensajeVO = null;

		logger.info("Parseando el XML del mensaje...");
		logger.debug("XML del mensaje a parsear: {}", xml);

		try {

			De_Mensaje mensaje = De_Mensaje.unmarshal(new StringReader(xml));
			if (mensaje != null) {

				mensajeVO = new MensajeVO();
				mensajeVO.setCodigoEntidadRegistralOrigen(mensaje
						.getCodigo_Entidad_Registral_Origen());
				mensajeVO.setCodigoEntidadRegistralDestino(mensaje
						.getCodigo_Entidad_Registral_Destino());
				mensajeVO.setIdentificadorIntercambio(mensaje
						.getIdentificador_Intercambio());
				mensajeVO.setDescripcionMensaje(mensaje
						.getDescripcion_Mensaje());
				mensajeVO.setNumeroRegistroEntradaDestino(mensaje
						.getNumero_Registro_Entrada_Destino());
				mensajeVO.setCodigoError(mensaje.getCodigo_Error());

				// Identificadores de ficheros
				if (mensaje.getIdentificador_Fichero() != null) {
					mensajeVO.setIdentificadoresFicheros(Arrays.asList(mensaje
							.getIdentificador_Fichero()));
				}

				// Fecha y hora de entrada en destino
				String fechaEntradaDestino = mensaje
						.getFecha_Hora_Entrada_Destino();
				if (StringUtils.isNotBlank(fechaEntradaDestino)) {
					mensajeVO.setFechaEntradaDestino(SDF
							.parse(fechaEntradaDestino));
				}

				// Tipo de mensaje
				String tipoMensaje = mensaje.getTipo_Mensaje();
				if (StringUtils.isNotBlank(tipoMensaje)) {
					mensajeVO.setTipoMensaje(TipoMensajeEnum
							.getTipoMensaje(tipoMensaje));
				}

				// Indicador de prueba
				Indicador_PruebaType indicadorPrueba = mensaje
						.getIndicador_Prueba();
				if ((indicadorPrueba != null)
						&& StringUtils.isNotBlank(indicadorPrueba.value())) {
					mensajeVO.setIndicadorPrueba(IndicadorPruebaEnum
							.getIndicadorPrueba(indicadorPrueba.value()));
				}
			}

		} catch (Throwable e) {
			logger.error("Error al parsear el XML del mensaje: [" + xml + "]",
					e);
			throw new ValidacionException(ErroresEnum.ERROR_0037, e);
		}

		if (logger.isDebugEnabled()){ 
			logger.debug("Información obtenida: {}", ToStringLoggerApiHelper.toStringLogger(mensajeVO));
		}

		return mensajeVO;
	}

	/**
	 * Obtiene la extensión del anexo.
	 * 
	 * @param anexo
	 *            Información del anexo.
	 * @return Extensión del anexo.
	 */
	protected static String getExtension(AnexoVO anexo) {

		String extension = null;

		// Obtener la extensión a partir del nombre de fichero
		String nombreFichero = anexo.getNombreFichero();
		int dotIndex = StringUtils.lastIndexOf(nombreFichero, ".");

		if ((dotIndex > 0) && (nombreFichero.length() > dotIndex)) {
			extension = nombreFichero.substring(dotIndex + 1);
		} else {
			extension = DEFAULT_FILE_EXTENSION;
		}

		return extension;
	}

	/**
	 * Validar el segmento de origen o remitente
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoOrigen(FicheroIntercambioVO ficheroIntercambio) {

		// Validar que el código de entidad registral de origen esté informado
		Assert.hasText(ficheroIntercambio.getCodigoEntidadRegistralOrigen(),
				"'Codigo_Entidad_Registral_Origen' must not be empty");

		// Validar el código de entidad registral de origen en DIR3
		Assert.isTrue(validarCodigoEntidadRegistral(ficheroIntercambio
				.getCodigoEntidadRegistralOrigen()),
				"'Codigo_Entidad_Registral_Origen' is invalid");

		// Validar el código de unidad de tramitación de origen en DIR3
		if (StringUtils.isNotBlank(ficheroIntercambio
				.getCodigoUnidadTramitacionOrigen())) {
			Assert.isTrue(validarCodigoUnidadTramitacion(ficheroIntercambio
					.getCodigoUnidadTramitacionOrigen()),
					"'Codigo_Unidad_Tramitacion_Origen' is invalid");
		}

		// Validar que el número de registro de entrada en origen esté informado
		Assert.hasText(ficheroIntercambio.getNumeroRegistro(),
				"'Numero_Registro_Entrada' must not be empty");

		// Validar que la fecha y hora de entrada esté informada
		Assert.hasText(ficheroIntercambio.getFechaRegistroXML(),
				"'Fecha_Hora_Entrada' must not be empty");

		// Validar el formato de la fecha de entrada
		Assert.isTrue(
				StringUtils.equals(ficheroIntercambio.getFechaRegistroXML(),
						SDF.format(ficheroIntercambio.getFechaRegistro())),
				"'Fecha_Hora_Entrada' is invalid ["
						+ ficheroIntercambio.getFechaRegistroXML() + "]");

		// Validar el timestamp de entrada 
		// (El esquema ya valida que sea un base64)
//		if (!ArrayUtils.isEmpty(ficheroIntercambio.getTimestampRegistro())) {
//
//			// Validar que esté en Base64
//			Assert.isTrue(
//					Base64.isBase64(ficheroIntercambio.getTimestampRegistro()),
//					"'Timestamp_Entrada' is invalid");
//		}
	}

	/**
	 * Validar el segmento de destino
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoDestino(
			FicheroIntercambioVO ficheroIntercambio) {

		// Validar que el código de entidad registral de destino esté informado
		Assert.hasText(ficheroIntercambio.getCodigoEntidadRegistralDestino(),
				"'Codigo_Entidad_Registral_Destino' must not be empty");

		// Validar el código de entidad registral de destino en DIR3
		Assert.isTrue(validarCodigoEntidadRegistral(ficheroIntercambio
				.getCodigoEntidadRegistralDestino()),
				"'Codigo_Entidad_Registral_Destino' is invalid");

		// Validar el código de unidad de tramitación de destino en DIR3
		if (StringUtils.isNotBlank(ficheroIntercambio
				.getCodigoUnidadTramitacionDestino())) {
			Assert.isTrue(validarCodigoUnidadTramitacion(ficheroIntercambio
					.getCodigoUnidadTramitacionDestino()),
					"'Codigo_Unidad_Tramitacion_Destino' is invalid");
		}

		//validamos que el destino está configurado en el módulo
		String entidadDestinoConfigurada=getConfiguracionManager().getValorConfiguracion(ficheroIntercambio.getCodigoEntidadRegistralDestino()+".entidad.configurada");
		Assert.isTrue(StringUtils.isNotBlank(entidadDestinoConfigurada),
				"'Codigo_Entidad_Registral_Destino' is not configurated");
	}

	/**
	 * Validar el segmento de interesados
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoInteresados(
			FicheroIntercambioVO ficheroIntercambio) {

		/*
		 * TODO SIR-RC-PR-126 
		 * 
		 * Recepción de fichero de intercambio
		 * correspondiente a un asiento registral con los campos mínimos
		 * requeridos por la norma SICRES3, CON un interesado persona jurídica y
		 * sin representante sin informar, con canal preferente de notificación
		 * la dirección postal de España.
		 */
		
		// Comprobar los datos de los interesados
		if ((ficheroIntercambio.getFicheroIntercambio() != null)
				&& (ficheroIntercambio.getFicheroIntercambio()
						.getDe_InteresadoCount() > 0)) {

			for (De_Interesado interesado : ficheroIntercambio
					.getFicheroIntercambio().getDe_Interesado()) {

				// Validar el canal preferente de comunicación del interesado
				if (StringUtils.isNotBlank(interesado
						.getCanal_Preferente_Comunicacion_Interesado())) {
					Assert.notNull(
							CanalNotificacionEnum
									.getCanalNotificacion(interesado
											.getCanal_Preferente_Comunicacion_Interesado()),
							"'Canal_Preferente_Comunicacion_Interesado' is invalid ["
									+ interesado
											.getCanal_Preferente_Comunicacion_Interesado()
									+ "]");
				}

				// Validar el canal preferente de comunicación del representante
				if (StringUtils.isNotBlank(interesado
						.getCanal_Preferente_Comunicacion_Representante())) {
					Assert.notNull(
							CanalNotificacionEnum
									.getCanalNotificacion(interesado
											.getCanal_Preferente_Comunicacion_Representante()),
							"'Canal_Preferente_Comunicacion_Representante' is invalid ["
									+ interesado
											.getCanal_Preferente_Comunicacion_Representante()
									+ "]");
				}

				if (StringUtils.isBlank(ficheroIntercambio
						.getCodigoUnidadTramitacionOrigen())) {

					Assert.isTrue(
							StringUtils.isNotBlank(interesado
									.getRazon_Social_Interesado())
									|| (StringUtils.isNotBlank(interesado
											.getNombre_Interesado()) && StringUtils.isNotBlank(interesado
											.getPrimer_Apellido_Interesado()) ||"O".equalsIgnoreCase(interesado.getTipo_Documento_Identificacion_Interesado())),
							"'razonSocialInteresado' or ('nombreInteresado' and 'primerApellidoInteresado') must not be empty");

					/*
					Assert.isTrue(
							StringUtils
									.isNotBlank(interesado
											.getCanal_Preferente_Comunicacion_Interesado())
									|| StringUtils
											.isNotBlank(interesado
													.getCanal_Preferente_Comunicacion_Representante()),
							"'canalPreferenteComunicacionInteresado' or 'canalPreferenteComunicacionRepresentante' must not be null");
					 */
					
					if (StringUtils.isNotEmpty(interesado.getTipo_Documento_Identificacion_Interesado())){
						Assert.notNull(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesado.getTipo_Documento_Identificacion_Interesado()),"'invalid tipo_Documento_Identificacion_Interesado'");
					}
					
					if (StringUtils.isNotEmpty(interesado.getTipo_Documento_Identificacion_Representante())){
						Assert.notNull(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesado.getTipo_Documento_Identificacion_Representante()),"invalid 'tipo_Documento_Identificacion_Representante'");
					}
					
					if (StringUtils.isNotBlank(interesado
							.getCanal_Preferente_Comunicacion_Interesado())) {
						if (CanalNotificacionEnum.DIRECCION_POSTAL
								.getValue()
								.equals(interesado
										.getCanal_Preferente_Comunicacion_Interesado())) {

							Assert.hasText(interesado.getPais_Interesado(),
									"'paisInteresado' must not be empty");
							Assert.hasText(
									interesado.getDireccion_Interesado(),
									"'direccionInteresado' must not be empty");

							if (CODIGO_PAIS_ESPANA.equals(interesado
									.getPais_Interesado())) {
								Assert.isTrue(
										StringUtils.isNotBlank(interesado
												.getCodigo_Postal_Interesado())
												|| (StringUtils
														.isNotBlank(interesado
																.getProvincia_Interesado()) && StringUtils
														.isNotBlank(interesado
																.getMunicipio_Interesado())),
										"'codigoPostalInteresado' or ('provinciaInteresado' and 'municipioInteresado') must not be empty");
							}

						} else if (CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA
								.getValue()
								.equals(interesado
										.getCanal_Preferente_Comunicacion_Interesado())) {
							Assert.hasText(
									interesado
											.getDireccion_Electronica_Habilitada_Interesado(),
									"'direccionElectronicaHabilitadaInteresado' must not be empty");
						}
					}

					if (StringUtils.isNotBlank(interesado
							.getCanal_Preferente_Comunicacion_Representante())) {
						if (CanalNotificacionEnum.DIRECCION_POSTAL
								.getValue()
								.equals(interesado
										.getCanal_Preferente_Comunicacion_Representante())) {

							Assert.hasText(interesado.getPais_Representante(),
									"'paisRepresentante' must not be empty");
							Assert.hasText(
									interesado.getDireccion_Representante(),
									"'direccionRepresentante' must not be empty");

							if (CODIGO_PAIS_ESPANA.equals(interesado
									.getPais_Representante())) {
								Assert.isTrue(
										StringUtils
												.isNotBlank(interesado
														.getCodigo_Postal_Representante())
												|| (StringUtils
														.isNotBlank(interesado
																.getProvincia_Representante()) && StringUtils
														.isNotBlank(interesado
																.getMunicipio_Representante())),
										"'codigoPostalRepresentante' or ('provinciaRepresentante' and 'municipioRepresentante') must not be empty");
							}

						} else if (CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA
								.getValue()
								.equals(interesado
										.getCanal_Preferente_Comunicacion_Representante())) {
							Assert.hasText(
									interesado
											.getDireccion_Electronica_Habilitada_Representante(),
									"'direccionElectronicaHabilitadaRepresentante' must not be empty");
						}
					}
				}
			}
		}
	}

	/**
	 * Validar el segmento de asunto
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoAsunto(FicheroIntercambioVO ficheroIntercambio) {

		// Validar que el resumen esté informado
		Assert.hasText(ficheroIntercambio.getResumen(),
				"'Resumen' must not be empty");
	}

	/**
	 * Validar el segmento de anexos
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoAnexos(FicheroIntercambioVO ficheroIntercambio) {

		// Validar los documentos
		if ((ficheroIntercambio.getFicheroIntercambio() != null)
				&& ArrayUtils.isNotEmpty(ficheroIntercambio
						.getFicheroIntercambio().getDe_Anexo())) {
			for (De_Anexo anexo : ficheroIntercambio.getFicheroIntercambio()
					.getDe_Anexo()) {
				validarAnexo(anexo, ficheroIntercambio.getIdentificadorIntercambio());
			}
		}
	}
	
	/**
	 * Valida un anexo del segmento de anexos
	 * 
	 * @param anexo
	 *            Información del anexo
	 * @param identificadorIntercambio
	 *            Identificador de intercambio
	 */
	protected void validarAnexo(De_Anexo anexo, String identificadorIntercambio) {
		
		if (anexo != null) {
			
			// Validar el nombre del fichero anexado
			Assert.hasText(anexo.getNombre_Fichero_Anexado(),
					"'Nombre_Fichero_Anexado' must not be empty");
			Assert.isTrue(
					!StringUtils.containsAny(anexo.getNombre_Fichero_Anexado(),
							"\\/?*:|<>\";&"),
					"'Nombre_Fichero_Anexado' has invalid characters ["
							+ anexo.getNombre_Fichero_Anexado() + "]");
			
			// Validar el identificador de fichero
			validarIdentificadorFichero(anexo, identificadorIntercambio);
			
			// Validar el campo validez de documento
			if (StringUtils.isNotBlank(anexo.getValidez_Documento())) {
				Assert.notNull(
						ValidezDocumentoEnum
								.getValidezDocumento(anexo
										.getValidez_Documento()),
						"'Validez_Documento' is invalid ["
								+ anexo.getValidez_Documento() + "]");
			}

			// Validar el campo tipo de documento
			Assert.hasText(anexo.getTipo_Documento(),
					"'Tipo_Documento' must not be empty");
			Assert.notNull(
					TipoDocumentoEnum.getTipoDocumento(anexo
							.getTipo_Documento()),
					"'Tipo_Documento' is invalid ["
							+ anexo.getTipo_Documento() + "]");
			
			// Validar el hash del documento
			// Nota: no se comprueba el código hash de los documentos porque no
			// se especifica con qué algoritmo está generado.
			Assert.isTrue(!ArrayUtils.isEmpty(anexo.getHash()),
					"'Hash' must not be empty");
			
			// Validar el tipo MIME
			/*
			if (StringUtils.isNotBlank(anexo.getTipo_MIME())) {
				Assert.isTrue(StringUtils.equalsIgnoreCase(
						anexo.getTipo_MIME(), MimeTypeUtils.getMimeType(anexo
								.getIdentificador_Fichero())),
						"'Tipo_MIME' does not match 'Identificador_Fichero'");
			}
			*/

			/*
			 * TODO SIR-RC-PR-096 
			 * 
			 * Recepción de fichero de intercambio correspondiente
			 * a un asiento registral con los campos mínimos requeridos por la
			 * norma SICRES3 y con un fichero anexado y con los campos del
			 * "Hash", "Certificado del firmante", "Firma del documento",
			 * "Sello de tiempo de la firma" y "Validación OCSP del certificado"
			 * informados (no válidos en codificación Base64).
			 */

			/*
			 * TODO SIR-RC-PR-100
			 *  
			 * Recepción de fichero de intercambio
			 * correspondiente a un asiento registral con los campos mínimos
			 * requeridos por la norma SICRES3 y con el campo "Anexo" (no
			 * válido).
			 */

		}
	}
	

	/**
	 * Valida el identificador de fichero de un anexo del segmento de anexos
	 * 
	 * @param anexo
	 *            Información del anexo
	 * @param identificadorIntercambio
	 *            Identificador de intercambio
	 */
	protected void validarIdentificadorFichero(De_Anexo anexo,
			String identificadorIntercambio) {
		
		// No vacío
		Assert.hasText(anexo.getIdentificador_Fichero(),
				"'Identificador_Fichero' must not be empty");

		// Validar el tamaño
		Assert.isTrue(StringUtils.length(anexo.getIdentificador_Fichero()) <= LONGITUD_MAX_IDENTIFICADOR_FICHERO, 
				"'Identificador_Fichero' is invalid");

		// Validar formato: <Identificador del Intercambio>_<Código de tipo de archivo>_<Número Secuencial>.<Extensión del fichero>
		String identificadorFichero = anexo.getIdentificador_Fichero();
		Assert.isTrue(StringUtils.startsWith(identificadorFichero, identificadorIntercambio), 
				"'Identificador_Fichero' does not match 'Identificador_Intercambio'");

		identificadorFichero = StringUtils.substringAfter(identificadorFichero, identificadorIntercambio + "_");
		String[] tokens = StringUtils.split(identificadorFichero, "_.");
		Assert.isTrue(ArrayUtils.getLength(tokens) == 3,
				"'Identificador_Fichero' is invalid");
	
		Assert.isTrue(StringUtils.equals(tokens[0], "01"), 
				"'Identificador_Fichero' is invalid"); // Código de tipo de archivo de 2 dígitos
		Assert.isTrue(StringUtils.length(tokens[1]) <= 4, 
				"'Identificador_Fichero' is invalid"); // Número secuencial de hasta 4 dígitos
		Assert.isTrue(StringUtils.isNumeric(tokens[1]), 
				"'Identificador_Fichero' is invalid"); // Número secuencial compuesto por solo dígitos
		Assert.hasText(tokens[2], 
				"'Identificador_Fichero' is invalid"); // Extensión del fichero
	}
	
	/**
	 * Validar el segmento de internos y control
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoControl(
			FicheroIntercambioVO ficheroIntercambio) {

		// Validar el tipo de transporte
		if (StringUtils.isNotBlank(ficheroIntercambio.getTipoTransporteXML())) {
			Assert.notNull(ficheroIntercambio.getTipoTransporte(),
					"'Tipo_Transporte_Entrada' is invalid ["
							+ ficheroIntercambio.getTipoTransporteXML() + "]");
		}
		
		// Validar el identificador de intercambio
		validarIdentificadorIntercambio(ficheroIntercambio);

		// Validar el tipo de anotación
		Assert.hasText(ficheroIntercambio.getTipoAnotacionXML(),
				"'Tipo_Anotacion' must not be empty");
		Assert.notNull(
				ficheroIntercambio.getTipoAnotacion(),
				"'Tipo_Anotacion' is invalid ["
						+ ficheroIntercambio.getTipoAnotacionXML() + "]");

		// Validar que el código de entidad registral de inicio esté informado
		Assert.hasText(ficheroIntercambio.getCodigoEntidadRegistralInicio(),
				"'Codigo_Entidad_Registral_Inicio' must not be empty");

		// Validar el código de entidad registral de inicio en DIR3
		Assert.isTrue(validarCodigoEntidadRegistral(ficheroIntercambio
				.getCodigoEntidadRegistralInicio()),
				"'Codigo_Entidad_Registral_Inicio' is invalid");
				
		// Validar el identificador de intercambio, tiene que realizarse despues de la validacion del código de entidad registral de inicio 
		validarIdentificadorIntercambio(ficheroIntercambio);
	}
	
	/**
	 * Validar el identificador de intercambio.
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarIdentificadorIntercambio(FicheroIntercambioVO ficheroIntercambio) {
		
		// Comprobar que no esté vacío
		Assert.hasText(ficheroIntercambio.getIdentificadorIntercambio(),
				"'Identificador_Intercambio' must not be empty");
		
		Assert.isTrue(ficheroIntercambio.getIdentificadorIntercambio().length() <= LONGITUD_IDENTIFICADOR_INTERCAMBIO,
				"'Identificador_Intercambio' is invalid");

		// Comprobar el formato del identificiador de intercambio: <Código Entidad Registral Origen>_<AA>_<Número Secuencial>
		String[] tokens = StringUtils.split(
				ficheroIntercambio.getIdentificadorIntercambio(), "_");
		Assert.isTrue(ArrayUtils.getLength(tokens) == 3,
				"'Identificador_Intercambio' is invalid");
		Assert.isTrue(
				StringUtils.length(tokens[0]) <= LONGITUD_CODIGO_ENTIDAD_REGISTRAL,
				"'Identificador_Intercambio' is invalid"); // Código de la entidad registral
		Assert.isTrue(
				StringUtils.equals(tokens[0],
						ficheroIntercambio.getCodigoEntidadRegistralInicio()),
				"'Identificador_Intercambio' does not match 'Codigo_Entidad_Registral_Inicio'");		
		Assert.isTrue(
				StringUtils.length(tokens[1]) == 2,
				"'Identificador_Intercambio' is invalid"); // Año con 2 dígitos
		Assert.isTrue(StringUtils.isNumeric(tokens[1]),
				"'Identificador_Intercambio' is invalid"); //numerico
		
		Assert.isTrue(
				StringUtils.length(tokens[2]) == 8,
				"'Identificador_Intercambio' is invalid"); // Número secuencia de 8 dígitos
		
	}

	/**
	 * Validar el segmento de formulario genérico
	 * 
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 */
	protected void validarSegmentoFormularioGenerico(
			FicheroIntercambioVO ficheroIntercambio) {
		// No hay validaciones
	}

	protected boolean validarCodigoEntidadRegistral(
			String codigoEntidadRegistral) {

		boolean valido = true;
		
		if (StringUtils.length(codigoEntidadRegistral) > LONGITUD_CODIGO_ENTIDAD_REGISTRAL) {
			return false;
		}

		if (isValidacionDirectorioComun()) {

			Criterios<CriterioOficinaEnum> criterios = new Criterios<CriterioOficinaEnum>();
			criterios.addCriterio(new Criterio<CriterioOficinaEnum>(
					CriterioOficinaEnum.OFICINA_ID, codigoEntidadRegistral));

			valido = (getServicioConsultaDirectorioComun().countOficinas(
					criterios) > 0);
		}

		return valido;
	}

	protected boolean validarCodigoUnidadTramitacion(String codigoUnidadTramitacion) {

		boolean valido = true;
		
		if (StringUtils.length(codigoUnidadTramitacion) > LONGITUD_CODIGO_UNIDAD_TRAMITACION) {
			return false;
		}

		/*
		 * No validar contra el directorio común porque pueden ser códigos de
		 * subdirecciones y éstas no se identifican en el DIR3.
		 */
//		if (isValidacionDirectorioComun()) {
//		
//			Criterios<CriterioUnidadOrganicaEnum> criterios = new Criterios<CriterioUnidadOrganicaEnum>();
//			criterios.addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//					CriterioUnidadOrganicaEnum.UO_ID, codigoUnidadTramitacion));
//
//			valido = (getServicioConsultaDirectorioComun()
//					.countUnidadesOrganicas(criterios) > 0);
//		}

		return valido;
	}

	protected boolean isValidacionDirectorioComun() {

		boolean validacion = isDefaultValidacionDirectorioComun();

		if (getConfiguracionManager() != null) {

			// Comprobar si hay que validar los códigos de entidades registrales
			// y unidades de tramitación contra el directorio común a partir de
			// la configuración en base de datos
			String strValidacion = getConfiguracionManager()
					.getValorConfiguracion(
							VALIDACION_DIRECTORIO_COMUN_PARAM_NAME);
			logger.info("Valor de {} en base de datos: [{}]",
					VALIDACION_DIRECTORIO_COMUN_PARAM_NAME, strValidacion);

			if (StringUtils.isNotBlank(strValidacion)) {
				validacion = Boolean.valueOf(strValidacion);
			}
		}

		return validacion;
	}

	protected long getMaxFileSize() {

		long maxFileSize = DEFAULT_MAX_FILE_SIZE;

		if (getConfiguracionManager() != null) {

			// Tamaño máximo para los adjuntos
			String strMaxFileSize = getConfiguracionManager()
					.getValorConfiguracion(
							MAX_TAMANO_ANEXOS_PARAM_NAME);
			logger.info("Valor de {} en base de datos: [{}]",
					MAX_TAMANO_ANEXOS_PARAM_NAME, strMaxFileSize);

			if (StringUtils.isNotBlank(strMaxFileSize)) {
				maxFileSize = Long.valueOf(strMaxFileSize);
			}
		}

		return maxFileSize;
	}

	protected int getMaxNumFiles() {

		int maxNumFiles = DEFAULT_MAX_NUM_FILES;

		if (getConfiguracionManager() != null) {

			// Número máximo de adjuntos
			String strMaxNumFiles = getConfiguracionManager()
					.getValorConfiguracion(
							MAX_NUM_ANEXOS_PARAM_NAME);
			logger.info("Valor de {} en base de datos: [{}]",
					MAX_NUM_ANEXOS_PARAM_NAME, strMaxNumFiles);

			if (StringUtils.isNotBlank(strMaxNumFiles)) {
				maxNumFiles = Integer.valueOf(strMaxNumFiles);
			}
		}

		return maxNumFiles;
	}
	
	/**
	 * Realizamos una validación de los campos del xml que deben estar en base64 en caso de estar presentes
	 * @param xml
	 */
	protected void validateBase64Fields(String xml) {
		XPathReaderUtil reader = null;

		// procesamos el xml para procesar las peticiones xpath
		try {
			reader = new XPathReaderUtil(new ByteArrayInputStream(xml
					.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger
					.error("Error al parsear el XML del fichero de intercambio en la validación campos en Base64"
							+ "[" + xml + "]");
			throw new ValidacionException(ErroresEnum.ERROR_0037);
		}

		// obtenemos el nombre de los campos en base64 junto con su expresion
		// xpath
		Map<String, String> fieldsBase64 = getBase64Fields();
		Set fieldsNameBase64 = fieldsBase64.keySet();

		for (Iterator iterator = fieldsNameBase64.iterator(); iterator
				.hasNext();) {

			// obtenemos la expresion xpath de los campos que deben estar en
			// base64
			String fieldBase64Name = (String) iterator.next();
			String expression = (String) fieldsBase64.get(fieldBase64Name);

			//realizams la consulta en xpath
			NodeList results = (NodeList) reader.read(expression,
					XPathConstants.NODESET);
			
			// verificamos que si los resultados obtenidos son distinto de vacio están en base64
			if (results != null) {
				for (int i = 0; i < results.getLength(); i++) {
					Node item = results.item(i);
					String value = item.getNodeValue();
					if (StringUtils.isNotBlank(value)
							&& !Base64.isBase64(value)) {
						logger
								.error("Error al parsear el XML del fichero de intercambio: Campo no codificado en Base64"
										+ fieldBase64Name + "[" + xml + "]");
						throw new ValidacionException(ErroresEnum.ERROR_0037);
					}
				}
			}
		}
	}
}
