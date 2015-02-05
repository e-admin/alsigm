/**
 * 
 */
package ieci.tdw.ispac.audit;

import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditConstants {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(IspacAuditConstants.class);

	public static final String APP_DESCRIPTION = "TRAMITACION_EXPEDIENTES";
	private static Long APP_ID = null;
	
	private IspacAuditConstants() {

		
	}


	
	public static Long getAppId(){
		if (APP_ID == null){
			try {
				IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();
				AppAuditoriaVO appAuditoriaVO = auditoriaManager.getAppAuditoria(APP_DESCRIPTION);
				APP_ID = appAuditoriaVO.getAppId();
			} catch (Exception e) {
				logger.error(
						"Error al obtener el ID de la aplicación de la auditoría asociada a esta aplicación: "
								+ APP_DESCRIPTION, e);
			}
		}
		return APP_ID;
	}
}
