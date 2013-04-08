package fondos.vos;

import common.vos.BaseVO;

public class AccionVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String resourceKey = "archigest.archivo.busquedas.operacion.accion.";

	private String label;
	private String value;

	public AccionVO(String key) {
		this.label = resourceKey + key;
		this.value = key;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
