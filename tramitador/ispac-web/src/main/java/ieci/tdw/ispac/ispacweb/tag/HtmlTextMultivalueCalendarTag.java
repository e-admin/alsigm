package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class HtmlTextMultivalueCalendarTag extends HtmlTextCalendarTag {


	public String divWidth = null;
	public String divHeight = MultivalueHelper.DEFAULT_DIV_HEIGHT;

	
    public int doStartTag() throws JspException {
    	
    	calculateReadonly();
    	String[] values = (String[])TagUtils.getInstance().lookup(pageContext, name, property ,null);
    	StringBuffer results = new StringBuffer();
        String propertyName =  StringUtils.substring(property, property.indexOf("(")+1, property.length()-1);
        propertyName =  StringUtils.replace(propertyName, ":", "_");
    	
        
        if (!getReadonly()){
	        //Se genera código para los botones que gestionan los campos (Insertar, Eliminar)
	    	results.append(MultivalueHelper.composeDeleteElement(propertyName, message(null,"forms.button.delete"),  message(null,"forms.alert.delete.elements.nocheck" ),message(null,"common.alert"),message(null,"common.message.ok"),message(null,"common.message.cancel"), getTabindex()));
	    	results.append(MultivalueHelper.composeMultivalueCalendarElement(propertyName, property, getSize(), getMaxlength(), getStyleClass(), getImage(), message(null,"forms.button.insertar"), getTabindex()));
        }
    	results.append(MultivalueHelper.composeHeaderDivMultivalueElement(propertyName, divHeight, divWidth));

    	//Para cada valor se genera el código html para un checkbox y para el campo, encuadrado todo dentro de un div
    	int i = 0;
    	String styleId = null;
    	for (; i < values.length; i++) {
    		this.setValue(values[i]);
    		results.append(MultivalueHelper.composeHeaderDivInputElement(propertyName, i));
	    	if (!getReadonly()){
	    		results.append(MultivalueHelper.composeHeaderCheckboxMultivalueElement(propertyName, i, getTabindex()));
	    	}
    		styleId = StringUtils.replace(property, ")", "_"+(i+1)) +")";    		
            this.setStyleId(styleId);
            
	    	String tagTabIndex = getTabindex();
	 		if (tagTabIndex != null) {
	 			setTabindex(String.valueOf(Integer.parseInt(tagTabIndex) + 3 + 3*i));
	 		}

            results.append(renderInputElement());
            
            setTabindex(tagTabIndex);
            
			if (!getReadonly()) {
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				// Ejecutar el calendario.
				
				results.append(MultivalueHelper.composeControlCalendarElement(styleId, propertyName, i, getImage(), isEnablePast(),getFormat(), getLanguage(request), getFixedX(), getFixedY()));
				
			}            
			results.append("</div>");
		}
    	results.append("<script>max" + propertyName + "=" + (i+1) + "</script>");
    	results.append("</div>");

    	TagUtils.getInstance().write(this.pageContext, results.toString());
    	
    	return (EVAL_BODY_TAG);
    }
    
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
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
}
