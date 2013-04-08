// 
// FileName: Repository.java
//
package com.ieci.tecdoc.common.utils;

import gnu.trove.THashMap;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.utils.HibernateUtil;

/**
 * Este singleton almacena valores que se cargan una única vez en la aplicación.
 * 
 * @author LMVICENTE
 * @version @since @creationDate 06-abr-2004
 */

public class Repository {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static final Logger log = Logger.getLogger(Repository.class);

	private static Repository singleton = null;

	// OSCache
	//private Cache cache = null;

	// SwarmCache
	//private ObjectCache cache = null;
	
	// HashMap
	private THashMap cache = null;

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	private Repository(String entidad) {
		//cache = new Cache(true, true);

//		CacheConfiguration conf = new CacheConfiguration();
//		conf.setCacheType(CacheConfiguration.TYPE_AUTO);
//		CacheFactory cacheFactory = new CacheFactory(conf);
//		cache = cacheFactory.createCache(Repository.class.getName());

	    cache = new THashMap();
	    
		init(entidad);
	}

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	public synchronized static Repository getInstance(String entidad) {
		if (singleton == null) {
			singleton = new Repository(entidad);
		}
		return singleton;
	}

	public Boolean isInBook(Integer bookId) {
	    return isInBook(bookId.toString());
	}

	public Boolean isOutBook(Integer bookId) {
	    return isOutBook(bookId.toString());
	}

	public Boolean isInBook(String bookId) {
		//		try {
		//			return new Boolean(((Integer) cache.getFromCache(bookId)).intValue() == ISicresKeys.SCRREGSTATE_IN_BOOK);
		//		} catch (NeedsRefreshException nE) {
		//			return null;
		//		}
		return new Boolean(((Integer) cache.get(bookId)).intValue() == ISicresKeys.SCRREGSTATE_IN_BOOK);
	}

	public Boolean isOutBook(String bookId) {
		//		try {
		//			return new Boolean(((Integer) cache.getFromCache(bookId)).intValue() == ISicresKeys.SCRREGSTATE_OUT_BOOK);
		//		} catch (NeedsRefreshException nE) {
		//			return null;
		//		}
		return new Boolean(((Integer) cache.get(bookId)).intValue() == ISicresKeys.SCRREGSTATE_OUT_BOOK);
	}

	public Boolean hasExternalDistribution() {
		return (Boolean) cache.get(HibernateKeys.HIBERNATE_ScrExtdist);
	}
	
	public int getLicencesNumber(){
		//Iuserlicenc iuserlicenc = (Iuserlicenc) cache.get(ServerKeys.HIBERNATE_Iuserlicenc);
		return Integer.MAX_VALUE;
	}
	
	public synchronized void setProperty(String key, Object value) {
		if (key != null && value != null) {
			//cache.putInCache(key, value);
			cache.put(key, value);

//			StringBuffer buffer = new StringBuffer();
//			buffer.append("Repository key[");
//			buffer.append(key);
//			buffer.append("] value [");
//			buffer.append(value);
//			buffer.append("]");
//			log.info(buffer.toString());
		}
	}

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	private void init(String entidad) {
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Consultamos si existe distribucion externa
/*			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT count(*) FROM ");
			buffer.append(ServerKeys.HIBERNATE_ScrExtdist);
			int size = ((Integer) session.iterate(buffer.toString()).next()).intValue();
			Boolean externalDistribution = null;
			if (size == 0) {
				externalDistribution = Boolean.FALSE;
			} else {
				externalDistribution = Boolean.TRUE;
				log.info("Returned from [" + ServerKeys.HIBERNATE_ScrExtdist + "] a collection with size: " + size);
			}
			setProperty(ServerKeys.HIBERNATE_ScrExtdist, externalDistribution);
*/
			// Recuperamos la licencia de invesdoc
			List list = session.find("FROM " + HibernateKeys.HIBERNATE_Iuserlicenc);
			if (list != null && !list.isEmpty()) {
				setProperty(HibernateKeys.HIBERNATE_Iuserlicenc, list.get(0));
			}
		} catch (HibernateException e) {
			log.error("Impossible to load intial values for Repository.", e);
		}

	}

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/
}