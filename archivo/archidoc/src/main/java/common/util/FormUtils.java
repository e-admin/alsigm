package common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;

import common.Constants;

public class FormUtils {

	/**
	 * Resetea los campos declarados de un formulario
	 * 
	 * @param form
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	public static void resetDeclaredFields(ActionForm form)
			throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		Field[] fields = form.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String key = fields[i].getName();
			if (!key.equals(Constants.SERIAL_ID)) {
				Object object = PropertyUtils.getProperty(form, key);
				if (object != null && object instanceof Boolean)
					PropertyUtils.setSimpleProperty(form, key, Boolean.FALSE);
				else if (object != null && object instanceof String)
					PropertyUtils.setSimpleProperty(form, key, null);
			}
		}
	}
}
