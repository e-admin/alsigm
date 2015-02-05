package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class LayerTag extends TagSupport
{
	private static final long serialVersionUID = 1L;
	
    protected String styleClass = null;
    protected String msgKey = null;
    protected String styleClassMsg = null;
    protected String showCloseLink = "false";

    public LayerTag()
    {
        super();
        setId("layer");
    }

    public String getStyleClass()
    {
        return this.styleClass;
    }

    public void setStyleClass(String styleClass)
    {
        this.styleClass = styleClass;
    }

    public int doStartTag() throws JspException {
    	
    	/*String style = null;
    	
        if (getStyleClass() == null) {
        	
        	style = "display:none;z-index:1000;background:white;filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;";
        }
        */
    	StringBuffer frameTag = new StringBuffer();
    	
    	frameTag.append("<div id=\"")
    			.append(getId())
    			.append("\"");
    	
    	if (getStyleClass() == null) {
    		
    		frameTag.append(" style=\"display:none;z-index:1000;background:white;filter:alpha(opacity=80);-moz-opacity:.80;opacity:.80;");
    	}
    	else {
    		frameTag.append(" class=\"")
					.append(getStyleClass());
    	}
    	frameTag.append("\"/>");
        
    	if (StringUtils.isNotEmpty((String)pageContext.getAttribute("msgKey", PageContext.REQUEST_SCOPE))){
    		setMsgKey((String)pageContext.getAttribute("msgKey", PageContext.REQUEST_SCOPE));
    	}
    	TagUtils tagUtils = TagUtils.getInstance();
        if (StringUtils.isNotEmpty(getMsgKey())) {
        	
        if (StringUtils.equalsIgnoreCase(getShowCloseLink(), "true")){
        		//frameTag.append("<br/><br/><input type=\"button\" value='" + tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.close")  + "' class=\"form_button_layer\" onclick=\"javascript:hideLayer('" + getId()+ "')\"/>");
    	        frameTag.append("<div id=\"contenido\" class=\""+getStyleClassMsg()+"\" ><div class=\"ficha\">")
    	        		.append("<div class=\"encabezado_ficha\"><div class=\"titulo_ficha\">")
    	        		.append("<div class=\"acciones_ficha\">")
    	        		.append("<a href=\"#\" id=\"btnCancel\" onclick=\"javascript:hideLayer('" + getId()+ "')\"class=\"btnCancel\">")
        				.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.close")+"</a></div></div></div>")
        				.append("<div class=\"cuerpo_ficha\"><div class=\"seccion_ficha\"><p><label class=\"popUp\">")
        				.append("<nobr>"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgKey())+"</nobr></label>")
        				.append("</p></div></div></div></div>");
        							
       }
        	else{
		        		  frameTag.append("<div id=\"contenido\" class=\""+getStyleClassMsg()+"\" ><div class=\"ficha\">")
			        	.append("<div class=\"encabezado_ficha\"><div class=\"titulo_ficha\">")
			        	.append("<div class=\"acciones_ficha\">")
		  				.append("</div></div></div>")
		  				.append("<div class=\"cuerpo_ficha\"><div class=\"seccion_ficha\"><p><label class=\"popUp\">")
		  				.append("<nobr>"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgKey())+"</nobr></label>")
		  				.append("</p></div></div></div></div>");
        	
        	
        	/*
        	frameTag.append("<div");
        	
			if (StringUtils.isNotEmpty(getStyleClassMsg())) {
				
				frameTag.append(" class=\"");
				frameTag.append(getStyleClassMsg());
				frameTag.append("\"");
			}

			
			frameTag.append(">")
					.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgKey()));

			if (StringUtils.equalsIgnoreCase(getShowCloseLink(), "true")){
	        	frameTag.append("<br/><br/><input type=\"button\" value='" + tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.close")  + "' class=\"form_button_layer\" onclick=\"javascript:hideLayer('" + getId()+ "')\"/>");
	        	//frameTag.append("<br/><a class=\"inprogress\" href=\"javascript:hideLayer('" + getId()+ "')\">" + tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.close")  + "</a>");
	        	//frameTag.append("<br/><input type=\"button\" value='" + tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.close")  + "' class=\"form_button_inprogress\" onclick=\"javascript:hideLayer('" + getId()+ "')\"/>");
	        }
			frameTag.append("</div>");
			
			
			*/
        	}
        
        }

        
    	frameTag.append("</div>");
        
        JspWriter out = pageContext.getOut();
        
        try {
            out.write(frameTag.toString());
        }
        catch (IOException e) {
        	
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
    }

	/**
	 * @return Returns the msgKey.
	 */
	public String getMsgKey() {
		return msgKey;
	}
	/**
	 * @param msgKey The msgKey to set.
	 */
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	/**
	 * @return Returns the styleClassMsg.
	 */
	public String getStyleClassMsg() {
		return styleClassMsg;
	}
	/**
	 * @param styleClassMsg The styleClassMsg to set.
	 */
	public void setStyleClassMsg(String styleClassMsg) {
		this.styleClassMsg = styleClassMsg;
	}

	public String getShowCloseLink() {
		return showCloseLink;
	}

	public void setShowCloseLink(String showCloseLink) {
		this.showCloseLink = showCloseLink;
	}
	
}