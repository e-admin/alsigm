//
// FileName: AbstractAx.java
//
package com.ieci.tecdoc.common.isicres;

/**
 * Estructura común de un VALUE OBJECT para su salida del servidor.
 * 
 * @author lmvicente
 * @version 
 * @since 
 * @creationDate 04-mar-2004
 */

public class AbstractAx {

	public String toXML() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1, className.length()).toUpperCase();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<");
		buffer.append(className);
		buffer.append(">");
		try {
			java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
			java.lang.reflect.Field field = null;
			String name = null;
			int size = fields.length;
			for (int i = 0; i < size; i++) {
				field = fields[i];
				name = field.getName();
				buffer.append("<");
				buffer.append(name.toUpperCase());
				buffer.append(">");
				if (field.get(this) != null) {
					buffer.append(field.get(this));
				}
				buffer.append("</");
				buffer.append(name.toUpperCase());
				buffer.append(">");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		buffer.append("</");
		buffer.append(className);
		buffer.append(">");
		return buffer.toString();
	}

}
