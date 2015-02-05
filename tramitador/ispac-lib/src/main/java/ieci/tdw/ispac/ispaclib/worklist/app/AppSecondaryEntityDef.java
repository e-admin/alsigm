package ieci.tdw.ispac.ispaclib.worklist.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.worklist.WLTableDef;
import ieci.tdw.ispac.ispaclib.worklist.WLTableJoinDef;

public class AppSecondaryEntityDef extends WLTableJoinDef {
    
	private String join = null;
	
    public AppSecondaryEntityDef() {
		super();
	}
    
    public AppSecondaryEntityDef(String name, String property, WLTableDef table, String join) {
    	super(name, property, table);
    	setJoin(join);
    }

	public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public String getJoinBind(DbCnt cnt, String worklistbind)
			throws ISPACException {
		return getJoin();
	}

}
