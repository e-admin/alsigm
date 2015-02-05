package es.ieci.tecdoc.fwktd.sir.ws.service.wssir8b;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.ws.service.AbstractWSTest;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;

@ContextConfiguration({ "/beans/fwktd-sir-ws-test-beans.xml" })
public class WSSIR8BWSTest extends AbstractWSTest {

	private static final String CODIGO_ENTIDAD_REGISTRAL = "TESTER000000000000002";
	
	@Autowired
	private WS_SIR8_B_PortType WSSIR8BWSClient;

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
		Assert.assertNotNull(WSSIR8BWSClient);
	}

	@Test
	public void testEnvioFicheroEnvioConDocumentos() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		RespuestaWS respuesta = WSSIR8BWSClient.envioFicherosAAplicacion(
				getRegistro(identificadorIntercambio, TipoAnotacionEnum.ENVIO, null,
						true), Base64.encodeBase64String("firma".getBytes()));

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);
	}

	@Test
	public void testEnvioFicheroReenvioConDocumentos() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		RespuestaWS respuesta = WSSIR8BWSClient.envioFicherosAAplicacion(
				getRegistro(identificadorIntercambio,
						TipoAnotacionEnum.REENVIO, "Motivo del reenvío", true),
				Base64.encodeBase64String("firma".getBytes()));

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);
	}

	@Test
	public void testEnvioFicheroRechazoConDocumentos() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		// Crear asiento
		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = intercambioRegistralWSClient.saveAsientoRegistral(asientoForm);

		// Actualizar el estado del registro
		asiento.setIdentificadorIntercambio(identificadorIntercambio);
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO.getValue());
		intercambioRegistralWSClient.updateAsientoRegistral(asiento);

		// Enviar fichero
		RespuestaWS respuesta = WSSIR8BWSClient.envioFicherosAAplicacion(
				getRegistro(identificadorIntercambio,
						TipoAnotacionEnum.RECHAZO, "Motivo del rechazo", true),
				Base64.encodeBase64String("firma".getBytes()));

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);
	}

	@Test
	public void testEnvioFicherosError() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		RespuestaWS respuesta = WSSIR8BWSClient.envioFicherosAAplicacion(
				getRegistro(identificadorIntercambio,
						TipoAnotacionEnum.RECHAZO, "Motivo del rechazo", true), 
						Base64.encodeBase64String("firma".getBytes()));

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.ERROR_0063.getValue(), respuesta.getCodigo());
	}

	private String getRegistro(String identificadorIntercambio,
			TipoAnotacionEnum tipoAnotacion, String descripcionTipoAnotacion,
			boolean attached) {
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<Fichero_Intercambio_SICRES_3>"
				+ "<De_Origen_o_Remitente>"
				+ "<Codigo_Entidad_Registral_Origen>TESTER000000000000002</Codigo_Entidad_Registral_Origen>"
				+ "<Decodificacion_Entidad_Registral_Origen><![CDATA[Descripcin de TESTER000000000000002]]></Decodificacion_Entidad_Registral_Origen>"
				+ "<Numero_Registro_Entrada>201000100000001</Numero_Registro_Entrada>"
				+ "<Fecha_Hora_Entrada>20110107121346</Fecha_Hora_Entrada>"
				+ "<Codigo_Unidad_Tramitacion_Origen>UT0000000000000000002</Codigo_Unidad_Tramitacion_Origen>"
				+ "<Decodificacion_Unidad_Tramitacion_Origen><![CDATA[Descripcin de UT0000000000000000002]]></Decodificacion_Unidad_Tramitacion_Origen>"
				+ "</De_Origen_o_Remitente>"
				+ "<De_Destino>"
				+ "<Codigo_Entidad_Registral_Destino>TESTER000000000000001</Codigo_Entidad_Registral_Destino>"
				+ "<Decodificacion_Entidad_Registral_Destino>Descripcin de TESTER000000000000001</Decodificacion_Entidad_Registral_Destino>"
				+ "<Codigo_Unidad_Tramitacion_Destino>UT0000000000000000001</Codigo_Unidad_Tramitacion_Destino>"
				+ "<Decodificacion_Unidad_Tramitacion_Destino>Descripcin de UT0000000000000000001</Decodificacion_Unidad_Tramitacion_Destino>"
				+ "</De_Destino>"
				+ "<De_Interesado>"
				+ "<Tipo_Documento_Identificacion_Interesado>C</Tipo_Documento_Identificacion_Interesado>"
				+ "<Documento_Identificacion_Interesado>A28855260</Documento_Identificacion_Interesado>"
				+ "<Razon_Social_Interesado>razonSocialInteresado</Razon_Social_Interesado>"
				+ "<Tipo_Documento_Identificacion_Representante>N</Tipo_Documento_Identificacion_Representante>"
				+ "<Documento_Identificacion_Representante>00000000T</Documento_Identificacion_Representante>"
				+ "<Nombre_Representante>nombreRepresentante</Nombre_Representante>"
				+ "<Primer_Apellido_Representante>primerApellidoRepresentante</Primer_Apellido_Representante>"
				+ "<Segundo_Apellido_Representante>segundoApellidoRepresentante</Segundo_Apellido_Representante>"
				+ "<Pais_Interesado>0001</Pais_Interesado>"
				+ "<Provincia_Interesado>05</Provincia_Interesado>"
				+ "<Municipio_Interesado>01544</Municipio_Interesado>"
				+ "<Direccion_Interesado>direccionInteresado</Direccion_Interesado>"
				+ "<Codigo_Postal_Interesado>33004</Codigo_Postal_Interesado>"
				+ "<Correo_Electronico_Interesado>correoElectronico@interesado.es</Correo_Electronico_Interesado>"
				+ "<Telefono_Contacto_Interesado>999999999</Telefono_Contacto_Interesado>"
				+ "<Direccion_Electronica_Habilitada_Interesado>deu</Direccion_Electronica_Habilitada_Interesado>"
				+ "<Canal_Preferente_Comunicacion_Interesado>01</Canal_Preferente_Comunicacion_Interesado>"
				+ "<Pais_Representante>0001</Pais_Representante>"
				+ "<Provincia_Representante>05</Provincia_Representante>"
				+ "<Municipio_Representante>01544</Municipio_Representante>"
				+ "<Direccion_Representante>direccionRepresentante</Direccion_Representante>"
				+ "<Codigo_Postal_Representante>33004</Codigo_Postal_Representante>"
				+ "<Correo_Electronico_Representante>correoElectronico@representante.es</Correo_Electronico_Representante>"
				+ "<Telefono_Contacto_Representante>666666666</Telefono_Contacto_Representante>"
				+ "<Direccion_Electronica_Habilitada_Representante>deu_repr</Direccion_Electronica_Habilitada_Representante>"
				+ "<Canal_Preferente_Comunicacion_Representante>02</Canal_Preferente_Comunicacion_Representante>"
				+ "<Observaciones><![CDATA[observaciones]]></Observaciones>"
				+ "</De_Interesado>"
				+ "<De_Asunto>"
				+ "<Resumen><![CDATA[Resumen]]></Resumen>"
				+ "<Codigo_Asunto_Segun_Destino>ASUNTO0000000001</Codigo_Asunto_Segun_Destino>"
				+ "<Referencia_Externa><![CDATA[REF0000000000001]]></Referencia_Externa>"
				+ "<Numero_Expediente><![CDATA[EXP2010/000001]]></Numero_Expediente>"
				+ "</De_Asunto>"
				+ "<De_Anexo>"
				+ "<Nombre_Fichero_Anexado>fichero1.txt</Nombre_Fichero_Anexado>"
				+ "<Identificador_Fichero>" + identificadorIntercambio + "_01_0002.txt</Identificador_Fichero>"
				+ "<Validez_Documento>02</Validez_Documento>"
				+ "<Tipo_Documento>03</Tipo_Documento>"
				+ "<Certificado></Certificado>"
				+ "<Firma_Documento></Firma_Documento>"
				+ "<TimeStamp></TimeStamp>"
				+ "<Validacion_OCSP_Certificado></Validacion_OCSP_Certificado>"
				+ "<Hash>" + getHash("Contenido del documento 1".getBytes()) + "</Hash>"
				+ "<Tipo_MIME>text/plain</Tipo_MIME>"
				+ "<Anexo>"
				+ (attached ? Base64.encodeBase64String("Contenido del documento 1".getBytes()) : "")
				+ "</Anexo>"
				+ "<Identificador_Documento_Firmado></Identificador_Documento_Firmado>"
				+ "<Observaciones><![CDATA[Observaciones]]></Observaciones>"
				+ "</De_Anexo>"
				+ "<De_Anexo>"
				+ "<Nombre_Fichero_Anexado>fichero2.txt</Nombre_Fichero_Anexado>"
				+ "<Identificador_Fichero>" + identificadorIntercambio + "_01_0003.txt</Identificador_Fichero>"
				+ "<Validez_Documento>02</Validez_Documento>"
				+ "<Tipo_Documento>03</Tipo_Documento>"
				+ "<Certificado></Certificado>"
				+ "<Firma_Documento></Firma_Documento>"
				+ "<TimeStamp></TimeStamp>"
				+ "<Validacion_OCSP_Certificado></Validacion_OCSP_Certificado>"
				+ "<Hash>" + getHash("Contenido del documento 2".getBytes()) + "</Hash>"
				+ "<Tipo_MIME>text/plain</Tipo_MIME>"
				+ "<Anexo>"
				+ (attached ? Base64.encodeBase64String("Contenido del documento 2".getBytes()) : "")
				+ "</Anexo>"
				+ "<Identificador_Documento_Firmado>" + identificadorIntercambio + "_01_0002.txt</Identificador_Documento_Firmado>"
				+ "<Observaciones><![CDATA[Observaciones]]></Observaciones>"
				+ "</De_Anexo>"
				+ "<De_Internos_Control>"
				+ "<Tipo_Transporte_Entrada>03</Tipo_Transporte_Entrada>"
				+ "<Numero_Transporte_Entrada>00000000000000000001</Numero_Transporte_Entrada>"
				+ "<Nombre_Usuario>usuario</Nombre_Usuario>"
				+ "<Contacto_Usuario><![CDATA[contactousuario]]></Contacto_Usuario>"
				+ "<Identificador_Intercambio>" + identificadorIntercambio + "</Identificador_Intercambio>"
				+ "<Aplicacion_Version_Emisora>ieci</Aplicacion_Version_Emisora>"
				+ "<Tipo_Anotacion>" + tipoAnotacion.getValue() + "</Tipo_Anotacion>"
				+ "<Descripcion_Tipo_Anotacion><![CDATA[" + StringUtils.defaultIfEmpty(descripcionTipoAnotacion, "")+ "]]></Descripcion_Tipo_Anotacion>"
				+ "<Tipo_Registro>0</Tipo_Registro>"
				+ "<Documentacion_Fisica>3</Documentacion_Fisica>"
				+ "<Observaciones_Apunte><![CDATA[observacionesApunte]]></Observaciones_Apunte>"
				+ "<Indicador_Prueba>1</Indicador_Prueba>"
				+ "<Codigo_Entidad_Registral_Inicio>TESTER000000000000002</Codigo_Entidad_Registral_Inicio>"
				+ "<Decodificacion_Entidad_Registral_Inicio><![CDATA[Descripcin de TESTER000000000000002]]></Decodificacion_Entidad_Registral_Inicio>"
				+ "</De_Internos_Control>"
				+ "<De_Formulario_Generico>"
				+ "<Expone><![CDATA[expone]]></Expone>"
				+ "<Solicita><![CDATA[solicita]]></Solicita>"
				+ "</De_Formulario_Generico>"
				+ "</Fichero_Intercambio_SICRES_3>";
	}

}