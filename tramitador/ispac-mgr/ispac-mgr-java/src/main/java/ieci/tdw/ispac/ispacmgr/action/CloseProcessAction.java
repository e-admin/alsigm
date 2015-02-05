package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
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
 * Cierra la tramitación del expediente
 *
 */
public class CloseProcessAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form, 
    								   HttpServletRequest request,
    								   HttpServletResponse response, 
    								   SessionAPI session) throws Exception {
    	
    	ClientContext cct = session.getClientContext();
    	
    	// Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));

		String idsStage = request.getParameter("idsStage");
		String[] stageids = idsStage.split("-");

		int nidstage = 0;

		IInvesflowAPI invesflowAPI = session.getAPI();
		ITXTransaction tx = invesflowAPI.getTransactionAPI();
		
		StringBuffer message = new StringBuffer();
		String[] args = new String[stageids.length];
		
		for (int i = 0; i < stageids.length; i++) {
			
			nidstage = Integer.parseInt(stageids[i]);
			IItem stage = invesflowAPI.getStage(nidstage);
			try {
				tx.closeProcess(stage.getInt("ID_EXP"));
			}
			catch (ISPACInfo e) {

				String msg = e.getExtendedMessage(request.getLocale());
				if (msg != null) {
					
					msg = StringUtils.replace(msg, "{0}", "{" + i + "}");
					args[i] = stage.getString("NUMEXP");
				}
				
				message.append(msg)
					   .append("<br/><br/>");
			}
		}
		
		/*if (message.length() > 0) {
			throw new ISPACInfo(message.toString(), args);
		}*/
		ISPACInfo info=null;
		if (message.length() > 0) {
			info=new ISPACInfo(message.toString(), args,false);
			request.getSession().setAttribute("infoAlert", info);
		}
		
		
		
		return NextActivity.refresh(request, mapping, currentstate);
    }
    
}