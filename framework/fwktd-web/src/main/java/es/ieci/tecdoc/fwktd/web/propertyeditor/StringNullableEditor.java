package es.ieci.tecdoc.fwktd.web.propertyeditor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

/**
 * <code>PropertyEditor</code> que permite el binding de
 * <code>java.lang.String</code> nulos.
 * 
 * @author IECISA
 * @see PropertyEditor
 */
public class StringNullableEditor extends PropertyEditorSupport {
	private Boolean allowEmpty;

	public StringNullableEditor(final Boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String getAsText() {
		return (null == getValue()) ? StringUtils.EMPTY : (String) getValue();
	}

	public void setAsText(final String text) throws IllegalArgumentException {
		if (Boolean.TRUE.equals(this.allowEmpty) && StringUtils.isEmpty(text)) {
			setValue(null);
		} else {
			setValue(text);
		}
	}
}