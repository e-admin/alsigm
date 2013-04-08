package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
"/beans/fwktd-sir-test-beans-custom.xml" })
public class CodificacionFicherosManagerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CodificacionFicherosManager fwktd_sir_codificacionFicherosManager;

	public CodificacionFicherosManager getCodificacionFicherosManager() {
		return fwktd_sir_codificacionFicherosManager;
	}

	@Test
	public void testGetIdentificadorIntercambio() {

		Assert.assertNotNull(getCodificacionFicherosManager());

		AsientoRegistralVO asiento = new AsientoRegistralVO();
		asiento.setCodigoEntidadRegistralOrigen("ER0000000000000000001");

		String identificador = getCodificacionFicherosManager().getIdentificadorIntercambio(asiento);
		Assert.assertNotNull("Identificador intercambio", identificador);
		Assert.assertTrue(identificador.startsWith(asiento.getCodigoEntidadRegistralOrigen()));
	}

	@Test
	public void testGetCodificacionMensaje() {

		Assert.assertNotNull(getCodificacionFicherosManager());

		MensajeVO mensaje = new MensajeVO();
		mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000001");

		String codigo = getCodificacionFicherosManager().getCodificacionMensaje(mensaje);
		Assert.assertNotNull("Código del mensaje", codigo);
		Assert.assertTrue(codigo.startsWith(mensaje.getCodigoEntidadRegistralOrigen()));
	}
}
