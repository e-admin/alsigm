package transferencias.forms;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en el proceso de recepción de relaciones
 * de entrega
 */
public class RecepcionRelacionesForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String codigo;
	String idusuarioseleccionado;
	String nombreusuarioseleccinado;
	boolean solicitarreserva;

	String idubicacionseleccionada;
	String formaDocumental;
	String idFormato;

	String idseleccionado;
	String idtiposeleccionado;
	String idtipopadre;
	String idpadre;

	String idDepositoReserva;
	String idTipoDepositoReserva;
	String idElementoSeleccionadoReserva;
	String idTipoElementoSeleccionadoReserva;
	String nombreFormato;

	String relacionSinDocsFisicos;
	String motivoRechazo;

	/*
	 * //String idnoasignableseleccionado; public String
	 * getIdnoasignableseleccionado() { return this.idnoasignableseleccionado; }
	 * public void setIdnoasignableseleccionado(String idedificioseleccionado) {
	 * this.idnoasignableseleccionado = idedificioseleccionado; }
	 */
	public String getIdubicacionseleccionada() {
		return this.idubicacionseleccionada;
	}

	public void setIdubicacionseleccionada(String iddepositoseleccionado) {
		this.idubicacionseleccionada = iddepositoseleccionado;
	}

	public boolean isSolicitarreserva() {
		return this.solicitarreserva;
	}

	public void setSolicitarreserva(boolean solicitarreserva) {
		this.solicitarreserva = solicitarreserva;
	}

	public String getIdusuarioseleccionado() {
		return idusuarioseleccionado;
	}

	public void setIdusuarioseleccionado(String idusuarioseleccionado) {
		this.idusuarioseleccionado = idusuarioseleccionado;
	}

	public String getNombreusuarioseleccinado() {
		return nombreusuarioseleccinado;
	}

	public void setNombreusuarioseleccinado(String nombreusuarioseleccinado) {
		this.nombreusuarioseleccinado = nombreusuarioseleccinado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFormaDocumental() {
		return formaDocumental;
	}

	public void setFormaDocumental(String formaDocumental) {
		this.formaDocumental = formaDocumental;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	/**
	 * @return el idpadre
	 */
	public String getIdpadre() {
		return idpadre;
	}

	/**
	 * @param idpadre
	 *            el idpadre a establecer
	 */
	public void setIdpadre(String idpadre) {
		this.idpadre = idpadre;
	}

	/**
	 * @return el idseleccionado
	 */
	public String getIdseleccionado() {
		return idseleccionado;
	}

	/**
	 * @param idseleccionado
	 *            el idseleccionado a establecer
	 */
	public void setIdseleccionado(String idseleccionado) {
		this.idseleccionado = idseleccionado;
	}

	/**
	 * @return el idtipopadre
	 */
	public String getIdtipopadre() {
		return idtipopadre;
	}

	/**
	 * @param idtipopadre
	 *            el idtipopadre a establecer
	 */
	public void setIdtipopadre(String idtipopadre) {
		this.idtipopadre = idtipopadre;
	}

	/**
	 * @return el idtiposeleccionado
	 */
	public String getIdtiposeleccionado() {
		return idtiposeleccionado;
	}

	/**
	 * @param idtiposeleccionado
	 *            el idtiposeleccionado a establecer
	 */
	public void setIdtiposeleccionado(String idtiposeleccionado) {
		this.idtiposeleccionado = idtiposeleccionado;
	}

	/**
	 * @return el idDepositoReserva
	 */
	public String getIdDepositoReserva() {
		return idDepositoReserva;
	}

	/**
	 * @param idDepositoReserva
	 *            el idDepositoReserva a establecer
	 */
	public void setIdDepositoReserva(String idDepositoReserva) {
		this.idDepositoReserva = idDepositoReserva;
	}

	/**
	 * @return el idElementoSeleccionadoReserva
	 */
	public String getIdElementoSeleccionadoReserva() {
		return idElementoSeleccionadoReserva;
	}

	/**
	 * @param idElementoSeleccionadoReserva
	 *            el idElementoSeleccionadoReserva a establecer
	 */
	public void setIdElementoSeleccionadoReserva(String idElementoSeleccionado) {
		this.idElementoSeleccionadoReserva = idElementoSeleccionado;
	}

	/**
	 * @return el idTipoDepositoReserva
	 */
	public String getIdTipoDepositoReserva() {
		return idTipoDepositoReserva;
	}

	/**
	 * @param idTipoDepositoReserva
	 *            el idTipoDepositoReserva a establecer
	 */
	public void setIdTipoDepositoReserva(String idTipoDepositoReserva) {
		this.idTipoDepositoReserva = idTipoDepositoReserva;
	}

	/**
	 * @return el idTipoElementoSeleccionadoReserva
	 */
	public String getIdTipoElementoSeleccionadoReserva() {
		return idTipoElementoSeleccionadoReserva;
	}

	/**
	 * @param idTipoElementoSeleccionadoReserva
	 *            el idTipoElementoSeleccionadoReserva a establecer
	 */
	public void setIdTipoElementoSeleccionadoReserva(
			String idTipoElementoSeleccionado) {
		this.idTipoElementoSeleccionadoReserva = idTipoElementoSeleccionado;
	}

	public String getNombreFormato() {
		return nombreFormato;
	}

	public void setNombreFormato(String nombreFormato) {
		this.nombreFormato = nombreFormato;
	}

	public String getRelacionSinDocsFisicos() {
		return relacionSinDocsFisicos;
	}

	public void setRelacionSinDocsFisicos(String relacionSinDocsFisicos) {
		this.relacionSinDocsFisicos = relacionSinDocsFisicos;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

}