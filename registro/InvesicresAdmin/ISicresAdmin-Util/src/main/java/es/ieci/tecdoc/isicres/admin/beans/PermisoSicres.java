package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Permiso de un usuario y oficina sobre un libro
 *
 */
public class PermisoSicres {

	public static final int TIPO_USUARIO = 1;
	public static final int TIPO_OFICINA = 2;
	public static final int TIPO_GRUPO = 3;	
	
	public static final int CENTRAL = 0;
	public static final int POR_OFICINA = 2;
	
	private int id;
	private int tipo;
	private String nombre;
	private boolean consultar;
	private boolean crear;
	private boolean modificar;
	private int idBook;
	private int numeracion;

	/**
	 * @return
	 */
	public int getIdBook() {
		return idBook;
	}
	/**
	 * @param idBook
	 */
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	/**
	 * @return
	 */
	public boolean getCrear() {
		return crear;
	}
	/**
	 * @param crear
	 */
	public void setCrear(boolean crear) {
		this.crear = crear;
	}
	/**
	 * @return
	 */
	public boolean getConsultar() {
		return consultar;
	}
	/**
	 * @param consultar
	 */
	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}
	/**
	 * @return
	 */
	public boolean getModificar() {
		return modificar;
	}
	/**
	 * @param modificar
	 */
	public void setModificar(boolean modificar) {
		this.modificar = modificar;
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
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return
	 */
	public int getNumeracion() {
		return numeracion;
	}
	/**
	 * @param numeracion
	 */
	public void setNumeracion(int numeracion) {
		this.numeracion = numeracion;
	}
}