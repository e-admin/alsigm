package auditoria.vos;

import common.vos.BaseVO;

/**
 * Clase que encapsula los objetos acciones de los módulos de la aplicación.
 */
public class ArchivoAction extends BaseVO implements Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador único de la acción */
	private int id = 0;
	/** Nombre de la acción **/
	private String name = null;
	/** Modulo al que está asociado la accion */
	private ArchivoModule module = null;
	/** Criticidad de la accion */
	private int logLevel = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdModule() {
		return this.module.getId();
	}

	public void setIdModule(int idModule) {
		this.module.setId(idModule);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameModule() {
		return this.module.getName();
	}

	public void setNameModule(String nameModule) {
		this.module.setName(nameModule);
	}

	public ArchivoModule getModule() {
		return module;
	}

	public void setModule(ArchivoModule module) {
		this.module = module;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public int compareTo(Object o) {
		int result = 0;

		if (o != null && o.getClass().equals(this.getClass())) {
			ArchivoAction am = (ArchivoAction) o;

			result = this.getName().compareTo(am.getName());
		} else
			throw new ClassCastException(
					"Excepcion tratando de comparar un ArchivoAction.class y un "
							+ o.getClass());

		return result;
	}
}
