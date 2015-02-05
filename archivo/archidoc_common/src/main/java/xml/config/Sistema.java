package xml.config;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase abstracta que almacena la información de un sistema.
 */
public class Sistema extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del sistema. */
	protected String id = null;

	/** Nombre del sistema. */
	protected String nombre = null;

	/** Clase del sistema. */
	protected String clase = null;

	/** Parámetros de configuración del sistema. */
	protected Properties parametros = new Properties();


	/**
	 * Constructor.
	 */
	public Sistema()
	{
		super();
	}


	/**
	 * Obtiene la clase del sistema.
	 * @return Clase del sistema.
	 */
	public String getClase()
	{
		return clase;
	}


	/**
	 * Establece la clase del sistema.
	 * @param clase Clase del sistema.
	 */
	public void setClase(String clase)
	{
		this.clase = clase;
	}


	/**
	 * Obtiene el identificador del sistema.
	 * @return Identificador del sistema.
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * Establece el identificador del sistema.
	 * @param id Identificador del sistema.
	 */
	public void setId(String id)
	{
		this.id = id;
	}


	/**
	 * Obtiene el nombre del sistema.
	 * @return Nombre del sistema.
	 */
	public String getNombre()
	{
		return nombre;
	}


	/**
	 * Establece el nombre del sistema.
	 * @param nombre Nombre del sistema.
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}


	/**
	 * Obtiene los parámetros de configuración del sistema.
	 * @return Parámetros de configuración del sistema.
	 */
	public Properties getParametros()
	{
		return parametros;
	}


	/**
	 * Añade un parámetro de configuración.
	 * @param nombre Nombre del parámetro.
	 * @param valor Valor del parámetro.
	 */
	public void addParametro(String nombre, String valor)
	{
		parametros.setProperty(nombre, valor);
	}


	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @param nombreTag Nombre del Tag.
	 * @return Representación del objeto.
	 */
	protected String toXML(int indent, final String nombreTag)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<" + nombreTag + ">");
		xml.append(Constants.NEWLINE);

		// Id
		xml.append(tabs + "  <Id>");
	    xml.append(id != null ? id : "");
	    xml.append("</Id>");
		xml.append(Constants.NEWLINE);

		// Nombre
		if (StringUtils.isNotBlank(nombre))
		{
			xml.append(tabs + "  <Nombre>");
		    xml.append(nombre);
		    xml.append("</Nombre>");
			xml.append(Constants.NEWLINE);
		}

		// Clase
		xml.append(tabs + "  <Clase>");
	    xml.append(clase != null ? clase : "");
	    xml.append("</Clase>");
		xml.append(Constants.NEWLINE);

		// Parametros
		Enumeration keys = parametros.keys();
		String key = null, value = null;
		while (keys.hasMoreElements())
		{
			key = (String) keys.nextElement();
			value = parametros.getProperty(key, "");

			xml.append(tabs + "  <init-param>" + Constants.NEWLINE);
			xml.append(tabs + "    <param-name>");
			xml.append(key);
			xml.append("</param-name>" + Constants.NEWLINE);
			xml.append(tabs + "    <param-value>");
			xml.append(value);
			xml.append("</param-value>" + Constants.NEWLINE);
		    xml.append(tabs + "  </init-param>" + Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + nombreTag + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}


	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		return toXML(indent, "Sistema");
	}

}
