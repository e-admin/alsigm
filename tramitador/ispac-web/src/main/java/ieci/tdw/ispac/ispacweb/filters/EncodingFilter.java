package ieci.tdw.ispac.ispacweb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class EncodingFilter implements Filter {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(EncodingFilter.class); 

	private static final String DEFAULT_ENCODING = "ISO-8859-15";
	
	private String encoding = DEFAULT_ENCODING;
	
	
	/**
	 * Constructor.
	 */
	public EncodingFilter() {
		super();
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("encoding");
		if ((encodingParam != null) && (encodingParam.trim().length() > 0)) {
			setEncoding(encodingParam);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Estableciendo el encoding a: " + getEncoding());
		}
		
		request.setCharacterEncoding(getEncoding());
		
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
