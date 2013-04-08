package ieci.tdw.ispac.ispacmgr.intermediacion.services;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import es.redsara.intermediacion.scsp.esquemas.ws.Scspws;
import es.redsara.intermediacion.scsp.esquemas.ws.ScspwsServiceLocator;
import es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Atributos;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Consentimiento;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Funcionario;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionPdf;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.PeticionSincrona;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Procedimiento;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Solicitante;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmision;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.SolicitudTransmisionDatosGenericos;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.TipoDocumentacion;
import es.redsara.intermediacion.scsp.esquemas.ws.peticion.Titular;
import es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Estado;
import es.redsara.intermediacion.scsp.esquemas.ws.respuesta.Respuesta;
import es.redsara.intermediacion.scsp.esquemas.ws.respuesta.RespuestaPdf;

public class PeticionSincronaDatosGenericos {

	private static final Logger logger = Logger.getLogger(PeticionSincronaDatosGenericos.class);
	
	private static final String ROOT_XML = "DatosEspecificos";

	private String codigoServicio = null;
	private String nombreServicio = null;
	private String finalidad = null;
	private String idSolicitante = null;
	private String nameSolicitante = null;
	private String nameTitular = null;
	private String nifTitular = null;
	private String consentimiento = null;
	private String nifFuncionario = null;
	private String nameFuncionario = null;
	private String numExp = null;
	private String codigoProcedimiento = null;
	private String nombreProcedimiento = null;
	private String unidadTramitadora = null;
	
	private Scspws getScspwsService() throws ServiceException, ISPACException{
		ScspwsServiceLocator locator = new ScspwsServiceLocator();
		locator.setscspwsSoap11EndpointAddress(ISPACConfiguration.getInstance().get(Constantes.RECUBRIMIENTOWS_SERVICE_WS_URL) );
		
		return locator.getscspwsSoap11();
	}	
	
	
	
	//Cliente AXIS 1.4
	public RespuestaRecubrimientoWS invoke(SessionAPI session, HttpServletRequest request) throws ISPACException{
		try{
			Scspws scspws = getScspwsService();
			
			PeticionSincrona peticionSincrona = new PeticionSincrona();
			Atributos atributos = new Atributos();
			atributos.setCodigoCertificado(getCodigoServicio());
			peticionSincrona.setAtributos(atributos);
			
			Funcionario funcionario = new Funcionario();
			funcionario.setNifFuncionario(getNifFuncionario());
			funcionario.setNombreCompletoFuncionario(getNameFuncionario());
			
	        Solicitante solicitante = new Solicitante();
	        solicitante.setFinalidad(getFinalidad());
	        solicitante.setFuncionario(funcionario);
	        solicitante.setConsentimiento(Consentimiento.fromValue(getConsentimiento()));
	        solicitante.setIdExpediente(getNumExp());
	        solicitante.setIdentificadorSolicitante(getIdSolicitante());
	        solicitante.setNombreSolicitante(getNameSolicitante());
	        
	        //Intrroducimos estos datos pero no son obligatorios
	        Procedimiento procedimiento = new Procedimiento();
	        procedimiento.setCodProcedimiento(getCodigoProcedimiento());
	        procedimiento.setNombreProcedimiento(getNombreProcedimiento());
	        solicitante.setProcedimiento(procedimiento);
	        
	        //Departamento al que pertenece el usuario que realiza la consulta
	        solicitante.setUnidadTramitadora(getUnidadTramitadora());
	        
	        //Titular titular = new Titular(tipoDocumentacion, documentacion, nombreCompleto, nombre, apellido1, apellido2);
	        TipoDocumentacion tipoDocumentacion = TipoDocumentacion.fromValue(TipoDocumentacion._NIF);
	        Titular titular = new Titular();
			titular.setTipoDocumentacion(tipoDocumentacion);
			titular.setDocumentacion(getNifTitular());
			//titular.setNombreCompleto(getNameTitular());
			
			
			SolicitudTransmisionDatosGenericos datosGenericos = new SolicitudTransmisionDatosGenericos(solicitante, titular);
			SolicitudTransmision solicitud = new SolicitudTransmision();
			
			solicitud.setDatosGenericos(datosGenericos);
			
			peticionSincrona.setSolicitudes(new SolicitudTransmision[]{solicitud});
			
			RespuestaRecubrimientoWS respuestaWS = null;
			try{
				if (logger.isInfoEnabled()){
					logger.info("Realizando consulta al servicio web de Recubrimiento");
				}
				Respuesta respuesta = scspws.peticionSincrona(peticionSincrona);
				respuestaWS = composeResult(respuesta);
				
				if (StringUtils.equals(respuestaWS.getCodigoEstado(), RespuestaRecubrimientoWS.CODIGO_ESTADO_OK)){
					if (logger.isDebugEnabled()){
						logger.debug("Respuesta recibida con estado correcto");
					}
//					String datosEspecificos = "<"+ROOT_XML+">"+respuesta.getTransmisiones()[0].getDatosEspecificos() +"</"+ROOT_XML+">";
//					String pdf = getPdfFromDatosEspecificos(datosEspecificos);
					//Consultamos el pdf
					String idTransmision = respuesta.getTransmisiones()[0].getDatosGenericos().getTransmision().getIdTransmision();
					String idPeticion = respuesta.getAtributos().getIdPeticion();
					RespuestaPdf respuestaPDF = invokePeticionPdf(idPeticion, idTransmision);
					respuestaWS.setPdf(respuestaPDF.getPdf());
					//respuestaWS.setPdfBase64(pdf);
				}				
				
			}catch(Fault e){
				if (logger.isDebugEnabled()){
					logger.debug("Respuesta recibida con error");
				}
				respuestaWS = new RespuestaRecubrimientoWS();
				respuestaWS.setLiteralError(e.getLiteralError());
				respuestaWS.setCodigoEstado(e.getCodigoEstado());
			}			
			
			return respuestaWS;
			
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new ISPACException("Error al consultar al Recubrimiento del Servicio de Intermediacion", e);
		}	
	}


	private RespuestaPdf invokePeticionPdf(String idPeticion, String idTransmision) throws Exception {
		PeticionPdf peticionPdf=new PeticionPdf();
		peticionPdf.setIdPeticion(idPeticion);
		peticionPdf.setIdTransmision(idTransmision);
			
		Scspws service=getScspwsService();
		RespuestaPdf respuestaPdf=service.peticionPdf(peticionPdf);
		 
		return respuestaPdf;
	}	
	
	
	private String getPdfFromDatosEspecificos(String datosEspecificos) throws Exception{

	    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    //domFactory.setNamespaceAware(true); 
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    
	    ByteArrayInputStream bais=new ByteArrayInputStream(datosEspecificos.getBytes("UTF-8"));
	    Document doc = builder.parse(bais);
	    String pdf=getValueInXmlByXPath("/" + ROOT_XML + "/DatosSalida/pdf",doc);
		
		return pdf;
	}


	private String getValueInXmlByXPath(String xpathExpression, Document doc) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(xpathExpression);
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : null;
	}



	private RespuestaRecubrimientoWS composeResult(Respuesta respuesta) {
		RespuestaRecubrimientoWS respuestaWS = new RespuestaRecubrimientoWS();
		Estado estado = respuesta.getAtributos().getEstado();
		if (estado != null){
			respuestaWS.setCodigoEstado(estado.getCodigoEstado());
			respuestaWS.setLiteralError(estado.getLiteralError());
		}
		return respuestaWS;
	}
	

	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getFinalidad() {
		return finalidad;
	}

	public void setFinalidad(String finalidad) {
		this.finalidad = finalidad;
	}

	public String getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(String idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getNameSolicitante() {
		return nameSolicitante;
	}

	public void setNameSolicitante(String nameSolicitante) {
		this.nameSolicitante = nameSolicitante;
	}

	public String getNameTitular() {
		return nameTitular;
	}

	public void setNameTitular(String nameTitular) {
		this.nameTitular = nameTitular;
	}	

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public String getConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(String consentimiento) {
		this.consentimiento = consentimiento;
	}
	
	public String getNifFuncionario() {
		return nifFuncionario;
	}

	public void setNifFuncionario(String nifFuncionario) {
		this.nifFuncionario = nifFuncionario;
	}

	public String getNameFuncionario() {
		return nameFuncionario;
	}

	public void setNameFuncionario(String nameFuncionario) {
		this.nameFuncionario = nameFuncionario;
	}	

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}
	
	public String getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	public void setCodigoProcedimiento(String codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}

	public String getNombreProcedimiento() {
		return nombreProcedimiento;
	}

	public void setNombreProcedimiento(String nombreProcedimiento) {
		this.nombreProcedimiento = nombreProcedimiento;
	}

	public String getUnidadTramitadora() {
		return unidadTramitadora;
	}

	public void setUnidadTramitadora(String unidadTramitadora) {
		this.unidadTramitadora = unidadTramitadora;
	}	
}