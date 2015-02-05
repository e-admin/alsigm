package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.Firmante;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

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
public class Sigem23SignConnector extends SigemSignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Sigem23SignConnector.class);

	/**
	 * InfoPagRDE del documento original ya firmado
	 * antes de generar el nuevo documento con la nueva firma.
	 */
	protected String antInfoPagRDE = null;

	/**
	 * Constructor.
	 *
	 */
	public Sigem23SignConnector() {
		super();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.tram.sign.SigemSignConnector#generateGrayBandImagen(java.lang.String, com.lowagie.text.pdf.PdfContentByte, float, boolean, float, int, int, java.lang.String, java.util.List)
	 */
	public void generateGrayBandImagen(String dateFirma,
			PdfContentByte pdfContentByte, float margen, boolean vh, float x,
			int numberOfPages, int pageActual, String codCotejo, List signerList)
			throws ISPACException {

		getImagen(dateFirma, pdfContentByte, margen, vh, x, numberOfPages, pageActual, codCotejo, signerList);
	}

	/**
	 * Obtiene el documento , pdf si ya se hubiese firmado alguna vez o el original en otro caso.
	 * Añade los datos del firmante y genera o modifica  el pdf.
	 * @param changeState
	 * @throws ISPACException
	 */
	protected void generatePdfToSign(boolean changeState) throws ISPACException{

		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
		antInfoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");

		IInvesflowAPI invesflowAPI = clientContext.getAPI();

		// Obtener el documento original convertido a PDF
		File originalPDFFile = getFile(infoPag);

		try {

			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			String pathFileTemp = FileTemporaryManager.getInstance().put(originalPDFFile.getAbsolutePath(), ".pdf");

			// Fecha de la firma del PDF
			ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
			Date date = servicioTiempos.getCurrentDate();

			// Código de cotejo
			String codCotejo = signDocument.getItemDoc().getString("COD_COTEJO");
			if (StringUtils.isBlank(codCotejo)) {
				codCotejo = addCodVerificacion(signDocument, clientContext);
			}

			// Lista de firmantes
			List signerList = getSignerList(signDocument);

			// Añadir la banda gris lateral al documento PDF
			addGrayBand(originalPDFFile, pathFileTemp, date, signDocument, codCotejo, signerList);

			// Obtenemos la información del expediente
			IItem expediente = invesflowAPI.getEntitiesAPI().getExpedient(signDocument.getNumExp());

			/* INICIO - INCRUSTAR FIRMA EN PDF */
			PdfReader reader = new PdfReader(FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + pathFileTemp);
			FileOutputStream fout = new FileOutputStream(originalPDFFile.getAbsolutePath());
			PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');

			PdfSignatureAppearance sap = stp.getSignatureAppearance();

			sap.setLayer2Font(new Font(Font.HELVETICA, 8, Font.BOLDITALIC, new Color(0, 0, 0)));
			sap.setLayer4Text(" ");

			Calendar signDate = Calendar.getInstance();
			signDate.setTime(date);
		    sap.setSignDate(signDate);

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

			byte[] firma = Base64Util.decode(firmaDigital.firmar(out.toByteArray()));

			byte[] outc = new byte[SIGN_SIZE];
			System.arraycopy(firma, 0, outc, 0, firma.length);
			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
			sap.close(dic2);

			/* FIN - INCRUSTAR FIRMA EN PDF */

			if (StringUtils.isBlank(antInfoPagRDE)) {
				addNewSign(originalPDFFile, changeState);
			} else {
				addSign(originalPDFFile, changeState);
			}

			doAfterSign();

		} catch (Exception e) {

			doErrorSign();

			logger.error("Error en la firma del documento", e);
			throw new ISPACException(e);
		}
	}

	protected void doAfterSign() throws ISPACException {

		if (StringUtils.isNotBlank(antInfoPagRDE)) {
			// En función del gestor documental (SharePoint) puede darse el caso de que al crear
			// el nuevo documento con la nueva firma el documento no se cree como nuevo sino que
			// se actualice, con lo que el infoPagRDE será el mismo y no habrá que borrar
			if (!StringUtils.equals(antInfoPagRDE, signDocument.getItemDoc().getString("INFOPAG_RDE"))) {
				deleteDocument(antInfoPagRDE);
			}
		}
	}

	protected void doErrorSign() throws ISPACException {

		if (StringUtils.isNotBlank(antInfoPagRDE)) {
			if (!StringUtils.equals(antInfoPagRDE, signDocument.getItemDoc().getString("INFOPAG_RDE"))) {
				// Eliminar el nuevo documento
				deleteDocument(signDocument.getItemDoc().getString("INFOPAG_RDE"));
			}
		}
	}

	protected void addGrayBand(File file, String pathFileTemp, Date signDate, SignDocument signDocument, String codCotejo, List signerList)
			throws Exception {

		float margen = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(MARGIN_BAND));
		int band = Integer.parseInt(ISPACConfiguration.getInstance().getProperty(BAND));
		float bandSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(SIZE_INICIAL_BAND));
		float signerLineSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty("SIGNER_LINE_SIZE", "10F"));

		try {

			PdfReader readerInicial = new PdfReader(file.getAbsolutePath());
			int n = readerInicial.getNumberOfPages();
			int largo = (int) readerInicial.getPageSize(n).getHeight();
			int ancho = (int) readerInicial.getPageSize(n).getWidth();

			bandSize += (signerLineSize * signerList.size());

			Image imagen = createBgImage();
			Document document = new Document();

			FileOutputStream fileOut = new FileOutputStream(
					FileTemporaryManager.getInstance().getFileTemporaryPath()
							+ "/" + pathFileTemp, true);
			PdfWriter writer = PdfWriter.getInstance(document, fileOut);
			document.open();
			Rectangle r = document.getPageSize();

			String dateFirma = getSignDateFormatter(DATE_FORMATTER).format(signDate);

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
					//getImagen(dateFirma, over, margen, true, margen, n, i, codCotejo, signerList);
					generateGrayBandImagen(dateFirma, over, margen, true, margen, n, i, codCotejo, signerList);
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
					//getImagen(dateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo, signerList);
					generateGrayBandImagen(dateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo, signerList);
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
			float margen, boolean vh, float x, int numberOfPages,
			int pageActual, String codCotejo, List signerList) throws ISPACException {

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

						if ((signerList != null) && (!signerList.isEmpty())) {
							for (int signerCont = 0; signerCont < signerList.size(); signerCont++) {
								x += fontSize;
								pdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, x, margen);
								pdfContentByte.showText((String) signerList.get(signerCont));
							}
						}
					} else if (i == 1) {
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
	                    pdfContentByte.showText(sCadena + codCotejo);

	                    if ((signerList != null) && (!signerList.isEmpty())) {
							for (int signerCont = 0; signerCont < signerList.size(); signerCont++) {
								x -= fontSize;
								pdfContentByte.setTextMatrix(margen, x);
								pdfContentByte.showText((String) signerList.get(signerCont));
							}
	                    }
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

	protected List getSignerList(SignDocument signDocument) throws ISPACException {

		List signerList = new ArrayList();

		// Obtener el contenido firmado para validar las firmas
		String contenidoFirmado = getContenidoFirmado(signDocument);

		// Firmantes
		List signers = new ArrayList();

		// Obtener los firmantes anteriores (circuito de firmas)
		List previousSigners = getPreviousSigners(signDocument, contenidoFirmado);
		if (previousSigners != null) {
			signers.addAll(previousSigners);
		}

//		if (!CollectionUtils.isEmpty(previousSigners)) {
//			for (int i = 0; i < previousSigners.size(); i++) {
//				Firmante signer = (Firmante) previousSigners.get(i);
//				signerList.add(getSignerName(signer));
//			}
//		}

//		List certificates = signDocument.getSignCertificate();
//		if (!CollectionUtils.isEmpty(certificates)) {
//
//			try {
//
//				// Servicio para obtener la información de los certificados
//				ServicioCriptoValidacion servicioCriptoValidacion = LocalizadorServicios.getServicioCriptoValidacion();
//
//				for (int i = 0; i < certificates.size(); i++) {
//					String certificateB64 = (String) certificates.get(i);
//					if (StringUtils.isNotBlank(certificateB64)) {
//						ResultadoValidacion validacion = servicioCriptoValidacion.validateCertificate(certificateB64);
//						if ((validacion == null) || ResultadoValidacion.VALIDACION_ERROR.equals(validacion.getResultadoValidacion())){
//							throw new ISPACException(validacion.getMensajeValidacion());
//						}
//
//						InfoCertificado infoCert = validacion.getCertificado();
//						if (infoCert != null) {
//							signerList.add(getSignerName(infoCert));
//						}
//					}
//				}
//
//			} catch (ISPACException e) {
//				logger.error("Error al obtener la lista de firmantes", e);
//				throw e;
//			} catch (Exception e) {
//				logger.error("Error al obtener la lista de firmantes", e);
//				throw new ISPACException(e);
//			}
//		}

		// Se obtiene la información del firmante a partir de la firma y no del
		// certificado, para homogeneizar los nombres de los firmantes
		List signList = signDocument.getSign();
		if (!CollectionUtils.isEmpty(signList)) {
			for (int i = 0; i < signList.size(); i++) {
				List currentSigners = getSigners((String) signList.get(i), contenidoFirmado);
				if (currentSigners != null) {
					signers.addAll(currentSigners);
				}
			}
		}

		// Añadir la cadena de cada firmante
		if (!CollectionUtils.isEmpty(signers)) {
			for (int i = 0; i < signers.size(); i++) {
				Firmante signer = (Firmante) signers.get(i);
				signerList.add(getSignerName(signer));
			}
		}

		return signerList;
	}

	protected String getContenidoFirmado(SignDocument signDocument) throws ISPACException {

		String contenido = null;

		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;

    	try {

    		connectorSession = genDocAPI.createConnectorSession();

    		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		genDocAPI.getDocument(connectorSession, infoPag, baos);
    		contenido = new String(Base64.encode(Base64.encode(baos.toByteArray())));

		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}

    	return contenido;
	}

	protected List getPreviousSigners(SignDocument signDocument, String contenidoFirmado) throws ISPACException {

		List signerList = new ArrayList();

		String infoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");
		if (StringUtils.isNotBlank(infoPagRDE)) {

			IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
			Object connectorSession = null;

	    	try {

	    		connectorSession = genDocAPI.createConnectorSession();

	    		String infoFirmas = genDocAPI.getDocumentProperty(connectorSession, infoPagRDE, "Firma");
	    		XmlFacade xmlInfoFirmas = new XmlFacade(infoFirmas);

	    		List firmas = xmlInfoFirmas.getList("/" + SignDocument.TAG_FIRMAS + "/" + SignDocument.TAG_FIRMA);
	    		if (!CollectionUtils.isEmpty(firmas)) {
	    			for (int i = 0; i < firmas.size(); i++) {
	    				String firmaB64 = (String) firmas.get(i);
						signerList.addAll(getSigners(firmaB64, contenidoFirmado));
	    			}
	    		}

			} finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
	    	}
		}

		return signerList;
	}

	protected List getSigners(String firmaB64, String contentBase64) throws ISPACException {

		List signerList = new ArrayList();

		try {
			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			ResultadoValidacionFirma resultado = firmaDigital.validarFirma(firmaB64.getBytes(), contentBase64.getBytes());
			List signers = resultado.getSigners();
			if (!CollectionUtils.isEmpty(signers)) {
				for (int j = 0; j < signers.size(); j++) {
					Firmante signer = (Firmante) signers.get(j);
					if (signer != null) {
						signerList.add(signer);
					}
				}
			}
		} catch (Exception e) {
			logger.error("No se han podido obtener los firmantes del documento", e);
			throw new ISPACException(e);
		}

		return signerList;
	}

//	protected String getSignerName(InfoCertificado infoCert) {
//
//		StringBuffer firmante = new StringBuffer();
//
//		if (StringUtils.isNotBlank(infoCert.getName())) {
//
//			if (StringUtils.isNotBlank(infoCert.getNif())) {
//				firmante.append(infoCert.getNif()).append(" - ");
//			}
//
//			firmante.append(infoCert.getName());
//
//			if (StringUtils.isNotBlank(infoCert.getLastname1())){
//				firmante.append(" ").append(infoCert.getLastname1());
//			}
//
//			if (StringUtils.isNotBlank(infoCert.getLastname2())){
//				firmante.append(" ").append(infoCert.getLastname2());
//			}
//
//		} else if (StringUtils.isNotBlank(infoCert.getCorporateName())) {
//
//			if (StringUtils.isNotBlank(infoCert.getCif())) {
//				firmante.append(infoCert.getCif()).append(" - ");
//			}
//
//			firmante.append(infoCert.getCorporateName());
//		}
//
//		return firmante.toString();
//	}

	protected String getSignerName(Firmante signer) {

		StringBuffer firmante = new StringBuffer();

		if (StringUtils.isNotBlank(signer.getNif())) {
			firmante.append(signer.getNif());
		}

		if (StringUtils.isNotBlank(signer.getName())) {
			if (firmante.length() > 0) {
				firmante.append(" - ");
			}
			firmante.append(signer.getName());
		}

		return firmante.toString();
	}

	protected void deleteDocument(String uid) throws ISPACException {

		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;

    	try {

    		connectorSession = genDocAPI.createConnectorSession();

    		// Eliminar el documento fisico del gestor documental
            genDocAPI.deleteDocument(connectorSession, uid);

		} catch(Exception e) {
			logger.warn("No se ha podido eliminar el documento firmado anterior", e);
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}
	}
}
