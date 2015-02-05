package ieci.tdw.ispac.ispaccatalog.action.entities;
 
import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.entity.EntityTableManager;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.wizard.WizardButton;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Asistente para la creación de entidades.
 *
 */
public class CreateEntityWizardAction extends EntityManageAction {

	private static final int STEP_COUNT = 4;
    private static final int WIZARDTBLVLD_STEP_COUNT = 1;
	private static final String WIZARD_ENTITY_BEAN_KEY	= "WIZARD_ENTITY_BEAN";
	private static final String WIZARD_ENTITY_DEF_KEY	= "WIZARD_ENTITY_DEF";
	private static final String WIZARD_TITLE_KEY 		= "WIZARD_TITLE";
	private static final String WIZARD_TITLE_KEY_KEY	= "WIZARD_TITLE_KEY";
	private static final String WIZARD_SUBTITLE_KEY 	= "WIZARD_SUBTITLE";
	private static final String WIZARD_SUBTITLE_KEY_KEY	= "WIZARD_SUBTITLE_KEY";
	private static final String WIZARD_BUTTONBAR_KEY 	= "WIZARD_BUTTONBAR";
    private static final String INDEX_FIELD_LIST	    = "INDEX_FIELD_LIST";
    private static final String TIPO_TBLVLD 			= "TIPO_TBLVLD";
	
	
	int getStepCount(HttpServletRequest request, SessionAPI session) throws ISPACException{
        return isValidationTableWizard(request, session)?WIZARDTBLVLD_STEP_COUNT:STEP_COUNT;
    }
    
    boolean isEntityWizard(HttpServletRequest request, SessionAPI session) throws ISPACException{
        return !EntityType.isValidationTableType(getEntityTypeId(request, session));
    }

    boolean isValidationTableWizard(HttpServletRequest request, SessionAPI session) throws ISPACException{
        return EntityType.isValidationTableType(getEntityTypeId(request, session));
    }
    
	/**
	 * Inicia el asistente de creación de entidades.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

		ItemBean entityBean = createEntityBean(request, session);
		entityBean.setProperty("TIPO", 
				new Integer(EntityType.PROCESS_ENTITY_TYPE.getId()));

		createEntityDef(request, session, EntityType.PROCESS_ENTITY_TYPE.getId());
		
		return mapping.findForward("general");
	}

    public ActionForward initTblVal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

 		ItemBean entityBean = createEntityBean(request, session);
        //de momento tabla de validacion simple
        entityBean.setProperty("TIPO", new Integer(EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId()));
        entityBean.setProperty("TIPO_VALIDACION", Boolean.TRUE );

        createEntityDef(request, session, EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId());
        
        // No editable
        // EntityDef entityDef = createEntityDef(request, session);
        // entityDef.setEditable(false);
        // TODO Es editable cuando se pueden añadir o eliminar valores de la lista??
        
        return mapping.findForward("general");
    }
    
    /**
     * Guarda los campos de una entidad
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

    	// Información de la definición de la entidad
    	EntityDef entityDef = getEntityDef(request, session);
    	
    	return saveField(mapping, form, request, response, session, null,
                entityDef, false);
	}
    

	/**
	 * Muestra el formulario general de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward general(ActionMapping mapping, 
    							 ActionForm form,
    							 HttpServletRequest request, 
    							 HttpServletResponse response,
    							 SessionAPI session) throws ISPACException {
    	
    	ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        EntityTableManager entityTableManager = new EntityTableManager();
    	
    	ActionMessages errors = new ActionMessages();
    	
    	EntityForm entityForm = (EntityForm) form;
    	ItemBean entityBean = getEntityBean(request, session);
    	
    	String forward = request.getParameter("forward");
    	if (("next".equals(forward)) && 
    		(getAppErrors(request) == null)) {
    		
			// Validar el formulario
			entityForm.setEntityAppName("CreateCTEntity");
			errors = entityForm.validate(mapping, request);
			if(errors.isEmpty()) {
				
		    	// Entidad de proceso
		    	if (getEntityTypeId(request, session) == EntityType.PROCESS_ENTITY_TYPE.getId()) {
				
					String nameTable = entityForm.getProperty("SPAC_CT_ENTIDADES:NOMBRE").toUpperCase();
					
			    	// Comprobar si la tabla ya está asociada a una entidad
			    	int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, "WHERE NOMBRE = '" + nameTable + "'");
			    	if (count > 0) {
			    		
			            errors.add("property(SPAC_CT_ENTIDADES:NOMBRE)", new ActionMessage("entity.error.alreadyExists"));
			            addErrors(request, errors);
			    	}
		        	// Comprobar si la tabla ya existe
			    	else if (entityTableManager.isTableCreated(cct.getConnection(), entityForm.getProperty("SPAC_CT_ENTIDADES:NOMBRE"))) {
		    	        
		        		errors.add("property(SPAC_CT_ENTIDADES:NOMBRE)", new ActionMessage("entity.error.tablaFisicaDeLaEntidadYaExiste"));
		    	        addErrors(request, errors);
		        	}
		        	else {
		        		// Guardar la información general de la entidad de proceso
		        		entityForm.processItemBean(entityBean);
		        		
						// Ir a la definición de campos
						return mapping.findForward("fields");
		        	}
				}
				// Tablas de validación
				else {
	        		// Guardar la información general de la tabla de validación
	        		entityForm.processItemBean(entityBean);
	        		entityBean.setProperty("TIPO", new Integer(entityForm.getProperty("SPAC_CT_ENTIDADES:TIPO")));
						
					// Tabla de validación ya existente
					if (StringUtils.isNotBlank(entityForm.getProperty("TBL_EXISTENTE"))) {
						entityBean.setProperty("TBL_EXISTENTE", Boolean.TRUE);
					}
					else {
						entityBean.setProperty("TBL_EXISTENTE", null);
					}
					
					// Finalizar
					return mapping.findForward("finalize");
				}
			}
			else {
				saveErrors(request, errors);
			}
    	}			
    	else  {
    		// Establecer la información general al volver
			entityForm.setItemBean(entityBean);
			entityForm.setProperty("ETIQUETA", entityBean.getString("ETIQUETA"));
			
			if (getEntityTypeId(request, session) != EntityType.PROCESS_ENTITY_TYPE.getId()) {

	        	entityForm.setProperty("TAM_VALOR", entityBean.getString("TAM_VALOR"));
	        	entityForm.setProperty("TAM_SUSTITUTO", entityBean.getString("TAM_SUSTITUTO"));
	        	entityForm.setProperty("TBL_EXISTENTE", entityBean.getString("TBL_EXISTENTE"));
			}
    	}	

    	// Procesar el tipo de entidad para generar la vista
    	int intTipo = getEntityTypeId(request, session);
    	
		if (StringUtils.isBlank(entityForm.getProperty("SPAC_CT_ENTIDADES:TIPO"))) {
			entityForm.setProperty("SPAC_CT_ENTIDADES:TIPO", String.valueOf(intTipo));	
		}
        request.setAttribute(TIPO_TBLVLD, new Boolean(EntityType.isValidationTableType(intTipo)));
        
        if (EntityType.isValidationTableType(intTipo)) {
        	
        	// Titulos y botones cuando es una tabla de validación
        	request.setAttribute(WIZARD_TITLE_KEY, getMessage(request, cct.getLocale(), "validationTable.wizard.title"));
        	request.setAttribute(WIZARD_SUBTITLE_KEY, getMessage(request, cct.getLocale(), "validationTable.wizard.title.step1.subtitle"));
            request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
                    new WizardButton("wizard.button.finalize", composeJScriptIspacSubmitURL(request, "/showEntityWizard.do?method=general&forward=next")),
                    new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL(request, "/showCTValueTablesList.do?method=cancel"))
            });
        }
        else {
        	// Titulos y botones cuando es una entidad de proceso
            request.setAttribute(WIZARD_TITLE_KEY, getWizardTitle(1, request, session));
            request.setAttribute(WIZARD_SUBTITLE_KEY, getWizardSubtitle(1, request, session));
        	request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
        			new WizardButton("wizard.button.prev", false),
        			new WizardButton("wizard.button.next", composeJScriptIspacSubmitURL(request,"/showEntityWizard.do?method=general&forward=next")),
        			new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL(request, "/showEntityWizard.do?method=cancel"))
        	});
        }

       	return mapping.findForward("general_page");
	}
    
	/**
	 * Muestra el formulario de los campos de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward fields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

    	EntityDef entityDef = getEntityDef(request, session);
    	String forward = request.getParameter("forward");
    	
    	if ("prev".equals(forward)) {
    		
    		return mapping.findForward("general");
    		
    	} else if ("next".equals(forward)) {

    		if (!CollectionUtils.isEmpty(entityDef.getFields())) {
    			return mapping.findForward("indexes");
    		} else {
	    		ActionMessages errors = new ActionMessages();
	    		errors.add(ActionMessages.GLOBAL_MESSAGE, 
	    				new ActionMessage("form.entity.fields.error.empty"));
    			addErrors(request, errors);
    		}
    	}

        request.setAttribute(WIZARD_TITLE_KEY, getWizardTitle(2, request, session));
        request.setAttribute(WIZARD_SUBTITLE_KEY, getWizardSubtitle(2, request, session));
        request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
                new WizardButton("wizard.button.prev", composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=fields&forward=prev")),
                new WizardButton("wizard.button.next", composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=fields&forward=next")),
                new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=cancel"))
        });

        if (EntityType.isValidationTableType(getEntityTypeId(request, session))) {
            request.setAttribute(FIELDS_MODIFICABLES, Boolean.FALSE);
        }else
            request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);
       	return prepareAndRedirectToFieldsPage(mapping, form, request, response,
                session, entityDef, null,getEntityTypeId(request, session));
	}


	/**
	 * Muestra el formulario con la información de un campos de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward field(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
        
        return prepareAndRedirectToFieldPage(mapping, form, request, response,
                session, getWizardTitle(2, request, session),null, null);
    }
    /**
     * Muestra los campos de una entidad
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param session
     * @return
     * @throws ISPACException
     */
    public ActionForward showFieldEntity(ActionMapping mapping, 
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws ISPACException {

 			
 			int idField =Integer.parseInt(request.getParameter("idField"));
 			EntityDef entityDef = getEntityDef(request, session);
 			 entityDef.getFields();
 			 
 			 for(int i=0; i<entityDef.getFields().size(); i++){
 				 EntityField field= (EntityField) entityDef.getFields().get(i);
 				 if(field.getId()==idField){
 					 
 					 
 					
 					 return prepareAndRedirectToFieldPage(mapping, form, request, response,
 		 	                session, getWizardTitle(2, request, session),field, null);
 					
 					 
 				 }
 			 }
 			 return prepareAndRedirectToFieldPage(mapping, form, request, response,
	 	                session, getWizardTitle(2, request, session),null, null);
 			
 		
}
    

	/**
	 * Añade el campo a la Entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward addField(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

    	// Información de la definición de la entidad
    	EntityDef entityDef = getEntityDef(request, session);
    	return addField(mapping, form, request, response, session, null,
                entityDef, false);
	}

	/**
	 * Muestra el formulario de los índices de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward indexes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

    	String forward = request.getParameter("forward");
    	
    	if ("prev".equals(forward)) {

    		return mapping.findForward("fields");
    		
    	} else if ("next".equals(forward)) {

    		return mapping.findForward("validations");
    	}
    		
		request.setAttribute(WIZARD_TITLE_KEY, getWizardTitle(3, request, session));
		request.setAttribute(WIZARD_SUBTITLE_KEY, getWizardSubtitle(3, request, session));
    	request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
    			new WizardButton("wizard.button.prev", composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=indexes&forward=prev")),
    			new WizardButton("wizard.button.next", composeJScriptIspacSubmitURL( request,"/showEntityWizard.do?method=indexes&forward=next")),
    			new WizardButton("wizard.button.cancel", composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=cancel"))
    	});
    	
    	request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);

        return prepareAndRedirectToIndexesPage(getEntityDef(request, session), request, mapping,"/formatters/entities/indexlistformatterInCreateWizard.xml");
	}
	
	/**
	 * Muestra el formulario con la información de un índice de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward newIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
    	
    	// Inicializar la lista de campos de sesión 
    	request.getSession().removeAttribute(INDEX_FIELD_LIST);

    	return index(mapping, form, request, response, session, getWizardTitle(3, request, session));
    }

    /**
     * Baja el campo del índice una posición.
     * @param mapping El ActionMapping utilizado para seleccionar esta instancia
     * @param form El ActionForm bean (opcional) para esta petición
     * @param request La petición HTTP que se está procesando
     * @param response La respuesta HTTP que se está creando
     * @param session Información de la sesión del usuario
     * @return La redirección a la que se va a transferir el control.
     * @throws ISPACException si ocurre algún error.
     */
    public ActionForward indexFieldDown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        List fieldList = (List) request.getSession()
            .getAttribute("INDEX_FIELD_LIST");

        String pos = request.getParameter("pos");
        
        if (StringUtils.isNotBlank(pos) && !CollectionUtils.isEmpty(fieldList)) {
            
            int ipos = TypeConverter.parseInt(pos);
            if (ipos < fieldList.size()) {
                EntityField currentField = (EntityField) fieldList.get(ipos-1);
                fieldList.remove(ipos-1);
                fieldList.add(ipos, currentField);
            }
        }
        
        return mapping.findForward("index");
    }

    public ActionForward addIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        EntityDef entityDef = getEntityDef(request, session);
        return logicAddIndex(mapping, form, request, response, session, null,
                entityDef, false);
    }

    
	/**
	 * Muestra el formulario de las validaciones de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward validations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

    	String forward = request.getParameter("forward");

    	if ("prev".equals(forward)) {

    		return mapping.findForward("indexes");

    	} else if ("next".equals(forward)) {

    		return mapping.findForward("finalize");

    	} else {
    		
			request.setAttribute(WIZARD_TITLE_KEY, getWizardTitle(4, request, session));
			request.setAttribute(WIZARD_SUBTITLE_KEY, getWizardSubtitle(4, request, session));
	    	request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
	    			new WizardButton("wizard.button.prev",  composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=validations&forward=prev")),
	    			new WizardButton("wizard.button.finalize",  composeJScriptIspacSubmitURL( request,"/showEntityWizard.do?method=validations&forward=next")),
	    			new WizardButton("wizard.button.cancel",  composeJScriptIspacSubmitURL( request, "/showEntityWizard.do?method=cancel"))
	    	});
	    	
	    	request.setAttribute(FIELDS_MODIFICABLES, Boolean.TRUE);

	    	EntityDef entityDef = getEntityDef(request, session);
            return prepareAndRedirectoToValidationsPage(entityDef, request, mapping, session, null);
    	}
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
			SessionAPI session) throws ISPACException {

    	// Información de la definición de la entidad
    	EntityDef entityDef = getEntityDef(request, session);
        return addValidation(mapping, form, request, response, session,
                null, entityDef, false);
	}
    
    public ActionForward validation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        return validation(mapping, form, request, response, session,
                getEntityDef(request, session), getWizardTitle(4, request, session), null);
    }

    public ActionForward initIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        String titulo = getWizardTitle(3, request, session);
        request.getSession().removeAttribute(INDEX_FIELD_LIST);
        return index(mapping, form, request, response, session, titulo);
    }

    public ActionForward index(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        return index(mapping, form, request, response, session, getWizardTitle(3, request, session));
    }
    
	/**
	 * Elimina los ítems seleccionados.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward deleteItems(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        EntityDef entityDef = getEntityDef(request, session);
        return deleteItems(mapping, form, request, response, session,
                null, entityDef, false);
    	
    }

    int getEntityTypeId(HttpServletRequest request, SessionAPI session) throws ISPACException{
        String tipo = getEntityBean(request, session).getString("TIPO");
        int intTipo = Integer.parseInt(tipo);
        return intTipo;
    }


	/**
	 * Crea la entidad y muestra una pantalla de resultado.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward finalize(ActionMapping mapping, 
    							  ActionForm form,
    							  HttpServletRequest request, 
    							  HttpServletResponse response,
    							  SessionAPI session) throws ISPACException {
    	
        IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Obtener la información de la entidad a crear
    	ItemBean entityBean = getEntityBean(request, session);
        EntityDef entityDefinition = getEntityDef(request, session);
        
        String entityPhysicalName = entityBean.getString("SPAC_CT_ENTIDADES:NOMBRE").toUpperCase();
        String logicalName =  entityBean.getString("ETIQUETA");
        String description = entityBean.getString("SPAC_CT_ENTIDADES:DESCRIPCION");
        EntityType tipo = EntityType.getInstance(getEntityTypeId(request, session));
    	
        // Tabla de validación ya existente
        boolean tblExist = false;
        if(entityBean.getProperty("TBL_EXISTENTE") != null) {
        	tblExist = true;
        	// No se puede modificar la lista de valores
        	entityDefinition.setEditable(false);
        }
        
        // Crear la entidad
        catalogAPI.createEntity(tipo,
        						logicalName,
        						entityPhysicalName, description,
				        		entityDefinition, 
				        		TypeConverter.parseInt((String)entityBean.getProperty("TAM_VALOR"), -1),
				        		TypeConverter.parseInt((String)entityBean.getProperty("TAM_SUSTITUTO"), -1),
				        		tblExist);
        
        
        
        request.setAttribute(WIZARD_SUBTITLE_KEY_KEY, "entity.wizard.create");
    	request.setAttribute(WIZARD_TITLE_KEY_KEY, "entity.wizard.title");
    	request.setAttribute(WIZARD_BUTTONBAR_KEY, new WizardButton[] {
    			new WizardButton("wizard.button.exit", 
    					request.getContextPath() + "/showCTEntitiesList.do")
    	});

        String forward = null;
        if (EntityType.isValidationTableType(getEntityTypeId(request, session))){
        	forward = "wizardTblVldSuccessPage";
        }
        else{
        	forward = "wizardEntitiesSuccessPage";
        }

        // Eliminar variables de sesión
        cleanSession(request);
        
		return mapping.findForward(forward);
	}

	/**
	 * Cancela la creación de la entidad y cierra el asistente.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response,
				SessionAPI session) {
	    
    	// Eliminar variables de sesión
    	cleanSession(request);

    	return mapping.findForward("wizardCancelPage");
	}

	/**
	 * Obtiene de la sesión la información recogida por el asistente. 
	 * @param request La petición HTTP que se está procesando
	 * @param session Información de la sesión del usuario
	 * @return Información recogida por el asistente.
	 * @throws ISPACException si ocurre algún error.
	 */
    private ItemBean getEntityBean(HttpServletRequest request, 
			SessionAPI session) throws ISPACException {
		
		ItemBean entityBean = (ItemBean) request.getSession()
			.getAttribute(WIZARD_ENTITY_BEAN_KEY);

		if (entityBean == null) {
			
			// Crear el bean 
			entityBean = createEntityBean(request, session);
		}
		
		return entityBean;
	}
    
    private ItemBean createEntityBean(HttpServletRequest request, 
			SessionAPI session) throws ISPACException {

    	IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		
		CompositeItem entity = new CompositeItem("SPAC_CT_ENTIDADES:ID");
		entity.addItem(entitiesAPI.getEntity("SPAC_CT_ENTIDADES"),"SPAC_CT_ENTIDADES:");
		
		/*
		entity.addItem(entitiesAPI.getEntity("SPAC_CT_APLICACIONES"), "SPAC_CT_APLICACIONES:");
		*/
		
		ItemBean entityBean = new ItemBean(entity); 
//		propiedad para recoger el valor etiqueta que ira a la tabla de recursos de la entidad
		entityBean.setProperty("ETIQUETA", new String());
		request.getSession().setAttribute(WIZARD_ENTITY_BEAN_KEY, 
				entityBean);
		
		return entityBean;
    }
	
    private EntityDef createEntityDef(HttpServletRequest request, 
			SessionAPI session, int type) {
    	EntityDef entityDef = new EntityDef(type);
		request.getSession().setAttribute(WIZARD_ENTITY_DEF_KEY, entityDef);
		return entityDef;
    }
    
    private EntityDef getEntityDef(HttpServletRequest request,
    		SessionAPI session) {
    	
    	EntityDef entityDef = (EntityDef) request.getSession()
    		.getAttribute(WIZARD_ENTITY_DEF_KEY);
    	
    	if (entityDef == null) {
    		entityDef = createEntityDef(request, session, -1);
    	}
    	
    	return entityDef;
    }
    
    private void cleanSession(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if (session != null) {
    		session.removeAttribute(WIZARD_ENTITY_BEAN_KEY);
    		session.removeAttribute(WIZARD_ENTITY_DEF_KEY);
    	}
    }
    
	/**
	 * Obtiene el título del asistente
	 * @param currentStep Paso actual del asistente.
	 * @return Título del asistente
	 * @throws ISPACException 
	 */
	private String getWizardTitle(int currentStep, HttpServletRequest request, SessionAPI session) throws ISPACException {
		StringBuffer title = new StringBuffer();

		ClientContext cct = session.getClientContext();
		
        title.append(getMessage(request, cct.getLocale(), "entity.wizard.title"));
        
        String entityPhysicalName = getEntityBean(request, session).getString("ETIQUETA");
        if (!StringUtils.isEmpty(entityPhysicalName)) {

        	title.append(" '")
        		 .append(entityPhysicalName.toUpperCase())
        		 .append("' ");
        }
        
		if ( (currentStep > 0) && (currentStep <= STEP_COUNT) ) {
            title.append(" - ");
    			title.append(getMessage(request, cct.getLocale(), "wizard.steps.title", 
    					new String [] { 
    						String.valueOf(currentStep),
    						String.valueOf(getStepCount(request, session)) }));
            title.append(" - ");
			title.append(getMessage(request, cct.getLocale(), new StringBuffer()
					.append("entity.wizard.title.step")
					.append(currentStep).toString()));
		}

		
		return title.toString();
	}

	/**
	 * Obtiene el subtítulo del asistente
	 * @param currentStep Paso actual del asistente.
	 * @param request La petición HTTP que se está procesando
	 * @return Subtítulo del asistente
	 */
	private String getWizardSubtitle(int currentStep, HttpServletRequest request, SessionAPI session) {
		ClientContext cct = session.getClientContext();
		return getMessage(request, cct.getLocale(), new StringBuffer()
				.append("entity.wizard.title.step")
				.append(currentStep)
				.append(".subtitle")
				.toString());
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
        
        EntityDef entityDef = getEntityDef(request, session);
        
        return prepareAndRedirectToIndexFieldsPage(entityDef, request, mapping, null, session);
    }

    public ActionForward addIndexField(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request, session);
        
        return addIndexField(mapping, form, request, response, session, entityDef, null);

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

    public ActionForward showIndex(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
        
        EntityDef entityDef = getEntityDef(request, session);
        int indexId = Integer.parseInt(request.getParameter("indexId"));
        
        return showIndex(mapping, form, request, response, session,
                new Integer(indexId), entityDef, null);
        
    }
    
}