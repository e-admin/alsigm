package ieci.tdw.applets.ispacdocapplet.messages;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "ieci.tdw.applets.ispacdocapplet.messages";

	public Messages() {
		super();
	}

	public static String getString(Locale locale, String key) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}
}