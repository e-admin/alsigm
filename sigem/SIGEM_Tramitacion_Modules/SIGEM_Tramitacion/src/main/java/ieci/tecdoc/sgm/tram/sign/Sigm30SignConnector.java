package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestioncsv.CodigosAplicacionesConstants;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignature;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

public class Sigm30SignConnector extends Sigem23SignConnector implements ISigemSignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Sigm30SignConnector.class);

	/**
	 * Tipo mime para el CSV
	 */
	protected static final String INFODOCUMENTO_CSV_TIPO_MIME = "application/pdf";

	/**
	 * Constructor.
	 *
	 */
	public Sigm30SignConnector() {
		super();
	}

	/**
	 * Obtiene el documento , pdf si ya se hubiese firmado alguna vez o el original en otro caso.
	 * Añade los datos del firmante y genera o modifica  el pdf.
	 * @param changeState
	 * @throws ISPACException
	 */
	protected void generatePdfToSign(boolean changeState) throws ISPACException{

		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
		String antInfoPagRDE = signDocument.getItemDoc().getString("INFOPAG_RDE");

		IInvesflowAPI invesflowAPI = clientContext.getAPI();

		// Obtener el documento original convertido a PDF
		File originalPDFFile = getFile(infoPag);

		// CSV para el nuevo documento firmado
		String newCodCotejo = null;

		try {

			ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
			String pathFileTemp = FileTemporaryManager.getInstance().put(originalPDFFile.getAbsolutePath(), ".pdf");

			// Fecha de la firma del PDF
			ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
			Date date = servicioTiempos.getCurrentDate();

			// Código de cotejo
			String codCotejo = signDocument.getItemDoc().getString("COD_COTEJO");
			if (StringUtils.isBlank(codCotejo)) {
				newCodCotejo = addCodVerificacion(signDocument, date, clientContext);
				codCotejo = newCodCotejo;
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

			// Si se produce algún error
			// anular el CSV si ya fue generado
			anularCodVerificacion(newCodCotejo);

			doErrorSign();

			logger.error("Error en la firma del documento", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Generar el CSV y
	 * actualizar la referencia en el campo COD_COTEJO en la tabla de documentos.
	 *
	 * @param signDocument Documento a firmar.
	 * @param signDate Fecha de la firma.
	 * @param clientContext Contexto de tramitación.
	 * @return Código seguro de verificación.
	 * @throws ISPACException Si se produce algún error, en caso afirmativo, se anula el CSV si fue generado.
	 */
	protected synchronized String addCodVerificacion(SignDocument signDocument, Date signDate, IClientContext clientContext) throws ISPACException {

		InfoDocumentoCSV infoDocumento = null;

		try {
			ServicioGestionCSV servicioGestionCSV = LocalizadorServicios.getServicioGestionCSV();

			// Información del documento
			IItem itemDoc = signDocument.getItemDoc();

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
			// Fecha del documento (la de la firma)
			infoDocumentoForm.setFechaCreacion(signDate);
			// Nombre del documento más extensión
			// y tipo Mime asociado
			infoDocumentoForm.setNombre(itemDoc.getString("NOMBRE") + ".pdf");
			infoDocumentoForm.setTipoMime(INFODOCUMENTO_CSV_TIPO_MIME);

			// Generar el CSV invocando al Servicio de Gestión de CSV
			infoDocumento = servicioGestionCSV.generarCSV(entidad,
					infoDocumentoForm);

			if (logger.isDebugEnabled()) {
				logger.debug("SigemSignConnector.addCodVerificacion: Generado el CSV: [" + infoDocumento.getCsv()
						+ "] para el documento: [" + infoDocumento.getId() + "].");
			}

			// Establecer el código de cotejo electrónico a partir del CSV
			itemDoc.set("COD_COTEJO", infoDocumento.getCsv());

			//Bloqueamos el documento para la edicion
			// itemDoc.set("BLOQUEO", DocumentLockStates.EDIT_LOCK);
			itemDoc.store(clientContext);

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
				logger.error("Error en la firma al eliminar el CSV", e);
			}
		}
	}

}
