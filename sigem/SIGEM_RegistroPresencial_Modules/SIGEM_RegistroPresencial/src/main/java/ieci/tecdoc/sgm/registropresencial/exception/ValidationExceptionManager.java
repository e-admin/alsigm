package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.ValidationException;

public class ValidationExceptionManager {

	Map optionId = null;

	public ValidationExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {
		optionId.put(ValidationException.ERROR_ATTRIBUTE_VALIDATION, new Long(2100010047));
		optionId.put(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND, new Long(2100010048));
		optionId.put(ValidationException.ERROR_LOGIN_LENGTH, new Long(2100010049));
		optionId.put(ValidationException.ERROR_PASSWORD_LENGTH, new Long(2100010050));
		optionId.put(ValidationException.ERROR_PAGE_NAME_LENGTH, new Long(2100010051));
		optionId.put(ValidationException.ERROR_PAGE_NAME_EXTENSION, new Long(2100010052));

		optionId.put(ValidationException.ERROR_DOCUMENT_NAME_LENGTH, new Long(2100010079));

		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
