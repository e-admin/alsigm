/*
 * Created on 20-sep-2004
 *
 */
package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author JUANIN
 *
 */
public class CheckNoticeAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception
	{
//	    String snotice=request.getParameter("noticeId");
	    String sstate=request.getParameter("state");

	    try
        {
            //int noticeId=Integer.parseInt(snotice);
            int state=Integer.parseInt(sstate);
            BatchForm frm = (BatchForm)form;
            String[] selectedNotices = frm.getMultibox();
            for (int i = 0; i < selectedNotices.length; i++) {
    			IInvesflowAPI invesflowAPI = session.getAPI();
    			IInboxAPI inbox = invesflowAPI.getInboxAPI();
    			inbox.modifyNotice(Integer.parseInt(selectedNotices[i]),state);
			}
        }
        catch (NumberFormatException e)
        {
            throw new ISPACInfo("No se ha podido encontrar el aviso",e);
        }
        catch (ISPACException e)
        {
            throw new ISPACInfo("No se ha podido modificar el aviso",e);
        }
		return mapping.findForward("success");
	}
}
