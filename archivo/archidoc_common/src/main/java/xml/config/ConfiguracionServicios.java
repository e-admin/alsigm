package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la información del módulo de gestión de préstamos.
 */
public class ConfiguracionServicios extends XMLObject
{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String PlazoPrestamo = null;
    private String PlazoTrasReclamacion = null;
	private String PlazoReserva = null;
	private String PlazoProrroga = null;
	private String patronExpFS = null;
	private String etiquetaValExpFS = null;
	private String caracterAutoExpFS = null;
	private String limiteAutoExpFS = null;

	/** Identificador de la lista de validación de los tipos de entrega */
    private String listaValidacionTipoEntrega = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionServicios()
	{
		super();
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
		xml.append(tabs + "<Configuracion_Servicios>");
		xml.append(Constants.NEWLINE);

		// Tag de inicio de Préstamos
		xml.append(tabs + "  <Prestamos>");
		xml.append(Constants.NEWLINE);

		// PlazoPrestamo
		if (StringUtils.isNotBlank(PlazoPrestamo))
		{
			xml.append(tabs + "    <Plazo>");
		    xml.append(PlazoPrestamo);
		    xml.append("</Plazo>");
			xml.append(Constants.NEWLINE);
		}
		//PlazoTrasReclamacion
		if (StringUtils.isNotBlank(PlazoTrasReclamacion))
		{
			xml.append(tabs + "    <Plazo_tras_reclamacion>");
		    xml.append(PlazoTrasReclamacion);
		    xml.append("</Plazo_tras_reclamacion>");
			xml.append(Constants.NEWLINE);
		}
		//PlazoReserva
		if (StringUtils.isNotBlank(PlazoReserva))
		{
			xml.append(tabs + "    <Plazo_Reserva>");
		    xml.append(PlazoReserva);
		    xml.append("</Plazo_Reserva>");
			xml.append(Constants.NEWLINE);
		}
		//PlazoProrroga
		if (StringUtils.isNotBlank(PlazoProrroga))
		{
			xml.append(tabs + "    <Plazo_Prorroga>");
		    xml.append(PlazoProrroga);
		    xml.append("</Plazo_Prorroga>");
			xml.append(Constants.NEWLINE);
		}

		/*<Expediente_Fraccion_Serie>
      		<Patron>([0-9]{7}[/][0-9]{4})?</Patron>
      		<Etiqueta_Validacion>DDDDDDD/DDDD</Etiqueta_Validacion>
			<Autocompletar_Izquierda>
				<Caracter>0</Caracter>
				<Limite>12</Limite>
			</Autocompletar_Izquierda>
      	</Expediente_Fraccion_Serie> */

		// Expediente de fracción de serie
		xml.append(tabs + "		<Expediente_Fraccion_Serie>");
		xml.append(Constants.NEWLINE);
		// Patrón de validación de número de expediente de fracción de serie
		if (StringUtils.isNotEmpty(patronExpFS))
		{
			xml.append(tabs + "    <Patron>");
			xml.append(patronExpFS);
			xml.append("</Patron>");
			xml.append(Constants.NEWLINE);
		}
		// Etiqueta de validación de número de expediente de fracción de serie
		if (StringUtils.isNotEmpty(etiquetaValExpFS))
		{
			xml.append(tabs + "    <Etiqueta_Validacion>");
			xml.append(etiquetaValExpFS);
			xml.append("</Etiqueta_Validacion>");
			xml.append(Constants.NEWLINE);
		}
		// Carácter con el que autocompletar por la izquierda el número de expediente de fracción de serie
		if (StringUtils.isNotEmpty(caracterAutoExpFS))
		{
			xml.append(tabs + "    <Autocompletar_Izquierda><Caracter>");
			xml.append(caracterAutoExpFS);
			xml.append("</Caracter></Autocompletar_Izquierda>");
			xml.append(Constants.NEWLINE);
		}
		// Límite máximo de longitud hasta el que completar por la izquierda el número de expediente de fracción de serie
		if (StringUtils.isNotEmpty(limiteAutoExpFS))
		{
			xml.append(tabs + "    <Autocompletar_Izquierda><Limite>");
			xml.append(limiteAutoExpFS);
			xml.append("</Limite></Autocompletar_Izquierda>");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "		</Expediente_Fraccion_Serie>");
		xml.append(Constants.NEWLINE);

		//Tabla validación relación de entrega
		if (StringUtils.isNotBlank(listaValidacionTipoEntrega))
		{
			xml.append(tabs + "  <Tabla_Validacion_Tipo_Entrega>");
		    xml.append(listaValidacionTipoEntrega);
		    xml.append("</Tabla_Validacion_Tipo_Entrega>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre de Préstamos
		xml.append(tabs + "  </Prestamos>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Servicios>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
    public String getPlazoPrestamo() {
        return PlazoPrestamo;
    }
    public void setPlazoPrestamo(String plazoPrestamo) {
        PlazoPrestamo = plazoPrestamo;
    }
    public String getPlazoProrroga() {
        return PlazoProrroga;
    }
    public void setPlazoProrroga(String plazoProrroga) {
        PlazoProrroga = plazoProrroga;
    }
    public String getPlazoReserva() {
        return PlazoReserva;
    }
    public void setPlazoReserva(String plazoReserva) {
        PlazoReserva = plazoReserva;
    }
    public String getPlazoTrasReclamacion() {
        return PlazoTrasReclamacion;
    }
    public void setPlazoTrasReclamacion(String plazoTrasReclamacion) {
        PlazoTrasReclamacion = plazoTrasReclamacion;
    }


	public String getListaValidacionTipoEntrega() {
		return listaValidacionTipoEntrega;
	}


	public void setListaValidacionTipoEntrega(String listaValidacionTipoEntrega) {
		this.listaValidacionTipoEntrega = listaValidacionTipoEntrega;
	}

	public void setPatronExpFS (String patronExpFS) {
		this.patronExpFS = patronExpFS;
	}

	public void setEtiquetaValExpFS (String etiquetaValExpFS) {
		this.etiquetaValExpFS = etiquetaValExpFS;
	}

	public String getEtiquetaValExpFS() {
		return etiquetaValExpFS;
	}

	public String getPatronExpFS() {
		return patronExpFS;
	}


	public String getCaracterAutoExpFS() {
		return caracterAutoExpFS;
	}


	public void setCaracterAutoExpFS(String caracterAutoExpFS) {
		this.caracterAutoExpFS = caracterAutoExpFS;
	}


	public String getLimiteAutoExpFS() {
		return limiteAutoExpFS;
	}


	public void setLimiteAutoExpFS(String limiteAutoExpFS) {
		this.limiteAutoExpFS = limiteAutoExpFS;
	}

}
