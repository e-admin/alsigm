package ieci.tdw.ispac.ispaclib.dao.entity;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class EntityResourceDAO extends ObjectDAO
{

	static final String TABLENAME 	= "SPAC_CT_ENTIDADES_RESOURCES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_ENTIDADESRESOURCES";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public EntityResourceDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public EntityResourceDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public EntityResourceDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}
	
	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(int)
	 */
	protected void newObject(DbCnt cnt) throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}
	
	public static void deleteFieldResources(DbCnt cnt, 
											int entityId,
											String key) throws ISPACException {
		
		String sql = "WHERE ID_ENT = " + entityId 
				   + " AND CLAVE = '" + DBUtil.replaceQuotes(key.toUpperCase()) + "'";
		
		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
		collection.query(cnt,sql);
		while (collection.next()) {
			
			EntityResourceDAO entityResource = (EntityResourceDAO) collection.value();
			entityResource.delete(cnt);
			
		}
	}
	
	public static void deleteResources(DbCnt cnt, 
									   int entityId) throws ISPACException {

		String sql = "WHERE ID_ENT = " + entityId + " ";
		
		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
		collection.query(cnt,sql);
		while (collection.next()) {
		
		EntityResourceDAO entityResource = (EntityResourceDAO) collection.value();
		entityResource.delete(cnt);

		}
	}
	
	public static CollectionDAO getEntityResources(DbCnt cnt, 
											  	   int entityId,
											  	   String keys,
											  	   String language) throws ISPACException {
		
		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);

		String sql = "WHERE ID_ENT = " + entityId
				   + " AND IDIOMA = '" + language 
				   + "' AND CLAVE IN (" + keys.toUpperCase() + ")";
		
		collection.query(cnt,sql);
		
		return collection;
	}
	
	public static CollectionDAO getEntityOtherResources(DbCnt cnt, 
												   		int entityId,
												   		String fieldKeys,
												   		String language) throws ISPACException {
		
		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
				
		String sql = "WHERE ID_ENT = " + entityId 
				   + " AND IDIOMA = '" + language 
				   + "' AND CLAVE NOT IN (" + fieldKeys.toUpperCase() + ")";
		
		collection.queryDistinct(cnt, sql, new String[] {"CLAVE", "VALOR"});
				
		return collection;
	
	}
	
	public static CollectionDAO getEntityOtherResourceKeys(DbCnt cnt, 
	   		   											   int entityId,
	   		   											   String fieldKeys) throws ISPACException {

		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
		
		String sql = "WHERE ID_ENT = " + entityId
				   + " AND CLAVE NOT IN (" + fieldKeys.trim().toUpperCase() + ")";

		collection.queryDistinct(cnt, sql, new String[] {"CLAVE"});

		return collection;
	}
	
	public static EntityResourceDAO getEntityResource(DbCnt cnt, 
													  int entityId, 
													  String key, 
													  String language) throws ISPACException {
		
		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
		
		String sql = "WHERE ID_ENT = " + entityId 
				   + " AND CLAVE = '" + DBUtil.replaceQuotes(key.toUpperCase()) 
				   + "' AND IDIOMA = '" + language + "'";
		
		collection.query(cnt,sql);

		if (collection.next())
			return (EntityResourceDAO) collection.value();

		return null;
	}
	
	public static boolean isEntityResourceKey(DbCnt cnt, 
			  								  int entityId, 
			  								  String key) throws ISPACException {

		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);

		String sql = "WHERE ID_ENT = " + entityId 
				   + " AND CLAVE = '" + DBUtil.replaceQuotes(key.toUpperCase()) + "'";

		int count = collection.count(cnt,sql);

		if (count > 0)
			return true;

		return false;
	}
	
}