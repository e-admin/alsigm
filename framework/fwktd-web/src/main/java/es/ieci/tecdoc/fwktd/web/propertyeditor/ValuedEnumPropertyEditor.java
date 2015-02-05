package es.ieci.tecdoc.fwktd.web.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.commons.lang.math.NumberUtils;

/**
 * <code>PropertyEditor</code> para la transformación de
 * <code>java.lang.String</code> o <code>int</code> en su equivalente como
 * <code>ValuedEnum</code> de tipo <code>T</code>.
 * 
 * @author IECISA
 * 
 * @param <T>
 */
public abstract class ValuedEnumPropertyEditor<T> extends PropertyEditorSupport {

	/**
	 * Transforma <code>text</code> en su representación como
	 * <code>ValuedEnum</code>. Dependiendo de que el valor de <code>text</code>
	 * sea una cadena de caracteres o un número se instancia el
	 * <code>ValuedEnum</code> con su <code>name</code> o su <code>value</code>.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		ValuedEnum valuedEnum = null;

		if (NumberUtils.isDigits(text)) {
			valuedEnum = EnumUtils.getEnum(getParameterizedClass(), NumberUtils
					.toInt(text));
		} else {
			valuedEnum = (ValuedEnum) EnumUtils.getEnum(
					getParameterizedClass(), text);
		}

		setValue(valuedEnum);
	}

	/**
	 * Devuelve el <code>name</code> del <code>ValuedEnum</code> almacenado como
	 * <code>value</code> del <code>PropertyEditor</code> en caso de que este no
	 * sea nulo. Si fuera nulo se devuelve <code>null</code>.
	 */
	@Override
	public String getAsText() {
		ValuedEnum valuedEnum = (ValuedEnum) getValue();

		if (null != valuedEnum) {
			return valuedEnum.getName();
		}
		return null;
	}

	/**
	 * Devuelve la clase del tipo parametrizado.
	 * 
	 * @return
	 */
	private Class<T> getParameterizedClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
