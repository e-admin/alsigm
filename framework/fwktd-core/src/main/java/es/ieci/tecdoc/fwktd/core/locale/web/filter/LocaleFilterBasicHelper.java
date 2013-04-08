package es.ieci.tecdoc.fwktd.core.locale.web.filter;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.ieci.tecdoc.fwktd.core.locale.web.LocaleUtilStrutsJstlImpl;

public class LocaleFilterBasicHelper {
	
	public static LocaleUtilStrutsJstlImpl localeUtil= new LocaleUtilStrutsJstlImpl();

	public void  doFilter(ServletRequest request, ServletResponse response){
		
		if (request instanceof HttpServletRequest) {
			
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			
			HttpSession session = httpRequest.getSession();
			Locale localeSession = (Locale)session.getAttribute(LocaleUtilStrutsJstlImpl.LOCALE_PARAM_NAME);
						
			
			if (localeSession==null ){
				localeUtil.setLocale(httpRequest);
			}
				
			Locale localeFromRequestParam=localeUtil.parseLocaleFromRequest(httpRequest);
			if (localeSession!=null && localeFromRequestParam!=null && !localeSession.toString().equals(localeFromRequestParam.toString())){
				localeUtil.setLocale(httpRequest);
			}
		
		
			
		}
	
		
	}
	
	public static Locale getCurrentLocale(HttpServletRequest request){
		Locale result =null;
		result=localeUtil.getCurrentLocale(request);
		return result;
	}

}
