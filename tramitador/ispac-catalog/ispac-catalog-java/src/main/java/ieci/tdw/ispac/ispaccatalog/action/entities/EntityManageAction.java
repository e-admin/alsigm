/*
 * @(#)EntityManageAction.java 1.0 19/07/2007
 *
 * Copyright (c) 2006 Informática El Corte Inglés, S. A.
 * Travesía de Costa Brava, 4 (Mirasierra). 28034 MADRID. España.
 * All Rights Reserved.
 * 
 */

package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.CatalogAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.validations.RangoValidator;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;
import ieci.tdw.ispac.ispacweb.wizard.WizardButton;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EntityManageAction extends BaseDispatchAction {
	
    protected static final String WIZARD_TITLE_KEY        = "WIZARD_TITLE";
    protected static final String WIZARD_SUBTITLE_KEY_KEY = "WIZARD_SUBTITLE_KEY";
    protected static final String WIZARD_SUBTITLE_KEY     = "WIZARD_SUBTITLE";
    protected static final String ACTION_DESTINO_KEY 	  = "ActionDestino";
    protected static final String WIZARD_BUTTONBAR_KEY    = "WIZARD_BUTTONBAR";
    protected static final String LIST_TBL_VALIDATION     = "LIST_TBL_VALIDATION";
    protected static final String LIST_TBL_HIERARCHICAL   = "LIST_TBL_HIERARCHICAL";
    protected static final String INDEX_FIELD_LIST        = "INDEX_FIELD_LIST";
    protected static final String READ_ONLY 			  = "READ_ONLY";
    protected static final String EDITABLE 			  	  = "EDITABLE";
    protected static final String INDEXES_METHOD 		  = "indexes";
    protected static final String INDEX_METHOD 			  = "index";
    protected static final String FIELDS_MODIFICABLES     = "FIELDS_MODIFICABLES";
    protected static final String TIENE_APPDEFAULT        = "TIENE_APPDEFAULT";
    protected static final String LIST_FIELDS_KEY		  = "LIST_FIELDS";
    protected static final String DEFUALT_JS_FUNCTION 	  = "ispac_submit";
    
    public ActionForward index(ActionMapping mapping,
    						   ActionForm form,
    						   HttpServletRequest request,
    						   HttpServletResponse response,
    						   SessionAPI session, String title) throws ISPACException {

        EntityForm entityForm = (EntityForm) form;
        String nextAction = request.getServletPath(); 
        List buttons = new ArrayList();
        buttons.add(new WizardButton("forms.button.accept", 
                "javascript:"+DEFUALT_JS_FUNCTION+"(\"" + request.getContextPath() 
                    + nextAction + "?method=addIndex\")"));
        
        if (StringUtils.isNotBlank(entityForm.getProperty("ID"))) {
            buttons.add(new WizardButton("forms.button.delete", 
                    "javascript:"+DEFUALT_JS_FUNCTION+"(\"" + request.getContextPath() 
                        + nextAction + "?method=deleteItems\")"));
        }
        
        buttons.add(new WizardButton("wizard.button.cancel", 
                "javascript:"+DEFUALT_JS_FUNCTION+"(\"" + request.getContextPath() 
                    +  nextAction + "?method=indexes\")"));

        if (title != null) {
        	request.setAttribute(WIZARD_TITLE_KEY, title);
        }
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step3.index");
        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));
        request.setAttribute(ACTION_DESTINO_KEY, nextAction);

        return mapping.findForward("index_page");
    }

    public ActionForward showIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, Integer indexId, EntityDef entityDef, String entityId) throws ISPACException{
        
    	ClientContext cct = session.getClientContext();
    	
        EntityForm entityForm = (EntityForm) form;
        
        EntityIndex aIndex = null;
        List indexes = entityDef.getIndexes();
        for (Iterator iter = indexes.iterator(); iter.hasNext();) {
            aIndex = (EntityIndex) iter.next();
            if (aIndex.getId()== indexId.intValue()){
                break;
            }
        }
        
        entityForm.fillForm(aIndex);
        
        //obtencion de lista de campos del indice
        List fieldsIndex = new ArrayList();
        HashMap mapFields = entityDef.fieldsToMap();
        List fieldIds = aIndex.getFieldIds();
        for (Iterator iter = fieldIds.iterator(); iter.hasNext();) {
            Integer fieldId = (Integer) iter.next();
            EntityField field = (EntityField)mapFields.get(fieldId);
            fieldsIndex.add(createItemBeanEntityField(request, session, entityId,field));
        }
        
        request.getSession().setAttribute(INDEX_FIELD_LIST, fieldsIndex);
        
        String nextAction = request.getServletPath(); 
        
        List buttons = new ArrayList();

    	buttons.add(new WizardButton("forms.button.back", 
            "javascript:"+DEFUALT_JS_FUNCTION+"(\"" + request.getContextPath() 
                +  nextAction + "?method=indexes\")"));
        
        request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "entityManage.label.informacionDelIndice"));
        request.setAttribute(WIZARD_SUBTITLE_KEY, "");
        request.setAttribute(WIZARD_BUTTONBAR_KEY, 
                buttons.toArray(new WizardButton[buttons.size()]));

        request.setAttribute(ACTION_DESTINO_KEY, nextAction);
        
        request.setAttribute(READ_ONLY, Boolean.TRUE);
        
        return mapping.findForward("index_page");
        
    }
    
    protected List getIdsIndexFieldList(HttpServletRequest request) throws ISPACException{
        List actualIndexFieldList = (List) request.getSession()
        .getAttribute(INDEX_FIELD_LIST);
        List idsFieldsIndex = new ArrayList();
        if (!CollectionUtils.isEmpty(actualIndexFieldList)){
            for (Iterator itActualIndexFieldList = actualIndexFieldList.iterator(); itActualIndexFieldList.hasNext();) {
                ItemBean fieldIndex = (ItemBean) itActualIndexFieldList.next();
                idsFieldsIndex.add(fieldIndex.getProperty("ID"));
            }    
        
        }
        return idsFieldsIndex;
    }
    
    protected ActionMessages getErrorIndiceSinCamposNoValido(){
        ActionMessages errors = new ActionMessages();
        ActionMessage mensaje = null;
        mensaje = new ActionMessage("ispacexception", "entityManage.error.indiceSinCamposNoValido");
        errors.add(ActionMessages.GLOBAL_MESSAGE, mensaje);
        return errors;
        
    }
    
    public ActionForward logicAddIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, Integer entityId, 
            EntityDef entityDef, boolean isEdit) throws ISPACException {
        try{
            EntityForm entityForm = (EntityForm) form;
            
            // Validar el formulario
            entityForm.setEntityAppName("CreateCTEntityIndex");
            ActionMessages errors = entityForm.validate(mapping, request);
            
            //obtencion de los ids de los campos del indice
            List idsFieldsIndex = getIdsIndexFieldList(request); 
            if (CollectionUtils.isEmpty(idsFieldsIndex)){
                //no puede añadirse un indice sin campos
                //saveErrors(request, getErrorIndiceSinCamposNoValido());
            	errors.add("property(ERROR_OPCION)", new ActionMessage("entityManage.error.indiceSinCamposNoValido"));
                //return getActionForwardToThisAction(request, INDEX_METHOD);
            }            

            if (errors.isEmpty()) {
                IInvesflowAPI invesFlowAPI = session.getAPI();
                ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
                int indexId = entityDef.getIndexes().size() + 1;
                EntityIndex index = entityForm.composeEntityIndex(indexId,
                        idsFieldsIndex);           
                if (isEdit){
                    catalogAPI.addIndex(entityId.intValue(),
                            index);
                }
                
                entityDef.addIndex(index);
                
                return getActionForwardToThisAction(request, INDEXES_METHOD);
                
            } else {
                saveErrors(request, errors);
                return getActionForwardToThisAction(request, INDEX_METHOD);
            }

        }catch (ISPACException e) {
        	//Por ahora tiene que lanzarse asi porque no tenemos una tabla en la base de datos que guarde los indices, y la comprobacion seria 
        	//muy dificil de implementar.
            throw new ISPACInfo("exception.indexes.duplicated");
        }
    }
    
    protected String getThisActionURL(HttpServletRequest request, String method){
        return request.getServletPath() + "?method=" + method;
    }
    
    protected ActionForward getActionForwardToThisAction(HttpServletRequest request, String method){
        return new ActionForward(getThisActionURL(request, method));
    }
    
    protected String composeJScriptURLForLabel(HttpServletRequest request, String methodName){
//        return "javascript:ispac_submit(\"" + request.getContextPath()
//                + getThisActionURL(request, methodName) + "\")";
    	
    	return composeJScriptURLForLabel(DEFUALT_JS_FUNCTION, request, methodName);    	
    }

    protected String composeJScriptURLForLabel(HttpServletRequest request, String methodName, String[] parameters, String[] values){
    	return composeJScriptURLForLabel(DEFUALT_JS_FUNCTION, request, methodName, parameters, values);
    }
    
    protected String composeJScriptIspacSubmitURL(HttpServletRequest request, String actionName){
        //return "javascript:ispac_submit(\"" + request.getContextPath() + actionName + "\")";
    	return composeJScriptIspacSubmitURL(DEFUALT_JS_FUNCTION, request, actionName);
    }    
    
    protected String composeJScriptURLForLabel(String jsFunction, HttpServletRequest request, String methodName, String[] parameters, String[] values){
        return "javascript:"+jsFunction+"(\"" + request.getContextPath()
        + getThisActionURL(request, methodName) + "&" + concatenateParameters(parameters, values) + "\")";

    }
    
    protected String composeJScriptURLForLabel(String jsFunction, HttpServletRequest request, String methodName){
      return "javascript:"+jsFunction+"(\"" + request.getContextPath()
      	+ getThisActionURL(request, methodName) + "\")";
    }

    protected String composeJScriptIspacSubmitURL(String jsFunction, HttpServletRequest request, String actionName){
    	return "javascript:"+jsFunction+"(\"" + request.getContextPath() + actionName + "\")";
    }
    
    
    private String concatenateParameters(String[] parameters, String[] values){
    	String result = "";
    	for (int i = 0; i < parameters.length; i++) {
			result += parameters[i] + "=" + values[i];
		}
    	return result;
    }
    
	/**
	 * Ordenación para la lista de funcionarios. 
	 */
	public static class OrdenacionFields implements Comparator {
		
		private Collator collator = null;
		
		public OrdenacionFields(Locale locale) {
			collator = Collator.getInstance(locale);
		}
		
		public int compare(Object o1, Object o2)
		{
			try {
				String label1 = (String)((ItemBean) o1).getProperty("ETIQUETA");
				if (label1 == null)
					label1 = "";
				String label2 = (String)((ItemBean) o2).getProperty("ETIQUETA");
				if (label2 == null)
					label2 = "";
				
				return collator.compare(label1, label2);
			} catch (ISPACException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
    /**
     * Muestra el formulario con la información de una validación de la entidad.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
    public ActionForward validation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, EntityDef entityDef, String title, String entityId) throws ISPACException {
        
        EntityForm entityForm = (EntityForm) form;
        String nextAction = request.getServletPath();
        List buttons = new ArrayList();
        buttons.add(new WizardButton("forms.button.accept", 
                "javascript:ispac_submit(\"" + request.getContextPath() 
                    + nextAction + "?method=addValidation\")"));
        
        if (StringUtils.isNotBlank(entityForm.getProperty("ID"))) {
            buttons.add(new WizardButton("forms.button.delete", 
                    "javascript:ispac_submit(\"" + request.getContextPath() 
                        + nextAction + "?method=deleteItems\")"));
        }
        
        buttons.add(new WizardButton("wizard.button.cancel", 
                "javascript:ispac_submit(\"" + request.getContextPath() 
                    + nextAction + "?method=validations\")"));

        if (title != null) {
        	request.setAttribute(WIZARD_TITLE_KEY, title);
        }
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step4.validation");
        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));
        request.setAttribute(LIST_TBL_VALIDATION, getListTblsValidation(session));
        request.setAttribute(LIST_TBL_HIERARCHICAL, getListTblsHierarchical(session));

        // Eliminar de la lista de campos aquellos que ya tienen alguna validacion
        Map entityFields = entityDef.fieldsToMap();
        List validations = entityDef.getValidations();
        if (!CollectionUtils.isEmpty(validations)) {
        	
        	for (Iterator iter = validations.iterator(); iter.hasNext();) {
        		
				EntityValidation validation = (EntityValidation) iter.next();
				entityFields.remove(new Integer(validation.getFieldId()));
				
			}
        }
        
        for (Iterator iter = entityFields.values().iterator(); iter.hasNext();) {
        	
			EntityField element = (EntityField) iter.next();
			// Eliminar de la lista de campos el id y el numero de expediente
			if ((element.getPhysicalName().equalsIgnoreCase(ICatalogAPI.ID_FIELD_NAME)) ||
				(element.getPhysicalName().equalsIgnoreCase(ICatalogAPI.NUMEXP_FIELD_NAME)) ) {
				
				iter.remove();
			}
			/*
			else {
				// Eliminar todos aquellos que no son texto y texto largo
				if(!((element.getType().equals(InternalDataType.SHORT_TEXT)) || 
					 (element.getType().equals(InternalDataType.LONG_TEXT)))) {
					
					iter.remove();
				}
			}
			*/
        }
        
        // Ordenar la lista de campos
        Collection validablesFields = entityFields.values();
        List listaValidablesFields = new ArrayList(validablesFields);
        List entityBeanListValidablesFields = CollectionBean.getBeanList(new ListCollection(listaValidablesFields));
        transformExtendedFields(request, entityId, entityBeanListValidablesFields, session);
        Collections.sort(entityBeanListValidablesFields, new OrdenacionFields(request.getLocale()));

        request.setAttribute(LIST_FIELDS_KEY, entityBeanListValidablesFields);
        
        String actionDestino = request.getServletPath();
        request.setAttribute(ACTION_DESTINO_KEY, actionDestino);

        return mapping.findForward("validation_page");
    }
    
    protected List getListTblsValidation(SessionAPI session)
    throws ISPACException
    {
    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
    	IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
    	
        StringBuffer condicion = new StringBuffer();
        condicion.append("WHERE TIPO >= ")
        		 .append(EntityType.MIN_ID_VALIDATION_TABLE)
        		 .append(" AND TIPO <= ")
        		 .append(EntityType.MAX_ID_VALIDATION_TABLE);
        
        IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, condicion.toString());
        List collBean = CollectionBean.getBeanList(itemcol);
        if (!collBean.isEmpty()) {

	        StringBuffer queryTblVlds = new StringBuffer();
	        for (Iterator iter = collBean.iterator(); iter.hasNext();) {
	        	
				ItemBean tblVld = (ItemBean) iter.next();
				if (queryTblVlds.length() > 0)
					queryTblVlds.append(" OR ");
				
				queryTblVlds.append(" CLAVE = '").append(tblVld.getString("NOMBRE")).append("' ");
			}
	        
	    	Map entitiesLabelMap = null;
	    	
			StringBuffer query = new StringBuffer();
			query.append(" WHERE IDIOMA='")
				 .append(session.getClientContext().getAppLanguage())
				 .append("' AND ( ")
				 .append(queryTblVlds)
				 .append(" )");
			
			IItemCollection col = entitiesAPI.queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
			entitiesLabelMap = col.toMap("CLAVE");
			 
			for (Iterator iter = collBean.iterator(); iter.hasNext();) {
				
				ItemBean tblVld = (ItemBean) iter.next();
				EntityDAO dao = ( (EntityDAO) entitiesLabelMap.get(tblVld.getString("NOMBRE")));
				if (dao != null) {
					tblVld.setProperty("LOGICALNAME", dao.getString("VALOR"));
				}
				else {
					tblVld.setProperty("LOGICALNAME", tblVld.getString("NOMBRE"));
				}
			}
        }
        
        return collBean;
    }
    
    protected List getListTblsHierarchical(SessionAPI session)
    throws ISPACException
    {
    	
    	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
    	
    	IItemCollection itemcol = catalogAPI.getHierarchicalTables(null);
    	List collBean = CollectionBean.getBeanList(itemcol);
    	
    	return collBean;
    }
    
    
   
    public ActionMessages validateAddValidation(ActionMapping mapping, 
    											ActionForm form, 
    											HttpServletRequest request, 
    											SessionAPI session, 
    											EntityDef entityDef) throws ISPACException {
    	
    	
    	// Validacion del formulario
    	EntityForm entityForm = (EntityForm) form; 
    	ActionMessages errors = entityForm.validate(mapping, request);
    	
    	if ((StringUtils.isBlank(entityForm.getProperty("VALIDATION_WITH"))) && 
    		(StringUtils.isBlank(entityForm.getProperty("MANDATORY")))) {
    	   
    		errors.add("property(ERROR_OPCION)", new ActionMessage("entity.validation.error.faltaOpcionValidacion"));
    	}
    	
    	// check de validacion contra tabla o clase esta activa
    	if (StringUtils.isNotBlank(entityForm.getProperty("VALIDATION_WITH"))) {
    		
    		if (StringUtils.isBlank(entityForm.getProperty("VALIDATION_TYPE"))) {
    			
    		   errors.add("property(VALIDATION_TYPE)", new ActionMessage("entity.validation.error.validationType"));
    		}
    		else {
    			
    			String validationType = entityForm.getProperty("VALIDATION_TYPE");
    			if ("CLAZZ".equals(validationType)) {
    				
	                if (StringUtils.isBlank(entityForm.getProperty("CLAZZ"))) {
	                	errors.add("property(CLAZZ)", new ActionMessage("entity.validation.error.class"));
	                }
	            } 
    			else {
	                if (StringUtils.isBlank(entityForm.getProperty("TABLE"))){
	                	errors.add("property(TABLE)", new ActionMessage("entity.validation.error.table"));	
	                }
	                else {
	                	
	                	// Campo que se quiere validar
		     	    	EntityField entityFieldEntity = entityDef.findField(Integer.parseInt(entityForm.getProperty("FIELDID")));
		     	    	
// Sólo campos de texto
//						if(!((entityFieldEntity.getType().equals(InternalDataType.SHORT_TEXT)) || 
//							 (entityFieldEntity.getType().equals(InternalDataType.LONG_TEXT)))) {
		     	    	// Sólo campos de texto corto
		     	    	if(!((entityFieldEntity.getType().equals(InternalDataType.SHORT_TEXT))) ) {
							 
							throw new ISPACInfo("exception.entities.addValidation.noString");
						}
						else {
							// Comprobar si el tamaño del campo valor de la tabla de validacion
							// es mayor que el tamaño del campo de la entidad
							IInvesflowAPI invesFlowAPI = session.getAPI();
							CatalogAPI catalogAPI = (CatalogAPI) invesFlowAPI.getCatalogAPI();
	     	       
							// Obtener la definición de la tabla de validación seleccionada
		     	    	   	IItem itemTb = catalogAPI.getCTEntity(CatalogAPI.ENTITY_CT_ENTITY, Integer.parseInt(entityForm.getProperty("TABLE")));
		     	    	   	EntityDef entityDefTb = EntityDef.parseEntityDef(itemTb.getString("DEFINICION"));
		     	    	   	List fieldsTb = entityDefTb.getFields();
		     	    	   	// Campo valor y su tamaño
		     	    	   	EntityField entityFieldTb = (EntityField) fieldsTb.get(1);
		     	    	   	int sizeValueVldTbl = entityFieldTb.getSize();
		     	       
		     	    	   	// Tamaño del campo que se quiere validar
		     	    	   	int sizeFieldEntity = entityFieldEntity.getSize();
		     	    	   	
		     	    	   	if (sizeValueVldTbl > sizeFieldEntity) {
		     	    		   
		     	    		   throw new ISPACInfo("exception.entities.addValidation.sizeValue");
		     	    	   }
						}
	                }
	            }
    		}
       } 

       return errors;
    }
    /**
     * Añade la validación a la Entidad.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
    public ActionForward addValidation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, Integer entityId, EntityDef entityDef, boolean isEdit ) throws ISPACException {
        
        EntityForm entityForm = (EntityForm) form;

        // Validar el formulario
        entityForm.setEntityAppName("CreateCTEntityValidation");
        //ActionMessages errors = entityForm.validate(mapping, request);
        ActionMessages errors = validateAddValidation(mapping, form, request, session, entityDef);
        if (errors.isEmpty()) {
        	
        	ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();

            // Añadir la información de la validación
            EntityValidation validation = new EntityValidation();
            validation.setId(entityDef.getValidations().size() + 1);
            validation.setFieldId(TypeConverter.parseInt(entityForm.getProperty("FIELDID"), 0));
            validation.setMandatory(entityForm.getProperty("MANDATORY"));

            //solo si el check de validacion contra tabla o clase esta activa
            if (StringUtils.isNotBlank(entityForm.getProperty("VALIDATION_WITH"))) {
            	
            	String validationType = entityForm.getProperty("VALIDATION_TYPE");
	            if ("CLAZZ".equals(validationType)) {
	            	
	                validation.setClazz(entityForm.getProperty("CLAZZ"));   
	            }
	            else {
	            	String validationTableId = entityForm.getProperty("TABLE");
	                
	                // Obtener la entidad de la tabla de validación seleccionada
	                IItem validationTable = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_ENTITY, Integer.parseInt(validationTableId));
	                
	                // Validación para el campo de la entidad
	                validation.setTable(validationTable.getString("NOMBRE"));
	                validation.setTableType(validationTable.getString("TIPO"));

	                String hierarchicalTableId = entityForm.getProperty("HIERARCHICAL_TABLE");
	                if (StringUtils.isNotEmpty(hierarchicalTableId)){
	                	validation.setHierarchicalId(Integer.parseInt(hierarchicalTableId));
	                	IItemCollection itemcol = catalogAPI.getHierarchicalTables("WHERE ID = "+hierarchicalTableId);
	                	IItem itemHT = itemcol.value();
	                	validation.setHierarchicalName(itemHT.getString("NOMBRE"));
	                }
	            }
            }
            
            if (isEdit){
                catalogAPI.addValidation(entityId.intValue(), validation);
            }
            
            entityDef.addValidation(validation);
            
            return mapping.findForward("validations");
        }
        else {
            saveErrors(request, errors);
            return mapping.findForward("validation");
        }
    }

    public void putButtonsInRequest(HttpServletRequest request, List buttons){
        request.setAttribute(WIZARD_BUTTONBAR_KEY, 
                buttons.toArray(new WizardButton[buttons.size()]));
        
    }
    
    public ActionForward prepareAndRedirectoToValidationsPage(EntityDef entityDef,
            HttpServletRequest request, ActionMapping mapping, SessionAPI session, String entityId)
    throws ISPACException {

        //Map a session para 
        HashMap map = new HashMap();
        List tblsValidation = getListTblsValidation(session);
        for (Iterator itTblsValidation = tblsValidation.iterator(); itTblsValidation.hasNext();) {
            ItemBean aTbl = (ItemBean) itTblsValidation.next();
            map.put(aTbl.getString("NOMBRE"), aTbl.getProperty("LOGICALNAME"));
        }

        List validationList = EntityValidation.getEntityValidationExtendedList(entityDef, map, entityId, session);        
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(
                getISPACPath("/formatters/entities/validationlistformatter.xml"));
        request.setAttribute("ValidationListFormatter", formatter);
        request.setAttribute("ValidationList", validationList);

 
        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());


        return mapping.findForward("validations_page");
    }
    
    public ActionForward prepareAndRedirectToIndexesPage(EntityDef entityDef,
            HttpServletRequest request, ActionMapping mapping, String formatterName)
            throws ISPACException {
        
        List indexList = CollectionBean.getBeanList(new ListCollection(
                entityDef.getIndexes()));

        //añadir lista de campos
        ArrayList indices = new ArrayList();
        List indexes = entityDef.getIndexes();
        for (Iterator iter = indexes.iterator(); iter.hasNext();) {
			EntityIndex element = (EntityIndex) iter.next();
			List fieldsIndex = element.getFieldIds();
			StringBuffer str = new StringBuffer();
			for (Iterator iterator = fieldsIndex.iterator(); iterator.hasNext();) {
				Integer id = (Integer) iterator.next();
				if (str.length()>0)
					str.append(", ");
				str.append(entityDef.findField(id.intValue()).getPhysicalName());
			}
			indices.add(str.toString());
		}
        int i = 0;
        for (Iterator iter = indexList.iterator(); iter.hasNext();) {
        	ItemBean element = (ItemBean) iter.next();
			element.setProperty("CAMPOS_INDICE", indices.get(i));
			i++;
		}

        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory
                .getFormatter(getISPACPath(formatterName));

        request.setAttribute("IndexListFormatter", formatter);
        request.setAttribute("IndexList", indexList);

        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());

        return mapping.findForward("indexes_page");
    }
    
    public ActionForward prepareAndRedirectToIndexFieldsPage(EntityDef entityDef,
            HttpServletRequest request, ActionMapping mapping, String entityId, SessionAPI session)
            throws ISPACException {
        
    	
    	 List actualIndexFieldList = (List) request.getSession()
         .getAttribute(INDEX_FIELD_LIST);
    	
    	
        String actionDestino = request.getServletPath();
        request.setAttribute(ACTION_DESTINO_KEY, actionDestino);

        List fieldList = CollectionBean.getBeanList(
                new ListCollection(entityDef.getSimpleFieldsNotLOB()));
                
        transformExtendedFields(request, entityId, fieldList, session);
        
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(
                getISPACPath("/formatters/entities/fieldlistindexformatter.xml"));

        request.setAttribute("FieldListFormatter", formatter);
        
        List aux=new ArrayList();
        if ( (actualIndexFieldList != null)) {	  	
        	Iterator itractualIndexFieldList = actualIndexFieldList.iterator();
        	Iterator itrFieldList = null;
        	while (itractualIndexFieldList.hasNext()) {
        		ItemBean itemActualIndexFieldList = (ItemBean) itractualIndexFieldList.next();   
        		itrFieldList = fieldList.iterator();
        		while (itrFieldList.hasNext()) {
        			ItemBean itemFieldList = (ItemBean) itrFieldList.next();
        			if ((itemFieldList.getString("ID")).equalsIgnoreCase(itemActualIndexFieldList.getString("ID"))) {
        				aux.add(itemFieldList);
        				break;
        			}       			       			
        		}
        	}
        	fieldList.removeAll(aux);
        }
  
        
        request.setAttribute("FieldList", fieldList);

        return mapping.findForward("indexFields_page");

    }
    
    public ActionForward addIndexField(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, EntityDef entityDef, String entityId) throws ISPACException {

        List actualIndexFieldList = (List) request.getSession()
        .getAttribute(INDEX_FIELD_LIST);
        if (actualIndexFieldList==null){
            actualIndexFieldList= new ArrayList();
            request.getSession().setAttribute(INDEX_FIELD_LIST, actualIndexFieldList);
        }

        EntityForm entityForm = (EntityForm) form;
        HashMap mapFields = entityDef.fieldsToMap();
        String[] fieldIds = entityForm.getMultibox();
        if (fieldIds != null && fieldIds.length > 0) {
            for (int i = 0; i < fieldIds.length; i++) {
                EntityField field = (EntityField)mapFields.get(new Integer(fieldIds[i]));
                actualIndexFieldList.add(createItemBeanEntityField(request, session, entityId,field));
            }
        }
        return getActionForwardIndexFromChangeIndexField(mapping, form);
        
    } 
    
    private ActionForward getActionForwardIndexFromChangeIndexField(ActionMapping mapping, ActionForm form ){
        
        EntityForm entityForm = (EntityForm) form;
        
        //mantener la seleccion del formulario de indice
        ActionForward ret = mapping.findForward("index");
        StringBuffer newPath = new StringBuffer(ret.getPath()).append("&property(NAME)=").append(entityForm.getProperty("NAME"));
        newPath.append("&property(PRIMARYKEY)=").append(entityForm.getProperty("PRIMARYKEY"));
        newPath.append("&property(UNIQUE)=").append(entityForm.getProperty("UNIQUE"));
        newPath.append("&property(DOCUMENTAL)=").append(entityForm.getProperty("DOCUMENTAL"));
        ActionForward newRet = new ActionForward();
        newRet.setPath(newPath.toString());
        newRet.setRedirect(true);
        return newRet;
    }

    public ActionForward deleteIndexFields(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        List fieldList = (List) request.getSession()
            .getAttribute("INDEX_FIELD_LIST");

        String [] ids = ((EntityForm) form).getMultibox();

        if (!ArrayUtils.isEmpty(ids) && !CollectionUtils.isEmpty(fieldList)) {
            Iterator it = fieldList.iterator();
            for (int cont = 1; it.hasNext(); cont++) {
                it.next();
                if (ArrayUtils.contains(ids, String.valueOf(cont))) {
                    it.remove();
                }
            }
        }
        
        return getActionForwardIndexFromChangeIndexField(mapping, form);
    }
    
    /**
     * Obtiene un mapa con los recusos de la entidad 
     * @param session
     * @param entityId
     * @return
     * @throws ISPACException
     */
    private Map getEntitiesLabelMap(ISessionAPI session, String entityId)
    throws ISPACException 
    {
    	Map entitiesLabelMap = null;
    	
    	if (entityId != null) {
    		
			StringBuffer query = new StringBuffer();
			query.append(" WHERE ID_ENT=")
				 .append(entityId)
				 .append(" AND IDIOMA='")
				 .append(session.getClientContext().getAppLanguage())
				 .append("' ");
			
			IItemCollection col = session.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
			entitiesLabelMap = col.toMap("CLAVE");
    	}
    	
    	return entitiesLabelMap;
    }

    private void setEtiqueta(ItemBean itemBeanField, EntityField field, Map resourcesEntity)
    throws ISPACException
    {
        if (resourcesEntity != null) {
        	
            EntityDAO entResource = ((EntityDAO)resourcesEntity.get(itemBeanField.getString("PHYSICALNAME").toUpperCase()));
			if (entResource != null) {
				
				itemBeanField.setProperty("ETIQUETA", entResource.get("VALOR"));
				return;
			}
        }

        String label = field.getLogicalName();
		if (label == null) {
			label = field.getPhysicalName();
		}		
		itemBeanField.setProperty("ETIQUETA", label);    	
    }
    
    protected ItemBean createItemBeanEntityField(HttpServletRequest request, 
    											 ISessionAPI session, 
    											 String entityId, 
    											 EntityField field) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	ItemBean bean = new ItemBean(field);
    	
        bean.setProperty("TYPEDESCR", getMessage(request, cct.getLocale(), "field.type." + ((EntityField)bean.getItem()).getType().getId()));
        setEtiqueta(bean, field, getEntitiesLabelMap(session,entityId));
        
        return bean;
    }
    
    private void transformExtendedFields(HttpServletRequest request, 
    									 String entityId, 
    									 List fieldList, 
    									 SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
    	Map entitiesLabelMap = getEntitiesLabelMap(session,entityId);
    	ItemBean bean;
        for (int i = 0; i < fieldList.size(); i++) {
            bean = (ItemBean) fieldList.get(i);
            bean.setProperty("TYPEDESCR", getMessage(request, cct.getLocale(), "field.type." 
                    + ((EntityField)bean.getItem()).getType().getId()));
            //si estoy en la edicion
            setEtiqueta(bean, ((EntityField)bean.getItem()), entitiesLabelMap);
        }
    }
    
    public ActionForward prepareAndRedirectToFieldsPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, EntityDef entityDef, String entityId, int entityType) throws ISPACException {
        
		List fieldList = CollectionBean.getBeanList(
                new ListCollection(entityDef.getFields()));
		
		transformExtendedFields(request, entityId, fieldList, session);
        
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        String formatterPath = null;
        if (EntityType.isValidationTableType(entityType)){
        	formatterPath = "/formatters/entities/fieldlisttblvldformatter.xml";
        }else {
        	formatterPath = "/formatters/entities/fieldlistformatter.xml";
        }
        request.setAttribute("linkAction", "showCTEntityToManage.do?method=showFieldEntity");
        if(StringUtils.isEmpty(entityId)){
        	 request.setAttribute("linkAction", "showEntityWizard.do?method=showFieldEntity");
        }
        BeanFormatter formatter = factory.getFormatter(
                getISPACPath(formatterPath));

        request.setAttribute("FieldListFormatter", formatter);
        request.setAttribute("FieldList", fieldList);

        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());
        
        return mapping.findForward("fields_page");
    }
    
    public ActionForward prepareAndRedirectToResourcesPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session, EntityDef entityDef, String entityId, int entityType) throws ISPACException {
        
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();

    	// Nombre de la entidad
        BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/entities/resourcesentitylistformatter.xml"));
        request.setAttribute("ResourcesEntityListFormatter", formatter);
        
    	// Campos de la entidad
        formatter = factory.getFormatter(getISPACPath("/formatters/entities/resourcesfieldslistformatter.xml"));
        request.setAttribute("ResourcesFieldsListFormatter", formatter);
    	
    	// Otros recursos
        formatter = factory.getFormatter(getISPACPath("/formatters/entities/resourcesotherslistformatter.xml"));
        request.setAttribute("ResourcesOthersListFormatter", formatter);

        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());
        
        return mapping.findForward("resources_page");
    }

    public ActionForward prepareAndRedirectToFieldPage(ActionMapping mapping,
    												   ActionForm form,
    												   HttpServletRequest request,
    												   HttpServletResponse response,
    												   SessionAPI session, String title, 
    												   EntityField field, 
    												   String entityId) throws ISPACException {
        
        EntityForm entityForm = (EntityForm) form;
        request.setAttribute("readOnly", "false");
        List buttons = new ArrayList();
        if(field==null){
        buttons.add(new WizardButton("forms.button.accept", 
                "javascript:ispac_submit(\"" + request.getContextPath() 
                    + request.getServletPath() + "?method=addField\")"));
        }
        else if( entityId==null || (field.getType().getId()== InternalDataType.LONG_TEXT.getId() || field.getType().getId()==InternalDataType.SHORT_TEXT.getId()) && !field.getDocumentarySearch()){
        	
        	buttons.add(new WizardButton("forms.button.accept", 
                     "javascript:ispac_submit(\"" + request.getContextPath() 
                         + request.getServletPath() + "?method=saveField\")"));
        }
        
        if (StringUtils.isNotBlank(entityForm.getProperty("ID"))) {
            buttons.add(new WizardButton("forms.button.delete", 
                    "javascript:ispac_submit(\"" + request.getContextPath() 
                         + request.getServletPath() + "?method=deleteItems\")"));
        }
        
        buttons.add(new WizardButton("wizard.button.cancel", 
                "javascript:ispac_submit(\"" + request.getContextPath() 
                    + request.getServletPath() + "?method=fields\")"));

        if (title != null) {
        	request.setAttribute(WIZARD_TITLE_KEY, title);
        }
        
        
      if(field!=null){
		 Property [] propiedades=field.getProperties().getPropertyArray();
		for(int j=0; j<propiedades.length; j++){
			 entityForm.setProperty(propiedades[j].getName(),field.get(propiedades[j].getName())+"");
			
		}
		
		ClientContext cct = session.getClientContext();
    	ItemBean bean = new ItemBean(field);
    	int idTipo=((EntityField)bean.getItem()).getType().getId();
        bean.setProperty("TYPEDESCR", getMessage(request, cct.getLocale(), "field.type." + idTipo));
        if(StringUtils.isNotEmpty(entityId)){
        request.setAttribute("readOnly", "true");
        }
    
        else{
        	 request.setAttribute("readOnly", "false");
        }
        if(  idTipo==InternalDataType.SHORT_TEXT.getId()  ||  idTipo== InternalDataType.LONG_TEXT.getId()){
        	  request.setAttribute("showBusquedaDocumental", "true");
 	   
        }
        if(StringUtils.isNotEmpty(entityId)){
        setEtiqueta(bean, field, getEntitiesLabelMap(session,entityId));
		entityForm.setProperty("LOGICALNAME",bean.getProperty("ETIQUETA")+"");
        }
		entityForm.setProperty("TYPE",idTipo+"");
		
      }
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.title.step2.field");
        request.setAttribute(WIZARD_BUTTONBAR_KEY, buttons.toArray(new WizardButton[buttons.size()]));
        request.setAttribute(ACTION_DESTINO_KEY, request.getServletPath());
        
        return mapping.findForward("field_page");
    }
    
    
    public ActionMessages validateField(ActionMapping mapping, ActionForm form, HttpServletRequest request, EntityDef entityDef,  SessionAPI session){
    	
    	EntityForm entityForm = (EntityForm) form; 
    	ActionMessages errors = entityForm.validate(mapping, request);
    	ClientContext cct = session.getClientContext();
    	
    	//validacion de campo no utilizado por el sistema de tramitacion
    	if ("ID".equals(entityForm.getProperty("PHYSICALNAME").toUpperCase())){
    		errors.add("property(PHYSICALNAME)", new ActionMessage("entity.validation.error.nombreFisicoUtilizadoPorElSistema"));
    	}
    	if ("NUMEXP".equals(entityForm.getProperty("PHYSICALNAME").toUpperCase())){
    		errors.add("property(PHYSICALNAME)", new ActionMessage("entity.validation.error.nombreFisicoUtilizadoPorElSistema"));
    	}
    	
    	//Validacion del tamaño controlando si el campo es multivalor
    	if ((!StringUtils.equals(entityForm.getProperty("MULTIVALUE"), EntityDef.TRUE))){ 
    	if(StringUtils.isNotEmpty(entityForm.getProperty("TYPE")) && (Integer.parseInt(entityForm.getProperty("TYPE"))==0 || Integer.parseInt(entityForm.getProperty("TYPE"))==2 || Integer.parseInt(entityForm.getProperty("TYPE"))==4)){
    		if(StringUtils.isEmpty(entityForm.getProperty("SIZE"))){
    			errors.add("property(SIZE)", new ActionMessage("form.entity.field.error.campo.empty",getResources(request).getMessage("form.entity.field.propertyLabel.size")));
    		}
    	}
    	
    	//Validacion de los rangos
    	if(StringUtils.isNotEmpty(entityForm.getProperty("TYPE")) && (Integer.parseInt(entityForm.getProperty("TYPE"))==2 || Integer.parseInt(entityForm.getProperty("TYPE"))==3)){
    	
    		
    		if(StringUtils.isNotEmpty(entityForm.getProperty("RANGOMAX"))){
    			if(!RangoValidator.validateEntero(entityForm.getProperty("RANGOMAX"))){
    				errors.add("property(RANGOMAX)", new ActionMessage("form.entity.field.propertyLabel.rangoMax.invalid"));
    			}
    		}
    		if(StringUtils.isNotEmpty(entityForm.getProperty("RANGOMIN"))){
    			if(!RangoValidator.validateEntero(entityForm.getProperty("RANGOMIN"))){
    				errors.add("property(RANGOMIN)", new ActionMessage("form.entity.field.propertyLabel.rangoMin.invalid"));
    			}
    		}
    		
    	}
    	
    	//Validacion de los rangos
    	if(StringUtils.isNotEmpty(entityForm.getProperty("TYPE")) && (Integer.parseInt(entityForm.getProperty("TYPE"))==4 || Integer.parseInt(entityForm.getProperty("TYPE"))==5)){
    	
    		
    		if(StringUtils.isNotEmpty(entityForm.getProperty("RANGOMAX"))){
    			if(!RangoValidator.validateDecimal(entityForm.getProperty("RANGOMAX"), cct.getLocale()  )){
    				errors.add("property(RANGOMAX)", new ActionMessage("form.entity.field.propertyLabel.rangoMax.invalid"));
    				addErrors(request, errors);
    			}
    		}
    		if(StringUtils.isNotEmpty(entityForm.getProperty("RANGOMIN"))){
    			if(!RangoValidator.validateDecimal(entityForm.getProperty("RANGOMIN"), cct.getLocale())){
    				errors.add("property(RANGOMIN)", new ActionMessage("form.entity.field.propertyLabel.rangoMin.invalid"));
    				addErrors(request, errors);
    			}
    		}
    		
    	}
    	}
    	return errors;
    }
    
    public ActionMessages validateAddField(ActionMapping mapping, ActionForm form, HttpServletRequest request, EntityDef entityDef,  SessionAPI session){
    	
    	
    	//validacion del formulario
    	EntityForm entityForm = (EntityForm) form; 

    	ActionMessages errors=validateField(mapping,form, request, entityDef, session);
    	

    	//validacion de campo no repetido
    	List campos = entityDef.getFields();
    	for (Iterator iter = campos.iterator(); iter.hasNext();) {
			EntityField field = (EntityField ) iter.next();
			if (field.getPhysicalName().equalsIgnoreCase(entityForm.getProperty("PHYSICALNAME"))){
				errors.add("property(PHYSICALNAME)", new ActionMessage("entity.validation.error.nombreFisicoDuplicado"));
			}
			
		}
    	
    	
    	return errors;
    }
    /**
     * En los tipos de datos 'ficticios' es decir nif, cif, ccc y email no se muestra el campo tamaño para que rellene el usuario , porque
     * ya se sabe de cuanto tiene que ser cada uno, este método devuelve el tamaño en función de parámetro tipo recibido.
     * @param tipo
     * @return
     */
    private int setTamanyoCampo(int tipo){
    	
    	
    	switch (tipo){
    	case 8 : //NIF CIF NIE
    	case 9: 	
    	case 10: return 9;
    	case 11: return 100;//email
    	case 12: return 20; //ccc
    	default: return 0;
    	}
    	
    }
    
    public ActionForward saveField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session, Integer entityId, EntityDef entityDef, boolean isEdit)
    throws ISPACException
    {
        EntityForm entityForm = (EntityForm) form;
        
        // Validar el formulario
        entityForm.setEntityAppName("CreateCTEntityField");
        
        //ActionMessages errors = entityForm.validate(mapping, request);
        ActionMessages errors = validateField(mapping, form, request, entityDef, session);
                
        if (errors.isEmpty()) {
        	
        	// Identificador del campo
            String id = entityForm.getProperty("ID");
            
            // Establecer la definición del campo
            EntityField field = setField(entityForm, request, Integer.parseInt(id) , errors);
            
            if (isEdit) {
            	
	           ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
	           catalogAPI.saveField(entityId.intValue(), field);
            } 
            //Es un campo de una entidad que se esta creando, como puede editarse, hay que volver a validar
            else{
            	ClientContext cct = session.getClientContext();
                // Validar la precisión
                if (field.getPrecision() > field.getSize()) {
                  	
                  	errors.add("property(PRECISION)", new ActionMessage("form.entity.field.error.precision"));
                  	addErrors(request, errors);
                  	
                  	return mapping.findForward("field");
                }
                
                // Validar los rangos
                if (StringUtils.isNotEmpty(entityForm.getProperty("RANGOMIN"))) {
             	   
             	   field.setRangoMin(RangoValidator.transformDecimalToDouble(entityForm.getProperty("RANGOMIN"), cct.getLocale()));
                }
                if (StringUtils.isNotEmpty(entityForm.getProperty("RANGOMAX"))) {
             	   
             	   field.setRangoMax(RangoValidator.transformDecimalToDouble(entityForm.getProperty("RANGOMAX"), cct.getLocale()));
             	   
             	   if ((field.getRangoMin() != Integer.MIN_VALUE) && 
             		   (field.getRangoMin() > field.getRangoMax())) {
             		   
                  		errors.add("property(RANGOMAX)", new ActionMessage("form.entity.field.error.rango"));
                      	addErrors(request, errors);
                      	
                      	return mapping.findForward("field");
             	   }
                }
            }
            // Actualizar la definición de la entidad
            entityDef.saveField(field);
        
            return mapping.findForward("fields");
        }
        else {
        	
            saveErrors(request, errors);
            return mapping.findForward("field");
        }
    }
    
    
    
    private EntityField setField(EntityForm entityForm, HttpServletRequest request, int id,ActionMessages errors){
    	 EntityField field = new EntityField();
    	 int tam=setTamanyoCampo(Integer.parseInt((entityForm).getProperty("TYPE").toString()));
         //En el caso de ser un tipo ficticio el tamaño se informa por código
         if(tam!=0){
         	entityForm.setProperty("SIZE",(entityForm).getProperty("TYPE") );
         }

         field.setId(id);
         field.setLogicalName(entityForm.getProperty("LOGICALNAME"));
         field.setMultivalue(entityForm.getProperty("MULTIVALUE"));
         field.setPhysicalName(entityForm.getProperty("PHYSICALNAME"));
         field.setDescripcion(entityForm.getProperty("DESCRIPCION"));
         if("on".equalsIgnoreCase(entityForm.getProperty("DOCUMENTARYSEARCH"))){
         field.setDocumentarySearch((new Boolean (true)).booleanValue());
         }
         else{
         	field.setDocumentarySearch((new Boolean (false)).booleanValue());
         }
         	
        
        field.setType(InternalDataType.getInstance(TypeConverter.parseInt(entityForm.getProperty("TYPE"),InternalDataType.SHORT_TEXT.getId())));
        
        if (field.isMultivalue()){
        	MultivalueTable.getInstance().setSize(field);
        	MultivalueTable.getInstance().setPrecision(field);
        }else{
        
	        if (StringUtils.isNotEmpty(entityForm.getProperty("SIZE"))) {
	        	field.setSize(Integer.parseInt(entityForm.getProperty("SIZE")));
	        }
	        
	        if(tam!=0){
	        	field.setSize(tam);
	        }
	    
	        if (StringUtils.isNotEmpty(entityForm.getProperty("PRECISION"))) {
	        	field.setPrecision(Integer.parseInt(entityForm.getProperty("PRECISION")));
	        }
	        
	        // La precisión no puede ser mayor que el tamaño
	        if (field.getPrecision() > field.getSize()) {
	        	
	        	errors.add("property(PRECISION)", new ActionMessage("form.entity.field.error.precision"));
	        	addErrors(request, errors);
	        }
        }
        
        return field;
   }
    
   public ActionForward addField(ActionMapping mapping, 
		   						 ActionForm form,
           						 HttpServletRequest request, 
           						 HttpServletResponse response,
           						 SessionAPI session, 
           						 Integer entityId, 
           						 EntityDef entityDef, 
           						 boolean isEdit) throws ISPACException {

	   ClientContext cct = session.getClientContext();
	   
       EntityForm entityForm = (EntityForm) form;
       
       // Validar el formulario
       entityForm.setEntityAppName("CreateCTEntityField");
       
       //ActionMessages errors = entityForm.validate(mapping, request);
       ActionMessages errors = validateAddField(mapping, form, request, entityDef, session);

       if (errors.isEmpty()) {
       
           // Establecer la definición del campo
           EntityField field = setField(entityForm, request, entityDef.getMaxId() + 1, errors);
           
           // Validar la precisión
           if (field.getPrecision() > field.getSize()) {
             	
             	errors.add("property(PRECISION)", new ActionMessage("form.entity.field.error.precision"));
             	addErrors(request, errors);
             	
             	return mapping.findForward("field");
           }
           
           // Validar los rangos
           if (StringUtils.isNotEmpty(entityForm.getProperty("RANGOMIN"))) {
        	   
        	   field.setRangoMin(RangoValidator.transformDecimalToDouble(entityForm.getProperty("RANGOMIN"), cct.getLocale()));
           }
           if (StringUtils.isNotEmpty(entityForm.getProperty("RANGOMAX"))) {
        	   
        	   field.setRangoMax(RangoValidator.transformDecimalToDouble(entityForm.getProperty("RANGOMAX"), cct.getLocale()));
        	   
        	   if ((field.getRangoMin() != Integer.MIN_VALUE) && 
        		   (field.getRangoMin() > field.getRangoMax())) {
        		   
             		errors.add("property(RANGOMAX)", new ActionMessage("form.entity.field.error.rango"));
                 	addErrors(request, errors);
                 	
                 	return mapping.findForward("field");
        	   }
           }
           
           if (isEdit) {
        	   
               ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();         
               catalogAPI.addField(entityId.intValue(), field, true);
               
           }
           
           // Actualizar la definición de la entidad
           entityDef.addField(field);
       
           return mapping.findForward("fields");
       }
       else {
           saveErrors(request, errors);
           
           return mapping.findForward("field");
       }
   }    
    
    public ActionForward deleteItems(ActionMapping mapping, 
    								 ActionForm form,
    								 HttpServletRequest request, 
    								 HttpServletResponse response,
    								 SessionAPI session, 
    								 Integer entityId, 
    								 EntityDef entityDef, 
    								 boolean isEdit) throws ISPACException {
    	
        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        EntityForm entityForm = (EntityForm) form;

        List items = null;
        String actionForward = null;
        boolean isDeleteFields = false, isDeleteIndexes = false, isDeleteValidations = false;

        // Obtener la lista de objetos        
        String itemName = entityForm.getProperty("ITEMS");
        
        // Campos
        if ("FIELDS".equals(itemName)) {
            items = entityDef.getFields();
            actionForward = "fields";
            isDeleteFields = true;
        }
        // Índices
        else if ("INDEXES".equals(itemName)) {
            items = entityDef.getIndexes();
            actionForward = "indexes";
            isDeleteIndexes = true;
        }
        // Validaciones
        else if ("VALIDATIONS".equals(itemName)) {
            items = entityDef.getValidations();
            actionForward = "validations";
            isDeleteValidations = true;
        }
        
        // Eliminar los campos seleccionados
        String [] multibox = entityForm.getMultibox();
        if (!ArrayUtils.isEmpty(multibox) && !CollectionUtils.isEmpty(items)) {
        
          for(int i=items.size()-1; i>=0; i--){
            	
            	XmlObject xmlObject = (XmlObject) items.get(i);
            	
            	// Comprobar si el objeto está entre los seleccionados para eliminar
                if (ArrayUtils.contains(multibox, String.valueOf(xmlObject.getKey()))) {
                	
                	// Modificación de entidad
                    if (isEdit) {
                    	
                        if (isDeleteFields){
                        	EntityDef entityDefTemp = catalogAPI.dropField(entityId.intValue(), (EntityField) xmlObject);
                        	entityDef.setFields(entityDefTemp.getFields());
                        	entityDef.setIndexes(entityDefTemp.getIndexes());
                        	entityDef.setValidations(entityDefTemp.getValidations());
                        
                        }
                        else if (isDeleteIndexes){
                            entityDef.setIndexes(catalogAPI.dropIndex(entityId.intValue(), (EntityIndex) xmlObject).getIndexes());
                        }
                        else if (isDeleteValidations){
                        	entityDef.setValidations(catalogAPI.dropValidation(entityId.intValue(), (EntityValidation) xmlObject).getValidations());
                        }
                       
                    }
                    else {
                        
                    	if (isDeleteFields) {
                    	     entityDef=catalogAPI.dropFieldTemporal( (EntityField) xmlObject, entityDef);
                    	     request.getSession().setAttribute("WIZARD_ENTITY_DEF", entityDef);	
                    	}
                    	   else if (isDeleteIndexes){
                    		   entityDef.removeIndex((EntityIndex) xmlObject);
                              
                           }
                           else if (isDeleteValidations){
                        	   entityDef.removeValidation((EntityValidation) xmlObject);
                        	   
                           }
                    	
                    	
                    }
                  
                   
                }
            }
        }

        return mapping.findForward(actionForward);
    }
    
}