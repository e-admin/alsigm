package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;

@ContextConfiguration({ "/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralRechazoWSTest extends AbstractWSTest { 

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
	}

	@Test
	public void testRecibirYRechazarAsientoRegistral() throws IOException {

		// XML del fichero de intercambio
		String xmlFicheroIntercambio = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream(
						"ficheroIntercambio.xml"));

		AsientoRegistralDTO asientoRecibido = null;
		
		try {

			// Recibir asiento
			asientoRecibido = intercambioRegistralWSClient
					.recibirFicheroIntercambio(xmlFicheroIntercambio, null);
			Assert.assertNotNull(asientoRecibido);
			
			// Rechazar
			intercambioRegistralWSClient
					.rechazarAsientoRegistral(
							asientoRecibido.getId(),
							TestUtils
									.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
			
			Assert.assertNotNull(intercambioRegistralWSClient
					.getAsientoRegistral(asientoRecibido.getId()));
			
		} finally  {
			if (asientoRecibido != null) {
				intercambioRegistralWSClient.deleteAsientoRegistral(asientoRecibido.getId());
			}
		}
	}
	
}
