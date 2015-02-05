package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.app.EntityAppFactory;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.IApplicationDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.builders.JSPBuilder;
import ieci.tdw.ispac.ispaclib.builders.JSPBuilderConfiguration;
import ieci.tdw.ispac.ispaclib.catalog.CTObjectCatalog;
import ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHierarchyDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.FrmBusquedaReportDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.HierarchyValueDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityResourceDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PCtoFirmaDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbColDef;
import ieci.tdw.ispac.ispaclib.db.DbIndexDefinition;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.EntityTableManager;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * CatalogAPI
 *
 */
public class CatalogAPI implements ICatalogAPI
{
	protected Logger logger = Logger.getLogger(CatalogAPI.class);

//    private EntityField getIdFieldInstace(int id){
//        EntityField ID_FIELD = new EntityField();
//        ID_FIELD.setId(id);
//        //ID_FIELD.setLogicalName(ID_FIELD_NAME);
//        ID_FIELD.setPhysicalName(ID_FIELD_NAME);
//        ID_FIELD.setDescripcion("Campos Clave de la entidad (Uso interno del sistema)");
//        //ID_FIELD.setSize(10);
//        ID_FIELD.setType(InternalDataType.LONG_INTEGER);
//        ID_FIELD.setNullable(false);
//        return ID_FIELD;
//    }

//    private EntityField getNumExpFieldInstace(int id){
//        EntityField NUMEXP_FIELD = new EntityField();
//        NUMEXP_FIELD = new EntityField();
//        NUMEXP_FIELD.setId(id);
//        //NUMEXP_FIELD.setLogicalName(NUMEXP_FIELD_NAME);
//        NUMEXP_FIELD.setPhysicalName(NUMEXP_FIELD_NAME);
//        NUMEXP_FIELD.setSize(30);
//        NUMEXP_FIELD.setDescripcion("Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)");
//        NUMEXP_FIELD.setType(InternalDataType.SHORT_TEXT);
//        NUMEXP_FIELD.setNullable(false);
//        return NUMEXP_FIELD;
//    }

    private EntityField getEstadoFieldInstace(int id){
        EntityField ESTADO_FIELD = new EntityField();
        ESTADO_FIELD = new EntityField();
        ESTADO_FIELD.setId(id);
        //ESTADO_FIELD.setLogicalName(ESTADO_FIELD_NAME);
        ESTADO_FIELD.setPhysicalName(ESTADO_FIELD_NAME);
        ESTADO_FIELD.setSize(1);
        ESTADO_FIELD.setType(InternalDataType.SHORT_INTEGER);
        ESTADO_FIELD.setDescripcion("Indica si el valor está en vigencia para ser seleccionado");
        return ESTADO_FIELD;
    }

    private EntityField getOrdenFieldInstance(int id)
    {
    	  EntityField ESTADO_FIELD = new EntityField();
          ESTADO_FIELD = new EntityField();
          ESTADO_FIELD.setId(id);
          ESTADO_FIELD.setPhysicalName(ORDEN_FIELD_NAME);
          ESTADO_FIELD.setSize(10);
          ESTADO_FIELD.setType(InternalDataType.SHORT_INTEGER);
          ESTADO_FIELD.setDescripcion("Indica el orden del valor");
          return ESTADO_FIELD;
    }

    private EntityField getValorFieldInstace(int id, int size){
        EntityField VALOR_FIELD = new EntityField();
        VALOR_FIELD = new EntityField();
        VALOR_FIELD.setId(id);
        //VALOR_FIELD.setLogicalName(VALOR_FIELD_NAME);
        VALOR_FIELD.setPhysicalName(VALOR_FIELD_NAME);
        VALOR_FIELD.setSize(size >= 0 ? size : 10);
        VALOR_FIELD.setType(InternalDataType.SHORT_TEXT);
        VALOR_FIELD.setDescripcion("Valor que se almacena en el campo validado");
        return VALOR_FIELD;
    }

    private EntityField getCodigoFieldInstace(int id){
        EntityField CODIGO_FIELD = new EntityField();
        CODIGO_FIELD = new EntityField();
        CODIGO_FIELD.setId(id);
        //CODIGO_FIELD.setLogicalName(CODIGO_FIELD_NAME);
        CODIGO_FIELD.setPhysicalName(CODIGO_FIELD_NAME);
        CODIGO_FIELD.setDescripcion("Identificador inequívoco del valor");
        //CODIGO_FIELD.setSize(10);
        CODIGO_FIELD.setType(InternalDataType.LONG_INTEGER);
        CODIGO_FIELD.setNullable(false);
        return CODIGO_FIELD;
    }

    private EntityField getSustitutoFieldInstace(int id, int size){
        EntityField SUSTITUTO_FIELD = new EntityField();
        SUSTITUTO_FIELD = new EntityField();
        SUSTITUTO_FIELD.setId(id);
        //SUSTITUTO_FIELD.setLogicalName(SUSTITUTO_FIELD_NAME);
        SUSTITUTO_FIELD.setPhysicalName(SUSTITUTO_FIELD_NAME);
        SUSTITUTO_FIELD.setSize(size >= 0 ? size : 64);
        SUSTITUTO_FIELD.setType(InternalDataType.SHORT_TEXT);
        SUSTITUTO_FIELD.setDescripcion("Texto que se muestra en la lista de selección");
        return SUSTITUTO_FIELD;
    }

    private ClientContext mcontext;

	public CatalogAPI(ClientContext context)
	{
		mcontext = context;
	}

	protected EntityDAO createEntity(DbCnt cnt,int ctentityId)
	throws ISPACException
	{
	    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		return EntityFactoryDAO.getInstance().newEntityDAO(cnt,entitydef);
	}

	protected EntityDAO createEntity(DbCnt cnt, String ctentityName)
	throws ISPACException
	{
	    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityName);
		return EntityFactoryDAO.getInstance().newEntityDAO(cnt,entitydef);
	}

	protected EntityDAO getEntity(DbCnt cnt, int ctentityId, int entityRegId)
	throws ISPACException
	{
		IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt,entitydef);

		//Si no hay id de registro de la entidad, ésta se devuelve vacía
		if (entityRegId==ISPACEntities.ENTITY_NULLREGKEYID)
		    return entityDAO;

		entityDAO.setKey(entityRegId);
		entityDAO.load(cnt);
		return entityDAO;
	}


	protected IItemCollection getEntityByIds(DbCnt cnt, int ctentityId, Map entityRegIds)
	throws ISPACException
	{
		if (ctentityId == ENTITY_CT_APP) {
			return ObjectDAO.getByIds(cnt, CTApplicationDAO.class, entityRegIds, null).disconnect();
		} else if (ctentityId == ENTITY_CT_RULE) {
			return ObjectDAO.getByIds(cnt, CTRuleDAO.class, entityRegIds, null).disconnect();
		}

		return null;
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getCTEntity(int)
     */
    public IItem getCTEntity(int ctentityId)
    throws ISPACException
    {
		DbCnt cnt = mcontext.getConnection();

		try
		{
		    return createEntity(cnt,ctentityId);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:getCTEntity("+ ctentityId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#createCTEntity(int)
     */
    public IItem createCTEntity(int ctentityId)
    throws ISPACException
    {
		DbCnt cnt = mcontext.getConnection();

		try
		{
			EntityDAO entityDAO = null;
		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
			entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entitydef);
			entityDAO.createNew( cnt);
			return entityDAO;
		}
		catch (ISPACException ie)
		{
		    throw new ISPACException("Error en CatalogAPI:createCTEntity("+ ctentityId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    public IItem createCTEntity(DbCnt cnt, int ctentityId)
    throws ISPACException
    {
		try
		{
			EntityDAO entityDAO = null;
		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
			entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entitydef);
			entityDAO.createNew( cnt);
			return entityDAO;
		}
		catch (ISPACException ie)
		{
		    throw new ISPACException("Error en CatalogAPI:createCTEntity("+ ctentityId + ")", ie);
		}
    }


    public IItem createCTEntityResource() throws ISPACException
    {
		DbCnt cnt = mcontext.getConnection();
		try
		{
			EntityResourceDAO entityresourceDAO = new EntityResourceDAO(cnt);
    		entityresourceDAO.createNew(cnt);
			return entityresourceDAO;
		}
		catch (ISPACException ie)
		{
		    throw new ISPACException("Error en CatalogAPI:createCTEntityResource()", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }



    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getCTEntity(int, int)
     */
    public IItem getCTEntity(int ctentityId, int ctentityRegId)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{
		    return getEntity( cnt, ctentityId, ctentityRegId);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:getCTEntity("+ ctentityId +","+ctentityRegId+")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getCTEntity(int, Map)
     */
    public IItemCollection getCTEntityByIds(int ctentityId, Map ctentityRegIds)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{
			// TODO Incluir todos los ctentityIds con su DAO
		    return getEntityByIds( cnt, ctentityId, ctentityRegIds);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:getCTEntityByIds("+ ctentityId +")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getValidationTables()
     */
    public IItemCollection getValidationTables() throws ISPACException
    {
    	return getValidationTablesByName(null);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getValidationTablesByName( java.lang.String)
     */
    public IItemCollection getValidationTablesByName(String nameFilter) throws ISPACException
    {
    	StringBuffer condicion = new StringBuffer();
		condicion.append("WHERE (TIPO = ")
				 .append(EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId())
				 .append(" OR TIPO = ")
				 .append(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId())
				 .append(")");


		if (StringUtils.isNotBlank(nameFilter)) {

			condicion.append(" AND NOMBRE LIKE '%")
					 .append(DBUtil.replaceQuotes(nameFilter))
					 .append("%'");
		}

		return queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, condicion.toString());

    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#queryCTEntities(int, java.lang.String)
     */
    public IItemCollection queryCTEntities(int ctentityId, String query)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{
		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		    return EntityFactoryDAO.getInstance().queryEntities(cnt, entitydef, query).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:queryCTEntities("+ ctentityId +","+query+")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    public IItemCollection queryCTEntitiesForUpdate(int ctentityId, String query)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{

		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		    return EntityFactoryDAO.getInstance().queryEntitiesForUpdate(cnt, entitydef, query).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:queryCTEntitiesForUpdate("+ ctentityId +","+query+"," + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    public void setCTEntitiesForUpdate(int ctentityId, String query)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{

		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		    EntityFactoryDAO.getInstance().queryEntitiesForUpdate(cnt, entitydef, query);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:setCTEntitiesForUpdate("+ ctentityId +","+query+"," + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#countCTEntities(int, java.lang.String)
     */
    public int countCTEntities(int ctentityId, String query)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{
		    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		    return EntityFactoryDAO.getInstance().countEntities(cnt, entitydef, query);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:queryCTEntities("+ ctentityId +","+query+")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#queryCTEntities(java.util.Map, java.lang.String)
	 */
	public IItemCollection queryCTEntities(Map tableentitymap, String query)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try
		{
		    TableJoinFactoryDAO joinfactory=new TableJoinFactoryDAO();

		    Iterator it=tableentitymap.entrySet().iterator();
		    while (it.hasNext())
            {
                Map.Entry entry = (Map.Entry) it.next();
                String prefix=(String)entry.getKey();
                Object enttable=entry.getValue();

                //Se procesan tablas o entidades.
                if (enttable instanceof Number)
                {
                    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(((Number)enttable).intValue());
                    joinfactory.addTable(entitydef.getName(),prefix);
                }
                else
                {
            		joinfactory.addTable((String)enttable,prefix);
                }
            }
			return joinfactory.queryTableJoin(cnt,query).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:queryCTEntities()",ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    /*
    public EntityApp getCTEntityApp(int ctentityId, int ctentityRegId)
    throws ISPACException
    {

        /*CatalogEntityApp ctentityapp = new CatalogEntityApp(mcontext);

        ctentityapp.setAppName("Entidades");
        ctentityapp.setURL("/forms/ctentityform.jsp");
        ctentityapp.setItem(getCTEntity(1, ctentityRegId));
        ctentityapp.setMainEntity(11);
        ctentityapp.setFormatter(null);

        // Inicialización de la aplicación.
        ctentityapp.initiate();

        return ctentityapp;
    }
    */

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#getCTEntityApp(int, int, int, java.lang.String)
     */
    public EntityApp getCTEntityApp(int appId, int ctentityId, int ctentityRegId, String path)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try
		{
			// Obtiene la aplicación especificada
		    IApplicationDef appdef=CTObjectCatalog.getInstance().getAppDef(appId);
			EntityDAO entityDAO =getEntity(cnt,ctentityId, ctentityRegId);
			return EntityAppFactory.createEntityApp(mcontext, appdef, null, ISPACEntities.ENTITY_NULLREGKEYID, ctentityId, entityDAO, path, 0);
		}
		catch (ISPACNullObject e)
		{
		    throw new ISPACNullObject("Error en CatalogAPI:getCTEntityApp("
					+appId+ ", "+ ctentityId + ", "+ ctentityRegId + ")", e);
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:getCTEntityApp("
					+appId+ ", "+ ctentityId + ", "+ ctentityRegId + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#getCTEntityApp(int, int, java.lang.String)
	 */
	public EntityApp getCTEntityApp(int appId, int ctentityId, String path)
	throws ISPACException
	{
		return getCTEntityApp(appId, ctentityId, ISPACEntities.ENTITY_NULLREGKEYID, path);
	}

    /**
     * Obtiene la aplicación indicada. Además crea un registro
     * para la entidad especificada y carga la aplicación con sus datos.
     *
     * @param appId identificador de la aplicación
     * @param entdef definición de la entidad
     * @param path ruta de la aplicación
     * @return EntityApp cargada con los datos del registro recien creado
     * @throws ISPACException
     */
	private EntityApp newCTEntityApp(int appId, IEntityDef entdef, String path)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

		try
		{
			// Obtiene la aplicación de la entidad
		    IApplicationDef appdef=CTObjectCatalog.getInstance().getAppDef(appId);
		    EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entdef);
	        entityDAO.createNew(cnt);
	        entityDAO.store(cnt);

			return EntityAppFactory.createEntityApp(mcontext, appdef, null, ISPACEntities.ENTITY_NULLREGKEYID, entdef.getId(), entityDAO, path, 0);
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:newCTEntityApp("
					+appId+ ", "+ entdef.getId() + " )", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#newCTEntityApp(int, int, java.lang.String)
	 */
	public EntityApp newCTEntityApp(int appId, int ctentityId, String path)
	throws ISPACException
	{
	    IEntityDef entitydef = CTObjectCatalog.getInstance().getEntityDef(ctentityId);
        return newCTEntityApp(entitydef.getAppId(), entitydef, path);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#getCTDefaultEntityApp(int, java.lang.String)
	 */
	public EntityApp getCTDefaultEntityApp(int ctentityId, String path)
	throws ISPACException
	{
	 	return getCTDefaultEntityApp(ctentityId, ISPACEntities.ENTITY_NULLREGKEYID, path);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#getCTDefaultEntityApp(int, int, java.lang.String)
	 */
	public EntityApp getCTDefaultEntityApp(int ctentityId, int ctentityRegId, String path)
	throws ISPACException
	{
	    IEntityDef entitydef=CTObjectCatalog.getInstance().getEntityDef(ctentityId);
	    return getCTEntityApp(entitydef.getAppId(), ctentityId, ctentityRegId, path);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#newCTDefaultEntityApp(int, java.lang.String)
	 */
	public EntityApp newCTDefaultEntityApp(int ctentityId, String path)
	throws ISPACException
	{
	    IEntityDef entitydef = CTObjectCatalog.getInstance().getEntityDef(ctentityId);
		return newCTEntityApp(entitydef.getAppId(), entitydef, path);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#getPEvents(int, int)
	 */
	public IItemCollection getPEvents(int pobjectType, int pobjectId)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

	    try
	    {
	    	return PEventoDAO.getEvents(cnt, pobjectType, pobjectId).disconnect();
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:getPEvents("
					+ pobjectType + ", "+ pobjectId + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#getPRulesEvent(int, int, int)
	 */
	public IItemCollection getPRulesEvent(int pobjectType, int pobjectId, int peventCod)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

	    try
	    {
	        return PEventoDAO.getEvents(cnt, pobjectType, pobjectId, peventCod).disconnect();
	    }
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:delPEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getPRuleEvent(int pobjectType, int pobjectId, int peventCod, int ruleId, int order)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

	    try
	    {
	        return PEventoDAO.getEvent(cnt, pobjectType, pobjectId, peventCod, ruleId, order);
	    }
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:getPRuleEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ", " + ruleId
					+ ", " + order + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#delPEvent(int, int, int)
	 */
	public void delPEvent(int pobjectType, int pobjectId, int peventCod)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try
		{
			CollectionDAO collection = new CollectionDAO (PEventoDAO.class);
			collection.delete(cnt, "WHERE ID_OBJ=" + pobjectId + " AND TP_OBJ=" +
			        pobjectType + " AND EVENTO=" + peventCod);
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:delPEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#delPRuleEvent(int, int, int, int)
	 */
	public void delPRuleEvent(int pobjectType, int pobjectId, int peventCod, int pruleId,int order)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try
		{
			CollectionDAO collection = new CollectionDAO (PEventoDAO.class);
			collection.delete(cnt, "WHERE ID_OBJ=" + pobjectId + " AND TP_OBJ="
			        + pobjectType + " AND EVENTO=" + peventCod + " AND ID_REGLA=" + pruleId
			        + " AND ORDEN=" + order);
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:delPRuleEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ", " + pruleId + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ICatalogAPI#addPRuleEvent(int, int, int, int)
	 */
	public void addPRuleEvent(int pobjectType, int pobjectId, int peventCod, int pruleId)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

	    try
		{
		    PEventoDAO objevent = new PEventoDAO(cnt);
		    objevent.createNew(cnt);
		    objevent.setOrderSequence(cnt);
		    objevent.set("ID_OBJ", pobjectId);
		    objevent.set("TP_OBJ", pobjectType);
		    objevent.set("EVENTO", peventCod);
		    objevent.set("ID_REGLA", pruleId);
	        objevent.store(cnt);
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:addPRuleEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ", " + pruleId + ")", e);
		}
	    finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void addPConditionEvent(int pobjectType, int pobjectId, int peventCod, String condition)
	throws ISPACException
	{
	    DbCnt cnt = mcontext.getConnection();

	    try
		{
		    PEventoDAO objevent = new PEventoDAO(cnt);
		    objevent.createNew(cnt);
		    objevent.setOrderSequence(cnt);
		    objevent.set("ID_OBJ", pobjectId);
		    objevent.set("TP_OBJ", pobjectType);
		    objevent.set("EVENTO", peventCod);
		    objevent.set("ID_REGLA", ISPACEntities.ENTITY_NULLREGKEYID);
		    objevent.set("CONDICION", condition);
	        objevent.store(cnt);
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:addPConditionEvent("
					+ pobjectType + ", "+ pobjectId + ", " + peventCod + ", ...)", e);
		}
	    finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void incOrderPEvents(int objectType, int objectId, int eventCod, int ruleId, int orderRule)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try
		{
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	        PEventoDAO destevent = PEventoDAO.getEventRuleAnt(cnt, objectType,
	                objectId, eventCod, orderRule);

	        PEventoDAO origevent = PEventoDAO.getEvent(cnt, objectType,
	                objectId, eventCod,ruleId, orderRule);

	        if (destevent != null && origevent !=null)
	            modOrderEvents(cnt,destevent, origevent);

	        // Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:incOrderPEvents(" + objectType + ", "
			        + objectId + ", " + eventCod + ", " + ruleId + ", " + orderRule + ")", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
	    }
	}

	public void decOrderPEvents(int objectType, int objectId, int eventCod, int ruleId, int orderRule)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try
		{
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	        PEventoDAO destevent = PEventoDAO.getEventRulePred(cnt, objectType,
	                objectId, eventCod, orderRule);

	        PEventoDAO origevent = PEventoDAO.getEvent(cnt, objectType,
	                objectId, eventCod, ruleId, orderRule);

	        if (destevent != null && origevent !=null)
	            modOrderEvents(cnt,destevent, origevent);

	        // Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:decOrderPEvents(" + objectType + ", "
			        + objectId + ", " + eventCod + ", " + ruleId + ", " + orderRule + ")", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
	    }
	}

	private void modOrderEvents (DbCnt cnt, PEventoDAO destevent, PEventoDAO origevent)
	throws ISPACException
	{
	    int objid = destevent.getInt("ID_OBJ");
	    int tpobj = destevent.getInt("TP_OBJ");
	    int event = destevent.getInt("EVENTO");
	    int ruleid = destevent.getInt("ID_REGLA");
	    String condition = destevent.getString("CONDICION");

	    destevent.delete(cnt);
	    origevent.delete(cnt);

	    PEventoDAO desteventMod=new PEventoDAO(cnt);
	    PEventoDAO origeventMod=new PEventoDAO(cnt);
	    origeventMod.createNew(cnt);
	    desteventMod.createNew(cnt);

	    desteventMod.set("ID_OBJ", origevent.getInt("ID_OBJ"));
	    desteventMod.set("TP_OBJ", origevent.getInt("TP_OBJ"));
	    desteventMod.set("EVENTO", origevent.getInt("EVENTO"));
	    desteventMod.set("ID_REGLA", origevent.getInt("ID_REGLA"));
	    desteventMod.set("CONDICION", origevent.getString("CONDICION"));
	    //Se conserva el orden
	    desteventMod.set("ORDEN", destevent.getInt("ORDEN"));

	    origeventMod.set("ID_OBJ", objid);
	    origeventMod.set("TP_OBJ", tpobj);
	    origeventMod.set("EVENTO", event);
	    origeventMod.set("ID_REGLA", ruleid);
	    origeventMod.set("CONDICION", condition);
	    //Se conserva el orden
	    origeventMod.set("ORDEN", origevent.getInt("ORDEN"));

	    desteventMod.store(cnt);
	    origeventMod.store(cnt);
	}

	public IItem setDeadLine(int Id, int objType, int idObj, String sXML)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try
		{
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			// Obtiene la aplicación de la entidad
			PPlazoDAO plazoDao = new PPlazoDAO(cnt);

			if(Id == ISPACEntities.ENTITY_NULLREGKEYID) {

				plazoDao.createNew(cnt);
				plazoDao.set("PLAZO", sXML);
				plazoDao.store(cnt);
				PRelPlazoDAO relPlazoDao = new PRelPlazoDAO(cnt);
				relPlazoDao.createNew(cnt);
				relPlazoDao.setKey(plazoDao.getKey());
				relPlazoDao.set("TP_OBJ", objType);
				relPlazoDao.set("ID_OBJ", idObj);
				relPlazoDao.store(cnt);
			}
			else {
				plazoDao.setKey(Id);
				plazoDao.load(cnt);
				plazoDao.set("PLAZO", sXML);
				plazoDao.store(cnt);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;

			return plazoDao;
		}
		catch (Exception e)
		{
			throw new ISPACException("Error estableciendo el plazo para la entidad. ", e);
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public IItem getDeadLine(int objType, int idObj)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try
		{
			IItem mitem = null;

			CollectionDAO deadlines = PPlazoDAO.getDeadline(cnt, objType, idObj);
			if (deadlines.next()) {
				mitem = (IItem)deadlines.value();
			} else {
				mitem = new PPlazoDAO(cnt);
				mitem.setKey(ISPACEntities.ENTITY_NULLREGKEYID);
			}

			return mitem;
		}
		catch (Exception e)
		{
			throw new ISPACException(
					"Error obteniendo el plazo asociado a la entidad. ", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void dropDeadLine(int objType, int idObj)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			IItem deadline = getDeadLine(objType, idObj);
			int plazoKey = deadline.getKeyInt();
			deadline.delete(mcontext);

			if (plazoKey > -1) {

				//borrado de tabla de relaciones
				PRelPlazoDAO relPlazoDao = new PRelPlazoDAO(cnt);
				relPlazoDao.setKey(plazoKey);
				//no hace falta el load para borrar!!
				//relPlazoDao.load(cnt);
				relPlazoDao.delete(cnt);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (Exception e)
		{
			throw new ISPACException("Error eliminando el plazo asociado a la entidad. ", e);
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
	 * Obtiene los informes del catálogo.
	 *
	 * @return Lista de informes del catálogo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTReports() throws ISPACException {
		return getCTReports(null);
	}


	/**
	 * Obtiene los informes del catálogo a partir del nombre.
	 *
	 * @param pattern
	 *            Patrón del nombre del informe.
	 * @return Lista de informes del catálogo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTReports(String pattern) throws ISPACException {

		StringBuffer sql = new StringBuffer();

        if (StringUtils.isNotBlank(pattern)) {
        	sql.append("WHERE NOMBRE LIKE '%")
        		.append(DBUtil.replaceQuotes(pattern.trim()))
        		.append("%'");
        }

        sql.append(" ORDER BY NOMBRE");

		return queryCTEntities(ICatalogAPI.ENTITY_CT_INFORMES, sql.toString());
	}

	/**
	 * Obtiene la información del informe en el catálogo.
	 *
	 * @param id
	 *            Identificador del informe.
	 * @return Información del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTReport(int id) throws ISPACException {
		return getCTEntity(ENTITY_CT_INFORMES, id);
	}

	/**
	 * Obtiene los informes de un tipo determinado
	 * @param type Tipo del informe 1-Genérico 2-Específico 3-Global 4-Busqueda
	 * @return
	 */
	public IItemCollection getReportByType(int type) throws ISPACException{
		StringBuffer sql = new StringBuffer();

        sql.append("WHERE TIPO= "+type);
        sql.append(" ORDER BY NOMBRE");

		return queryCTEntities(ICatalogAPI.ENTITY_CT_INFORMES, sql.toString());
	}


	/**
	 * Obtener los circuitos de firmas disponibles para un procedimiento
	 *
	 * @param idPcd
	 * @return IItemCollection
	 * @throws ISPACException
	 */
    public IItemCollection getCtosFirmasProcedure(int pcdId)
    throws ISPACException
    {
    	DbCnt cnt = mcontext.getConnection();

		try
		{
			String sql = "WHERE CFC.ID_CIRCUITO = PCTOSFIRMA.ID_CIRCUITO AND PCTOSFIRMA.ID_PCD  = " + pcdId;
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
			factory.addTable( "SPAC_CTOS_FIRMA_CABECERA", "CFC");
			factory.addTable( "SPAC_P_CTOSFIRMA", "PCTOSFIRMA");

	   		CollectionDAO collection = factory.queryTableJoin(cnt, sql);
			return collection.disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:getCtosFirmasProcedure("+ pcdId + ")", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

	/**
	 * Para añadir un circuito de firmas al procedimiento seleccionado
	 *
	 * @param idPcd
	 * @param ctofirmaId
	 * @throws ISPACException
	 */
	 public void addCtoFirmas(int pcdId, int ctofirmaId)
	 throws ISPACException
	 {
		 DbCnt cnt = mcontext.getConnection();

		 try {
			PCtoFirmaDAO pcfdao = new PCtoFirmaDAO(cnt);
			pcfdao.createNew(cnt);
			pcfdao.set("ID_CIRCUITO", ctofirmaId);
			pcfdao.set("ID_PCD", pcdId);
			pcfdao.store(cnt);
		 }
		 catch (Exception e)
		 {
			 throw new ISPACException("Error en CatalogAPI:addCtoFirmas("
						+ pcdId + ", " + ctofirmaId + ")",e);
		 }
		 finally
		 {
			mcontext.releaseConnection(cnt);
		 }
	}

	/**
	 * Para borrar circuito de firmas asociado a un procedimiento
	 *
	 * @param idPcd
	 * @param ctofirmaId
	 * @throws ISPACException
	 */
	 public void dropCtoFirmas(int pcdId, int ctofirmaId)
	 throws ISPACException
	 {
		 DbCnt cnt = mcontext.getConnection();

		 try {
			 CollectionDAO collection = new CollectionDAO(PCtoFirmaDAO.class);
			 collection.delete(cnt, "WHERE ID_CIRCUITO=" + ctofirmaId + " AND ID_PCD="+ pcdId);
		 }
		 catch (Exception e)
		 {
			 throw new ISPACException("Error en CatalogAPI:dropCtoFirmas("
						+ pcdId + ", " + ctofirmaId + ")",e);
		 }
		 finally
		 {
			mcontext.releaseConnection(cnt);
		 }
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#incOrderPEntity(int, int)
     */
    public void incOrderPEntity(int procedureid, int pentityid)
    throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try
		{
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	        PEntidadDAO origpentity = new PEntidadDAO(cnt, pentityid);

	        PEntidadDAO destpentity = origpentity.getAntecessor(cnt,procedureid);

	        if (destpentity != null && origpentity !=null)
	            modOrderPEntities(cnt,destpentity, origpentity);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:incOrderPEntity(" +
			        procedureid + ", " + pentityid + ")", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
	    }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.ICatalogAPI#decOrderPEntity(int, int)
     */
    public void decOrderPEntity(int procedureid, int pentityid)
    throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try
		{
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	        PEntidadDAO origpentity = new PEntidadDAO(cnt, pentityid);

	        PEntidadDAO destpentity = origpentity.getPredecessor(cnt,procedureid);

	        if (destpentity != null && origpentity !=null)
	            modOrderPEntities(cnt,destpentity, origpentity);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:decOrderPEntity(" +
			        procedureid + ", " + pentityid + ")", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
	    }
    }

    public void modOrderPEntities(DbCnt cnt,PEntidadDAO destpentity,PEntidadDAO origpentity)
    throws ISPACException
    {
        if (origpentity.isNull("ORDEN"))
            origpentity.set("ORDEN",origpentity.getKeyInt());

        if (destpentity.isNull("ORDEN"))
            destpentity.set("ORDEN",destpentity.getKeyInt());

        int origorder=origpentity.getInt("ORDEN");
        origpentity.set("ORDEN",destpentity.getInt("ORDEN"));
        destpentity.set("ORDEN",origorder);

        origpentity.store(cnt);
        destpentity.store(cnt);
    }

//    private class PredicateFindFieldByName implements Predicate {
//		String fieldNameToSearch = null;
//
//		PredicateFindFieldByName(String fieldNameToSearch) {
//			this.fieldNameToSearch = fieldNameToSearch;
//		}
//
//		public boolean evaluate(Object arg0) {
//			return ((EntityField) arg0).getPhysicalName().equals(fieldNameToSearch);
//		}
//	}

    private CTEntityDAO createEntity(DbCnt cnt,
    								 EntityType entityType,
    								 String logicalName,
    								 String physicalName,
    								 String description,
    								 EntityDef entityDefinition,
    								 int valueColSize,
    								 int subsColSize,
    								 boolean tblExist) throws ISPACException
    {
    	EntityTableManager entityTableManager = new EntityTableManager();

    	if (!tblExist) {

    		// comprobar si la entidad ya existe
	        if (entityTableManager.isTableCreated(cnt, physicalName)) {
	            throw new ISPACInfo("exception.entities.tableBD.alreadyExists", new Object[] {physicalName});
	        }
    	}

        // Establecer el nombre de la clave primaria (necesario en mayúsculas)
        String fieldNamePrimaryKey = null;

        if (entityType.isValidationTableType()) {
        	// Tabla de validación
            fieldNamePrimaryKey = CODIGO_FIELD_NAME.toUpperCase();
        }
        else {
        	// Entidad
            fieldNamePrimaryKey = ID_FIELD_NAME.toUpperCase();
        }

        // Secuencia
        String sqPkName = "<SIN SECUENCIA>";

        // Campos
        List newFields = new ArrayList(entityDefinition.getFields());

    	// Creación de la tabla (si no es una tabla de sistema)
        if (!entityType.isSystemValidationTableType()) {

        	// Tabla de validación
            if (entityType.isValidationTableType()) {

            	// Código
	            newFields.add(getCodigoFieldInstace(1));
	            // Valor
	            newFields.add(getValorFieldInstace(2, valueColSize));
	            // Tabla de validación con sustituto
	            if (entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)) {
	                newFields.add(getSustitutoFieldInstace(3, subsColSize));
	            }
	            // Estado
	            newFields.add(getEstadoFieldInstace(newFields.size() + 1));

	            //Orden
	            newFields.add(getOrdenFieldInstance(newFields.size()+1));
            }
            // Entidad
            else {

            	// Entidad de proceso (necesita número de expediente)
            	if (entityType.equals(EntityType.PROCESS_ENTITY_TYPE)) {
                 //Creamos un indice para el numero de expediente
                    EntityIndex index = new EntityIndex();
                    index.addFieldId((entityDefinition.findField(NUMEXP_FIELD_NAME)).getId());
                    index.setKey(entityDefinition.getFields().size()+1);
                    index.setName("IND_" + Math.abs(physicalName.hashCode()));
                    entityDefinition.addIndex(index);
                }
            }

            // No crear la tabla física ni la secuencia cuando se indica que la tabla ya existe
            if (!tblExist) {

            	entityTableManager.createTable(cnt, physicalName, newFields, entityDefinition);

                // creacion de secuencias para PK
                //if (isSequenceNeeded(entityType, entityDefinition)) {
            	sqPkName = entityTableManager.createSequence(cnt, physicalName, null);
                //}
            }
            // Si la tabla ya existe y es de validación
            // se comprueba que la definición se corresponde con las columnas de la tabla en BD
            else if ((entityType.isValidationTableType()) &&
            		 (!entityTableManager.checkValidationTableColumns(cnt, physicalName, newFields))) {

            	throw new ISPACInfo("exception.entities.validationTable.exists");
            }
        }

        // Actualizar los campos de la definición de la entidad
        entityDefinition.setFields(newFields);

        // Crear la entidad en SPAC_CT_ENTIDADES
        CTEntityDAO entitiesDAO = new CTEntityDAO(cnt);
        entitiesDAO.createNew(cnt);

        entitiesDAO.set("TIPO", entityType.getId());
        entitiesDAO.set("NOMBRE", physicalName.toUpperCase());
        entitiesDAO.set("CAMPO_PK", fieldNamePrimaryKey);
        entitiesDAO.set("DESCRIPCION", description);
        entitiesDAO.set("SEC_PK", sqPkName);
        entitiesDAO.set("DEFINICION", entityDefinition.getXmlValues());
        entitiesDAO.set("FECHA", DateUtil.getToday());

        // Campo que guarda el número de expediente en las entidades de proceso
        if (entityType.equals(EntityType.PROCESS_ENTITY_TYPE)) {
            entitiesDAO.set("CAMPO_NUMEXP", NUMEXP_FIELD_NAME.toUpperCase());
            entitiesDAO.set("SCHEMA_EXPR", NUMEXP_FIELD_NAME.toUpperCase());
        }

        entitiesDAO.store(cnt);

        return entitiesDAO;
    }

    public int createEntity(EntityType entityType,
    						String logicalName,
    						String physicalName,
    						String description,
    						EntityDef entityDefinition,
    						int valueColSize,
    						int subsColSize,
    						boolean tblExist) throws ISPACException {

    	// Campos a restaurar en caso de error al crear la entidad
    	List oldFields = new ArrayList(entityDefinition.getFields());

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
		String tableName=null;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	    	/*
	    	// Idioma para los recursos: el del cliente si está soportado, en caso contrario, el de por defecto
	    	String appLanguage = mcontext.getAppLanguage();
	    	*/
	    	// Ahora los recursos se crean para todos los idiomas
	    	String[] languages = ConfigurationMgr.getLanguages(mcontext);

			// Bloquear las entidades
			setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_ENTITY, "");

			// Crear la entidad
			CTEntityDAO entityDAO = createEntity(cnt, entityType, logicalName, physicalName, description, entityDefinition, valueColSize, subsColSize, tblExist);

			// Si la entidad no es de validación
			// generar los recursos junto con el formulario JSP por defecto de la entidad
			if (!entityType.isValidationTableType()) {

				// Generar los recursos de la entidad
				generateEntityResources(cnt, entityDAO.getId(), physicalName, logicalName, entityDefinition, languages);

				// Generar el formulario de la entidad
				String jspCodeEntityForm = generateEntityForm(cnt, entityDAO, physicalName, entityDefinition);

				// Agregación de entidades
				List entities = new ArrayList();
				entities.add(physicalName);
				List dataBlocks = new ArrayList();
				dataBlocks.add(jspCodeEntityForm);


				String jspCodeDefaultForm = JSPBuilder.generateTabsDataBlocks(entities, dataBlocks, false);

				JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();

				// Establecer el formulario por defecto para la entidad
				CTApplicationDAO applicationDAO = new CTApplicationDAO(cnt);
				applicationDAO.createNew(cnt);

				// Nombre
				applicationDAO.set("NOMBRE", jspBuilderConfiguration.get(JSPBuilderConfiguration.CREATE_ENTITY_FORM_NAME) + physicalName);
				// Descripción
				applicationDAO.set("DESCRIPCION", MessagesFormatter.format(jspBuilderConfiguration.get(JSPBuilderConfiguration.CREATE_ENTITY_FORM_DESCRIPTION), new Object[] {physicalName}));
				// Clase
				applicationDAO.set("CLASE", jspBuilderConfiguration.get(JSPBuilderConfiguration.CREATE_ENTITY_FORM_CLASS));
				//Página
				String page = "/forms/"
							+ physicalName
							+ "/" + applicationDAO.getString("NOMBRE").replace(' ', '_')
							+ ".jsp";
				applicationDAO.set("PAGINA", page);
				// XML de parámetros
				applicationDAO.set("PARAMETROS", entityDefinition.generateXmlParameters());
				// Id de entidad principal
				applicationDAO.set("ENT_PRINCIPAL_ID", entityDAO.getKeyInt());
				// Nombre de entidad principal
				applicationDAO.set("ENT_PRINCIPAL_NOMBRE", physicalName);
				// Formulario JSP
				applicationDAO.set("FRM_JSP", jspCodeDefaultForm);
				// Versión del formulario
				applicationDAO.set("FRM_VERSION", 1);

				applicationDAO.store(cnt);

				// Guardar la entidad con su formulario JSP y su formulario por defecto
				entityDAO.set("FRM_EDIT", applicationDAO.getKeyInt());
				entityDAO.store(cnt);
			}
			else {
				// Recurso para el nombre de la entidad de validación
				for (int i = 0; i < languages.length; i++) {

					EntityResourceDAO entityResource = new EntityResourceDAO(cnt);
					entityResource.createNew(cnt);
			        entityResource.set("ID_ENT", entityDAO.getId());
			        entityResource.set("IDIOMA", languages[i]);
			        entityResource.set("CLAVE", physicalName);
			        entityResource.set("VALOR", logicalName);
			        entityResource.store(cnt);
				}
			}

			tableName=entityDAO.getName();
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit=true;
			return entityDAO.getId();
		}
		catch (ISPACException ie)
		{
			// Restaurar los campos originales (sin los que se crean automáticamente)
			entityDefinition.setFields(oldFields);

			if (ie instanceof ISPACInfo) {
				throw ie;
			}
			else {
				throw new ISPACInfo(ie.getMessage(), ie);
			}
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
			if(bCommit){
			//Recorremos todos los campos y si alguno tiene busqueda documental asociada la generamos.
			for(int i=0; i<entityDefinition.getFields().size(); i++){
				EntityField field= (EntityField) entityDefinition.getFields().get(i);
				if(field.isDocumentarySearch()){
					documentarySearch(true, mcontext.getConnection(), field.getPhysicalName(), tableName);
				}
			}
			}
		}
	}

    public void modifyEntity(int entityId,
    						 String physicalName,
    						 String newLogicalName,
    						 String newDescription,
    						 EntityDef newEntityDefinition,
    						 String language) throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			CTEntityDAO entityDAO = new CTEntityDAO(cnt, entityId);
			entityDAO.set("DESCRIPCION", newDescription);
			//entityDAO.set("FRM_EDIT", newDefaultAppId);
			entityDAO.set("DEFINICION", newEntityDefinition.getXmlValues());
			entityDAO.store(cnt);

			//actualizar resources
			StringBuffer query = new StringBuffer();
			query.append(" WHERE ID_ENT=")
				 .append(entityId)
				 .append(" AND IDIOMA='")
				 .append(language)
				 .append("' AND CLAVE='")
				 .append(physicalName)
				 .append("' ");

			IItemCollection col = mcontext.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
			if (col.next()) {

				IItem item = (IItem)col.iterator().next();
				item.set("VALOR", newLogicalName);
				item.store(mcontext);
			}
			else {
	        	// Generar el recurso asociado a la etiqueta
	        	EntityResourceDAO entityResource = new EntityResourceDAO(cnt);
	        	entityResource.createNew(cnt);
	            entityResource.set("ID_ENT", entityId);
	            entityResource.set("IDIOMA", language);
	            entityResource.set("CLAVE", physicalName);
	            entityResource.set("VALOR", newLogicalName);
	            entityResource.store(cnt);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en CatalogAPI:createEntity", e);
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	private void deleteEntity(DbCnt cnt, int entityId, boolean deleteTableInBD)
	throws Exception
	{
		EntityTableManager entityTableManager = new EntityTableManager();

        // obtener el nombre de la tabla de la entidad
		CTEntityDAO entityDAO = new CTEntityDAO(cnt);
		entityDAO.set("ID", entityId);
		entityDAO.load(cnt);
		String tblName = (String) entityDAO.get("NOMBRE");
		String entityDenifitionXML = (String) entityDAO.get("DEFINICION");
		EntityType tipo = EntityType.getInstance(entityDAO.getType());

		EntityDef entityDefinition = EntityDef.parseEntityDef(entityDenifitionXML);
		if (!entityDefinition.isDropable()) {
			throw new ISPACInfo("exception.entities.deleteEntity.system", new String[] {tblName});
		}

		// Si la tabla no es una tabla de validación ya existente (no editable)
		if (!(tipo.isValidationTableType() && !entityDefinition.isEditable())) {

			if (entityTableManager.isTableFilled(cnt, tblName)) {
				throw new ISPACInfo("exception.entities.deleteEntity.noEmpty", new String[] {tblName});
			}

			// eliminar la tabla
			if (deleteTableInBD) {
				entityTableManager.dropTable(cnt, tblName);
			}

			// eliminar secuencia
			//int type  = entityDAO.getInt("TIPO");
			//if (isSequenceNeeded(EntityType.getInstance(type), entityDefinition)){
				entityTableManager.deleteSequence(cnt, (String) entityDAO.get("SEC_PK"));
			//}
		}

		//eliminar aplicaciones de la entidad
		CTApplicationDAO.deleteApp(cnt, entityId);

		//Eliminar los recursos asociados al field
    	EntityResourceDAO.deleteResources(cnt, entityId);

		//eliminar de la tabla de entidades
        CTEntityDAO entitiesDAO = new CTEntityDAO(cnt);
        entitiesDAO.set("ID", entityId);
        entitiesDAO.delete(cnt);

	}

	public void deleteEntity(int entityId, boolean deleteTableInBD)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			deleteEntity(cnt, entityId, deleteTableInBD);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (ISPACInfo e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new ISPACInfo("exception.entities.deleteEntity.error", new Object[] {e.getCause()});
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public void deleteResources(int entityId, String campo)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	    	EntityResourceDAO.deleteFieldResources(cnt, entityId, campo);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (ISPACInfo e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new ISPACInfo("exception.entities.deleteEntity.error", new Object[] {e.getCause()});
		}
		finally
		{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}


	private void recalcularIds(List items)
	throws ISPACException
	{
		// Reorganizar los ids de los items
        if (!items.isEmpty()) {

            XmlObject field;
            for (int i = 0; i < items.size(); i++) {
                field = (XmlObject) items.get(i);
                field.set("ID", i+1);
            }
        }
	}

	private void recalcularIdsIndicesValidations(List fields,
												 List indexes,
												 List validations) throws ISPACException
	{
		// Map para clave=id_field_antiguo, valor=id_field_nuevo
		Map fieldIds = new HashMap();

        if (!fields.isEmpty()) {

            XmlObject field;
            // Reorganizamos los identificadores de los campos
            // guardando en el map los nuevos ids para los antiguos ids
            for (int i = 0; i < fields.size(); i++) {

                field = (XmlObject) fields.get(i);
                int identifier = field.getKeyInt("ID");
                fieldIds.put(new Integer(identifier), new Integer(i+1));
                field.set("ID", i+1);
            }

            // Recorremos todos los indices
            // y actualizamos los ids de los campos antiguos con los nuevos
            for (Iterator itrIndexes = indexes.iterator(); itrIndexes.hasNext();) {

            	EntityIndex aIndex = (EntityIndex) itrIndexes.next();
            	List list = (List) aIndex.get("fieldIds");
            	List listTemp=new LinkedList();
            	for (Iterator itr=list.iterator();itr.hasNext();) {
                    Integer it = (Integer) itr.next();
                    Integer i = (Integer) fieldIds.get(it);
            		listTemp.add(i);
            	}
            	aIndex.setFieldIds(listTemp);
            }

            // Recorremos todos las validaciones
            // y cambiamos los ids de los campos antiguos con los nuevos
            for (Iterator itrValidations = validations.iterator(); itrValidations.hasNext();) {

            	EntityValidation aValidation = (EntityValidation) itrValidations.next();
            	int identifier = aValidation.getFieldId();
            	Integer newIdentifier = (Integer) fieldIds.get(new Integer(identifier));
				aValidation.setFieldId(newIdentifier.intValue());
            }
        }
	}

	public EntityDef dropFieldTemporal( EntityField fieldToRemove, EntityDef entityDefinition)
	throws ISPACException
	{


        try {


        	if (ID_FIELD_NAME.equalsIgnoreCase(fieldToRemove.getPhysicalName())) {
        		throw new ISPACInfo("exception.entities.deleteField.no", new Object[] {ID_FIELD_NAME});
        	}
        	else if (NUMEXP_FIELD_NAME.equalsIgnoreCase(fieldToRemove.getPhysicalName())){
        		throw new ISPACInfo("exception.entities.deleteField.no", new Object[] {NUMEXP_FIELD_NAME});
        	}



        	// Comprobar que no existan indices sobre el campo
        	List indexes = entityDefinition.getIndexes();
        	for (Iterator iter = indexes.iterator(); iter.hasNext();) {

				EntityIndex aIndex = (EntityIndex) iter.next();
				if (aIndex.isOverField(fieldToRemove.getId())) {
					throw new ISPACInfo("exception.entities.deleteField.hasIndex");
				}
			}

        	//comprobar que no existan validaciones sobre el campo
        	List validations = entityDefinition.getValidations();
        	for (Iterator iter = validations.iterator(); iter.hasNext();) {

        		EntityValidation aValidation = (EntityValidation) iter.next();
				if (aValidation.getFieldId()==fieldToRemove.getId()) {
					throw new ISPACInfo("exception.entities.deleteField.hasValidation");
				}
			}

        	// Actualizar la entidad
        	entityDefinition.removeField(fieldToRemove);

        	recalcularIdsIndicesValidations(entityDefinition.getFields(), entityDefinition.getIndexes(), entityDefinition.getValidations());

        	return entityDefinition;
        }
        catch (ISPACException ie)
        {
			if (ie instanceof ISPACInfo) {
				throw ie;
			}
			else {
				throw new ISPACInfo(ie.getMessage(), ie);
			}
        }
        catch (Exception e)
        {
			throw new ISPACInfo("exception.entities.deleteField.error", new Object[] {e.getMessage()});
        }

	}

	public EntityDef dropField(int entityId, EntityField fieldToRemove)
	throws ISPACException
	{
		EntityTableManager entityTableManager = new EntityTableManager();

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
		 String tblName=null;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	if (ID_FIELD_NAME.equalsIgnoreCase(fieldToRemove.getPhysicalName())) {
        		throw new ISPACInfo("exception.entities.deleteField.no", new Object[] {ID_FIELD_NAME});
        	}
        	else if (NUMEXP_FIELD_NAME.equalsIgnoreCase(fieldToRemove.getPhysicalName())){
        		throw new ISPACInfo("exception.entities.deleteField.no", new Object[] {NUMEXP_FIELD_NAME});
        	}

            CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
            tblName = ctEntityDAO.getName();

            // Comprobar que la tabla esta vacia
            if (entityTableManager.isTableFilled(cnt, tblName)) {
                throw new ISPACInfo("exception.entities.deleteField.table.notEmpty");
            }

            // Obtener la definición de la entidad
        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        	EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

        	// Comprobar que no existan indices sobre el campo
        	List indexes = entityDefinition.getIndexes();
        	for (Iterator iter = indexes.iterator(); iter.hasNext();) {

				EntityIndex aIndex = (EntityIndex) iter.next();
				if (aIndex.isOverField(fieldToRemove.getId())) {
					throw new ISPACInfo("exception.entities.deleteField.hasIndex");
				}
			}

        	//comprobar que no existan validaciones sobre el campo
        	List validations = entityDefinition.getValidations();
        	for (Iterator iter = validations.iterator(); iter.hasNext();) {

        		EntityValidation aValidation = (EntityValidation) iter.next();
				if (aValidation.getFieldId()==fieldToRemove.getId()) {
					throw new ISPACInfo("exception.entities.deleteField.hasValidation");
				}
			}

        	// Actualizar la entidad
        	entityDefinition.removeField(fieldToRemove);

        	//si el campo es multivalor, y no hay mas campos multivalor del mismo tipo se borra la tabla correspondiente
        	if (fieldToRemove.isMultivalue()){
        		List fields = entityDefinition.getMultivalueFields(MultivalueTable.getCompatibleInternalDataType(fieldToRemove.getType()));
        		if (fields.size() == 0){
        			String multivalueTblName = MultivalueTable.getInstance().composeMultivalueTableName(tblName, fieldToRemove.getType().getJdbcType());
        			entityTableManager.dropMultiValueTable(cnt, multivalueTblName);

        			entityTableManager.deleteSequence(cnt, EntityTableManager.composeSequenceName(multivalueTblName));
        		}
        	}

        	recalcularIdsIndicesValidations(entityDefinition.getFields(), entityDefinition.getIndexes(), entityDefinition.getValidations());

        	//recalcularIds(entityDefinition.getFields());

        	ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        	ctEntityDAO.set("FECHA", new Date());
        	ctEntityDAO.store(cnt);

            // Eliminar columna de la tabla física
        	if(!fieldToRemove.isMultivalue()){
        	entityTableManager.dropFieldTable(cnt, tblName, fieldToRemove);
        	}

        	// Eliminar los recursos asociados al field
        	EntityResourceDAO.deleteFieldResources(cnt, entityId, fieldToRemove.getPhysicalName());

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;

        	return entityDefinition;
        }
        catch (ISPACException ie)
        {
			if (ie instanceof ISPACInfo) {
				throw ie;
			}
			else {
				throw new ISPACInfo(ie.getMessage(), ie);
			}
        }
        catch (Exception e)
        {
			throw new ISPACInfo("exception.entities.deleteField.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
			if(bCommit){
				 if(fieldToRemove.isDocumentarySearch()){
			    		documentarySearch(false, mcontext.getConnection(),fieldToRemove.getPhysicalName(), tblName);
			    	}
			}
		}

	}

	/**
	 * Modificar un campo de la entidad.
	 *
	 * @param entityId Identificador de la entidad.
	 * @param field Campo a modificar.
	 * @throws ISPACException
	 */
	public void saveField(int entityId, EntityField field)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
		EntityField fieldAnt = null;
		String tblName = null;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	// Entidad para la que se va a modificar un campo
        	CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        	tblName = ctEntityDAO.getName();

        	// Obtener la definición de la entidad
        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        	EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

        	// Campo anterior sin las modificaciones
        	fieldAnt = entityDefinition.findField(field.getId());

        	// Actualizar la definición de la entidad y la fecha de última actualización
        	entityDefinition.saveField(field);
        	ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        	ctEntityDAO.set("FECHA", new Date());
        	ctEntityDAO.store(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
			throw new ISPACInfo("exception.entities.saveField.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
        }

     // Campo con búsqueda documental
        if (fieldAnt.isDocumentarySearch() != field.isDocumentarySearch()) {

    		documentarySearch(field.getDocumentarySearch(), mcontext.getConnection(), field.getPhysicalName(), tblName);
    	}

	}

	/**
	 * En los casos en lo que la conexión sea sql server será necesario comprobar que se tiene incorporado
	 * la busqueda documental sobre la tabla
	 * @param cnt
	 * @param nameTable
	 * @return Cierto si tenemos que añadir la busqueda documental a la tabla o falso en caso contrario.
	 * @throws Exception
	 */
	private boolean needCreateFullTextInTable(DbCnt cnt, String nameTable) throws Exception {

		String sql=DBUtil.CheckFullTextInTable(cnt);
		if(StringUtils.isEmpty(sql)){
			return false;
		}
		else{
			/*TABLE_OWNER 	sysname  	Table owner. This is the name of the database user that created the table.
			 TABLE_NAME 	sysname 	Table name.
			FULLTEXT_KEY_INDEX_NAME 	sysname 	Index imposing the UNIQUE constraint on the column designated as the unique key column.
			FULLTEXT_KEY_COLID 	integer 	Column ID of the unique index identified by FULLTEXT_KEY_NAME.
			FULLTEXT_INDEX_ACTIVE 	integer 	Specifies whether columns marked for full-text indexing in this table are eligible for queries:
			0 = Inactive
			1 = Active
			FULLTEXT_CATALOG_NAME*/
		    if(cnt.execute(sql)){
		    	DbResultSet mdbrs = null;

		    	try {
					mdbrs=cnt.executeQuery(sql);
					if(mdbrs!=null){
						ResultSet resultSet = mdbrs.getResultSet();
						while(resultSet!=null && resultSet.next() ) {
							String nombre = resultSet.getString("TABLE_NAME");
							if(StringUtils.equalsIgnoreCase(nombre, nameTable)){
								return false;
							}
						}
					}
		    	} finally {
		    		if (mdbrs != null) {
		    			mdbrs.close();
		    		}
		    	}
		    }
		}

		return true;

	}
	/**
	 *
	 * @param activar Indica si hay que crear  o borrar la busqueda documental
	 * @param cnt Conexión sobre la que hay que trabajar
	 * @param nameField Nombre del campos sobre el que queremos establecer la búsqueda documental
	 * @param nameTable Nombre de la tabla
	 * @throws ISPACException
	 */
	private void documentarySearch(boolean activar, DbCnt cnt,  String  nameField, String nameTable) throws ISPACException{
		String[] sentencias=null;
		try {
		if(activar){

				if(needCreateFullTextInTable(cnt, nameTable)){
					cnt.openTX();
					cnt.execute("CREATE UNIQUE INDEX ID_TS ON "+ nameTable +" (ID)");
					cnt.closeTX(true);
					sentencias=DBUtil.activateSearchInTable(cnt,nameField, nameTable);


					}
				else{
					sentencias=DBUtil.addDocumentarySearch(cnt, nameField,nameTable);
		}
		}

		else{
			sentencias=DBUtil.deleteDocumentarySearch(cnt,nameField, nameTable);
		}

		for(int i=0; i<sentencias.length; i++){

			cnt.execute(sentencias[i]);

		}


		} catch (Exception e) {

			throw new ISPACInfo("exception.entities.documentarySearch.error", new Object[] {e.getMessage()});
		}finally{
			if (mcontext.ongoingTX()) {
				mcontext.endTX(false);
			}
		}
	}


	/**
	 * Activa la busqueda documental
	 * @param cnt Conexión sobre la que hay que trabajar
	 * @param nameField Nombre del campos sobre el que queremos establecer la búsqueda documental
	 * @param nameTable Nombre de la tabla
	 * @throws ISPACException
	 * @throws ISPACException
	 */
	public void activateDocumentarySearch (DbCnt cnt,  String  nameField, String nameTable) throws ISPACException{
		String[] sentencias=null;
		boolean bCommit=false;
		try {
			sentencias=DBUtil.addDocumentarySearch(cnt,nameField, nameTable);
			cnt.openTX();
			for(int i=0; i<sentencias.length; i++){
				cnt.execute(sentencias[i]);
			}
		bCommit=true;
		} catch (ISPACException e) {
			bCommit=false;
			e.printStackTrace();
		}finally{
			cnt.closeTX(bCommit);
		}

	}

	/**
	 * Añadir un campo a la entidad.
	 *
	 * @param entityId Identificador de la entidad.
	 * @param newField Campo nuevo a añadir.
	 * @param createResources Indicador para crear los recursos asociados al campo.
	 * @throws ISPACException
	 * @return Identificador del nuevo campo añadido.
	 */
	public int addField(int entityId, EntityField newField, boolean createResources)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
		String tblName = null;
		int id = 0;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	// Entidad a la que se va añadir un nuevo campo
        	CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        	tblName = ctEntityDAO.getName();

        	/*
        	DbTableFns.dropTable(cnt, tblName);
        	createTable(cnt, tblName, entityDefinition.getFields(), entityDefinition);
        	*/
        	EntityTableManager entityTableManager = new EntityTableManager();

        	// Si el campo es multivalor, se crea la tabla para campos multivalor, si corresponde.
        	if (newField.isMultivalue()) {
        		entityTableManager.createMultiValueTable(cnt, tblName, newField);
        	}
        	else {
        		entityTableManager.addField(cnt, tblName, entityTableManager.newDbColDef(newField));
        	}

        	// Obtener la definición de la entidad
        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        	EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

        	entityDefinition.addField(newField);
        	newField=(EntityField) entityDefinition.getFields().get(entityDefinition.getFields().size()-1);
        	id = newField.getId();

        	// Actualizar la definición de la entidad y la fecha de última actualización
        	ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        	ctEntityDAO.set("FECHA", new Date());
        	ctEntityDAO.store(cnt);

        	if (createResources) {

        		// Los recursos se crean para todos los idiomas
		    	String[] languages = ConfigurationMgr.getLanguages(mcontext);

				// Recurso para el nombre de la entidad de validación
				for (int i = 0; i < languages.length; i++) {

					// Generar el recurso asociado al field
		        	EntityResourceDAO entityResource = new EntityResourceDAO(cnt);

		        	entityResource.createNew(cnt);
		            entityResource.set("ID_ENT", entityId);
		            entityResource.set("IDIOMA", languages[i]);
		            entityResource.set("CLAVE", newField.getPhysicalName().toUpperCase());
		            entityResource.set("VALOR", newField.getLogicalName());

		            entityResource.store(cnt);
				}
        	}

			// Si todo ha sido correcto se hace commit de la transacción
        	bCommit = true;
        }
        catch (Exception e)
        {
			throw new ISPACInfo("exception.entities.addField.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
        }

        // Si es un campo con búsqueda documental,
        // para generar el índice de la búsqueda documental es necesario que
        // el campo ya esté creado por lo que se hace fuera de la transacción
        if (newField.isDocumentarySearch()) {

    		documentarySearch(true, mcontext.getConnection(), newField.getPhysicalName(), tblName);
    	}

        return id;
	}

    public void untransactionalAddIndex(DbCnt cnt, int entityId, EntityIndex newIndex)
    throws Exception
    {
    	EntityTableManager entityTableManager = new EntityTableManager();

        //crear indice
        CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        String tblName = ctEntityDAO.getName();

        String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

        DbIndexDefinition indexDef = entityTableManager.newDbIndexDef(entityDefinition.getFields(), newIndex);
        entityTableManager.createIndex(cnt, tblName, indexDef);

        //actualizar la entidad
        entityDefinition.addIndex(newIndex);
        ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        ctEntityDAO.store(cnt);
    }

    public void addIndex(int entityId, EntityIndex newIndex)
    throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

            untransactionalAddIndex(cnt, entityId, newIndex);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
        	throw new ISPACInfo("exception.entities.addIndex.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

    public void updateIndex(int entityId, EntityIndex indexToUpdate)
    throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

            untransactionalDropIndex(cnt, entityId, indexToUpdate);
            untransactionalAddIndex(cnt, entityId, indexToUpdate);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
        	throw new ISPACInfo("exception.entities.updateIndex.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
        }
    }

    private EntityDef untransactionalDropIndex(DbCnt cnt, int entityId, EntityIndex indexToDrop)
    throws Exception
    {
    	EntityTableManager entityTableManager = new EntityTableManager();

        //anadir columna a la tabla
        CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        String tblName = ctEntityDAO.getName();

        entityTableManager.dropIndex(cnt, tblName, indexToDrop.getName());

        //actualizar la entidad
        String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);
        entityDefinition.removeIndex(indexToDrop);

        recalcularIds(entityDefinition.getIndexes());

        ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        ctEntityDAO.store(cnt);

        return entityDefinition;
    }

	public EntityDef dropIndex(int entityId, EntityIndex indexToDrop)
	throws ISPACException
	{
        EntityDef entityDefinition = null;

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	entityDefinition = untransactionalDropIndex(cnt, entityId, indexToDrop);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
        	throw new ISPACInfo("exception.entities.deleteIndex.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}

        return entityDefinition;
	}

	public void addValidation(int entityId, EntityValidation newValidation)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	//anadir columna a la tabla
        	CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);

        	//actualizar la entidad
        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        	EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

            // comprobar que la validacion no exista
            List validations =  entityDefinition.getValidations();
            if (!ieci.tdw.ispac.ispaclib.utils.CollectionUtils.isEmpty(validations)){
                for (Iterator itValidations = validations.iterator(); itValidations.hasNext();) {

                    EntityValidation aValidation = (EntityValidation) itValidations.next();
                    if (aValidation.getFieldId()==newValidation.getFieldId()) {
                        throw new ISPACInfo("exception.entities.column.validationExists");
                    }
                }
            }

            entityDefinition.addValidation(newValidation);
        	ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
        	ctEntityDAO.store(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (ISPACException ie)
        {
			if (ie instanceof ISPACInfo) {
				throw ie;
			}
			else {
				throw new ISPACInfo(ie.getMessage(), ie);
			}
        }
        catch (Exception e)
        {
        	throw new ISPACInfo("exception.entities.addValidation.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public EntityDef dropValidation(int entityId, EntityValidation validationToDrop)
	throws ISPACException
	{
        EntityDef entityDef = null;

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	//anadir columna a la tabla
        	CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);

            //actualizar la entidad
        	String xmlEntityDefinition = ctEntityDAO.getString("DEFINICION");
        	entityDef = EntityDef.parseEntityDef(xmlEntityDefinition);
        	entityDef.removeValidation(validationToDrop);

        	recalcularIds(entityDef.getValidations());

        	ctEntityDAO.set("DEFINICION", entityDef.getXmlValues());
        	ctEntityDAO.store(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
        	throw new ISPACInfo("exception.entities.deleteValidation.error", new Object[] {e.getMessage()});
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}

        return entityDef;
	}

	private void generateEntityResources(DbCnt cnt,
									     int entityId,
									     String physicalName,
									     String logicalName,
									     EntityDef definition,
									     String[] languages) throws ISPACException {

		EntityResourceDAO entityResource = null;

		// Recurso para el nombre de la entidad
		for (int i = 0; i < languages.length; i++) {

			entityResource = new EntityResourceDAO(cnt);
			entityResource.createNew(cnt);
	        entityResource.set("ID_ENT", entityId);
	        entityResource.set("IDIOMA", languages[i]);
	        entityResource.set("CLAVE", physicalName);
	        entityResource.set("VALOR", logicalName);
	        entityResource.store(cnt);
		}

        // Recursos para los campos de la entidad
        List fields = definition.getFields();
        Iterator it = fields.iterator();
        while (it.hasNext()) {

            EntityField entityField = (EntityField) it.next();
            String physicalNameField = entityField.getPhysicalName();

            if ((!physicalNameField.equals(ICatalogAPI.ID_FIELD_NAME)) &&
                (!physicalNameField.equals(ICatalogAPI.NUMEXP_FIELD_NAME))) {

        		for (int i = 0; i < languages.length; i++) {

	            	entityResource = new EntityResourceDAO(cnt);
	            	entityResource.createNew(cnt);
	                entityResource.set("ID_ENT", entityId);
	                entityResource.set("IDIOMA", languages[i]);
	                entityResource.set("CLAVE", physicalNameField.toUpperCase());
	                entityResource.set("VALOR", entityField.getLogicalName());
	                entityResource.store(cnt);
        		}
            }
        }
	}

	private String generateEntityForm(DbCnt cnt,
									  CTEntityDAO entityDAO,
			   						  String physicalName,
			   						  EntityDef definition) throws ISPACException
	{
		// Generar el bloque JSP de datos de la entidad
        String jspCode = JSPBuilder.generateDataBlock(cnt, definition, physicalName);

        entityDAO.set("FRM_JSP", jspCode);
        entityDAO.store(cnt);

        return jspCode;
	}

	/**
	 * Regenera el formulario JSP de una entidad.
	 *
	 * @param entityId Identificador de la entidad
	 * @param definition Definición de la entidad
	 * @return El formulario JSP
	 * @throws ISPACException
	 */
    public String regenerateEntityForm(int entityId, EntityDef definition)
    throws ISPACException
    {
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

    	try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

    		// Obtener la definición de la entidad
    		CTEntityDAO entityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);

    		// Regenerar el formulario de la entidad
    		String jspCode = generateEntityForm(cnt, entityDAO, entityDAO.getName(), definition);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;

    		return jspCode;
    	}
    	catch (ISPACException ie)
    	{
    		throw ie;
    	}
    	catch (Exception e)
    	{
    		throw new ISPACException("Error al regenerar el formulario de la entidad", e);
    	}
    	finally
    	{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
    	}
    }

	public void remakeForm(int keyId, EntityDef entityDef, String entityFrmJsp)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

        	// Obtener el formulario
        	CTApplicationDAO application = new CTApplicationDAO(cnt, keyId);

    		// Generar el JSP del formulario
    		List entities = new ArrayList();
    		entities.add(application.getEntPrincipalNombre());
    		List dataBlocks = new ArrayList();
    		dataBlocks.add(entityFrmJsp);

        	// Obtener el XML de los parámetros
 			String parameters = application.getParameters();
 			if (!StringUtils.isEmpty(parameters)) {

 				// Parámetros para las validaciones de sustituto de la entidad principal
 				ParametersDef regenerateXmlParameters = entityDef.generateParametersDef();

 				// Regenerar los parámetros para las entidades agregadas
 				ParametersDef xmlParameters = ParametersDef.parseParametersDef(parameters);
 				List compositeEntities = xmlParameters.getCompositeMultipleRelationEntities();
 				Iterator itComposites = compositeEntities.iterator();
 				while (itComposites.hasNext()) {

 					EntityParameterDef entityParameterDef = (EntityParameterDef) itComposites.next();

 					// Definición de la entidad agregada
 					CTEntityDAO entityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityParameterDef.getTable());

 					// Añadir los parámetros para la entidad agregada y sus propias validaciones de sustituto
 					regenerateXmlParameters.addEntity(entityParameterDef);

 		 			EntityDef secondaryEntityDef = EntityDef.parseEntityDef(entityDAO.getDefinition());
 		 			if (secondaryEntityDef != null) {

 			 			Iterator it = secondaryEntityDef.generateParametersDef().getEntities().iterator();
 			 			while (it.hasNext()) {

 			 				// Añadir el primaryTable a las entidades de sustituto
 			 				// para diferenciar las validaciones de la entidad principal de la de composición
 			 				// cuanda ambas entidades tienen un campo de sustituto validado contra la misma tabla de validación
 			 				regenerateXmlParameters.addEntity((EntityParameterDef) it.next(), entityParameterDef.getTable());
 			 			}
 		 			}

 					// Añadir los formularios de las entidades de composición
 					entities.add(entityDAO.getName());
 					dataBlocks.add(entityDAO.getString("FRM_JSP"));
 				}

 				// Parámetros regenerados
 				parameters = regenerateXmlParameters.toXml();
 			}
 			else {
 				// Parámetros para las validaciones de sustituto de la entidad
 				parameters = entityDef.generateXmlParameters();
 			}
 			boolean generateTabDocumentos=false;
    		if( application.get("DOCUMENTOS")!=null &&  CTApplicationDAO.DOCUMENTS_ACTIVATE.equalsIgnoreCase(application.get("DOCUMENTOS").toString()))
    		{
    			generateTabDocumentos=true;
    			dataBlocks.add(JSPBuilder.getBlockDocuments(dataBlocks.size()));
    		}


    		application.set("PARAMETROS", parameters);

 			String jspCode = JSPBuilder.generateTabsDataBlocks(entities, dataBlocks, generateTabDocumentos);
 			application.set("FRM_JSP", jspCode);

 			// Establecer la versión del formulario
			int version = 1;
			String sVersion = application.getString("FRM_VERSION");
			if (!StringUtils.isEmpty(sVersion)) {
				version += Integer.parseInt(sVersion);
			}
 			application.set("FRM_VERSION", version);

        	application.store(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
        }
        catch (Exception e)
        {
        	throw new ISPACException("Error en CatalogAPI:remakeForm("+ keyId +", entityFrmJsp)", e);
        }
        finally
        {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
     * Incrementa el orden de un productor al siguiente orden existente y
     * decrementa el sustituido
     *
     * @param id Identificador del productor dentro del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public void incOrderPcdProducer(int id)
	throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			IItem origPcdProd = getCTEntity(ICatalogAPI.ENTITY_CT_PCDORG, id);
			if (origPcdProd != null) {

				IItem destPcdProd = getPrevPcdProducer(
						cnt,
						origPcdProd.getString("COD_PCD"),
						origPcdProd.getInt("ORDEN"));

		        if (destPcdProd !=null) {

		        	int origOrder = origPcdProd.getInt("ORDEN");
		        	origPcdProd.set("ORDEN", destPcdProd.getInt("ORDEN"));
		        	destPcdProd.set("ORDEN", origOrder);

		        	origPcdProd.store(mcontext);
		        	destPcdProd.store(mcontext);
		        }
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (ISPACException e)
	    {
			throw new ISPACException("Error al incrementar el orden de un productor", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
     * Decrementa el orden de un productor al anterior orden existente e
     * incrementa el sustituido
     *
     * @param id Identificador del productor dentro del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public void decOrderPcdProducer(int id)
	throws ISPACException
	{
		// Ejecución de las reglas en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

	    try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

			IItem origPcdProd = getCTEntity(ICatalogAPI.ENTITY_CT_PCDORG, id);
			if (origPcdProd != null) {

				IItem destPcdProd = getNextPcdProducer(
						cnt,
						origPcdProd.getString("COD_PCD"),
						origPcdProd.getInt("ORDEN"));

		        if (destPcdProd !=null) {

		        	int origOrder = origPcdProd.getInt("ORDEN");
		        	origPcdProd.set("ORDEN", destPcdProd.getInt("ORDEN"));
		        	destPcdProd.set("ORDEN", origOrder);

		        	origPcdProd.store(mcontext);
		        	destPcdProd.store(mcontext);
		        }
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (ISPACException e)
	    {
			throw new ISPACException("Error al decrementar el orden de un productor", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public static IItem getPrevPcdProducer(DbCnt cnt, String codPcd, int order)
	throws ISPACException
	{
		IItem producer = null;

	    String sql = new StringBuffer("WHERE COD_PCD = '").append(DBUtil.replaceQuotes(codPcd)).append("'")
	    			.append(" AND ORDEN <= ").append(order - 1)
	    			.append(" ORDER BY ORDEN DESC").toString();

		CollectionDAO col = EntityFactoryDAO.getInstance().queryEntities(cnt,
				SpacEntities.SPAC_CT_PCDORG, sql);
	    if (col.next()) {
	        producer = col.value();
	    }

	    return producer;
	}

	public static IItem getNextPcdProducer(DbCnt cnt, String codPcd, int order)
	throws ISPACException
	{
		IItem producer = null;

		String sql = new StringBuffer("WHERE COD_PCD = '").append(DBUtil.replaceQuotes(codPcd)).append("'")
					.append(" AND ORDEN >= ").append(order + 1)
					.append(" ORDER BY ORDEN ASC").toString();

		CollectionDAO col = EntityFactoryDAO.getInstance().queryEntities(cnt,
				SpacEntities.SPAC_CT_PCDORG, sql);
		if (col.next()) {
			producer = col.value();
		}

		return producer;
	}

	public IItemCollection getProcedureEntities(int pcdId)
	throws ISPACException
	{
		HashMap entidades=new HashMap();
		entidades.put("CTENTITY",new Integer(ICatalogAPI.ENTITY_CT_ENTITY));
		entidades.put("PENTITIES", new Integer(ICatalogAPI.ENTITY_P_ENTITY));

		String sqlquery = new StringBuffer()
			.append("WHERE CTENTITY.ID = PENTITIES.ID_ENT AND PENTITIES.ID_PCD = ")
			.append(pcdId)
			// La entidad del trámite no se muestra
			//.append(" AND CTENTITY.ID <> ")
			//.append(SpacEntities.SPAC_DT_TRAMITES)
			.append(" ORDER BY PENTITIES.ORDEN")
			.toString();

        IItemCollection itemcol =
        	queryCTEntities(entidades,sqlquery);

        return itemcol;
	}

	public List getProcedureEntitiesForm(int pcdId)
	throws ISPACException
	{
		// Obtener la lista de entidades del procedimiento
		IItemCollection pcdEntities = getProcedureEntities(pcdId);
		if (pcdEntities.next()) {

			// Obtener los formularios
			Map mapForms = null;
			Map mapRuleForms = null;
			Map mapRuleVisible = null;
			Map formIds = new HashMap();

			// Obtener las reglas para asignar formulario
			Map ruleFormIds = new HashMap();

			// Obtener las reglas para asignar la visibilidad de la entidad
			Map ruleVisibleIds = new HashMap();

			while (pcdEntities.next()) {

				IItem pcdEntity = (IItem) pcdEntities.value();

				// Formulario en el procedimiento
				String sFormId = pcdEntity.getString("PENTITIES:FRM_EDIT");
				if (StringUtils.isEmpty(sFormId)) {

					// Formulario por defecto en la entidad
					sFormId = pcdEntity.getString("CTENTITY:FRM_EDIT");
				}

				if (StringUtils.isNotEmpty(sFormId)) {

					int formId = Integer.valueOf(sFormId).intValue();
					if (formId > 0) {
						formIds.put(new Integer(formId), null);
					}
				}

				// Regla que asigna el formulario a nivel de procedimiento
				String sRuleFormId = pcdEntity.getString("PENTITIES:ID_RULE_FRM");
				if (StringUtils.isNotEmpty(sRuleFormId)) {
					ruleFormIds.put(Integer.valueOf(sRuleFormId), null);
				}

				// Regla que asigna la visibilidad de la entidad a nivel de procedimiento
				String sRuleVisibleId = pcdEntity.getString("PENTITIES:ID_RULE_VISIBLE");
				if (StringUtils.isNotEmpty(sRuleVisibleId)) {

					ruleVisibleIds.put(Integer.valueOf(sRuleVisibleId), null);
				}
			}

			mapForms = getCTEntityByIds(CatalogAPI.ENTITY_CT_APP, formIds).toMap();

			if (!ruleFormIds.isEmpty()) {
				// Obtener las reglas asociadas a los identificadores
				mapRuleForms = getCTEntityByIds(CatalogAPI.ENTITY_CT_RULE, ruleFormIds).toMapStringKey();
			}

			if (!ruleVisibleIds.isEmpty()) {
				// Obtener las reglas asociadas a los identificadores
				mapRuleVisible = getCTEntityByIds(CatalogAPI.ENTITY_CT_RULE, ruleVisibleIds).toMapStringKey();
			}

			// La entidad del trámite se muestra la última
			int index = 0;
			int iEntityTask = 0;
			ItemBean entityTask = null;

			// Establecer los formularios para las entidades del procedimiento
			List entitiesFormBeanList = CollectionBean.getBeanList(pcdEntities);
   			for (Iterator iter = entitiesFormBeanList.iterator(); iter.hasNext();) {

				ItemBean entityForm = (ItemBean) iter.next();

				// Obtener la entidad del trámite para mostrarla la última
				if (Integer.parseInt(entityForm.getString("CTENTITY:ID")) == SpacEntities.SPAC_DT_TRAMITES) {

					iEntityTask = index;
					entityTask = entityForm;
				}

			    Object formId = entityForm.getProperty("PENTITIES:FRM_EDIT");
			    if (formId != null) {

			    	int iFormId = Integer.parseInt(formId.toString());
			    	if (iFormId == 0) {

						// Formulario no visible
						entityForm.setProperty("NOMBRE_APP_KEY", "procedure.entity.form.noVisible");
						entityForm.setProperty("NO_VISIBLE", Boolean.TRUE);
			    	}
			    	else {
				    	// Formulario de entidad asignado
				    	entityForm.setProperty("NOMBRE_APP", ( (IItem) mapForms.get(formId)).get("NOMBRE"));
			    	}
			    }
			    else {
				    Object ruleFormId = entityForm.getProperty("PENTITIES:ID_RULE_FRM");
				    if (ruleFormId != null) {

				    	// Regla asignada
				    	entityForm.setProperty("NOMBRE_APP", ( (IItem) mapRuleForms.get(ruleFormId.toString())).get("NOMBRE"));
				    	entityForm.setProperty("KEY", "procedure.entity.form.rule");
				    } else {

					    formId = entityForm.getProperty("CTENTITY:FRM_EDIT");
					    if (formId != null) {

					    	// Formulario por defecto en la entidad
					    	entityForm.setProperty("NOMBRE_APP", ( (IItem) mapForms.get(formId)).get("NOMBRE"));
					    	entityForm.setProperty("NOMBRE_APP_KEY", "procedure.entity.form.default");
					    }
					    /*
					    else {
					    	// La entidad no tiene formulario por defecto
					    }
					    */
				    }
			    }

				// Visibilidad de la entidad
			    Object ruleVisibleId = entityForm.getProperty("PENTITIES:ID_RULE_VISIBLE");
				if (ruleVisibleId != null) {

					// Regla asignada
					entityForm.setProperty("NOMBRE_VISIBLE_RULE", ( (IItem) mapRuleVisible.get(ruleVisibleId.toString())).get("NOMBRE"));
				}

			    index++;
			}

   			// Mover la entidad del trámite al final de la lista
   			if (entityTask != null) {
	   			entitiesFormBeanList.remove(iEntityTask);
	   			entitiesFormBeanList.add(entityTask);
   			}

   			return entitiesFormBeanList;
		}

        return null;
	}

	public List getSubprocedureEntitiesForm(int subPcdId)
	throws ISPACException
	{
		// Obtener la lista de entidades del subproceso
		IItemCollection pcdEntities = getProcedureEntities(subPcdId);
		if (pcdEntities.next()) {

			// Obtener los formularios
			Map mapForms = null;
			Map formIds = new HashMap();

			while (pcdEntities.next()) {

				IItem pcdEntity = (IItem) pcdEntities.value();

				// Formulario en el subproceso
				String formId = pcdEntity.getString("PENTITIES:FRM_EDIT");
				if (!StringUtils.isEmpty(formId)) {

					formIds.put(Integer.valueOf(formId), null);
				}
			}

			if (!formIds.isEmpty()) {
				mapForms = getCTEntityByIds(CatalogAPI.ENTITY_CT_APP, formIds).toMap();
			}

			// Establecer los formularios para las entidades del subproceso
			List entitiesFormBeanList = CollectionBean.getBeanList(pcdEntities);
   			for (Iterator iter = entitiesFormBeanList.iterator(); iter.hasNext();) {

				ItemBean entityForm = (ItemBean) iter.next();

			    Object formId = entityForm.getProperty("PENTITIES:FRM_EDIT");
			    if (formId != null) {

			    	// Formulario asignado
			    	entityForm.setProperty("NOMBRE_APP", ( (IItem) mapForms.get(formId)).get("NOMBRE"));
			    }
			    else {
			    	// Formulario por defecto en el subproceso
			    	entityForm.setProperty("NOMBRE_APP_KEY", "procedure.entity.form.procedure");
			    }
			}

   			return entitiesFormBeanList;
		}

        return null;
	}

	public IItem getProcedureEntity(int entityId)
	throws ISPACException
	{
		HashMap entidades=new HashMap();
		entidades.put("CTENTITY",new Integer(ICatalogAPI.ENTITY_CT_ENTITY));
		entidades.put("PENTITIES", new Integer(ICatalogAPI.ENTITY_P_ENTITY));

		String sqlquery = new StringBuffer()
			.append("WHERE CTENTITY.ID=PENTITIES.ID_ENT AND CTENTITY.ID=")
			.append(entityId)
			.toString();

        IItemCollection itemcol =
        	queryCTEntities(entidades,sqlquery);
        if (itemcol.next())
        	return (IItem)itemcol.iterator().next();

        return null;
	}

    public IItemCollection getHierarchicalTables(String query)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

		try
		{
			CollectionDAO collection = new CollectionDAO(CTHierarchyDAO.class);
			collection.query(cnt, query);
			return collection.disconnect();

		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en CatalogAPI:getHierarchicalTables()", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
    }

    public void createHierarchicalTable(String name,
									    String description,
									    int entidadPadreId,
									    int entidadHijaId,
									    boolean createTable) throws ISPACException
	{



		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

    	try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

    		// Crear la jerarquía
        	CTHierarchyDAO hierarchyDAO = new CTHierarchyDAO(cnt);
        	hierarchyDAO.createNew(cnt);
        	hierarchyDAO.set("NOMBRE", name);
        	hierarchyDAO.set("DESCRIPCION", description);
        	hierarchyDAO.set("TIPO", (createTable ? HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE : HierarchicalTablesConstants.HIERARCHICAL_TABLE_VIEW_TYPE));
        	hierarchyDAO.set("ID_ENTIDAD_PADRE", entidadPadreId);
        	hierarchyDAO.set("ID_ENTIDAD_HIJA", entidadHijaId);
        	hierarchyDAO.store(cnt);

            if (createTable){
            	createPhysicalHierarchicalTable(cnt, hierarchyDAO.getKeyInt());
            }

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
    	}
    	catch (ISPACException ei)
    	{
    		throw ei;
    	}
    	finally
    	{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
    	}
    }


    private void createPhysicalHierarchicalTable(DbCnt cnt,int hierarchicalId) throws ISPACException{
    	EntityTableManager entityTableManager = new EntityTableManager();
    	// Crear la tabla para la jerarquía
        DbColDef[] colsDefs = new DbColDef[3];
        EntityField fieldPk = new EntityField();
        fieldPk.setPhysicalName(ID_FIELD_NAME);
        fieldPk.setType(InternalDataType.LONG_INTEGER);
        colsDefs[0] = entityTableManager.newDbColDef(fieldPk);

        EntityField fieldPadre = new EntityField();
        fieldPadre.setPhysicalName(ID_FIELD_ENTIDAD_PADRE);
        fieldPadre.setType(InternalDataType.LONG_INTEGER);
        colsDefs[1] = entityTableManager.newDbColDef(fieldPadre);

        EntityField fieldHija = new EntityField();
        fieldHija.setPhysicalName(ID_FIELD_ENTIDAD_HIJA);
        fieldHija.setType(InternalDataType.LONG_INTEGER);
        colsDefs[2] = entityTableManager.newDbColDef(fieldHija);

    	//entityTableManager.createTable(cnt, HIERARCHICAL_TABLE_NAME + String.valueOf(hierarchyDAO.getKeyInt()), colsDefs, new String[]{ID_FIELD_ENTIDAD_PADRE, ID_FIELD_ENTIDAD_HIJA});
        String tableName = HIERARCHICAL_TABLE_NAME + String.valueOf(hierarchicalId);
        entityTableManager.createTable(cnt, tableName, colsDefs);
        entityTableManager.createSequence(cnt, tableName, null);
    }

	public void deleteHierarchicalTable(int id, boolean deleteTableBD) throws ISPACException {
    	EntityTableManager entityTableManager = new EntityTableManager();

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

    	try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	DbCnt cnt = mcontext.getConnection();

	    	//Se obtiene la definicion de la jerarquica
	    	CTHierarchyDAO hierarchyDAO = new CTHierarchyDAO(cnt);
	    	hierarchyDAO.set(hierarchyDAO.getKeyName(), id);
	    	hierarchyDAO.load(cnt);
	    	String tblName = ICatalogAPI.HIERARCHICAL_TABLE_NAME+id;

	    	//Se elimina la tabla de la BBDD si corresponde
	    	if (deleteTableBD){
		    	//Se comprueba si es una tabla (no es una vista)
	    		IItemCollection itemcol = getHierarchicalTables("WHERE ID = "+id);
		    	if (!itemcol.next()){
		    		throw new ISPACException("Tabla jerárquica con id '"+id+"' no encontrada");
		    	}
		    	int type = itemcol.value().getInt("TIPO");
	    		if (type == HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE){
	    			entityTableManager.dropTable(cnt, tblName);
	    			entityTableManager.deleteSequence(cnt, IdSequenceMgr.SEQUENCE_NAME_PREFIX + Math.abs(tblName.hashCode()));
	    		}
	    	}

	    	//Se elimina la definicion (registro)
	    	hierarchyDAO.delete(cnt);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
    	}catch (Exception e){
    		throw new ISPACException(e);
    	}finally{
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
    	}
	}

	public IItemCollection getHierarchicalTableValues(int hierarchicalId, String query)
	throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

		try{
			CollectionDAO collection = HierarchyValueDAO.getValues(cnt, hierarchicalId, query);

			return collection.disconnect();

		}catch (Exception e){
			throw new ISPACException("Error en CatalogAPI:getHierarchicalTableValues(" + hierarchicalId + ", "+ query + ")", e);
		}finally{
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean deleteHierarchicalTableValues(String tableName,String[] values) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();
		try{
			HierarchyValueDAO dao =  new HierarchyValueDAO();
			return dao.deleteValues(cnt, tableName, values);

		}catch (Exception e){
			throw new ISPACException("Error en CatalogAPI:deleteHierarchicalTableValues(" + tableName + ", "+ values + ")", e);
		}finally{
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean deleteHierarchicalTableValues(String tableName, List parentIds,
			List descendantIds) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();
		try{
			HierarchyValueDAO dao =  new HierarchyValueDAO();
			return dao.deleteValues(cnt, tableName, parentIds, descendantIds);

		}catch (Exception e){
			throw new ISPACException("Error en CatalogAPI:deleteHierarchicalTableValues(" + tableName + ", parentIds, descendantIds)", e);
		}finally{
			mcontext.releaseConnection(cnt);
		}


	}

//	public boolean addHierarchicalTableValue(String tableName, int parentValue,
//			int descendantValue) throws ISPACException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public void addHierarchicalTableValues(String tableName, List parentIds,
//			List descendantIds) throws ISPACException {
//		// TODO Auto-generated method stub
//
//	}

	public boolean addHierarchicalTableValue(String tableName,
			int parentValue, int descendantValue) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();

		try {
			HierarchyValueDAO dao =  new HierarchyValueDAO();
			dao.setTableName(tableName);
			dao.createNew(cnt);
			dao.set("ID_PADRE", parentValue);
			dao.set("ID_HIJA", descendantValue);
			dao.store(cnt);
		}catch (Exception e){
			throw new ISPACException(e);
		}finally{
   			mcontext.releaseConnection(cnt);
   		}

		return false;
	}

	public boolean addHierarchicalTableValues(String tableName,List parentIds, List descendantIds) throws ISPACException {
		DbCnt cnt = mcontext.getConnection();
		try {
			HierarchyValueDAO dao =  null;
			for (Iterator iterator = parentIds.iterator(); iterator.hasNext();) {
				Integer parentId = (Integer) iterator.next();
				for (Iterator iterator2 = descendantIds.iterator(); iterator2.hasNext();) {
					dao =  new HierarchyValueDAO();
					dao.setTableName(tableName);
					Integer descendantId = (Integer) iterator2.next();
					dao.createNew(cnt);
					dao.set("ID_PADRE", parentId);
					dao.set("ID_HIJA", descendantId);
					dao.store(cnt);
				}
			}
		}catch (Exception e){
			throw new ISPACException(e);
		}finally{
   			mcontext.releaseConnection(cnt);
   		}
		return false;
	}

    public IItemCollection getHierarchicalDescendantValues(int hierarchicalId, String codeParent) throws ISPACException
    {
    	IItemCollection itemcol=null;
    	DbCnt cnt=null;

    	try{
    		cnt = mcontext.getConnection();
        	IEntitiesAPI entitiesAPI=mcontext.getAPI().getEntitiesAPI();
	    	itemcol=getHierarchicalTables("WHERE ID="+hierarchicalId);
	    	if (!itemcol.next()){
	    		if(logger.isDebugEnabled()){
	    			logger.debug("Tabla jerárquica con id '"+hierarchicalId+"' no encontrada");
	    		}
	    		throw new ISPACException("Tabla jerárquica con id '"+hierarchicalId+"' no encontrada");
			}
	    	IItem item = itemcol.value();
	    	int id_entidad_padre= item.getInt("ID_ENTIDAD_PADRE");
		    int id_entidad_hija=item.getInt("ID_ENTIDAD_HIJA");

		   //Obtener el id del registro que tenga por valor el código recibido
		   itemcol=entitiesAPI.queryEntities(id_entidad_padre, "WHERE "+ ICatalogAPI.VALOR_FIELD_NAME+"='"+DBUtil.replaceQuotes(codeParent)+"'");
			if (!itemcol.next()){
	    		if(logger.isDebugEnabled()){
	    			logger.debug("No existe el valor '"+codeParent+"' para la entidad con id:"+id_entidad_padre);
	    		}
	    		throw new ISPACException("Tabla jerárquica con id '"+hierarchicalId+"' no encontrada");
			}
		    int parentValue=itemcol.value().getKeyInt();
			//Obtenemos los registros que satisfacen la relación jerárquica.
			 itemcol= entitiesAPI.queryEntities(id_entidad_hija, " WHERE ID IN (SELECT ID_HIJA FROM SPAC_CT_JERARQUIA_"+hierarchicalId+
					    														" WHERE ID_PADRE="+parentValue+") AND VIGENTE=1" );
		    }catch (Exception e){
				throw new ISPACException(e);
			}finally{
					mcontext.releaseConnection(cnt);
			}
    	return itemcol;

    }
	public List getStageEntities(int pcdId, int stageId, String keyDefine)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try {
			// Obtener la lista de entidades del procedimiento
			IItemCollection pcdEntities = getProcedureEntities(pcdId);
			if (pcdEntities.next()) {

				// Formularios a nivel de fase
				IItemCollection stageEntitiesForm = PFrmFaseDAO.getStageEntities(cnt, stageId);

				// Entidades con sus formularios a nivel de fase
				return getStageTaskEntitiesForm(pcdEntities, stageEntitiesForm, keyDefine);
			}

			return null;
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
   			mcontext.releaseConnection(cnt);
   		}
	}

	public List getTaskEntities(int pcdId, int taskId)
	throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();

		try {
			// Obtener la lista de entidades del procedimiento
			IItemCollection pcdEntities = getProcedureEntities(pcdId);
			if (pcdEntities.next()) {

				// Formularios a nivel de trámite
				IItemCollection taskEntitiesForm = PFrmTramiteDAO.getTaskEntities(cnt, taskId);

				// Entidades con sus formularios a nivel de trámite
				return getStageTaskEntitiesForm(pcdEntities, taskEntitiesForm, "procedure.entity.form.stage");
			}

			return null;
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
   			mcontext.releaseConnection(cnt);
   		}
	}

	/**
	 *
	 * @param pcdEntities
	 * @param elementEntitiesForm
	 * @param keyDefine
	 * @return
	 * @throws ISPACException
	 */
	private List getStageTaskEntitiesForm(IItemCollection pcdEntities,
										  IItemCollection elementEntitiesForm,
										  String keyDefine) throws ISPACException
	{
		Map mapForms = null;
		Map mapRuleForms = null;
		Map mapRuleVisible = null;
		Map mapEntityIdsForm = new HashMap();
		Map mapEntityIdsRuleForm = new HashMap();
		Map mapEntityIdsRuleVisible = new HashMap();
		Map mapEntityIdsElementForm = new HashMap();

		if (elementEntitiesForm.next()) {

			// Obtener los formularios
			Map formIds = new HashMap();

			// Obtener las reglas para asignar formulario
			Map ruleFormIds = new HashMap();

			// Obtener las reglas para asignar la visibilidad de la entidad
			Map ruleVisibleIds = new HashMap();

			while (elementEntitiesForm.next()) {

				IItem elementEntityForm = (IItem) elementEntitiesForm.value();
				String entityId = elementEntityForm.getString("ID_ENT");

				// Formulario a nivel de fase o trámite
				String sFormId = elementEntityForm.getString("FRM_EDIT");
				if (StringUtils.isNotEmpty(sFormId)) {

					int formId = Integer.parseInt(sFormId);
					if (formId > 0) {
						formIds.put(new Integer(formId), null);
					}

					mapEntityIdsForm.put(entityId, new Integer(formId));
				}

				// Regla que asigna el formulario a nivel de fase o trámite
				String sRuleFormId = elementEntityForm.getString("ID_RULE_FRM");
				if (StringUtils.isNotEmpty(sRuleFormId)) {

					ruleFormIds.put(Integer.valueOf(sRuleFormId), null);
					mapEntityIdsRuleForm.put(entityId, Integer.valueOf(sRuleFormId));
				}

				// Regla que asigna la visibilidad de la entidad a nivel de fase o trámite
				String sRuleVisibleId = elementEntityForm.getString("ID_RULE_VISIBLE");
				if (StringUtils.isNotEmpty(sRuleVisibleId)) {

					ruleVisibleIds.put(Integer.valueOf(sRuleVisibleId), null);
					mapEntityIdsRuleVisible.put(entityId, Integer.valueOf(sRuleVisibleId));
				}

				mapEntityIdsElementForm.put(entityId, elementEntityForm);
			}

			if (!formIds.isEmpty()) {
				// Obtener los formularios asociados a los identificadores
				mapForms = getCTEntityByIds(CatalogAPI.ENTITY_CT_APP, formIds).toMapStringKey();
			}

			if (!ruleFormIds.isEmpty()) {
				// Obtener las reglas asociadas a los identificadores
				mapRuleForms = getCTEntityByIds(CatalogAPI.ENTITY_CT_RULE, ruleFormIds).toMapStringKey();
			}

			if (!ruleVisibleIds.isEmpty()) {
				// Obtener las reglas asociadas a los identificadores
				mapRuleVisible = getCTEntityByIds(CatalogAPI.ENTITY_CT_RULE, ruleVisibleIds).toMapStringKey();
			}
		}

		// La entidad del trámite se muestra la última
		int index = 0;
		int iEntityTask = 0;
		ItemBean entityTask = null;

		// Establecer los formularios para las entidades del procedimiento
		List entitiesFormBeanList = CollectionBean.getBeanList(pcdEntities);
		for (Iterator iter = entitiesFormBeanList.iterator(); iter.hasNext();) {

			ItemBean entityForm = (ItemBean) iter.next();
			String entityId = entityForm.getString("CTENTITY:ID");

			// La entidad del trámite no se muestra
			/*
			if (Integer.parseInt(entityId) == SpacEntities.SPAC_DT_TRAMITES) {
				iter.remove();
			}
			else {
			*/

			// Obtener la entidad del trámite para mostrarla la última
			if (Integer.parseInt(entityId) == SpacEntities.SPAC_DT_TRAMITES) {

				iEntityTask = index;
				entityTask = entityForm;
			}

			IItem elementEntityForm = (IItem) mapEntityIdsElementForm.get(entityId);

			Integer formId = (Integer) mapEntityIdsForm.get(entityId);
			if (formId != null) {

				if (formId.intValue() > 0) {

					// Formulario de entidad asignado
					entityForm.setProperty("NOMBRE_APP", ( (IItem) mapForms.get(formId.toString())).get("NOMBRE"));
				}
				else {
					// Formulario no visible
					entityForm.setProperty("NOMBRE_APP_KEY", "procedure.entity.form.noVisible");
					entityForm.setProperty("NO_VISIBLE", Boolean.TRUE);
				}
			} else {
				Integer ruleFormId = (Integer) mapEntityIdsRuleForm.get(entityId);
				if (ruleFormId != null) {

					// Regla asignada
					entityForm.setProperty("NOMBRE_APP", ( (IItem) mapRuleForms.get(ruleFormId.toString())).get("NOMBRE"));
					entityForm.setProperty("NOMBRE_APP_KEY", "procedure.entity.form.rule");
				} else {
					// Formulario definido en el padre
					entityForm.setProperty("NOMBRE_APP_KEY", keyDefine);
					entityForm.setProperty("ID_P_FRM", String.valueOf(ISPACEntities.ENTITY_NULLREGKEYID));
				}
			}

			// Visibilidad de la entidad
			Integer ruleVisibleId = (Integer) mapEntityIdsRuleVisible.get(entityId);
			if (ruleVisibleId != null) {

				// Regla asignada
				entityForm.setProperty("NOMBRE_VISIBLE_RULE", ( (IItem) mapRuleVisible.get(ruleVisibleId.toString())).get("NOMBRE"));
			}

			// Comprobar formulario en sólo lectura
			if (elementEntityForm == null) {
				entityForm.setProperty("PENTITIES:FRM_READONLY", null);
			}
			else {
				entityForm.setProperty("PENTITIES:FRM_READONLY", elementEntityForm.getString("FRM_READONLY"));
				entityForm.setProperty("ID_P_FRM", elementEntityForm.getKeyInteger());
			}

			//}

			index++;
		}

		// Mover la entidad del trámite al final de la lista
		if (entityTask != null) {
   			entitiesFormBeanList.remove(iEntityTask);
   			entitiesFormBeanList.add(entityTask);
		}

		return entitiesFormBeanList;
	}

    /**
     * Obtiene la información de un procedimiento en el catálogo.
     * @param ctPcdId Identificador del procedimiento en el catálogo
     * @return Información del procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTProcedure(int ctPcdId) throws ISPACException {
		return getCTEntity(ENTITY_CT_PROCEDURE, ctPcdId);
	}

    /**
     * Obtiene la lista de fases del catálogo.
     * @return Lista de fases del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStages()
    throws ISPACException
    {
		return getCTStages(null);
    }

    /**
     * Obtiene la lista de fases del catálogo a partir del nombre.
     * @param name Nombre de la fase.
     * @return Lista de fases del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStages(String name)
    throws ISPACException
    {
        StringBuffer sql = new StringBuffer();

        if (StringUtils.isNotBlank(name)) {
        	sql.append("WHERE NOMBRE LIKE '%")
        		.append(DBUtil.replaceQuotes(name.trim()))
        		.append("%'");
        }

        sql.append(" ORDER BY ORDEN");

		return queryCTEntities(ICatalogAPI.ENTITY_CT_STAGE, sql.toString());
    }

    /**
     * Obtiene la lista de trámites de una fase del catálogo.
     * @param ctStageId Identificador de la fase en el catálogo.
     * @return Lista de trámites del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTStageTasks(int ctStageId)
    throws ISPACException
    {
    	HashMap entidades = new HashMap();
		entidades.put("SPAC_CT_TRAMITES", new Integer(ICatalogAPI.ENTITY_CT_TASK));
		entidades.put("SPAC_CT_FSTR", new Integer(ICatalogAPI.ENTITY_CT_STAGETASK));

		StringBuffer sql = new StringBuffer()
			.append("WHERE SPAC_CT_FSTR.ID_TRAMITE=SPAC_CT_TRAMITES.ID")
			.append(" AND SPAC_CT_FSTR.ID_FASE=")
			.append(ctStageId);

		return queryCTEntities(entidades, sql.toString());
    }

    /**
     * Obtiene la información de una fase en el catálogo.
     * @param ctStageId Identificador de la fase en el catálogo
     * @return Información de la fase.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTStage(int ctStageId) throws ISPACException {
		return getCTEntity(ENTITY_CT_STAGE, ctStageId);
	}

    /**
     * Obtiene la información de un trámite en el catálogo.
     * @param ctTaskId Identificador del trámite en el catálogo
     * @return Información del trámite.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTTask(int ctTaskId) throws ISPACException {
		return getCTEntity(ENTITY_CT_TASK, ctTaskId);
	}

    /**
     * Obtiene la información de un trámite en el catálogo
     * a partir del trámite en el procedimiento.
     * @param taskPcdId Identificador del trámite en el procedimiento.
     * @return Información del trámite.
     * @throws ISPACException si ocurre algún error.
     */
	public IItem getCTTaskPCD(int taskPcdId) throws ISPACException {

		IItem item = null;
		DbCnt cnt = mcontext.getConnection();

		try {
			TableJoinFactoryDAO factory = new TableJoinFactoryDAO();

			factory.addTable("SPAC_CT_TRAMITES", "SPAC_CT_TRAMITES");
			factory.addTable("SPAC_P_TRAMITES", "SPAC_P_TRAMITES");

			String sql = " WHERE SPAC_CT_TRAMITES.ID = SPAC_P_TRAMITES.ID_CTTRAMITE AND SPAC_P_TRAMITES.ID = " + taskPcdId;

			IItemCollection collection = factory.queryTableJoin(cnt, sql).disconnect();
			if (collection.next()) {
				item = collection.value();
			}

			return item;
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en CustomAPI:getCTTaskPCD(" + taskPcdId + ")", ie);
		}
		finally {
			mcontext.releaseConnection(cnt);
		}

	}

    /**
     * Obtiene la lista de reglas del catálogo.
     * @return Lista de reglas del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTRules()
    throws ISPACException
    {
        return queryCTEntities(ENTITY_CT_RULE, "ORDER BY NOMBRE");
    }

    /**
     * Obtiene la lista de reglas del catálogo que contengan una cadena en el nombre.
     * @param pattern Cadena que debe estar contenida en el nombre.
     * @return Lista de reglas del catálogo.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getCTRules(String pattern)
    throws ISPACException
    {
    	String query = "";

    	if (StringUtils.isNotBlank(pattern)) {
    		query += " WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(pattern) + "%'";
    	}

		query += " ORDER BY ID";

		return queryCTEntities(ENTITY_CT_RULE, query);
    }

    /**
     * Obtiene la lista de responsables para los que se ha asignado el formulario de búsqueda.
     * @param searchFormId Identificador del formulario de búsqueda.
     * @return Lista de responsables asignados al formulario de búsqueda.
     * @throws ISPACException si ocurre algún error.
     */
    public List getSearchFormOrganization(int searchFormId)
    throws ISPACException
    {
    	String query = " WHERE ID_SEARCHFRM = " + searchFormId;
        IItemCollection itemcol = queryCTEntities(ICatalogAPI.ENTITY_CT_SEARCHFORM_ORG, query);

		IRespManagerAPI respAPI = mcontext.getAPI().getRespManagerAPI();

		// Obtener los responsables
		IItemCollection permissionObjectOrganizaction = respAPI.getInfoResponsibles(itemcol);
		List permissionObjectOrganizactionList = CollectionBean.getBeanList(permissionObjectOrganizaction);
		Collections.sort(permissionObjectOrganizactionList, Responsible.getRespComparator());

		return permissionObjectOrganizactionList;
    }

    public IItemCollection getSearchForms()
    throws ISPACException
    {
    	  DbCnt cnt = mcontext.getConnection();
  		try {
  			return CTFrmBusquedaDAO.getSearchForms(cnt).disconnect();
  		} finally {
  			mcontext.releaseConnection(cnt);
  		}
    }


    /**
	 * Obtiene las variables de sistema a partir del nombre.
	 *
	 * @param pattern
	 *            Patrón del nombre de la variable de sistema
	 * @return Lista de variables del sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTSystemVars(String pattern) throws ISPACException {

		StringBuffer sql = new StringBuffer();

        if (StringUtils.isNotBlank(pattern)) {
        	sql.append("WHERE NOMBRE LIKE '%")
        		.append(DBUtil.replaceQuotes(pattern.trim()))
        		.append("%'");
        }

        sql.append(" ORDER BY NOMBRE");

		return queryCTEntities(ICatalogAPI.ENTITY_CT_SYSTEM_VARS, sql.toString());
	}

	/**
	 * Obtiene las variables de sistema
	 *
	 * @return Lista de variables de sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTSystemVars() throws ISPACException {
		return getCTSystemVars(null);
	}



	/**
	 * Obtiene la información de una variable de sistema.
	 *
	 * @param id
	 *            Identificador de la variable del sistema
	 * @return Información de la variable de sistema
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTSystemVar(int id) throws ISPACException {
		return getCTEntity(ENTITY_CT_SYSTEM_VARS, id);
	}


	/**
    *
	 * @param nameVarSystemCod :Nombre de la variable de sistema que contiene el
	 * código del tipo documental
	 * @return id del tipo documental
	 * @throws ISPACException
	 */

	public String getIdTpDocByCode(String nameVarSystemCod)
			throws ISPACException {
		try {
			String id = "";
			String codTpDoc = ConfigurationMgr.getVarGlobal(mcontext,
					nameVarSystemCod);
			String query = " WHERE COD_TPDOC='"
					+ DBUtil.replaceQuotes(codTpDoc) + "'";
			IEntitiesAPI entitiesAPI = mcontext.getAPI().getEntitiesAPI();
			IItemCollection itemcol = entitiesAPI.queryEntities(
					SpacEntities.SPAC_CT_TPDOC, query);
			if (itemcol.next()) {
				id = ((IItem) itemcol.value()).getKeyInt() + "";
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("CatalogAPI:No se ha obtenido el id del tipo documental cuyo cod es"
							+ codTpDoc);
				}
			}
			return id;
		} catch (ISPACException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Error al obtener el valor de la variable de sistema"
						+ nameVarSystemCod);

			}
			return "";
		}
	}

	/**
	 * Obtiene el número de tipos de documentos que tiene asociados el tramite
	 * @param idTask Tramite
	 * @return
	 * @throws ISPACException
	 */

	public int countTaskTpDoc (String idTask) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return CTTaskTpDocDAO.countTaskTpDoc(cnt, idTask);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:countTaskTpDoc(" + idTask
					+ ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Asocia los informes al formulario de búsqueda
	 * @param idFrm Identificador del formulario
	 * @param idReports Array con los identificadores de los informes
	 * @return
	 */
	public void asociateReportToFrmBusqueda(int idFrm, int [] idReports)throws ISPACException{
	DbCnt cnt= null ;

	 try{
		 cnt=mcontext.getConnection();
		 if(idReports!=null){
			 for(int i=0; i<idReports.length; i++){
				FrmBusquedaReportDAO.newObject(cnt, idFrm, idReports[i]);
			 }
		 }
	 }catch (ISPACException ie) {
			throw new ISPACException("Error en catalogAPI:asociateReportToFrmBusqueda(" + idFrm
					+ ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	/**
	 * Actualiza las asociaciones entre el formulario de busqueda y los informes
	 * @param idFrm Identificador del formulario
	 * @param idReports Array con los identificadores de los informes que van a estar asociados con el idFrm
	 * @throws ISPACException
	 */
	public void updateAsociateReportToFrmBusqueda(int idFrm, String [] idReports)throws ISPACException{
		DbCnt cnt= null ;
		boolean bCommit=false;
		 try{
			 cnt=mcontext.getConnection();
			 mcontext.beginTX();
			 FrmBusquedaReportDAO.delete(cnt, idFrm);
			 if(idReports!=null){
				 for(int i=0; i<idReports.length; i++){
					FrmBusquedaReportDAO.newObject(cnt, idFrm, Integer.parseInt(idReports[i]));
				 }
			 }
			 bCommit=true;
		 }catch (ISPACException ie) {
			 bCommit=false;
				throw new ISPACException("Error en catalogAPI:updateAsociateReportToFrmBusqueda(" + idFrm
						+ ")", ie);
			} finally {
				if (mcontext.ongoingTX()) {
					mcontext.endTX(bCommit);
				}
				mcontext.releaseConnection(cnt);
			}
	}

	/**
	 * Obtiene los identificadores de los informes que estan asociados al formulario de búsqueda
	 * @param idFrm Identificador del formulario de búsqueda
	 * @return Colección con los informes asociados al formulario de búsqueda
	 * @throws ISPACException
	 */
	public IItemCollection getAsociateReports(int idFrm)throws ISPACException{
		DbCnt cnt= null ;

		 try{
			 cnt=mcontext.getConnection();
			 return FrmBusquedaReportDAO.getReportByIdFormSearch(cnt, idFrm).disconnect();

		 }catch (ISPACException ie) {
				throw new ISPACException("Error en CatalogPI:getAsociateReports(" + idFrm
						+ ")", ie);
			} finally {
				mcontext.releaseConnection(cnt);
			}
	}

	 /**
	 * Obtiene las ayudas a partir del nombre.
	 *
	 * @param pattern
	 *            Patrón del nombre de la ayuda
	 * @return Lista de ayudas
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTHelps(String pattern) throws ISPACException {

		StringBuffer sql = new StringBuffer();

        if (StringUtils.isNotBlank(pattern)) {
        	sql.append("WHERE NOMBRE LIKE '%")
        		.append(DBUtil.replaceQuotes(pattern.trim()))
        		.append("%'");
        }

        sql.append(" ORDER BY NOMBRE");

		return queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql.toString());
	}

	/**
	 * Obtiene las ayudas
	 *
	 * @return Lista de ayudas
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getCTHelps() throws ISPACException {
		return getCTHelps(null);
	}


	/**
	 * Obtiene la información de una ayuda.
	 *
	 * @param id
	 *            Identificador de la ayuda
	 * @return Información de la ayuda
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem getCTHelp(int id) throws ISPACException {
		return getCTEntity(ENTITY_CT_HELPS, id);
	}


	/**
	 * Obtiene la informcaión de una ayuda, busca el form asociado al idObjeto y sino existe el de por defecto
	 *
	 * @param tipoObj Tipo de objeto de la ayuda
	 * @param idObj Identificador de la ayuda
	 * @return
	 * @throws ISPACException
	 */
	public IItem getCTHelp (String tipoObj, String idObj, String idioma)throws ISPACException{

		StringBuffer sql = new StringBuffer();
		IItemCollection itemcol=null;
		if (StringUtils.isNotBlank(tipoObj) && StringUtils.isNotBlank(idioma)) {

			String condicionConIdioma=" WHERE TIPO_OBJ="+tipoObj+" AND IDIOMA='"+DBUtil.replaceQuotes(idioma)+"' ";
			String condicionSinIdioma=" WHERE TIPO_OBJ="+tipoObj+" AND IDIOMA IS NULL";
			sql.append(condicionConIdioma);
			if(StringUtils.isNotBlank(idObj)){
				sql.append(" AND  ID_OBJ="+idObj);
			}
			itemcol=queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql.toString());
			if(itemcol.next())
				return itemcol.value();

			//Buscamos la ayuda por defecto
			sql = new StringBuffer();
			sql.append(condicionSinIdioma);
			if(StringUtils.isNotBlank(idObj)){
				sql.append(" AND  ID_OBJ="+idObj);
			}
			itemcol=queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql.toString());
			if(itemcol.next())
				return itemcol.value();


			sql = new StringBuffer();
			sql.append(condicionConIdioma+" AND ID_OBJ=-1");
			itemcol=queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql.toString());
			if(itemcol.next())
				return itemcol.value();

			//Buscamos la ayuda por defecto
			sql = new StringBuffer();
			sql.append(condicionSinIdioma+" AND ID_OBJ=-1");
			itemcol=queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql.toString());
			if(itemcol.next())
				return itemcol.value();

		}
		return null;
	}


}