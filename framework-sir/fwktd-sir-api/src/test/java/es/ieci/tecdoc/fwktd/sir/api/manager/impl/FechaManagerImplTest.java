package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class FechaManagerImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private FechaManager fwktd_sir_fechaManager;

	@Test
	public void testManager() {
		Assert.assertNotNull(fwktd_sir_fechaManager);
	}

	@Test
	public void testGetFechaActual() {
		Assert.assertNotNull(fwktd_sir_fechaManager.getFechaActual());
	}

}
