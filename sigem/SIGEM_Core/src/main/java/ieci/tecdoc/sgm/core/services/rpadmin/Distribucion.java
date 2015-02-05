package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Lista de distribución de registros
 * 
 * 
 */
public class Distribucion {

	public static final int TIPO_USUARIO = 1;
	public static final int TIPO_DEPARTAMENTO = 2;
	public static final int TIPO_GRUPO = 3;
	public static final int TIPO_ORGANIZACION = 4;

	/**
	 * Identificador de la lista de distribución
	 */
	private int id;

	/**
	 * Identificador de la organización a la que pertenece la lista de
	 * distribución
	 */
	private int idOrganizacion;

	/**
	 * Tipo de destino de la distribución
	 * 
	 * @value: 1.- Usuario
	 * @value: 2.- Departamento
	 * @value: 3.- Grupo
	 */
	private int tipoDestino;

	/**
	 * Identificador del destinatario de la distribución
	 */
	private int idDestino;

	/**
	 * Nombre del destinatario
	 */
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(int idDestino) {
		this.idDestino = idDestino;
	}

	public int getIdOrganizacion() {
		return idOrganizacion;
	}

	public void setIdOrganizacion(int idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoDestino() {
		return tipoDestino;
	}

	public void setTipoDestino(int tipoDestino) {
		this.tipoDestino = tipoDestino;
	}

}
