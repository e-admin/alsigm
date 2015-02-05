package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Establecer el skin del organismo de conexión cuando se recibe por parámetro.
 */
public class ISPACSkinTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources
            .getMessageResources(Constants.Package + ".LocalStrings");
    
    protected String parameter = null;

    /**
     * Process the end of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
    	   	
    	//String organization = (String) pageContext.getRequest().getParameter(getParameter());
    	String organization = null;

//    	MULTIORGANIZACION EN UNA MISMA BBDD    	
//    	try {
//    		String multiOrganization = ISPACConfiguration.getInstance().get(ISPACConfiguration.MULTIORGANIZATION);
//    		if ((multiOrganization != null) &&
//    			(multiOrganization.toUpperCase().equals("YES"))) {
//    			
//    			organization = (String) pageContext.getRequest().getParameter(ISPACConfiguration.getInstance().get(ISPACConfiguration.MULTIORGANIZATION_PARAM));
//    		}
//		}
//    	catch (ISPACException e) {
//		}
    	
		if (!StringUtils.isEmpty(organization)) {
			
			// Establecer el skin del organismo
			pageContext.getSession().setAttribute("skin", "skin_" + organization);
			pageContext.getSession().setAttribute("organization", organization);
		}
    	
        return EVAL_PAGE;
    }

	/**
	 * @return Returns the parameter.
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}