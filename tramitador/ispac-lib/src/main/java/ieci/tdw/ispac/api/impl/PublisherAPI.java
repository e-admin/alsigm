package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACIsInUseException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubErrorDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubHitoActivoDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubLastMilestoneTreatedDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubReglaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.InetUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class PublisherAPI implements IPublisherAPI {


	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(PublisherAPI.class);
	

	
	/**
	 * Contexto del cliente.
	 */
    private ClientContext context;

    
    /**
     * Constructor.
     * @param context Contexto del cliente.
     */
	public PublisherAPI(ClientContext context) {
		this.context = context;
	}

	
	/*
	 * 
	 * Métodos para gestionar las ACCIONES de publicación
	 *  
	 */

	/**
	 * Obtiene la información de las acciones.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getActions() throws ISPACException {
		return getActions(null);
	}

	/**
	 * Obtiene la información de las acciones.
	 * @param pattern Patrón con el nombre de la acción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getActions(String pattern) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		String query = null;
		if (StringUtils.isNotBlank(pattern)) {
			query = "WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(pattern) + "%'";
		}
		
        IItemCollection actions = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_ACTIONS, query);
        return actions;
	}

	/**
	 * Obtiene la información de una acción.
	 * @param actionId Identificador de la acción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getAction(int actionId) throws ISPACException {
	
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItem action = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_ACTIONS, actionId);
		return action;
	}
	
	/**
	 * Elimina una acción.
	 * @param actionId Identificador de la acción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteAction(int actionId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_PUB_RULES, "WHERE ID_ACCION =" + actionId);
		if (count > 0) {
			throw new ISPACIsInUseException();
		}

		IItem action = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_ACTIONS, actionId);
		action.delete(context);
	}


	/*
	 * 
	 * Métodos para gestionar las APLICACIONES de publicación
	 *  
	 */
	
	/**
	 * Obtiene la información de las aplicaciones.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getApplications() throws ISPACException {
		return getApplications(null);
	}

	/**
	 * Obtiene la información de las aplicaciones.
	 * @param pattern Patrón con el nombre de la aplicación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getApplications(String pattern) throws ISPACException {

		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		String query = null;
		if (StringUtils.isNotBlank(pattern)) {
			query = "WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(pattern) + "%'";
		}
		
        IItemCollection applications = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_APPLICATIONS, query);
        return applications;
	}

	/**
	 * Obtiene la información de una aplicación.
	 * @param applicationId Identificador de la aplicación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getApplication(int applicationId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItem application = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_APPLICATIONS, applicationId);
		return application;
	}

	/**
	 * Elimina una aplicación.
	 * @param applicationId Identificador de la aplicación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteApplication(int applicationId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		// Aplicación en uso en las reglas
		int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_PUB_RULES, "WHERE ID_APLICACION =" + applicationId);
		if (count > 0) {
			throw new ISPACIsInUseException();
		}
			
		// Aplicación en uso en los hitos
		count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_PUB_MILESTONES, "WHERE ID_APLICACION =" + applicationId);
		if (count > 0) {
			throw new ISPACIsInUseException();
		}
				
		// Aplicación en uso en los errores
		count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_PUB_ERRORS, "WHERE ID_APLICACION =" + applicationId);
		if (count > 0) {
			throw new ISPACIsInUseException();
		}

		IItem application = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_APPLICATIONS, applicationId);
		application.delete(context);
	}


	/*
	 * 
	 * Métodos para gestionar las CONDICIONES de publicación
	 *  
	 */

	/**
	 * Obtiene la información de las condiciones.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getConditions() throws ISPACException {
		return getConditions(null);
	}

	/**
	 * Obtiene la información de las condiciones.
	 * @param pattern Patrón con el nombre de la condición.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getConditions(String pattern) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		String query = null;
		if (StringUtils.isNotBlank(pattern)) {
			query = "WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(pattern) + "%'";
		}
		
        IItemCollection conditions = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_CONDITIONS, query);
        return conditions;
	}

	/**
	 * Obtiene la información de una condición.
	 * @param conditionId Identificador de la condición.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getCondition(int conditionId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItem condition = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_CONDITIONS, conditionId);
		return condition;
	}

	/**
	 * Elimina una condición.
	 * @param conditionId Identificador de la condición.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteCondition(int conditionId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		// Condición en uso en las reglas
		int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_PUB_RULES, "WHERE ID_CONDICION =" + conditionId);
		if (count > 0) {
			throw new ISPACIsInUseException();
		}
			
		IItem condition = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_CONDITIONS, conditionId);
		condition.delete(context);
	}

	
	/*
	 * 
	 * Métodos para gestionar los ERRORES de publicación
	 *  
	 */

	/**
	 * Obtiene la información de los errores.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getErrors() throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItemCollection errors = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_ERRORS, null);
		return errors;
	}

	/**
	 * Obtiene la información del error.
	 * @param milestoneId Identificador del hito.
	 * @param applicationId Identificador de la aplicación.
	 * @param systemId Identificador del sistema.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getError(int milestoneId, int applicationId, String systemId) 
			throws ISPACException {
		return PubErrorDAO.getError(context.getConnection(), 
				milestoneId, applicationId, systemId);
	}

	/*
	 * 
	 * Métodos para gestionar los HITOS de publicación
	 *  
	 */
	
	/**
	 * Obtiene la información de los hitos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getMilestones() throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItemCollection milestones = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_MILESTONES, 
				"ORDER BY ID_PCD, ID_FASE, ID_TRAMITE, TIPO_DOC, ID_OBJETO");
		return milestones;
	}

	/**
	 * Obtiene la información de los hitos.
	 * @param status Estado de los hitos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getMilestones(int status) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItemCollection milestones = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_MILESTONES, 
				"WHERE ESTADO= " + status + " ORDER BY ID_PCD, ID_FASE, ID_TRAMITE, TIPO_DOC, ID_OBJETO");
		return milestones;
	}

	/**
	 * Obtiene la información de un hito.
	 * @param milestoneId Identificador del hito.
	 * @param applicationId Identificador de la aplicación.
	 * @param systemId Identificador del sistema.
	 * @param status Estado del hito.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getMilestone(int milestoneId, int applicationId, String systemId, int status) 
			throws ISPACException {

		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		IItem milestone = null;
		
		IItemCollection milestones = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_MILESTONES, 
				"WHERE ESTADO=" + status 
					+ " AND ID_HITO=" + milestoneId 
					+ " AND ID_APLICACION = " + applicationId 
					+ " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(systemId) + "'");
		if (milestones.next()) {
			milestone = milestones.value();
		}
		
		return milestone;
	}
	
	public IItem getMilestone(int milestoneId, int applicationId, String systemId) 
			throws ISPACException {
		return PubHitoActivoDAO.getHitoActivo(context.getConnection(), 
				milestoneId, applicationId, systemId);
	}
	
	
	/**
	 * Crea un nuevo hito de publicación. 
	 * @param milestoneId Identificador del hito en el sistema externo.
	 * @param pcdId Identificador del procedimiento.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @param taskId Identificador del trámite en el procedimiento.
	 * @param docType Tipo de documento.
	 * @param objectId Identificador del objeto (número de expediente).
	 * @param eventId Identificador del evento que da lugar al hito.
	 * @param infoId Identificador del subtipo de evento para hitos informativos.
	 * @param infoAux Datos extra del hito en el sistema externo.
	 * @param applicationId Identificador de la aplicación que debe procesar el hito.
	 * @param systemId Identificador del sistema de donde proviene el hito.
	 * @return Información del hito creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem addMilestone(int milestoneId, int pcdId, int stageId, int taskId, 
			int docType, String objectId, int eventId, int infoId, String infoAux,  
			int applicationId, String systemId) throws ISPACException {
	
		PubHitoActivoDAO milestone = null;
		
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;
		
		try {
			
			if (!ongoingTX) {
				context.beginTX();
			}
			
			// Crear el hito en la tabla de publicación
			milestone = new PubHitoActivoDAO(context.getConnection());
			milestone.createNew(context.getConnection());
			
	        milestone.set("ID_HITO", milestoneId);
	        milestone.set("ID_PCD", pcdId);
	        milestone.set("ID_FASE", stageId);
	        milestone.set("ID_TRAMITE", taskId);
	        milestone.set("TIPO_DOC", docType);
	        milestone.set("ID_OBJETO", objectId);
	        milestone.set("ID_INFO", infoId);
	        milestone.set("INFO_AUX", infoAux);
	        milestone.set("ID_EVENTO", eventId);
	        milestone.set("ESTADO", MILESTONE_STATUS_INITIAL);
	        milestone.set("ID_APLICACION", applicationId);
	        milestone.set("IP_MAQUINA", InetUtils.getLocalHostAddress());
	        milestone.set("FECHA", new Timestamp(System.currentTimeMillis()));
	        milestone.set("ID_SISTEMA", systemId);
	        milestone.store(context);
	        
	        // Actualizar el último hito leído del sistema
	        if (StringUtils.isNotBlank(systemId)) {
	        	updateLastMilestoneTreated(systemId, milestoneId);
	        }
	        
	        bCommit = true;
	        
		} finally {
			
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
        
        return milestone;
	}
	
	public IItem updateMilestone(int milestoneId, int pcdId, int stageId, int taskId, 
			int docType, String objectId, int eventId, int infoId, String infoAux,  
			int applicationId, String systemId, int id_status) throws ISPACException {
		
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;
		
		try {
			
			if (!ongoingTX) {
				context.beginTX();
			}
			
			PubHitoActivoDAO milestone = PubHitoActivoDAO.getHitoActivo(context.getConnection(), 
					milestoneId, applicationId, systemId);

			if (milestone != null) {
		        milestone.set("ID_PCD", pcdId);
		        milestone.set("ID_FASE", stageId);
		        milestone.set("ID_TRAMITE", taskId);
		        milestone.set("TIPO_DOC", docType);
		        milestone.set("ID_OBJETO", objectId);
		        milestone.set("ID_INFO", infoId);
		        milestone.set("INFO_AUX", infoAux);
		        milestone.set("ID_EVENTO", eventId);
		        milestone.set("ESTADO", id_status);
		        milestone.set("IP_MAQUINA", InetUtils.getLocalHostAddress());
		        milestone.set("FECHA", new Timestamp(System.currentTimeMillis()));
	
		        milestone.store(context);
	        
		        bCommit = true;
			}
	        
	        return milestone;
	        
		} finally {
			
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
	}
	
	/**
	 * Reactiva un hito erróneo eliminando su error
	 * 
	 * @param milestoneId Identificador del hito
	 * @param applicationId Identificador de la aplicación
	 * @param systemId Identificador del sistema
	 * @throws ISPACException
	 */
	public IItem reactivateMilestone(int milestoneId, int applicationId, String systemId) throws ISPACException {
		
		// Ejecución en un contexto transaccional
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				context.beginTX();
			}
	    	DbCnt cnt = context.getConnection();
			
			PubHitoActivoDAO milestone = PubHitoActivoDAO.getHitoActivo(cnt, milestoneId, applicationId, systemId);
			PubErrorDAO error = PubErrorDAO.getError(cnt, milestoneId, applicationId, systemId);
			
			if (error != null) {	
				// Borrar el error
				error.delete(cnt);
			}
			
			if (milestone != null) {
				// Reactivar el hito
				milestone.set("ESTADO", 0);
				milestone.store(cnt);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
			
			return milestone;
			
		} catch (Exception e) {
			logger.error("Error al reactivar el hito de publicación con error [" + milestoneId + ", " + applicationId + ", " + systemId + "]", e);
			throw new ISPACException("Error en reactivateErrorMilestone(" + milestoneId + ", "
					+ applicationId + ", " + systemId + ")", e);
		} finally {
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
	}
	
	public void deleteMilestone(int milestoneId, int applicationId, String systemId) 
			throws ISPACException {

		// Ejecución en un contexto transaccional
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				context.beginTX();
			}
			DbCnt cnt = context.getConnection();

			PubHitoActivoDAO milestone = PubHitoActivoDAO.getHitoActivo(cnt,
					milestoneId, applicationId, systemId);
			PubErrorDAO error = PubErrorDAO.getError(cnt, milestoneId,
					applicationId, systemId);

			if (error != null) {
				// Borrar el error
				error.delete(cnt);
			}

			if (milestone != null) {
				// Borrar el hito
				milestone.delete(cnt);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		} catch (Exception e) {
			logger.error("Error al eliminar el hito de publicación [" + milestoneId + ", " + applicationId + ", " + systemId + "]", e);
			throw new ISPACException("Error en deleteMilestone(" + milestoneId
					+ ", " + applicationId + ", " + systemId 
					+ ")", e);
		} finally {
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
	}	

	/*
	 * 
	 * Métodos para gestionar las REGLAS de publicación
	 *  
	 */
	
	/**
	 * Obtiene la información de las reglas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getRules() throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItemCollection rules = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_RULES, 
				"ORDER BY ID_PCD, ID_FASE, ID_TRAMITE, TIPO_DOC");
		return rules;
	}

	/**
	 * Obtiene la información de las reglas.
	 * @param pcdId Identificador del procedimiento.
	 * @param stageId Identificador de la fase.
	 * @param taskId Identificador del trámite.
	 * @param docTypeId Identificador del tipo de documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getRules(int pcdId, int stageId, int taskId,
			int docTypeId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		String query = "WHERE ID_PCD = " + pcdId
			 + " AND ID_FASE = " + stageId
			 + " AND ID_TRAMITE = " + taskId
			 + " AND TIPO_DOC = " + docTypeId
			 + " ORDER BY ID_EVENTO, ORDEN";

		IItemCollection rules = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_RULES, query);
		return rules;
	}

	/**
	 * Obtiene la información de las reglas.
	 * @param pcdId Identificador del procedimiento.
	 * @param stageId Identificador de la fase.
	 * @param taskId Identificador del trámite.
	 * @param docTypeId Identificador del tipo de documento.
	 * @param eventId Identificador del evento.
	 * @param infoId Identificador de la información
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getRules(int pcdId, int stageId, int taskId,
			int docTypeId, int eventId, int infoId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		String query = "WHERE (ID_PCD=" + pcdId + " OR ID_PCD<0)"
			 + " AND (ID_FASE=" + stageId + " OR ID_FASE<0)"
			 + " AND (ID_TRAMITE=" + taskId + " OR ID_TRAMITE<0)"
			 + " AND (TIPO_DOC=" + docTypeId + " OR TIPO_DOC<0)"
			 + " AND (ID_EVENTO=" + eventId + " OR ID_EVENTO<0)"
			 + " AND (ID_INFO=" + infoId + " OR ID_INFO<0)"
			 + " ORDER BY ORDEN ASC";
		
		IItemCollection rules = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_PUB_RULES, query);
		return rules;
	}

	/**
	 * Obtiene la información de una regla.
	 * @param ruleId Identificador de la regla.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem getRule(int ruleId) throws ISPACException {
	
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItem rule = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_RULES, ruleId);
		return rule;
	}

	/**
	 * Añade una regla al publicador
	 * 
	 * @return Regla creada.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItem addRule(int idPcd, int idFase, int idTramite, int tipoDoc,
			int idEvento, int idAccion, int idCondicion, String atributos,
			int orden, int idAplicacion, int idInfo) throws ISPACException {

		TransactionContainer txcontainer = context.getTXContainer();

		try {
			txcontainer.begin();

			PubReglaDAO rule = new PubReglaDAO(txcontainer.getConnection());
			rule.createNew(txcontainer.getConnection());

			rule.set("ID_PCD", idPcd);
			rule.set("ID_FASE", idFase);
			rule.set("ID_TRAMITE", idTramite);
			rule.set("TIPO_DOC", tipoDoc);
			rule.set("ID_EVENTO", idEvento);
			rule.set("ID_ACCION", idAccion);
			rule.set("ID_CONDICION", idCondicion);
			rule.set("ATRIBUTOS", atributos);
			rule.set("ORDEN", orden);
			rule.set("ID_APLICACION", idAplicacion);
			rule.set("ID_INFO", idInfo);

			rule.store(context);

			txcontainer.commit();

			return rule;

		} finally {
			txcontainer.release();
		}
	}
	
	public IItem updateRegla(int idRegla, int idPcd, int idFase, int idTramite, int tipoDoc,
			int idEvento, int idAccion, int idCondicion, String atributos,
			int orden, int idAplicacion, int idInfo) throws ISPACException {
	
		TransactionContainer txcontainer = context.getTXContainer();

		try {
			txcontainer.begin();

			PubReglaDAO rule = new PubReglaDAO(txcontainer.getConnection(), idRegla);

			if (rule != null) {
				rule.set("ID_PCD", idPcd);
				rule.set("ID_FASE", idFase);
				rule.set("ID_TRAMITE", idTramite);
				rule.set("TIPO_DOC", tipoDoc);
				rule.set("ID_EVENTO", idEvento);
				rule.set("ID_ACCION", idAccion);
				rule.set("ID_CONDICION", idCondicion);
				rule.set("ATRIBUTOS", atributos);
				rule.set("ORDEN", orden);
				rule.set("ID_APLICACION", idAplicacion);
				rule.set("ID_INFO", idInfo);
	
				rule.store(context);
	
				txcontainer.commit();
			}

			return rule;

		} finally {
			txcontainer.release();
		}
	}
	
	/**
	 * Elimina una regla.
	 * 
	 * @param ruleId
	 *            Identificador de la regla.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deleteRule(int ruleId) throws ISPACException {
		
		ICatalogAPI catalogAPI = context.getAPI().getCatalogAPI();
		
		IItem rule = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_PUB_RULES, ruleId);
		rule.delete(context);
	}
		
	/**
     * Incrementa el orden de una regla del publicador al siguiente orden existente y decrementa 
     * al sustituido
     *
     * @param id Identificador de la regla
     * @param pcdId Identificador del procedimiento
     * @param stageId Identificador de la fase
     * @param taskId Identificador del trámite
     * @param typeDoc Identificador del tipo de documento
     * @param eventId Identificador del evento
     * @param infoId Identificador de info
     * @param order Orden de la regla
     * @throws ISPACException si ocurre algún error.
     */
	public void incRuleOrder(int id, 
								  int pcdId, 
								  int stageId, 
								  int taskId, 
								  int typeDoc,
								  int eventId,
								  int infoId,
								  int order) throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;
	    
	    try {
			if (!ongoingTX) {
				context.beginTX();
			}
	    	DbCnt cnt = context.getConnection();
	        
	        PubReglaDAO destEvent = PubReglaDAO.getEventRuleAnt(cnt, pcdId, stageId, taskId, typeDoc, eventId, infoId, order);
	        PubReglaDAO origEvent = PubReglaDAO.getEvent(cnt, id);
	        
	        if ((destEvent != null) && 
	        	(origEvent !=null)) {
	        	
	        	int origOrder = origEvent.getInt("ORDEN");
	        	origEvent.set("ORDEN", destEvent.getInt("ORDEN"));
	        	destEvent.set("ORDEN", origOrder);
	        	
	        	origEvent.store(cnt);
	        	destEvent.store(cnt);
	        }

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (Exception e)
	    {
	    	logger.error("Error al incrementar el orden de la regla", e);
			throw new ISPACException("Error en PublisherAPI:incRuleOrder(" + id + ", "
			        + pcdId + ", " + stageId + ", " + taskId + ", " + typeDoc + ", " + eventId + ", " + infoId + ", " + order + ")", e);
		}
	    finally
	    {
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
	}

	/**
     * Decrementa el orden de una regla del publicador al siguiente orden existente e incrementa 
     * al sustituido
     *
     * @param id Identificador de la regla
     * @param pcdId Identificador del procedimiento
     * @param stageId Identificador de la fase
     * @param taskId Identificador del trámite
     * @param typeDoc Identificador del tipo de documento
     * @param eventId Identificador del evento
     * @param infoId Identificador de info
     * @param order Orden de la regla
     * @throws ISPACException si ocurre algún error.
     */
	public void decRuleOrder(int id, 
								  int pcdId, 
								  int stageId, 
								  int taskId, 
								  int typeDoc,
								  int eventId,
								  int infoId,
								  int order) throws ISPACException
	{
		// Ejecución de las reglas en un contexto transaccional
		boolean ongoingTX = context.ongoingTX();
		boolean bCommit = false;
		
	    try {
			if (!ongoingTX) {
				context.beginTX();
			}
	    	DbCnt cnt = context.getConnection();
	        
	        PubReglaDAO destEvent = PubReglaDAO.getEventRulePred(cnt, pcdId, stageId, taskId, typeDoc, eventId, infoId, order);
	        PubReglaDAO origEvent = PubReglaDAO.getEvent(cnt, id);
	        
	        if ((destEvent != null) && 
	        	(origEvent !=null)) {
	        	
	        	int origOrder = origEvent.getInt("ORDEN");
	        	origEvent.set("ORDEN", destEvent.getInt("ORDEN"));
	        	destEvent.set("ORDEN", origOrder);
	        	
	        	origEvent.store(cnt);
	        	destEvent.store(cnt);
	        }

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
	    catch (Exception e)
	    {
	    	logger.error("Error al decrementar el orden de la regla", e);
			throw new ISPACException("Error en PublisherAPI:decRuleOrder(" + id + ", "
			        + pcdId + ", " + stageId + ", " + taskId + ", " + typeDoc + ", " + eventId + ", " + infoId + ", " + order + ")", e);
		}
	    finally 
	    {
			if (!ongoingTX) {
				context.endTX(bCommit);
			}
		}
	}
	

	/*
	 * 
	 * Métodos para gestionar el ULTIMO HITO TRATADO
	 *  
	 */

	/**
	 * Actualizar el último hito tratado en un sistema.
	 * @param systemId Identificador del sistema.
	 * @param milestoneId Identificador del hito tratado.
	 * @throws ISPACException si ocurre algún error.
	 */
    public boolean updateLastMilestoneTreated(String systemId, int milestoneId) throws ISPACException {
    	
		DbCnt cnt = null;

		try {
			cnt = context.getConnection();
			
			int count = PubLastMilestoneTreatedDAO.updateLastMilestoneTreated(cnt, systemId, milestoneId);
			
			// si no se ha podido actualizar el valor... (puede ser porque no
			// existe el registro para el Sistema Externo)
			if (count == 0) {
				
				// inicializando el valor
				count = PubLastMilestoneTreatedDAO.insertLastMilestoneTreated(cnt, systemId, milestoneId);
			}
			
			return count > 0;
			
		} finally {
			context.releaseConnection(cnt);
		}
	}
	
 
}
