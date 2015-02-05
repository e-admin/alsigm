package descripcion.model.xml.card;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un elemento de tipo etiqueta-dato.
 */
public class ElementoEtiquetaDato extends Elemento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Estilo del dato. */
	private String estilo = null;

	/** Lista de valores del elemento. */
	private List valores = new LinkedList();

	/** Información para la edición. */
	private EdicionDato edicion = null;

	/**
	 * Constructor.
	 */
	public ElementoEtiquetaDato() {
		super(TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO);
	}

	/**
	 * Obtiene el Estilo del dato.
	 * 
	 * @return Estilo del dato.
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * Establece el estilo del dato.
	 * 
	 * @param estilo
	 *            Estilo del dato.
	 */
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	/**
	 * Añade un valor a la lista.
	 * 
	 * @param valor
	 *            Valor.
	 */
	public void addValor(Valor valor) {
		valores.add(valor);
	}

	/**
	 * Obtiene un valor de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del valor dentro de la lista.
	 * @return Valor.
	 */
	public Valor getValor(int index) {
		return (Valor) valores.get(index);
	}

	/**
	 * Obtiene la lista de valores.
	 * 
	 * @return Lista de valores.
	 */
	public Valor[] getValores() {
		return (Valor[]) valores.toArray(new Valor[valores.size()]);
	}

	/**
	 * Establece la lista de valores.
	 * 
	 * @param Lista
	 *            de valores.
	 */
	public void setValores(List valores) {
		this.valores = valores;
	}

	/**
	 * Obtiene el número de valores de la lista.
	 * 
	 * @return Número de valores.
	 */
	public int getTotalValores() {
		return valores.size();
	}

	/**
	 * Elimina el valor de la posición determinada.
	 * 
	 * @param index
	 *            Posición del valor a eliminar.
	 */
	public void removeValor(int index) {
		valores.remove(index);
	}

	/**
	 * Elimina todos las valores de la lista.
	 */
	public void clearValores() {
		valores.clear();
	}

	/**
	 * Obtiene la información para la edición.
	 * 
	 * @return Información para la edición.
	 */
	public EdicionDato getEdicion() {
		return edicion;
	}

	/**
	 * Establece la información para la edición.
	 * 
	 * @param edicion
	 *            Información para la edición.
	 */
	public void setEdicion(EdicionDato edicion) {
		this.edicion = edicion;
	}

	/**
	 * Devuelve la lista de órdenes de los valores del elemento.
	 * 
	 * @return Lista de órdenes de valores.
	 */
	public List getOrdenes() {
		List ordenes = new LinkedList();

		for (int i = 0; i < getTotalValores(); i++)
			ordenes.add(new Integer(getValor(i).getOrden()));

		return ordenes;
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

		// Inicio del tag Dato
		xml.append(tabs + "  <" + TagsFicha.TAG_DATO_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		// Inicio del tag Valores
		xml.append(tabs + "    <" + TagsFicha.TAG_VALORES_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Valores
		for (int i = 0; i < getTotalValores(); i++)
			xml.append(getValor(i).toXML(indent + 6));

		// Cierre del tag Valores
		xml.append(tabs + "    </" + TagsFicha.TAG_VALORES_DATO + ">");
		xml.append(Constants.NEWLINE);

		// Estilo
		xml.append(tabs + "    <" + TagsFicha.TAG_ESTILO + ">");
		xml.append(estilo != null ? estilo : "");
		xml.append("</" + TagsFicha.TAG_ESTILO + ">");
		xml.append(Constants.NEWLINE);

		// Cierre del tag Dato
		xml.append(tabs + "  </" + TagsFicha.TAG_DATO_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		// Edición
		if (edicion != null)
			xml.append(edicion.toXML(indent + 2));

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}