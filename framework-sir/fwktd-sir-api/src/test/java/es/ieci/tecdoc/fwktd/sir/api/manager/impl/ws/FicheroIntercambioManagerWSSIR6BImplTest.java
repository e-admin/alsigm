package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.URL;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.WS_SIR6_BService;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir6b.WS_SIR6_B_PortType;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class FicheroIntercambioManagerWSSIR6BImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private FicheroIntercambioManagerWSSIR6BImpl fwktd_sir_ficheroIntercambioManagerWSSIR6BImpl;

	public FicheroIntercambioManagerWSSIR6BImpl getFicheroIntercambioManager() throws Exception {

		WS_SIR6_B_PortType service = EasyMock.createMock(WS_SIR6_B_PortType.class);

		RespuestaWS respuesta = new RespuestaWS();
		respuesta.setCodigo(ErroresEnum.OK.getValue());
		respuesta.setDescripcion(ErroresEnum.OK.getName());

		EasyMock.expect(service.recepcionFicheroDeAplicacion((String) EasyMock.anyObject()))
				.andReturn(respuesta);

		EasyMock.replay(service);

		WS_SIR6_BService serviceLocator = EasyMock.createMock(WS_SIR6_BService.class);
		EasyMock.expect(serviceLocator.getWS_SIR6_B((URL)EasyMock.anyObject())).andReturn(service);
		
		EasyMock.replay(serviceLocator);

		fwktd_sir_ficheroIntercambioManagerWSSIR6BImpl.setServiceLocator(serviceLocator);

		return fwktd_sir_ficheroIntercambioManagerWSSIR6BImpl;
	}

	@Test
	public void testManager() throws Exception {
		Assert.assertNotNull(getFicheroIntercambioManager());
	}

	@Test
	public void testEnviarFicheroIntercambio() throws Exception {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);

		getFicheroIntercambioManager().enviarFicheroIntercambio(asiento);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
	}

}
