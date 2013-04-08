package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispacweb.tag.utils.TooltipGenerator;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class TooltipLabelTag extends TagSupport {
	
	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
	MessageResources.getMessageResources(Constants.Package + ".LocalStrings");	
	
	protected String labelKey = null;
	protected String tooltipKey = null;
	
	protected String labelEnd = ":";
	protected String nobr = "true";
	
	public int doStartTag() throws JspException {
		
		TagUtils tagUtils = TagUtils.getInstance();
		
		StringBuffer labelTag = new StringBuffer();

		labelTag.append("<label");
		TooltipGenerator.setTooltip(labelTag, getTooltipKey(), tagUtils, pageContext);
		labelTag.append(">");
		
		String label = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getLabelKey()) + getLabelEnd();
		
		if (getNobr().equals("true")) {
			
			labelTag.append("<nobr>")
					.append(label)
					.append("</nobr>");
		}
		else {
			labelTag.append(label);
		}
		
		labelTag.append("</label>");
		
	 	JspWriter writer = pageContext.getOut();
		
	 	try	{
	 		writer.print(labelTag.toString());
	 	}
	 	catch (IOException e) {
	 		tagUtils.saveException(pageContext, e);
			throw new JspException(messages.getMessage("common.io", e.toString()));
	 	}
		
		return SKIP_BODY;
	}

	/**
	 * @return Returns the labelEnd.
	 */
	public String getLabelEnd() {
		return labelEnd;
	}
	/**
	 * @param labelEnd The labelEnd to set.
	 */
	public void setLabelEnd(String labelEnd) {
		this.labelEnd = labelEnd;
	}

	/**
	 * @return Returns the labelKey.
	 */
	public String getLabelKey() {
		return labelKey;
	}
	/**
	 * @param labelKey The labelKey to set.
	 */
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

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

	/**
	 * @return Returns the tooltipKey.
	 */
	public String getTooltipKey() {
		return tooltipKey;
	}
	/**
	 * @param tooltipKey The tooltipKey to set.
	 */
	public void setTooltipKey(String tooltipKey) {
		this.tooltipKey = tooltipKey;
	}
	
}