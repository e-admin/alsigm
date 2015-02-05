package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

public class FrameTag extends TagSupport {

	public static final String WAIT_WINDOW = "wait.jsp";

	public FrameTag()
	{
	    super();
	    setId("workframe");
	}

	public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        JspWriter out = pageContext.getOut();
        try {
            // Calculo de la etiqueta base
        	/*
            String ispacbase = (String) pageContext.getServletContext().getAttribute("ispacbase");
            String src = request.getContextPath();
            
        	if (!StringUtils.isEmpty(ispacbase)) {
        		src = src + "/" + ispacbase + "/";
        	}
            
        	String frameTag = "<iframe src='" + src + WAIT_WINDOW + "'"
        	*/
        	String frameTag ="<iframe src='" + StaticContext.rewritePage(pageContext, request.getContextPath(), WAIT_WINDOW) + "'"
				            + " id='" + id + "'"
				            + " name='" + id + "'"
							+ " style='visibility:hidden;z-index:1024;border:none;height:0px' allowtransparency='true'>"
							+ "</iframe>";
        	
            out.write(frameTag);
        }
        catch (IOException e)
		{
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
    }
}
