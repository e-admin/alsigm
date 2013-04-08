package es.redsara.intermediacion.scsp.esquemas.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import junit.framework.TestCase;
import es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Consentimiento;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Funcionario;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionAsincrona;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Procedimiento;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular;
import es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta;


import org.apache.axiom.om.*;
import org.apache.axis.utils.Options;

import org.apache.axis.client.*;
import org.apache.commons.lang.StringUtils;

public class ScspwsProxyTest extends TestCase {

	
	//private static final String SERVICE_WSDL_URL = "http://localhost:9090/scsp-ws-3.2.2/ws";
	//private static final String SERVICE_WSDL_URL = "http://10.228.75.80:8080/scsp-ws/ws";
	private static final String SERVICE_WSDL_URL = "http://localhost:7777/scsp-ws/ws";
	
	//tcpmon
	//private static final String SERVICE_WSDL_URL = "http://localhost:9999/scsp-ws-3.2.2/ws";

	
	private Scspws getScspwsService() throws ServiceException{
		ScspwsServiceLocator locator = new ScspwsServiceLocator();
		locator.setscspwsSoap11EndpointAddress(SERVICE_WSDL_URL);
		
		return locator.getscspwsSoap11();

	}	
	
	public void testPeticionSincrona(){
		try {
			Scspws scspws = getScspwsService();
		
		
			PeticionSincrona peticionSincrona = new PeticionSincrona();
			Atributos atributos = new Atributos();
			//atributos.setCodigoCertificado("SVDCDYGWS01");
			atributos.setCodigoCertificado("SVDTUWS01");
			peticionSincrona.setAtributos(atributos);
			
			
			//Solicitante
	        String identificadorSolicitante = "P1300000E";
	        String nombreSolicitante = "Diputación Provincial de Ciudad Real";
	        String unidadTramitadora = "SECCIONINICIADORA";
	        Procedimiento procedimiento = new Procedimiento("codProcedimiento", "nombreProcedimiento");
	        String finalidad = "finalidad de la solicitudd";
	        Consentimiento consentimiento = Consentimiento.fromValue(Consentimiento._Si);
	        Funcionario funcionario = new Funcionario("nombreCompletoFuncionario", "00000000T");
	        String idExpediente  = "<NUMEXP>";			
			
            //Titular
			TipoDocumentacion tipoDocumentacion = TipoDocumentacion.fromValue(TipoDocumentacion._NIF);
			java.lang.String documentacion = "30977684L";  
			java.lang.String nombreCompleto = "NOMBRE_COMPLETO";
			java.lang.String nombre = "NOMBRE";
			java.lang.String apellido1 = "APELLIDO1";
			java.lang.String apellido2 = "APELLIDO2";
	           
			Solicitante solicitante = new Solicitante(identificadorSolicitante, nombreSolicitante, unidadTramitadora, procedimiento, finalidad, consentimiento, funcionario, idExpediente);
			Titular titular = new Titular(tipoDocumentacion, documentacion, nombreCompleto, nombre, apellido1, apellido2);
			
			//SolicitudTransmisionDatosGenericos datosGenericos = new SolicitudTransmisionDatosGenericos(solicitante, titular);
			//SolicitudTransmision solicitud = new SolicitudTransmision();
			SolicitudTransmisionDatosGenericos datosGenericos = new SolicitudTransmisionDatosGenericos(solicitante, titular);
			SolicitudTransmision solicitud = new SolicitudTransmision();
			
			solicitud.setDatosGenericos(datosGenericos);
			
			
			
			
//			String datosEspecificos = "";
//			solicitud.setDatosEspecificos(null);
//			solicitud.setDatosEspecificos2(null);
//			solicitud.setDatosEspecificos3(null);
			
			peticionSincrona.setSolicitudes(new SolicitudTransmision[]{solicitud});
			
//			Solicitudes solicitudes = new Solicitudes();
//			solicitudes.setSolicitudTransmision(solicitud);
//			peticionSincrona.setSolicitudes(solicitudes);

			String error = null;
			try{
				Respuesta respuesta = scspws.peticionSincrona(peticionSincrona);
				respuesta.getAtributos();
				System.out.println("Ok");
			}catch(Fault e){
				error = e.getLiteralError();
			}
			
			if (StringUtils.isNotEmpty(error)){
				System.out.println(error);
			}
			
			//PETICION ASINCRONA
//			peticionAsincrona.setAtributos(atributos);
//			peticionAsincrona.setSolicitudes(new SolicitudTransmision[]{solicitud});
//			ConfirmacionPeticion confirmacion = scspws.peticionAsincrona(peticionAsincrona);
//			confirmacion.getAtributos();
			
			
		}catch (ServiceException e) {
			e.printStackTrace();
		}catch (RemoteException e) {
			e.printStackTrace();
		}catch (RuntimeException e){
			e.printStackTrace();
		}
	}
	

	public void testTitulosUniversitarios(){
		
		try {
			Scspws scspws = getScspwsService();		
			PeticionSincrona peticion=new PeticionSincrona();
			
			Atributos atributos=new Atributos();
			atributos.setCodigoCertificado("SVDTUWS01");
			peticion.setAtributos(atributos);
	
			Solicitante solicitante=new Solicitante();
			solicitante.setIdentificadorSolicitante("S2811001C");
			solicitante.setNombreSolicitante("MPR");
			solicitante.setFinalidad("PRUEBA RECUBRIMIENTO");
			solicitante.setConsentimiento(Consentimiento.Si);
			
			Titular titular=new Titular();
			titular.setTipoDocumentacion(TipoDocumentacion.DNI);
			titular.setDocumentacion("30977684L");
			
			SolicitudTransmisionDatosGenericos datosGenericos=new SolicitudTransmisionDatosGenericos();
			datosGenericos.setSolicitante(solicitante);
			datosGenericos.setTitular(titular);
			
			SolicitudTransmision solicitudTransmision=new SolicitudTransmision();
			solicitudTransmision.setDatosGenericos(datosGenericos);
			peticion.setSolicitudes(new SolicitudTransmision[]{solicitudTransmision});
			
			
			String error = null;
			try{
				Respuesta respuesta = scspws.peticionSincrona(peticion);
				respuesta.getAtributos();
				System.out.println("Ok");
			}catch(Fault e){
				error = e.getLiteralError();
			}
			
			if (StringUtils.isNotEmpty(error)){
				System.out.println(error);
			}			
			
		}catch (ServiceException e) {
			e.printStackTrace();
		}catch (RemoteException e) {
			e.printStackTrace();
		}catch (RuntimeException e){
			e.printStackTrace();
		}	
	}
	
	
}
