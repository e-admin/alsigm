package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.SessionException;

public class SessionExceptionManager {

	Map optionId = null;

	public SessionExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {
		optionId.put(SessionException.ERROR_SESSION_EXPIRED, new Long(2100010062));
		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
