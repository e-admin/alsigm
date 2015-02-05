package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class LinkFrameTag extends ActionFrameTag {
	
	protected String nobr = "true";
    
	/**
	 * @return Returns the nobr.
	 */
	public String getNobr() {
		return nobr;
	}
	/**
	 * @param nobr The nobr to set.
	 */
	public void setNobr(String nobr) {
		this.nobr = nobr;
	}
	
	public int doStartTag() throws JspException {
		
		int ret = super.doStartTag();
		if (ret != SKIP_BODY) {
			
		  	TagUtils tagUtils = TagUtils.getInstance();
		
			StringBuffer linkTag = new StringBuffer();
			linkTag.append("<a");
		
			Boolean ispac_needToConfirm = new Boolean(this.getNeedToConfirm());
			
			if (ispac_needToConfirm.booleanValue()) {
				linkTag.append(" href=\"javascript:");
			}
			else {
				linkTag.append(" onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript: ispac_needToConfirm = true;");
			}
			
			Boolean show = new Boolean(getShowFrame());
			linkTag.append(generateShowExecuteFrameJS(tagUtils, show))
				   .append("\"");
			 	
			if (!StringUtils.isEmpty(getStyleClass()))
			{
				linkTag.append(" class='")
					   .append(getStyleClass())
					   .append("'");
			}
			linkTag.append(">");
			
		    if (getNobr().equals("true")) {
		    	linkTag.append("<nobr>");
		    }
		    
		 	JspWriter writer = pageContext.getOut();
			
		 	try
		 	{
		 		writer.print(linkTag.toString());
		 	}
		 	catch (IOException e)
		 	{
		 		tagUtils.saveException(pageContext, e);
		 		throw new JspException(messages.getMessage("common.io", e.toString()));
		 	}
		}
		
		return ret;
	}

	public int doAfterBody()
  	throws JspException {

	  	TagUtils tagUtils = TagUtils.getInstance();
	  	
	  	StringBuffer linkTag = new StringBuffer();
	  	String title = null;
	  	
		if (!StringUtils.isEmpty(getTitleKey())) {
			
			title = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKey());

			linkTag.append(title);
		}
		if (getNobr().equals("true")) {
			linkTag.append("</nobr>");
		}
		linkTag.append("</a>");
	
	 	JspWriter writer = pageContext.getOut();
	
	 	try
	 	{
	 		writer.print(linkTag.toString());
	 	}
	 	catch (IOException e)
	 	{
	 		tagUtils.saveException(pageContext, e);
	 		throw new JspException(messages.getMessage("common.io", e.toString()));
	 	}
	
	 	return SKIP_BODY;
  	}

  public void release() {

    super.release();
    styleClass = null;
  }
  
}
