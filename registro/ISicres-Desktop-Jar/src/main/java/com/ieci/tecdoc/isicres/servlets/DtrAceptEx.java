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
import java.io.StringWriter;
import java.io.Writer;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.SQLValidator;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.distribution.DistributionUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * @author jcebrien
 *
 */
public class DtrAceptEx extends HttpServlet implements Keys {

    private static Logger _logger = Logger.getLogger(DtrAceptEx.class);

    private DistributionUseCase distributionUseCase = null;

    private TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        distributionUseCase = new DistributionUseCase();
        factory = TransformerFactory.newInstance();
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

        //Estado de la distribución (pendiente o aceptado).
        Integer estado = RequestUtils.parseRequestParameterAsInteger(request, "State", new Integer(1));
        //Identificador de libro de entrada (opcional).
        Integer bookId = RequestUtils.parseRequestParameterAsInteger(request, "BookId", new Integer(0));
        //Valor del elemento inicial tras ejecutar una accion sobre una
        // selleción de la lista.
        Integer initValue = RequestUtils.parseRequestParameterAsInteger(request, "InitValue");
        //Tipo de distribucion (entrada o salida).
        Integer lnTypeDistr = RequestUtils.parseRequestParameterAsInteger(request, "TypeDistr");
        //Registros seleccionados de la lista.
        List ids = RequestUtils.parseRequestParametersAsList(request, "Ids");
        if (_logger.isDebugEnabled()) {
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Integer flI = (Integer) it.next();
                _logger.debug("===================> ids " + flI.intValue());
            }
        }
        // Clausura WHERE de búsqueda de registros distribuidos.
        String distWhere = RequestUtils.parseRequestParameterAsString(request, "distWhere");
        // Clausura WHERE de búsqueda de registros distribuidos.
        String regWhere = RequestUtils.parseRequestParameterAsString(request, "regWhere");
		// Lista de ordenación de la bandeja de distribución
		String listOrder = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "orderDistribution");

         // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
        // el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        PrintWriter writer = response.getWriter();
        try {
		//Validamos que los valores para generar el where son correctos
		// Invocamos al método que valida el where para los campos distribución
		SQLValidator.getInstance().validateDistributionDistWhere(distWhere);
		// Invocamos al método que valida el where para los campos del registro
		// y retorna la consulta tratada
		regWhere = SQLValidator.getInstance().validateDistributionRegWhere(useCaseConf,
				lnTypeDistr, regWhere);

            // Transformamos el xml mediante la xsl en html.
            // Los errores pueden ser de comunicación, de validación, de
            // transformación, etc...
        	String xslPath = null;
			Document xmlDocumentAccept = distributionUseCase
					.acceptDistributionEx(useCaseConf, ids, estado.intValue(),
							initValue.intValue(), lnTypeDistr.intValue(),
							bookId, distWhere, regWhere, listOrder);

			if (xmlDocumentAccept == null) {
				ResponseUtils.generateJavaScriptLog(writer,
						RBUtil.getInstance(useCaseConf.getLocale())
								.getProperty(Keys.I18N_DTREX_ACCEPT_SATISFY));
				ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer,
						initValue.intValue());
			} else {
				xslPath = ContextUtil.getRealPath(session.getServletContext(),
						XSL_DISTRIBUTIONBOOK_RELATIVE_PATH);
				Transformer transformer = factory
						.newTransformer(new StreamSource(new InputStreamReader(
								new BufferedInputStream(new FileInputStream(
										xslPath)))));
				DocumentSource source = new DocumentSource(xmlDocumentAccept);
				Writer outWriter = new StringWriter();
				StreamResult result = new StreamResult(outWriter);
				transformer.transform(source, result);

				String html = result.getWriter().toString();
				html = parseValues(html);
				ResponseUtils.generateJavaScriptVldBooksDtrAcceptEx(writer,
						html, initValue.intValue());
				writer.flush();
				writer.close();
			}

        } catch (RemoteException e) {
            _logger.fatal("Error de comunicaciones", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (DistributionException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (EventException e) {
            _logger.fatal("Error en el evento", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptLogSessionExpiredDtrAcceptRechEx(writer, e.getMessage(), idioma, numIdioma);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (TransformerException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        } catch (Exception e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrAceptRechEx(writer, initValue.intValue());
        }

    }
    private String parseValues (String value)
    {
    	String result = value;
       if (value == null) return "";

       char c;

       result = result.replaceAll("<","%3C");
       result = result.replaceAll(">","%3E");
       result = result.replaceAll("\"","%22");
       result = result.replaceAll("'","%27");
       result = result.replaceAll("\\n","");
       result = result.replaceAll("\\r","");
       return result;
    }
}