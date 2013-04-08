/*
 * Created on 19-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.session.LockDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.lock.LockManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * TXLockManager
 * @version   $Revision: 1.4 $ $Date: 2008/05/28 08:03:29 $
 */
public class TXLockManager
{
	private final Map mprocessMap;
	private final Map mstageMap;
	private final Map mtaskMap;
	private final ClientContext mcct;
	
	public TXLockManager(ClientContext cct)
	{
		mprocessMap	= new HashMap();
		mstageMap = new HashMap();
		mtaskMap = new HashMap();
		mcct=cct;
	}
	
	public void lockProcess(int idProc) throws ISPACLockedObject, ISPACException
	{
		Integer nIdObj=new Integer(idProc);
		if (mprocessMap.get(nIdObj)!=null)
			return;
		
		LockManager lockmgr=new LockManager(mcct);
		LockDAO lock=lockmgr.lockObj(LockManager.LOCKTYPE_PROCESS,idProc, StringUtils.EMPTY);
		if (lock != null) {
			mprocessMap.put(nIdObj,lock);
		}	
	}
	
	public void lockStage(int idStage) throws ISPACLockedObject, ISPACException
	{
		Integer nIdObj=new Integer(idStage);
		if (mstageMap.get(nIdObj)!=null)
			return;
		
		LockManager lockmgr=new LockManager(mcct);
		LockDAO lock=lockmgr.lockObj(LockManager.LOCKTYPE_STAGE,idStage, StringUtils.EMPTY);
		if (lock != null) {
			mstageMap.put(nIdObj,lock);
		}
	}
	
	public void lockTask(int idTask) throws ISPACLockedObject, ISPACException
	{
		Integer nIdObj=new Integer(idTask);
		if (mtaskMap.get(nIdObj)!=null)
			return;
		
		LockManager lockmgr=new LockManager(mcct);
		LockDAO lock=lockmgr.lockObj(LockManager.LOCKTYPE_TASK,idTask, StringUtils.EMPTY);
		if (lock != null) {
			mtaskMap.put(nIdObj,lock);
		}
	}
	
	public void unlock(DbCnt cnt,Collection values) throws ISPACException
	{
        Iterator it=values.iterator();
		while (it.hasNext())
		{
			LockDAO obj=(LockDAO)it.next();
			obj.delete(cnt);
		}
	}
	
	public void unlockAll() throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt=new DbCnt();
			cnt.getConnection();
			
			unlock(cnt,mtaskMap.values());
			unlock(cnt,mstageMap.values());
			unlock(cnt,mprocessMap.values());
		}
		catch (ISPACException e)
		{
			throw e;
		}
		finally
		{
			cnt.closeConnection();
		}
	}
}
