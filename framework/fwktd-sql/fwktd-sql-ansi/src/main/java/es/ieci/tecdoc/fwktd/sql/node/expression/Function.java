/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

/**
 * Función.
 */
public abstract class Function extends Expression {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// Members
	
	private static final long serialVersionUID = -9105028488964257509L;
	/**
	 * Nombre de la función
	 */
	protected String name;
}
