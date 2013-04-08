package ieci.tecdoc.sgm.consulta_telematico.web.decorators;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class NumRegistroExcelDecorator extends TableDecorator implements DisplaytagColumnDecorator {



	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum media) throws DecoratorException {
		 String decorated = (String)columnValue;
         if (media.equals(MediaTypeEnum.EXCEL) || media.equals(MediaTypeEnum.CSV)) {
             decorated= "\u00a0" + decorated;
         }

         return decorated;
	}
}
