package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

@ContextConfiguration({ "/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralWSTest extends AbstractWSTest { 

	private static final ThreadLocal<String> asientoThreadLocal = new ThreadLocal<String>();

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
	}

	@Test
	public void testSaveAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = intercambioRegistralWSClient.saveAsientoRegistral(asientoForm);

		Assert.assertNotNull("Asiento registral nulo", asiento);
		Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
		asientoThreadLocal.set(asiento.getId());
	}

	@Test
	public void testGetAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue(), asiento.getEstado());
	}

	@Test
	public void testUpdateAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		InfoBAsientoRegistralDTO infoBAsiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		infoBAsiento.setObservacionesApunte("Observaciones al apunte modificadas");

		intercambioRegistralWSClient.updateAsientoRegistral(infoBAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(infoBAsiento.getObservacionesApunte(), asiento.getObservacionesApunte());
	}

	@Test
	public void testGetEstadoAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		EstadoAsientoRegistralDTO estado = intercambioRegistralWSClient.getEstadoAsientoRegistral(idAsiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue(), estado.getEstado());
	}

	@Test
	public void testCountAsientosRegistrales() {

		Assert.assertTrue("No se ha encontrado ningún asiento registral",
				intercambioRegistralWSClient.countAsientosRegistrales(null) > 0);
	}

	@Test
	public void testFindAsientosRegistrales() {

		CriterioDTO criterio = new CriterioDTO();
		criterio.setNombre(CriterioEnum.ASIENTO_ESTADO.getValue());
		criterio.setOperador(OperadorCriterioEnum.EQUAL.getValue());
		criterio.getValor().add(0);

		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setMaxNumItems(100);

		CriteriosDTO criterios = new CriteriosDTO();
		criterios.getCriterios().add(criterio);
		criterios.setPageInfo(pageInfo);

		List<AsientoRegistralDTO> asientos = intercambioRegistralWSClient.findAsientosRegistrales(criterios);

		Assert.assertNotNull("No se ha encontrado ningún asiento registral", asientos);
		Assert.assertTrue("No se ha encontrado ningún asiento registral", asientos.size() > 0);
	}

	@Test
	public void testAddInteresado() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

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

		InteresadoDTO interesadoCreado = intercambioRegistralWSClient.addInteresado(idAsiento, interesado);

		Assert.assertNotNull("No se ha creado el interesado", interesadoCreado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoCreado.getId()));
		Assert.assertTrue("El número de interesados del asiento no es correcto",
				intercambioRegistralWSClient.getAsientoRegistral(idAsiento).getInteresados().size() == 2);
	}

	@Test
	public void testUpdateInteresado() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<InteresadoDTO> interesados = asiento.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 2);

		InteresadoDTO interesado = interesados.get(1);
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoDTO interesadoModificado = intercambioRegistralWSClient.updateInteresado(interesado);
		Assert.assertNotNull("No se ha modificado el interesado", interesadoModificado);
		Assert.assertTrue("El identificador del interesado es nulo",
				StringUtils.isNotBlank(interesadoModificado.getId()));
		Assert.assertEquals(interesado.getObservaciones(), interesadoModificado.getObservaciones());
	}

	@Test
	public void testRemoveInteresado() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<InteresadoDTO> interesados = asiento.getInteresados();
		Assert.assertTrue("El número de interesados del asiento no es correcto", interesados.size() == 2);

		InteresadoDTO interesado = interesados.get(1);

		intercambioRegistralWSClient.removeInteresado(interesado.getId());

		Assert.assertTrue("El número de interesados del asiento no es correcto",
				intercambioRegistralWSClient.getAsientoRegistral(idAsiento).getInteresados().size() == 1);
	}

	@Test
	public void testAddAnexo() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

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

		AnexoDTO anexoCreado = intercambioRegistralWSClient.addAnexo(idAsiento, anexo);

		Assert.assertNotNull("No se ha creado el anexo", anexoCreado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoCreado.getId()));
		Assert.assertTrue("El número de anexos del asiento no es correcto",
				intercambioRegistralWSClient.getAsientoRegistral(idAsiento).getAnexos().size() == 4);
	}

	@Test
	public void testUpdateAnexo() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 4);

		AnexoDTO anexo = anexos.get(3);
		anexo.setObservaciones("Observaciones modificadas");

		AnexoDTO anexoModificado = intercambioRegistralWSClient.updateAnexo(anexo);
		Assert.assertNotNull("No se ha modificado el anexo", anexoModificado);
		Assert.assertTrue("El identificador del anexo es nulo",
				StringUtils.isNotBlank(anexoModificado.getId()));
		Assert.assertEquals(anexo.getObservaciones(), anexoModificado.getObservaciones());
	}

	@Test
	public void testGetContenidoAnexo() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 4);

		AnexoDTO anexo = anexos.get(3);

		byte[] contenido = intercambioRegistralWSClient.getContenidoAnexo(anexo.getId());

		Assert.assertNotNull("No se ha encontrado el contenido del anexo", contenido);
		Assert.assertEquals(Base64.encodeBase64String("Contenido del fichero3".getBytes()), Base64.encodeBase64String(contenido));
	}

	@Test
	public void testSetContenidoAnexo() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 4);

		AnexoDTO anexo = anexos.get(3);

		byte[] contenido = "Contenido del fichero3".getBytes();
		intercambioRegistralWSClient.setContenidoAnexo(anexo.getId(), contenido);

		byte[] contenidoCreado = intercambioRegistralWSClient.getContenidoAnexo(anexo.getId());
		Assert.assertEquals(Base64.encodeBase64String(contenido), Base64.encodeBase64String(contenidoCreado));
	}

	@Test
	public void testRemoveAnexo() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		List<AnexoDTO> anexos = asiento.getAnexos();
		Assert.assertTrue("El número de anexos del asiento no es correcto", anexos.size() == 4);

		AnexoDTO anexo = anexos.get(3);

		intercambioRegistralWSClient.removeAnexo(anexo.getId());

		Assert.assertTrue("El número de anexos del asiento no es correcto",
				intercambioRegistralWSClient.getAsientoRegistral(idAsiento).getAnexos().size() == 3);
	}

	@Test
	public void testGetHistoricoAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		List<TrazabilidadDTO> trazas = intercambioRegistralWSClient.getHistoricoAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);
	}

	@Test
	public void testEnviarAsientoRegistral() {

		AsientoRegistralFormDTO asientoForm = new AsientoRegistralFormDTO();

		asientoForm.setCodigoEntidadRegistral("ER0000000000000000001");
		
		asientoForm.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
		asientoForm.setDescripcionEntidadRegistralOrigen("Entidad Registral ER0000000000000000001");
		asientoForm.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
		asientoForm.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");

		asientoForm.setCodigoEntidadRegistralDestino("ER0000000000000000002");
		asientoForm.setDescripcionEntidadRegistralDestino("Entidad Registral ER0000000000000000002");
		asientoForm.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
		asientoForm.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");

		asientoForm.setCodigoEntidadRegistralInicio(asientoForm.getCodigoEntidadRegistralOrigen());
		asientoForm.setDescripcionEntidadRegistralInicio(asientoForm.getDescripcionEntidadRegistralOrigen());
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
		asientoForm.setNumeroRegistro("201100100000001");
		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
		asientoForm.setTimestampRegistro("***timestamp***".getBytes());

		asientoForm.setResumen("Resumen");
		asientoForm.setCodigoAsunto("ASUNTO0000000001");
		asientoForm.setReferenciaExterna("REF0000000000001");
		asientoForm.setNumeroExpediente("EXP2011/00001");
		asientoForm.setTipoTransporte(TipoTransporteEnum.SERVICIO_MENSAJEROS.getValue());
		asientoForm.setNumeroTransporte("99999");
		asientoForm.setNombreUsuario("usuario");
		asientoForm.setContactoUsuario("usuario@contacto.es");
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA.getValue());
		asientoForm.setObservacionesApunte("observaciones");
		asientoForm.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());
		asientoForm.setExpone("expone");
		asientoForm.setSolicita("solicita");

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));

		intercambioRegistralWSClient.deleteAsientoRegistral(asiento.getId());
	}

	@Test
	public void testEnviarAsientoRegistralById() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		intercambioRegistralWSClient.enviarAsientoRegistralById(idAsiento);

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.getValue() == asiento.getEstado())
				|| (EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado()));
	}

	@Test
	public void testRechazarAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		try {

			// Rechazar el asiento
			intercambioRegistralWSClient
					.rechazarAsientoRegistral(
							idAsiento,
							TestUtils
									.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));

		} catch (Exception e) {

			// Modificar el estado del asiento
			AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
			asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
			intercambioRegistralWSClient.updateAsientoRegistral(asiento);

			// Rechazar el asiento
			intercambioRegistralWSClient
					.rechazarAsientoRegistral(
							idAsiento,
							TestUtils
									.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
		}

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testValidarAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		try {

			// Validar el asiento
			intercambioRegistralWSClient.validarAsientoRegistral(idAsiento,
					"201100100000001", DatatypeFactory.newInstance()
							.newXMLGregorianCalendar(new GregorianCalendar()));

		} catch (Exception e) {

			// Modificar el estado del asiento
			AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
			asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO.getValue());
			intercambioRegistralWSClient.updateAsientoRegistral(asiento);

			// Validar el asiento
			intercambioRegistralWSClient.validarAsientoRegistral(idAsiento,
					"201100100000001", DateUtils.toXMLGregorianCalendar(new Date()));
		}

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testAnularAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		try {

			// Anular el asiento
			intercambioRegistralWSClient.anularAsientoRegistral(idAsiento);

		} catch (Exception e) {

			// Modificar el estado del asiento
			AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
			asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
			intercambioRegistralWSClient.updateAsientoRegistral(asiento);

			// Anular el asiento
			intercambioRegistralWSClient.anularAsientoRegistral(idAsiento);
		}

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.ANULADO.getValue(), asiento.getEstado());
	}

	@Test
	public void testReenviarAsientoRegistral() {

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		try {

			// Reenviar asiento
			intercambioRegistralWSClient.reenviarAsientoRegistralById(idAsiento);

		} catch (Exception e) {

			// Modificar el estado del asiento
			AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
			asiento.setEstado(EstadoAsientoRegistralEnum.DEVUELTO.getValue());
			intercambioRegistralWSClient.updateAsientoRegistral(asiento);

			// Reenviar asiento
			intercambioRegistralWSClient.reenviarAsientoRegistralById(idAsiento);
		}

		AsientoRegistralDTO asiento = intercambioRegistralWSClient.getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO.getValue() == asiento.getEstado()));
	}

//	@Test
//	public void testValidarAnexos() {
//
//		String idAsiento = asientoThreadLocal.get();
//		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);
//
//		List<ValidacionAnexoDTO> validaciones = intercambioRegistralWSClient.validarAnexos(idAsiento);
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

		String idAsiento = asientoThreadLocal.get();
		Assert.assertNotNull("No se ha encontrado el identificador del asiento registral creado", idAsiento);

		intercambioRegistralWSClient.deleteAsientoRegistral(idAsiento);
	}

}
