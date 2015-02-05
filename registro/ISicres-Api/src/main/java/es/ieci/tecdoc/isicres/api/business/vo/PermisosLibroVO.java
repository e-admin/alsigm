package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * Clase que identifica los permisos que se tienen sobre un libro. Estos
 * permisos se asocian a nivel de usuarios y oficinas/departamentos
 * 
 * Los permisos de un usuario para un libro serán la unión de los permisos
 * para ese usuario, y departamentos
 * 
 * Los permisos se podrán sumar, pero nunca restar.
 * 
 * Es decir si un departamento u oficina tiene permiso, un usuario de ese
 * departamento u oficina siempre tendrá ese permiso, no se puede eliminar
 * 
 * @author Iecisa
 * 
 * 
 * 
 */
public class PermisosLibroVO extends BasePermisosVO {

	private static final long serialVersionUID = 8105492578528444550L;

	protected String idLibro;

	protected boolean consulta;

	protected boolean creacion;

	/**
	 * permite modificar cualquier campo menos los protegidos protegidos: son
	 * los necesarios para que un registro sea completo
	 */
	protected boolean modificacion;

	protected boolean administrador;

	/**
	 * Constructor por defecto.
	 */
	public PermisosLibroVO() {
		setAdministrador(false);
	}

	public PermisosLibroVO(boolean superuser) {
		if (superuser) {
			setAdministrador(true);
			setConsulta(true);
			setCreacion(true);
			setModificacion(true);
		} else {
			setAdministrador(false);
		}
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public boolean isCreacion() {
		return creacion;
	}

	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}

	public boolean isModificacion() {
		return modificacion;
	}

	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}
}
