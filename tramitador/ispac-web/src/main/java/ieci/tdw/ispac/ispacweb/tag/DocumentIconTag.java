package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
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
 * Muestra el icono relacionado con el documento.
 */
public class DocumentIconTag extends TagSupport {

	private static final long serialVersionUID = 7145943432596274256L;
	
	protected String imageSrc = null;
	protected String extension = null;
	protected String styleClass = null;
	
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getStyleClass() {
		return (this.styleClass);
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public int doStartTag() throws JspException {
		
		TagUtils tagUtils = TagUtils.getInstance();
		
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context = request.getContextPath();
        
        /*
        String ispacbase = (String) pageContext.getServletContext()
				.getAttribute("ispacbase");
		String skin = (String) pageContext.getServletContext().getAttribute(
				"skin");

        StringBuffer html = new StringBuffer()
        	.append("<img src=\"").append(getImageSrc(request, ispacbase, skin))
        		.append("\"");
        */
        
        StringBuffer html = new StringBuffer();
        
	    String baseurl;
	    try {
	    	baseurl = StaticContext.getInstance().getBaseUrl(context);
	    }
	    catch (ISPACException ie) {
	        pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
	        throw new JspException(ie.getMessage());
	    }
	    
    	// Href que incluya el ispacbase junto con el skin
	    String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImageSrc() + getIcon());
	    
    	html.append("<img src=\"")
    		.append(imageRealPath)
			.append("\"");

        if (styleClass != null) {
        	html.append(" class=\"").append(styleClass).append("\"");
        }

        html.append("/>");
		
        JspWriter writer = pageContext.getOut();
		try {
			writer.print(html.toString());			
		}
		catch (IOException ioe) {
			tagUtils.saveException(pageContext, ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}
	
	private String getIcon() {
		
    	String icon = null;
    	
    	if (StringUtils.isBlank(extension)) {
    		icon = "doc_unknown.gif";
    	} else if ("doc".equalsIgnoreCase(extension)) {
    		icon = "doc_doc.gif";
    	} else if ("pdf".equalsIgnoreCase(extension)) {
    		icon = "doc_pdf.gif";
    	} else if ("jpg".equalsIgnoreCase(extension)
    			|| "jpeg".equalsIgnoreCase(extension)
    			|| "jpe".equalsIgnoreCase(extension)) {
    		icon = "doc_jpg.gif";
    	} else if ("tif".equalsIgnoreCase(extension)
    			|| "tiff".equalsIgnoreCase(extension)) {
    		icon = "doc_tif.gif";
    	} else if ("zip".equalsIgnoreCase(extension)) {
    		icon = "doc_zip.gif";
    	} else if ("xls".equalsIgnoreCase(extension)) {
    		icon = "doc_xls.gif";
    	} else if ("ppt".equalsIgnoreCase(extension)) {
    		icon = "doc_ppt.gif";
    	} else if ("bmp".equalsIgnoreCase(extension)) {
    		icon = "doc_bmp.gif";
    	} else if ("exe".equalsIgnoreCase(extension)) {
    		icon = "doc_exe.gif";
    	} else if ("gif".equalsIgnoreCase(extension)) {
    		icon = "doc_gif.gif";
    	} else if ("mp3".equalsIgnoreCase(extension)) {
    		icon = "doc_mp3.gif";
    	} else if ("rar".equalsIgnoreCase(extension)) {
    		icon = "doc_rar.gif";
    	} else if ("txt".equalsIgnoreCase(extension)) {
    		icon = "doc_txt.gif";
    	} else if ("wav".equalsIgnoreCase(extension)) {
    		icon = "doc_wav.gif";
    	} else if ("htm".equalsIgnoreCase(extension)
    			|| "html".equalsIgnoreCase(extension)) {
    		icon = "doc_html.gif";
    	} else if ("odt".equalsIgnoreCase(extension)) {
    		icon = "doc_odt.gif";
    	} else {
    		icon = "doc_unknown.gif";
    	}

        return icon;
	}
	
	/*
	private String getImageSrc(HttpServletRequest request, String ispacbase, 
			String skin) {
		
		StringBuffer src = new StringBuffer();
		
		if (imageSrc.indexOf("://") < 0) {
			src.append(request.getContextPath());
		}

		if (ispacbase != null) {
            src.append("/").append(ispacbase);
		}

        src.append("/").append(skin).append(imageSrc);
    	
    	String icon = null;
    	
    	if ("doc".equalsIgnoreCase(extension)) {
    		icon = "doc_doc.gif";
    	} else if ("pdf".equalsIgnoreCase(extension)) {
    		icon = "doc_pdf.gif";
    	} else if ("jpg".equalsIgnoreCase(extension)
    			|| "jpeg".equalsIgnoreCase(extension)
    			|| "jpe".equalsIgnoreCase(extension)) {
    		icon = "doc_jpg.gif";
    	} else if ("tif".equalsIgnoreCase(extension)
    			|| "tiff".equalsIgnoreCase(extension)) {
    		icon = "doc_tif.gif";
    	} else if ("zip".equalsIgnoreCase(extension)) {
    		icon = "doc_zip.gif";
    	} else if ("xls".equalsIgnoreCase(extension)) {
    		icon = "doc_xls.gif";
    	} else if ("ppt".equalsIgnoreCase(extension)) {
    		icon = "doc_ppt.gif";
    	} else if ("bmp".equalsIgnoreCase(extension)) {
    		icon = "doc_bmp.gif";
    	} else if ("exe".equalsIgnoreCase(extension)) {
    		icon = "doc_exe.gif";
    	} else if ("gif".equalsIgnoreCase(extension)) {
    		icon = "doc_gif.gif";
    	} else if ("mp3".equalsIgnoreCase(extension)) {
    		icon = "doc_mp3.gif";
    	} else if ("rar".equalsIgnoreCase(extension)) {
    		icon = "doc_rar.gif";
    	} else if ("txt".equalsIgnoreCase(extension)) {
    		icon = "doc_txt.gif";
    	} else if ("wav".equalsIgnoreCase(extension)) {
    		icon = "doc_wav.gif";
    	} else if ("htm".equalsIgnoreCase(extension)
    			|| "html".equalsIgnoreCase(extension)) {
    		icon = "doc_html.gif";
    	} else {
    		icon = "doc_unknown.gif";
    	}

    	src.append(icon);
        
        return src.toString();
	}
	*/
	
}
