/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.vo.events.IspacAuditAbstractBasicUserEventVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;

/**
 * 
 * Clase de ayuda para rellenar los campos de los objetos de tipo @see
 * TrazaAuditoriaVO a partir de objetos de eventos
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventTrazaBuilderHandlerHelper {

	/**
	 * metodo para popular a partir del parametro evento el parametro traza con
	 * los datos referentes al usuario generador del evento y fecha
	 * 
	 * @param evento
	 * @param traza
	 */
	public static void populateBasicUserEventTrazaAuditoria(
			IspacAuditAbstractBasicUserEventVO evento, TrazaAuditoriaVO traza) {
		String appDescription = evento.getAppDescription();
		traza.setAppDescription(appDescription);

		Long appId = evento.getAppId();
		traza.setAppId(appId);

		// fecha de creacion
		traza.setEventDate(evento.getFecha());

		String userName = evento.getUser();
		traza.setUserName(userName);

		String userId = evento.getIdUser();
		traza.setUserId(userId);

		String userHostName = evento.getUserHostName();
		traza.setUserHostName(userHostName);

		String userIp = evento.getUserIp();
		traza.setUserIp(userIp);

	}

}
