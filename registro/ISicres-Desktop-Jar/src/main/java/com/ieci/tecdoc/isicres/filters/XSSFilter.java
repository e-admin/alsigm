package com.ieci.tecdoc.isicres.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;

public class XSSFilter implements Filter {

	public Map validador = new HashMap();
	public static final String VALIDACION_ESTANDAR = "pattern.defecto";
	public Pattern validacionEstandar = null;
	public Properties config = new Properties();
	private static Logger _logger = Logger.getLogger(XSSFilter.class);

    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {
	if(req != null) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Enumeration paramNames = req.getParameterNames();
		String paramName = null;
		String paramValue = null;
		boolean valido = true;
		Pattern pattern = null;
		Matcher matcher = null;
		while(paramNames.hasMoreElements()) {
			paramName = (String)paramNames.nextElement();
			paramValue = req.getParameter(paramName);
			if(validador.containsKey(paramName)) {
				pattern = (Pattern)validador.get(paramName);
				matcher = pattern.matcher(paramValue);
				valido = matcher.matches();
			}

			if(valido) {
				matcher = validacionEstandar.matcher(paramValue.toLowerCase());
				valido = !matcher.find();
			}

			if(!valido) {
				stopErrorValidation(request, response, paramName,
							paramValue);
				return;
			}
		}
		chain.doFilter(req, res);
	}
    }

	private void stopErrorValidation(HttpServletRequest request,
			HttpServletResponse response, String paramName, String paramValue)
			throws IOException {

		_logger.warn("Se ha detectado un parametro invalido: " + paramName + "(" + paramValue +")");
		Locale locale = request.getLocale();

		// Texto del idioma. Ej: EU_
		String idioma = (String) request.getSession().getAttribute(Keys.J_IDIOMA);
		if(idioma == null){
			idioma = IdiomaUtils.getInstance().getIdioma(request);
		}
		// Numero del idioma. Ej: 10
		Long numIdioma = (Long) request.getSession().getAttribute(Keys.J_NUM_IDIOMA);
		if(numIdioma == null){
			numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);
		}

		response.setContentType("text/html; charset=UTF-8");
		RBUtil rbutil = RBUtil.getInstance(locale);
		Writer writer = response.getWriter();
		//se alerta de campos erróneos y se redirecciona la aplicación
		ResponseUtils.generateJavaScriptLogSessionExpiredDtrfdr(writer,
				rbutil.getProperty("exception.validationException"), idioma,numIdioma);
	}

    public void init(FilterConfig filterConfig) throws ServletException {
	try {
		Map patterns = new HashMap();
		_logger.info("Inicializando configuracion");
		_logger.info("Cargando fichero propiedades");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ParamValidation.properties");
		config.load(is);
		_logger.info("Fichero cargado");

		_logger.info("Configurando validacion estandar");
		validacionEstandar = Pattern.compile(config.getProperty(VALIDACION_ESTANDAR));

		Enumeration props = config.propertyNames();
		String prop = null;
		String propPattern = null;
		String sPattern = null;
		while(props.hasMoreElements()) {
			prop = (String)props.nextElement();
			_logger.info("Cargando " + prop);
			//Si no es un patron es un campo a validar.
			if (!prop.startsWith("pattern.")) {
				propPattern = config.getProperty(prop);
				if(!patterns.containsKey(propPattern)) {
					sPattern = config.getProperty(propPattern);
					patterns.put(propPattern, Pattern.compile(sPattern));
				}
				validador.put(prop, patterns.get(propPattern));
			}
		}
	} catch(Exception e) {
		_logger.fatal("No se ha podido configurar el filtro anti-xss.", e);
		throw new ServletException("No se ha podido configurar el filtro anti-xss.", e);
	}
    }

    public void destroy() {}
}
