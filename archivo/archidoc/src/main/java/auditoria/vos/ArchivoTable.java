package auditoria.vos;

import common.vos.BaseVO;

/**
 * Clase que encapsula los objetos tabla de la aplicacion
 */
public class ArchivoTable extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador único de la tabla */
	private int id = 0;
	/** Nombre de la tabla */
	private String name = null;

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
