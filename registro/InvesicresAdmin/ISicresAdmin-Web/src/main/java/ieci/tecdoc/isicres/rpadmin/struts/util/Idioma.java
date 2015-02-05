package ieci.tecdoc.isicres.rpadmin.struts.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;

public class Idioma {

	public static final String IDIOMA_KEY="Idioma";
	public static final String NUMIDIOMA_KEY="numIdioma";

	protected static Object[][] microsoftLocales=initMicrosoftLocales();
	protected static Map microsoftLocalesID2DefaultLocales=initMicrosoftLocalesID2DefaultLocales();


	/**
	 * Obtiene la cadena del idioma siguiendo la siguiente prioridad:
	 *  1.- del parametro de la request
	 *  2.- A partir del parametro numIdioma de la request
	 *  3.- A partir del locale de la request
	 * @param request
	 * @return
	 */
	public  String getIdioma(HttpServletRequest request){
		// Texto del idioma. Ej: EU_
		// lo cogemos del parametro de la request
		String result = RequestUtils.parseRequestParameterAsString(request,IDIOMA_KEY);

		// si no lo encuentra lo coge a partir del numIdioma que se le pasa en la request
		if (result==null){
				String numIdioma = getNumIdioma(request).toString();

				if (numIdioma != ""){
		    		if (numIdioma.equals(microsoftLocales[2][2].toString())) {
	    				result= microsoftLocales[2][0].toString();
	        		} else if (numIdioma.equals(microsoftLocales[1][2].toString())){
	    				result = microsoftLocales[1][0].toString();
	        		} else if (numIdioma.equals(microsoftLocales[3][2].toString())){
	    				result = microsoftLocales[3][0].toString();
	        		} else {
	        			result = microsoftLocales[0][0].toString();
	        		}
				}
		}

		//si sigue sin encontrarlo lo coge del locale indicado en la request
		if (result==null){

					String language = request.getLocale().getLanguage();
		    		if (language.equals("ca")) {
		    			result = microsoftLocales[2][0].toString();

		    		} else if (language.equals("eu")) {
		    			result = microsoftLocales[1][0].toString();

		    		} else if (language.equals("gl")) {
		    			result = microsoftLocales[3][0].toString();

		    		} else {
		    			result = microsoftLocales[0][0].toString();

		    		}

		}

		return result;
	}

	/**
	 * Obtiene la cadena del idioma siguiendo la siguiente prioridad:
	 *  1.- del parametro de la request
	 *  2.- A partir de la configuracion de sicres
	 *  3.- A partir del locale de la request
	 * @param request
	 * @return
	 */
	public  Long getNumIdioma(HttpServletRequest request){
		// Número del idioma. Ej: 10
		Long result = RequestUtils.parseRequestParameterAsLong(request,NUMIDIOMA_KEY);


		if (result==null){
			Object numIdioma = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_IDIOMA);
			if (!StringUtils.isEmpty(numIdioma.toString())){
				result=new Long(numIdioma.toString());
			}
		}

		//si sigue sin encontrarlo lo coge del locale indicado en la request
		if (result==null){

					String language = request.getLocale().getLanguage();
		    		if (language.equals("ca")) {
		    			result = new Long(microsoftLocales[2][2].toString());

		    		} else if (language.equals("eu")) {
		    			result = new Long(microsoftLocales[1][2].toString());

		    		} else if (language.equals("gl")) {
		    			result = new Long(microsoftLocales[3][2].toString());

		    		} else {
		    			result = new Long(microsoftLocales[0][2].toString());

		    		}

		}


		return result;
	}

	public  Locale getLocale(Long numIdioma){
		// Obtenemos el locale java para el código de idioma seleccionado por el usuario.
		Locale result= (Locale) microsoftLocalesID2DefaultLocales.get(numIdioma);
		return result;
	}

	protected static Object[][]  initMicrosoftLocales(){
		microsoftLocales = new Object[4][3];
		microsoftLocales[0][0] = "";
		microsoftLocales[0][1] = "Castellano";
		microsoftLocales[0][2] = new Long(10);
		microsoftLocales[1][0] = "EU_";
		microsoftLocales[1][1] = "Euskera";
		microsoftLocales[1][2] = new Long(45);
		microsoftLocales[2][0] = "CT_";
		microsoftLocales[2][1] = "Catal&aacute;n";
		microsoftLocales[2][2] = new Long(3);
		microsoftLocales[3][0] = "GL_";
		microsoftLocales[3][1] = "Gallego";
		microsoftLocales[3][2] = new Long(86);
		return microsoftLocales;
	}

	protected static Map initMicrosoftLocalesID2DefaultLocales(){
		List defaultLocales = new ArrayList();
		microsoftLocalesID2DefaultLocales= new HashMap();


		defaultLocales.add(new Locale("es","ES"));
		defaultLocales.add(new Locale("eu","ES"));
		defaultLocales.add(new Locale("ca","ES"));
		defaultLocales.add(new Locale("gl","ES"));


		microsoftLocalesID2DefaultLocales.put(microsoftLocales[0][2], defaultLocales.get(0));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[1][2], defaultLocales.get(1));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[2][2], defaultLocales.get(2));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[3][2], defaultLocales.get(3));


		return microsoftLocalesID2DefaultLocales;

	}
}
