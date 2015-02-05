package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExpTransAction extends BaseAction
{
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     SessionAPI session) throws Exception
  {
	    BatchForm batchForm = (BatchForm)form;
	    for (int i=0; i <batchForm.getMultibox().length; i++)
	    {
	      System.out.println(batchForm.getMultibox()[i]);
	    }

	    return mapping.findForward("success");
  }
}