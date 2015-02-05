package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración del módulo de documentos vitales.
 */
public class ConfiguracionDocumentosVitales extends XMLObject
{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador del Repositorio ECM */
	private String idRepEcm = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionDocumentosVitales()
	{
		super();
	}

    public String getIdRepEcm()
    {
        return idRepEcm;
    }
    public void setIdRepEcm(String idRepEcm)
    {
        this.idRepEcm = idRepEcm;
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
		xml.append(tabs + "<Configuracion_Documentos_Vitales>");
		xml.append(Constants.NEWLINE);

		// Id_Lista_Volumenes
		xml.append(tabs + "  <Id_Repositorio_ECM>");
		xml.append(idRepEcm != null ? idRepEcm : "");
		xml.append("</Id_Repositorio_ECM>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Documentos_Vitales>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
