package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ibm.icu.util.Calendar;

import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

public abstract class AbstractWSTest extends AbstractJUnit4SpringContextTests {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractWSTest.class);
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_0 = "O00001222";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_0 = "MSPI";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_0 = "E00138403";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_0 = "MSPI UNI";
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_0_0 = "O00001223";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_0_0 = "MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	protected static final String CODIGO_UNIDAD_TRAMITACION_0_0 = "E00106103";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_0_0 = "MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	

	protected static final String CODIGO_ENTIDAD_REGISTRAL = "O00002062";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL = "REGISTRO GENERAL DEL AYUNTAMIENTO DE COLINDRES";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION = "L01390232";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION = "Ayuntamiento de Colindres";

	protected static final String CODIGO_ENTIDAD_REGISTRAL_1 = "O00002061";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_1 = "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE SORIA";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_1 = "L02000042";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_1 = "Diputación Provincial de Soria";

	protected static final String CODIGO_ENTIDAD_REGISTRAL_2 = "O00002101";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_2 = "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE CIUDAD REAL";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_2 = "L02000013";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_2 = "Diputación Provincial de Ciudad Real";
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_3 = "O00002101";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_3 = "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE CIUDAD REAL";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_3 = "L02000013";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_3 = "Diputación Provincial de Ciudad Real";
	
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_4 = "O00002102";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_4 = "REGISTRO GENERAL DEL AYUNTAMIENTO DE CIUDAD REAL";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_4 = "L01130343";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_4 = "Ayuntamiento de Ciudad Real";

	
	

	protected static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	protected IntercambioRegistralWS intercambioRegistralWSClient;

	protected IntercambioRegistralWS getIntercambioRegistralWS() {
		return intercambioRegistralWSClient;
	}
	
	protected String getHash(byte[] content) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(content);
			return Base64.encodeBase64String(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al generar el hash", e);
		}
	}

	protected String createIdentificadorIntercambio(String codEntidadRegistral) {
		return codEntidadRegistral
				+ "_"
				+ String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
						.substring(2) + "_"
				+ StringUtils.right(String.valueOf(new Date().getTime()), 8);
	}


	protected void deleteAsientoRegistral(String codigoEntidadRegistral, String identificadorIntercambio) {

		CriteriosDTO criterios = new CriteriosDTO();
		criterios.getCriterios().add(
				createCriterioDTO(
						CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL,
						OperadorCriterioEnum.EQUAL, codigoEntidadRegistral));
		criterios.getCriterios().add(
				createCriterioDTO(
						CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO,
						OperadorCriterioEnum.EQUAL, identificadorIntercambio));

		List<AsientoRegistralDTO> asientos = intercambioRegistralWSClient.findAsientosRegistrales(criterios);
		if (CollectionUtils.isNotEmpty(asientos)) {
			intercambioRegistralWSClient.deleteAsientoRegistral(asientos.get(0).getId());
		}
	}

	protected static CriteriosDTO createCriteriosDTO(CriterioDTO[] arrayCriterios, String[] arrayOrderBy) {
		CriteriosDTO criterios = new CriteriosDTO();
		
		if (!ArrayUtils.isEmpty(arrayCriterios)) {
			for (CriterioDTO criterio : arrayCriterios) {
				criterios.getCriterios().add(criterio);
			}
		}
		
		if (!ArrayUtils.isEmpty(arrayOrderBy)) {
			for (String orderBy : arrayOrderBy) {
				criterios.getOrderBy().add(orderBy);
			}
		}

		return criterios;
	}
	
	protected static CriterioDTO createCriterioDTO(CriterioEnum nombre, OperadorCriterioEnum operador, Object valor) {
		CriterioDTO criterio = new CriterioDTO();
		criterio.setNombre(nombre.getValue());
		criterio.setOperador(operador.getValue());
		criterio.getValor().add(valor);
		
		return criterio;
	}

	protected static String toString(AsientoRegistralDTO asiento) {
		
		if (asiento == null) {
			return null;
		}
		
		return ToStringBuilder.reflectionToString(asiento);
	}

	protected static String toString(TrazabilidadDTO traza) {
		
		if (traza == null) {
			return null;
		}

		return ToStringBuilder.reflectionToString(traza);
	}
	

	protected static AsientoRegistralFormDTO createAsientoRegistralFormDTO() {
		return createAsientoRegistralFormDTO(CODIGO_ENTIDAD_REGISTRAL,
				CODIGO_ENTIDAD_REGISTRAL, DESCRIPCION_ENTIDAD_REGISTRAL,
				CODIGO_UNIDAD_TRAMITACION, DESCRIPCION_UNIDAD_TRAMITACION,
				CODIGO_ENTIDAD_REGISTRAL_1, DESCRIPCION_ENTIDAD_REGISTRAL_1,
				CODIGO_UNIDAD_TRAMITACION_1, DESCRIPCION_UNIDAD_TRAMITACION_1,
				CODIGO_ENTIDAD_REGISTRAL, DESCRIPCION_ENTIDAD_REGISTRAL);
	}
	
	protected static AsientoRegistralFormDTO createAsientoRegistralFormDTONumeroRegistro(String numeroRegistro,String codEROrigen, String descEROrigen, String codUTOrigen, String descUTOrigen, 
			String codERDestino, String descERDestino, String codUTDestino, String descUTDestino,
			String codERInicio, String descERInicio){
		AsientoRegistralFormDTO result=createAsientoRegistralFormDTO(codEROrigen,codEROrigen, descEROrigen, codUTOrigen, descUTOrigen, codERDestino, descERDestino, codUTDestino, descUTDestino, codERInicio, descERInicio);
		result.setNumeroRegistro(numeroRegistro);
		result.setNumeroRegistroInicial(numeroRegistro);
		return result;
	}
	
	protected static AsientoRegistralFormDTO createAsientoRegistralFormDTO(
			String codER, 
			String codEROrigen, String descEROrigen, String codUTOrigen, String descUTOrigen, 
			String codERDestino, String descERDestino, String codUTDestino, String descUTDestino,
			String codERInicio, String descERInicio) {

		AsientoRegistralFormDTO asientoForm = new AsientoRegistralFormDTO();

		asientoForm.setCodigoEntidadRegistral(codER);
		
		asientoForm.setCodigoEntidadRegistralOrigen(codEROrigen);
		asientoForm.setDescripcionEntidadRegistralOrigen(descEROrigen);
		asientoForm.setCodigoUnidadTramitacionOrigen(codUTOrigen);
		asientoForm.setDescripcionUnidadTramitacionOrigen(descUTOrigen);

		asientoForm.setCodigoEntidadRegistralDestino(codERDestino);
		asientoForm.setDescripcionEntidadRegistralDestino(descERDestino);
		asientoForm.setCodigoUnidadTramitacionDestino(codUTDestino);
		asientoForm.setDescripcionUnidadTramitacionDestino(descUTDestino);

		asientoForm.setCodigoEntidadRegistralInicio(codERInicio);
		asientoForm.setDescripcionEntidadRegistralInicio(descERInicio);

		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
		asientoForm.setNumeroRegistro("201200100000001");
		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
		asientoForm.setTimestampRegistro(null);

		asientoForm.setNumeroRegistroInicial(asientoForm.getNumeroRegistro());
		asientoForm.setFechaRegistroInicial(asientoForm.getFechaRegistro());
		asientoForm.setTimestampRegistroInicial(asientoForm.getTimestampRegistro());

		asientoForm.setResumen("Resumen del registro");
		asientoForm.setCodigoAsunto("ASUNTO0000000001");
		asientoForm.setReferenciaExterna("REF0000000000001");
		asientoForm.setNumeroExpediente("EXP2012/00001");
		asientoForm.setTipoTransporte(TipoTransporteEnum.SERVICIO_MENSAJEROS.getValue());
		asientoForm.setNumeroTransporte("99999");
		asientoForm.setNombreUsuario("ieci");
		asientoForm.setContactoUsuario("ieci@ieci.es");
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA.getValue());
		asientoForm.setObservacionesApunte("Observaciones del apunte");
		asientoForm.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());
		asientoForm.setExpone("Texto de EXPONE");
		asientoForm.setSolicita("Texto de SOLICITA");

		asientoForm.getInteresados().add(createInteresadoFormDTO());

		
		asientoForm.getAnexos().add(createAnexoFormDTO("1"));
		asientoForm.getAnexos().add(createAnexoFormDTO("2"));
		asientoForm.getAnexos().add(createAnexoFormDTO("3"));
		
		return asientoForm;
	}

	protected static InteresadoFormDTO createInteresadoFormDTO() {

		InteresadoFormDTO interesado = new InteresadoFormDTO();

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("INFORMÁTICA EL CORTE INGLÉS, S.A.");
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("724");
		interesado.setCodigoProvinciaInteresado("28");
		interesado.setCodigoMunicipioInteresado("0796");
		interesado.setDireccionInteresado("Travesía de Costa Brava nº4");
		interesado.setCodigoPostalInteresado("28034");
		interesado.setCorreoElectronicoInteresado("mkt@ieci.es");
		interesado.setTelefonoInteresado("913874700");
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("Isidoro");
		interesado.setPrimerApellidoRepresentante("Álvarez");
		interesado.setSegundoApellidoRepresentante("Álvarez");
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante("Hermosilla, 112");
		interesado.setCodigoPostalRepresentante("28009");
		interesado.setCorreoElectronicoRepresentante("servicio_clientes@elcorteingles.es");
		interesado.setTelefonoRepresentante("901122122");
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		
		interesado.setObservaciones("Observaciones sobre el interesado");

		return interesado;
	}

	protected static AnexoFormDTO createAnexoFormDTO(String id) {

		AnexoFormDTO anexo = new AnexoFormDTO();

		anexo.setCertificado(null);
	    anexo.setCodigoFichero("Fichero_" + id);
	    anexo.setCodigoFicheroFirmado(null);
	    anexo.setContenido("Contenido del anexo".getBytes());
	    
	    anexo.setFirma(null);
	    anexo.setIdentificadorFicheroFirmado(null);
	    anexo.setNombreFichero("Fichero_" + id + ".txt");
	    anexo.setObservaciones(null);
	    anexo.setTimestamp(null);
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
	    anexo.setTipoMIME("text/plain");
	    anexo.setValidacionOCSPCertificado(null);
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());
	    
	    //anexo.setHash(Base64.encode(anexo.getNombreFichero().getBytes()));

		return anexo;
	}

	protected String getIdAsientoRegistral(String codigoEntidadRegistral, String identificadorIntercambio) {
		
		logger.info("Obteniendo identificador del asiento registral con codigoEntidadRegistral {} e identificador de intercambio: {}",
				codigoEntidadRegistral, identificadorIntercambio);
		
		// Identificador del asiento registral 
		String idAsientoRegistral = getAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio).getId();
		
		logger.info("Identificador del asiento registral: {}", idAsientoRegistral);
		
		return idAsientoRegistral;
	}
	
	
	protected AsientoRegistralDTO getAsientoRegistral(String codigoEntidadRegistral, String identificadorIntercambio) {
		
		logger.info(
				"Obteniendo asiento registral con codigoEntidadRegistral {} e identificador de intercambio: {}",
				codigoEntidadRegistral, identificadorIntercambio);

		// Buscar el asiento registral a partir del identificador de intercambio
		List<AsientoRegistralDTO> asientos = getIntercambioRegistralWS()
				.findAsientosRegistrales(createCriteriosDTO(new CriterioDTO[] {
						createCriterioDTO(CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL, OperadorCriterioEnum.EQUAL, codigoEntidadRegistral),
						createCriterioDTO(CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO, OperadorCriterioEnum.EQUAL, identificadorIntercambio) 
			}, null));
		Assert.assertTrue(
				"No se ha encontrado el asiento registral con codigoEntidadRegistral " + codigoEntidadRegistral + " e identificador de intercambio "
						+ identificadorIntercambio, asientos.size() > 0);		
		
		AsientoRegistralDTO asientoRegistral = asientos.get(0);
		logger.info("Asiento Obtenido con Identificador del asiento registral: {}", asientoRegistral.getId());
		
		return asientoRegistral;
	}
	
	public String xmlFileToString(File file) {
		String result=null;
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			InputStream inputStream = new FileInputStream(file);
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			result=stw.toString();
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		
		return result;
	}

	
}
