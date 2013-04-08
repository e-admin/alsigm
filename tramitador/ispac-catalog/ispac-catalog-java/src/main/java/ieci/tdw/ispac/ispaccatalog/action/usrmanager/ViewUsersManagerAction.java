package ieci.tdw.ispac.ispaccatalog.action.usrmanager;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.UsersForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewUsersManagerAction extends BaseAction {
	
	protected static final String PARAM_ACTION		 		= "action";
	protected static final String PARAM_TYPE		 		= "type";
	protected static final String TYPE_ORGANIZATION		 	= "organization";
	protected static final String PARAM_UID		 			= "uid";
	protected static final String PARAM_UIDINT		 		= "uidint";
	protected static final String PARAM_NO_VIEW_USERS 		= "noviewusers";
	protected static final String PARAM_NO_VIEW_GROUPS 		= "noviewgroups";
	protected static final String PARAM_ONLY_SELECT_USERS 	= "onlyselectusers";
	protected static final String PARAM_INVALID_SELECT 		= "invalidSelect";
	
	public ActionForward executeAction(ActionMapping mapping,
	                                   ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       SessionAPI session) throws Exception {
		
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();
			
        //Si recibimos este parámetro es que habilitamos la selección múltiple de usuarios
        if(request.getParameter("selVarios")!=null){
        	request.setAttribute("selVarios", request.getParameter("selVarios"));
        }
		UsersForm userForm = (UsersForm) form;
		
		if(request.getParameterValues("seleccion")!=null){
			userForm.setMultibox(request.getParameterValues("seleccion"));
		}
        // Tipo de vista (estructura organizativa / grupos)
        String type = request.getParameter(PARAM_TYPE);
        if (StringUtils.isBlank(type)) {
        	
        	// Por defecto se visualiza la estructura organizativa
        	type = TYPE_ORGANIZATION;
        }
        request.setAttribute(PARAM_TYPE, type);
        
        // No se visualiza la vista de grupos
        request.setAttribute(PARAM_NO_VIEW_GROUPS, request.getParameter(PARAM_NO_VIEW_GROUPS));
        
        // Sólo se permite seleccionar usuarios
        request.setAttribute(PARAM_ONLY_SELECT_USERS, request.getParameter(PARAM_ONLY_SELECT_USERS));

        // UID en el formulario para el radio seleccionado al aceptar
        // Si es nulo es que se está navegando por la estructura organizativa o grupos
        // o cuando la selección no es válida (se lanza una excepción con el error)
        if ((userForm.getUid() == null && ( request.getAttribute("selVarios")==null || userForm.getMultibox()==null ||userForm.getMultibox().length==0 )) || 
        	(request.getAttribute(PARAM_INVALID_SELECT) != null)) {

            String formatterURL = "";
            
            // Estructura organizativa
            if (type.equals(TYPE_ORGANIZATION)) {
            	
            	// Elementos a mostrar
            	request.setAttribute("items", calculateOrganization(request, respAPI));
            	
            	// Formateador para organización
                formatterURL = "/formatters/usrmanager/organizationformatterdisplay.xml";
            }
            // Grupos
            else {
            	
            	// Elementos a mostrar
            	request.setAttribute("items", calculateGroups(request, respAPI));
            	
            	// Formateador para grugos
                formatterURL = "/formatters/usrmanager/groupformatterdisplay.xml";
            }
            
            CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
            BeanFormatter formatter = factory.getFormatter(getISPACPath(formatterURL));
            request.setAttribute("Formatter", formatter);

            // Navegación por la estructura organizativa
            request.setAttribute("URLParams", buildURLParams(request.getParameterMap()));           
            return mapping.findForward("nav");
        }

        // Action a llamar
        if (StringUtils.isNotBlank(request.getParameter(PARAM_ACTION))) {
        	
        	// Se redirecciona para que no se guarde como la última URL accedida correctamente
        	// y con el UID seleccionado
        	if(userForm.getUid()!=null){
            return new ActionForward("success", request.getParameter(PARAM_ACTION) + 
            									"?" + 
            									buildURLParams(request.getParameterMap()) + 
            									"&" + 
            									PARAM_UID + 
            									"=" + 
            									userForm.getUid(), true);
        	}
        	else{
        		 return new ActionForward("success", request.getParameter(PARAM_ACTION) + 
							"?" + 
							buildURLParams(request.getParameterMap()) + 
							"&" + 
							PARAM_UID + 
							"=" + 
							((String [])userForm.getMultibox()), true);
        	}
        }
        else {
        	// UID del radio seleccionado al aceptar
            String uid = userForm.getUid();
            
            // Obtener el responsable
            IResponsible responsible = respAPI.getResp(uid);
            ItemBean item = new ItemBean(responsible);
            request.setAttribute("Substitute", item);

            // Obtener los parámetros con los campos del formulario en los que se establecerá el responsable
            String parameters = request.getParameter("parameters");
//            String sParameters = session.getClientContext().getSsVariable(parameters);
//            if (sParameters != null) {
//                request.setAttribute(parameters, ActionFrameTag.toMap(sParameters));
//            }

            // Obtiene los parametros del tag y los salva en la request
            Map sParameters = (Map)request.getSession().getAttribute(parameters);
            if (sParameters != null) {
                request.setAttribute("parameters", sParameters);
            }

            // No refrescar la pantalla desde la que se llama 
            request.setAttribute("Refresh", "false");
            
    		return mapping.findForward("success"); 
        }
    }

    protected List calculateOrganization(HttpServletRequest request, 
    									 IRespManagerAPI respAPI) throws ISPACException {
    	
    	List items = new ArrayList();
    	
    	// UID del elemento seleccionado en la navegación 
        String uid = request.getParameter(PARAM_UIDINT);
        IResponsible responsible;
        
        if (uid == null) {
            responsible = respAPI.getRootResp();
        }
        else {
            responsible = respAPI.getResp(uid);
        }

        // Ruta hasta el root
        IItemCollection ancestors = respAPI.getAncestors(responsible.getUID());
        List ancestorsList = CollectionBean.getBeanList(ancestors);
        request.setAttribute("ancestors", ancestorsList);
        
        // Unidades organizativas del elemento seleccionado
        IItemCollection orgUnits = responsible.getRespOrgUnits();
        List orgUnitsList = CollectionBean.getBeanList(orgUnits);
        Collections.sort(orgUnitsList, Responsible.getRespComparator());
        items.addAll(orgUnitsList);

        // Mostrar los usuarios del elemento seleccionado
        String noViewUsers = (String) request.getParameter(PARAM_NO_VIEW_USERS);
        if (!((noViewUsers != null) && 
        	  (noViewUsers.equals("true")))) {
        	
        	IItemCollection users = responsible.getRespUsers();
        	List usersList = CollectionBean.getBeanList(users);
        	Collections.sort(usersList, Responsible.getRespComparator());
        	items.addAll(usersList);
        }
    	request.setAttribute(PARAM_NO_VIEW_USERS, noViewUsers);
    	
    	return items;
    }

    protected List calculateGroups(HttpServletRequest request, 
    							   IRespManagerAPI respAPI) throws ISPACException {
    	
    	List items = new ArrayList();
    	
    	// UID del elemento seleccionado en la navegación
        String uid = request.getParameter(PARAM_UIDINT);
        
        if (uid == null) {
        	
        	// Todos los grupos
            IItemCollection groups = respAPI.getAllGroups();
            List groupsList = CollectionBean.getBeanList(groups);
            Collections.sort(groupsList, Responsible.getRespComparator());
            items.addAll(groupsList);
        }
        else {
        	// Grupo seleccionado
            IResponsible responsible = respAPI.getResp(uid);
            
            // Mostrar los usuarios
            String noViewUsers = (String) request.getParameter(PARAM_NO_VIEW_USERS);
            if (!((noViewUsers != null) && 
            	  (noViewUsers.equals("true")))) {
            	
	            IItemCollection users = responsible.getRespUsers();
	            List usersList = CollectionBean.getBeanList(users);
	            Collections.sort(usersList, Responsible.getRespComparator());
	            items.addAll(usersList);
            }
            request.setAttribute(PARAM_NO_VIEW_USERS, noViewUsers);

            // Ruta hasta el root
            IItemCollection ancestors = respAPI.getAncestors(responsible.getUID());
            List ancestorsList = new ArrayList();
            ancestorsList.add(CollectionBean.getBeanList(ancestors).get(0));
            request.setAttribute("ancestors", ancestorsList);
        }
        
        return items;
    }

    protected String buildURLParams(Map parameters) {
    	
        StringBuffer url = new StringBuffer();
        String key = null;
        Map urlKeys = new HashMap();

        Iterator it = parameters.keySet().iterator();
        while (it.hasNext()) {
        	
            key = (String) it.next();
            if ((!urlKeys.containsKey(key)) && 
            	(!key.equals(PARAM_UIDINT) && !key.equals(PARAM_TYPE) && !key.equals(PARAM_UID) &&  (key.indexOf("property") == -1))) {
	            	String [] array=(String[]) parameters.get(key);
	            	//Recorremos todo el arraya para salvar todos los valores
	            	for(int i=0; i<array.length; i++){
		                if (url.length() != 0) {
		                	url.append("&");
		                }
		               	url.append(key).append("=").append(((String[])parameters.get(key))[i]);
	            	}
	          
                urlKeys.put(key, null);
            }
        }

        return url.toString();
    }
    
}