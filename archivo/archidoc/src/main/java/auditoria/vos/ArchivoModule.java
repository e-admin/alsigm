package auditoria.vos;

import common.vos.BaseVO;

/**
 * Clase que encapsula los objetos módulos de la aplicacion
 */
public class ArchivoModule extends BaseVO implements Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador único del módulo */
	private int id = 0;
	/** Nombre del módulo */
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

	public int compareTo(Object o) {
		int result = 0;

		if (o != null && o.getClass().equals(this.getClass())) {
			ArchivoModule am = (ArchivoModule) o;

			result = this.getName().compareTo(am.getName());
		} else
			throw new ClassCastException(
					"Excepcion tratando de comparar un ArchivoModule.class y un "
							+ o.getClass());

		return result;
	}
}
