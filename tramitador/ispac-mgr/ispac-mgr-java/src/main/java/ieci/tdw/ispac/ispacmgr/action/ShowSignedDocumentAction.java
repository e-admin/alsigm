package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowSignedDocumentAction extends ShowDocumentAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {
    	return super.executeAction(mapping, form, request, response, session);
    }

	
	protected String getDocRef(IItem entity) throws ISPACException{
    	return entity.getString("INFOPAG_RDE");
    }


	protected void isResponsible(IItem entity, SessionAPI session) throws ISPACException {
		IInvesflowAPI invesFlowAPI = session.getAPI();
		boolean ret = invesFlowAPI.getSignAPI().isResponsible(entity.getKeyInt(), session.getClientContext().getRespId());
	  	if (!ret) {
	  		throw new ISPACInfo("exception.documents.noResponsability");
	  	}
	}

	
}
