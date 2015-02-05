package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class AuditoriaMensajeManagerImplTest extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	private AuditoriaMensajeManagerImpl fwktd_sir_auditoriaMensajeManagerImpl;

	public AuditoriaMensajeManagerImpl getAuditoriaMensajeManager() {

		// Indicar que se elimine el documento
		fwktd_sir_auditoriaMensajeManagerImpl.setTest(true);

		return fwktd_sir_auditoriaMensajeManagerImpl;
	}

	@Test
	public void testAuditaSinXML() {
		MensajeVO mensaje = TestUtils.createDefaultMensajeVO();
		getAuditoriaMensajeManager().audita(mensaje, BandejaEnum.ENVIADOS);
	}

	@Test
	public void testAuditaConXML() {
		MensajeVO mensaje = TestUtils.createDefaultMensajeVO();
		getAuditoriaMensajeManager().audita(mensaje, "<xml/>",
				BandejaEnum.ENVIADOS);
	}

}
