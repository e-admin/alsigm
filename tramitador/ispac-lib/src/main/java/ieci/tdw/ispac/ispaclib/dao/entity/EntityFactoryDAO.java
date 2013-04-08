/*
 * Created on 28-jun-2004
 *
 */
package ieci.tdw.ispac.ispaclib.dao.entity;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.TableDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.Date;

/**
 * @author juanin
 *
 */
public class EntityFactoryDAO
{
	private static EntityFactoryDAO _instance;

	//TODO: Implementar cache de entidades de catálogo
	//private CTCatalogBuilder cacheCTEntityDAOs;

	private EntityFactoryDAO()
	{
		super();
		
		//cacheCTEntityDAOs = new CTCatalogBuilder();
	}

	public static synchronized EntityFactoryDAO getInstance()
	{
		if (_instance == null)
			_instance =  new EntityFactoryDAO();

		return _instance;
	}

	public EntityDAO newEntityDAO(DbCnt cnt, int entityid)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityid);
		return newEntityDAO(cnt,entity);
	}

	public EntityDAO newEntityDAO(DbCnt cnt, String entityname)
	throws ISPACException
	{
		CTEntityDAO ctentity = getCatalogEntityDAO(cnt, entityname);
		return newEntityDAO(cnt, ctentity);
	}
	
	public EntityDAO newEntityDAO(DbCnt cnt, IEntityDef entitydef)
	throws ISPACException
	{
		// Se pasa el timestamp de la entidad para validar
		// contra la definición de la tabla cacheada
		return new EntityDAO(cnt,
							 entitydef.getId(),
							 entitydef.getType(),
							 entitydef.getName(),
		        			 entitydef.getKeyField(),
		        			 entitydef.getKeyNumExp(),
		        			 entitydef.getSequence(),
		        			 entitydef.getTimestamp());
	}
	
	public CTEntityDAO getCatalogEntityDAO(DbCnt cnt, int entityid)
	throws ISPACException
	{
		// TODO: Implementar cache de entidades de catálogo
		return new CTEntityDAO(cnt, entityid);
	}

	public CTEntityDAO getCatalogEntityDAO(DbCnt cnt, String entityname)
	throws ISPACException
	{
		// TODO: Implementar cache de entidades de catálogo
		return (CTEntityDAO) ObjectDAO.getByName(cnt, CTEntityDAO.class, entityname);
	}

	public CollectionDAO newCollectionDAO(DbCnt cnt, int entityid)
	throws ISPACException
	{
		CTEntityDAO ctentity = getCatalogEntityDAO(cnt, entityid);
		return newCollectionDAO(ctentity);
	}

	public CollectionDAO newCollectionDAO(DbCnt cnt, String entityname)
	throws ISPACException
	{
		CTEntityDAO ctentity = getCatalogEntityDAO(cnt, entityname);
		return newCollectionDAO(ctentity);
	}

	public CollectionDAO newCollectionDAO(IEntityDef entitydef)
	throws ISPACException
	{
		Object[] args = null;
		
		if (entitydef.getTimestamp() != null) {
			args = new Object[7];
			args[6] = entitydef.getTimestamp();
		}
		else {
			args = new Object[6];
		}
		
		// Definición de la entidad de catálogo a cargar
		args[0] = new Integer(entitydef.getId());
		args[1] = new Integer(entitydef.getType());
		args[2] = entitydef.getName();
		args[3] = entitydef.getKeyField();
		
		// El campo que indica cual es el campo NumExp en la entidad
		// puede ser nulo
		if (entitydef.getKeyNumExp() != null) {
			args[4] = entitydef.getKeyNumExp();
		}
		else {
			args[4] = "";
		}
		
		args[5] = entitydef.getSequence();
		
		return new CollectionDAO(EntityDAO.class,args);
	}

	public CollectionDAO newCollectionDAO(IEntityDef entitydef, Class daoclass, Property[] columns)
	throws ISPACException
	{
		Object[] args = null;
		
		if (entitydef.getTimestamp() != null) {
			args = new Object[5];
			args[3] = entitydef.getTimestamp();
			args[4]=columns;
		}
		else {
			args = new Object[4];
			args[3]=columns;
		}
		
		args[0] = entitydef.getName();
		args[1] = entitydef.getKeyField();
		args[2] = entitydef.getSequence();

		return new CollectionDAO(daoclass, args);
	}

	public CollectionDAO newCollectionDAO(String tablename, String keyproperty, String sequence, Date timestamp)
	throws ISPACException
	{
		Object[] args = null;
		
		if (timestamp != null) {
			args = new Object[4];
			args[3] = timestamp;
		}
		else {
			args = new Object[3];
		}
		
		// Definición de la entidad de catálogo a cargar
		args[0] = tablename;
		args[1] = keyproperty;
		args[2] = sequence;

		return new CollectionDAO(EntityDAO.class, args);
	}
	
    public IItem getEntity(DbCnt cnt, IClientContext context, int entityId, int entityRegId) throws ISPACException {
    	
        EntityDAO entityDAO = newEntityDAO(cnt, entityId);
        entityDAO.setKey(entityRegId);
        
        // Ejecutar las reglas asociadas al cargar (ver -> EVENT_ENTITY_VIEW) la entidad
        entityDAO.load(context);
        
        return entityDAO;
    }

    public IItem getEntity(DbCnt cnt, IClientContext context, String entityName, int entityRegId) throws ISPACException {
    	
        EntityDAO entityDAO = newEntityDAO(cnt, entityName);
        entityDAO.setKey(entityRegId);
        
        // Ejecutar las reglas asociadas al cargar (ver -> EVENT_ENTITY_VIEW) la entidad
        entityDAO.load(context);
        
        return entityDAO;
    }    
    
    public IItem getEntity(DbCnt cnt, IClientContext context, IEntityDef entitydef, int entityRegId) throws ISPACException {
    	
        EntityDAO entityDAO = newEntityDAO(cnt, entitydef);
        entityDAO.setKey(entityRegId);
        
        // Ejecutar las reglas asociadas al cargar (ver -> EVENT_ENTITY_VIEW) la entidad
        entityDAO.load(context);
        
        return entityDAO;
    }
    
    public IItem getEntity(DbCnt cnt, int entityId, String numexp) throws ISPACException {
    	
        ObjectDAO entityDAO = null;
        
        CollectionDAO entities = getEntities(cnt, entityId, numexp, null);
        if (entities.next()) {
        	entityDAO = entities.value();
        }
        
        return entityDAO;
    }

    private CollectionDAO getEntities(DbCnt cnt, 
									  String tablename, 
									  String keyproperty,
									  String sequence, 
									  Date timestamp, 
									  String sqlQuery) throws ISPACException
	{
		CollectionDAO collection = newCollectionDAO(tablename, keyproperty, sequence, timestamp);
		collection.query(cnt, sqlQuery);
		return collection;
	}

	public CollectionDAO getEntities(DbCnt cnt,IEntityDef entitydef,String numexp,String sqlQuery, String order, String sort)
	throws ISPACException
	{
		String query = "WHERE " + entitydef.getKeyNumExp() + " = '" + DBUtil.replaceQuotes(numexp) + "'";
		
		if (StringUtils.isNotEmpty(sqlQuery)) {
			query = query + " AND (" + sqlQuery + ") ";
		}
		
		if (StringUtils.isNotEmpty(order)){
			query = query + " ORDER BY " + order;
			if (StringUtils.isNotEmpty(sort)){
				query = query + " " + sort;
			}
		}

		return getEntities(cnt, entitydef, query);
	}		
	
	public CollectionDAO getEntities(DbCnt cnt,IEntityDef entitydef,String numexp,String sqlQuery)
	throws ISPACException
	{
		String query = "WHERE " + entitydef.getKeyNumExp() + " = '" + DBUtil.replaceQuotes(numexp) + "'";

		if (!StringUtils.isEmpty(sqlQuery)) {
			query = query + " AND (" + sqlQuery + ")";
		}

		return getEntities(cnt, entitydef, query);
	}
	
	public CollectionDAO getEntitiesWithOrder(DbCnt cnt, IEntityDef entitydef, String numexp, String sqlQuery, String order)
	throws ISPACException
	{
		String query = "WHERE " + entitydef.getKeyNumExp() + " = '" + DBUtil.replaceQuotes(numexp) + "'";

		if (!StringUtils.isEmpty(sqlQuery)) {
			query = query + " AND (" + sqlQuery + ")";
		}
		
		if (!StringUtils.isEmpty(order)) {
			query = query + " ORDER BY " + order;
		}
		
		return getEntities(cnt, entitydef, query);
	}
	
	public CollectionDAO getEntities(DbCnt cnt,IEntityDef entitydef,String sqlQuery)
	throws ISPACException
	{
		CollectionDAO collection = newCollectionDAO(entitydef);
		collection.query(cnt, sqlQuery);
		return collection;
	}
	
	public CollectionDAO getEntitiesForUpdate(DbCnt cnt,IEntityDef entitydef,String sqlQuery)
	throws ISPACException
	{
		CollectionDAO collection = newCollectionDAO(entitydef);
		collection.queryForUpdate(cnt, sqlQuery);
		return collection;
	}
	
	public CollectionDAO getEntities(DbCnt cnt,IEntityDef entitydef,String sqlQuery, String order, int limit)
	throws ISPACException
	{
		CollectionDAO collection = newCollectionDAO(entitydef);
		collection.query(cnt, sqlQuery, order, limit);
		return collection;
	}

	public int countEntities(DbCnt cnt,IEntityDef entitydef,String sqlQuery)
	throws ISPACException
	{
		CollectionDAO collection = newCollectionDAO(entitydef);
		return collection.count(cnt, sqlQuery);
	}

	public CollectionDAO getEntities(DbCnt cnt,String entityname,String numexp,String sqlQuery)
	throws ISPACException
	{
		CTEntityDAO entity = (CTEntityDAO) ObjectDAO.getByName(cnt, CTEntityDAO.class, entityname);
		return getEntities(cnt,entity,numexp,sqlQuery);
	}

	/**
	 * Obtiene la colección de registros de una entidad
	 * de un expediente
	 */
	public CollectionDAO getEntities(DbCnt cnt, int entityId, String numexp, String sqlQuery)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		return getEntities(cnt, entity, numexp, sqlQuery);

	}

	/**
	 * Obtiene la colección ordenada de registros de una entidad
	 * de un expediente
	 */
	public CollectionDAO getEntitiesWithOrder(DbCnt cnt, int entityId, String numexp, String sqlQuery, String order)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		return getEntitiesWithOrder(cnt, entity, numexp, sqlQuery, order);

	}
	
	/**
	 * Obtiene la colección de registros de una entidad
	 */
	public CollectionDAO queryEntities(DbCnt cnt, int entityId, String query)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		return getEntities(cnt,entity,query);
	}
	
	public int queryCountEntities(DbCnt cnt, String entityName, String query)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityName);
		return countEntities(cnt, entity, query);
	}
	public CollectionDAO queryEntities(DbCnt cnt, String entityName, String query, String order, int limit)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityName);
		return getEntities(cnt,entity,query,order,limit);
	}

	public CollectionDAO queryEntities(DbCnt cnt, String entityName, String query)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityName);
		return getEntities(cnt,entity,query);
	}

	public CollectionDAO queryEntities(DbCnt cnt, IEntityDef entitydef, String query)
	throws ISPACException
	{
		return getEntities(cnt,entitydef,query);
	}
	
	public CollectionDAO queryEntitiesForUpdate(DbCnt cnt,IEntityDef entitydef,String sqlQuery)
	throws ISPACException
	{
		return getEntitiesForUpdate(cnt, entitydef, sqlQuery);
	}
	
	public CollectionDAO queryEntities(DbCnt cnt,
									   String tablename,
									   String keyproperty,
									   String sequence,
									   Date timestamp,
									   String query)  throws ISPACException
	{
		return getEntities(cnt, tablename, keyproperty, sequence, timestamp, query);
	}

	public int countEntities(DbCnt cnt,int entityId,String query)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		return countEntities(cnt,entity,query);
	}
	
	/**
	 * Devuelve el número de registros que satisfacen la búsqueda
	 * @param cnt
	 * @param entityname
	 * @param query
	 * @return
	 * @throws ISPACException
	 */
	public int countEntities(DbCnt cnt,String entityname,String query)
	throws ISPACException
	{
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityname);
		return countEntities(cnt,entity,query);
	}

	/**
	 * Obtiene una colección con la descripción de los registros
	 * de una entidad de un expediente.
	 * Obtiene el campo ID de la entidad y el esquema.
	 */
	public CollectionDAO getSchemeEntities(DbCnt cnt,int entityId,String expedient,String query)
	throws ISPACException
	{
	    return getSchemeEntities(cnt, entityId, expedient, query,null);
	}
	
	public CollectionDAO getSchemeEntities(DbCnt cnt,IEntityDef entitydef,String expedient,String query)
	throws ISPACException
	{
	    return getSchemeEntities(cnt, entitydef, expedient, query, null);
	}

	/*
	public CollectionDAO getSchemeEntities(DbCnt cnt,int entityId,String expedient,String query)
	throws ISPACException
	{
		String sqlQuery;
		CollectionDAO collection;
		Property[] columns = new Property[2];

		// Obtiene la descripción de la entidad en el catálogo
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		// Campo que identifica a el registro en la entidad
		columns[0] = new Property(1,"SCHEME_ID",entity.getKeyField(),Types.INTEGER);
		// Expresión que describe a el registro en la entidad
		columns[1] = new Property(2,"SCHEME_EXPR",entity.getSchemaExpr(),Types.VARCHAR);

		collection = newCollectionDAO(entity,TableDAO.class,columns);
		// Obtiene sólo los registros del expediente en la entidad
		sqlQuery = "WHERE "
			     + entity.getKeyNumExp()
				 + " = '"
				 + DBUtil.replaceQuotes(expedient)
				 + "'";

		if (query.length() != 0)
		{
			sqlQuery += " AND (" + query + ")";
		}
		collection.query( cnt, sqlQuery);
		return collection;
	}
	*/

	/**
	 * Obtiene una colección con la descripción de los registros
	 * de la entidad de documento de un expediente.
	 * Obtiene el campo ID de la entidad, el esquema, el número de expediente
	 * y el estado del documento.
	 */
	public CollectionDAO getSchemeDocumentEntity(DbCnt cnt,int entityId,String expedient,String query)
	throws ISPACException
	{
	    Property[] columns = new Property[1];
	    columns[0] = new Property(1,"ESTADO",Types.VARCHAR);
	    return getSchemeEntities(cnt,entityId,expedient,query,columns);
	}

	/*
	public CollectionDAO getSchemeDocumentEntity(DbCnt cnt,int entityId,String expedient,String query)
	throws ISPACException
	{
		String sqlQuery;
		CollectionDAO collection;
		Property[] columns = new Property[4];

		// Obtiene la descripción de la entidad en el catálogo
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		// Campo que identifica a el registro en la entidad
		columns[0] = new Property(1,"SCHEME_ID",entity.getKeyField(),Types.INTEGER);
		// Expresión que describe a el registro en la entidad
		columns[1] = new Property(2,"SCHEME_EXPR",entity.getSchemaExpr(),Types.VARCHAR);
		// Campo que identifica a el número de expediente
		columns[2] = new Property(1,"SCHEME_NUMEXP",entity.getKeyNumExp(),Types.VARCHAR);
		// Campo estado del documento
		columns[3] = new Property(3,"ESTADO",Types.VARCHAR);

		collection = newCollectionDAO(entity,TableDAO.class,columns);
		// Obtiene sólo los registros del expediente en la entidad
		sqlQuery = "WHERE "
			     + entity.getKeyNumExp()
				 + " = '"
				 + DBUtil.replaceQuotes(expedient)
				 + "'";

		if (query.length() != 0)
		{
			sqlQuery += " AND (" + query + ")";
		}
		collection.query( cnt, sqlQuery);
		return collection;
	}
	*/

	/**
	 * Obtiene una colección con la descripción de los registros
	 * de la entidad de documento de un expediente.
	 * Obtiene el campo ID de la entidad y el esquema.
	 */
	public CollectionDAO getSchemeEntities(DbCnt cnt,int entityId,String expedient,String query,Property[] extracolumns)
	throws ISPACException
	{
	    // Definición de la entidad en el catálogo
		CTEntityDAO entity = getCatalogEntityDAO(cnt, entityId);
		return getSchemeEntities(cnt,entity,expedient,query,extracolumns);
	}

	public CollectionDAO getSchemeEntities(DbCnt cnt,IEntityDef entitydef,String expedient,String query,Property[] extracolumns , String orderBy , boolean desc)
	throws ISPACException
	{
		final int DEFAULT_NUMPROP=2;

		String sqlQuery;
		CollectionDAO collection;

		//Por defecto sólo dos propiedades/columnas
		int numprop=DEFAULT_NUMPROP;
		if (extracolumns!=null)
			numprop+=extracolumns.length;

		Property[] columns = new Property[numprop];

		// Campo que identifica al registro en la entidad
		columns[0] = new Property(1,"SCHEME_ID",entitydef.getKeyField(),Types.INTEGER);
		// Expresión que describe al registro en la entidad
		columns[1] = new Property(2,"SCHEME_EXPR",entitydef.getSchemaExpr(),Types.VARCHAR);

		// Campos extra suministrados.
		for (int i=2;i<numprop;i++)
		{
		    columns[i]=extracolumns[i-DEFAULT_NUMPROP];
		}

		collection = newCollectionDAO(entitydef,TableDAO.class,columns);
		// Obtiene sólo los registros del expediente en la entidad
		sqlQuery = "WHERE "
			     + entitydef.getKeyNumExp()
				 + " = '"
				 + DBUtil.replaceQuotes(expedient)
				 + "'";

		if (query!=null && query.length() != 0)
		{
			sqlQuery += " AND (" + query + ")";
		}
		
		sqlQuery += " ORDER BY " ;
		if(StringUtils.isBlank(orderBy)){
			sqlQuery+=entitydef.getKeyField();
		}
		else {
			sqlQuery+=orderBy;
		}
		if(desc){
			sqlQuery+=" DESC ";
		}
		
		collection.query( cnt, sqlQuery);
		return collection;
		
	}
	public CollectionDAO getSchemeEntities(DbCnt cnt,IEntityDef entitydef,String expedient,String query,Property[] extracolumns)
	throws ISPACException
	{
		return getSchemeEntities(cnt, entitydef, expedient, query, extracolumns, null,false);
	}
	
	public int deleteEntities(DbCnt cnt,IEntityDef entitydef,String sqlQuery) throws ISPACException{
		CollectionDAO collection = newCollectionDAO(entitydef);
		return collection.delete(cnt, sqlQuery);
	}

}