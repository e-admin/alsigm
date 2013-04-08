package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * 
 * VO que contiene los datos de un usuario
 * 
 */
public class UsuarioRegistrador {

	private int id = 0;
	private String nombre = null;
	private String perfil = null;
	private String oficinaRegistro = null;
	private String nombreCompleto = null;

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
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return
	 */
	public String getOficinaRegistro() {
		return oficinaRegistro;
	}

	/**
	 * @param oficinaRegistro
	 */
	public void setOficinaRegistro(String oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}

	/**
	 * @return
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}
