package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración de almacenamiento.
 */
public class ConfiguracionAlmacenamiento extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Nombre del Data Source de almacenamiento. */
	private String dataSourceNameAlmacenamiento = null;

	/** Plataforma del motor documental. */
	private String plataformaMotorDocumental = null;

	/** Ruta de la plataforma del motor documental. */
	private String rutaPlataformaMotorDocumental = null;

	/** Extensiones para indexar en el motor documental. */
	private String extensionesMotorDocumental = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionAlmacenamiento()
	{
		super();
	}

	/**
	 * Permite obtener el datasource utilizado para almacenamiento
	 * @return datasource utilizado para almacenamiento
	 */
	public String getDataSourceNameAlmacenamiento() {
		return dataSourceNameAlmacenamiento;
	}

	/**
	 * Permite establecer el datasource utilizado para almacenamiento
	 * @param dataSourceNameAlmacenamiento Datasource utilizado para almacenamiento
	 */
	public void setDataSourceNameAlmacenamiento(String dataSourceNameAlmacenamiento) {
		this.dataSourceNameAlmacenamiento = dataSourceNameAlmacenamiento;
	}

	/**
	 * Permite obtener las extensiones que se indexarán con el motor documental
	 * @return Extensiones a indexar con el motor documental
	 */
	public String getExtensionesMotorDocumental() {
		return extensionesMotorDocumental;
	}

	/**
	 * Permite obtener un array con las extensiones a indexar por el motor documental
	 * @return array con las extensiones a indexar por el motor documental
	 */
	public String [] getExtensionesArrayMotorDocumental() {
		String [] extensiones = null;

		try {
			if (StringUtils.isNotEmpty(extensionesMotorDocumental)){
				extensiones = extensionesMotorDocumental.split(";");
			}

			for (int i=0;i<extensiones.length;i++){
				if (StringUtils.isNotEmpty(extensiones[i]))
					extensiones[i]=extensiones[i].toUpperCase().trim();
			}

		} catch (Exception e) {
		}

		return extensiones;
	}

	/**
	 * Permite obtener las extensiones a indexar por el motor documental
	 * @param extensionesMotorDocumental extensiones a indexar por el motor documental
	 */
	public void setExtensionesMotorDocumental(String extensionesMotorDocumental) {
		this.extensionesMotorDocumental = extensionesMotorDocumental;
	}

	/**
	 * Permite obtener la plataforma del motor documental
	 * @return Plataforma del motor documental
	 */
	public String getPlataformaMotorDocumental() {
		return plataformaMotorDocumental;
	}

	/**
	 * Permite obtener un entero con la plataforma del motor documental
	 * @return Plataforma del motor documental
	 */
	public int getPlataformaIntMotorDocumental() {
		return Integer.parseInt(plataformaMotorDocumental);
	}

	/**
	 * Permite obtener la plataforma del motor documental
	 * @param plataformaMotorDocumental Plataforma del motor documental
	 */
	public void setPlataformaMotorDocumental(String plataformaMotorDocumental) {
		this.plataformaMotorDocumental = plataformaMotorDocumental;
	}

	/**
	 * Permite obtener la ruta del motor documental
	 * @return Ruta del motor documental
	 */
	public String getRutaPlataformaMotorDocumental() {
		return rutaPlataformaMotorDocumental;
	}

	/**
	 * Permite establecer la ruta del motor documental
	 * @param rutaPlataformaMotorDocumental Ruta del motor documental
	 */
	public void setRutaPlataformaMotorDocumental(String rutaPlataformaMotorDocumental) {
		this.rutaPlataformaMotorDocumental = rutaPlataformaMotorDocumental;
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
		xml.append(tabs + "<Configuracion_Almacenamiento>");
		xml.append(Constants.NEWLINE);

		// Data_Source de Almacenamiento
		xml.append(tabs + "  <Data_Source_Almacenamiento>");
		xml.append(dataSourceNameAlmacenamiento != null ? dataSourceNameAlmacenamiento : "");
		xml.append("</Data_Source_Almacenamiento>");
		xml.append(Constants.NEWLINE);

		if (StringUtils.isNotEmpty(getPlataformaMotorDocumental())||
			StringUtils.isNotEmpty(getRutaPlataformaMotorDocumental())||
			StringUtils.isNotEmpty(getExtensionesMotorDocumental())){

			xml.append(tabs + "     <Motor_Documental>");
			xml.append(Constants.NEWLINE);

			// Plataforma
			xml.append(tabs + "        <Plataforma>");
			xml.append(plataformaMotorDocumental != null ? plataformaMotorDocumental : "");
			xml.append("</Plataforma>");
			xml.append(Constants.NEWLINE);

			// Ruta plataforma
			xml.append(tabs + "        <Ruta_Plataforma>");
			xml.append(rutaPlataformaMotorDocumental != null ? rutaPlataformaMotorDocumental : "");
			xml.append("</Ruta_Plataforma>");
			xml.append(Constants.NEWLINE);

			// Extensiones a indexar
			xml.append(tabs + "        <Extensiones>");
			xml.append(extensionesMotorDocumental != null ? extensionesMotorDocumental : "");
			xml.append("</Extensiones>");
			xml.append(Constants.NEWLINE);

			xml.append(tabs + "     </Motor_Documental>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Almacenamiento>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
