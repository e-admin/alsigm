/*
 * Created on 17-jun-2005
 *
 */
package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbInputRecord;
import ieci.core.db.DbInputStatement;

/**
 * @author ABELRL
 * 
 */
public class SigiaDbInputRecord implements DbInputRecord {
	DbColumnDef[] colsDefs;

	Object objectVO;

	public SigiaDbInputRecord(DbColumnDef[] colsDefs, Object objectVO) {
		this.colsDefs = colsDefs;
		this.objectVO = objectVO;
	}

	public void setStatementValues(DbInputStatement stmt) throws Exception {
		DBUtils.fillDbInputStmt(colsDefs, stmt, objectVO);
	}
}