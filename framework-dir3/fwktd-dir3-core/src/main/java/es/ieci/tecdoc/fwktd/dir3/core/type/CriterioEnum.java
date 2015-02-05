package es.ieci.tecdoc.fwktd.dir3.core.type;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Clase abstracta para los enumerados de las constantes de los nombres de los
 * criterios de las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioEnum extends StringValuedEnum {

	private static final long serialVersionUID = 3521411830835486969L;

	/**
	 * Nombre de la tabla.
	 */
	private String table = null;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            Nombre de la tabla.
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected CriterioEnum(String table, String name, String value) {
		super(name, value);
		setTable(table);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
