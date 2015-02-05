package es.ieci.tecdoc.fwktd.csv.ws.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;


/**
 * Test de la clase de utilidad para la adaptación de objetos con la información
 * de aplicaciones externas desde el fwktd-csv-api.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionAdapterHelperTest {

	@Test
	public void testGetListaAplicacionCSV() {

		List<es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV> aplicacionesCore = null;
		List<AplicacionCSV> aplicaciones = AplicacionAdapterHelper.getListaAplicacionCSV(aplicacionesCore);
		Assert.assertNull("El objeto debería ser nulo", aplicaciones);

		aplicacionesCore = new ArrayList<es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV>();
		aplicacionesCore.add(createAplicacionCSVCore("1", "C1", "Aplicación 1", "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));
		aplicacionesCore.add(createAplicacionCSVCore("2", "C2", "Aplicación 2", "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));

		aplicaciones = AplicacionAdapterHelper.getListaAplicacionCSV(aplicacionesCore);
		assertEquals(aplicaciones, aplicacionesCore);
	}

	@Test
	public void testGetAplicacionCSV() {

		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacionCore = null;
		AplicacionCSV aplicacion = AplicacionAdapterHelper.getAplicacionCSV(aplicacionCore);
		Assert.assertNull("El objeto debería ser nulo", aplicacion);

		aplicacionCore = createAplicacionCSVCore("1", "C1", "Aplicación 1", "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		aplicacion = AplicacionAdapterHelper.getAplicacionCSV(aplicacionCore);
		assertEquals(aplicacion, aplicacionCore);
	}

	@Test
	public void testGetAplicacionCSVFormCore() {

		AplicacionCSVForm aplicacionForm = null;
		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm aplicacionFormCore = AplicacionAdapterHelper.getAplicacionCSVFormCore(aplicacionForm);
		Assert.assertNull("El objeto debería ser nulo", aplicacionFormCore);

		aplicacionForm = createAplicacionCSVForm("1", "C1", "Aplicación 1", "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		aplicacionFormCore = AplicacionAdapterHelper.getAplicacionCSVFormCore(aplicacionForm);
		assertEquals(aplicacionForm, aplicacionFormCore);
	}

	@Test
	public void testGetAplicacionCSVCore() {

		AplicacionCSV aplicacion = null;
		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacionCore = AplicacionAdapterHelper.getAplicacionCSVCore(aplicacion);
		Assert.assertNull("El objeto debería ser nulo", aplicacionCore);

		aplicacion = createAplicacionCSV("1", "C1", "Aplicación 1", "<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");
		aplicacionCore = AplicacionAdapterHelper.getAplicacionCSVCore(aplicacion);
		assertEquals(aplicacion, aplicacionCore);
	}

	protected static AplicacionCSVForm createAplicacionCSVForm(String id, String codigo, String nombre, String infoConexion) {

		AplicacionCSVForm aplicacion = new AplicacionCSVForm();
		aplicacion.setCodigo(codigo);
		aplicacion.setNombre(nombre);
		aplicacion.setInfoConexion(infoConexion);

		return aplicacion;
	}

	protected static AplicacionCSV createAplicacionCSV(String id, String codigo, String nombre, String infoConexion) {

		AplicacionCSV aplicacion = new AplicacionCSV();
		aplicacion.setId(id);
		aplicacion.setCodigo(codigo);
		aplicacion.setNombre(nombre);
		aplicacion.setInfoConexion(infoConexion);

		return aplicacion;
	}

	protected static es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV createAplicacionCSVCore(String id, String codigo, String nombre, String infoConexion) {

		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacion = new es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV();
		aplicacion.setId(id);
		aplicacion.setCodigo(codigo);
		aplicacion.setNombre(nombre);
		aplicacion.setInfoConexion(infoConexion);

		return aplicacion;
	}

	protected void assertEquals(List<AplicacionCSV> aplicaciones, List<es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV> aplicacionesCore) {

		Assert.assertEquals(CollectionUtils.size(aplicaciones), CollectionUtils.size(aplicacionesCore));
		for (int i = 0; i < aplicaciones.size(); i++) {
			assertEquals(aplicaciones.get(i), aplicacionesCore.get(i));
		}
	}

	protected void assertEquals(AplicacionCSVForm aplicacion, es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm aplicacionCore) {

		if (aplicacion == null) {
			Assert.assertNull(aplicacionCore);
		}

		if (aplicacion instanceof AplicacionCSV) {
			Assert.assertEquals(((AplicacionCSV)aplicacion).getId(), ((es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV)aplicacionCore).getId());
		}

		Assert.assertEquals(aplicacion.getCodigo(), aplicacionCore.getCodigo());
		Assert.assertEquals(aplicacion.getNombre(), aplicacionCore.getNombre());
		Assert.assertEquals(aplicacion.getInfoConexion(), aplicacionCore.getInfoConexion());
	}
 }
