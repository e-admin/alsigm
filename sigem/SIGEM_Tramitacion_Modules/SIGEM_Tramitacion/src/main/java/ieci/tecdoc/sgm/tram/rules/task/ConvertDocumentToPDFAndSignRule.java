package ieci.tecdoc.sgm.tram.rules.task;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.converter.DocumentConverter;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.sign.ISignConnector;
import ieci.tdw.ispac.ispaclib.sign.SignConnectorFactory;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.impl.states.TaskState;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestioncsv.CodigosAplicacionesConstants;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;
import ieci.tecdoc.sgm.tram.rules.helpers.RuleHelper;
import ieci.tecdoc.sgm.tram.sign.ISigemSignConnector;
import ieci.tecdoc.sgm.tram.sign.SigemSignConnector;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
 * Convierte el documento asociado al trámite a PDF y lo firma.
 * validando previamente que
 * - se hayan generado documentos para el trámite actual
 * - no haya ningún documento al que no se le haya aplicado la función resumen
 *   entre los documentos que no sean los del trámite actual
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ConvertDocumentToPDFAndSignRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger.getLogger(ConvertDocumentToPDFAndSignRule.class);

	/**
	 * Tamanio que se reserva para la firma.
	 */
	public static final int SIGN_SIZE = 64000;

	/**
	 * Ruta por defecto de la imagen de fondo del PDF.
	 */
	public static final String DEFAULT_PDF_BG_IMAGE_PATH = "firma/fondo.gif";

	/**
	 * Ruta por defecto del fichero con el texto de la banda lateral del PDF.
	 */
	public static final String DEFAULT_PDF_BG_DATA_PATH = "firma/datosFirma.gif";

	/**
	 * Tipo mime para el CSV
	 */
	protected static final String INFODOCUMENTO_CSV_TIPO_MIME = "application/pdf";

	/**
	 * Nombre del fichero de recursos de la firma.
	 */
	protected static String BUNDLE_NAME = "ieci.tecdoc.sgm.tram.sign.SignConnectorMessages";

	/**
	 * Formato para las fechas.
	 */
	public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#cancel(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#execute(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();

		// CSV para el documento
		String codCotejo = null;
		ServicioGestionCSV servicioGestionCSV = null;

		try {

			// Obtener el documento del trámite actual
			IItemCollection itemcol = invesflowAPI.getEntitiesAPI().getEntities(SpacEntities.SPAC_DT_DOCUMENTOS, rulectx.getNumExp(), " ID_TRAMITE = " + rulectx.getTaskId());
			if (itemcol.next()) {

				IItem documento = itemcol.value();
				String docref = documento.getString("INFOPAG");
				if (StringUtils.isNotBlank(docref)) {

					// Obtener el documento original convertido a PDF
					File originalPDFFile = getDocumentInPDF(invesflowAPI, docref);

					ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
					String pathFileTemp = FileTemporaryManager.getInstance().put(originalPDFFile.getAbsolutePath(), ".pdf");

					// Fecha de la firma del PDF
					ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
					Date date = servicioTiempos.getCurrentDate();

					// Código de cotejo
					// haciendo uso del servicio de gestión de CSV de generación y consulta
					servicioGestionCSV = LocalizadorServicios.getServicioGestionCSV();
					codCotejo = addCodVerificacion(servicioGestionCSV, documento, ctx);

					// Añadir la banda gris lateral al documento PDF
					addGrayBand(ctx, originalPDFFile, pathFileTemp, date, codCotejo, ctx.getLocale());

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

				    sap.setReason("Indice Electronico del Expediente " + rulectx.getNumExp());
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

					// Actualizar el documento original con el PDF firmado
					updateDocument(documento, originalPDFFile, ctx);
				}
			}

		} catch (Exception ie) {

			// Si se produce algún error
			// anular el CSV si ya fue generado
			anularCodVerificacion(codCotejo);

			logger.error("Error en la regla ConvertDocumentToPDFAndSignRule:execute", ie);
			throw new ISPACRuleException(ie);
		}

		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#init(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#validate(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();

		try {

			// Comprobar que se hayan generado documentos para el trámite actual
			int countDocumentos = invesflowAPI.getEntitiesAPI().countEntities(SpacEntities.SPAC_DT_DOCUMENTOS, " WHERE ID_TRAMITE = " + rulectx.getTaskId());
			if (countDocumentos == 0) {

				RuleHelper.setInfoMessage(rulectx, TaskState.class,
						"operation.noExecute.task.noDocuments", null,
						"operation.noExecute.task.noDocuments.numexp",
						new String[] { rulectx.getNumExp() });

				return false;
			}

			// Comprobar que no haya ningún documento al que no se le haya aplicado la función resumen
			// entre los documentos que no sean los del trámite actual
			IItemCollection itemcol = invesflowAPI.getEntitiesAPI().getEntities(SpacEntities.SPAC_DT_DOCUMENTOS, rulectx.getNumExp(), " ID_TRAMITE <> " + rulectx.getTaskId() + " AND INFOPAG IS NOT NULL AND HASH IS NULL");
			if (itemcol.next()) {

				RuleHelper.setInfoMessage(rulectx, TaskState.class,
						"operation.noExecute.task.newDocuments", null,
						"operation.noExecute.task.newDocuments.numexp",
						new String[] { rulectx.getNumExp() });

				return false;
			}

		} catch (ISPACException e) {
			logger.error("Error en la regla ConvertDocumentToPDFAndSignRule:validate", e);
			throw new ISPACRuleException(e);
		}

		return true;
	}

	/**
	 * Actualiza la información del documento a partir del documento original convertido a PDF.
	 * @param documento
	 * @param originalPDFFile
	 * @param clientContext
	 * @throws ISPACException
	 */
	protected void updateDocument(IItem documento, File originalPDFFile, IClientContext clientContext) throws ISPACException {

		IGenDocAPI gendocAPI = clientContext.getAPI().getGenDocAPI();

		Object connectorSession = null;
		InputStream in = null;

		try {
			connectorSession = gendocAPI.createConnectorSession();

			try {
				in = new FileInputStream(originalPDFFile);
			} catch (FileNotFoundException e) {
				throw new ISPACException("Fichero no encontrado: '" + originalPDFFile.getName() + "'");
			}

			// Reemplazar el documento original por el PDF firmado
			String sMimeType = MimetypeMapping.getMimeType("pdf");
			gendocAPI.setDocument(connectorSession, documento.getKeyInt(), documento.getString("INFOPAG"), in, (int) originalPDFFile.length(), sMimeType);

			// Nueva extensión PDF para el documento
			if (!documento.getString("EXTENSION").equalsIgnoreCase("pdf")) {

				documento.set("EXTENSION", "pdf");
				documento.store(clientContext);
			}

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
		}
	}

	/**
	 * Obtiene el documento para convertirlo a PDF siempre y cuando su extensión no sea PDF.
	 */
	protected File getDocumentInPDF(IInvesflowAPI invesflowAPI, String docRef) throws ISPACException{

		IGenDocAPI gendocAPI = invesflowAPI.getGenDocAPI();

		Object connectorSession = null;
		File file = null;
		String fileName = null;

		try {
			connectorSession = gendocAPI.createConnectorSession();

			String extension = MimetypeMapping.getExtension(gendocAPI.getMimeType(connectorSession, docRef));
			if(!extension.equalsIgnoreCase("pdf")) {

				// Convertir el documento original a PDF
				file = convert2PDF(invesflowAPI, docRef, extension);

			} else {

				// Se obtiene el documento del repositorio documental
				fileName = FileTemporaryManager.getInstance().newFileName("." + extension);
				fileName = FileTemporaryManager.getInstance().getFileTemporaryPath() + "/" + fileName;

				OutputStream out = new FileOutputStream(fileName);
				gendocAPI.getDocument(connectorSession, docRef, out);

				file = new File(fileName);
			}

		} catch (FileNotFoundException e) {
			logger.error("Error al obtener el fichero temporal: " + fileName + " para el documento: " + docRef, e);
			throw new ISPACException(e);
		} finally {
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
		}

		return file;
	}

	/**
	 * Incluye la banda lateral en el fichero PDF.
	 *
	 * @param clientContext Contexto actual de tramitación.
	 * @param file Fichero PDF.
	 * @param pathFileTemp Ruta al fichero temporal.
	 * @param signDate Fecha de la firma.
	 * @param codCotejo Código de cotejo (código seguro de verificación) para el documento.
	 * @param locale Idioma para el texto de la banda lateral.
	 *
	 * @throws Exception Si se produce algún error.
	 */
	protected void addGrayBand(IClientContext clientContext, File file, String pathFileTemp, Date signDate, String codCotejo, Locale locale) throws Exception {

		float margen = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(SigemSignConnector.MARGIN_BAND));
		int band = Integer.parseInt(ISPACConfiguration.getInstance().getProperty(SigemSignConnector.BAND));
		float bandSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(SigemSignConnector.SIZE_INICIAL_BAND));

		try {

			PdfReader readerInicial = new PdfReader(file.getAbsolutePath());
			int n = readerInicial.getNumberOfPages();
			int largo = (int) readerInicial.getPageSize(n).getHeight();
			int ancho = (int) readerInicial.getPageSize(n).getWidth();

			Image imagen = createBgImage();
			Document document = new Document();

			FileOutputStream fileOut = new FileOutputStream(
					FileTemporaryManager.getInstance().getFileTemporaryPath()
					+ "/" + pathFileTemp, true);
			PdfWriter writer = PdfWriter.getInstance(document, fileOut);
			document.open();
			Rectangle r = document.getPageSize();
			String dateFirma = DATE_FORMATTER.format(signDate);

			// Obtener el conector de firma configurado a partir del cual
			// se generará la banda gris lateral
			ISignConnector signConnector = SignConnectorFactory.getSignConnector();
			signConnector.initializate(null, clientContext);
			// Nuevo interface a partir de SIGM3
			ISigemSignConnector sigemSignConnector = null;
			if (signConnector instanceof ISigemSignConnector) {
				sigemSignConnector = (ISigemSignConnector) signConnector;
			}

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
					if (sigemSignConnector != null) {
						sigemSignConnector.generateGrayBandImagen(dateFirma, over, margen, true, margen, n, i, codCotejo, null);
					} else {
						getImagen(dateFirma, over, margen, true, margen, n, i, codCotejo, locale);
					}
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
					if (sigemSignConnector != null) {
						sigemSignConnector.generateGrayBandImagen(dateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo, null);
					} else {
						getImagen(dateFirma, over, margen, false, (float) largo - margen, n, i, codCotejo, locale);
					}
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

	/**
	 * Genera la imagen de la banda lateral.
	 *
	 * @param dateFirma Fecha de la firma.
	 * @param pdfContentByte Contenido del documento PDF para el que se genera la banda gris lateral.
	 * @param margen Margen para la banda lateral.
	 * @param vh Indicador de orientación del documento (vertical/horizontal).
	 * @param x Ancho de línea para cada línea de la banda.
	 * @param numberOfPages Número de páginas del documento PDF.
	 * @param pageActual Número de página actual.
	 * @param codCotejo Código de cotejo (código seguro de verificación) para el documento.
	 * @param locale Idioma para el texto de la banda lateral.
	 *
	 * @throws ISPACException Si se produce algún error.
	 */
	protected void getImagen(String dateFirma, PdfContentByte pdfContentByte,
			float margen, boolean vh, float x, int numberOfPages,
			int pageActual, String codCotejo, Locale locale) throws ISPACException {

		try {

			String font = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.FONT_BAND);
			String encoding = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.ENCODING_BAND);
			float fontSize = Float.parseFloat(ISPACConfiguration.getInstance().getProperty(SigemSignConnector.FONTSIZE_BAND));

			BaseFont bf = BaseFont.createFont(font, encoding, false);
			pdfContentByte.beginText();
			pdfContentByte.setFontAndSize(bf, fontSize);

			BufferedReader br = new BufferedReader(new FileReader(getDataFile(locale.getLanguage())));
			String sCadena = null;
			int i = 0;
			while ((sCadena = br.readLine()) != null) {
				if (vh) {
					pdfContentByte.setTextMatrix(0.0F, 1.0F, -1F, 0.0F, x, margen);
					if (i == 0) {
						pdfContentByte.showText(sCadena + codCotejo);
					} else if (i == 1) {
						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(locale, "pdf.band.pageInfo"), new String[] {
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
	                } else if (i == 1) {
	                    //pdfContentByte.showText(sCadena + numberOfPages + " folios. Folio " + pageActual + " de " + numberOfPages + ".");
						pdfContentByte.showText(sCadena
								+ MessagesFormatter.format(getString(locale, "pdf.band.pageInfo"), new String[] {
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

	/**
	 * Obtiene el fichero con el texto para la banda lateral.
	 * @param language
	 * @return
	 * @throws ISPACException
	 */
	protected File getDataFile(String language) throws ISPACException {

		// Ruta relativa del texto de la banda lateral
		String dataPath = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.PATH_TEXT);
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
		String dataFullPath = ConfigurationHelper.getConfigFilePath(basename + "_" + language + ext);
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
	 * Convierte el fichero a pdf.
	 *
	 * @param infoPag
	 * @param extension
	 * @return
	 * @throws ISPACException
	 */
	protected File convert2PDF(IInvesflowAPI invesflowAPI, String infoPag, String extension) throws ISPACException {

		// Convertir el documento a pdf
		String docFilePath = DocumentConverter.convert2PDF(invesflowAPI, infoPag, extension);

		// Obtener el fichero convertido
		File file = new File(docFilePath);
		if (!file.exists())
			throw new ISPACException("No se ha podido convertir el documento a PDF");

		return file;
	}

	/**
	 * Generar el CSV
	 * y actualizar la referencia en el campo COD_COTEJO en la tabla de documentos.
	 *
	 * @throws ISPACException
	 */
	protected synchronized String addCodVerificacion(ServicioGestionCSV servicioGestionCSV, IItem documento, IClientContext clientContext) throws ISPACException {

		InfoDocumentoCSV infoDocumento = null;

		try {
			// Identificador de la entidad
			String entityId = getEntityId();

			// Generación del CSV para el documento
			// haciendo uso del servicio de gestión de CSV de generación y consulta
			Entidad entidad = new Entidad();
			entidad.setIdentificador(entityId);
			InfoDocumentoCSVForm infoDocumentoForm = new InfoDocumentoCSVForm();
			infoDocumentoForm
					.setCodigoAplicacion(CodigosAplicacionesConstants.TRAMITACION_EXPEDIENTES_CODE);
			infoDocumentoForm.setDisponible(true);
			// La fecha de caducidad es null porque nunca caduca
			infoDocumentoForm.setFechaCaducidad(null);
			// Fecha del documento
			infoDocumentoForm.setFechaCreacion(documento.getDate("FDOC"));
			// Nombre del documento más extensión
			// y tipo Mime asociado
			infoDocumentoForm.setNombre(documento.getString("NOMBRE") + ".pdf");
			infoDocumentoForm.setTipoMime(INFODOCUMENTO_CSV_TIPO_MIME);

			// Generar el CSV
			infoDocumento = servicioGestionCSV.generarCSV(entidad,
					infoDocumentoForm);

			if (logger.isDebugEnabled()) {
				logger.debug("ConvertDocumentToPDFAndSignRule.addCodVerificacion: Generado el CSV: [" + infoDocumento.getCsv()
						+ "] para el documento: [" + infoDocumento.getId() + "].");
			}

			// Generar el código de cotejo electrónico
			documento.set("COD_COTEJO", infoDocumento.getCsv());

			//Bloqueamos el documento para la edicion
			//itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
			documento.store(clientContext);

			return infoDocumento.getCsv();

		} catch (Exception e) {

			// Si se produce algún error
			// anular el CSV si ya fue generado
			if (infoDocumento != null) {
				anularCodVerificacion(infoDocumento.getCsv());
			}

			logger.error("Error en la firma del documento al generar el CSV", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Anula el código de verificación generado para el documento.
	 *
	 * @param codCotejo
	 */
	protected void anularCodVerificacion(String codCotejo) {

		if (codCotejo != null) {

			// Eliminar el CSV
			try {
				Entidad entidad = new Entidad();
				entidad.setIdentificador(getEntityId());

				ServicioGestionCSV servicioGestionCSV = LocalizadorServicios.getServicioGestionCSV();
				InfoDocumentoCSV infoDocumentoCSV = servicioGestionCSV.getInfoDocumentoByCSV(entidad, codCotejo);
				if (infoDocumentoCSV != null) {

					// Eliminar la información de CSV del documento
					servicioGestionCSV.deleteInfoDocumento(entidad, infoDocumentoCSV.getId());
				}
			} catch (Exception e) {
				logger.error("Error en la regla ConvertDocumentToPDFAndSignRule:execute al eliminar el CSV", e);
			}
		}
	}

	/**
	 * Obtiene la entidad (organizacion) en un entorno multientidad.
	 * @return
	 */
    protected String getEntityId() {

    	String entityId = null;

		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			entityId = info.getOrganizationId();
		}

		return entityId;
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
		String imagePath = ISPACConfiguration.getInstance().getProperty(SigemSignConnector.PATH_IMAGEN_BAND);
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

}
