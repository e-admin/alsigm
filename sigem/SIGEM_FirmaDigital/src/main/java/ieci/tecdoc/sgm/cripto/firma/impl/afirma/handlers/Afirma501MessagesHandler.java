package ieci.tecdoc.sgm.cripto.firma.impl.afirma.handlers;

import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.Constantes;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.ExcepcionAFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.SignManagerAFirmaImpl;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.Util;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.config.AFirmaConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.utils.CertificateUtils;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.Firma;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.FirmarDocumento;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.ResultadoFirmarDocumento;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.InformacionAuxiliar;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Valida;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.registrarFirma.FirmarUsuario;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.registrarFirma.ResultadoFirmarUsuario;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Firmante;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Nombre;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.ResultadoVerificarFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.VerificarFirma;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/** 
 * Implementación del gestor de mensajes contra los servicios de afirma v5.0.1 
 *
 */
public class Afirma501MessagesHandler implements IAfirmaMessagesHandler {

    // etiquetas utilizadas 
    final public static String MENSAJE_ENTRADA="mensajeEntrada";
    final public static String MENSAJE_SALIDA="mensajeSalida";

    final public static String PETICION="peticion";
    final public static String VERSION_MSG="versionMsg";
    final public static String PARAMETROS="parametros";
    final public static String FORMATO_FIRMA="formatoFirma";
    final public static String ID_APLICACION="idAplicacion";
    final public static String ALGORITMO_HASH="algoritmoHash";
    final public static String FIRMA_ELECTRONICA="firmaElectronica";
    final public static String CERT_FIRMANTE="certificadoFirmante";
    final public static String CUSTODIAR_DOCUMENTO="custodiarDocumento";

    // etiquetas utilizadas en entrada firma
    final public static String FIRMA_SERVIDOR="FirmaServidor";
    final public static String MENSAJE_ENTRADA_FIRMAR_ATTRS[][]={
    	{"xmlns", "https://afirmaws/ws/firma"}, 
    	{"xmlns:xsi", "http://www.w3.org/2001/XMLSchemainstance"}, 
    	{"xsi:SchemaLocation", "https://localhost/afirmaws/xsd/mfirma/ws.xsd"} };

    final public static String ID_DOCUMENTO="idDocumento";
    final public static String FIRMANTE="firmante";
    final public static String ID_REFERENCIA="idReferencia";

    // etiquetas utilizadas en entrada firma (almacenar documento)
    final public static String ALMACENAR_DOCUMENTO="AlmacenarDocumento";
    final public static String DOCUMENTO="documento";
    final public static String NOMBRE_DOCUMENTO="nombreDocumento";
    final public static String TIPO_DOCUMENTO="tipoDocumento";
    
    
    //Etiquetas utilizadas a la salida de la validacion de la firma
    final public static String VALIDACION_FIRMA_ELECTRONICA="validacionFirmaElectronica";
    final public static String INFORMACION_ADICIONAL="informacionAdicional";
    final public static String CERTIFICADO="certificado";
    
	// etiquetas utilizadas en salida firma
	final public static String MENSAJE_SALIDA_FIRMAR_ATTRS[][] = {
		{ "xmlns", "https://afirmaws/ws/firma" },
		{ "xmlns:xsi", "http://www.w3.org/2001/XMLSchemainstance" },
		{ "xsi:SchemaLocation", "https://localhost/afirmaws/xsd/mfirma/ws.xsd" } };
	
	final public static String RESPUESTA = "respuesta";
	final public static String rESPUESTA = "Respuesta";
	final public static String ESTADO = "estado";
	final public static String TRANSACTIONID = "idTransaccion";
	final public static String DESCRIPCION = "descripcion";
	final public static String eXCEPCION = "Excepcion";
	final public static String CODIGO_ERROR = "codigoError";
	final public static String EXCEPCION_ASOCIADA = "excepcionAsociada";

	// etiquetas utilizadas en entrada verificar
	final public static String VALIDAR_FIRMA = "ValidarFirma";
	final public static String HASH = "hash";
	final public static String DATOS = "datos";

	// etiquetas utilizadas en entrada firmaUsuario
	final public static String FIRMAUSUARIO = "FirmaUsuario2FasesF2";


	
    /**
     * Constructor
     */
    public Afirma501MessagesHandler() {
    	super();
    }
    
	public String createRequestAlmacenarDocumento(SignManagerAFirmaImpl af, String contentBase64)  throws FirmaDigitalException {
		AFirmaConfiguration config = af.loadConfig();
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		
		Element root = document.addElement(MENSAJE_ENTRADA);

		Element elem = root.addElement(PETICION);
		elem.setText(ALMACENAR_DOCUMENTO);

		elem = root.addElement(VERSION_MSG);
		elem.setText("1.0");

		elem = root.addElement(PARAMETROS);

		elem.addElement(ID_APLICACION).addText(config.getIdAplicacion());
		elem.addElement(DOCUMENTO).addCDATA(contentBase64);
		elem.addElement(NOMBRE_DOCUMENTO).addText("Justificante"); // TODO Nombre del documento
		elem.addElement(TIPO_DOCUMENTO).addText("PDF"); // TODO Tipo de documento

		return document.asXML();
	}

	public void checkResponseAlmacenarDocumento(String response) throws Exception {
		
		response = Util.cambiarCabeceraXml(response);
		
		InputStream is = new ByteArrayInputStream(new String(response.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();
		Document document = reader.read(is);

		// Comprobar si hay error
		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node == null) { 
			throwAFirmaException(document);
		}
		
		checkStatus(document);
	}

	public String createRequestFirmar(SignManagerAFirmaImpl af, String xml) throws Exception {
		AFirmaConfiguration config = af.loadConfig();
		xml = Util.cambiarCabeceraXml(xml);
		
		InputStream is = new ByteArrayInputStream(new String(xml.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();

		Document document = reader.read(is);

		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node == null) { // esto indica que hay error
			String desc = null, error = null, excepcion = null;
			node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + eXCEPCION + "/" + DESCRIPCION);
			if (node != null)
				desc = node.getStringValue();

			/*
			 * node=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+eXCEPCION+"/"+CODIGO_ERROR);
			 * if(node!=null) error=node.getStringValue();
			 * 
			 * document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+eXCEPCION+"/"+EXCEPCION_ASOCIADA);
			 * if(node!=null) excepcion=node.getStringValue();
			 */
			throw new ExcepcionAFirma(desc, error, excepcion);
		}

		String idDocumento = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/"
						+ ID_DOCUMENTO).getStringValue(); // "177";

		String idAplicacion = config.getIdAplicacion(); // "appPrueba";
		String firmante = config.getFirmante(); // "RSA.2048";
		String idReferencia = config.getIdReferencia(); // "idReferencia";
		String algoritmoHash = config.getAlgoritmoHash(); // "SHA1";
		String formatoFirma = config.getFormatoFirma(); // "CMS";

		document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(MENSAJE_ENTRADA);

		for (int i = 0; i < MENSAJE_ENTRADA_FIRMAR_ATTRS.length; i++) {
			root.addAttribute(MENSAJE_ENTRADA_FIRMAR_ATTRS[i][0], MENSAJE_ENTRADA_FIRMAR_ATTRS[i][1]);
		}
		
		Element elem = root.addElement(PETICION);
		elem.setText(FIRMA_SERVIDOR);

		elem = root.addElement(VERSION_MSG);
		elem.setText("1.0");

		elem = root.addElement(PARAMETROS);

		elem.addElement(ID_APLICACION).addText(idAplicacion);
		elem.addElement(ID_DOCUMENTO).addText(idDocumento);
		elem.addElement(FIRMANTE).addText(firmante);
		elem.addElement(ID_REFERENCIA).addText(idReferencia);
		elem.addElement(ALGORITMO_HASH).addText(algoritmoHash);
		elem.addElement(FORMATO_FIRMA).addText(formatoFirma);

		return document.asXML();
	}

	public void checkResponseFirmar(FirmarDocumento fd, String response) throws Exception {
		
		response = Util.cambiarCabeceraXml(response);

		InputStream is = new ByteArrayInputStream(new String(response.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();
		Document document = reader.read(is);

		// Comprobar si hay error
		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node == null) { 
			throwAFirmaException(document);
		}

		checkStatus(document);

		String b64 = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/"
				+ FIRMA_ELECTRONICA).getStringValue();

		ResultadoFirmarDocumento resultado = new ResultadoFirmarDocumento();
		resultado.setFirma(new Firma(b64));
		fd.setResultado(resultado);
	}

	public String createRequestVerificar(SignManagerAFirmaImpl af, String firma) throws FirmaDigitalException{
		return createRequestVerificar(af,firma,null);
	}

	public String createRequestVerificar(SignManagerAFirmaImpl af, String firma,String b64Datos) throws FirmaDigitalException{
	
		AFirmaConfiguration config = af.loadConfig();
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		
		Element root = document.addElement(MENSAJE_ENTRADA);

		Element elem = root.addElement(PETICION);
		elem.setText(VALIDAR_FIRMA);

		elem = root.addElement(VERSION_MSG);
		elem.setText("1.0");

		elem = root.addElement(PARAMETROS);

		elem.addElement(ID_APLICACION).addText(config.getIdAplicacion());
		elem.addElement(FIRMA_ELECTRONICA).addCDATA(firma);
		elem.addElement(FORMATO_FIRMA).addText(config.getFormatoFirma());
		elem.addElement(HASH).addCDATA(""); // hash de los datos cuya firma se
											// va a validar, codificado en Base
											// 64, puede estar vacio
		elem.addElement(ALGORITMO_HASH).addText(""); // si se envia el hash,
														// formato del mismo
		elem.addElement(DATOS).addCDATA(b64Datos); // el texto original, puede
												// estar vacio

		return document.asXML();
	}

	public void checkResponseVerificar(VerificarFirma vf, String s) throws Exception {
		s = Util.cambiarCabeceraXml(s);
		
		InputStream is = new ByteArrayInputStream(new String(s.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();
		Document document = reader.read(is);

		// Comprobar si hay error
		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node == null) { 
			throwAFirmaException(document);
		}
		
		ResultadoVerificarFirma resultado = new ResultadoVerificarFirma();
		String desc = null;

		String estado = node.getStringValue();
		if (Constantes.TRUE.equalsIgnoreCase(estado)) {
			
			// la validacion de la firma es verdadera
			resultado.setValida(new Valida("0")); // true

			node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/"
					+ RESPUESTA + "/" + rESPUESTA + "/" + DESCRIPCION + "/"
					+ VALIDACION_FIRMA_ELECTRONICA + "/"
					+ INFORMACION_ADICIONAL + "/" + FIRMANTE + "/"
					+ CERTIFICADO);

			if (node != null) {
				Firmante firmante= new Firmante();
				firmante.setNombre(new Nombre(CertificateUtils.getCN(node.getText())));
				
			    ArrayList firmantes=new ArrayList();
			    firmantes.add(firmante);
			    
			    resultado.setFirmantes(firmantes);
			    desc="Firma digital correcta";
			} else {
				desc="No se ha encontrado ningún firmante";
			}
		    
		} else {
			
			// la validacion de la firma es falsa
			resultado.setValida(new Valida("-1"));
			// aqui se puede obtener mas informacion
			desc = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA
					+ "/" + DESCRIPCION).getStringValue();
			// throw new ExcepcionAFirma(desc, "?", "?");
		}

		resultado.setInformacionAuxiliar(new InformacionAuxiliar(desc));

		vf.setResultado(resultado);
	}

	public String createRequestFirmarUsuario(SignManagerAFirmaImpl af, String firmaUsuario, String certFirmante, String hash) throws FirmaDigitalException{
		AFirmaConfiguration config = af.loadConfig();
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement(MENSAJE_ENTRADA);
		for (int i = 0; i < MENSAJE_ENTRADA_FIRMAR_ATTRS.length; i++) {
			root.addAttribute(
					MENSAJE_ENTRADA_FIRMAR_ATTRS[i][0],
					MENSAJE_ENTRADA_FIRMAR_ATTRS[i][1]);
		}
		
		Element elem = root.addElement(PETICION);
		elem.setText(FIRMAUSUARIO);

		elem = root.addElement(VERSION_MSG);
		elem.setText("1.0");

		elem = root.addElement(PARAMETROS);

		elem.addElement(ID_APLICACION).addText(config.getIdAplicacion());
		elem.addElement(FIRMA_ELECTRONICA).addCDATA(firmaUsuario);
		elem.addElement(CERT_FIRMANTE).addCDATA(certFirmante);

		elem.addElement(FORMATO_FIRMA).addText(config.getFormatoFirma());
		// elem.addElement(DOCUMENTO).addCDATA("");
		// elem.addElement(NOMBRE_DOCUMENTO).addText("Justificante");
		// elem.addElement(TIPO_DOCUMENTO).addText("PDF");

		elem.addElement(HASH).addCDATA(hash); // hash de los datos cuya firma
		// se va a validar, codificado en Base 64, puede estar vacio

		elem.addElement(ALGORITMO_HASH).addText(config.getAlgoritmoHash()); // si se
																		// envia
																		// el
																		// hash,
																		// formato
																		// del
																		// mismo

		elem.addElement(CUSTODIAR_DOCUMENTO).addText("false");

		return document.asXML();
	}

	public void checkResponseFirmaUsuario(FirmarUsuario fu, String s) throws Exception {
		s = Util.cambiarCabeceraXml(s);
		
		InputStream is = new ByteArrayInputStream(new String(s.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();
		Document document = reader.read(is);
		
		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + TRANSACTIONID);
		if (node == null) {
			throwAFirmaException(document);
		}

		String idTransacion = node.getStringValue();
		ResultadoFirmarUsuario resultado = new ResultadoFirmarUsuario();
		resultado.setIdTransacion(idTransacion);
		fu.setResultado(resultado);

	}

	protected void throwAFirmaException(Document doc) throws ExcepcionAFirma {
		
		// Componer la información del error
		String desc = null;
		String error = null;
		String excepcion = null;
		
		// Descripción del error
		Node node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + eXCEPCION + "/" + DESCRIPCION);
		if (node != null) {
			desc = node.getStringValue();
		} else {
			node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + DESCRIPCION);
			if (node != null) {
				desc = node.getStringValue();
			}
		}

		// Código del error.
		node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + eXCEPCION + "/" + CODIGO_ERROR);
		if (node != null) {
			error = node.getStringValue();
		} else {
			node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + CODIGO_ERROR);
			if (node != null) {
				error = node.getStringValue();
			}
		}

		// Excepción asociada
		node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + eXCEPCION + "/" + EXCEPCION_ASOCIADA);
		if (node != null) {
			excepcion = node.getStringValue();
		} else {
			node = doc.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + EXCEPCION_ASOCIADA);
			if (node != null) {
				excepcion = node.getStringValue();
			}
		}

		throw new ExcepcionAFirma(desc, error, excepcion);
	}
	
	protected void checkStatus(Document resDocument) throws ExcepcionAFirma {
		
		Node node = resDocument.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node != null) { 

			String estado = node.getStringValue();
			if (!Constantes.TRUE.equalsIgnoreCase(estado)) {
				String desc = null;
				
				node = resDocument.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + DESCRIPCION);
				if (node != null) {
					desc = node.getStringValue();	
				}
				
				throw new ExcepcionAFirma(desc, "?", "?");
			}
		}
	}

}
