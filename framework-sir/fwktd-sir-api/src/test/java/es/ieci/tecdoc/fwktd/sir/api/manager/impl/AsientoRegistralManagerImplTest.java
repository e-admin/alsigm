package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.helpers.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import com.ibm.icu.util.Calendar;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.RecepcionManager;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class AsientoRegistralManagerImplTest extends BaseManagerTest {

	protected static final String ID_ASIENTO_REGISTRAL_EXISTENTE = "10000001";
	protected static final String ID_ASIENTO_REGISTRAL_NO_EXISTENTE = "99999999";
	protected static final String ID_ASIENTO_REGISTRAL_A_BORRAR = "10000002";

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";
	protected static final String ID_ASIENTO_REGISTRAL_RECIBIDO = "10000003";
	protected static final String ID_ASIENTO_REGISTRAL_DEVUELTO = "10000004";


	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private RecepcionManager fwktd_sir_recepcionManager;

	public AsientoRegistralManager getAsientoRegistralManager() {
		return fwktd_sir_asientoRegistralManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseManager<Entity, String> getManager() {
		return (BaseManager) getAsientoRegistralManager();
	}

	public String getIdExistente() {
		return ID_ASIENTO_REGISTRAL_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_ASIENTO_REGISTRAL_NO_EXISTENTE;
	}

	@Test
	public void testCountAsientosRegistrales() {

		// Test de consulta simple
		int count = getAsientoRegistralManager().countAsientosRegistrales(null);
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta simple
		count = getAsientoRegistralManager().countAsientosRegistrales(new CriteriosVO());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre 2 tablas: asientos + interesados
		count = getAsientoRegistralManager().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre 2 tablas: asientos + anexos
		count = getAsientoRegistralManager().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Anexos());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre las 3 tablas: asientos + interesados + anexos
		count = getAsientoRegistralManager().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados_Anexos());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindAsientosRegistrales() {

		// Test de consulta simple
		List<AsientoRegistralVO> asientos = getAsientoRegistralManager().findAsientosRegistrales(null);

		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta simple
		asientos = getAsientoRegistralManager().findAsientosRegistrales(new CriteriosVO());

		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre 2 tablas: asientos + interesados
		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre 2 tablas: asientos + anexos
		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Anexos());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre las 3 tablas: asientos + interesados + anexos
		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados_Anexos());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo()));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 5);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(1,2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2,2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(1,3)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 3);

		asientos = getAsientoRegistralManager().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2,3)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);
	}

	@Test
	public void testGet() {

		AsientoRegistralVO asiento = getAsientoRegistralManager().get(getIdExistente());

		Assert.assertNotNull(asiento);
		Assert.assertEquals(getIdExistente(), asiento.getId());
		Assert.assertEquals("ER0000000000000000001", asiento.getCodigoEntidadRegistralOrigen());
		Assert.assertEquals("Entidad Registral ER0000000000000000001", asiento.getDescripcionEntidadRegistralOrigen());
		Assert.assertEquals("201000100000001", asiento.getNumeroRegistro());
		Assert.assertEquals("Thu May 06 12:07:31 CEST 2010", asiento.getFechaRegistro().toString());
		Assert.assertEquals("KioqdGltZXN0YW1wKioq", Base64.encodeBase64String(asiento.getTimestampRegistro()));
		Assert.assertEquals("201000100000001", asiento.getNumeroRegistroInicial());
		Assert.assertEquals("Thu May 06 12:07:31 CEST 2010", asiento.getFechaRegistroInicial().toString());
		Assert.assertEquals("KioqdGltZXN0YW1wKioq", Base64.encodeBase64String(asiento.getTimestampRegistroInicial()));
		Assert.assertEquals("UT0000000000000000001", asiento.getCodigoUnidadTramitacionOrigen());
		Assert.assertEquals("Unidad de Tramitación UT0000000000000000001", asiento.getDescripcionUnidadTramitacionOrigen());
		Assert.assertEquals("ER0000000000000000002", asiento.getCodigoEntidadRegistralDestino());
		Assert.assertEquals("Entidad Registral ER0000000000000000002", asiento.getDescripcionEntidadRegistralDestino());
		Assert.assertEquals("UT0000000000000000002", asiento.getCodigoUnidadTramitacionDestino());
		Assert.assertEquals("Unidad de Tramitación UT0000000000000000002", asiento.getDescripcionUnidadTramitacionDestino());
		Assert.assertEquals("Resumen", asiento.getResumen());
		Assert.assertEquals("ASUNTO0000000001", asiento.getCodigoAsunto());
		Assert.assertEquals("REF0000000000001", asiento.getReferenciaExterna());
		Assert.assertEquals("EXP2010/000001", asiento.getNumeroExpediente());
		Assert.assertEquals(TipoTransporteEnum.CORREO_POSTAL_CERTIFICADO, asiento.getTipoTransporte());
		Assert.assertEquals("00000000000000000001", asiento.getNumeroTransporte());
		Assert.assertEquals("fwktdsir", asiento.getNombreUsuario());
		Assert.assertEquals("fwktdsir@ieci.es", asiento.getContactoUsuario());
		Assert.assertEquals("ER0000000000000000001_10_10000001", asiento.getIdentificadorIntercambio());
		Assert.assertEquals("ieci", asiento.getAplicacion());
		Assert.assertEquals(TipoAnotacionEnum.PENDIENTE, asiento.getTipoAnotacion());
		Assert.assertEquals("Pendiente", asiento.getDescripcionTipoAnotacion());
		Assert.assertEquals(TipoRegistroEnum.ENTRADA, asiento.getTipoRegistro());
		Assert.assertEquals(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA, asiento.getDocumentacionFisica());
		Assert.assertEquals("Observaciones", asiento.getObservacionesApunte());
		Assert.assertEquals(IndicadorPruebaEnum.PRUEBA, asiento.getIndicadorPrueba());
		Assert.assertEquals("ER0000000000000000001", asiento.getCodigoEntidadRegistralInicio());
		Assert.assertEquals("Entidad Registral ER0000000000000000001", asiento.getDescripcionEntidadRegistralInicio());
		Assert.assertEquals("Exposición de los hechos y antecedentes relacionados con la solicitud", asiento.getExpone());
		Assert.assertEquals("Descripción del objeto de la solicitud", asiento.getSolicita());

		// Interesados del asiento registral
		List<InteresadoVO> interesados = asiento.getInteresados();
		Assert.assertNotNull(interesados);
		Assert.assertFalse(interesados.isEmpty());

		// Anexos del asiento registral
		List<AnexoVO> anexos = asiento.getAnexos();
		Assert.assertFalse(anexos.isEmpty());
	}

	@Test
	public void testGetCodigoIntercambio() {

		String codigoIntercambio = getAsientoRegistralManager().getCodigoIntercambio(getIdExistente());

		Assert.assertNotNull(codigoIntercambio);
		Assert.assertEquals("ER0000000000000000001_10_10000001", codigoIntercambio);
	}
	
	@Test
	public void testGetDescripcionTipoAnotacion(){
		String descripcionTipoAnotacion = getAsientoRegistralManager().getDescripcionTipoAnotacion(getIdExistente());

		Assert.assertNotNull(descripcionTipoAnotacion);
		Assert.assertEquals("Pendiente", descripcionTipoAnotacion);
	}

	@Test
	public void testGetAsientoRegistralByCodigoIntercambio() {

		AsientoRegistralVO asiento = getAsientoRegistralManager().getAsientoRegistral("ER0000000000000000001", "ER0000000000000000001_10_10000001");

		Assert.assertNotNull(asiento);
		Assert.assertEquals(getIdExistente(), asiento.getId());
	}

	@Test
	public void testGetEstado() {

		 EstadoAsientoRegistraVO estado = getAsientoRegistralManager().getEstado(getIdExistente());

		Assert.assertNotNull(estado);
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO, estado.getEstado());
	}

	@Test
	public void testSaveAsientoRegistral() {
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asientoCreado = getAsientoRegistralManager().saveAsientoRegistral(asientoForm);
		TestUtils.assertEquals(asientoForm, asientoCreado);
	}

	@Test
	public void testSave() {
		AsientoRegistralVO asiento = TestUtils.createDefaultAsientoRegistralVO();
		AsientoRegistralVO asientoCreado = getAsientoRegistralManager().save(asiento);
		TestUtils.assertEqualsSimple(asiento, asientoCreado);
	}

	@Test
	public void testUpdate() {

		AsientoRegistralVO asiento = getAsientoRegistralManager().get(getIdExistente());
		asiento.setObservacionesApunte("Observaciones modificadas");

		AsientoRegistralVO asientoModificado = getAsientoRegistralManager().update(asiento);
		TestUtils.assertEqualsSimple(asiento, asientoModificado);
	}

	@Test
	public void testDeleteAsientoRegistral() {
		int count = getManager().count();
		getAsientoRegistralManager().deleteAsientoRegistral(ID_ASIENTO_REGISTRAL_EXISTENTE);
		Assert.assertEquals(count - 1, getManager().count());
	}

	@Test
	public void testDelete() {
		int count = getManager().count();
		getManager().delete(ID_ASIENTO_REGISTRAL_A_BORRAR);
		Assert.assertEquals(getManager().count(), count-1);

		try {
			getManager().delete(getIdExistente());
			Assert.fail("Se ha eliminado un asiento con interesados y anexos");
		} catch (DataIntegrityViolationException e) {
		}
	}

	@Test
	public void testEnviarAsientoRegistral() {
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento =  getAsientoRegistralManager().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);
		TestUtils.assertEquals(asientoForm, asiento);

		getAsientoRegistralManager().deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testEnviarAsientoRegistralById() {
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento =  getAsientoRegistralManager().enviarAsientoRegistral(asientoForm);

		getAsientoRegistralManager().enviarAsientoRegistral(asiento.getId());

		asiento = getAsientoRegistralManager().get(asiento.getId());
		Assert.assertNotNull(asiento);
		Assert.assertNotNull(asiento.getIdentificadorIntercambio());

		getAsientoRegistralManager().deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testValidarAsientoRegistral() {
		getAsientoRegistralManager().validarAsientoRegistral(
				ID_ASIENTO_REGISTRAL_RECIBIDO, "2010", new Date());
	}

	@Test
	public void testReenviarAsientoRegistral() {
		AsientoRegistralVO infoBAsiento = getAsientoRegistralManager()
				.get(ID_ASIENTO_REGISTRAL_RECIBIDO);
		infoBAsiento.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		infoBAsiento.setDescripcionEntidadRegistralDestino("Descripción de ER0000000000000000002");
		infoBAsiento.setObservacionesApunte("Observaciones al reenvío");
		getAsientoRegistralManager().update(infoBAsiento);

		getAsientoRegistralManager().reenviarAsientoRegistral(
				ID_ASIENTO_REGISTRAL_RECIBIDO);
	}

	@Test
	public void testReenviarAsientoRegistralSimple() {

		InfoReenvioVO infoReenvio = new InfoReenvioVO();
		infoReenvio.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		infoReenvio.setDescripcionEntidadRegistralDestino("Descripción de ER0000000000000000002");
		infoReenvio.setDescripcion("Motivos del reenvio");
		infoReenvio.setUsuario("usuario");
		infoReenvio.setContacto("contacto");
		infoReenvio.setAplicacion("app1");

		getAsientoRegistralManager().reenviarAsientoRegistral(
				ID_ASIENTO_REGISTRAL_RECIBIDO, infoReenvio);
	}

	@Test
	public void testRechazarAsientoRegistral() {
		getAsientoRegistralManager()
				.rechazarAsientoRegistral(
						ID_ASIENTO_REGISTRAL_RECIBIDO,
						TestUtils
								.createInfoRechazoVO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
	}

	@Test
	public void testAnularAsientoRegistral() {
		getAsientoRegistralManager().anularAsientoRegistral(ID_ASIENTO_REGISTRAL_DEVUELTO);
	}

	@Test
	public void testComprobarTimeOutEnvios() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);

		// Crear un asiento registral con estado enviado y fecha de envío antigua
		AsientoRegistralVO asiento = TestUtils.createDefaultAsientoRegistralVO();
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
		asiento.setFechaEstado(cal.getTime());
		asiento.setFechaEnvio(cal.getTime());
		asiento.setNumeroReintentos(1);

		AsientoRegistralVO asientoCreado = getAsientoRegistralManager().save(asiento);
		TestUtils.assertEqualsSimple(asiento, asientoCreado);

		getAsientoRegistralManager().comprobarTimeOutEnvios();

		AsientoRegistralVO asiento2 = getAsientoRegistralManager().get(asientoCreado.getId());
		Assert.assertEquals(2, asiento2.getNumeroReintentos());

	}

	@Test
	public void testRecibirYRechazarAsientoRegistral() throws IOException {

		// XML del fichero de intercambio
		String xmlFicheroIntercambio = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream(
						"ficheroIntercambio.xml"));

		AsientoRegistralVO asientoRecibido = null;
		
		try {

			// Recibir asiento
			asientoRecibido = fwktd_sir_recepcionManager
					.recibirFicheroIntercambio(xmlFicheroIntercambio, null);
			Assert.assertNotNull(asientoRecibido);
			
			// Rechazar
			getAsientoRegistralManager()
					.rechazarAsientoRegistral(
							asientoRecibido.getId(),
							TestUtils
									.createInfoRechazoVO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
			
			AsientoRegistralVO asientoRechazado = getAsientoRegistralManager().get(asientoRecibido.getId());
			Assert.assertNotNull(asientoRechazado);
			
		} finally  {
			if (asientoRecibido != null) {
				getAsientoRegistralManager().deleteAsientoRegistral(asientoRecibido.getId());
			}
		}
	}
}
