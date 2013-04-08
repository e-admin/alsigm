package es.ieci.tecdoc.fwktd.web.delegate.impl;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.web.delegate.CRUDDelegate;

public class CRUDDelegateImpl<T extends Entity> implements CRUDDelegate<T> {

	public CRUDDelegateImpl(BaseManager<T, String> manager) {
		super();
		setManager(manager);
	}

	public T create(T entity) {
		return getManager().save(entity);
	}

	public void delete(String id) {
		getManager().delete(id);
	}

	public T retrieve(String id) {
		return getManager().get(id);
	}

	public T update(T entity) {
		return getManager().update(entity);
	}

	public BaseManager<T, String> getManager() {
		return manager;
	}

	public void setManager(BaseManager<T, String> manager) {
		this.manager = manager;
	}

	// Members
	protected BaseManager<T, String> manager;
}
