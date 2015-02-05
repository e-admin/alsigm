package descripcion.model.xml.card;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase abstracta de la que deben extender todas las clases que contengan
 * elementos.
 */
public abstract class ContenedorElementos extends Elemento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Lista de elementos. */
	protected List elementos;

	/**
	 * Constructor.
	 */
	public ContenedorElementos() {
		super();
		elementos = new LinkedList();
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de elemento.
	 */
	public ContenedorElementos(short tipo) {
		super(tipo);
		elementos = new LinkedList();
	}

	/**
	 * Añade un elemento a la lista.
	 * 
	 * @param elemento
	 *            Elemento.
	 */
	public void addElemento(Elemento elemento) {
		elementos.add(elemento);
	}

	/**
	 * Obtiene un elemento de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del elemento dentro de la lista.
	 * @return Elemento.
	 */
	public Elemento getElemento(int index) {
		return (Elemento) elementos.get(index);
	}

	/**
	 * Obtiene la lista de elementos.
	 * 
	 * @return Lista de elementos.
	 */
	public Elemento[] getElementos() {
		return (Elemento[]) elementos.toArray(new Elemento[elementos.size()]);
	}

	/**
	 * Obtiene el número de elementos de la lista.
	 * 
	 * @return Número de elementos.
	 */
	public int getTotalElementos() {
		return elementos.size();
	}

	/**
	 * Elimina el elemento de la posición determinada.
	 * 
	 * @param index
	 *            Posición del elemento a eliminar.
	 */
	public void removeElemento(int index) {
		elementos.remove(index);
	}

	/**
	 * Elimina el elemento de la lista.
	 * 
	 * @param elemento
	 *            Elemento a eliminar.
	 */
	public void removeElemento(Elemento elemento) {
		elementos.remove(elemento);
	}

	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void clearElementos() {
		elementos.clear();
	}
}
