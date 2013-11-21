/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.SQLValidator;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Muestra la ayuda de los campos del formulario de consulta de libros.
 *
 * @author jcebrien
 *
 */

public class Vldres extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(Vldres.class);
	private TransformerFactory factory = null;
	private ValidationListUseCase validationListUseCase = null;

	private static final int HELP_TYPE_UA = 1;
	private static final int HELP_TYPE_TRANSPORT = 2;
	private static final int HELP_TYPE_SUBJECT = 3;
	private static final int HELP_TYPE_OFFIC = 4;
	private static final int HELP_TYPE_ENT_REG = 5;
	private static final int HELP_TYPE_REGISTER_TYPE = 7;
	private static final int HELP_TYPE_DISTRIBUTION = 8;
	private static final int HELP_TYPE_PROC = 9;
	private static final int HELP_BOOK_LIST = 10;
	private static final int HELP_TYPE_UA_IR = 11;
	private static final int HELP_INVESDOC = 1000;


	public void init() throws ServletException {
		super.init();

		factory = TransformerFactory.newInstance();
		validationListUseCase = new ValidationListUseCase();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	private void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		response.setContentType("text/html; charset=UTF-8");

		Integer archivePId = RequestUtils.parseRequestParameterAsInteger(
				request, "ArchivePId");
		int tblvalidated = RequestUtils.parseRequestParameterAsint(request,
				"tblvalidated");
		int initValue = RequestUtils.parseRequestParameterAsint(request,
				"InitValue");
		int tblFldId = RequestUtils.parseRequestParameterAsint(request,
				"TblFldId");
		String vldQuery = RequestUtils.parseRequestParameterAsString(request,
				"VldQuery");
		String vldQueryValue = RequestUtils.parseRequestParameterAsString(
				request, "VldQueryValue");
		int typeId = RequestUtils.parseRequestParameterAsint(request, "TypeId");
		int idCrl = RequestUtils.parseRequestParameterAsint(request, "IdCrl");
		int typeBusc = RequestUtils.parseRequestParameterAsint(request,
				"TypeBusc");
		int enabled = RequestUtils.parseRequestParameterAsint(request,
				"Enabled");

		//Nos indica si venimos del formulario de registro
		int frmData = RequestUtils.parseRequestParameterAsint(request,
				"FrmData");
		int frmDataBusc = RequestUtils.parseRequestParameterAsint(request,
				"FrmDataBusc");

		//si la búsqueda es por libro obtenemos el tipo de libro a buscar
		int typeBook= RequestUtils.parseRequestParameterAsint(request, "TypeBook");


		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Recuperamos el id de libro
		Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador de sesión para este usuario en el servidor de
		// aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);

		PrintWriter writer = response.getWriter();
		try {

			// Validamos que el la condición del WHERE que recibimos es valida y
			// no contiene SQL Inyectado
			vldQueryValue = SQLValidator.getInstance()
					.validateQueryCamposValidados(vldQuery, vldQueryValue);

			if ((vldQuery != null) && (!"".equals(vldQuery))
					&& (vldQueryValue != null) && (!"".equals(vldQueryValue))) {
				vldQuery = vldQuery + " " + vldQueryValue;
			}

			Document xmlDocument = null;

			switch (tblvalidated) {
			case HELP_TYPE_UA: {
				xmlDocument = validationListUseCase.getValidationListTypeUA(
						useCaseConf, bookID, initValue, typeId, typeBusc,
						tblFldId, idCrl, enabled, frmData, frmDataBusc,
						vldQuery);
				break;
			}
			case HELP_TYPE_TRANSPORT: {
				xmlDocument = validationListUseCase
						.getValidationListTypeTransport(useCaseConf, initValue,
								vldQuery, useCaseConf.getLocale());
				break;
			}
			case HELP_TYPE_SUBJECT: {
				xmlDocument = validationListUseCase
						.getValidationListTypeSubject(useCaseConf, bookID,
								initValue, enabled, vldQuery);
				break;
			}
			case HELP_TYPE_OFFIC: {
				xmlDocument = validationListUseCase.getValidationListTypeOffic(
						useCaseConf, initValue, enabled, vldQuery, useCaseConf
								.getLocale());
				break;
			}
			case HELP_TYPE_ENT_REG: {
				xmlDocument = validationListUseCase
						.getValidationListTypeEntReg(useCaseConf, initValue,
								enabled, vldQuery, useCaseConf.getLocale());
				break;
			}
			case HELP_TYPE_REGISTER_TYPE: {
				xmlDocument = validationListUseCase
						.getValidationListRegisterType(useCaseConf);
				break;
			}
			case HELP_TYPE_DISTRIBUTION: {
				xmlDocument = validationListUseCase
						.getValidationListTypeDistribution(useCaseConf, bookID,
								initValue, typeId, typeBusc, tblFldId, idCrl,
								enabled, vldQuery);
				break;
			}
			case HELP_TYPE_PROC: {
				xmlDocument = validationListUseCase.getValidationListTypeProc(
						useCaseConf, initValue, enabled, vldQuery, useCaseConf
								.getLocale());
				break;
			}
			case HELP_BOOK_LIST: {
				xmlDocument = validationListUseCase.getValidationListBooks(
						useCaseConf, initValue, typeBook);
				break;
			}
			case HELP_INVESDOC: {
				xmlDocument = validationListUseCase
						.getValidationListTypeInvesDoc(useCaseConf, initValue,
								typeId, bookID, tblFldId, idCrl, typeBusc,
								vldQuery, useCaseConf.getLocale());
				break;
			}
			case HELP_TYPE_UA_IR: {
				xmlDocument = validationListUseCase.getValidationListTypeUA(
						useCaseConf, initValue, typeId, typeBusc,
						tblFldId, idCrl, enabled, frmData, frmDataBusc,
						vldQuery);
				break;
			}
			default: {
				break;
			}
			}

			if (xmlDocument != null) {
				String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_VLDRES_RELATIVE_PATH);
				Templates cachedXSLT = factory.newTemplates(new StreamSource(
						new InputStreamReader(new BufferedInputStream(
								new FileInputStream(xslPath)))));
				Transformer transformer = cachedXSLT.newTransformer();
				DocumentSource source = new DocumentSource(xmlDocument);

				StreamResult result = new StreamResult(writer);
				transformer.transform(source, result);
			}
		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (BookException e) {
			_logger.fatal("Error en el libro", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (AttributesException e) {
			_logger.fatal("Error en los atributos", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptError(writer, e, useCaseConf
					.getLocale());
		} catch (SessionException e) {
			_logger.fatal("Error en la sesion", e);
			// writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptLogSessionExpiredVldRes(writer, e
					.getMessage(), idioma, numIdioma, enabled);
			// ResponseUtils.generateJavaScriptError(writer, e);
		} catch (TransformerConfigurationException e) {
			_logger.fatal("Error al recuperar la lista", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_VLDTBL_OBJ));
		} catch (TransformerException e) {
			_logger.fatal("Error al recuperar la lista", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_VLDTBL_OBJ));
		} catch (Exception e) {
			_logger.fatal("Error al recuperar la lista", e);
			writer.write(ACTIVATE_CLOSE_VALIDATION);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_VLDTBL_OBJ));
		}
	}
}