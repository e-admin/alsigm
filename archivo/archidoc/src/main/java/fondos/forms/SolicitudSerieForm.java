package fondos.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en el proceso de identificación de una
 * serie
 */
public class SolicitudSerieForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idSerie = null;
	String codigo = null;
	String denominacion = null;
	String observaciones = null;
	String motivo = null;
	String idNivelSerie = null;
	String idNivelPadre = null;
	int nivelAcceso;
	String codOrdenacion = null;

	String idPadre = null;
	String idGestor = null;
	boolean seleccionNuevoGestor = false;
	String idRepEcmSerie = null;
	String idLCA = null;
	String idFichaDescriptivaSerie = null;

	String fondoSerie = null;
	String tituloSerie = null;
	Boolean autorizacionAutomatica = false;
	String[] solicitudSeleccionada = null;
	String fechaInicial = null;

	/**
	 * @return Returns the idFichaClDocumentalUdocSerie.
	 */
	/*
	 * public String getIdFichaClDocumentalUdocSerie() { return
	 * idFichaClDocumentalUdocSerie; }
	 */
	/**
	 * @param idFichaClDocumentalUdocSerie
	 *            The idFichaClDocumentalUdocSerie to set.
	 */
	/*
	 * public void setIdFichaClDocumentalUdocSerie(String
	 * idFichaClDocumentalUdocSerie) { this.idFichaClDocumentalUdocSerie =
	 * idFichaClDocumentalUdocSerie; }
	 */
	/**
	 * @return Returns the idFichaDescriptivaSerie.
	 */
	public String getIdFichaDescriptivaSerie() {
		return idFichaDescriptivaSerie;
	}

	/**
	 * @param idFichaDescriptivaSerie
	 *            The idFichaDescriptivaSerie to set.
	 */
	public void setIdFichaDescriptivaSerie(String idFichaDescriptivaSerie) {
		this.idFichaDescriptivaSerie = idFichaDescriptivaSerie;
	}

	/**
	 * @return Returns the idFichaDescriptivaUdocSerie.
	 */
	/*
	 * public String getIdFichaDescriptivaUdocSerie() { return
	 * idFichaDescriptivaUdocSerie; }
	 */
	/**
	 * @param idFichaDescriptivaUdocSerie
	 *            The idFichaDescriptivaUdocSerie to set.
	 */
	/*
	 * public void setIdFichaDescriptivaUdocSerie(String
	 * idFichaDescriptivaUdocSerie) { this.idFichaDescriptivaUdocSerie =
	 * idFichaDescriptivaUdocSerie; }
	 */
	/**
	 * @return Returns the idVolSerie.
	 */
	public String getIdRepEcmSerie() {
		return idRepEcmSerie;
	}

	/**
	 * @param idRepEcmSerie
	 *            The idVolSerie to set.
	 */
	public void setIdRepEcmSerie(String idRepEcmSerie) {
		this.idRepEcmSerie = idRepEcmSerie;
	}

	public boolean isAutorizacionAutomatica() {
		return autorizacionAutomatica;
	}

	public void setAutorizacionAutomatica(boolean autorizacionAutomatica) {
		this.autorizacionAutomatica = autorizacionAutomatica;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdGestor() {
		return idGestor;
	}

	public void setIdGestor(String idGestor) {
		this.idGestor = idGestor;
	}

	public String getIdNivelSerie() {
		return idNivelSerie;
	}

	public void setIdNivelSerie(String idNivelSerie) {
		this.idNivelSerie = idNivelSerie;
	}

	public String getIdNivelPadre() {
		return idNivelPadre;
	}

	public void setIdNivelPadre(String idNivelPadre) {
		this.idNivelPadre = idNivelPadre;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public boolean getSeleccionNuevoGestor() {
		return seleccionNuevoGestor;
	}

	public void setSeleccionNuevoGestor(boolean seleccionNuevoGestor) {
		this.seleccionNuevoGestor = seleccionNuevoGestor;
	}

	public String[] getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(String[] solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public String getFondoSerie() {
		return fondoSerie;
	}

	public void setFondoSerie(String fondoSerie) {
		this.fondoSerie = fondoSerie;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public String getCodOrdenacion() {
		return codOrdenacion;
	}

	public void setCodOrdenacion(String codOrdenacion) {
		this.codOrdenacion = codOrdenacion;
	}

}
