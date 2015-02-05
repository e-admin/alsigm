package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.builders.JSPBuilder;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class HtmlTextMultivalueImageFrameTag extends HtmlTextImageFrameTag {

	public String propertyDestination = null;
	private String[] actions = null;
	private String tableValidationType = null;


	public String divWidth = null;
	
	public String divHeight = MultivalueHelper.DEFAULT_DIV_HEIGHT;

	
	

	public int doStartTag() throws JspException {
    	String[] values = (String[])TagUtils.getInstance().lookup(pageContext, name, propertyDestination ,null);
    	actions = new String[values.length];
		// Sólo lectura en el campo de texto
    	if (getReadonly()) {
    		setStyleClass(getStyleClassReadonly());
    	}
		
		// Sólo lectura en el control
		if (!isReadonlyTag()) {

			// Sólo lectura en el formulario
			String isReadonly = null;
			try {
				isReadonly = (String) TagUtils.getInstance().lookup(pageContext, getName(), getPropertyReadonly(), null);
			}
			catch (JspException e) {
			}
    	
	    	if ((isReadonly != null) &&
	    		(isReadonly.equals("true"))) {
	    		
	    		setReadonlyTag(true);
	    		
	    		// Comprobar si el control permite escribir en el campo de texto (no es de sólo lectura)
	    		if (!getReadonly()) {
	    			setReadonly(true);
	    			setStyleClass(getStyleClassReadonly());	
	    		}
	    	}
		}
		else if (!getReadonly()) {
			setReadonly(true);
			setStyleClass(getStyleClassReadonly());
		}

		int i = 0;
		// Crea el mapa de parámetros asociado al tag
		pageContext.setAttribute(getId(), new HashMap());
    	for (; i < values.length; i++) {
			actions[i] = action;
			// Incluye en la petición el nombre del frame
			if (getAction().indexOf("?" ) == -1)	{
				actions[i] += "?parameters=" + getId();
			}
			else {
				actions[i] += "&parameters=" + getId();
			}
			// Incluye en la petición el campo de entrada
			if (getInputField() != null) {
				actions[i] += "&field=" + getInputField();
			}
			if (getCaptionKey() != null) {
				actions[i] += "&caption=" + getCaptionKey();
			}
			actions[i] += "&multivalueId=" + (i+1);
    	}

		return EVAL_BODY_INCLUDE;
	}

	
	public int doAfterBody() throws JspException {
	 	return SKIP_BODY;
	}
	

	public int doEndTag() throws JspException {
		
    	String[] values = (String[])TagUtils.getInstance().lookup(pageContext, name, propertyDestination ,null);
    	String[] substitutes = (String[])TagUtils.getInstance().lookup(pageContext, name, property ,null);
    	StringBuffer results = new StringBuffer();
    	String propertyName =  StringUtils.substring(propertyDestination, propertyDestination.indexOf("(")+1, propertyDestination.length()-1);
        propertyName =  StringUtils.replace(propertyName, ":", "_");
    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String context = request.getContextPath();
		
	    String baseurl;
	    TagUtils tagUtils = TagUtils.getInstance();
		
		if (!StringUtils.isEmpty(getTitleKeyLink())) {
			setTitleLink(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyLink()));
		}
	    try {
	    	baseurl = StaticContext.getInstance().getBaseUrl(context);
	    }
	    catch (ISPACException ie) {
	        pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
	        throw new JspException(ie.getMessage());
	    }		

    	String base = StaticContext.rewriteHref(pageContext, baseurl, "");

	    String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());
	    String pathDeleteImg = base + getImageDelete(); 
	    
		if (!isReadonlyTag()){
			results.append(MultivalueHelper.composeDeleteElement(propertyName, message(null,"forms.button.delete"), message(null,"forms.alert.delete.elements.nocheck"),message(null,"common.alert"),message(null,"common.message.ok"),message(null,"common.message.cancel"), getTabindex()));
			results.append(MultivalueHelper.composeInsertMultivalueImageFrameSubstituteElement(tableValidationType, propertyName, propertyDestination, property ,StaticContext.rewriteAction((request.getRequestURL()).toString(), context, action) , imageRealPath,  getSize(), getMaxlength(), getStyleClass(), getShowDelete(), pathDeleteImg, message(null,"forms.button.seleccionar"), message(null,"forms.hint.delete.selected.elements") , message(null,"forms.button.insertar"), getTabindex()));
		}
		results.append(MultivalueHelper.composeHeaderDivMultivalueElement(propertyName, divHeight, divWidth));
		
    	int i = 0;
    	Map parameters = (HashMap) pageContext.findAttribute(getId());
    	for (; i < values.length; i++) {
			if (StringUtils.equalsIgnoreCase(tableValidationType, JSPBuilder.TABLE_VALIDATION_SUBSTITUTE)){
				this.setValue(substitutes[i]);
			}else{
				this.setValue(values[i]);
			}
//    		if (!isReadonlyTag()){
//    			results.append(MultivalueHelper.composeHeaderDivInputElement(propertyName, i));
//    			if (StringUtils.equalsIgnoreCase(tableValidationType, JSPBuilder.TABLE_VALIDATION_SUBSTITUTE)){
//    				results.append(MultivalueHelper.composeReadOnlyInputElemenbt(propertyDestination, i, values[i]));
//    			}
//    		}
			results.append(MultivalueHelper.composeHeaderDivInputElement(propertyName, i));
			if (StringUtils.equalsIgnoreCase(tableValidationType, JSPBuilder.TABLE_VALIDATION_SUBSTITUTE)){
				results.append(MultivalueHelper.composeReadOnlyInputElemenbt(propertyDestination, i, values[i]));
			}
			if (!isReadonlyTag()){
				results.append(MultivalueHelper.composeHeaderCheckboxMultivalueElement(propertyName, i, getTabindex()));
			}
			
			
            //Se establece el valor del atributo id en el control html
    		setStyleId(StringUtils.replace(property, ")", "_"+(i+1)) +")");
            results.append(this.renderInputElement());
    		if (!isReadonlyTag()) {
    			//doAfterBody

    			Boolean ispac_needToConfirm = new Boolean(getNeedToConfirm());
    			String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, actions[i]);
    		 
    		    // Href que incluya el ispacbase junto con el skin
    		    StringBuffer imageTag = new StringBuffer();
    		    
    		    imageTag.append("<span style=\"margin: 0px\" id=\"imgSearch_" + getId()+"_"+(i+1) + "\">");
    		    
    		    if (getImageTabIndex().equals("true")) {
    		    	
    	 			// Por la imagen pasa el tabulador -> enlace que ejecuta el javascript (a con href y onclick)
    	 			imageTag.append("<a href=\"javascript: ");
    	 			
    				if (!ispac_needToConfirm.booleanValue()) {
    					imageTag.append("ispac_needToConfirm = true; ");
    				}
    	 		
	    		 	Boolean show = new Boolean(getShowFrame());
	    		 	if (show.booleanValue()) {
	    		 		
	    			 	imageTag.append(getJsShowFrame() + "('" + getTarget() + "',")
	    			 			//.append("'" + actionWithPath + "'")
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
    			 	imageTag.append("\"");
    			 	
    				if (!StringUtils.isEmpty(getStyleClassLink())) {
    					
    				    imageTag.append(" class='");
    				    imageTag.append(getStyleClassLink());
    				    imageTag.append("' ");
    				}
    		 		
    				if (!ispac_needToConfirm.booleanValue()) {
    					imageTag.append(" onclick=\"javascript: ispac_needToConfirm = false;\" ");
    				}
    				
    				imageTag.append(">")
    		 			.append("<img src=\"")
    		 			.append(imageRealPath)
    		 				.append("\" border=\"0\"");
    		 	
    			 	if (!StringUtils.isEmpty(getTitleKeyLink())) {
    			 	 	imageTag.append(" title=\"" + getTitleLink() + "\"");
    			 	}
    		 		
    		 		if (getTabindex() != null) {
    			 		
    		 			imageTag.append(" tabindex=\"")
    		 					.append(Integer.parseInt(getTabindex()) + 3 + 3*i)
    		 					.append("\"");
    		 		}
    		 		
    		 		imageTag.append(" /></a>");
    		    }
    		    else {
    		    	// Por la imagen no pasa el tabulador -> imagen que ejecuta el javascript (img con onclick)
    		 		imageTag.append("<img src=\"")
    		 				.append(imageRealPath)
    		 				.append("\" border=\"0\" style=\"cursor:pointer\"");
    			
    			if (ispac_needToConfirm.booleanValue()) {
    				imageTag.append(" onclick=\"javascript: ");
    			}
    			else {
    				imageTag.append(" onclick=\"javascript: ispac_needToConfirm = false; ");
    			}
    			
    		 	Boolean show = new Boolean(getShowFrame());
    		 	if (show.booleanValue()) {
    		 		
    			 	imageTag.append(getJsShowFrame() + "('" + getTarget() + "',")
    			 			//.append("'" + actionWithPath + "'")
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
    	
	    		 	imageTag.append("/>");
    		    }
    		 	
    		    imageTag.append("</span>");

   		 		results.append(imageTag.toString());

   		 		//doEndTag
    		    if (getShowDelete().equals("true")) {
    		        // Href que incluya el ispacbase junto con el skin
    		    	results.append(generateMultivalueDeleteButtonJS(tagUtils, base, parameters, i+1));
    		    }    		 	
    	    }
    	    
    		results.append("</div>");
    	    
    	}
		if (!isReadonlyTag())
			results.append( generateMultivalueDeleteFunctionJS(tagUtils,(HashMap) pageContext.findAttribute(getId())) );
    	
	    if (parameters != null) {
			HttpSession session = pageContext.getSession();
			if (session != null) {
				session.setAttribute(getId(), parameters);
			}
	    }
    	
    	results.append("<script>max" + propertyName + "=" + (i+1) + "</script>");
		results.append("</div>");
		TagUtils.getInstance().write(this.pageContext, results.toString());
		
	 	return SKIP_BODY;
	}
	public String getDivWidth() {
		return divWidth;
	}
	public void setDivWidth(String divWidth) {
		this.divWidth = MultivalueHelper.calculateDivWidth(divWidth);
	}

	public String getDivHeight() {
		return divHeight;
	}

	public void setDivHeight(String divHeight) {
		this.divHeight = MultivalueHelper.calculateDivHeight(divHeight);
	}

	public String getPropertyDestination() {
		return propertyDestination;
	}

	public void setPropertyDestination(String propertyDestination) {
		this.propertyDestination = propertyDestination;
	}

	public String getTableValidationType() {
		return tableValidationType;
	}

	public void setTableValidationType(String tableValidationType) {
		this.tableValidationType = tableValidationType;
	}
}