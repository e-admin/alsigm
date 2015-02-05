/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.PersonAddress;
import com.ieci.tecdoc.common.isicres.PersonAddressTel;
import com.ieci.tecdoc.common.isicres.PersonInfo;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.person.PersonValidationFactory;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

/**
 * Este servlet se invoca para dar de alta o modificar los datos de una persona.
 *
 * @author jcebrien
 *
 */
public class VldInterSave extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(VldInterSave.class);
	private static final String ALMOHADILLA = "#";
	private static final String BARRA = "|";
	private static final String DOSBARRA = "||";
	private static final String ESCAPE = "\\";
	private static final String NULO = "";
	private static final String BLANCO = " ";
	private static final String COMA = ", ";
	private static final String UNO = "1";
	private String encoding = null;

	BookUseCase bookUseCase = null;

	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletConfig().getServletContext();
		encoding = context.getInitParameter("PARAMETER_ENCODING");

		bookUseCase = new BookUseCase();
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

		if (encoding != null)
			request.setCharacterEncoding(encoding);

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/html; charset=UTF-8");

		String strName = null;
		String strApe1 = null;
		String strApe2 = null;
		Integer strIdDir = null;
		Integer strTypeId = null;

		// identificador de persona.
		Integer idPerson = RequestUtils.parseRequestParameterAsInteger(request,
				"PersonId");
		// Nº de Documento identificativo.
		String strDoc = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "oDocumento");
		// Nº de Documento identificativo.
		String strDocJur = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "oDocumentoJur");
		// Tipo de persona (física o juridica).
		int strTipoPer = RequestUtils.parseRequestParameterAsint(request,
				"TipoPer");
		// Tipo de documento.
		int strTipoDoc = RequestUtils.parseRequestParameterAsint(request,
				"oTipoDocu");
		// Tipo de documento.
		int strTipoDocJur = RequestUtils.parseRequestParameterAsint(request,
				"oTipoDocuJur");
		// Nombre de la persona física.
		String oPerNombre = RequestUtils
				.parseRequestParameterAsStringWithEmpty(request, "oPerNombre");
		// Primer apellido.
		String oPerApe1 = RequestUtils.parseRequestParameterAsString(request,
				"oPerApe1");
		// Segundo apellido.
		String oPerApe2 = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "oPerApe2");
		// Nombre de la persona jurídica.
		String oRazon = RequestUtils.parseRequestParameterAsString(request,
				"oRazon");
		// Lista de direcciones.
		String strDirecciones = RequestUtils.parseRequestParameterAsString(
				request, "CadenaDir");
		//Lista de direcciones telemáticas.
		String strDireccionesTel = RequestUtils.parseRequestParameterAsString(
				request, "CadenaTel");
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		StringBuffer srtAllName = new StringBuffer();
		StringBuffer strDirPpal = new StringBuffer();

		strDoc = replaceInvalid(strDoc);
		if (strTipoPer == 1) {
			strName = replaceInvalid(oPerNombre).trim();
			strApe1 = replaceInvalid(oPerApe1).trim();
			strApe2 = replaceInvalid(oPerApe2).trim();
			srtAllName.append(strApe1);

			if (!strApe2.equals(NULO)) {
				srtAllName.append(BLANCO);
				srtAllName.append(strApe2);
			}
			if (!strName.equals(NULO)) {
				srtAllName.append(COMA);
				srtAllName.append(strName);
			}
		} else {
			strDoc = strDocJur;
			strTipoDoc = strTipoDocJur;
			strName = replaceInvalid(oRazon).trim();
			srtAllName.append(strName);
		}

		PrintWriter writer = response.getWriter();
		try {
			//guardamos el interesado
			Integer newIdPerson = bookUseCase.vldInterSave(useCaseConf, strDoc,
					strName, strApe1, strApe2, strTipoDoc, strDirecciones,
					strDireccionesTel, strTipoPer, idPerson);

			//obtenemos la informacion del interesado con sus direcciones
			String xmlParamId = XMLPersons.createXMLParamIdInfo(newIdPerson
					.toString(), useCaseConf.getSessionID(), useCaseConf.getEntidadId());

			String result = PersonValidationFactory
					.getCurrentPersonValidation().getInfo(xmlParamId);

			PersonInfo personInfo = XMLPersons.getPersonInfoFromXMLText(result);

			if(personInfo != null && !personInfo.getAddresses().isEmpty()){
				for(Iterator it = personInfo.getAddresses().iterator();it.hasNext();){
					PersonAddress pAddress = (PersonAddress) it.next();
					//se comprueba la direccion preferente
					if(UNO.equals(pAddress.getPreference())){
						strIdDir = new Integer(pAddress.getId());
							strTypeId = new Integer(0);
						strDirPpal.append(pAddress.getDom());
							strDirPpal.append(BLANCO);
						if(pAddress.getZip()!= null){
							strDirPpal.append(pAddress.getZip());
								strDirPpal.append(BLANCO);
							}
						strDirPpal.append(pAddress.getCity());
							strDirPpal.append(BLANCO);
						strDirPpal.append(pAddress.getProvince());
							strDirPpal.append(BLANCO);
							break;
						}
				}
			} else {
				if(personInfo != null && !personInfo.getAddressesTel().isEmpty()){
					for(Iterator it = personInfo.getAddressesTel().iterator(); it.hasNext();){
						PersonAddressTel pPersonAddress = (PersonAddressTel) it.next();
						//se comprueba la direccion preferente
						if(UNO.equals(pPersonAddress.getPreference())){
							strIdDir = new Integer(pPersonAddress.getId());
							strDirPpal.append(pPersonAddress.getDirTel());
							strTypeId = new Integer(pPersonAddress.getType());
							break;
						}
					}
				}
			}


			if (_logger.isDebugEnabled()) {
				_logger.debug("newIdPerson: " + newIdPerson);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append(newIdPerson.toString());
			buffer.append(BARRA);
			buffer.append(srtAllName.toString());
			buffer.append(BARRA);
			buffer.append(strTipoDoc);
			buffer.append(BARRA);
			buffer.append(strApe1);
			buffer.append(BARRA);
			buffer.append(strApe2);
			buffer.append(BARRA);
			buffer.append(strName);
			buffer.append(BARRA);
			buffer.append(UNO);
			// Aniado esto para que se pase como parametro el documento de
			// identificacion fiscal
			buffer.append(BARRA);
			buffer.append(strDoc);
			if(strIdDir != null && strDirPpal != null ){
				buffer.append(BARRA);
				buffer.append(strIdDir.toString());
				buffer.append(BARRA);
				buffer.append(strDirPpal.toString());
				buffer.append(BARRA);
				buffer.append(strTypeId.toString());
			}
			buffer.append(DOSBARRA);

			writer.write(buffer.toString());
			writer.flush();
			writer.close();

		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			ResponseUtils.generateJavaScriptLogInterSave(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (BookException e) {
			_logger.fatal("Error en el libro", e);
			ResponseUtils
					.generateJavaScriptLogInterSave(writer, e.getMessage());
		} catch (AttributesException e) {
			_logger.fatal("Error en los atributos", e);
			ResponseUtils
					.generateJavaScriptLogInterSave(writer, e.getMessage());
		} catch (SessionException e) {
			_logger.fatal("Error en la sesion", e);
			writer.write(Keys.ACTIVATE_SAVEPERSON);
			ResponseUtils
					.generateJavaScriptLogInterSave(writer, e.getMessage());
			// ResponseUtils.generateJavaScriptError(writer, e);
		} catch (Exception e) {
			_logger.fatal("Error al guardar los datos", e);
			ResponseUtils.generateJavaScriptLogInterSave(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_SAVEPERSON_OBJ));
		}
	}

	private String replaceInvalid(String cadena) {
		String result = null;
		if (cadena != null) {
			result = cadena.replaceAll(BARRA, NULO);
			result = result.replaceAll(ALMOHADILLA, NULO);
		}
		return result;
	}

}