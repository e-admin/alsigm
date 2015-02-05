/*
 * Created on 26-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx.tx;

import java.util.Date;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXAnotacionDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXRemark implements ITXAction
{
	private final int mnIdProc;
	private final int mnIdPCDStage;
	private final int mnIdPCDTask;
	private final String mText;

	/**
	 * 
	 */
	public TXRemark(int nIdProc, int nIdPCDStage, int nIdPCDTask, String Text)
	{
		super();
		mnIdProc=nIdProc;
		mnIdPCDStage=nIdPCDStage;
		mnIdPCDTask=nIdPCDTask;
		mText=Text;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException
	{
		TXAnotacionDAO remark=dtc.newRemark();
		remark.set("ID_EXP",mnIdProc);
		remark.set("ID_FASE",mnIdPCDStage);
		remark.set("ID_TRAMITE",mnIdPCDTask);
		remark.set("FECHA",new Date());
		remark.set("AUTOR",cs.getRespId());
		remark.set("TEXTO",mText);
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
