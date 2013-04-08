package es.ieci.tecdoc.fwktd.server.manager.impl;

import java.io.Serializable;
import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseReadOnlyDao;
import es.ieci.tecdoc.fwktd.server.manager.BaseReadOnlyManager;

public class BaseReadOnlyManagerImpl<Entity, Id extends Serializable>
		implements BaseReadOnlyManager<Entity, Id> {

	public BaseReadOnlyDao<Entity, Id> getDao() {
		return dao;
	}

	public void setDao(BaseReadOnlyDao<Entity, Id> dao) {
		this.dao = dao;
	}

	/**
	 * Constructor de la clase. Toma como argumento el Dao encargado de
	 * gestionar las entidades del tipo indicado para el presente manager.
	 *
	 * @param aGenericDao
	 */
	public BaseReadOnlyManagerImpl(BaseReadOnlyDao<Entity, Id> aGenericDao) {
		this.dao = aGenericDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public int count() {
		return getDao().count();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(Id anId) {
		return getDao().exists(anId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity get(Id anId) {
		return getDao().get(anId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Entity> getAll() {
		return getDao().getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Entity> getAllDistinct() {
		return getDao().getAllDistinct();
	}

	// Members
	protected BaseReadOnlyDao<Entity, Id> dao;

}