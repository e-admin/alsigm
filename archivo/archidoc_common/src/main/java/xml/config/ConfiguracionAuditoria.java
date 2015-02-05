package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración de la auditoría.
 */
public class ConfiguracionAuditoria extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Plantilla XSL de transformación. */
	private String plantillaXSL = null;


	/**
	 * Constructor.
	 */
	public ConfiguracionAuditoria()
	{
		super();
	}


	/**
	 * Obtiene la plantilla XSL de transformación.
	 * @return Plantilla XSL de transformación.
	 */
	public String getPlantillaXSL()
	{
		return plantillaXSL;
	}


	/**
	 * Establece la plantilla XSL de transformación.
	 * @param plantillaXSL Plantilla XSL de transformación.
	 */
	public void setPlantillaXSL(String plantillaXSL)
	{
		this.plantillaXSL = plantillaXSL;
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
		xml.append(tabs + "<Configuracion_Auditoria>");
		xml.append(Constants.NEWLINE);

		// Plantilla_XSL
		xml.append(tabs + "  <Plantilla_XSL>");
		xml.append(plantillaXSL != null ? plantillaXSL : "");
		xml.append("</Plantilla_XSL>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Auditoria>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
