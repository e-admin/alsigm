package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;
import common.util.FileHelper;


/**
 * Clase que almacena la configuración del módulo de Fondos.
 */
public class ConfiguracionFondos extends XMLObject
{

    /**
	 *
	 */
	private static final long serialVersionUID = 418609895187669045L;

	/** Sistema de organización del que es posible importar instituciones para asociarlas como entidades productoras*/
    private String idSistGestorOrg = null;

    /** Directorio de logs de eliminacion. */
	private String directorioLogsEliminacion = null;

	/** Delimitiador de los codigos que se concatenan para componer el codigo de referencia de un elemento del cuadro de clasificacion */
	private String delimitadorCodigoReferencia = null;

	/** Tabla de validación con los boletines oficiales. */
	private String tablaValidacionBoletinesOficiales = null;

	/** Tabla de Validación de Tipo de Documentación de la Serie */
	private String tablaValidacionTipoDocumentacion = null;

	/**
	 * Número de elementos por pagina en la búsqueda de unidades documentales a conservar
	 */
	private int numResultadosPagSeleccionUdocConservar = 0;

	/**
	 * Numero maximo de resultados en la búsqueda de unidades documentales de la serie.
	 */
	private int numMaxResultadosSerie = 0;

	/**
	 * Constructor.
	 */
	public ConfiguracionFondos()
	{
		super();
	}


	/**
	 * Obtiene el directorio donde se establecen los logs del modulo de eliminacion
	 * @return Directorio de logs
	 */
    public String getDirectorioLogsEliminacion() {
    	return FileHelper.getNormalizedToSystemPath(directorioLogsEliminacion);
    }


    /**
     * Establece el directorio donde se establecen los logs del modulo de eliminacion
     * @param directorioLogsEliminacion Directorio de logs
     */
    public void setDirectorioLogsEliminacion(String directorioLogsEliminacion) {
    	this.directorioLogsEliminacion = directorioLogsEliminacion;
    }


	/**
	 * @return Returns the delimitadorCodigoReferencia.
	 */
	public String getDelimitadorCodigoReferencia()
	{
		return delimitadorCodigoReferencia;
	}
	/**
	 * @param delimitadorCodigoReferencia The delimitadorCodigoReferencia to set.
	 */
	public void setDelimitadorCodigoReferencia(
			String delimitadorCodigoReferencia)
	{
		this.delimitadorCodigoReferencia = delimitadorCodigoReferencia;
	}


    /**
     * Obtener la tabla de validación con los boletines oficiales.
     * @return Tabla de validación con los boletines oficiales.
     */
    public String getTablaValidacionBoletinesOficiales()
    {
        return tablaValidacionBoletinesOficiales;
    }


    /**
     * Establecer la tabla de validación con los boletines oficiales.
     * @param tablaValidacionBoletinesOficiales Tabla de validación con los boletines oficiales.
     */
    public void setTablaValidacionBoletinesOficiales(
            String tablaValidacionBoletinesOficiales)
    {
        this.tablaValidacionBoletinesOficiales = tablaValidacionBoletinesOficiales;
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
		xml.append(tabs + "<Configuracion_Fondos>");
		xml.append(Constants.NEWLINE);

		// Directorio_Logs_Eliminacion
		xml.append(tabs + "  <Directorio_Logs_Eliminacion>");
		xml.append(directorioLogsEliminacion != null ? directorioLogsEliminacion : "");
		xml.append("</Directorio_Logs_Eliminacion>");
		xml.append(Constants.NEWLINE);


		// Numero_Maximo_Resultados
		if (getNumMaxResultadosSerie() > 0)
		{
			xml.append(tabs + "  <Numero_Maximo_Resultados_Serie>");
			xml.append(getNumMaxResultadosSerie());
			xml.append("</Numero_Maximo_Resultados_Serie>");
			xml.append(Constants.NEWLINE);
		}


		// Numero_Maximo_Resultados
		if (getNumResultadosPagSeleccionUdocConservar() > 0)
		{
			xml.append(tabs + "  <Num_Resultados_Pag_Seleccion_Udoc_Conservar>");
			xml.append(getNumResultadosPagSeleccionUdocConservar());
			xml.append("</Num_Resultados_Pag_Seleccion_Udoc_Conservar>");
			xml.append(Constants.NEWLINE);
		}


		// Delimitador_Codigo_Referencia
		xml.append(tabs + "  <Delimitador_Codigo_Referencia>");
		xml.append(delimitadorCodigoReferencia != null ? delimitadorCodigoReferencia : "");
		xml.append("</Delimitador_Codigo_Referencia>");
		xml.append(Constants.NEWLINE);

		// Tabla_Validacion_Boletines_Oficiales
		xml.append(tabs + "  <Tabla_Validacion_Boletines_Oficiales>");
		xml.append(tablaValidacionBoletinesOficiales != null ? tablaValidacionBoletinesOficiales : "");
		xml.append("</Tabla_Validacion_Boletines_Oficiales>");
		xml.append(Constants.NEWLINE);

		// Tabla Validacion Tipo Documentacion
		xml.append(tabs + "  <Tabla_Validacion_Tipo_Documentacion>");
		xml.append(tablaValidacionTipoDocumentacion != null ? tablaValidacionTipoDocumentacion : "");
		xml.append("</Tabla_Validacion_Tipo_Documentacion>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Fondos>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

    public String getIdSistGestorOrg() {
        return idSistGestorOrg;
    }
    public void setIdSistGestorOrg(String idSistGestorOrg) {
        this.idSistGestorOrg = idSistGestorOrg;
    }
	public String getTablaValidacionTipoDocumentacion() {
		return tablaValidacionTipoDocumentacion;
	}
	public void setTablaValidacionTipoDocumentacion(
			String tablaValidadcionTipoDocumentacion) {
		this.tablaValidacionTipoDocumentacion = tablaValidadcionTipoDocumentacion;
	}


	public void setNumResultadosPagSeleccionUdocConservar(
			int numResultadosPagSeleccionUdocConservar) {
		this.numResultadosPagSeleccionUdocConservar = numResultadosPagSeleccionUdocConservar;
	}


	public int getNumResultadosPagSeleccionUdocConservar() {
		return numResultadosPagSeleccionUdocConservar;
	}


	public void setNumMaxResultadosSerie(int numMaxResultadosSerie) {
		this.numMaxResultadosSerie  = numMaxResultadosSerie;
	}


	public int getNumMaxResultadosSerie() {
		return numMaxResultadosSerie;
	}
}
