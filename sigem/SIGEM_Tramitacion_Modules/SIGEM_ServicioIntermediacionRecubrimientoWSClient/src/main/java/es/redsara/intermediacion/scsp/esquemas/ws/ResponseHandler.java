package es.redsara.intermediacion.scsp.esquemas.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import es.redsara.intermediacion.scsp.esquemas.ws.fault.Fault;

public class ResponseHandler  extends GenericHandler {

	private static final Logger logger = Logger.getLogger(ResponseHandler.class);	
	
	HandlerInfo hi;
	
	public void init(HandlerInfo info) {
	    hi = info;
	}
	
	 private String getSoapBodyContent(MessageContext context){
		 try{
			 SOAPMessageContext smc = (SOAPMessageContext) context;
			 SOAPMessage message = smc.getMessage();
		 
			 TransformerFactory tff = TransformerFactory.newInstance();
			 Transformer tf = tff.newTransformer();

			 Source sc = message.getSOAPPart().getContent();
			 ByteArrayOutputStream baos=new ByteArrayOutputStream();
			 StreamResult result = new StreamResult(baos);
			 tf.transform(sc, result);
			 
			 return baos.toString(); 
		 }catch(Exception e){
			 return null;
		 }
	 }
	 
	 private boolean isFaultMessage(String soapbodyMessage){
		 if(soapbodyMessage==null) return false;
		 if(soapbodyMessage.toLowerCase().indexOf("</fault>")!=-1){
			 return  true;
		 }
		 return false;
	 }
	
	 private String getValueInXmlByXPath(String xpathExpression,Document doc) throws Exception{
		 XPath xpath = XPathFactory.newInstance().newXPath();
		 XPathExpression expr = xpath.compile(xpathExpression);
		 NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		 return nodes.getLength()>0?nodes.item(0).getTextContent():null;
	 }
	 
		//Si se recibe una respuesta Fault. no se reconoce como tal y lanza SAXParseException
		//en este handler se comprueba si se trata de ese caso, y si es así
		//genera el objeto Fault y lanza la excepción
		/*
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
		<soapenv:Body>
			<fault xmlns="http://intermediacion.redsara.es/scsp/esquemas/ws/fault">
				<IdPeticion>SCSPv300000000000000000021</IdPeticion>
				<TimeStamp>2012-09-03T18:16:52.282+02:00</TimeStamp>
				<CodigoCertificado>AEAT103I</CodigoCertificado>
				<CodigoEstado>0904</CodigoEstado>
				<LiteralError>Error general Indefinido Error durante la llamada al
					servicio web.</LiteralError>
			</fault>
		</soapenv:Body>
	</soapenv:Envelope>
		 */
	 public boolean handleResponse(MessageContext context){
		    String respuesta=getSoapBodyContent(context);
		    if (logger.isInfoEnabled()){
		    	logger.info("Respuesta recibida: "+respuesta);
		    }
		    if(isFaultMessage(respuesta)){
		    	try{
				    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				    //domFactory.setNamespaceAware(true); 
				    DocumentBuilder builder = domFactory.newDocumentBuilder();
				    
				    ByteArrayInputStream bais=new ByteArrayInputStream(respuesta.getBytes("UTF-8"));
				    Document doc = builder.parse(bais);
				    String idPeticion=getValueInXmlByXPath("/Envelope/Body/fault/IdPeticion",doc);
				    String timeStamp=getValueInXmlByXPath("/Envelope/Body/fault//TimeStamp",doc);
				    String codigoCertificado=getValueInXmlByXPath("/Envelope/Body/fault/CodigoCertificado",doc);
				    String codigoEstado=getValueInXmlByXPath("/Envelope/Body/fault/CodigoEstado",doc);
				    String literalError=getValueInXmlByXPath("/Envelope/Body/fault/LiteralError",doc);
			    
				    Fault fault=new Fault(idPeticion,timeStamp,codigoCertificado,codigoEstado,literalError);
				    throw fault;
		    	}catch(Exception e){
		    		throw new RuntimeException(e);
		    	}
		    }
		    
		    if(containsDatosEspecificos(respuesta)){
		    	respuesta=escapeDatosEspecificos(respuesta);
		    	setSoapMessageFromXml(context,respuesta);
		    }
			    
		    return true;
	 }
	 
	 //La etiqueta datosEspecificos puede contener un fragmento de XML (varios nodos raiz).
	 //Para no perder los datos devueltos/evitar problemas hay que evitar que axis Parsee esta parte de la respuesta.
	 //la solución es sustituir < por &lt; y > por &gt; y generar un nuevo SOAPMessage con el nuevo XML.
	 
	 private boolean containsDatosEspecificos(String soapbodyMessage){
		 if(soapbodyMessage==null) return false;
		 if(soapbodyMessage.toLowerCase().indexOf("datosespecificos>")!=-1){
			 return  true;
		 }
		 return false;
	 }
	 
	 private String escapeDatosEspecificos(String soapbodyMessage){
		 //obtener el fragmento correspondiente a los datos especificos
		 //guardar la posición de inicio y fin de dicho fragmento
		 int posCadInicioDatosEspecificos=soapbodyMessage.indexOf("DatosEspecificos");
		 if(posCadInicioDatosEspecificos==-1) return soapbodyMessage;
		 int inicioDatosEspecificos=soapbodyMessage.substring(0,posCadInicioDatosEspecificos).lastIndexOf("<");
		 int inicioFragmentoDatosEspecificos=soapbodyMessage.indexOf(">",posCadInicioDatosEspecificos)+1;
		 
		 int posCadFinDatosEspecificos=soapbodyMessage.substring(inicioFragmentoDatosEspecificos).lastIndexOf("DatosEspecificos")+inicioFragmentoDatosEspecificos;
		 if(posCadFinDatosEspecificos==-1) return soapbodyMessage;
		 int finFragmentoDatosEspecificos=soapbodyMessage.substring(0,posCadFinDatosEspecificos).lastIndexOf("</");
		 //int finDatosEspecificos=soapbodyMessage.substring(0,posCadFinDatosEspecificos).lastIndexOf("<");
		 
		 String preDatosEspecificos=soapbodyMessage.substring(0,inicioFragmentoDatosEspecificos);
		 String postDatosEspecificos=soapbodyMessage.substring(finFragmentoDatosEspecificos);
		 String cadDatosEspecificos=soapbodyMessage.substring(
				 inicioFragmentoDatosEspecificos,finFragmentoDatosEspecificos);
		 
		 cadDatosEspecificos=escapeCad(cadDatosEspecificos);
		 
		 return preDatosEspecificos+cadDatosEspecificos+postDatosEspecificos;
	 }
	 
	 private String escapeCad(String cad){
		 //cad=cad.replaceAll("</.+:","</");
		 //cad=cad.replaceAll("<.+:","<");
		 cad=cad.replaceAll("<","&lt;");
		 cad=cad.replaceAll(">","&gt;");
		 return cad;
	 }
	 
	 private void setSoapMessageFromXml(MessageContext context,String xml){
		 try{
			 SOAPMessageContext smc = (SOAPMessageContext) context;
			 SOAPMessage message = smc.getMessage();
			 message.getSOAPPart().setContent(
					 new javax.xml.transform.stream.StreamSource(
							 new StringReader(xml))); 
		 }catch(Exception e){
			 return;
		 }
	 }
	  
	 public boolean handleRequest(MessageContext context) {	
	    return true;
	 }
	
	public QName[] getHeaders() {
		return hi.getHeaders();
	}

	
}
