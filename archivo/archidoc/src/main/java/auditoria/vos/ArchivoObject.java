package auditoria.vos;

/**
 * Clase que encapsula los objetos Objeto de la aplicacion.
 */
public class ArchivoObject {
	/** Cte que idnetifica el objeto */
	private int id = 0;
	/** Nombre del objeto */
	private String name = null;
	/** Clase Java a la que esta asociado el objeto */
	private Class clase = null;

	public Class getClase() {
		return clase;
	}

	public void setClase(Class clase) {
		this.clase = clase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
