package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

/**
 * @author Lema
 */
public class SearchThirdPartyTag extends TagSupport {

	// Atributos del tag
	protected String field = null;
	protected String forward = null;
	protected String image = null;
	protected String id = null;
	
	public String getField()
	{	return (this.field);}
	public void setField(String field)
	{	this.field = field;}

	public String getForward()
	{	return (this.forward);}
	public void setForward(String forward)
	{	this.forward = forward;}

	public String getImage()
	{	return (this.image);}
	public void setImage(String image)
	{	this.image = image;}

	public String getId()
	{	return (this.id);}
	public void setId(String id)
	{	this.id = id;}

	public int doStartTag() throws JspException	{
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String context = request.getContextPath();
		
		StringBuffer javascript = new StringBuffer();
		javascript.append("<script language='javascript'>\n")
				  .append("function search" + getId() + "(object)\n")
				  .append("{\n")
				  .append("  var element = object.parentNode;\n")
				  .append("  while (element != null)\n")
				  .append("  {\n")
				  .append("    if (element.tagName == \"FORM\")\n")
				  .append("      break;\n")
				  .append("    element = element.parentNode;\n")
				  .append("  }\n")
				  .append("  if (element != null)\n")
				  .append("  {\n")
				  .append("    element.action= \"" + context + "/searchThirdParty.do" + "?field=" + getField() + "&forward=" + getForward() + "\";\n")
				  .append("    element.submit();\n")
				  .append("  }\n")
				  .append("}\n")
				  .append("</script>\n");
		
        String imageUrl;        	
        try {
        	imageUrl = StaticContext.getInstance().getBaseUrl(context);
        }
        catch (ISPACException ie) {
            pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
            throw new JspException(ie.getMessage());
        }
    	
    	// Href que incluya el ispacbase junto con el skin
        imageUrl = StaticContext.rewriteHref(pageContext, imageUrl, getImage());
		
		javascript.append("<img src='" + imageUrl + "' border='0' style='cursor:pointer' onclick='javascript:search" + getId() + "(this);'/>\n");
		
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(javascript.toString());
		} 
		catch (IOException ioe) {
			TagUtils.getInstance().saveException(pageContext, ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}
	
}