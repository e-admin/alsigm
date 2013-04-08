/*
 * Created on Nov 12, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.frmsupervision;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AddSupervisedsForm extends ActionForm
{
    private String[] uids;

    /**
     * Reset all properties to their default values.
     *
     * @param mapping
     *            The ActionMapping used to select this instance.
     * @param request
     *            The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
    }

    /**
     * Validate all properties to their default values.
     *
     * @param mapping
     *            The ActionMapping used to select this instance.
     * @param request
     *            The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    public String[] getUids()
    {
        return uids;
    }

    public void setUids(String[] uids)
    {
        this.uids = uids;
    }

}