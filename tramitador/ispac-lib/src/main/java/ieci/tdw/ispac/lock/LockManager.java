/*
 * Created on 16-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.lock;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.session.LockDAO;
import ieci.tdw.ispac.ispaclib.dao.session.SessionDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

/**
 * LockManager
 * @version   $Revision: 1.7 $ $Date: 2008/07/04 09:15:14 $
 */
public class LockManager
{
	// Constantes para identificadores de bloqueos
	public final static int LOCKTYPE_PROCEDURE = 1;
	public final static int LOCKTYPE_CTOBJ = 2;
	public final static int LOCKTYPE_PROCESS = 3;
	public final static int LOCKTYPE_STAGE = 4;
	public final static int LOCKTYPE_TASK = 5;
	public final static int LOCKTYPE_ENTITY = 6;
	public final static int LOCKTYPE_APPLICATION = 7;
	public final static int LOCKTYPE_DOCUMENT = 8;
	public final static int LOCKTYPE_TEMPLATE = 9;

	public final static int LOCKID_CTOBJ_STAGES = 1;
	public final static int LOCKID_CTOBJ_ACTS = 2;
	public final static int LOCKID_CTOBJ_DOCTYPES = 3;
	public final static int LOCKID_CTOBJ_SHEETS = 4;
	public final static int LOCKID_CTOBJ_ENTITIES = 5;
	public final static int LOCKID_CTOBJ_RULES = 6;
	public final static int LOCKID_CTOBJ_PROCESSCREATION = 7;

	public final static int NOT_LOCKED = -1;
	public final static int LOCKED_CURSESSION = 0;
	public final static int LOCKED = 1;

	IClientContext mcct;
	public LockManager(IClientContext cct)
	{
		mcct = cct;
	}

	/**
	 * Bloquea un objeto.
	 *
	 * @param tpObj Tipo del objeto. Depende de la aplicación el sentido de
	 *            este parámetro
	 * @param idObj Identificador del objeto.
	 */

	public LockDAO lockObj(int tpObj, int idObj, String numexp)
	throws ISPACLockedObject, ISPACException
	{
		try {
			// Comprobar si el objeto ya está bloqueado por la misma sesión
			if (isLock(tpObj, idObj) != LOCKED_CURSESSION){
				
				DbCnt cnt = null;
				
				try
				{
					cnt = new DbCnt();
					cnt.getConnection();
		
					LockDAO lock = new LockDAO(cnt);
					lock.createNew(cnt);
					lock.set("ID", mcct.getTicket());
					lock.set("ID_OBJ", idObj);
					lock.set("TP_OBJ", tpObj);
					lock.set("NUMEXP", StringUtils.isNotBlank(numexp) ? numexp : "-");
					lock.set("FECHA", new java.util.Date(System.currentTimeMillis()));
					lock.store(cnt);
					return lock;
				}
				catch (ISPACException e)
				{
					throw new ISPACLockedObject(e);
				}
				finally
				{
					if (cnt != null) {
						cnt.closeConnection();
					}
				}
			}
		}
		catch (ISPACException e)
		{
			// Excepción en isLock
			throw e;
		}
		
		return null;
	}

	/**
	 * Desbloquea un objeto.
	 *
	 * @param tpObj Tipo del objeto. Depende de la aplicación el sentido de
	 *            este parámetro
	 * @param idObj Identificador del objeto.
	 */
	public void unlockObj(int tpObj, int idObj)
	throws ISPACException
	{
		DbCnt cnt = null;
		
		try
		{
			cnt = new DbCnt();
			cnt.getConnection();

			SessionDAO sessionDAO = new SessionDAO(cnt, mcct.getTicket());
			LockDAO lock = sessionDAO.getBloqueo(cnt, idObj, tpObj);
			lock.delete(cnt);
		}
		catch (ISPACException e)
		{
			throw e;
		}
		finally
		{
			if (cnt != null) {
				cnt.closeConnection();
			}
		}
	}
	
	public void unlockObj(int tpObj, int idObj, String numexp)
	throws ISPACException
	{
		DbCnt cnt = null;
		
		try
		{
			cnt = new DbCnt();
			cnt.getConnection();
		
			SessionDAO sessionDAO = new SessionDAO(cnt, mcct.getTicket());
			LockDAO lock = sessionDAO.getBloqueo(cnt, idObj, tpObj, numexp);
			lock.delete(cnt);
		}
		catch (ISPACException e)
		{
			throw e;
		}
		finally
		{
			if (cnt != null) {
				cnt.closeConnection();
			}
		}
	}
	
	/**
	 * Comprueba si está bloqueado el objeto indicado.
	 * @param tpObj Tipo del objeto. Depende de la aplicación el sentido de
	 *            este parámetro
	 * @param idObj Identificador del objeto.
	 * @return -1 si el objeto no está bloqueado,
	 * 			0 si está bloqueado por el usuario de la sesion,
	 * 			1 si está bloqueado por otro usuario
	 * @throws ISPACException
	 */
	public int isLock(int tpObj, int idObj)
	throws ISPACException
	{
		int result = NOT_LOCKED;
		
		DbCnt cnt = null;
		String ticket = mcct.getTicket();
		
		try
		{
			cnt = new DbCnt();
			cnt.getConnection();

			String sql = " WHERE TP_OBJ = " + tpObj +
						 " AND ID_OBJ = "  + idObj;
			LockDAO bloqueo = new LockDAO(cnt);
			bloqueo.load(cnt, sql);
			if (ticket.equals(bloqueo.getString("ID")))
				result = LOCKED_CURSESSION;
			else
				result = LOCKED;

		} catch (ISPACNullObject e)
		{
			return result;
		}
		catch (ISPACException e)
		{
			throw e;
		}
		finally
		{
			if (cnt != null) {
				cnt.closeConnection();
			}
		}
		return result;
	}
	
	public int isLock(int tpObj, int idObj, String numexp)
	throws ISPACException
	{
		int result = NOT_LOCKED;
		
		DbCnt cnt = null;
		String ticket = mcct.getTicket();
		
		try
		{
			cnt = new DbCnt();
			cnt.getConnection();

			String sql = " WHERE TP_OBJ = " + tpObj +
						 " AND ID_OBJ = "  + idObj +
						 " AND NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
			LockDAO bloqueo = new LockDAO(cnt);
			bloqueo.load(cnt, sql);
			if (ticket.equals(bloqueo.getString("ID")))
				result = LOCKED_CURSESSION;
			else
				result = LOCKED;

		} catch (ISPACNullObject e)
		{
			return result;
		}
		catch (ISPACException e)
		{
			throw e;
		}
		finally
		{
			if (cnt != null) {
				cnt.closeConnection();
			}
		}
		return result;
	}

	/*
	/ Bloqueos / desbloqueos de elementos del catálogo
	*/
	
	public void lockProcedure(int processId)
			throws ISPACException
	{
		lockObj(LOCKTYPE_PROCEDURE, processId, StringUtils.EMPTY);
	}

	public void unlockProcedure(int processId)
			throws ISPACException
	{
		unlockObj(LOCKTYPE_PROCEDURE, processId);
	}

	public void lockStagesCT() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_STAGES, StringUtils.EMPTY);
	}

	public void unlockStagesCT() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_STAGES);
	}

	public void lockActCT() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_ACTS, StringUtils.EMPTY);
	}

	public void unlockActCT() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_ACTS);
	}

	public void lockDocTypesCT() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_DOCTYPES, StringUtils.EMPTY);
	}

	public void unlockDocTypesCT() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_DOCTYPES);
	}

	public void lockSheets() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_SHEETS, StringUtils.EMPTY);
	}

	public void unlockSheets() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_SHEETS);
	}

	public void lockEntities() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_ENTITIES, StringUtils.EMPTY);
	}

	public void unlockEntities() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_ENTITIES);
	}

	public void lockProcessCreation() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_PROCESSCREATION, StringUtils.EMPTY);
	}

	public void unlockProcessCreation() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_PROCESSCREATION);
	}

	public void lockRules() throws ISPACException
	{
		lockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_RULES, StringUtils.EMPTY);
	}

	public void unlockRules() throws ISPACException
	{
		unlockObj(LOCKTYPE_CTOBJ, LOCKID_CTOBJ_RULES);
	}

	public void lockStage(int stageId)
	throws ISPACException
	{
		lockObj(LOCKTYPE_STAGE, stageId, StringUtils.EMPTY);
	}

	public void unlockStage(int stageId)
	throws ISPACException
	{
		unlockObj(LOCKTYPE_STAGE, stageId);
	}
	
	public void unlockEntity(int entityId, String numexp)
	throws ISPACException
	{
		unlockObj(LOCKTYPE_ENTITY, entityId, numexp);
	}

	public void lockTask(int taskId)
	throws ISPACException
	{
		lockObj(LOCKTYPE_TASK, taskId, StringUtils.EMPTY);
	}

	public void unlockTask(int taskId)
	throws ISPACException
	{
		unlockObj(LOCKTYPE_TASK, taskId);
	}
	
	public void lockEntity(int entityId, String numexp)
	throws ISPACException
	{
		lockObj(LOCKTYPE_ENTITY, entityId, numexp);
	}

	public void lockDocument(int docId) throws ISPACException {
		lockObj(LOCKTYPE_DOCUMENT, docId, StringUtils.EMPTY);
	}

	public void unlockDocument(int docId) throws ISPACException {
		unlockObj(LOCKTYPE_DOCUMENT, docId);
	}

	public void lockTemplate(int templateId) throws ISPACException {
		lockObj(LOCKTYPE_TEMPLATE, templateId, StringUtils.EMPTY);
	}

	public void unlockTemplate(int templateId) throws ISPACException {
		unlockObj(LOCKTYPE_TEMPLATE, templateId);
	}

}
