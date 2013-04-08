package ieci.tdw.ispac.ispaccatalog.action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class SelectForm extends ActionForm
{

    private String[] uids;
    
    private String filter;



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
        if (uids == null || uids.length <= 0)
            errors.add("empty", new ActionMessage(
                    "error.form.deletesuperviseds"));
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
    
    public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

}