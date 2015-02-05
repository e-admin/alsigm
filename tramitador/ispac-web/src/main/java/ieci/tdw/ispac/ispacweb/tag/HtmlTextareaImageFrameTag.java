package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class HtmlTextareaImageFrameTag extends HtmlTextareaActionFrameTag {

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
		    
		    StringBuffer imageTag = new StringBuffer();
		    imageTag.append("<span style=\"margin: 0px\" id=\"imgSearch_" + getId() + "\">")
		 			.append("<img src=\"")
		 			//.append(base + getImage())
		 			.append(imageRealPath)
		 			.append("\" border=\"0\" style=\"cursor:pointer\"");
		 	
			Boolean ispac_needToConfirm = new Boolean(getNeedToConfirm());
			
			if (ispac_needToConfirm.booleanValue()) {
				imageTag.append(" onclick=\"javascript: ");
			}
			else {
				imageTag.append(" onclick=\"javascript: ispac_needToConfirm = false; ");
			}
			
			String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, getAction());
	
		 	Boolean show = new Boolean(getShowFrame());
		 	if (show.booleanValue()) {
		 		
			 	imageTag.append(getJsShowFrame() + "('" + getTarget() + "',")
			 			.append("'" + actionWithPath + "',")
			 			.append(getWidth() +",")
			 			.append(getHeight())
			 			.append(",'" + ((!StringUtils.isEmpty(getMsgConfirmKey())) ? tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey()) : "") + "'")
			 			.append(");");
		 	}
		 	else {
			 	imageTag.append(getJsExecuteFrame() + "('" + getTarget() + "',")
						.append("'" + actionWithPath + "'");
						if(!StringUtils.isEmpty(getMsgConfirmKey())){
				 			imageTag.append(",'"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey())+",'"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"'");
				 		}
				 			
				 		imageTag.append(");");	
						
		 	}
		 	
			if (ispac_needToConfirm.booleanValue()) {
				imageTag.append("\"");
			}
			else {
				imageTag.append(" ispac_needToConfirm = true;\"");
			}	
		 	
		 	if (!StringUtils.isEmpty(getTitleKeyLink())) {
		 	 	imageTag.append(" title=\"" + getTitleLink() + "\"");
		 	}
		 	
			if (!StringUtils.isEmpty(getStyleClassLink())) {
				
			    imageTag.append(" class='");
			    imageTag.append(getStyleClassLink());
			    imageTag.append("'");
			}
	
		 	imageTag.append("/>")
 					.append("</span>");
	
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