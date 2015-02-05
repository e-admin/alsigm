package ieci.tecdoc.sgm.ct.ws.client;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import junit.framework.TestCase;

public class ConsultaExpedientesWebServiceRemoteClientTest extends TestCase {

	ieci.tecdoc.sgm.core.services.dto.Entidad entidad = null;
	protected void setUp() throws Exception {
		entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("00002");
		entidad.setNombre("Ayto 00002");
	}

	public void testAltaNotificacion() {
		fail("Not yet implemented");
	}

	public void testObtenerURLPagoTasas() {
		fail("Not yet implemented");
	}

	public void testRecogerDocumento() {
		fail("Not yet implemented");
	}

	public void testRecogerNotificaciones() {

	}

	public void testAltaSolicitudPago() {
		fail("Not yet implemented");
	}

	public void testAltaSolicitudSubsanacion() {
		fail("Not yet implemented");
	}

	public void testAnexarFicherosHitoActual() {
		fail("Not yet implemented");
	}

	public void testBusquedaExpedientes() {
		fail("Not yet implemented");
	}

	public void testCargarDocumento() {
		fail("Not yet implemented");
	}

	public void testConsultarExpedientes() {
		try {
			ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes("SIGEM_ServicioConsultaExpedientes.SIGEM.WEBSERVICE");
			ieci.tecdoc.sgm.core.services.consulta.Expedientes expedientes = oServicio.consultarExpedientes(new CriterioBusquedaExpedientes(), entidad);
			System.out.println(expedientes);
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testConsultarExpedientesNIF() {
		fail("Not yet implemented");
	}

	public void testEliminarExpediente() {
		fail("Not yet implemented");
	}

	public void testEliminarHitoActual() {
		fail("Not yet implemented");
	}

	public void testEliminarHitoHistorico() {
		fail("Not yet implemented");
	}

	public void testEliminarInteresado() {
		fail("Not yet implemented");
	}

	public void testEliminarInteresadoExpediente() {
		fail("Not yet implemented");
	}

	public void testEstablecerHitoActual() {
		fail("Not yet implemented");
	}

	public void testExistenNotificaciones() {
		fail("Not yet implemented");
	}

	public void testExistenPagos() {
		fail("Not yet implemented");
	}

	public void testExistenSubsanaciones() {
		fail("Not yet implemented");
	}

	public void testNuevoExpediente() {
		fail("Not yet implemented");
	}

	public void testNuevoHitoHistorico() {
		fail("Not yet implemented");
	}

	public void testNuevoInteresado() {
		fail("Not yet implemented");
	}

	public void testObtenerDetalle() {
		fail("Not yet implemented");
	}

	public void testObtenerFicherosHito() {
		fail("Not yet implemented");
	}

	public void testObtenerFicherosHitos() {
		fail("Not yet implemented");
	}

	public void testObtenerHistoricoExpediente() {
		fail("Not yet implemented");
	}

	public void testObtenerHitoEstado() {
		fail("Not yet implemented");
	}

	public void testObtenerNotificionesHito() {
		fail("Not yet implemented");
	}

	public void testObtenerNotificionesHitoActual() {
		fail("Not yet implemented");
	}

	public void testObtenerPagosHito() {
		fail("Not yet implemented");
	}

	public void testObtenerPagosHitoActual() {
		fail("Not yet implemented");
	}

	public void testObtenerSubsanacionesHito() {
		fail("Not yet implemented");
	}

	public void testObtenerSubsanacionesHitoActual() {
		fail("Not yet implemented");
	}

	public void testObtenerURLAportacionExpedientes() {
		fail("Not yet implemented");
	}

	public void testObtenerURLNotificacionExpedientes() {
		fail("Not yet implemented");
	}

	public void testGetService() {
		fail("Not yet implemented");
	}

	public void testSetService() {
		fail("Not yet implemented");
	}

}
