package es.ieci.tecdoc.fwktd.sir.ws.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
		"/beans/fwktd-sir-api-test-initial-beans.xml",
		"/beans/fwktd-sir-ws-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-api-test-beans.xml"})
public class EnvioManagerImplTest extends AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String IDENTIFICADOR_INTERCAMBIO = "ER0000000000000000001_99_10000001";

	@Autowired
	private EnvioManagerImpl fwktd_sir_ws_envioManagerImpl;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	protected EnvioManagerImpl getEnvioManagerImpl() {
		return fwktd_sir_ws_envioManagerImpl;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getEnvioManagerImpl());
	}

	@Test
	public void testEnvioFicheroEnvio() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, true);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, null);
	}

	@Test
	public void testEnvioFicheroEnvioDetached() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, false);

		// Documentos en modo detached
		byte[] contenido = Base64.decodeBase64("Y29udGVuaWRvIGZpY2hlcm8gMQ==");
		List<byte[]> documentos = new ArrayList<byte[]>();
		documentos.add(contenido);
		documentos.add(contenido);
		documentos.add(contenido);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, documentos);
	}

	@Test
	public void testEnvioFicheroReenvio() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.REENVIO, true);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, null);
	}

	@Test
	public void testEnvioFicheroReenvioDetached() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.REENVIO, false);

		// Documentos en modo detached
		byte[] contenido = Base64.decodeBase64("Y29udGVuaWRvIGZpY2hlcm8gMQ==");
		List<byte[]> documentos = new ArrayList<byte[]>();
		documentos.add(contenido);
		documentos.add(contenido);
		documentos.add(contenido);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, documentos);
	}

	@Test
	public void testEnvioFicheroRechazo() {

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
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(asiento.getIdentificadorIntercambio(), TipoAnotacionEnum.RECHAZO, true);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, null);
	}

	@Test
	public void testEnvioFicheroRechazoDetached() {

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
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(asiento.getIdentificadorIntercambio(), TipoAnotacionEnum.RECHAZO, false);

		// Documentos en modo detached
		byte[] contenido = Base64.decodeBase64("Y29udGVuaWRvIGZpY2hlcm8gMQ==");
		List<byte[]> documentos = new ArrayList<byte[]>();
		documentos.add(contenido);
		documentos.add(contenido);
		documentos.add(contenido);

		// Enviar el mensaje
		getEnvioManagerImpl().envioFichero(ficheroIntercambio, null, documentos);
	}

	@Test
	public void testEnvioMensajeACK() {

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
		getEnvioManagerImpl().envioMensaje(mensaje, null);
	}

	@Test
	public void testEnvioMensajeConfirmacion() {

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
		getEnvioManagerImpl().envioMensaje(mensaje, null);
	}

	@Test
	public void testEnvioMensajeError() {

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
		getEnvioManagerImpl().envioMensaje(mensaje, null);
	}

}
