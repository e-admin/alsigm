package common.security;

import common.Constants;

/**
 * Clase que mapea acciones con sus modulos asociados
 */
public class ActionObject {
	private String nombre = null;
	private int module = 0;

	public static ActionObject getInstance(String nombre, int module) {
		return new ActionObject(nombre, module);
	}

	private ActionObject(String nombre, int module) {
		this.nombre = nombre;
		this.module = module;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return new StringBuffer().append("<ActionObject>")
				.append(Constants.NEWLINE).append("  <Name>")
				.append(nombre != null ? nombre : "").append("</Name>")
				.append(Constants.NEWLINE).append("  <Module>").append(module)
				.append("</Module>").append(Constants.NEWLINE)
				.append("</ActionObject>").toString();
	}

	public boolean equals(ActionObject action) {
		return nombre.equalsIgnoreCase(action.getNombre())
				&& module == action.getModule();

	}
}
