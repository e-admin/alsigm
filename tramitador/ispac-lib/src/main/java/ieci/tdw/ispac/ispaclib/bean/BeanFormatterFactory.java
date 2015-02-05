/*
 * Created on 17-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.digester.DigesterAnchor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

public class BeanFormatterFactory
{
	/**
	 * Constructor vacio
	 */
	public BeanFormatterFactory() throws ISPACException
	{
	}

	/**
	 * @param resourcePath ruta completa al fichero xml con el formato.
	 * @return el objeto BeanFormatter obtenido del recurso
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatterResPath(String resourcePath)
			throws ISPACException
	{
	    try
	    {
	        return getBeanFormatter(new FileInputStream(resourcePath));
	    }
	    catch(FileNotFoundException e)
	    {
	        throw new ISPACException(e);
	    }
	}


	/**
	 * @param resource stream con el formato requerido
	 * @return el objeto BeanFormatter obtenido del recurso
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatter(InputStream resource)
			throws ISPACException
	{
		try {
			Digester digester = getDefaultDigester();
			return  (BeanFormatter) digester.parse(resource);
		} catch (Exception e) {
			throw new ISPACException("getBeanFormatter(...)", e);
		}
	}

	/**
	 * Usa un objeto de tipo digester para crear una instancia de BeanFormatter.
	 * La definición del BeanFormatter se encuentra en el StringReader pasado
	 * como argumento
	 * @param resource definicion del beanformatter
	 * @return BeanFormatter
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatter (StringReader resource) throws ISPACException
	{
		try
		{
			Digester digester = getDefaultDigester();
			return (BeanFormatter) digester.parse(resource);
		}
		catch (Exception e)
		{
			throw new ISPACException ("Error en BeanFormatterFactory::newInstance(StringReader resource)", e);
		}
	}

	/**
	 * Devuelve un BeanFormatter a partir un string xml q contiene la configuracion
	 * del beanformatter
	 * @param xml Texto xml con la definición del formato
	 * @return BeanFormatter
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatter (String xml) throws ISPACException
	{
		try
		{
			return getBeanFormatter(new StringReader(xml));
		}
		catch (Exception e)
		{
			throw new ISPACException ("Error en BeanFormatterFactory::getBeanFormatter()", e);
		}
	}

	private Digester getDefaultDigester()
	{
		URL rulesUrl = DigesterAnchor.class.getClassLoader().getResource("ieci/tdw/ispac/ispaclib/bean/digester/BeanFormatterRules.xml");
		return DigesterLoader.createDigester(rulesUrl);
	}
}
