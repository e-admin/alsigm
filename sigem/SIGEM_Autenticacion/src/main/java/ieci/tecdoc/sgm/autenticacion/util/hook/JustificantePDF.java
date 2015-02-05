package ieci.tecdoc.sgm.autenticacion.util.hook;

import ieci.tecdoc.sgm.autenticacion.MessagesUtil;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.vo.ReceiptVO;
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestioncsv.CodigosAplicacionesConstants;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

import sun.misc.BASE64Encoder;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
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

public class JustificantePDF implements FirmaExt {

	/**
	 *
	 */
	protected static final String DEFAULT_LOCATION = "Es";
	protected static final String INFODOCUMENTO_CSV_TIPO_MIME = "application/pdf";
	protected static final String INFODOCUMENTO_CSV_NOMBRE = "Justificante de Registro.pdf";

	// Parámetros de configuración para el módulo de CSV
	protected static final String CSV_DOCUMENTO_JUSTIFICANTE_REGISTRO_DESCRIPTION_KEY = "csv.documento.justificanteRegistro.description";

	public static final String JUSTIFICANTE_BANDA_LATERAL_BACKGROUND_IMAGE_DEFAULT = "/ieci/tecdoc/sgm/autenticacion/resources/fondo.gif";

	// Recursos para la banda lateral a incluir en el justificante de registro
	protected static final String JUSTIFICANTE_RESOURCE_DOCUMENTO_FIRMADO_POR_KEY = "justificante.confirmacion.documentoFirmadoPor";
	protected static final String JUSTIFICANTE_RESOURCE_FECHA_FIRMA_POR_KEY = "justificante.confirmacion.fechaFirma";
	protected static final String JUSTIFICANTE_RESOURCE_CSV_KEY = "justificante.confirmacion.csv";
	protected static final String JUSTIFICANTE_RESOURCE_PAGINACION_KEY = "justificante.confirmacion.paginacion";
	protected static final String JUSTIFICANTE_RESOURCE_FIRMA_RAZON_KEY="justificante.confirmacion.firma.razon";

	private static final Logger logger = Logger.getLogger(JustificantePDF.class);

	/**
	 * Tamaño máximo en bytes de la firma del PDF.
	 */
	private static final int SIZE = 32000;

	public JustificantePDF() {
	}

	/**
	 * Genera un justificante de registro firmado y obtiene el código CSV
	 * asociado a este documento. Después, añade una banda lateral con el CSV.
	 *
	 * @param data
	 *            Plantilla pdf con el justificante de registro.
	 * @param additionalInfo
	 *            Solicitud de registro.
	 * @param certificate
	 *            Certificado a utilizar para la firma del justificante.
	 * @return El justificante de registro mencionado.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public ReceiptVO sign(InputStream data, String request, String additionalInfo,
			CertificadoFirmaX509Info certificate) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("sign(InputStream, String, String, CertificadoFirmaX509Info) - start");
		}

		/*
		 * 1.- Generar el justificante sin firmar
		 *
		 * 2.- Generar el CSV del justificante
		 *
		 * 3.- Añadir la banda lateral con el CSV
		 *
		 * 4.- Añadir la firma electrónica al justificante
		 */

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfStamper pdfStamper = null;
		PdfReader pdfReader = null;
		ByteArrayOutputStream receipt;
		Rectangle signatureRectangle = new Rectangle(230, 45, 380, 75);
		PdfSignatureAppearance pdfSignatureAppearance;

		ServicioFirmaDigital servicioFirmaDigital = LocalizadorServicios.getServicioFirmaDigital();
		ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
		ServicioGestionCSV servicioGestionCSV = LocalizadorServicios.getServicioGestionCSV();

		// Obtener la entidad:
		String idEntidad = MultiEntityContextHolder.getEntity();
		logger.debug("JustificantePDF.sign: Entidad obtenida del objeto MultiEntityContextHolder: ["
				+ idEntidad + "]");
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);

		ReceiptVO receiptVO = new ReceiptVO();

		Calendar signDate = Calendar.getInstance();
		signDate.setTime(servicioTiempos.getCurrentDate());

		byte[] bytesFirma = null;

		try {
			/*
			 * 1.- Generar el justificante sin firmar
			 */
			receipt = generateReceipt(data, request, additionalInfo);



			if (logger.isDebugEnabled()) {
				logger.debug("JustificantePDF.sign: Generado el justificante sin firmar satisfactoriamente.");
			}

			/*
			 * 2.- Generar el CSV
			 */
			InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
			infoDocumentoForm
					.setCodigoAplicacion(CodigosAplicacionesConstants.REGISTRO_TELEMATICO_CODE);
			infoDocumentoForm.setDisponible(true);
			// La fecha de caducidad es null porque nunca caduca
			infoDocumentoForm.setFechaCaducidad(null);
			infoDocumentoForm.setFechaCreacion(signDate.getTime());
			infoDocumentoForm.setNombre(INFODOCUMENTO_CSV_NOMBRE);
			infoDocumentoForm.setTipoMime(INFODOCUMENTO_CSV_TIPO_MIME);

			// Generar el CSV
			InfoDocumentoCSV infoDocumento = servicioGestionCSV.generarCSV(entidad,
					infoDocumentoForm);

			if (logger.isDebugEnabled()) {
				logger.debug("JustificantePDF.sign: Generado el CSV: [" + infoDocumento.getCsv()
						+ "] para el documento ID: [" + infoDocumento.getId() + "].");
			}

			receiptVO.setCsv(infoDocumento.getCsv());
			receiptVO.setContent(receipt.toByteArray());

			/*
			 * 3.- Generar la banda lateral con el CSV
			 */
			receiptVO = incluirBandaLateralDocumentoPDF(signDate.getTime(), receiptVO);
			if (logger.isDebugEnabled()) {
				logger.debug("JustificantePDF.sign: Generada la banda lateral satisfactoriamente.");
			}

			/*
			 * 4.- Generar la firma
			 */
			pdfReader = new PdfReader(receiptVO.getContent());

			pdfStamper = PdfStamper.createSignature(pdfReader, output, '\0');
			pdfSignatureAppearance = pdfStamper.getSignatureAppearance();

			pdfSignatureAppearance.setLayer2Font(new Font(Font.HELVETICA, 12, Font.BOLDITALIC, new Color(0, 0, 0)));
			pdfSignatureAppearance.setLayer2Text(" ");
			pdfSignatureAppearance.setLayer4Text(" ");
			pdfSignatureAppearance.setVisibleSignature(signatureRectangle, pdfReader.getNumberOfPages(), null);


			pdfSignatureAppearance.setSignDate(signDate);

			Locale locale = new Locale("ES", "es");
			String justificanteConfirmacionMessage = MessagesUtil.getMessage(
					JUSTIFICANTE_RESOURCE_FIRMA_RAZON_KEY, new Object[] {},
					locale);
			pdfSignatureAppearance.setReason(justificanteConfirmacionMessage);
			pdfSignatureAppearance.setLocation(DEFAULT_LOCATION);

			PdfSignature pdfSignature = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_DETACHED);

			if (pdfSignatureAppearance.getReason() != null)
				pdfSignature.setReason(pdfSignatureAppearance.getReason());
			if (pdfSignatureAppearance.getLocation() != null)
				pdfSignature.setLocation(pdfSignatureAppearance.getLocation());

			pdfSignature.setDate(new PdfDate(pdfSignatureAppearance.getSignDate()));

			pdfSignatureAppearance.setCryptoDictionary(pdfSignature);

			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(SIZE * 2 + 2));

			pdfSignatureAppearance.preClose(exc);

			byte[] datosAFirmar = streamToByteArray(pdfSignatureAppearance.getRangeStream());

			bytesFirma = Base64Util.decode(servicioFirmaDigital.firmar(datosAFirmar));

			byte[] outc = new byte[SIZE];
			System.arraycopy(bytesFirma, 0, outc, 0, bytesFirma.length);
			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
			pdfSignatureAppearance.close(dic2);
			if (logger.isDebugEnabled()) {
				logger.debug("JustificantePDF.sign: Generada la firma electrónica");
			}

		} catch (Exception e) {

			// Si se produce algún error
			// anular el CSV si ya fue generado
			if (receiptVO.getCsv() != null) {

				// Eliminar el CSV
				try {
					InfoDocumentoCSV infoDocumentoCSV = servicioGestionCSV.getInfoDocumentoByCSV(entidad, receiptVO.getCsv());
					if (infoDocumentoCSV != null) {
						// Eliminar la información de CSV del documento
						servicioGestionCSV.deleteInfoDocumento(entidad, infoDocumentoCSV.getId());
					}
				} catch (Exception ex) {
					logger.error("JustificantePDF.sign: Error al eliminar el CSV", ex);
				}
			}

			logger.error("JustificantePDF.sign: Error en la firma del justificante", e);
			throw e;
		} finally {
			// if (stp != null) stp.close();
			if (pdfReader != null)
				pdfReader.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sign(InputStream, String, String, CertificadoFirmaX509Info) - end");
		}

		receiptVO.setContent(output.toByteArray());

		return receiptVO;
	}

	/**
	 * Genera un justificante de registro sin firmar
	 *
	 * @param receipt
	 *            Plantilla pdf con el justificante de registro.
	 * @param additionalInfo
	 *            Solicitud de registro.
	 * @return El justificante de registro mencionado.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	private static ByteArrayOutputStream generateReceipt(InputStream receipt, String request,
			String additionalInfo) throws Exception {
		ByteArrayInputStream byteArray = new ByteArrayInputStream(Goodies.fromStrToUTF8(request));
		JRXmlDataSource conn = new JRXmlDataSource(byteArray, "Solicitud_Registro");
		HashMap params = new HashMap();
		int i = additionalInfo.lastIndexOf(System.getProperty("file.separator"));
		String rutaPlantilla = additionalInfo.substring(0, i)
				+ System.getProperty("file.separator");
		params.put("SUBREPORT_DIR", rutaPlantilla);
		JasperPrint objJasperPrint = JasperFillManager.fillReport(receipt, params, conn);
		byte[] pdf = JasperExportManager.exportReportToPdf(objJasperPrint);
		ByteArrayOutputStream output = new ByteArrayOutputStream(pdf.length);
		output.write(pdf);
		return output;
	}

	/**
	 * Añade una banda lateral con el CSV en el documento pdf del justificante
	 *
	 * @param fechaFirma
	 *            Fecha en la que se genera el CSV
	 * @param receiptVO
	 *            Contenido del justificante del documento con su CSV
	 * @return Contenido del justificante del documento con su CSV
	 */
	private static ReceiptVO incluirBandaLateralDocumentoPDF(Date fechaFirma, ReceiptVO receiptVO) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("incluirBandaLateralDocumentoPDF(Date, ReceiptVO) - start");
		}

		float bandSize = 40F;
		float bandMargin = 10F;
		PdfReader reader = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		InfoCertificado infoCertificado = null;

		try {

			// Obtenemos la información del certificado
			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			CertificadoX509Info certificadoX509Info = firmaDigital.getcertInfo();

			if (certificadoX509Info != null) {
				ServicioCriptoValidacion servicioCriptoValidacion = LocalizadorServicios
						.getServicioCriptoValidacion();

				BASE64Encoder encoder = new BASE64Encoder();

				String psB64Certificate = encoder.encodeBuffer(certificadoX509Info.getCertificate()
						.getEncoded());

				ResultadoValidacion resultado = servicioCriptoValidacion
						.validateCertificate(psB64Certificate);

				if (resultado.getResultadoValidacion() == resultado.VALIDACION_ERROR) {
					logger.info("Justificante de Registro - incluirBandaLateralDocumentoPDF: El certificado de la firma no es valido. Certificado: ["
							+ certificadoX509Info.getCertificate() + "]");
				} else {
					infoCertificado = resultado.getCertificado();
				}
			}

			// Leer el documento PDF de justificante de registro
			reader = new PdfReader(receiptVO.getContent());
			int numberOfPages = reader.getNumberOfPages();
			int pageHeight = (int) reader.getPageSize(numberOfPages).getHeight();

			// Documento PDF con la banda lateral
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, output);

			// Abrir el documento PDF para incluir el documento de justificante
			// de registro junto con la banda lateral
			document.open();

			// Tamaño de página del documento PDF con la banda lateral
			Rectangle documentPageSize = document.getPageSize();

			// Locale para los recursos a incluir en la banda lateral
			Locale locale = new Locale("ES", "es");

			// Incluir la banda lateral en todas las páginas del documento
			// original
			for (int i = 1; i <= numberOfPages; i++) {
				// Página del documento original
				PdfImportedPage importedPage = writer.getImportedPage(reader, i);
				// Escalar la imagen de la página original para dejar espacio
				// para la banda lateral
				Image imageImportedPage = Image.getInstance(importedPage);
				imageImportedPage.setAbsolutePosition(bandSize, 0.0F);
				imageImportedPage.scaleAbsoluteWidth(documentPageSize.getWidth() - bandSize);
				imageImportedPage.scaleAbsoluteHeight(documentPageSize.getHeight());
				// Página original escalada en el documento final
				document.add(imageImportedPage);

				// Obtener la imagen de fondo para la banda lateral
				Image imageBandBackground = obtenerImagenFondoBandaLateral();

				// Establecer el fondo de la banda lateral
				if (imageBandBackground != null) {

					// Rotar la imagen de fondo para el lateral
					imageBandBackground.setRotationDegrees(90F);

					for (int yPosition = 0; yPosition < pageHeight; yPosition = (int) ((float) yPosition + imageBandBackground
							.getWidth())) {

						// Incluir repetidamente la imagen de fondo una
						// detrás de otra hasta completar el alto de la
						// página y escalar al ancho de la banda lateral
						imageBandBackground.setAbsolutePosition(0.0F, yPosition);
						imageBandBackground.scaleAbsoluteHeight(bandSize);
						// Fondo de la banda escalado en el documento final
						document.add(imageBandBackground);
					}
				}

				// Contenido del PDF sobre el que se escribirá el texto de la
				// banda lateral
				PdfContentByte overPdfContentByte = writer.getDirectContent();
				overPdfContentByte.beginText();

				// Fuente para el texto
				String font = "Helvetica";
				String encoding = BaseFont.WINANSI;
				float fontSize = 8F;

				BaseFont baseFont = BaseFont.createFont(font, encoding, false);
				overPdfContentByte.setFontAndSize(baseFont, fontSize);

				float xPosition = bandMargin;

				overPdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, xPosition, bandMargin);

				// Establecer el texto de la banda lateral

				// Certificado con el que se firma el justificante:
				if (infoCertificado != null) {
					overPdfContentByte.showText(MessagesUtil.getMessage(
							JUSTIFICANTE_RESOURCE_DOCUMENTO_FIRMADO_POR_KEY, new Object[] {
									StringUtils.defaultIfEmpty(infoCertificado.getName(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getNif(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getCorporateName(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getCif(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getIssuer(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getSerialNumber(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getSubject(), "")
							  },
							locale));

					xPosition += fontSize;
					overPdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, xPosition, bandMargin);

				}

				// Fecha junto con el certificado con el que se firma el justificante:
				if (infoCertificado != null) {
					overPdfContentByte.showText(MessagesUtil.getMessage(
							JUSTIFICANTE_RESOURCE_FECHA_FIRMA_POR_KEY, new Object[] {
									StringUtils.defaultIfEmpty(infoCertificado.getName(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getNif(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getCorporateName(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getCif(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getIssuer(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getSerialNumber(), ""),
									StringUtils.defaultIfEmpty(infoCertificado.getSubject(), ""),
									formatearFechaFirma(fechaFirma)
							  },
							locale));
				} else {
					overPdfContentByte.showText(MessagesUtil.getMessage(
							JUSTIFICANTE_RESOURCE_FECHA_FIRMA_POR_KEY,
							new Object[] {"", "", "", "", "", "", "", formatearFechaFirma(fechaFirma) }, locale));
				}

				// CSV:
				xPosition += fontSize;
				overPdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, xPosition, bandMargin);
				overPdfContentByte.showText(MessagesUtil.getMessage(JUSTIFICANTE_RESOURCE_CSV_KEY,
						new Object[] { receiptVO.getCsv() }, locale));

				// Paginación:
				xPosition += fontSize;
				overPdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, xPosition, bandMargin);

				overPdfContentByte.showText(MessagesUtil.getMessage(
						JUSTIFICANTE_RESOURCE_PAGINACION_KEY, new Object[] { i, numberOfPages },
						locale));

				overPdfContentByte.endText();

				// Indicador de fin de página para añadir una nueva página en el
				// documento PDF final con la banda lateral
				document.newPage();
			}

			document.close();

			// Actualizamos el contenido del documento
			receiptVO.setContent(output.toByteArray());

		} catch (Exception e) {

			logger.error("JustificantePDF.incluirBandaLateralDocumentoPDF: Error al generar la banda lateral del justificante", e);
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("incluirBandaLateralDocumentoPDF(Date, ReceiptVO) - end");
		}
		return receiptVO;
	}

	/**
	 * Formatear la fecha de emisión de la firma a cadena.
	 *
	 * @param fechaFirma
	 *            Fecha de emisión de la firma.
	 * @return Fecha formateada.
	 */
	protected static String formatearFechaFirma(Date fechaFirma) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");

		return simpleDateFormat.format(fechaFirma);
	}

	/**
	 * Obtener la imagen de fondo para la banda lateral del justificante de
	 * registro. Inicialmente, la imagen se busca a partir de la configuración
	 * (subdirectorio de recursos e imagen), y si ésta no existe, se carga una
	 * imagen por defecto.
	 *
	 * @return Imagen para el fondo de la banda lateral si existe, en caso
	 *         contrario, devuelve null.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	protected static Image obtenerImagenFondoBandaLateral() throws Exception {

		Image imageBandBackground = null;

		InputStream inputStream = JustificantePDF.class
				.getResourceAsStream(JUSTIFICANTE_BANDA_LATERAL_BACKGROUND_IMAGE_DEFAULT);

		byte[] bytes = IOUtils.toByteArray(inputStream);
		// URL url = new
		// URL(JUSTIFICANTE_BANDA_LATERAL_BACKGROUND_IMAGE_DEFAULT);
		imageBandBackground = Image.getInstance(bytes);

		return imageBandBackground;
	}

	private static byte[] streamToByteArray(InputStream stream) throws Exception {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		byte buffer1[] = new byte[8192];
		int c = 0;
		while ((c = stream.read(buffer1)) > 0) {
			byteArray.write(buffer1, 0, c);
		}
		byteArray.flush();
		return byteArray.toByteArray();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	// Members
	// protected LocalizadorConectores localizadorConectores;

	// protected FirmaDigitalService fwktd_signature_firmaDigitalService;

	protected static ApplicationContext applicationContext;

	protected Properties justificanteRegistroConfigurationProperties;

	protected ResourceBundleMessageSource justificanteRegistroMessageSource;

}