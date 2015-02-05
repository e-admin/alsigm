package es.ieci.tecdoc.isicres.api.audit;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.IsicresAuditoriaManager;
import es.ieci.tecdoc.isicres.api.audit.business.manager.impl.IsicresAuditoriaManagerImpl;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class ISicresAuditConstants {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ISicresAuditConstants.class);

	private static IsicresAuditoriaManager auditoriaManager = new IsicresAuditoriaManagerImpl();
	private static final String APP_DESCRIPTION = "REGISTRO_PRESENCIAL";
	private static Long APP_ID = null;

	
	public static Long getAppId(){
		if (APP_ID == null){
			try{
				AppAuditoriaVO appAuditoriaVO = auditoriaManager.getAppAuditoria(APP_DESCRIPTION);
				APP_ID = appAuditoriaVO.getAppId();
			}catch(Exception e){
				logger.error("Error al obtener el ID de la aplicación de la auditoría asociada a esta aplicación: "+APP_DESCRIPTION, e);
			}
		}
		return APP_ID;
	}
	
	public static String getAppDescription(){
		return APP_DESCRIPTION;
	}
}
