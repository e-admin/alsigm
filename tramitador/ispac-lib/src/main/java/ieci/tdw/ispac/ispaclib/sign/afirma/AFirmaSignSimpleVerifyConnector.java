package ieci.tdw.ispac.ispaclib.sign.afirma;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.sign.DefaultSignConnector;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.security.cert.CertStore;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.util.encoders.Base64;


/**
 * Conector de firma con @firma.
 *
 */
public class AFirmaSignSimpleVerifyConnector extends DefaultSignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DefaultSignConnector.class);
	
	/**
	 * Ruta a la plantilla de Velocity para generar el código HTML para incluir en la página.
	 */
	private static final String AFIRMA_HTML_TEMPLATE = "ieci/tdw/ispac/ispaclib/sign/afirma/aFirma-3.2.0-html-template.vm";

	
	/**
	 * Motor de Velocity.
	 */
	protected VelocityEngine engine;

	
	/**
	 * Constructor.
	 */
	public AFirmaSignSimpleVerifyConnector() {
		super();
		
		// Instanciar el motor de velocity
		engine = new VelocityEngine();
		try {
			
			engine.setProperty("resource.loader", "class");
			engine.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

//			// Por defecto, se deshabilita el log de Velocity
//			engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
//					"ieci.tecdoc.sgm.tram.sign.velocity.log.Slf4jLogChute");
//
//			// Se asigna a velocity el logger del engine
//			engine.setProperty("runtime.log.logsystem.log4j.category", logger.getName());

			engine.init();
			
		} catch (Exception e) {
			logger.error("Error al iniciar el motor de Velocity", e);
		}
	}
	
	/**
	 * Obtiene el código HTML para incluir en la página.
	 * @param locale Información del idioma del cliente.
	 * @param baseURL URL base.
	 * @param hashCode Código HASH del documento a firmar.
	 * @return Código HTML.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String hashCode) throws ISPACException {
		
		VelocityContext ctx = new VelocityContext();

		ctx.put("JAVASCRIPT_PATH", baseURL + "/ispac/applets/afirma/common-js");
		ctx.put("APPLET_PATH", baseURL + "/ispac/applets/afirma");
		ctx.put("FIRMA", "Hash");
		ctx.put("CODIGO_HASH", hashCode);

		return mergeTemplate(ctx);
	}

	/**
	 * Obtiene el código HTML para incluir en la página.
	 * @param locale Información del idioma del cliente.
	 * @param baseURL URL base.
	 * @param hashCodes Códigos HASH de los documentos a firmar.
	 * @return Código HTML.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String [] hashCodes) throws ISPACException {
		
		VelocityContext ctx = new VelocityContext();

		ctx.put("JAVASCRIPT_PATH", baseURL + "/ispac/applets/afirma/common-js");
		ctx.put("APPLET_PATH", baseURL + "/ispac/applets/afirma");
		ctx.put("FIRMA", "MassiveHash");
		ctx.put("CODIGOS_HASH", hashCodes);

		return mergeTemplate(ctx);
	}

	protected String mergeTemplate(VelocityContext context) throws ISPACException {

		StringWriter writer = new StringWriter();

		try {
			engine.mergeTemplate(AFIRMA_HTML_TEMPLATE, VelocityEngine.ENCODING_DEFAULT, context, writer);
		} catch (Exception e) {
			logger.error("Error al mezclar la plantilla", e);
			throw new ISPACException(e);
		}

		return writer.toString();		
	}

	/**
	 * Realiza una validación de una firma o un hash.
	 * 
	 * @param signatureValue Valor de la firma
	 * @param signedContentB64 Contenido firmado en base 64
	 * @throws InvalidSignatureValidationException
	 *             Si la firma no es válida.
	 * @return Detalles de la validación.
	 * 
	 */
	public  Map verify(String signatureValue, String signedContentB64) {
		boolean firmaVerificada=false;
		
		CMSSignedData signature;
		Map result = new HashMap();
		
		try {
			
			byte signedContent [] = Base64.decode(signedContentB64.getBytes());
			CMSProcessableByteArray signedContentCMS = new CMSProcessableByteArray(signedContent);
			byte [] signPk7 = Base64.decode(signatureValue);
			signature = new CMSSignedData(signedContentCMS, new ByteArrayInputStream(signPk7));
			SignerInformation signer = (SignerInformation) signature.getSignerInfos().getSigners().iterator().next();
			CertStore cs = signature.getCertificatesAndCRLs("Collection", "BC");
			Iterator iter = cs.getCertificates(signer.getSID()).iterator();
			X509Certificate  certificate = (X509Certificate) iter.next();
			result=getDetailsSign(signature, certificate);
			// Comprobar integridad de la firma
			firmaVerificada = signer.verify(certificate, "BC");
	
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_ERROR);
		}
		
		if(firmaVerificada) {
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_OK);
		} else {
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_STRANGER);
		}
		
		return result;
	}
	

	/**
	 * Genera el justificante y actualiza los datos en la bbdd
	 */
	public void sign(boolean changeState) throws ISPACException{

		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
		String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");
		
		File file=null;
		if(StringUtils.isNotBlank(infoPagRDE)){
			 file = getFile(infoPagRDE);
		} else {
			 file = getFile(infoPag);
		}
			
		try {

			if (StringUtils.isEmpty(infoPagRDE) ){
				addNewSign(file, changeState);
			} else {
				addSign(file, changeState);
			}
		
		} catch(Exception exc) {
			logger.error("Error en la firma al actualizar los datos en la bbdd [sign][Exception]", exc);
			throw new ISPACException(exc);
		}	
	}
	
	/**
	 * Al firmar un documento por primera vez hay que actualizar no solo la firma sino también el propio doccumento
	 * @param file
	 * @param changeState
	 * @throws ISPACException
	 */
	protected void addNewSign(File file, boolean changeState) throws ISPACException{
		String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");
		
		IInvesflowAPI invesflowAPI = clientContext.getAPI();
		//Obtenemos el tipo mime
		int index1 = file.getName().lastIndexOf(".");
		String sExtension = file.getName().substring(index1 + 1, file.getName().length());
		String sMimeType = MimetypeMapping.getMimeType(sExtension);

		IItem itemDoc = signDocument.getItemDoc();

		//Obtenemos el nombre del tipo de documento
		String doctype = itemDoc.getString("NOMBRE");

		//Obtenemos la fase
		String stageName = invesflowAPI.getProcedureStage(itemDoc.getInt("ID_FASE_PCD")).getString("NOMBRE");
			
		//Obtenemos el tramite si el documento esta asociado a un tramite
		String taskName = null;
		if(itemDoc.getInt("ID_TRAMITE_PCD") != 0)
			taskName = invesflowAPI.getProcedureTaskPCD(itemDoc.getInt("ID_TRAMITE_PCD")).getString("NOMBRE");
			
		//Componemos los metadatos del documento firmado a guardar
		signDocument.setDocumentType(doctype);
		signDocument.setLength((int)file.length());
		signDocument.setMimetype(sMimeType);
		signDocument.setFechaCreacion(new Date());
		if (stageName != null) {
				signDocument.setStage(stageName);
		}
		if (taskName != null) {
				signDocument.setTaskName(taskName);
		}
			
		String documentName = itemDoc.getString("DESCRIPCION");
		if (StringUtils.isBlank(documentName)) {
			documentName = itemDoc.getString("NOMBRE");
		}
		documentName += "." + FileUtils.getFileExtension(file);
		signDocument.setDocumentName(documentName);

		//Se almacena el documento firmado
		infoPagRDE = store();
			
		// Eliminar el fichero PDF una vez subido al gestor documental
		file.delete();
	
		//Actualizamos el documento
		updateDataDoc(infoPagRDE, sExtension,changeState);
			
	}
	/**
	 * Añadimos la nueva firma a la firma del documento.
	 * Cuando se está en un circuito de deberán almacenar varias firmas, en el momento que se alamacene la primera simplemente hay que
	 * actualizar la firma y los metadatos.
	 * @throws ISPACException
	 */
	protected void addSign(File file, boolean changeState) throws ISPACException{
		String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");
		
		//solo actualizamos la firma
		//obtenemos la ultima firma insertada para actualizarla en los metadatos
		int size = signDocument.getSign().size();
		String sign = (String)signDocument.getSign().get(size-1);
				
		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			// Obtenemos el xml con las firmas adjuntadas antes de añadir la
			// nueva
			if (StringUtils.isBlank(infoPagRDE) || !genDocAPI.existsDocument(connectorSession, infoPagRDE)){
				logger.error("No se ha encontrado el documento físico con identificador: '"+infoPagRDE+"' en el repositorio de documentos");
				throw new ISPACInfo("exception.documents.notExists", false);
			}
			String signProperty = genDocAPI.getDocumentProperty(
					connectorSession, infoPagRDE, signDocument.PROPERTY_FIRMA);

			XmlFacade xmlFacade = new XmlFacade(signProperty);
			List list = xmlFacade.getList("/" + SignDocument.TAG_FIRMAS + "/"
					+ SignDocument.TAG_FIRMA);
			// añadimos a la lista de firmas la ultima
			list.add(sign);

			// componemos el xml con las firmas en el objeto SignDocument
			signDocument.setSign(list);

			addNewSign(file, changeState);

			// Actualizamos la propiedad de firma en el gestor documental
			genDocAPI.setDocumentProperty(connectorSession, infoPagRDE,
					signDocument.PROPERTY_FIRMA, signDocument.getSignsXml());
			genDocAPI.setDocumentProperty(connectorSession, infoPagRDE,
					signDocument.PROPERTY_CERTIFICADO, signDocument
							.getCertsXml());
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}		
	}
	
}
