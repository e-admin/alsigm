package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la información de un superusuario.
 */
public class Superusuario extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String clave = null;

	/**
	 * Constructor por defecto
	 */
	public Superusuario()
	{
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * @return el clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave el clave a establecer
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		if (StringUtils.isNotEmpty(nombre)){
			// Tag de inicio
			xml.append(tabs + "<Superusuario>");
			xml.append(Constants.NEWLINE);

			// Nombre
			xml.append(tabs + "  <Usuario>");
		    xml.append(nombre != null ? nombre : "");
		    xml.append("</Usuario>");
			xml.append(Constants.NEWLINE);

			// Contraseña
			xml.append(tabs + "  <Clave>");
		    xml.append(clave != null ? clave : "");
		    xml.append("</Clave>");
			xml.append(Constants.NEWLINE);

			// Tag de cierre
			xml.append(tabs + "</Superusuario>");
			xml.append(Constants.NEWLINE);
		}
		return xml.toString();
	}

}
