package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

public class HideFrameTag extends TagSupport {
	
	private static final String DEFAULT_ID = "workframe";
	public static final String WAIT_WINDOW = "wait.jsp";

	protected String id = DEFAULT_ID;
	protected String event = "true";

	protected String save = "false";
	protected String refresh = "false";

	public String getId()
	{	return (this.id);}

	public void setId(String id)
	{	
		if (StringUtils.isEmpty(id)){
			this.id = DEFAULT_ID;
		}else{
			this.id = id;
		}
	}

	public String getEvent()
	{	return (this.event);}

	public void setEvent(String event)
	{	this.event = event;}


	public String getSave()
	{	return (this.save);}

	public void setSave(String save)
	{	this.save = save;}

	public String getRefresh()
	{	return (this.refresh);}

	public void setRefresh(String refresh)
	{	this.refresh = refresh;}

	public int doStartTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    	StringBuffer javascript = new StringBuffer();

		if (getEvent().equalsIgnoreCase("false")) {
			javascript.append("<script>");
		}
		
		/*
        // Calculo de la etiqueta base
        String ispacbase = (String) pageContext.getServletContext().getAttribute("ispacbase");
        String src = request.getContextPath();
        
    	if (!StringUtils.isEmpty(ispacbase)) {
    		src = src + "/" + ispacbase + "/";
    	}
    	*/
		
		StringBuffer javascriptActiveBlock = new StringBuffer();
		javascriptActiveBlock.append("if (newhref.indexOf(\"?\") == -1) {")
							 .append("newhref = newhref + \"?reload=true\";")
							 .append("} else if (newhref.indexOf(\"reload\") == -1) {")
							 .append("newhref = newhref + \"&reload=true\";")
							 .append("}")
							 .append("if (activeBlock) {")
							 .append("if (newhref.indexOf(\"block\") == -1) {")
							 .append("newhref = newhref + \"&block=\" + activeBlock.value;")
							 .append("} else {")
							 .append("index = newhref.indexOf(\"block\");")
							 .append("newhref1 = newhref.substring(0,index);")
							 .append("newhref1 = newhref1 + \"block=\" + activeBlock.value;")
							 .append("newhref2 = newhref.substr(index);")
							 .append("index2 = newhref2.indexOf(\"&\");")
							 .append("if (index2 != -1) {")
							 .append("newhref1 = newhref1 + newhref2.substr(index2);")
							 .append("}")
							 .append("newhref = newhref1;")
							 .append("}")
							 .append("}");
		
		// Target PARENT
		javascript.append("if (parent.hideFrame) {");
		if (getSave().equalsIgnoreCase("true")) {
			
			javascript.append("if (parent.save) {")
		    		  .append("parent.save();")
		    		  .append("}");
		}
		if (getRefresh().equalsIgnoreCase("true")) {
			//javascript.append("parent.window.location.href = parent.window.location.href;");
		  
			// Refrescar el parent manteniendo el bloque activo
			javascript.append("var activeBlock = parent.window.document.getElementById(\"block\");")
					  .append("var newhref = parent.window.location.href;")
					  .append(javascriptActiveBlock.toString())
			  		  .append("parent.window.location.href = newhref;");
		}
		else {
			// Ocultar el frame
			javascript.append("parent.hideFrame(\"" + id + "\", \"" + StaticContext.rewritePage(pageContext, request.getContextPath(), WAIT_WINDOW) + "\");");
		}
		javascript.append("}");
		
		// Target TOP
		javascript.append(" else if (top.hideFrame) {");
		if (getSave().equalsIgnoreCase("true")) {
			
			javascript.append("if (top.save) {")
		    		  .append("top.save();")
		    		  .append("}");
		}
		if (getRefresh().equalsIgnoreCase("true")) {
		    //javascript.append("top.window.location.href = top.window.location.href;");
			
			// Refrescar el top manteniendo el bloque activo
			javascript.append("var activeBlock = top.window.document.getElementById(\"block\");")
					  .append("var newhref = top.window.location.href;")
					  .append(javascriptActiveBlock.toString())
    		  		  .append("top.window.location.href = newhref;");
		}
		else {
			// Ocultar el frame
			javascript.append("top.hideFrame(\"" + id + "\", \"" + StaticContext.rewritePage(pageContext, request.getContextPath(), WAIT_WINDOW) + "\");");
		}
		javascript.append("}");

		if (getEvent().equalsIgnoreCase("false"))
		{
			javascript.append("</script>");
		}
		
		JspWriter out = pageContext.getOut();
    	try
		{
    		out.write(javascript.toString());
        }
        catch (IOException e)
		{
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
    }
	
}