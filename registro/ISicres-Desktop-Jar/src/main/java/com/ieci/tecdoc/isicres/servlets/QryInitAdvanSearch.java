/**
 * 
 * @author mabenito
 * 
 * Este servlet genera el formulario de consulta avanzada
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.FieldSearchAvanced;
import com.ieci.tecdoc.common.isicres.OrderField;
import com.ieci.tecdoc.common.isicres.QuerySearchAvanced;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLQueryBook;

public class QryInitAdvanSearch extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(QryInitAdvanSearch.class);
    public static long archPId = 1;
    private TransformerFactory factory = null;
    private BookUseCase bookUseCase = null;
	
    public void init() throws ServletException {
        super.init();

        factory = TransformerFactory.newInstance();
        bookUseCase = new BookUseCase();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }

	private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");        
        response.setContentType("text/html; charset=UTF-8"); 
        
        PrintWriter writer = response.getWriter ();
        
        // ArchiveId es el identificador del libro: A<ArchiveId><sufijo invesdoc>
        Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
        
        long archPId = 1;
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);

        // Guardamos el libro
        session.setAttribute(Keys.J_BOOK, archiveId);
        
        String method = request.getParameter("method");
        
        try {
			Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
			
			// Hay que cerrar la consulta anterior si existe
			if (bookID != null) {
			    bookUseCase.closeTableResults(useCaseConf, bookID);
			}
			
			// Si volvemos aquí con la variable de sesión rellena, simplemente tenemos que recargar la página
			if (StringUtils.isEmpty(method) || !method.equals("reload")) {

				// Transformamos el xml mediante la xsl en html.
				// Los errores pueden ser de comunicación, de validación, de transformación, etc...
				AxSf axsf = BookSession.getQueryFormat(useCaseConf.getSessionID(),
						archiveId, useCaseConf.getEntidadId());
	
				UserConf usrConf = BookSession.getUserConfig(
						useCaseConf.getSessionID(), archiveId, axsf, true, useCaseConf
								.getLocale(), useCaseConf.getEntidadId());
				
				List validationFields = AttributesSession
					.getExtendedValidationFieldsForBook(useCaseConf.getSessionID(),
						archiveId, useCaseConf.getEntidadId());
				
				Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
						.getSessionID(), archiveId);
				FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
				
				SessionInformation sessionInformation = BookSession
						.getSessionInformation(useCaseConf.getSessionID(),
								useCaseConf.getLocale(), useCaseConf
										.getEntidadId());
				
				
				Map namecampos = new LinkedHashMap();
				Map camposOrderBy = new HashMap();
				Map allCampos = new LinkedHashMap();
				
				FieldConf fieldConf = null;
				FieldSearchAvanced fieldSearchAvanced;
				
				Map operadores = bookUseCase.getOperadoresCampos(useCaseConf, archiveId, archPId);
				session.setAttribute("alloperadores", operadores);			
				
				for (Iterator it = usrConf.getFieldConf().iterator(); it.hasNext();){
					fieldConf = (FieldConf) it.next();
					
					// allCampos.put(Integer.toString(fieldConf.getFieldId()), fieldConf);
					allCampos.put(XML_FLD_TEXT + Integer.toString(fieldConf.getFieldId()), fieldConf);
					if (operadores.get(XML_FLD_UPPER_TEXT + Integer.toString(fieldConf.getFieldId()))!=null){
						fieldSearchAvanced = new FieldSearchAvanced();
						
						fieldSearchAvanced.setFieldConf(fieldConf);
											
						//comprueba si el campo se tiene que validar y contra que tabla
						fieldSearchAvanced.setValidation(XMLQueryBook.getValidation(axsf, fieldConf.getFieldId(), validationFields));
									
						//comprobamos el tipo de dato del campo
						fieldSearchAvanced.setFldType(XMLQueryBook.getDataType(axsf, fieldFormat, fieldConf.getFieldId()));
						
						//asignamos el CaseSensitive
						fieldSearchAvanced.setCaseSensitive(sessionInformation.getCaseSensitive());
											
						namecampos.put(Integer.toString(fieldSearchAvanced.getFieldConf().getFieldId()),fieldSearchAvanced);
					}	
				}
				
				session.setAttribute("camposconsulta", namecampos);
						
				//variables para establecer el orden de consulta, 
				List camposOrderSelec = new ArrayList();
				camposOrderBy.putAll(allCampos);
				
				//establecemos el campo Numero de registro como unico campo ordenado por defecto
				OrderField orderField = new OrderField();
				orderField.setFieldConf((FieldConf) camposOrderBy.get(AxSf.FLD1_FIELD));
				orderField.setSense("ASC");
				camposOrderSelec.add(orderField);
				session.setAttribute("camposOrderSelec", camposOrderSelec);
				
				//campos por los que se puede ordenar	
				camposOrderBy.remove(AxSf.FLD1_FIELD);
				session.setAttribute("camposOrderBy", camposOrderBy);
				
				//almacenamos en sesion todos los campos del formulario
				session.setAttribute("allCampos", allCampos);				
										
				QuerySearchAvanced querySearchAvanced = new QuerySearchAvanced();
				
				//inicialimos un FieldSearchAcanced para que genere la pantalla con un registro vacio
				fieldSearchAvanced = new FieldSearchAvanced();
				fieldSearchAvanced.setCaseSensitive("CI");
				
				FieldSearchAvanced[] arrayFieldSearchAvanced = new FieldSearchAvanced[1];
				arrayFieldSearchAvanced[0] = fieldSearchAvanced;
							
				querySearchAvanced.setFieldSearchAvanced(arrayFieldSearchAvanced);
					
				querySearchAvanced.setIdOperator(new int[1]);
				querySearchAvanced.setNexo(new String[1]);
				querySearchAvanced.setValueWhere(new String[1]);
					
				session.setAttribute("valoresConsulta", querySearchAvanced);				
				session.setAttribute("contRegistros", new Integer(1));
			}
			
			request.getRequestDispatcher("/frmqueryadvan.jsp").forward(request,response); 
			
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
		} catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            writer.write(ACTIVATE_SEVERAL);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
		} catch (SecurityException e) {
            _logger.fatal("Error de seguridad", e);
            writer.write(ACTIVATE_SEVERAL);
            ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());
		} catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
            writer.write(ACTIVATE_SEVERAL);
            ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());	
		}
		   
	}


}
