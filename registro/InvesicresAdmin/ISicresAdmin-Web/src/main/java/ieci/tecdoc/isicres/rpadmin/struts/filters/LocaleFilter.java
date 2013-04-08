package ieci.tecdoc.isicres.rpadmin.struts.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;

public class LocaleFilter implements Filter {

	protected LocaleFilterHelper helper= new LocaleFilterHelper ();


	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		helper.doFilter(request, response);
		chain.doFilter(request, response);


	}

	public void init(FilterConfig cfg) throws ServletException {


	}

}
