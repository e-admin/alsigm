package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHelpDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHierarchyDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityResourceDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.system.InfoSistemaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbColDef;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.EntityTableManager;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class ImportProcedureMgr {

	public static final String PATH_VENDOR = ExportProcedureMgr.TAG_PACKAGE_HEADER + "/" + ExportProcedureMgr.TAG_VENDOR;
	public static final String PATH_WORKFLOW_PROCESS = ExportProcedureMgr.TAG_WORKFLOW_PROCESSES + "/" + ExportProcedureMgr.TAG_WORKFLOW_PROCESS;
	public static final String PATH_ACTIVITY = ExportProcedureMgr.TAG_ACTIVITIES + "/" + ExportProcedureMgr.TAG_ACTIVITY;
	public static final String PATH_COORDINATES = ExportProcedureMgr.TAG_NODE_GRAPHICS_INFOS + "/" + ExportProcedureMgr.TAG_NODE_GRAPHICS_INFO + "/" + ExportProcedureMgr.TAG_COORDINATES;
	public static final String PATH_JOIN = ExportProcedureMgr.TAG_TRANSITION_RESTRICTIONS + "/" + ExportProcedureMgr.TAG_TRANSITION_RESTRICTION + "/" + ExportProcedureMgr.TAG_JOIN;
	public static final String PATH_TRANSITION = ExportProcedureMgr.TAG_TRANSITIONS + "/" + ExportProcedureMgr.TAG_TRANSITION;
	public static final String PATH_EVENT = ExportProcedureMgr.TAG_EVENTS + "/" + ExportProcedureMgr.TAG_EVENT;
	public static final String PATH_ENTITY = ExportProcedureMgr.TAG_ENTITIES + "/" + ExportProcedureMgr.TAG_ENTITY;
	public static final String PATH_VALIDATION_TABLE = ExportProcedureMgr.TAG_VALIDATION_TABLES + "/" + ExportProcedureMgr.TAG_VALIDATION_TABLE;
	public static final String PATH_HIERARCHICAL_TABLE = ExportProcedureMgr.TAG_HIERARCHICAL_TABLES+ "/" + ExportProcedureMgr.TAG_HIERARCHICAL_TABLE;
	public static final String PATH_FORM = ExportProcedureMgr.TAG_FORMS + "/" + ExportProcedureMgr.TAG_FORM;
	public static final String PATH_RESOURCE = ExportProcedureMgr.TAG_RESOURCES + "/" + ExportProcedureMgr.TAG_RESOURCE;
	public static final String PATH_TEMPLATE = ExportProcedureMgr.TAG_TEMPLATES + "/" + ExportProcedureMgr.TAG_TEMPLATE;
	public static final String PATH_TPDOC = ExportProcedureMgr.TAG_TP_DOCS + "/" + ExportProcedureMgr.TAG_TP_DOC;
	public static final String PATH_TASK = ExportProcedureMgr.TAG_TASKS + "/" + ExportProcedureMgr.TAG_TASK;
	public static final String PATH_STAGE = ExportProcedureMgr.TAG_STAGES + "/" + ExportProcedureMgr.TAG_STAGE;
	public static final String PATH_RULE = ExportProcedureMgr.TAG_RULES + "/" + ExportProcedureMgr.TAG_RULE;
	public static final String PATH_HELP = ExportProcedureMgr.TAG_HELPS + "/" + ExportProcedureMgr.TAG_HELP;
	/**
	 *
	 * @param cnt
	 * @param packageNode
	 * @param ctRuleDAOs
	 * @param importLog
	 * @param test
	 * @param version
	 * @param language
	 * @throws ISPACException
	 */
	public static void importRules(DbCnt cnt,
								   Node packageNode,
								   Map ctRuleDAOs,
								   StringBuffer importLog,
								   boolean test,
								   String version,
								   String language) throws ISPACException {

		importLog.append(ExportProcedureMgr.RETORNO);
		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.rules");
		importLog.append(ExportProcedureMgr.RETORNO);

		// Procesar las reglas
		NodeIterator itrRule = XmlFacade.getNodeIterator(packageNode, PATH_RULE);
		for (Node nodeRule = itrRule.nextNode(); nodeRule != null; nodeRule = itrRule.nextNode()) {

			String xmlRuleId = XmlFacade.getAttributeValue(nodeRule, ExportProcedureMgr.ATR_ID);

			// Control de versiones
			if (version != null) {

				// Importación de procedimientos exportados con una versión anterior a la 5.3
				if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_3)) {

					// Eliminación de la regla asociada a los procedimientos:
					// ID: 4 -> 'Inicializa Valores Expediente' -> ieci.tdw.ispac.api.rule.processes.InicializarValoresExpedienteRule
					// y eliminación de la regla asociada a los trámites:
					// ID: 7 -> 'AsignarTipoRegistro' -> ieci.tdw.ispac.api.rule.docs.SetRegistryTypeRule
					// cuyos Executes han pasado al código fuente
					int iXmlRuleId = Integer.parseInt(xmlRuleId);
					if ((iXmlRuleId == ImportProcedureMgrVersions.ID_RULE_INICIALIZAR_VALORES_EXPEDIENTE) ||
						(iXmlRuleId == ImportProcedureMgrVersions.ID_RULE_SET_REGISTRY_TYPE)) {

						// Estas reglas no se importan
						continue;
					}
	            }
			}

			String xmlRuleName = importName(XmlFacade.getAttributeValue(nodeRule, ExportProcedureMgr.ATR_NAME));

			// Comprobar si la regla ya existe
			CTRuleDAO ctRuleDAO = (CTRuleDAO) ObjectDAO.getByName(cnt, CTRuleDAO.class, xmlRuleName);
			if (ctRuleDAO == null) {

				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.rule", new String[] {xmlRuleName});

				// Comprobar si existe una regla con la misma clase
				String xmlClassName = XmlFacade.get(nodeRule, ExportProcedureMgr.TAG_CLASE);
				ctRuleDAO = CTRuleDAO.findRuleByClass(cnt, xmlClassName);
				if (ctRuleDAO == null) {

					// La regla se crea si no se está validando
					ctRuleDAO = createRule(cnt, nodeRule, xmlRuleName,xmlClassName, test);
					if (ctRuleDAO != null) {

						ProcedureUtil.generateLog(importLog, language, "import.procedure.log.rule.create", new String[] {xmlRuleName});
					}
				}
				else {
					ProcedureUtil.generateLog(importLog, language, "import.procedure.log.rule.exists.class", new String[] {xmlRuleName, xmlClassName});
				}
			}
			else {
				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.rule.exists", new String[] {xmlRuleName});
			}

			if (ctRuleDAO != null) {

				// Metemos en el map de reglas
				// [clave=id del xml, valor=la nueva regla en BD] o
				// [clave=id del xml, valor=la regla ya existente en BD]
				ctRuleDAOs.put(Integer.valueOf(xmlRuleId), ctRuleDAO);
			}
		}
	}

	/**
	 *
	 * @param ctx
	 * @param cnt
	 * @param packageNode
	 * @param ctStageDAOs
	 * @param importLog
	 * @param test
	 * @throws ISPACException
	 */
   public static void importStages(ClientContext ctx,
		   						   DbCnt cnt,
		   						   Node packageNode,
		   						   Map ctStageDAOs,
		   						   StringBuffer importLog,
		   						   boolean test) throws ISPACException {

	   String language = ctx.getAppLanguage();

	   importLog.append(ExportProcedureMgr.RETORNO);
	   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.stages");
       importLog.append(ExportProcedureMgr.RETORNO);

       // Bloquear las fases para establecer el orden en las fases que se creen
       ObjectDAO.getForUpdate(cnt, CTStageDAO.class, "");

       // Siguiente orden
       CollectionDAO collection = new CollectionDAO(CTStageDAO.class);
       int nextOrder = collection.count(cnt, "")  + 1;

       // Procesar las fases
	   NodeIterator itrStage = XmlFacade.getNodeIterator(packageNode, PATH_STAGE);
	   for (Node stageNode = itrStage.nextNode(); stageNode != null; stageNode = itrStage.nextNode()) {

		   String xmlStageName = importName(XmlFacade.getAttributeValue(stageNode, ExportProcedureMgr.ATR_NAME));
		   String xmlStageId = XmlFacade.getAttributeValue(stageNode, ExportProcedureMgr.ATR_ID);

		   // Comprobar si la fase ya existe
		   CTStageDAO ctStageDAO = (CTStageDAO) ObjectDAO.getByName(cnt, CTStageDAO.class, xmlStageName);
		   if (ctStageDAO == null) {

			   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage", new String[] {xmlStageName});

			   // La fase se crea si no se está validando
			   ctStageDAO = createStage(ctx, cnt, stageNode, xmlStageName, nextOrder, test);
			   if (ctStageDAO != null) {

				   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage.create", new String[] {xmlStageName});

				   // Metemos en el map de fases [clave=id del xml, valor=la nueva fase en BD]
				   ctStageDAOs.put(Integer.valueOf(xmlStageId), ctStageDAO);

				   nextOrder++;
			   }
		   }
		   else {
			   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.stage.exists", new String[] {xmlStageName});

			   // Metemos en el map de fases  [clave=id del xml, valor=la fase ya existente en BD]
			   ctStageDAOs.put(Integer.valueOf(xmlStageId), ctStageDAO);
		   }
	   }
   }

   /**
    *
    * @param ctx
    * @param cnt
    * @param packageNode
    * @param ctTaskDAOs
    * @param importLog
    * @param ctTpDocDAOs
    * @param test
    * @return
    * @throws ISPACException
    */
   public static Map importTasks(ClientContext ctx,
		   						 DbCnt cnt,
		   						 Node packageNode,
		   						 Map ctTaskDAOs,
		   						 StringBuffer importLog,
		   						 Map ctTpDocDAOs,
		   						 boolean test) throws ISPACException {

	   String language = ctx.getAppLanguage();

	   importLog.append(ExportProcedureMgr.RETORNO);
	   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.tasks");
       importLog.append(ExportProcedureMgr.RETORNO);

	   Map xmlSubPcdTasks = new HashMap();

       // Procesar los trámites
	   NodeIterator itrTask = XmlFacade.getNodeIterator(packageNode, PATH_TASK);
       for (Node nodeTask = itrTask.nextNode(); nodeTask != null; nodeTask = itrTask.nextNode()) {

        	String xmlTaskName = importName(XmlFacade.getAttributeValue(nodeTask, ExportProcedureMgr.ATR_NAME));
        	String xmlTaskId = XmlFacade.getAttributeValue(nodeTask, ExportProcedureMgr.ATR_ID);

        	// Comprobar si el trámite ya existe
        	CTTaskDAO ctTaskDAO = (CTTaskDAO) ObjectDAO.getByName(cnt, CTTaskDAO.class, xmlTaskName);
        	if (ctTaskDAO == null) {

        		// Si el trámite no existe se crea y se le asocian los tipos de documento
        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task", new String[] {xmlTaskName});

 			   	// El trámite se crea si no se está validando
 			   	ctTaskDAO = createTask(ctx, cnt, nodeTask, xmlTaskId, xmlTaskName, xmlSubPcdTasks, test);
 			   	if (ctTaskDAO != null) {

 			   		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task.create", new String[] {xmlTaskName});

 			   		// Metemos en el map de trámites [clave=id del xml, valor=el nuevo tramite en BD]
 			   		ctTaskDAOs.put(Integer.valueOf(xmlTaskId), ctTaskDAO);

 			   		// Procesar los tipos de documento asociados al trámite
 			   		NodeIterator itrTpDocTask = XmlFacade.getNodeIterator(nodeTask, PATH_TPDOC);
 			   		for (Node nodeTpDocTask = itrTpDocTask.nextNode(); nodeTpDocTask != null; nodeTpDocTask = itrTpDocTask.nextNode()) {

 			   			String xmlTpDocTaskId = XmlFacade.getAttributeValue(nodeTpDocTask, ExportProcedureMgr.ATR_ID);

 			   			// Obtener el tipo de documento en BD
 			   			CTTpDocDAO ctTpDocDAO = (CTTpDocDAO) ctTpDocDAOs.get(Integer.valueOf(xmlTpDocTaskId));
 			   			createTaskTpDoc(cnt, ctTaskDAO.getKeyInteger(), ctTpDocDAO.getKeyInteger(), test);

 			   			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task.tpdoc", new String[] {xmlTaskName, ctTpDocDAO.getString("NOMBRE")});
 			   		}
 			   	}
        	}
        	else {
        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task.exists", new String[] {xmlTaskName});

        		// Metemos en el map de trámites [clave=id del xml, valor=el tramite ya existente en BD]
        		ctTaskDAOs.put(Integer.valueOf(xmlTaskId), ctTaskDAO);

        		// Obtener los Ids de los tipos de documento asociados al trámite
        		IItemCollection collection = CTTaskTpDocDAO.getTaskTpDoc(cnt, ctTaskDAO.getKeyInt()).disconnect();
        		Map taskTpDocIds = collection.toMap("ID_TPDOC");

        		// Procesar los tipos de documento asociados al trámite
	        	NodeIterator itrTpDocTask = XmlFacade.getNodeIterator(nodeTask, PATH_TPDOC);
	            for (Node nodeTpDocTasks = itrTpDocTask.nextNode(); nodeTpDocTasks != null; nodeTpDocTasks = itrTpDocTask.nextNode()) {

	            	String xmlTpDocTaskId = XmlFacade.getAttributeValue(nodeTpDocTasks, ExportProcedureMgr.ATR_ID);

	            	// Obtener el tipo de documento en BD
	            	CTTpDocDAO ctTpDocDAO = (CTTpDocDAO) ctTpDocDAOs.get(Integer.valueOf(xmlTpDocTaskId));
	            	if (ctTpDocDAO != null) {

		            	Object tpDocId = ctTpDocDAO.getKey();

		            	// Asociar los tipos de documento al trámite
		            	if (taskTpDocIds.get(tpDocId) == null) {

		            		// Se asocia al trámite el tipo de documento si no se está validando
		            		CTTaskTpDocDAO ctTaskTpDocDAO = createTaskTpDoc(cnt, ctTaskDAO.getKeyInteger(), ctTpDocDAO.getKeyInteger(), test);
		            		if (ctTaskTpDocDAO != null) {

		            			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.task.tpdoc", new String[] {xmlTaskName, ctTpDocDAO.getString("NOMBRE") });
		            		}
		            	}
	            	}
	            }
        	}
        }

       return xmlSubPcdTasks;
   }

   /**
    *
    * @param ctx
    * @param cnt
    * @param packageNode
    * @param ctTpDocDAOs
    * @param ctTemplateDAOs
    * @param importLog
    * @param test
    * @param zipFile
    * @throws ISPACException
    * @throws IOException
    */
   public static void importTpDocs(ClientContext ctx,
		   						   DbCnt cnt,
		   						   Node packageNode,
		   						   Map ctTpDocDAOs,
		   						   Map ctTemplateDAOs,
		   						   StringBuffer importLog,
		   						   boolean test,
		   						   ZipFile zipFile) throws ISPACException, IOException {

	   String language = ctx.getAppLanguage();

	   importLog.append(ExportProcedureMgr.RETORNO);
	   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.tpdocs");
       importLog.append(ExportProcedureMgr.RETORNO);

       	// Procesar los tipos de documento
   		NodeIterator itTpDoc = XmlFacade.getNodeIterator(packageNode, PATH_TPDOC);
        for (Node tpDocNode = itTpDoc.nextNode(); tpDocNode != null; tpDocNode = itTpDoc.nextNode()) {

        	String tpDocName = importName(XmlFacade.getAttributeValue(tpDocNode, ExportProcedureMgr.ATR_NAME));
        	String xmlTpDocId = XmlFacade.getAttributeValue(tpDocNode, ExportProcedureMgr.ATR_ID);

        	// Comprobar si el tipo de documento ya existe
        	CTTpDocDAO ctTpDocDAO = (CTTpDocDAO) ObjectDAO.getByName(cnt, CTTpDocDAO.class, tpDocName);
        	if (ctTpDocDAO == null) {

    			// Si el tipo de documento no existe se crea junto con sus plantillas asociadas
        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.tpdoc", new String[] {tpDocName});

    			// El tipo de documento se crea si no se está validando
    			ctTpDocDAO = createTpDoc(ctx, cnt, tpDocNode, tpDocName, test);
    			if (ctTpDocDAO != null) {

    				ProcedureUtil.generateLog(importLog, language, "import.procedure.log.tpdoc.create", new String[] {tpDocName});

    				// Procesar las plantillas asociadas
		        	NodeIterator itTemplate = XmlFacade.getNodeIterator(tpDocNode, PATH_TEMPLATE);
		            for (Node templateNode = itTemplate.nextNode(); templateNode != null; templateNode = itTemplate.nextNode()) {

		            	String xmlTemplateId = XmlFacade.getAttributeValue(templateNode, ExportProcedureMgr.ATR_ID);
		            	String xmlTemplateName = importName(XmlFacade.getAttributeValue(templateNode, ExportProcedureMgr.ATR_NAME));

		            	// Crear la plantilla con su documento
		            	TemplateDAO templateDAO = createTemplate(cnt, xmlTemplateId, ctTpDocDAO.getKeyInt(), templateNode, importLog, zipFile, xmlTemplateName, test);

		            	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.template.create", new String[] {templateDAO.getString("NOMBRE"), tpDocName});

			           	// Metemos en el map de plantillas [clave=id del xml, valor=la nueva plantilla en BD]
		            	ctTemplateDAOs.put(Integer.valueOf(xmlTemplateId), templateDAO);
		            }

    	        	// Metemos en el map de tipos de documento [clave=id del xml, valor=el nuevo tipo de documento en BD]
		            ctTpDocDAOs.put(Integer.valueOf(xmlTpDocId), ctTpDocDAO);
    			}
        	}
        	else {
        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.tpdoc.exists", new String[] {tpDocName});

        		// Metemos en el map de tipos de documento [clave=id del xml, valor=el tipo de documento que ya existente en BD]
        		ctTpDocDAOs.put(Integer.valueOf(xmlTpDocId), ctTpDocDAO);

    			// Procesar las plantillas asociadas
	        	NodeIterator itTemplate = XmlFacade.getNodeIterator(tpDocNode, PATH_TEMPLATE);
	            for (Node templateNode = itTemplate.nextNode(); templateNode != null; templateNode = itTemplate.nextNode()) {

			        String xmlTemplateId = XmlFacade.getAttributeValue(templateNode, ExportProcedureMgr.ATR_ID);
		           	String xmlTemplateName = importName(XmlFacade.getAttributeValue(templateNode, ExportProcedureMgr.ATR_NAME));

		           	// Comprobar si la plantilla ya existe
		           	TemplateDAO templateDAO = (TemplateDAO) ObjectDAO.getByName(cnt, TemplateDAO.class, xmlTemplateName);
		           	if (templateDAO == null) {

		           		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.template", new String[] {xmlTemplateName, tpDocName});

		            	// La plantilla con su documento se crea si no se está validando
	           			templateDAO = createTemplate(cnt, xmlTemplateId, ctTpDocDAO.getKeyInt(), templateNode, importLog, zipFile, xmlTemplateName, test);
		           		if (templateDAO != null) {

		           			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.template.create", new String[] {xmlTemplateName, tpDocName});

		           			// Metemos en el map de plantillas [clave=id del xml, valor=la nueva plantilla en BD]
		           			ctTemplateDAOs.put(Integer.valueOf(xmlTemplateId), templateDAO);
		           		}
		           	}
		           	else {
		           		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.template.exists", new String[] {xmlTemplateName, tpDocName});

			           	// Metemeos en el map de plantillas [clave=id del xml, valor=la plantilla ya existente en BD]
		           		ctTemplateDAOs.put(Integer.valueOf(xmlTemplateId), templateDAO);
		           	}
	            }
        	}
        }
   }

   /**
    *
    * @param cnt
    * @param packageNode
    * @param ctEntityDAOs
    * @param ctFormDAOs
    * @param importLog
    * @param test
    * @param version
    * @param language
    * @throws ISPACException
    */
   public static void importEntities(DbCnt cnt,
		   							 Node packageNode,
		   							 Map ctEntityDAOs,
		   							 Map ctFormDAOs,
		   							 StringBuffer importLog,
		   							 boolean test,
		   							 String version,
		   							 String language,
		   							 ICatalogAPI catalogAPI) throws ISPACException {

	   importLog.append(ExportProcedureMgr.RETORNO);
	   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.entities");
	   importLog.append(ExportProcedureMgr.RETORNO);

	   // Procesar las entidades con sus formularios y recursos asociados
	   NodeIterator itEntity = XmlFacade.getNodeIterator(packageNode, PATH_ENTITY);
	   for (Node entityNode = itEntity.nextNode(); entityNode != null; entityNode = itEntity.nextNode()) {

        	String entityName = importName(XmlFacade.getAttributeValue(entityNode, ExportProcedureMgr.ATR_NAME));
        	String xmlEntityId = XmlFacade.getAttributeValue(entityNode, ExportProcedureMgr.ATR_ID);

        	// Comprobar si la entidad ya existe
        	CTEntityDAO ctEntityDAO = (CTEntityDAO) ObjectDAO.getByName(cnt, CTEntityDAO.class, entityName);

    		if (ctEntityDAO == null) {

    			// Si la entidad no existe se crea junto con sus formularios y recursos asociados
    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity", new String[] {entityName});

    			// La entidad se crea si no se está validando
    			ctEntityDAO = createEntity(cnt, entityNode, entityName, test, version, ctEntityDAOs);
    			if (ctEntityDAO != null) {

    				EntityDef entityDefinition = EntityDef.parseEntityDef(ctEntityDAO.getDefinition());

    				// Creación de la tabla física y la secuencia en BD
	    			createEntityTable(cnt, ctEntityDAO, entityDefinition, version);

	    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.create", new String[] {entityName});

		        	// Metemos en en map de entidades [clave=id del xml, valor=la nueva entidad en BD]
	    			ctEntityDAOs.put(Integer.valueOf(xmlEntityId), ctEntityDAO);

		        	// Formularios
		        	Node nodeForms = XmlFacade.getSingleNode(entityNode, ExportProcedureMgr.TAG_FORMS);
		        	if (nodeForms != null) {

		        		// Obtener el formulario por defecto
			        	String xmlDefaultFormId = XmlFacade.getAttributeValue(nodeForms, ExportProcedureMgr.ATR_DEFAULT_ID);

			        	// Procesar los formulario asociados
			        	NodeIterator itForm = XmlFacade.getNodeIterator(nodeForms, ExportProcedureMgr.TAG_FORM);
			            for (Node formNode = itForm.nextNode(); formNode != null; formNode = itForm.nextNode()) {

			            	String xmlFormId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID);
			            	String xmlFormName = importName(XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_NAME));

			            	// Crear el formulario
			            	CTApplicationDAO ctApplicationDAO = createApplication(cnt, formNode, xmlFormName, ctEntityDAO.getId(), entityName, test, version, ctEntityDAOs);

			            	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.form.create", new String[] {ctApplicationDAO.getName(), entityName});

				           	// Metemos en el map de formularios [clave=id del xml, valor=el nuevo formulario en BD]
			            	ctFormDAOs.put(Integer.valueOf(xmlFormId), ctApplicationDAO);
			            }

			            // Obtener el Id del formulario por defecto en BD
			            if (StringUtils.isNotBlank(xmlDefaultFormId)) {

			            	CTApplicationDAO defaultFormDAO = (CTApplicationDAO) ctFormDAOs.get(Integer.valueOf(xmlDefaultFormId));
			            	if (defaultFormDAO != null) {

			            		ctEntityDAO.set("FRM_EDIT", defaultFormDAO.getKeyInt());
			            	}
			            }
		        	}

		            // Guardar la entidad
		            ctEntityDAO.store(cnt);

		            // Crear los recursos de la entidad
		            importResources(cnt, entityNode, ctEntityDAO.getId());

		            ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.resources.create", new String[] {entityName});
    			}
    		}
    		else {
    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.exists", new String[] {entityName});

    			// Metemos en el map de entidades [clave=id del xml, valor=la entidad ya existente en BD]
    			ctEntityDAOs.put(Integer.valueOf(xmlEntityId), ctEntityDAO);

	        	// Procesar los formularios asociados a la entidad
	        	NodeIterator itForm = XmlFacade.getNodeIterator(entityNode, PATH_FORM);
	            for (Node formNode = itForm.nextNode(); formNode != null; formNode = itForm.nextNode()) {

			        String xmlFormId = XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_ID);
		           	String xmlFormName = importName(XmlFacade.getAttributeValue(formNode, ExportProcedureMgr.ATR_NAME));

		           	// Comprobar si el formulario ya existe
		           	CTApplicationDAO ctApplicationDAO = (CTApplicationDAO) ObjectDAO.getByName(cnt, CTApplicationDAO.class, xmlFormName);
		           	if (ctApplicationDAO == null) {

		           		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.form", new String[] {xmlFormName, entityName});

		           		// El formulario se crea si no se está validando
		           		ctApplicationDAO = createApplication(cnt, formNode, xmlFormName, ctEntityDAO.getId(), entityName, test, version, ctEntityDAOs);
		           		if (ctApplicationDAO != null) {

		           			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.form.create", new String[] {xmlFormName, entityName});

				           	// Metemos en el map de formularios [clave=id del xml, valor=el nuevo formulario en BD]
			            	ctFormDAOs.put(Integer.valueOf(xmlFormId), ctApplicationDAO);
		           		}
		           	}
		           	else {
		           		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.form.exists", new String[] {xmlFormName, entityName});

			           	// Metemos en el map de formularios [clave=id del xml, valor=el formulario ya existente en BD]
		           		ctFormDAOs.put(Integer.valueOf(xmlFormId), ctApplicationDAO);
		           	}
	            }

	            // Procesar los recursos de la entidad
	            ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.resources", new String[] {entityName});

	            // Importar nuevos recursos que pueden generar
	            // nuevos campos en la entidad o ser otros recursos de la entidad
	            importResourcesFromEntityExist(cnt, entityNode, ctEntityDAO.getId(), entityName, ctEntityDAO, importLog, test, language, catalogAPI);
    		}
        }
   }

   /**
    *
    * @param cnt
    * @param packageNode
    * @param ctEntityDAOs
    * @param importLog
    * @param version
    * @param test
    * @throws ISPACException
    */
   public static void importValidationTables(DbCnt cnt,
				 							 Node packageNode,
				 							 Map ctEntityDAOs,
				 							 StringBuffer importLog,
				 							 boolean test,
				 							 String version,
				 							 String language) throws ISPACException {

	   importLog.append(ExportProcedureMgr.RETORNO);
	   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.validationTables");
       importLog.append(ExportProcedureMgr.RETORNO);

       // Procesar las tablas de validación
       NodeIterator itValidationTable = XmlFacade.getNodeIterator(packageNode, PATH_VALIDATION_TABLE);
       for (Node validationTableNode = itValidationTable.nextNode(); validationTableNode != null; validationTableNode = itValidationTable.nextNode()) {

        	String validationTableName = importName(XmlFacade.getAttributeValue(validationTableNode, ExportProcedureMgr.ATR_NAME));
        	String xmlValidationTableId = XmlFacade.getAttributeValue(validationTableNode, ExportProcedureMgr.ATR_ID);

        	// Comprobar si la tabla de validación ya existe
        	CTEntityDAO ctValidationTableDAO = (CTEntityDAO) ObjectDAO.getByName(cnt, CTEntityDAO.class, validationTableName);
    		if (ctValidationTableDAO == null) {

    			// Si la tabla de validación no existe se crea
    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.validationTable", new String[] {validationTableName});

    			// La tabla de validación se crea si no se está validando
    			ctValidationTableDAO = createEntity(cnt, validationTableNode, validationTableName, test, null, null);
    			if (ctValidationTableDAO != null) {

    				EntityDef entityDefinition = EntityDef.parseEntityDef(ctValidationTableDAO.getDefinition());

    				// Cuando la tabla de validación es no editable significa
    				// que ya existe por lo que no hay que crear la tabla ni la secuencia
    				if (entityDefinition.isEditable()) {

		    			// Creación de la tabla física y la secuencia en BD
		    			createEntityTable(cnt, ctValidationTableDAO, entityDefinition, version);

		    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.validationTable.create", new String[] {validationTableName});
    				}

		        	// Metemos en en map de entidades [clave=id del xml, valor=la nueva entidad en BD]
	    			ctEntityDAOs.put(Integer.valueOf(xmlValidationTableId), ctValidationTableDAO);

		            // Guardar la tabla de validación
	    			ctValidationTableDAO.store(cnt);

		            // Procesar los recursos de la tabla de validación
	    			importResources(cnt, validationTableNode, ctValidationTableDAO.getId());
    			}
    		}
    		else {
    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.validationTable.exists", new String[] {validationTableName});

    			// Metemos en el map de entidades [clave=id del xml, valor=la entidad ya existente en BD]
    			ctEntityDAOs.put(Integer.valueOf(xmlValidationTableId), ctValidationTableDAO);
    		}
		}
   }


	public static void importHierarchicalTables(DbCnt cnt,
												Node packageNode,
												Map ctHierarchicalDAOs,
												StringBuffer importLog,
												boolean test,
												String version,
												String language)throws ISPACException {

		   importLog.append(ExportProcedureMgr.RETORNO);
		   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.hierarchicalTables");
	       importLog.append(ExportProcedureMgr.RETORNO);

	       // Procesar las tablas de jerarquicas
	       NodeIterator itHierarchicalTable = XmlFacade.getNodeIterator(packageNode, PATH_HIERARCHICAL_TABLE);
	       for (Node hierarchicalTableNode = itHierarchicalTable.nextNode(); hierarchicalTableNode != null; hierarchicalTableNode = itHierarchicalTable.nextNode()) {

	        	String hierarchicalTableName = importName(XmlFacade.getAttributeValue(hierarchicalTableNode, ExportProcedureMgr.ATR_NAME));
	        	String xmlHierarchicalTableId = XmlFacade.getAttributeValue(hierarchicalTableNode, ExportProcedureMgr.ATR_ID);

	        	// Comprobar si la tabla jerarquica ya existe
	        	CTHierarchyDAO ctHierarchicalTableDAO = (CTHierarchyDAO) ObjectDAO.getByName(cnt, CTHierarchyDAO.class, hierarchicalTableName);
	    		if (ctHierarchicalTableDAO == null) {

	    			// Si la tabla de validación no existe se crea
	    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.hierarchicalTable", new String[] {hierarchicalTableName});

	    			// La tabla de validación se crea si no se está validando
	    			//ctHierarchicalTableDAO = createEntity(cnt, hierarchicalTableNode, hierarchicalTableName, test, null, null);

	    			ctHierarchicalTableDAO = createHierarchicalTableDefinition(cnt, hierarchicalTableNode, hierarchicalTableName, test);

	    			if (ctHierarchicalTableDAO != null) {

//			        	//Si el tipo es una tabla se crea (el otro tipo seria una vista en cuyo caso no se crea)
//	    				if (ctHierarchicalTableDAO.getInt("TIPO") == HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE){
//			        		createPhysicalHierarchicalTable(cnt, ctHierarchicalTableDAO);
//			        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.hierarchicalTable.create", new String[] {hierarchicalTableName, ICatalogAPI.HIERARCHICAL_TABLE_NAME + String.valueOf(ctHierarchicalTableDAO.getKeyInt())});
//			        	}

	    				//Se crea la tabla fisica independientemente que fuera una vista
	    				createPhysicalHierarchicalTable(cnt, ctHierarchicalTableDAO);
		        		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.hierarchicalTable.create", new String[] {hierarchicalTableName, ICatalogAPI.HIERARCHICAL_TABLE_NAME + String.valueOf(ctHierarchicalTableDAO.getKeyInt())});

	    				// Metemos en en map de entidades [clave=id del xml, valor=la nueva entidad en BD]
	    				ctHierarchicalDAOs.put(Integer.valueOf(xmlHierarchicalTableId), ctHierarchicalTableDAO);

			            // Guardar la tabla de validación
		    			ctHierarchicalTableDAO.store(cnt);
	    			}
	    		}
	    		else {
	    			ProcedureUtil.generateLog(importLog, language, "import.procedure.log.hierarchicalTable.exists", new String[] {hierarchicalTableName});

	    			// Metemos en el map de entidades [clave=id del xml, valor=la entidad ya existente en BD]
	    			ctHierarchicalDAOs.put(Integer.valueOf(xmlHierarchicalTableId), ctHierarchicalTableDAO);
	    		}
			}

	}

	/**
	 *
	 * @param ctx
	 * @param cnt
	 * @param node
	 * @param ctHelpDAOs
	 * @param importLog
	 * @param test
	 * @param language
	 * @throws ISPACException
	 * @throws IOException
	 */
	public static void importHelps(ClientContext ctx,
			   						   DbCnt cnt,
			   						   Node node,
			   						   Map ctHelpDAOs,
			   						   StringBuffer importLog,
			   						   boolean test,
			   						   String language) throws ISPACException, IOException {

   		importLog.append(ExportProcedureMgr.RETORNO);
		ProcedureUtil.generateLog(importLog, language, "import.procedure.log.subtitle.helps");
		importLog.append(ExportProcedureMgr.RETORNO);


		NodeIterator itHelp = XmlFacade.getNodeIterator(node, PATH_HELP);
	    for (Node helpNode = itHelp.nextNode(); helpNode != null; helpNode = itHelp.nextNode()) {

	    	String helpName=  XmlFacade.get(helpNode, ExportProcedureMgr.TAG_HELP_NAME);
	    	if(!test){
	    		// Crear la ayuda
	    		setHelp(cnt, helpNode, ctHelpDAOs);
	    	}

	    	ProcedureUtil.generateLog(importLog, language, "import.procedure.log.help.create", new String[] {helpName , language});

	    }
	}

	/**
	 *
	 * @param cnt
	 * @param node
	 * @param entityId
	 * @throws ISPACException
	 */
	private static void importResources(DbCnt cnt,
		   							   Node node,
		   							   int entityId) throws ISPACException {

       NodeIterator itResource = XmlFacade.getNodeIterator(node, PATH_RESOURCE);
       for (Node resourceNode = itResource.nextNode(); resourceNode != null; resourceNode = itResource.nextNode()) {

    	   // Crear los recursos asociados a la entidad
    	   createResource(cnt, entityId, resourceNode);
       }
   }

   /**
    *
    * @param cnt
    * @param node
    * @param entityId
    * @throws ISPACException
    */
   private static void importResourcesFromEntityExist(DbCnt cnt,
		   							   				  Node node,
		   							   				  int entityId,
		   							   				  String entityName,
		   							   				  CTEntityDAO ctEntityDAO,
		   							   				  StringBuffer importLog,
		   							   				  boolean test,
		   							   				  String language,
		   							   				  ICatalogAPI catalogAPI) throws ISPACException {

	   // Obtener los recursos de la entidad ya existente
	   IItemCollection collectionResources = ctEntityDAO.getResources(cnt).disconnect();
	   Map entityExistResourcesMap = collectionResources.toMapStringKey("CLAVE");
	   EntityDef entityExistDefinition = EntityDef.parseEntityDef(ctEntityDAO.getDefinition());

	   // Obtener la definición de la entidad que se importa
	   String definicionEntidad = replaceEndCDATA(XmlFacade.get(node, ExportProcedureMgr.TAG_DEFINICION));
	   EntityDef entityDefinition = EntityDef.parseEntityDef(definicionEntidad);
	   List fields = entityDefinition.getFields();
	   Map validacionesCampos = entityDefinition.validationsToMapByIdField();

	   Map camposNuevos = new HashMap();
	   Map recursosNuevos = new HashMap();

	   // Procesar los recursos de la entidad
	   NodeIterator itResource = XmlFacade.getNodeIterator(node, PATH_RESOURCE);
	   for (Node resourceNode = itResource.nextNode(); resourceNode != null; resourceNode = itResource.nextNode()) {

		   // Compruebar si el recurso ya existe
		   if (resourceNode.getAttributes() != null) {

			   String clave = resourceNode.getAttributes().getNamedItem(ExportProcedureMgr.ATR_CLAVE).getNodeValue();
			   if ((StringUtils.isNotEmpty(clave)) &&
				   (!entityExistResourcesMap.containsKey(clave))) {

				   // Comprobar si el recurso es de un campo de la entidad -> nuevo campo a crear
				   // o es otro recurso
				   boolean esCampo = false;
				   for (Iterator iter = fields.iterator(); iter.hasNext() && !esCampo;) {

					   EntityField fieldDef = (EntityField) iter.next();

					   // Comprobar si el recurso se corresponde con un campo en la entidad que se importa
					   if (fieldDef.getPhysicalName().equalsIgnoreCase(clave)) {

						   // Comprobar si el campo no existe en la entidad ya existente
						   if (entityExistDefinition.findField(clave) == null) {

							   esCampo = true;
						   }
					   }
				   }

				   // Recurso que implica un nuevo campo a crear
				   if (esCampo) {

					   if(test && !camposNuevos.containsKey(clave)) {

						   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.field", new String[] {entityName, clave});
					   }

					   camposNuevos.put(clave, null);
				   }
				   // Otro recurso
				   else {
					   if (test) {

						   if (!recursosNuevos.containsKey(clave)) {

							   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.resource", new String[] {entityName , clave});
						   }
					   }
					   else {
						   createResource(cnt, entityId, resourceNode);

						   if (!recursosNuevos.containsKey(clave)) {

							   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.resource.add", new String[] {entityName , clave});
						   }
					   }
					   recursosNuevos.put(clave, null);
				   }
			   }
		   }
	   }

	   // Crear los nuevos campos en la entidad que fueron detectados a partir de los recursos
	   if(!camposNuevos.isEmpty() && !test) {

		   // Procesar los recursos para los nuevos campos de la entidad
		   itResource = XmlFacade.getNodeIterator(node, PATH_RESOURCE);
		   for (Node resourceNode = itResource.nextNode(); resourceNode != null; resourceNode = itResource.nextNode()) {

			   //Compruebo que ese recurso no existe actualmente
			   if (resourceNode.getAttributes() != null) {

				   String clave = resourceNode.getAttributes().getNamedItem(ExportProcedureMgr.ATR_CLAVE).getNodeValue();
				   if (StringUtils.isNotEmpty(clave) && camposNuevos.containsKey(clave)) {

					   if (camposNuevos.get(clave) == null) {

						   for (Iterator iter = fields.iterator(); iter.hasNext();) {

							   EntityField fieldDef = (EntityField) iter.next();

							   //Compruebo si ese campo se corresponde con el que estamos tratando
							   if (fieldDef.getPhysicalName().equalsIgnoreCase(clave)) {

								   //Añadimos el campo a la defincion de la entidad
								   fieldDef.setLogicalName(XmlFacade.get(resourceNode, ExportProcedureMgr.TAG_VALUE));
								   int idCampo = catalogAPI.addField(entityId, fieldDef, false);

								   //Compruebo si el campo tiene alguna validacion asociada
								   if (validacionesCampos.containsKey(fieldDef.getKey())) {

									   EntityValidation entityValidation = (EntityValidation) validacionesCampos.get(fieldDef.getKey());

									   //Actualizo con el nuevo id del campos que se acaba de insertar
									   entityValidation.setFieldId(idCampo);
									   catalogAPI.addValidation(entityId, entityValidation);
								   }

								   ProcedureUtil.generateLog(importLog, language, "import.procedure.log.entity.field.add", new String[] {entityName, clave});
							   }
						   }

						   camposNuevos.put(clave, Boolean.TRUE);
					   }

					   // Crear el recurso para el campo nuevo
					   createResource(cnt, entityId, resourceNode);
				   }
			   }
		   }
	   }
   }

   /**
    *
    * @param cnt
    * @param ctEntityDAO
    * @param version
    * @throws ISPACException
    */
   /*
   private static void createEntityTable(DbCnt cnt,
		   								 CTEntityDAO ctEntityDAO,
		   								 String version) throws ISPACException {

	   String tblName = ctEntityDAO.getName();
	   String sqPkName = ctEntityDAO.getSequence();
	   EntityDef entityDefinition = EntityDef.parseEntityDef(ctEntityDAO.getDefinition());

	   EntityTableManager entityTableManager = new EntityTableManager();

	   // Importación de procedimientos exportados con una versión anterior a la 5.4
	   if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_4)) {

		   // En las entidades y tablas de validación el campo ID se establece como no nulo
		   List fields = entityDefinition.getFields();
		   for (Iterator iter = fields.iterator(); iter.hasNext();) {

			   EntityField fieldDef = (EntityField) iter.next();
			   if (fieldDef.getPhysicalName().equalsIgnoreCase(ICatalogAPI.ID_FIELD_NAME)) {

				   // Campo ID no nulo
				   fieldDef.setNullable(false);

				   // Actualizar la definición de la entidad
				   ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());

				   break;
               }
           }
	   }

	   // Creación de la tabla
	   entityTableManager.createTable(cnt, tblName, entityDefinition.getFields(), entityDefinition);

	   // Creación de la secuencia
	   entityTableManager.createSequence(cnt, tblName, sqPkName);
   }
   */

   /**
    *
    * @param cnt
    * @param ctEntityDAO
    * @param entityDefinition
    * @param version
    * @throws ISPACException
    */
   private static void createEntityTable(DbCnt cnt,
		   								 CTEntityDAO ctEntityDAO,
		   								 EntityDef entityDefinition,
		   								 String version) throws ISPACException {

	   String tblName = ctEntityDAO.getName();
	   String sqPkName = ctEntityDAO.getSequence();

	   EntityTableManager entityTableManager = new EntityTableManager();

	   // Importación de procedimientos exportados con una versión anterior a la 5.4
	   if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_4)) {

		   // En las entidades y tablas de validación el campo ID se establece como no nulo
		   List fields = entityDefinition.getFields();
		   for (Iterator iter = fields.iterator(); iter.hasNext();) {

			   EntityField fieldDef = (EntityField) iter.next();
			   if (fieldDef.getPhysicalName().equalsIgnoreCase(ICatalogAPI.ID_FIELD_NAME)) {

				   // Campo ID no nulo
				   fieldDef.setNullable(false);

				   // Actualizar la definición de la entidad
				   ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());

				   break;
               }
           }
	   }

	   // Importación de procedimientos exportados con una versión anterior a la 5.5.1
	   if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_5_1)) {

		   if (!entityDefinition.containsFieldByName(ICatalogAPI.ORDEN_FIELD_NAME)) {

			   // En las tablas de validación se añade el campo ORDEN
			   EntityField orden = new EntityField();
			   orden = new EntityField();
			   orden.setId(entityDefinition.getFields().size() + 1);
			   orden.setPhysicalName(ICatalogAPI.ORDEN_FIELD_NAME);
			   orden.setType(InternalDataType.SHORT_INTEGER);
			   orden.setSize(10);
			   orden.setDescripcion("Indica el orden del valor");

			   entityDefinition.addField(orden);

			   // Actualizar la definición de la entidad
			   ctEntityDAO.set("DEFINICION", entityDefinition.getXmlValues());
		   }
	   }

	   // Creación de la tabla
	   entityTableManager.createTable(cnt, tblName, entityDefinition.getFields(), entityDefinition);

	   // Creación de la secuencia
	   entityTableManager.createSequence(cnt, tblName, sqPkName);
   }


   private static void createPhysicalHierarchicalTable(DbCnt cnt,
		CTHierarchyDAO ctHierarchicalTableDAO) throws ISPACException {

	   	   EntityTableManager entityTableManager = new EntityTableManager();

       	   // Crear la tabla para la jerarquía
           DbColDef[] colsDefs = new DbColDef[3];
           EntityField fieldPk = new EntityField();
           fieldPk.setPhysicalName(ICatalogAPI.ID_FIELD_NAME);
           fieldPk.setType(InternalDataType.LONG_INTEGER);
           colsDefs[0] = entityTableManager.newDbColDef(fieldPk);

           EntityField fieldPadre = new EntityField();
           fieldPadre.setPhysicalName(ICatalogAPI.ID_FIELD_ENTIDAD_PADRE);
           fieldPadre.setType(InternalDataType.LONG_INTEGER);
           colsDefs[1] = entityTableManager.newDbColDef(fieldPadre);

           EntityField fieldHija = new EntityField();
           fieldHija.setPhysicalName(ICatalogAPI.ID_FIELD_ENTIDAD_HIJA);
           fieldHija.setType(InternalDataType.LONG_INTEGER);
           colsDefs[2] = entityTableManager.newDbColDef(fieldHija);

           String tableName = ICatalogAPI.HIERARCHICAL_TABLE_NAME + String.valueOf(ctHierarchicalTableDAO.getKeyInt());
           entityTableManager.createTable(cnt, tableName, colsDefs);
           entityTableManager.createSequence(cnt, tableName, null);
	}

   /**
    *
    * @param cnt
    * @param node
    * @param name
    * @param clase
    * @param test
    * @return
    * @throws ISPACException
    */
   private static CTRuleDAO createRule(DbCnt cnt,
		   							   Node node,
		   							   String name,
		   							   String clase,
		   							   boolean test) throws ISPACException {

	   String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
	   String tipo = XmlFacade.get(node, ExportProcedureMgr.TAG_TIPO);

	   // Al validar la importación no se creará la regla
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear la regla
		   CTRuleDAO ctRuleDAO = new CTRuleDAO(cnt);
		   ctRuleDAO.createNew(cnt);

		   // Establecer los datos de la regla
		   ctRuleDAO.set("NOMBRE", name);
		   ctRuleDAO.set("CLASE", clase);

		   if (StringUtils.isNotBlank(descripcion)) {
			   ctRuleDAO.set("DESCRIPCION", descripcion);
		   }
		   if (StringUtils.isNotBlank(tipo)) {
			   ctRuleDAO.set("TIPO", Integer.parseInt(tipo));
		   }

		   // Guardar la regla
	       ctRuleDAO.store(cnt);

	       return ctRuleDAO;
	   }

	   return null;
   }

   /**
    *
    * @param ctx
    * @param cnt
    * @param node
    * @param name
    * @param order
    * @param test
    * @return
    * @throws ISPACException
    */
   private static CTStageDAO createStage(ClientContext ctx,
		   								 DbCnt cnt,
		   							  	 Node node,
		   							  	 String name,
		   							  	 int order,
		   							  	 boolean test) throws ISPACException {

	   String codigo = XmlFacade.get(node, ExportProcedureMgr.TAG_CODIGO);
	   String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
	   String observaciones = XmlFacade.get(node, ExportProcedureMgr.TAG_OBSERVACIONES);

	   // Al validar la importación no se creará la fase
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear la fase
		   CTStageDAO ctStageDAO = new CTStageDAO(cnt);
		   ctStageDAO.createNew(cnt);

		   // Establecer los datos de la fase
		   ctStageDAO.set("NOMBRE", name);
		   ctStageDAO.set("FALTA", DateUtil.getToday());
		   ctStageDAO.set("AUTOR", ctx.getUser().getName());
		   ctStageDAO.set("ORDEN", order);

		   if (StringUtils.isNotBlank(codigo)) {
			   ctStageDAO.set("COD_FASE", codigo);
		   }
		   if (StringUtils.isNotBlank(descripcion)) {
			   ctStageDAO.set("DESCRIPCION", descripcion);
		   }
		   if (StringUtils.isNotBlank(observaciones)) {
			   ctStageDAO.set("OBSERVACIONES", observaciones);
		   }

		   // Guardar la fase
	       ctStageDAO.store(cnt);

	       return ctStageDAO;
	   }

	   return null;
   }

   /**
    *
    * @param cnt
    * @param taskId
    * @param tpDocId
    * @param test
    * @return
    * @throws ISPACException
    */
   private static CTTaskTpDocDAO createTaskTpDoc(DbCnt cnt,
		   							   			 Integer taskId,
		   							   			 Integer tpDocId,
		   							   			 boolean test) throws ISPACException {

	   if (!test) {

		   // Asociar al trámite el tipo de documento
		   CTTaskTpDocDAO ctTaskTpDocDAO = new CTTaskTpDocDAO(cnt);
		   ctTaskTpDocDAO.createNew(cnt);
		   ctTaskTpDocDAO.set("ID_TRAMITE", taskId.intValue());
		   ctTaskTpDocDAO.set("ID_TPDOC", tpDocId.intValue());
	       ctTaskTpDocDAO.store(cnt);

	       return ctTaskTpDocDAO;
	   }

	   return null;
   }

   /**
    *
    * @param ctx
    * @param cnt
    * @param node
    * @param xmlId
    * @param name
    * @param xmlSubPcdTasks
    * @param test
    * @return
    * @throws ISPACException
    */
   private static CTTaskDAO createTask(ClientContext ctx,
		   							   DbCnt cnt,
		   							   Node node,
		   							   String xmlId,
		   							   String name,
		   							   Map xmlSubPcdTasks,
		   							   boolean test) throws ISPACException {

	   String codigo = XmlFacade.get(node, ExportProcedureMgr.TAG_CODIGO);
	   String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
	   String observaciones = XmlFacade.get(node, ExportProcedureMgr.TAG_OBSERVACIONES);
	   String tipo = XmlFacade.get(node, ExportProcedureMgr.TAG_TIPO);
	   String subPcdId = XmlFacade.get(node, ExportProcedureMgr.TAG_ID_SUBPROCESO);

	   // Al validar la importación no se creará el trámite
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear el trámite
		   CTTaskDAO ctTaskDAO = new CTTaskDAO(cnt);
		   ctTaskDAO.createNew(cnt);

		   // Establecer los datos del trámite
		   ctTaskDAO.set("NOMBRE", name);
		   ctTaskDAO.set("FCREACION", DateUtil.getToday());
		   ctTaskDAO.set("AUTOR", ctx.getUser().getName());

		   if (StringUtils.isNotBlank(codigo)) {
			   ctTaskDAO.set("COD_TRAM", codigo);
		   }
		   if (StringUtils.isNotBlank(descripcion)) {
			   ctTaskDAO.set("DESCRIPCION", descripcion);
		   }
		   if (StringUtils.isNotBlank(tipo)) {
			   ctTaskDAO.set("TIPO", tipo);
		   }
		   if (StringUtils.isNotBlank(observaciones)) {
			   ctTaskDAO.set("OBSERVACIONES", observaciones);
		   }

		   // Trámite con subproceso al que habrá que establece el Id de subproceso en BD
		   if (StringUtils.isNotBlank(subPcdId)) {
			   xmlSubPcdTasks.put(Integer.valueOf(xmlId), Integer.valueOf(subPcdId));
		   }

		   // Guardar el trámite
		   ctTaskDAO.store(cnt);

		   return ctTaskDAO;
	   }

	   return null;
   }

   /**
    *
    * @param ctx
    * @param cnt
    * @param node
    * @param name
    * @param test
    * @return
    * @throws ISPACException
    */
   private static CTTpDocDAO createTpDoc(ClientContext ctx,
		   								 DbCnt cnt,
		   								 Node node,
		   								 String name,
		   								 boolean test) throws ISPACException {

	   String codigo = XmlFacade.get(node, ExportProcedureMgr.TAG_CODIGO);
	   String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
	   String observaciones = XmlFacade.get(node, ExportProcedureMgr.TAG_OBSERVACIONES);
	   String tipo = XmlFacade.get(node, ExportProcedureMgr.TAG_TIPO);
	   String tipoReg = XmlFacade.get(node, ExportProcedureMgr.TAG_TIPO_REG);

	   // Al validar la importación no se creará el tipo de documento
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear el tipo de documento
		   CTTpDocDAO ctTpDocDAO = new CTTpDocDAO(cnt);
		   ctTpDocDAO.createNew(cnt);

		   // Establecer los datos del tipo de documento
	       ctTpDocDAO.set("NOMBRE", name);
	       ctTpDocDAO.set("FECHA", DateUtil.getToday());
	       ctTpDocDAO.set("AUTOR", ctx.getUser().getName());

	       if (StringUtils.isNotBlank(codigo)) {
	    	   ctTpDocDAO.set("COD_TPDOC", codigo);
	       }
	       if (StringUtils.isNotBlank(tipo)) {
	    	   ctTpDocDAO.set("TIPO", tipo);
	       }
	       if (StringUtils.isNotBlank(descripcion)) {
	    	   ctTpDocDAO.set("DESCRIPCION", descripcion);
	       }
	       if (StringUtils.isNotBlank(tipoReg)) {
	    	   ctTpDocDAO.set("TIPOREG", tipoReg);
	       }
	       if (StringUtils.isNotBlank(observaciones)) {
	    	   ctTpDocDAO.set("OBSERVACIONES", observaciones);
	       }

	       // Guardar el tipo de documento
	   	   ctTpDocDAO.store(cnt);

	   	   return ctTpDocDAO;
	   }

	   return null;
   }

   /**
    *
    * @param cnt
    * @param entityId
    * @param node
    * @throws ISPACException
    */
   private static void createResource(DbCnt cnt,
		   							  int entityId,
		   							  Node node) throws ISPACException {

	   String language = XmlFacade.getAttributeValue(node, ExportProcedureMgr.ATR_LANGUAGE);
	   String clave = XmlFacade.getAttributeValue(node, ExportProcedureMgr.ATR_CLAVE);
	   String value = XmlFacade.get(node, ExportProcedureMgr.TAG_VALUE);

	   // Crear el recurso
	   EntityResourceDAO entityResourceDAO = new EntityResourceDAO(cnt);
	   entityResourceDAO.createNew(cnt);

	   // Establecer los datos del recurso
	   entityResourceDAO.set("ID_ENT", entityId);
	   entityResourceDAO.set("IDIOMA", language);
	   entityResourceDAO.set("CLAVE", clave);
	   entityResourceDAO.set("VALOR", value);

	   // Guardar el recurso
	   entityResourceDAO.store(cnt);
   }

  /**
   *
   * @param cnt
   * @param node
   * @param test
   * @return
   * @throws ISPACException
   */
   private static void setHelp(DbCnt cnt,
		   								Node node,Map ctHelpDAOs ) throws ISPACException {


		   String nombre = XmlFacade.get(node, ExportProcedureMgr.TAG_HELP_NAME);
		   String tipo_obj = XmlFacade.get(node, ExportProcedureMgr.TAG_HELP_TIPO_OBJ);
		   //El id_obj depende del id que se asigne cuando se cree el objeto por lo que no se guarda
		  // String id_obj = XmlFacade.get(node, ExportProcedureMgr.TAG_HELP_ID_OBJ);
		   String contenido = XmlFacade.get(node, ExportProcedureMgr.TAG_HELP_CONTENIDO);
		   String idioma = XmlFacade.get(node, ExportProcedureMgr.TAG_HELP_IDIOMA);
		   CTHelpDAO ctHelpDAO = new CTHelpDAO(cnt);
		   ctHelpDAO.createNew(cnt);
		   ctHelpDAO.set("TIPO_OBJ",tipo_obj );
		   ctHelpDAO.set("IDIOMA",idioma );
		   ctHelpDAO.set("CONTENIDO",contenido);
		   ctHelpDAO.set("NOMBRE", nombre);

		   ctHelpDAOs.put(new Integer(ctHelpDAO.getKeyInt()), ctHelpDAO);

   }


   /**
    *
    * @param cnt
    * @param node
    * @param name
    * @param entityId
    * @param entityName
    * @param test
    * @param version
    * @param ctEntityDAOs
    * @return
    * @throws ISPACException
    */
   private static CTApplicationDAO createApplication(DbCnt cnt,
		   											 Node node,
		   											 String name,
		   											 int entityId,
		   											 String entityName,
		   											 boolean test,
		   											 String version,
		   											 Map ctEntityDAOs) throws ISPACException {

	   String descripcionForm = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
	   String claseForm = XmlFacade.get(node, ExportProcedureMgr.TAG_CLASE);
       String paginaForm = XmlFacade.get(node, ExportProcedureMgr.TAG_PAGINA);
       String parametrosForm = XmlFacade.get(node, ExportProcedureMgr.TAG_PARAMETROS);
       String formateadorForm = XmlFacade.get(node, ExportProcedureMgr.TAG_FORMATEADOR);
       String xmlFormateadorForm = XmlFacade.get(node, ExportProcedureMgr.TAG_XML_FORMATEADOR);
       String frmJspForm = XmlFacade.get(node, ExportProcedureMgr.TAG_FRM_JSP);
       String frmVersionForm = XmlFacade.get(node, ExportProcedureMgr.TAG_FRM_VERSION);

	   // Al validar la importación no se creará el formulario
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear el formulario
	  	   CTApplicationDAO ctApplicationDAO = new CTApplicationDAO(cnt);
	  	   ctApplicationDAO.createNew(cnt);

	  	   // Establecer los datos del formulario
	  	   ctApplicationDAO.set("NOMBRE", name);
	  	   ctApplicationDAO.set("CLASE", claseForm);
	  	   ctApplicationDAO.set("PAGINA", paginaForm);
	  	   ctApplicationDAO.set("ENT_PRINCIPAL_ID", entityId);
	  	   ctApplicationDAO.set("ENT_PRINCIPAL_NOMBRE", entityName);

	  	   if (StringUtils.isNotBlank(descripcionForm)) {
	  		   ctApplicationDAO.set("DESCRIPCION", descripcionForm);
	  	   }
	  	   if (StringUtils.isNotBlank(parametrosForm)) {
	  		   ctApplicationDAO.set("PARAMETROS", replaceEndCDATA(parametrosForm));
	  	   }
	  	   if (StringUtils.isNotBlank(formateadorForm)) {
	  		   ctApplicationDAO.set("FORMATEADOR", formateadorForm);
	  	   }
	  	   if (StringUtils.isNotBlank(xmlFormateadorForm)) {
	  		   ctApplicationDAO.set("XML_FORMATEADOR", xmlFormateadorForm);
	  	   }
	  	   if (StringUtils.isNotBlank(frmJspForm)) {
	  		   ctApplicationDAO.set("FRM_JSP", frmJspForm);
	  	   }
	  	   if (StringUtils.isNotBlank(frmVersionForm)) {
	  		   ctApplicationDAO.set("FRM_VERSION", 1);
	  	   }

	       // Control de versiones
	       if (version != null) {

	           // Importación de procedimientos exportados con una versión anterior a la 5.2.7
		       if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_2_7)) {

	        		// Actualizar XML de parámetros y formulario JSP
	        		// - en los parámetros  sustituir el tag id por table, el atributo primaryId por primaryTabla y el Id de tabla por el nombre de la tabla
	            	// - en el formulario JSP sustituir en el action de las lupas entity=Id por entity=Nombre
	        		ImportProcedureMgrVersions.updateForm_5_2_7(ctApplicationDAO, ctEntityDAOs);
	            }
	       }

	  	   // Guardar el formulario
	   	   ctApplicationDAO.store(cnt);

	   	   return ctApplicationDAO;
	   }

	   return null;
   }

   /**
    *
    * @param cnt
    * @param node
    * @param name
    * @param test
    * @param version
    * @param ctEntityDAOs
    * @return
    * @throws ISPACException
    */
   private static CTEntityDAO createEntity(DbCnt cnt,
		   								   Node node,
		   								   String name,
		   								   boolean test,
		   								   String version,
		   								   Map ctEntityDAOs) throws ISPACException {

       String tipo = XmlFacade.get(node, ExportProcedureMgr.TAG_TIPO);
       String campo_pk = XmlFacade.get(node, ExportProcedureMgr.TAG_CAMPO_PK);
       String campo_numexp = XmlFacade.get(node, ExportProcedureMgr.TAG_CAMPO_NUMEXP);
       String schema_expr = XmlFacade.get(node, ExportProcedureMgr.TAG_SCHEMA_EXPR);
       String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_DESCRIPCION);
       String sec_pk = XmlFacade.get(node, ExportProcedureMgr.TAG_SEC_PK);
       String frm_jsp = XmlFacade.get(node, ExportProcedureMgr.TAG_FRM_JSP);

	   // Al validar la importación no se creará la entidad
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear la entidad
	       CTEntityDAO ctEntityDAO = new CTEntityDAO(cnt);
	       ctEntityDAO.createNew(cnt);

	       // Establecer los datos de la entidad
	       ctEntityDAO.set("TIPO", Integer.parseInt(tipo));
	       ctEntityDAO.set("NOMBRE", name);
	       ctEntityDAO.set("CAMPO_PK", campo_pk);
	       ctEntityDAO.set("SEC_PK", sec_pk);
	       ctEntityDAO.set("DEFINICION", replaceEndCDATA(XmlFacade.get(node, ExportProcedureMgr.TAG_DEFINICION)));
	       ctEntityDAO.set("FECHA", new Date(System.currentTimeMillis()));

	       if (StringUtils.isNotBlank(campo_numexp)) {
	    	   ctEntityDAO.set("CAMPO_NUMEXP", campo_numexp);
	       }
	       if (StringUtils.isNotBlank(schema_expr)) {
	    	   ctEntityDAO.set("SCHEMA_EXPR", schema_expr);
	       }
	       if (StringUtils.isNotBlank(descripcion)) {
	    	   ctEntityDAO.set("DESCRIPCION", descripcion);
	       }
	       if (StringUtils.isNotBlank(frm_jsp)) {
	    	   ctEntityDAO.set("FRM_JSP", frm_jsp);
	       }

	       // Control de versiones
	       if (version != null) {

	           	// Importación de procedimientos exportados con una versión anterior a la 5.2.7
		       	if (ImportProcedureMgrVersions.isPreviousVersion(version, ImportProcedureMgrVersions.VERSION_5_2_7)) {

	        		// Actualizar XML de definición y formulario JSP
	        		// - en las validaciones de la definición sustituir el Id de tabla por el nombre de la tabla
	            	// - en el formulario JSP sustituir en el action de las lupas entity=Id por entity=Nombre
	        		ImportProcedureMgrVersions.updateCatalogEntity_5_2_7(ctEntityDAO, ctEntityDAOs);
	            }
	       }

	       return ctEntityDAO;
	   }

	   return null;
   }


   private static CTHierarchyDAO createHierarchicalTableDefinition(DbCnt cnt,
			Node node, String hierarchicalTableName,
			boolean test) throws ISPACException {


	   // Al validar la importación no se creará la tabla jerarquica sólo se leen los datos del XML
	   if (!test) {

	       //String tipo = XmlFacade.get(node, ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_TIPO);
		   //A la hora de importar establecemos todas las tablas jerarquicas como tablas y no como vistas.
		   String tipo = ""+HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE;
	       String nombreTablaValidacionPadre = XmlFacade.get(node, ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_NOMBRE_PADRE);
	       String nombreTablaValidacionHija = XmlFacade.get(node, ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_NOMBRE_HIJA);

	       CTEntityDAO ctEntityDAO = new CTEntityDAO(cnt);
	       ctEntityDAO.load(cnt, "WHERE NOMBRE = '"+nombreTablaValidacionPadre+"'");
	       int idEntidadPadre = ctEntityDAO.getKeyInt();

	       ctEntityDAO = new CTEntityDAO(cnt);
	       ctEntityDAO.load(cnt, "WHERE NOMBRE = '"+nombreTablaValidacionHija+"'");
	       int idEntidadHija = ctEntityDAO.getKeyInt();

	       String descripcion = XmlFacade.get(node, ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_DESCRIPCION);

		   CTHierarchyDAO ctHierarchyDAO = new CTHierarchyDAO(cnt);
		   ctHierarchyDAO.createNew(cnt);

		   ctHierarchyDAO.set("NOMBRE", hierarchicalTableName);
		   ctHierarchyDAO.set("TIPO", Integer.parseInt(tipo));
		   ctHierarchyDAO.set("ID_ENTIDAD_PADRE", idEntidadPadre);
		   ctHierarchyDAO.set("ID_ENTIDAD_HIJA", idEntidadHija);
		   ctHierarchyDAO.set("DESCRIPCION", descripcion);

		   return ctHierarchyDAO;


	   }
	   return null;
	}



   /**
    *
    * @param cnt
    * @param xmlTemplateId
    * @param tpDocId
    * @param node
    * @param importLog
    * @param zipFile
    * @param name
    * @param test
    * @return
    * @throws ISPACException
    * @throws IOException
    */
   private static TemplateDAO createTemplate(DbCnt cnt,
		   									 String xmlTemplateId,
		   									 int tpDocId,
		   									 Node node,
		   									 StringBuffer importLog,
		   									 ZipFile zipFile,
		   									 String name,
		   									 boolean test) throws ISPACException, IOException {

	   String expression = XmlFacade.get(node, ExportProcedureMgr.TAG_EXPRESION);
	   String nbytes = XmlFacade.get(node, ExportProcedureMgr.TAG_NBYTES);
	   String mimeType = XmlFacade.get(node, ExportProcedureMgr.TAG_MIMETYPE);
	   String codigo = XmlFacade.get(node, ExportProcedureMgr.TAG_CODIGO);


	   // Al validar la importación no se creará la plantilla
	   // sólo se leen los datos del XML
	   if (!test) {

		   // Crear la plantilla
		   TemplateDAO templateDAO = new TemplateDAO(cnt);
		   templateDAO.createNew(cnt);

		   // Establecer los datos de la plantilla
		   templateDAO.set("NOMBRE", name);
	  	   templateDAO.set("FECHA", new Date(System.currentTimeMillis()));
	  	   templateDAO.set("ID_TPDOC", tpDocId);
	  	   templateDAO.set("COD_PLANT", codigo);

	  	   if (StringUtils.isNotBlank(expression)) {
	  		 templateDAO.set("EXPRESION", expression);
	  	   }

	  	   // Guardar la plantilla
	  	   templateDAO.store(cnt);

	  	   // Obtener el documento de la plantilla
	  	   ZipEntry zipEntry = (ZipEntry) zipFile.getEntry(xmlTemplateId + ExportProcedureMgr.TEMPLATE_FILE_EXTENSION);
	  	   if (zipEntry != null) {

	  		   // Crear el documento de la plantilla
	  		   InputStream zipEntryInputStream = null;
	  		   try {
	  			   zipEntryInputStream = zipFile.getInputStream(zipEntry);
	  			   templateDAO.setTemplate(cnt, zipEntryInputStream, Integer.parseInt(nbytes), mimeType);
	  		   } finally {
	  			   if (zipEntryInputStream != null) {
	  				   try {
	  					   zipEntryInputStream.close();
	  				   } catch (Exception e) {
	  				   }
	  			   }
	  		   }
	  	   }

	   	   return templateDAO;
	   }

	   return null;
   }

   /**
    *
    * @param value
    * @return
    */
   public static String replaceEndCDATA(String value) {

	   return StringUtils.replace(value, ExportProcedureMgr.END_CDATA_REPLACE, ExportProcedureMgr.END_CDATA);
   }

   /**
    *
    * @param cnt
    * @param packageNode
    * @return
    * @throws ISPACException
    */
   public static String validateExportVersion(DbCnt cnt,
		   									  Node packageNode) throws ISPACException {

	   String vendor = XmlFacade.get(packageNode, PATH_VENDOR);

	   try {
		   String version = vendor.split(" ")[1];

		   String[] exportVersion = version.split("\\.");
		   //String[] productVersion = ISPACVersion.getInstance().get(ISPACVersion.PRODUCT_VERSION).split("\\.");

		   String versionApp = "";

		   // Obtener la versión de la aplicación
		   InfoSistemaDAO infoSistema = InfoSistemaDAO.getVersionApp(cnt);
		   if (infoSistema != null) {
			   versionApp = infoSistema.getVersion();
		   }

		   String[] productVersion = versionApp.split("\\.");

		   for(int i = 0; i < exportVersion.length; i++) {

			   // La versión de exportación tiene más numeración
			   // luego es una versión superior a la versión del producto
			   if (productVersion.length <= i) {
				   return null;
			   }

			   int iExportVersion = Integer.parseInt(exportVersion[i]);
			   int iProductVersion = Integer.parseInt(productVersion[i]);

			   if (iProductVersion > iExportVersion) {
				   return version;
			   }
			   else if (iProductVersion < iExportVersion) {
				   return null;
			   }
		   }

		   return version;
	   }
	   catch (Exception e) {

		   throw new ISPACInfo("exception.procedures.import.errorVersion", e.getMessage());
	   }
   }

   	/**
   	 *
   	 * @param node
   	 * @return
   	 * @throws ISPACException
   	 */
	public static GInfo createGInfo(Node node) throws ISPACException {

		Node coordinatesNode = XmlFacade.getSingleNode(node, PATH_COORDINATES);
		if (coordinatesNode != null) {

			String x = XmlFacade.getAttributeValue(coordinatesNode, ExportProcedureMgr.ATR_X_COORDINATE);
			String y = XmlFacade.getAttributeValue(coordinatesNode, ExportProcedureMgr.ATR_Y_COORDINATE);

			if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {

				return new GInfo((new Double(x)).intValue(), (new Double(y)).intValue());
			}
		}

		return null;
	}

	/**
	 *
	 * @param cnt
	 * @param node
	 * @param idObj
	 * @param tpObj
	 * @param idRule
	 * @throws ISPACException
	 */
	public static void createEvent(DbCnt cnt,
								   Node node,
								   int idObj,
								   int tpObj,
								   int idRule) throws ISPACException {

		PEventoDAO event = new PEventoDAO(cnt);
		event.createNew(cnt);

		// Objeto al que afecta el evento
		event.set("ID_OBJ", idObj);
		event.set("TP_OBJ", tpObj);
		event.set("EVENTO", Integer.parseInt(XmlFacade.getAttributeValue(node, ExportProcedureMgr.ATR_ID)));

		// Orden
		event.setOrderSequence(cnt);

		// Id de la regla
		event.set("ID_REGLA", idRule);

		// Condición SQL cuando ID_REGLA es nulo
		if (idRule == ISPACEntities.ENTITY_NULLREGKEYID) {

			String condition = XmlFacade.get(node, ExportProcedureMgr.TAG_CONDITION);
			if (condition != null) {
				event.set("CONDICION", replaceEndCDATA(condition));
			}
		}

		event.store(cnt);
	}

    public static String importName(String value) {

    	return StringUtils.unescapeXml(value);
    }



}