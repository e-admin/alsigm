package ieci.tecdoc.sgm.tram.ws.client;

import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import junit.framework.TestCase;

import org.apache.axis.encoding.Base64;


public class ServicioTramitacionRemoteClientTest extends TestCase {

	
	private static final String TRAMITACION_WSDL_URL =		
		"http://localhost:8080/SIGEM_TramitacionWS/services/TramitacionWebService?wsdl";
	
	private static final String FILE_GUID = "<guid><archive>4</archive><folder>55</folder><document>1</document></guid>";
	private static final String NUM_EXP = "EXP2008/5";
	
	String ID_ENTIDAD = "000";

	private ServicioTramitacion getServicioTramitacion() 
			throws ServiceException {
		ServicioTramitacionRemoteClient servicio = 
			new ServicioTramitacionRemoteClient();
		servicio.setService(getTramitacionWebService());
		return servicio;
	}
	
	private TramitacionWebService getTramitacionWebService() 
			throws ServiceException {
		TramitacionWebServiceServiceLocator locator = 
			new TramitacionWebServiceServiceLocator();
		locator.setTramitacionWebServiceEndpointAddress(TRAMITACION_WSDL_URL);
		return locator.getTramitacionWebService();
	}

	public void testProcedimientosPorTipo(){
		
		log("\n\n--- Entrada en testProcedimientosPorTipo ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			log("a) Tipo ALL_PROCEDURES:");
			InfoBProcedimiento[] procs = servicio.getProcedimientos(ID_ENTIDAD,
					InfoBProcedimiento.ALL_PROCEDURES, null);
			log(procs);

			log("b) Tipo ONLY_AUTOMATIZED:");
			procs = servicio.getProcedimientos(ID_ENTIDAD,
					InfoBProcedimiento.ONLY_AUTOMATIZED, null);
			log(procs);

			log("c) Tipo ONLY_NOT_AUTOMATIZED:");
			procs = servicio.getProcedimientos(ID_ENTIDAD,
					InfoBProcedimiento.ONLY_NOT_AUTOMATIZED, null);
			log(procs);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testProcedimientosPorTipo", e);
		}
		
		log("--- Salida de testProcedimientosPorTipo ---");
	}

	public void testProcedimientos(){
		
		log("\n\n--- Entrada en testProcedimientos ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			log("a) ids={}:");
			InfoBProcedimiento[] procs = servicio.getProcedimientos(ID_ENTIDAD,new String[0]);
			log(procs);

			log("b) ids={'PCD-3','PCD-4'}:");
			procs = servicio.getProcedimientos(ID_ENTIDAD,new String[] { "PCD-3", "PCD-4" });
			log(procs);

			log("c) ids={'PCD-555','PCD-3'}:");
			procs = servicio.getProcedimientos(ID_ENTIDAD,new String[] { "PCD-555", "PCD-3" });
			log(procs);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testProcedimientos", e);
		}
		
		log("--- Salida de testProcedimientos ---");
	}

	public void testProcedimiento(){
		
		log("\n\n--- Entrada en testProcedimiento ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			Procedimiento proc = servicio.getProcedimiento(ID_ENTIDAD,"PCD-3");
			log(proc);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testProcedimiento", e);
		}
		
		log("--- Salida de testProcedimiento ---");
	}

	public void testFichero(){
		
		log("\n\n--- Entrada en testFichero ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			byte[] contenido = servicio.getFichero(ID_ENTIDAD, FILE_GUID);
			log("Contenido: " + (contenido != null ? contenido.length : 0));

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testFichero", e);
		}
		
		log("--- Salida de testFichero ---");
	}

	public void testInfoFichero(){
		
		log("\n\n--- Entrada en testInfoFichero ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			InfoFichero info = servicio.getInfoFichero(ID_ENTIDAD, FILE_GUID);
			log(info);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testInfoFichero", e);
		}
		
		log("--- Salida de testInfoFichero ---");
	}

	public void testInfoOcupacion(){
		
		log("\n\n--- Entrada en testInfoOcupacion ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			InfoOcupacion info = servicio.getInfoOcupacion(ID_ENTIDAD);
			log(info);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testInfoOcupacion", e);
		}
		
		log("--- Salida de testInfoOcupacion ---");
	}

	public void testEliminarFicheros(){
		
		log("\n\n--- Entrada en testEliminarFicheros ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			servicio.eliminaFicheros(ID_ENTIDAD, new String[] {
					"<guid><archive>3</archive><folder>373</folder><document>1</document></guid>"
			});
			log("Ficheros eliminados");

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testEliminarFicheros", e);
		}
		
		log("--- Salida de testEliminarFicheros ---");
	}

	public void testIdsExpedientes(){
		
		log("\n\n--- Entrada en testIdsExpedientes ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 0, 1);
			String[] ids = servicio.getIdsExpedientes(ID_ENTIDAD, "PCD-3", cal.getTime(), new Date(), 0);
			log(ids);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testIdsExpedientes", e);
		}
		
		log("--- Salida de testIdsExpedientes ---");
	}

	public void testExpedientes(){
		
		log("\n\n--- Entrada en testExpedientes ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			InfoBExpediente[] exps = servicio.getExpedientes(ID_ENTIDAD, new String[] {NUM_EXP});
			log(exps);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testExpedientes", e);
		}
		
		log("--- Salida de testExpedientes ---");
	}

	public void testExpediente(){
		
		log("\n\n--- Entrada en testExpediente ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			
			Expediente exp = servicio.getExpediente(ID_ENTIDAD, NUM_EXP);
			log(exp);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testExpediente", e);
		}
		
		log("--- Salida de testExpediente ---");
	}

	public void testIniciarExpediente(){
		
		log("\n\n--- Entrada en testIniciarExpediente ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();

			// Datos comunes del expediente
			DatosComunesExpediente datosComunes = new DatosComunesExpediente();
			//datosComunes.setIdOrganismo("1");
			datosComunes.setTipoAsunto("PCD-3");
			datosComunes.setNumeroRegistro("200700000001");
			datosComunes.setFechaRegistro(new Date());
			
			InteresadoExpediente int1 = new InteresadoExpediente();
			int1.setThirdPartyId("4");
			int1.setIndPrincipal("S");
			int1.setNifcif("12345678A");
			int1.setName("Pablo Alonso García");
			int1.setTelematicAddress("paglo_g_a@gmail.com");
			int1.setPlaceCity("Oviedo");
			int1.setPostalAddress("c\\Caveda");
			int1.setPostalCode("33000");
			int1.setRegionCountry("ASTURIAS/ESPAÑA");
			int1.setNotificationAddressType(InteresadoExpediente.IND_TELEMATIC);
			int1.setPhone("900000001");
			int1.setMobilePhone("600000001");

			InteresadoExpediente int2 = new InteresadoExpediente();
			int2.setThirdPartyId("2");
			int2.setIndPrincipal("N");
			int2.setNifcif("12345678A");
			int2.setName("Joaquín Regodón Holguín");
			int2.setPlaceCity("Oviedo");
			int2.setPostalAddress("c\\Caveda");
			int2.setPostalCode("33000");
			int2.setRegionCountry("ASTURIAS/ESPAÑA");
			int2.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
			int2.setPhone("900000002");
			int2.setMobilePhone("600000002");

			InteresadoExpediente int3 = new InteresadoExpediente();
			int3.setThirdPartyId(null);
			int3.setIndPrincipal("N");
			int3.setNifcif("00000000T");
			int3.setName("Homer Simpson");
			int3.setPlaceCity("Oviedo");
			int3.setPostalAddress("c\\Caveda");
			int3.setPostalCode("33000");
			int3.setRegionCountry("ASTURIAS/ESPAÑA");
			int3.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
			int3.setPhone("900000003");
			int3.setMobilePhone("600000003");

			datosComunes.setInteresados(new InteresadoExpediente[] { int1, int2, int3 });
			
			// Datos específicos del expediente
			String datosEspecificos = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><datos_especificos><asunto_queja>Ruidos</asunto_queja><cod_organo>0001</cod_organo><descr_organo>Servicio de Atención al Ciudadano</descr_organo></datos_especificos>";

			// Documentos del expediente
			List documentos = new ArrayList();
			DocumentoExpediente doc = new DocumentoExpediente();
	        doc.setCode("Anexo a Solicitud");
	        doc.setName("Solicitud");
	        doc.setContent(getDefaultFileContent());
	        doc.setExtension("doc");
	        doc.setLenght(doc.getContent().length);
			documentos.add(doc);
			
			boolean creado = servicio.iniciarExpediente(ID_ENTIDAD, datosComunes, 
					datosEspecificos, (DocumentoExpediente[])documentos.toArray(
							new DocumentoExpediente[documentos.size()]));
			log("Expediente creado: " + creado);

			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testIniciarExpediente", e);
		}
		
		log("--- Salida de testIniciarExpediente ---");
	}

	public void testAnexarDocsExpediente(){
		
		log("\n\n--- Entrada en testAnexarDocsExpediente ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();

			// Documentos del expediente
			List documentos = new ArrayList();
			DocumentoExpediente doc = new DocumentoExpediente();
			doc.setId("10-0");
	        doc.setCode("Anexo a Solicitud");
	        doc.setName("Solicitud");
	        doc.setContent(getDefaultFileContent());
	        doc.setExtension("doc");
	        doc.setLenght(doc.getContent().length);
			documentos.add(doc);
			
			boolean anexados = servicio.anexarDocsExpediente(ID_ENTIDAD, 
					NUM_EXP,
					"20080001", 
					new Date(), 
					(DocumentoExpediente[])documentos.toArray(
							new DocumentoExpediente[documentos.size()]));
			log("Documentos anexados: " + anexados);

			documentos = new ArrayList();
			doc = new DocumentoExpediente();
			doc.setId("2-0");
	        doc.setCode("Anexo a Solicitud");
	        doc.setName("Solicitud");
	        doc.setContent(getDefaultFileContent());
	        doc.setExtension("doc");
	        doc.setLenght(doc.getContent().length);
	        documentos.add(doc);
			
			anexados = servicio.anexarDocsExpediente(ID_ENTIDAD, 
					NUM_EXP,
					"20080002", 
					new Date(), 
					(DocumentoExpediente[])documentos.toArray(
							new DocumentoExpediente[documentos.size()]));
			log("Documentos anexados: " + anexados);
			
			assertTrue(true);
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testAnexarDocsExpediente", e);
		}
		
		log("--- Salida de testAnexarDocsExpediente ---");
	}
	
	public void testArchivarExpedientes(){
		
		
		log("\n\n--- Entrada en testArchivarExpedientes ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();

			// Obtengo los expedientes pendientes de archivar
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fechaIni = sdf.parse("2008-01-01 00:00:00");
			Date fechaFin = sdf.parse("2009-01-01 00:00:00");

			String[] exps = servicio.getIdsExpedientes(ID_ENTIDAD, "PCD-3", fechaIni,
					fechaFin, 1); // Ordenamos por numero de expediente

			// Archivo el primer expediente
			if (exps != null && exps.length > 0) {
				servicio.archivarExpedientes(ID_ENTIDAD, new String[] { exps[0] });
				log("Expedientes archivados");
			}

			// Compruebo q tenemos los mismo expediente de antes salvo el
			// primero
			exps = servicio.getIdsExpedientes(ID_ENTIDAD, "PCD-3", fechaIni, fechaFin, 1);
			// Compruebo que ese expediente ahora tiene el estado archivado 2

			assertTrue(true);

		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testArchivarExpedientes", e);
		}
		
		log("--- Salida de testArchivarExpedientes ---");
	}
	
	
	
	public void testIniciarExpedienteRetornoNumExp(){
	log("\n\n--- Entrada en testIniciarExpedienteTasaRSU ---");
	
	try {
			ServicioTramitacion servicio = getServicioTramitacion();
	
			// Datos comunes del expediente
			DatosComunesExpediente datosComunes = new DatosComunesExpediente();
			//datosComunes.setIdOrganismo("1");
			datosComunes.setTipoAsunto("PCD-11");
			//datosComunes.setNumeroRegistro("200700000001");
			datosComunes.setFechaRegistro(new Date());
			
			InteresadoExpediente int1 = new InteresadoExpediente();
			int1.setThirdPartyId("4");
			int1.setIndPrincipal("S");
			int1.setNifcif("12345678A");
			int1.setName("Pablo Alonso García");
			int1.setTelematicAddress("paglo_g_a@gmail.com");
			int1.setPlaceCity("Oviedo");
			int1.setPostalAddress("c\\Caveda");
			int1.setPostalCode("33000");
			int1.setRegionCountry("ASTURIAS/ESPAÑA");
			int1.setNotificationAddressType(InteresadoExpediente.IND_TELEMATIC);
			int1.setPhone("900000001");
			int1.setMobilePhone("600000001");
	
			InteresadoExpediente int2 = new InteresadoExpediente();
			int2.setThirdPartyId("2");
			int2.setIndPrincipal("N");
			int2.setNifcif("12345678A");
			int2.setName("Joaquín Regodón Holguín");
			int2.setPlaceCity("Oviedo");
			int2.setPostalAddress("c\\Caveda");
			int2.setPostalCode("33000");
			int2.setRegionCountry("ASTURIAS/ESPAÑA");
			int2.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
			int2.setPhone("900000002");
			int2.setMobilePhone("600000002");
	
			InteresadoExpediente int3 = new InteresadoExpediente();
			int3.setThirdPartyId(null);
			int3.setIndPrincipal("N");
			int3.setNifcif("00000000T");
			int3.setName("Homer Simpson");
			int3.setPlaceCity("Oviedo");
			int3.setPostalAddress("c\\Caveda");
			int3.setPostalCode("33000");
			int3.setRegionCountry("ASTURIAS/ESPAÑA");
			int3.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
			int3.setPhone("900000003");
			int3.setMobilePhone("600000003");
	
			datosComunes.setInteresados(new InteresadoExpediente[] { int1, int2, int3 });
			
			// Datos específicos del expediente
			//String datosEspecificos = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><datos_especificos><asunto_queja>Ruidos</asunto_queja><cod_organo>0001</cod_organo><descr_organo>Servicio de Atención al Ciudadano</descr_organo></datos_especificos>";
			String datosEspecificos = "<?xml version=\"1.0\" ?><objetoTributario><descripcionAtrasos>BOP 14/04/13    2006    2   56,00 €\nBOP 14/04/14    2007    1   56,00 €\nBOP 14/04/15    2007    1   56,00 €\n</descripcionAtrasos><importeTotalAtrasos>168</importeTotalAtrasos><importeTasa>56.00</importeTasa><dirDiputacion>CORRAL DA IGLESIA (NUCLEO)  36, 00</dirDiputacion><id>20452</id><pagosAtrasados>SI</pagosAtrasados><expediente><codigo>ABE2009.25</codigo></expediente><referenciaCatastral>000103600NH69E0001LJ</referenciaCatastral></objetoTributario>";			
			
			
			// Documentos del expediente
			List documentos = new ArrayList();
	//		DocumentoExpediente doc = new DocumentoExpediente();
	//        doc.setCode("Anexo a Solicitud");
	//        doc.setName("Solicitud");
	//        doc.setContent(getDefaultFileContent());
	//        doc.setExtension("doc");
	//        doc.setLenght(doc.getContent().length);
	//		documentos.add(doc);
			
	
				String initSystem = "TasaRSU";
				String numExp = servicio.iniciarExpediente(ID_ENTIDAD, datosComunes, 
						datosEspecificos, (DocumentoExpediente[])documentos.toArray(
								new DocumentoExpediente[documentos.size()]), initSystem);
				log("Expediente creado: " + numExp);
	
				assertTrue(true);
	} catch (Exception e) {
		fail("Error: " + e.toString());
		log("Error en testIniciarExpedienteTasaRSU", e);
	}
	
	log("--- Salida de testIniciarExpedienteTasaRSU ---");
}	
	

	public void testMoverExpedienteAFase(){
	log("\n\n--- Entrada en testMoverExpedienteAFase ---");
	
	try {
		ServicioTramitacion servicio = getServicioTramitacion();
		String numExp = "2008/PCD-11/000009";
		//Fase de Instruccion
		String idFaseCatalogo = "2";
		
		boolean resultado = servicio.moverExpedienteAFase(ID_ENTIDAD, numExp, idFaseCatalogo);
		if (resultado){
			log("Expediente movido: " + numExp);	
		}else {
			fail("Error: El expediente no se ha podido mover");
		}
		assertTrue(true);
	} catch (Exception e) {
		fail("Error: " + e.toString());
		log("Error en testMoverExpedienteAFase", e);
	}
	
	log("--- Salida de testMoverExpedienteAFase ---");
	
}	

	
	public void testCambiarEstadoAdministrativo(){
	
			
			log("\n\n--- Entrada en testCambiarEstadoAdministrativo ---");
			
			try {		
				ServicioTramitacion servicio = getServicioTramitacion();	
				String numExp = "2008/PCD-11/000009";
				String estadoAdm = "PA";
				boolean cambiado = servicio.cambiarEstadoAdministrativo(ID_ENTIDAD,numExp,estadoAdm );
				log("Cambiado estado del Expediente ");
			
				assertTrue(true);
			
			} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testIniciarExpedienteTasaRSU", e);
			}
			
			log("--- Salida de testCambiarEstadoAdministrativo ---");		
		}	
	
	
	
	public void testBusquedaAvanzada(){
		log("\n\n--- Entrada en testSearch ---");
		
		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			String searchXML = "<?xml version=\"1.0\"?><search><entity name=\"SPAC_EXPEDIENTES\"><field><name>NUMEXP</name><operator>like</operator><value>EXP2009</value></field></entity></search>";
			String groupName = "<NOMBRE_GRUPO>";
			String searchFormName = "<NOMBRE_FORMULARIO_BUSQUEDA>";
			String resultado;
			int domain = 1;
			resultado = servicio.busquedaAvanzada(ID_ENTIDAD,groupName, searchFormName, searchXML, domain);
			log("Resultado de busqueda: " + resultado);		

			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testSearch", e);
		}
		
		log("--- Salida de testSearch ---");
		
	}	
	
	
	public void testEstablecerRegistroEntidad(){
		log("\n\n--- Entrada en testInsertarRegistroEntidad ---");

		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			String entidadTramitacion = "CONS_FICHAS_SEGUIMIENTO";
			String numExp = "EXP2009/000062";
			String datos = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><fichaSeguimiento><fecha>22/12/2002</fecha><direccion>C\\\\Uria, nº 10, 5º H - 33001 - Oviedo - Asturias</direccion><concello>Oviedo</concello><procesoIntervencion>Proceso de Intervención</procesoIntervencion><accion>MSO</accion><itinerario>P3</itinerario><contrato>SI</contrato><formacionAMedida>NO</formacionAMedida><asistencia>NO</asistencia><observaciones>Observaciones</observaciones></fichaSeguimiento>";
			int resultado = 0;
			String datosRegistro = null;
			
			resultado = servicio.establecerDatosRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, datos);
			log("Identificador del registro creado: " + resultado);		

			
			datosRegistro = servicio.obtenerRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, resultado);
			log("Datos registro : " + datosRegistro);					

			datos = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><fichaSeguimiento><id>"+resultado+"</id><fecha>22/12/2002</fecha><direccion>C\\\\Uria, nº 22, 22º H - 22022 - Oviedo - Asturias</direccion><concello>Oviedo</concello><procesoIntervencion>Proceso de Intervención</procesoIntervencion><accion>MSO</accion><itinerario>P3</itinerario><contrato>SI</contrato><formacionAMedida>NO</formacionAMedida><asistencia>NO</asistencia><observaciones></observaciones></fichaSeguimiento>";
			resultado = servicio.establecerDatosRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, datos);
			log("Identificador del registro creado: " + resultado);		

			
			datosRegistro = servicio.obtenerRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, resultado);
			log("Datos registro : " + datosRegistro);					
			
			datos = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><fichaSeguimiento><id>0</id><observaciones>OBSERVACIONES</observaciones></fichaSeguimiento>";
			resultado = servicio.establecerDatosRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, datos);

			datosRegistro = servicio.obtenerRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, 0);
			log("Datos registro : " + datosRegistro);				
			
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testInsertarRegistroEntidad", e);
		}
		
		log("--- Salida de testInsertarRegistroEntidad ---");
		
	}	

	
	public void testObtenerRegistroEntidad(){
		log("\n\n--- Entrada en testActualizarRegistroEntidad ---");

		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			String entidadTramitacion = "CONS_FICHAS_SEGUIMIENTO";
			String numExp = "EXP2009/000062";
							
			
			String datosRegistro = servicio.obtenerRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, 33);
			log("Datos registro : " + datosRegistro);					
			
			datosRegistro = servicio.obtenerRegistroEntidad(ID_ENTIDAD,entidadTramitacion, numExp, 0);
			log("Datos registro : " + datosRegistro);					

			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testActualizarRegistroEntidad", e);
		}
		
		log("--- Salida de testActualizarRegistroEntidad ---");
		
	}
	

	public void testObtenerRegistrosEntidad(){
		log("\n\n--- Entrada en testObtenerRegistrosEntidad ---");

		try {
			ServicioTramitacion servicio = getServicioTramitacion();
			String entidadTramitacion = "CONS_FICHAS_SEGUIMIENTO";
			String numExp = "EXP2009/000062";
							
			
			String datosRegistro = servicio.obtenerRegistrosEntidad(ID_ENTIDAD,entidadTramitacion, numExp);
			log("Datos registros : " + datosRegistro);					
			
		} catch (Exception e) {
			fail("Error: " + e.toString());
			log("Error en testObtenerRegistrosEntidad", e);
		}
		
		log("--- Salida de testObtenerRegistrosEntidad ---");
		
	}
	
	
	private void log(String message) {
		System.out.println(message);
	}

	private void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}
	
	private void log(Object[] lista) {
		if ((lista != null) && (lista.length > 0)) {
			log("=> " + lista.length + " elementos encontrados:");
			for (int i = 0; i < lista.length; i++) {
				log(lista[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(InfoBProcedimiento proc) {
		if (proc != null){
			StringBuffer msg = new StringBuffer()
				.append("- InfoBProcedimiento: id=[")
				.append(proc.getId())
				.append("], codigo=[")
				.append(proc.getCodigo())
				.append("], nombre=[")
				.append(proc.getNombre())
				.append("], codSistProductor=[")
				.append(proc.getCodSistProductor())
				.append("], nombreSistProductor=[")
				.append(proc.getNombreSistProductor())
				.append("]");
			log(msg);
		} else {
			log("- [InfoBProcedimiento nulo]");
		}
	}

	private void log(Procedimiento proc) {
		if (proc != null){
			StringBuffer msg = new StringBuffer()
				.append("- Procedimiento: id=[")
				.append(proc.getInformacionBasica().getId())
				.append("], codigo=[")
				.append(proc.getInformacionBasica().getCodigo())
				.append("], nombre=[")
				.append(proc.getInformacionBasica().getNombre())
				.append("], codSistProductor=[")
				.append(proc.getInformacionBasica().getCodSistProductor())
				.append("], nombreSistProductor=[")
				.append(proc.getInformacionBasica().getNombreSistProductor())
				.append("]");
			log(msg);
		} else {
			log("- [InfoBProcedimiento nulo]");
		}
	}

	private void log(InfoBExpediente exp) {
		if (exp != null){
			StringBuffer msg = new StringBuffer()
				.append("- InfoBExpediente: id=[")
				.append(exp.getId())
				.append("], numexp=[")
				.append(exp.getNumExp())
				.append("], datosIdentificativos=[")
				.append(exp.getDatosIdentificativos())
				.append("]");
			log(msg);
		} else {
			log("- [InfoBProcedimiento nulo]");
		}
	}

	private void log(Expediente exp) {
		if (exp != null){
			StringBuffer msg = new StringBuffer()
				.append("- Expediente: id=[")
				.append(exp.getInformacionBasica().getId())
				.append("], numexp=[")
				.append(exp.getInformacionBasica().getNumExp())
				.append("], datosIdentificativos=[")
				.append(exp.getInformacionBasica().getDatosIdentificativos())
				.append("], fechaInicio=[")
				.append(exp.getFechaInicio())
				.append("], fechaFin=[")
				.append(exp.getFechaFinalizacion())
				.append("], idOrgProductor=[")
				.append(exp.getIdOrgProductor())
				.append("], nombreOrgProductor=[")
				.append(exp.getNombreOrgProductor())
				.append("], asunto=[")
				.append(exp.getAsunto())
				.append("]");
			log(msg);
			
			log("  -> Documentos físicos:");
			if (exp.getDocumentosFisicos() != null) {
				DocFisico doc;
				for (int i = 0; i < exp.getDocumentosFisicos().length; i++) {
					doc = exp.getDocumentosFisicos()[i];
					log("    - DocFisico: tipoDocumento=[" 
							+ doc.getTipoDocumento()
							+ "], asunto=["
							+ doc.getAsunto()
							+ "]");
				}
			}
			
			log("  -> Documentos electrónicos:");
			if (exp.getDocumentosElectronicos() != null) {
				DocElectronico doc;
				for (int i = 0; i < exp.getDocumentosElectronicos().length; i++) {
					doc = exp.getDocumentosElectronicos()[i];
					log("    - DocElectronico: tipoDocumento=[" 
							+ doc.getTipoDocumento()
							+ "], asunto=["
							+ doc.getAsunto()
							+ "], repositorio=["
							+ doc.getRepositorio()
							+ "], localizador=["
							+ doc.getLocalizador()
							+ "], extension=["
							+ doc.getExtension()
							+ "]");
				}
			}
			
			log("  -> Interesados:");
			if (exp.getInteresados() != null) {
				Interesado inter;
				for (int i = 0; i < exp.getInteresados().length; i++) {
					inter = exp.getInteresados()[i];
					log("    - Interesado: nombre=[" 
							+ inter.getNombre()
							+ "], numIdentidad=["
							+ inter.getNumIdentidad()
							+ "], rol=["
							+ inter.getRol()
							+ "], principal=["
							+ inter.isInteresadoPrincipal()
							+ "], idEnTerceros=["
							+ inter.getIdEnTerceros()
							+ "]");
				}
			}
			
			log("  -> Emplazamientos:");
			if (exp.getEmplazamientos() != null) {
				Emplazamiento empl;
				for (int i = 0; i < exp.getEmplazamientos().length; i++) {
					empl = exp.getEmplazamientos()[i];
					log("    - Emplazamiento: pais=[" 
							+ empl.getPais()
							+ "], comunidad=["
							+ empl.getComunidad()
							+ "], concejo=["
							+ empl.getConcejo()
							+ "], poblacion=["
							+ empl.getPoblacion()
							+ "], localizacion=["
							+ empl.getLocalizacion()
							+ "]");
				}
			}

		} else {
			log("- [InfoBProcedimiento nulo]");
		}
	}

	private void log(InfoFichero info) {
		if (info != null) {
			StringBuffer msg = new StringBuffer()
				.append("- InfoFichero: nombre=[")
				.append(info.getNombre())
				.append("], fecha alta=[")
				.append(info.getFechaAlta())
				.append("], firmas=[");
			if (info.getFirmas() != null) {
				for (int i = 0; i < info.getFirmas().length; i++) {
					if (i > 0) {
						msg.append(", ");
					}
					msg.append("[autor=[")
						.append(info.getFirmas()[i].getAutor())
						.append("], autenticada=[")
						.append(info.getFirmas()[i].isAutenticada())
						.append("]]");
				}
			}
			msg.append("]");
			
			log(msg.toString());
		} else {
			log("La información del fichero es NULL");
		}
	}

	private void log(InfoOcupacion info) {
		if (info != null) {
			StringBuffer msg = new StringBuffer()
				.append("- InfoOcupacion: espacio ocupado=[")
				.append(info.getEspacioOcupado())
				.append("], espacio total=[")
				.append(info.getEspacioTotal())
				.append("], numero ficheros=[")
				.append(info.getNumeroFicheros())
				.append("]");
			log(msg.toString());
		} else {
			log("La información de ocupación es NULL");
		}
	}

	private void log(Object obj) {
		if (obj instanceof InfoBProcedimiento) {
			log((InfoBProcedimiento)obj);
		} else if (obj instanceof InfoBExpediente) {
			log((InfoBExpediente)obj);
		} else {
			if (obj != null){
				log("- " + obj.toString());
			} else {
				log("- [Objeto nulo]");
			}
		}
	}


	private byte[] getDefaultFileContent() {
		final String fileBase64 = new StringBuffer()
			.append("0M8R4KGxGuEAAAAAAAAAAAAAAAAAAAAAPgADAP7/CQAGAAAAAAAAAAAAAAABAAAAKwAAAAAA")
			.append("AAAAEAAALQAAAAEAAAD+////AAAAACoAAAD/////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("///////////////////////////////////spcEAI2AJBAAA8BK/AAAAAAAAEAAAAAAABgAA")
			.append("SggAAA4AYmpiaqEVoRUAAAAAAAAAAAAAAAAAAAAAAAAKDBYALhAAAMN/AADDfwAAJQAAAAAA")
			.append("AAAkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD//w8AAAAAAAAAAAD//w8AAAAAAAAAAAD//w8A")
			.append("AAAAAAAAAAAAAAAAAAAAAKQAAAAAALoFAAAAAAAAugUAALoFAAAAAAAAugUAAAAAAAC6BQAA")
			.append("AAAAALoFAAAAAAAAugUAABQAAAAAAAAAAAAAAM4FAAAAAAAAMggAAAAAAAAyCAAAAAAAADII")
			.append("AAA4AAAAaggAAAwAAAB2CAAADAAAAM4FAAAAAAAA2AsAAJABAACOCAAAAAAAAI4IAAAAAAAA")
			.append("jggAAAAAAACOCAAAAAAAAI4IAAAAAAAAaQkAAAAAAABpCQAAAAAAAGkJAAAAAAAAVwsAAAIA")
			.append("AABZCwAAAAAAAFkLAAAAAAAAWQsAAAAAAABZCwAAAAAAAFkLAAAAAAAAWQsAACQAAABoDQAA")
			.append("aAIAANAPAAB2AAAAfQsAABUAAAAAAAAAAAAAAAAAAAAAAAAAugUAAAAAAABpCQAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAABpCQAAAAAAAGkJAAAAAAAAaQkAAAAAAABpCQAAAAAAAH0LAAAAAAAA")
			.append("AAAAAAAAAAC6BQAAAAAAALoFAAAAAAAAjggAAAAAAAAAAAAAAAAAAI4IAADbAAAAkgsAABYA")
			.append("AAD/CgAAAAAAAP8KAAAAAAAA/woAAAAAAABpCQAAZAAAALoFAAAAAAAAjggAAAAAAAC6BQAA")
			.append("AAAAAI4IAAAAAAAAVwsAAAAAAAAAAAAAAAAAAP8KAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaQkAAAAAAABXCwAAAAAAAAAAAAAAAAAA")
			.append("/woAAAAAAAAAAAAAAAAAAP8KAAAAAAAAugUAAAAAAAC6BQAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/woAAAAAAACOCAAA")
			.append("AAAAAIIIAAAMAAAA0E/3n3rwxwEAAAAAAAAAADIIAAAAAAAAzQkAAF4AAAD/CgAAAAAAAAAA")
			.append("AAAAAAAAQwsAABQAAACoCwAAMAAAANgLAAAAAAAA/woAAAAAAABGEAAAAAAAACsKAABwAAAA")
			.append("RhAAAAAAAAD/CgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD/CgAAFAAAAEYQAAAAAAAAAAAAAAAAAAC6BQAA")
			.append("AAAAABMLAAAwAAAAaQkAAAAAAABpCQAAAAAAAP8KAAAAAAAAaQkAAAAAAABpCQAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaQkAAAAAAABpCQAAAAAAAGkJAAAAAAAA")
			.append("fQsAAAAAAAB9CwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAmwoAAGQA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGkJAAAAAAAAaQkAAAAAAABpCQAA")
			.append("AAAAANgLAAAAAAAAaQkAAAAAAABpCQAAAAAAAGkJAAAAAAAAaQkAAAAAAAAAAAAAAAAAAM4F")
			.append("AAAAAAAAzgUAAAAAAADOBQAAZAIAADIIAAAAAAAAzgUAAAAAAADOBQAAAAAAAM4FAAAAAAAA")
			.append("MggAAAAAAADOBQAAAAAAAM4FAAAAAAAAzgUAAAAAAAC6BQAAAAAAALoFAAAAAAAAugUAAAAA")
			.append("AAC6BQAAAAAAALoFAAAAAAAAugUAAAAAAAD/////AAAAAAIADAEAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAEQvRPFhLjogUGFibG8NDQ0wNi8wOS8wNw0NDVNPTElD")
			.append("SVRVRA0DDQ0EDQ0DDQ0EDQ1BdmFuemEgTE9DQUwgICBTSUdFTQ0NDQ0NAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAYAAAgI")
			.append("AAANCAAADggAAA8IAAAQCAAAGAgAABkIAAAbCAAAJAgAACUIAAAmCAAAKAgAACkIAAArCAAA")
			.append("LAgAAC4IAAAvCAAAMQgAAD4IAABACAAARggAAEgIAABJCAAASggAAO/ezsrCvrXKvsqtqa2p")
			.append("ramtqZSQfpCpygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIhZoinUJADUIgUIqB0NKIABPSgMAUUoD")
			.append("AGFKIABwaP+ZAAAABhZoinUJAAAoFmiKdQkAQioIQ0okAGFKJABmSOAAcGj///8AccoKAAAA")
			.append("//+ZAAAAAAAGFmj3ZTgAAA8DagAAAAAWaPdlOABVCAERFmi+aRUAbUgABG5IAAR1CAEGFmjw")
			.append("FFoAAA4WaL5pFQBtSAoEc0gKBAAGFmi+aRUAAB8VaPI8+AAWaL5pFQA1CIFCKgZDShQAXAiB")
			.append("cGj/AAAAIRZo8BRaADUIgUIqAUNKFABtSAAEbkgABHBoAAAAAHUIASAWaL5pFQA1CIFCKgZc")
			.append("CIFtSAAEbkgABHBo/wAAAHUIARgABgAADggAAA8IAAAQCAAAGQgAABoIAAAbCAAAJQgAACcI")
			.append("AAAoCAAAKggAACsIAAAtCAAALggAADAIAAAxCAAARggAAEcIAABICAAASQgAAEoIAAD2AAAA")
			.append("AAAAAAAAAAAA9gAAAAAAAAAAAAAAAPEAAAAAAAAAAAAAAADkAAAAAAAAAAAAAAAA3AAAAAAA")
			.append("AAAAAAAAAPEAAAAAAAAAAAAAAADxAAAAAAAAAAAAAAAA2gAAAAAAAAAAAAAAANoAAAAAAAAA")
			.append("AAAAAADaAAAAAAAAAAAAAAAA2gAAAAAAAAAAAAAAANoAAAAAAAAAAAAAAADaAAAAAAAAAAAA")
			.append("AAAA2gAAAAAAAAAAAAAAANoAAAAAAAAAAAAAAADVAAAAAAAAAAAAAAAA0wAAAAAAAAAAAAAA")
			.append("ANoAAAAAAAAAAAAAAADaAAAAAAAAAAAAAAAA8QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAABDwAABA8AZ2Qtd50AAAEAAAAHAAADJAJhJAJnZL5pFQAADBIAD4TUDRGE")
			.append("xAJehNQNYITEAmdkvmkVAAAEAABnZL5pFQAACAAAD4SdEF6EnRBnZL5pFQAAFAAGAAAlCAAA")
			.append("SQgAAP39AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAABAQAAQECLAAxkGgBH7CCLiCwxkEhsKUGIrClBiOQiQUkkIkFJbAAABew")
			.append("xAIYsMQCDJDEAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAhgIUABIAAQCcAA8ABAAAAAAA")
			.append("AAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPAAAQPH/AgA8AAwAAAC+aRUA")
			.append("AAAGAE4AbwByAG0AYQBsAAAAAgAAABQAQ0oYAF9IAQRtSAoMc0gKDHRICgw8AAFAAQACADwA")
			.append("DAAAAL5pFQAAAAgAVADtAHQAdQBsAG8AIAAxAAAACAABAAYkAUAmAAoANQiBXAiBYUoYAAAA")
			.append("AAAAAAAAAAAAAAAAAABOAEFA8v+hAE4ADAERAAAAAAAAABsARgB1AGUAbgB0AGUAIABkAGUA")
			.append("IABwAOEAcgByAGEAZgBvACAAcAByAGUAZABlAHQAZQByAC4AAAAAAFYAaQDz/7MAVgAMBQAA")
			.append("AAAAAAAADABUAGEAYgBsAGEAIABuAG8AcgBtAGEAbAAAACAAOlYLABf2AwAANNYGAAEFAwAA")
			.append("NNYGAAEKA2wAYfYDAAACAAsAAAAsAGsA9P/BACwAAAUAAAAAAAAAAAkAUwBpAG4AIABsAGkA")
			.append("cwB0AGEAAAACAAwAAAAAADwAH0ABAPIAPAAMBAAALXedAAAACgBFAG4AYwBhAGIAZQB6AGEA")
			.append("ZABvAAAADQAPAA3GCAACnBA4IQECAAAAQgAgQAEAAgFCAAwEAAAtd50AAAANAFAAaQBlACAA")
			.append("ZABlACAAcADhAGcAaQBuAGEAAAANABAADcYIAAKcEDghAQIAAAB6AP5PAQASAXoADAAKAL5p")
			.append("FQAAAB4AIABDAGEAcgAgAEMAYQByACAAQwBhAHIAMQAgAEMAYQByACAAQwBhAHIAIABDAGEA")
			.append("cgAyACAAQwBhAHIAAAAMABEAEmQQ/wAAFKSgABgAT0oCAFFKAgBhShgAbUgDBHNIAwR0SAkE")
			.append("ZAD+TwEAIgFkAAwAEwC+aRUAAAAVAFAAYQByAHIAYQBmAG8ARwBlAG4AZQByAGEAbABOAGUA")
			.append("ZwByAGkAdABhAAAAEAASAAMkAxOk8AAUpHgAYSQDDwA1CIFDShQAT0oEAFFKBAAAZgD+T6IA")
			.append("MQFmAAwAEgC+aRUAAAAZAFAAYQByAHIAYQBmAG8ARwBlAG4AZQByAGEAbABOAGUAZwByAGkA")
			.append("dABhACAAQwBhAHIAAAAbADUIAU9KBABRSgQAX0gBBG1ICgxzSAoMdEgKDAAAAAAASgAAAAQA")
			.append("ABAAAAAA/////wAAAAAOAAAADwAAABAAAAAZAAAAGgAAABsAAAAlAAAAJwAAACgAAAAqAAAA")
			.append("KwAAAC0AAAAuAAAAMAAAADEAAABGAAAARwAAAEgAAABLAAAAmAAAAAAwAAAAAAAAAIAAAACA")
			.append("AAAAAAAAAAAAAJgAAAAAMAAAAAAAAACAAAAAgAAAAAAAAAAAAACYAAAAADAAAAAAAAAAgAAA")
			.append("AIAAAAAAAAAAAAAAmAAAABIwAAAAAAAAAIAAAACAAAAAAAAAAAAAAJgAAAAAMAAAAAAAAACA")
			.append("AAAAgAAAAAAAAAAAAACYAAAAADAAAAAAAAAAgAAAAIAAAAAAAAAAAAAAmAAAAAAwAAAAAAAA")
			.append("AIAAAACAAAAAAAAAAAAAAJhAAAAAMAAAAAAAAACAAAAAgAAAAAAAAAAAgAeIkAAwADAAAAAA")
			.append("AAABAAAAAAAAAAAAAAAAAM4HmEAAAAAwAAAAAAAAAIAAAACAAAAAAAAAAACAB4iQADAAMAAA")
			.append("AAAAAAEAAAAAAAAAAAAAAAAAzgeYQAAAADAAAAAAAAAAgAAAAIAAAAAAAAAAAIAHiJAAMAAw")
			.append("AAAAAAAAAQAAAAAAAAAAAAAAAADOB5hAAAAAMAAAAAAAAACAAAAAgAAAAAAAAAAAgAeIkAAw")
			.append("ADAAAAAAAAABAAAAAAAAAAAAAAAAAM4HmEAAAA8wAAAAAAAAAIAAAACAAAAAAAAAAAAAB5hA")
			.append("AAAPMAAAAAAAAACAAAAAgAAAAAAAAAAAAACYQAAAADAAAAAAAAAAgAAAAIAAAAAAAAAAAIAA")
			.append("iJAAMAAwAAAAAAAAAQAAAAAAAAAAAAAAtATOBwAAAAADAAAABgAAAAYAAAAJAAAADAAAAAwA")
			.append("AAAMAAAAIwAAACMAAAAjAAAAIwAAACMAAAAmAAAAAAYAAEoIAAAFAAAAAAYAAEoIAAAGAAAA")
			.append("AAYAAEkIAAAHAAAADwAA8DgAAAAAAAbwGAAAAAIIAAACAAAAAQAAAAEAAAABAAAAAgAAAEAA")
			.append("HvEQAAAA//8AAAAA/wCAgIAA9wAAEAAPAALwkgAAABAACPAIAAAAAQAAAAEEAAAPAAPwMAAA")
			.append("AA8ABPAoAAAAAQAJ8BAAAAAAAAAAAAAAAAAAAAAAAAAAAgAK8AgAAAAABAAABQAAAA8ABPBC")
			.append("AAAAEgAK8AgAAAABBAAAAA4AAFMAC/AeAAAAvwEAABAAywEAAAAA/wEAAAgABAMJAAAAPwMB")
			.append("AAEAAAAR8AQAAAABAAAAAAAAABsAAAAkAAAAJQAAACUAAAAnAAAAJwAAACgAAAAoAAAAKgAA")
			.append("ACsAAAAtAAAALgAAADAAAAAxAAAASAAAAEsAAAAHAAQABwAEAAcABAACAAQABwAEAAcABAAH")
			.append("AAQABwACAAAAAAAbAAAAJQAAACUAAAAnAAAAJwAAACgAAAAoAAAAKgAAACsAAAAtAAAALgAA")
			.append("ADAAAAAxAAAASAAAAEsAAAAHAAQABAAHAAQAAgAEAAcABAAHAAQABwAEAAcAAgAAAAAADgAA")
			.append("ABAAAAAkAAAAJQAAACUAAAAnAAAAJwAAACgAAAAoAAAAKgAAACsAAAAtAAAALgAAADAAAAAx")
			.append("AAAARgAAAEgAAABLAAAABQAHAAUABwAEAAcABAACAAQABwAEAAcABAAHAAQABQAHAAIAAAAA")
			.append("ABsAAAAkAAAAJQAAACUAAAAnAAAAJwAAACgAAAAoAAAAKgAAACsAAAAtAAAALgAAADAAAAAx")
			.append("AAAASAAAAEsAAAAHAAQABwAEAAcABAACAAQABwAEAAcABAAHAAQABwACAAEA7jfgAAAAAAAA")
			.append("AAAAAAECAAIABgAAAAQAAAAIAAAA5QAAAAAAAAAFAAAAinUJAL5pFQD3ZTgA8BRaAC13nQDD")
			.append("YKwAAAAAABkAAABLAAAAAQAAAAEAAAD/QAOAAQAkAAAAJAAAAIQ/XwEBAAEAJAAAAAAAAAAk")
			.append("AAAAAAAAAAIQAAAAAAAAAEoAAABAAAAQAEAAAP//AQAAAAcAVQBuAGsAbgBvAHcAbgD//wEA")
			.append("CAAAAAAAAAAAAAAA//8BAAAAAAD//wAAAgD//wAAAAD//wAAAgD//wAAAAAFAAAARxaQAQAA")
			.append("AgIGAwUEBQIDBIc6ACAAAAAAAAAAAAAAAAD/AQAAAAAAAFQAaQBtAGUAcwAgAE4AZQB3ACAA")
			.append("UgBvAG0AYQBuAAAANRaQAQIABQUBAgEHBgIFBwAAAAAAAAAQAAAAAAAAAAAAAACAAAAAAFMA")
			.append("eQBtAGIAbwBsAAAAMyaQAQAAAgsGBAICAgICBIc6ACAAAAAAAAAAAAAAAAD/AQAAAAAAAEEA")
			.append("cgBpAGEAbAAAAGEmkAEAFgIPBwQDBQQDAgQDAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABBAHIA")
			.append("aQBhAGwAIABSAG8AdQBuAGQAZQBkACAATQBUACAAQgBvAGwAZAAAAFQAYQBoAG8AbQBhAAAA")
			.append("dwaQAQAYAgAFAwUAAAIABKcAAIBAAAAAAAAAAAAAAAABAAAAAAAAAEYAcgB1AHQAaQBnAGUA")
			.append("cgBOAGUAeAB0ACAATABUACAAUgBlAGcAdQBsAGEAcgAAAFQAaQBtAGUAcwAgAE4AZQB3ACAA")
			.append("UgBvAG0AYQBuAAAAIgAEADEIiBgA8MQCAACpAQAAAADsmpFmZzO5hgAAAAAFAAIAAAAFAAAA")
			.append("IAAAAAEAAQAAAAQAAxABAAAABQAAACAAAAABAAEAAAABAAAAAAAAACEDAPAQAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKUGiQW0ALQAgYESNAAAEAAZAGQA")
			.append("AAAZAAAAJAAAACQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAACAAAAAAAAAAAACDKDcQDwEAAI3AMAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAABIUAAAAAAJ8P8PAQABPwAA5AQAAP///3////9/////f////3////9/")
			.append("////f////38td50AAAAAADIAAAAAAAAAAAAAAAAAAAAAAP//EgAAAAAAAAAAAAAAAAAAAB4A")
			.append("RQBkAHUAYQByAGQAbwAgAEcAbwBuAHoAYQBsAGUAegAtAEUAcwB0AHIAYQBkAGEAIABTAG8A")
			.append("bABlAHIACAA2ADUANwA4ADMAMQA3ADcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("/v8AAAUBAgAAAAAAAAAAAAAAAAAAAAAAAQAAAOCFn/L5T2gQq5EIACsns9kwAAAAiAEAABEA")
			.append("AAABAAAAkAAAAAIAAACYAAAAAwAAAKQAAAAEAAAAsAAAAAUAAADYAAAABgAAAOQAAAAHAAAA")
			.append("8AAAAAgAAAAEAQAACQAAABgBAAASAAAAJAEAAAoAAABEAQAADAAAAFABAAANAAAAXAEAAA4A")
			.append("AABoAQAADwAAAHABAAAQAAAAeAEAABMAAACAAQAAAgAAAOQEAAAeAAAABAAAAAAAAAAeAAAA")
			.append("BAAAAAAAAAAeAAAAIAAAAEVkdWFyZG8gR29uemFsZXotRXN0cmFkYSBTb2xlcgAAHgAAAAQA")
			.append("AAAAAAAAHgAAAAQAAAAAAAAAHgAAAAwAAABOb3JtYWwuZG90AAAeAAAADAAAADY1NzgzMTc3")
			.append("AAAAAB4AAAAEAAAANQAAAB4AAAAYAAAATWljcm9zb2Z0IE9mZmljZSBXb3JkAAAAQAAAAACM")
			.append("hkcAAAAAQAAAAACwCWcL/sQBQAAAAAAiUoR68McBAwAAAAEAAAADAAAABQAAAAMAAAAgAAAA")
			.append("AwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP7/AAAFAQIA")
			.append("AAAAAAAAAAAAAAAAAAAAAAEAAAAC1c3VnC4bEJOXCAArLPmuMAAAAOwAAAAMAAAAAQAAAGgA")
			.append("AAAPAAAAcAAAAAUAAACAAAAABgAAAIgAAAARAAAAkAAAABcAAACYAAAACwAAAKAAAAAQAAAA")
			.append("qAAAABMAAACwAAAAFgAAALgAAAANAAAAwAAAAAwAAADNAAAAAgAAAOQEAAAeAAAACAAAAElF")
			.append("Q0lTQQAAAwAAAAEAAAADAAAAAQAAAAMAAAAkAAAAAwAAAMQfCwALAAAAAAAAAAsAAAAAAAAA")
			.append("CwAAAAAAAAALAAAAAAAAAB4QAAABAAAAAQAAAAAMEAAAAgAAAB4AAAAHAAAAVO10dWxvAAMA")
			.append("AAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAgAAAAMAAAAEAAAA")
			.append("BQAAAAYAAAAHAAAACAAAAP7///8KAAAACwAAAAwAAAANAAAADgAAAA8AAAAQAAAA/v///xIA")
			.append("AAATAAAAFAAAABUAAAAWAAAAFwAAABgAAAAZAAAA/v///xsAAAAcAAAAHQAAAB4AAAAfAAAA")
			.append("IAAAACEAAAD+////IwAAACQAAAAlAAAAJgAAACcAAAAoAAAAKQAAAP7////9////LAAAAP7/")
			.append("///+/////v//////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("/////////////1IAbwBvAHQAIABFAG4AdAByAHkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAWAAUB//////////8DAAAABgkCAAAAAADAAAAAAAAARgAA")
			.append("AAAAAAAAAAAAAJAdD6B68McBLgAAAIAAAAAAAAAARABhAHQAYQAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAoAAgH/////////////")
			.append("//8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJAAAAABAAAAAAAAAxAFQA")
			.append("YQBiAGwAZQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAADgACAQEAAAAGAAAA/////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAABEAAABGEAAAAAAAAFcAbwByAGQARABvAGMAdQBtAGUAbgB0AAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaAAIBAgAAAAUAAAD/////AAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAC4QAAAAAAAABQBTAHUAbQBtAGEAcgB5AEkA")
			.append("bgBmAG8AcgBtAGEAdABpAG8AbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACgAAgH/////")
			.append("//////////8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaAAAAABAAAAAA")
			.append("AAAFAEQAbwBjAHUAbQBlAG4AdABTAHUAbQBtAGEAcgB5AEkAbgBmAG8AcgBtAGEAdABpAG8A")
			.append("bgAAAAAAAAAAAAAAOAACAQQAAAD//////////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAACIAAAAAEAAAAAAAAAEAQwBvAG0AcABPAGIAagAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASAAIA////////////////AAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHIAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAD///////////////8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAABAAAA/v//////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("////////////////////////////////////////////////////////////////////////")
			.append("/////////////////////////////////////////////wEA/v8DCgAA/////wYJAgAAAAAA")
			.append("wAAAAAAAAEYgAAAARG9jdW1lbnRvIE1pY3Jvc29mdCBPZmZpY2UgV29yZAAKAAAATVNXb3Jk")
			.append("RG9jABAAAABXb3JkLkRvY3VtZW50LjgA9DmycQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
			.append("AAAAAAAA")
			.toString();

		return Base64.decode(fileBase64);
	}
}
