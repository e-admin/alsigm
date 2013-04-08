package deposito.forms;

import common.forms.CustomForm;

public class ReubicacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idAsignable = null;
	int[] numeroOrdenHueco = null;
	String elementoDestino = null;

	public String getIdAsignable() {
		return idAsignable;
	}

	public void setIdAsignable(String idAsignable) {
		this.idAsignable = idAsignable;
	}

	public int[] getNumeroOrdenHueco() {
		return numeroOrdenHueco;
	}

	public void setNumeroOrdenHueco(int[] numeroOrdenHueco) {
		this.numeroOrdenHueco = numeroOrdenHueco;
	}

	public String getElementoDestino() {
		return elementoDestino;
	}

	public void setElementoDestino(String elementoDestino) {
		this.elementoDestino = elementoDestino;
	}
}