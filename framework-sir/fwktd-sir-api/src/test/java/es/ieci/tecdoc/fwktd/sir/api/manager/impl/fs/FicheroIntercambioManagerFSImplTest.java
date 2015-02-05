package es.ieci.tecdoc.fwktd.sir.api.manager.impl.fs;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;


@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class FicheroIntercambioManagerFSImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private FicheroIntercambioManagerFSImpl fwktd_sir_ficheroIntercambioManagerFS;

	public FicheroIntercambioManagerFSImpl getFicheroIntercambioManager() {
		return fwktd_sir_ficheroIntercambioManagerFS;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getFicheroIntercambioManager());
	}

	@Test
	public void testEnviarFicheroIntercambioAttached() {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);

		getFicheroIntercambioManager().enviarFicheroIntercambio(asiento);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testEnviarFicheroIntercambioDetached() {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);

		getFicheroIntercambioManager().setAttached(false);
		getFicheroIntercambioManager().enviarFicheroIntercambio(asiento);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
	}

}
