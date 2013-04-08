package com.ieci.tecdoc.isicres.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.IsicresContextServletPath;
import com.ieci.tecdoc.isicres.desktopweb.utils.ISicresContextPathUtils;

public class IsicresServletContextListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(IsicresServletContextListener.class);
	

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		
		IsicresContextServletPath.SERVLET_CONTEXT_PATH=ISicresContextPathUtils.getWebServletContextPath(event.getServletContext());
	}
	

}
