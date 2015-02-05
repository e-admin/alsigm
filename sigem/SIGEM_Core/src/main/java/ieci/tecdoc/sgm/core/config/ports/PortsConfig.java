package ieci.tecdoc.sgm.core.config.ports;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class PortsConfig {
	private static final Logger logger = Logger.getLogger(PortsConfig.class);

	private static Map ports = new HashMap();
	
	static{
		try {
			Config configuracion = new Config(new String[]{"SIGEM_spring.xml"});
			ports = (Map) configuracion.getBean("SIGEM_PORTS");
		} catch (Exception e) {
			logger.error("Error inicializando configuración de puertos", e);
		}
	}
		
    public static String getHttpPort() {
		return (String)ports.get(PortsConstants.HTTP_PORT);
	}

    public static String getHttpsPort() {
		return (String)ports.get(PortsConstants.HTTPS_PORT);
	}

    public static String getCertPort() {
		return (String)ports.get(PortsConstants.CERT_PORT);
	}
    
    public static String getHttpFrontendPort() {
		return (String)ports.get(PortsConstants.HTTP_FRONTEND_PORT);
	}

    public static String getHttpsFrontendPort() {
		return (String)ports.get(PortsConstants.HTTPS_FRONTEND_PORT);
	}

    public static String getHttpsFrontendAuthclientPort() {
		return (String)ports.get(PortsConstants.HTTPS_FRONTEND_AUTHCLIENT_PORT);
	}
}
