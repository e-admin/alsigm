package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author RAULHC
 *
 */
public class DocumentacionCompletaAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

        //------------------------------------------------------------------------------
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		// Estado de tramitación
    	IState state = managerAPI.currentState(getStateticket(request));    
        //------------------------------------------------------------------------------

    	//Obtener valor de variable de Documentacion Completa
    	String estado = ConfigurationMgr.getVarGlobal(cct, 
    			ConfigurationMgr.ESTADOADM_DOC_COMPLETA);
    	
    	//Actualizar Estado del Expediente
    	IItem expediente = entitiesAPI.getExpedient(state.getNumexp());
    	expediente.set("ESTADOADM", estado);
    	expediente.store(cct);
    	
    	//Moverse al expediente
    	return NextActivity.refresh(state, mapping);
    	
    }

}
