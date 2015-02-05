package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHierarchyDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.wizard.WizardButton;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class HierarchicalTableManageAction extends EntityManageAction {

    public static final String ENTITY_BEAN_KEY  = "entityBean";
    private static final String EDITANDO 			= "EDITANDO";
	private static final String EDITABLE = 			"EDITABLE";
    public static final String LABELS_LIST_KEY  = "labelsListKey";
    private static final String VIEWGENERAL_METHOD 	= "viewgeneral";
    //private static final String FIELDS_METHOD 		= "fields";
    //private static final String EDIT_METHOD			= "edit";
    private static final String ADD_METHOD			= "addValues";
    private static final String DELETE_METHOD 		= "deleteValues";

    private static final String LIST_VALUES_METHOD 	= "valuelist";
	private static final String ACCEPT_METHOD = "accept";


 	public ActionForward init(ActionMapping mapping,
 							  ActionForm form,
 							  HttpServletRequest request,
 							  HttpServletResponse response,
 							  SessionAPI session) throws Exception {

 		ClientContext cct = session.getClientContext();
    	request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "hierarchicalTable.wizard.title"));
    	request.setAttribute(WIZARD_SUBTITLE_KEY, getMessage(request, cct.getLocale(), "hierarchicalTable.wizard.title.step1.subtitle"));
        request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
                new WizardButton("wizard.button.finalize", composeJScriptIspacSubmitURL(request, "/hierarchicalTableManage.do?method=finalize")),
                new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL(request, "/showCTHierarchicalTablesList.do"))
        });

        request.setAttribute(LIST_TBL_VALIDATION, getListTblsValidation(session));

		return mapping.findForward("general_page");
	}

 	public ActionForward delete(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response,
			  SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

    	EntityForm frm = (EntityForm)form;
		String[] entitiesIds = frm.getMultibox();

		boolean deleteTableBD = StringUtils.equalsIgnoreCase(request.getParameter("deleteTableBD"),"true");
		for (int i = 0; i < entitiesIds.length; i++) {
			String id = entitiesIds[i];
			// Borrar la tabla jerárquica
			catalogAPI.deleteHierarchicalTable(Integer.parseInt(id), deleteTableBD);
		}

 		request.setAttribute(LIST_TBL_VALIDATION, getListTblsValidation(session));

		return mapping.findForward("finalize");

 	}


 	public ActionForward finalize(ActionMapping mapping,
			  					  ActionForm form,
			  					  HttpServletRequest request,
			  					  HttpServletResponse response,
			  					  SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

 		EntityForm entityForm = (EntityForm) form;
 		entityForm.setEntityAppName("CreateCTHierarchicalTable");
		ActionMessages errors = entityForm.validate(mapping, request);

    	//Se comprueba si existe otra con el mismo nombre
    	try{
    		CTHierarchyDAO hierarchyDAO = new CTHierarchyDAO();
    		hierarchyDAO.load(session.getClientContext().getConnection(), "WHERE NOMBRE = '" + entityForm.getProperty("NOMBRE") + "'");
    		errors.add("property(NOMBRE)", new ActionMessage("hierarchicalTable.error.table.exist"));
       	}catch(ISPACNullObject e){
       	}

		// Entidades no repetidas
		String idEntidadPadre = entityForm.getProperty("ID_ENTIDAD_PADRE");
		String idEntidadHija = entityForm.getProperty("ID_ENTIDAD_HIJA");

		if ((!StringUtils.isEmpty(idEntidadPadre)) &&
			(idEntidadPadre.equals(idEntidadHija))) {

			errors.add("property(ID_ENTIDAD_HIJA)", new ActionMessage("hierarchicalTable.error.entidadHijaIgualPadre"));
		}

		if (errors.isEmpty()) {

			IInvesflowAPI invesFlowAPI = session.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

			// Generar la tabla jerárquica si corresponde
			catalogAPI.createHierarchicalTable(entityForm.getProperty("NOMBRE"),
											   entityForm.getProperty("DESCRIPCION"),
											   Integer.parseInt(idEntidadPadre),
											   Integer.parseInt(idEntidadHija),
											   StringUtils.isEmpty(entityForm.getProperty("USE_VIEW")));
		}
		else {
			saveErrors(request, errors);
			return mapping.findForward("init");
		}

		return mapping.findForward("finalize");
 	}

 	public ActionForward viewgeneral(ActionMapping mapping,
			  					 	 ActionForm form,
			  					 	 HttpServletRequest request,
			  					 	 HttpServletResponse response,
			  					 	 SessionAPI session) throws Exception {

 		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_READ,
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

 		String regId = request.getParameter("regId");

		IItemCollection itemcol = catalogAPI.getHierarchicalTables(" WHERE id = " + regId);
		if (itemcol.next()) {

			ItemBean entityBean = new ItemBean(itemcol.value());
	        request.getSession().setAttribute(ENTITY_BEAN_KEY, entityBean);

	        // Establecer los datos en el formulario
	        EntityForm entityForm = (EntityForm) form;
	        entityForm.setItemBean(entityBean);
	        entityForm.setReadonly("true");

	        // Obtener las descripciones de las entidades
	        IItem entidadPadre = entitiesAPI.getCatalogEntity(entityBean.getItem().getInt("ID_ENTIDAD_PADRE"));
	        entityForm.setProperty("DES_ENTIDAD_PADRE", entitiesAPI.getEntityResourceValue(entidadPadre.getKeyInt(), cct.getAppLanguage(), entidadPadre.getString("NOMBRE")));

	        IItem entidadHija = entitiesAPI.getCatalogEntity(entityBean.getItem().getInt("ID_ENTIDAD_HIJA"));
	        entityForm.setProperty("DES_ENTIDAD_HIJA", entitiesAPI.getEntityResourceValue(entidadHija.getKeyInt(), cct.getAppLanguage(), entidadHija.getString("NOMBRE")));

	        // Título
	        request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "hierarchicalTable.label.modify") + " '" + entityForm.getProperty("NOMBRE") + "'");
	        request.setAttribute(WIZARD_SUBTITLE_KEY, null);

			// Pestañas
			WizardButton[] labels = createLabels(request);
			request.getSession().setAttribute(LABELS_LIST_KEY, labels);

	        // Botones
	        List buttons = new ArrayList();
	        //buttons.add(new WizardButton("forms.button.edit", composeJScriptURLForLabel(request, EDIT_METHOD)));
	        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step1.general");
	        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));

	        return mapping.findForward("general_page");
		}

		return mapping.findForward("finalize");
 	}

 	public ActionForward edit(ActionMapping mapping,
			 	 ActionForm form,
			 	 HttpServletRequest request,
			 	 HttpServletResponse response,
			 	 SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

        request.setAttribute(EDITANDO, Boolean.TRUE);

 		return mapping.findForward("general_page");

}
 	public ActionForward valuelist(ActionMapping mapping,
		 	 ActionForm form,
		 	 HttpServletRequest request,
		 	 HttpServletResponse response,
		 	 SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_READ,
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		EntityForm entityForm = (EntityForm)form;
 		String regId = ""+entityForm.getProperty("ID");
 		if (StringUtils.isEmpty(regId)){
 			regId = (String) request.getAttribute("regId");
 		}
 		if (StringUtils.isEmpty(regId)){
 			regId = (String) request.getParameter("regId");
 		}

 		IItem itemHT = getHTDefinition(session, regId);
		if (itemHT.getInt("TIPO") == HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE){
			request.setAttribute(EDITABLE, "true");
		}else{
			request.setAttribute(EDITABLE, "false");
		}

        // Título
		request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, session.getClientContext().getLocale(), "hierarchicalTable.label.values.mgr")+ " '" + itemHT.getString("NOMBRE") + "'");
        request.setAttribute(WIZARD_SUBTITLE_KEY, null);


		// Pestañas
		WizardButton[] labels = new WizardButton[] {
                new WizardButton("forms.button.back", composeJScriptIspacSubmitURL(request, "/showCTHierarchicalTablesList.do"), true),
                new WizardButton("entityManage.label.general", composeJScriptURLForLabel(request, VIEWGENERAL_METHOD, new String[]{"regId"}, new String[]{regId}), true),
                new WizardButton("entityManage.label.valores", composeJScriptURLForLabel(request, LIST_VALUES_METHOD), false)
        };
		request.getSession().setAttribute(LABELS_LIST_KEY, labels);


		// Obtener las credenciales del usuario conectado
		boolean editionMode = FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT);

        //Botones
		if (editionMode && (itemHT.getInt("TIPO") == HierarchicalTablesConstants.HIERARCHICAL_TABLE_TABLE_TYPE)) {
			List buttons = new ArrayList();
	        buttons.add(new WizardButton("forms.button.add",composeJScriptURLForLabel(request, ADD_METHOD, new String[]{"regId"}, new String[]{regId})));
	        buttons.add(new WizardButton("forms.button.delete",composeJScriptURLForLabel("deleteValues",request, DELETE_METHOD, new String[]{"regId"}, new String[]{regId})));
	        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));
		}

		IItemCollection itemcol = catalogAPI.getHierarchicalTableValues(Integer.parseInt(regId), null);
		List values = CollectionBean.getBeanList(itemcol);

		if (editionMode) {
			setFormatter(request, "CTHierarchicalTableValueListFormatter", "/formatters/cthierarchicaltablevalueslistformatter.xml");
		} else {
			setFormatter(request, "CTHierarchicalTableValueListFormatter", "/formatters/cthierarchicaltablevalueslistreadonlyformatter.xml");
		}

		request.setAttribute("regId", regId);
   	 	request.setAttribute("CTHierarchicalTableValueList", values);

		return mapping.findForward("value_list");
 	}

 	private IItem getHTDefinition(SessionAPI session, String regId) throws ISPACException {
 		IInvesflowAPI invesFlowAPI = session.getAPI();
 		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		IItemCollection itemcol = catalogAPI.getHierarchicalTables(" WHERE id = " + regId);
		if (itemcol.next()) {

			return itemcol.value();
		}
		return null;
	}

	public ActionForward addValues(ActionMapping mapping,
		 	 ActionForm form,
		 	 HttpServletRequest request,
		 	 HttpServletResponse response,
		 	 SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

 		String regId = request.getParameter("regId");

        // Título

 		IItem itemHT = getHTDefinition(session, regId);
        request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, session.getClientContext().getLocale(), "hierarchicalTable.label.values.add")+ " '" + itemHT.getString("NOMBRE") + "'");
        request.setAttribute(WIZARD_SUBTITLE_KEY, null);

		// Pestañas
		WizardButton[] labels = new WizardButton[] {
                new WizardButton("forms.button.back", composeJScriptIspacSubmitURL(request, "/showCTHierarchicalTablesList.do"), true),
                new WizardButton("entityManage.label.general", composeJScriptURLForLabel(request, VIEWGENERAL_METHOD, new String[]{"regId"}, new String[]{regId}), true),
                new WizardButton("entityManage.label.valores", composeJScriptURLForLabel(request, LIST_VALUES_METHOD), false)
        };
		request.getSession().setAttribute(LABELS_LIST_KEY, labels);

        //Bototnes
		List buttons = new ArrayList();
        buttons.add(new WizardButton("forms.button.accept", composeJScriptURLForLabel(request, ACCEPT_METHOD)));
        buttons.add(new WizardButton("wizard.button.cancel", composeJScriptURLForLabel(request, LIST_VALUES_METHOD, new String[]{"regId"}, new String[]{regId})));



        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));

        int entityId = itemHT.getInt("ID_ENTIDAD_PADRE");
        IItem itemEntity = entitiesAPI.getCatalogEntity(entityId);
        request.setAttribute("PARENT_ENTITY", itemEntity.getString("NOMBRE"));

        String searchFieldParentTable = "SUSTITUTO";
        if (itemEntity.getInt("TIPO") == EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId()) {
        	searchFieldParentTable = "VALOR";
        }
        request.setAttribute("searchFieldParentTable", searchFieldParentTable);

        entityId = itemHT.getInt("ID_ENTIDAD_HIJA");
        itemEntity = entitiesAPI.getCatalogEntity(entityId);
        request.setAttribute("DESCENDANT_ENTITY", itemEntity.getString("NOMBRE"));

        String searchFieldDescendantTable = "SUSTITUTO";
        if (itemEntity.getInt("TIPO") ==EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId()){
        	searchFieldDescendantTable = "VALOR";
        }
        request.setAttribute("searchFieldDescendantTable", searchFieldDescendantTable);

        request.setAttribute("regId", regId);

 		return mapping.findForward("addValues");
 	}

 	public ActionForward deleteValues(ActionMapping mapping,
		 	 ActionForm form,
		 	 HttpServletRequest request,
		 	 HttpServletResponse response,
		 	 SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        EntityForm entityForm = (EntityForm) form;

        String [] multibox = entityForm.getMultibox();
        String regId = request.getParameter("regId");
        String tableName = ICatalogAPI.HIERARCHICAL_TABLE_NAME + regId;
        catalogAPI.deleteHierarchicalTableValues(tableName, multibox);
        request.setAttribute("regId", regId);
		return valuelist(mapping, form, request, response, session);
	}


 	public ActionForward accept(ActionMapping mapping,
		 	 ActionForm form,
		 	 HttpServletRequest request,
		 	 HttpServletResponse response,
		 	 SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        EntityForm entityForm = (EntityForm) form;
        String parentValueId = entityForm.getProperty("PARENT_VALUE_ID");
 		String descendantValueId = entityForm.getProperty("DESCENDANT_VALUE_ID");
 		ActionMessages errors = entityForm.validate(mapping, request);

 		if (StringUtils.isEmpty(parentValueId)){
 	    	errors.add("property(PARENT_VALUE_ID)", new ActionMessage("errors.required", getMessage(request, session.getClientContext().getLocale(), "form.hierarchicalTables.values.propertyLabel.parentValue")));
 		}
 		if (StringUtils.isEmpty(descendantValueId)){
 	    	errors.add("property(DESCENDANT_VALUE_ID)", new ActionMessage("errors.required", getMessage(request, session.getClientContext().getLocale(), "form.hierarchicalTables.values.propertyLabel.descendantValue")));
 		}
		if (!errors.isEmpty()){
			saveErrors(request, errors);
			return mapping.findForward("reloadAddValues");
		}

 		String regId = request.getParameter("regId");
 		IItem itemHT = getHTDefinition(session, regId);

 		List parentIds = null;
 		List descendantIds = null;
 		if (StringUtils.equals(parentValueId, "-1")){
 			parentIds = getAllIdsValidationTable(session, itemHT.getInt("ID_ENTIDAD_PADRE"));
 			if (parentIds.isEmpty()) {
 				errors.add("property(PARENT_VALUE_ID)", new ActionMessage("hierarchicalTable.error.entidadSinValores", getMessage(request, session.getClientContext().getLocale(), "form.hierarchicaltables.entidadPadre")));
 			}
 		}
 		if (StringUtils.equals(descendantValueId, "-1")){
 			descendantIds = getAllIdsValidationTable(session, itemHT.getInt("ID_ENTIDAD_HIJA"));
 			if (descendantIds.isEmpty()) {
 				errors.add("property(DESCENDANT_VALUE_ID)", new ActionMessage("hierarchicalTable.error.entidadSinValores", getMessage(request, session.getClientContext().getLocale(), "form.hierarchicaltables.entidadHija")));
 			}
 		}
		if (!errors.isEmpty()){
			saveErrors(request, errors);
			return mapping.findForward("reloadAddValues");
		}

 		if (parentIds == null){
 			parentIds = new ArrayList();
 			parentIds.add(Integer.valueOf(parentValueId));
 		}

 		if (descendantIds == null){
 			descendantIds = new ArrayList();
 			descendantIds.add(Integer.valueOf(descendantValueId));
 		}

       	String tableName = ICatalogAPI.HIERARCHICAL_TABLE_NAME + regId;
 		//Se eliminan previamente los valores que ya estuvieran
       	catalogAPI.deleteHierarchicalTableValues(tableName, parentIds, descendantIds);
       	//Se insertan los nuevos valores
       	catalogAPI.addHierarchicalTableValues(tableName, parentIds, descendantIds);

       	request.setAttribute("regId", regId);
		return valuelist(mapping, form, request, response, session);
	}

 	private List getAllIdsValidationTable(SessionAPI session, int entityId) throws ISPACException{
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        List list = new ArrayList();

 		IItemCollection itemcol = entitiesAPI.queryEntities(entityId, null);
 		while(itemcol.next()){
 			list.add(new Integer(itemcol.value().getInt("ID")));
 		}
 		return list;
 	}

    private WizardButton[] createLabels(HttpServletRequest request) throws ISPACException{
    	return new WizardButton[] {
                    new WizardButton("forms.button.back", composeJScriptIspacSubmitURL(request, "/showCTHierarchicalTablesList.do"), true),
                    new WizardButton("entityManage.label.general", composeJScriptURLForLabel(request, VIEWGENERAL_METHOD), false),
                    new WizardButton("entityManage.label.valores", composeJScriptURLForLabel(request, LIST_VALUES_METHOD), true)
            };
    }

}