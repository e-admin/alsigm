/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CharacterDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.initialize.random.InitializeRandom;
import es.ieci.tecdoc.fwktd.sql.node.statement.rename.Rename;
import es.ieci.tecdoc.fwktd.sql.node.statement.truncate.Truncate;
import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;


/**
 * <code>StatementRenderer</code> para Oracle
 * 
 */
public class OracleStatementRenderer extends StatementRenderer {

	public OracleStatementRenderer() {
	}

	@Override
	protected String decorate(String name) {
		return SqlUtils.doubleQuotes(name);
	}

	public void visit(NumericDataType numericDataType) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("NUMBER");
		if (numericDataType.getPrecision() != null) {

			buffer.append("(").append(numericDataType.getPrecision());
			if (numericDataType.getScale() != null) {
				buffer.append(", ").append(numericDataType.getScale());
			}
			buffer.append(")");
		}

		numericDataType.setSqlString(buffer.toString());

		this.visit((ColDataType) numericDataType);
	}

	public void visit(CharacterDataType characterDataType) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("VARCHAR2");
		if (characterDataType.getSize() != null) {
			buffer.append("(").append(characterDataType.getSize()).append(")");
		}

		characterDataType.setSqlString(buffer.toString());
		this.visit((ColDataType) characterDataType);
	}

	public void visit(Truncate truncate) {

		StringBuffer buffer = new StringBuffer();

		truncate.getTable().accept(tableReferenceRenderer);
		buffer.append("TRUNCATE TABLE "
				+ truncate.getTable().getWholeTableName());

		truncate.setSqlString(buffer.toString());
	}

	public void visit(Rename rename) {

		rename.getTable().accept(tableReferenceRenderer);

		StringBuffer buffer = new StringBuffer();
		// La sentencia RENAME de Oracle no permite especificar el schema
		buffer.append("RENAME ").append(decorate(rename.getTable().getName()))
				.append(" TO ").append(decorate(rename.getNewName()));

		rename.setSqlString(buffer.toString());
	}

	public void visit(InitializeRandom initializeRandom) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("BEGIN\n").append("dbms_random.initialize(").append(
				initializeRandom.getSeed()).append(");\n").append("END;");
		initializeRandom.setSqlString(buffer.toString());
	}
}
