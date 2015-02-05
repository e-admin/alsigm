package es.ieci.tecdoc.fwktd.csv.wsclient.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;

/**
 * Test de la clase de utilidad para la adaptación de objetos con la información
 * de aplicaciones externas desde el fwktd-csv-ws.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionAdapterHelperTest {

	@Test
	public void testGetListaAplicacionCSV() {

		List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV> aplicacionesWS = new ArrayList<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV>();
		aplicacionesWS
				.add(createAplicacionWS(
						"1",
						"C1",
						"Nombre1",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));
		aplicacionesWS
				.add(createAplicacionWS(
						"2",
						"C2",
						"Nombre2",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));

		List<AplicacionCSV> aplicacionesCSV = AplicacionAdapterHelper
				.getListaAplicacionCSV(aplicacionesWS);
		assertEquals(aplicacionesWS, aplicacionesCSV);
	}

	@Test
	public void testGetAplicacionCSV() {

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS = createAplicacionWS(
				"1",
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacion = AplicacionAdapterHelper
				.getAplicacionCSV(aplicacionWS);

		assertEquals(aplicacionWS, aplicacion);
	}

	@Test
	public void testGetWSAplicacionCSVForm() {

		AplicacionCSVForm aplicacionForm = createAplicacionCSVForm(
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm aplicacionWS = AplicacionAdapterHelper.getWSAplicacionCSVForm(aplicacionForm);
		assertEquals(aplicacionWS, aplicacionForm);
	}

	@Test
	public void testGetWSAplicacionCSV() {

		AplicacionCSV aplicacion = createAplicacionCSV(
				"1",
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS = AplicacionAdapterHelper.getWSAplicacionCSV(aplicacion);
		assertEquals(aplicacionWS, aplicacion);
	}

	protected es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV createAplicacionWS(String id, String codigo,
			String nombre, String infoConexion) {
		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionVO = new es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV();
		aplicacionVO.setId(id);
		aplicacionVO.setCodigo(codigo);
		aplicacionVO.setNombre(nombre);
		aplicacionVO.setInfoConexion(infoConexion);
		return aplicacionVO;
	}

	protected AplicacionCSVForm createAplicacionCSVForm(String codigo,
			String nombre, String infoConexion) {
		AplicacionCSVForm aplicacionCSVForm = new AplicacionCSVForm();
		aplicacionCSVForm.setCodigo(codigo);
		aplicacionCSVForm.setNombre(nombre);
		aplicacionCSVForm.setInfoConexion(infoConexion);
		return aplicacionCSVForm;
	}

	protected AplicacionCSV createAplicacionCSV(String id, String codigo,
			String nombre, String infoConexion) {
		AplicacionCSV aplicacionCSV = new AplicacionCSV();
		aplicacionCSV.setId(id);
		aplicacionCSV.setCodigo(codigo);
		aplicacionCSV.setNombre(nombre);
		aplicacionCSV.setInfoConexion(infoConexion);
		return aplicacionCSV;
	}

	protected void assertEquals(List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV> aplicacionesWS, List<AplicacionCSV> aplicacionesCSV) {

		Assert.assertTrue(CollectionUtils.size(aplicacionesWS) == CollectionUtils.size(aplicacionesCSV));

		for (int i = 0; i < aplicacionesWS.size(); i++) {
			assertEquals(aplicacionesWS.get(i), aplicacionesCSV.get(i));
		}
	}

	protected void assertEquals(es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm aplicacionWS, AplicacionCSVForm aplicacionCSVForm) {

		Assert.assertNotNull(aplicacionWS);
		Assert.assertNotNull(aplicacionCSVForm);

		if (aplicacionCSVForm instanceof AplicacionCSV) {
			Assert.assertEquals(((es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV)aplicacionWS).getId(), ((AplicacionCSV)aplicacionCSVForm).getId());
		}
		Assert.assertEquals(aplicacionWS.getCodigo(), aplicacionCSVForm.getCodigo());
		Assert.assertEquals(aplicacionWS.getNombre(), aplicacionCSVForm.getNombre());
		Assert.assertEquals(aplicacionWS.getInfoConexion(), aplicacionCSVForm.getInfoConexion());
	}
}
