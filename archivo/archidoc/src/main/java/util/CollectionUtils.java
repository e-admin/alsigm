package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.commons.lang.ArrayUtils;

/**
 * Utilidad de trabajo con colecciones
 * 
 */
public class CollectionUtils {

	/**
	 * Obtiene el tamaño de la colección de valores.
	 * 
	 * @param collection
	 *            Colección de valores.
	 * @return Tamaño de la colección.
	 */
	public static int collectionSize(Collection collection) {
		if (collection != null)
			return collection.size();
		return 0;
	}

	/**
	 * Obtiene el iterador que recorre el objeto.
	 * 
	 * @param obj
	 *            Objeto iterable.
	 * @return Iterador.
	 */
	public static Iterator getIterator(Object obj) {
		if (obj instanceof Iterator)
			return (Iterator) obj;
		if (obj instanceof Collection)
			return ((Collection) obj).iterator();
		if (obj instanceof Object[])
			return new ArrayIterator(obj);
		if (obj instanceof Enumeration)
			return new EnumerationIterator((Enumeration) obj);
		if (obj instanceof Map)
			return ((Map) obj).values().iterator();
		if (obj != null && obj.getClass().isArray())
			return new ArrayIterator(obj);
		else
			return null;
	}

	public static boolean isEmptyCollection(Collection col) {
		return (col == null || col.size() == 0 ? true : false);
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de
	 * una colección separados por una determinada cadena de caracteres.
	 */
	public static String join(Collection collection, String token) {
		if (collection != null)
			return join(collection.iterator(), token);
		else
			return null;
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de
	 * una colección separados por una determinada cadena de caracteres.
	 */
	public static String join(Iterator it, String token) {
		if (it != null) {
			StringBuffer buffer = new StringBuffer();
			while (it.hasNext()) {
				buffer.append(it.next());
				if (it.hasNext())
					buffer.append(token);
			}

			return buffer.toString();
		}

		return null;
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de
	 * una colección separados por una determinada cadena de caracteres.
	 */
	public static String join(Collection collection, String token,
			String delimiter) {
		if (collection != null)
			return join(collection.iterator(), token, delimiter);
		else
			return null;
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de
	 * una colección separados por una determinada cadena de caracteres.
	 */
	public static String join(Iterator it, String token, String delimiter) {
		if (it != null) {
			StringBuffer buffer = new StringBuffer();
			while (it.hasNext()) {
				buffer.append(delimiter).append(it.next()).append(delimiter);

				if (it.hasNext())
					buffer.append(token);
			}

			return buffer.toString();
		}

		return null;
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de
	 * una colección separados por una determinada cadena de caracteres hasta un
	 * número máximo de elementos.
	 */
	public static String join(Iterator it, String token, String delimiter,
			long maxSize) {
		if (it != null) {
			long num = 0;
			StringBuffer buffer = new StringBuffer();
			while (it.hasNext() && (num < maxSize)) {
				buffer.append(delimiter).append(it.next()).append(delimiter);

				num++;

				if (it.hasNext() && (num < maxSize))
					buffer.append(token);
			}

			return buffer.toString();
		}

		return null;
	}

	/**
	 * Indica si la colección está vacía.
	 * 
	 * @param collection
	 *            Colección.
	 * @return true si la colección está vacía.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * Añade los elementos de una lista a otra siempre y cuando no estén
	 * contenidos en ella.
	 * 
	 * @param container
	 *            Lista general donde se van a añadir los elementos.
	 * @param aux
	 *            Lista que contiene los elementos a añadir.
	 */
	public static void addList(List container, List aux) {
		if ((container != null) && (aux != null)) {
			for (int i = 0; i < aux.size(); i++) {
				Object obj = aux.get(i);
				if (!container.contains(obj))
					container.add(obj);
			}
		}
	}

	/**
	 * Aplica una transformación al contenido de una colección.
	 * 
	 * @param collection
	 *            Colección de objetos a transformar.
	 * @param transformer
	 *            Transformador.
	 */
	public static void transform(Collection collection, Transformer transformer) {
		org.apache.commons.collections.CollectionUtils.transform(collection,
				transformer);
	}

	/**
	 * Crea una lista con el contenido de un array.
	 * 
	 * @param array
	 *            Array.
	 * @return Lista.
	 */
	public static List createList(Object[] array) {
		List list = new ArrayList();

		if (array != null)
			for (int i = 0; i < array.length; i++)
				list.add(array[i]);

		return list;
	}

	/**
	 * Crea una lista con el contenido de un array.
	 * 
	 * @param array
	 *            Array.
	 * @return Lista.
	 */
	public static List createList(Object[] array, Predicate predicate) {
		List list = new ArrayList();

		if (array != null)
			for (int i = 0; i < array.length; i++) {
				Object object = array[i];
				if (predicate.evaluate(object))
					list.add(object);
			}

		return list;
	}

	public static boolean contains(Collection collection, Comparable comparable) {
		if (isEmpty(collection))
			return false;

		for (Iterator it = collection.iterator(); it.hasNext();)
			if (comparable.compareTo(it.next()) == 0)
				return true;

		return false;
	}

	public static List reverse(List list) {
		List reversedList = null;

		if (list != null) {
			reversedList = new ArrayList();
			for (int i = list.size() - 1; i >= 0; i--)
				reversedList.add(list.get(i));
		}

		return reversedList;
	}

	public static String[] toArray(List list) {
		String[] array = ArrayUtils.EMPTY_STRING_ARRAY;
		if (list != null) {
			int i = 0;
			array = new String[list.size()];
			for (Iterator iter = list.iterator(); iter.hasNext();)
				array[i++] = (String) iter.next();
		}
		return array;
	}
}