package common.db;

import ieci.core.db.DbColumnDef;

public class JoinDefinition {
	DbColumnDef leftCol;
	DbColumnDef rightCol;
	String sqlAppend;

	public JoinDefinition(DbColumnDef leftCol, DbColumnDef rightCol) {
		this.leftCol = leftCol;
		this.rightCol = rightCol;
	}

	public JoinDefinition(DbColumnDef leftCol, DbColumnDef rightCol,
			String sqlAppend) {
		this.leftCol = leftCol;
		this.rightCol = rightCol;
		this.sqlAppend = sqlAppend;
	}

	public DbColumnDef getLeftCol() {
		return leftCol;
	}

	public void setLeftCol(DbColumnDef leftCol) {
		this.leftCol = leftCol;
	}

	public DbColumnDef getRightCol() {
		return rightCol;
	}

	public void setRightCol(DbColumnDef rightCol) {
		this.rightCol = rightCol;
	}

	public String getSqlAppend() {
		return sqlAppend;
	}

	public void setSqlAppend(String sqlAppend) {
		this.sqlAppend = sqlAppend;
	}

}
