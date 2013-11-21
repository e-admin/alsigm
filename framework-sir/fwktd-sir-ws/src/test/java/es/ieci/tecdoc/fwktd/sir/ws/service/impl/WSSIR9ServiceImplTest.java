package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-sir-api-test-initial-beans.xml",
		"/beans/fwktd-sir-ws-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-api-test-beans.xml"})
public class WSSIR9ServiceImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String IDENTIFICADOR_INTERCAMBIO = "ER0000000000000000001_99_10000001";

	@Autowired
	private WSSIR9ServiceImpl fwktd_sir_ws_wssir9ServiceImpl;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;


	protected WSSIR9ServiceImpl getWSSIR9Service() {
		return fwktd_sir_ws_wssir9ServiceImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getWSSIR9Service());
	}

	@Test
	public void testEnvioMensajeDatosControlAAplicacionACK() {

		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

		// Establecer el asiento registral como enviado
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
		asiento.setFechaEstado(new Date());
        asiento.setFechaEnvio(new Date());
		asiento.setIdentificadorIntercambio(IDENTIFICADOR_INTERCAMBIO);
        asiento.setNumeroReintentos(asiento.getNumeroReintentos() + 1);
        asiento = fwktd_sir_asientoRegistralManager.update(asiento);

        // Construir el XML del mensaje
		String mensaje = TestUtils.createXMLMensaje(IDENTIFICADOR_INTERCAMBIO, TipoMensajeEnum.ACK);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR9Service().envioMensajeDatosControlAAplicacion(mensaje, null);

		// Eliminar el asiento creado (con sus documentos)
		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioMensajeDatosControlAAplicacionConfirmacion() {



		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

		// Establecer el asiento registral como enviado
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO_Y_ACK);
		asiento.setFechaEstado(new Date());
        asiento.setFechaEnvio(new Date());
		asiento.setIdentificadorIntercambio(IDENTIFICADOR_INTERCAMBIO);
        asiento.setNumeroReintentos(asiento.getNumeroReintentos() + 1);
        asiento = fwktd_sir_asientoRegistralManager.update(asiento);

        // Construir el XML del mensaje
		String mensaje = TestUtils.createXMLMensaje(IDENTIFICADOR_INTERCAMBIO, TipoMensajeEnum.CONFIRMACION);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR9Service().envioMensajeDatosControlAAplicacion(mensaje, null);

		// Eliminar el asiento creado (con sus documentos)
		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioMensajeDatosControlAAplicacionError() {

		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

		// Establecer el asiento registral como enviado
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
		asiento.setFechaEstado(new Date());
        asiento.setFechaEnvio(new Date());
		asiento.setIdentificadorIntercambio(IDENTIFICADOR_INTERCAMBIO);
        asiento.setNumeroReintentos(asiento.getNumeroReintentos() + 1);
        asiento = fwktd_sir_asientoRegistralManager.update(asiento);

        // Construir el XML del mensaje
		String mensaje = TestUtils.createXMLMensaje(IDENTIFICADOR_INTERCAMBIO, TipoMensajeEnum.ERROR);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR9Service().envioMensajeDatosControlAAplicacion(mensaje, null);

		// Eliminar el asiento creado (con sus documentos)
		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}
}

