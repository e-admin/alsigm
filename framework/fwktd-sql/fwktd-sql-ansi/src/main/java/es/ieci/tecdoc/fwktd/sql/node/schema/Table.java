/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.schema;

import es.ieci.tecdoc.fwktd.sql.node.statement.select.TableReference;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Una tabla. Puede tener un alias y el nombre del esquema al que pertenece.
 */
public class Table extends TableReference {

	/**
	 * Constructor por defecto.
	 */
	public Table() {
	}

	/**
	 * 
	 * @param aName
	 */
	public Table(String aName) {
		setName(aName);
	}

	/**
	 * 
	 * @param aSchemaName
	 * @param aName
	 */
	public Table(String aSchemaName, String aName) {
		setSchemaName(aSchemaName);
		setName(aName);
	}

	/**
	 * 
	 * @param aSchemaName
	 * @param aName
	 * @param anAlias
	 */
	public Table(String aSchemaName, String aName, String anAlias) {
		this(aSchemaName, aName);
		this.setAlias(anAlias);
	}

	public String getName() {
		return name;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setName(String string) {
		name = string;
	}

	public void setSchemaName(String string) {
		schemaName = string;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public void setWholeTableName(String wholeTableName) {
		this.wholeTableName = wholeTableName;
	}

	public String getWholeTableName() {
		return wholeTableName;
	}

	/**
	 * 
	 */
	public void accept(TableReferenceVisitor tableReferenceVisitor) {
		tableReferenceVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = 673767895417249564L;

	/** Nombre del schema */
	private String schemaName;

	/** Nombre de la tabla */
	private String name;

	/** Nombre físico de la tabla, incluyendo el schema */
	private String wholeTableName;

	/**
	 * Nombre de acceso a la tabla. Alias en caso de que lo tenga, nombre físico
	 * en otro caso
	 */
	private String referenceName;

}
