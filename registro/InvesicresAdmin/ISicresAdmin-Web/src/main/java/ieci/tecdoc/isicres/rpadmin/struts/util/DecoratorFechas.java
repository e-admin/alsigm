package ieci.tecdoc.isicres.rpadmin.struts.util;

import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import es.ieci.tecdoc.isicres.admin.base.datetime.DateTimeUtil;

public class DecoratorFechas implements DisplaytagColumnDecorator {

	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {

		if (arg0 == null) {
			return "";
		} else {
			return DateTimeUtil.getDateTime((Date) arg0, "dd/MM/yyyy");
		}
	}

}
