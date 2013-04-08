package common.view;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import common.exceptions.UncheckedArchivoException;

public class POUtils {

	public static void copyVOProperties(Object po, Object vo) {
		try {
			if (po != null && vo != null)
				PropertyUtils.copyProperties(po, vo);
		} catch (IllegalAccessException iae) {
			throw new UncheckedArchivoException(iae);
		} catch (InvocationTargetException ite) {
			throw new UncheckedArchivoException(ite);
		} catch (NoSuchMethodException nsme) {
			throw new UncheckedArchivoException(nsme);
		}
	}
}
