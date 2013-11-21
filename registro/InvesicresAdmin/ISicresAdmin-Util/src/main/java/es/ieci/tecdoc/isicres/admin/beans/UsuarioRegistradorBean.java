package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Extensión de los datos de usuario para la gestión de la información del
 * usuario, direccion, tipo de usuario y permisos
 *
 * @author 66575267
 *
 */
public class UsuarioRegistradorBean {

	private int id;
	private String nombre;
	private int idPerfil;
	private boolean altaPersonas;
	private boolean modificaPersonas;
	private boolean introduccionFecha;
	private boolean modificacionFecha;
	private boolean modificacionCampos;
	private boolean accesoOperaciones;
	private boolean adaptacionRegistros;
	private boolean rechazoRegistros;
	private boolean archivoRegistros;
	private boolean cambioDestinoDistribuidos;
	private boolean cambioDestinoRechazados;
	private boolean distribucionManual;
	private boolean verDocumentos;
	private boolean deleteDocumentos;
	private boolean gestionUnidadesAdministrativas;
	private boolean gestionInformes;
	private boolean gestionTiposAsunto;
	private boolean gestionUsuarios;
	private boolean gestionTiposTransporte;
	private String primerApellido;
	private String segundoApellido;
	private String nombreIdentificacion;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String telefono;
	private String fax;
	private String email;
	private String ldapguid;
	private String ldapfullname;

	/**
	 * @return
	 */
	public boolean getAccesoOperaciones() {
		return accesoOperaciones;
	}

	/**
	 * @param accesoOperaciones
	 */
	public void setAccesoOperaciones(boolean accesoOperaciones) {
		this.accesoOperaciones = accesoOperaciones;
	}

	/**
	 * @return
	 */
	public boolean getAdaptacionRegistros() {
		return adaptacionRegistros;
	}

	/**
	 * @param adaptacionRegistros
	 */
	public void setAdaptacionRegistros(boolean adaptacionRegistros) {
		this.adaptacionRegistros = adaptacionRegistros;
	}

	/**
	 * @return
	 */
	public boolean getAltaPersonas() {
		return altaPersonas;
	}

	/**
	 * @param altaPersonas
	 */
	public void setAltaPersonas(boolean altaPersonas) {
		this.altaPersonas = altaPersonas;
	}

	/**
	 * @return
	 */
	public boolean getArchivoRegistros() {
		return archivoRegistros;
	}

	/**
	 * @param archivoRegistros
	 */
	public void setArchivoRegistros(boolean archivoRegistros) {
		this.archivoRegistros = archivoRegistros;
	}

	/**
	 * @return
	 */
	public boolean getCambioDestinoDistribuidos() {
		return cambioDestinoDistribuidos;
	}

	/**
	 * @param cambioDestinoDistribuidos
	 */
	public void setCambioDestinoDistribuidos(boolean cambioDestinoDistribuidos) {
		this.cambioDestinoDistribuidos = cambioDestinoDistribuidos;
	}

	/**
	 * @return
	 */
	public boolean getCambioDestinoRechazados() {
		return cambioDestinoRechazados;
	}

	/**
	 * @param cambioDestinoRechazados
	 */
	public void setCambioDestinoRechazados(boolean cambioDestinoRechazados) {
		this.cambioDestinoRechazados = cambioDestinoRechazados;
	}

	/**
	 * @return
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return
	 */
	public boolean isDistribucionManual() {
		return distribucionManual;
	}

	/**
	 * @param distribucionManual
	 */
	public void setDistribucionManual(boolean distribucionManual) {
		this.distribucionManual = distribucionManual;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public int getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil
	 */
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return
	 */
	public boolean getIntroduccionFecha() {
		return introduccionFecha;
	}

	/**
	 * @param introduccionFecha
	 */
	public void setIntroduccionFecha(boolean introduccionFecha) {
		this.introduccionFecha = introduccionFecha;
	}

	/**
	 * @return
	 */
	public boolean getModificacionCampos() {
		return modificacionCampos;
	}

	/**
	 * @param modificacionCampos
	 */
	public void setModificacionCampos(boolean modificacionCampos) {
		this.modificacionCampos = modificacionCampos;
	}

	/**
	 * @return
	 */
	public boolean getModificacionFecha() {
		return modificacionFecha;
	}

	/**
	 * @param modificacionFecha
	 */
	public void setModificacionFecha(boolean modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}

	/**
	 * @return
	 */
	public boolean getModificaPersonas() {
		return modificaPersonas;
	}

	/**
	 * @param modificaPersonas
	 */
	public void setModificaPersonas(boolean modificaPersonas) {
		this.modificaPersonas = modificaPersonas;
	}

	/**
	 * @return
	 */
	public String getNombreIdentificacion() {
		return nombreIdentificacion;
	}

	/**
	 * @param nombreIdentificacion
	 */
	public void setNombreIdentificacion(String nombreIdentificacion) {
		this.nombreIdentificacion = nombreIdentificacion;
	}

	/**
	 * @return
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return
	 */
	public boolean getRechazoRegistros() {
		return rechazoRegistros;
	}

	/**
	 * @param rechazoRegistros
	 */
	public void setRechazoRegistros(boolean rechazoRegistros) {
		this.rechazoRegistros = rechazoRegistros;
	}

	/**
	 * @return
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 */
	public String getLdapGuid() {
		return ldapguid;
	}

	/**
	 * @param ldapguid
	 */
	public void setLdapGuid(String ldapguid) {
		this.ldapguid = ldapguid;
	}

	/**
	 * @return
	 */
	public String getLdapFullName() {
		return ldapfullname;
	}

	/**
	 * @param ldapfullname
	 */
	public void setLdapFullName(String ldapfullname) {
		this.ldapfullname = ldapfullname;
	}

	/**
	 * @return the verDocumentos
	 */
	public boolean isVerDocumentos() {
		return verDocumentos;
	}

	/**
	 * @param verDocumentos
	 *            the verDocumentos to set
	 */
	public void setVerDocumentos(boolean verDocumentos) {
		this.verDocumentos = verDocumentos;
	}
	/**
	 * @return the deleteDocumentos
	 */
	public boolean isDeleteDocumentos() {
		return deleteDocumentos;
	}

	/**
	 * @param deleteDocumentos
	 *            the verDocumentos to set
	 */
	public void setDeleteDocumentos(boolean deleteDocumentos) {
		this.deleteDocumentos = deleteDocumentos;
	}

	public boolean isGestionUnidadesAdministrativas() {
		return gestionUnidadesAdministrativas;
	}

	public void setGestionUnidadesAdministrativas(
			boolean gestionUnidadesAdministrativas) {
		this.gestionUnidadesAdministrativas = gestionUnidadesAdministrativas;
	}

	public boolean isGestionInformes() {
		return gestionInformes;
	}

	public void setGestionInformes(boolean gestionInformes) {
		this.gestionInformes = gestionInformes;
	}

	public boolean isGestionTiposAsunto() {
		return gestionTiposAsunto;
	}

	public void setGestionTiposAsunto(boolean gestionTiposAsunto) {
		this.gestionTiposAsunto = gestionTiposAsunto;
	}

	public boolean isGestionUsuarios() {
		return gestionUsuarios;
	}

	public void setGestionUsuarios(boolean gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
	}

	public boolean isGestionTiposTransporte() {
		return gestionTiposTransporte;
	}

	public void setGestionTiposTransporte(boolean gestionTiposTransporte) {
		this.gestionTiposTransporte = gestionTiposTransporte;
	}



}
