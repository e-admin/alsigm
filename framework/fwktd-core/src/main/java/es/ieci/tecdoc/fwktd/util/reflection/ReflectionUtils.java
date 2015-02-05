/**
 * 
 */
package es.ieci.tecdoc.fwktd.util.reflection;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author IECISA
 * 
 */
public class ReflectionUtils {

	/**
	 * Devuelve todos los campos declarados de una clase hoja y todas sus
	 * superclases. Los campos de la clase hoja se devuelven en primer lugar.
	 * 
	 * @param leafClass
	 *            clase de la que se desean obtener todos los campos declarados
	 *            hasta su raíz
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<? extends Object> leafClass) {

		final List<Field> l = new LinkedList<Field>();
		org.springframework.util.ReflectionUtils.doWithFields(leafClass,
				new org.springframework.util.ReflectionUtils.FieldCallback() {
					public void doWith(Field f) {
						l.add(f);
					}
				});
		return (Field[]) l.toArray(new Field[l.size()]);
	}

	private ReflectionUtils() {
	}

}