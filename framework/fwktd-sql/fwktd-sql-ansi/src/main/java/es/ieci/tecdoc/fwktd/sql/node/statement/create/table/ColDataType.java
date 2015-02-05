/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;


/**
 * Tipo de dato para una columna.
 */
public abstract class ColDataType extends SqlNode {

	public void accept(StatementVisitor statementVisitor) {
		statementVisitor.visit(this);
	}

	public void setArgumentsStringList(List<String> list) {
		argumentsStringList = list;
	}

	public List<String> getArgumentsStringList() {
		return argumentsStringList;
	}
	
	// Members
	
	private static final long serialVersionUID = -7803461795687136802L;
	
	/** Argumentos para la creación de la columna */
	private List<String> argumentsStringList;

	
}