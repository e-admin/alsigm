/*
 * Created on 26-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx.tx;


import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXNewMilestone implements ITXAction
{
	private final int mnIdProc;
	private final int mnIdPCDStage;
	private final int mnIdPCDTask;
	private final int mnIdInfo;
	private final String mInfo;
	private final String mDesc;


	/**
	 *
	 */
	public TXNewMilestone(int nIdProc, int nIdPCDStage, int nIdPCDTask,int nIdInfo, String info, String desc)
	{
		super();
		mnIdProc=nIdProc;
		mnIdPCDStage=nIdPCDStage;
		mnIdPCDTask=nIdPCDTask;
		mInfo=info;
		mnIdInfo=nIdInfo;
		mDesc = desc;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException
	{
		TXHitoDAO hito=dtc.newMilestone(mnIdProc,mnIdPCDStage,mnIdPCDTask,
				TXConstants.MILESTONE_INFORMATIVE, mDesc);

		hito.set("INFO",mInfo);
		hito.set("ID_INFO",mnIdInfo);
	}

	public Object getResult(String nameResult)
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.ITXAction#lock(ieci.tdw.ispac.ispaclib.context.ClientContext, ieci.tdw.ispac.ispactx.TXTransactionDataContainer)
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		dtc.getLockManager().lockProcess(mnIdProc);
	}
}
