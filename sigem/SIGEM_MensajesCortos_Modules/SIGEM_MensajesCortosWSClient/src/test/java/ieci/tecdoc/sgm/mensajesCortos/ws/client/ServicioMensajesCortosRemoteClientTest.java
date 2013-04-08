package ieci.tecdoc.sgm.mensajesCortos.ws.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceServiceLocator;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.ServicioMensajesCortosRemoteClient;

import javax.xml.rpc.ServiceException;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;


public class ServicioMensajesCortosRemoteClientTest extends TestCase {

	
	private static final java.lang.String MENSAJESCORTOS_WSDL_URL =		
		"http://localhost:8080/SIGEM_MensajesCortosWS/services/MensajesCortosWebService?wsdl";
	
	private static final java.lang.String ENTIDAD = "000";
	
	private static int idRegla;
	
	private static java.lang.String PWD="";
	private static java.lang.String USER="";
	private static java.lang.String DST="";
	private static java.lang.String SRC="";
	private static java.lang.String TXT="PROBANDO SERVICIOS WEB";
	private static java.lang.String LANG="ES";
	private static java.lang.String ID ="";
	private static java.lang.String [] DTS_ARRAY = {"",""};

	private ServicioMensajesCortos getServicioMensajesCortos() 
			throws ServiceException {
		ServicioMensajesCortosRemoteClient servicio = 
			new ServicioMensajesCortosRemoteClient();
		servicio.setService(getMensajesCortosWebService());
		return servicio;
	}
	
	private MensajesCortosWebService getMensajesCortosWebService() 
			throws ServiceException {
		MensajesCortosWebServiceServiceLocator locator = 
			new MensajesCortosWebServiceServiceLocator();
		locator.setMensajesCortosWebServiceEndpointAddress(MENSAJESCORTOS_WSDL_URL);
		return locator.getMensajesCortosWebService();
	}
	
	
	/*
	 * ===================================================================
	 * SMS
	 * ===================================================================
	*/
	
	public void testSendSms(){
		log("\n\n--- Entrada en testSendSms ---");

		try {
		ServicioMensajesCortos servicio = getServicioMensajesCortos();
		java.lang.String mtid=servicio.sendSMS(USER, PWD, SRC, DST, TXT);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetListaHitos");
		}

		log("--- Salida de testSendSms ---");
	}
	
	public void testGetCertSMSSignatureStatus(){
		log("\n\n--- Entrada en testgetCertSMSSignatureStatus ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			int estado=servicio.getCertSMSSignatureStatus(USER, PWD, ID);
			log("\n\n estado: "+estado);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testgetCertSMSSignatureStatus");
		}

		log("--- Salida de testgetCertSMSSignatureStatus ---");
	}
	
	public void testGtSMSStatus(){
		log("\n\n--- Entrada en testgetSMSStatus ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			int estado=servicio.getSMSStatus(USER, PWD, ID);
			log("\n\n estado: "+estado);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testgtSMSStatus");
		}

		log("--- Salida de testgtSMSStatus ---");
	}
	public void testGetCertSMSSignatureDocument(){
		log("\n\n--- Entrada en tesGtetCertSMSSignatureDocumen ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			byte [] bytes =servicio.getCertSMSSignatureDocument(USER, PWD, ID);
			log("\n\n bytes: "+bytes);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetCertSMSSignatureDocumen");
		}

		log("--- Salida de testgetCertSMSSignatureStatus ---");
	}
	
	public void testGetCertSMSSignatureXML(){
		log("\n\n--- Entrada en testgtCertSMSSignatureXML ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			String xml=servicio.getCertSMSSignatureXML(USER, PWD, ID);
			log("\n\n signature: "+xml);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testgtCertSMSSignatureXML");
		}

		log("--- Salida de testgtCertSMSSignatureXML ---");
	}
	
	public void testSendCertSMS(){
		log("\n\n--- Entrada en testSendCertSMS ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			String id=servicio.sendCertSMS(USER, PWD, SRC, DST, TXT, LANG);
			log("\n\n id: "+id);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testSendCertSMS");
		}

		log("--- Salida de testSendCertSMS ---");
	}
	
	public void testSendSMSMulti(){
		log("\n\n--- Entrada en testSendSMSMulti ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			servicio.sendSMS(USER, PWD, SRC, DTS_ARRAY, TXT);
			
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testSendSMSMulti");
		}

		log("--- Salida de testSendSMSMulti ---");
	}
	
	
	
	/*
	 * ===================================================================
	 * EMAIL
	 * ===================================================================
	*/
	
	public void testSendMail(){
		log("\n\n--- Entrada en testSendSMSMulti ---");

		try {
			ServicioMensajesCortos servicio = getServicioMensajesCortos();
			String [] vacio = null;
			Attachment atch=new Attachment();
			
			File archivo = new File ("C:\\temp\\datos.txt");
			FileReader fr = new FileReader (archivo);
	        BufferedReader br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea="";
	         String cont="";
	         while((linea=br.readLine())!=null)
	            cont+=linea;

	        atch.setContent(cont.getBytes());
	        atch.setFileName("datos.txt");
			Attachment[] adjuntos =new Attachment[1];
			adjuntos[0]=atch;
			String [] to = new String[1];
			to[0]="user@server";
			servicio.sendMail("p@server.com", to,vacio, vacio, "asunto", "el contenido", adjuntos);
			
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testSendSMSMulti");
		}

		log("--- Salida de testSendSMSMulti ---");
	}
	
	private void log(java.lang.String message) {
		System.out.println(message);
	}
	
	private void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}
	
	/*
	 * ===================================================================
	 *  HITOS
	 * ===================================================================
	 
	
	public void testGetListaHitos() {
		
		log("\n\n--- Entrada en testGetListaHitos ---");

		try {
			ServiciomensajesCortos servicio = getServiciomensajesCortos();
			Hito[] hitos = servicio.getListaHitos(ENTIDAD);
			
			log(hitos);
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetListaHitos", e);
		}

		log("--- Salida de testGetListaHitos ---");
	}

	public void testAddHito() {

		log("\n\n--- Entrada en testAddHito ---");
		
		try {

			Hito hito = new Hito();
			
			hito.setIdHito(-1);
			hito.setIdPcd(1);
			hito.setIdFase(2);
			hito.setIdTramite(3);
			hito.setTipoDoc(4);
			hito.setIdObjeto("<objeto id='5'/>");
			hito.setIdEvento(6);
			hito.setEstado(7);
			hito.setIdAplicacion(8);
			hito.setIpMaquina("9.9.9.9");
			hito.setIdSistema("SISTEMA");
			hito.setInfoAux("Info aux");
			hito.setIdInfo(11);
			
			ServicioPublicador servicio = getServicioPublicador();
			Hito ret = servicio.addHito(ENTIDAD, hito);

			log(ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testAddHito", e);
		}

		log("--- Salida de testAddHito ---");
	}
	
	public void testGetHito() {

		log("\n\n--- Entrada en testGetHito ---");

		try {

			ServicioPublicador servicio = getServicioPublicador();
			Hito hito = servicio.getHito(ENTIDAD, -1, 8, "SISTEMA");
			
			log(hito);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetHito", e);
		}

		log("--- Salida de testGetHito ---");

	}
	
	public void testUpdateHito() {

		log("\n\n--- Entrada en testUpdateHito ---");
		
		try {

			Hito hito = new Hito();
			
			hito.setIdHito(-1);
			hito.setIdPcd(1);
			hito.setIdFase(2);
			hito.setIdTramite(3);
			hito.setTipoDoc(4);
			hito.setIdObjeto("<objeto id='5'/>");
			hito.setIdEvento(6);
			hito.setEstado(7);
			hito.setIdAplicacion(8);
			hito.setIpMaquina("9.9.9.9");
			hito.setIdSistema("SISTEMA");
			hito.setInfoAux("Info aux MODIFICADO");
			hito.setIdInfo(11);

			ServicioPublicador servicio = getServicioPublicador();
			Hito ret = servicio.updateHito(ENTIDAD, hito);
			
			log(ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testUpdateHito", e);
		}

		log("--- Salida de testUpdateHito ---");
	}

	public void testReactivateHito() {
		
		log("\n\n--- Entrada en testReactivateHito ---");
		
		try {
			ServicioPublicador servicio = getServicioPublicador();
			Hito ret = servicio.reactivateHito(ENTIDAD, -1, 8, "SISTEMA");
			
			log(ret);
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testReactivateHito", e);

		}
		
		log("\n\n--- Salida ReactivateHito ---");
	}
	
	public void testDeleteHito() {
		
		log("\n\n--- Entrada en testDeleteHito ---");
		
		try {

			ServicioPublicador servicio = getServicioPublicador();
			boolean ret = servicio.deleteHito(ENTIDAD, -1, 8, "SISTEMA");
			
			log("Retorno: " + ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testDeleteHito", e);

		}
		
		log("--- Salida de testDeleteHito ---");
	}
	*/
	
	/*
	 * ===================================================================
	 *  REGLAS
	 * ===================================================================
	 

	public void testGetListaReglas() {
		
		log("\n\n--- Entrada en testGetListaReglas ---");
		
		try {
			ServicioPublicador servicio = getServicioPublicador();
			Regla[] reglas = servicio.getListaReglas(ENTIDAD);
			log(reglas);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetListaReglas", e);
		}

		log("--- Salida de testGetListaReglas ---");
	}

	public void testAddRegla() {

		log("\n\n--- Entrada en testAddRegla ---");
		try {

			Regla regla = new Regla();

			regla.setId(-1);
			regla.setIdAccion(1);
			regla.setIdCondicion(2);
			regla.setIdEvento(3);
			regla.setIdFase(4);
			regla.setIdInfo(5);
			regla.setIdPcd(6);
			regla.setIdTramite(7);
			regla.setOrden(8);
			regla.setTipoDoc(9);
			regla.setIdAplicacion(10);
			regla.setAtributos("<atributos/>");

			ServicioPublicador servicio = getServicioPublicador();
			Regla ret = servicio.addRegla(ENTIDAD, regla);
			
			if (ret != null) {
				idRegla = ret.getId();
			}
			
			log(ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testAddRegla", e);
		}

		log("--- Salida de testAddRegla ---");

	}

	public void testGetRegla() {

		log("\n\n--- Entrada en testGetRegla ---");
		try {

			log("Obteniendo información de la regla con id [" + idRegla + "]");
			
			ServicioPublicador servicio = getServicioPublicador();
			Regla ret = servicio.getRegla(ENTIDAD, idRegla);
			
			log(ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetRegla", e);
		}

		log("--- Salida de testGetRegla ---");

	}

	public void testUpdateRegla() {

		log("\n\n--- Entrada en testUpdateRegla ---");
		try {

			log("Modificando la regla con id [" + idRegla + "]");
			
			Regla regla = new Regla();

			regla.setId(idRegla);
			regla.setIdAccion(1);
			regla.setIdCondicion(2);
			regla.setIdEvento(3);
			regla.setIdFase(4);
			regla.setIdInfo(5);
			regla.setIdPcd(6);
			regla.setIdTramite(7);
			regla.setOrden(8);
			regla.setTipoDoc(9);
			regla.setIdAplicacion(10);
			regla.setAtributos("<atributos>MODIFICADO</atributos>");

			ServicioPublicador servicio = getServicioPublicador();
			Regla ret = servicio.updateRegla(ENTIDAD, regla);
			
			log(ret);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testUpdateRegla", e);
		}

		log("--- Salida de testUpdateRegla ---");

	}


	public void testDeleteRegla() {

		log("\n\n--- Entrada en testDeleteRegla ---");
		try {

			log("Eliminando la regla con id [" + idRegla + "]");
			
			ServicioPublicador servicio = getServicioPublicador();
			boolean ret = servicio.deleteRegla(ENTIDAD, idRegla);

			log("Retorno: " + ret);
			
		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testDeleteRegla", e);
		}

		log("--- Salida de testDeleteRegla ---");

	}
	*/
	
	/*
	 * ===================================================================
	 *  ERRORES
	 * ===================================================================
	 

	public void testGetListaErrores() {
		
		log("\n\n--- Entrada en testGetListaErrores ---");
		
		try {

			ServicioPublicador servicio = getServicioPublicador();
			Error[] errores = servicio.getListaErrores(ENTIDAD);
			
			log(errores);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testGetListaErrores", e);
		}

		log("--- Salida de testGetListaErrores ---");
	}
	
	public void testGetListErroresId() {
		
		log("\n\n--- Entrada en testErroresId ---");
		
		try {

			ServicioPublicador servicio = getServicioPublicador();
			Error error = servicio.getError(ENTIDAD, -1, 1, "1");
			
			log(error);

		} catch (Throwable e) {
			fail("Error: " + e.toString());
			log("Error en testErroresId", e);
		}

		log("--- Salida de testGetListErrores ---");
	}

	

	
	

	private void log(Hito[] lista) {
		if ((lista != null) && (lista.length > 0)) {
			log("=> " + lista.length + " elementos encontrados:");
			for (int i = 0; i < lista.length; i++) {
				log(lista[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(Hito obj) {
		if (obj != null){
			String msg = new StringBuffer()
				.append("- Hito: idHito=[").append(obj.getIdHito())
				.append("], idPcd=[").append(obj.getIdPcd())
				.append("], idFase=[").append(obj.getIdFase())
				.append("], idTramite=[").append(obj.getIdTramite())
				.append("], tipoDoc=[").append(obj.getTipoDoc())
				.append("], idObjeto=[").append(obj.getIdObjeto())
				.append("], idEvento=[").append(obj.getIdEvento())
				.append("], estado=[").append(obj.getEstado())
				.append("], idAplicacion=[").append(obj.getIdAplicacion())
				.append("], ipMaquina=[").append(obj.getIpMaquina())
				.append("], fecha=[").append(obj.getFecha())
				.append("], idSistema=[").append(obj.getIdSistema())
				.append("], infoAux=[").append(obj.getInfoAux())
				.append("], idInfo=[").append(obj.getIdInfo())
				.append("]")
				.toString();
			log(msg);
		} else {
			log("- [Objeto nulo]");
		}
	}

	private void log(Regla[] lista) {
		if ((lista != null) && (lista.length > 0)) {
			log("=> " + lista.length + " elementos encontrados:");
			for (int i = 0; i < lista.length; i++) {
				log(lista[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(Regla obj) {
		if (obj != null){
			String msg = new StringBuffer()
				.append("- Regla: id=[").append(obj.getId())
				.append("], idPcd=[").append(obj.getIdPcd())
				.append("], idFase=[").append(obj.getIdFase())
				.append("], idTramite=[").append(obj.getIdTramite())
				.append("], tipoDoc=[").append(obj.getTipoDoc())
				.append("], idEvento=[").append(obj.getIdEvento())
				.append("], idAccion=[").append(obj.getIdAccion())
				.append("], idCondicion=[").append(obj.getIdCondicion())
				.append("], atributos=[").append(obj.getAtributos())
				.append("], idAplicacion=[").append(obj.getIdAplicacion())
				.append("], orden=[").append(obj.getOrden())
				.append("], idInfo=[").append(obj.getIdInfo())
				.append("]")
				.toString();
			
			log(msg);
		} else {
			log("- [Objeto nulo]");
		}
	}

	private void log(Error[] lista) {
		if ((lista != null) && (lista.length > 0)) {
			log("=> " + lista.length + " elementos encontrados:");
			for (int i = 0; i < lista.length; i++) {
				log(lista[i]);
			}
		} else {
			log("=> No se han obtenido resultados.");
		}
	}

	private void log(Error obj) {
		if (obj != null){
			String msg = new StringBuffer()
				.append("- Error: idHito=[").append(obj.getIdHito())
				.append("], idAplicacion=[").append(obj.getIdAplicacion())
				.append("], idSistema=[").append(obj.getIdSistema())
				.append("], idObjeto=[").append(obj.getIdObjeto())
				.append("], descripcion=[").append(obj.getDescripcion())
				.append("], idError=[").append(obj.getIdError())
				.append("]")
				.toString();

			log(msg);
		} else {
			log("- [Objeto nulo]");
		}
	}
	*/
}
