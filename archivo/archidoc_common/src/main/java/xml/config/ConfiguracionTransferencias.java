package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración del módulo de transferencias.
 */
public class ConfiguracionTransferencias extends XMLObject
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tabla de validación con los roles que un interesado puede jugar en un expediente */
	private String idTablaValidacionRolInteresado = null;

    /** Tabla de validación con la lista de formas documentales que pueden presentar una unidad documental */
    private String idTablaValidacionFormaDocumental = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionTransferencias()
	{
		super();
	}

    public String getIdTablaValidacionRolInteresado() {
       return idTablaValidacionRolInteresado;
    }
    public void setIdTablaValidacionRolInteresado(String idTablaValidacionRolInteresado) {
       this.idTablaValidacionRolInteresado = idTablaValidacionRolInteresado;
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
		xml.append(tabs + "<Configuracion_Transferencias>");

		// Tabla_Validacion_Rol_Interesado
		xml.append(tabs + "  <Tabla_Validacion_Rol_Interesado>");
		xml.append(idTablaValidacionRolInteresado != null ? idTablaValidacionRolInteresado : "");
		xml.append("</Tabla_Validacion_Rol_Interesado>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Transferencias>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

    public String getIdTablaValidacionFormaDocumental() {
        return idTablaValidacionFormaDocumental;
    }
    public void setIdTablaValidacionFormaDocumental(String idTablaValidacionFormaDocumental) {
        this.idTablaValidacionFormaDocumental = idTablaValidacionFormaDocumental;
    }
}
