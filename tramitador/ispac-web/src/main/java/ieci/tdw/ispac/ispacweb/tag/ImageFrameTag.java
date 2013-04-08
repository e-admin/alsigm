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

public class ImageFrameTag extends ActionFrameTag {

  protected String image = null;

  public String getImage() {
    return this.image;
  }
  public void setImage( String image) {
    this.image = image;
  }

  public int doAfterBody() throws JspException {

  	TagUtils tagUtils = TagUtils.getInstance();
  	String title = null;

	if (!StringUtils.isEmpty(getTitleKey())) {
		title = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKey());
	}
	
	//String ispac = (String) pageContext.getServletContext().getAttribute("ispacbase");
    //String skin = (String) pageContext.getServletContext().getAttribute("skin");
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
	//String base = getBasePath(request.getContextPath(), ispac, skin);
    String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());

    StringBuffer imageTag = new StringBuffer();
    
 	imageTag.append("<img src=\"")
 			//.append(base + getImage())
 			.append(imageRealPath)
 			.append("\" border=\"0\" style=\"cursor:pointer\"");
 	
	Boolean ispac_needToConfirm = new Boolean(getNeedToConfirm());
	
	if (ispac_needToConfirm.booleanValue()) {
		imageTag.append(" onclick=\"javascript:");
	}
	else {
		imageTag.append(" onclick=\"javascript: ispac_needToConfirm = false;");
	}

	Boolean show = new Boolean(getShowFrame());
	imageTag.append(generateShowExecuteFrameJS(tagUtils, show));
 	
	if (ispac_needToConfirm.booleanValue()) {
		imageTag.append("\"");
	}
	else {
		imageTag.append(" ispac_needToConfirm = true;\"");
	}	
 	
 	if (!StringUtils.isEmpty(title)) {
 	 	imageTag.append(" title=\"" + title + "\"");
 	}
 	
	if (!StringUtils.isEmpty(getStyleClass())) {
	    imageTag.append(" class='")
	    		.append(styleClass)
	    		.append("'");
	}

 	imageTag.append("/>");

 	JspWriter writer = pageContext.getOut();
 	try {
 		writer.print(imageTag.toString());
 	}
 	catch (IOException e) {
 		tagUtils.saveException(pageContext, e);
		throw new JspException(messages.getMessage("common.io", e.toString()));
 	}

 	return SKIP_BODY;
  }

  public void release() {

    super.release();
    image = null;
  }

  /*
  private String getBasePath(String base, String ispac, String skin) {
	  
  	  String sPath = base;

      if (ispac != null) {
      	sPath += "/" + ispac;
      }
      sPath += "/" + skin + "/";

      return sPath;
  }
  */
  
}