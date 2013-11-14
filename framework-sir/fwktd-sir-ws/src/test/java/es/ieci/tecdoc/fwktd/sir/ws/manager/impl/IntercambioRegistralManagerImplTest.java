package es.ieci.tecdoc.fwktd.sir.ws.manager.impl;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriterioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.PageInfoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-api-test-initial-beans.xml",
	"/beans/fwktd-sir-ws-applicationContext.xml",
	"classpath*:/beans/fwktd-sir-applicationContext.xml",
	"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-api-test-beans.xml" })
public class IntercambioRegistralManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String IDENTIFICADOR_INTERCAMBIO = "ER0000000000000000001_99_10000001";

	@Autowired
	private IntercambioRegistralManagerImpl fwktd_sir_ws_intercambioRegistralManagerImpl;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;


	protected IntercambioRegistralManagerImpl getIntercambioRegistralManager() {
		return fwktd_sir_ws_intercambioRegistralManagerImpl;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getIntercambioRegistralManager());
	}

	@Test
	public void testSaveAsientoRegistral() {

		AsientoRegistralDTO asiento = null;

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		asiento = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		Assert.assertNotNull("Asiento registral nulo", asiento);

		TestUtils.assertEquals(asientoForm, asiento);
	}


	@Test
	public void testCountAsientosRegistrales() {

		// Sin criterios
		Assert.assertTrue("No se ha encontrado ningún asiento registral",
				getIntercambioRegistralManager().countAsientosRegistrales(null) > 0);

		// Con criterios
		CriterioDTO criterio = new CriterioDTO();
		criterio.setNombre(CriterioEnum.ASIENTO_ESTADO.getValue());
		criterio.setOperador(OperadorCriterioEnum.EQUAL.getValue());
		criterio.getValor().add(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue());

		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setMaxNumItems(100);

		CriteriosDTO criterios = new CriteriosDTO();
		criterios.getCriterios().add(criterio);
		criterios.setPageInfo(pageInfo);

		Assert.assertTrue("No se ha encontrado ningún asiento registral",
				getIntercambioRegistralManager().countAsientosRegistrales(criterios) > 0);
	}

	@Test
	public void testFindAsientosRegistrales() {

		// Sin criterios
		List<AsientoRegistralDTO> asientos = getIntercambioRegistralManager().findAsientosRegistrales(null);
		Assert.assertNotNull("No se ha encontrado ningún asiento registral", asientos);
		Assert.assertTrue("No se ha encontrado ningún asiento registral",
				asientos.size() > 0);

		// Con criterios
		CriterioDTO criterio = new CriterioDTO();
		criterio.setNombre(CriterioEnum.ASIENTO_ESTADO.getValue());
		criterio.setOperador(OperadorCriterioEnum.EQUAL.getValue());
		criterio.getValor().add(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue());

		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setMaxNumItems(100);

		CriteriosDTO criterios = new CriteriosDTO();
		criterios.getCriterios().add(criterio);
		criterios.setPageInfo(pageInfo);

		asientos = getIntercambioRegistralManager().findAsientosRegistrales(criterios);

		Assert.assertNotNull("No se ha encontrado ningún asiento registral", asientos);
		Assert.assertTrue("No se ha encontrado ningún asiento registral", asientos.size() > 0);
	}

	@Test
	public void testGetAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		asientoDTO = getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asientoDTO);

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		asientoFormDTO.setFechaRegistro(asientoDTO.getFechaRegistro());
		asientoFormDTO.setFechaRegistroInicial(asientoDTO.getFechaRegistroInicial());
		TestUtils.assertEquals(asientoFormDTO, asientoDTO);
	}

	@Test
	public void testUpdateAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		asientoDTO.setObservacionesApunte("Observaciones al apunte modificadas");

		getIntercambioRegistralManager().updateAsientoRegistral(asientoDTO);

		AsientoRegistralDTO asientoDTOModificado = getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asientoDTOModificado);
		Assert.assertEquals(asientoDTO.getObservacionesApunte(), asientoDTOModificado.getObservacionesApunte());
	}

	@Test
	public void testGetEstadoAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		EstadoAsientoRegistralDTO estado = getIntercambioRegistralManager().getEstadoAsientoRegistral(asientoDTO.getId());
		Assert.assertNotNull(estado);
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue(), estado.getEstado());
	}

	@Test
	public void testAddInteresado() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		InteresadoFormDTO interesado = new InteresadoFormDTO();
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("00000000T");
		interesado.setRazonSocialInteresado("");
		interesado.setNombreInteresado("nombreInteresado");
		interesado.setPrimerApellidoInteresado("primerApellidoInteresado");
		interesado.setSegundoApellidoInteresado("segundoApellidoInteresado");
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado");
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
		interesado.setTelefonoInteresado("666666666");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu_repr");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setObservaciones("observaciones");

		InteresadoDTO interesadoCreado = getIntercambioRegistralManager().addInteresado(asientoDTO.getId(), interesado);

		Assert.assertNotNull("No se ha creado el interesado", interesadoCreado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoCreado.getId()));
		Assert.assertTrue("El número de interesados del asiento no es correcto",
				getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId()).getInteresados().size() == 2);
	}

	@Test
	public void testUpdateInteresado() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<InteresadoDTO> interesados = asientoDTO.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 1);

		InteresadoDTO interesado = interesados.get(0);
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoDTO interesadoModificado = getIntercambioRegistralManager().updateInteresado(interesado);
		Assert.assertNotNull("No se ha modificado el interesado", interesadoModificado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoModificado.getId()));
		Assert.assertEquals(interesado.getObservaciones(), interesadoModificado.getObservaciones());
	}

	@Test
	public void testRemoveInteresado() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<InteresadoDTO> interesados = asientoDTO.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 1);

		InteresadoDTO interesado = interesados.get(0);

		getIntercambioRegistralManager().removeInteresado(interesado.getId());

		Assert.assertTrue("El número de interesados del asiento no es correcto",
				getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId()).getInteresados().size() == 0);
	}

	@Test
	public void testAddAnexo() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		AnexoFormDTO anexo = new AnexoFormDTO();
	    anexo.setNombreFichero("fichero3.txt");
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
	    anexo.setTipoMIME("plain/text");
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_ORIGINAL.getValue());
	    anexo.setCertificado("***certificador***".getBytes());
	    anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
	    anexo.setFirma("***firma***".getBytes());
	    anexo.setIdentificadorFicheroFirmado(null);
	    anexo.setObservaciones("Observaciones");
	    anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setContenido("Contenido del fichero3".getBytes());

		AnexoDTO anexoCreado = getIntercambioRegistralManager().addAnexo(asientoDTO.getId(), anexo);

		Assert.assertNotNull("No se ha creado el anexo", anexoCreado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoCreado.getId()));
		Assert.assertTrue("El número de anexos del asiento no es correcto",
				getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId()).getAnexos().size() == 4);
	}

	@Test
	public void testUpdateAnexo() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<AnexoDTO> anexos = asientoDTO.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);
		anexo.setObservaciones("Observaciones modificadas");

		AnexoDTO anexoModificado = getIntercambioRegistralManager().updateAnexo(anexo);
		Assert.assertNotNull("No se ha modificado el anexo", anexoModificado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoModificado.getId()));
		Assert.assertEquals(anexo.getObservaciones(), anexoModificado.getObservaciones());
	}

	@Test
	public void testGetContenidoAnexo() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<AnexoDTO> anexos = asientoDTO.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		byte[] contenido = getIntercambioRegistralManager().getContenidoAnexo(anexo.getId());

		Assert.assertNotNull("No se ha encontrado el contenido del anexo", contenido);
		Assert.assertEquals(Base64.encodeBase64String("Contenido del anexo".getBytes()), Base64.encodeBase64String(contenido));
	}

	@Test
	public void testSetContenidoAnexo() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<AnexoDTO> anexos = asientoDTO.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		byte[] contenido = "Contenido del fichero3".getBytes();
		getIntercambioRegistralManager().setContenidoAnexo(anexo.getId(), contenido);

		byte[] contenidoCreado = getIntercambioRegistralManager().getContenidoAnexo(anexo.getId());
		Assert.assertEquals(Base64.encodeBase64String(contenido), Base64.encodeBase64String(contenidoCreado));
	}

	@Test
	public void testRemoveAnexo() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
		List<AnexoDTO> anexos = asientoDTO.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		getIntercambioRegistralManager().removeAnexo(anexo.getId());

		Assert.assertTrue("El número de anexos del asiento no es correcto",
				getIntercambioRegistralManager().getAsientoRegistral(asientoDTO.getId()).getAnexos().size() == 2);
	}
//
//	@Test
//	public void testGetHistoricoAsientoRegistral() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		List<TrazabilidadDTO> trazas = getIntercambioRegistralManager().getHistoricoAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);
//	}
//
//	@Test
//	public void testEnviarAsientoRegistral() {
//
//		AsientoRegistralFormDTO asientoForm = new AsientoRegistralFormDTO();
//
//		asientoForm.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
//		asientoForm.setDescripcionEntidadRegistralOrigen("Entidad Registral ER0000000000000000001");
//		asientoForm.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
//		asientoForm.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");
//
//		asientoForm.setCodigoEntidadRegistralDestino("ER0000000000000000002");
//		asientoForm.setDescripcionEntidadRegistralDestino("Entidad Registral ER0000000000000000002");
//		asientoForm.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
//		asientoForm.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");
//
//		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
//		asientoForm.setNumeroRegistro("201100100000001");
//		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
//		asientoForm.setTimestampRegistro("***timestamp***".getBytes());
//
//		asientoForm.setResumen("Resumen");
//		asientoForm.setCodigoAsunto("ASUNTO0000000001");
//		asientoForm.setReferenciaExterna("REF0000000000001");
//		asientoForm.setNumeroExpediente("EXP2011/00001");
//		asientoForm.setTipoTransporte(TipoTransporteEnum.SERVICIO_MENSAJEROS.getValue());
//		asientoForm.setNumeroTransporte("99999");
//		asientoForm.setNombreUsuario("usuario");
//		asientoForm.setContactoUsuario("usuario@contacto.es");
//		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA.getValue());
//		asientoForm.setObservacionesApunte("observaciones");
//		asientoForm.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());
//		asientoForm.setExpone("expone");
//		asientoForm.setSolicita("solicita");
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().enviarAsientoRegistral(asientoForm);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
//				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));
//
//		getIntercambioRegistralManager().deleteAsientoRegistral(asiento.getId());
//	}
//
//	@Test
//	public void testEnviarAsientoRegistralById() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		getIntercambioRegistralManager().enviarAsientoRegistralById(idAsiento);
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
//				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));
//	}
//
//	@Test
//	public void testRechazarAsientoRegistral() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		try {
//
//			// Rechazar el asiento
//			getIntercambioRegistralManager().rechazarAsientoRegistral(idAsiento, TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(), "Motivos de rechazo");
//
//		} catch (Exception e) {
//
//			// Modificar el estado del asiento
//			AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//			asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
//			getIntercambioRegistralManager().updateAsientoRegistral(asiento);
//
//			// Rechazar el asiento
//			getIntercambioRegistralManager().rechazarAsientoRegistral(idAsiento, TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(), "Motivos de rechazo");
//		}
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), asiento.getEstado());
//	}
//
//	@Test
//	public void testValidarAsientoRegistral() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		try {
//
//			// Validar el asiento
//			getIntercambioRegistralManager().validarAsientoRegistral(idAsiento,
//					"201100100000001", DatatypeFactory.newInstance()
//							.newXMLGregorianCalendar(new GregorianCalendar()));
//
//		} catch (Exception e) {
//
//			// Modificar el estado del asiento
//			AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//			asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
//			getIntercambioRegistralManager().updateAsientoRegistral(asiento);
//
//			// Validar el asiento
//			getIntercambioRegistralManager().validarAsientoRegistral(idAsiento,
//					"201100100000001", DateUtils.toXMLGregorianCalendar(new Date()));
//		}
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), asiento.getEstado());
//	}
//
//	@Test
//	public void testAnularAsientoRegistral() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		try {
//
//			// Anular el asiento
//			getIntercambioRegistralManager().anularAsientoRegistral(idAsiento);
//
//		} catch (Exception e) {
//
//			// Modificar el estado del asiento
//			AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//			asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
//			getIntercambioRegistralManager().updateAsientoRegistral(asiento);
//
//			// Anular el asiento
//			getIntercambioRegistralManager().anularAsientoRegistral(idAsiento);
//		}
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertEquals(EstadoAsientoRegistralEnum.ANULADO.getValue(), asiento.getEstado());
//	}
//
//	@Test
//	public void testReenviarAsientoRegistral() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		try {
//
//			// Reenviar asiento
//			getIntercambioRegistralManager().reenviarAsientoRegistral(idAsiento);
//
//		} catch (Exception e) {
//
//			// Modificar el estado del asiento
//			AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//			asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
//			getIntercambioRegistralManager().updateAsientoRegistral(asiento);
//
//			// Reenviar asiento
//			getIntercambioRegistralManager().reenviarAsientoRegistral(idAsiento);
//		}
//
//		AsientoRegistralDTO asiento = getIntercambioRegistralManager().getAsientoRegistral(idAsiento);
//		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
//		Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO.getValue() == asiento.getEstado()));
//	}

//	@Test
//	public void testValidarAnexos() {
//
//		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
//		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);
//
//		List<ValidacionAnexoDTO> validaciones = getIntercambioRegistralManager().validarAnexos(asientoDTO.getId());
//		Assert.assertNotNull("Listado de validaciones nulo", validaciones);
//		Assert.assertTrue("El número de validaciones no es correcto", validaciones.size() == 3);
//
//		for (ValidacionAnexoDTO validacionAnexo : validaciones) {
//			Assert.assertNotNull("Validación nula", validacionAnexo);
//			Assert.assertNotNull("No se ha encontrado el anexo", validacionAnexo.getAnexo());
//			Assert.assertTrue("El hash no es válido", validacionAnexo.isHashValidado());
//			Assert.assertTrue("El certificado no es válido", ValidacionCertificadoEnum.CERTIFICADO_EXPIRADO.getValue() == validacionAnexo.getValidacionCertificado());
//			Assert.assertTrue("Validación OCSP del certificado no es válida", validacionAnexo.isValidacionOCSPCertificado());
//			Assert.assertTrue("La firma no es válida", ValidacionFirmaEnum.FIRMA_VALIDA.getValue() == validacionAnexo.getValidacionFirma());
//		}
//	}

	@Test
	public void testDeleteAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralManager().saveAsientoRegistral(asientoForm);

		getIntercambioRegistralManager().deleteAsientoRegistral(asientoDTO.getId());
	}

	@Test
	public void testRecibirMensaje() {

		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

        // Construir el XML del mensaje
		String mensaje = TestUtils.createXMLMensaje(asiento.getIdentificadorIntercambio(), TipoMensajeEnum.ACK);

		// Recibir el mensaje
		getIntercambioRegistralManager().recibirMensaje(mensaje);
	}

	@Test
	public void testRecibirFicheroIntercambio() {

        // Construir el XML del mensaje
		String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, true);

		// Enviar el fichero de intercambio
		AsientoRegistralDTO asiento = getIntercambioRegistralManager().recibirFicheroIntercambio(ficheroIntercambio, null);
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECIBIDO.getValue(), asiento.getEstado());
	}

	@Test
	public void testProcesarFicherosRecibidos() {
		getIntercambioRegistralManager().procesarFicherosRecibidos();
	}

	@Test
	public void testComprobarTimeOutEnvios() {
		getIntercambioRegistralManager().comprobarTimeOutEnvios();
	}
}
