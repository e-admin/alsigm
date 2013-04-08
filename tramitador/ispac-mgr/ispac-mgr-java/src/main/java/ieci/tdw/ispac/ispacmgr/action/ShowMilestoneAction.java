package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowMilestoneAction extends BaseAction
{
    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
        ClientContext cct = session.getClientContext();

        /////////////////////////////////////////
        //Obtener el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
        IState state = managerAPI.currentState(getStateticket(request));
        IScheme scheme = managerAPI.getSchemeAPI();
        
        if (state.isResponsible()) {
        	
            // Cargar el contexto de la cabecera (miga de pan)
            SchemeMgr.loadContextHeader(state, request, getResources(request), session);
        }

    	// Establecer el menu
	    request.setAttribute("menus", MenuFactory.getMilestoneMenu(cct, getResources(request), state));
	    
        // Expediente
        request.setAttribute("Expedient", state.getNumexp());

        //////////////////////////
        // Hitos
        request.setAttribute("MilestoneList", scheme.getMilestones(state));

        return mapping.findForward("success");
    }
    
}