package ieci.tdw.ispac.ispaclib.common.constants;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

/**
 * 
 * Constantes que identifican los tipos de bloqueos de un docuemnto
 */
public class DocumentLockStates extends EntityLockStates{
	
	/**
	 * El documento se encuentra bloqueado solo para la edici&oacute;n 
	 */
	public static final String EDIT_LOCK = "2";
	
	public static boolean hasAnyLock(String lockState){
		if(lockState==null) {
			return false;
		}
		return (StringUtils.equals(lockState, TOTAL_LOCK) || StringUtils.equals(lockState, EDIT_LOCK)); 
	}
}
