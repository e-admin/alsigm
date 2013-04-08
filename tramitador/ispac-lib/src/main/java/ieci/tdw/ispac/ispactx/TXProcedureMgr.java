package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager para la gestión de procedimientos.
 */
public class TXProcedureMgr
{

	private static TXProcedureMgr _instance=null;

	private Map mProcedureMap;

	private TXProcedureMgr()
	{
		super();
		mProcedureMap = Collections.synchronizedMap(new HashMap());
	}

	public static synchronized TXProcedureMgr getInstance()
	{
		if (_instance == null)
			_instance = new TXProcedureMgr();

		return _instance;
	}

	private TXProcedure getProcedure(int nProcedure)
	{
		return (TXProcedure)mProcedureMap.get(getProcedureMapKey(nProcedure));
	}

	private TXProcedure addProcedure(DbCnt cnt,int nProcedure)
	throws ISPACException
	{
		TXProcedure procedure = new TXProcedure(cnt,nProcedure);
//		mProcedureMap.put(getProcedureMapKey(nProcedure), procedure);
		
		return procedure;
	}


	public synchronized TXProcedure getProcedure(ClientContext cs, int nProcedure) throws ISPACException {
		TXProcedure procedure = getProcedure(nProcedure);
        DbCnt cnt=null;
        try {
			cnt = cs.getConnection();
			if (procedure == null) {
				procedure = addProcedure(cnt, nProcedure);
			} else {
				procedure.reload(cnt);
			}
		} finally {
			cs.releaseConnection(cnt);
		}
		return procedure;
	}

	public synchronized void invalidate(int nProcedure)
	throws ISPACException
	{
		mProcedureMap.remove(getProcedureMapKey(nProcedure));
	}

	public synchronized void invalidateAll()
	throws ISPACException
	{
	    mProcedureMap.clear();
	}

	protected Object getProcedureMapKey(int nProcedure) {
		Object key = null;
		
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			key = info.getUserId() + "_" + nProcedure;
		} else {
			key = new Integer(nProcedure);
		}

		return key;
	}
}
