package common.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.Transformer;

import common.Constants;
import common.exceptions.UncheckedArchivoException;

public class VO2IDTransformer implements Transformer {

	private static final VO2IDTransformer instance = new VO2IDTransformer();

	private VO2IDTransformer() {
	}

	public Object transform(Object obj) {
		try {
			return BeanUtils.getProperty(obj, Constants.ID);
		} catch (IllegalAccessException iae) {
			throw new UncheckedArchivoException(iae);
		} catch (InvocationTargetException ite) {
			throw new UncheckedArchivoException(ite);
		} catch (NoSuchMethodException nse) {
			throw new UncheckedArchivoException(nse);
		}
	}

	public static Transformer getInstance() {
		return instance;
	}
}
