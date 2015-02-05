package es.ieci.tecdoc.fwktd.core.locale.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtro que permite setear el locale correcto a utilizar
 * 
 */
public class LocaleFilterBasicImpl implements Filter {

	protected LocaleFilterBasicHelper helper = new LocaleFilterBasicHelper();

	public void destroy() {
		// nothing to do
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		helper.doFilter(request, response);
		chain.doFilter(request, response);

	}

	public void init(FilterConfig cfg) throws ServletException {
		// nothing to do
	}

}