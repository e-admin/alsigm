package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.URL;
import java.util.Date;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.WS_SIR7Service;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir7.WS_SIR7_PortType;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class MensajeManagerWSSIR7ImplTest extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	private MensajeManagerWSSIR7Impl fwktd_sir_mensajeManagerWSSIR7Impl;

	public MensajeManagerWSSIR7Impl getMensajeManager() throws Exception {

		WS_SIR7_PortType service = EasyMock.createMock(WS_SIR7_PortType.class);

		RespuestaWS respuesta = new RespuestaWS();
		respuesta.setCodigo(ErroresEnum.OK.getValue());
		respuesta.setDescripcion(ErroresEnum.OK.getName());

		EasyMock.expect(service.recepcionMensajeDatosControlDeAplicacion((String) EasyMock.anyObject())).andReturn(respuesta);

		EasyMock.replay(service);

		WS_SIR7Service serviceLocator = EasyMock.createMock(WS_SIR7Service.class);
		EasyMock.expect(serviceLocator.getWS_SIR7((URL)EasyMock.anyObject())).andReturn(service);
		
		EasyMock.replay(serviceLocator);

		fwktd_sir_mensajeManagerWSSIR7Impl.setServiceLocator(serviceLocator);

		return fwktd_sir_mensajeManagerWSSIR7Impl;
	}

	@Test
	public void testManager() throws Exception {
		Assert.assertNotNull(getMensajeManager());
	}

	@Test
	public void testEnviarMensaje() throws Exception {

		Assert.assertNotNull(getMensajeManager());

		// Eliminar el fichero tras la prueba
		getMensajeManager().enviarMensaje(getMensajeVO());
	}

	private static MensajeVO getMensajeVO() {

		MensajeVO mensaje = new MensajeVO();

		mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
		mensaje.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		mensaje.setIdentificadorIntercambio("ER0000000000000000001_10_10000001");
		mensaje.setTipoMensaje(TipoMensajeEnum.ACK);
		mensaje.setDescripcionMensaje("Descripción del mensaje");
		mensaje.setNumeroRegistroEntradaDestino("201000100000001");
		mensaje.setFechaEntradaDestino(new Date());
		mensaje.setIdentificadoresFicheros(null);
		mensaje.setCodigoError(null);

		return mensaje;
	}
}
