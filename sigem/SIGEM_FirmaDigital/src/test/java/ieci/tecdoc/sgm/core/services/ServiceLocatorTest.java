package ieci.tecdoc.sgm.core.services;

import sun.misc.BASE64Encoder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import junit.framework.TestCase;

public class ServiceLocatorTest extends TestCase {

	public void testGetDigitalSignService() {
		try {
			String firma = new String("qwerqwerqwerqewrsdklfjasñdkfjq`weifjañsdkfjañdsjf");
			String datos = new String("qwerqwerqwerqewrsdklfjasñdkfjq`weifjañsdkfjañdsjfsdfadsfdsafds");
			BASE64Encoder encoder = new BASE64Encoder();
			ServicioFirmaDigital oService = LocalizadorServicios.getServicioFirmaDigital(ConstantesServicios.LOCAL_SERVICE_DIGITAL_SIGN_AFIRMA_IMPL);
			String cResultado = oService.firmar((encoder.encode(firma.getBytes())).getBytes());
			System.out.println(cResultado);
			ResultadoValidacionFirma oResultado = oService.validarFirma(cResultado.getBytes(), datos.getBytes());
			System.out.println("OK");
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
