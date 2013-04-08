package com.ieci.tecdoc.isicres.desktopweb.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditContext;
import es.ieci.tecdoc.fwktd.audit.integration.context.AuditContextHolder;
/**
 * Clase de Utilidades para la capa WEB de ISicres
 * @author IECISA
 *
 */
public class Utils {

	/**
	 * Método que añade a la variable de tipo ThrealLocal la información de IP y
	 * nombre de Host
	 *
	 * @param request
	 */
	public static void setAuditContext(HttpServletRequest request) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		AuditContextHolder.setAuditContext(auditContext);
	}
	/**
	 * Metodo que devuelve la entidad en la que se trabaja
	 * @param request
	 * @return
	 */
	public static String getEntidad(HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		UseCaseConf loginStatus = (UseCaseConf)mySession.getAttribute(Keys.J_USECASECONF);
		if(loginStatus!=null) {
			return loginStatus.getEntidadId();
		} else {
			return null;
		}
	}
}
