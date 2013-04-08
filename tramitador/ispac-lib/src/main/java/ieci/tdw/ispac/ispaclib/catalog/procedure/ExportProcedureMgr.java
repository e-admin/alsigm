package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTHierarchyDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTStageDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.xml.XmlCTTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.system.InfoSistemaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.CollectionUtils;

public class ExportProcedureMgr {

	// Etiquetas para los tags, atributos y valores en el fichero XPDL generado
	public static final String TAG_PACKAGE = "Package";
	public static final String TAG_PACKAGE_HEADER = "PackageHeader";
	public static final String TAG_XPDL_VERSION = "XPDLVersion";
	public static final String TAG_VENDOR = "Vendor";
	public static final String TAG_CREATED = "Created";
	public static final String TAG_WORKFLOW_PROCESSES = "WorkflowProcesses";
	public static final String TAG_WORKFLOW_PROCESS= "WorkflowProcess";
	public static final String TAG_ACTIVITIES = "Activities";
	public static final String TAG_ACTIVITY = "Activity";
	public static final String TAG_ROUTE = "Route";
	public static final String TAG_TRANSITION_RESTRICTIONS = "TransitionRestrictions";
	public static final String TAG_TRANSITION_RESTRICTION = "TransitionRestriction";
	public static final String TAG_TRANSITION_REFS = "TransitionRefs";
	public static final String TAG_TRANSITION_REF = "TransitionRef";
	public static final String TAG_JOIN = "Join";
	public static final String TAG_SPLIT = "Split";
	public static final String TAG_TRANSITIONS = "Transitions";
	public static final String TAG_TRANSITION = "Transition";
	public static final String TAG_IMPLEMENTATION = "Implementation";
	public static final String TAG_NO = "No";
	public static final String TAG_NODE_GRAPHICS_INFOS = "NodeGraphicsInfos";
	public static final String TAG_NODE_GRAPHICS_INFO = "NodeGraphicsInfo";
	public static final String TAG_COORDINATES = "Coordinates";

	public static final String ATR_ID = "Id";
	public static final String ATR_NAME = "Name";
	public static final String ATR_GATEWAY_TYPE = "GatewayType";
	public static final String ATR_TYPE = "Type";
	public static final String ATR_FROM = "From";
	public static final String ATR_TO = "To";
	public static final String ATR_X_COORDINATE = "XCoordinate";
	public static final String ATR_Y_COORDINATE = "YCoordinate";
	public static final String ATR_OBLIGATORY = "Obligatory";
	public static final String ATR_READONLY = "Readonly";

	public static final String VAL_XOR = "XOR";
	public static final String VAL_AND = "AND";

	public static final String VAL_YES = "YES";
	public static final String VAL_NO = "NO";

	// Tags y atributos para elementos de catálogo
	public static final String TAG_STAGES = "Stages";
	public static final String TAG_STAGE = "Stage";
	public static final String TAG_TASKS = "Tasks";
	public static final String TAG_TASK = "Task";
	public static final String TAG_TP_DOCS = "TpDocs";
	public static final String TAG_TP_DOC = "TpDoc";
	public static final String TAG_TEMPLATES = "Templates";
	public static final String TAG_TEMPLATE = "Template";
	public static final String TAG_ENTITIES = "Entities";
	public static final String TAG_ENTITY = "Entity";
	public static final String TAG_FORMS = "Forms";
	public static final String TAG_FORM = "Form";
	public static final String TAG_DEPENDENCIES = "Dependencies";
	public static final String TAG_DEPENDENCY = "Dependency";
	public static final String TAG_RESOURCES = "Resources";
	public static final String TAG_RESOURCE = "Resource";
	public static final String TAG_VALIDATION_TABLES = "ValidationTables";
	public static final String TAG_VALIDATION_TABLE = "ValidationTable";
	public static final String TAG_RULES = "Rules";
	public static final String TAG_RULE = "Rule";
	public static final String TAG_EVENTS = "Events";
	public static final String TAG_EVENT = "Event";
	public static final String TAG_CONDITION = "Condition";
	public static final String TAG_CODIGO = "Codigo";
	public static final String TAG_VERSION = "Version";
	public static final String TAG_TITULO = "Titulo";
	public static final String TAG_OBJETO = "Objeto";
	public static final String TAG_ASUNTO = "Asunto";
	public static final String TAG_ACTIVIDAD_FUNCION = "ActividadFuncion";
	public static final String TAG_MATERIAS_COMPETENCIA = "MateriasCompetencia";
	public static final String TAG_SITUACION_TELEMATICA = "SituacionTelematica";
	public static final String TAG_URL = "Url";
	public static final String TAG_INTERESADOS = "Interesados";
	public static final String TAG_TIPO_RELACION = "TipoRelacion";
	public static final String TAG_FORMA_INICIACION = "FormaIniciacion";
	public static final String TAG_PLAZO_RESOLUCION = "PlazoResolucion";
	public static final String TAG_UNIDAD_PLAZO = "UnidadPlazo";
	public static final String TAG_EFECTO_SILENCIO = "EfectoSilencio";
	public static final String TAG_FIN_VIA_ADMINISTRATIVA = "FinViaAdministrativa";
	public static final String TAG_RECURSOS = "Recursos";
	public static final String TAG_FECHA_CATALOGACION = "FechaCatalogacion";
	public static final String TAG_AUTOR = "Autor";
	public static final String TAG_OBSERVACIONES = "Observaciones";
	public static final String TAG_LUGAR_PRESENTACION = "LugarPresentacion";
	public static final String TAG_CONDICIONES_ECONOMICAS = "CondicionesEconomicas";
	public static final String TAG_INGRESOS = "Ingresos";
	public static final String TAG_FORMAS_PAGOS_COBROS = "FormasPagosCobros";
	public static final String TAG_INFRACCIONES_SANCIONES = "InfraccionesSanciones";
	public static final String TAG_CALENDARIO = "Calendario";
	public static final String TAG_DESCRIPCION_TRAMITES = "DescripcionTramites";
	public static final String TAG_NORMATIVA = "Normativa";
	public static final String TAG_CONDICIONES_PARTICIPACION = "CondicionesParticipacion";
	public static final String TAG_DOCUMENTACION_APORTAR = "DocumentacionAportar";
	public static final String TAG_CONFIGURACION_RT = "ConfiguracionRT";
	public static final String TAG_TIPO = "Tipo";
	public static final String TAG_CAMPO_PK = "CampoPK";
	public static final String TAG_CAMPO_NUMEXP = "CampoNumExp";
	public static final String TAG_SCHEMA_EXPR = "SchemaExpr";
	public static final String TAG_DESCRIPCION = "Descripcion";
	public static final String TAG_SEC_PK = "SecPK";
	public static final String TAG_DEFINICION = "Definicion";
	public static final String TAG_FRM_JSP = "FrmJsp";
	public static final String TAG_CLASE = "Clase";
	public static final String TAG_PAGINA = "Pagina";
	public static final String TAG_PARAMETROS = "Parametros";
	public static final String TAG_FORMATEADOR = "Formateador";
	public static final String TAG_XML_FORMATEADOR = "XMLFormateador";
	public static final String TAG_FRM_VERSION = "FrmVersion";
	public static final String TAG_VALUE = "Value";
	public static final String TAG_TIPO_REG = "TipoReg";
	public static final String TAG_ID_SUBPROCESO = "IdSubproceso";
	public static final String TAG_EXPRESION = "Expresion";
	public static final String TAG_NBYTES = "NBytes";
	public static final String TAG_MIMETYPE = "MimeType";
	public static final String TAG_TP_REL = "TpRel";

	public static final String ATR_ID_CATALOGO = "IdCatalogo";
	public static final String ATR_ID_ENTIDAD = "IdEntidad";
	public static final String ATR_ID_REGLA = "IdRegla";
	public static final String ATR_ID_REGLA_FORMULARIO = "IdReglaFormulario";
	public static final String ATR_ID_REGLA_VISIBLE = "IdReglaVisible";
	public static final String ATR_DEFAULT_ID = "DefaultId";
	public static final String ATR_LANGUAGE = "Language";
	public static final String ATR_CLAVE = "Clave";
	public static final String ATR_ID_TRAMITE = "TaskId";

	public static final String END_CDATA = "]]>";
	public static final String END_CDATA_REPLACE = "]]&gt;";

	public static final String RETORNO = "\r\n";

	public static final String TEMPLATE_FILE_EXTENSION = ".tmp";
	public static final String TAG_HIERARCHICAL_TABLES = "HierarchicalTables";
	public static final String TAG_HIERARCHICAL_TABLE = "HierarchicalTable";

	public static final String TAG_HIERARCHICAL_FIELD_ID = "HierarchicalTable";
	public static final String TAG_HIERARCHICAL_FIELD_NOMBRE = "Nombre";
	public static final String TAG_HIERARCHICAL_FIELD_ID_PADRE = "IdPadre";
	public static final String TAG_HIERARCHICAL_FIELD_ID_HIJA = "IdHija";
	public static final String TAG_HIERARCHICAL_FIELD_NOMBRE_PADRE = "NombrePadre";
	public static final String TAG_HIERARCHICAL_FIELD_NOMBRE_HIJA = "NombreHija";
	public static final String TAG_HIERARCHICAL_FIELD_TIPO = "Tipo";
	public static final String TAG_HIERARCHICAL_FIELD_DESCRIPCION = "Descripcion";


	public static final String TAG_HELPS="Helps";
	public static final String TAG_HELP="Help";
	public static final String TAG_HELP_NAME="Nombre";
	public static final String TAG_HELP_TIPO_OBJ="TipoObjeto";
	public static final String TAG_HELP_ID_OBJ="IdObjeto";
	public static final String TAG_HELP_IDIOMA="Idioma";
	public static final String TAG_HELP_CONTENIDO="Contenido";



	/**
	 *
	 * @param procedureId
	 * @param procedureName
	 * @return
	 * @throws ISPACException
	 */
	public static String getXpdlPackageAttributes(int procedureId,
												  String procedureName) throws ISPACException {

		StringBuffer buffer = new StringBuffer();

		buffer.append(XmlTag.newAttr(ATR_ID, String.valueOf(procedureId)))
			  .append(XmlTag.newAttr(ATR_NAME, exportName(procedureName)))
			  /*
			  .append(XmlTag.newAttr("xmlns:deprecated", "http://www.wfmc.org/2002/XPDL1.0"))
			  .append(XmlTag.newAttr("xmlns", "http://www.wfmc.org/2004/XPDL2.0alpha"))
			  */
			  .append(XmlTag.newAttr("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"))
			  .append(XmlTag.newAttr("xsi:schemaLocation", "http://www.wfmc.org/2004/XPDL2.0alpha http://www.wfmc.org/standards/docs/TC-1025_bpmnxpdl_24.xsd"));

		return buffer.toString();
	}

	/**
	 *
	 * @param cnt
	 * @return
	 * @throws ISPACException
	 */
	public static String getXpdlPackageHeader(DbCnt cnt) throws ISPACException {

		StringBuffer buffer = new StringBuffer();

		String versionApp = "";

		// Obtener la versión de la aplicación
		InfoSistemaDAO infoSistema = InfoSistemaDAO.getVersionApp(cnt);
		if (infoSistema != null) {
			versionApp = infoSistema.getVersion();
		}

		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_XPDL_VERSION, "2.0"))
			  //.append(XmlTag.newTag(ExportProcedureMgr.TAG_VENDOR, "ISPAC " + ISPACVersion.getInstance().get(ISPACVersion.PRODUCT_VERSION)))
			  .append(XmlTag.newTag(ExportProcedureMgr.TAG_VENDOR, "ISPAC " + versionApp))
			  .append(XmlTag.newTag(ExportProcedureMgr.TAG_CREATED, DateUtil.formatDateTime(new Date())));

		return XmlTag.newTag(ExportProcedureMgr.TAG_PACKAGE_HEADER, buffer.toString());
	}

	/**
	 *
	 * @param cnt
	 * @param it
	 * @param ctStageIds
	 * @param ctTaskIds
	 * @param ctRuleIds
	 * @param ctEntityIds
	 * @param ctTpDocIds
	 * @param subPcdIds
	 * @return
	 * @throws ISPACException
	 */
    public static String elementsToXpdl(DbCnt cnt,
    									Iterator it,
    									Map ctStageIds,
    									Map ctTaskIds,
    									Map ctRuleIds,
    									Map ctEntityIds,
    									Map ctTpDocIds,
    									Map subPcdIds) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	while (it.hasNext()) {

    		IPcdElement el = (IPcdElement) it.next();
    		buffer.append(el.toXpdl(cnt, ctStageIds, ctTaskIds, ctRuleIds, ctEntityIds, ctTpDocIds, subPcdIds));
    	}

    	return buffer.toString();
    }

    /**
     *
     * @param events
     * @param ctRuleIds
     * @return
     * @throws ISPACException
     */
    public static String eventsToXml(List events,
    								 Map ctRuleIds) throws ISPACException {

    	StringBuffer buffer =  new StringBuffer();

    	if ((events != null) &&
    		(!events.isEmpty())) {

    		Iterator it = events.iterator();
    		while (it.hasNext()) {

    			IItem event = (IItem) it.next();

    			String idRegla = event.getString("ID_REGLA");
    			Integer id = Integer.valueOf(idRegla);
    			if (id.intValue() != ISPACEntities.ENTITY_NULLREGKEYID) {

    				// Evento con regla
	    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_EVENT, null,
	    										XmlTag.newAttr(ExportProcedureMgr.ATR_ID, event.getString("EVENTO")) +
	    										XmlTag.newAttr(ExportProcedureMgr.ATR_ID_REGLA, idRegla)));

	    			if (!ctRuleIds.containsKey(id)) {
	    				ctRuleIds.put(id, null);
	    			}
    			}
    			else {
    				// Evento con condición SQL
	    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_EVENT,
	    										XmlTag.newTag(ExportProcedureMgr.TAG_CONDITION,  ExportProcedureMgr.replaceEndCDATA(event.getString("CONDICION")), true),
												XmlTag.newAttr(ExportProcedureMgr.ATR_ID, event.getString("EVENTO"))));
    			}
    		}
    	}

   		return XmlTag.newTag(ExportProcedureMgr.TAG_EVENTS, buffer.toString());
    }

    /**
     *
     * @param forms
     * @param ctEntityIds
     * @param ctRuleIds
     * @return
     * @throws ISPACException
     */
    public static String formsToXml(List forms,
    								Map ctEntityIds,
    								Map ctRuleIds) throws ISPACException {

    	StringBuffer buffer =  new StringBuffer();

    	if ((forms != null) &&
    		(!forms.isEmpty())) {

    		Iterator it = forms.iterator();
    		while (it.hasNext()) {

    			IItem form = (IItem) it.next();

    			String idEntidad = form.getString("ID_ENT");
    			Integer id = Integer.valueOf(idEntidad);

    			StringBuffer attributes =  new StringBuffer();
    			attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_ENTIDAD, idEntidad));

    			// Formulario de la entidad asignado
    			String sFrmEdit = form.getString("FRM_EDIT");
    			if (StringUtils.isNotBlank(sFrmEdit)) {
    				attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, sFrmEdit));
    			}

    			// Formulario en sólo lectura
    			String sFrmReadonly = form.getString("FRM_READONLY");
    			if (StringUtils.isNotBlank(sFrmReadonly)) {

    				int frmReadonly = Integer.parseInt(sFrmReadonly);
    				if (ISPACEntities.ENTITY_FORM_EDITABLE == frmReadonly) {
    					attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_READONLY, ExportProcedureMgr.VAL_NO));
    				}
    				else if (ISPACEntities.ENTITY_FORM_READONLY == frmReadonly) {
    					attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_READONLY, ExportProcedureMgr.VAL_YES));
    				}
    			}

    			// Regla que asigna el formulario
    			String sRuleFormId = form.getString("ID_RULE_FRM");
    			if (StringUtils.isNotBlank(sRuleFormId)) {

    				attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_REGLA_FORMULARIO, sRuleFormId));

			    	Integer ruleId = Integer.valueOf(sRuleFormId);
	    			if (!ctRuleIds.containsKey(ruleId)) {
	    				ctRuleIds.put(ruleId, null);
	    			}
    			}

    			// Regla que establece la visibilidad de la entidad
    			String sRuleVisibleId = form.getString("ID_RULE_VISIBLE");
    			if (StringUtils.isNotBlank(sRuleVisibleId)) {

    				attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_REGLA_VISIBLE, sRuleVisibleId));

			    	Integer ruleId = Integer.valueOf(sRuleVisibleId);
	    			if (!ctRuleIds.containsKey(ruleId)) {
	    				ctRuleIds.put(ruleId, null);
	    			}
    			}

    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FORM, null, attributes.toString()));

    			if (!ctEntityIds.containsKey(id)) {
    				ctEntityIds.put(id, null);
    			}
    		}
    	}

   		return XmlTag.newTag(ExportProcedureMgr.TAG_FORMS, buffer.toString());
    }

    /**
     *
     * @param tpdocs
     * @param ctTpDocIds
     * @return
     * @throws ISPACException
     */
    public static String tpDocsToXml(List tpdocs,
    								 Map ctTpDocIds) throws ISPACException {

    	StringBuffer buffer =  new StringBuffer();

    	if ((tpdocs != null) &&
    		(!tpdocs.isEmpty())) {

    		Iterator it = tpdocs.iterator();
    		while (it.hasNext()) {

    			IItem tpdoc = (IItem) it.next();

    			String idTpdoc = tpdoc.getString("ID_TPDOC");
    			Integer id = Integer.valueOf(idTpdoc);

    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOC, null,
    										XmlTag.newAttr("Id", idTpdoc)));

    			if (!ctTpDocIds.containsKey(id)) {
    				ctTpDocIds.put(id, null);
    			}
    		}
    	}

   		return XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOCS, buffer.toString());
    }

    /**
     *
     * @param cnt
     * @param ctStageIds
     * @return
     * @throws ISPACException
     */
    public static String ctStagesToXml(DbCnt cnt,
    								   Map ctStageIds) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	if (!ctStageIds.isEmpty()) {

    		IItemCollection ctStages = ObjectDAO.getByIds(cnt, XmlCTStageDAO.class, ctStageIds, "ORDER BY ORDEN").disconnect();
    		while (ctStages.next()) {

    			XmlCTStageDAO xmlCTStageDAO = (XmlCTStageDAO) ctStages.value();
    			buffer.append(xmlCTStageDAO.export());
    		}
    	}

    	return buffer.toString();
    }

    /**
     *
     * @param cnt
     * @param ctTaskIds
     * @param ctTpDocIds
     * @return
     * @throws ISPACException
     */
    public static String ctTasksToXml(DbCnt cnt,
    								  Map ctTaskIds,
    								  Map ctTpDocIds) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	if (!ctTaskIds.isEmpty()) {

    		IItemCollection ctTasks = ObjectDAO.getByIds(cnt, XmlCTTaskDAO.class, ctTaskIds, null).disconnect();
    		while (ctTasks.next()) {

    			XmlCTTaskDAO xmlCTTaskDAO = (XmlCTTaskDAO) ctTasks.value();
    			buffer.append(xmlCTTaskDAO.export(cnt, ctTpDocIds));
    		}
    	}

    	return buffer.toString();
    }

    /**
     *
     * @param cnt
     * @param ctRuleIds
     * @param ruleClasses
     * @return
     * @throws ISPACException
     */
    public static String ctRulesToXml(DbCnt cnt,
    								  Map ctRuleIds,
    								  List ruleClasses) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	if (!ctRuleIds.isEmpty()) {

    		IItemCollection ctRules = ObjectDAO.getByIds(cnt, XmlCTRuleDAO.class, ctRuleIds, null).disconnect();
    		while (ctRules.next()) {

    			XmlCTRuleDAO xmlCTRuleDAO = (XmlCTRuleDAO) ctRules.value();
    			buffer.append(xmlCTRuleDAO.export(ruleClasses));
    		}
    	}

    	return buffer.toString();
    }

    /**
     *
     * @param cnt
     * @param ctTpDocIds
     * @param procedureId
     * @param templates
     * @return
     * @throws ISPACException
     */
    public static String ctTpDocsToXml(DbCnt cnt,
    								   Map ctTpDocIds,
    								   int procedureId,
    								   List templates) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	if (!ctTpDocIds.isEmpty()) {

    		IItemCollection ctTpDocs = ObjectDAO.getByIds(cnt, XmlCTTpDocDAO.class, ctTpDocIds, null).disconnect();
    		while (ctTpDocs.next()) {

    			XmlCTTpDocDAO xmlCTTpDocDAO = (XmlCTTpDocDAO) ctTpDocs.value();
    			buffer.append(xmlCTTpDocDAO.export(cnt, procedureId, templates));
    		}
    	}

    	return buffer.toString();
    }

    /**
     *
     * @param cnt
     * @param ctEntityIds
     * @param ctValidationTableNames
     * @param formatters
     * @param formClasses
     * @return
     * @throws ISPACException
     */
    public static String ctEntitiesToXml(DbCnt cnt,
    									 Map ctEntityIds,
    									 Map ctValidationTableNames,
    									 Map ctHierarchicalTableNames,
    									 Map formatters,
    									 List formClasses) throws ISPACException {

    	StringBuffer entitiesInPcdBuffer = new StringBuffer();
    	StringBuffer entitiesInFormBuffer = new StringBuffer();

    	/*
    	// No exportar las entidades por defecto de
    	// expediente, trámite, documento e interviniente
    	ctEntityIds.remove(new Integer(ISPACEntities.DT_ID_EXPEDIENTES));
    	ctEntityIds.remove(new Integer(ISPACEntities.DT_ID_TRAMITES));
    	ctEntityIds.remove(new Integer(ISPACEntities.DT_ID_DOCUMENTOS));
    	ctEntityIds.remove(new Integer(ISPACEntities.DT_ID_INTERVINIENTES));
    	*/

    	if (!ctEntityIds.isEmpty()) {

    		// Entidades utilizadas en los formularios
    		// que no están declaradas como entidades del procedimiento
    		Map ctEntityNamesInForms = new HashMap();

    		// Exportar las entidades del procedimiento
    		IItemCollection ctEntities = ObjectDAO.getByIds(cnt, XmlCTEntityDAO.class, ctEntityIds, null).disconnect();
    		while(ctEntities.next()) {

    			XmlCTEntityDAO xmlCTEntityDAO = (XmlCTEntityDAO) ctEntities.value();

    			// Los formularios sólo se exportan cuando la entidad es del procedimiento
    			entitiesInPcdBuffer.append(xmlCTEntityDAO.export(cnt, ctValidationTableNames, ctHierarchicalTableNames ,formatters, formClasses, true, ctEntityNamesInForms));

    			ctEntityIds.put(xmlCTEntityDAO.getKeyInteger(), xmlCTEntityDAO.getName());
    		}

    		// Procesar las entidades utilizadas en los formularios
    		if (!ctEntityNamesInForms.isEmpty()) {

    			// Eliminar las entidades ya exportadas (las pertenecientes al procedimiento)
    			Iterator it = ctEntityIds.values().iterator();
    			while (it.hasNext()) {
    				ctEntityNamesInForms.remove((String) it.next());
    			}

    			if (!ctEntityNamesInForms.isEmpty()) {

    				// Exportar las entidades utilizadas en los formularios
    	    		ctEntities = ObjectDAO.getByNames(cnt, XmlCTEntityDAO.class, ctEntityNamesInForms, null).disconnect();
    	    		while(ctEntities.next()) {

    	    			XmlCTEntityDAO xmlCTEntityDAO = (XmlCTEntityDAO) ctEntities.value();
    	    			// Sus formularios no se exportan
    	    			entitiesInFormBuffer.append(xmlCTEntityDAO.export(cnt, ctValidationTableNames, ctHierarchicalTableNames,  formatters, formClasses, false, null));
    	    		}
    			}
    		}
    	}

    	return (entitiesInFormBuffer.toString() + entitiesInPcdBuffer.toString());
    }

    /**
     *
     * @param cnt
     * @param ctValidationTableNames
     * @return
     * @throws ISPACException
     */
    public static String ctValidationTablesToXml(DbCnt cnt,
			 									 Map ctValidationTableNames, Map ctHierarchicalTableNames) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

    	if (!ctValidationTableNames.isEmpty()) {

    		IItemCollection ctValidationTables = ObjectDAO.getByNames(cnt, XmlCTEntityDAO.class, ctValidationTableNames, null).disconnect();
    		while(ctValidationTables.next()) {

    			XmlCTEntityDAO xmlCTEntityDAO = (XmlCTEntityDAO) ctValidationTables.value();
    			buffer.append(xmlCTEntityDAO.export(cnt, ctValidationTableNames, ctHierarchicalTableNames, null, null, false, null));
    		}
    	}

    	return buffer.toString();
    }


    /**
     *
     * @param cnt
     * @return
     * @throws ISPACException
     */
    public static String ctHierarchicalTablesToXml(DbCnt cnt, Map ctValidationTableNames, Map ctHierarchicalTableNames) throws ISPACException {
    	StringBuffer buffer = new StringBuffer();

    	if (!ctHierarchicalTableNames.isEmpty()) {

    		IItemCollection ctHierarchicalTables = ObjectDAO.getByNames(cnt, XmlCTHierarchyDAO.class, ctHierarchicalTableNames, null).disconnect();
    		while(ctHierarchicalTables.next()) {

    			XmlCTHierarchyDAO xmlCTHierarchyDAO = (XmlCTHierarchyDAO)ctHierarchicalTables.value();
    			buffer.append(xmlCTHierarchyDAO.export(cnt, ctValidationTableNames, ctHierarchicalTableNames));
    		}
    	}

    	return buffer.toString();
    }


    /**
     *
     * @param cnt
     * @param ctStageIds
     * @return
     * @throws ISPACException
     */
    public static String ctHelpsToXml(List helplist,
    								   Map ctHelpsIds) throws ISPACException {

    	StringBuffer buffer =  new StringBuffer();
    	String sXml = "";

    	if ((helplist != null) &&
    		(!helplist.isEmpty())) {

    		Iterator it = helplist.iterator();
    		while (it.hasNext()) {

    			IItem help = (IItem) it.next();
    			int id=help.getKeyInt();
    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HELP_NAME, help.getString("NOMBRE"), true));
    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HELP_TIPO_OBJ, help.getInt("TIPO_OBJ")));
    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HELP_ID_OBJ, help.getInt("ID_OBJ")));
    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HELP_CONTENIDO, help.getString("CONTENIDO"), true));
    			buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HELP_IDIOMA, help.getString("IDIOMA"), true));


    			sXml+=XmlTag.newTag(ExportProcedureMgr.TAG_HELP, buffer.toString());

    			if (!ctHelpsIds.containsKey(new Integer(id))) {
    				ctHelpsIds.put(new Integer (id), null);
    			}
    		}
    	}

   		return XmlTag.newTag(ExportProcedureMgr.TAG_HELPS, sXml.toString());
    }
    /**
     *
     * @param cnt
     * @param xpdl
     * @param templates
     * @param path
     * @param formatters
     * @param entityClasses
     * @param ruleClasses
     * @param ctValidationTableNames
     * @param ctHierarchicalTableNames
     * @param language
     * @return
     * @throws ISPACException
     */
    public static File createProcedureZipFile(DbCnt cnt,
    										  String xpdl,
    										  List templates,
    										  String path,
    										  Map formatters,
    										  List entityClasses,
    										  List ruleClasses,
    										  Map ctValidationTableNames,
    										  Map ctHierarchicalTableNames,
    										  String language) throws ISPACException {

		// Manejador de ficheros temporales
		FileTemporaryManager ftMgr = FileTemporaryManager.getInstance();

		// Crear un fichero temporal para el zip
		File zipFile = ftMgr.newFile(".zip");

		try {
			// Iniciar el zip
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
			out.setEncoding("CP437");

			// XPDL del procedimiento
			out.putNextEntry(new ZipEntry(ProcedureUtil.getString(language, "export.procedure.fileName.xpdl")));
			out.write(xpdl.getBytes());
			// Finalizar entrada
			out.closeEntry();

			// Plantillas
			if (!templates.isEmpty()) {

				Iterator it = templates.iterator();
				while (it.hasNext()) {

					TemplateDAO templateDAO = (TemplateDAO) it.next();

					// Plantilla
					out.putNextEntry(new ZipEntry(String.valueOf(templateDAO.getKeyInt()) + ExportProcedureMgr.TEMPLATE_FILE_EXTENSION));
					templateDAO.getTemplate(cnt, out);
					// Finalizar entrada
					out.closeEntry();
				}
			}

			// Readme con instrucciones
			StringBuffer readme = new StringBuffer();

			if ((!formatters.isEmpty()) ||
				(!entityClasses.isEmpty()) ||
				(!ruleClasses.isEmpty())) {

				readme.append(ProcedureUtil.getString(language, "export.procedure.readme.info.files")).append(RETORNO).append(RETORNO);

				// Formateadores para los formularios de las entidades
				if (!formatters.isEmpty()) {
					readme.append(generateReadmeInfo(ProcedureUtil.getString(language, "export.procedure.readme.info.formatters"),
													 formatters.keySet().iterator()));
				}
				// Clases para los formularios de las entidades
				if (!entityClasses.isEmpty()) {
					readme.append(generateReadmeInfo(ProcedureUtil.getString(language, "export.procedure.readme.info.classes.forms"),
													 entityClasses.iterator()));
				}
				// Clases para las reglas
				if (!ruleClasses.isEmpty()) {
					readme.append(generateReadmeInfo(ProcedureUtil.getString(language, "export.procedure.readme.info.classes.rules"),
													 ruleClasses.iterator()));
				}
			}

			// Tablas de validación
			if (!ctValidationTableNames.isEmpty()) {
				readme.append(generateReadmeInfo(ProcedureUtil.getString(language, "export.procedure.readme.info.validationTables"),
												 ctValidationTableNames.keySet().iterator()));
			}

			// Tablas jerarquicas
			if (!ctHierarchicalTableNames.isEmpty()) {
				readme.append(generateReadmeInfo(ProcedureUtil.getString(language, "export.procedure.readme.info.hierarchicalTables"),
												ctHierarchicalTableNames.keySet().iterator()));
			}

			if (readme.length() > 0) {

				// Readme
				out.putNextEntry(new ZipEntry(ProcedureUtil.getString(language, "export.procedure.fileName.readme")));
				out.write(readme.toString().getBytes());
				// Finalizar entrada
				out.closeEntry();
			}

			// Finalizar el zip
			out.close();
		}
		catch (IOException e) {

			if (zipFile != null) {
		        // Eliminar fichero temporal del zip
		        FileUtils.deleteFile(zipFile);
			}

			throw new ISPACException(e);
		}

		return zipFile;
    }

    /**
     *
     * @param title
     * @param it
     * @return
     */
    private static String generateReadmeInfo(String title,
    										 Iterator it) {

    	StringBuffer buffer = new StringBuffer();

    	if (title != null) {
    		buffer.append(title).append(RETORNO).append(RETORNO);
    	}

    	Map infos = new HashMap();

    	while (it.hasNext()) {

    		String info = (String) it.next();
    		if (!infos.containsKey(info)) {

    			buffer.append(info).append(RETORNO);
    			infos.put(info, null);
    		}
    	}

    	buffer.append(RETORNO);

    	return buffer.toString();
    }

    /**
     * Cambiar todas aquellas subcadenas del string con la forma ]]> por ]]&gt;
     * para evitar tener un CDATA dentro de otro CDATA
     * @param value
     * @return
     */
    public static String replaceEndCDATA(String value) {

    	return StringUtils.replace(value, ExportProcedureMgr.END_CDATA, ExportProcedureMgr.END_CDATA_REPLACE);
    }

    public static String exportName(String value) {

    	return StringUtils.escapeXml(value);
    }

    public static String getXpdlNodeGraphicsInfo(GInfo gInfo) throws ISPACException {

    	StringBuffer buffer = new StringBuffer();

		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_COORDINATES, null,
					  XmlTag.newAttr(ExportProcedureMgr.ATR_X_COORDINATE, new Double(gInfo.getX()).toString()) +
					  XmlTag.newAttr(ExportProcedureMgr.ATR_Y_COORDINATE, new Double(gInfo.getY()).toString())));

		return XmlTag.newTag(ExportProcedureMgr.TAG_NODE_GRAPHICS_INFOS,
							 XmlTag.newTag(ExportProcedureMgr.TAG_NODE_GRAPHICS_INFO, buffer.toString()));
    }

    
	public static String dependenciesToXml(List dependencies)
			throws ISPACException {

		StringBuffer buffer = new StringBuffer();

		if (!CollectionUtils.isEmpty(dependencies)) {

			Iterator it = dependencies.iterator();
			while (it.hasNext()) {

				IItem dependency = (IItem) it.next();

				StringBuffer attributes = new StringBuffer();
				attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, dependency.getString("ID")));
				attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID_TRAMITE, dependency.getString("ID_TRAMITE_PADRE")));

				buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DEPENDENCY, null, attributes.toString()));
			}
		}

		return XmlTag.newTag(ExportProcedureMgr.TAG_DEPENDENCIES, buffer.toString());
	}
  
}