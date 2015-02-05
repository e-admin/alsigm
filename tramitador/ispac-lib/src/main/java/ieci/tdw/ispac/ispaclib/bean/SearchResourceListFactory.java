package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.digester.DigesterAnchor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

public class SearchResourceListFactory {

	public static final int REPORT_RESOURCETYPE = 1;
	public static final int SEARCH_RESOURCETYPE = 2;

	private static final String REPORT_DEFAULT_DIGESTER = "ieci/tdw/ispac/ispaclib/bean/digester/ReportResourceListRules.xml";
	private static final String SEARCH_DEFAULT_DIGESTER = "ieci/tdw/ispac/ispaclib/bean/digester/SearchResourceListRules.xml";
	
	
	/**
	 * @param resourcePath ruta completa al fichero xml con el formato.
	 * @return la lista de recursos para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchResourceListResPath(int type, String resourcePath) throws ISPACException {
		
	    try {
	        return getSearchResourceList(type, new FileInputStream(resourcePath));
	    }
	    catch(FileNotFoundException e) {
	        throw new ISPACException(e);
	    }
	}

	/**
	 * @param resource stream con el formato requerido
	 * @return la lista de recursos para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchResourceList(int type, InputStream resource) throws ISPACException {
		
		try {
			Digester digester = getDefaultDigester(type);
			return (List) digester.parse(resource);
		}
		catch (Exception e) {
			throw new ISPACException("getSearchResourceList(...)", e);
		}
	}

	/**
	 * Usa un objeto de tipo digester para crear una lista de recursos para la búsqueda
	 * La definición se encuentra en el StringReader pasado como argumento
	 * @param resource definicion de la lista
	 * @return la lista de recursos para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchResourceList(int type, StringReader resource) throws ISPACException {
		
		try {
			Digester digester = getDefaultDigester(type);
			return (List) digester.parse(resource);
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchResourceListFactory::getSearchResourceList(resource)", e);
		}
	}


	/**
	 * Devuelve una lista de recursos para la búsqueda a partir un string xml
	 * @param xml Texto xml con la definición de la lista
	 * @return la lista de recursos para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchResourceList(int type, String xml) throws ISPACException {
		
		try {
			return getSearchResourceList(type, new StringReader(xml));
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchResourceListFactory::getSearchResourceList()", e);
		}
	}

	public static Properties getSearchResourceProperties(int type, String xml) throws ISPACException {
		try {
			List list = getSearchResourceList(type, new StringReader(xml));
			return getProperties(list);
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchResourceListFactory::getSearchResourceProperties()", e);
		}
	}	
	
	public static Properties getSearchResourceProperties(int type, File xml) throws ISPACException {
		try {
			List list = getSearchResourceList(type, new FileInputStream(xml));
			return getProperties(list);
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchResourceListFactory::getSearchResourceProperties()", e);
		}
	}	

	
	private static Properties getProperties(List list) {
		Properties properties = new Properties();
		if (list != null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SearchResourceBean searchResourceBean = (SearchResourceBean) iterator.next();
				properties.put(searchResourceBean.getLocale() + "." + searchResourceBean.getKey(), searchResourceBean.getValue());
				if (searchResourceBean.isDefault()){
					properties.put(searchResourceBean.getKey(), searchResourceBean.getValue());
				}
			}
		}
		return properties;
	}

	private static Digester getDefaultDigester(int type) {
		
		String resource = (type == REPORT_RESOURCETYPE) ? REPORT_DEFAULT_DIGESTER : SEARCH_DEFAULT_DIGESTER;
		
		URL rulesUrl = DigesterAnchor.class.getClassLoader().getResource(resource);
		return DigesterLoader.createDigester(rulesUrl);
	}


	
}
