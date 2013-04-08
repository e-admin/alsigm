import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import es.ieci.scsp.verifdata.model.dao.Servicio;
import es.ieci.scsp.verifdata.services.ClienteLigero;
import es.ieci.scsp.verifdata.services.ClienteLigeroServiceLocator;
import junit.framework.Assert;
import junit.framework.TestCase;


public class ClienteLigeroWSClientTest extends TestCase {

	private static final String SERVICE_WSDL_URL = "http://localhost:8080/SIGEM_ServicioIntermediacionClienteLigeroWS/services/ClienteLigero";
	
	private ClienteLigero getClienteLigeroService() throws ServiceException{
		ClienteLigeroServiceLocator locator = new ClienteLigeroServiceLocator();
		locator.setClienteLigeroEndpointAddress(SERVICE_WSDL_URL);
		
		
		return locator.getClienteLigero();
	}	
	
	public void testConsultaProcedimientoByNIF(){
//        String nifFuncionario = "00234580A";
//        String codigoProcedimiento = "Prueba";

      String nifFuncionario = "51959103X";
      String codigoProcedimiento = "Prueba";
		
		try {
			ClienteLigero clienteLigero = getClienteLigeroService();
			Servicio[] servicios = clienteLigero.consultaProcedimientoByNIF(nifFuncionario, codigoProcedimiento);
			Assert.assertTrue(true);
			if (servicios != null && servicios.length > 0){
				for (int j = 0; j < servicios.length; j++) {
					Servicio servicio = servicios[j];
					System.out.println("["+servicio.getCodcertificado()+"] "+servicio.getDescripcion());
				}
			}
		} catch (ServiceException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
}
