package auditoria.vos;

import common.vos.BaseVO;

/**
 * Clase que encapsula los errores de la aplicacion
 */
public class ArchivoError extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador único del error */
	private int id = 0;
	/** Nombre del error */
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
