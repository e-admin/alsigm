package es.ieci.tecdoc.fwktd.sir.api.manager.impl.fs;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.manager.impl.fs.MensajeManagerFSImpl;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class MensajeManagerFSImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MensajeManagerFSImpl fwktd_sir_mensajeManagerFS;

	public MensajeManagerFSImpl getMensajeManager() {
		return fwktd_sir_mensajeManagerFS;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getMensajeManager());
	}

	@Test
	public void testEnviarMensaje() {

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
