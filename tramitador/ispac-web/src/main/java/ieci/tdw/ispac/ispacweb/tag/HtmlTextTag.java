package ieci.tdw.ispac.ispacweb.tag;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.TextTag;

public class HtmlTextTag extends TextTag {
	
	protected String propertyReadonly = "readonly";
	protected String styleClassReadonly = "inputReadOnly";

    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
    	calculateReadonly();
    	return super.doStartTag();
    }

    public void calculateReadonly(){
    	// Sólo lectura en el control
    	if (!getReadonly()) {
    	
    		// Sólo lectura en el formulario
			String isReadonly = null;
			try {
	    		isReadonly = (String) TagUtils.getInstance().lookup(pageContext, name, getPropertyReadonly(), null);
			}
			catch (JspException e) {
			}
			
	    	if ((isReadonly != null) &&
	    		(isReadonly.equals("true"))) {
	    		
	    		setReadonly(true);
	    		setStyleClass(getStyleClassReadonly());
	    	}
    	}
    	else {
    		setStyleClass(getStyleClassReadonly());
    	}    	
    }

	/**
	 * @return Returns the propertyReadonly.
	 */
	public String getPropertyReadonly() {
		return propertyReadonly;
	}
	/**
	 * @param propertyReadonly The propertyReadonly to set.
	 */
	public void setPropertyReadonly(String propertyReadonly) {
		this.propertyReadonly = propertyReadonly;
	}

	/**
	 * @return Returns the styleClassReadonly.
	 */
	public String getStyleClassReadonly() {
		return styleClassReadonly;
	}
	/**
	 * @param styleClassReadonly The styleClassReadonly to set.
	 */
	public void setStyleClassReadonly(String styleClassReadonly) {
		this.styleClassReadonly = styleClassReadonly;
	}

	public void setReadonly(String readonly) {
		super.setReadonly(new Boolean(readonly).booleanValue());
	}
}