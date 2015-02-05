package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class HtmlTextareaMultivalueTag extends HtmlTextareaTag {
	public String divWidth = null;
	public String divHeight = MultivalueHelper.DEFAULT_DIV_HEIGHT;
	
	
	   public int doStartTag() throws JspException {
	    	calculateReadonly();
	    	String[] values = (String[])TagUtils.getInstance().lookup(pageContext, name, property ,null);
	    	StringBuffer results = new StringBuffer();
	        String propertyName =  StringUtils.substring(property, property.indexOf("(")+1, property.length()-1);
	        propertyName =  StringUtils.replace(propertyName, ":", "_");
	    	
	    	//Se genera código para los botones que gestionan los campos (Insertar, Eliminar)
	        if (!getReadonly()){
		    	results.append(MultivalueHelper.composeDeleteElement(propertyName, message(null,"forms.button.delete"), message(null,"forms.alert.delete.elements.nocheck"), message(null,"common.alert"),message(null,"common.message.ok"),message(null,"common.message.cancel"), getTabindex()));
		    	results.append(MultivalueHelper.composeInsertMultivalueTextareaElement(propertyName, property, getStyleClass(), message(null,"forms.button.insertar"), getTabindex()));
	        }
		    	results.append(MultivalueHelper.composeHeaderDivMultivalueElement(propertyName, divHeight, divWidth));

	    	//Para cada valor se genera el código html para un checkbox y para el campo, encuadrado todo dentro de un div
	    	int i = 0;
	    	for (; i < values.length; i++) {
	    		this.setValue(values[i]);
	    		results.append(MultivalueHelper.composeHeaderDivInputElement(propertyName, i));
		    	if (!getReadonly()){
		    		results.append(MultivalueHelper.composeHeaderCheckboxMultivalueElement(propertyName, i, getTabindex()));
		    	}
		    	
		    	String tagTabIndex = getTabindex();
		 		if (tagTabIndex != null) {
		 			setTabindex(String.valueOf(Integer.parseInt(tagTabIndex) + 3 + 3*i));
		 		}
		    	
	    		results.append(renderTextareaElement());

	    		setTabindex(tagTabIndex);
	    		
	            results.append("</div>");
			}
	    	results.append("<script>max" + propertyName + "=" + (i+1) + "</script>");
	    	results.append("</div>");

	    	TagUtils.getInstance().write(this.pageContext, results.toString());
	    	
	    	return (EVAL_BODY_TAG);
	    }	
	
	public String getDivWidth() {
		return divWidth;
	}

	public void setDivWidth(String divWidth) {
		MultivalueHelper.calculateDivWidth(divWidth);
		this.divWidth = divWidth;
	}

	public String getDivHeight() {
		return divHeight;
	}

	public void setDivHeight(String divHeight) {
		MultivalueHelper.calculateDivHeight(divHeight);
		this.divHeight = divHeight;
	}	
}
