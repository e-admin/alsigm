package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.util.Date;

import junit.framework.Assert;

import org.apache.axis.attachments.OctetStream;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-sir-api-test-initial-beans.xml",
		"/beans/fwktd-sir-ws-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-api-test-beans.xml" })
public class WSSIR8AServiceImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String IDENTIFICADOR_INTERCAMBIO = "ER0000000000000000001_99_10000001";

	@Autowired
	private WSSIR8AServiceImpl fwktd_sir_ws_wssir8aServiceImpl;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;


	protected WSSIR8AServiceImpl getWSSIR8AService() {
		return fwktd_sir_ws_wssir8aServiceImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getWSSIR8AService());
	}

	@Test
	public void testEnvioFicherosAAplicacionEnvio() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, true);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR8AService().envioFicherosAAplicacion(ficheroIntercambio, null, new OctetStream[0]);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioFicherosAAplicacionEnvioDetached() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, false);

		// Documentos en modo detached
		byte[] contenido = Base64.decodeBase64("Y29udGVuaWRvIGZpY2hlcm8gMQ==");
		OctetStream[] documentos = new OctetStream[] {
			new OctetStream(contenido),
			new OctetStream(contenido),
			new OctetStream(contenido)
		};

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR8AService().envioFicherosAAplicacion(ficheroIntercambio, null, documentos);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioFicherosAAplicacionReenvio() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.REENVIO, true);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR8AService().envioFicherosAAplicacion(ficheroIntercambio, null, null);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioFicherosAAplicacionReenvioDetached() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.REENVIO, false);

		// Documentos en modo detached
		byte[] contenido = Base64.decodeBase64("Y29udGVuaWRvIGZpY2hlcm8gMQ==");
		OctetStream[] documentos = new OctetStream[] {
				new OctetStream(contenido),
				new OctetStream(contenido),
				new OctetStream(contenido)
			};

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR8AService().envioFicherosAAplicacion(ficheroIntercambio, null, documentos);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

	@Test
	public void testEnvioFicherosAAplicacionRechazo() {

		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		asientoForm.setCodigoEntidadRegistral("ER0000000000000000002");
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

		// Establecer el asiento registral como enviado
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO_Y_ACK);
		asiento.setFechaEstado(new Date());
        asiento.setFechaEnvio(new Date());
        asiento = fwktd_sir_asientoRegistralManager.update(asiento);

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(
				asiento.getIdentificadorIntercambio(),
				TipoAnotacionEnum.RECHAZO, true);

		// Enviar el mensaje
		RespuestaWS respuesta = getWSSIR8AService().envioFicherosAAplicacion(ficheroIntercambio, null, null);
		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}

}
