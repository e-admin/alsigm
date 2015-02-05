package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaccatalog.action.entities.EntityManageAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.app.EntityAppConstants;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.builders.JSPBuilder;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.wizard.WizardButton;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class  ShowCTEntityToManageAction extends EntityManageAction {

    public static final String ENTITY_BEAN_KEY  = "entityBean";
    public static final String ACTUAL_LABEL_KEY = "actualLabelKey";
    public static final String LABELS_LIST_KEY  = "labelsListKey";
    
    public static final String GENERAL_LABEL_KEY     = "1";
    public static final String FIELDS_LABEL_KEY      = "2";
    public static final String INDEXES_LABEL_KEY     = "3";
    public static final String VALIDATIONS_LABEL_KEY = "4";
    public static final String FORMS_LABEL_KEY       = "5";
    public static final String RESOURCES_LABEL_KEY   = "6";
    
    public static final String LISTA_VALORES_LABEL_KEY = "3";
      
    private static final String VIEWGENERAL_METHOD 	= "viewgeneral";
    private static final String FIELDS_METHOD 		= "fields";
    private static final String LIST_VALUES_METHOD 	= "listavalores";
    private static final String VALIDATIONS_METHOD 	= "validations";
    private static final String FORMS_METHOD 		= "forms";
    private static final String RESOURCES_METHOD    = "resources";
    
    private static final String PROPERTY_ENTITY_DEF = "ENTITY_DEF";
    private static final String EDITANDO 			= "EDITANDO";
    
    /*
    private final String GENERAL_METHOD = "general";
    private final String REMAKEJSP_METHOD = "remakejsp";
    private static final String WIZARD_ENTITY_BEAN_KEY  = "WIZARD_ENTITY_BEAN";
    private static final String WIZARD_ENTITY_DEF_KEY   = "WIZARD_ENTITY_DEF";
    private static final String WIZARD_TITLE_KEY_KEY    = "WIZARD_TITLE_KEY";
    */
	
    /**
     * Inicializacion de la vista de gestion de la entidad
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param session
     * @return
     * @throws ISPACException
     */
    public ActionForward init(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {

        //creacion de objeto entidad
        ItemBean entityBean = createEntidadesAplicacionesCompositeBean(request, session);
        request.getSession().setAttribute(ENTITY_BEAN_KEY, 
                entityBean);

        //creacion de pestañas
        WizardButton[] labels = createLabels(request);
        request.getSession().setAttribute(LABELS_LIST_KEY, labels);


        return viewgeneral(mapping, form, request, response, session);
        
    }
    
    public ActionForward remakejsp(ActionMapping mapping, 
    							   ActionForm form,
    							   HttpServletRequest request,
    							   HttpServletResponse response,
    							   SessionAPI session) throws ISPACException {
    	
        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        
        int entityId = getEntityId(request).intValue();
    	EntityDef entityDef = getEntityDef(request);
    	
    	// Regenerar el formulario JSP de la entidad
    	String jspCode = catalogAPI.regenerateEntityForm(entityId, entityDef);
  
    	ItemBean entityBean = getEntityBeanFromSession(request);
    	entityBean.setProperty("SPAC_CT_ENTIDADES:FRM_JSP", jspCode);

    	request.setAttribute("refresh", "false");
    	
    	// Cargar en un iframe el mensaje de generacion completada
        return mapping.findForward("success_remakejsp");
    }

    /**
     * Muestra la informacion general de la entidad
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param session
     * @return
     * @throws ISPACException
     */
    public ActionForward viewgeneral(ActionMapping mapping, ActionForm form,
                HttpServletRequest request, HttpServletResponse response,
                SessionAPI session) throws ISPACException {
        
        ItemBean entityBean = getEntityBeanFromSession(request);
        
        ClientContext cct = session.getClientContext();
        
        int tipoEntidad = getTipo(request);
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
        checkFunctions(request, cct, tipoEntidad);
        
        EntityForm entityForm = (EntityForm) form;
        entityForm.setItemBean(entityBean);
        entityForm.setReadonly("true");
        
        entityForm.setProperty("ETIQUETA", entityBean.getString("ETIQUETA"));
        
//        entityBean.setProperty("LABELS", labelsMap);
//        entityBean.setProperty("ETIQUETA", labelsMap.get(entityBean.getProperty("SPAC_CT_ENTIDADES:NOMBRE")));

        configureLabels(getLabelsFromSession(request), GENERAL_LABEL_KEY);
        configureTitles(request, session);

        boolean isEntidadProceso = EntityType.isValidationTableType(getTipo(request));

        //botones 
        List buttons = new ArrayList();
        
        if ((isEntidadProceso && FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT))
        		|| (!isEntidadProceso && FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT))) {
        	buttons.add(new WizardButton("forms.button.edit",composeJScriptURLForLabel(request,"general")));
        }
        
        // Ver uso para las entidades
        if (isEntidadProceso) {
       
        	String actionUrl = "/showComponentsUseList.do?entityId=" + ICatalogAPI.ENTITY_CT_ENTITY
        			   		 + "&regId=" + entityBean.getProperty("SPAC_CT_ENTIDADES:ID");
        	
        	buttons.add(new WizardButton("forms.button.use", composeJScriptIspacSubmitURL(request, actionUrl)));
        }
        else {
        	// Tipo de la tabla de validación y si es una tabla ya existente
        	entityForm.setProperty("TYPE_TBLVLD", composeValidationTableType(request));
        }
        
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step1.general");
        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));

//        if (EntityType.isValidationTableType(getTipo(request))) {
//            request.setAttribute(TIENE_APPDEFAULT, Boolean.FALSE);
//        } else
//            request.setAttribute(TIENE_APPDEFAULT, Boolean.TRUE);
        
 
        return mapping.findForward("viewgeneral");
    }
    
    private String composeValidationTableType(HttpServletRequest request) throws ISPACException {
    	
    	StringBuffer buffer = new StringBuffer();
    	int type = getTipo(request);
    	
    	// Tipo
    	if (type == EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId()) {
    		buffer.append(getResources(request).getMessage("form.entity.tablaValidacionSimple"));
    	}
    	else if (type == EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId()) {
    		buffer.append(getResources(request).getMessage("form.entity.tablaValidacionSustituto"));
    	}
    	
    	// Ya existente
        EntityDef entityDef = getEntityDef(request);
        if (!entityDef.isEditable()) {
        	buffer.append(" (")
        		  .append(getResources(request).getMessage("form.entity.propertyLabel.tablaYaExistente"))
        		  .append(")");
        }
        
        return buffer.toString();
    }
    
    int getTipo(HttpServletRequest request) throws ISPACException{
        String tipo = getEntityBeanFromSession(request).getString("SPAC_CT_ENTIDADES:TIPO");
        int intTipo = Integer.parseInt(tipo);
        return intTipo;
    }
    
    public ActionForward savegeneral(ActionMapping mapping, 
    								 ActionForm form,
    								 HttpServletRequest request, 
    								 HttpServletResponse response,
    								 SessionAPI session) throws ISPACException {

    	ClientContext cct = session.getClientContext();
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

    	ActionMessages errors = new ActionMessages();        
        EntityForm entityForm = (EntityForm) form;
        
        // Validación
		entityForm.setEntityAppName("EditCTEntity");
		errors = entityForm.validate(mapping, request);
		if (errors.isEmpty()) {

	        catalogAPI.modifyEntity(getEntityId(request).intValue(), 
	        						entityForm.getProperty("SPAC_CT_ENTIDADES:NOMBRE"), 
	        						entityForm.getProperty("ETIQUETA"), 
	        						entityForm.getProperty("SPAC_CT_ENTIDADES:DESCRIPCION"), 
	        						getEntityDef(request),
	        						cct.getAppLanguage());
	        
	        //actualizar objeto de session
	        ItemBean entityBean = getEntityBeanFromSession(request);
	        entityBean.setProperty("SPAC_CT_ENTIDADES:DESCRIPCION",  entityForm
	                .getProperty("SPAC_CT_ENTIDADES:DESCRIPCION"));
	        //entityBean.setProperty("SPAC_CT_ENTIDADES:FRM_EDIT",  frmEdit);
	        entityBean.setProperty("SPAC_CT_APLICACIONES:NOMBRE", entityForm.getProperty("SPAC_CT_APLICACIONES:NOMBRE"));
	        entityBean.setProperty("ETIQUETA", entityForm.getProperty("ETIQUETA"));
	        
	        return viewgeneral(mapping, form, request, response, session);
		}
		else {
			
			saveErrors(request, errors);

	        List buttons = new ArrayList();
	        buttons.add(new WizardButton("forms.button.save",
	                composeJScriptURLForLabel(request, "savegeneral")));
	        buttons.add(new WizardButton("forms.button.close",
	                composeJScriptURLForLabel(request, "viewgeneral")));
	        putButtonsInRequest(request, buttons);

	        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step1.general");
	        request.setAttribute(EDITANDO, Boolean.TRUE);
	        configureTitles(request, session);

	        return mapping.findForward("general");
		}
    }
    
    private void configureTitles(HttpServletRequest request, SessionAPI session) 
    		throws ISPACException {

    	ClientContext cct = session.getClientContext();
    	
    	if (getTipo(request) == EntityType.PROCESS_ENTITY_TYPE.getId()) {
	        request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "entityManage.label.modificacionDeLaEntidad") + " '" + getEntityLabel(request) + "'");
    	} else {
	        request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "entityManage.label.modificacionDeLaTablaValidacion") + " '" + getEntityLabel(request) + "'");
    	}
        
        request.setAttribute(WIZARD_SUBTITLE_KEY, null);
    }

    public ActionForward general(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {

        ItemBean entityBean = getEntityBeanFromSession(request);

        EntityForm entityForm = (EntityForm) form;
        entityForm.setItemBean(entityBean);

        List buttons = new ArrayList();
        buttons.add(new WizardButton("forms.button.save",
                composeJScriptURLForLabel(request, "savegeneral")));
        buttons.add(new WizardButton("forms.button.close",
                composeJScriptURLForLabel(request, "viewgeneral")));
        putButtonsInRequest(request, buttons);

        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step1.general");
        request.setAttribute(EDITANDO, Boolean.TRUE);
        configureTitles(request, session);

//        if (EntityType.isValidationTableType(getTipo(request))) {
//            request.setAttribute(TIENE_APPDEFAULT, Boolean.FALSE);
//        } else
//            request.setAttribute(TIENE_APPDEFAULT, Boolean.TRUE);

        return mapping.findForward("general");
    }
    
    public ActionForward indexes(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
    
    	ClientContext cct = session.getClientContext();
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
    	checkFunctions(request, cct, getTipo(request));

        ItemBean entityBean = getEntityBeanFromSession(request);
        
        EntityForm entityForm = (EntityForm) form;
        entityForm.setItemBean(entityBean);
        
        configureLabels(getLabelsFromSession(request), INDEXES_LABEL_KEY);
        configureTitles(request, session);
        
        EntityDef entityDef = getEntityDef(request);
        
        if (entityDef.isEditable() 
        		&& FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT)) {
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);
        }
        else {
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.FALSE);
        }
        
        return prepareAndRedirectToIndexesPage(entityDef, request, mapping, "/formatters/entities/indexlistformatter.xml");
    }
    
    private Integer getEntityId(HttpServletRequest request) throws ISPACException{
        int id = getEntityBeanFromSession(request).getItem().getInt("SPAC_CT_ENTIDADES:ID");
        return new Integer(id);
    }
    
    private String getEntityName(HttpServletRequest request) throws ISPACException{
        return getEntityBeanFromSession(request).getString("SPAC_CT_ENTIDADES:NOMBRE");
    }
    
    private String getEntityLabel(HttpServletRequest request) throws ISPACException{
        return getEntityBeanFromSession(request).getString("ETIQUETA");
    }
    
    public ActionForward addIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request);
        return logicAddIndex(mapping, form, request, response,
                session, getEntityId(request), entityDef, true);
    }
    
    /**
     * Elimina los campos seleccionados del índice.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
//    public ActionForward deleteIndexFields(ActionMapping mapping, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response,
//            SessionAPI session) throws ISPACException {
//        
//        List fieldList = (List) request.getSession()
//            .getAttribute("INDEX_FIELD_LIST");
//
//        String [] ids = ((EntityForm) form).getMultibox();
//
//        if (!ArrayUtils.isEmpty(ids) && !CollectionUtils.isEmpty(fieldList)) {
//            Iterator it = fieldList.iterator();
//            for (int cont = 1; it.hasNext(); cont++) {
//                it.next();
//                if (ArrayUtils.contains(ids, String.valueOf(cont))) {
//                    it.remove();
//                }
//            }
//        }
//        
//        return mapping.findForward("index");
//    }

    public ActionForward validations(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {

    	ClientContext cct = session.getClientContext();
    	
    	int tipoEntidad = getTipo(request);
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
    	checkFunctions(request, cct, tipoEntidad);

    	EntityDef entityDef = getEntityDef(request);
        
        configureLabels(getLabelsFromSession(request), VALIDATIONS_LABEL_KEY);
        configureTitles(request, session);
        
        if (entityDef.isEditable()
        		&& FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT)) {
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);
        }
        else {
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.FALSE);
        }
        
        return prepareAndRedirectoToValidationsPage(entityDef, request, mapping, session, getEntityId(request).toString());
    }

    public ActionForward validation(ActionMapping mapping, 
    								ActionForm form,
    								HttpServletRequest request, 
    								HttpServletResponse response,
    								SessionAPI session) throws ISPACException {
    	
        configureTitles(request, session);
        return validation(mapping, form, request, response, session, getEntityDef(request), null, getEntityId(request).toString() );
    }

    public ActionForward addValidation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {

        // Información de la definición de la entidad
        EntityDef entityDef = getEntityDef(request);
        return addValidation(mapping, form, request, response, session,
                getEntityId(request), entityDef, true);
    }
        
    protected ItemBean getEntityBeanFromSession(HttpServletRequest request){
        return (ItemBean )request.getSession().getAttribute(ENTITY_BEAN_KEY);
    }
    
    /**
     * Crea las pestañas que se muestran en la JSP
     * @param request
     * @return
     * @throws ISPACException 
     */
    public WizardButton[] createLabels(HttpServletRequest request) throws ISPACException{
        if (getTipo(request) == EntityType.PROCESS_ENTITY_TYPE.getId()) {
            return new WizardButton[] {
                    new WizardButton("forms.button.close", composeJScriptIspacSubmitURL(request, "/showCTEntitiesList.do"), false),
                    new WizardButton("entityManage.label.general", composeJScriptURLForLabel(request, VIEWGENERAL_METHOD), false),
                    new WizardButton("entityManage.label.fields", composeJScriptURLForLabel(request, FIELDS_METHOD), false),
                    new WizardButton("entityManage.label.indexes", composeJScriptURLForLabel(request, INDEXES_METHOD), false),
                    new WizardButton("entityManage.label.validations", composeJScriptURLForLabel(request, VALIDATIONS_METHOD), false),
                    //new WizardButton("entityManage.label.remakeJsp", composeJScriptIspacShowFrameURL(request, REMAKEJSP_METHOD), false)
                    new WizardButton("entityManage.label.forms", composeJScriptURLForLabel(request, FORMS_METHOD), false), 
                    new WizardButton("entityManage.label.resources", composeJScriptURLForLabel(request, RESOURCES_METHOD), false)       
            };
        }
        else{
            return new WizardButton[] {
                    new WizardButton("forms.button.close", composeJScriptIspacSubmitURL(request, "/showCTValueTablesList.do"), false),
                    new WizardButton("entityManage.label.general", composeJScriptURLForLabel(request, VIEWGENERAL_METHOD), false),
                    new WizardButton("entityManage.label.fields", composeJScriptURLForLabel(request, FIELDS_METHOD), false),
                    new WizardButton("entityManage.label.valores", composeJScriptURLForLabel(request, LIST_VALUES_METHOD), false)
            };
        }
    } 
    
    public void configureLabels(WizardButton[] labels, String actualLabelKey){
        //activar todas 
        for (int i = 0; i < labels.length; i++) {
            labels[i].setActive(true);
        }
        //desactivar la actual
        labels[Integer.parseInt(actualLabelKey)].setActive(false);
    }

    private WizardButton[] getLabelsFromSession(HttpServletRequest request){
        return (WizardButton[] )request.getSession().getAttribute(LABELS_LIST_KEY);
    }


    private EntityDef getEntityDef(HttpServletRequest request) throws ISPACException{
        ItemBean itBean = getEntityBeanFromSession(request);
        return (EntityDef)itBean.getProperty(PROPERTY_ENTITY_DEF);
    }
    
    /**
     * Obtiene la entidad asociada
     *
     * @param entityId identificador de la entidad asociada
     * @return EntityDAO
     */
    /*
    protected IItem getAsociatedEntityApp(SessionAPI session, IItem itemEntity)
    throws ISPACException
    {
        IInvesflowAPI invesFlowAPI = session.getAPI();

        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        
        int ctentityId = ICatalogAPI.ENTITY_CT_APP;
        int appid = itemEntity.getInt("FRM_EDIT");

        try
        {
            IItemCollection collection =
            catalogAPI.queryCTEntities(ctentityId, "WHERE ID = " + appid);

            if (!collection.next())
                return catalogAPI.getCTEntity(ctentityId);
                //throw new ISPACException(" CTEntityApp: No existe la aplicación");

            return collection.value();
        }
        catch (ISPACException e)
        {
            throw new ISPACException(" ", e);
        }
    }
    */
    
    private ItemBean createEntidadesAplicacionesCompositeBean(HttpServletRequest request, 
            												  SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	
    	IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        int entityId = Integer.parseInt(request.getParameter("entityId"));
        String regId = request.getParameter("regId");

        IItemCollection collection =
            catalogAPI.queryCTEntities(entityId, "WHERE ID = " + regId);
        IItem itemEntity = null;
            if (!collection.next())
                itemEntity = catalogAPI.getCTEntity(entityId);
            itemEntity = collection.value();


        CompositeItem entity = new CompositeItem("SPAC_CT_ENTIDADES:ID");
        entity.addItem( itemEntity, "SPAC_CT_ENTIDADES:");
        
        /*
        entity.addItem(getAsociatedEntityApp(session, itemEntity), "SPAC_CT_APLICACIONES:");
        */
        
        ItemBean entityBean = new ItemBean(entity); 
        String xmlDefinicion = entity.getString("SPAC_CT_ENTIDADES:DEFINICION");
        entityBean.setProperty( PROPERTY_ENTITY_DEF, EntityDef.parseEntityDef(xmlDefinicion));

        // Etiqueta
        HashMap labelsMap = new HashMap();
        IItemCollection labelsCollection = entitiesAPI.getEntityResources(Integer.parseInt(regId), cct.getAppLanguage());
    	while (labelsCollection.next()) {

    		// Establecer las etiquetas
    		IItem item = labelsCollection.value();
    		labelsMap.put(item.getString("CLAVE"), item.getString("VALOR"));
    	}
    	entityBean.setProperty("LABELS", labelsMap);
        entityBean.setProperty("ETIQUETA", labelsMap.get(entityBean.getProperty("SPAC_CT_ENTIDADES:NOMBRE")));
        return entityBean;
    }

    public ActionForward initIndex(ActionMapping mapping, 
    							   ActionForm form,
    							   HttpServletRequest request, HttpServletResponse response,
    							   SessionAPI session) throws ISPACException {

        request.getSession().removeAttribute(INDEX_FIELD_LIST);
        configureTitles(request, session);
        return index(mapping, form, request, response, session, null);
    }

//    public ActionForward viewindex(ActionMapping mapping, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response,
//            SessionAPI session) throws ISPACException {
//
//        String titulo = "";
//        List buttons = new ArrayList();
//        buttons.add(new WizardButton("forms.button.edit",composeJScriptURLForLabel(request,"index")));
//        request.setAttribute(WIZARD_BUTTONBAR_KEY, 
//                buttons.toArray(new WizardButton[buttons.size()]));
//        
//        EntityDef entityDef = getEntityDef(request);
//        int indexId = Integer.parseInt(request.getParameter("indexId"));
//
//        EntityForm entityForm = (EntityForm) form;
//        
//        EntityIndex aIndex = null;
//        List indexes = entityDef.getIndexes();
//        for (Iterator iter = indexes.iterator(); iter.hasNext();) {
//            aIndex = (EntityIndex) iter.next();
//            if (aIndex.getId()== indexId){
//                break;
//            }
//        }
//        
//        entityForm.fillForm(aIndex);
//        
//        //obtencion de lista de campos del indice
//        List fieldsIndex = new ArrayList();
//        HashMap mapFields = entityDef.fieldsToMap();
//        List fieldIds = aIndex.getFieldIds();
//        for (Iterator iter = fieldIds.iterator(); iter.hasNext();) {
//            Integer fieldId = (Integer) iter.next();
//            EntityField field = (EntityField)mapFields.get(fieldId);
//            fieldsIndex.add(createItemBeanEntityField(field));
//        }
//        
//        request.getSession().setAttribute(INDEX_FIELD_LIST, fieldsIndex);
//        
//        String nextAction = request.getServletPath(); 
//
//
//        buttons.add(new WizardButton("forms.button.back", 
//                "javascript:ispac_submit(\"" + request.getContextPath() 
//                    +  nextAction + "?method=indexes\")"));
//
//        
//        request.setAttribute(WIZARD_TITLE_KEY, Messages.getString("entityManage.label.informacionDelIndice"));
//        
//        request.setAttribute(WIZARD_BUTTONBAR_KEY, 
//                buttons.toArray(new WizardButton[buttons.size()]));
//
//        request.setAttribute(ACTION_DESTINO_KEY, nextAction);
//        
//        return mapping.findForward("index_page");
//        
//    }

    public ActionForward index(ActionMapping mapping, 
    						   ActionForm form,
    						   HttpServletRequest request,
    						   HttpServletResponse response,
    						   SessionAPI session) throws ISPACException {
    	
    	configureTitles(request, session);
        return index(mapping, form, request, response, session, null);
    }
    

    public ActionForward saveindex(ActionMapping mapping, 
    							   ActionForm form,
    							   HttpServletRequest request,
    							   HttpServletResponse response,
    							   SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        
        String titulo = "";
        EntityForm entityForm = (EntityForm) form;
        
        //eliminar el indice seleccionado
        String nameIndex = entityForm.getProperty("NAME");
        EntityDef entityDef = getEntityDef(request);
        List indexes = entityDef.getIndexes();
        for (Iterator itIndexes = indexes.iterator(); itIndexes.hasNext();) {
        	
            EntityIndex aIndex = (EntityIndex) itIndexes.next();
            if (aIndex.getName().equals(nameIndex)) {
            	
                //campos del indice
                List idsFieldsIndex = getIdsIndexFieldList(request); 
                if (CollectionUtils.isEmpty(idsFieldsIndex)){
                    //no puede añadirse un indice sin campos
                    saveErrors(request, getErrorIndiceSinCamposNoValido());
                    return getActionForwardToThisAction(request, INDEX_METHOD);
                }

                entityForm.composeEntityIndex(aIndex.getId(), idsFieldsIndex);
                catalogAPI.updateIndex(getEntityId(request).intValue(), aIndex);
            }
        }

        //crear un indice nuevo
        catalogAPI.modifyEntity(getEntityId(request).intValue(), 
        						entityForm.getProperty("SPAC_CT_ENTIDADES:NOMBRE"),
        						entityForm.getProperty("ETIQUETA"),
        						entityForm.getProperty("SPAC_CT_ENTIDADES:DESCRIPCION"), 
        						getEntityDef(request), 
        						cct.getAppLanguage());
        
        return index(mapping, form, request, response, session, titulo);
    }
    
    /**
     * Muestra la lista de campos de la entidad.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
    public ActionForward indexFields(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request);
        return prepareAndRedirectToIndexFieldsPage(entityDef, request, mapping, getEntityId(request).toString(), session);
    }

    /**
     * Sube el campo del índice una posición.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
    public ActionForward indexFieldUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        List fieldList = (List) request.getSession()
            .getAttribute("INDEX_FIELD_LIST");

        String pos = request.getParameter("pos");
        
        if (StringUtils.isNotBlank(pos) && !CollectionUtils.isEmpty(fieldList)) {
            
            int ipos = TypeConverter.parseInt(pos);
            if ((ipos <= fieldList.size()) && (ipos > 1) ) {
                EntityField currentField = (EntityField) fieldList.get(ipos-1);
                fieldList.remove(ipos-1);
                fieldList.add(ipos-2, currentField);
            }
        }
        
        return mapping.findForward("index");
    }

    public ActionForward addIndexField(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request);
        return addIndexField(mapping, form, request, response, session, entityDef, getEntityId(request).toString());
    }
    
    public ActionForward showIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request);
        int indexId = Integer.parseInt(request.getParameter("indexId"));
        
        return showIndex(mapping, form, request, response, session,
                new Integer(indexId), entityDef,getEntityId(request).toString());
        
    }

    public ActionForward field(ActionMapping mapping, 
    						   ActionForm form,
    						   HttpServletRequest request,
    						   HttpServletResponse response,
    						   SessionAPI session) throws ISPACException {
        
        configureTitles(request, session);
        request.setAttribute("activateDocumentarySearch","false");	
        return prepareAndRedirectToFieldPage(mapping, form, request, response, session, null,null,null);
    }
    
    public ActionForward showFieldEntity(ActionMapping mapping, 
			   							 ActionForm form,
			   							 HttpServletRequest request,
			   							 HttpServletResponse response,
			   							 SessionAPI session) throws ISPACException {

    	configureTitles(request, session);
    	
    	// Identificador del campo seleccionado
    	int idField =Integer.parseInt(request.getParameter("idField"));
    	
    	// Obtener la definición de la entidad
    	EntityDef entityDef = getEntityDef(request);
    	
    	// Obtener la definición del campo
    	EntityField entityField = entityDef.findField(idField);

    	if (entityField.getDocumentarySearch()) {
    		request.setAttribute("activateDocumentarySearch","true");	
    	}
    	else {
    		request.setAttribute("activateDocumentarySearch","false");	
    	}
    					 		
    	return prepareAndRedirectToFieldPage(mapping, form, request, response, session, null, entityField, getEntityId(request).toString());
    }
    
    public ActionForward fields(ActionMapping mapping, 
    							ActionForm form,
    							HttpServletRequest request,
    							HttpServletResponse response,
    							SessionAPI session) throws ISPACException {

    	ClientContext cct = session.getClientContext();
    	
    	int tipoEntidad = getTipo(request);
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
    	checkFunctions(request, cct, tipoEntidad);

        String title = "";
        String subtitle = "";
        EntityDef entityDef = getEntityDef(request);

        request.setAttribute(WIZARD_TITLE_KEY, title);
        request.setAttribute(WIZARD_SUBTITLE_KEY, subtitle);
        
        configureLabels(getLabelsFromSession(request), FIELDS_LABEL_KEY);
        configureTitles(request, session);

        
        
        if (EntityType.isValidationTableType(tipoEntidad) 
        		|| !entityDef.isEditable()
        		|| !FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT)) {
            
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.FALSE);
        }
        else {
            request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);
        }
        
        return prepareAndRedirectToFieldsPage(mapping, form, request, response, session, entityDef, String.valueOf(getEntityId(request)),getTipo(request));
    }
    
    public ActionForward resources(ActionMapping mapping, 
    							   ActionForm form,
    							   HttpServletRequest request,
    							   HttpServletResponse response,
    							   SessionAPI session) throws ISPACException {
       
    	ClientContext cct = session.getClientContext();
       
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });
    	
    	//APIS, formulario, conexión y definición de la entidad
    	IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entityAPI = invesFlowAPI.getEntitiesAPI();
        ICatalogAPI icatalogAPI = invesFlowAPI.getCatalogAPI();

        EntityDef entityDef = getEntityDef(request);

        EntityForm entityForm = (EntityForm) form;
        
        //Declaracion de lista auxiliar para los campos de la entidad    
    	List listFields = new LinkedList();
  	   	
    	//Listas que contendran los campos de la entidad y los campos de tipo otros, se guardaran en request. Se guardaran objetos para la vista.
    	List listEntityRequest = new LinkedList();
    	List listFieldsRequest = new LinkedList();   
    	List listOthersFieldsRequest = new LinkedList();
    	
    	//Definicion de properties el primero para los campos y entidad, el segundo para los lenguajes
    	Properties propertiesResourceView = getPropertiesResourceView();
              
    	// Idiomas soportados
    	request.setAttribute("languages", 
    						ConfigurationMgr.getLanguages(cct, getResources(request)));
    	
    	String languageSelected ="";
    	// Idioma seleccionado
    	//Esto quiere decir que no viene de la pagina de añadir un nuevo recurso de tipo "otro".
    	if (request.getParameter("language")==null) { 
    		languageSelected = entityForm.getProperty("languageSelected");
    		if (StringUtils.isEmpty(languageSelected)) { 		
    			languageSelected = cct.getAppLanguage();
    			entityForm.setProperty("languageSelected", languageSelected);
    		}
    	} else {
    		//Esto quiere decir que viene de la pagina de añadir un nuevo recurso de tipo "otro" y se guarda su lenguage.
    		languageSelected = (String) request.getParameter("language"); 
    		entityForm.setProperty("languageSelected", languageSelected);
    	}

    	//****************PARA LA ENTIDAD*****************//
    	
    	//Sacamos Un IItem con la entidad.
    	int entityId = getEntityId(request).intValue();
    	String entityResourceClave = getEntityName(request);
    	
    	//Necesitamos su identifacador en la tabla spac_ct_entidades_resources, de paso cogemos su valor.
    	IItem iEntity = entityAPI.getEntityResource(entityId, entityResourceClave, languageSelected); 

    	IItemCollection collectionEntity=null;
    	//Si la entidad ya existia en ese lenguaje asociamos al IItem, sino creamos una nueva entrada para la entidad en el nuevo lenguaje.
    	if (iEntity == null) {

    		iEntity = icatalogAPI.createCTEntityResource();
    		iEntity.set("ID_ENT", entityId);
    		iEntity.set("IDIOMA", languageSelected);
    		iEntity.set("CLAVE", entityResourceClave);
    		iEntity.set("VALOR", ""); 
    		iEntity.store(cct);
    	}

    	//Creamos el objeto de la vista y lo guardamos en una lista que se guardara en request.
    	IItem iViewEntity = new GenericItem(propertiesResourceView, "ID_RECURSO");
    	iViewEntity.set("CLAVE", entityResourceClave );
    	iViewEntity.set("VALOR", iEntity.getString("VALOR"));
    	iViewEntity.set("ID_RECURSO", iEntity.getString("ID"));
		
    	listEntityRequest.add(iViewEntity);
    	collectionEntity = new ListCollection(listEntityRequest);    		
    	request.setAttribute("entity", CollectionBean.getBeanList(collectionEntity));
    	
    	//****************PARA LOS CAMPOS DE LA ENTIDAD*****************//
    	
        //Sacamos un IItemCollection con todos los valores de los campos de la entidad.
    	listFields = entityDef.getFields();
    	String sListFields = "";
    	ListIterator itrListFields = listFields.listIterator();
    	
    	//formamos en un string todos los campos separados por comas, lo utilizaremos en la consulta
    	if (itrListFields.hasNext()) {
    		EntityField e = (EntityField) itrListFields.next();
    		sListFields+="'"+e.getPhysicalName()+"'";  		
    	}
    	while (itrListFields.hasNext()) {
    		EntityField e = (EntityField) itrListFields.next();
    		sListFields+=",'"+e.getPhysicalName()+"'";   		
    	}
    	
    	//Obtendremos una colección con los valores que tenían en la tabla SPAC_CT_ENTIDADES_RESOURCES los campos anteriores según el lenguaje.
    	IItemCollection itemColListFields =  entityAPI.getEntityResources(entityId, sListFields, languageSelected);   	
    	
    	//Pasamos a hashmap, poniendo como key la CLAVE
    	Map mapColListFields = itemColListFields.toMap("CLAVE");
     
    	//Creamos un iterador sobre todos los campos de tal manera que si en un idioma no existe un valor para ese campo se pone vacio
        ListIterator itrListFieldsEntity = listFields.listIterator();
    	
    	String clave;
    	while (itrListFieldsEntity.hasNext()) {
    		
    		//Creamos el objeto de la vista
    		IItem iViewField = new GenericItem(propertiesResourceView, "ID_RECURSO");
    		EntityField entityField = (EntityField) itrListFieldsEntity.next();
    		clave=entityField.getPhysicalName(); 
    		iViewField.set("CLAVE", clave.toUpperCase());
    		IItem iField = (IItem) mapColListFields.get(clave.toUpperCase());
    		if (!clave.equalsIgnoreCase("numexp") && !clave.equalsIgnoreCase("id") ) {
    			if (iField != null) {
    				if (iField.get("VALOR")!=null){
    					iViewField.set("VALOR",(String)iField.get("VALOR")); 			
    				}
    			}else {
    				iViewField.set("VALOR",""); 
    			}
    			if (iField != null) {
    				if (iField.getKey()!=null){
    					iViewField.set("ID_RECURSO", new Integer(iField.getKeyInt()).toString()); 			
    				} 
    			}else {
    				iViewField.set("ID_RECURSO", "nuevo"); 	
    			}
    			listFieldsRequest.add(iViewField);  			
    		}
 
    		if (clave.equalsIgnoreCase("numexp") && entityResourceClave.equalsIgnoreCase("SPAC_EXPEDIENTES")) {
    			if (iField != null) {
    				if (iField.get("VALOR")!=null){
    					iViewField.set("VALOR",(String)iField.get("VALOR")); 			
    				}
    			}else {
    				iViewField.set("VALOR",""); 
    			}
    			if (iField != null) {
    				if (iField.getKey()!=null){
    					iViewField.set("ID_RECURSO", new Integer(iField.getKeyInt()).toString()); 			
    				} 
    			}else {
    				iViewField.set("ID_RECURSO", "nuevo"); 	
    			}
    			listFieldsRequest.add(iViewField);  			
    		}
    		
    	}  
    	//Guardamos en request una lista con todos los objetos de la vista.
    	IItemCollection collection = new ListCollection(listFieldsRequest);  	
    	request.setAttribute("listFields", CollectionBean.getBeanList(collection));
    	
    	//****************PARA LOS CAMPOS DE LA ENTIDAD DE TIPO OTROS*****************// 
    	
    	//Concatenamos tb el nombre de la entidad para que no salga tambien como otros campos.
    	sListFields+=",'"+iEntity.getString("CLAVE")+"'";
    	//Sacamos una coleccion de campos del tipo otros, pero en cualquier lenguaje, de esta manera salen todos.
    	IItemCollection itemColOthersListFieldsWithOutLanguage =  entityAPI.getEntityOtherResourceKeys(entityId, sListFields);	

    	//Sacamos una coleccion de campos del tipo otros pero en el lenguaje seleccionado, de esta manera salen solo los que tiene en ese lenguaje.
    	IItemCollection itemColOthersListFieldsByLanguage =  entityAPI.getEntityOtherResources(entityId, sListFields, languageSelected);   	
    	//Creamos un map donde la key será la "CLAVE"
    	Map mapItemOthersListFieldsByLanguage = itemColOthersListFieldsByLanguage.toMap("CLAVE");
    	
    	//Iteraremos la colección de todos los campos independientemente de su lenguaje, si un campo no existe en un lenguaje en concreto se pondrá vacio.
    	Iterator itrOthersFields = itemColOthersListFieldsWithOutLanguage.iterator();
    	
    	while(itrOthersFields.hasNext()) {
	
    		//Creamos el objeto de la vista
    		IItem iViewField = new GenericItem(propertiesResourceView, "ID_RECURSO");
    		IItem iFieldWithOutLanguage = (IItem) itrOthersFields.next();
    		clave = iFieldWithOutLanguage.getString("CLAVE"); 
    		iViewField.set("CLAVE", clave.toUpperCase());
    		
    		IItem iFieldByLanguage = (IItem) mapItemOthersListFieldsByLanguage.get(clave.toUpperCase());
    		if (iFieldByLanguage != null) {
    			iViewField.set("VALOR", iFieldByLanguage.getString("VALOR"));
    		} else {
    			iViewField.set("VALOR", "");
    		}
    		listOthersFieldsRequest.add(iViewField);
    	}
    	
    	IItemCollection collectionOthersFields = new ListCollection(listOthersFieldsRequest);  	
    	request.setAttribute("listOthersFields", CollectionBean.getBeanList(collectionOthersFields));
    	
    	String title = "";
        String subtitle = "";

        request.setAttribute(WIZARD_TITLE_KEY, title);
        request.setAttribute(WIZARD_SUBTITLE_KEY, subtitle);
        
        configureLabels(getLabelsFromSession(request), RESOURCES_LABEL_KEY);
        configureTitles(request, session);

		if (EntityType.isValidationTableType(getTipo(request))
				|| !entityDef.isEditable()
				|| !FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT)) {
            
        	request.setAttribute(FIELDS_MODIFICABLES, Boolean.FALSE);
        }
        else {
            request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);
        }
        
        return prepareAndRedirectToResourcesPage(mapping, form, request, response, session, entityDef, String.valueOf(getEntityId(request)),getTipo(request));
    }
    
    public ActionForward saveResources(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

    	//APIS, formulario, conexión y definición de la entidad
    	IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entityAPI = invesFlowAPI.getEntitiesAPI();
        ICatalogAPI icatalogAPI = invesFlowAPI.getCatalogAPI();
    	EntityForm entityForm = (EntityForm) form;
    	EntityDef entityDef = getEntityDef(request);
              
        //Lenguaje seleccionado en el formulario e identificador de la entidad.
        String language = entityForm.getProperty("languageSelected");       
        int entityId = getEntityId(request).intValue();
                
    	//****************PARA LA ENTIDAD*****************//
        
        String entityResourceClave = getEntityName(request);
    	//Necesitamos su identifacador en la tabla spac_ct_entidades_resources, de paso cogemos su valor
        IItem iEntity = entityAPI.getEntityResource(entityId , entityResourceClave, language); 

        //Si la entrada en la tabla para la entidad ya existia mofidicamos su valor y sino creamos una nueva entrada para la entidad en el lenguaje dado.
       	if (iEntity !=null) {
       		if (!iEntity.getString("VALOR").equals(entityForm.getProperty(iEntity.getString("CLAVE")))) {
       			iEntity.set("VALOR", entityForm.getProperty(iEntity.getString("CLAVE")));
       			iEntity.store(session.getClientContext());
       		}
       	}	
       	else {
       		IItem iEntityNew = icatalogAPI.createCTEntityResource();
       		iEntityNew.set("ID_ENT", entityId);
       		iEntityNew.set("IDIOMA", language);
       		iEntityNew.set("CLAVE",  entityResourceClave); 
       	 	iEntityNew.set("VALOR", entityForm.getProperty(entityResourceClave)); 
       		iEntityNew.store(session.getClientContext());
       	}
    	
    	//****************PARA LOS CAMPOS DE LA ENTIDAD*****************//
       	
        //Sacamos un IItemCollection con todos los campos de la entidad
    	List listFields = entityDef.getFields();
    	String sListFields = "";
    	ListIterator itrListFields = listFields.listIterator();
    	
    	if (itrListFields.hasNext()) {
    		EntityField e = (EntityField) itrListFields.next();
    		sListFields+="'"+e.getPhysicalName()+"'";  		
    	}
    	while (itrListFields.hasNext()) {
    		EntityField e = (EntityField) itrListFields.next();
    		sListFields+=",'"+e.getPhysicalName()+"'";   		
    	}
    	
    	//Obtendremos una colección con los valores que tenían en la tabla SPAC_CT_ENTIDADES_RESOURCES los campos anteriores según el lenguaje.
    	IItemCollection itemColListFields =  entityAPI.getEntityResources(entityId, sListFields, language);   	
    	//Pasamos a hashmap poniendo como key la "CLAVE"
    	Map mapColListFields = itemColListFields.toMap("CLAVE");

    	//Iteramos la lista de todos los campos de la entidad si se mofidico su valor se guarda y si no existia una entrada en la tabla para ese 
    	//campo en ese lenguaje se crea una nueva.
        ListIterator itrListFieldsEntity = listFields.listIterator();    
    	
        String campo;
    	while (itrListFieldsEntity.hasNext()) {
    		
    		EntityField entityField = (EntityField) itrListFieldsEntity.next();
    		campo=entityField.getPhysicalName();  
    		IItem iField = (IItem) mapColListFields.get(campo.toUpperCase());
    		if (iField!=null) {
	    		if (!StringUtils.equalsNullEmpty(iField.getString("VALOR"),entityForm.getProperty(campo.toUpperCase()))) {  	
	    			iField.set("VALOR", entityForm.getProperty(campo.toUpperCase()));
	    			iField.store(session.getClientContext());
	    		}
    		} else if (!campo.equalsIgnoreCase("numexp") && !campo.equalsIgnoreCase("id")){   			
    			IItem iFieldNew = icatalogAPI.createCTEntityResource();
    			iFieldNew.set("ID_ENT", entityId);
    			iFieldNew.set("IDIOMA", language);
    			iFieldNew.set("CLAVE", campo.toUpperCase());
    			iFieldNew.set("VALOR", entityForm.getProperty(campo.toUpperCase()));
    			iFieldNew.store(session.getClientContext());
    		} else if (campo.equalsIgnoreCase("numexp") && entityResourceClave.equalsIgnoreCase("SPAC_EXPEDIENTES")) {
    			IItem iFieldNew = icatalogAPI.createCTEntityResource();
    			iFieldNew.set("ID_ENT", entityId);
    			iFieldNew.set("IDIOMA", language);
    			iFieldNew.set("CLAVE", campo.toUpperCase());
    			iFieldNew.set("VALOR", entityForm.getProperty(campo.toUpperCase()));
    			iFieldNew.store(session.getClientContext());			
    		}
    	}
	
    	//****************PARA LOS CAMPOS DE TIPO OTROS DE LA ENTIDAD*****************//
    	
    	//Concatenamos tb el nombre de la entidad para que no salga tambien como otros campos.
    	sListFields+=",'"+iEntity.getString("CLAVE")+"'";
    	
    	//Sacamos una coleccion de campos del tipo otros pero en cualquier lenguaje, de esta manera salen todos.
    	IItemCollection itemColOthersListFieldsWithOutLanguage =  entityAPI.getEntityOtherResourceKeys(entityId, sListFields);   	

    	//Sacamos una coleccion de campos del tipo otros pero en el lenguaje seleccionado, de esta manera salen solo los que tiene en ese lenguaje.
    	IItemCollection itemColOthersListFieldsByLanguage = entityAPI.getEntityOtherResources(entityId, sListFields, language);   	
    	//Lo pasamos a map poniendo como key la "CLAVE"
    	Map mapItemOthersListFieldsByLanguage = itemColOthersListFieldsByLanguage.toMap("CLAVE");
    	
    	//Iteramos la lista de todos los campos independientemente del lenguaje y si se modifico para ese lenguaje lo guardamos
    	//Si no habia una entrada para ese campo en ese lenguaje creamos una entrada nueva y le ponemos ese valor.
    	Iterator itrOthersFields = itemColOthersListFieldsWithOutLanguage.iterator();
    	
    	while(itrOthersFields.hasNext()) {
    		
    		IItem iFieldWithOutLanguage = (IItem) itrOthersFields.next();
    		String clave=iFieldWithOutLanguage.getString("CLAVE"); 
    		if (mapItemOthersListFieldsByLanguage.get(clave.toUpperCase())!=null) {

    			IItem iFieldByLanguage = entityAPI.getEntityResource(entityId, clave, language);
        		iFieldByLanguage.set("VALOR", entityForm.getProperty(clave.toUpperCase()));  
        		iFieldByLanguage.store(session.getClientContext());
    		}
    		else {
    			IItem iFieldNew = icatalogAPI.createCTEntityResource();
    			iFieldNew.set("ID_ENT", entityId);
    			iFieldNew.set("IDIOMA", language);
    			iFieldNew.set("CLAVE", clave.toUpperCase());
    			iFieldNew.set("VALOR", entityForm.getProperty(clave.toUpperCase()));
    			iFieldNew.store(session.getClientContext());
    		}
    	}

    	request.setAttribute("saveResourceLanguage", entityForm.getProperty("languageSelected"));

    	return mapping.findForward("resources");
    }
    
    private Properties getPropertiesResourceView() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "ID_RECURSO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CLAVE", Types.VARCHAR));
        properties.add( new Property(ordinal++, "VALOR", Types.VARCHAR));

        return properties;
    }    
       
    public ActionForward newOtherResource(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
    	
    	EntityForm entityForm = (EntityForm) form;
    	
    	String language = entityForm.getProperty("languageSelected");
    	request.setAttribute("language", language);
    	
    	return mapping.findForward("resource_page");
    }   
    
    public ActionForward addOtherResource(ActionMapping mapping, 
    									  ActionForm form,
    									  HttpServletRequest request,
    									  HttpServletResponse response,
    									  SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	
    	//APIS, formulario
    	IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        ICatalogAPI icatalogAPI = invesFlowAPI.getCatalogAPI();
    	EntityForm entityForm = (EntityForm) form;
    	//EntityDef entityDef = getEntityDef(request);
    	
    	String key = entityForm.getProperty("CLAVE").trim().toUpperCase();
    	String text = entityForm.getProperty("VALOR");    	
    	int entityId = getEntityId(request).intValue();
    	
    	String language = request.getParameter("language");
    	request.setAttribute("language", language);
    	
        // Validación
		ActionMessages errors = new ActionMessages();	
		entityForm.setEntityAppName("EditCTEntityResource");
		errors = entityForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
		
			/*
	        //Sacamos un IItemCollection con todos los campos de la entidad
	    	List listFields = entityDef.getFields();
	    	String sListFields = "";
	    	ListIterator itrListFields = listFields.listIterator();
	    	
	    	if (itrListFields.hasNext()) {
	    		EntityField e = (EntityField) itrListFields.next();
	    		sListFields+="'"+e.getPhysicalName()+"'";  		
	    	}
	    	while (itrListFields.hasNext()) {
	    		EntityField e = (EntityField) itrListFields.next();
	    		sListFields+=",'"+e.getPhysicalName()+"'";   		
	    	}
	    	
	    	//Sacamos una coleccion de campos del tipo otros pero en cualquier lenguaje, de esta manera salen todos.
	    	IItemCollection itemColOthersListFieldsWithOutLanguage = entitiesAPI.getEntityOtherResourceKeys(entityId, sListFields);     	
	    	//Creamos un map donde la key será la "CLAVE"
	    	Map mapItemColOthersListFieldsWithOutLanguage = itemColOthersListFieldsWithOutLanguage.toMap("CLAVE");
	    	
	    	if (mapItemColOthersListFieldsWithOutLanguage.get(clave)==null) {
	    	*/
			
			if (!entitiesAPI.isEntityResourceKey(entityId, key)) {
	    		
		    	// Los recursos se crean para todos los idiomas
		    	String[] languages = ConfigurationMgr.getLanguages(cct);
				
				for (int i = 0; i < languages.length; i++) {
				
					IItem iFieldNew = icatalogAPI.createCTEntityResource();
					iFieldNew.set("ID_ENT", entityId);
					iFieldNew.set("IDIOMA", languages[i]);
					iFieldNew.set("CLAVE", key);
					iFieldNew.set("VALOR", text);
					iFieldNew.store(cct);
				}
				
				request.setAttribute("target", "top");
				request.setAttribute("url", "?method=resources&language="+language);
				
				return mapping.findForward("loadOnTarget");
	    	}
	    	else {
	    		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("entityManage.error.resource.duplicated", new String[] {key}));				
	    	}
		}
		
		saveErrors(request, errors);
		
		return mapping.findForward("resource_page");
    }    
    
    public ActionForward deleteOthersResources(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

    	//APIS, formulario, conexión y definición de la entidad
    	IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI icatalogAPI = invesFlowAPI.getCatalogAPI();
        
    	EntityForm entityForm = (EntityForm) form;
    	String [] resources = entityForm.getMultibox();
    	int entityId = getEntityId(request).intValue();
    	
    	
    	for ( int i=0; i<resources.length; i++) {
    		icatalogAPI.deleteResources(entityId, resources[i]);
    	}
    	
    	return mapping.findForward("resources");
    }
    
    
    public ActionForward addField(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        EntityDef entityDef = getEntityDef(request);
        return addField(mapping, form, request, response, session,
                getEntityId(request), entityDef, true);
    }
    /**
     * Guarda los cambios que se realizan sobre un campo en una entidad
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param session
     * @return
     * @throws ISPACException
     */
    public ActionForward saveField(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        EntityDef entityDef = getEntityDef(request);
        return saveField(mapping, form, request, response, session,
                getEntityId(request), entityDef, true);
    }
   
    public ActionForward deleteItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request);
        return deleteItems(mapping, form, request, response, session,
                getEntityId(request), entityDef, true);
    }
    
    public ActionForward deleteEntity(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
    	
    	boolean anyEntityInUse = false;

    	EntityForm frm = (EntityForm)form;
    	IInvesflowAPI invesFlowAPI = session.getAPI();
    	String tipoTblVld = request.getParameter("tblVld");
    	
		String[] entitiesIds = frm.getMultibox();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
		boolean deleteTableBD = false;
		if (request.getParameter("deleteTableBD") != null)
			deleteTableBD = true;
		
		// Contexto del cliente
		IClientContext cct = session.getClientContext();
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
    	
    	try {
			// Abrir transacción
    		cct.beginTX();

			for (int i = 0; i < entitiesIds.length; i++) {
				
				if (tipoTblVld == null) {
					
					// Una entidad no se puede eliminar si está siendo usada en algún procedimiento
					boolean entityInUse = procedureAPI.isInUse(ICatalogAPI.ENTITY_CT_ENTITY, Integer.parseInt(entitiesIds[i]));
					if(!entityInUse) {
						catalogAPI.deleteEntity(Integer.parseInt(entitiesIds[i]), deleteTableBD);
					}
					else if (!anyEntityInUse) {
						anyEntityInUse = true;
					}
				}
				else {
					// TODO Restricciones al eliminar una tabla de validación
					catalogAPI.deleteEntity(Integer.parseInt(entitiesIds[i]), deleteTableBD);
				}
			}

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
    	}
    	catch (Exception e) {
    		
    		// Retorno a la lista de entidades
        	String forward = null;
            if (tipoTblVld != null) {
            	forward = "tblvld_list_withoutredirect";
            }
            else {
            	forward = "entities_list_withoutredirect";
            }
            
    		saveAppErrors(request, getActionMessages(request, e));
    		
    		return mapping.findForward(forward);
    	}
		finally {
			cct.endTX(bCommit);
		}
    	
		if(anyEntityInUse) {
			throw new ISPACInfo(getResources(request).getMessage("error.entity.inUse"));
		}
		
		// Retorno a la lista de entidades
    	String forward = null;
    	if (tipoTblVld != null) {
        	forward = "tblvld_list";
        }
    	else {
        	forward = "entities_list";
        }
    	
		return mapping.findForward(forward);
    }
    
    public ActionForward listavalores(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
    	String title = "";
        String subtitle = "";
        String fmtName = "";
   	 	
        EntityDef entityDef = getEntityDef(request);
        
		boolean editable = entityDef.isEditable()
				&& FunctionHelper.userHasFunction(request,
						session.getClientContext(),
						ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT);
        if(editable) {
        	
        	request.setAttribute(EDITABLE, Boolean.TRUE);
        	
        	// Formateadores
       	 	if (EntityType.getInstance(getTipo(request))==EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE) {
       	 		fmtName ="/formatters/entities/simpletblvldvalueslistformatter.xml";
       	 	}
       	 	else if (EntityType.getInstance(getTipo(request))==EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) {
       	 		fmtName ="/formatters/entities/tblvldvalueslistformatter.xml";
       	 	}
        }
        else {
        	// Formateadores para sólo lectura
       	 	if (EntityType.getInstance(getTipo(request))==EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE) {
       	 		fmtName ="/formatters/entities/simpletblvldvalueslistreadonlyformatter.xml";
       	 	}
       	 	else if (EntityType.getInstance(getTipo(request))==EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) {
       	 		fmtName ="/formatters/entities/tblvldvalueslistreadonlyformatter.xml";
       	 	}
        }

        request.setAttribute(WIZARD_TITLE_KEY, title);
        request.setAttribute(WIZARD_SUBTITLE_KEY, subtitle);
        configureLabels(getLabelsFromSession(request), LISTA_VALORES_LABEL_KEY);
        configureTitles(request, session);

        //obtener lista de valores
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

//		StringBuffer condicion = new StringBuffer()
//			.append("WHERE TIPO = ")
//			.append(EntityType.PROCESS_ENTITY_TYPE.getId());
//		
//		String criterio = request.getParameter("property(criterio)");
//		if (StringUtils.isNotBlank(criterio)) {
//			condicion.append(" AND NOMBRE LIKE '%")
//				.append(DBUtil.replaceQuotes(criterio))
//				.append("%'");
//		}

		IItemCollection itemcol = entitiesAPI.queryEntities(
        		getEntityId(request).intValue(), " ORDER BY  " + ICatalogAPI.ORDEN_FIELD_NAME);
        List valuesList = CollectionBean.getBeanList(itemcol);
        if(valuesList.size()>0 &&((ItemBean)valuesList.get(0)).getProperty("SUSTITUTO")!=null){
        	request.setAttribute("haySustituto", "true");
        }
        
        for (Iterator iter = valuesList.iterator(); iter.hasNext();) {
			ItemBean element = (ItemBean) iter.next();
			element.setProperty("ENTITY_ID", getEntityBeanFromSession(request).getItem().getKey());
			element.setProperty("ENTITY_TYPE", new Integer(getTipo(request)));
		}

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
   	 	
   	 	BeanFormatter formatter = factory.getFormatter(
				getISPACPath(fmtName));

		request.setAttribute("TblVldValuesListFormatter", formatter);
   	 	request.setAttribute("TblVldValuesList", valuesList);
   	 	request.setAttribute("Action", "/showCTEntityToManage.do?method=listavalores");
   	 	request.setAttribute("ActionAdd", "/ManageTblVld.do?method=show&entityId="
				+ String.valueOf(getEntityId(request)) + "&regId=-1&type="+getEntityBeanFromSession(request).getString("SPAC_CT_ENTIDADES:TIPO"));	
   	 	request.setAttribute("ActionDelete", "/ManageTblVld.do?method=delete&entityId="
				+ String.valueOf(getEntityId(request)) + " ");
   	 	request.setAttribute("ActionSubir", "/ManageTblVld.do?method=subir&entityId="
				+ String.valueOf(getEntityId(request)) + " ");
   	 request.setAttribute("ActionOrdenar", "/ManageTblVld.do?method=ordenar&entityId="
				+ String.valueOf(getEntityId(request)) + " ");
   	 	request.setAttribute("ActionBajar", "/ManageTblVld.do?method=bajar&entityId="
			+ String.valueOf(getEntityId(request)) + " ");
   	 	return mapping.findForward("listado_valores");
    }
    
    public ActionForward forms(ActionMapping mapping,
    						   ActionForm form,
    						   HttpServletRequest request,
    						   HttpServletResponse response,
    						   SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

        EntityDef entityDef = getEntityDef(request);
        configureLabels(getLabelsFromSession(request), FORMS_LABEL_KEY);
        configureTitles(request, session);
                
        return prepareAndRedirectoToFormsPage(entityDef, request, mapping, session);
    }
    
    public ActionForward prepareAndRedirectoToFormsPage(EntityDef entityDef,
														HttpServletRequest request, 
														ActionMapping mapping, 
														SessionAPI session) throws ISPACException {

    	int entityId = getEntityId(request).intValue();

    	StringBuffer condicion = new StringBuffer()
    							 .append(" where ENT_PRINCIPAL_ID = ")
    							 .append(entityId);
    	
    	ICatalogAPI catalogApi = session.getAPI().getCatalogAPI();
    	IItemCollection itemcol = catalogApi.queryCTEntities(ICatalogAPI.ENTITY_CT_APP, condicion.toString());

    	List formList = CollectionBean.getBeanList(itemcol);
    	
    	// Obtengo el formulario por defecto
    	IEntitiesAPI entities = session.getAPI().getEntitiesAPI();
    	Object formId = entities.getFormDefault(entityId);
    	// Miro cual coincide con el de por defecto y le cambio la propiedad NOMBRE (solamente en la vista)
    	for (Iterator iter = formList.iterator(); iter.hasNext();) {
 			ItemBean element = (ItemBean) iter.next();
 			if (element.getProperty("ID").equals(formId)){
 				element.setProperty("NOMBRE", element.getProperty("NOMBRE") + " " + getResources(request).getMessage("form.entity.forms.defaultForm"));
 				break;
 			}
 		}
    	
    	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
    	BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/entities/formlistformatter.xml"));
    	request.setAttribute("FormListFormatter", formatter);
    	request.setAttribute("FormList", formList);
    	request.setAttribute("label", getEntityLabel(request));

    	request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());

    	return mapping.findForward("forms_page");
    }
    
    public ActionForward form(ActionMapping mapping, 
    						  ActionForm form,
    						  HttpServletRequest request,
    						  HttpServletResponse response,
    						  SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
        EntityForm entityForm = (EntityForm) form;
    	
        // Detalle de un formulario
 		String id = request.getParameter("regId");
 		if (!StringUtils.isEmpty(id)) {
 			
 			IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, Integer.parseInt(id));
 			entityForm.setItemBean(new ItemBean(item));
 		}
 		else {
 			// Generar los parámetros para las validaciones de sustituto de la entidad
 			entityForm.setProperty("PARAMETROS", getEntityDef(request).generateXmlParameters());
 		}
    	
        String nextAction = request.getServletPath();
        List buttons = new ArrayList();
        
        boolean editionMode = FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_ENTITIES_EDIT);
       
        if (StringUtils.isNotBlank(entityForm.getProperty("ID"))) {
        	
        	entityForm.setKey(entityForm.getProperty("ID"));
        	entityForm.setEntity(String.valueOf(ICatalogAPI.ENTITY_CT_APP));
        	
        	if (editionMode) {
        		buttons.add(new WizardButton("forms.button.save", composeJScriptIspacSubmitURL(request, nextAction + "?method=saveForm")));
        	}
        	
            buttons.add(new WizardButton("forms.button.close", composeJScriptIspacSubmitURL(request, nextAction + "?method=forms")));
        }
        else {
        	
        	if (editionMode) {
        		buttons.add(new WizardButton("forms.button.accept", composeJScriptIspacSubmitURL(request, nextAction + "?method=addForm")));
        	}
        	
            buttons.add(new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL(request, nextAction + "?method=forms")));
        }
        
        
        if (StringUtils.isNotBlank(entityForm.getProperty("ID"))) {
            
        	if (editionMode) {
	        	if (StringUtils.isNotBlank(entityForm.getProperty("FRM_VERSION"))) {
	        	    String bean=getMessage(request, session.getClientContext().getLocale(), "form.entity.forms.confirm.regenerateForm");
	        	    String aviso=getMessage(request, session.getClientContext().getLocale(), "common.confirm");
	        	    String accept=getMessage(request, session.getClientContext().getLocale(), "common.message.ok");
	        	    String cancel=getMessage(request, session.getClientContext().getLocale(), "common.message.cancel");
		            buttons.add(new WizardButton("entityManage.label.forms.remakeForm", 
		            		 "javascript: confirm_remake(\"remakeForm\", \""+bean+"\",\""+request.getContextPath()
		                     + nextAction+"\", 330 , 230, \""+aviso+"\", \""+accept+"\",\""+cancel+"\")"));
	        	}
	            
	            buttons.add(new WizardButton("entityManage.label.forms.defaultForm", 
	                    "javascript: showFrame(\"workframemsg\", \"" + request.getContextPath()
	                        + nextAction + "?method=defaultForm\", 330, 230)"));
        	}
            
            buttons.add(new WizardButton("forms.button.use", composeJScriptIspacSubmitURL(request, "/showComponentsUseList.do"))); 

            if (editionMode) {
	            buttons.add(new WizardButton("forms.button.delete", composeJScriptIspacSubmitURL(request, nextAction + "?method=deleteForm")));
	            
	            buttons.add(new WizardButton("entityManage.label.forms.addEntity", 
	                    "javascript: showFrame(\"workframe\", \"" + request.getContextPath()
	            			+ "/selectObject.do?codetable=SPAC_CT_ENTIDADES&codefield=ID&valuefield=NOMBRE&caption=select.entity.caption&decorator=/formatters/entities/addentityformformatter.xml"
	            			+ "&regId=" + entityForm.getProperty("ID") +"&sqlquery=WHERE TIPO=1 AND ID<>"
	            			+ getEntityId(request).toString() + "\", 640, 480)"));
            }
        }

        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entityManage.title.form");
        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());
        configureTitles(request, session);

        return mapping.findForward("form_page");
    }
    
    private void validateDataForm(EntityForm entityForm,
    							  ActionMessages errors,
    							  ICatalogAPI catalogAPI,
    							  int entityId) throws ISPACException {
    	
		// Nombre del formulario único en la entidad
		String name = entityForm.getProperty("NOMBRE").trim();
		if (!StringUtils.isEmpty(name)) {
			
			String query = "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "'";
			//String query = "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name.trim()) + "' AND ENT_PRINCIPAL_ID = " + entityId;
			IItemCollection collection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_APP, query);
			if (collection.next()) {
				
				IItem item = collection.value();
				if (!item.getString("ID").equals(entityForm.getProperty("ID"))) {
				
					errors.add("property(NOMBRE)", new ActionMessage("error.form.nameDuplicated", new String[] {name}));
					//errors.add("property(NOMBRE)", new ActionMessage("entityManage.error.nameForm"), );
				}
			}
		}
		
		// Validar el XML de los parámetros
		String parameters = entityForm.getProperty("PARAMETROS");
		if (!StringUtils.isEmpty(parameters)) {
			
			ParametersDef xmlParameters = ParametersDef.parseParametersDef(parameters);
			if (xmlParameters == null) {
				
				errors.add("property(PARAMETROS)", new ActionMessage("entityManage.error.parametersForm"));
			}
			else {
				
				entityForm.setProperty("PARAMETROS", xmlParameters.toXml());
			}
		}
		
		// Validar el formateador
		String formatter = entityForm.getProperty("FORMATEADOR");
		if (!StringUtils.isEmpty(formatter)) {
			
			// La ruta empieza por /
			if (!formatter.startsWith("/")) {
				
				errors.add("property(FORMATEADOR)", new ActionMessage("entityManage.error.formatterForm.begin"));
			}
			// Extensión .jsp
			else if (!formatter.toLowerCase().endsWith(".xml")) {
				
				errors.add("property(FORMATEADOR)", new ActionMessage("entityManage.error.formatterForm.end"));
			}
		}
		
		// Validar la página
		String page = entityForm.getProperty("PAGINA");
		if (!StringUtils.isEmpty(page)) {
			
			// La ruta empieza por /
			if (!page.startsWith("/")) {
				
				errors.add("property(PAGINA)", new ActionMessage("entityManage.error.pageForm.begin"));
			}
			// Extensión .jsp
			else if (!page.toLowerCase().endsWith(".jsp")) {
				
				errors.add("property(PAGINA)", new ActionMessage("entityManage.error.pageForm.end"));
			}
		}
    }
    
    public ActionForward addForm(ActionMapping mapping,
    							 ActionForm form,
    							 HttpServletRequest request,
    							 HttpServletResponse response,
    							 SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	
    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
        EntityForm entityForm = (EntityForm) form;
        
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
        
		try {
	        // Abrir transacción
	        cct.beginTX();
	        
			// Bloqueo
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_APP, "");
        
	        // Validar el formulario
	        entityForm.setEntityAppName("AddCTApp");
	        ActionMessages errors = entityForm.validate(mapping, request);
	        validateDataForm(entityForm, errors, catalogAPI, getEntityId(request).intValue());
	        
	        if (errors.isEmpty()) {
	        	
	        	// Obtener la aplicación que gestiona el formulario
	    		String path = getServlet().getServletContext().getRealPath("");
	    		EntityApp entityapp = catalogAPI.newCTDefaultEntityApp(ICatalogAPI.ENTITY_CT_APP, path);
	    		entityForm.processEntityApp(entityapp);
	    		IItem application = entityapp.getItem();
	    		
	    		// Entidad principal
	    		application.set("ENT_PRINCIPAL_ID", getEntityId(request).intValue());
	        	application.set("ENT_PRINCIPAL_NOMBRE", getEntityName(request));
	        	
	        	// Generar el JSP del formulario
	    		List entities = new ArrayList();
	    		entities.add(application.getString("ENT_PRINCIPAL_NOMBRE"));
	    		List dataBlocks = new ArrayList();
	    		dataBlocks.add(getEntityBeanFromSession(request).getString("SPAC_CT_ENTIDADES:FRM_JSP"));
	    		boolean generateTabDocumentos=false;
	    		if(StringUtils.equalsIgnoreCase(CTApplicationDAO.DOCUMENTS_ACTIVATE,(String)application.get("DOCUMENTOS")))
	    		{
	    			generateTabDocumentos=true;
	    			dataBlocks.add(JSPBuilder.getBlockDocuments(dataBlocks.size()));
	    		}
	    		
	    		String jspCode = JSPBuilder.generateTabsDataBlocks(entities, dataBlocks, generateTabDocumentos);
	    		
	    		application.set("FRM_JSP", jspCode);
	    		
				// Establecer la versión del formulario
	    		application.set("FRM_VERSION", 1);
	    		
				// Guardar la entidad
				entityapp.store();
				
				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
	        	
	            //return mapping.findForward("forms");
				
				ActionForward forward = mapping.findForward("form");
				String url = new StringBuffer(forward.getPath())
					.append("&regId=").append(entityapp.getItem().getKeyInt())
					.toString();
				
				return new ActionForward(forward.getName(), url, true);
	        }
	        else {				
	            saveErrors(request, errors);
	            
	            return mapping.findForward("form");
	        }
		}
		finally {
			cct.endTX(bCommit);
		}
    }
    
    public ActionForward addEntityForm(ActionMapping mapping,
			 						   ActionForm form,
			 						   HttpServletRequest request,
			 						   HttpServletResponse response,
			 						   SessionAPI session) throws ISPACException {
    	
  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
    	ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
    	
		String sEntityId = request.getParameter("entityId");
		String sRegId = request.getParameter("regId");
		
		if ((StringUtils.isNotBlank(sEntityId)) && 
			(StringUtils.isNotBlank(sRegId))) {
			
			int entityId = Integer.parseInt(sEntityId);
			int regId = Integer.parseInt(sRegId);
						
 			IItem application = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, regId);
 			
 			ParametersDef xmlParameters = null;
 			String parameters = application.getString("PARAMETROS");
 			if (!StringUtils.isEmpty(parameters)) {
 			
 				xmlParameters = ParametersDef.parseParametersDef(parameters);
 			}
 			else {
 				xmlParameters = new ParametersDef();
 			}
 			
			// Entidad a añadir como composición
			IItem entity = entitiesAPI.getCatalogEntity(entityId);
			String entityName = entity.getString("NOMBRE");
 			
 			// Generar la composición
 			EntityParameterDef compositeDef = new EntityParameterDef(EntityAppConstants.ENTITY_TYPE_COMPOSITE, entityName, EntityAppConstants.RELATION_TYPE_NUMEXP);
 			xmlParameters.addEntity(compositeDef);
 			
 			// Agregar el XML de los parámetros de la entidad al XML de los parámetros del formulario
 			EntityDef entityDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
 			if (entityDef != null) {
 				
	 			Iterator it = entityDef.generateParametersDef().getEntities().iterator();
	 			while (it.hasNext()) {
	 				
	 				// Añadir el primaryTable a las entidades de sustituto
	 				// para diferenciar las validaciones de la entidad principal de la de composición
	 				// cuanda ambas entidades tienen un campo de sustituto validado contra la misma tabla de validación
	 				xmlParameters.addEntity((EntityParameterDef) it.next(), entityName); 
	 			}
 			}
	 			
 			// Actualizar el formulario
 			application.set("PARAMETROS", xmlParameters.toXml());
 			application.store(session.getClientContext());
		}
    	
    	return mapping.findForward("closeIFrame");
    }
    
    public ActionForward saveForm(ActionMapping mapping,
			 					  ActionForm form,
			 					  HttpServletRequest request,
			 					  HttpServletResponse response,
			 					  SessionAPI session) throws ISPACException {
    	
		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
		EntityForm entityForm = (EntityForm) form;
		
		ActionForward forward = mapping.findForward("form");
		String url = new StringBuffer(forward.getPath())
			.append("&regId=").append(entityForm.getKey())
			.toString();
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
	        
			// Bloqueo
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_APP, "");

			// Validar el formulario
			entityForm.setEntityAppName("EditCTApp");
			ActionMessages errors = entityForm.validate(mapping, request);
			validateDataForm(entityForm, errors, catalogAPI, getEntityId(request).intValue());
	
			if (errors.isEmpty()) {
				
				// Obtener la aplicación que gestiona el formulario
				String path = getServlet().getServletContext().getRealPath("");
				EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(ICatalogAPI.ENTITY_CT_APP, path);
				entityForm.processEntityApp(entityapp);
				
				// Guardar la entidad
				entityapp.store();
				
				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
				
				return new ActionForward(forward.getName(), url, true);
				//return mapping.findForward("forms");
			}
			else {
				saveErrors(request, errors);
				
				return new ActionForward(forward.getName(), url, false);
				//return mapping.findForward("form");
			}
		}
		finally {
			cct.endTX(bCommit);
		}
    }
    
    public ActionForward deleteForm(ActionMapping mapping,
			 						ActionForm form,
			 						HttpServletRequest request,
			 						HttpServletResponse response,
			 						SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

    	IInvesflowAPI invesFlowAPI = session.getAPI();
    	ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
    	
    	EntityForm entityForm = (EntityForm) form;
		int keyId = Integer.parseInt(entityForm.getKey());
    	
    	if (procedureAPI.isInUse(ICatalogAPI.ENTITY_CT_APP, keyId)) {
    		
    		entityForm.setEntity(String.valueOf(ICatalogAPI.ENTITY_CT_APP));
    		entityForm.setKey(String.valueOf(keyId));
    		
    		return mapping.findForward("use");	
    	}
    	
    	// Eliminar el formulario
		IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, keyId);
		item.delete(session.getClientContext());
    	
    	return mapping.findForward("forms");
    }
    
    public ActionForward remakeForm(ActionMapping mapping,
			 						ActionForm form,
			 						HttpServletRequest request,
			 						HttpServletResponse response,
			 						SessionAPI session) throws ISPACException {
    	
    	// Comprobar que la entidad tiene formulario
    	String frmJsp = getEntityBeanFromSession(request).getString("SPAC_CT_ENTIDADES:FRM_JSP");
    	if (StringUtils.isEmpty(frmJsp)) {
    		
    		request.setAttribute("refresh", "false");
    		
    		// Cargar en el iframe el mensaje de error
    		return mapping.findForward("error_remakeform");
    	}
    	
    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
    	
    	EntityForm entityForm = (EntityForm) form;
		int keyId = Integer.parseInt(entityForm.getKey());
		
		catalogAPI.remakeForm(keyId, getEntityDef(request), frmJsp);
    			
    	request.setAttribute("refresh", "true");

    	// Cargar en el iframe el mensaje de generación completada
		return mapping.findForward("success_remakeform");
    }
    
    public ActionForward defaultForm(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response,
									 SessionAPI session) throws ISPACException {
    	
    	// Identificador del formulario
    	EntityForm entityForm = (EntityForm) form;
		int keyId = Integer.parseInt(entityForm.getKey());
		
		if (keyId > 0) {
    	
			// Entidad
			ItemBean entityBean = getEntityBeanFromSession(request);
			IItem entity = entityBean.getItem();
			
			// Establecer formulario por defecto
			entity.set("SPAC_CT_ENTIDADES:FRM_EDIT", keyId);
			entity.store(session.getClientContext());
		}

    	request.setAttribute("refresh", "false");
    	
        // Cargar en un iframe el mensaje de generacion completada 
        return mapping.findForward("success_remakejsp");
    }
 
	private void checkFunctions(HttpServletRequest request, ClientContext cct,
			int tipoEntidad) throws ISPACException {
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
        if (EntityType.isValidationTableType(tipoEntidad)) {
			FunctionHelper.checkFunctions(request, cct, new int[] {
					ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_READ,
					ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });
        } else {
			FunctionHelper.checkFunctions(request, cct, new int[] {
					ISecurityAPI.FUNC_COMP_ENTITIES_READ,
					ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });
        }
    }
}