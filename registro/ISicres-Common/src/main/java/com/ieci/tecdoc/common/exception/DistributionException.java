//
// FileName: SecurityException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class DistributionException extends TecDocException implements Serializable {

    public static final String ERROR_CANNOT_OBTAIN_DISTRIBUTION = "distributionException.canNotObtainDistribution";
    public static final String ERROR_CANNOT_ACCEPT_DISTRIBUTION = "distributionException.canNotAcceptDistribution";
    public static final String ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE = "distributionException.distributionRegisterNotInState";
    public static final String ERROR_DISTRIBUTION_REGISTER_NOT_DIST_LIST = "distributionException.distributionRegisterNotDistList";
    public static final String ERROR_CANNOT_REJECT_DISTRIBUTION = "distributionException.canNotRejectDistribution";
    public static final String ERROR_CANNOT_SAVE_DISTRIBUTION = "distributionException.canNotSaveDistribution";
    public static final String ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED = "distributionException.distributionTypeNotSupported";
    public static final String ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER = "distributionException.canNotUpdatedistributionLockRegister";
    public static final String ERROR_NO_REGISTER_PERMISSION_ABOUT_ANY_BOOK = "distributionException.noRegisterPermissionAboutAnyBook";
    public static final String ERROR_REGISTERS_NOT_DISTRIBUTED = "distributionException.distributedNotRegisters";
    
	/**
	 * @param key
	 */
	public DistributionException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public DistributionException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public DistributionException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public DistributionException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public DistributionException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public DistributionException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public DistributionException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public DistributionException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

}
