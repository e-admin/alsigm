package gcontrol.vos;

/**
 * Clase con información adicional del usuario.
 */
public class UsuarioExtendedVO extends UsuarioVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Nombre de la organización a la que pertenece el usuario */
	private String nombreOrgPertenece = null;
	/** Identificador de la organizacion a la que pertenece el user */
	private String idOrgPertenece = null;
	/**
	 * Identificador del archivo receptor del usuario(es el de su organo
	 * asociado)
	 */
	private String idArchivoReceptor = null;

	public UsuarioExtendedVO(UsuarioVO user) {
		this.setId(user.getId());
		this.setNombre(user.getNombre());
		this.setApellidos(user.getApellidos());
		this.setEmail(user.getEmail());
		this.setDireccion(user.getDireccion());
		this.setTipo(user.getTipo());
		this.setHabilitado(user.getHabilitado());
		this.setFMaxVigencia(user.getFMaxVigencia());
		this.setIdUsrsExtGestor(user.getIdUsrsExtGestor());
		this.setIdUsrSistOrg(user.getIdUsrSistOrg());
		this.setDescripcion(user.getDescripcion());
	}

	public String getIdOrgPertenece() {
		return idOrgPertenece;
	}

	public void setIdOrgPertenece(String idOrgPertenece) {
		this.idOrgPertenece = idOrgPertenece;
	}

	public String getNombreOrgPertenece() {
		return nombreOrgPertenece;
	}

	public void setNombreOrgPertenece(String nombreOrgPertenece) {
		this.nombreOrgPertenece = nombreOrgPertenece;
	}

	public String getIdArchivoReceptor() {
		return idArchivoReceptor;
	}

	public void setIdArchivoReceptor(String idArchivoReceptor) {
		this.idArchivoReceptor = idArchivoReceptor;
	}
}
