package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.distribution.DistributionUseCase;

/**
 * Este servlet se invoca cuando realizamos una distribucion manual
 *
 * @author jcebrien
 *
 */
public class DtrDist extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(DtrDist.class);

	private static final String GUIONBAJO = "_";
	private static final String BARRA = "|";
	private static final String AMPERSAN = "#";

	private DistributionUseCase distributionUseCase = null;


	public void init() throws ServletException {
		super.init();

		distributionUseCase = new DistributionUseCase();
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

		// Tipo de usuarios (1 departamento; 2 Grupo; 3 Ususario).
		Integer userType = RequestUtils.parseRequestParameterAsInteger(request,
				"oSelectUsersType", new Integer(0));
		// Identificador de usuario.
		Integer userId = RequestUtils.parseRequestParameterAsInteger(request,
				"oSelectDeptGroupUser", new Integer(0));
		//Cadena con el id de usuario al que se distribuye y su mensaje asociado.
		String messageForUser = RequestUtils.parseRequestParameterAsString(
				request, "oMensaje");
		//Registros seleccionados de la lista.
		String ids = RequestUtils
				.parseRequestParameterAsString(request, "List");
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Recuperamos el id de libro
		Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		PrintWriter writer = response.getWriter();
		String resp = null;
		try {
			List listIdsRegister = parseList(ids);
			if (_logger.isDebugEnabled()) {
				for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
					_logger.debug("===================> listIdsRegister "
							+ it.next());
				}
				_logger.debug("===================> messageForUser "
						+ messageForUser);
				_logger.debug("===================> userType " + userType);
				_logger.debug("===================> userId " + userId);
			}
			resp = distributionUseCase.createDistribution(useCaseConf, bookID,
					listIdsRegister, userType, userId, messageForUser);
			if (_logger.isDebugEnabled()) {
				_logger.debug("===================> resp " + resp);
			}
			Map resps = parseResp(resp);
			String respNoDistributed = null;
			String respDistributed = null;
			if (resps.containsKey("ND")) {
				respNoDistributed = (String) resps.get("ND");
				respNoDistributed = respNoDistributed.substring(0, respNoDistributed.length() - 1);
			}
			if (resps.containsKey("SD")) {
				respDistributed= (String) resps.get("SD");
			}
			ResponseUtils.generateJavaScriptLogDtrDist(writer, respNoDistributed, respDistributed, RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
								Keys.I18N_DTREX_CREATE_SATISFY));
		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (DistributionException e) {
			_logger.fatal("Error en la distribucion", e);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (BookException e) {
			_logger.fatal("Error en el libro", e);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (EventException e) {
			_logger.fatal("Error en el evento", e);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (SessionException e) {
			_logger.fatal("Error en la sesion", e);
			ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e,
					idioma, numIdioma);
		} catch (Exception e) {
			_logger.fatal("Error en distribucion", e);
			ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
		}
	}

	private List parseList(String listIds) {
		List result = new ArrayList();
		StringTokenizer tokens = new StringTokenizer(listIds, GUIONBAJO);
		String token = null;
		int i = 0;
		while (tokens.hasMoreTokens()) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("i: " + i);
			}
			if (i == 0) {
				tokens.nextToken();
			}
			token = tokens.nextToken();
			if (_logger.isDebugEnabled()) {
				_logger.debug("Ids : " + token);
			}
			result.add(new Integer(token));
			i++;
		}
		return result;
	}

	private Map parseResp(String resp) {
		Map result = new HashMap();
		StringTokenizer tokens = new StringTokenizer(resp, BARRA);
		int numTokens = tokens.countTokens();
		String token = null;
		if (numTokens == 2){
			token = tokens.nextToken();
			result.put("SD", token);
			token = tokens.nextToken();
			result.put("ND", token);
		} else if (numTokens == 1){
			token = tokens.nextToken();
			if (token.indexOf("#") != -1){
				result.put("SD", token);
			} else {
				result.put("ND", token);
			}
		}
		return result;
	}

}