package deposito.forms;

import common.forms.CustomForm;

public class GestionEstadoHuecoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idAsignable = null;
	String estadoAEstablecer = null;
	String estadoASeleccionar = null;
	boolean hayElementoASeleccionar = false;
	int[] numeroOrdenHueco = null;
	String idHueco = null;
	String numeracion = null;
	boolean renumerar = false;

	public String getEstadoAEstablecer() {
		return estadoAEstablecer;
	}

	public void setEstadoAEstablecer(String estadoAEstablecer) {
		this.estadoAEstablecer = estadoAEstablecer;
	}

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

	public String getEstadoASeleccionar() {
		return estadoASeleccionar;
	}

	public void setEstadoASeleccionar(String estadoASeleccionar) {
		this.estadoASeleccionar = estadoASeleccionar;
	}

	public boolean isHayElementoASeleccionar() {
		return hayElementoASeleccionar;
	}

	public void setHayElementoASeleccionar(boolean hayElementoASeleccionar) {
		this.hayElementoASeleccionar = hayElementoASeleccionar;
	}

	public String getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}

	public String getIdHueco() {
		return idHueco;
	}

	public void setIdHueco(String idHueco) {
		this.idHueco = idHueco;
	}

	public boolean isRenumerar() {
		return renumerar;
	}

	public void setRenumerar(boolean renumerar) {
		this.renumerar = renumerar;
	}
}