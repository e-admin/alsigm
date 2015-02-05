package transferencias.forms;

import se.terceros.TipoAtributo;

import common.Constants;
import common.forms.CustomForm;

/**
 * ActionForm empleado en la recogida de los datos introducidos por el usuario
 * en la creacion y edicion de un interesado asociado a una unidad documental
 *
 */
public class InteresadoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int BUSQUEDA_POR_IF = 0;

	public static final int BUSQUEDA_POR_NOMBRE = 1;

	public static final int BUSQUEDA_POR_RAZON_SOCIAL = 2;

	public static final String[] TIPOS_BUSQUEDA = { "IF", "NOMBRE",
			"RAZON_SOCIAL" };


	String nombre = null;

	String nombre_idref = null;

	String nombre_tiporef = null;

	String numeroIdentificacion = null;

	int tipoIdentificacion = Constants.TIPO_DOCUMENTO_NIF; // Indica si el
															// numero de
															// Identificacion es
															// CIF
	// o NIF Valores (0-Nif, 1-Cif);

	int tipoInteresado = Constants.TIPO_INTERESADO_VALIDADO; // Indica si el
																// tipo es
																// Validado (0)
																// o No Validado
																// (1)
	int tipoNumeroIdentificacion = TipoAtributo.IDENTIFICACION_CIF;

	String rol = null;

	boolean validado = false;

	String buscarPor = TIPOS_BUSQUEDA[BUSQUEDA_POR_NOMBRE];

	String personalidad = null;

	String nameSearchToken = null;

	String surname1SearchToken = null;

	String surname2SearchToken = null;

	String companySearchToken = null;

	String niSearchToken = null;

	String[] tercerosSeleccionados = null;

	String[] seleccionInteresado = null;

	boolean validacionCorrecta = false;

	String contextPath = null;

	private String ref = null;

	/* Atributos para la búsqueda de interesados en la ficha */
	String descripcionIdentidad = null;
	String descripcionNumIdentidad = null;
	String descripcionRol = null;
	String descripcionValidado = null;
	String descripcionIdTercero = null;

	/* Valores de referencias para la búsqueda de interesados en la ficha */
	String refDescripcionIdentidad = null;
	String refDescripcionNumIdentidad = null;
	String refDescripcionRol = null;
	String refDescripcionValidado = null;
	String refDescripcionIdTercero = null;

	/* Valores para las listas */
	String listasDescripcionIdentidad = null;

	/* Tipos de referencia */
	String refTypeDescripcionIdentidad = null;

	/*
	 * Identificador de la fila a borrar si se cierra el pop-up sin seleccionar
	 * ningún valor
	 */
	String idRow = null;

	/* Indica el link pulsado en la jsp */
	String linkPressed = null;

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(int tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public int getTipoInteresado() {
		return tipoInteresado;
	}

	public void setTipoInteresado(int tipoInteresado) {
		this.tipoInteresado = tipoInteresado;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getPersonalidad() {
		return personalidad;
	}

	public void setPersonalidad(String personalidad) {
		this.personalidad = personalidad;
	}

	public String[] getSeleccionTerceros() {
		return tercerosSeleccionados;
	}

	public void setSeleccionTerceros(String[] tercerosSeleccionados) {
		this.tercerosSeleccionados = tercerosSeleccionados;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getBuscarPor() {
		return buscarPor;
	}

	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}

	public void clear() {
		this.nombre = null;
		this.numeroIdentificacion = null;
	}

	public String getNameSearchToken() {
		return nameSearchToken;
	}

	public void setNameSearchToken(String nameSearchToken) {
		this.nameSearchToken = nameSearchToken;
	}

	public String getNiSearchToken() {
		return niSearchToken;
	}

	public void setNiSearchToken(String niSearchToken) {
		this.niSearchToken = niSearchToken;
	}

	public String[] getSeleccionInteresado() {
		return seleccionInteresado;
	}

	public void setSeleccionInteresado(String[] seleccionInteresado) {
		this.seleccionInteresado = seleccionInteresado;
	}

	public String getCompanySearchToken() {
		return companySearchToken;
	}

	public void setCompanySearchToken(String companySearchToken) {
		this.companySearchToken = companySearchToken;
	}

	public String getSurname1SearchToken() {
		return surname1SearchToken;
	}

	public void setSurname1SearchToken(String surname1SearchToken) {
		this.surname1SearchToken = surname1SearchToken;
	}

	public String getSurname2SearchToken() {
		return surname2SearchToken;
	}

	public void setSurname2SearchToken(String surname2SearchToken) {
		this.surname2SearchToken = surname2SearchToken;
	}

	public String getNombre_idref() {
		return nombre_idref;
	}

	public void setNombre_idref(String nombre_idref) {
		this.nombre_idref = nombre_idref;
	}

	public String getNombre_tiporef() {
		return nombre_tiporef;
	}

	public void setNombre_tiporef(String nombre_tiporef) {
		this.nombre_tiporef = nombre_tiporef;
	}

	public boolean isValidacionCorrecta() {
		return validacionCorrecta;
	}

	public void setValidacionCorrecta(boolean validacionCorrecta) {
		this.validacionCorrecta = validacionCorrecta;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDescripcionIdentidad() {
		return descripcionIdentidad;
	}

	public void setDescripcionIdentidad(String descripcionIdentidad) {
		this.descripcionIdentidad = descripcionIdentidad;
	}

	public String getDescripcionIdTercero() {
		return descripcionIdTercero;
	}

	public void setDescripcionIdTercero(String descripcionIdTercero) {
		this.descripcionIdTercero = descripcionIdTercero;
	}

	public String getDescripcionNumIdentidad() {
		return descripcionNumIdentidad;
	}

	public void setDescripcionNumIdentidad(String descripcionNumIdentidad) {
		this.descripcionNumIdentidad = descripcionNumIdentidad;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public String getDescripcionValidado() {
		return descripcionValidado;
	}

	public void setDescripcionValidado(String descripcionValidado) {
		this.descripcionValidado = descripcionValidado;
	}

	public String getRefDescripcionIdentidad() {
		return refDescripcionIdentidad;
	}

	public void setRefDescripcionIdentidad(String refDescripcionIdentidad) {
		this.refDescripcionIdentidad = refDescripcionIdentidad;
	}

	public String getRefDescripcionIdTercero() {
		return refDescripcionIdTercero;
	}

	public void setRefDescripcionIdTercero(String refDescripcionIdTercero) {
		this.refDescripcionIdTercero = refDescripcionIdTercero;
	}

	public String getRefDescripcionNumIdentidad() {
		return refDescripcionNumIdentidad;
	}

	public void setRefDescripcionNumIdentidad(String refDescripcionNumIdentidad) {
		this.refDescripcionNumIdentidad = refDescripcionNumIdentidad;
	}

	public String getRefDescripcionRol() {
		return refDescripcionRol;
	}

	public void setRefDescripcionRol(String refDescripcionRol) {
		this.refDescripcionRol = refDescripcionRol;
	}

	public String getRefDescripcionValidado() {
		return refDescripcionValidado;
	}

	public void setRefDescripcionValidado(String refDescripcionValidado) {
		this.refDescripcionValidado = refDescripcionValidado;
	}

	public String getListasDescripcionIdentidad() {
		return listasDescripcionIdentidad;
	}

	public void setListasDescripcionIdentidad(String listasDescripcionIdentidad) {
		this.listasDescripcionIdentidad = listasDescripcionIdentidad;
	}

	public String getRefTypeDescripcionIdentidad() {
		return refTypeDescripcionIdentidad;
	}

	public void setRefTypeDescripcionIdentidad(
			String refTypeDescripcionIdentidad) {
		this.refTypeDescripcionIdentidad = refTypeDescripcionIdentidad;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public String getLinkPressed() {
		return linkPressed;
	}

	public void setLinkPressed(String linkPressed) {
		this.linkPressed = linkPressed;
	}

	public int getTipoNumeroIdentificacion() {
		return tipoNumeroIdentificacion;
	}

	public void setTipoNumeroIdentificacion(int tipoNumeroIdentificacion) {
		this.tipoNumeroIdentificacion = tipoNumeroIdentificacion;
	}

}
