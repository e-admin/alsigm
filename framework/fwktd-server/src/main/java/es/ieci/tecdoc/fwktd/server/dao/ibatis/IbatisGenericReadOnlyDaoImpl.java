package es.ieci.tecdoc.fwktd.server.dao.ibatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.util.ClassUtils;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.fwktd.server.dao.BaseReadOnlyDao;

public class IbatisGenericReadOnlyDaoImpl<Entity, Id extends Serializable>
		extends IbatisGenericBaseDaoImpl implements BaseReadOnlyDao<Entity, Id> {

	/**
	 * Constructor
	 *
	 * @param aPersistentClass
	 *            tipo de la clase a persistir
	 */
	public IbatisGenericReadOnlyDaoImpl(final Class<Entity> aPersistentClass) {
		super();
		this.persistentClass = aPersistentClass;
	}

	/**
	 *
	 * @param aPersistentClass
	 *            tipo de la clase a persistir
	 * @param aSqlMapClient
	 */
	public IbatisGenericReadOnlyDaoImpl(final Class<Entity> aPersistentClass,
			SqlMapClient aSqlMapClient) {
		super(aSqlMapClient);
		this.persistentClass = aPersistentClass;
	}

	public Class<Entity> getPersistentClass() {
		return persistentClass;
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
	@SuppressWarnings("unchecked")
	public boolean exists(Id anId) {
		Entity object = (Entity) getSqlMapClientTemplate().queryForObject(
				getFindQuery(getPersistentClass()), anId);
		return object != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Entity get(Id anId) {
		Entity object = (Entity) getSqlMapClientTemplate().queryForObject(
				getFindQuery(getPersistentClass()), anId);
		if (object == null) {
			logger
					.warn(
							"El objeto de tipo '{}' con identificador '{}' no se ha encontrado...",
							getPersistentClass(), anId);
			throw new ObjectRetrievalFailureException(ClassUtils
					.getShortName(getPersistentClass()), anId);
		}
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getAll() {
		return getSqlMapClientTemplate().queryForList(
				getSelectQuery(getPersistentClass()), null);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Entity> getAllDistinct() {
		Collection<Entity> result = new LinkedHashSet<Entity>(getAll());
		return new ArrayList<Entity>(result);
	}

	/**
	 *
	 * @return
	 */
	protected String getFindQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getFindQuery(ClassUtils
				.getShortName(aPersistentClass));
	}

	/**
	 *
	 * @return
	 */
	protected String getSelectQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getSelectQuery(ClassUtils
				.getShortName(aPersistentClass));
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(IbatisGenericReadOnlyDaoImpl.class);
	private Class<Entity> persistentClass;

}
