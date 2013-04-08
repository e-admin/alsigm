//
// FileName: HibernateUtil.java
//
package com.ieci.tecdoc.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ISicresKeys;

/**
 * Una única SessionFactory y una Session por thread.
 *
 * @author lmvicente
 * @version @since @creationDate 04-mar-2004
 */

public class HibernateUtil implements ISicresKeys {

	private static final Logger log = Logger.getLogger(HibernateUtil.class);

	private static final Map sessionFactorys = new HashMap();

	private static final Map hibernateCfgs = new HashMap();

	private static final ThreadLocal sessions = new ThreadLocal();

	public static void addEntityConfig(String entity) throws HibernateException {
		Configuration hibernateCfg = null;
		String entityKey = null;
		if (entity != null) {
			entityKey = entity;
		} else {
			log.fatal("La entidad suministrada tiene valor nulo");
			throw new HibernateException(
					"La entidad suministrada tiene valor nulo");
		}
		if (!hibernateCfgs.containsKey(entityKey)) {
			try {

				String hibernateFilePath= findHibernateConfigFile();
				hibernateCfg = new Configuration().configure(new File(hibernateFilePath));

			} catch (HibernateException e) {
				log.fatal("No se puede encontrar Hibernate.", e);
				throw e;
			}
			if (!entityKey.equals(IS_INVESICRES)) {
				StringBuffer sbJndi = new StringBuffer(hibernateCfg
						.getProperty("hibernate.connection.datasource"));

				//comprobamos si la cadena contiene al final GUION BAJO, si no lo contiene lo  añadimos
				if(!StringUtils.endsWithIgnoreCase(sbJndi.toString(), GUION_BAJO)){
					sbJndi = sbJndi.append(GUION_BAJO);
				}

				sbJndi = sbJndi.append(entityKey);
				hibernateCfg.setProperty("hibernate.connection.datasource",
						sbJndi.toString());
			}
			hibernateCfgs.put(entityKey, hibernateCfg);
		} else {
			hibernateCfg = (Configuration) hibernateCfgs.get(entityKey);
		}
		if (!sessionFactorys.containsKey(entityKey)) {
			SessionFactory sessionFactory = hibernateCfg.buildSessionFactory();
			sessionFactorys.put(entityKey, sessionFactory);
		}
	}


	protected static String findHibernateConfigFile() {
		String result = null;
		HibernateUtilHelper helper = new HibernateUtilHelper();
		result = helper.findHibernateConfigFile();
		return result;
	}


	public static Session currentSession(String entity)
			throws HibernateException {
		Map ss = (Map) sessions.get();
		if (ss == null) {
			sessions.set(new HashMap());
			ss = (Map) sessions.get();
		}
		String entityKey = null;
		if (entity != null) {
			entityKey = entity;
			if (!hibernateCfgs.containsKey(entityKey)) {
				addEntityConfig(entityKey);
			}
		} else {
			log
					.fatal("No se ha encontrado datos para conectar a la BBDD del ayuntamiento, probablemente no se ha proporcionado uno");
		}
		Session s = (Session) ss.get(entityKey);

		if (s == null) {
			s = ((SessionFactory) sessionFactorys.get(entityKey)).openSession();
			ss.put(entityKey, s);
		}
		return s;
	}

	public static void closeSession(String entity) {
		try {
			Map ss = (Map) sessions.get();
			String entityKey = null;
			if (entity != null) {
				entityKey = entity;
			} else {
				log
						.fatal("No se ha encontrado datos para conectar a la BBDD del ayuntamiento, probablemente no se ha proporcionado uno");
			}
			Session s = (Session) ss.get(entityKey);
			if (s != null) {
				s.close();
				ss.put(entityKey, null);
			}
		} catch (HibernateException hE) {
			log.fatal("No se puede cerrar una sesion de Hibernate.", hE);
		}
	}

	public static void commitTransaction(Transaction t)
			throws HibernateException {
		try {
			if (t != null && !t.wasCommitted()) {
				t.commit();
			}
		} catch (HibernateException hE) {
			log.fatal("No se puede hacer commit.", hE);
			throw hE;
		}
	}

	public static void rollbackTransaction(Transaction t) {
		try {
			if (t != null && !t.wasRolledBack()) {
				t.rollback();
			}
		} catch (HibernateException hE) {
			log.fatal("No se puede hacer rollback.", hE);
		}
	}

}
