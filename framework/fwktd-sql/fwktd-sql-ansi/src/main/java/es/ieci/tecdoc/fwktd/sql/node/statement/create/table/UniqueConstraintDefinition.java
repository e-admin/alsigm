/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Definición de una constante de tipo unique: UNIQUE o PRIMARY KEY.
 */
public class UniqueConstraintDefinition extends TableConstraintDefinition {

	public enum UniqueConstraintType {
		unique, primaryKey;
	};

	public UniqueConstraintDefinition(String aName,
			UniqueConstraintType aUniqueConstraintType,
			List<String> aColumnsNames) {

		this(aUniqueConstraintType, aColumnsNames);
		setName(aName);
	}

	public UniqueConstraintDefinition(
			UniqueConstraintType aUniqueConstraintType,
			List<String> aColumnsNames) {

		super();
		setType(aUniqueConstraintType);
		setColumnsNames(aColumnsNames);
	}

	public UniqueConstraintDefinition(String aName,
			UniqueConstraintType aUniqueConstraintType, String aColumnName) {

		this(aUniqueConstraintType, aColumnName);
		setName(aName);
	}

	public UniqueConstraintDefinition(
			UniqueConstraintType aUniqueConstraintType, String aColumnName) {

		this(aUniqueConstraintType, new ArrayList<String>());
		getColumnsNames().add(aColumnName);
	}

	@Override
	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public UniqueConstraintType getType() {
		return type;
	}

	public void setType(UniqueConstraintType aType) {
		this.type = aType;
	}

	// Members

	private static final long serialVersionUID = -1460856798446479663L;

	private UniqueConstraintType type;
}
