package ieci.tecdoc.sgm.core.web.locale.telematico;

import ieci.tecdoc.sgm.core.web.locale.LocaleFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LocaleTelematicoFilter extends LocaleFilter {
	
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		helper = new LocaleTelematicoFilterHelper();
		super.doFilter(request, response, chain);
	}
}
