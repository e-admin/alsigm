package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la información de un tipo de usuario.
 */
public class Usuario extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tipo = null;
	private String nombre = null;
	private String idSistGestorUsr = null;
	private String idSistGestorOrg = null;


	public Usuario()
	{
	}


	/**
	 * @return Returns the idSistGestorOrg.
	 */
	public String getIdSistGestorOrg()
	{
		return idSistGestorOrg;
	}
	/**
	 * @param idSistGestorOrg The idSistGestorOrg to set.
	 */
	public void setIdSistGestorOrg(String idSistGestorOrg)
	{
		this.idSistGestorOrg = idSistGestorOrg;
	}
	/**
	 * @return Returns the idSistGestorUsr.
	 */
	public String getIdSistGestorUsr()
	{
		return idSistGestorUsr;
	}
	/**
	 * @param idSistGestorUsr The idSistGestorUsr to set.
	 */
	public void setIdSistGestorUsr(String idSistGestorUsr)
	{
		this.idSistGestorUsr = idSistGestorUsr;
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
	 * @return Returns the tipo.
	 */
	public String getTipo()
	{
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
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

		// Tag de inicio
		xml.append(tabs + "<Usuario Tipo=\"");
	    xml.append(tipo != null ? tipo : "");
	    xml.append("\">");
		xml.append(Constants.NEWLINE);

		// Nombre tipo
		xml.append(tabs + "  <Nombre_Tipo>");
	    xml.append(nombre != null ? nombre : "");
	    xml.append("</Nombre_Tipo>");
		xml.append(Constants.NEWLINE);

		// idSistGestorUsr
		if (StringUtils.isNotBlank(idSistGestorUsr))
		{
			xml.append(tabs + "  <Id_SistGestor_Usr>");
		    xml.append(idSistGestorUsr);
		    xml.append("</Id_SistGestor_Usr>");
			xml.append(Constants.NEWLINE);
		}

		// idSistGestorOrg
		if (StringUtils.isNotBlank(idSistGestorOrg))
		{
			xml.append(tabs + "  <Id_SistGestor_Org>");
		    xml.append(idSistGestorOrg);
		    xml.append("</Id_SistGestor_Org>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Usuario>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
