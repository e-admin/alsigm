package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.RecepcionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class RecepcionManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	private RecepcionManager fwktd_sir_recepcionManager;

	@Autowired
	private SicresXMLManager fwktd_sir_sicresXMLManager;

	@Test
	public void testManager() {
		Assert.assertNotNull(fwktd_sir_recepcionManager);
	}

	@Test
	public void testRecibirFicheros() {
		fwktd_sir_recepcionManager.procesarFicherosRecibidos();
	}

	@Test
	public void testRecibirAsientoRegistralEnviado() {
		AsientoRegistralVO asientoRecibido = fwktd_sir_recepcionManager.recibirFicheroIntercambio(
				TestUtils.createXMLFicheroIntercambio("ER0000000000000000001_10_99999999", TipoAnotacionEnum.ENVIO), null);
		Assert.assertNotNull(asientoRecibido);
		
		List<AnexoVO> anexos = asientoRecibido.getAnexos();
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0001.txt", anexos.get(0).getIdentificadorFichero());
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0002.txt", anexos.get(1).getIdentificadorFichero());
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0003.txt", anexos.get(2).getIdentificadorFichero());
	}

	@Test
	public void testRecibirAsientoRegistralReenviado() {
		AsientoRegistralVO asientoRecibido = fwktd_sir_recepcionManager.recibirFicheroIntercambio(
				TestUtils.createXMLFicheroIntercambio("ER0000000000000000001_10_99999999", TipoAnotacionEnum.REENVIO), null);
		Assert.assertNotNull(asientoRecibido);

		List<AnexoVO> anexos = asientoRecibido.getAnexos();
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0001.txt", anexos.get(0).getIdentificadorFichero());
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0002.txt", anexos.get(1).getIdentificadorFichero());
		Assert.assertEquals("ER0000000000000000001_10_99999999_01_0003.txt", anexos.get(2).getIdentificadorFichero());
	}

	@Test
	public void testRecibirAsientoRegistralRechazado() {
		AsientoRegistralVO asientoRecibido = fwktd_sir_recepcionManager.recibirFicheroIntercambio(
				TestUtils.createXMLFicheroIntercambio("ER0000000000000000001_10_10000005", TipoAnotacionEnum.RECHAZO), null);
		Assert.assertNotNull(asientoRecibido);
	}

	@Test
	public void testRecibirMensaje() {

		// ACK
		MensajeVO mensaje = new MensajeVO();
		mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000002");
		mensaje.setCodigoEntidadRegistralDestino("ER0000000000000000001");
		mensaje.setIdentificadorIntercambio("ER0000000000000000001_10_10000002");
		mensaje.setTipoMensaje(TipoMensajeEnum.ACK);
		mensaje.setDescripcionMensaje("Descripción del mensaje");
		mensaje.setNumeroRegistroEntradaDestino("201000100000001");
		mensaje.setFechaEntradaDestino(new Date());
		mensaje.setIdentificadoresFicheros(null);
		mensaje.setCodigoError(null);

		String xmlMensaje = fwktd_sir_sicresXMLManager.createXMLMensaje(mensaje);
		fwktd_sir_recepcionManager.recibirMensaje(xmlMensaje);

		// ERROR
		mensaje = new MensajeVO();
		mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
		mensaje.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		mensaje.setIdentificadorIntercambio("ER0000000000000000001_10_10000005");
		mensaje.setTipoMensaje(TipoMensajeEnum.ERROR);
		mensaje.setDescripcionMensaje("Descripción del mensaje");
		mensaje.setNumeroRegistroEntradaDestino("201000100000001");
		mensaje.setFechaEntradaDestino(new Date());
		mensaje.setIdentificadoresFicheros(null);
		mensaje.setCodigoError(null);

		xmlMensaje = fwktd_sir_sicresXMLManager.createXMLMensaje(mensaje);
		fwktd_sir_recepcionManager.recibirMensaje(xmlMensaje);

		// CONFIRMACION
		mensaje = new MensajeVO();
		mensaje.setCodigoEntidadRegistralOrigen("ER0000000000000000002");
		mensaje.setCodigoEntidadRegistralDestino("ER0000000000000000001");
		mensaje.setIdentificadorIntercambio("ER0000000000000000001_10_10000002");
		mensaje.setTipoMensaje(TipoMensajeEnum.CONFIRMACION);
		mensaje.setDescripcionMensaje("Descripción del mensaje");
		mensaje.setNumeroRegistroEntradaDestino("201000100000001");
		mensaje.setFechaEntradaDestino(new Date());
		mensaje.setIdentificadoresFicheros(null);
		mensaje.setCodigoError(null);

		xmlMensaje = fwktd_sir_sicresXMLManager.createXMLMensaje(mensaje);
		fwktd_sir_recepcionManager.recibirMensaje(xmlMensaje);
	}
}
