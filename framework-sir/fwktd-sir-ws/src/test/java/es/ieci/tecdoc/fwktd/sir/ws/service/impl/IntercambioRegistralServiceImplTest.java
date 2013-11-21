package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

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
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
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
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.PageInfoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-sir-api-test-initial-beans.xml",
		"/beans/fwktd-sir-ws-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-applicationContext.xml",
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-api-test-beans.xml" })
public class IntercambioRegistralServiceImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	private static final String CODIGO_ENTIDAD_REGISTRAL = "ER0000000000000000001";
	private static final String IDENTIFICADOR_INTERCAMBIO = "ER0000000000000000001_99_10000001";

	@Autowired
	private IntercambioRegistralServiceImpl fwktd_sir_ws_intercambioRegistralServiceImpl;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;


	protected IntercambioRegistralServiceImpl getIntercambioRegistralService() {
		return fwktd_sir_ws_intercambioRegistralServiceImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getIntercambioRegistralService());
	}

	@Test
	public void testSaveAsientoRegistral() {

		AsientoRegistralDTO asiento = null;

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoForm);

		Assert.assertNotNull("Asiento registral nulo", asiento);

		TestUtils.assertEquals(asientoForm, asiento);
	}


	@Test
	public void testCountAsientosRegistrales() {

		// Sin criterios
		Assert.assertTrue("No se ha encontrado ningún asiento registral",
				getIntercambioRegistralService().countAsientosRegistrales(null) > 0);

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
				getIntercambioRegistralService().countAsientosRegistrales(criterios) > 0);
	}

	@Test
	public void testFindAsientosRegistrales() {

		// Sin criterios
		List<AsientoRegistralDTO> asientos = getIntercambioRegistralService().findAsientosRegistrales(null);
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

		asientos = getIntercambioRegistralService().findAsientosRegistrales(criterios);

		Assert.assertNotNull("No se ha encontrado ningún asiento registral", asientos);
		Assert.assertTrue("No se ha encontrado ningún asiento registral", asientos.size() > 0);
	}

	@Test
	public void testGetAsientoRegistral() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);

		asientoDTO = getIntercambioRegistralService().getAsientoRegistral(asientoDTO.getId());

		Assert.assertNotNull("No se ha encontrado el asiento registral", asientoDTO);
		TestUtils.assertEquals(asientoFormDTO, asientoDTO);
	}

	@Test
	public void testUpdateAsientoRegistral() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		asientoDTO.setObservacionesApunte("Observaciones al apunte modificadas");

		getIntercambioRegistralService().updateAsientoRegistral(asientoDTO);

		AsientoRegistralDTO asientoDTOModificado = getIntercambioRegistralService().getAsientoRegistral(asientoDTO.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asientoDTOModificado);
		Assert.assertEquals(asientoDTO.getObservacionesApunte(), asientoDTOModificado.getObservacionesApunte());
	}

	@Test
	public void testGetEstadoAsientoRegistral() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);

		EstadoAsientoRegistralDTO estado = getIntercambioRegistralService().getEstadoAsientoRegistral(asientoDTO.getId());
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue(), estado.getEstado());
	}



	@Test
	public void testAddInteresado() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asientoDTO = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		int numInteresados = asientoDTO.getInteresados().size();

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

		InteresadoDTO interesadoCreado = getIntercambioRegistralService().addInteresado(asientoDTO.getId(), interesado);

		Assert.assertNotNull("No se ha creado el interesado", interesadoCreado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoCreado.getId()));
		Assert.assertTrue("El número de interesados del asiento no es correcto",
				getIntercambioRegistralService().getAsientoRegistral(asientoDTO.getId()).getInteresados().size() == (numInteresados + 1));
	}

	@Test
	public void testUpdateInteresado() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<InteresadoDTO> interesados = asiento.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 1);

		InteresadoDTO interesado = interesados.get(0);
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoDTO interesadoModificado = getIntercambioRegistralService().updateInteresado(interesado);
		Assert.assertNotNull("No se ha modificado el interesado", interesadoModificado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoModificado.getId()));
		Assert.assertEquals(interesado.getObservaciones(), interesadoModificado.getObservaciones());
	}

	@Test
	public void testRemoveInteresado() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<InteresadoDTO> interesados = asiento.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 1);

		InteresadoDTO interesado = interesados.get(0);

		getIntercambioRegistralService().removeInteresado(interesado.getId());

		Assert.assertTrue("El número de interesados del asiento no es correcto",
				getIntercambioRegistralService().getAsientoRegistral(asiento.getId()).getInteresados().size() == 0);
	}

	@Test
	public void testAddAnexo() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		int numAnexos = asiento.getAnexos().size();

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

		AnexoDTO anexoCreado = getIntercambioRegistralService().addAnexo(asiento.getId(), anexo);

		Assert.assertNotNull("No se ha creado el anexo", anexoCreado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoCreado.getId()));
		Assert.assertTrue("El número de anexos del asiento no es correcto",
				getIntercambioRegistralService().getAsientoRegistral(asiento.getId()).getAnexos().size() == (numAnexos + 1));
	}

	@Test
	public void testUpdateAnexo() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);
		anexo.setObservaciones("Observaciones modificadas");

		AnexoDTO anexoModificado = getIntercambioRegistralService().updateAnexo(anexo);
		Assert.assertNotNull("No se ha modificado el anexo", anexoModificado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoModificado.getId()));
		Assert.assertEquals(anexo.getObservaciones(), anexoModificado.getObservaciones());
	}

	@Test
	public void testGetContenidoAnexo() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		byte[] contenido = getIntercambioRegistralService().getContenidoAnexo(anexo.getId());

		Assert.assertNotNull("No se ha encontrado el contenido del anexo", contenido);
		Assert.assertEquals(Base64.encodeBase64String("Contenido del anexo".getBytes()), Base64.encodeBase64String(contenido));
	}

	@Test
	public void testSetContenidoAnexo() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		byte[] contenido = "Contenido del fichero3".getBytes();
		getIntercambioRegistralService().setContenidoAnexo(anexo.getId(), contenido);

		byte[] contenidoCreado = getIntercambioRegistralService().getContenidoAnexo(anexo.getId());
		Assert.assertEquals(Base64.encodeBase64String(contenido), Base64.encodeBase64String(contenidoCreado));
	}

	@Test
	public void testRemoveAnexo() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 3);

		AnexoDTO anexo = anexos.get(0);

		getIntercambioRegistralService().removeAnexo(anexo.getId());

		Assert.assertTrue("El número de anexos del asiento no es correcto",
				getIntercambioRegistralService().getAsientoRegistral(asiento.getId()).getAnexos().size() == 2);
	}

	@Test
	public void testGetHistoricoAsientoRegistral() {

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoFormDTO);

		List<TrazabilidadDTO> trazas = getIntercambioRegistralService().getHistoricoAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);
	}

	@Test
	public void testEnviarAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));
	}

	@Test
	public void testEnviarAsientoRegistralById() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoForm);

		getIntercambioRegistralService().enviarAsientoRegistralById(asiento.getId());

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));
	}

	@Test
	public void testRechazarAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoForm);

		// Modificar el estado del asiento
		asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
		getIntercambioRegistralService().updateAsientoRegistral(asiento);

		// Rechazar el asiento
		getIntercambioRegistralService()
				.rechazarAsientoRegistral(
						asiento.getId(),
						TestUtils
								.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testValidarAsientoRegistral() throws DatatypeConfigurationException {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoForm);

		// Modificar el estado del asiento
		asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
		getIntercambioRegistralService().updateAsientoRegistral(asiento);

		// Validar el asiento
		getIntercambioRegistralService().validarAsientoRegistral(asiento.getId(),
				"201100100000001", DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(new GregorianCalendar()));

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testAnularAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoForm);

		// Modificar el estado del asiento
		asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
		getIntercambioRegistralService().updateAsientoRegistral(asiento);

		// Anular el asiento
		getIntercambioRegistralService().anularAsientoRegistral(asiento.getId());

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.ANULADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testReenviarAsientoRegistralById() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoForm);

		// Modificar el estado del asiento
		asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
		getIntercambioRegistralService().updateAsientoRegistral(asiento);

		// Reenviar asiento
		getIntercambioRegistralService().reenviarAsientoRegistralById(asiento.getId());

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO.getValue() == asiento.getEstado()));
	}

	@Test
	public void testReenviarAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().enviarAsientoRegistral(asientoForm);

		// Modificar el estado del asiento
		asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
		getIntercambioRegistralService().updateAsientoRegistral(asiento);

		InfoReenvioDTO infoReenvio = new InfoReenvioDTO();
		infoReenvio.setCodigoEntidadRegistralDestino(asiento.getCodigoEntidadRegistralDestino());
		infoReenvio.setDescripcionEntidadRegistralDestino(asiento.getDescripcionEntidadRegistralDestino());
		infoReenvio.setAplicacion("app1");
		infoReenvio.setContacto("contacto");
		infoReenvio.setUsuario("usuario");
		infoReenvio.setDescripcion("Motivo del reenvío");
			
		// Reenviar asiento
		getIntercambioRegistralService().reenviarAsientoRegistral(asiento.getId(), infoReenvio);

		asiento = getIntercambioRegistralService().getAsientoRegistral(asiento.getId());
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO.getValue() == asiento.getEstado()));
	}

//	@Test
//	public void testValidarAnexos() {
//
//		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
//		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);
//
//		List<ValidacionAnexoDTO> validaciones = getIntercambioRegistralService().validarAnexos(asiento.getId());
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

		AsientoRegistralFormDTO asientoFormDTO = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = getIntercambioRegistralService().saveAsientoRegistral(asientoFormDTO);

		getIntercambioRegistralService().deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testRecibirMensaje() {

		// Crear el asiento registral
		AsientoRegistralFormVO asientoForm = TestUtils.createDefaultAsientoRegistralFormVO();
		AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.saveAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);

		try {

			// Establecer el asiento registral como enviado
			asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
			asiento.setFechaEstado(new Date());
	        asiento.setFechaEnvio(new Date());
			asiento.setIdentificadorIntercambio(IDENTIFICADOR_INTERCAMBIO);
	        asiento.setNumeroReintentos(asiento.getNumeroReintentos() + 1);
	        asiento = fwktd_sir_asientoRegistralManager.update(asiento);

	        // Construir el XML del mensaje
			String mensaje = TestUtils.createXMLMensaje(IDENTIFICADOR_INTERCAMBIO, TipoMensajeEnum.ACK);

			// Recibir el mensaje
			getIntercambioRegistralService().recibirMensaje(mensaje);

		} finally {

			// Eliminar el asiento creado (con sus documentos)
			fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
		}
	}

	@Test
	public void testRecibirFicheroIntercambio() {

		try {

	        // Construir el XML del mensaje
			String ficheroIntercambio = TestUtils.createXMLFicheroIntercambio(IDENTIFICADOR_INTERCAMBIO, TipoAnotacionEnum.ENVIO, true);

			// Enviar el mensaje
			getIntercambioRegistralService().recibirFicheroIntercambio(ficheroIntercambio, null);

		} finally {

			// Eliminar el asiento creado (con sus documentos)
			AsientoRegistralVO asiento = fwktd_sir_asientoRegistralManager.getAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, IDENTIFICADOR_INTERCAMBIO);
			if (asiento != null) {
				fwktd_sir_asientoRegistralManager.deleteAsientoRegistral(asiento.getId());
			}
		}
	}

	@Test
	public void testProcesarFicherosRecibidos() {
		getIntercambioRegistralService().procesarFicherosRecibidos();
	}

	@Test
	public void testComprobarTimeOutEnvios() {
		getIntercambioRegistralService().comprobarTimeOutEnvios();
	}
}
