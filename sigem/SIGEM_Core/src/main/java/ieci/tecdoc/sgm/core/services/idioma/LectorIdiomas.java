package ieci.tecdoc.sgm.core.services.idioma;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class LectorIdiomas {
	private static final Logger logger = Logger.getLogger(LectorIdiomas.class);
			
	private static final String CONFIG_IDIOMA	= "configIdioma";
	
	private static final String KEY_IDIOMAS = "idiomas";
	public static final String KEY_LIST_IDIOMAS = "listadoIdiomas";
	private static final String KEY_SEPARADOR = ".";
	private static final String KEY_DESC_IDIOMA = "descripcion";
	private static final String KEY_COD_IDIOMA = "codigo";

	private static final Object KEY_LIST_IDIOMAS_PRESENTACION = "listadoIdiomasPresentacion";;
	
	
	
	private static Config configuracion;
	private static ConfigIdioma configIdioma;
	
	private static HashMap config = new HashMap();
	
	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml","Idiomas_spring.xml"});
			configIdioma = (ConfigIdioma)configuracion.getBean(CONFIG_IDIOMA);
		} catch (Exception e) {
			logger.error("Error inicializando configuración de idiomas disponibles.", e);
		}

		// Parámetros de configuración de base de datos
		String strCantidad = (String)configIdioma.getIdiomasProperties().get(KEY_IDIOMAS);
		int cantidad = 0;
		if (strCantidad != null && !"".equals(strCantidad))
			cantidad = new Integer(strCantidad).intValue();
		
		List idiomas = new ArrayList();
		for(int i=1; i<=cantidad; i++){
			String descripcion = (String)configIdioma.getIdiomasProperties().get(KEY_IDIOMAS + KEY_SEPARADOR + i + KEY_SEPARADOR + KEY_DESC_IDIOMA);
			String codigo = (String)configIdioma.getIdiomasProperties().get(KEY_IDIOMAS + KEY_SEPARADOR + i + KEY_SEPARADOR + KEY_COD_IDIOMA);
			idiomas.add(new Idioma(codigo, descripcion));
		}
		
		config.put(KEY_LIST_IDIOMAS, idiomas);
		
		
		// 	CONFIGURACIÓN DE IDIOMAS DE PRESENTACIÓN
		String strCantidadPresentacion = (String)configIdioma.getIdiomasPresentacionProperties().get(KEY_IDIOMAS);
		cantidad = 0;
		if (strCantidadPresentacion != null && !"".equals(strCantidadPresentacion))
			cantidad = new Integer(strCantidadPresentacion).intValue();
		
		List idiomasPresentacion = new ArrayList();
		for(int i=1; i<=cantidad; i++){
			String descripcion = (String)configIdioma.getIdiomasPresentacionProperties().get(KEY_IDIOMAS + KEY_SEPARADOR + i + KEY_SEPARADOR + KEY_DESC_IDIOMA);
			String codigo = (String)configIdioma.getIdiomasPresentacionProperties().get(KEY_IDIOMAS + KEY_SEPARADOR + i + KEY_SEPARADOR + KEY_COD_IDIOMA);
			idiomasPresentacion.add(new Idioma(codigo, descripcion));
		}
		
		config.put(KEY_LIST_IDIOMAS_PRESENTACION, idiomasPresentacion);
	}
		
    public static Config getConfiguracion() {
		return configuracion;
	}
    
    public static ArrayList getIdiomas(){
    	return (ArrayList)config.get(KEY_LIST_IDIOMAS);
    }
    
    public static String getMostrarComboIdiomas(){
    	return configIdioma.getMostrarComboIdioma();
    }

	public static ArrayList getIdiomasPresentacion() {
		return (ArrayList)config.get(KEY_LIST_IDIOMAS_PRESENTACION);
	}

	public static String getMostrarYGuardarIdiomaPresentacion() {
		return configIdioma.getMostrarIdiomaPresentacion();
	}
}
