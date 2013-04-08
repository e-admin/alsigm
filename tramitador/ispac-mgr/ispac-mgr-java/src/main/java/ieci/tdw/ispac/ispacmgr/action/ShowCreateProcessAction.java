package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCreateProcessAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
	    ClientContext cct = session.getClientContext();
	    
		IInvesflowAPI invesflowAPI = session.getAPI();

		
		//Eliminamos la busqueda de expedientes de sesión
		request.getSession().removeAttribute("numExpsSearch");
		
    	/////////////////////////////////////////
    	//Se cambia el estado de tramitación
    	IManagerAPI managerAPI = ManagerAPIFactory.getInstance()
    		.getManagerAPI(cct);
    	IState state = managerAPI.enterState(getStateticket(request),
    			ManagerState.CREATEPROCESSLIST, request.getParameterMap());
    	storeStateticket(state,response);
		
		////////////////////////////
		// Menús
		request.setAttribute("menus", MenuFactory.getSingleMenu(cct, getResources(request)));        
		
		////////////////////////////
		// Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("FormatterInstProcedure", 
				factory.getFormatter(getISPACPath("/digester/instancedformatter.xml")));

		// Procedimientos instanciables por el usuario
		IWorklistAPI wl=invesflowAPI.getWorkListAPI();
		request.setAttribute("InstProcedureList", 
				CollectionBean.getBeanList(wl.getCreateProcedures()));
		
	    request.setAttribute("url",
	    		mapping.findForward("showcreateprocess").getPath());

		return mapping.findForward("success");
	}

}