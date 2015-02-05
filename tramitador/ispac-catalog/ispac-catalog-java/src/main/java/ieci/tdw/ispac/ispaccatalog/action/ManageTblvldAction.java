package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.EntitiesAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.OrderUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ManageTblvldAction extends BaseDispatchAction {
	
	private final String TIENE_SUSTITUTO = "TIENE_SUSTITUTO";
	
	public ActionForward show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		int entityId = 0;
		int keyId = 0;
		int type = 0;

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_READ,
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

		String parameter;

		EntityForm defaultForm = (EntityForm) form;

		parameter = request.getParameter("type");
		if (parameter == null) {
			parameter = defaultForm.getProperty("TYPE");
		}
		type = Integer.parseInt(parameter);

		parameter = request.getParameter("entityId");
		if (parameter == null) {
			parameter = defaultForm.getEntity();
		}
		entityId = Integer.parseInt(parameter);

		parameter = request.getParameter("regId");
		if (parameter == null) {
			parameter = defaultForm.getKey();
		}
		keyId = Integer.parseInt(parameter);

		defaultForm.setEntity(Integer.toString(entityId));
		defaultForm.setKey(Integer.toString(keyId));
		defaultForm.setReadonly("false");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesApi = invesFlowAPI.getEntitiesAPI();
		
		if (keyId != ISPACEntities.ENTITY_NULLREGKEYID) {
			IItem item = entitiesApi.getEntity(entityId, keyId);
			
			defaultForm.setProperty("ID", item.getString("ID"));
			defaultForm.setProperty("VALOR", item.getString("VALOR"));
			if (EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.equals(EntityType.getInstance(type))){
				defaultForm.setProperty("SUSTITUTO", item.getString("SUSTITUTO"));
			}
			
			String orden = item.getString("ORDEN");
			if (StringUtils.isNotBlank(orden))
				defaultForm.setProperty("ORDEN", orden);
			
			String vigente = item.getString("VIGENTE");
			if (StringUtils.isNotBlank(vigente))
				defaultForm.setProperty("VIGENTE", vigente);
			else
				defaultForm.setProperty("VIGENTE", "0");
		}else{
			defaultForm.setProperty("VIGENTE", "1");
		}
		
		request.setAttribute("EntityId", Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		
		// Establecer el tamaño de los campos
		EntityDef entityDef = getEntityDef(request, session, entityId);
		setFieldsSize(request, entityDef);
		
		/*if (!entityapp.validate()) {
			
			ActionMessages errors = new ActionMessages();
			List errorList = entityapp.getErrors();
			Iterator iteError = errorList.iterator();
			while (iteError.hasNext()) {
				
				ValidationError validError = (ValidationError) iteError.next();
				ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			}
			saveAppErrors(request, errors);
			
			return new ActionForward(mapping.getInput());
		}*/
		
		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(invesFlowAPI.getCatalogAPI())
				.getBreadCrumbs(request);
		request.setAttribute("BreadCrumbs", bcm.getList());
		request.setAttribute(TIENE_SUSTITUTO, new Boolean(
				EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.equals(EntityType
						.getInstance(type))));

		return mapping.findForward("success_show");
	}
	
	protected EntityDef getEntityDef(HttpServletRequest request, 
			SessionAPI session, int entityId) throws ISPACException {

		EntityDef entityDef = null;

		ItemBean bean = (ItemBean) request.getSession()
			.getAttribute(ShowCTEntityToManageAction.ENTITY_BEAN_KEY);
		if (bean != null) {
			entityDef = (EntityDef) bean.getProperty("ENTITY_DEF");
		}
		
		if (entityDef == null) {
			ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
			IItem entity = catalogAPI.getCTEntity(entityId);
			if (entity != null) {
		        String xmlDef = entity.getString("SPAC_CT_ENTIDADES:DEFINICION");
		        if (StringUtils.isNotBlank(xmlDef)) {
		        	entityDef = EntityDef.parseEntityDef(xmlDef);
		        }
			}
		}
		
		return entityDef;
	}
	
	protected void setFieldsSize(HttpServletRequest request, 
			EntityDef entityDef) throws ISPACException {

		int valueSize = 10;
		int substSize = 64;
		int ordenSize=10;
		
		if (entityDef != null) {
			List fields = entityDef.getFields();
			if (!CollectionUtils.isEmpty(fields)) {
				EntityField field;
				for (int i = 0; i < fields.size(); i++) {
					field = (EntityField) fields.get(i);
					if ("VALOR".equalsIgnoreCase(field.getPhysicalName())) {
						valueSize = field.getSize();
					} else if ("SUSTITUTO".equalsIgnoreCase(field.getPhysicalName())) {
						substSize = field.getSize();
					}
					 else if ("ORDEN".equalsIgnoreCase(field.getPhysicalName())) {
							ordenSize = field.getSize();
						}
				}
			}
		}

		request.setAttribute("TAM_VALOR", new Integer(valueSize));
		request.setAttribute("TAM_SUSTITUTO", new Integer(substSize));
		request.setAttribute("TAM_ORDEN", new Integer(ordenSize));
	}
    
	public ActionMessages validateForm(ActionForm form, int type, int key, SessionAPI session) throws ISPACException{
		
		EntityForm entityForm = (EntityForm)form;
		ActionMessages errors = new ActionMessages();
		int entityId = Integer.parseInt(entityForm.getEntity());
		String valor = entityForm.getProperty("VALOR");
		String sustituto = entityForm.getProperty("SUSTITUTO");
		
		if (StringUtils.isBlank(valor)){
			ActionMessage msg = new ActionMessage("tblvld.error.campoValorRequired");
			errors.add("property(VALOR)", msg);
		}	
		else {
			EntitiesAPI entities = (EntitiesAPI) session.getAPI().getEntitiesAPI();
			IItemCollection itemcol = null;

			if (key==ISPACEntities.ENTITY_NULLREGKEYID)
				itemcol=entities.queryEntities(entityId, "WHERE VALOR = '" + DBUtil.replaceQuotes(valor) + "'");
			else
				itemcol =entities.queryEntities(entityId, "WHERE VALOR = '" + DBUtil.replaceQuotes(valor) + "' AND ID != " + key);
			if (itemcol.next()) {	
					ActionMessage msg  = new ActionMessage("form.tblvldvalue.value.duplicated");
					errors.add("property(VALOR)", msg);
			}
		}
		
		if (EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.equals(EntityType.getInstance(type))){
			if (StringUtils.isBlank(sustituto)){
				ActionMessage msg = new ActionMessage("tblvld.error.campoSustituteRequired");
				errors.add("property(SUSTITUTO)", msg);
			}
				
	
		}
		
		return errors;
	}
	
	public ActionForward store(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		IEntitiesAPI entapi = session.getAPI().getEntitiesAPI();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());
		int type = Integer.parseInt(request.getParameter("type"));
		EntityApp entityapp = null;
		ActionMessages errors = null;
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
			// Abrir transacción
			cct.beginTX();
			
			errors = validateForm(form, type, keyId, session);
			if (errors.isEmpty()) {

				//Si el identificador del registro es nulo (-1) se debe
				//crear una nueva entrada.
				IItem item = null;
				
				if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
					
					item = entapi.createEntity(entityId);
					item.set("ORDEN", item.getString("ID"));
				}
				else{
					item = entapi.getEntity(entityId, keyId);
				}
				
				// Valor
				item.set("VALOR", defaultForm.getProperty("VALOR"));
				
				// Tabla con sustituto
				if (EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.equals(EntityType.getInstance(type))){
					item.set("SUSTITUTO", defaultForm.getProperty("SUSTITUTO"));
				}
				
				// Vigencia
				String vigente = defaultForm.getProperty("VIGENTE");
				if (!StringUtils.isBlank(vigente)) {
					item.set("VIGENTE", "1");
				}
				else {
					item.set("VIGENTE", "0");
				}
				
				item.store(cct);

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
			}
			else {
				saveErrors(request, errors);
				request.setAttribute(TIENE_SUSTITUTO, new Boolean(
						EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.equals(EntityType
								.getInstance(type))));

				//return mapping.findForward("success_show");
				return new ActionForward(mapping.getInput());
			}			
		}
		catch (ISPACException e) {

			if (entityapp != null) {

				throw new ISPACInfo(e.getMessage());
			} else {

				// Suele producirse error en las secuencias al estar mal inicializadas
				// provocando una duplicación de keys
				throw e;
			}
		}
		finally {
			cct.endTX(bCommit);
		}

		if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
			
			return mapping.findForward("success_store");
		}
		else {
			ActionForward action = mapping.getInputForward();
			String redirected = action.getPath()
							  + "&type="+ type
							  + "&regId="+ keyId
							  + "&entityId="+ entityId;
			return new ActionForward( action.getName(), redirected, true);
		}
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

 		EntityForm defaultForm = (EntityForm) form;
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		IEntitiesAPI entapi = session.getAPI().getEntitiesAPI();
		String[] seleccion = defaultForm.getMultibox();
		
		//if(seleccion == null)
			//throw new ISPACInfo("exception.info.list.noSelectedElements");
		
		if (seleccion!=null && seleccion.length>0){
			for (int i = 0; i < seleccion.length; i++) {
				IItem newitem = entapi.createEntity(entityId);
				newitem.setKey(Integer.parseInt(seleccion[i]));
				newitem.delete(session.getClientContext());
				 
			}
			
		}
		return mapping.findForward("success_store");
	}
	
	public ActionForward subir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		return moverElementos(mapping,form,request, session,true);
	}
	
	public ActionForward bajar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		return moverElementos(mapping,form,request, session,false);
	}

	public ActionForward ordenar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

		String tipoOrdenacion= request.getParameter("orderBy");
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		IEntitiesAPI  entitiesAPI= session.getAPI().getEntitiesAPI();
		entitiesAPI.orderValuesTblValidation(entityId, tipoOrdenacion);
	
		return mapping.findForward("success_store");
	}

	
	
	
	private ActionForward moverElementos(ActionMapping mapping,ActionForm form, HttpServletRequest request, SessionAPI session, boolean subir) throws ISPACException {
		
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

		EntityForm defaultForm = (EntityForm) form;
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		IEntitiesAPI  entitiesAPI= session.getAPI().getEntitiesAPI();
		String[] seleccion = defaultForm.getMultibox();
		
		
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
	        /*Obtengo todos los valores de la entidad*/
			IItemCollection itemcol = entitiesAPI.queryEntities(
	        		entityId, " ORDER BY  " + ICatalogAPI.ORDEN_FIELD_NAME);
	       
	        List listPars = new ArrayList();
	        List checkChange=new ArrayList();
	        OrderUtil.ordenar(subir, "ORDEN", itemcol, seleccion, listPars, checkChange);
	        
	        
	        int i=0;
	        
	     
	        for (i=0; i<listPars.size();i++) {
	        	IItem item=(IItem) listPars.get(i);
	         //se puede utilizar la variable i xq se ha usuado el recorrido del iterador para inicializar esta estructura.
	        	if( checkChange.get(i).toString().equals("true") ){
			
				   item.store(session.getClientContext());	
	        	}
	        	
	        	
			}
	        
			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
			
	    	return mapping.findForward("success_store");
		}
		finally {
			cct.endTX(bCommit);
		}
	}
	
	public ActionForward movedown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
		return moverElementos(mapping,form,request,session,false);
	}
	
	
}
