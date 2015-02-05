package ieci.tdw.ispac.ispacweb.decorators;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class BooleanDecorator implements DisplaytagColumnDecorator {

    
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
    
        if (columnValue == null)
        	return "";
        
        String value = StringUtils.strip(columnValue.toString(), "\r\n\t");
        
    	if (value.equalsIgnoreCase("true"))
    		return "S";
    	return "N";
    }
	
}
