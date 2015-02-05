/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

/**
 * 
 * @author IECISA
 * 
 * @param <T>
 * @param <PK>
 */
public class HibernateGenericDaoImpl<Entity, Id extends Serializable>
		implements BaseDao<Entity, Id> {

	protected static final Logger logger = LoggerFactory
			.getLogger(HibernateGenericDaoImpl.class);
	private Class<Entity> persistentClass;

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public HibernateGenericDaoImpl(final Class<Entity> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public HibernateGenericDaoImpl(final Class<Entity> persistentClass,
			SessionFactory sessionFactory) {
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public Class<Entity> getPersistentClass() {
		return persistentClass;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Required
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public int count() {
		return getAll().size();
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Id anId) {
		getHibernateTemplate().delete(this.get(anId));
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(Id anId) {
		Entity entity = (Entity) getHibernateTemplate().get(
				this.persistentClass, anId);
		return entity != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity get(Id anId) {
		Entity entity = (Entity) getHibernateTemplate().get(
				getPersistentClass(), anId);

		if (entity == null) {
			logger
					.warn(
							"El objeto de tipo '{} con identificador '{}' no se ha encontrado...",
							getPersistentClass(), anId);
			throw new ObjectRetrievalFailureException(getPersistentClass(),
					anId);
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Entity> getAll() {
		return getHibernateTemplate().loadAll(getPersistentClass());
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Entity> getAllDistinct() {
		Collection<Entity> result = new LinkedHashSet<Entity>(getAll());
		return new ArrayList<Entity>(result);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity save(Entity entity) {
		return (Entity) getHibernateTemplate().merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity update(Entity entity) {
		return save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() {
		getHibernateTemplate().deleteAll(getAll());
	}

}