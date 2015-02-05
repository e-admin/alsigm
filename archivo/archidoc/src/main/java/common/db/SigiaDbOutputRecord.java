/*
 * Created on 15-jun-2005
 *
 */
package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;

import common.util.ArrayUtils;

/**
 * @author ABELRL
 * 
 */
public class SigiaDbOutputRecord implements DbOutputRecord {
	private Object objectVO;
	private DbColumnDef[] defs;
	private DbColumnDef[] defsFill;

	public SigiaDbOutputRecord(Object objectVO, DbColumnDef[] defs) {
		this.objectVO = objectVO;
		this.defs = defs;
	}

	public SigiaDbOutputRecord(Object objectVO, DbColumnDef[] defs,
			DbColumnDef[] defsFill) {
		this.objectVO = objectVO;
		this.defs = defs;
		this.defsFill = defsFill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ieci.core.db.DbOutputRecord#getStatementValues(ieci.core.db.DbOutputStatement
	 * )
	 */
	public void getStatementValues(DbOutputStatement stmt) throws Exception {
		if (ArrayUtils.isNotEmpty(defsFill)) {
			DBUtils.fillVoWithDbOutputStament(defsFill, stmt, objectVO);
		} else {
			DBUtils.fillVoWithDbOutputStament(defs, stmt, objectVO);
		}
	}
}
