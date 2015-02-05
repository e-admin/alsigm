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

public class SelectThirdPartyTag extends TagSupport {

	// Atributos del tag
	protected String forward = null;
	protected String target = "workframe";
	protected String image = null;
	protected String id = null;
	protected String height = "480";
	protected String width = "640";
	
	public String getForward()
	{	return (this.forward);}

	public void setForward(String forward)
	{	this.forward = forward;}

	public String getTarget()
	{	return (this.target);}

	public void setTarget(String target)
	{	this.target = target;}

	public String getImage()
	{	return (this.image);}

	public void setImage(String image)
	{	this.image = image;}

	public String getId()
	{	return (this.id);}

	public void setId(String id)
	{	this.id = id;}

	public String getHeight()
	{	return (this.height);}

	public void setHeight(String height)
	{	this.height = height;}

	public String getWidth()
	{	return (this.width);}

	public void setWidth(String width)
	{	this.width = width;}
	
	public int doStartTag() throws JspException
	{
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String context = request.getContextPath();
		
		StringBuffer javascript = new StringBuffer();
		javascript.append("<script language='javascript'>\n")
				  .append("function search" + getId() + "()\n")
				  .append("{\n")
				  .append("  var element = document.getElementById( '" + getTarget() + "');\n")
				  .append("  if (element != null)\n")
				  .append("  {\n")
				  .append("    var width = " + getWidth() + ";\n")
				  .append("    var height = " + getHeight() + ";\n")
				  .append("    var x = (screen.width - width)/2;\n")
				  .append("    var y = (screen.height - height)/2;\n")
				  .append("    element.style.height = height;\n")
				  .append("    element.style.width = width;\n")
				  .append("    element.style.position = 'absolute';\n")
				  .append("    element.style.left = x;\n")
				  .append("    element.style.top = y;\n")
				  .append("    element.style.visibility = 'visible';\n")
				  .append("    element.src = '" + context + "/selectThirdParty.do?forward=" + getForward() + "';\n")
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
        	
		javascript.append("<img src='" + imageUrl + "' border='0' style='cursor:pointer' onclick='javascript:search" + getId() + "();'/>\n");
		
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