package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.SecurityException;

public class SecurityExceptionManager {

	Map optionId = null;

	public SecurityExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {

		optionId.put(SecurityException.ERROR_SQL, new Long(2100010053));
		optionId.put(SecurityException.ERROR_USER_NOTFOUND, new Long(2100010054));
		optionId.put(SecurityException.ERROR_PASSWORD_INCORRECT, new Long(2100010055));
		optionId.put(SecurityException.ERROR_USER_ISLOCKED, new Long(2100010056));
		optionId.put(SecurityException.ERROR_USER_WASLOCKED, new Long(2100010057));
		optionId.put(SecurityException.ERROR_IMPOSSIBLE_LOGOUT, new Long(2100010058));
		optionId.put(SecurityException.ERROR_IUSEROBJPERM_NOT_FOUND, new Long(2100010059));
		optionId.put(SecurityException.ERROR_SCRUSRPERM_NOT_FOUND, new Long(2100010060));
		optionId.put(SecurityException.ERROR_SCROFIC_NOT_FOUND, new Long(2100010061));

		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
