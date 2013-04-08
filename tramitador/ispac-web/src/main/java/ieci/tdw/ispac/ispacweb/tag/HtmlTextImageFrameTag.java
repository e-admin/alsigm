package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;
import ieci.tdw.ispac.ispacweb.util.FormUtils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class HtmlTextImageFrameTag extends HtmlTextActionFrameTag {

	protected String image = null;
	protected String jsShowFrame = "showFrame";
	protected String jsExecuteFrame = "executeFrame";
	
	/**
	 * @return Returns the image.
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image The image to set.
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	public int doAfterBody() throws JspException {
		
		if (!isReadonlyTag()) {

		  	TagUtils tagUtils = TagUtils.getInstance();
	
			if (!StringUtils.isEmpty(getTitleKeyLink())) {
				setTitleLink(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyLink()));
			}
			
			/*
		    String ispac = (String) pageContext.getServletContext().getAttribute("ispacbase");
		    String skin = (String) pageContext.getServletContext().getAttribute("skin");
		    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		    String base = getBasePath(request.getContextPath(), ispac, skin);
		    */
			
		    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		    String context = request.getContextPath();
			
		    String baseurl;
		    try {
		    	baseurl = StaticContext.getInstance().getBaseUrl(context);
		    }
		    catch (ISPACException ie) {
		        pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
		        throw new JspException(ie.getMessage());
		    }
		 
		    // Href que incluya el ispacbase junto con el skin
		    String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());
	
			
			String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, getAction());
	
		    StringBuffer imageTag = new StringBuffer();
		    String literalMsgConfirm="";
		    if(!StringUtils.isBlank(getMsgConfirmKey())){
		    	literalMsgConfirm=tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey());
		    }
		    String literalOk=tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok");
		    String literalCancel=tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel");
		    String literalConfirm=tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm");
		  
		    imageTag=FormUtils.getValidationsActionsHtml(getId(), getNeedToConfirm(), getShowFrame(), getJsShowFrame(),
		    											getTarget(), getWidth(), getHeight(), actionWithPath, 
		    											literalMsgConfirm, imageTabIndex, jsExecuteFrame, styleClassLink, 
		    											getTabindex(), getTitleKeyLink(), getTitleLink(), 
		    											literalConfirm, literalOk, literalCancel, imageRealPath);
		   
	
		 	JspWriter writer = pageContext.getOut();
	
		 	try	{
		 		writer.print(imageTag.toString());
		 	}
		 	catch (IOException e) {
		 		tagUtils.saveException(pageContext, e);
				throw new JspException(messages.getMessage("common.io", e.toString()));
		 	}
		}
		
	 	return SKIP_BODY;
	}
	
	public void release() {

	    super.release();
	    image = null;
	}
	 
	/**
	 * @return Returns the jsShowFrame.
	 */
	public String getJsShowFrame() {
		return jsShowFrame;
	}
	/**
	 * @param jsShowFrame The jsShowFrame to set.
	 */
	public void setJsShowFrame(String jsShowFrame) {
		this.jsShowFrame = jsShowFrame;
	}
	
	/**
	 * @return Returns the jsExecuteFrame.
	 */
	public String getJsExecuteFrame() {
		return jsExecuteFrame;
	}
	/**
	 * @param jsExecuteFrame The jsExecuteFrame to set.
	 */
	public void setJsExecuteFrame(String jsExecuteFrame) {
		this.jsExecuteFrame = jsExecuteFrame;
	}
	 
}