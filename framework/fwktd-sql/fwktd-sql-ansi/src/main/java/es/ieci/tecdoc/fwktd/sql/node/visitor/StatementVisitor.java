/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.visitor;

import es.ieci.tecdoc.fwktd.sql.node.statement.alter.table.AlterTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CharacterDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColumnDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CreateTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.DateDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ReferentialConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.TableConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.delete.Delete;
import es.ieci.tecdoc.fwktd.sql.node.statement.drop.Drop;
import es.ieci.tecdoc.fwktd.sql.node.statement.initialize.random.InitializeRandom;
import es.ieci.tecdoc.fwktd.sql.node.statement.insert.Insert;
import es.ieci.tecdoc.fwktd.sql.node.statement.rename.Rename;
import es.ieci.tecdoc.fwktd.sql.node.statement.replace.Replace;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Select;
import es.ieci.tecdoc.fwktd.sql.node.statement.truncate.Truncate;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.Update;

/**
 * Visitor para instancias del interfaz <code>Statement</code>
 * 
 * @author IECISA
 */
public interface StatementVisitor {

	// Sentencias

	public void visit(Select aSelect);

	public void visit(Delete aDelete);

	public void visit(Update anUpdate);

	public void visit(Insert anInsert);

	public void visit(Replace aReplace);

	public void visit(Drop aDrop);

	public void visit(Truncate aTruncate);

	public void visit(CreateTable aCreateTable);

	public void visit(AlterTable anAlterTable);

	public void visit(Rename aRename);

	public void visit(InitializeRandom anInitializeRandom);

	// Partes de sentencias

	public void visit(ColumnDefinition aColumnDefinition);

	public void visit(TableConstraintDefinition aTableConstraintDefinition);

	public void visit(UniqueConstraintDefinition aUniqueConstraintDefinition);

	public void visit(
			ReferentialConstraintDefinition aReferentialConstraintDefinition);

	public void visit(ColDataType aColDataType);

	public void visit(NumericDataType aNumericDataType);

	public void visit(CharacterDataType aCharacterDataType);

	public void visit(DateDataType aDateDataType);

	public void visit(SetClause aSetClause);
}
