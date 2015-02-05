package auditoria.vos;

import common.vos.BaseVO;

/**
 * Clase que encapsula los objetos de nivel de log de la aplicación
 */
public class ArchivoLogLevel extends BaseVO implements Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador único del nivel de log */
	private int id = 0;
	/** Nombre del nivel de log */
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
			ArchivoLogLevel all = (ArchivoLogLevel) o;

			result = new Integer(this.getId()).compareTo(new Integer(all
					.getId()));
		} else
			throw new ClassCastException(
					"Excepcion tratando de comparar un ArchivoLogLevel.class y un "
							+ o.getClass());

		return result;
	}
}
