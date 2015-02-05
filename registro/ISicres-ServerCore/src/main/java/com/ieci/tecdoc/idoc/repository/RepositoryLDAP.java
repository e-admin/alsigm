package com.ieci.tecdoc.idoc.repository;

import java.util.List;

import gnu.trove.THashMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.invesdoc.Idocdbinfo;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.idoc.decoder.dbinfo.IdocBdInfo;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;

/**
 * @author 79426599
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryLDAP {

	private static final Logger log = Logger.getLogger(RepositoryLDAP.class);

	private static RepositoryLDAP singleton = null;
	// HashMap
	private THashMap cacheLDAP = null;

	public RepositoryLDAP(String entidad) {
	    cacheLDAP = new THashMap();

		init(entidad);
	}

	public synchronized static RepositoryLDAP getInstance(String entidad) {
		if (singleton == null) {
			singleton = new RepositoryLDAP(entidad);
		}
		return singleton;
	}

	public LDAPDef getLDAPInfo(){
		return (LDAPDef) cacheLDAP.get(ISicresKeys.LDAP_CONFIGURATION);
	}

	public synchronized void setProperty(String key, Object value) {
		if (key != null && value != null) {
			cacheLDAP.put(key, value);
		}
	}

	private void init(String entidad) {
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Obtenemos la configuración del LDAP para el sistema
	        StringBuffer query = new StringBuffer();
	        query.append("FROM ");
	        query.append(HibernateKeys.HIBERNATE_Idocdbinfo);
	        List list =  session.find(query.toString());
	        Idocdbinfo idocDBInfo = null;
	        String misc = null;
            if(list != null && !list.isEmpty()) {
            	idocDBInfo = (Idocdbinfo) list.get(0);
            	misc = idocDBInfo.getMisc();
            }
            IdocBdInfo idocBdInfo= new IdocBdInfo(misc);
            LDAPDef ldapDef = idocBdInfo.getLdap();
			setProperty(ISicresKeys.LDAP_CONFIGURATION, ldapDef);

		} catch (HibernateException e) {
			log.error("Impossible to load initial values for LDAP.", e);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

}
