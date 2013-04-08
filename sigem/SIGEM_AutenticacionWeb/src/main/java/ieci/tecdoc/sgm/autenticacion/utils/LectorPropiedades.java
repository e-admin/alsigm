package ieci.tecdoc.sgm.autenticacion.utils;

import java.util.Properties;
import java.util.ResourceBundle;

public class LectorPropiedades {
	
	public static String getPropiedad(String prop){
		ResourceBundle application = ResourceBundle.getBundle("resources.config");
        try{
        	return application.getString(prop);
        }catch(Exception e){
        	return "";
        }
	}
}
