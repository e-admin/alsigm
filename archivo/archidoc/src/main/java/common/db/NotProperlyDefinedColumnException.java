package common.db;

import ieci.core.db.DbColumnDef;

/**
 * Indica que se ha producido un error debido a una definicion de columna
 * incorrecta
 * 
 */
public class NotProperlyDefinedColumnException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Columna mal definida
	DbColumnDef column = null;

	protected NotProperlyDefinedColumnException(DbColumnDef column) {
		super("Columna mal definida: " + column.getName());
		this.column = column;
	}
}