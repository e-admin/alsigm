/*
 * Created on 08-sep-2004
 */
package ieci.tdw.ispac.ispacmgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowExceptionAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Exception e = (Exception) request.getAttribute(org.apache.struts.Globals.EXCEPTION_KEY);
        
		// TODO: log de la excepción, con información particular (ISPACException)
		return mapping.findForward("success");
	}
}













