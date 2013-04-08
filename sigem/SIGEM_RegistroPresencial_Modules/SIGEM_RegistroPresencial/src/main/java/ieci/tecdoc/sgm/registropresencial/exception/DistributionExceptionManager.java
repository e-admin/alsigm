package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.DistributionException;

public class DistributionExceptionManager {

	Map optionId = null;

	public DistributionExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {

		optionId.put(DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION, new Long(2100010070));
		optionId.put(DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION, new Long(2100010071));
		optionId.put(DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE, new Long(2100010072));
		optionId.put(DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_DIST_LIST, new Long(2100010073));
		optionId.put(DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION, new Long(2100010074));
		optionId.put(DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION, new Long(2100010075));
		optionId.put(DistributionException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED, new Long(2100010076));
		optionId.put(DistributionException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER, new Long(2100010077));
		optionId.put(DistributionException.ERROR_NO_REGISTER_PERMISSION_ABOUT_ANY_BOOK, new Long(2100010078));

		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
