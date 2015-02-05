package salas.form;

import common.forms.CustomForm;

public class NavegadorRegistroConsultaSalaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	private String idArchivo;
	private String equipoInformatico;
	private String idSeleccionado = null;
	private String tipoSeleccionado = null;

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getEquipoInformatico() {
		return equipoInformatico;
	}

	public void setEquipoInformatico(String equipoInformatico) {
		this.equipoInformatico = equipoInformatico;
	}

	public String getIdSeleccionado() {
		return idSeleccionado;
	}

	public void setIdSeleccionado(String idSeleccionado) {
		this.idSeleccionado = idSeleccionado;
	}

	public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}
}