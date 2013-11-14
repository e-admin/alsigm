package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

public class UsuarioForm extends RPAdminBaseForm {

	private static final long serialVersionUID = -213122056953242597L;

	private String[] attrsToUpper = new String[] { "primerApellido",
			"segundoApellido", "nombreIdentificacion", "direccion", "ciudad",
			"provincia", "email" };

	protected String id;
	protected String nombre;
	protected String idPerfil;
	protected String altaPersonas;
	protected String modificaPersonas;
	protected String introduccionFecha;
	protected String modificacionFecha;
	protected String modificacionCampos;
	protected String accesoOperaciones;
	protected String adaptacionRegistros;
	protected String rechazoRegistros;
	protected String archivoRegistros;
	protected String cambioDestinoDistribuidos;
	protected String cambioDestinoRechazados;
	protected String distribucionManual;
	protected String verDocumentos;
	protected String deleteDocumentos;
	protected String gestionUnidadesAdministrativas;
	protected String gestionInformes;
	protected String gestionTiposAsunto;
	protected String gestionUsuarios;
	protected String gestionTiposTransporte;
	protected String primerApellido;
	protected String segundoApellido;
	protected String nombreIdentificacion;
	protected String direccion;
	protected String ciudad;
	protected String codigoPostal;
	protected String provincia;
	protected String telefono;
	protected String fax;
	protected String email;
	protected String tipoUsuario;
	protected String treeId;
	protected String ldapGuid;
	protected String ldapFullName;


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombreIdentificacion() {
		return nombreIdentificacion;
	}
	public void setNombreIdentificacion(String nombreIdentificacion) {
		this.nombreIdentificacion = nombreIdentificacion;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getAccesoOperaciones() {
		return accesoOperaciones;
	}
	public void setAccesoOperaciones(String accesoOperaciones) {
		this.accesoOperaciones = accesoOperaciones;
	}
	public String getAdaptacionRegistros() {
		return adaptacionRegistros;
	}
	public void setAdaptacionRegistros(String adaptacionRegistros) {
		this.adaptacionRegistros = adaptacionRegistros;
	}
	public String getAltaPersonas() {
		return altaPersonas;
	}
	public void setAltaPersonas(String altaPersonas) {
		this.altaPersonas = altaPersonas;
	}
	public String getArchivoRegistros() {
		return archivoRegistros;
	}
	public void setArchivoRegistros(String archivoRegistros) {
		this.archivoRegistros = archivoRegistros;
	}
	public String getCambioDestinoDistribuidos() {
		return cambioDestinoDistribuidos;
	}
	public void setCambioDestinoDistribuidos(String cambioDestinoDistribuidos) {
		this.cambioDestinoDistribuidos = cambioDestinoDistribuidos;
	}
	public String getCambioDestinoRechazados() {
		return cambioDestinoRechazados;
	}
	public void setCambioDestinoRechazados(String cambioDestinoRechazados) {
		this.cambioDestinoRechazados = cambioDestinoRechazados;
	}
	public String getIntroduccionFecha() {
		return introduccionFecha;
	}
	public void setIntroduccionFecha(String introduccionFecha) {
		this.introduccionFecha = introduccionFecha;
	}
	public String getModificacionCampos() {
		return modificacionCampos;
	}
	public void setModificacionCampos(String modificacionCampos) {
		this.modificacionCampos = modificacionCampos;
	}
	public String getModificacionFecha() {
		return modificacionFecha;
	}
	public void setModificacionFecha(String modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}
	public String getModificaPersonas() {
		return modificaPersonas;
	}
	public void setModificaPersonas(String modificaPersonas) {
		this.modificaPersonas = modificaPersonas;
	}
	public String getRechazoRegistros() {
		return rechazoRegistros;
	}
	public void setRechazoRegistros(String rechazoRegistros) {
		this.rechazoRegistros = rechazoRegistros;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getDistribucionManual() {
		return distribucionManual;
	}
	public void setDistribucionManual(String distribucionManual) {
		this.distribucionManual = distribucionManual;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getLdapGuid() {
		return ldapGuid;
	}
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}

	public String getLdapFullName() {
		return ldapFullName;
	}
	public void setLdapFullName(String ldapFullName) {
		this.ldapFullName = ldapFullName;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	/**
	 * @return the verDocumentos
	 */
	public String getVerDocumentos() {
		return verDocumentos;
	}

	/**
	 * @param verDocumentos
	 *            the verDocumentos to set
	 */
	public void setVerDocumentos(String verDocumentos) {
		this.verDocumentos = verDocumentos;
	}


	/**
	 * @return the deleteDocumentos
	 */
	public String getDeleteDocumentos() {
		return deleteDocumentos;
	}

	/**
	 * @param deleteDocumentos
	 *            the verDocumentos to set
	 */
	public void setDeleteDocumentos(String deleteDocumentos) {
		this.deleteDocumentos = deleteDocumentos;
	}


	public String getGestionUnidadesAdministrativas() {
		return gestionUnidadesAdministrativas;
	}
	public void setGestionUnidadesAdministrativas(
			String gestionUnidadesAdministrativas) {
		this.gestionUnidadesAdministrativas = gestionUnidadesAdministrativas;
	}
	public String getGestionInformes() {
		return gestionInformes;
	}
	public void setGestionInformes(String gestionInformes) {
		this.gestionInformes = gestionInformes;
	}
	public String getGestionTiposAsunto() {
		return gestionTiposAsunto;
	}
	public void setGestionTiposAsunto(String gestionTiposAsunto) {
		this.gestionTiposAsunto = gestionTiposAsunto;
	}
	public String getGestionUsuarios() {
		return gestionUsuarios;
	}
	public void setGestionUsuarios(String gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
	}
	public String getGestionTiposTransporte() {
		return gestionTiposTransporte;
	}
	public void setGestionTiposTransporte(String gestionTiposTransporte) {
		this.gestionTiposTransporte = gestionTiposTransporte;
	}


}
