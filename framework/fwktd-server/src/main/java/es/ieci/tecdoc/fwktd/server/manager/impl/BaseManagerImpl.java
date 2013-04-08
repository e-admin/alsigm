/**
 *
 */
package es.ieci.tecdoc.fwktd.server.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.server.util.EntityUtils;

/**
 * Implementación de un manager genérico para la gestión de entidades tipo CRUD.
 * Si se desea ampliar la lógica basta con extender la clase e implementar un
 * interfaz propio. Esta implementación lleva asociado un
 * <code>GenericDao</code> que será el encargado del acceso a la capa de datos
 * para las entidades de tipo <code>Entity</code>.
 * 
 * @author IECISA
 * 
 * @param <Entity>
 *            tipo de la entidad a gestionar
 * @param <Id>
 *            tipo de la clave primaria de la entidad a gestionar
 * 
 * @since 1.0
 */
public class BaseManagerImpl<Entity, Id extends Serializable> extends
		BaseReadOnlyManagerImpl<Entity, Id> implements BaseManager<Entity, Id> {

	public DataFieldMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	/**
	 * Constructor de la clase. Toma como argumento el Dao encargado de
	 * gestionar las entidades del tipo indicado para el presente manager.
	 * 
	 * @param aGenericDao
	 */
	public BaseManagerImpl(BaseDao<Entity, Id> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Id anId) {
		Assert.isInstanceOf(BaseDao.class, this.dao);
		((BaseDao<Entity, Id>) getDao()).delete(anId);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void deleteAll(List<? extends Entity> entities) {
		Assert.notNull(entities);
		for (Entity entity : entities) {
			Object primaryKeyValue = EntityUtils.getPrimaryKeyValue(entity);
			delete((Id) primaryKeyValue);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public Entity save(Entity anEntity) {
		Object primaryKey = EntityUtils.getPrimaryKeyValue(anEntity);
		if (null == primaryKey || StringUtils.EMPTY.equals(primaryKey)) {
			Assert
					.notNull(incrementer,
							"No se puede crear VO. VO sin Id y Manager no tiene incrementer inyectado");
			Class<?> primaryKeyClass = EntityUtils
					.getPrimaryKeyFieldType(anEntity);
			EntityUtils.setPrimaryKey(anEntity, primaryKeyClass, incrementer
					.nextStringValue());
		}

		Assert.isInstanceOf(BaseDao.class, this.dao);
		return ((BaseDao<Entity, Id>) getDao()).save(anEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entity update(Entity anEntity) {
		Assert.isInstanceOf(BaseDao.class, this.dao);
		return ((BaseDao<Entity, Id>) getDao()).update(anEntity);
	}

	// Members
	protected static Logger logger = LoggerFactory
			.getLogger(BaseManagerImpl.class);

	/**
	 * Servicio de incrementers
	 */
	protected DataFieldMaxValueIncrementer incrementer;

}