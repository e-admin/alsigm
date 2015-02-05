package transferencias.forms;

import common.forms.CustomForm;

/**
 *
 */
public class AsignacionCajasForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idRelacion = null;
	int numeroCaja;
	String idUnidadInstalacion = null;
	String idUnidadDocumental = null;
	String[] udocSeleccionada = null;
	String descripcionContenido = null;
	String signaturaCaja = null;

	int ordenCaja;
	boolean asignando = false;

	public String getSignaturaCaja() {
		return signaturaCaja;
	}

	public void setSignaturaCaja(String signaturaCaja) {
		this.signaturaCaja = signaturaCaja;
	}

	public String getDescripcionContenido() {
		return descripcionContenido;
	}

	public void setDescripcionContenido(String descripcionContenido) {
		this.descripcionContenido = descripcionContenido;
	}

	int cambioPosicion = 0;

	public int getCambioPosicion() {
		return cambioPosicion;
	}

	public void setCambioPosicion(int cambioPosicion) {
		this.cambioPosicion = cambioPosicion;
	}

	public String getIdUnidadDocumental() {
		return idUnidadDocumental;
	}

	public void setIdUnidadDocumental(String idUnidadDocumental) {
		this.idUnidadDocumental = idUnidadDocumental;
	}

	public String getIdUnidadInstalacion() {
		return idUnidadInstalacion;
	}

	public void setIdUnidadInstalacion(String idUnidadInstalacion) {
		this.idUnidadInstalacion = idUnidadInstalacion;
	}

	public String[] getUdocSeleccionada() {
		return udocSeleccionada;
	}

	public void setUdocSeleccionada(String[] idParte) {
		this.udocSeleccionada = idParte;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	/**
	 * @return el ordenCaja
	 */
	public int getOrdenCaja() {
		return ordenCaja;
	}

	/**
	 * @param ordenCaja
	 *            el ordenCaja a establecer
	 */
	public void setOrdenCaja(int ordenCaja) {
		this.ordenCaja = ordenCaja;
	}

	public boolean isAsignando() {
		return asignando;
	}

	public void setAsignando(boolean asignando) {
		this.asignando = asignando;
	}

}