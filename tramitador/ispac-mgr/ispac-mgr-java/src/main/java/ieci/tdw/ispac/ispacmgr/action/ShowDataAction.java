package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowDataAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
        ClientContext cct = session.getClientContext();
        
        // Se cambia el estado de tramitación
        IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
        Map params = request.getParameterMap();
        IState state = null;
        try {
        	state = managerAPI.enterState(getStateticket(request),ManagerState.DATA,params);
        }
        catch(Exception e){
            throw new ISPACInfo(e.getCause().getMessage());
        }
        
        // Cargamos el expediente
        SpacMgr.loadExpedient(session, state, request);
        
    	// Cargamos los datos del esquema
    	IScheme scheme = SchemeMgr.loadScheme(mapping, request, session, state);

        //////////////////////////////////////////////////////////////////////
        // Formulario asociado a la acción
        EntityForm defaultForm = (EntityForm) form;
        EntityApp entityapp = null;
        String path = getRealPath("");
		try {
		    entityapp = scheme.getDefaultEntityApp(state, path, false);
		}
		catch(ISPACNullObject e) {
			
			// Si no existe ningun registro creado para la entidad indicada en el estado
			// buscamos el EntityApp pasando un registro vacio
			entityapp = scheme.getEntityApp(state, state.getEntityId(), ISPACEntities.ENTITY_NULLREGKEYID, path, state.getEntityRegId(), false);
		}
		
		// Limpiar el formulario
		defaultForm.reset();
		
		// Establecer los datos
		defaultForm.setEntityApp(entityapp, cct.getLocale());
        
		// No se permite modificar los datos del formulario
		defaultForm.setReadonly(Boolean.toString(true));
		defaultForm.setReadonlyReason(""+state.getReadonlyReason());
		request.setAttribute(ActionsConstants.READONLYSTATE, ""+state.getReadonlyReason());
		
		// Se actualiza el estado de tramitación.
		storeStateticket(state,response);
		
		// Introducimos como atributo el action utilizado como enlace para todas las entidades
   		ActionForward action = mapping.findForward("showdata");
   		String urlExp = action.getPath();
   		request.setAttribute("urlExp", urlExp);
   		
   		// Y se mantiene la ordenación de las listas
   		String displayTagOrderParams = getDisplayTagOrderParams(request);	
   		if (!StringUtils.isEmpty(displayTagOrderParams)) {
   			urlExp = urlExp + "?" + displayTagOrderParams;
   		}
   		request.setAttribute("urlExpDisplayTagOrderParams", urlExp);
   		request.setAttribute("displayTagOrderParams", displayTagOrderParams);
		
        // Enviamos un map con parámetros
        Map linkParams = new HashMap();
        linkParams.put("numexp", state.getNumexp());
        request.setAttribute("Params", linkParams);

		// Mandamos los parámetros entity y regentity
		request.setAttribute("entityid", Integer.toString(state.getEntityId()));
		request.setAttribute("entityregid", Integer.toString(state.getEntityRegId()));
		
        // Pasamos el atributo del pcdId para la ayuda en linea
        request.setAttribute("pcdid", Integer.toString(state.getPcdId()));

        // Página jsp asociada a la presentación de la entidad
        request.setAttribute("application", defaultForm.getEntityApp().getURL());
        
        // Si se recibe el parametro 'form' con el valor 'single'
        if (request.getParameter("form")!= null && request.getParameter("form").equals("single")) {
            return mapping.findForward("singleSuccess");
        }
        
        // Cargamos el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);
        
        // Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
        String returnToSearch = null;
        SearchForm searchForm = (SearchForm) request.getSession().getAttribute(ActionsConstants.FORM_SEARCH_RESULTS);
        if (searchForm != null) {
        	
        	returnToSearch = searchForm.getDisplayTagParams();
        }
        
		// Menus
        request.setAttribute("menus", MenuFactory.getExpMenu(cct, state,  getResources(request), returnToSearch));
        
		// Cargamos enlaces para los expedientes relacionados
		SpacMgr.loadRelatedExpedient(session, request, state.getNumexp(), SpacMgr.ALL_EXPEDIENTS );
                        
        return mapping.findForward("success");
    }

}