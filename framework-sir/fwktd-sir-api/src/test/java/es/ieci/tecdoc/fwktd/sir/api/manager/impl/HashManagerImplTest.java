package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.api.manager.HashManager;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class HashManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	private HashManager fwktd_sir_hashManagerImpl;
	
	@Test
	public void testGenerarHashBean() {
		byte[] contenido = fwktd_sir_hashManagerImpl.generarHash("contenido".getBytes());
		Assert.assertNotNull("Contenido nulo", contenido);
		Assert.assertEquals("KA+fgWIUmvhDdsq6Qx3odcFImHQ=", Base64.encodeBase64String(contenido));
	}

	@Test
	public void testGenerarHash() {
		byte[] contenido = new HashManagerImpl().generarHash("contenido".getBytes());
		Assert.assertNotNull("Contenido nulo", contenido);
		Assert.assertEquals("KA+fgWIUmvhDdsq6Qx3odcFImHQ=", Base64.encodeBase64String(contenido));
	}

	@Test
	public void testGenerarHashSHA1() {
		byte[] contenido = new HashManagerImpl("SHA-1").generarHash("contenido".getBytes());
		Assert.assertNotNull("Contenido nulo", contenido);
		Assert.assertEquals("KA+fgWIUmvhDdsq6Qx3odcFImHQ=", Base64.encodeBase64String(contenido));
	}

	@Test
	public void testGenerarHashMD5() {
		byte[] contenido = new HashManagerImpl("MD5").generarHash("contenido".getBytes());
		Assert.assertNotNull("Contenido nulo", contenido);
		Assert.assertEquals("V7tLSF9SYoqrk8vedtMi0A==", Base64.encodeBase64String(contenido));
	}

}
