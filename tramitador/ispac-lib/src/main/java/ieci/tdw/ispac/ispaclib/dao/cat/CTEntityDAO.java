package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityResourceDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Date;

public class CTEntityDAO extends ObjectDAO implements IEntityDef
{

	static final String TABLENAME 	= "SPAC_CT_ENTIDADES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTENTIDADES";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public CTEntityDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTEntityDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTEntityDAO(DbCnt cnt, Integer id) throws ISPACException {
		super(cnt, id, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
    public CTEntityDAO(DbCnt cnt, int id) throws ISPACException {
    	super(cnt, id, null);
    }
    
	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(int)
	 */
	protected void newObject(DbCnt cnt)
		throws ISPACException
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

	/**
	 * Obtiene la colección de entidades del catálogo relacionadas
	 * con un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param procedureId identificador del procedimiento
	 * @return una colección de objetos CTEntityDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getProcedureEntities(DbCnt cnt,int procedureId)
	throws ISPACException
	{
		String sql = "WHERE ID IN (SELECT ID_ENT FROM SPAC_P_ENTIDADES WHERE ID_PCD = " + procedureId + ")";

		CollectionDAO collection = new CollectionDAO(CTEntityDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	/**
	 * Obtiene la aplicación por defecto asociada a la entidad.
	 * @param cnt manejador de la conexión
	 * @return el objeto aplicación CTApplicationDAO
	 * @throws ISPACException
	 */
	public CTApplicationDAO getApplication( DbCnt cnt)
	throws ISPACException
	{
		CTApplicationDAO ctApplication = null;

		int application = getInt( "FRM_EDIT");

		ctApplication = new CTApplicationDAO(cnt,application);
		ctApplication.load(cnt);

		return ctApplication;
	}
	
	/**
	 * Obtiene los recursos asociados a la entidad.
	 * @param cnt manejador de la conexión
	 * @return colección de recursos
	 * @throws ISPACException
	 */
	public CollectionDAO getResources(DbCnt cnt) throws ISPACException
	{
		String sql = " WHERE ID_ENT = " + getKeyInt();

		CollectionDAO collection = new CollectionDAO(EntityResourceDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	///////////////////////////////////////////
	// Implementación de IEntityDef

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getId()
     */
    public int getId() throws ISPACException
    {
        return getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getName()
     */
    public String getName() throws ISPACException
    {
        return getString("NOMBRE");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getKeyField()
     */
    public String getKeyField() throws ISPACException
    {
        return getString("CAMPO_PK");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getKeyNumExp()
     */
    public String getKeyNumExp() throws ISPACException
    {
        return getString("CAMPO_NUMEXP");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getSequence()
     */
    public String getSequence() throws ISPACException
    {
        return getString("SEC_PK");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getType()
     */
    public int getType() throws ISPACException
    {

        return getInt("TIPO");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getSchemaExpr()
     */
    public String getSchemaExpr() throws ISPACException
    {
        return getString("SCHEMA_EXPR");
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getAppId()
     */
    public int getAppId() throws ISPACException
    {
        return getInt("FRM_EDIT");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getAppIdString()
     */
    public String getAppIdString() throws ISPACException
    {
        return getString("FRM_EDIT");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getDescription()
     */
    public String getDescription() throws ISPACException
    {
        return getString("DESCRIPCION");
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getDefinition()
     */
    public String getDefinition() throws ISPACException
    {
        return getString("DEFINICION");
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getTimestamp()
     */
    public Date getTimestamp() throws ISPACException
    {
        return getDate("FECHA");
    }
    
}
