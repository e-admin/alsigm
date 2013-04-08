package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.ispacmgr.action.form.SelectSearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectSearchAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception
  {
	    SelectSearchForm selsearchform = (SelectSearchForm)form;

	    // Se recoge el valor del desplegable de búsqueda
	    String urlForm = selsearchform.getUrlForm();
	    int pcdId = selsearchform.getPcdId();
	    request.setAttribute("formSelect",urlForm);

	    if (pcdId > 0)
	    {
	      request.setAttribute("pcdId",Integer.toString(pcdId));
	      return mapping.findForward("success2");
	    }

        return mapping.findForward("success1");
  }
}