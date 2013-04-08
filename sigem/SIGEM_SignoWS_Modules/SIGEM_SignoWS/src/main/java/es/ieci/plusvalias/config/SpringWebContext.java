package es.ieci.plusvalias.config;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringWebContext implements ServletContextAware{
	private static ApplicationContext ctx;
	
	public void setServletContext(ServletContext servletContext) {
		SpringWebContext.ctx=WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
	
	public static ApplicationContext getContext() {
		return ctx;
	}
}
