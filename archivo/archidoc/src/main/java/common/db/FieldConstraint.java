package common.db;

import ieci.core.db.DbColumnDef;

/**
 * Condición de búsqueda
 */
public class FieldConstraint {

	/** Definición de la columna a la que se refiere la columna */
	DbColumnDef column;

	/** Definición de la columna a la que se refiere la columna */
	DbColumnDef idxColumn;

	/** Operador a aplicar en la condición */
	int operator;
	/** Valor que debe ser verificado para que la condición se cumpla */
	String value;

	// public FieldConstraint(DbColumnDef column, int operator, String value) {
	// this(column, operator, value);
	// }

	public FieldConstraint(DbColumnDef column, int operator, String value) {
		this.column = column;
		this.operator = operator;
		this.value = value;
	}

	public FieldConstraint(DbColumnDef column, DbColumnDef idxColumn,
			int operator, String value) {
		this.column = column;
		this.idxColumn = idxColumn;
		this.operator = operator;
		this.value = value;
	}

	public DbColumnDef getColumn() {
		return column;
	}

	public DbColumnDef getIdxColumn() {
		return idxColumn;
	}

	public int getOperator() {
		return operator;
	}

	public String getValue() {
		return value;
	}
	// public boolean getIgnoreCase() {
	// return ignoreCase;
	// }
}
