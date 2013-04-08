package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Iterator;
import java.util.Map;

public class TXResumeProcess implements ITXAction {
	
	private final int mnIdProc;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	/**
	 * Constructor
	 */
	public TXResumeProcess(int nIdProc) {
		this(nIdProc, null);
	}

	/**
	 * Constructor
	 */
	public TXResumeProcess(int nIdProc, Map params) {
		super();
		mnIdProc=nIdProc;
		mparams=params;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx) throws ISPACException
	{
		EventManager eventmgr=new EventManager(cs, mparams);
		
		TXProcesoDAO process=dtc.getProcess(mnIdProc);
		if (process.getInt("ESTADO")!=TXConstants.STATUS_CANCELED) {
			throw new ISPACInfo("exception.expedients.resumeProcess.statusNotCanceled",
					new String[] { process.getString("NUMEXP") });
		}
		
		process.set("ESTADO",TXConstants.STATUS_OPEN);
		
		Iterator it=dtc.getProcessStages(mnIdProc).iterator();
		while(it.hasNext())
		{
			ObjectDAO obj=(ObjectDAO)it.next();
			obj.set("ESTADO",TXConstants.STATUS_OPEN);
		}
	
		it=dtc.getProcessTasks(mnIdProc).iterator();
		while(it.hasNext())
		{
			ObjectDAO obj=(ObjectDAO)it.next();
			obj.set("ESTADO",TXConstants.STATUS_OPEN);
		}
		
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		
		int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
		if (process.isSubProcess())
			eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
		//Ejecutar evento al cancelar fase.
		eventmgr.executeRules(eventObjectType, process.getInt("ID_PCD"), EventsDefines.EVENT_EXEC_REACTIVATE);
			
		int milestoneType = TXConstants.MILESTONE_EXPED_RESUMED;
		if (process.isSubProcess())
			milestoneType = TXConstants.MILESTONE_SUBPROCESS_RESUMED;
		dtc.newMilestone(process.getKeyInt(),0,0, milestoneType);		
	}

	public Object getResult(String nameResult)
	{
		return null;
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		dtc.getLockManager().lockProcess(mnIdProc);
	}
}
