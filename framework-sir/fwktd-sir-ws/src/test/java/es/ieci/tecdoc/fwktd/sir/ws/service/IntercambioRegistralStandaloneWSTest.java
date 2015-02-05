package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.axis.attachments.OctetStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Mensaje;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Indicador_PruebaType;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.WS_SIR8_A_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8b.WS_SIR8_B_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.WS_SIR9_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

@ContextConfiguration({
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralStandaloneWSTest extends AbstractWSTest {

	private static final Logger logger = LoggerFactory.getLogger(IntercambioRegistralStandaloneWSTest.class);
	
	@Autowired
	private WS_SIR8_A_PortType WSSIR8AWSClient;

	@Autowired
	private WS_SIR8_B_PortType WSSIR8BWSClient;

	@Autowired
	private WS_SIR9_PortType WSSIR9WSClient;

	@Autowired
	private AsientoRegistralManager fwktd_sir_asientoRegistralManager;

	@Autowired
	private SicresXMLManager fwktd_sir_sicresXMLManager;

	protected WS_SIR8_A_PortType getWSSIR8A() {
		return WSSIR8AWSClient;
	}

	protected WS_SIR8_B_PortType getWSSIR8B() {
		return WSSIR8BWSClient;
	}

	protected WS_SIR9_PortType getWSSIR9() {
		return WSSIR9WSClient;
	}

	protected AsientoRegistralManager getAsientoRegistralManager() {
		return fwktd_sir_asientoRegistralManager;
	}

	protected SicresXMLManager getSicresXMLManager() {
		return fwktd_sir_sicresXMLManager;
	}

	
	@Test
	public void testEnvio() throws Exception {
		
		logger.info("Caso de prueba de envío:\n1.-Envío (fwktd-sir -> O1)\n2.-ACK (O1 -> fwktd-sir)\n3.-Confirmación (O1 -> fwktd-sir)");

		AsientoRegistralDTO asiento = null;
		
		try {
			
			// Enviar asiento registral
			logger.info("Enviando asiento registral (fwktd-sir -> O1)...");
			AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTO();
			asiento = getIntercambioRegistralWS().enviarAsientoRegistral(asientoForm);
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado());
			TestUtils.assertEquals(asientoForm, asiento);
			logger.info("Asiento registral enviado");
			
			// ACK
			logger.info("Enviando ACK a través del WS_SIR8_A (O1 -> fwktd-sir)...");
			enviarMensaje(CODIGO_ENTIDAD_REGISTRAL_1, CODIGO_ENTIDAD_REGISTRAL,
					asiento.getIdentificadorIntercambio(), TipoMensajeEnum.ACK,
					TipoMensajeEnum.ACK.getName(), null, null, null);
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.ENVIADO_Y_ACK.getValue() == asiento.getEstado()));
			logger.info("ACK recibido");
			
			// Confirmación
			logger.info("Enviando confirmación a través del WS_SIR8_A (O1 -> fwktd-sir)...");
			enviarMensaje(CODIGO_ENTIDAD_REGISTRAL_1, CODIGO_ENTIDAD_REGISTRAL,
					asiento.getIdentificadorIntercambio(), TipoMensajeEnum.CONFIRMACION,
					TipoMensajeEnum.ACK.getName(), "2012999000001", new Date(), null);
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.ACEPTADO.getValue(), asiento.getEstado());
			logger.info("Confirmación recibida");

			// Ver histórico
			List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(asiento.getId());
			Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);
			logger.info("Trazabilidad: {}", trazas);

		} finally {
			
			// Eliminar el asiento registral
			if (asiento != null) {
				logger.info("Eliminando asiento registral...");
				getIntercambioRegistralWS().deleteAsientoRegistral(asiento.getId());
				logger.info("Asiento registral eliminado");
			}
		}
	}

	@Test
	public void testConfirmacion() throws Exception {
		
		logger.info("Caso de prueba de confirmación:\n1.-Envío (O1 -> fwktd-sir)\n2.-ACK (fwktd-sir -> O1)\n3.-Confirmación (fwktd-sir -> O1)");
		
		AsientoRegistralDTO asiento = null;
		
		try {

			// Recibir asiento registral
			logger.info("Recibiendo asiento registral (O1 -> fwktd-sir)...");
			asiento = recibirAsientoRegistral(TipoAnotacionEnum.ENVIO, "");
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.RECIBIDO.getValue() == asiento.getEstado());
			logger.info("Asiento registral recibido (se ha enviado automáticamente el ACK: fwktd-sir -> O1)");

			// Validar el asiento registral
			logger.info("Enviando confirmación (fwktd-sir -> O1)...");
			getIntercambioRegistralWS().validarAsientoRegistral(
					asiento.getId(), "2012999000001",
					DateUtils.toXMLGregorianCalendar(new Date()));
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), asiento.getEstado());
			logger.info("Confirmación enviada");

			// Ver histórico
			List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(asiento.getId());
			Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);

		} finally {
			
			// Eliminar el asiento registral
			if (asiento != null) {
				logger.info("Eliminando asiento registral...");
				getIntercambioRegistralWS().deleteAsientoRegistral(asiento.getId());
				logger.info("Asiento registral eliminado");
			}
		}
	}

	@Test
	public void testConfirmacion2() throws Exception {
		
		logger.info("Caso de prueba de confirmación:\n1.-Envío (O1 -> O2)\n2.-ACK (O2 -> O1)\n3.-Reenvío (O2 -> fwktd-sir)\n4.-ACK (fwktd-sir -> O2)\n5.-Confirmación (fwktd-sir -> O1)");
		
		AsientoRegistralDTO asiento = null;
		
		try {

			// Recibir asiento registral
			logger.info("Recibiendo reenvío de asiento registral (O2 -> fwktd-sir)...");
			asiento = recibirAsientoRegistral(TipoAnotacionEnum.REENVIO, "Motivo del reenvío",
					CODIGO_ENTIDAD_REGISTRAL_2, DESCRIPCION_ENTIDAD_REGISTRAL_2, CODIGO_UNIDAD_TRAMITACION_2, DESCRIPCION_UNIDAD_TRAMITACION_2, 
					CODIGO_ENTIDAD_REGISTRAL, DESCRIPCION_ENTIDAD_REGISTRAL, CODIGO_UNIDAD_TRAMITACION, DESCRIPCION_UNIDAD_TRAMITACION, 
					CODIGO_ENTIDAD_REGISTRAL_1, DESCRIPCION_ENTIDAD_REGISTRAL_1);
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.RECIBIDO.getValue() == asiento.getEstado());
			logger.info("Asiento registral recibido (se ha enviado automáticamente el ACK: fwktd-sir -> O2)");

			// Validar el asiento registral
			logger.info("Enviando confirmación (fwktd-sir -> O1)...");
			getIntercambioRegistralWS().validarAsientoRegistral(
					asiento.getId(), "2012999000001",
					DateUtils.toXMLGregorianCalendar(new Date()));
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), asiento.getEstado());
			logger.info("Confirmación enviada");

			// Ver histórico
			List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(asiento.getId());
			Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);

		} finally {
			
			// Eliminar el asiento registral
			if (asiento != null) {
				logger.info("Eliminando asiento registral...");
				getIntercambioRegistralWS().deleteAsientoRegistral(asiento.getId());
				logger.info("Asiento registral eliminado");
			}
		}
	}

	@Test
	public void testEnviarReenviarYAnularAsientoRegistral() throws Exception {

		AsientoRegistralDTO asiento = null;
		
		try {
			
			// Enviar asiento registral
			AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTO();
			asiento = getIntercambioRegistralWS().enviarAsientoRegistral(asientoForm);
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado());
			TestUtils.assertEquals(asientoForm, asiento);
			
			// Enviar ACK al origen
			enviarMensaje(CODIGO_ENTIDAD_REGISTRAL_1, CODIGO_ENTIDAD_REGISTRAL,
					asiento.getIdentificadorIntercambio(), TipoMensajeEnum.ACK,
					TipoMensajeEnum.ACK.getName(), null, null, null);
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.ENVIADO_Y_ACK.getValue() == asiento.getEstado()));
			
			// Rechazar el asiento enviado
			rechazarAsientoRegistral(asiento.getId());
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.DEVUELTO.getValue() == asiento.getEstado()));

			// Reenviar asiento
			getIntercambioRegistralWS().reenviarAsientoRegistralById(asiento.getId());
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO.getValue() == asiento.getEstado()));

			// Enviar ACK al origen
			enviarMensaje(CODIGO_ENTIDAD_REGISTRAL_1, CODIGO_ENTIDAD_REGISTRAL,
					asiento.getIdentificadorIntercambio(), TipoMensajeEnum.ACK,
					TipoMensajeEnum.ACK.getName(), null, null, null);
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.REENVIADO_Y_ACK.getValue() == asiento.getEstado()));
			
			// Rechazar el asiento reenviado
			rechazarAsientoRegistral(asiento.getId());
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertTrue((EstadoAsientoRegistralEnum.DEVUELTO.getValue() == asiento.getEstado()));

			// Anular el asiento
			getIntercambioRegistralWS().anularAsientoRegistral(asiento.getId());
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.ANULADO.getValue(), asiento.getEstado());

			// Ver histórico
			List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(asiento.getId());
			Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);

		} finally {
			
			// Eliminar el asiento registral
			if (asiento != null) {
				getIntercambioRegistralWS().deleteAsientoRegistral(asiento.getId());
			}
		}
	}


	@Test
	public void testRecibirYRechazarAsientoRegistral() throws Exception {
		
		AsientoRegistralDTO asiento = null;
		
		try {

			// Recibir asiento registral
			asiento = recibirAsientoRegistral(TipoAnotacionEnum.ENVIO, "");
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.RECIBIDO.getValue() == asiento.getEstado());

			// Rechazar el asiento
			getIntercambioRegistralWS()
					.rechazarAsientoRegistral(
							asiento.getId(),
							TestUtils
									.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), asiento.getEstado());

			// Recibir asiento registral
			asiento = recibirAsientoRegistral(TipoAnotacionEnum.REENVIO, "Motivo del reenvío");
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
			Assert.assertTrue(EstadoAsientoRegistralEnum.RECIBIDO.getValue() == asiento.getEstado());

			// Rechazar el asiento
			getIntercambioRegistralWS()
					.rechazarAsientoRegistral(
							asiento.getId(),
							TestUtils
									.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
			asiento = getIntercambioRegistralWS().getAsientoRegistral(asiento.getId());
			Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), asiento.getEstado());

			// Ver histórico
			List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(asiento.getId());
			Assert.assertNotNull("No se han encontrado las trazas del asiento registral", trazas);

		} finally {
			
			// Eliminar el asiento registral
			if (asiento != null) {
				getIntercambioRegistralWS().deleteAsientoRegistral(asiento.getId());
			}
		}

	}

	protected void rechazarAsientoRegistral(String idAsiento) throws Exception {

		// Obtener información del asiento registral
		AsientoRegistralDTO asiento = getIntercambioRegistralWS().getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		
        String codigoEntidadRegistral = asiento.getCodigoEntidadRegistralOrigen();
        String descEntidadRegistral = asiento.getDescripcionEntidadRegistralOrigen();
        String codigoUnidadTramitacion = asiento.getCodigoUnidadTramitacionOrigen();
        String descUnidadTramitacion = asiento.getDescripcionUnidadTramitacionOrigen();

        // Actualizar la información del asiento registral
        asiento.setCodigoEntidadRegistralOrigen(asiento.getCodigoEntidadRegistralDestino());
        asiento.setDescripcionEntidadRegistralOrigen(asiento.getDescripcionEntidadRegistralDestino());
        asiento.setCodigoUnidadTramitacionOrigen(asiento.getCodigoUnidadTramitacionDestino());
        asiento.setDescripcionUnidadTramitacionOrigen(asiento.getDescripcionUnidadTramitacionDestino());

        asiento.setCodigoEntidadRegistralDestino(codigoEntidadRegistral);
        asiento.setDescripcionEntidadRegistralDestino(descEntidadRegistral);
        asiento.setCodigoUnidadTramitacionDestino(codigoUnidadTramitacion);
        asiento.setDescripcionUnidadTramitacionDestino(descUnidadTramitacion);

        asiento.setObservacionesApunte("Asiento rechazado");
        
        asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());

		// Fichero de intercambio
        AsientoRegistralVO ar = TestUtils.getAsientoRegistralVO(asiento);
        ar.setTipoAnotacion(TipoAnotacionEnum.RECHAZO);
        ar.setDescripcionTipoAnotacion("Motivo del rechazo");
        
		String registro = getSicresXMLManager().createXMLFicheroIntercambio(ar,
				false);
		
		// Documentos
		List<OctetStream> documentos = new ArrayList<OctetStream>();
		if (!CollectionUtils.isEmpty(asiento.getAnexos())) {
			for (AnexoDTO anexo : asiento.getAnexos()) {
				documentos.add(new OctetStream(getIntercambioRegistralWS().getContenidoAnexo(anexo.getId())));
			}
		}
		
		// Rechazar asiento
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta = getWSSIR8A()
				.envioFicherosAAplicacion(
						registro,
						"",
						(OctetStream[]) documentos
								.toArray(new OctetStream[documentos.size()]));
		
		Assert.assertNotNull("No se ha obtenido respuesta", respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
		
		// Comprobar el estado del asiento
		asiento = getIntercambioRegistralWS().getAsientoRegistral(idAsiento);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.DEVUELTO.getValue(), asiento.getEstado());
	}

	protected AsientoRegistralDTO recibirAsientoRegistral(
			TipoAnotacionEnum tipoAnotacion, String descripcionTipoAnotacion)
			throws Exception {
		return recibirAsientoRegistral(tipoAnotacion, descripcionTipoAnotacion,
				CODIGO_ENTIDAD_REGISTRAL_1, DESCRIPCION_ENTIDAD_REGISTRAL_1,
				CODIGO_UNIDAD_TRAMITACION_1, DESCRIPCION_UNIDAD_TRAMITACION_1,
				CODIGO_ENTIDAD_REGISTRAL, DESCRIPCION_ENTIDAD_REGISTRAL,
				CODIGO_UNIDAD_TRAMITACION, DESCRIPCION_UNIDAD_TRAMITACION,
				CODIGO_ENTIDAD_REGISTRAL_1, DESCRIPCION_ENTIDAD_REGISTRAL_1);
	}
	
	protected AsientoRegistralDTO recibirAsientoRegistral(
			TipoAnotacionEnum tipoAnotacion, String descripcionTipoAnotacion,
			String codEROrigen, String descEROrigen, String codUTOrigen,
			String descUTOrigen, String codERDestino, String descERDestino,
			String codUTDestino, String descUTDestino, String codERInicial,
			String descERInicial) throws Exception {

		// Fichero de intercambio
		String registro = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<Fichero_Intercambio_SICRES_3>")
			
			.append("<De_Origen_o_Remitente>")
			.append("<Codigo_Entidad_Registral_Origen>").append(codEROrigen).append("</Codigo_Entidad_Registral_Origen>")
			.append("<Decodificacion_Entidad_Registral_Origen><![CDATA[").append(descEROrigen).append("]]></Decodificacion_Entidad_Registral_Origen>")
			.append("<Numero_Registro_Entrada>201200100000001</Numero_Registro_Entrada>")
			.append("<Fecha_Hora_Entrada>20120306124754</Fecha_Hora_Entrada>")
			.append("<Codigo_Unidad_Tramitacion_Origen>").append(codUTOrigen).append("</Codigo_Unidad_Tramitacion_Origen>")
			.append("<Decodificacion_Unidad_Tramitacion_Origen><![CDATA[").append(descUTOrigen).append("]]></Decodificacion_Unidad_Tramitacion_Origen>")
			.append("</De_Origen_o_Remitente>")
			
			.append("<De_Destino>")
			.append("<Codigo_Entidad_Registral_Destino>").append(codERDestino).append("</Codigo_Entidad_Registral_Destino>")
			.append("<Decodificacion_Entidad_Registral_Destino>").append(descERDestino).append("</Decodificacion_Entidad_Registral_Destino>")
			.append("<Codigo_Unidad_Tramitacion_Destino>").append(codUTDestino).append("</Codigo_Unidad_Tramitacion_Destino>")
			.append("<Decodificacion_Unidad_Tramitacion_Destino>").append(descUTDestino).append("</Decodificacion_Unidad_Tramitacion_Destino>")
			.append("</De_Destino>")
			
			.append("<De_Interesado>")
			.append("<Tipo_Documento_Identificacion_Interesado>C</Tipo_Documento_Identificacion_Interesado>")
			.append("<Documento_Identificacion_Interesado>A28855260</Documento_Identificacion_Interesado>")
			.append("<Razon_Social_Interesado>INFORMÁTICA EL CORTE INGLÉS, S.A.</Razon_Social_Interesado>")
			.append("<Tipo_Documento_Identificacion_Representante>N</Tipo_Documento_Identificacion_Representante>")
			.append("<Documento_Identificacion_Representante>00000000T</Documento_Identificacion_Representante>")
			.append("<Nombre_Representante>Isidoro</Nombre_Representante>")
			.append("<Primer_Apellido_Representante>Álvarez</Primer_Apellido_Representante>")
			.append("<Segundo_Apellido_Representante>Álvarez</Segundo_Apellido_Representante>")
			.append("<Pais_Interesado>724</Pais_Interesado>")
			.append("<Provincia_Interesado>28</Provincia_Interesado>")
			.append("<Municipio_Interesado>0796</Municipio_Interesado>")
			.append("<Direccion_Interesado>Travesía de Costa Brava nº4</Direccion_Interesado>")
			.append("<Codigo_Postal_Interesado>28034</Codigo_Postal_Interesado>")
			.append("<Correo_Electronico_Interesado>mkt@ieci.es</Correo_Electronico_Interesado>")
			.append("<Telefono_Contacto_Interesado>913874700</Telefono_Contacto_Interesado>")
			.append("<Canal_Preferente_Comunicacion_Interesado>01</Canal_Preferente_Comunicacion_Interesado>")
			.append("<Pais_Representante>724</Pais_Representante>")
			.append("<Provincia_Representante>28</Provincia_Representante>")
			.append("<Municipio_Representante>0796</Municipio_Representante>")
			.append("<Direccion_Representante>Hermosilla, 112</Direccion_Representante>")
			.append("<Codigo_Postal_Representante>28009</Codigo_Postal_Representante>")
			.append("<Correo_Electronico_Representante>servicio_clientes@elcorteingles.es</Correo_Electronico_Representante>")
			.append("<Telefono_Contacto_Representante>901122122</Telefono_Contacto_Representante>")
			.append("<Canal_Preferente_Comunicacion_Representante>01</Canal_Preferente_Comunicacion_Representante>")
			.append("</De_Interesado>")
			
			.append("<De_Asunto>")
			.append("<Resumen><![CDATA[Resumen del registro]]></Resumen>")
			.append("<Codigo_Asunto_Segun_Destino>ASUNTO0000000001</Codigo_Asunto_Segun_Destino>")
			.append("<Referencia_Externa><![CDATA[REF0000000000001]]></Referencia_Externa>")
			.append("<Numero_Expediente><![CDATA[EXP2012/00001]]></Numero_Expediente>")
			.append("</De_Asunto>")
			
			.append("<De_Anexo>")
			.append("<Nombre_Fichero_Anexado>Fichero_1.txt</Nombre_Fichero_Anexado>")
			.append("<Identificador_Fichero>").append(CODIGO_ENTIDAD_REGISTRAL_1).append("_12_10000001_01_0001.txt</Identificador_Fichero>")
			.append("<Validez_Documento>01</Validez_Documento>")
			.append("<Tipo_Documento>02</Tipo_Documento>")
			.append("<Certificado>LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K</Certificado>")
			.append("<Firma_Documento>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</Firma_Documento>")
			.append("<Validacion_OCSP_Certificado>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</Validacion_OCSP_Certificado>")
			.append("<Hash>MzLopkxAoo7LRwZCrlDB3mkepbI=</Hash>")
			.append("<Tipo_MIME>text/plain</Tipo_MIME>")
			.append("<Anexo>Q29udGVuaWRvIGRlbCBhbmV4bw==</Anexo>")
			.append("</De_Anexo>")
			
			.append("<De_Internos_Control>")
			.append("<Tipo_Transporte_Entrada>01</Tipo_Transporte_Entrada>")
			.append("<Numero_Transporte_Entrada>99999</Numero_Transporte_Entrada>")
			.append("<Nombre_Usuario>sigm</Nombre_Usuario>")
			.append("<Contacto_Usuario><![CDATA[sigm@alsigm.es]]></Contacto_Usuario>")
			.append("<Identificador_Intercambio>").append(CODIGO_ENTIDAD_REGISTRAL_1).append("_12_10000001</Identificador_Intercambio>")
			.append("<Tipo_Anotacion>").append(tipoAnotacion.getValue()).append("</Tipo_Anotacion>")
			.append("<Descripcion_Tipo_Anotacion><![CDATA[").append(descripcionTipoAnotacion).append("]]></Descripcion_Tipo_Anotacion>")
			.append("<Tipo_Registro>0</Tipo_Registro>")
			.append("<Documentacion_Fisica>2</Documentacion_Fisica>")
			.append("<Observaciones_Apunte><![CDATA[TEST de asiento recibido]]></Observaciones_Apunte>")
			.append("<Indicador_Prueba>1</Indicador_Prueba>")
			.append("<Codigo_Entidad_Registral_Inicio>").append(codERInicial).append("</Codigo_Entidad_Registral_Inicio>")
			.append("<Decodificacion_Entidad_Registral_Inicio><![CDATA[").append(descERInicial).append("]]></Decodificacion_Entidad_Registral_Inicio>")
			.append("</De_Internos_Control>")
			
			.append("<De_Formulario_Generico>")
			.append("<Expone><![CDATA[Texto de EXPONE]]></Expone>")
			.append("<Solicita><![CDATA[Texto de SOLICITA]]></Solicita>")
			.append("</De_Formulario_Generico>")
			.append("</Fichero_Intercambio_SICRES_3>")
			.toString();
		
		// Recibir asiento
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8b.RespuestaWS respuesta = getWSSIR8B().envioFicherosAAplicacion(registro, "");
		
		Assert.assertNotNull("No se ha obtenido respuesta", respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
		
		// Obtener el asiento registral recibido
		List<AsientoRegistralDTO> asientos = getIntercambioRegistralWS()
				.findAsientosRegistrales(createCriteriosDTO(new CriterioDTO[] {
						createCriterioDTO(
								CriterioEnum.ASIENTO_ESTADO,
								OperadorCriterioEnum.EQUAL,
								EstadoAsientoRegistralEnum.RECIBIDO.getValue()),						
						createCriterioDTO(
								CriterioEnum.ASIENTO_OBSERVACIONES_APUNTE,
								OperadorCriterioEnum.EQUAL,
								"TEST de asiento recibido") }, null));
		Assert.assertNotNull("No se han encontrado asientos registrales", asientos);
		Assert.assertTrue("No se han encontrado asientos registrales", asientos.size() > 0);
		
		return asientos.get(0);
	}
	
	protected void enviarMensaje(String codigoEntidadRegistralOrigen,
			String codigoEntidadRegistralDestino,
			String identificadorIntercambio, TipoMensajeEnum tipoMensaje,
			String descripcionMensaje, String numeroRegistroEntradaDestino,
			Date fechaEntradaDestino, String codigoError) throws Exception {
		
		StringWriter stringWriter = new StringWriter();
    	De_Mensaje msg = new De_Mensaje();
    	msg.setCodigo_Entidad_Registral_Origen(codigoEntidadRegistralOrigen);
    	msg.setCodigo_Entidad_Registral_Destino(codigoEntidadRegistralDestino);
    	msg.setIdentificador_Intercambio(identificadorIntercambio);
    	msg.setDescripcion_Mensaje(descripcionMensaje);
    	
    	if (numeroRegistroEntradaDestino != null) {
    		msg.setNumero_Registro_Entrada_Destino(numeroRegistroEntradaDestino);
    	}
    	
    	msg.setCodigo_Error(codigoError);

        // Fecha y hora de entrada en destino
        if (fechaEntradaDestino != null) {
            msg.setFecha_Hora_Entrada_Destino(SDF.format(fechaEntradaDestino));
        }

        msg.setTipo_Mensaje(tipoMensaje.getValue());

        msg.setIndicador_Prueba(Indicador_PruebaType.fromValue(IndicadorPruebaEnum.PRUEBA.getValue()));
        msg.marshal(stringWriter);

		es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.RespuestaWS respuesta = getWSSIR9()
				.envioMensajeDatosControlAAplicacion(stringWriter.toString(), "");
		
		Assert.assertNotNull("No se ha obtenido respuesta", respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());
	}
	
}
