package es.ieci.tecdoc.fwktd.sir.ws.service.wssir9;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.ws.service.AbstractWSTest;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;

@ContextConfiguration({ "/beans/fwktd-sir-ws-test-beans.xml" })
public class WSSIR9WSTest extends AbstractWSTest {

	private static final String CODIGO_ENTIDAD_REGISTRAL = "TESTER000000000000002";
	
	@Autowired
	private WS_SIR9_PortType WSSIR9WSClient;

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
		Assert.assertNotNull(WSSIR9WSClient);
	}

	@Test
	public void testEnvioMensajeACK() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		// Crear asiento
		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = intercambioRegistralWSClient.saveAsientoRegistral(asientoForm);

		// Actualizar el estado del registro
		asiento.setIdentificadorIntercambio(identificadorIntercambio);
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO.getValue());
		intercambioRegistralWSClient.updateAsientoRegistral(asiento);

		String mensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
		+ "<Mensaje>"
		+ "<Codigo_Entidad_Registral_Origen>TESTER000000000000002</Codigo_Entidad_Registral_Origen>"
		+ "<Codigo_Entidad_Registral_Destino>TESTER000000000000001</Codigo_Entidad_Registral_Destino>"
		+ "<Identificador_Intercambio>" + identificadorIntercambio + "</Identificador_Intercambio>"
		+ "<Tipo_Mensaje>01</Tipo_Mensaje>"
		+ "<Descripcion_Mensaje><![CDATA[ACK]]></Descripcion_Mensaje>"
		+ "<Numero_Registro_Entrada_Destino><![CDATA[201000100000001]]></Numero_Registro_Entrada_Destino>"
		+ "<Fecha_Hora_Entrada_Destino>20110107102347</Fecha_Hora_Entrada_Destino>"
		+ "<Indicador_Prueba>1</Indicador_Prueba>"
		+ "</Mensaje>";

		RespuestaWS respuesta = WSSIR9WSClient
				.envioMensajeDatosControlAAplicacion(mensaje, null);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		// Comprobar el estado del asiento
		asiento = intercambioRegistralWSClient.getAsientoRegistral(asiento.getId());
		Assert.assertNotNull(asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.ENVIADO_Y_ACK.getValue(), asiento.getEstado());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);
	}

	@Test
	public void testEnvioMensajeError() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		// Crear asiento
		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = intercambioRegistralWSClient.saveAsientoRegistral(asientoForm);

		// Actualizar el estado del registro
		asiento.setIdentificadorIntercambio(identificadorIntercambio);
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO.getValue());
		intercambioRegistralWSClient.updateAsientoRegistral(asiento);


		String mensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
		+ "<Mensaje>"
		+ "<Codigo_Entidad_Registral_Origen>TESTER000000000000002</Codigo_Entidad_Registral_Origen>"
		+ "<Codigo_Entidad_Registral_Destino>TESTER000000000000001</Codigo_Entidad_Registral_Destino>"
		+ "<Identificador_Intercambio>" + identificadorIntercambio + "</Identificador_Intercambio>"
		+ "<Tipo_Mensaje>02</Tipo_Mensaje>"
		+ "<Descripcion_Mensaje><![CDATA[Error]]></Descripcion_Mensaje>"
		+ "<Numero_Registro_Entrada_Destino><![CDATA[201000100000001]]></Numero_Registro_Entrada_Destino>"
		+ "<Fecha_Hora_Entrada_Destino>20110107102347</Fecha_Hora_Entrada_Destino>"
		+ "<Indicador_Prueba>1</Indicador_Prueba>"
		+ "<Codigo_Error>xxx</Codigo_Error>"
		+ "</Mensaje>";

		RespuestaWS respuesta = WSSIR9WSClient
				.envioMensajeDatosControlAAplicacion(mensaje, null);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		// Comprobar el estado del asiento
		asiento = intercambioRegistralWSClient.getAsientoRegistral(asiento.getId());
		Assert.assertNotNull(asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR.getValue(), asiento.getEstado());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);
	}

	@Test
	public void testEnvioMensajeConfirmacion() throws RemoteException {

		String identificadorIntercambio = createIdentificadorIntercambio(CODIGO_ENTIDAD_REGISTRAL);

		// Crear asiento
		AsientoRegistralFormDTO asientoForm = TestUtils.createAsientoRegistralFormDTO();
		AsientoRegistralDTO asiento = intercambioRegistralWSClient.saveAsientoRegistral(asientoForm);

		// Actualizar el estado del registro
		asiento.setIdentificadorIntercambio(identificadorIntercambio);
		asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO.getValue());
		intercambioRegistralWSClient.updateAsientoRegistral(asiento);


		String mensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
		+ "<Mensaje>"
		+ "<Codigo_Entidad_Registral_Origen>TESTER000000000000002</Codigo_Entidad_Registral_Origen>"
		+ "<Codigo_Entidad_Registral_Destino>TESTER000000000000001</Codigo_Entidad_Registral_Destino>"
		+ "<Identificador_Intercambio>" + identificadorIntercambio + "</Identificador_Intercambio>"
		+ "<Tipo_Mensaje>03</Tipo_Mensaje>"
		+ "<Descripcion_Mensaje><![CDATA[Confirmaci\u00f3n]]></Descripcion_Mensaje>"
		+ "<Numero_Registro_Entrada_Destino><![CDATA[201000100000001]]></Numero_Registro_Entrada_Destino>"
		+ "<Fecha_Hora_Entrada_Destino>20110107102347</Fecha_Hora_Entrada_Destino>"
		+ "<Indicador_Prueba>1</Indicador_Prueba>"
		+ "</Mensaje>";

		RespuestaWS respuesta = WSSIR9WSClient
				.envioMensajeDatosControlAAplicacion(mensaje, null);

		Assert.assertNotNull(respuesta);
		Assert.assertEquals(ErroresEnum.OK.getValue(), respuesta.getCodigo());

		// Comprobar el estado del asiento
		asiento = intercambioRegistralWSClient.getAsientoRegistral(asiento.getId());
		Assert.assertNotNull(asiento);
		Assert.assertEquals(EstadoAsientoRegistralEnum.ACEPTADO.getValue(), asiento.getEstado());

		deleteAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL, identificadorIntercambio);

	}
}
