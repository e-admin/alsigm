package ieci.tdw.ispac.ispacmgr.action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

/**
 * @author Ildefonso Noreña
 *
 */
public class EntityBatchForm extends EntityForm {

    private String[] multibox;

    /**
     * Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      super.reset(mapping, request);
      multibox = new String [0];
    }

    /**
     * Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
      return super.validate(mapping, request);
    }

    public String[] getMultibox() {
      return multibox;
    }

    public void setMultibox(String[] newMultibox) {
      multibox = newMultibox;
    }    
}
