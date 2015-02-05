package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un elemento de tipo tabla.
 */
public class ElementoTabla extends ContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Información para la edición. */
	private Edicion edicion = null;

	/** Indica si hay que mostrar las cabeceras. */
	private boolean mostrarCabeceras = true;

	/** Indica el valor del sistema externo */
	private String sistemaExterno;

	/** Indica el numero de elementos de la tabla */
	private String numeroElementos;

	private String filasABorrar;

	/**
	 * Constructor.
	 */
	public ElementoTabla() {
		super(TiposElemento.TIPO_ELEMENTO_TABLA);
	}

	/**
	 * Obtiene la información para la edición.
	 * 
	 * @return Información para la edición.
	 */
	public Edicion getEdicion() {
		return edicion;
	}

	/**
	 * Establece la información para la edición.
	 * 
	 * @param edicion
	 *            Información para la edición.
	 */
	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	/**
	 * Indica si hay que mostrar las cabeceras.
	 * 
	 * @return Si hay que mostrar las cabeceras.
	 */
	public boolean isMostrarCabeceras() {
		return mostrarCabeceras;
	}

	/**
	 * Establece si hay que mostrar las cabeceras.
	 * 
	 * @param cabeceras
	 *            Indica si hay que mostrar las cabeceras.
	 */
	public void setMostrarCabeceras(boolean cabeceras) {
		this.mostrarCabeceras = cabeceras;
	}

	public String getSistemaExterno() {
		return sistemaExterno;
	}

	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}

	public String getNumeroElementos() {
		return numeroElementos;
	}

	public void setNumeroElementos(String numeroElementos) {
		this.numeroElementos = numeroElementos;
	}

	public String getFilasABorrar() {
		return filasABorrar;
	}

	public void setFilasABorrar(String filasABorrar) {
		this.filasABorrar = filasABorrar;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<" + TagsFicha.TAG_ELEMENTO + " "
				+ TagsFicha.ATTR_TIPO_ELEMENTO + "=\""
				+ getNombreTipoElemento() + "\" "
				+ TagsFicha.ATTR_SCROLL_ELEMENTO + "=\"" + getScroll() + "\">");
		xml.append(Constants.NEWLINE);

		// Etiqueta
		xml.append(getEtiqueta().toXML(indent + 2));

		// Edición
		if (edicion != null)
			xml.append(edicion.toXML(indent + 2));

		// Mostrar_Cabeceras
		if (!mostrarCabeceras) {
			xml.append(tabs + "  <" + TagsFicha.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.FALSE_STRING);
			xml.append("</" + TagsFicha.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.NEWLINE);
		}

		// Inicio del tag Elementos_Tabla
		xml.append(tabs + "  <" + TagsFicha.TAG_ELEMENTOS_TABLA + ">");
		xml.append(Constants.NEWLINE);

		// Elementos
		for (int i = 0; i < getTotalElementos(); i++)
			xml.append(getElemento(i).toXML(indent + 4));

		// Cierre del tag Elementos_Tabla
		xml.append(tabs + "  </" + TagsFicha.TAG_ELEMENTOS_TABLA + ">");
		xml.append(Constants.NEWLINE);

		// Sistema Externo
		if (!common.util.StringUtils.isEmpty(sistemaExterno)) {
			xml.append(tabs + "  <" + TagsFicha.TAG_SISTEMA_EXTERNO_EDICION
					+ ">");
			xml.append(sistemaExterno);
			xml.append("</" + TagsFicha.TAG_SISTEMA_EXTERNO_EDICION + ">");
			xml.append(Constants.NEWLINE);
		}

		// Numero de elementos
		xml.append(tabs + "  <" + TagsFicha.TAG_NUMERO_ELEMENTOS_EDICION + ">");
		xml.append(numeroElementos);
		xml.append("</" + TagsFicha.TAG_NUMERO_ELEMENTOS_EDICION + ">");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
