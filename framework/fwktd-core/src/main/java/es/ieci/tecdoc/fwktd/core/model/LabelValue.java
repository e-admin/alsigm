/**
 * 
 */
package es.ieci.tecdoc.fwktd.core.model;

import java.io.Serializable;

/**
 * 
 * @author IECISA
 * 
 */
public class LabelValue implements Comparable<LabelValue>, Serializable {

	public LabelValue() {
	}

	public LabelValue(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int compareTo(LabelValue o) {
		if (null == o) {
			throw new IllegalArgumentException();
		}
		return getLabel().compareTo(((LabelValue) o).getLabel());
	}

	// Members
	private static final long serialVersionUID = -6377262540806597583L;

	private String label;
	private String value;
}
