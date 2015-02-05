package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.HiddenTag;
import org.apache.struts.util.MessageResources;

public class HtmlCheckboxTag extends HiddenTag {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
	MessageResources.getMessageResources(Constants.Package + ".LocalStrings");
	
	/**
	 * Constantes que se establecen en el campo hidden que contiene el valor de BD
	 * que se corresponde con el checked/not checked del checkbox.
	 */
	public static final String CHECKED_VALUE 		= "SI";
	public static final String NOT_CHECKED_VALUE	= "NO";
	
	public static final String VALUE_CHECKED_KEY 		= "forms.si";
	public static final String VALUE_NOT_CHECKED_KEY	= "forms.no";
	
	protected boolean readonly = false;
	protected String propertyReadonly = "readonly";
	
	protected String styleClassCheckbox = "inputSelectP";
	protected String titleKeyCheckbox = null;
	protected String valueChecked = null;
	
	protected String valueCheckedKey = VALUE_CHECKED_KEY;
	protected String valueNotCheckedKey = VALUE_NOT_CHECKED_KEY;
	
	protected String jsOnclick = "selectOnChecked";
	
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
	 * @return Returns the readonly.
	 */
	public boolean isReadonly() {
		return readonly;
	}
	/**
	 * @param readonly The readonly to set.
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return Returns the styleClassCheckbox.
	 */
	public String getStyleClassCheckbox() {
		return styleClassCheckbox;
	}
	/**
	 * @param styleClassCheckbox The styleClassCheckbox to set.
	 */
	public void setStyleClassCheckbox(String styleClassCheckbox) {
		this.styleClassCheckbox = styleClassCheckbox;
	}

	/**
	 * @return Returns the titleKeyCheckbox.
	 */
	public String getTitleKeyCheckbox() {
		return titleKeyCheckbox;
	}
	/**
	 * @param titleKeyCheckbox The titleKeyCheckbox to set.
	 */
	public void setTitleKeyCheckbox(String titleKeyCheckbox) {
		this.titleKeyCheckbox = titleKeyCheckbox;
	}

	/**
	 * @return Returns the valueChecked.
	 */
	public String getValueChecked() {
		return valueChecked;
	}
	/**
	 * @param valueChecked The valueChecked to set.
	 */
	public void setValueChecked(String valueChecked) {
		this.valueChecked = valueChecked;
	}

	/**
	 * @return Returns the valueCheckedKey.
	 */
	public String getValueCheckedKey() {
		return valueCheckedKey;
	}
	/**
	 * @param valueCheckedKey The valueCheckedKey to set.
	 */
	public void setValueCheckedKey(String valueCheckedKey) {
		this.valueCheckedKey = valueCheckedKey;
	}
	
	/**
	 * @return Returns the valueNotCheckedKey.
	 */
	public String getValueNotCheckedKey() {
		return valueNotCheckedKey;
	}
	/**
	 * @param valueNotCheckedKey The valueNotCheckedKey to set.
	 */
	public void setValueNotCheckedKey(String valueNotCheckedKey) {
		this.valueNotCheckedKey = valueNotCheckedKey;
	}
	
	/**
	 * @return Returns the jsOnclick.
	 */
	public String getJsOnclick() {
		return jsOnclick;
	}
	/**
	 * @param jsOnclick The jsOnclick to set.
	 */
	public void setJsOnclick(String jsOnclick) {
		this.jsOnclick = jsOnclick;
	}
	
	public int doStartTag() throws JspException {
		
		// Sólo lectura en el control
		if (!isReadonly()) {

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
	    	}
	    	else {
	    		super.doStartTag();
	    	}
		}
	    
	 	return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		
		TagUtils tagUtils = TagUtils.getInstance();
		JspWriter out = pageContext.getOut();
				
	 	try	{
    		String value = "";
    		try {
				value = (String) TagUtils.getInstance().lookup(pageContext, name, getProperty(), null);
			}
    		catch (JspException e) {
    		}
			
	    	if (!isReadonly()) {
	    		
	    		StringBuffer html = new StringBuffer();
	    		
	    		html.append("<input type=\"checkbox\" name=\"checkbox_" + getProperty().substring(getProperty().indexOf("(") + 1, getProperty().length() - 1) +"\" styleClass=\"")
	    			.append(getStyleClassCheckbox())
	    			.append("\" onclick=\""+ getJsOnclick() + "(this, top.document.forms[0].elements[ '")
	    			.append(getProperty())
	    			.append("' ]);\" value=\"ON\"");
	    		
	    		if (getValueChecked().equals(value)) {
	    			
	    			html.append(" checked=\"true\"");
	    		}
	    		
	     		if (!StringUtils.isEmpty(getTitleKeyCheckbox())) {
	     			
	    			html.append(" title=\"")
	    				.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyCheckbox()))
	    				.append("\"");
	     		}
	     		
	     		if (!StringUtils.isEmpty(getTabindex())) {
	     			
	     			html.append(" tabindex=\"")
	     				.append(getTabindex())
	     				.append("\"");
	     		}
	    		
	    		html.append(" />");
	    		
	    		out.write(html.toString());
	    	}
	    	else {
	    		out.write("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td height=\"3px\"></td></tr><tr><td>");

	    		if (value.equals(getValueChecked())) {
	    			out.write(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getValueCheckedKey()));
	    		}
	    		else {
	    			out.write(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getValueNotCheckedKey()));
	    		}
	    		
	    		out.write("</td></tr></table>");
	    	}
	 	}
	 	catch (IOException e) {
	 		tagUtils.saveException(pageContext, e);
			throw new JspException(messages.getMessage("common.io", e.toString()));
	 	}
    	
	    return EVAL_PAGE;
	}
	
}