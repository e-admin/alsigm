/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.utils;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.config.ports.PortsConfig;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class SigemHttpUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SigemHttpUtils.class);



	/**
	 * Devuelve el código base para el protocolo HTTP 
	 * 
	 * de la url de la aplicación web para los applets.
	 * 
	 * Carga el puerto no seguro.
	 * 
	 * @param request
	 * @return
	 */
	public static String getHttpCodeBase(HttpServletRequest request) {
		if (logger.isDebugEnabled()) {
			logger.debug("getHttpCodeBase(HttpServletRequest) - start");
		}

		
		String serverPort = String.valueOf(request.getServerPort());

		String proxyHttpFrontedPort = PortsConfig.getHttpFrontendPort();
		String proxyHttpsFrontEndNoCertPort = PortsConfig.getHttpsFrontendPort();
		String proxyHttpsFrontEndSiCertPort = PortsConfig.getHttpsFrontendAuthclientPort();


		/*
		 * Si el puerto coincide con HTTPSFrontendPort o HttpsFrontedAuthClientPort o HTTPFrontendPort
		 * hay que cargar el HttpFrontedPort.
		 * 
		 * Si el puerto coincide con HttpsPort hay que cargar el HttpPort
		 */
		if ((proxyHttpFrontedPort != null && proxyHttpFrontedPort.equals(serverPort)) ||
			(proxyHttpsFrontEndNoCertPort != null && proxyHttpsFrontEndNoCertPort.equals(serverPort)) ||
			(proxyHttpsFrontEndSiCertPort != null && proxyHttpsFrontEndSiCertPort.equals(serverPort))) {

			// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
			serverPort = proxyHttpFrontedPort;
		} else {
			// Servidor Tomcat
			serverPort = PortsConfig.getHttpPort();
		}
	
		
		StringBuffer codeBase = new StringBuffer();
		codeBase.append("http://");
		String host = request.getServerName();
		
		String contextPath = request.getContextPath();
		codeBase.append(host);
		codeBase.append(":");
		codeBase.append(serverPort);
		codeBase.append(contextPath);
		
		String returnString = codeBase.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getHttpCodeBase(HttpServletRequest): " + returnString);
		}
		return returnString;
	}

}
