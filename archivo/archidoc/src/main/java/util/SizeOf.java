package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;

public class SizeOf {

	// overhead ~8 bytes/object
	// boolean 1
	// byte 1
	// char 2
	// short 2
	// int 4
	// long 8
	// float 4
	// double 8
	// reference 4/8†
	// String length * 2 + 4

	private static final long SZ_REF = 4;
	private static final long SZ_OVERHEAD_OBJECT = 8;

	public static int sizeof(boolean b) {
		return 1;
	}

	public static int sizeof(byte b) {
		return 1;
	}

	public static int sizeof(char c) {
		return 2;
	}

	public static int sizeof(short s) {
		return 2;
	}

	public static int sizeof(int i) {
		return 4;
	}

	public static int sizeof(long l) {
		return 8;
	}

	public static int sizeof(float f) {
		return 4;
	}

	public static int sizeof(double d) {
		return 8;
	}

	public static long sizeof(Object obj) {
		return sizeof(obj, new HashSet());
	}

	public static long sizeof(Object obj, Set referencias) {
		if (referencias.contains(obj))
			return SZ_REF;
		referencias.add(obj);
		long size = 0;
		try {
			if (obj == null)
				return SZ_REF;
			Class t = obj.getClass();
			if (t == Boolean.TYPE)
				return 1;
			else if (t == Boolean.class)
				return 1 + SZ_REF;
			else if (t == String.class)
				return 2 * obj.toString().length() + SZ_REF;
			else if (t == Byte.TYPE)
				return 1;
			else if (t == Byte.class)
				return 1 + SZ_REF;
			else if (t == Character.TYPE)
				return 2;
			else if (t == Character.class)
				return 2 + SZ_REF;
			else if (t == Short.TYPE)
				return 2;
			else if (t == Short.class)
				return 2 + SZ_REF;
			else if (t == Integer.TYPE)
				return 4;
			else if (t == Integer.class)
				return 4 + SZ_REF;
			else if (t == Long.TYPE)
				return 8;
			else if (t == Long.class)
				return 8 + SZ_REF;
			else if (t == Float.TYPE)
				return 4;
			else if (t == Float.class)
				return 4 + SZ_REF;
			else if (t == Double.TYPE)
				return 8;
			else if (t == Double.class)
				return 8 + SZ_REF;
			else if (t == Void.TYPE)
				return 0;
			else if (obj.getClass().isArray()) {
				for (int i = 0; i < ((Object[]) obj).length; i++) {
					size += sizeof(((Object[]) obj)[i], referencias);
				}
				return size;
			} else if (obj instanceof Collection) {
				for (Iterator it = ((Collection) obj).iterator(); it.hasNext();) {
					size += sizeof(it.next(), referencias);
				}
				return size + SZ_OVERHEAD_OBJECT;
			} else if (obj instanceof Map) {
				for (Iterator it = ((Map) obj).entrySet().iterator(); it
						.hasNext();) {
					Map.Entry par = (Map.Entry) it.next();
					size += sizeof(par.getKey());
					size += sizeof(par.getValue(), referencias);
				}
				return size + SZ_OVERHEAD_OBJECT;
			} else { // otro tipo de clase.
				// campos propios de la clase
				size += getSizeOfObject(obj,
						obj.getClass().getDeclaredFields(), referencias);

				Class c = obj.getClass();
				while (c.getSuperclass() != null) {
					Class superClase = c.getSuperclass();
					size += getSizeOfObject(obj,
							superClase.getDeclaredFields(), referencias);
					c = superClase;
				}
				return size + SZ_OVERHEAD_OBJECT;
			}
		} catch (Exception e) {
			Logger.getLogger("SizeOf").debug(
					"-> Error durante el recuento de memoria utilizada ("
							+ obj.getClass() + ") = " + size + " bytes");
		}
		return -1;
	}

	private static long getSizeOfObject(Object obj, Field[] fields,
			Set referencias) {
		long size = 0;
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (obj.getClass().isInterface()
					|| ((f.getModifiers() & Modifier.STATIC) != 0))
				continue;
			PropertyUtilsBean bean = new PropertyUtilsBean();
			try {
				Object valorAtributo = bean.getProperty(obj, f.getName());
				// size+=SZ_REF;
				// if(valorAtributo!=null)
				size += sizeof(valorAtributo, referencias);
			} catch (Exception e) {
				Logger.getLogger("SizeOf").debug(
						"-> No se pudo obtener el campo (get) de "
								+ obj.getClass() + "." + f.getName());
				// Plan b
				try {
					Object newObject = f.getClass().newInstance();
					size += sizeof(newObject, referencias);
				} catch (Exception e2) {
					Logger.getLogger("SizeOf")
							.debug("-> No fue posible instanciar la clase (Constructor por defecto)");
				}
			}
		}
		return size;
	}
}
