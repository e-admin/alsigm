package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.converter.DocumentConverter;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.sign.ResultadoValidacionCertificado;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.sign.afirma.AFirmaSignSimpleVerifyConnector;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.Firmante;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignature;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;

/**
 */
public class SigemSignConnector extends AFirmaSignSimpleVerifyConnector implements ISigemSignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigemSignConnector.class);

	/**
	 * Tamaño reservado para incrustar la firma en el PDF.
	 */
	protected static final int SIGN_SIZE = 32000;

	/**
	 * Formato por defecto para la fecha de la firma.
	 */
	protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Propiedades de configuración para el aspecto de la banda gris
	 * con la información del firmante incrustada en el PDF.
	 */
	public final static String MARGIN_BAND 			= "MARGIN_BAND";
	public final static String POSITIONY_BAND		= "POSITIONY_BAND";
	public final static String FONT_BAND			= "FONT_BAND";
	public final static String ENCODING_BAND		= "ENCODING_BAND";
	public final static String FONTSIZE_BAND 		= "FONTSIZE_BAND";
	public final static String BAND					= "BAND";
	public final static String SIZE_BAND			= "SIZE_BAND";
	public final static String SIZE_INICIAL_BAND	= "SIZE_INICIAL_BAND";
	public final static String SIZE_OTHER_BAND		= "SIZE_OTHER_BAND";
	public final static String PATH_IMAGEN_BAND		= "PATH_IMAGE_BAND";
	public final static String PATH_TEXT			= "PATH_TEXT";
	// Formato para la fecha de la firma
	public final static String DATE_FORMAT			= "DATE_FORMAT";

	/**
	 * Ruta por defecto de la imagen de fondo del PDF.
	 */
	private static final String DEFAULT_PDF_BG_IMAGE_PATH = "firma/fondo.gif";

	/**
	 * Ruta por defecto del fichero con el texto de la banda lateral del PDF.
	 */
	private static final String DEFAULT_PDF_BG_DATA_PATH = "firma/datosFirma.gif";

	/**
	 * Nombre del fichero de recursos.
	 */
	private static String BUNDLE_NAME = "ieci.tecdoc.sgm.tram.sign.SignConnectorMessages";

	/**
	 * Constructor.
	 *
	 */
	public SigemSignConnector() {
		super();
	}

	/**
	 * Realiza una validación de una firma o un hash.
	 *
	 * @param signatureValue Valor de la firma
	 * @param signedContentB64 Contenido de la firma en base 64
	 * @throws InvalidSignatureValidationException
	 *             Si la firma no es válida.
	 * @return Detalles de la validación.
	 *
	 */
	public  Map verify(String signatureValue, String signedContentB64) {

		boolean firmaVerificada=false;
		Map result = new HashMap();

		try {

			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			//Llamamos al servicio de firma digital que invocará al servicio de @firma para validar la firma.

			ResultadoValidacionFirma validacionFirma= firmaDigital.validarFirma(signatureValue.getBytes(),signedContentB64.getBytes());
			firmaVerificada=validacionFirma.isValidationResultOK();
			List signers= validacionFirma.getSigners();
			result.put(ISignAPI.DN, ((Firmante)signers.get(0)).getName());


		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_ERROR);
		}
		if(firmaVerificada){
		result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_OK);

		}
		else{
			result.put(ISignAPI.INTEGRIDAD, ISignAPI.INTEGRIDAD_STRANGER);
		}
		return result;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.tram.sign.ISigemSignConnector#generateGrayBandImagen(java.lang.String, com.lowagie.text.pdf.PdfContentByte, float, boolean, float, int, int, java.lang.String, java.util.List)
	 */
	public void generateGrayBandImagen(String dateFirma,
			PdfContentByte pdfContentByte, float margen, boolean vh, float x,
			int numberOfPages, int pageActual, String codCotejo, List signerList)
			throws ISPACException {

		getImagen(dateFirma, pdfContentByte, margen, vh, x, numberOfPages, pageActual, codCotejo);
	}


	/**
	 * Obtiene el fimero a firmar y lo convierte a pdf
	 */
	protected File getFile(String docRef) throws ISPACException{

		IGenDocAPI gendocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		File file = null;

		try {
			connectorSession = gendocAPI.createConnectorSession();

			String extension = MimetypeMapping.getExtension(gendocAPI.getMimeType(connectorSession, docRef));
			if(!extension.equalsIgnoreCase("pdf")) {

				// Convertir el documento original a PDF
				file = convert2PDF(docRef, extension);

			} else {

				// Se obtiene el documento del repositorio documental
				String fileName = FileTemporaryManager.getInstance().newFileName("."+extension);
				fileName = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileName;

				OutputStream out = new FileOutputStream(fileName);
				gendocAPI.getDocument(connectorSession, docRef, out);

				file = new File(fileName);
			}

			signDocument.setDocument(new FileInputStream(file));

		} catch (FileNotFoundException e) {
			logger.error("Error al obtener el fichero: " + docRef, e);
			throw new ISPACException(e);
		} finally {
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
		}

		return file;
	}

	/**
	 * Convierte le fichero a pdf
	 * @param infoPag
	 * @param extension
	 * @return
	 * @throws ISPACException
	 */
	private File convert2PDF(String infoPag, String extension) throws ISPACException {
		// Convertir el documento a pdf
		String docFilePath= DocumentConverter.convert2PDF(clientContext.getAPI(), infoPag,extension);

		//String docFilePath = DocumentConverter.convert(clientContext.getAPI(), infoPag, DocumentConverter.PDFWRITER);

		// Obtener la información del fichero convertido
		File file = new File( docFilePath);
		if (!file.exists())
			throw new ISPACException("No se ha podido convertir el documento a PDF");
		InputStream in;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ISPACException("Fichero no encontrado: '" + file.getName() + "'");
		}
		signDocument.setDocument(in);
		return file;
	}

	/**
	 * Realiza la firma del documento
	 */
	public void sign(boolean changeState) throws ISPACException{

		int signerNumber = signDocument.getSign().size();

		if (signerNumber == 0) {
			throw new ISPACException("El documento " + signDocument.getIdPcd()
					+ "tiene que estar firmado.");
		}

	   	generatePdfToSign(changeState);

		ServicioFirmaDigital firmaDigital;
		try {
			firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			// El certificado y la firma actuales son los últimos elementos de sus respectivas listas
			String signatureValue = signDocument.getSign().get(signDocument.getSign().size() - 1).toString();
			String signCertificateValue = signDocument.getSignCertificate().get(signDocument.getSignCertificate().size() - 1).toString();
			String hash = signDocument.getHash();

			//Llamamos al servicio de firma digital
			String idTransaction=firmaDigital.registrarFirma(Base64Util.decode(signatureValue), Base64Util.decode(signCertificateValue), Base64Util.decode(hash));

			if (logger.isInfoEnabled()) {
				logger.info("Documento registrado en la plataforma afirma OK: "	+ idTransaction);
			}
			storeIdTransaccion(idTransaction);
		} catch (Exception e) {
			logger.error("No se ha podido registrar el documento en la plataforma firma5.",e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Obtiene el texto del recurso especificado.
	 * @param language Idioma
	 * @param key Clave del texto.
	 * @return Texto.
	 */
	protected String getString(Locale locale, String key) {
		try {
			return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Obtiene el documento , pdf si ya se hubiese firmado alguna vez o el original en otro caso.
	 * Añade los datos del firmante y genera o modifica  el pdf.
	 * @param changeState
	 * @throws ISPACException
	 */
	protected void generatePdfToSign(boolean changeState) throws ISPACException {

		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
		String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");

		IInvesflowAPI invesflowAPI = clientContext.getAPI();
		ISignAPI signAPI= invesflowAPI.getSignAPI();

		// construimos el pdf con la información de los firmantes  y lo mandamos a firmar al servidor
		PdfStamper stp = null;
		PdfReader reader = null;
		FileOutputStream fout = null;
		String pathFileTemp = null;

		File file=null;
		if(StringUtils.isNotBlank(infoPagRDE)){
			 file = getFile(infoPagRDE);
		} else {
			 file = getFile(infoPag);
		}

		try {

			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
			pathFileTemp = FileTemporaryManager.getInstance().put(file.getAbsolutePath(), ".pdf");

			/*
			 * A certificate factory for X.509 must return certificates that are an instance of java.security.cert.X509Certificate,
			 * and CRLs that are an instance of java.security.cert.X509CRL.
			 * The following code reads a String with Base64 encoded certificate,
			 * which are each bounded at the beginning by -----BEGIN CERTIFICATE-----, and bounded at the end by -----END CERTIFICATE-----.
			 */
			List signsCertList = signDocument.getSignCertificate();
			String x509CertString = (String) signsCertList.get(signsCertList.size()-1);
			String firmante=signAPI.getFirmanteFromCertificado(x509CertString);
	        x509CertString = "-----BEGIN CERTIFICATE-----\n" + x509CertString + "\n-----END CERTIFICATE-----";

	        ByteArrayInputStream bais = new ByteArrayInputStream((byte[])x509CertString.getBytes());
	        CertificateFactory cf = CertificateFactory.getInstance("X.509");
	        X509Certificate x509cer = (X509Certificate)cf.generateCertificate(bais);

	        // Firmante
			int posComa = firmante.indexOf(",");
			if (posComa > 0) {
				firmante = firmante.substring(firmante.indexOf("=") + 1, posComa);
			} else {
				firmante = firmante.substring(firmante.indexOf("=") + 1);
			}

			// Fecha de la firma
			Date signDate = servicioTiempos.getCurrentDate();

			if(StringUtils.isBlank(infoPagRDE)) {

				//addGrayBand(file, pathFileTemp, infoPagRDE, dateFirma, firmante);
				String codCotejo = addCodVerificacion(signDocument, clientContext);
				// Añadir la banda gris lateral con la información de la firma
				addGrayBand(file, pathFileTemp, infoPagRDE, signDate, firmante, codCotejo);
			}

			// Obtenemos la información del expediente
			IItem expediente = invesflowAPI.getEntitiesAPI().getExpedient(signDocument.getNumExp());

			/* INICIO - INCRUSTAR FIRMA EN PDF */
			reader = new PdfReader(FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + pathFileTemp);
			fout = new FileOutputStream(file.getAbsolutePath());

			//Si infoPagRde esta vacio es que aún no ha sido firmado , entonces tenemos que crear el dpf
			if (StringUtils.isNotBlank(infoPagRDE)) {
				stp = PdfStamper.createSignature(reader, fout, '\0', null, true);
			} else{
				stp = PdfStamper.createSignature(reader, fout, '\0');
			}

//			PdfSignatureAppearance sap = stp.getSignatureAppearance();
//			sap.setCrypto(null, new Certificate[] { x509cer }, null, PdfSignatureAppearance.SELF_SIGNED);
//			sap.setReason(expediente.getString("ASUNTO"));
//			sap.setLocation("Es");
//			stp.getWriter().setSigFlags(PdfAnnotation.FLAGS_PRINT);
//			sap.setLayer2Font(new Font(Font.HELVETICA, 8, Font.BOLDITALIC, new Color(0, 0, 0)));
//			sap.setLayer4Text("");
//			sap.setExternalDigest(new byte[128], null, "RSA");
//			Calendar signDate = Calendar.getInstance();
//		    signDate.setTime(date);
//		    sap.setSignDate(signDate);
//		    sap.preClose();
//
//			InputStream inp = sap.getRangeStream();
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte buf2[] = new byte[8192];
//			int n;
//			while((n = inp.read(buf2)) > 0)
//				out.write(buf2, 0, n);
//
//			//byte base[] = Base64Util.encode(out.toByteArray()).getBytes();
//			//byte[] firma = Base64Util.decode(firmaDigital.firmar(base));
//			byte[] firma = Base64Util.decode(firmaDigital.firmar(out.toByteArray()));
//
//			CMSSignedData signedData = new CMSSignedData(firma);
//			Collection signerInfos = signedData.getSignerInfos().getSigners();
//			Iterator it = signerInfos.iterator();
//			if (it.hasNext()) {
//				SignerInformation signerInformation = (SignerInformation)it.next();
//				firma = signerInformation.getSignature();
//			}
//
//			PdfSignature dicPdf = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_DETACHED);
//		    if (sap.getReason() != null)
//		    	  dicPdf.setReason(sap.getReason());
//		    if (sap.getLocation() != null)
//		    	  dicPdf.setLocation(sap.getLocation());
//
//		    dicPdf.setDate(new PdfDate(sap.getSignDate()));
//		    sap.setCryptoDictionary(dicPdf);
//			PdfPKCS7 sig = sap.getSigStandard().getSigner();
//			sig.setExternalDigest(firma, null, "RSA");
//			PdfDictionary dic = new PdfDictionary();
//			dic.put(PdfName.CONTENTS, (new PdfString(sig.getEncodedPKCS1())).setHexWriting(true));
//			sap.close(dic);


			PdfSignatureAppearance sap = stp.getSignatureAppearance();

			sap.setLayer2Font(new Font(Font.HELVETICA, 8, Font.BOLDITALIC, new Color(0, 0, 0)));
			sap.setLayer4Text(" ");

			Calendar signCalendar = Calendar.getInstance();
			signCalendar.setTime(signDate);
		    sap.setSignDate(signCalendar);

		    sap.setReason(expediente.getString("ASUNTO"));
		    sap.setLocation("Es");

		    PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_DETACHED);

			if (sap.getReason() != null) {
				dic.setReason(sap.getReason());
			}
			if (sap.getLocation() != null) {
				dic.setLocation(sap.getLocation());
			}

			dic.setDate(new PdfDate(sap.getSignDate()));
			dic.setCert(x509cer.getEncoded());
			dic.setName(firmante);

			sap.setCryptoDictionary(dic);

			HashMap exc = new HashMap();
			exc.put(PdfName.CONTENTS, new Integer(SIGN_SIZE * 2 + 2));

			sap.preClose(exc);

			InputStream inp = sap.getRangeStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte buf2[] = new byte[8192];
			int n;
			while((n = inp.read(buf2)) > 0) {
				out.write(buf2, 0, n);
			}

			//byte base[] = Base64Util.encode(out.toByteArray()).getBytes();
			//byte[] firma = Base64Util.decode(firmaDigital.firmar(base));
			byte[] firma = Base64Util.decode(firmaDigital.firmar(out.toByteArray()));

			byte[] outc = new byte[SIGN_SIZE];
			System.arraycopy(firma, 0, outc, 0, firma.length);
			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
			sap.close(dic2);

			/* FIN - INCRUSTAR FIRMA EN PDF */

		} catch(Exception exc) {
			logger.error("Error en la firma del justificante [sign][Exception]", exc);
			throw new ISPACException(exc);
		}
		try{
			if (StringUtils.isEmpty(infoPagRDE) ){
				addNewSign(file, changeState);
			}
			else{
				addSign(file, changeState);
			}

			} catch(Exception exc) {
			logger.error("Error en la firma al actualizar los datos en la bbdd [sign][Exception]", exc);
			throw new ISPACException(exc);
		}
	}

	/**
	 * Obtener el formateador para la fecha de la firma.
	 * @param defaultSignDateFormatter Formateador por defecto.
	 *
	 * @return Formateador para la fecha de la firma con el formato establecido en la configuración (DATE_FORMAT)
	 * si existe y es correcto, en caso contrario, el formateador por defecto.
	 *
	 * @throws ISPACException Si se produce algún error.
	 */
	protected SimpleDateFormat getSignDateFormatter(SimpleDateFormat defaultSignDateFormatter) throws ISPACException {

		SimpleDateFormat singDateFormatter = null;

		String signDateFormat = ISPACConfiguration.getInstance().getProperty(DATE_FORMAT);
		if (StringUtils.isNotBlank(signDateFormat)) {

			try {
				singDateFormatter = new SimpleDateFormat(signDateFormat);
			} catch (Exception e) {
				logger.debug("Error en el formato configurado para la fecha de la firma", e);
			}
		}

		if (singDateFormatter == null) {
			singDateFormatter = defaultSignDateFormatter;
		}

		return singDateFormatter;
	}

	/**
	 * Almacena el id de transaccion generado por la plataforma afirma5 en el
	 * repositorio digital de documentos
	 *
	 * @param signDocument
	 * @param idTransaccion
	 * @throws ISPACException
	 */
	private void storeIdTransaccion(String idTransaccion) throws ISPACException {
		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		String signProperty = null;
		try {
			String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");

			if (logger.isInfoEnabled()){
				logger.info("Actualizando idTransaccion sobre el documento RDE: " + infoPagRDE);
			}

			int signerNumber = signDocument.getSign().size();

			connectorSession = genDocAPI.createConnectorSession();
			//Obtenemos el xml con las firmas adjuntadas antes de añadir la nueva
		    signProperty = genDocAPI.getDocumentProperty(connectorSession, infoPagRDE, "Firma");

		    XmlFacade xmlFacade = new XmlFacade(signProperty);
		    xmlFacade.set("/firmas/firma["+signerNumber+"]/@idTransaccion", idTransaccion);
		    genDocAPI.setDocumentProperty(connectorSession, infoPagRDE, "Firma", xmlFacade.toString() );
		    logger.debug(xmlFacade.toString());

		}
	    finally {
	    	if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}
	}

	protected void addGrayBand(File file, String pathFileTemp, String infoPagRDE, Date dateFirma, String firmante, String codCotejo)
			throws Exception {

		float margen = Float.parseFloat(ISPACConfiguration.getInstance()
				.getProperty(MARGIN_BAND));
		int band = Integer.parseInt(ISPACConfiguration.getInstance()
				.getProperty(BAND));
		float bandSize = Float.parseFloat(ISPACConfiguration.getInstance()
				.getProperty(SIZE_BAND));

		try {

			PdfReader readerInicial = new PdfReader(file.getAbsolutePath());
			int n = readerInicial.getNumberOfPages();
			int largo = (int) readerInicial.getPageSize(n).getHeight();
			int ancho = (int) readerInicial.getPageSize(n).getWidth();

			bandSize = Float.parseFloat(ISPACConfiguration.getInstance()
					.getProperty(SIZE_INICIAL_BAND));
			Image imagen = createBgImage();
			Document document = new Document();
			FileOutputStream fileOut = new FileOutputStream(
					FileTemporaryManager.getInstance().getFileTemporaryPath()
							+ "/" + pathFileTemp, true);
			PdfWriter writer = PdfWriter.getInstance(document, fileOut);
			document.open();
			Rectangle r = document.getPageSize();

			String sDateFirma = getSignDateFormatter(DATE_FORMATTER).format(dateFirma);

			for (int i = 1; i <= n; i++) {
				PdfImportedPage page = writer.getImportedPage(readerInicial, i);
				Image image = Image.getInstance(page);
				if (band == 1) {
					image.setAbsolutePosition(bandSize, 0.0F);
					image.scaleAbsoluteWidth(r.getWidth() - bandSize);
					image.scaleAbsoluteHeight(r.getHeight());
					imagen.setRotationDegrees(90F);
					document.add(image);
					if (imagen != null) {
						for (int j = 0; j < largo; j = (int) ((float) j + imagen
								.getWidth())) {
							imagen.setAbsolutePosition(0.0F, j);
							imagen.scaleAbsoluteHeight(bandSize);
							document.add(imagen);
						}
					}
					PdfContentByte over = writer.getDirectContent();
					//getImagen(sDateFirma, over, margen, true, margen, n, i, codCotejo);
					generateGrayBandImagen(sDateFirma, over, margen, true, margen, n, i, codCotejo, null);
				} else {
					image.setAbsolutePosition(0.0F, 0.0F);
					image.scaleAbsoluteWidth(r.getWidth());
					image.scaleAbsoluteHeight(r.getHeight() - bandSize);
					document.add(image);
					if (imagen != null) {
						for (int j = 0; j < ancho; j = (int) ((float) j + imagen
								.getWidth())) {
							imagen.setAbsolutePosition(j, (float) largo
									- bandSize);
							imagen.scaleAbsoluteHeight(bandSize);
							document.add(imagen);
						}

					}
					PdfContentByte over = writer.getDirectContent();
					//getImagen(sDateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo);
					generateGrayBandImagen(sDateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo, null);
				}
				document.newPage();
			}

			document.close();

		} catch (ISPACException e) {
			logger.error("Error al añadir la banda lateral al PDF", e);
			throw e;
		} catch (Exception exc) {
			logger.error("Error al añadir la banda lateral al PDF", exc);
			throw new ISPACException(exc);
		}
	}

	protected void getImagen(String dateFirma, PdfContentByte pdfContentByte,
			float margen, boolean vh, float x, int numberOfPages, int pageActual, String codCotejo) throws ISPACException {

		try {

			String font = ISPACConfiguration.getInstance().getProperty(FONT_BAND);
			String encoding = ISPACConfiguration.getInstance().getProperty(ENCODING_BAND);
			float fontSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(FONTSIZE_BAND));
			//float positionY = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(MARGIN_BAND));

			BaseFont bf = BaseFont.createFont(font, encoding, false);
			pdfContentByte.beginText();
			pdfContentByte.setFontAndSize(bf, fontSize);

			BufferedReader br = new BufferedReader(new FileReader(getDataFile()));
			String sCadena = null;
			int i = 0;
			while ((sCadena = br.readLine()) != null) {
				if (vh) {
					pdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, x, margen);
					if (i == 0) {
						pdfContentByte.showText(sCadena + codCotejo);
						//pdfContentByte.showText(sCadena + signDocument.getHash());
					} else if (i == 1) {
	                    //pdfContentByte.showText(sCadena + numberOfPages + " folios. Folio " + pageActual + " de " + numberOfPages + ".");
						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(clientContext.getLocale(), "pdf.band.pageInfo"), new String[] {
									String.valueOf(numberOfPages),
									String.valueOf(pageActual),
									String.valueOf(numberOfPages) }));
					} else {
						pdfContentByte.showText(sCadena);
					}
					i++;
					x += fontSize;
				} else {
	                pdfContentByte.setTextMatrix(margen, x);
	                if(i == 0) {
	                    //pdfContentByte.showText(sCadena + codCotejo);
	                	pdfContentByte.showText(sCadena + signDocument.getHash());
	                } else if (i == 1) {
	                    //pdfContentByte.showText(sCadena + numberOfPages + " folios. Folio " + pageActual + " de " + numberOfPages + ".");
						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(clientContext.getLocale(), "pdf.band.pageInfo"), new String[] {
									String.valueOf(numberOfPages),
									String.valueOf(pageActual),
									String.valueOf(numberOfPages) }));
	                } else {
	                    pdfContentByte.showText(sCadena);
	                }
	                i++;
	                x -= fontSize;
	            }
			}

			pdfContentByte.endText();

		} catch (Exception e) {
			logger.error("Error al componer la imagen de la banda lateral", e);
			throw new ISPACException(e);
		}
	}

	protected File getDataFile() throws ISPACException {

		// Ruta relativa del texto de la banda lateral
		String dataPath = ISPACConfiguration.getInstance().getProperty(PATH_TEXT);
		if (StringUtils.isBlank(dataPath)) {
			dataPath = DEFAULT_PDF_BG_DATA_PATH;
		}

		String basename = null;
		String ext = null;
		int dotIx = dataPath.lastIndexOf(".");
		if (dotIx > 0) {
			basename = dataPath.substring(0, dotIx);
			ext = dataPath.substring(dotIx);
		} else {
			basename = dataPath;
		}

		// Ruta absoluta del texto localizado de la banda lateral
		String dataFullPath = ConfigurationHelper.getConfigFilePath(basename + "_" + clientContext.getLocale().getLanguage() + ext);
		if (StringUtils.isBlank(dataFullPath)) {

			// Ruta absoluta del texto de la banda lateral
			dataFullPath = ConfigurationHelper.getConfigFilePath(dataPath);
		}

		if (logger.isInfoEnabled()) {
			logger.info("Texto de la banda lateraldel PDF: " + dataFullPath);
		}

		return new File(dataFullPath);
	}

	/**
	 * Obtiene la imagen a utilizar como fondo.
	 *
	 * @return Imagen de fondo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	protected Image createBgImage() throws ISPACException {

		// Ruta relativa de la imagen de fondo
		String imagePath = ISPACConfiguration.getInstance().getProperty(PATH_IMAGEN_BAND);
		if (StringUtils.isBlank(imagePath)) {
			imagePath = DEFAULT_PDF_BG_IMAGE_PATH;
		}

		try {

			// Ruta absoluta de la imagen de fondo
			String imageFullPath = ConfigurationHelper.getConfigFilePath(imagePath);
			if (logger.isInfoEnabled()) {
				logger.info("Imagen de fondo del PDF: " + imageFullPath);
			}

			// Construir la imagen de fondo
			Image image = Image.getInstance(imageFullPath);
			image.setAbsolutePosition(200F, 400F);
			return image;

		} catch (Exception e) {
			logger.error("Error al leer la imagen de fondo del PDF firmado: " + imagePath, e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Actualizar la referencia en el campo COD_COTEJO en la tabla de documentos
	 * @throws ISPACException
	 */
	protected static String addCodVerificacion(SignDocument signDocument, IClientContext clientContext) throws ISPACException {

		// Información del documento
		IItem itemDoc = signDocument.getItemDoc();

		// Identificador de la entidad
		String entityId = getEntityId();

		// Extraer id del documento
		String id = String.valueOf(itemDoc.getInt("ID"));

		// Componer el id con 4 dígitos
		id = StringUtils.leftPad(id, 5, "0");

		// Fecha del documento (2009-07-08 14:49:14.0)
		String date = itemDoc.getDate("FDOC").toString();

		// Longitud del identificador del documento
		int contIdDocumento = id.length();

		// Generar el código de verificación
//		String codVerificacion = date.substring(11,12)+//primer dígito de la hora
//			date.substring(5, 6)+//primer dígito del mes
//			date.substring(2, 3)+//tercer dígito del año
//			id.substring(2, 3)+//tercer dígito del id
//			entityId.substring(0, 1)+//primer dígito de la entidad
//
//			date.substring(12, 13)+//segundo dígito de la hora
//			date.substring(6, 7)+//segundo dígito del mes
//			date.substring(3, 4)+//cuarto dígito del año
//			id.substring(1, 2)+//segundo dígito del id
//			entityId.substring(1, 2)+//segundo dígito de la entidad
//
//			date.substring(14, 15)+//primer dígito de los minutos
//			date.substring(8, 9)+//primer dígito del día
//			date.substring(1, 2)+//segundo dígito del año
//			id.substring(3, 4)+//cuarto dígito del id
//			entityId.substring(2, 3)+//tercer dígito de la entidad
//
//			date.substring(15, 16)+//segundo dígito de los minutos
//			date.substring(9, 10)+//segundo dígito del día
//			date.substring(17, 18)+//primer dígito de los segundos
//			date.substring(18, 19)+//segundo dígito de los segundos
//			id.substring(0, 1);//primer dígito del id

		String codVerificacion = date.substring(11,12)+//primer dígito de la hora
		date.substring(5, 6)+//primer dígito del mes
		id.substring(contIdDocumento - 1, contIdDocumento)+//quinto dígito del id
		id.substring(contIdDocumento - 3, contIdDocumento - 2)+//tercer dígito del id
		entityId.substring(0, 1)+//primer dígito de la entidad

		date.substring(12, 13)+//segundo dígito de la hora
		date.substring(6, 7)+//segundo dígito del mes
		date.substring(3, 4)+//cuarto dígito del año
		id.substring(contIdDocumento - 4, contIdDocumento - 3)+//segundo dígito del id
		entityId.substring(1, 2)+//segundo dígito de la entidad

		date.substring(14, 15)+//primer dígito de los minutos
		date.substring(8, 9)+//primer dígito del día
		date.substring(2, 3)+//tercer dígito del año
		id.substring(contIdDocumento - 2, contIdDocumento - 1)+//cuarto dígito del id

		entityId.substring(2, 3)+//tercer dígito de la entidad
		date.substring(15, 16)+//segundo dígito de los minutos
		date.substring(9, 10)+//segundo dígito del día
		date.substring(17, 18)+//primer dígito de los segundos
		date.substring(18, 19)+//segundo dígito de los segundos
		id.substring(contIdDocumento - 5, contIdDocumento - 4);//primer dígito del id

		// Transformar el código de verificación
		String alfaCodVerificacion = getAlfaCodVerificacion(codVerificacion);

		// Generar el código de cotejo electrónico
		itemDoc.set("COD_COTEJO", alfaCodVerificacion);

		//Bloqueamos el documento para la edicion
//		itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
		itemDoc.store(clientContext);

		return alfaCodVerificacion;
	}


	/**
	 * Obtiene el Código de verificación alfanumérico
	 * @throws ISPACException
	 */
	public static String getAlfaCodVerificacion(String codVerificacion) throws ISPACException {

		String alfaCodVerificacion = codVerificacion;

		//Obtenemos el penúltimo dígito del Código de verificación
		String choice = codVerificacion.substring(18, 19);
		int iChoice = Integer.parseInt(choice);

		switch (iChoice){
		case 0:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 0, getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 3, getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 5, getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 7, getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 10, getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 12, getSubstitute(12, codVerificacion.charAt(12), iChoice));
			break;
		case 1:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 1, getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 7, getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 8, getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 11, getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 13, getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 15, getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 2:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 0, getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 6, getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 10, getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 12, getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 16, getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 17, getSubstitute(17, codVerificacion.charAt(17), iChoice));
			break;
		case 3:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 1, getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 7, getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 4:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 0, getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 5, getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 6, getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 12, getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 16, getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 5:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 7, getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 10, getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 15, getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 6:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 3, getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 6, getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 12, getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 17, getSubstitute(17, codVerificacion.charAt(17), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 7:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 6, getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 12, getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 16, getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 8:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 0, getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 7, getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 11, getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 9:
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 1, getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 2, getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 8, getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 13, getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 16, getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion=replaceChar(alfaCodVerificacion, 19, getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		default:
			throw new ISPACException ("Valor de iChoice incorrecto.");
		}

		return alfaCodVerificacion;
	}

	/**
	 * Obtiene el sustituto de un caracter para el Código de verificación alfanumérico
	 * @throws ISPACException
	 */
	public static char getSubstitute(int posicion, char digito, int choice) throws ISPACException {

		final char letras[]= {'a', 'b', 'c', 'd', 'e',
								'f', 'g', 'h', 'i', 'j',
								'k', 'l', 'm', 'n', 'o',
								'p', 'q', 'r', 's', 't',
								'u', 'v', 'w', 'x', 'y', 'z'};

		final List grupo1 = new ArrayList(7);
		final List grupo2 = new ArrayList(6);
		final List grupo3 = new ArrayList(3);

		grupo1.add(new Integer(1));
		grupo1.add(new Integer(3));
		grupo1.add(new Integer(6));
		grupo1.add(new Integer(8));
		grupo1.add(new Integer(11));
		grupo1.add(new Integer(13));
		grupo1.add(new Integer(16));

		grupo2.add(new Integer(2));
		grupo2.add(new Integer(5));
		grupo2.add(new Integer(7));
		grupo2.add(new Integer(12));
		grupo2.add(new Integer(15));
		grupo2.add(new Integer(19));

		grupo3.add(new Integer(0));
		grupo3.add(new Integer(10));
		grupo3.add(new Integer(17));

		int iDigito = Integer.parseInt(Character.toString(digito));
		Integer iPosicion = new Integer(posicion);

		if (grupo1.contains(iPosicion)){
			if (samePar(iDigito,choice)){
				//MAYUSCULAS
				return Character.toUpperCase(letras[iDigito]);
			}else{
				//minusculas
				return letras[iDigito];
			}

		}else if(grupo2.contains(iPosicion)){
			if (samePar(iDigito,choice)){
				//MAYUSCULAS
				return Character.toUpperCase(letras[10+iDigito]);
			}else{
				//minusculas
				return letras[10+iDigito];
			}
		}else if(grupo3.contains(iPosicion)){
			if (samePar(iDigito,choice)){
				//MAYUSCULAS
				return Character.toUpperCase(letras[20+iDigito]);
			}else{
				//minusculas
				return letras[20+iDigito];
			}
		}else{
			throw new ISPACException ("Valor de posicion incorrecto.");
		}

	}

	/**
	 * Reemplaza en la cadena recibida el carácter de la posición pos por el carácter car
	 * @throws ISPACException
	 */
	public static String replaceChar(String cadena, int pos, char car) throws ISPACException {

		String resultado = "";

		if (pos>=0 && pos<cadena.length()){
			int i=0;
			while (i<pos){
				resultado += cadena.charAt(i);
				i++;
			}
			resultado += car;
			i++;
			while (i>pos && i<cadena.length()){
				resultado += cadena.charAt(i);
				i++;
			}
		}

		return resultado;
	}

	/**
	 * Devuelve true si los dos números recibidos tienen la misma paridad
	 */
    public static boolean samePar(int num1, int num2){
        boolean p = false;

        if ( (num1%2==0 && num2%2==0) || (num1%2!=0 && num2%2!=0) )  {
            p = true;
        }

        return p;
    }

	/**
	 * Obtener el identificador de la entidad.
	 */
    protected static String getEntityId() {

    	String entityId = null;

		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			entityId = info.getOrganizationId();
		}

		return entityId;
    }

	@Override
	public ResultadoValidacionCertificado validateCertificate(String x509CertString)
			throws ISPACException {
		ResultadoValidacionCertificado resultado = new ResultadoValidacionCertificado();
		try {
			ServicioCriptoValidacion criptoValidacion = LocalizadorServicios.getServicioCriptoValidacion();
			ResultadoValidacion resultadoCriptovalidacion = criptoValidacion.validateCertificate(x509CertString);

			logger.info("Resultado validación certificado: " + resultadoCriptovalidacion.getMensajeValidacion());

			resultado.setMensajeValidacion(resultadoCriptovalidacion.getMensajeValidacion());
			resultado.setResultadoValidacion(resultadoCriptovalidacion.getResultadoValidacion());
		} catch (SigemException e) {
			logger.error("Error en la validación del certificado", e);
			throw new ISPACException(e);
		}
		return resultado;
	}

}
