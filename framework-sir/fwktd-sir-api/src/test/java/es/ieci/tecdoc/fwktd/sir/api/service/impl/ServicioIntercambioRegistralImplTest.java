package es.ieci.tecdoc.fwktd.sir.api.service.impl;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;

import com.ibm.icu.util.Calendar;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class ServicioIntercambioRegistralImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";
	protected static final String ID_ASIENTO_REGISTRAL_RECIBIDO = "10000003";
	protected static final String ID_ASIENTO_REGISTRAL_DEVUELTO = "10000004";
	protected static final String ID_ANEXO = "10000001";

	@Autowired
	private ServicioIntercambioRegistralImpl fwktd_sir_servicioIntercambioRegistralImpl;

	@Autowired
	private SicresXMLManager fwktd_sir_sicresXMLManager;

	protected ServicioIntercambioRegistralImpl getServicioIntercambioRegistral() {
		return fwktd_sir_servicioIntercambioRegistralImpl;
	}

	protected SicresXMLManager getSicresXMLManager() {
		return fwktd_sir_sicresXMLManager;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getServicioIntercambioRegistral());
	}

	@Test
	public void testCountAsientosRegistrales() {

		// Test de consulta simple
		int count = getServicioIntercambioRegistral().countAsientosRegistrales(null);
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta simple
		count = getServicioIntercambioRegistral().countAsientosRegistrales(new CriteriosVO());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre 2 tablas: asientos + interesados
		count = getServicioIntercambioRegistral().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre 2 tablas: asientos + anexos
		count = getServicioIntercambioRegistral().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Anexos());
		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta sobre las 3 tablas: asientos + interesados + anexos
		count = getServicioIntercambioRegistral().countAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados_Anexos());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindAsientosRegistrales() {

		// Test de consulta simple
		List<AsientoRegistralVO> asientos = getServicioIntercambioRegistral().findAsientosRegistrales(null);

		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta simple
		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(new CriteriosVO());

		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre 2 tablas: asientos + interesados
		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre 2 tablas: asientos + anexos
		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Anexos());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		// Test de consulta sobre las 3 tablas: asientos + interesados + anexos
		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO_Asientos_Interesados_Anexos());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo()));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 5);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(1,2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2,2)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(1,3)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 3);

		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO(new PageInfo(2,3)));
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue(asientos instanceof PaginatedArrayList);
		Assert.assertTrue(asientos.size() == 5);
		Assert.assertTrue(((PaginatedArrayList<AsientoRegistralVO>)asientos).getList().size() == 2);
	}

	@Test
	public void testGetEstado() {

		EstadoAsientoRegistraVO estado = getServicioIntercambioRegistral().getEstadoAsientoRegistral(ID_ASIENTO_REGISTRAL);

		Assert.assertNotNull(estado);
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO, estado.getEstado());
	}

	@Test
	public void testGetAsientoRegistral() {

		// Asiento registral existente
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);

		Assert.assertNotNull(asiento);
		Assert.assertEquals(ID_ASIENTO_REGISTRAL, asiento.getId());
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

		try {
			// Asiento registral no existente
			asiento = getServicioIntercambioRegistral().getAsientoRegistral("0");
			Assert.fail("No debería encontrar este asiento");
		} catch (ObjectRetrievalFailureException e) {
		}

	}

	@Test
	public void testSaveAsientoRegistral() {
		AsientoRegistralFormVO asiento = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asientoCreado = getServicioIntercambioRegistral().saveAsientoRegistral(asiento);
		TestUtils.assertEquals(asiento, asientoCreado);
	}

	@Test
	public void testUpdateAsientoRegistral() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		asiento.setObservacionesApunte("Observaciones modificadas");
		getServicioIntercambioRegistral().updateAsientoRegistral(asiento);
		TestUtils.assertEquals(asiento, getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL));
	}

	@Test
	public void testDeleteAsientoRegistral() {
		int count = getServicioIntercambioRegistral().countAsientosRegistrales(null);
		getServicioIntercambioRegistral().deleteAsientoRegistral(ID_ASIENTO_REGISTRAL);
		Assert.assertEquals(count -1, getServicioIntercambioRegistral().countAsientosRegistrales(null));
	}

	@Test
	public void testAddAnexo() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		AnexoFormVO anexo = TestUtils.createDefaultAnexoFormVO();

		AnexoVO anexoCreado = getServicioIntercambioRegistral().addAnexo(asiento.getId(), anexo);
		TestUtils.assertEquals(anexo, anexoCreado);
	}

	@Test
	public void testUpdateAnexo() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		AnexoVO anexo = asiento.getAnexos().get(0);
		anexo.setObservaciones("Observaciones modificadas");

		AnexoVO anexoModificado = getServicioIntercambioRegistral().updateAnexo(anexo);
		TestUtils.assertEquals(anexo, anexoModificado);
	}

	@Test
	public void testRemoveAnexo() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		int countAnexos = asiento.getAnexos().size();

		getServicioIntercambioRegistral().removeAnexo(asiento.getAnexos().get(0).getId());

		Assert.assertEquals(countAnexos - 1, getServicioIntercambioRegistral()
				.getAsientoRegistral(ID_ASIENTO_REGISTRAL).getAnexos().size());
	}

	@Test
	public void testAddInteresado() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		InteresadoFormVO interesado = TestUtils.createDefaultInteresadoFormVO();

		InteresadoVO interesadoCreado = getServicioIntercambioRegistral().addInteresado(asiento.getId(), interesado);
		TestUtils.assertEquals(interesado, interesadoCreado);
	}

	@Test
	public void testUpdateInteresado() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		InteresadoVO interesado = asiento.getInteresados().get(0);
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoVO interesadoModificado = getServicioIntercambioRegistral().updateInteresado(interesado);
		TestUtils.assertEquals(interesado, interesadoModificado);
	}

	@Test
	public void testRemoveInteresado() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID_ASIENTO_REGISTRAL);
		int countInteresados = asiento.getInteresados().size();

		getServicioIntercambioRegistral().removeInteresado(asiento.getInteresados().get(0).getId());

		Assert.assertEquals(countInteresados - 1, getServicioIntercambioRegistral()
				.getAsientoRegistral(ID_ASIENTO_REGISTRAL).getInteresados().size());
	}

	@Test
	public void testGetContenidoAnexo() {

		AnexoFormVO anexoForm = TestUtils.createDefaultAnexoFormVO();
		AnexoVO anexo = getServicioIntercambioRegistral().addAnexo(ID_ASIENTO_REGISTRAL, anexoForm);
		Assert.assertNotNull(anexo);

		byte[] contenido = getServicioIntercambioRegistral().getContenidoAnexo(anexo.getId());
		Assert.assertNotNull(contenido);
		Assert.assertEquals(Base64.encodeBase64String(anexoForm.getContenido()), Base64.encodeBase64String(contenido));
	}

	@Test
	public void testUpdateContenidoAnexo() {

		String contenido = "/9j/4AAQSkZJRgABAQEAlgCWAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAAVAB4DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD6r+LF54O+Ejwxp4b0YQzobfTtEsPDqXlzMVZdzKiKP3aqwBLYAyOckKY/DesfD7XfCEPiKLw/oWo2bymLfb+GgssboSrq8XlM6sHRuCB29ifLP2kda1aL9sDw3a2q3t9Zx6ASttaCR/JkkuHDOQikgNtiX3IUelH7Oceq+Gte+J1r4isZ9FspdVt7y0i1FTbLI7qylo2kADE+Upz1+Tn28jGWhC97W1Pfw8VOEXZO7tt5HtsuheHLu2jubbwnoAjcsq+bo1vGxwcEbXjByPTuPWlXwhoqj5/DPhrI4w+kWoH/AH15WCahtPFFhFJa2qX2nyPfti2a3dWUnDk5wSMfu2w2cHGOvFW7DUrXWg4tNTspmgwJEt5VLRkk8OFJKnKtwcdD6GvJp4vFXTjC/l+R7M8PRS1SJvE0uk3vji9v5/D+nXOowL9kW8uYEklCo+9SHK7hhxuAzgE564I1I9b2RKslpDcdMNKMngY/E+/fNFFfQSSkve1PjozktmQTOlzdCeOOO1ZU2YijQ/jllJ/XFZtxrFzpF1J5JidX5KSwR4B9flVT+Z9aKKdOMddDpdapKNmz/9k=";
		getServicioIntercambioRegistral().setContenidoAnexo(ID_ANEXO, Base64.decodeBase64(contenido));

		byte[] contenidoCreado = getServicioIntercambioRegistral().getContenidoAnexo(ID_ANEXO);
		Assert.assertEquals(contenido, Base64.encodeBase64String(contenidoCreado));
	}

	@Test
	public void testGetHistoricoAsientoRegistral() {
		List<TrazabilidadVO> trazas = getServicioIntercambioRegistral()
				.getHistoricoAsientoRegistral(ID_ASIENTO_REGISTRAL);

		Assert.assertNotNull(trazas);
	}
	
	@Test
	public void testEnviarAsientoRegistral() {
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);
		TestUtils.assertEquals(asientoForm, asiento);
	}

	@Test
	public void testEnviarAsientoRegistralById() {

		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().saveAsientoRegistral(asientoForm);

		getServicioIntercambioRegistral().enviarAsientoRegistral(asiento.getId());

		asiento = getServicioIntercambioRegistral().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull(asiento);
		Assert.assertNotNull(asiento.getIdentificadorIntercambio());
	}

	@Test
	public void testValidarAsientoRegistral() {
		getServicioIntercambioRegistral().validarAsientoRegistral(
				ID_ASIENTO_REGISTRAL_RECIBIDO, "2010", new Date());
	}

	@Test
	public void testReenviarAsientoRegistral() {
		AsientoRegistralVO infoBAsiento = getServicioIntercambioRegistral()
				.getAsientoRegistral(ID_ASIENTO_REGISTRAL_RECIBIDO);
		infoBAsiento.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		infoBAsiento.setDescripcionEntidadRegistralDestino("Descripción de ER0000000000000000002");
		infoBAsiento.setObservacionesApunte("Observaciones al reenvío");
		getServicioIntercambioRegistral().updateAsientoRegistral(infoBAsiento);

		getServicioIntercambioRegistral().reenviarAsientoRegistral(
				ID_ASIENTO_REGISTRAL_RECIBIDO);
	}

	@Test
	public void testRechazarAsientoRegistral() {
		getServicioIntercambioRegistral()
				.rechazarAsientoRegistral(
						ID_ASIENTO_REGISTRAL_RECIBIDO,
						TestUtils
								.createInfoRechazoVO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
	}

	@Test
	public void testAnularAsientoRegistral() {
		getServicioIntercambioRegistral().anularAsientoRegistral(ID_ASIENTO_REGISTRAL_DEVUELTO);
	}

	@Test
	public void testRecibirAsientoRegistralEnviado() {
		AsientoRegistralVO asientoRecibido = getServicioIntercambioRegistral().recibirFicheroIntercambio(
				TestUtils.createXMLFicheroIntercambio("ER0000000000000000001_10_99999999", TipoAnotacionEnum.ENVIO), null);
		Assert.assertNotNull(asientoRecibido);
	}

	@Test
	public void testRecibirAsientoRegistralReenviado() {
		AsientoRegistralVO asientoRecibido = getServicioIntercambioRegistral().recibirFicheroIntercambio(
				TestUtils.createXMLFicheroIntercambio("ER0000000000000000001_10_99999999", TipoAnotacionEnum.REENVIO), null);
		Assert.assertNotNull(asientoRecibido);
	}

	@Test
	public void testRecibirAsientoRegistralRechazado() {
		AsientoRegistralVO asientoRecibido = getServicioIntercambioRegistral().recibirFicheroIntercambio(
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

		String xmlMensaje = getSicresXMLManager().createXMLMensaje(mensaje);
		getServicioIntercambioRegistral().recibirMensaje(xmlMensaje);

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

		xmlMensaje = getSicresXMLManager().createXMLMensaje(mensaje);
		getServicioIntercambioRegistral().recibirMensaje(xmlMensaje);

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

		xmlMensaje = getSicresXMLManager().createXMLMensaje(mensaje);
		getServicioIntercambioRegistral().recibirMensaje(xmlMensaje);
	}

	@Test
	public void testComprobarTimeOutEnvios() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);

		// Crear un asiento registral con estado enviado y fecha de envío antigua
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asientoCreado = getServicioIntercambioRegistral().saveAsientoRegistral(asientoForm);
		TestUtils.assertEquals(asientoForm, asientoCreado);

		asientoCreado.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
		asientoCreado.setFechaEstado(cal.getTime());
		asientoCreado.setFechaEnvio(cal.getTime());
		asientoCreado.setNumeroReintentos(1);
		getServicioIntercambioRegistral().updateAsientoRegistral(asientoCreado);

		getServicioIntercambioRegistral().comprobarTimeOutEnvios();

		AsientoRegistralVO asiento2 = getServicioIntercambioRegistral().getAsientoRegistral(asientoCreado.getId());
		Assert.assertEquals(2, asiento2.getNumeroReintentos());

	}

//	@Test
//	public void testValidarAnexos() {
//
//		AsientoRegistralVO asiento = null;
//
//		try {
//
//			// Crear el asiento registral con sus documentos
//			asiento = getServicioIntercambioRegistral().saveAsientoRegistral(TestUtils.createDefaultAsientoRegistralFormVO());
//
//			List<ValidacionAnexoVO> validaciones = getServicioIntercambioRegistral().validarAnexos(asiento.getId());
//			Assert.assertNotNull("Listado de validaciones nulo", validaciones);
//			Assert.assertTrue("El número de validaciones no es correcto", validaciones.size() == 4);
//
//			// Anexo con firma
//			ValidacionAnexoVO validacionAnexo = validaciones.get(0);
//			Assert.assertNotNull("Validación nula", validacionAnexo);
//			Assert.assertNotNull("No se ha encontrado el anexo", validacionAnexo.getAnexo());
//			Assert.assertTrue("El hash no es válido", validacionAnexo.isHashValidado());
//			Assert.assertTrue("El certificado no es válido", ValidacionCertificadoEnum.CERTIFICADO_EXPIRADO == validacionAnexo.getValidacionCertificado());
//			Assert.assertTrue("Validación OCSP del certificado no es válida", validacionAnexo.isValidacionOCSPCertificado());
//			Assert.assertTrue("La firma no es válida", ValidacionFirmaEnum.FIRMA_VALIDA == validacionAnexo.getValidacionFirma());
//
//			// Anexo con firma embebida
//			validacionAnexo = validaciones.get(1);
//			Assert.assertNotNull("Validación nula", validacionAnexo);
//			Assert.assertNotNull("No se ha encontrado el anexo", validacionAnexo.getAnexo());
//			Assert.assertTrue("El hash no es válido", validacionAnexo.isHashValidado());
//			Assert.assertTrue("El certificado no es válido", ValidacionCertificadoEnum.CERTIFICADO_EXPIRADO == validacionAnexo.getValidacionCertificado());
//			Assert.assertTrue(!validacionAnexo.isValidacionOCSPCertificado());
//			Assert.assertTrue("La firma no es válida", ValidacionFirmaEnum.FIRMA_EMBEBIDA == validacionAnexo.getValidacionFirma());
//
//			// Anexo con firma externa
//			validacionAnexo = validaciones.get(2);
//			Assert.assertNotNull("Validación nula", validacionAnexo);
//			Assert.assertNotNull("No se ha encontrado el anexo", validacionAnexo.getAnexo());
//			Assert.assertTrue("El hash no es válido", validacionAnexo.isHashValidado());
//			Assert.assertTrue("El certificado no es válido", ValidacionCertificadoEnum.CERTIFICADO_EXPIRADO == validacionAnexo.getValidacionCertificado());
//			Assert.assertTrue(!validacionAnexo.isValidacionOCSPCertificado());
//			Assert.assertTrue("La firma no es válida", ValidacionFirmaEnum.FIRMA_VALIDA == validacionAnexo.getValidacionFirma());
//
//			// Anexo con la firma del anexo anterior
//			validacionAnexo = validaciones.get(3);
//			Assert.assertNotNull("Validación nula", validacionAnexo);
//			Assert.assertNotNull("No se ha encontrado el anexo", validacionAnexo.getAnexo());
//			Assert.assertTrue("El hash no es válido", validacionAnexo.isHashValidado());
//			Assert.assertTrue("El certificado no es válido", ValidacionCertificadoEnum.SIN_CERTIFICADO == validacionAnexo.getValidacionCertificado());
//			Assert.assertTrue(!validacionAnexo.isValidacionOCSPCertificado());
//			Assert.assertTrue("La firma no es válida", ValidacionFirmaEnum.SIN_FIRMA == validacionAnexo.getValidacionFirma());
//
//		} finally {
//			if (asiento != null) {
//				getServicioIntercambioRegistral().deleteAsientoRegistral(asiento.getId());
//			}
//		}
//	}
}
