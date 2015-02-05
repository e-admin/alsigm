package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.digester.DigesterAnchor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

public class SearchActionListFactory {

	/**
	 * @param resourcePath ruta completa al fichero xml con el formato.
	 * @return la lista de acciones para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchActionListResPath(String resourcePath) throws ISPACException {
		
	    try {
	        return getSearchActionList(new FileInputStream(resourcePath));
	    }
	    catch(FileNotFoundException e) {
	        throw new ISPACException(e);
	    }
	}

	/**
	 * @param resource stream con el formato requerido
	 * @return la lista de acciones para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchActionList(InputStream resource) throws ISPACException {
		
		try {
			Digester digester = getDefaultDigester();
			return (List) digester.parse(resource);
		}
		catch (Exception e) {
			throw new ISPACException("getSearchActionList(...)", e);
		}
	}

	/**
	 * Usa un objeto de tipo digester para crear una lista de acciones para la búsqueda
	 * La definición se encuentra en el StringReader pasado como argumento
	 * @param resource definicion de la lista
	 * @return la lista de acciones para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchActionList(StringReader resource) throws ISPACException {
		
		try {
			Digester digester = getDefaultDigester();
			return (List) digester.parse(resource);
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchActionListFactory::newInstance(StringReader resource)", e);
		}
	}

	/**
	 * Devuelve una lista de acciones para la búsqueda a partir un string xml
	 * @param xml Texto xml con la definición de la lista
	 * @return la lista de acciones para la búsqueda
	 * @throws ISPACException
	 */
	public static List getSearchActionList(String xml) throws ISPACException {
		
		try {
			return getSearchActionList(new StringReader(xml));
		}
		catch (Exception e) {
			throw new ISPACException ("Error en SearchActionListFactory::getBeanFormatter()", e);
		}
	}

	private static Digester getDefaultDigester() {
		
		URL rulesUrl = DigesterAnchor.class.getClassLoader().getResource("ieci/tdw/ispac/ispaclib/bean/digester/SearchActionListRules.xml");
		return DigesterLoader.createDigester(rulesUrl);
	}
	
}
