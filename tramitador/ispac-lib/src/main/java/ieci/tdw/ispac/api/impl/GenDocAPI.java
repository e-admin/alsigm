package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.PropName;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventFicheroBajaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.InformationMilestonesConstants;
import ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.ExpedientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.DocumentData;
import ieci.tdw.ispac.ispaclib.gendoc.DMConnectorFactory;
import ieci.tdw.ispac.ispaclib.gendoc.DMDocumentManager;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.tageval.ITagTranslator;
import ieci.tdw.ispac.ispaclib.tageval.TagTranslator;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.util.FileTemplateManager;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;

public class GenDocAPI implements IGenDocAPI {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GenDocAPI.class);

	/**
	 * Contexto de cliente.
	 */
	private final ClientContext m_clientCtx;

	private IspacAuditoriaManager auditoriaManager;

	/**
	 * Constructor.
	 *
	 * @param context
	 *            Contexto de cliente.
	 */
	public GenDocAPI(ClientContext context) {
		this.m_clientCtx = context;
		auditoriaManager = new IspacAuditoriaManagerImpl();
	}

	/**
	 * Devuelve la lista de tipos de documento que se pueden generar a partir de
	 * una fase de tramitación
	 *
	 * @param stageId
	 *            Identificador de la fase de un procedimiento
	 * @return lista de procedimientos
	 */
	public IItemCollection getDocTypesFromStage(int stageId) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();
			return CTTpDocDAO.getDocTypesFromStage(cnt, stageId).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::GetDocTypesFromStage()", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene un tipo de documento.
	 *
	 * @param typeId
	 *            Identificador del tipo de documento
	 * @return el objeto CTTpDocDAO con los datos del tipo de documento
	 */
	public IItem getDocumentType(int typeId) throws ISPACException {
		DbCnt cnt = null;

		try {
			cnt = m_clientCtx.getConnection();
			CTTpDocDAO documentType = new CTTpDocDAO(cnt);
			documentType.setKey(typeId);
			documentType.load(cnt);
			return documentType;
		} catch (ISPACInfo info) {
			throw info;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocumentType(" + typeId + ")", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene un tipo de documento a partir del código.
	 *
	 * @param typeCode
	 *            Código del tipo de documento
	 * @return el objeto CTTpDocDAO con los datos del tipo de documento
	 */
	public IItem getDocumentType(String typeCode) throws ISPACException {
		DbCnt cnt = null;

		try {
			cnt = m_clientCtx.getConnection();

			IItemCollection itemcol = CTTpDocDAO.getDocTypeByCode(cnt, typeCode).disconnect();

			if (!itemcol.next()) {
				return null;
			}

			return itemcol.value();
		} catch (ISPACInfo info) {
			throw info;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocumentType(" + typeCode + ")", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * Devuelve la lista de tipos de documento que se pueden generar a partir de
	 * una tarea en el procedimiento.
	 *
	 * @param taskPcdId
	 *            Identificador de la tarea en el procedimiento.
	 * @return lista de tipos de documentos
	 */
	public IItemCollection getDocTypesFromTaskPCD(int taskPcdId) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();
			return CTTpDocDAO.getDocTypesFromTaskPCD(cnt, taskPcdId).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocTypesFromTaskPCD()", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * Devuelve la lista de tipos de documento que se pueden generar a partir de
	 * una tarea en el catálogo.
	 *
	 * @param taskCtlId
	 *            Identificador de la tarea en el catálogo
	 * @return lista de tipos de documentos
	 */
	public IItemCollection getDocTypesFromTaskCTL(int taskCtlId) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();
			return CTTpDocDAO.getDocTypesFromTaskCTL(cnt, taskCtlId).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocTypesFromTaskCTL()", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * calcula la lista de plantillas de un tipo de documento generables desde
	 * el contexto de una fase
	 *
	 * @param docType
	 *            Identificador del tipo de documento
	 * @param stageId
	 *            Identificador de la fase
	 * @return lista de plantillas
	 */
	public IItemCollection getTemplatesInStage(int docType, int stageId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		if (stageId != 0)
			expctx.setStage(stageId);
		return getTemplatesInContext(docType, expctx);
	}

	public IItemCollection getTemplatesInStages(int docType, int[] stagesId) throws ISPACException {
		LinkedHashMap map = new LinkedHashMap();

		ExpedientContext expctx = new ExpedientContext(m_clientCtx);

		for (int i = 0; i < stagesId.length; i++) {
			expctx.setStage(stagesId[i]);
			IItemCollection collection = getTemplatesInContext(docType, expctx);
			while (collection.next()) {
				IItem item = collection.value();
				if (!map.containsKey(item.getKey())) {
					map.put(item.getKey(), item);
				}
			}
		}
		HashSet set = new HashSet(map.values());
		return new ListCollection(set);
	}

	/**
	 * calcula la lista de plantillas de un tipo de documento generables desde
	 * el contexto de un trámite
	 *
	 * @param docType
	 *            Identificador del tipo de documento
	 * @param stageId
	 *            Identificador del trámite
	 * @return lista de plantillas
	 */
	public IItemCollection getTemplatesInTask(int docType, int taskId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		return getTemplatesInContext(docType, expctx);
	}

	public IItemCollection getTemplatesInTasks(int docType, int[] tasksId) throws ISPACException {
		// LinkedHashMap map = new LinkedHashMap();
		//
		// ExpedientContext expctx=new ExpedientContext(m_clientCtx);
		//
		// for (int i = 0; i < tasksId.length; i++)
		// {
		// expctx.setTask(tasksId[i]);
		// IItemCollection collection = getTemplatesInContext(docType, expctx);
		// while(collection.next())
		// {
		// IItem item = collection.value();
		// if (!map.containsKey(item.getKey()))
		// {
		// map.put(item.getKey(), item);
		// }
		// }
		// }
		//
		// HashSet set = new HashSet(map.values());
		// return new ListCollection(set);

		// Se debe hacer la interseccion de las plantillas obtenidas para los
		// distintos tramites seleccionados, ya que estos pueden pertenecer
		// a distintos procedimeintos y por ende tener asociadas diferentes
		// plantillas
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);

		Map mapTemplates = null;
		List templateIds = new ArrayList();
		for (int i = 0; i < tasksId.length; i++) {
			expctx.setTask(tasksId[i]);
			IItemCollection collection = getTemplatesInContext(docType, expctx);

			if (mapTemplates == null) {
				mapTemplates = CollectionBean.getBeanMap(collection);
				templateIds = new ArrayList(CollectionBean.getBeanMap(collection).keySet());
			} else {
				// Interseccion entre las plantillas ya obtenidas y las
				// correspondientes al tramite actual de la iteracion
				List auxList = ListUtils.intersection(templateIds, new ArrayList(CollectionBean
						.getBeanMap(collection).keySet()));
				for (Iterator iter = templateIds.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (!auxList.contains(id))
						mapTemplates.remove(id);
				}
			}
		}
		// Obtenemos la collecion de plantillas que estan como ItemBean en el
		// mapTemplates y se esperan como Item
		List result = new ArrayList();
		Iterator entryIter = mapTemplates.entrySet().iterator();
		while (entryIter.hasNext()) {
			Map.Entry entry = (Map.Entry) entryIter.next();
			ItemBean value = (ItemBean) entry.getValue();
			result.add(value.getItem());
		}

		return new ListCollection(result);
	}

	/**
	 * Obtiene las plantillas de un tipo de documento en un trámite del
	 * procedimiento.
	 *
	 * @param docType
	 *            Identificador del tipo de documento.
	 * @param nIdTaskPCD
	 *            Identificador del trámite en el procedimiento.
	 * @return Colección de plantillas.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getTemplatesInPTask(int docType, int nIdTaskPCD) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setPTask(nIdTaskPCD);
		return getTemplatesInContext(docType, expctx);
	}

	/**
	 * calcula la lista de plantillas de un tipo de documento generables desde
	 * un contexto
	 *
	 * @param docType
	 *            Identificador del tipo de documento
	 * @param expctx
	 *            Contexto del tramitación
	 * @return lista de plantillas
	 */
	private IItemCollection getTemplatesInContext(int docType, ExpedientContext expctx)
			throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();

			ITagTranslator translator = new TagTranslator(m_clientCtx, expctx);
			// CollectionDAO collection =
			// PPlantillaDAO.getTemplates(cnt,docType);
			// Para la obtencion de plantillas se hace uso del filtro que las
			// asocia a un procedimiento (tabla SPAC_P_PLANTILLAS)
			IItemCollection collection = m_clientCtx.getAPI().getProcedureAPI()
					.getProcTemplates(docType, expctx.getProcedure());

			ArrayList list = new ArrayList();

			while (collection.next()) {
				IItem item = collection.value();
				/*
				 * Filtrar plantillas
				 */
				String expr = item.getString("EXPRESION");
				if ((expr != null) && (expr.length() > 0)) {
					if (translator.translateBooleanTag(expr)) {
						list.add(item);
					}
				} else
					list.add(item);
			}

			return new ListCollection(list);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getTemplatesInContext()", ie);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	public IItem createStageDocument(int stageId, int docType) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);

		return createDocument(expctx, docType);
	}

	public IItem createStageDocument(int stageId, int docType, int entityId, int regId)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);
		expctx.setEntity(entityId);
		expctx.setKey(regId);

		return createDocument(expctx, docType);
	}

	/**
	 * Crea un registro de la entidad de documentos asociándolo a una actividad
	 *
	 * @param activityId
	 *            Identificador de la actividad instanciada
	 * @param taskId
	 *            Identificador del trámite instanciado
	 * @param taskPcdId
	 *            Identificador del trámite en el procedimiento
	 * @param docType
	 *            Identificador del tipo de documento
	 * @param idEntidad
	 *            Identificador de la entidad
	 * @param regId
	 *            Identificador del registro de la entidad
	 * @return Registro de la entidad creado
	 * @throws ISPACException
	 */
	public IItem createActivityDocument(int activityId, int taskId, int taskPcdId, int docType,
			int idEntid, int regId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);
		expctx.setEntity(idEntid);
		expctx.setKey(regId);

		return createDocument(expctx, docType);
	}

	public IItem createActivityDocument(int activityId, int taskId, int taskPcdId, int docType)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);

		return createDocument(expctx, docType);
	}

	public IItem createTaskDocument(int taskId, int docType) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);

		return createDocument(expctx, docType);
	}

	public IItem createTaskDocument(int taskId, int docType, int entityId, int regId)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		expctx.setEntity(entityId);
		expctx.setKey(regId);
		return createDocument(expctx, docType);
	}

	public IItem createBatchTaskDocument(int batchTaskId, int taskId, int docType, int idTramite,
			int idTemplate) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);

		IItem doc = createDocument(expctx, docType);

		// actualizar en tramitaciones agrupadas el ultimo tipo de documento,
		// plantilla y tramite
		TXTramitacionAgrupadaDAO batchTaskDAO = new TXTramitacionAgrupadaDAO(
				m_clientCtx.getConnection(), batchTaskId);
		batchTaskDAO.set("ID_ULTIMO_TRAMITE", idTramite);
		batchTaskDAO.set("ID_ULTIMO_TIPODOC", docType);
		batchTaskDAO.set("ID_ULTIMO_TEMPLATE", idTemplate);
		batchTaskDAO.store(m_clientCtx);
		return doc;
	}

	/**
	 *
	 * @param connectorSession
	 *            Conexión con el conector documental
	 * @param stageId
	 *            Identificador de la fase instanciada
	 * @param docId
	 *            Identificador del documento
	 * @param templateId
	 *            Identificador de la plantilla
	 * @param entityId
	 *            Identificador de la entidad
	 * @param regId
	 *            Identificador del registro de la entidad
	 * @return Registro de la entidad de documentos
	 * @throws ISPACException
	 */
	public IItem attachStageTemplate(Object connectorSession, int stageId, int docId,
			int templateId, int entityId, int regId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);
		expctx.setEntity(entityId);
		expctx.setKey(regId);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa la plantilla al documento en el contexto de una fase.
	 *
	 * @param stageId
	 *            identificador de la fase.
	 * @param docId
	 *            identificador del documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @return Item de la entidad de documentos.
	 */
	public IItem attachStageTemplate(Object connectorSession, int stageId, int docId, int templateId)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa la plantilla al documento en el contexto de una fase.
	 *
	 * @param stageId
	 *            identificador de la fase.
	 * @param docId
	 *            identificador del documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return Item de la entidad de documentos.
	 */
	public IItem attachStageTemplate(Object connectorSession, int stageId, int docId,
			int templateId, String sFileTemplate, int entity, int key) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa la plantilla al documento en el contexto de una actividad.
	 *
	 * @param activityId
	 *            identificador de la actividad.
	 * @param taskId
	 *            identificador del trámite del subproceso de la actividad.
	 * @param taskPcdId
	 *            identificador del trámite en el procedimiento.
	 * @param docId
	 *            identificador del documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return Item de la entidad de documentos.
	 */
	public IItem attachActivityTemplate(Object connectorSession, int activityId, int taskId,
			int taskPcdId, int docId, int templateId, String sFileTemplate, int entity, int key)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	public IItem attachActivityTemplate(Object connectorSession, int activityId, int taskId,
			int taskPcdId, int documentId, int templateId, int entityId, int regId)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);
		expctx.setEntity(entityId);
		expctx.setKey(regId);

		return attachTemplate(connectorSession, null, expctx, documentId, templateId);
	}

	public IItem attachActivityTemplate(Object connectorSession, int activityId, int taskId,
			int taskPcdId, int documentId, int templateId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);

		return attachTemplate(connectorSession, null, expctx, documentId, templateId);
	}

	/**
	 *
	 * @param connectorSession
	 *            Conexión con el conector documental
	 * @param taskId
	 *            Identificador de la fase instanciada
	 * @param docId
	 *            Identificador del documento
	 * @param templateId
	 *            Identificador de la plantilla
	 * @param entityId
	 *            Identificador de la entidad
	 * @param regId
	 *            Identificador del registro de la entidad
	 * @return Registro de la entidad de documentos
	 * @throws ISPACException
	 */
	public IItem attachTaskTemplate(Object connectorSession, int taskId, int docId, int templateId,
			int entityId, int regId) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		expctx.setEntity(entityId);
		expctx.setKey(regId);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa la plantilla al documento en el contexto de una tarea.
	 *
	 * @param taskId
	 *            identificador de la tarea.
	 * @param docId
	 *            identificador del documento.
	 * @param templateId
	 *            identificador de la plantilla.
	 * @return Item de la entidad de documentos.
	 */
	public IItem attachTaskTemplate(Object connectorSession, int taskId, int docId, int templateId)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa la plantilla al documento en el contexto de una tarea.
	 *
	 * @param taskId
	 *            identificador de la tarea.
	 * @param docId
	 *            identificador del documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return Item de la entidad de documentos.
	 */
	public IItem attachTaskTemplate(Object connectorSession, int taskId, int docId, int templateId,
			String sFileTemplate, int entity, int key) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	public IItem attachTaskTemplate(Object connectorSession, int taskId, int docId, int templateId,
			String sFileTemplate) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		if (!StringUtils.isBlank(sFileTemplate))
			expctx.setFileTemplate(sFileTemplate);
		// expctx.setEntity( entity);
		// expctx.setKey( key);

		return attachTemplate(connectorSession, null, expctx, docId, templateId);
	}

	/**
	 * Anexa un stream al documento en el contexto de una fase.
	 *
	 * @param stageId
	 *            identificador de la fase.
	 * @param docId
	 *            identificador del documento.
	 * @param in
	 *            stream
	 * @param length
	 *            longitud del stream
	 * @param sMimeType
	 *            tipo mime del fichero
	 * @param sName
	 *            Nombre del fichero
	 */

	public IItem attachStageInputStream(Object connectorSession, int stageId, int docId,
			InputStream in, int length, String sMimeType, String sName) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);

		return attachInputStream(connectorSession, null, expctx, docId, in, length, sMimeType,
				sName, null);
	}

	public IItem attachStageInputStream(Object connectorSession, Object obj, int stageId,
			int docId, InputStream in, int length, String sMimeType, String sName,
			String sProperties) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);

		return attachInputStream(connectorSession, obj, expctx, docId, in, length, sMimeType,
				sName, sProperties);
	}

	/**
	 * Anexa un stream al documento en el contexto de una tarea.
	 *
	 * @param taskId
	 *            identificador de la fase.
	 * @param docId
	 *            identificador del documento.
	 * @param in
	 *            stream
	 * @param length
	 *            longitud del stream
	 * @param sMimeType
	 *            tipo mime del fichero
	 * @param sName
	 *            Nombre del fichero
	 */
	public IItem attachTaskInputStream(Object connectorSession, int taskId, int docId,
			InputStream in, int length, String sMimeType, String sName) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);

		return attachInputStream(connectorSession, null, expctx, docId, in, length, sMimeType,
				sName, null);
	}

	public IItem attachTaskInputStream(Object connectorSession, Object obj, int taskId, int docId,
			InputStream in, int length, String sMimeType, String sName, String properties)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);

		return attachInputStream(connectorSession, obj, expctx, docId, in, length, sMimeType,
				sName, properties);
	}

	private IItem createDocument(ExpedientContext ctx, int docType) throws ISPACException {
		DbCnt cnt = null;

		try {
			cnt = m_clientCtx.getConnection();

			int docTypeId = 0;
			String docTypeName = null;
			String registerType = null;

			if (docType != 0) {
				CTTpDocDAO tpdoc = new CTTpDocDAO(cnt, docType);
				docTypeId = tpdoc.getInt("ID");
				docTypeName = tpdoc.getString("NOMBRE");
				registerType = tpdoc.getString("TIPOREG");
			}

			DocumentData docdata = new DocumentData(ctx.getNumExp(), docTypeId, ctx.getStagePCD(),
					ctx.getStage(), ctx.getTaskPCD(), ctx.getTask(), docTypeName, m_clientCtx
							.getUser().getUID(), m_clientCtx.getUser().getName(), registerType);
			docdata.setEntity(ctx.getEntity());
			docdata.setKey(ctx.getKey());

			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);

			EntityDAO document = manager.createDocumentEntity(docdata);
			document.store(m_clientCtx);

			// Ejecutar eventos asociados a la creación del documento.

			// Se añaden propiedades extra para el contexto de ejecución de la
			// regla.
			ctx.addContextParams(docdata.getRuleParameters());

			/*
			 * ctx.addContextParam(RuleProperties.RCTX_DOCUMENTID,String.valueOf(
			 * docdata.getDocId()));
			 * ctx.addContextParam(RuleProperties.RCTX_DOCUMENTTYPE
			 * ,String.valueOf(docdata.getDocType()));
			 * ctx.addContextParam(RuleProperties
			 * .RCTX_DOCUMENTNAME,docdata.getName());
			 * ctx.addContextParam(RuleProperties
			 * .RCTX_DOCUMENTAUTHOR,docdata.getAuthor());
			 */
			ITXTransaction txapi = m_clientCtx.getAPI().getTransactionAPI();
			StateContext stateContext = m_clientCtx.getStateContext();

			if (stateContext.getActivityId() != 0) {
				txapi.executeEvents(EventsDefines.EVENT_OBJ_ACTIVITY, ctx.getStagePCD(),
						EventsDefines.EVENT_DOCUMENT_NEW, ctx);
			} else if (ctx.getTaskPCD() != 0) {
				txapi.executeEvents(EventsDefines.EVENT_OBJ_TASK, ctx.getTaskPCD(),
						EventsDefines.EVENT_DOCUMENT_NEW, ctx);
			} else if (ctx.getStagePCD() != 0) {
				txapi.executeEvents(EventsDefines.EVENT_OBJ_STAGE, ctx.getStagePCD(),
						EventsDefines.EVENT_DOCUMENT_NEW, ctx);
			}

			return document;
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	private IItem attachTemplate(Object connectorSession, Object obj, ExpedientContext ctx,
			int docId, int templateId) throws ISPACException {
		DbCnt cnt = null;
		FileTemplateManager templateManager = null;
		FileTemporaryManager temporaryManager = null;
		String sDocRefAnt = null;
		String sDocRefNew = null;
		IDocConnector connector = null;
		String sSourceName = null;
		String sTargetName = null;
		String sTargetPath = null;
		String sTemplatePath = null;

		FileInputStream in = null;

		try {

			cnt = m_clientCtx.getConnection();

			// Obtiene el manejador de plantillas
			templateManager = (FileTemplateManager) FileTemplateManager.getInstance();
			// Obtiene el manejador de ficheros temporales
			temporaryManager = FileTemporaryManager.getInstance();
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();

			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);

			EntityDAO document = manager.getDocumentEntity(docId);

			CTTpDocDAO tpdoc = new CTTpDocDAO(cnt, document.getInt("ID_TPDOC"));
			CTTemplate ctTemplate = new CTTemplate(cnt, templateId);

			String documentName = templateId + "."
					+ MimetypeMapping.getExtension(ctTemplate.getMimetype());
			DocumentData docdata = new DocumentData(ctx.getNumExp(), tpdoc.getInt("ID"),
					ctx.getStagePCD(), ctx.getStage(), ctx.getTaskPCD(), ctx.getTask(),
					// tpdoc.getString("NOMBRE"),
					documentName, m_clientCtx.getUser().getUID(), m_clientCtx.getUser().getName(),
					tpdoc.getString("TIPOREG"));
			docdata.setEntity(ctx.getEntity());
			docdata.setKey(ctx.getKey());

			docdata.setTemplate(templateId);

			// Asigna el Mimetype de la plantilla
			docdata.setMimeType(ctTemplate.getMimetype());
			docdata.setDoc(document.getKeyInt());

			String sourceURL;

			if (ctx.getFileTemplate() == null) {
				// Hace una copia de la plantilla
				sTemplatePath = templateManager.getTemplatePath(cnt, ctTemplate);
				String mimetype = ctTemplate.getMimetype();

				String suffix = "." + MimetypeMapping.getExtension(mimetype);

				sSourceName = temporaryManager.put(sTemplatePath, suffix);

				sourceURL = "file:///" + temporaryManager.getFileTemporaryPath() + "/"
						+ sSourceName;
			} else
				sourceURL = "file:///" + temporaryManager.getFileTemporaryPath() + "/"
						+ ctx.getFileTemplate();

			docdata.setTemplateName(ctTemplate.getName());

			// Compone la URL de un fichero temporal
			sTargetName = temporaryManager.newFileName();
			sTargetPath = temporaryManager.getFileTemporaryPath() + "/" + sTargetName;
			String targetURL = "file:///" + sTargetPath;

			// Genera el nuevo documento a partir de la plantilla
			// con el contexto calculado.
			manager.mergeDocument(sourceURL, targetURL, docdata);

			// Obtiene el documento XML con las propiedades del documcento
			String sProperties = manager.getProperties(docdata);

			// Salva el documento
			File file = new File(sTargetPath);
			in = new FileInputStream(file);

			// Referencia al fichero del documento
			sDocRefAnt = document.getString("INFOPAG");

			// Referencia al nuevo fichero del documento
			sDocRefNew = connector.newDocument(connectorSession, in, (int) file.length(),
					sProperties);

			// Actualizar el documento
			document.set("INFOPAG", sDocRefNew);
			document.set("ID_PLANTILLA", templateId);
			document.set("DESCRIPCION", ctTemplate.getString("TEMPLATE:NOMBRE"));
			document.store(cnt);

			docdata.setDocRef(sDocRefNew);

			/*
			 * ctx.addContextParam(RuleProperties.RCTX_DOCUMENTID,String.valueOf(
			 * docdata.getDocId()));
			 * ctx.addContextParam(RuleProperties.RCTX_DOCUMENTTYPE
			 * ,String.valueOf(docdata.getDocType()));
			 * ctx.addContextParam(RuleProperties
			 * .RCTX_DOCUMENTNAME,docdata.getName());
			 * ctx.addContextParam(RuleProperties
			 * .RCTX_DOCUMENTAUTHOR,docdata.getAuthor());
			 * ctx.addContextParam(RuleProperties
			 * .RCTX_DOCUMENTMIMETYPE,docdata.getMimeType());
			 * ctx.addContextParam(RuleProperties.RCTX_DOCUMENTREF,sDocRef);
			 * ctx.
			 * addContextParam(RuleProperties.RCTX_TEMPLATEID,String.valueOf(
			 * docdata.getTemplate()));
			 * ctx.addContextParam(RuleProperties.RCTX_TEMPLATENAME
			 * ,template.getString("NOMBRE"));
			 */

			// Controlar error en los eventos para eliminar el nuevo fichero:
			// - que elimina el fichero cuando el documento es nuevo
			// - que restaura el fichero anterior cuando el documento es
			// sustituido
			try {

				// Ejecutar eventos asociados al anexar la plantilla
				ctx.addContextParams(docdata.getRuleParameters());

				ITXTransaction txapi = m_clientCtx.getAPI().getTransactionAPI();
				StateContext stateContext = m_clientCtx.getStateContext();

				if (stateContext.getActivityId() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_ACTIVITY, ctx.getStagePCD(),
							EventsDefines.EVENT_TEMPLATE_USE, ctx);
				} else if (ctx.getTaskPCD() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_TASK, ctx.getTaskPCD(),
							EventsDefines.EVENT_TEMPLATE_USE, ctx);
				} else if (ctx.getStagePCD() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_STAGE, ctx.getStagePCD(),
							EventsDefines.EVENT_TEMPLATE_USE, ctx);
				}
			} catch (ISPACException ie) {

				// Eliminar el nuevo fichero
				connector.deleteDocument(connectorSession, sDocRefNew);

				throw ie;

			} catch (Exception e) {

				logger.error("Error al ejecutar el evento de utilizar plantilla", e);

				// Eliminar el nuevo fichero
				connector.deleteDocument(connectorSession, sDocRefNew);

				throw new ISPACException("exception.documents.generate.error.event", e);
			}

			// Eliminar el fichero anterior
			if (sDocRefAnt != null && !StringUtils.equals(sDocRefAnt, sDocRefNew)) {
				connector.deleteDocument(connectorSession, sDocRefAnt);
			}

			return document;
		} catch (IOException e) {

			throw new ISPACException("Error fichero temporal", e);
		} finally {

			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
			}

			m_clientCtx.releaseConnection(cnt);

			// Borrar temporales
			if (temporaryManager != null) {

				if (sSourceName != null) {
					temporaryManager.delete(sSourceName);
				}
				if (sTargetName != null) {
					temporaryManager.delete(sTargetName);
				}
			}
		}
	}

	private IItem attachInputStream(Object connectorSession, Object obj, ExpedientContext ctx,
			int docId, InputStream in, int length, String sMimeType, String sName,
			String sProperties) throws ISPACException {
		DbCnt cnt = null;
		String sDocRefAnt = null;
		String sDocRefNew = null;
		IDocConnector connector = null;

		try {

			cnt = m_clientCtx.getConnection();

			// Obtiene el conector de almacenamiento
			if (obj != null)
				connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector(obj);
			else
				connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();

			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);

			EntityDAO document = manager.getDocumentEntity(docId);

			// CTTpDocDAO tpdoc = new
			// CTTpDocDAO(cnt,document.getInt("ID_TPDOC"));
			int docTypeId = 0;
			String docTypeName = null;
			String registerType = null;

			if (document.getInt("ID_TPDOC") != 0) {
				CTTpDocDAO tpdoc = new CTTpDocDAO(cnt, document.getInt("ID_TPDOC"));
				docTypeId = tpdoc.getInt("ID");
				docTypeName = tpdoc.getString("NOMBRE");
				registerType = tpdoc.getString("TIPOREG");
			}

			String documentName = sName;
			if (StringUtils.indexOf(documentName, ".") == -1)
				documentName += "." + document.getString("EXTENSION");

			DocumentData docdata = new DocumentData(ctx.getNumExp(), docTypeId, ctx.getStagePCD(),
					ctx.getStage(), ctx.getTaskPCD(), ctx.getTask(),
					// tpdoc.getString("NOMBRE"),
					documentName, m_clientCtx.getUser().getUID(), m_clientCtx.getUser().getName(),
					registerType);
			docdata.setEntity(ctx.getEntity());
			docdata.setKey(ctx.getKey());

			docdata.setDoc(document.getKeyInt());
			docdata.setMimeType(sMimeType);

			// Obtiene el documento XML con las propiedades del documento
			if (sProperties == null)
				sProperties = manager.getProperties(docdata);

			// Referencia al fichero del documento
			sDocRefAnt = document.getString("INFOPAG");

			// Referencia al nuevo fichero del documento
			sDocRefNew = connector.newDocument(connectorSession, in, length, sProperties);

			// Actualizar el documento
			document.set("INFOPAG", sDocRefNew);
			document.set("ID_PLANTILLA", ((Integer) null));
			if (sName != null) {
				document.set("DESCRIPCION", sName);
			}
			document.store(cnt);

			docdata.setDocRef(sDocRefNew);

			// Controlar error en los eventos para eliminar el nuevo fichero:
			// - que elimina el fichero cuando el documento es nuevo
			// - que restaura el fichero anterior cuando el documento es
			// sustituido
			try {

				// Ejecutar eventos asociados al anexar un fichero
				ctx.addContextParams(docdata.getRuleParameters());

				ITXTransaction txapi = m_clientCtx.getAPI().getTransactionAPI();
				StateContext stateContext = m_clientCtx.getStateContext();

				if (stateContext.getActivityId() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_ACTIVITY, ctx.getStagePCD(),
							EventsDefines.EVENT_TEMPLATE_EXTERNAL, ctx);
				} else if (ctx.getTaskPCD() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_TASK, ctx.getTaskPCD(),
							EventsDefines.EVENT_TEMPLATE_EXTERNAL, ctx);
				} else if (ctx.getStagePCD() != 0) {
					txapi.executeEvents(EventsDefines.EVENT_OBJ_STAGE, ctx.getStagePCD(),
							EventsDefines.EVENT_TEMPLATE_EXTERNAL, ctx);
				}
			} catch (ISPACException ie) {

				// Eliminar el nuevo fichero
				connector.deleteDocument(connectorSession, sDocRefNew);

				throw ie;

			} catch (Exception e) {

				logger.error("Error al ejecutar el evento de anexar documento externo", e);

				// Eliminar el nuevo fichero
				connector.deleteDocument(connectorSession, sDocRefNew);

				throw new ISPACException("exception.uploadfile.error.event", e);
			}

			// Eliminar el fichero anterior
			if (sDocRefAnt != null && !StringUtils.equals(sDocRefAnt, sDocRefNew)) {
				connector.deleteDocument(connectorSession, sDocRefAnt);
			}

			// TODO: Auditar la creación del fichero

			auditCreacionDocumento(document,docId,ctx.getNumExp(),ctx.getTask());
			return document;
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * @param document
	 * @throws ISPACException
	 */
	private void auditCreacionDocumento(EntityDAO document,int idDocumento, String numExpediente, int idTramite) throws ISPACException {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventDocumentoAltaVO evento = new IspacAuditEventDocumentoAltaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setIdDocumento(String.valueOf(idDocumento));
		evento.setNumExpediente(numExpediente);
		evento.setValores(document.getXmlValues());
		evento.setIdTramite(String.valueOf(idTramite));
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

	private String createTemplate(ExpedientContext ctx, int docType, int templateId)
			throws ISPACException {
		DbCnt cnt = null;
		FileTemplateManager templateManager = null;
		FileTemporaryManager temporaryManager = null;
		String sSourceName = null;
		String sTargetName = null;
		String sTemplatePath = null;

		try {
			cnt = m_clientCtx.getConnection();

			// Obtiene el manejador de plantillas
			templateManager = (FileTemplateManager) FileTemplateManager.getInstance();
			// Obtiene el manejador de ficheros temporales
			temporaryManager = FileTemporaryManager.getInstance();

			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);

			CTTpDocDAO tpdoc = new CTTpDocDAO(cnt, docType);
			DocumentData docdata = new DocumentData(ctx.getNumExp(), tpdoc.getInt("ID"),
					ctx.getStagePCD(), ctx.getStage(), ctx.getTaskPCD(), ctx.getTask(),
					tpdoc.getString("NOMBRE"), m_clientCtx.getUser().getUID(), m_clientCtx
							.getUser().getName(), tpdoc.getString("TIPOREG"));
			docdata.setEntity(ctx.getEntity());
			docdata.setKey(ctx.getKey());

			docdata.setTemplate(templateId);

			CTTemplate ctTemplate = new CTTemplate(cnt, templateId);

			// Asigna el mimetype de la plantilla
			docdata.setMimeType(ctTemplate.getMimetype());
			// Asigna el nombre de la plantilla
			docdata.setTemplateName(ctTemplate.getName());

			String sourceURL;
			String suffix = "";

			if (ctx.getFileTemplate() == null) {
				// Hace una copia de la plantilla
				sTemplatePath = templateManager.getTemplatePath(cnt, ctTemplate);
				String mimetype = ctTemplate.getMimetype();

				suffix = "." + MimetypeMapping.getExtension(mimetype);

				sSourceName = temporaryManager.put(sTemplatePath, suffix);

				sourceURL = "file:///" + temporaryManager.getFileTemporaryPath() + "/"
						+ sSourceName;
			} else
				sourceURL = "file:///" + temporaryManager.getFileTemporaryPath() + "/"
						+ ctx.getFileTemplate();

			// Compone la URL de un fichero temporal
			sTargetName = temporaryManager.newFileName(suffix);
			String targetURL = "file:///" + temporaryManager.getFileTemporaryPath() + "/"
					+ sTargetName;

			// Genera el documento.
			manager.mergeDocument(sourceURL, targetURL, docdata);

			return sTargetName;
		} finally {
			m_clientCtx.releaseConnection(cnt);

			// Borrar temporales
			if (temporaryManager != null) {

				if (sSourceName != null) {
					temporaryManager.delete(sSourceName);
				}
			}
		}
	}

	public void getDocument(Object connectorSession, String sDocRef, OutputStream out)
			throws ISPACException {
		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			// Vuelca el document
			connector.getDocument(connectorSession, sDocRef, out);

			//Auditar la consulta del documento
			auditConsultaDocumento(sDocRef);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocument()", ie);
		}
	}

	public void setDocument(Object connectorSession, int docId, String sDocRef, InputStream in,
			int length, String sMimetype) throws ISPACException {

		IDocConnector connector = null;

		try {
			ExpedientContext ctx = new ExpedientContext(m_clientCtx);

			DocumentData docdata = new DocumentData();
			// docdata.setDoc(docId);
			docdata.setMimeType(sMimetype);
			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);
			String sProperties = manager.getProperties(docdata);
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			// Vuelca el document
			String newDocRef = connector.updateDocument(connectorSession, sDocRef, in, length,
					sProperties);
			String numExp ="";

			if (newDocRef != null) {
				IEntitiesAPI entitiesAPI = m_clientCtx.getAPI().getEntitiesAPI();
				IItem itemDoc = entitiesAPI.getDocument(docId);
				itemDoc.set("INFOPAG", newDocRef);
				itemDoc.store(m_clientCtx);
				numExp = itemDoc.getString("NUMEXP");
			}

			//TODO: Auditar la modificación del documento


			auditActualizacionDocumento(docId, sDocRef, numExp);

		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::setDocument()", ie);
		}
	}

	public String getMimeType(Object connectorSession, String sDocRef) throws ISPACException {
		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			return connector.getMimeType(connectorSession, sDocRef);
		} catch (ISPACInfo ii) {
			throw ii;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getMimeType()", ie);
		}
	}

	public int getDocumentSize(Object connectorSession, String sDocRef) throws ISPACException {
		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			return connector.getDocumentSize(connectorSession, sDocRef);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocumentSize()", ie);
		}
	}

	public boolean existsDocument(Object connectorSession, String sDocRef) throws ISPACException {
		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			return connector.existsDocument(connectorSession, sDocRef);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::existsDocument()", ie);
		}
	}

	public void deleteDocument(Object connectorSession, String sDocRef) throws ISPACException {
		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			String properties = connector.getProperties(connectorSession, sDocRef);
			ExpedientContext ctx = new ExpedientContext(m_clientCtx);


			DMDocumentManager manager = new DMDocumentManager(m_clientCtx, ctx);
			//TODO: No tenemos acceso al contexto del expediente para obtener el número del expediente.
			//EntityDAO document = manager.getDocumentEntity(Integer.valueOf(sDocRef).intValue());
			properties = this.getDocumentProperties(connectorSession, sDocRef);


			//EntityDAO entityDao = manager.getDocumentEntity(Integer.valueOf(sDocRef).intValue());
			//numExp = entityDao.getString("NUMEXP");
			connector.deleteDocument(connectorSession, sDocRef);
			//Auditar la eliminación del documento
			auditEliminacionDocumento(sDocRef);
		} catch (ISPACNullObject e) {
			throw e;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::deleteDocument()", ie);
		}
	}

	/**
	 * @param sDocRef
	 */
	private void auditEliminacionDocumento(String sDocRef) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventFicheroBajaVO evento = new IspacAuditEventFicheroBajaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setIdDocumento(sDocRef);

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la eliminación del documento");
		auditoriaManager.audit(evento);
	}

	/**
	 * @param sDocRef
	 */
	private void auditActualizacionDocumento(int docId, String sDocRef, String numExpediente) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventDocumentoModificacionVO evento = new IspacAuditEventDocumentoModificacionVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setIdDocumento(String.valueOf(docId));
		evento.setNewValue(sDocRef);

		evento.setNumExpediente(numExpediente);

		//evento.setIdTramite(String.valueOf(idTramite));
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
	 * @param sDocRef
	 */
	private void auditConsultaDocumento(String sDocRef) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventDocumentoConsultaVO evento = new IspacAuditEventDocumentoConsultaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setIdDocumento(sDocRef);
		
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
	 * Vuelca una plantilla en un fichero del directorio temporal
	 *
	 * @return el nombre del fichero temporal
	 */
	public String getTemporaryTemplate(int templateId) throws ISPACException {
		DbCnt cnt = null;
		FileTemplateManager templateManager = null;
		FileTemporaryManager temporaryManager = null;

		try {
			cnt = m_clientCtx.getConnection();

			// Obtiene el manejador de plantillas
			templateManager = (FileTemplateManager) FileTemplateManager.getInstance();
			// Obtiene el manejador de ficheros temporales
			temporaryManager = FileTemporaryManager.getInstance();

			CTTemplate ctTemplate = new CTTemplate(cnt, templateId);

			// Hace una copia de la plantilla
			String sTemplatePath = templateManager.getTemplatePath(cnt, ctTemplate);
			String mimetype = ctTemplate.getMimetype();

			String suffix = "." + MimetypeMapping.getExtension(mimetype);

			return temporaryManager.put(sTemplatePath, suffix);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	/**
	 * Genera una plantilla en el contexto de una fase.
	 *
	 * @param stageId
	 *            identificador de la fase.
	 * @param docType
	 *            identificador del tipo de documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return el nombre del fichero temporal con el documento generado.
	 */
	public String createStageDocument(int stageId, int docType, int templateId,
			String sFileTemplate, int entity, int key) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setStage(stageId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return createTemplate(expctx, docType, templateId);
	}

	/**
	 * Genera una plantilla en el contexto de una actividad.
	 *
	 * @param activityId
	 *            identificador de la actividad.
	 * @param taskId
	 *            identificador del trámite del subproceso de la actividad.
	 * @param taskPcdId
	 *            identificador del trámite en el procedimiento.
	 * @param docType
	 *            identificador del tipo de documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return el nombre del fichero temporal con el documento generado.
	 */
	public String createActivityDocument(int activityId, int taskId, int taskPcdId, int docType,
			int templateId, String sFileTemplate, int entity, int key) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setActivity(activityId, taskId, taskPcdId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return createTemplate(expctx, docType, templateId);
	}

	/**
	 * Genera una plantilla en el contexto de una tarea.
	 *
	 * @param taskId
	 *            identificador de la fase.
	 * @param docType
	 *            identificador del tipo de documento.
	 * @param templateId
	 *            identificador de la plantilla
	 * @param sFileTemplate
	 *            nombre del fichero temporal que contiene la plantilla
	 *            modificada.
	 * @return el nombre del fichero temporal con el documento generado.
	 */
	public String createTaskDocument(int taskId, int docType, int templateId, String sFileTemplate,
			int entity, int key) throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		expctx.setFileTemplate(sFileTemplate);
		expctx.setEntity(entity);
		expctx.setKey(key);

		return createTemplate(expctx, docType, templateId);
	}

	public String createTaskDocument(int taskId, int docType, int templateId, String sFileTemplate)
			throws ISPACException {
		ExpedientContext expctx = new ExpedientContext(m_clientCtx);
		expctx.setTask(taskId);
		expctx.setFileTemplate(sFileTemplate);

		return createTemplate(expctx, docType, templateId);
	}

	/**
	 * @param obj
	 *            objeto a pasar al instanciar al conector documental, por
	 *            ejemplo para invesDoc seria el identificador del archivador a
	 *            utilizar
	 * @param in
	 * @param length
	 * @param sMimetype
	 * @throws ISPACException
	 */
	public String newDocument(Object connectorSession, Object obj, InputStream in, int length,
			String properties) throws ISPACException {

		IDocConnector connector = null;

		try {
			// Obtiene el conector de almacenamiento
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector(obj);
			// Vuelca el document
			String docRef = connector.newDocument(connectorSession, in, length, properties);
			return docRef;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::setDocument()", ie);
		}
	}

	public String getDocumentProperties(Object connectorSession, String sDocRef)
			throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			String property = connector.getProperties(connectorSession, sDocRef);
			return property;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocumentProperty()", ie);
		}
	}

	public String getDocumentProperty(Object connectorSession, String sDocRef, String name)
			throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			String property = connector.getProperty(connectorSession, sDocRef, name);
			return property;
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getDocumentProperty()", ie);
		}
	}

	public void setDocumentProperty(Object connectorSession, String sDocRef, String name,
			String value) throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			connector.setProperty(connectorSession, sDocRef, name, value);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::setDocumentProperty()", ie);
		}
	}

	public String getRepositoryInfo(Object connectorSession, String repId) throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			return connector.getRepositoryInfo(connectorSession, repId);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::getRepositoryInfo()", ie);
		}
	}

	public Object createConnectorSession() throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			return connector.createSession();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::createConnectorSession()", ie);
		}
	}

	public void closeConnectorSession(Object connectorSession) throws ISPACException {
		IDocConnector connector = null;
		try {
			connector = DMConnectorFactory.getInstance(m_clientCtx).getConnector();
			connector.closeSession(connectorSession);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en GenDocAPI::closeConnectorSession()", ie);
		}
	}

	// public IItem attachStageInputStream(Object connectorSession, Object obj,
	// int stageId, int keyInt, InputStream content, int lenght, String
	// mimeType, String name, String properties) throws ISPACException {
	// IDocConnector connector = null;
	// try
	// {
	// // // Obtiene el conector de almacenamiento
	// // connector =
	// DMConnectorFactory.getInstance(m_clientCtx).getConnector(obj);
	// // // Vuelca el document
	// // String docRef = connector.attachStageI .newDocument(connectorSession,
	// in, length,properties);
	// // return docRef;
	// //
	// ExpedientContext expctx=new ExpedientContext(m_clientCtx);
	// expctx.setStage(stageId);
	//
	// return attachInputStream(connectorSession, obj, stageId, keyInt, content,
	// lenght, mimeType, name, properties);
	//
	//
	// }
	// catch (ISPACException ie)
	// {
	// throw new ISPACException("Error en GenDocAPI::setDocument()", ie);
	// }
	// }

	/**
	 * Inicia la notificación de un documento.
	 *
	 * @param processId
	 *            Identificador del proceso.
	 * @param stagePcdId
	 *            Identificador de la fase en el procedimiento.
	 * @param taskPcdId
	 *            Identificador del trámite en el procedimiento.
	 * @param documentId
	 *            Identificador del documento.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void initNotification(int processId, int stagePcdId, int taskPcdId, int documentId)
			throws ISPACException {

		// API de invesFlow
		IInvesflowAPI invesflowAPI = m_clientCtx.getAPI();

		// Ejecución en un contexto transaccional
		boolean ongoingTX = m_clientCtx.ongoingTX();
		boolean bCommit = false;

		try {
			if (!ongoingTX) {
				m_clientCtx.beginTX();
			}

			// Insertamos un hito informativo
			invesflowAPI.getTransactionAPI().newMilestone(
					processId,
					stagePcdId,
					taskPcdId,
					InformationMilestonesConstants.INIT_NOTIFICACON_MILESTONE,
					new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>")
							.append("<infoaux><id_documento>").append(documentId)
							.append("</id_documento></infoaux>").toString(),
					"Inicio de Notificación");

			if (logger.isInfoEnabled()) {
				logger.info("Notificación iniciada para el documento [" + documentId + "]");
			}

			// Obtener la información del documento
			IItem itemDoc = invesflowAPI.getEntitiesAPI().getDocument(documentId);

			// Actualizar el estado y fecha de notificación del documento
			itemDoc.set("ESTADONOTIFICACION", NotifyStatesConstants.IN_PROCESS);
			itemDoc.set("FNOTIFICACION", new Date());

			// Se bloquea el documento
			itemDoc.set("BLOQUEO", DocumentLockStates.TOTAL_LOCK);

			itemDoc.store(m_clientCtx);

			if (logger.isInfoEnabled()) {
				logger.info("Estado y fecha de notificación del documento [" + documentId
						+ "] actualizados: " + NotifyStatesConstants.IN_PROCESS);
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		} catch (ISPACException e) {

			logger.error("Error al iniciar la notificación", e);
			throw e;
		} finally {
			if (!ongoingTX) {
				m_clientCtx.endTX(bCommit);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.api.IGenDocAPI#initNotification(ieci.tdw.ispac.api.item.IItem)
	 */
	public void initNotification(IItem document) throws ISPACException {

		// Comprobar que no se haya notificado anteriormente
		String estadoNotificacion = document.getString("ESTADONOTIFICACION");
		if (!StringUtils.isBlank(estadoNotificacion)
				&& !NotifyStatesConstants.UNRESOLVED.equals(estadoNotificacion)) {
			logger.warn("No se puede iniciar la notificación del documento ["
					+ document.getKeyInt()
					+ "] porque ya se ha iniciado otra notificación.");
			throw new ISPACInfo("exception.notification.alreadyInitiated");
		}

		// Comprobar que el documento sea de tipo SALIDA
		if (!RegisterType.SALIDA.equalsIgnoreCase(document.getString(PropName.DOC_TP_REG))) {
			logger.warn("No se puede iniciar la notificación del documento ["
					+ document.getKeyInt()
					+ "] porque no es de tipo SALIDA.");
			throw new ISPACInfo("exception.notification.invalidType");
		}

		// Comprobar que esté registrado de salida
		if (StringUtils.isBlank(document.getString(PropName.DOC_NREG))) {
			logger.warn("No se puede iniciar la notificación del documento ["
					+ document.getKeyInt()
					+ "] porque no se ha registrado de salida.");
			throw new ISPACInfo("exception.notification.notRegistered");
		}

		// Comprobar que el el tipo de dirección del interesado sea TELEMÁTICA
		if (!IThirdPartyAdapter.ADDRESS_TYPE_TELEMATIC
				.equalsIgnoreCase(getTipoDireccionInteresado(document))) {
			logger.warn("No se puede iniciar la notificación del documento ["
					+ document.getKeyInt()
					+ "] porque el tipo de dirección de notificación del interesado no es TELEMÁTICA.");
			throw new ISPACInfo("exception.notification.notTelematic");
		}		

		// Iniciar la notificación
		int stageId = document.getInt(PropName.DOC_ID_FASE);
		IStage stage = m_clientCtx.getAPI().getStage(stageId);
		initNotification(stage.getInt("ID_EXP"), stageId,
				document.getInt(PropName.DOC_ID_TRAMITE),
				document.getKeyInt());
	}
	
	protected String getTipoDireccionInteresado(IItem document) throws ISPACException {

		String tipoDireccion = null;
		
		// Nombre del destinatario
	    String destino = document.getString("DESTINO");
	    if (!StringUtils.isBlank(destino)) {
	    
	    	IEntitiesAPI entitiesAPI = m_clientCtx.getAPI().getEntitiesAPI();
	    	
	    	// Información del expediente
	    	IItem expediente = entitiesAPI.getExpedient(document.getString("NUMEXP"));
			if (destino.equalsIgnoreCase(expediente.getString("IDENTIDADTITULAR"))) {
				tipoDireccion = expediente.getString("TIPODIRECCIONINTERESADO");
			} else {

				// Obtener la información de los participantes
				IItemCollection participantes = entitiesAPI.getParticipants(document.getString("NUMEXP"), null, null);
				while ((tipoDireccion == null) && participantes.next()) {
					IItem participante = participantes.value();
					if (destino.equalsIgnoreCase(participante.getString("NOMBRE"))) {
						tipoDireccion = participante.getString("TIPO_DIRECCION");
					}
				}
			}
	    }

	    return tipoDireccion;
	}

	/**
	 * Actualiza el estado de la notificación de un documento.
	 *
	 * @param documentId
	 *            Identificador del documento.
	 * @param status
	 *            Estado de notificación ({@link NotifyStatesConstants}).
	 * @param date
	 *            Fecha de notificación.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void updateNotificationStatus(int documentId, String status, Date date)
			throws ISPACException {

		// Obtener la información del documento
		IItem itemDoc = m_clientCtx.getAPI().getEntitiesAPI().getDocument(documentId);

		// Actualizar el estado y fecha de notificación del documento
		itemDoc.set("ESTADONOTIFICACION", status);
		itemDoc.set("FNOTIFICACION", (date != null ? date : new Date()));

		itemDoc.store(m_clientCtx);

		if (logger.isInfoEnabled()) {
			logger.info("Estado y fecha de notificación del documento [" + documentId
					+ "] actualizados: " + status);
		}
	}

}
