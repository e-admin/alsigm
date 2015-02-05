package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Borra un fichero temporal.
 * Se utiliza desde el tag printfile.
 */
public class DeleteFileAction extends BaseAction
{
    public ActionForward executeAction(
    ActionMapping mapping, 
	ActionForm form,
    HttpServletRequest request, 
	HttpServletResponse response,
    SessionAPI session) throws Exception
    {
        String file = request.getParameter("file");

        if (file == null)
            return null;

        try
		{
			FileTemporaryManager temporaryManager = null;
			temporaryManager = FileTemporaryManager.getInstance();
			temporaryManager.delete( file);
		}
        catch (Exception e)
		{}

        return null;
    }
}
