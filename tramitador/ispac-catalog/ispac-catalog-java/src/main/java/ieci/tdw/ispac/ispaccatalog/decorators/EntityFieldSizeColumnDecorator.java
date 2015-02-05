package ieci.tdw.ispac.ispaccatalog.decorators;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class EntityFieldSizeColumnDecorator implements DisplaytagColumnDecorator {
    
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
    
        if (columnValue != null) {
        	
        	String value = StringUtils.strip(columnValue.toString(), "\r\n\t");
        	int size = 0;
        	
        	try {
				size = Integer.parseInt(value);
			}
        	catch (NumberFormatException e) {
        	}
        	
        	if (size > 0) {
        		
        		return columnValue;
        	}
        }
        
        return "";
    }
	
}
