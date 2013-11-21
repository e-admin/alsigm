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
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
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
public class DtrRech extends HttpServlet implements Keys{

	private static Logger _logger = Logger.getLogger(DtrRech.class);
	private DistributionUseCase distributionUseCase = null;
    private TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        distributionUseCase = new DistributionUseCase();
        factory = TransformerFactory.newInstance();
    }
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
	}
    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServletContext context = getServletConfig().getServletContext();

    	if(context.getInitParameter("PARAMETER_ENCODING") != null)
    		request.setCharacterEncoding(context.getInitParameter("PARAMETER_ENCODING"));

    	response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");

	    //Estado de la distribución (pendiente o aceptado).
        Integer estado = RequestUtils.parseRequestParameterAsInteger(request, "State", new Integer(1));
        //Tipo de distribucion (entrada o salida).
        Integer lnTypeDistr = RequestUtils.parseRequestParameterAsInteger(request, "TypeDistr");
        //Valor del elemento inicial tras ejecutar una accion sobre una selleción de la lista.
        Integer initValue = RequestUtils.parseRequestParameterAsInteger(request, "InitValue");
        //Motivo por el que se rechaza del registro de la lista.
        String motivo = RequestUtils.parseRequestParameterAsString(request, "Remarks");
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
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
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
            // Los errores pueden ser de comunicación, de validación, de transformación, etc...

			distributionUseCase.rejectDistribution(useCaseConf, ids,
					estado.intValue(), initValue.intValue(),
					lnTypeDistr.intValue(), motivo, distWhere, regWhere,
					listOrder);

			Document xmlDocument = distributionUseCase.getDistribution(
					useCaseConf, estado.intValue(), initValue.intValue(),
					lnTypeDistr.intValue(), distWhere, regWhere, listOrder);

            String xslPath = ContextUtil.getRealPath(session.getServletContext(),XSL_DISTRIBUTION_RELATIVE_PATH);
            Transformer transformer = factory.newTransformer(new StreamSource(new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(xslPath)))));
            DocumentSource source = new DocumentSource(xmlDocument);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (DistributionException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (EventException e) {
            _logger.fatal("Error en el evento", e);
            ResponseUtils.generateJavaScriptError(writer, e);
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (TransformerException e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        } catch (Exception e) {
            _logger.fatal("Error en la distribucion", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ));
            ResponseUtils.generateJavaScriptErrorDtrChangeDestination(writer, initValue.intValue());
        }

    }
}
