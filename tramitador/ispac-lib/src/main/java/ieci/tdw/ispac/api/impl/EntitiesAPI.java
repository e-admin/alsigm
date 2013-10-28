package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoConsultaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.app.EntityAppFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.common.constants.EntityLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityResourceDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.EntityResources;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class EntitiesAPI implements IEntitiesAPI
{
    private ClientContext mcontext;

    private IspacAuditoriaManager auditoriaManager;

    protected Logger logger = Logger.getLogger(EntitiesAPI.class);

    public EntitiesAPI(ClientContext context)
    {
        mcontext = context;
        auditoriaManager = new IspacAuditoriaManagerImpl();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(int)
     */
    public IItem getEntity(int entityId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return getEntity(cnt, entityId);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getEntity("+ entityId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef)
     */
    public IItem getEntity(IEntityDef entitydef) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return getEntity(cnt, entitydef);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getEntity(IEntityDef[" + entitydef.getId() + "])", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(java.lang.String)
     */
    public IItem getEntity(String entityName) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
		try {
			return getEntity(cnt, entityName);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en EntitiesAPI:getEntity("
					+ entityName + ")", ie);
		}
		finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /**
     * Obtiene la descripción de un campo de una entidad catalogada.
     * @param entityName Nombre de la entidad
     * @param fieldName Nombre del campo de la entidad
     * @return Property información del campo.
     * @throws ISPACException
     */
    public Property getEntityFieldProperty(String entityName, String fieldName) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
		try {
			IItem entity = getEntity(cnt, entityName);
			return entity.getProperty(fieldName);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en EntitiesAPI:getEntityFieldProperty("
					+ entityName + ", " + fieldName + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#createEntity(int)
     */
    public IItem createEntity(int entityId) throws ISPACException {

        EntityDAO entityDAO = null;
        DbCnt cnt = mcontext.getConnection();
        try {
            entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew(cnt);
            return entityDAO;
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:createEntity("+ entityId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#createEntity(java.lang.String, java.lang.String)
     */
    public IItem createEntity(String entityname, String numexp) throws ISPACException {

        EntityDAO entityDAO = null;
        DbCnt cnt = mcontext.getConnection();
        try {
            entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityname);
            entityDAO.createNew(cnt);

            if (!StringUtils.isEmpty(numexp)) {

            	// Establecer el número de expediente
            	CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityname);
				entityDAO.set(ctEntityDAO.getKeyNumExp(), numexp);
            }

            return entityDAO;
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:createEntity("+ entityname + ", " + numexp + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(int, int)
     */
    public IItem getEntity(int entityId, int key) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return getEntity(cnt, entityId, key);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getEntity(" + entityId + ", " + key + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(String, int)
     */
    public IItem getEntity(String entityName, int key) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return getEntity(cnt, entityName, key);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getEntity(" + entityName + ", " + key + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntity(ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, int)
     */
    public IItem getEntity(IEntityDef entitydef, int key) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return getEntity(cnt, entitydef, key);
		}
		catch (ISPACNullObject inoe) {
			throw inoe;
		}
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getEntity(IEntityDef[" + entitydef.getId() + "], " + key + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getProcedureEntities(int)
     */
    public IItemCollection getProcedureEntities(int procedureId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return PEntidadDAO.getProcedureEntities(cnt, procedureId).disconnect();
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getProcedureEntities("
                    + procedureId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getProcedureEntities(int, int, int)
     */
    public IItemCollection getProcedureEntities(int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
    		// Entidades visibles
    		// las que no están marcadas como no visibles en el contexto actual de trámite, fase o procedimiento
    		// y las que estando marcadas como no visibles en contextos inferiores de fase o procedimiento
    		// tienen un formulario o regla asignados en contextos superiores de trámite o fase
        	IItemCollection procedureEntities = PEntidadDAO.getProcedureEntities(cnt, procedureId, stagePcdId, taskPcdId).disconnect();

        	// Identificadores de las entidades del procedimiento que aún no tienen evaluada su visibilidad
        	Map procedureVisibleEntityIds = procedureEntities.toMapStringKey("ID_ENT");

        	// Identificadores de las entidades habiendo evaluado su visibilidad
        	// están visibles
        	Map visibleEntityIds = new HashMap();

        	if (taskPcdId > 0) {
        		// Evaular la visibilidad a nivel de trámite
        		evaluateTaskVisibleEntities(cnt, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);
        	}

           	if (stagePcdId > 0) {
        		// Evauluar la visibilidad a nivel de fase
        		evaluateStageVisibleEntities(cnt, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);
        	}

    		// Evaluar la visibilidad a nivel de procedimiento
    		evaluateProcedureVisibleEntities(cnt, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);

        	// Generar el listado de entidades que finalmente estarán visibles
        	List visibleEntities = new ArrayList();
        	procedureEntities.reset();
        	while (procedureEntities.next()) {

        		IItem procedureEntity = (IItem) procedureEntities.value();
        		String entityId = procedureEntity.getString("ID_ENT");
        		if (visibleEntityIds.containsKey(entityId) || procedureVisibleEntityIds.containsKey(entityId)) {
        			visibleEntities.add(procedureEntity);
        		}
        	}

        	return new ListCollection(visibleEntities);
        }
        catch (ISPACInfo ie) {
        	throw ie;
        }
        catch (ISPACException e) {
            throw new ISPACException("Error en EntitiesAPI:getProcedureEntities("
                    + procedureId + ", " + stagePcdId + ", " + taskPcdId + ")", e);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /**
     * Evaluar la visibilidad de las entidades a nivel de procedimiento.
     *
     * @param cnt
     * @param procedureVisibleEntityIds
     * @param visibleEntityIds
     * @param procedureId
     * @param stagePcdId
     * @param taskPcdId
     * @throws ISPACException
     */
    protected void evaluateProcedureVisibleEntities(DbCnt cnt, Map procedureVisibleEntityIds, Map visibleEntityIds, int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {

    	if (!procedureVisibleEntityIds.isEmpty()) {

	    	IItemCollection formsEntity = PEntidadDAO.getFormsAssignedOrWithEntityVisibleRule(cnt, procedureId, procedureVisibleEntityIds).disconnect();
	    	while (formsEntity.next()) {

	    		IItem formEntity = (IItem) formsEntity.value();
	    		// Evaluar la visibilidad de la entidad
	    		evaluateEntityVisible(cnt, formEntity, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);
	    	}
    	}
    }

    /**
     * Evaluar la visibilidad de las entidades a nivel de fase.
     *
     * @param cnt
     * @param procedureVisibleEntityIds
     * @param visibleEntityIds
     * @param procedureId
     * @param stagePcdId
     * @param taskPcdId
     * @throws ISPACException
     */
    protected void evaluateStageVisibleEntities(DbCnt cnt,  Map procedureVisibleEntityIds, Map visibleEntityIds, int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {

    	if (!procedureVisibleEntityIds.isEmpty()) {

	    	IItemCollection formsEntity = PFrmFaseDAO.getFormsAssignedOrWithEntityVisibleRule(cnt, stagePcdId, procedureVisibleEntityIds).disconnect();
	    	while (formsEntity.next()) {

	    		IItem formEntity = (IItem) formsEntity.value();
	    		// Evaluar la visibilidad de la entidad
	    		evaluateEntityVisible(cnt, formEntity, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);
	    	}
    	}
    }

    /**
     * Evaluar la visibilidad de las entidades a nivel de trámite.
     *
     * @param cnt
     * @param procedureVisibleEntityIds
     * @param visibleEntityIds
     * @param procedureId
     * @param stagePcdId
     * @param taskPcdId
     * @throws ISPACException
     */
    protected void evaluateTaskVisibleEntities(DbCnt cnt, Map procedureVisibleEntityIds, Map visibleEntityIds, int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {

    	if (!procedureVisibleEntityIds.isEmpty()) {

	    	IItemCollection formsEntity = PFrmTramiteDAO.getFormsAssignedOrWithEntityVisibleRule(cnt, taskPcdId, procedureVisibleEntityIds).disconnect();
	    	while (formsEntity.next()) {

	    		IItem formEntity = (IItem) formsEntity.value();
	    		// Evaluar la visibilidad de la entidad
	    		evaluateEntityVisible(cnt, formEntity, procedureVisibleEntityIds, visibleEntityIds, procedureId, stagePcdId, taskPcdId);
	    	}
    	}
    }

    /**
     * Evaluar la visibilidad de la entidad.
     *
     * @param cnt
     * @param formEntity
     * @param procedureVisibleEntityIds
     * @param visibleEntityIds
     * @param procedureId
     * @param stagePcdId
     * @param taskPcdId
     * @throws ISPACException
     */
    protected void evaluateEntityVisible(DbCnt cnt, IItem formEntity, Map procedureVisibleEntityIds, Map visibleEntityIds, int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {

		// Entidad visible
		// cuya visibilidad aún no ha sido establecida por una regla
		// o por tener asignado un formulario o una regla que asigna el formulario
		String entityId = formEntity.getString("ID_ENT");

		if (formEntity.get("ID_RULE_VISIBLE") != null) {

			// Establecer la visibilidad de la entidad al ejecutar la regla asignada
	    	EventManager meventmgr = new EventManager(mcontext);
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, String.valueOf(procedureId));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCESS, String.valueOf(mcontext.getStateContext().getProcessId()));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, mcontext.getStateContext().getNumexp());
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGE, String.valueOf(mcontext.getStateContext().getStageId()));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, String.valueOf(stagePcdId));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASK, String.valueOf(mcontext.getStateContext().getTaskId()));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASKPCD, String.valueOf(taskPcdId));
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_ENTITYID, entityId);
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_REGENTITYID, String.valueOf(mcontext.getStateContext().getKey()));

			// Ejecución de la regla que establece la visibilidad de la entidad
			Object oVisible = meventmgr.executeRule(formEntity.getInt("ID_RULE_VISIBLE"));
			if ((oVisible != null) && (oVisible instanceof Boolean)) {

				// La regla retorna si la entidad es visible o no
				Boolean visible = (Boolean) oVisible;
				if (visible) {
					// Establecer la entidad como visible
					// al establecerse explícitamente como visible tras ejecutar la regla
					visibleEntityIds.put(entityId, null);
				}
			} else {
				// Error en la regla por no retornar una condición de cierto o falso para la visibilidad de la entidad
				CTRuleDAO rule = new CTRuleDAO(cnt, formEntity.getInt("ID_RULE_VISIBLE"));
				throw new ISPACInfo("exception.entities.form.rule.visible.invalid", new Object[] {rule.getString("NOMBRE"), mcontext.getStateContext().getNumexp()});
			}
		} else {
			// Establecer la entidad como visible
			// por tener asignado explícitamente un formulario o una regla que asigna el formulario
			visibleEntityIds.put(entityId, null);
		}

		// Eliminar la entidad para que no se evalúe su visibilidad
		// en los siguientes niveles (trámite -> fase -> procedimiento)
		procedureVisibleEntityIds.remove(entityId);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getStageEntities(int, int)
     */
    public IItemCollection getStageEntities(int procedureId, int stagePcdId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return PEntidadDAO.getProcedureEntities(cnt, procedureId).disconnect();
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getStageEntities("
                    + procedureId + ", " + stagePcdId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTaskEntities(int, int)
     */
    public IItemCollection getTaskEntities(int procedureId, int taskId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try {
            return PEntidadDAO.getProcedureEntities(cnt, procedureId).disconnect();
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getTaskEntities("
                    + procedureId + ", " + taskId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getProcedureEntity(int, int)
     */
    public IItem getProcedureEntity(int procedureId, int entityId) throws ISPACException {

    	DbCnt cnt = mcontext.getConnection();
        try {
            String sqlWhere = "WHERE ID_PCD = "
                                            + procedureId
                                            + "AND ID_ENT = "
                                            + entityId;
            PEntidadDAO entity = new PEntidadDAO(cnt);
            entity.load( cnt, sqlWhere);

            return entity;
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:getProcedureEntity("
            + procedureId + "," + entityId + ")", ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntities(java.lang.String, java.lang.String)
     */
    public IItemCollection getEntities(String entityname, String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return getEntities(cnt, entityname, numexp, "");
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntities("
                    + entityname + ", " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntities(java.lang.String, java.lang.String, java.lang.String)
     */
    public IItemCollection getEntities(String entityname, String numexp, String sqlQuery) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return getEntities(cnt, entityname, numexp, sqlQuery);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntities("
                    + entityname + ", " + numexp + ", " + sqlQuery + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntities(int, java.lang.String, java.lang.String)
     */
    public IItemCollection getEntities(int entityId, String numexp, String sqlQuery) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return getEntities(cnt, entityId, numexp, sqlQuery);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntities("
                    + entityId + ", " + numexp + ", " + sqlQuery + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntities(int, java.lang.String)
     */
    public IItemCollection getEntities(int entityId, String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return getEntities(cnt, entityId, numexp, "");
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntities("
                    + entityId + ", " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntitiesWithOrder(int, java.lang.String, java.lang.String, java.lang.String)
     */
    public IItemCollection getEntitiesWithOrder(int entityId, String numexp, String sqlQuery, String order) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return getEntitiesWithOrder(cnt, entityId, numexp, sqlQuery, order);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntitiesWithOrder("
                    + entityId + ", " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(int, java.lang.String)
     */
    public IItemCollection queryEntities(int entityId, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().queryEntities(cnt, entityId, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities("+entityId+", "+query+")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(int, java.lang.String)
     */
    public IItemCollection queryEntitiesForUpdate(int entityId, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	IEntityDef entitydef = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
            return EntityFactoryDAO.getInstance().queryEntitiesForUpdate(cnt, entitydef, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntitiesForUpdate("+entityId+", "+query+")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /**
     * (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(java.lant.String, java.lang.String , java.lang.String, int)
     */

    public IItemCollection queryEntities(String entityName, String query, String order, int limit) throws ISPACException{

    	DbCnt cnt = mcontext.getConnection();
        try
        {
        	 //En funcion de que bbdd estemos
             return EntityFactoryDAO.getInstance().queryEntities(cnt, entityName, query, order, limit).disconnect();
        }
        catch (ISPACException ie)
        {
        	throw new ISPACException("Error en EntitiesAPI:queryEntities("+entityName+", "+query+", "+order+", "+limit+")",ie);
        }
        finally
        {
        	mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(java.lang.String, java.lang.String)
     */
    public IItemCollection queryEntities(String entityName, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().queryEntities(cnt, entityName, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities("+entityName+", "+query+")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

	public int deleteEntities(String entityName, String query) throws ISPACException {
        DbCnt cnt = mcontext.getConnection();
        try
        {
        	IEntityDef entitydef = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityName);
        	return EntityFactoryDAO.getInstance().deleteEntities(cnt, entitydef, query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:deleteEntities("
                    + entityName + ", " + query + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(IEntityDef, java.lang.String)
     */
    public IItemCollection queryEntities(IEntityDef entityDef, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getEntities(cnt, entityDef, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities(IEntityDef[" + entityDef.getId() + "], " + query + ")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeEntities(int, java.lang.String)
     */
    public IItemCollection getSchemeEntities(int entityId, String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getSchemeEntities(cnt, entityId, numexp, "").disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeEntities("
                    + entityId + ", " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeEntities(ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, java.lang.String)
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getSchemeEntities(cnt, entitydef, numexp, "").disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeEntities(IEntityDef[" + entitydef.getId() + "], " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeEntities(int, java.lang.String, java.lang.String, ieci.tdw.ispac.api.item.Property[])
     */
    public IItemCollection getSchemeEntities(int entityId, String numexp, String query, Property[] extraprop) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getSchemeEntities(cnt, entityId, numexp, query, extraprop).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeEntities("
                    + entityId + ", " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeEntities(ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, java.lang.String, java.lang.String, ieci.tdw.ispac.api.item.Property[])
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp, String query, Property[] extraprop) throws ISPACException {
    	return getSchemeEntities(entitydef, numexp, query, extraprop, null, false);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeEntities(ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, java.lang.String, java.lang.String, ieci.tdw.ispac.api.item.Property[], java.lant.String , boolean)
     */
    public IItemCollection getSchemeEntities(IEntityDef entitydef, String numexp, String query, Property[] extraprop, String orderBy , boolean desc) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getSchemeEntities(cnt, entitydef, numexp, query, extraprop, orderBy, desc).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeEntities(IEntityDef[" + entitydef.getId() + "], " + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeStageDocuments(java.lang.String, int)
     */
    public IItemCollection getSchemeStageDocuments(String numexp, int stageId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        String sQuery = null;
        int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
        try
        {
            sQuery = "(ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "')) AND ID_FASE = "
                   + Integer.toString(stageId)
                   + " AND ID_TRAMITE = 0";
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,numexp,sQuery).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeStageDocuments("
                    + numexp + ", " + stageId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeStageDocuments(java.lang.String, int, int)
     */
    public IItemCollection getSchemeStageDocuments(String numexp, int stageId,int docTypeId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        String sQuery = null;
        int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
        try
        {
            sQuery = "(ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "')) AND ID_FASE = "
                   + Integer.toString(stageId)
                   + " AND ID_TPDOC = "
                   + Integer.toString(docTypeId)
                   + " AND ID_TRAMITE = 0";
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,numexp,sQuery).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeStageDocuments("
                    + numexp + ", " + stageId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeProcessDocuments(java.lang.String)
     */
    public IItemCollection getSchemeProcessDocuments(String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        String sQuery = null;
        int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
        try
        {
            sQuery = "ESTADO = 'FINALIZADO'";
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,numexp,sQuery).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeProcessDocuments("
                    + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeTaskDocuments(java.lang.String, int)
     */
    public IItemCollection getSchemeTaskDocuments(String numexp, int taskId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        String sQuery = null;
        int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
        try
        {
            sQuery = "( ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "' )) AND ID_TRAMITE = "
                   + Integer.toString(taskId);
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,numexp,sQuery).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeTaskDocuments("
                    + numexp + ", " + taskId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getSchemeTaskDocuments(java.lang.String, int, int)
     */
    public IItemCollection getSchemeTaskDocuments(String numexp, int taskId, int documentId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        String sQuery = null;
        int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
        try
        {
            sQuery = "( ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "' )) AND ID_TRAMITE = "
                   + Integer.toString(taskId)
                   + " AND ID_TPDOC = "
                   + Integer.toString(documentId);
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,numexp,sQuery).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSchemeTaskDocuments("
                    + numexp + ", " + taskId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntity(int)
     */
    public IItem getCatalogEntity(int entityId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getCatalogEntity("
                    + entityId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntity(String)
     */
    public IItem getCatalogEntity(String entityName) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityName);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getCatalogEntity("
                    + entityName + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntities()
     */
    public IItemCollection getCatalogEntities() throws ISPACException {

        return getCatalogEntities(null);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntities(java.lang.String)
     */
    public IItemCollection getCatalogEntities(String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            CollectionDAO collection = new CollectionDAO(CTEntityDAO.class);
            collection.query(cnt,query);
            return collection.disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getCatalogEntities()", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getExpedient(int)
     */
    public IItem getExpedient(int id) throws ISPACException {

        return getEntity(ISPACEntities.DT_ID_EXPEDIENTES, id);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getExpedient(java.lang.String)
     */
    public IItem getExpedient(String numexp) throws ISPACException {

        IItemCollection itemset = getEntities(ISPACEntities.DT_ID_EXPEDIENTES, numexp, null);
        if (!itemset.next())
            return null;
        return itemset.value();
    }

    /**
     * Obtiene los registros de la entidad de expedietes
     * a partir del número de registro.
     *
     * @param regNum Número de registro.
     * @return Lista de expedientes.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getExpedientsByRegNum(String regNum) throws ISPACException {
    	return queryEntities(ISPACEntities.DT_ID_EXPEDIENTES,
    			"WHERE NREG='" + DBUtil.replaceQuotes(regNum) + "' ORDER BY NUMEXP");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getParticipants(java.lang.String, java.lang.String, java.lang.String)
     */
    public IItemCollection getParticipants(String numexp, String sqlQuery, String order) throws ISPACException {

        return getEntitiesWithOrder(ISPACEntities.DT_ID_INTERVINIENTES, numexp, sqlQuery, order);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getParticipant(int)
     */
    public IItem getParticipant(int id) throws ISPACException {

        return getEntity(ISPACEntities.DT_ID_INTERVINIENTES, id);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getDocuments(java.lang.String, java.lang.String, java.lang.String)
     */
    public IItemCollection getDocuments(String numexp, String sqlQuery, String order) throws ISPACException {

		return getEntitiesWithOrder(ISPACEntities.DT_ID_DOCUMENTOS, numexp, sqlQuery, order);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityDocuments(int, int)
     */
    public IItemCollection getEntityDocuments(int entityId, int entityRegId) throws ISPACException {

    	StringBuffer query = new StringBuffer();

		query.append(" WHERE ID_ENTIDAD = ")
			 .append(entityId)
			 .append(" AND ID_REG_ENTIDAD = ")
			 .append(entityRegId);

    	return queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, query.toString());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTaskDocuments(java.lang.String, int)
     */
    public IItemCollection getTaskDocuments(String numexp, int taskId) throws ISPACException {

    	Map tblEntityMap = new HashMap();
    	tblEntityMap.put("SPAC_DT_DOCUMENTOS", "SPAC_DT_DOCUMENTOS");
    	tblEntityMap.put("SPAC_DT_TRAMITES", "SPAC_DT_TRAMITES");

    	StringBuffer query = new StringBuffer()
    		.append(" WHERE SPAC_DT_DOCUMENTOS.NUMEXP = '")
    		.append(DBUtil.replaceQuotes(numexp))
    		.append("' AND SPAC_DT_DOCUMENTOS.ID_TRAMITE = ")
    		.append(taskId)
    		.append(" AND SPAC_DT_DOCUMENTOS.NUMEXP = SPAC_DT_TRAMITES.NUMEXP AND SPAC_DT_DOCUMENTOS.ID_TRAMITE = SPAC_DT_TRAMITES.ID_TRAM_EXP");

    	return queryEntities(tblEntityMap, query.toString());
    }

    public IItemCollection getStageDocuments(String numexp, int stageId) throws ISPACException {

    	Map tblEntityMap = new HashMap();
    	tblEntityMap.put("SPAC_DT_DOCUMENTOS", "SPAC_DT_DOCUMENTOS");

    	StringBuffer query = new StringBuffer()
    		.append(" WHERE SPAC_DT_DOCUMENTOS.NUMEXP = '")
    		.append(DBUtil.replaceQuotes(numexp))
    		.append("' AND SPAC_DT_DOCUMENTOS.ID_FASE = ")
    		.append(stageId);

    	return queryEntities(tblEntityMap, query.toString());
    }

    public IItemCollection getStageDocuments(int stageId) throws ISPACException {

    	StringBuffer query = new StringBuffer()
    		.append(" WHERE ID_FASE=")
    		.append(stageId);

    	return queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, query.toString());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getDocuments(java.lang.String, int)
     */
    public IItemCollection getDocuments(String numexp, int idTemplate) throws ISPACException {

    	StringBuffer query = new StringBuffer();
    	query.append(" WHERE ")
    		 .append(" NUMEXP = '")
    		 .append(DBUtil.replaceQuotes(numexp))
    		 .append("' AND ID_PLANTILLA = ")
    		 .append(idTemplate);

    	return queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, query.toString());
    }

	public IItemCollection getDocumentsOpenedTask(String numexp, Integer idTemplate) throws ISPACException {

    	StringBuffer query = new StringBuffer();
    	query.append(" WHERE ")
    		 .append(" NUMEXP = '")
    		 .append(DBUtil.replaceQuotes(numexp))
    		 .append("' AND ID_PLANTILLA = ")
    		 .append(idTemplate)
    		 .append(" AND ID_TRAMITE IN ( SELECT ID FROM SPAC_TRAMITES ) ");

    	return queryEntities(ISPACEntities.DT_ID_DOCUMENTOS, query.toString());
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getDocument(int)
     */
    public IItem getDocument(int id) throws ISPACException {

        IItem document = getEntity(ISPACEntities.DT_ID_DOCUMENTOS, id);

        //Auditar consulta del documento

        auditConsultaDocumento(id, document);

    	return document;
    }

	/**
	 * @param id
	 * @param document
	 * @throws ISPACException
	 */
	private void auditConsultaDocumento(int id, IItem document) throws ISPACException {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventDocumentoConsultaVO evento = new IspacAuditEventDocumentoConsultaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());

		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setUser("");
		evento.setIdUser("");
		evento.setIdDocumento(String.valueOf(id));
		String numExpediente = document.getString("NUMEXP");
		evento.setNumExpediente(numExpediente);

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del documento");
		auditoriaManager.audit(evento);
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getStageEntityApp(java.lang.String, int, int, int, int, java.lang.String, int, java.util.Map)
     */
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stagePcdId, int entityId, int entityRegId, String path, int urlKey, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getStageApplication(cnt, stagePcdId, entityId, params);
            if (application == null)
                return null;

            EntityDAO entityDAO = null;
            // Obtiene la entidad
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entityId);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getStageEntityApp(" + numExp + ", " + procedureId + ", " + stagePcdId + ", "+ entityId + ", "+ entityRegId + ", " + path + ", " + urlKey + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getStageEntityApp(java.lang.String, int, int, ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, int, java.lang.String, int, java.util.Map)
     */
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stagePcdId, IEntityDef entitydef, int entityRegId, String path, int urlKey, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	int entityId = entitydef.getId();

            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getStageApplication(cnt, stagePcdId, entityId, params);
            if (application == null)
                return null;

            EntityDAO entityDAO = null;
            // Obtiene la entidad
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entitydef);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entitydef, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getStageEntityApp(" + numExp + ", " + procedureId + ", " + stagePcdId + ", IEntityDef["+ entitydef.getId() + "], "+ entityRegId + ", " + path + ", " + urlKey + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getStageEntityApp(java.lang.String, int, int, int, java.lang.String, int)
     */
    /*
    public EntityApp getStageEntityApp(String numExp, int procedureId, int stageId, int entityId, String path, int urlKey) throws ISPACException {

    	return getStageEntityApp(numExp, procedureId, stageId, entityId, ISPACEntities.ENTITY_NULLREGKEYID, path, urlKey);
    }
    */

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getProcedureEntityApp(java.lang.String, int, int, int, java.lang.String, int, java.util.Map)
     */
    public EntityApp getProcedureEntityApp(String numExp, int procedureId, int entityId, int entityRegId, String path, int urlKey, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getProcedureApplication(cnt, procedureId, entityId, params);
            if (application == null)
                return null;

            EntityDAO entityDAO = null;
            // Obtiene la entidad
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entityId);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getProcedureEntityApp(" + numExp + ", " + procedureId + ", "+ entityId + ", "+ entityRegId + ", " + path + ", " + urlKey + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getProcedureEntityApp(java.lang.String, int, ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, int, java.lang.String, int, java.util.Map)
     */
    public EntityApp getProcedureEntityApp(String numExp, int procedureId, IEntityDef entitydef, int entityRegId, String path, int urlKey, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	int entityId = entitydef.getId();

            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getProcedureApplication(cnt, procedureId, entityId, params);
            if (application == null)
                return null;

            EntityDAO entityDAO = null;
            // Obtiene la entidad
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entitydef);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entitydef, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getProcedureEntityApp(" + numExp + ", " + procedureId + ", IEntityDef["+ entitydef.getId() + "], "+ entityRegId + ", " + path + ", " + urlKey + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getExpedientEntityApp(java.lang.String, int, java.lang.String, int)
     */
    public EntityApp getExpedientEntityApp(String numexp, int procedureId, String path, int urlKey) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	int entityId = ISPACEntities.DT_ID_EXPEDIENTES;

            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getEntityApplication(cnt, entityId);

            IItemCollection collection = getEntities(cnt, entityId, numexp, null);
            if (!collection.next())
                return null;
            EntityDAO entityDAO = (EntityDAO) collection.value();

            return EntityAppFactory.createEntityApp(mcontext, application, numexp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getExpedientEntityApp("
                    + numexp + ")", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTaskEntityApp(java.lang.String, int, int, int, int, java.lang.String, int, boolean, java.util.Map)
     */
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, int entityId, int entityRegId, String path, int urlKey, boolean noDefault, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getTaskApplication(cnt, taskPcdId, entityId, noDefault, params);
            if (application == null)
            	return null;

            // Obtiene la entidad
            EntityDAO entityDAO = null;
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entityId);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getTaskEntityApp(" + numExp + ", " + procedureId + ", " + taskPcdId + ", " + entityId + ", " + entityRegId + ", " + path + ", " + urlKey + ", " + noDefault + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTaskEntityApp(java.lang.String, int, int, ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, int, java.lang.String, int, boolean, java.util.Map)
     */
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, IEntityDef entitydef, int entityRegId, String path, int urlKey, boolean noDefault, Map params) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	int entityId = entitydef.getId();

            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getTaskApplication(cnt, taskPcdId, entityId, noDefault, params);
            if (application == null)
            	return null;

            // Obtiene la entidad
            EntityDAO entityDAO = null;
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entitydef);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entitydef, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numExp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getTaskEntityApp(" + numExp + ", " + procedureId + ", " + taskPcdId + ", IEntityDef[" + entitydef.getId() + "], " + entityRegId + ", " + path + ", " + urlKey + ", " + noDefault + ", params)", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTaskEntityApp(java.lang.String, int, int, int, java.lang.String, int)
     */
    /*
    public EntityApp getTaskEntityApp(String numExp, int procedureId, int taskPcdId, int entityId, String path, int urlKey, boolean noDefault) throws ISPACException {

        return getTaskEntityApp(numExp, procedureId, taskPcdId, entityId, ISPACEntities.ENTITY_NULLREGKEYID, path, urlKey, noDefault);
    }
    */

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntityApp(java.lang.String, int, int, int, java.lang.String)
     */
    public EntityApp getCatalogEntityApp(String numexp, int procedureId, int entityId, int entityRegId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getEntityApplication(cnt, entityId);
            if (application==null)
                return null;

            // Obtiene la entidad
            EntityDAO entityDAO = null;
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entityId);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numexp, procedureId, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getCatalogEntityApp("
                    + entityId + ", "+ entityRegId + ")", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getCatalogEntityApp(int, ieci.tdw.ispac.api.item.IItem, java.lang.String)
     */
    public EntityApp getCatalogEntityApp(int entityId, IItem item, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getEntityApplication(cnt, entityId);

            return EntityAppFactory.createEntityApp(mcontext, application, null, ISPACEntities.ENTITY_NULLREGKEYID, entityId, item, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
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

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityApp(java.lang.String, int, int, int, int, java.lang.String, int)
     */
    public EntityApp getEntityApp(String numexp, int procedureId, int appId, int entityId, int entityRegId, String path, int urlKey) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = new CTApplicationDAO(cnt, appId);

            //EntityDAO entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);
            EntityDAO entityDAO = null;
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entityId);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numexp, procedureId, entityId, entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
        	throw new ISPACException("Error en EntitiesAPI:getEntityApp(" + numexp + ", " + procedureId + ", " + appId + ", " + entityId + ", " + entityRegId + ", " + path + ", " + urlKey + ")", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityApp(java.lang.String, int, int, ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef, int, java.lang.String, int)
     */
    public EntityApp getEntityApp(String numexp, int procedureId, int appId, IEntityDef entitydef, int entityRegId, String path, int urlKey) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = new CTApplicationDAO(cnt, appId);

            //EntityDAO entityDAO = (EntityDAO) getEntity(cnt, entityId, entityRegId);
            EntityDAO entityDAO = null;
            if (entityRegId == ISPACEntities.ENTITY_NULLREGKEYID) {

                entityDAO = (EntityDAO) getEntity(cnt, entitydef);

                // Esto mismo ya se hace en el StoreEntityAction
                /*
                IItem itemCat = getCatalogEntity(entityId);
                String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
                if (StringUtils.isNotEmpty(fieldNumExp)) {
                	entityDAO.set(fieldNumExp, numExp);
                }
                */
            }
            else
                entityDAO = (EntityDAO) getEntity(cnt, entitydef, entityRegId);

            return EntityAppFactory.createEntityApp(mcontext, application, numexp, procedureId, entitydef.getId(), entityDAO, path, urlKey);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityApp(" + numexp + ", " + procedureId + ", " + appId + ", IEntityDef["+ entitydef.getId() + "], " + entityRegId + ", " + path + ", " + urlKey + ")", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityApp(java.lang.String, int, int, int, java.lang.String, int)
     */
    public EntityApp getEntityApp(String numexp, int procedureId, int appId, int entityId, String path, int urlKey) throws ISPACException {

        return getEntityApp(numexp, procedureId, appId, entityId, ISPACEntities.ENTITY_NULLREGKEYID, path, urlKey);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#newEntityApp(int, java.lang.String)
     */
    public EntityApp newEntityApp(int entityId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getEntityApplication(cnt, entityId);
            if (application==null)
                return null;

            EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew( cnt);

            return EntityAppFactory.createEntityApp(mcontext, application, null, ISPACEntities.ENTITY_NULLREGKEYID, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:newEntityApp("
                    + entityId + " )", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /*
    public EntityApp newEntityApp(int procedureId, int appId, int entityId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application =new CTApplicationDAO(cnt,appId);
            if (application==null)
                return null;

            EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew( cnt);
            entityDAO.store(cnt);

            return EntityAppFactory.createEntityApp(mcontext, application, null, procedureId, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:newEntityApp("
                    +appId+ ", "+ entityId + " )", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }
    */

    /*
    public EntityApp newProcedureEntityApp(int procedureId, int entityId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getProcedureApplication(cnt,procedureId,entityId);
            if (application==null)
                return null;

            EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew( cnt);

            return EntityAppFactory.createEntityApp(mcontext, application, null, procedureId, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:newProcedureEntityApp("
                    + entityId + " )", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }
    */

    /*
    public EntityApp newStageEntityApp(int procedureId, int stagePcdId, int entityId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getStageApplication(cnt,stagePcdId,entityId);
            if (application==null)
                return null;

            EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew( cnt);

            return EntityAppFactory.createEntityApp(mcontext, application, null, procedureId, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:newStageEntityApp("
                    + entityId + " )", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }
    */

    /*
    public EntityApp newTaskEntityApp(int procedureId, int taskPcdId, int entityId, String path) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            // Obtiene la aplicación de la entidad
            CTApplicationDAO application = getTaskApplication(cnt,taskPcdId,entityId);
            if (application==null)
                return null;

            EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
            entityDAO.createNew( cnt);

            return EntityAppFactory.createEntityApp(mcontext, application, null, procedureId, entityId, entityDAO, path, 0);
        }
        catch (ISPACInfo i) {
        	throw i;
        }
        catch (Exception e)
        {
            throw new ISPACException("Error en EntitiesAPI:newTaskEntityApp("
                    + entityId + " )", e);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }
    */

    /**
     * Obtiene un registro vacío de una entidad
     * a partir del identificador de la entidad.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, int entityId) throws ISPACException {

        return EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
    }

    /**
     * Obtiene un registro vacío de una entidad
     * a partir del identificador de la entidad.
     *
     * @param cnt conexión
     * @param entityDef definición de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, IEntityDef entitydef) throws ISPACException {

        return EntityFactoryDAO.getInstance().newEntityDAO(cnt, entitydef);
    }

    /**
     * Obtiene un registro vacío de una entidad
     * a partir del nombre de la entidad.
     *
     * @param cnt conexión
     * @param entityName nombre de la entidad
     * @return IItem registro vacío de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, String entityName) throws ISPACException {

        return EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityName);
    }

    /**
     * Obtiene un registro con los datos de una entidad
     * a partir del identificador de la entidad
     * y el identificador del registro.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, int entityId, int entityRegId) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntity(cnt, mcontext, entityId, entityRegId);
    }

    /**
     * Obtiene un registro con los datos de una entidad
     * a partir del nombre de la entidad
     * y el identificador del registro.
     *
     * @param cnt conexión
     * @param entityName nombre de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, String entityName, int entityRegId) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntity(cnt, mcontext, entityName, entityRegId);
    }

    /**
     * Obtiene un registro con los datos de una entidad
     * a partir de la definición de la entidad
     * y el identificador del registro.
     *
     * @param cnt conexión
     * @param entityDef definición de la entidad
     * @param entityRegId identificador del registro de la entidad
     * @return IItem registro con datos de la entidad
     * @throws ISPACException
     */
    protected IItem getEntity(DbCnt cnt, IEntityDef entitydef, int entityRegId) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntity(cnt, mcontext, entitydef, entityRegId);
    }

    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del nombre de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param cnt conexión
     * @param entityname nombre de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    protected IItemCollection getEntities(DbCnt cnt, String entityname, String numexp, String sqlQuery) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntities(cnt, entityname, numexp, sqlQuery).disconnect();
    }

    /**
     * Obtiene una colección con los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @return IItemCollection collección de objetos CTEntityDAO de entidades del expediente
     * @throws ISPACException
     */
    protected IItemCollection getEntities(DbCnt cnt, int entityId, String numexp, String sqlQuery) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntities(cnt, entityId, numexp, sqlQuery).disconnect();
    }

    /**
     * Obtiene una colección ordenada con los registros de una entidad de un expediente concreto,
     * a partir del identificador de la entidad y el número de expediente, y una consulta para
     * seleccionar los registros.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad
     * @param numexp número de expediente
     * @param sqlQuery consulta a realizar para seleccionar los registros de entidades
     * @param order criterio de ordenación para la consulta
     * @return CollectionDAO collección de objetos CTEntityDAO de entidades del expediente
     */
    protected IItemCollection getEntitiesWithOrder(DbCnt cnt, int entityId, String numexp, String sqlQuery, String order) throws ISPACException {

        return EntityFactoryDAO.getInstance().getEntitiesWithOrder(cnt, entityId, numexp, sqlQuery, order).disconnect();
    }

    /**
     * Obtiene la aplicación asociada a la entidad para la fase suministrada.
     *
     * @param cnt conexión
     * @param stagePcdId identificador de la fase en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada
     * @throws ISPACException
     */
    protected CTApplicationDAO getStageApplication(DbCnt cnt, int stagePcdId, int entityId, Map params) throws ISPACException {

        CTApplicationDAO application = null;

        try
        {
            PFrmFaseDAO frmStage = new PFrmFaseDAO(cnt);
            frmStage.load(cnt, stagePcdId, entityId);

//            // Formulario no visible
            String sFrmEdit = frmStage.getString("FRM_EDIT");
//			if ((StringUtils.isNotBlank(sFrmEdit)) &&
//    			(Integer.parseInt(sFrmEdit) == ISPACEntities.ENTITY_FORM_NO_VISIBLE)) {
//
//            	throw new ISPACInfo("exception.expedients.entity.noVisible", false);
//            }
//            else {

            	if (params != null) {

	            	Boolean readonlyForm = (Boolean) params.get("FRM_READONLY");
	            	if (readonlyForm == null) {

		    			// Comprobar formulario en sólo lectura
		    			String sReadonly = frmStage.getString("FRM_READONLY");
		    			if (StringUtils.isNotBlank(sReadonly)) {

		    				int iReadonly = Integer.parseInt(sReadonly);
		    				if (ISPACEntities.ENTITY_FORM_READONLY == iReadonly) {
		    					params.put("FRM_READONLY", Boolean.TRUE);
		    				}
		    				else if (ISPACEntities.ENTITY_FORM_EDITABLE == iReadonly) {
		    					params.put("FRM_READONLY", Boolean.FALSE);
		    				}
		    			}
		    		}
            	}

    			if (StringUtils.isNotBlank(sFrmEdit)) {

	    			// Obtener el formulario a nivel de fase
	            	application = frmStage.getApplication(cnt);
    			}

    			if (StringUtils.isNotBlank(frmStage.getString("ID_RULE_FRM"))) {

    				// Obtener el formulario al ejecutar la regla asignada a nivel de trámite
    				application = getRuleApplication(cnt, entityId, frmStage.getInt("ID_RULE_FRM"));
    			}
//            }
        }
        catch(ISPACNullObject e)
        {
            return null;
        }

        return application;
    }

    /**
     * Obtiene la aplicación asociada a la entidad para el trámite suministrado.
     *
     * @param cnt conexión
     * @param taskPcdId identificador del trámite en el procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada
     * @throws ISPACException
     */
    protected CTApplicationDAO getTaskApplication(DbCnt cnt, int taskPcdId, int entityId, boolean noDefault, Map params) throws ISPACException {

        CTApplicationDAO application = null;
        try
        {
            PFrmTramiteDAO frmTask = new PFrmTramiteDAO(cnt);
            frmTask.load(cnt, taskPcdId, entityId);

//            // Formulario no visible
            String sFrmEdit = frmTask.getString("FRM_EDIT");
//			if ((StringUtils.isNotBlank(sFrmEdit)) &&
//    			(Integer.parseInt(sFrmEdit) == ISPACEntities.ENTITY_FORM_NO_VISIBLE)) {
//
//            	throw new ISPACInfo("exception.expedients.entity.noVisible", false);
//            }
//            else {

            	if (params != null) {

	            	Boolean readonlyForm = (Boolean) params.get("FRM_READONLY");
	            	if (readonlyForm == null) {

		    			// Comprobar formulario en sólo lectura
	            		String sReadonly = frmTask.getString("FRM_READONLY");
		    			if (StringUtils.isNotBlank(sReadonly)) {

		    				int iReadonly = Integer.parseInt(sReadonly);
		    				if (ISPACEntities.ENTITY_FORM_READONLY == iReadonly) {
		    					params.put("FRM_READONLY", Boolean.TRUE);
		    				}
		    				else if (ISPACEntities.ENTITY_FORM_EDITABLE == iReadonly) {
		    					params.put("FRM_READONLY", Boolean.FALSE);
		    				}
		    			}
		    		}
            	}

    			if (StringUtils.isNotBlank(sFrmEdit)) {

	    			// Obtener el formulario a nivel de trámite
	            	application = frmTask.getApplication(cnt);
    			}

    			if (StringUtils.isNotBlank(frmTask.getString("ID_RULE_FRM"))) {

    				// Obtener el formulario al ejecutar la regla asignada a nivel de trámite
    				application = getRuleApplication(cnt, entityId, frmTask.getInt("ID_RULE_FRM"));
    			}
//            }
        }
        catch(ISPACNullObject e)
        {
            //return null;
        }

        if (noDefault) {

        	return application;
        }
        else {

	        try {
	            // Aplicación por defecto (ID_TRAMITE = 0) para la entidad en cualquier trámite
	            if (application == null) {

	            	PFrmTramiteDAO frmTask = new PFrmTramiteDAO(cnt);
	                frmTask.loadDefault(cnt,entityId);
	                application = frmTask.getApplication(cnt);
	            }
	        }
	        catch(ISPACNullObject e) {

	            return null;
	        }

	        return application;
        }
    }

    /**
     * Obtiene la aplicación asociada a la entidad para el procedimiento suministrado.
     *
     * @param cnt conexión
     * @param procedureId identificador del procedimiento para la que se busca la aplicación
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param params parámetros modificables (FRM_READONLY = formulario en sólo lectura)
     * @return aplicación solicitada
     * @throws ISPACException
     */
    protected CTApplicationDAO getProcedureApplication(DbCnt cnt, int procedureId, int entityId, Map params) throws ISPACException {

        CTApplicationDAO application = null;

        /*
        try
        {
        */

        PEntidadDAO entity = new PEntidadDAO(cnt);
        entity.load(cnt, procedureId, entityId);

//        // Formulario no visible
//        String sFrmEdit = entity.getString("FRM_EDIT");
//		if ((StringUtils.isNotBlank(sFrmEdit)) &&
//			(Integer.parseInt(sFrmEdit) == ISPACEntities.ENTITY_FORM_NO_VISIBLE)) {
//
//        	throw new ISPACInfo("exception.expedients.entity.noVisible", false);
//        }
//		else
		if (params != null) {

	    	Boolean readonlyForm = (Boolean) params.get("FRM_READONLY");
	    	if (readonlyForm == null) {

	    		// Comprobar formulario en sólo lectura
	    		String sReadonly = entity.getString("FRM_READONLY");
				if (StringUtils.isNotBlank(sReadonly)) {

					int iReadonly = Integer.parseInt(sReadonly);
					if (ISPACEntities.ENTITY_FORM_READONLY == iReadonly) {
						params.put("FRM_READONLY", Boolean.TRUE);
					}
					else if (ISPACEntities.ENTITY_FORM_EDITABLE == iReadonly) {
						params.put("FRM_READONLY", Boolean.FALSE);
					}
				}
			}
		}

        // Obtener el formulario a nivel de procedimiento
        application = entity.getApplication(cnt);

        if ((application == null) && (StringUtils.isNotBlank(entity.getString("ID_RULE_FRM")))) {

			// Obtener el formulario al ejecutar la regla asignada a nivel de procedimiento
        	application = getRuleApplication(cnt, entityId, entity.getInt("ID_RULE_FRM"));
        }

	    /*
        }
        catch(ISPACNullObject e)
        {
            application = getEntityApplication(cnt, entityId);
        }
        */

        return application;
    }

    /**
     * Obtener el formulario a partir de la ejecución de una regla.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @param ruleId identificador de la regla a ejecutar
     * @return aplicación asociada en la regla
     * @throws ISPACException
     */
    protected CTApplicationDAO getRuleApplication(DbCnt cnt, int entityId, int ruleId) throws ISPACException {

    	CTApplicationDAO application = null;

		// Contexto para la regla que asigna el formulario
    	EventManager meventmgr = new EventManager(mcontext);
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, String.valueOf(mcontext.getStateContext().getPcdId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCESS, String.valueOf(mcontext.getStateContext().getProcessId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, mcontext.getStateContext().getNumexp());
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGE, String.valueOf(mcontext.getStateContext().getStageId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, String.valueOf(mcontext.getStateContext().getStagePcdId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASK, String.valueOf(mcontext.getStateContext().getTaskId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASKPCD, String.valueOf(mcontext.getStateContext().getTaskPcdId()));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_ENTITYID, String.valueOf(entityId));
		meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_REGENTITYID, String.valueOf(mcontext.getStateContext().getKey()));

		// Ejecución de la regla que asigna el formulario
		Object oFrmEdit = meventmgr.executeRule(ruleId);
		if (oFrmEdit != null) {

			// La regla retorna el identificador de formulario
			if (oFrmEdit instanceof Integer) {

    			// Obtener el formulario a partir del identificador retornado al ejecutar la regla
				try {
					application = new CTApplicationDAO(cnt, ((Integer) oFrmEdit).intValue());
				} catch (ISPACNullObject e) {
				}
			}
			// La regla retorna el formulario
			else if (oFrmEdit instanceof CTApplicationDAO) {

				application = (CTApplicationDAO) oFrmEdit;
			}
		}

		if (application == null) {

			// Error en la regla por no retornar un formulario
			CTRuleDAO rule = new CTRuleDAO(cnt, ruleId);
			throw new ISPACInfo("exception.entities.form.rule.empty", new Object[] {rule.getString("NOMBRE")});
		}

		return application;
    }

    /**
     * Obtiene la aplicación asociada a la entidad.
     *
     * @param cnt conexión
     * @param entityId identificador de la entidad para la que se busca la aplicación
     * @return aplicación solicitada
     * @throws ISPACException
     */
    protected CTApplicationDAO getEntityApplication(DbCnt cnt, int entityId) throws ISPACException {

        CTApplicationDAO application = null;

        CTEntityDAO ctEntity = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
        application = ctEntity.getApplication(cnt);

        return application;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getStageTasks(java.lang.String, int)
     */
    public IItemCollection getStageTasks(String numexp, int stagepcd) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            String query = "WHERE ID_FASE_PCD = " + stagepcd + " AND NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
            return EntityFactoryDAO.getInstance().queryEntities(cnt, ISPACEntities.DT_ID_TRAMITES, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getStageTasks("
                + numexp + ", " + stagepcd + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getExpedientTasks(java.lang.String)
     */
    public IItemCollection getExpedientTasks(String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            String query = "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
            return EntityFactoryDAO.getInstance().queryEntities(cnt, ISPACEntities.DT_ID_TRAMITES, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getExpedientTasks("
                + numexp + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getTask(int)
     */
    public IItem getTask(int taskProcessId) throws ISPACException {

        IItem task = null;
        DbCnt cnt = mcontext.getConnection();
        try
        {
            String query = "WHERE id_tram_exp = " + taskProcessId;
            IItemCollection collection =  EntityFactoryDAO.getInstance().queryEntities(cnt, ISPACEntities.DT_ID_TRAMITES, query).disconnect();
            if (collection.next())
                task = collection.value();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getTask("
                + taskProcessId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
        return task;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityTask(int)
     */
    public IItem getEntityTask(int taskId) throws ISPACException {

        IItem task = null;
        DbCnt cnt = mcontext.getConnection();
        try
        {
            String query = "WHERE id = " + taskId;
            IItemCollection collection = EntityFactoryDAO.getInstance().queryEntities(cnt, ISPACEntities.DT_ID_TRAMITES, query).disconnect();
            if (collection.next())
                task = collection.value();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityTask("
                + taskId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
        return task;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#countEntities(int, java.lang.String)
     */
    public int countEntities(int entityId, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().countEntities(cnt,entityId,query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:countEntities(" + entityId + ", " + query + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#countProcessEntities(int, java.lang.String)
     */
    public int countProcessEntities(int entityId, String numexp) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            IEntityDef entdef = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
            String query = " WHERE " + entdef.getKeyNumExp() + " = '" + DBUtil.replaceQuotes(numexp) + "'";

            return EntityFactoryDAO.getInstance().countEntities(cnt,entityId,query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:countProcessEntities(" + entityId + ", " + numexp + ")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public int countStageDocumentsInSignCircuit(int stageId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
			String query = "WHERE ID_FASE=" + stageId
				+ " AND ID_TRAMITE=0" // Documentos asociados a la fase
				+ " AND ESTADOFIRMA='" + SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA + "'";

            return EntityFactoryDAO.getInstance().countEntities(cnt, SpacEntities.SPAC_DT_DOCUMENTOS, query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:countStageDocumentsInSignCircuit(" + stageId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public int countTaskDocumentsInSignCircuit(int taskId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
			String query = "WHERE ID_TRAMITE=" + taskId
				+ " AND ESTADOFIRMA='" + SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA + "'";

            return EntityFactoryDAO.getInstance().countEntities(cnt, SpacEntities.SPAC_DT_DOCUMENTOS, query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:countTaskDocumentsInSignCircuit(" + taskId + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(java.util.Map, java.lang.String)
     */
    public IItemCollection queryEntities(Map tableentitymap, String query) throws ISPACException {

        return queryEntities(tableentitymap, query, false);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(java.util.Map, java.lang.String, boolean)
     */
    public IItemCollection queryEntities(Map tableentitymap, String query,boolean distinct) throws ISPACException {

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
                    joinfactory.addEntity(cnt,((Number)enttable).intValue(),prefix);
                else
                    joinfactory.addTable((String)enttable,prefix);
            }
            if (distinct)
                return joinfactory.queryDistinctTableJoin(cnt,query).disconnect();
            else
                return joinfactory.queryTableJoin(cnt,query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities()",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#queryEntities(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public IItemCollection queryEntities(String tablename, String keyproperty, String sequence, String query) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
            return EntityFactoryDAO.getInstance().queryEntities(cnt, tablename, keyproperty, sequence, null, query).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities("+tablename+"," +keyproperty+", "+sequence+", "+query+")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getSubstitute(int, java.lang.String)
	 */
	public String getSubstitute(int entityId, String value) throws ISPACException {

        String query = "WHERE " + ICatalogAPI.VALOR_FIELD_NAME.toUpperCase()
        			 + " = '" + DBUtil.replaceQuotes(value) + "'"
        			 + " AND " + ICatalogAPI.ESTADO_FIELD_NAME.toUpperCase()
        			 + " = 1";

        DbCnt cnt = mcontext.getConnection();

        try {
        	IItemCollection itemcol = EntityFactoryDAO.getInstance().queryEntities(cnt, entityId, query).disconnect();

        	if (!itemcol.next()) {
    			return "";
        	}

    		IItem item = itemcol.value();
    		return item.getString(ICatalogAPI.SUSTITUTO_FIELD_NAME.toUpperCase());
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSubstitute(" + entityId + "," + query + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getSubstitute(java.lang.String, java.lang.String)
	 */
	public String getSubstitute(String entityname, String value) throws ISPACException {

		IItem item = getSubstituteIItem(entityname, value);

		if (item == null) {
			return "";
		}

		return item.getString(ICatalogAPI.SUSTITUTO_FIELD_NAME.toUpperCase());
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getSubstituteIItem(java.lang.String, java.lang.String)
	 */
	public IItem getSubstituteIItem(String entityname, String value) throws ISPACException {

        String query = "WHERE " + ICatalogAPI.VALOR_FIELD_NAME.toUpperCase()
        			 + " = '" + DBUtil.replaceQuotes(value) + "'"
        			 + " AND " + ICatalogAPI.ESTADO_FIELD_NAME.toUpperCase()
        			 + " = 1";

        DbCnt cnt = mcontext.getConnection();

        try {
        	IItemCollection itemcol = EntityFactoryDAO.getInstance().queryEntities(cnt, entityname, query).disconnect();

        	if (!itemcol.next()) {
    			return null;
        	}

    		return itemcol.value();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getSubstituteIItem(" + entityname + "," + value + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
	}

    public EntityResources getEntityResources(int entityId) throws ISPACException {

    	EntityResources resources = new EntityResources();
    	resources.setEntityId(entityId);

    	// Obtener todos los recursos de la entidad
        IItemCollection col = queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES,
        		" WHERE ID_ENT=" + entityId);
        while (col.next()) {
        	IItem resource = col.value();
        	resources.addResource(resource.getString("IDIOMA"),
        			resource.getString("CLAVE"), resource.getString("VALOR"));
        }

        return resources;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityResources(int, java.lang.String)
     */
    public IItemCollection getEntityResources(int entityId, String language)
    throws ISPACException
    {
    	StringBuffer query = new StringBuffer();

    	query.append(" WHERE ID_ENT=")
    		 .append(entityId)
    		 .append(" AND IDIOMA='")
    		 .append(language)
    		 .append("'");

        return queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntityResourceValue(int, java.lang.String, java.lang.String)
     */
    public String getEntityResourceValue(int entityId, String language, String key)
    throws ISPACException
    {
    	IItem entityResource = getEntityResource(entityId, key, language);

    	if (entityResource == null) {
			return "";
    	}

		return entityResource.getString("VALOR");
    }

    public IItem getEntityResource(int entityId, String key, String language)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

        try
        {
            return EntityResourceDAO.getEntityResource(cnt, entityId, key, language);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityResource("
                + entityId + ", " + key + ", " + language + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public IItemCollection getEntityResources(int entityId, String keys, String language)
	throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

        try
        {
            return EntityResourceDAO.getEntityResources(cnt, entityId, keys, language).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityResources("
                + entityId + ", " + keys + ", " + language + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public IItemCollection getEntityOtherResources(int entityId, String fieldKeys, String language)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

        try
        {
        	Map properties = new HashMap();
        	properties.put("CLAVE", null);
        	properties.put("VALOR", null);

        	return EntityResourceDAO.getEntityOtherResources(cnt, entityId, fieldKeys, language).disconnect(properties);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityOtherResources("
                + entityId + ", " + fieldKeys + ", " + language + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public IItemCollection getEntityOtherResourceKeys(int entityId, String fieldKeys)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

        try
        {
        	Map properties = new HashMap();
        	properties.put("CLAVE", null);

        	return EntityResourceDAO.getEntityOtherResourceKeys(cnt, entityId, fieldKeys).disconnect(properties);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:getEntityOtherResources("
                + entityId + ", " + fieldKeys + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

    public boolean isEntityResourceKey(int entityId, String key)
    throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();

        try
        {
        	return EntityResourceDAO.isEntityResourceKey(cnt, entityId, key);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:isEntityResourceKey("
                + entityId + ", " + key + ")", ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#cloneRegEntity(int, int, java.lang.String, java.lang.String[])
	 */
	public boolean cloneRegEntity(int entityId, int key, String newNumExp, String[] idFieldsToClone) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        try
        {
        	// Entidad a clonar
        	IItem itemSource = getEntity(cnt, entityId, key);

        	// Nueva entidad en la que se copiarán los campos a clonar
        	EntityDAO entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);
        	entityDAO.createNew(cnt);
        	IItem newItem = entityDAO;

        	// La clave primaria y el número de expediente no se clonan
    		IItem itemCat = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, entityId);
    		String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
    		String fieldPk = itemCat.getString("CAMPO_PK");
    		newItem.set(fieldNumExp, newNumExp);

    		List excludedList = new ArrayList();
    		excludedList.add(fieldNumExp);
    		excludedList.add(fieldPk);

    		List entityFieldsToClone = null;

    		if (idFieldsToClone != null) {

    			// Obtener los campos de la definición de la entidad
    			EntityDef entityDef = EntityDef.parseEntityDef(itemCat.getString("DEFINICION"));

    			EntityField entityField;
    			entityFieldsToClone = new ArrayList();
    			for (int i = 0; i < idFieldsToClone.length; i++) {

    				entityField = entityDef.findField(Integer.parseInt(idFieldsToClone[i]));
    				if (entityField != null) {
    					entityFieldsToClone.add(entityField.getPhysicalName().toUpperCase());
    				}
    			}
    		}

    		copyRegEntityData(itemSource, newItem, excludedList, entityFieldsToClone);
    	}
        catch (ISPACException ie)
        {
            throw new ISPACException("exception.entities.clone", "("+ entityId + ", " + key + ")");
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }

		return true;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#copyRegEntityData(int, int, ieci.tdw.ispac.api.item.IItem, java.util.List, java.lang.String[])
	 */
	public void copyRegEntityData(int entityId, int keyId, IItem item, List excludedList, String[] idFieldsToCopy) throws ISPACException {

		IItem itemSource = getEntity(entityId, keyId);

		List entityFieldsToCopy = null;

		if (idFieldsToCopy != null) {

			// Obtener la entidad
			IItem entity = getCatalogEntity(entityId);

			// Obtener los campos de la definición de la entidad
			EntityDef entityDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));

			EntityField entityField;
			entityFieldsToCopy = new ArrayList();
			for (int i = 0; i < idFieldsToCopy.length; i++) {

				entityField = entityDef.findField(Integer.parseInt(idFieldsToCopy[i]));
				if (entityField != null) {
					entityFieldsToCopy.add(entityField.getPhysicalName().toUpperCase());
				}
			}
		}

		copyRegEntityData(itemSource, item, excludedList, entityFieldsToCopy);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#copyRegEntityData(ieci.tdw.ispac.api.item.IItem, ieci.tdw.ispac.api.item.IItem, java.util.List, java.util.List)
	 */
	public void copyRegEntityData(IItem itemSource, IItem item, List excludedList, List copyList) throws ISPACException {

		Iterator iterator = itemSource.getProperties().iterator();
		while (iterator.hasNext()) {

			Property property = (Property) iterator.next();
			String propertyName = property.getName();

			if ((excludedList != null) &&
				(excludedList.contains(propertyName))) {
				continue;
			}

			if (((copyList != null) &&(!copyList.contains(propertyName)))
					|| copyList==null) {
				continue;
			}

			Object value = itemSource.get(propertyName);
			item.set(propertyName, value);
		}

		item.store(mcontext);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#cloneRelatedExpedient(java.lang.String, java.lang.String)
	 */
	public void cloneRelatedExpedient(String numexp, String newNumExp) throws ISPACException {

		cloneRelatedExpedient(numexp, newNumExp, "NUMEXP_PADRE");
		cloneRelatedExpedient(numexp, newNumExp, "NUMEXP_HIJO");
	}

	/**
	 * Clona los expedientes relacionados de un expediente con un rol suminitrado
	 * en otro expediente.
	 *
	 * @param numexp número de expediente del que se toman sus expedientes relacionados
	 * @param newNumExp nuevo número de expediente para el que se establecen los expedientes relacionados
	 * @param fieldRol rol de la relación
	 * @throws ISPACException
	 */
	protected void cloneRelatedExpedient(String numexp, String newNumExp, String fieldRol) throws ISPACException {

		List excludedList = new ArrayList();
		String query = "WHERE " + fieldRol + " = '" + DBUtil.replaceQuotes(numexp) + "'";
		IItemCollection itemCol = queryEntities(SpacEntities.SPAC_EXP_RELACIONADOS, query);
		IItem itemCat = getCatalogEntity(SpacEntities.SPAC_EXP_RELACIONADOS);

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	//DbCnt cnt = mcontext.getConnection();

			while(itemCol.next()) {

				IItem itemSource = itemCol.value();
				IItem item = createEntity(SpacEntities.SPAC_EXP_RELACIONADOS);
				excludedList = new ArrayList();
				excludedList.add(fieldRol);
				excludedList.add(itemCat.getString("CAMPO_PK"));
				item.set(fieldRol, newNumExp);

				copyRegEntityData(itemSource, item, excludedList, null);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
	    }
	    finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntitiesResourcesMap(java.lang.String[])
	 */
	public Map getEntitiesResourcesMap(String entitiesNames[])
	throws ISPACException
	{
		Map entitiesLabelMap = new HashMap();

		if ((entitiesNames != null) &&
			(entitiesNames.length > 0)) {

	        StringBuffer querySelectEntitiesNames = new StringBuffer();
	        for (int i = 0; i < entitiesNames.length; i++) {

	        	if (querySelectEntitiesNames.length() > 0)
	        		querySelectEntitiesNames.append(" OR ");

				querySelectEntitiesNames.append("CLAVE = '").append(entitiesNames[i]).append("' ");
			}

			StringBuffer queryResources = new StringBuffer();
			queryResources.append(" WHERE IDIOMA='")
						  .append(mcontext.getAppLanguage())
						  .append("' AND ( ")
						  .append(querySelectEntitiesNames)
						  .append(" )");

			IItemCollection col = queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, queryResources.toString());
			entitiesLabelMap = col.toMap("CLAVE");
		}

		return entitiesLabelMap;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntitiesExtendedItemBean(ieci.tdw.ispac.api.item.IItemCollection)
	 */
	public List getEntitiesExtendedItemBean(IItemCollection entitiesCollection) throws ISPACException {

       return getEntitiesExtendedItemBean("NOMBRE", entitiesCollection);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IEntitiesAPI#getEntitiesExtendedItemBean(java.lang.String, ieci.tdw.ispac.api.item.IItemCollection)
	 */
	public List getEntitiesExtendedItemBean(String keyNameProperty, IItemCollection entitiesCollection)
	throws ISPACException
	{
		List beans = new ArrayList();

		if ((entitiesCollection != null) &&
			(entitiesCollection.next())) {

	        beans = CollectionBean.getBeanList(entitiesCollection);

	        // Obtener los nombres de las entidades
	        StringBuffer querySelectEntitiesNames = new StringBuffer();
	        for (Iterator iter = beans.iterator(); iter.hasNext();) {

				ItemBean itemBean = (ItemBean) iter.next();
				String nombre = (String)itemBean.getProperty(keyNameProperty);

				if (querySelectEntitiesNames.length() > 0)
					querySelectEntitiesNames.append(" OR ");

				querySelectEntitiesNames.append("CLAVE = '").append(nombre).append("' ");
			}

			StringBuffer queryResources = new StringBuffer();
			queryResources.append(" WHERE IDIOMA='")
						  .append(mcontext.getAppLanguage())
						  .append("' AND ( ")
						  .append(querySelectEntitiesNames)
						  .append(" )");

			IItemCollection col = queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, queryResources.toString());
			Map entitiesLabelMap = col.toMap("CLAVE");

			// Iterar las entidades para rellenar el nombre de la entidad
			for (Iterator iter = beans.iterator(); iter.hasNext();) {

				ItemBean element = (ItemBean) iter.next();

				EntityDAO entResource = ((EntityDAO)entitiesLabelMap.get(element.getString(keyNameProperty)));
				if (entResource != null) {

					element.setProperty("ETIQUETA", entResource.get("VALOR"));
				}
			}
		}

		return beans;
	}

	/**
	 * Obtiene el formulario por defecto para la entidad
	 * @param entityId
	 * @return
	 * @throws ISPACException
	 */
	public Object getFormDefault(int entityId) throws ISPACException {
		try {
			String query = "WHERE ID = " + entityId;
			IItemCollection items = mcontext.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, query);
			if (items.next()){
				IItem item = (IItem)items.iterator().next();
				return item.get("FRM_EDIT");
			}

			return null;
		}catch (ISPACNullObject e) {
			return null;
		}
	}

	/**
	 *
	 * @param numexp: Expediente
	 * @param idPhasePcd: Fase dentro del procedimiento en la que esta el trámite
	 * @param idTaskPcd: Trámite dentro del procedimiento del que queremos obtener la informacion
	 * @return
	 * @throws ISPACException
	 */
    public IItemCollection getTasksExpInPhase(String numexp, int idPhasePcd, int idTaskPcd) throws ISPACException {

    	Map tblEntityMap = new HashMap();
    	tblEntityMap.put("SPAC_DT_TRAMITES", "SPAC_DT_TRAMITES");

    	StringBuffer query = new StringBuffer()
    		.append(" WHERE SPAC_DT_TRAMITES.NUMEXP = '")
    		.append(DBUtil.replaceQuotes(numexp))
    		.append("' AND SPAC_DT_TRAMITES.ID_TRAM_PCD = ")
    		.append(idTaskPcd)
    		.append(" AND SPAC_DT_TRAMITES.ID_FASE_PCD = ")
    		.append(idPhasePcd)
    		.append(" ORDER BY SPAC_DT_TRAMITES.FECHA_INICIO");

    	return queryEntities(tblEntityMap, query.toString());
    }

    /**
     * Comprueba si un trámite no se puede borrar
     *
     * @param taskId Identificador del trámite
     * @return
     * @throws ISPACException
     */
	public boolean undeleteTask(int taskId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();

    	// No se puede eliminar un trámite cuando
    	// tenga documentos que han generado registro de salida o que han iniciado notificación telemática (documento con bloqueo total)
        String query = "WHERE ID_TRAMITE = " + taskId
        			 + " AND ( TP_REG = '" + RegisterType.SALIDA + "' AND NREG <> '' OR BLOQUEO = '" + EntityLockStates.TOTAL_LOCK +"' )";

        try {
        	int count = EntityFactoryDAO.getInstance().countEntities(cnt, SpacEntities.SPAC_DT_DOCUMENTOS, query);

        	if (count > 0) {
    			return true;
        	}
    		return false;
        }
        catch (ISPACException ie) {
            throw new ISPACException("Error en EntitiesAPI:undeleteTask("+taskId+")",ie);
        }
        finally {
            mcontext.releaseConnection(cnt);
        }
	}

	/**
	 * Elimina un documento a partir del identificador del documento.
	 * Esto conlleva eliminar el registro con los datos del documento,
	 * los documento físicos asociados (fichero original y fichero firmado)
	 * y cualquier referencia al documento en un proceso de firma.
	 *
	 * @param documentId Identificador del documento
	 * @throws ISPACException
	 */
	public void deleteDocument(int documentId) throws ISPACException {

		// Obtener el documento
    	IItem document = getEntity(SpacEntities.SPAC_DT_DOCUMENTOS, documentId);

    	// Eliminar el documento
    	deleteDocument(document);
	}

	public void deleteAllDocumentsOfNumExp(String numExp)throws ISPACException{

		IItemCollection itemcol=getEntities(SpacEntities.SPAC_DT_DOCUMENTOS, numExp);
		while(itemcol.next()){
			IItem document=itemcol.value();
			deleteDocument(document);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Se han eliminado todos los documentos del expediente"+numExp);
		}
	}

	/**
	 * Desasocia el documento al registro de la entidad.
	 *
	 * @param document Documento a desasociar
	 * @param deleteDataDocument Indicador para borrar el registro de SPAC_DT_DOCUMENTOS
	 * @throws ISPACException
	 */
	public void deleteDocumentFromRegEntity(IItem document, boolean deleteDataDocument) throws ISPACException {

        try {

        	if (deleteDataDocument) {

        		document.delete(mcontext);
        	}
        	else {
	        	document.set("ID_REG_ENTIDAD", "0");
	        	document.set("ID_ENTIDAD", "0");
	        	document.store(mcontext);
        	}
	    }
	    finally {
		}
	}

	/**
	 * Elimina el documento.
	 * Esto conlleva eliminar el registro con los datos del documento,
	 * los documento físicos asociados (fichero original y fichero firmado)
	 * y cualquier referencia al documento en un proceso de firma.
	 *
	 * @param document Documento a borrar
	 * @throws ISPACException
	 */
	public void deleteDocument(IItem document) throws ISPACException {

		IInvesflowAPI invesflowAPI = mcontext.getAPI();
		ISignAPI singAPI = invesflowAPI.getSignAPI();
		IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();

    	// Obtener las referencias al documento en los repositorios
    	String docref = document.getString("INFOPAG");
    	String docrefRDE = document.getString("INFOPAG_RDE");

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

        try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	//DbCnt cnt = mcontext.getConnection();

			// Eliminar las referencias al documento en un circuito de firma
	        IItemCollection itemcol = singAPI.getStepsByDocument(document.getKeyInt());
			while(itemcol.next()) {

			    IItem stepSignCircuit = itemcol.value();
			    stepSignCircuit.delete(mcontext);
			}

	        // Eliminar el registro que almacena los datos del documento
			document.delete(mcontext);

	        Object connectorSession = null;
	    	try {
	    		connectorSession = genDocAPI.createConnectorSession();

		        // Referencia del documento en el gestor documental
		    	if (StringUtils.isNotEmpty(docref)) {

		            // Eliminar el documento fisico del gestor documental
		            genDocAPI.deleteDocument(connectorSession, docref);
		    	}

		    	// Borrar tambien el documento en el repositorio de documentos electronicos (documento firmado)
		    	if (StringUtils.isNotEmpty(docrefRDE)) {

		    		// Eliminar el documento fisico del gestor documental
		            genDocAPI.deleteDocument(connectorSession, docrefRDE);
		    	}
			}catch(ISPACNullObject e){
					logger.error("Documento no encontrado "+e.toString());
			}
	    	finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
	    	}

	    	String numExpediente = document.getString("NUMEXP");
	    	String idDoc = String.valueOf(document.getKeyInt());
	    	auditEliminacionDocumento(idDoc, numExpediente);
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
	    }
	    finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}


	/**
	 * @param sDocRef
	 */
	private void auditEliminacionDocumento(String idDoc, String numExp) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventDocumentoBajaVO evento = new IspacAuditEventDocumentoBajaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setIdDocumento(idDoc);
		evento.setNumExpediente(numExp);

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del documento");
		auditoriaManager.audit(evento);
	}

	/**
	 * Elimina un trámite a partir del identificador del trámite.
	 * Esto conlleva eliminar toda la información del trámite en el procedimiento,
	 * incluyento los documentos generados.
	 *
	 * @param documentId Identificador del trámite
	 * @throws ISPACException
	 */
	public void deleteTask(int taskId) throws ISPACException {

		ITXTransaction txTransaction = mcontext.getAPI().getTransactionAPI();

        // Obtiene la lista de documentos generados en el trámite
        String query = "WHERE ID_TRAMITE = " + taskId;
        IItemCollection itemcol = queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	//DbCnt cnt = mcontext.getConnection();

			// Se eliminan los documentos generados en el trámite
			while(itemcol.next()) {

			    IItem document = itemcol.value();
			    deleteDocument(document);
			}

	    	// Eliminar el trámite
	    	txTransaction.deleteTask(taskId);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
	    }
	    finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	/**
    * Modifica la ordenacion de los valores de la tabla de validacion atendiendo al tipo de ordenacion que se realiza
    * @param entityId: Identificador de la tabla de validación sobre la que se realizará la ordenación
    * @param tipoOrdenacion: Por valor o por sustituto
    * @return
    * @throws ISPACException
    */
	 public void orderValuesTblValidation(int entityId, String tipoOrdenacion) throws ISPACException {

    	// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				mcontext.beginTX();
			}
	    	//DbCnt cnt = mcontext.getConnection();

			IItemCollection itemcol = queryEntitiesForUpdate(entityId, "ORDER BY " + tipoOrdenacion);

			int cont = 1;
			// Se actualiza el orden de los registros
			while (itemcol.next()) {

			    IItem valueTbl = itemcol.value();
			    valueTbl.set("ORDEN", cont);
			    valueTbl.store(mcontext);
			    cont += 1;
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
	    }
	    finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
    }

	public int countResultQuery(String entityName, String query)throws ISPACException {

		DbCnt cnt = mcontext.getConnection();

        try
        {
            return EntityFactoryDAO.getInstance().queryCountEntities(cnt, entityName, query);
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en EntitiesAPI:queryEntities("+entityName+", "+query+")",ie);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
	}

	public SearchResultVO getLimitedQueryEntities(String entityName, String query, String order) throws ISPACException{

    	DbCnt cnt = mcontext.getConnection();
        try
        {
        	EntityFactoryDAO entityFactoryDao=EntityFactoryDAO.getInstance();
        	int numTotal=entityFactoryDao.countEntities(cnt, entityName, query);
        	CollectionDAO results=null;
        	SearchResultVO searchResultVO= new SearchResultVO();
        	String sMaxResultados = ISPACConfiguration.getInstance().get(ISPACConfiguration.MAX_TBL_SEARCH_VALUES);
        	int max=0;
    		if(StringUtils.isNotBlank(sMaxResultados)){
    			max=TypeConverter.parseInt(sMaxResultados.trim(),0);
    			if(max>0){
    				searchResultVO.setNumMaxRegistros(max);
    				searchResultVO.setNumTotalRegistros(numTotal);

    			}
    		}
    		results=entityFactoryDao.queryEntities(cnt, entityName, query, order,max);
    		searchResultVO.setResultados(results.disconnect());
        	 //En funcion de que bbdd estemos
             return  searchResultVO;
        }
        catch (ISPACException ie)
        {
        	if(logger.isDebugEnabled()){
        		logger.debug("Error en EntitiesAPI:getLimitedQueryEntities("+entityName+", "+query+", "+order+")", ie);
        	}
        	throw new ISPACException("Error en EntitiesAPI:getLimitedQueryEntities("+entityName+", "+query+", "+order+")",ie);
        }
        finally
        {
        	mcontext.releaseConnection(cnt);
        }
	}

}