package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class TXTransactionTest extends TestCase {
	
	private static final Logger logger = Logger.getLogger("TEST");
	
	
	public static int idProceso=0;
	public static String numExp="EXP2011/000067";
	public static int idProcedimiento=0;

	protected  ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		return ctx;
	}
	
	protected InvesflowAPI getInvesFlowAPI() {
		return new InvesflowAPI(getClientContext());
	}

	protected IItem getProcesoByNumExp(String numExp) throws ISPACException{
		ClientContext ctx=getClientContext();
		IItemCollection procesos = TXProcesoDAO.getProcess(
				ctx.getConnection(), numExp, ctx);
		if(procesos.next())return procesos.value();
		return null;
		
	}
	public void testSendProcessToTrash(){
		try {
			IItem proceso=getProcesoByNumExp(numExp);
			getInvesFlowAPI().getTransactionAPI().sendProcessToTrash(
					proceso.getKeyInt());
			//Para verificar que ha ido bien en spac_procesos el estado ha de ser eliminado/enviado a la papelera
			proceso=getProcesoByNumExp(numExp);
			int estado = proceso.getInt("ESTADO");
			assertSame(TXConstants.STATUS_DELETED, estado);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
		
	}
	
	public void testRestoreProcess(){
		ClientContext ctx =getClientContext();
		try {
			IItemCollection procesos = TXProcesoDAO.getProcess(
					ctx.getConnection(), numExp, ctx);
			IItem proceso = procesos.value();
			getInvesFlowAPI().getTransactionAPI().restoreProcess(
					proceso.getKeyInt());
			//Para verificar que ha ido bien en spac_procesos el estado no ha de ser eliminado/enviado a la papelera
			proceso=getProcesoByNumExp(numExp);
			int estado = proceso.getInt("ESTADO");
			assertNotSame(TXConstants.STATUS_DELETED, estado);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
		
	}
	
	public void testDeleteProcess(){
		

		try {
			IItem proceso=getProcesoByNumExp(numExp);
			ITXTransaction txtransactionAPI = getInvesFlowAPI().getTransactionAPI();
			txtransactionAPI.sendProcessToTrash(
					proceso.getKeyInt());
			txtransactionAPI.deleteProcess(proceso.getKeyInt());
			//Para verificar que ha ido bien en spac_procesos el estado anterior ha de ser eliminado/enviado a la papelera
			proceso=getProcesoByNumExp(numExp);
			
			assertSame(proceso, null);
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	


}
