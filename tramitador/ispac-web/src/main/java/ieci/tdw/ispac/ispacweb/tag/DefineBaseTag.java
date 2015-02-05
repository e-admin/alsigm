package ieci.tdw.ispac.ispacweb.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DefineBaseTag extends BodyTagSupport
{

    protected String id = null;
    protected String value = null;

    public int doStartTag() throws JspException
    {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        String ispacbase =(String)pageContext.getServletContext().getAttribute("ispacbase");
        String skin =(String)pageContext.getServletContext().getAttribute("skin");

        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        String base = request.getContextPath();
        
        if (ispacbase != null)
        {
        	base += "/" + ispacbase;
        }

        if (skin != null)
        {
        	base += "/" + skin;
        }
        
        if (value != null)
        {
        	base += "/" + value;
        }

        base += "/";
        
        pageContext.setAttribute(id, base, PageContext.PAGE_SCOPE);

        return (EVAL_PAGE);
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void release() {

        super.release();

        id = null;
        value = null;
    }
}