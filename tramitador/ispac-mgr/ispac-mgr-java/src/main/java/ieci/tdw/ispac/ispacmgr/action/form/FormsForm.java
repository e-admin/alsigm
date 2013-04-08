package ieci.tdw.ispac.ispacmgr.action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FormsForm extends ActionForm  {
  private String urlForm;
  private String textForm;
  private int pcdId;

  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    super.reset(mapping, request);
  }

  /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();

        if ((urlForm == null) || urlForm.equals("")) {
            errors.add("user", new ActionMessage("error.form.url"));
        }
        return errors;
  }

  public String getUrlForm() {
    return urlForm;
  }

  public void setUrlForm(String newUrlForm) {
    urlForm = newUrlForm;
  }

  public String getTextForm() {
    return textForm;
  }

  public void setTextForm(String newTextForm) {
    textForm = newTextForm;
  }

  public int getPcdId() {
    return pcdId;
  }

  public void setPcdId(int newProcId) {
    pcdId = newProcId;
  }
}