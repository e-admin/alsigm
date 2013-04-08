// 
// FileName: CacheBag.java
//
package com.ieci.tecdoc.utils.cache;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.EntryRefreshPolicy;

/**
 * @author lmvicente
 * @version @since @creationDate 31-mar-2004
 */

public class CacheBag extends THashMap implements EntryRefreshPolicy {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static final Logger log = Logger.getLogger(CacheBag.class);

	private static final int INITIAL_SIZE = 10;

	private String sessionID = null;

	private int timeOut = Integer.MIN_VALUE;

	private boolean deleted = false;

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	public CacheBag() {
		this(INITIAL_SIZE);
	}

	public CacheBag(TObjectHashingStrategy arg0) {
		super(arg0);
	}

	public CacheBag(int arg0) {
		super(arg0);
	}

	public CacheBag(int arg0, TObjectHashingStrategy arg1) {
		super(INITIAL_SIZE, arg1);
	}

	public CacheBag(int arg0, float arg1) {
		super(arg0, arg1);
	}

	public CacheBag(int arg0, float arg1, TObjectHashingStrategy arg2) {
		super(INITIAL_SIZE, arg1, arg2);
	}

	public CacheBag(Map arg0) {
		super(arg0);
	}

	public CacheBag(Map arg0, TObjectHashingStrategy arg1) {
		super(arg0, arg1);
	}

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	/**
	 * @return Returns the sessionID.
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID
	 *            The sessionID to set.
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * @return Returns the deleted.
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            The deleted to set.
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean needsRefresh(CacheEntry arg0) {
	    if (Integer.parseInt(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_ENTRY_TIMEOUT)) == -1) {
	        return false;
	    }
	    
		// Esta comprobación se hace por si otro proceso a borrado
		// este objeto a traves del setDeleted(boolean)
		if (deleted) {
			return true;
		}

		if (timeOut == Integer.MIN_VALUE) {
			init();
		}

		deleted = (System.currentTimeMillis() - arg0.getLastUpdate()) > timeOut;

		return deleted;
	}

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	private void init() {
		timeOut = Integer.parseInt(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_ENTRY_TIMEOUT)) * 1000;
	}

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
