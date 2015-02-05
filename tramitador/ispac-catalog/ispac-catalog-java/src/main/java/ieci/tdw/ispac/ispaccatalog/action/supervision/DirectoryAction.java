package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class DirectoryAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request, 
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
        IInvesflowAPI invesflowAPI = session.getAPI();
        IRespManagerAPI respAPI = invesflowAPI.getRespManagerAPI();
    	
        // Cargar los objetos que se presentan en el tile del directorio
        String treeURL = "";
        String formatterURL = "";
        String ispacbase = (String) servlet.getServletContext().getAttribute("ispacbase");
        
        // El directorio tiene dos vistas: organización y grupos, ambos con sus usuarios respectivos
        String view = request.getParameter("view");
        String uid = request.getParameter("uid");
        IResponsible resp = null;

        // Por defecto se carga la organización
        if (view == null || view.equals("organization")) {
        	
        	view = "organization";
            resp = calculateOrganization(uid, respAPI, request);
            
            // Jsp y formateador para la estructura organizativa
            treeURL = StaticContext.rewritePage(ispacbase, "common/supervision/organizationbrowser.jsp");
            formatterURL = "/formatters/supervision/organizationformatter.xml";
        } 
        else if (view.equals("groups")) {
        	
            resp = calculateGroups(uid, respAPI, request);
            
            if (resp == null) {
            	request.setAttribute("isGroups", Boolean.TRUE.toString());
            }
            
            // Jsp y formateador para la estructura de grupos
            treeURL = StaticContext.rewritePage(ispacbase, "common/supervision/groupsbrowser.jsp");
            formatterURL = "/formatters/supervision/groupformatter.xml";
        }

       	request.setAttribute("RESPONSIBLE", resp);
        request.setAttribute("application", treeURL);
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath(formatterURL));
        request.setAttribute("Formatter", formatter);
        
        if ((resp != null) &&
        	(resp.isOrgUnit())) {
        	
        	request.setAttribute("isOrgUnit", Boolean.TRUE.toString());
        }
        
        request.setAttribute("view", view);
        request.setAttribute("uid", uid);
        
        // Ejecutar el action que carga los objetos que se presentan en el segundo tile
        return directoryExecuteAction(mapping, form, request, response, session);
    	
    	/*

        boolean bGroups = false;
        IInvesflowAPI invesflowapi = session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();

        // El directorio tiene tres vistas: organización, grupos y usuarios de
        // un grupo
        String view = request.getParameter("view");

        // Ejecutamos accion que carga en el request los objetos que se
        // presentaran en el
        // segundo tile
        ActionForward forward = directoryExecuteAction(mapping, form, request,
                response, session);

        // Cargamos en el request los objetos que se presentaran en el tile del
        // directorio.
        // X defecto se cargan los objetos de tipo 'organization'
        String treeURL = "";
        String formatterURL = "";
        String ispacbase = (String) servlet.getServletContext().getAttribute("ispacbase"); 
        
        if (view == null || view.equals("organization"))
        {
            calculateOrganization(request, response, respAPI);
            treeURL = StaticContext.rewritePage(ispacbase, "common/supervision/organizationbrowser.jsp");
            formatterURL = "/formatters/supervision/organizationformatter.xml";
        } 
        else if (view.equals("groups"))
        {
            bGroups = calculateGroups(request, response, respAPI);
            treeURL = StaticContext.rewritePage(ispacbase, "common/supervision/groupsbrowser.jsp");
            formatterURL = "/formatters/supervision/groupformatter.xml";
        }

        // Establecemos el tipo de directorio: LDAP o INVESDOC
        String dirManager = ISPACConfiguration.getInstance().get(
                ISPACConfiguration.DIRECTORY_MANAGER);
        if (dirManager.equals("LDAP"))
            request.setAttribute("dirManager", "LDAP");
        else if (dirManager.equals("INVESDOC"))
            request.setAttribute("dirManager", "INVESDOC");
        
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath(formatterURL));
        request.setAttribute("Formatter", formatter);
        
        request.setAttribute("application", treeURL);
        request.setAttribute("IsGroups", Boolean.toString(bGroups).toUpperCase());

        return forward;
        */
    }

    public abstract ActionForward directoryExecuteAction(ActionMapping mapping,
            											 ActionForm form, 
            											 HttpServletRequest request,
            											 HttpServletResponse response, 
            											 SessionAPI session) throws Exception;

    private IResponsible calculateOrganization(String uid,
            						   		   IRespManagerAPI respAPI,
            						   		   HttpServletRequest request) throws ISPACException {
    	
        // Se van a obtener los hijos de un responsable (usuarios y grupos)
        // y sus ancestros. El responsable del cual se obtiene todo esto es
        // aquel cuyo UID se recibe como parámetro
        IResponsible resp = null;
        
        if (uid == null) {
        	
            resp = respAPI.getRootResp();
            uid = resp.getUID();
        }
        else{
            resp = (Responsible) respAPI.getResp(uid);
        }
        
        // Obtener los ancestros para generar el árbol
        IItemCollection ancestors = respAPI.getAncestors(uid);
        List ancestorsList = CollectionBean.getBeanList(ancestors);
        List ancestorsReverseList = null;
        if (ancestorsList!=null && !ancestorsList.isEmpty()){
        	ItemBean[] respArray = (ItemBean[])ancestorsList.toArray(new ItemBean[ancestorsList.size()]);
        	CollectionUtils.reverseArray(respArray);
        	ancestorsReverseList = new ArrayList();
        	CollectionUtils.addAll(ancestorsReverseList, respArray);
        }
        request.setAttribute("ancestors", ancestorsReverseList);
        request.setAttribute("numAncestors", new Integer(ancestorsReverseList.size()));

        // Obtener los hijos (usuarios y unidades organizativas)
        IItemCollection users = resp.getRespUsers();
        List usersList = CollectionBean.getBeanList(users);
        Collections.sort(usersList, Responsible.getRespComparator());
        request.setAttribute("users", usersList);
        IItemCollection orgUnits = resp.getRespOrgUnits();
        List orgUnitsList = CollectionBean.getBeanList(orgUnits);
        Collections.sort(orgUnitsList, Responsible.getRespComparator());
        request.setAttribute("orgunits", orgUnitsList);
        
        return resp;
    }

    private IResponsible calculateGroups(String uid,
            							 IRespManagerAPI respAPI,
            							 HttpServletRequest request) throws ISPACException {

        if (uid == null) {
        	
        	// Obtener la lista de grupos
            IItemCollection groups = respAPI.getAllGroups();
            List groupsList = CollectionBean.getBeanList(groups);
            Collections.sort(groupsList, Responsible.getRespComparator());
            request.setAttribute("groups", groupsList);
            
            return null;
        }
        else {
        	
            Responsible resp = (Responsible) respAPI.getResp(uid);
            if (resp.isGroup()) {
            	
            	// Obtener los usuarios del grupo
                IItemCollection users = resp.getRespUsers();
                List usersList = CollectionBean.getBeanList(users);
                Collections.sort(usersList, Responsible.getRespComparator());
                request.setAttribute("users", usersList);
                
                request.setAttribute("uidGroup", resp.getUID());
            }
            else if (resp.isUser()) {
            
	            //String name = resp.getName();
	            //String nameformatted = name.replaceAll(",", ", ");
            	String uidGroup = request.getParameter("uidGroup");
            	Responsible group = (Responsible) respAPI.getResp(uidGroup);
            	request.setAttribute("groupName", group.getRespName());
            	request.setAttribute("uidGroup", uidGroup);
            }
            
            request.setAttribute("name", resp.getRespName());
            
            return resp;
        }
    }
    
}