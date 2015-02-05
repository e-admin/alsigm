package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * Conector de firma por defecto.
 *
 */
public class DefaultSignConnector implements ISignConnector {

	/**
	 * Logger
	 */
	public static final Logger logger = Logger.getLogger(DefaultSignConnector.class);

	/**
	 * Identificador de la propiedad fecha de firma
	 */
	public static String DATE_SIGN_OID = "1.2.840.113549.1.9.5";

	protected SignDocument signDocument = null;
	protected IClientContext clientContext = null;
	protected Map mapStorageObject = null;


	/**
	 * Constructor.
	 * 
	 */
	public DefaultSignConnector() {
		Security.addProvider(new BouncyCastleProvider());
	}

	public void initializate(SignDocument signDocument, IClientContext clientContext) {
		this.signDocument = signDocument;
		this.clientContext = clientContext;
		this.mapStorageObject = new HashMap();
	}

	/**
	 * Obtiene el código HTML para incluir en la página.
	 * 
	 * @param locale
	 *            Información del idioma del cliente.
	 * @param baseURL
	 *            URL base.
	 * @param hashCode
	 *            Código HASH del documento a firmar.
	 * @return Código HTML..
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String hashCode)
			throws ISPACException {
		return "";
	}

	/**
	 * Obtiene el código HTML para incluir en la página.
	 * 
	 * @param locale
	 *            Información del idioma del cliente.
	 * @param baseURL
	 *            URL base.
	 * @param hashCodes
	 *            Códigos HASH de los documentos a firmar.
	 * @return Código HTML.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String[] hashCodes)
			throws ISPACException {
		return "";
	}

	public static KeyPair generateKeyPair(long seed) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(
				"SHA1WithRSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecureRandom rng = SecureRandom.getInstance("SHA1WithRSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		rng.setSeed(seed);
		keyGenerator.initialize(1024, rng);

		return (keyGenerator.generateKeyPair());
	}

	public Map verify(String signatureValue, String signedContentB64)
			throws InvalidSignatureValidationException {
		return null;
	}

	public CMSSignedData getSignature(String signatureValue,
			String signedContentB64) throws CMSException {
		byte signedContent[] = Base64.decode(signedContentB64);
		CMSProcessableByteArray signedContentCMS = new CMSProcessableByteArray(
				signedContent);
		byte[] signPk7 = Base64.decode(signatureValue);
		CMSSignedData signature = new CMSSignedData(signedContentCMS, signPk7);
		return signature;
	}

	public X509Certificate getCertificate(CMSSignedData signature)
			throws CMSException, NoSuchAlgorithmException,
			GeneralSecurityException {

		SignerInformationStore signerInfoStore = signature.getSignerInfos();
		List listaSigner = (List) signerInfoStore.getSigners();
		SignerInformation signer = (SignerInformation) listaSigner.get(0);

		CertStore cs = signature.getCertificatesAndCRLs("Collection", "BC");
		Iterator iter = cs.getCertificates(signer.getSID()).iterator();
		X509Certificate certificate = (X509Certificate) iter.next();
		return certificate;

	}

	public Map getDetailsSign(CMSSignedData signature,
			X509Certificate certificate) {

		Map result = new HashMap();

		try {

			DEREncodable contentInfo = (DEREncodable) signature
					.getContentInfo();
			Map signProperties = new ASN1Parser()
					.readPropertiesOid(contentInfo);

			String signDate = (String) signProperties.get(DATE_SIGN_OID);

			Map signerProperties = new X509CertSimpleReader().read(certificate);

			// distinguished name
			String dn = certificate.getSubjectX500Principal().toString();

			result = signerProperties;

			result.put(ISignAPI.DN, dn);
			result.put(ISignAPI.FECHA_FIRMA, signDate);

			if (logger.isDebugEnabled()) {
				logger.debug("Documento firmado por:" + dn);
				logger.debug("Datos firmante: " + signerProperties);
				logger.debug("Fecha firma:" + signDate);
			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_ERROR);
		}

		return result;
	}

	public void sign(boolean changeState) throws ISPACException {

	}

	/**
	 * @param ctx
	 *            Contexto del cliente
	 * @return Cadena de identificación del documento en el gestor documental
	 *         Persiste el documento firmado en el gestor documental
	 *         establecido.
	 * @throws ISPACException
	 */
	public String store(IClientContext ctx) throws ISPACException {
		String docref = null;

		Integer rdeArchiveId = getRdeArchiveId();
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		
		Object connectorSession = null;
		
		try {
			connectorSession = genDocAPI.createConnectorSession();
			docref = genDocAPI.newDocument(connectorSession, rdeArchiveId,
					signDocument.getDocument(), signDocument.getLength(),
					signDocument.getProperties());
			
			// Cerramos el fichero
			signDocument.getDocument().close();
			
		} catch (ISPACException e) {
			logger.error("Error al guardar el documento firmado", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al guardar el documento firmado", e);
			throw new ISPACException(e);
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}

		return docref;
	}

	protected File getFile(IItem doc) throws ISPACException {

		IGenDocAPI gendocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;

		try {

			connectorSession = gendocAPI.createConnectorSession();
			File file = null;
			try {

				String infoPag = doc.getString("INFOPAG");
				String extension = doc.getString("EXTENSION");

				// Se almacena documento y firma en el gestor documental
				// String fileName =
				// FileTemporaryManager.getInstance().newFileName("."+extension);
				// fileName =
				// FileTemporaryManager.getInstance().getFileTemporaryPath() +
				// "/" + fileName;
				file = FileTemporaryManager.getInstance().newFile(
						"." + extension);

				OutputStream out = new FileOutputStream(file);
				if (StringUtils.isBlank(infoPag) || !gendocAPI.existsDocument(connectorSession, infoPag)){
					logger.error("No se ha encontrado el documento físico con identificador: '"+infoPag+"' en el repositorio de documentos");
					throw new ISPACInfo("exception.documents.notExists", false);
				}
				gendocAPI.getDocument(connectorSession, infoPag, out);

				signDocument.setDocument(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				throw new ISPACException(e);
			}
			return file;
		} finally {
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
		}
	}

	protected File getFile(String infoPag) throws ISPACException {
		IGenDocAPI gendocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		try {
			connectorSession = gendocAPI.createConnectorSession();
			File file = null;
			try {
				if (StringUtils.isBlank(infoPag) || !gendocAPI.existsDocument(connectorSession, infoPag)){
					logger.error("No se ha encontrado el documento físico con identificador: '"+infoPag+"' en el repositorio de documentos");
					throw new ISPACInfo("exception.documents.notExists", false);
				}
				String extension = MimetypeMapping.getExtension(gendocAPI
						.getMimeType(connectorSession, infoPag));

				// Se almacena documento y firma en el gestor documental
				String fileName = FileTemporaryManager.getInstance()
						.newFileName("." + extension);
				fileName = FileTemporaryManager.getInstance()
						.getFileTemporaryPath()
						+ "/" + fileName;

				OutputStream out = new FileOutputStream(fileName);
				gendocAPI.getDocument(connectorSession, infoPag, out);

				file = new File(fileName);
				signDocument.setDocument(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				throw new ISPACException(e);
			}
			return file;
		} finally {
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
		}

	}

	private Integer getRdeArchiveId() throws ISPACException {
		Object obj = mapStorageObject.get("invesDoc");
		if (obj == null) {
			ISPACConfiguration config = ISPACConfiguration.getInstance();
			String id = (String) config.get(ISPACConfiguration.RDE_ARCHIVE_ID);
			if (StringUtils.isEmpty(id))
				throw new ISPACException(
						"Identificador de archivador desconocido");
			obj = new Integer(id);
			mapStorageObject.put("invesDoc", obj);
		}
		return (Integer) obj;
	}

	protected void updateDataDoc(String infoPagRDE, String extension,
			boolean changeState) throws ISPACException {
		// Actualizar la referencia en el campo INFOPAG_RDE en la tabla de
		// documentos, que es la referencia para el gestor documental
		// Se Establece el repositorio y la extension del documento (El
		// repositorio es una constante a extraer de fichero de configuracion)
		// Obtenemos el repositorio
		ISPACConfiguration config = ISPACConfiguration.getInstance();
		String repositorio = config.getProperty(ISPACConfiguration.REPOSITORY);
		IItem itemDoc = signDocument.getItemDoc();
		itemDoc.set("INFOPAG_RDE", infoPagRDE);
		itemDoc.set("EXTENSION_RDE", extension);
		itemDoc.set("REPOSITORIO", repositorio);

		// Se cambia el estado del documento a FIRMADO si asi esta indicado,
		// se establece la fecha de firma y se asigna tambien esta fecha al
		// campo que indica la fecha de aprobacion
		if (changeState)
			itemDoc.set("ESTADOFIRMA", SignStatesConstants.FIRMADO);
		itemDoc.set("FFIRMA", new Date());
		itemDoc.set("FAPROBACION", new Date());
		// Bloqueamos el documento para la edicion
		itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
		itemDoc.store(clientContext);
	}

	/**
	 * @param ctx
	 *            Contexto del cliente
	 * @return Cadena de identificación del documento en el gestor documental
	 *         Persiste el documento firmado en el gestor documental
	 *         establecido.
	 * @throws ISPACException
	 */
	public String store() throws ISPACException {
		String docref = null;


		Integer rdeArchiveId = getRdeArchiveId();
		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;

		try {
			connectorSession = genDocAPI.createConnectorSession();
			docref = genDocAPI.newDocument(connectorSession, rdeArchiveId,
					signDocument.getDocument(), signDocument.getLength(),
					signDocument.getProperties());

			// Cerramos el fichero
			signDocument.getDocument().close();
			
		} catch (ISPACException e) {
			logger.error("Error al guardar el documento firmado", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al guardar el documento firmado", e);
			throw new ISPACException(e);
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}
		return docref;
	}

	/**
	 * 
	 * @param x509CertString
	 * @return Devuelve los datos del firmante
	 * @throws Exception
	 */
	public String getInfoCert(String x509CertString) throws ISPACException {

		String firmante = null;
		try {
			X509Certificate x509cer = getX509Certificate(x509CertString);
			firmante = x509cer.getSubjectDN().getName();

			int posComa = firmante.indexOf(",");
			if (posComa > 0)
				firmante = firmante.substring(firmante.indexOf("=") + 1,
						posComa);
			else
				firmante = firmante.substring(firmante.indexOf("=") + 1);
		} catch (CertificateException e) {
			throw new ISPACException(e);
		}

		return firmante;
	}

	public ResultadoValidacionCertificado validateCertificate(String x509CertString)
			throws ISPACException{
		logger.info("Se procede a realizar la validación del certificado de forma sencilla");
		
		ResultadoValidacionCertificado resultado = new ResultadoValidacionCertificado();
		try{
			X509Certificate x509cer = getX509Certificate(x509CertString);
			x509cer.checkValidity();
			resultado.setResultadoValidacion(ResultadoValidacionCertificado.VALIDACION_OK);	
		}catch(CertificateExpiredException e){
			logger.error("Error al validar certificado.El certificado ha expirado. ");
			resultado.setResultadoValidacion(ResultadoValidacionCertificado.VALIDACION_ERROR);
			resultado.setMensajeValidacion("El certificado ha expirado.");
			
		}catch(CertificateNotYetValidException e){
			logger.error("Error al validar certificado. El certificado no ha sido validado todavía.");
			resultado.setResultadoValidacion(ResultadoValidacionCertificado.VALIDACION_ERROR);
				resultado.setMensajeValidacion("El certificado no ha sido validado todavía.");
		}catch (Throwable e) {
			throw new ISPACException(e);
		}
		return resultado;
	}
	
	private X509Certificate getX509Certificate(String x509CertString)throws CertificateException{
		x509CertString = "-----BEGIN CERTIFICATE-----\n" + x509CertString
				+ "\n-----END CERTIFICATE-----";
		ByteArrayInputStream bais = new ByteArrayInputStream(
				(byte[]) x509CertString.getBytes());
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509cer = (X509Certificate) cf
				.generateCertificate(bais);
		return x509cer;
	}
	
}
