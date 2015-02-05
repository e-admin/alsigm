package transferencias.forms;

import common.forms.CustomForm;

public class UInstRelacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int[] elementosSeleccionados = null;
	private int[] elementosElectronicosSel = null;
	private String idRelacionEntrega;
	private boolean electronicas;

	public int[] getElementosSeleccionados() {
		return elementosSeleccionados;
	}

	public void setElementosSeleccionados(int[] elementosSeleccionados) {
		this.elementosSeleccionados = elementosSeleccionados;
	}

	public String getIdRelacionEntrega() {
		return idRelacionEntrega;
	}

	public void setIdRelacionEntrega(String idRelacionEntrega) {
		this.idRelacionEntrega = idRelacionEntrega;
	}

	/**
	 * @return el elementosElectronicosSel
	 */
	public int[] getElementosElectronicosSel() {
		return elementosElectronicosSel;
	}

	/**
	 * @param elementosElectronicosSel
	 *            el elementosElectronicosSel a establecer
	 */
	public void setElementosElectronicosSel(int[] elementosElectronicosSel) {
		this.elementosElectronicosSel = elementosElectronicosSel;
	}

	/**
	 * @return el electronicas
	 */
	public boolean isElectronicas() {
		return electronicas;
	}

	/**
	 * @param electronicas
	 *            el electronicas a establecer
	 */
	public void setElectronicas(boolean electronicas) {
		this.electronicas = electronicas;
	}

}
