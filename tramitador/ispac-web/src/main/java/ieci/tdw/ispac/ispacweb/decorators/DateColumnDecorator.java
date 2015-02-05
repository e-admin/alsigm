package ieci.tdw.ispac.ispacweb.decorators;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DateColumnDecorator implements DisplaytagColumnDecorator {
    
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
    
        if (columnValue == null)
        	return "";
        
    	DateFormat formatterComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        DateFormat formatterSimple = new SimpleDateFormat("dd/MM/yyyy");
        
        String value = StringUtils.strip(columnValue.toString(), "\r\n\t");
        Date date = null;
        
        try {
        	date = (Date)formatterComplete.parse(value);
        }
        catch (ParseException e) {
        }

        return formatterSimple.format(date);
    }
	
}