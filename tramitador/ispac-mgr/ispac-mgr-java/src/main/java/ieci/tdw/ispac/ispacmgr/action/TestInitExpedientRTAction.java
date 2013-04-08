package ieci.tdw.ispac.ispacmgr.action;

import java.util.Date;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TestInitExpedientRTAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		String idProcess = request.getParameter("idProcess");
		String action = request.getParameter("action");
		if ("p".equals(action)){
			session.getAPI().getTransactionAPI().pauseDeadline(PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE, Integer.parseInt(idProcess));
		
		}else if ("r".equals(action)){
			session.getAPI().getTransactionAPI().resumeDeadline(PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE, Integer.parseInt(idProcess));
		
		}else if ("c".equals(action)){
			String idCalendar = request.getParameter("idCalendar");
			session.getAPI().getTransactionAPI().setCalendar(PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE,
					Integer.parseInt(idProcess), Integer.parseInt(idCalendar));
			session.getAPI().getTransactionAPI().setBaseDate(PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE,
					Integer.parseInt(idProcess), new Date());
			session.getAPI().getTransactionAPI().recalculateDeadline(PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE,
					Integer.parseInt(idProcess));
			
		}
		return mapping.findForward("login");
	}
}
