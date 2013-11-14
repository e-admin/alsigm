package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.FieldSearchAvanced;
import com.ieci.tecdoc.common.isicres.OrderField;
import com.ieci.tecdoc.common.isicres.QuerySearchAvanced;
import com.ieci.tecdoc.common.isicres.RowQuerySearchAdvanced;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.SQLValidator;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

public class VldSearchOperator extends HttpServlet implements Keys{

	private static Logger _logger = Logger.getLogger(VldSearchOperator.class);

	private BookUseCase bookUseCase = null;
	private TransformerFactory factory = null;

	public void init() throws ServletException {
		super.init();

		factory = TransformerFactory.newInstance();
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

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter writer = response.getWriter();

        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
		// Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);

        String method = request.getParameter("method");

        try {

			if (method.equals("loadDatos")){
				saveOrdenation(session, request);
				saveSearchConditions(session, request);
	        	loadDatos(request, response, session);
	        }
			else {
				if (method.equals("search")) {
					saveOrdenation(session, request);
					doSearch(request, response, session, useCaseConf, writer);
				}
				else {

					if (method.equals("showResults")) {
						showResults(session, response);
					}
					else {
						saveOrdenation(session, request);
						deleteRow(request, response, session);
					}
				}
			}
        }
		catch (RemoteException e) {
	        _logger.fatal("Error de comunicaciones", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
	                .getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
	    } catch (SecurityException e) {
	        _logger.fatal("Error de seguridad", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());
	    } catch (ValidationException e) {
	        _logger.fatal("Error de validacion", e);
	        e.printStackTrace(System.err);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
	                .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
	    } catch (BookException e) {
	        _logger.fatal("Error en el libro", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptError(writer, e);
	    } catch (AttributesException e) {
	        _logger.fatal("Error en los atributos", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());
	    } catch (SessionException e) {
	        _logger.fatal("Error en la sesion", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
	        //ResponseUtils.generateJavaScriptError(writer, e);
	    } catch (Exception e) {
	        _logger.fatal("Error al cargar la lista", e);
	        writer.write(ACTIVATE_SEVERAL);
	        ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
	                .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
	    }

	}

	private void saveOrdenation (HttpSession session, HttpServletRequest request) {

		Map allCampos = (Map) session.getAttribute("allCampos");
        Map camposOrderBy = new LinkedHashMap();
        camposOrderBy.putAll(allCampos);
        List camposOrderSelec = new ArrayList();
        String camposOrder = request.getParameter("99999");

        if(StringUtils.isNotEmpty(camposOrder)){
        	StringTokenizer tokens = new StringTokenizer(camposOrder, COMMA);
    		while (tokens.hasMoreTokens()) {
    			StringTokenizer tokenCampos = new StringTokenizer (tokens.nextToken(), SPACE);
    			while(tokenCampos.hasMoreTokens()){
    				OrderField orderField = new OrderField();
    				String idField = tokenCampos.nextToken();
    				orderField.setFieldConf((FieldConf) allCampos.get(idField));
    				orderField.setSense(tokenCampos.nextToken());
    				camposOrderSelec.add(orderField);
    				if(camposOrderBy.get(idField)!=null){
    					camposOrderBy.remove(idField);
    				}
    			}
    		}
        }

        session.setAttribute("camposOrderSelec", camposOrderSelec);
        session.setAttribute("camposOrderBy", camposOrderBy);
	}


	private void doSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session, UseCaseConf useCaseConf, PrintWriter writer)
		throws ValidationException, SessionException, BookException, AttributesException, SecurityException, ParseException,
				FileNotFoundException, TransformerConfigurationException, TransformerException, Exception	{

		QuerySearchAvanced querySearchAvanced = saveSearchConditions(session, request);

		Document xmlDocument = null;
		List badCtrls = null;
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);

        Map rowMap = RowQuerySearchAdvanced.transformToMap(querySearchAvanced);

        // Validar los campos de búsqueda según el formato de cada uno
        badCtrls = bookUseCase.validateAdvancedQueryParams(useCaseConf, bookID, rowMap);

        // Si todos los campos están correctamente rellenados
        if (badCtrls.isEmpty()) {

	        xmlDocument = doSearch(useCaseConf, bookID, rowMap, querySearchAvanced.getOrder());

			if (xmlDocument != null) {
				// Almacenamos el xml con los resultados en sesión
				session.setAttribute("queryResults", xmlDocument);

				String script =
						"<script language=javascript>top.g_TreeFunc=true;top.Main.Workspace.EnabledTool();"
						 +"window.open(top.g_URL + \"/vldSearchOperator.jsp?method=showResults&SessionPId="
						 +session.getId()+"\", \"TableData\",\"location=no\",true);</script>";

				response.getWriter().write(script);

			} else {

				writer.write("<HTML><HEAD><script type=text/javascript language=javascript src=\"./scripts/tbltext.js\"></script>");

				ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
						Keys.I18N_EXCEPTION_BOOK_HAS_NO_FOLDERS));
				writer.write(ACTIVATE_SEVERAL_ADVSEARCH);

				writer.write("</HEAD><BODY tabIndex=-1></BODY></HTML>");
			}
        }
        else {
        	FieldSearchAvanced [] fieldList = querySearchAvanced.getFieldSearchAvanced();
        	boolean [] invalidValues = querySearchAvanced.getHasInvalidValue();
        	for (int i = 0; i<fieldList.length; i++){
        		FieldSearchAvanced field = fieldList[i];
        		if(field != null){
	        		if (badCtrls.contains(new Integer(field.getRowId()))) {
	        			invalidValues[i] = true;
	        		} else {
	        			invalidValues[i] = false;
	        		}
        		}
        	}
        	// Actualizamos el valor en sesión de los campos de búsqueda para que aparezcan los no validados
            session.setAttribute("valoresConsulta", querySearchAvanced);

            writer.write("<HTML><HEAD><script type=text/javascript language=javascript src=\"./scripts/tbltext.js\"></script>");

            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
					Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));

            writer.write(ACTIVATE_SEVERAL_ADVSEARCH);

			writer.write("</HEAD><BODY tabIndex=-1></BODY></HTML>");

        }
	}

	private void showResults (HttpSession session, HttpServletResponse response) {

		Document xmlDocument = null;

		try {
			PrintWriter writer = response.getWriter();

			xmlDocument = (Document)session.getAttribute("queryResults");

			String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_TBLTEXT_RELATIVE_PATH);

			StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(new FileInputStream(xslPath))));
			Templates cachedXSLT = factory.newTemplates(s);
			Transformer transformer = cachedXSLT.newTransformer();
			DocumentSource source = new DocumentSource(xmlDocument);

			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		}
		catch (Exception ex) {

		}

	}

	/** Método para realizar la búsqueda avanzada **/
	private Document doSearch(UseCaseConf useCaseConf,
								Integer bookId,
								Map rowMap,
								String order)

		throws ValidationException, SessionException, BookException, AttributesException, SecurityException, ParseException	{

		// Validamos si la ordenación que recibimos es correcta, para evitar SQL malintencionado
		SQLValidator.getInstance().validateOrderQueryRegister(order);

		Document xmlDocument = null;

    	// Algunos campos no van a formar parte directamente de la query, hay que traducirlos a su identificador
    	List fieldList = bookUseCase.translateAdvancedQueryParams(useCaseConf, bookId, rowMap);

    	int size = bookUseCase.openTableResults(useCaseConf,
    						bookId,
    						fieldList,
    						order);

		if (size > 0) {
			xmlDocument = bookUseCase
					.getTableResults(
							useCaseConf,
							bookId,
							com.ieci.tecdoc.common.isicres.Keys.QUERY_FIRST_PAGE, null);
		}

        return xmlDocument;
	}


	private QuerySearchAvanced saveSearchConditions (HttpSession session, HttpServletRequest request) {

		String listOrder = RequestUtils.parseRequestParameterAsStringWithEmpty(request, "99999");

		if (StringUtils.isEmpty(listOrder)) {
			listOrder = XML_FLD_UPPER_TEXT + 1;
		}

		int contRegistros = Integer.parseInt(request.getParameter("contadorreg"));

		QuerySearchAvanced querySearchAvanced = new QuerySearchAvanced();
        FieldSearchAvanced[] fieldSearchAvanced = new FieldSearchAvanced[contRegistros];
        int[] idoperator = new int[contRegistros];
        String[] valueWhere = new String[contRegistros];
        String[] nexo = new String[contRegistros];
        boolean[] hasInvalidValue = new boolean[contRegistros];

        Map nameCampos = (Map) session.getAttribute("camposconsulta");

        for (int i=0; i<contRegistros;i++){
        	FieldSearchAvanced field = (FieldSearchAvanced) nameCampos.get(request.getParameter("oSelectCampo_" + i));

        	if (field != null) {

	    		field.setRowId(i);
	        	fieldSearchAvanced[i] = field;

	        	try {
	        		idoperator[i] = Integer.parseInt(request.getParameter("oSelectOperador_" + i));
	        	}catch(NumberFormatException e){
	        		//idoperator[i] = 0;
	        	}

	        	valueWhere[i] = request.getParameter("where_" + i);

	        	hasInvalidValue[i] = false;
        	}
        	nexo[i] = request.getParameter("nexo_" + i);
        }

        querySearchAvanced.setFieldSearchAvanced(fieldSearchAvanced);
        querySearchAvanced.setIdOperator(idoperator);
        querySearchAvanced.setValueWhere(valueWhere);
        querySearchAvanced.setNexo(nexo);
        querySearchAvanced.setHasInvalidValue(hasInvalidValue);
        querySearchAvanced.setOrder(listOrder);

        session.setAttribute("contRegistros", new Integer(contRegistros));
        session.setAttribute("valoresConsulta", querySearchAvanced);

        return querySearchAvanced;
	}

	/**
	 * Método que se encarga de cargar los datos en pantalla
	 */
	private void loadDatos(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	 throws ServletException, IOException {

        request.getRequestDispatcher("/frmqueryadvan.jsp").forward(request,response);

	}


	/**
	 * Método que se encarga de cargar los datos en pantalla
	 */

	private void deleteRow(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	throws ServletException, IOException {

        int contRegistros = Integer.parseInt(request.getParameter("contadorreg"));


        QuerySearchAvanced querySearchAvanced = new QuerySearchAvanced();
        FieldSearchAvanced[] fieldSearchAvanced = new FieldSearchAvanced[contRegistros];
        int[] idoperator = new int[contRegistros];
        String[] valueWhere = new String[contRegistros];
        String[] nexo = new String[contRegistros];

        Map nameCampos = (Map) session.getAttribute("camposconsulta");

        int ind_auxiliar = 0;

        for (int i=0; i<=contRegistros;i++){

        	if (request.getParameter("oSelectCampo_" + i)!=null){

        		fieldSearchAvanced[ind_auxiliar] = (FieldSearchAvanced) nameCampos.get(request.getParameter("oSelectCampo_" + i));

        		try {
        			idoperator[ind_auxiliar] = Integer.parseInt(request.getParameter("oSelectOperador_" + i));
        		}catch(NumberFormatException e){
        			//idoperator[i] = 0;
        		}

        		valueWhere[ind_auxiliar] = request.getParameter("where_" + i);
        		nexo[ind_auxiliar] = request.getParameter("nexo_" + i);

        		ind_auxiliar++;
        	}

        }

        querySearchAvanced.setFieldSearchAvanced(fieldSearchAvanced);
        querySearchAvanced.setIdOperator(idoperator);
        querySearchAvanced.setValueWhere(valueWhere);
        querySearchAvanced.setNexo(nexo);

        session.setAttribute("contRegistros", new Integer(contRegistros));
        session.setAttribute("valoresConsulta", querySearchAvanced);

        request.getRequestDispatcher("/frmqueryadvan.jsp").forward(request,response);
	}


}
