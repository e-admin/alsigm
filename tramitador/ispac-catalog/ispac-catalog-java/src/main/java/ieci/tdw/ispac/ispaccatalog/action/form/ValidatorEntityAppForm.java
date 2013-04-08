/*
 * $Header: /TEST/SIGEM/ispaccatalog/src/ieci/tdw/ispac/ispaccatalog/action/form/ValidatorEntityAppForm.java,v 1.1 2007/04/24 11:12:48 luismimg Exp $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:12:48 $
 */

package ieci.tdw.ispac.ispaccatalog.action.form;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


/**
 * <p>This class extends <strong>ValidatorForm</strong> and provides
 * basic field validation based on an XML file.  The key passed into the
 * validator is the action element's 'path' attribute from the
 * struts-config.xml which should match the form element's name attribute
 * in the validation.xml.</p>
 *
 * <ul><li>See <code>ValidatorPlugin</code> definition in struts-config.xml
 * for validation rules.</li></ul>
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:12:48 $
 * @since Struts 1.1
 */

public class ValidatorEntityAppForm extends ValidatorForm implements Serializable {

    String entityAppName;

    /**
     * Returns the Validation key.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     * @return validation key - the action element's 'path' attribute in this case
     */
    public String getValidationKey(ActionMapping mapping,
                                   HttpServletRequest request)
    {
        return entityAppName;
    }
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
    }
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
//    public ActionErrors validate(ActionMapping mapping,
//            HttpServletRequest request)
//    {
//        return super.validate(mapping, request);
//    }


    /**
     * @return Returns the entityAppName.
     */
    public String getEntityAppName()
    {
        return entityAppName;
    }
    /**
     * @param entityAppName The entityAppName to set.
     */
    public void setEntityAppName(String entityAppName)
    {
        this.entityAppName = entityAppName;
    }

}
