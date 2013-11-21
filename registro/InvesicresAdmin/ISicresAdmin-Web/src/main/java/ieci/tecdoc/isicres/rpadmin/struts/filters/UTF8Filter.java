package ieci.tecdoc.isicres.rpadmin.struts.filters;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtro para que la aplicación acepte codificación en formato UTF-8
 *
 * @author SLuna
 * @version 1.0
 * @since 2008/11/10
 */
public class UTF8Filter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UTF8Filter.class);

	/**
	 * Cadena con la codificación
	 */
	private String encoding;

	/**
	 * Recogemos el tipo de codificación definido en el web.xml Si no se hubiera
	 * especificado ninguno se toma "UTF-8" por defecto
	 *
	 * @param aFilterConfig
	 *            Filtro de configuración
	 * @throws ServletException
	 *             Excepción lanzada por el servlet en caso de error
	 */
	public void init(FilterConfig aFilterConfig) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("init(FilterConfig) - start");
		}

		encoding = aFilterConfig.getInitParameter("requestEncoding");

		if (encoding == null) {

			encoding = "UTF-8";

		}

		if (logger.isDebugEnabled()) {
			logger.debug("init(FilterConfig) - end");
		}
	}

	/**
	 * Metemos en la request el formato de codificacion UTF
	 *
	 * @param aRequest
	 *            La petición
	 * @param aResponse
	 *            La respuesta
	 * @param aFilterChain
	 *            La cadena de filtro
	 * @throws IOException
	 *             Excepción lanzada en caso de error de E/S
	 * @throws ServletException
	 *             Excepción lanzada por el servlet en caso de error
	 */
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse,
			FilterChain aFilterChain) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start. Encoding: " + aRequest.getCharacterEncoding());
		}


		aRequest.setCharacterEncoding(encoding);
		aFilterChain.doFilter(aRequest, aResponse);

		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end. Encoding: " + aRequest.getCharacterEncoding());
		}
	}

	/**
	 * Método 'destroy'
	 */
	public void destroy() {
	}

}
