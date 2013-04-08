package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.AttributesException;

public class AttributesExceptionManager {

	Map optionId = null;

	public AttributesExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {

		optionId.put(AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS,
				new Long(2100010063));
		optionId.put(AttributesException.ERROR_NOT_VALIDATION_FIELD, new Long(2100010064));
		optionId.put(
				AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS,
				new Long(2100010065));
		optionId.put(AttributesException.ERROR_CANNOT_FIND_CITIES, new Long(2100010066));
		optionId.put(AttributesException.ERROR_CANNOT_FIND_INTER, new Long(2100010067));
		optionId.put(AttributesException.ERROR_CANNOT_FIND_PROV, new Long(2100010068));
		optionId
				.put(AttributesException.ERROR_CANNOT_FIND_PERSON_ADDRESS, new Long(2100010069));

		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
