package gcontrol.forms;

import common.forms.CustomForm;

public class OrganoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	String idOrgano;
	String codigo;
	String archivoReceptor;
	String descripcion;
	String nombre;
	String nombreLargo;
	String sistemaExterno;
	String idEnSistemaExterno;
	String idOrganoSeleccionado;
	private String vigente;

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the sistemaExterno.
	 */
	public String getSistemaExterno() {
		return sistemaExterno;
	}

	/**
	 * @param sistemaExterno
	 *            The sistemaExterno to set.
	 */
	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}

	/**
	 * @return Returns the idOrganoSeleccionado.
	 */
	public String getIdOrganoSeleccionado() {
		return idOrganoSeleccionado;
	}

	/**
	 * @param idOrganoSeleccionado
	 *            The idOrganoSeleccionado to set.
	 */
	public void setIdOrganoSeleccionado(String idOrganoSeleccionado) {
		this.idOrganoSeleccionado = idOrganoSeleccionado;
	}

	/**
	 * @return Returns the archivoReceptor.
	 */
	public String getArchivoReceptor() {
		return archivoReceptor;
	}

	/**
	 * @param archivoReceptor
	 *            The archivoReceptor to set.
	 */
	public void setArchivoReceptor(String archivoReceptor) {
		this.archivoReceptor = archivoReceptor;
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the idEnSistemaExterno.
	 */
	public String getIdEnSistemaExterno() {
		return idEnSistemaExterno;
	}

	/**
	 * @param idEnSistemaExterno
	 *            The idEnSistemaExterno to set.
	 */
	public void setIdEnSistemaExterno(String idEnSistemaExterno) {
		this.idEnSistemaExterno = idEnSistemaExterno;
	}

	/**
	 * @return Returns the id.
	 */
	public String getIdOrgano() {
		return idOrgano;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setIdOrgano(String idOrgano) {
		this.idOrgano = idOrgano;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	public String getVigente() {
		return vigente;
	}
}
