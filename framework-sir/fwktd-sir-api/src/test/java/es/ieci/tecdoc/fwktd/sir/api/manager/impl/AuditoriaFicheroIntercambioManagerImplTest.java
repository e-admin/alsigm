package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class AuditoriaFicheroIntercambioManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private AuditoriaFicheroIntercambioManagerImpl fwktd_sir_auditoriaFicheroIntercambioManagerImpl;

	public AuditoriaFicheroIntercambioManagerImpl getAuditoriaFicheroIntercambioManager() {

		// Indicar que se elimine el documento
		fwktd_sir_auditoriaFicheroIntercambioManagerImpl.setTest(true);

		return fwktd_sir_auditoriaFicheroIntercambioManagerImpl;
	}

	@Test
	public void testAuditaAsientoRegistral() {

		AsientoRegistralFormVO asientoForm = TestUtils
				.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager
				.saveAsientoRegistral(asientoForm);

		getAuditoriaFicheroIntercambioManager().audita(asiento,
				BandejaEnum.ENVIADOS);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento
				.getId());
	}

	@Test
	public void testAuditaFicheroIntercambio() throws Exception {

		FicheroIntercambioVO ficheroIntercambio = TestUtils
				.createDefaultFicheroIntercambioVO();
		getAuditoriaFicheroIntercambioManager().audita(ficheroIntercambio,
				"<xml/>", BandejaEnum.ENVIADOS);
	}

}
