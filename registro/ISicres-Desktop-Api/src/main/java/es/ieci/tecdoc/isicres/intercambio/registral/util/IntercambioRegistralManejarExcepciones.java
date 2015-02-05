package es.ieci.tecdoc.isicres.intercambio.registral.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.LocaleUtils;

import es.ieci.tecdoc.fwktd.core.locale.web.LocaleUtil;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;

public class IntercambioRegistralManejarExcepciones {

	private static IntercambioRegistralManejarExcepciones instance = null;
	private static String DEFAULT_LOCALE = "";
	private static String PREFIX = "intercambioRegistral.error.";
	private static final String resourcesFile = "resources/ISicres-IntercambioRegistralErrores";
	private static Map<String, ResourceBundle> resourcesBundles = new HashMap<String, ResourceBundle>();
	private IntercambioRegistralManejarExcepciones(){};

	public static IntercambioRegistralManejarExcepciones getInstance(){
		if(instance==null)
		{
			instance = new IntercambioRegistralManejarExcepciones();

		}
		return instance;
	}

	private String getMessage(String locale,String key){
		if(locale==null)
		{
			locale= DEFAULT_LOCALE;
		}
		if(!resourcesBundles.containsKey(locale)){
			resourcesBundles.put(DEFAULT_LOCALE, ResourceBundle.getBundle(resourcesFile));
		}
		return resourcesBundles.get(DEFAULT_LOCALE).getString(key);
	}


	public String manejarExcepcion(IntercambioRegistralException ex)
	{
		return getMessage(null,PREFIX+ex.getExceptionCode());
	}

	public String manejarExcepcion(IntercambioRegistralException ex, Locale locale)
	{
		return getMessage(locale.toString(),PREFIX+ex.getExceptionCode());
	}
}
