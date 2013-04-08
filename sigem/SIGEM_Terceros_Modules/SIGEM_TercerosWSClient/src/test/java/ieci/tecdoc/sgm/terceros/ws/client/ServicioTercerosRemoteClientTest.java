package ieci.tecdoc.sgm.terceros.ws.client;

import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionElectronica;
import ieci.tecdoc.sgm.core.services.terceros.dto.DireccionPostal;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.util.List;

import javax.xml.rpc.ServiceException;

import junit.framework.TestCase;


public class ServicioTercerosRemoteClientTest extends TestCase {

	
	private static final String TERCEROS_WSDL_URL =		
		"http://localhost:8080/SIGEM_TercerosWS/services/TercerosWebService?wsdl";
	
	String ID_ENTIDAD = "000";
	
	
	private ServicioTerceros getServicioTerceros() throws ServiceException {
		ServicioTercerosRemoteClient servicio = 
			new ServicioTercerosRemoteClient();
		servicio.setService(getTercerosWebService());
		return servicio;
	}
	
	private TercerosWebService getTercerosWebService() throws ServiceException {
		TercerosWebServiceServiceLocator locator = 
			new TercerosWebServiceServiceLocator();
		locator.setTercerosWebServiceEndpointAddress(TERCEROS_WSDL_URL);
		return locator.getTercerosWebService();
	}

	public void testTercerosPorCodigo(){

		log("\n\n--- Entrada en testTercerosPorCodigo ---");

		try {

			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) código no existente [12345678]:");
			List terceros = servicio.lookup(ID_ENTIDAD, "12345678");
			log(terceros);

			log("b) código existente [44444444A]:");
			terceros = servicio.lookup(ID_ENTIDAD, "44444444A");
			log(terceros);

			log("\n\n--- Entrada en testTercerosPorCodigoCompleto ---");

			log("a) código no existente completo [12345678]:");
			terceros = servicio.lookup(ID_ENTIDAD, "12345678", false);
			log(terceros);

			log("b) código existente completo [44444444A]:");
			terceros = servicio.lookup(ID_ENTIDAD, "44444444A", false);
			log(terceros);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testTercerosPorCodigo", e);
		}
		
		log("--- Salida de testTercerosPorCodigo ---");
	}

	public void testTercerosPorNombre(){
		
		log("\n\n--- Entrada en testTercerosPorNombre ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) nombre no existente [yuju]:");
			List terceros = servicio.lookup(ID_ENTIDAD, "yuju", null, null);
			log(terceros);

			log("b) nombre existente ['Delirium', 'Tremens', 'Mortis']:");
			terceros = servicio.lookup(ID_ENTIDAD, "Delirium", "Tremens", "Mortis");
			log(terceros);
			
			log("\n\n--- Entrada en testTercerosPorNombreCompleto---");
			
			log("a) nombre no existente completo [yuju]:");
			terceros = servicio.lookup(ID_ENTIDAD, "yuju", null, null, false);
			log(terceros);

			log("b) nombre existente completo ['Delirium', 'Tremens', 'Mortis']:");
			terceros = servicio.lookup(ID_ENTIDAD, "Delirium", "Tremens", "Mortis", false);
			log(terceros);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testTercerosPorNombre", e);
		}
		
		log("--- Salida de testTercerosPorNombre ---");
	}

	public void testTercerosPorId(){
		
		log("\n\n--- Entrada en testTercerosPorId ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			Tercero tercero = servicio.lookupById(ID_ENTIDAD, "999999");
			log(tercero);

			log("b) id existente [45171]:");
			tercero = servicio.lookupById(ID_ENTIDAD, "45171");
			log(tercero);
			
			log("\n\n--- Entrada en testTercerosPorIdCompleto---");
			
			log("a) id no existente [999999]:");
			tercero = servicio.lookupById(ID_ENTIDAD, "999999", false);
			log(tercero);

			log("b) id existente [45171]:");
			tercero = servicio.lookupById(ID_ENTIDAD, "45171", false);
			log(tercero);

			log("\n\n--- Entrada en testTercerosPorId + direcciones---");
			
			log("a) id no existente [999999, 1, 1]:");
			tercero = servicio.lookupById(ID_ENTIDAD, "999999", "1", "1");
			log(tercero);

			log("b) id existente [45171]:");
			tercero = servicio.lookupById(ID_ENTIDAD, "45171", "66289", "66292");
			log(tercero);


			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testTercerosPorId", e);
		}
		
		log("--- Salida de testTercerosPorId ---");
	}

	public void testDireccionesPostales(){
		
		log("\n\n--- Entrada en testDireccionesPostales ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionPostal[] direcciones = servicio.lookupPostalAddresses(ID_ENTIDAD, "999999");
			log(direcciones);

			log("b) id existente [45171]:");
			direcciones = servicio.lookupPostalAddresses(ID_ENTIDAD, "45171");
			log(direcciones);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionesPostales", e);
		}
		
		log("--- Salida de testDireccionesPostales ---");
	}

	public void testDireccionPostalPorDefecto(){
		
		log("\n\n--- Entrada en testDireccionPostalPorDefecto ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionPostal direccion = servicio.lookupDefaultPostalAddress(ID_ENTIDAD, "999999");
			log(direccion);

			log("b) id existente [45171]:");
			direccion = servicio.lookupDefaultPostalAddress(ID_ENTIDAD, "45171");
			log(direccion);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionPostalPorDefecto", e);
		}
		
		log("--- Salida de testDireccionPostalPorDefecto ---");
	}

	public void testDireccionPostal(){
		
		log("\n\n--- Entrada en testDireccionPostal ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionPostal direccion = servicio.getPostalAddress(ID_ENTIDAD, "999999");
			log(direccion);

			log("b) id existente [66294]:");
			direccion = servicio.getPostalAddress(ID_ENTIDAD, "66290");
			log(direccion);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionPostal", e);
		}
		
		log("--- Salida de testDireccionPostal ---");
	}

	public void testDireccionesElectronicas(){
		
		log("\n\n--- Entrada en testDireccionesElectronicas ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionElectronica[] direcciones = servicio.lookupElectronicAddresses(ID_ENTIDAD, "999999");
			log(direcciones);

			log("b) id existente [45171]:");
			direcciones = servicio.lookupElectronicAddresses(ID_ENTIDAD, "45171");
			log(direcciones);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionesElectronicas", e);
		}
		
		log("--- Salida de testDireccionesElectronicas ---");
	}

	public void testDireccionElectronicaPorDefecto(){
		
		log("\n\n--- Entrada en testDireccionElectronicaPorDefecto ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionElectronica direccion = servicio.lookupDefaultElectronicAddress(ID_ENTIDAD, "999999");
			log(direccion);

			log("b) id existente [45171]:");
			direccion = servicio.lookupDefaultElectronicAddress(ID_ENTIDAD, "45171");
			log(direccion);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionElectronicaPorDefecto", e);
		}
		
		log("--- Salida de testDireccionElectronicaPorDefecto ---");
	}

	public void testDireccionElectronica(){
		
		log("\n\n--- Entrada en testDireccionElectronica ---");
		
		try {
			ServicioTerceros servicio = getServicioTerceros();
			
			log("a) id no existente [999999]:");
			DireccionElectronica direccion = servicio.getElectronicAddress(ID_ENTIDAD, "999999");
			log(direccion);

			log("b) id existente [66294]:");
			direccion = servicio.getElectronicAddress(ID_ENTIDAD, "66294");
			log(direccion);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testDireccionElectronica", e);
		}
		
		log("--- Salida de testDireccionElectronica ---");
	}

	private void log(String message) {
		System.out.println(message);
	}

	private void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}

	private void log(List terceros) {
		if ((terceros != null) && (terceros.size() > 0)) {
			log("=> " + terceros.size() + " elementos encontrados:");
			for (int i = 0; i < terceros.size(); i++) {
				log((Tercero)terceros.get(i));
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(Tercero tercero) {
		if (tercero != null){
			StringBuffer msg = new StringBuffer()
				.append("- Tercero: id=[")
				.append(tercero.getIdExt())
				.append("], Identificacion=[")
				.append(tercero.getIdentificacion())
				.append("], Nombre=[")
				.append(tercero.getNombre())
				.append("], PrimerApellido=[")
				.append(tercero.getPrimerApellido())
				.append("], SegundoApellido=[")
				.append(tercero.getSegundoApellido())
				.append("], NotificacionTelematica=[")
				.append(tercero.isNotificacionTelematica())
				.append("], TipoPersona=[")
				.append(tercero.getTipoPersona())
				.append("]");
			log(msg.toString());
			
			log("\nDirecciones Postales:");
			log(tercero.getDireccionesPostales());
			
			log("\nDirecciones Electronicas:");
			log(tercero.getDireccionesElectronicas());
			
		} else {
			log("- [Tercero nulo]");
		}
	}

	private void log(DireccionPostal[] direcciones) {
		if ((direcciones != null) && (direcciones.length > 0)) {
			log("=> " + direcciones.length + " elementos encontrados:");
			for (int i = 0; i < direcciones.length; i++) {
				log(direcciones[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(DireccionPostal direccion) {
		if (direccion != null){
			StringBuffer msg = new StringBuffer()
				.append("- Dirección: id=[")
				.append(direccion.getId())
				.append("], direccionPostal=[")
				.append(direccion.getDireccionPostal())
				.append("], tipoVia=[")
				.append(direccion.getTipoVia())
				.append("], via=[")
				.append(direccion.getVia())
				.append("], bloque=[")
				.append(direccion.getBloque())
				.append("], piso=[")
				.append(direccion.getPiso())
				.append("], puerta=[")
				.append(direccion.getPuerta())
				.append("], codigoPostal=[")
				.append(direccion.getCodigoPostal())
				.append("], poblacion=[")
				.append(direccion.getPoblacion())
				.append("], municipio=[")
				.append(direccion.getMunicipio())
				.append("], provincia=[")
				.append(direccion.getProvincia())
				.append("], comunidadAutonoma=[")
				.append(direccion.getComunidadAutonoma())
				.append("], pais=[")
				.append(direccion.getPais())
				.append("], telefono=[")
				.append(direccion.getTelefono())
				.append("]");

			log(msg.toString());
		} else {
			log("- [Dirección nula]");
		}
	}

	private void log(DireccionElectronica[] direcciones) {
		if ((direcciones != null) && (direcciones.length > 0)) {
			log("=> " + direcciones.length + " elementos encontrados:");
			for (int i = 0; i < direcciones.length; i++) {
				log(direcciones[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(DireccionElectronica direccion) {
		if (direccion != null){
			StringBuffer msg = new StringBuffer()
				.append("- Dirección: id=[")
				.append(direccion.getId())
				.append("], type=[")
				.append(direccion.getTipo())
				.append("], direccion=[")
				.append(direccion.getDireccion())
				.append("]");
			
			log(msg.toString());
		} else {
			log("- [Dirección nula]");
		}
	}

}
