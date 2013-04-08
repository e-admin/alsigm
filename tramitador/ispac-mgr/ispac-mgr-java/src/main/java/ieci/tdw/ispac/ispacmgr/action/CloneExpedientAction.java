package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.bean.scheme.RegEntityBean;
import ieci.tdw.ispac.ispacmgr.bean.scheme.SchemeEntityBean;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CloneExpedientAction extends BaseDispatchAction {

	public ActionForward enter(ActionMapping mapping,
							   ActionForm form,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   SessionAPI session) throws Exception {

		request.getSession().removeAttribute("SchemeEntityList");
		request.getSession().removeAttribute("EntityFieldsMap");
		request.getSession().removeAttribute("EntityMod");

	    return mapping.findForward("enter");
	}

	public ActionForward viewEntities(ActionMapping mapping,
									  ActionForm form,
									  HttpServletRequest request,
									  HttpServletResponse response,
									  SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
	    IState state = managerAPI.currentState(getStateticket(request));

	    // Cargar los datos del esquema
	    SchemeMgr.loadScheme(mapping, request, session,state);

	    /*Comprobar que la entidad expediente e Intervinientes esten en el esquema
	     * Si no esta es que están a no visible , pero el usuario si puede hacer uso de campos
	     * de estas entidades en otra entidad, es por eso por lo que tenemos que añadir ambas entidades
	     */

		List schemebeanlist = (List) request.getAttribute("SchemeList");
		boolean entidadExpediente=false;
		boolean entidadParticipantes=false;
		 for (Iterator ite = schemebeanlist.iterator(); ite.hasNext() ;)
	        {
	            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
	            int entityId = scheEntBean.getItem().getKeyInt();
	            if (entityId == ISPACEntities.DT_ID_EXPEDIENTES){
	            		entidadExpediente=true;
	            	}
	            	else if (entityId == ISPACEntities.DT_ID_INTERVINIENTES){
	            			entidadParticipantes=true;
	            	}
	       }

		if(! entidadParticipantes){


			 addEntity(ISPACEntities.DT_ID_INTERVINIENTES,schemebeanlist,  session);

		 }

		if(! entidadExpediente){
			 addEntity(ISPACEntities.DT_ID_EXPEDIENTES,schemebeanlist, session);

		 }
		request.setAttribute("SchemeList", schemebeanlist);

        // Cargar el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);

    	// Establecer el menu
        request.setAttribute("menus", MenuFactory.getCloneExpedientMenu(cct, state, getResources(request)));

	    return mapping.findForward("viewEntities");
	}

	public ActionForward addEntity(ActionMapping mapping,
								   ActionForm form,
								   HttpServletRequest request,
								   HttpServletResponse response,
								   SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
	    IState state = managerAPI.currentState(getStateticket(request));

		List schemeEntityList = (List) request.getSession().getAttribute("SchemeEntityList");
		if (schemeEntityList == null) {

			schemeEntityList = new ArrayList();
			request.getSession().setAttribute("SchemeEntityList", schemeEntityList);
		}

		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		EntityForm entityForm = (EntityForm) form;

		if (!isSelectedEntity(mapping, request, session, managerAPI, state, entityForm.getEntity(), schemeEntityList)) {

			// Entidad seleccionada
			ArrayList entityList = new ArrayList();
			int entity = Integer.parseInt(entityForm.getEntity());
			IItem ctentity = entitiesAPI.getCatalogEntity(entity);
			entityList.add(ctentity);

			// Convertir a scheme
			IItemCollection entityItemcol = new ListCollection(entityList);
			List schemeList = CollectionBean.getBeanList(SchemeEntityBean.class, entityItemcol);

			// Obtener la etiqueta de la entidad
			SchemeEntityBean schemeEntityBean = (SchemeEntityBean) schemeList.get(0);
			schemeEntityBean.setProperty("TAB_LABEL", entitiesAPI.getEntityResourceValue(entity, cct.getAppLanguage(), schemeEntityBean.getItem().getString("NOMBRE")));

			// Obtener el número de registros
			IItemCollection itemcol = entitiesAPI.getEntities(entity, state.getNumexp());
			if (itemcol.next()) {

				schemeEntityBean.setRegs(itemcol.toList());
				schemeEntityList.addAll(schemeList);

			    return mapping.findForward("addEntity");
			}
			else {
				// El expediente no tiene registros de la entidad seleccinada
				ActionMessages errors = new ActionMessages();
				errors.add("", new ActionMessage("select.entityToClone.error.noRegister", new Object[] {state.getNumexp()}));
				saveErrors(request, errors);
			}
		}
		else {
			// El expediente no tiene registros de la entidad seleccinada
			ActionMessages errors = new ActionMessages();
			errors.add("", new ActionMessage("select.entityToClone.error.entitySelected", new Object[] {state.getNumexp()}));
			saveErrors(request, errors);
		}

		ActionForward action = mapping.findForward("notAddEntity");
		String path = action.getPath();

   		// Y se mantiene la ordenación de la lista de entidades
   		String displayTagOrderParams = getDisplayTagOrderParams(request);
   		if (!StringUtils.isEmpty(displayTagOrderParams)) {
   			path = path + "?" + displayTagOrderParams;
   		}
		return new ActionForward(action.getName(), path, false);
	}

	public ActionForward setEntityFields(ActionMapping mapping,
			   							 ActionForm form,
			   							 HttpServletRequest request,
			   							 HttpServletResponse response,
			   							 SessionAPI session) throws Exception {

		// IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();

        // Formulario asociado a la acción
        EntityForm defaultForm = (EntityForm) form;

		Map entityFieldsMap = (Map) request.getSession().getAttribute("EntityFieldsMap");
		if (entityFieldsMap == null) {

			entityFieldsMap = new HashMap();
			request.getSession().setAttribute("EntityFieldsMap", entityFieldsMap);
		}

		/*
		List entityFieldsToClone = null;

		// Establecer los campos a clonar de la entidad
		String[] idFieldsToClone = defaultForm.getMultibox();
		if (idFieldsToClone != null) {

			// Obtener la entidad
			int entityId = Integer.parseInt(defaultForm.getEntity());
			IItem entity = entitiesAPI.getCatalogEntity(entityId);

			// Obtener los campos de la definición de la entidad
			EntityDef entityDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));

			EntityField entityField;
			entityFieldsToClone = new ArrayList();
			for (int i = 0; i < idFieldsToClone.length; i++) {

				entityField = entityDef.findField(Integer.parseInt(idFieldsToClone[i]));
				if (entityField != null) {
					entityFieldsToClone.add(entityField);
				}
			}
		}

		// Guardar los campos a clonar para la entidad
		entityFieldsMap.put(defaultForm.getEntity(), entityFieldsToClone);
		*/
		entityFieldsMap.put(defaultForm.getEntity(), defaultForm.getMultibox());
		Map mapaEntidadesModificadas=null;
		if(request.getSession().getAttribute("EntityMapModify")==null){
			mapaEntidadesModificadas=new HashMap();
			mapaEntidadesModificadas.put(String.valueOf(defaultForm.getEntity()), "true");

		}
		else{
			mapaEntidadesModificadas=(Map)request.getSession().getAttribute("EntityFieldsMap");
			if(!mapaEntidadesModificadas.containsKey(String.valueOf(defaultForm.getEntity()))){
				mapaEntidadesModificadas.put(String.valueOf(defaultForm.getEntity()), "true");
			}
		}
		request.getSession().setAttribute("EntityFieldsMap",entityFieldsMap);
		request.getSession().setAttribute("EntityMapModify",mapaEntidadesModificadas);


		return mapping.findForward("setEntityFields");
	}

	public ActionForward deleteEntity(ActionMapping mapping,
			   						  ActionForm form,
			   						  HttpServletRequest request,
			   						  HttpServletResponse response,
			   						  SessionAPI session) throws Exception {

		List schemeEntityList = (List) request.getSession().getAttribute("SchemeEntityList");
		if ((schemeEntityList != null) &&
			(!schemeEntityList.isEmpty())) {

			int index = 0;
			String entityId = (String) request.getParameter("entityId");

        	Iterator it = schemeEntityList.iterator();
        	while (it.hasNext()) {

        		SchemeEntityBean schemeEntity = (SchemeEntityBean) it.next();
        		if (schemeEntity.getString("ID").equals(entityId)) {

        			schemeEntityList.remove(index);

        			// Eliminar los campos
        			Map entityFieldsMap = (Map) request.getSession().getAttribute("EntityFieldsMap");
        			if (entityFieldsMap != null) {
        				entityFieldsMap.remove(entityId);
        			}

        			break;
	        	}

        		index++;
	        }
		}

		return mapping.findForward("deleteEntity");
	}

	public ActionForward clone(ActionMapping mapping,
							   ActionForm form,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   SessionAPI session) throws Exception {

		// Número de veces a clonar el expediente
		int unidades = Integer.parseInt((String) request.getParameter("unidades"));

		ClientContext cct = session.getClientContext();

	    IInvesflowAPI invesflowAPI = session.getAPI();
	    IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();

		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
	    IState state = managerAPI.currentState(getStateticket(request));

        // Cargamor los datos del esquema
	    IScheme scheme = SchemeMgr.loadScheme(mapping, request, session, state);

        // Obtener las entidades del esquema junto con sus registros
        List schemeList = (List) request.getAttribute("SchemeList");

        // Obtener las entidades seleccionadas
        List schemeEntityList = (List) request.getSession().getAttribute("SchemeEntityList");

        // Obtener los campos a clonar de las entidades
		Map entityFieldsMap = (Map) request.getSession().getAttribute("EntityFieldsMap");

		if(entityFieldsMap==null){
			entityFieldsMap=new HashMap();
		}
		//Quiere decir que el usuario no ha modificado nada, entonces hay que cargar todos los campos
		Map entityMapModify=(Map)request.getSession().getAttribute("EntityMapModify");

		   for (Iterator ite = schemeList.iterator(); ite.hasNext();)
		     {

		            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
		            int entityId = scheEntBean.getItem().getKeyInt();
					ItemBean bean;
					// Obtener la entidad
					if(entityMapModify==null || !entityMapModify.containsKey(String.valueOf(entityId))) {
					IItem entity = entitiesAPI.getCatalogEntity(entityId);
					EntityDef entityDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
					List fieldList = CollectionBean.getBeanList(new ListCollection(entityDef.getFields()));
					String [] campos=new String[fieldList.size()];
					  for (int i = 0; i < fieldList.size(); i++) {

				        	bean = (ItemBean) fieldList.get(i);

				        	campos[i]=bean.getString("ID");
				        }


					  entityFieldsMap.put(String.valueOf(entityId), campos);
					}
		     }


//    	MULTIORGANIZACION EN UNA MISMA BBDD
//    	IItem organization = null;
//    	ISPACConfiguration configuration = ISPACConfiguration.getInstance();
// Comprobar si la multiorganización está activada
//		String multiOrganization = configuration.get(ISPACConfiguration.MULTIORGANIZATION);
//		if ((multiOrganization != null) &&
//			(multiOrganization.toUpperCase().equals("YES"))) {
//
//			// Organismo de conexión (cuando hay varios organismos tramitando)
//			organization = (IItem) request.getSession().getAttribute("organization");
//		}
//		else {
// Un único organismo tramitando
//	        Properties properties = new Properties();
//	        properties.add(new Property(0, "ID", Types.INTEGER));
//	        properties.add(new Property(1, "CODIGO", Types.VARCHAR));
//			organization = new GenericItem(properties, "ID");
//
//			organization.setKey(Integer.parseInt(configuration.get(ISPACConfiguration.ORGANIZATION_ID)));
//			organization.set("CODIGO", configuration.get(ISPACConfiguration.ORGANIZATION_CODE));
//		}

		// Obtener el código de procedimiento para el número de expediente
		IItem ctProcedure = entitiesAPI.getEntity(SpacEntities.SPAC_CT_PROCEDIMIENTOS, state.getPcdId());
		Map params = new HashMap();
		params.put("COD_PCD", ctProcedure.getString("COD_PCD"));

        // Lista de numeros de expediente creados
        List newExpedients = new ArrayList();

        // Error al clonar algún expediente
        int errorUnidades = 0;

        // Se clonan los registros de todas las entidades,
    	// excepto el de la entidad de expedientes que se hace de forma particular
		// y los tramites y documentos que no se clonan.
        for (int i = 0; i < unidades; i++) {

    		// Ejecución en un contexto transaccional
    		boolean bCommit = false;

            try {
        	    // La operación a realizar debe ser atómica
                cct.beginTX();

	    		// Crear el proceso del expediente
	            int processId = invesflowAPI.getTransactionAPI().createProcess(state.getPcdId(), params);

	    		// Obtener el proceso del expediente creado
	    		IProcess process = invesflowAPI.getProcess(processId);
	    		String newNumExp = process.getString("NUMEXP");
	    		IItem newExpedient = entitiesAPI.getExpedient(newNumExp);

	    		// Identificadores de catálogo de las entidades que se han clonado
	    		Map clonedCTEntityIds = new HashMap();

	    		// Registros de la entidades del esquema para clonarlos en el nuevo expediente
	        	for (Iterator iter = schemeList.iterator(); iter.hasNext();) {

					SchemeEntityBean schemeEntityBean = (SchemeEntityBean) iter.next();
					int entityId = schemeEntityBean.getItem().getKeyInt();

					// Si el registro es de la entidad expediente tiene un tratamiento especial
					if (entityId == SpacEntities.SPAC_EXPEDIENTES) {

						RegEntityBean regEntityBean = (RegEntityBean)schemeEntityBean.getRegs().get(0);

						// Lista de campos que se excluyen de la copia de datos
						List excludedList = new ArrayList();
						excludedList.add("ID");
						excludedList.add("NUMEXP");
						excludedList.add("IDPROCESO");
						excludedList.add("REFERENCIA_INTERNA");
						excludedList.add("FAPERTURA");

						// Campos a clonar
						String[] idFieldsToClone = null;
						if (entityFieldsMap != null) {
							idFieldsToClone = (String[]) entityFieldsMap.get(String.valueOf(entityId));
						}

						// Clonar el expediente
						entitiesAPI.copyRegEntityData(entityId, regEntityBean.getItem().getInt("SCHEME_ID"), newExpedient, excludedList, idFieldsToClone);
						clonedCTEntityIds.put(new Integer(entityId), null);

						// Clonar los registros de entidades secundarias en el formulario del expediente
						Map clonedSecondaryCtEntityIds = scheme.cloneEntityApp(state, entityId, newExpedient.getKeyInt(), newExpedient, entityFieldsMap, clonedCTEntityIds);

						clonedCTEntityIds.putAll(clonedSecondaryCtEntityIds);
					}
					// Si el registro es de la entidad de Tramites o Documentos, no se clonan
					else if (entityId != SpacEntities.SPAC_DT_TRAMITES && entityId != SpacEntities.SPAC_DT_DOCUMENTOS) {

						// Si existen registros de esta entidad asociados al nuevo expediente creado (mediante reglas asociadas al inicio del expediente)
						// se eliminan. Esto puede suceder cuando tengamos una regla al iniciar el expediente que se encarga de generar un registro de una entidad,
						// suele ser bastante común. Para evitar problemas lo eliminamos y se creara otro como resultado de la clonacion
						IItemCollection itemCol = entitiesAPI.getEntities(entityId, newNumExp, "");
						while(itemCol.next()) {
							itemCol.value().delete(cct);
						}

						Map clonedSecondaryCtEntityIds = null;

						List regs = schemeEntityBean.getRegs();
						for (Iterator iterator = regs.iterator(); iterator.hasNext();) {

							RegEntityBean regEntityBean = (RegEntityBean) iterator.next();
							IItem item = regEntityBean.getItem();
							// entitiesAPI.cloneRegEntity(entityId, item.getInt("SCHEME_ID"), newNumExp, idFieldsToClone);

							// Clonar el registro de la entidad y los registros de entidades secundarias en el formulario de la entidad
							clonedSecondaryCtEntityIds = scheme.cloneEntityApp(state, entityId, item.getInt("SCHEME_ID"), newExpedient, entityFieldsMap, null);
						}

						// Establecer la entidad principal y las secundarias como ya clonadas
						clonedCTEntityIds.put(new Integer(entityId), null);
						if (clonedSecondaryCtEntityIds != null) {
							clonedCTEntityIds.putAll(clonedSecondaryCtEntityIds);
						}
					}
				}

				// Clonar los registros de las entidades seleccionadas
	        	if ((schemeEntityList != null) &&
	        		(!schemeEntityList.isEmpty())) {

		        	for (Iterator iter = schemeEntityList.iterator(); iter.hasNext();) {

						SchemeEntityBean schemeEntityBean = (SchemeEntityBean) iter.next();
						int entityId = schemeEntityBean.getItem().getKeyInt();

						// Comprobar que la entidad no haya sido ya clonada
						// al ser una entidad secundaria de una de la principales
						if (!clonedCTEntityIds.containsKey(new Integer(entityId))) {

							/*
							String[] idFieldsToClone = null;
							if (entityFieldsMap != null) {
								idFieldsToClone = idFieldsToClone = (String[]) entityFieldsMap.get(String.valueOf(entityId));
							}
							*/

							// Si existen registros de esta entidad asociados al nuevo expediente creado (mediante reglas asociadas al inicio del expediente)
							// se eliminan. Esto puede suceder cuando tengamos una regla al iniciar el expediente que se encarga de generar un registro de una entidad,
							// suele ser bastante común. Para evitar problemas lo eliminamos y se creara otro como resultado de la clonacion
							IItemCollection itemCol = entitiesAPI.getEntities(entityId, newNumExp, "");
							while(itemCol.next()) {
								itemCol.value().delete(cct);
							}

							Map clonedSecondaryCtEntityIds = null;

							List regs = schemeEntityBean.getRegs();
							for (Iterator iterator = regs.iterator(); iterator.hasNext();) {

								IItem item = (IItem) iterator.next();
								//entitiesAPI.cloneRegEntity(entityId, item.getKeyInt(), newNumExp, idFieldsToClone);

								// Clonar el registro de la entidad y los registros de entidades secundarias en el formulario de la entidad
								clonedSecondaryCtEntityIds = scheme.cloneEntityApp(state, entityId, item.getKeyInt(), newExpedient, entityFieldsMap, clonedCTEntityIds);
							}

							// Establecer la entidad principal y las secundarias como ya clonadas
							clonedCTEntityIds.put(new Integer(entityId), null);
							if (clonedSecondaryCtEntityIds != null) {
								clonedCTEntityIds.putAll(clonedSecondaryCtEntityIds);
							}
						}
					}
	        	}

	        	// Clonamos los datos de la tabla que mantiene los expedientes relacionados
	        	// Consideramos que los datos de esta tabla deberian introducirse al iniciar un nuevo expediente
	        	// como regla asociada a un evento y no es neceario clonar
	        	// entitiesAPI.cloneRelatedExpedient(state.getNumexp(), newNumExp);

	        	// Nuevo expediente clonado correctamente
	        	newExpedients.add(newNumExp);

		        // Si todo ha sido correcto se hace commit de la transacción
		        bCommit = true;
	        }
	        catch (ISPACException e) {

				logger.error(e);
				//throw e;

				// Error al clonar
				errorUnidades++;
			}
			finally {
				cct.endTX(bCommit);
			}
        }

        // Eliminar las listas de sesión
        request.getSession().removeAttribute("SchemeEntityList");
        request.getSession().removeAttribute("EntityFieldsMap");

        // Listado de expedientes creados
		request.getSession().setAttribute(ActionsConstants.NEW_EXPEDIENTS_LIST, newExpedients);

		// Error al clonar algún expediente
		if (errorUnidades > 0) {

			ActionMessages errors = new ActionMessages();
			errors.add(null, new ActionMessage("errors.clone.expedient", new String[] {String.valueOf(errorUnidades)}));
			request.getSession().setAttribute(Globals.ERROR_KEY, errors);
		}

		return mapping.findForward("summary");
	}

	public ActionForward summary(ActionMapping mapping,
			   					 ActionForm form,
			   					 HttpServletRequest request,
			   					 HttpServletResponse response,
			   					 SessionAPI session) throws Exception {

		List newExpedients = (List) request.getSession().getAttribute(ActionsConstants.NEW_EXPEDIENTS_LIST);
		if (newExpedients != null) {

			ClientContext cct = session.getClientContext();

			IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		    IState state = managerAPI.currentState(getStateticket(request));

			// Establecer la información para el resumen de la clonación
	        request.setAttribute(ActionsConstants.NUM_EXP_SOURCE_CLONE, state.getNumexp());
	        request.setAttribute(ActionsConstants.NEW_EXPEDIENTS_LIST, newExpedients);

	        // Eliminar la lista de sesión
	        request.getSession().removeAttribute(ActionsConstants.NEW_EXPEDIENTS_LIST);

	        // Cargar el contexto de la cabecera (miga de pan)
	        SchemeMgr.loadContextHeader(state, request, getResources(request), session);

	    	// Establecer el menu
	        request.setAttribute("menus", MenuFactory.getCloneExpedientMenu(cct, state, getResources(request)));

			return mapping.findForward("success");
		}
		else {
			return mapping.findForward("enter");
		}
	}

	private boolean isSelectedEntity(ActionMapping mapping,
			   						 HttpServletRequest request,
			   						 SessionAPI session,
			   						 IManagerAPI managerAPI,
			   						 IState state,
			   						 String entityId,
									 List schemeEntityList) throws Exception {

        // Buscar en las entidades ya seleccionadas
        if ((schemeEntityList != null) &&
            (!schemeEntityList.isEmpty())) {

        	Iterator it = schemeEntityList.iterator();
        	while (it.hasNext()) {

        		SchemeEntityBean schemeEntity = (SchemeEntityBean) it.next();
        		if (schemeEntity.getString("ID").equals(entityId)) {

        			return true;
        		}
        	}
        }

        // Buscar en las entidades del esquema
		IScheme scheme = managerAPI.getSchemeAPI();
		IItemCollection itemcol = scheme.getCatalogEntityScheme(state);
		List schemeList = CollectionBean.getBeanList(SchemeEntityBean.class, itemcol);
        if ((schemeList != null) &&
        	(!schemeList.isEmpty())) {

        	Iterator it = schemeList.iterator();
        	while (it.hasNext()) {

        		SchemeEntityBean schemeEntity = (SchemeEntityBean) it.next();
        		if (schemeEntity.getString("ID").equals(entityId)) {

        			return true;
        		}
        	}
        }

		return false;
	}

	/**
	 * Se añade la entidad idEntidad a listado schemebeanlist
	 * @param idEntidad Identificador de la entidad a añadir en el catálogo
	 * @param schemebeanlist Listado de entidades
	 * @param session SessionApi
	 * @throws ISPACException
	 */
	private void  addEntity(int idEntidad, List   schemebeanlist ,  SessionAPI session) throws ISPACException{
		ClientContext cct = session.getClientContext();
	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IItem item=entitiesAPI.getCatalogEntity(idEntidad);

		  ArrayList itemEntidad=new ArrayList();
		  itemEntidad.add(item);
		  ListCollection listaCol= new ListCollection(itemEntidad);
		  List schemebeanL = CollectionBean.getBeanList(SchemeEntityBean.class, listaCol);
		  for (Iterator ite = schemebeanL.iterator(); ite.hasNext();)
	        {
	            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
	            IItemCollection itemcol=  entitiesAPI.queryEntities(idEntidad, "WHERE NUMEXP='"+cct.getStateContext().getNumexp()+"'");

	            List reglist = CollectionBean.getBeanList(RegEntityBean.class,itemcol);
	            for (Iterator iter = reglist.iterator(); iter.hasNext();)
	            {
	                RegEntityBean regEntBean = (RegEntityBean) iter.next();
	                scheEntBean.addRegEntity(regEntBean);
	            }
	            scheEntBean.addParameterId();

	            // Etiqueta para el tab de la entidad (los trámites no se presentan en un tab sino en el área de Datos de Trámites)
	            scheEntBean.setProperty("TAB_LABEL", entitiesAPI.getEntityResourceValue(idEntidad, cct.getAppLanguage(), scheEntBean.getItem().getString("NOMBRE")));

	        }


		 schemebeanlist.addAll(schemebeanL);
	}

}