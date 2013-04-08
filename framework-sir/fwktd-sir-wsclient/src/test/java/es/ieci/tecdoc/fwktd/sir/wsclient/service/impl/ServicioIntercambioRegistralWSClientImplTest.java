package es.ieci.tecdoc.fwktd.sir.wsclient.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.IntercambioRegistralWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.sir.wsclient.TestUtils;
import es.ieci.tecdoc.fwktd.sir.wsclient.mapper.impl.MapperImpl;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

@RunWith(BlockJUnit4ClassRunner.class)
public class ServicioIntercambioRegistralWSClientImplTest {

	private static final String ID = "1";

//	private static List<ValidacionAnexoDTO> LISTA_VALIDACIONES_ANEXO_DTO = new ArrayList<ValidacionAnexoDTO>();
//	static {
//		LISTA_VALIDACIONES_ANEXO_DTO.add(TestUtils.createValidacionAnexoDTO("1", ID));
//		LISTA_VALIDACIONES_ANEXO_DTO.add(TestUtils.createValidacionAnexoDTO("2", ID));
//	}


	protected ServicioIntercambioRegistralWSClientImpl getServicioIntercambioRegistral() {

		ServicioIntercambioRegistralWSClientImpl service = new ServicioIntercambioRegistralWSClientImpl();

		service.setMapper(new MapperImpl());
		service.setIntercambioRegistralWSClientFactory(getIntercambioRegistralWSClientFactory());

		return service;
	}

	@SuppressWarnings("unchecked")
	protected IntercambioRegistralWSClientFactory getIntercambioRegistralWSClientFactory() {

		IntercambioRegistralWS intercambioRegistralWS = EasyMock.createMock(IntercambioRegistralWS.class);
		

		EasyMock.expect(intercambioRegistralWS.countAsientosRegistrales((CriteriosDTO) EasyMock.anyObject())).andReturn(3);
		EasyMock.expect(intercambioRegistralWS.findAsientosRegistrales((CriteriosDTO) EasyMock.anyObject())).andReturn(
				Arrays.asList(new AsientoRegistralDTO[] {
						TestUtils.createAsientoRegistralDTO("1"),
						TestUtils.createAsientoRegistralDTO("2"),
						TestUtils.createAsientoRegistralDTO("3")
				}));

		EasyMock.expect(intercambioRegistralWS.getHistoricoAsientoRegistral((String) EasyMock.anyObject())).andReturn(
				Arrays.asList(new TrazabilidadDTO[] {
						TestUtils.createTrazabilidadDTO("1"),
						TestUtils.createTrazabilidadDTO("2"),
						TestUtils.createTrazabilidadDTO("3")
				}));

		EasyMock.expect(
				intercambioRegistralWS
						.getEstadoAsientoRegistral((String) EasyMock
								.anyObject())).andReturn(
				TestUtils.createEstadoAsientoRegistralDTO());

		EasyMock.expect(intercambioRegistralWS.getAsientoRegistral((String) EasyMock.anyObject())).andAnswer(
				new IAnswer<AsientoRegistralDTO>() {
			        public AsientoRegistralDTO answer() throws Throwable {
			        	return TestUtils.createAsientoRegistralDTO((String) EasyMock.getCurrentArguments()[0]);
			        }
			    });
		EasyMock.expect(intercambioRegistralWS.saveAsientoRegistral((AsientoRegistralFormDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<AsientoRegistralDTO>() {
			        public AsientoRegistralDTO answer() throws Throwable {
			        	return TestUtils.createAsientoRegistralDTO(ID, (AsientoRegistralFormDTO) EasyMock.getCurrentArguments()[0]);
			        }
			    });
		intercambioRegistralWS.updateAsientoRegistral((InfoBAsientoRegistralDTO) EasyMock.anyObject());
		intercambioRegistralWS.deleteAsientoRegistral((String) EasyMock.anyObject());

		EasyMock.expect(intercambioRegistralWS.addInteresado((String) EasyMock.anyObject(), (InteresadoFormDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<InteresadoDTO>() {
			        public InteresadoDTO answer() throws Throwable {
			        	return TestUtils.createInteresadoDTO(
			        			(String) EasyMock.getCurrentArguments()[0],
			        			(String) EasyMock.getCurrentArguments()[0],
			        			(InteresadoFormDTO) EasyMock.getCurrentArguments()[1]);
			        }
			    });
		EasyMock.expect(intercambioRegistralWS.updateInteresado((InteresadoDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<InteresadoDTO>() {
			        public InteresadoDTO answer() throws Throwable {
			        	return (InteresadoDTO) EasyMock.getCurrentArguments()[0];
			        }
			    });
		intercambioRegistralWS.removeInteresado((String) EasyMock.anyObject());

		EasyMock.expect(intercambioRegistralWS.getContenidoAnexo((String) EasyMock.anyObject())).andReturn(
				"contenido".getBytes());
		intercambioRegistralWS.setContenidoAnexo((String) EasyMock.anyObject(), (byte[]) EasyMock.anyObject());
		EasyMock.expect(intercambioRegistralWS.addAnexo((String) EasyMock.anyObject(), (AnexoFormDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<AnexoDTO>() {
			        public AnexoDTO answer() throws Throwable {
			        	return TestUtils.createAnexoDTO(
			        			(String) EasyMock.getCurrentArguments()[0],
			        			(String) EasyMock.getCurrentArguments()[0],
			        			(AnexoFormDTO) EasyMock.getCurrentArguments()[1]);
			        }
			    });
		EasyMock.expect(intercambioRegistralWS.updateAnexo((AnexoDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<AnexoDTO>() {
			        public AnexoDTO answer() throws Throwable {
			        	return (AnexoDTO) EasyMock.getCurrentArguments()[0];
			        }
			    });
		intercambioRegistralWS.removeAnexo((String) EasyMock.anyObject());

		EasyMock.expect(intercambioRegistralWS.enviarAsientoRegistral((AsientoRegistralFormDTO) EasyMock.anyObject())).andAnswer(
				new IAnswer<AsientoRegistralDTO>() {
			        public AsientoRegistralDTO answer() throws Throwable {
			        	return TestUtils.createAsientoRegistralDTO(ID, (AsientoRegistralFormDTO) EasyMock.getCurrentArguments()[0]);
			        }
			    });
		intercambioRegistralWS.enviarAsientoRegistralById((String) EasyMock.anyObject());
		intercambioRegistralWS.validarAsientoRegistral((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (XMLGregorianCalendar) EasyMock.anyObject());
		intercambioRegistralWS.reenviarAsientoRegistralById((String) EasyMock.anyObject());
		intercambioRegistralWS.reenviarAsientoRegistral((String) EasyMock.anyObject(), (InfoReenvioDTO) EasyMock.anyObject());
		intercambioRegistralWS.rechazarAsientoRegistral((String) EasyMock.anyObject(), (InfoRechazoDTO) EasyMock.anyObject());
		intercambioRegistralWS.anularAsientoRegistral((String) EasyMock.anyObject());

		EasyMock.expect(intercambioRegistralWS.recibirFicheroIntercambio((String) EasyMock.anyObject(), (List<byte[]>) EasyMock.anyObject())).andReturn(
				TestUtils.createAsientoRegistralDTO(ID));
		intercambioRegistralWS.recibirMensaje((String) EasyMock.anyObject());

		intercambioRegistralWS.comprobarTimeOutEnvios();
		intercambioRegistralWS.procesarFicherosRecibidos();

//		EasyMock.expect(intercambioRegistralWS.validarAnexos((String) EasyMock.anyObject())).andAnswer(
//				new IAnswer<List<ValidacionAnexoDTO>>() {
//			        public List<ValidacionAnexoDTO> answer() throws Throwable {
//			    		return LISTA_VALIDACIONES_ANEXO_DTO;
//			        }
//			    });

		EasyMock.replay(intercambioRegistralWS);
		
		
		//mock de la factoria
		IntercambioRegistralWSClientFactory  intercambioRegistralWSClientFactory= EasyMock.createMock(IntercambioRegistralWSClientFactory.class);
		EasyMock.expect(intercambioRegistralWSClientFactory.getIntercambioRegistralWS()).andReturn(intercambioRegistralWS);
		
		EasyMock.replay(intercambioRegistralWSClientFactory);

		return intercambioRegistralWSClientFactory;
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

		// Test de consulta
		count = getServicioIntercambioRegistral().countAsientosRegistrales(TestUtils.createCriteriosVO());
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
		asientos = getServicioIntercambioRegistral().findAsientosRegistrales(TestUtils.createCriteriosVO());
		Assert.assertNotNull("Resultado nulo", asientos);
		Assert.assertTrue("No se han obtenido resultados", asientos.size() > 0);
	}

	@Test
	public void testGetHistoricoAsientoRegistral() {
		List<TrazabilidadVO> trazas = getServicioIntercambioRegistral().getHistoricoAsientoRegistral(ID);
		Assert.assertNotNull(trazas);
		Assert.assertEquals(3, trazas.size());
	}

	@Test
	public void testGetEstado() {

		EstadoAsientoRegistraVO estado = getServicioIntercambioRegistral().getEstadoAsientoRegistral(ID);

		Assert.assertNotNull(estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO, estado.getEstado());
	}

	@Test
	public void testGetAsientoRegistral() {

		AsientoRegistralVO asientoVO = getServicioIntercambioRegistral().getAsientoRegistral(ID);

		AsientoRegistralDTO asientoDTO = TestUtils.createAsientoRegistralDTO(ID);
		asientoDTO.setFechaRegistro(DateUtils.toXMLGregorianCalendar(asientoVO.getFechaRegistro()));
		asientoDTO.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(asientoVO.getFechaRegistroInicial()));
		TestUtils.assertEquals(asientoDTO, asientoVO);
	}

	@Test
	public void testSaveAsientoRegistral() {
		AsientoRegistralFormVO asiento = TestUtils.createAsientoRegistralFormVO(ID);
		AsientoRegistralVO asientoCreado = getServicioIntercambioRegistral().saveAsientoRegistral(asiento);
		TestUtils.assertEquals(asiento, asientoCreado);
	}

	@Test
	public void testUpdateAsientoRegistral() {
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().getAsientoRegistral(ID);
		asiento.setObservacionesApunte("Observaciones modificadas");
		getServicioIntercambioRegistral().updateAsientoRegistral(asiento);
	}

	@Test
	public void testDeleteAsientoRegistral() {
		getServicioIntercambioRegistral().deleteAsientoRegistral(ID);
	}

	@Test
	public void testAddAnexo() {
		AnexoFormVO anexo = TestUtils.createAnexoFormVO(ID);
		AnexoVO anexoCreado = getServicioIntercambioRegistral().addAnexo(ID, anexo);
		TestUtils.assertEquals(anexo, anexoCreado);
	}

	@Test
	public void testUpdateAnexo() {
		AnexoVO anexo = TestUtils.createAnexoVO(ID, ID);
		anexo.setObservaciones("observaciones modificadas");
		AnexoVO anexoModificado = getServicioIntercambioRegistral().updateAnexo(anexo);
		TestUtils.assertEquals(anexo, anexoModificado);
	}

	@Test
	public void testRemoveAnexo() {
		getServicioIntercambioRegistral().removeAnexo(ID);
	}

	@Test
	public void testAddInteresado() {
		InteresadoFormVO interesado = TestUtils.createInteresadoFormVO(ID);
		InteresadoVO interesadoCreado = getServicioIntercambioRegistral().addInteresado(ID, interesado);
		TestUtils.assertEquals(interesado, interesadoCreado);
	}

	@Test
	public void testUpdateInteresado() {
		InteresadoVO interesado = TestUtils.createInteresadoVO(ID, ID);
		interesado.setObservaciones("Observaciones modificadas");
		InteresadoVO interesadoModificado = getServicioIntercambioRegistral().updateInteresado(interesado);
		TestUtils.assertEquals(interesado, interesadoModificado);
	}

	@Test
	public void testRemoveInteresado() {
		getServicioIntercambioRegistral().removeInteresado(ID);
	}

	@Test
	public void testGetContenidoAnexo() {

		byte[] contenido = getServicioIntercambioRegistral().getContenidoAnexo(ID);

		Assert.assertNotNull(contenido);
		Assert.assertEquals(Base64.encodeBase64String("contenido".getBytes()), Base64.encodeBase64String(contenido));
	}

	@Test
	public void testUpdateContenidoAnexo() {
		getServicioIntercambioRegistral().setContenidoAnexo(ID, "contenido".getBytes());
	}

	@Test
	public void testEnviarAsientoRegistral() {
		AsientoRegistralFormVO asientoForm = TestUtils.createAsientoRegistralFormVO(ID);
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);
		TestUtils.assertEquals(asientoForm, asiento);
	}

	@Test
	public void testEnviarAsientoRegistralById() {
		getServicioIntercambioRegistral().enviarAsientoRegistral(ID);
	}

	@Test
	public void testValidarAsientoRegistral() {
		getServicioIntercambioRegistral().validarAsientoRegistral(ID, "2010", new Date());
	}

	@Test
	public void testReenviarAsientoRegistral() {
		getServicioIntercambioRegistral().reenviarAsientoRegistral(ID);
	}

	@Test
	public void testRechazarAsientoRegistral() {
		getServicioIntercambioRegistral()
				.rechazarAsientoRegistral(
						ID,
						TestUtils
								.createInfoRechazoVO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
	}

	@Test
	public void testAnularAsientoRegistral() {
		getServicioIntercambioRegistral().anularAsientoRegistral(ID);
	}

	@Test
	public void testRecibirAsientoRegistral() {
		AsientoRegistralVO asientoRecibido = getServicioIntercambioRegistral().recibirFicheroIntercambio("<xml/>", null);

		AsientoRegistralDTO asientoDTO = TestUtils.createAsientoRegistralDTO(ID);
		asientoDTO.setFechaRegistro(DateUtils.toXMLGregorianCalendar(asientoRecibido.getFechaRegistro()));
		asientoDTO.setFechaRegistroInicial(DateUtils.toXMLGregorianCalendar(asientoRecibido.getFechaRegistroInicial()));
		TestUtils.assertEquals(asientoDTO, asientoRecibido);
	}

	@Test
	public void testRecibirMensaje() {
		getServicioIntercambioRegistral().recibirMensaje("<xml/>");
	}

	@Test
	public void testProcesarFicherosRecibidos() {
		getServicioIntercambioRegistral().procesarFicherosRecibidos();
	}

	@Test
	public void testComprobarTimeOutEnvios() {
		getServicioIntercambioRegistral().comprobarTimeOutEnvios();
	}

//	@Test
//	public void testValidarAnexos() {
//		List<ValidacionAnexoVO> validaciones = getServicioIntercambioRegistral().validarAnexos(ID);
//		TestUtils.assertEquals(LISTA_VALIDACIONES_ANEXO_DTO, validaciones);
//	}
}
