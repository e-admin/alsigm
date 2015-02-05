package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({ "/beans/ispac-lib-test-beans.xml" })
public class InboxAPITest extends AbstractJUnit4SpringContextTests {
	
	private static final int PROCEDURE_ID = 3;
	private static final String INTRAY_ID = "1";
	private static final String EXP_NUM = "exp/0001";
	
	protected ClientContext getClientContext() throws ISPACException {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		ctx.setLocale(Locale.getDefault());
		
		return ctx;
	}

	@Test
	public void testClientContext() throws ISPACException {
		Assert.assertNotNull(getClientContext());
	}

	@Test
	public void testCreateProcess() throws Exception {
		
		ClientContext ctx = getClientContext();
		
		int processId = 0;
		
		try {
			
			ctx.beginTX();
			
			IInboxAPI inboxAPI = ctx.getAPI().getInboxAPI();
			processId = inboxAPI.createProcess(INTRAY_ID, PROCEDURE_ID);
			Assert.assertTrue(processId > 0);
			
		} finally {
			ctx.endTX(false);
		}
		
		IProcess process = ctx.getAPI().getProcess(processId);
		Assert.assertNull(process);
		
	}

	public void testAnnexToProcess() throws Exception {
		
		IInboxAPI inboxAPI = getClientContext().getAPI().getInboxAPI();
		inboxAPI.annexToProcess(INTRAY_ID, EXP_NUM);
	}
}
