//
// FileName: CacheDebugger.java 
//
package com.ieci.tecdoc.utils.cache.oscache;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.events.CacheEntryEvent;
import com.opensymphony.oscache.extra.CacheEntryEventListenerImpl;

/**
 * @author lmvicente
 * @version @since @creationDate 31-mar-2004 
 */

public class CacheDebugger extends CacheEntryEventListenerImpl {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static final Logger log = Logger.getLogger(CacheDebugger.class);

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	public void cacheEntryAdded(CacheEntryEvent arg0) {
		super.cacheEntryAdded(arg0);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Cache entry added [");
		buffer.append(arg0.getEntry().getKey());
		buffer.append("]. Added [");
		buffer.append(getEntryAddedCount());
		buffer.append("] Removed [");
		buffer.append(getEntryRemovedCount());
		buffer.append("]");

		log.debug(buffer.toString());
	}

	public void cacheEntryUpdated(CacheEntryEvent arg0) {
		super.cacheEntryUpdated(arg0);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Cache entry updated [");
		buffer.append(arg0.getEntry().getKey());
		buffer.append("]. Updated [");
		buffer.append(getEntryUpdatedCount());
		buffer.append("]");

		log.debug(buffer.toString());
	}

	public void cacheEntryRemoved(CacheEntryEvent arg0) {
		super.cacheEntryRemoved(arg0);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Cache entry removed [");
		if (arg0.getEntry().getKey() != null) {
			buffer.append(arg0.getEntry().getKey());
		}
		buffer.append("]. Added [");
		buffer.append(getEntryAddedCount());
		buffer.append("] Removed [");
		buffer.append(getEntryRemovedCount());
		buffer.append("]");

		log.debug(buffer.toString());
	}

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
