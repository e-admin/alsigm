package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.validator.ValidatorActionForm;

public abstract class RPAdminBaseForm extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String[] getAttrsToUpper();

	public abstract void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException;

	protected void toUpperCase(Object form, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		if (Utils.isDBCaseSensitive(request)) {

			String[] attrs = getAttrsToUpper();

			if ((attrs != null) && (attrs.length > 0)) {
				if (form instanceof DynaBean) {
					DynaProperty formDescriptors[] = ((DynaBean) form)
							.getDynaClass().getDynaProperties();
					for (int i = 0; i < formDescriptors.length; i++) {
						String name = formDescriptors[i].getName();
						Object value = ((DynaBean) form).get(name);

						if (value instanceof String) {
							if (isAttrTuUpper(attrs, name)) {
								value = ((String) value).toUpperCase();
								((DynaBean) form).set(name, value);
							}
						}
					}
				} else if (form instanceof Map) {
					Iterator names = ((Map) form).keySet().iterator();
					while (names.hasNext()) {
						String name = (String) names.next();
						Object value = ((Map) form).get(name);

						if (value instanceof String) {
							if (isAttrTuUpper(attrs, name)) {
								value = ((String) value).toUpperCase();
								((Map) form).put(name, value);
							}
						}
					}
				} else /* if (orig is a standard JavaBean) */{
					PropertyDescriptor origDescriptors[] = BeanUtilsBean
							.getInstance().getPropertyUtils()
							.getPropertyDescriptors(form);
					for (int i = 0; i < origDescriptors.length; i++) {
						String name = origDescriptors[i].getName();

						if ("class".equals(name)) {
							continue; // No point in trying to set an object's
							// class
						}

						if (BeanUtilsBean.getInstance().getPropertyUtils()
								.isReadable(form, name)) {
							try {
								Object value = BeanUtilsBean.getInstance()
										.getPropertyUtils().getSimpleProperty(
												form, name);
								if (value instanceof String) {
									if (isAttrTuUpper(attrs, name)) {
										value = ((String) value).toUpperCase();
										BeanUtilsBean.getInstance()
												.getPropertyUtils()
												.setSimpleProperty(form, name,
														value);
									}
								}

							} catch (NoSuchMethodException e) {
								; // Should not happen
							}
						}
					}
				}
			}
		}
	}

	private boolean isAttrTuUpper(String[] attrs, String fieldName) {
		if (attrs != null && attrs.length > 0) {
			for (int i = 0; i < attrs.length; i++) {
				String attr = attrs[i];

				if (attr.equals(fieldName)) {
					return true;
				}
			}
		}

		return false;
	}

}
