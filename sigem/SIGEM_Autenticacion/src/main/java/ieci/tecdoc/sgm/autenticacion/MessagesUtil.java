/**
 * 
 */
package ieci.tecdoc.sgm.autenticacion;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class MessagesUtil {

	protected static ResourceBundleMessageSource messageSource;
	
	private static final String MESSAGES_PROPERTIES_ENTRY = "ieci.tecdoc.sgm.autenticacion.resources.Messages";

	static{
		messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGES_PROPERTIES_ENTRY);
	}
	
	private static ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_PROPERTIES_ENTRY);

	public static String getMessage(String key, Object[]args, Locale locale) {
		
		return messageSource.getMessage(key, args, locale);
		
	}

}
