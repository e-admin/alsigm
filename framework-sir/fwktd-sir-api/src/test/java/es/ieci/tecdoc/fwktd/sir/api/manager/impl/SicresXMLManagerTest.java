package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.util.Arrays;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class SicresXMLManagerTest extends AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private SicresXMLManager fwktd_sir_sicresXMLManager;

	public SicresXMLManager getSicresXMLManager() {
		return fwktd_sir_sicresXMLManager;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getSicresXMLManager());
	}

	@Test
	public void testValidarFicheroIntercambio() {

		getSicresXMLManager().validarAsientoRegistral(getAsientoRegistralVO());

		try {
			getSicresXMLManager().validarAsientoRegistral(new AsientoRegistralVO());
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("'codigoEntidadRegistralOrigen' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testValidarMensaje() {

		getSicresXMLManager().validarMensaje(getMensajeVO());

		try {
			getSicresXMLManager().validarMensaje(new MensajeVO());
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("'codigoEntidadRegistralOrigen' must not be empty", e.getMessage());
		}

	}

	@Test
	public void testCreateXMLFicheroIntercambio() {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);

		String xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento,
				true);
		Assert.assertNotNull(xml);

		xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento, false);
		Assert.assertNotNull(xml);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testCreateXMLMensaje() {
		String xml = getSicresXMLManager().createXMLMensaje(getMensajeVO());
		Assert.assertNotNull(xml);
	}

	@Test
	public void testParseXMLFicheroIntercambio() {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);

		asiento.setIdentificadorIntercambio("ER0000000000000000001_10_10000001");

		String xml = getSicresXMLManager().createXMLFicheroIntercambio(asiento, true);
		FicheroIntercambioVO ficheroIntercambio = getSicresXMLManager().parseXMLFicheroIntercambio(xml);
		Assert.assertNotNull(ficheroIntercambio);

		fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testParseXMLMensaje() {
		MensajeVO defaultMensajeVO = getMensajeVO();
		String xml = getSicresXMLManager().createXMLMensaje(defaultMensajeVO);
		MensajeVO mensaje = getSicresXMLManager().parseXMLMensaje(xml);
		Assert.assertNotNull(mensaje);
		Assert.assertEquals(defaultMensajeVO.getCodigoEntidadRegistralOrigen(), mensaje.getCodigoEntidadRegistralOrigen());
		Assert.assertEquals(defaultMensajeVO.getCodigoEntidadRegistralDestino(), mensaje.getCodigoEntidadRegistralDestino());
		Assert.assertEquals(defaultMensajeVO.getIdentificadorIntercambio(), mensaje.getIdentificadorIntercambio());
		Assert.assertEquals(defaultMensajeVO.getTipoMensaje().getValue(), mensaje.getTipoMensaje().getValue());
		Assert.assertEquals(defaultMensajeVO.getDescripcionMensaje(), mensaje.getDescripcionMensaje());
		Assert.assertEquals(defaultMensajeVO.getNumeroRegistroEntradaDestino(), mensaje.getNumeroRegistroEntradaDestino());
		Assert.assertEquals(defaultMensajeVO.getFechaEntradaDestino().toString(), mensaje.getFechaEntradaDestino().toString());
		Assert.assertEquals(Arrays.toString(defaultMensajeVO.getIdentificadoresFicheros().toArray()), Arrays.toString(mensaje.getIdentificadoresFicheros().toArray()));
		Assert.assertEquals(defaultMensajeVO.getCodigoError(), mensaje.getCodigoError());

	}

	private AsientoRegistralVO getAsientoRegistralVO() {
		return fwktd_sir_asientoRegistralManager.get(ID_ASIENTO_REGISTRAL);
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
		mensaje.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
		mensaje.setIdentificadoresFicheros(Arrays.asList(new String[] { "id1", "id2"}));
		mensaje.setCodigoError("0000");

		return mensaje;
	}

}
