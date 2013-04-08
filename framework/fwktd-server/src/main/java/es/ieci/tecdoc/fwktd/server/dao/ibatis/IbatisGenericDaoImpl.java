/*
 *
 */
package es.ieci.tecdoc.fwktd.server.dao.ibatis;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.util.ClassUtils;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.util.EntityUtils;

/**
 *
 * @author IECISA
 *
 * @param <Entity>
 * @param <Id>
 */
public class IbatisGenericDaoImpl<Entity, Id extends Serializable> extends
		IbatisGenericReadOnlyDaoImpl<Entity, Id> implements BaseDao<Entity, Id> {

	/**
	 * Constructor
	 *
	 * @param aPersistentClass
	 *            tipo de la clase a persistir
	 */
	public IbatisGenericDaoImpl(final Class<Entity> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 *
	 * @param aPersistentClass
	 *            tipo de la clase a persistir
	 * @param aSqlMapClient
	 */
	public IbatisGenericDaoImpl(final Class<Entity> aPersistentClass,
			SqlMapClient aSqlMapClient) {
		super(aPersistentClass,aSqlMapClient);
	}


	/**
	 * {@inheritDoc}
	 */
	public void delete(Id anId) {
		getSqlMapClientTemplate().delete(getDeleteQuery(getPersistentClass()),
				anId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity save(Entity anEntity) {

		getSqlMapClientTemplate().insert(getInsertQuery(getPersistentClass()),
				anEntity);

		// check for null id
		if (EntityUtils.getPrimaryKeyValue(anEntity) == null) {
			throw new ObjectRetrievalFailureException(ClassUtils
					.getShortName(anEntity.getClass()), anEntity);
		} else {
			return anEntity;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity update(Entity anEntity) {

		getSqlMapClientTemplate().update(getUpdateQuery(getPersistentClass()),
				anEntity);

		// check for null id
		if (EntityUtils.getPrimaryKeyValue(anEntity) == null) {
			throw new ObjectRetrievalFailureException(ClassUtils
					.getShortName(anEntity.getClass()), anEntity);
		} else {
			return anEntity;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() {
		String query = getDeleteAllQuery(getPersistentClass());

		getSqlMapClientTemplate().delete(query);
	}

	/**
	 *
	 * @return
	 */
	protected String getInsertQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getInsertQuery(ClassUtils
				.getShortName(aPersistentClass));
	}

	/**
	 *
	 * @return
	 */
	protected String getUpdateQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getUpdateQuery(ClassUtils
				.getShortName(aPersistentClass));
	}

	/**
	 *
	 * @param aPersistentClass
	 * @return
	 */
	protected String getDeleteQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getDeleteQuery(ClassUtils
				.getShortName(aPersistentClass));
	}

	/**
	 *
	 * @param aPersistentClass
	 * @return
	 */
	protected String getDeleteAllQuery(Class<Entity> aPersistentClass) {
		return IbatisDaoUtils.getDeleteQuery(ClassUtils
				.getShortName(aPersistentClass))
				+ "s";
	}

	// Members
	protected static final Logger logger = LoggerFactory
			.getLogger(IbatisGenericDaoImpl.class);

}
