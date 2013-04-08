package ieci.tecdoc.sgm.pe.impl.redes;
/*
 * $Id: PruebaWS.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;




public class PruebaWS {

	
	
	public static void probarWS(){
//		System.getProperties().put("http.proxySet" , "true");
 		System.getProperties().put("http.proxyHost","proxy.ieci.geci");
		System.getProperties().put("http.proxyPort", "8080"); //443
		System.getProperties().put("http.proxyUser", "65304255");
		System.getProperties().put("http.proxyPassword", "temporal");
//		System.setProperty("javax.net.debug", "ssl");
		
		System.setProperty("javax.net.ssl.trustStore", "c:\\workspaceRedes\\truststorecarlos");
		System.setProperty("javax.net.ssl.trustStorePassword", "carlos");
		
		Cuaderno60_1_2 oCuaderno = new Cuaderno60_1_2();
//		oCuaderno.setCcc("12360000400000000000");
		oCuaderno.setCccDomiciliacion("12360000400000000000");
		oCuaderno.setCodDomiciliacion("2");
		oCuaderno.setCodEntidad("1236");
		oCuaderno.setCpr("9050299");
		oCuaderno.setFecCaducidad("0909");
		oCuaderno.setFecha("20070510");
		oCuaderno.setHora("1657210000");
		oCuaderno.setIdent1("100");
		oCuaderno.setIdent2("1006714");
		oCuaderno.setIdentificadorMedioPago("1");
		oCuaderno.setIdioma("0");
		oCuaderno.setImporte("000000008200");
		oCuaderno.setNifCertificado("05261042E");
		oCuaderno.setOrganismoEmisor("000000");
		oCuaderno.setPan("6011000000000004");
		oCuaderno.setPasarela("01");
		oCuaderno.setReferencia("169600703501");
		oCuaderno.setReservado("Pruebas organismo IECI1IECI_SA");
		oCuaderno.setTipo("01");
		oCuaderno.setUrlRetorno("https://spt.demo.red.es/SIMO/jsp/respuestaC60_12.jsp");
		
		CuadernoRespuesta60_1_2 cuadernoRespuesta = null;
		ServicioOrganismoWSServiceLocator locator = new ServicioOrganismoWSServiceLocator();
		try {
			cuadernoRespuesta = locator.getServicioOrganismoWS().pago60_1_2(oCuaderno);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Throwable t){
			t.printStackTrace();
		}
		System.out.println("Correcto.");
	}
}
