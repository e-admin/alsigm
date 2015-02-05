package ieci.tecdoc.sgm.cripto.validacion.impl.afirma;

import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.config.AFirmaValidacionConfiguration;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Apellido1;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Apellido2;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Asunto;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.CIF;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Emisor;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.InfoCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.InformacionAuxiliar;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.NIF;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Nombre;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.NombreCompleto;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.NumeroSerie;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.RazonSocial;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.ResultadoValidarCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Valida;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.ValidarCertificado;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * $Id: Conversor.java,v 1.1.2.2 2008/07/09 08:48:46 jnogales Exp $
 *
 * clase que trata los documentos xml de entrada y salida con @firma
 */

public class Conversor {

    final public static String MENSAJE_ENTRADA="mensajeEntrada";
    final public static String MENSAJE_SALIDA="mensajeSalida";

    final public static String VALIDAR_CERTIFICADO="ValidarCertificado";

    final public static String PETICION="peticion";
    final public static String VERSION_MSG="versionMsg";
    final public static String PARAMETROS="parametros";

    final public static String CERTIFICADO="certificado";
    final public static String ID_APLICACION="idAplicacion";
    final public static String MODO_VALIDACION="modoValidacion";
    final public static String OBTENER_INFO="obtenerInfo";

    final public static String RESPUESTA="respuesta";
    final public static String rESPUESTA="Respuesta";
    final public static String ESTADO="estado";
    final public static String DESCRIPCION="descripcion";
    final public static String EXCEPCION="Excepcion";
    final public static String CODIGO_ERROR="codigoError";
    final public static String EXCEPCION_ASOCIADA="excepcionAsociada";
    final public static String RESULTADO_PROCESAMIENTO="ResultadoProcesamiento";
    final public static String RESULTADO_VALIDACION="ResultadoValidacion";
    final public static String RESULTADO="resultado";

    //Constantes del certificado
    final public static String INFO_CERTIFICADO="InfoCertificado";
    final public static String CAMPO="Campo";
    final public static String ID_CAMPO="idCampo";
    final public static String VALOR_CAMPO="valorCampo";

    final public static String CERO="0";

    public static String tratarEntradaValidar(String cert, ValidationManagerAFirmaImpl af) throws CriptoValidacionException{
    AFirmaValidacionConfiguration config = af.loadConfig();
    Document document=DocumentHelper.createDocument();
	document.setXMLEncoding("UTF-8");
	Element root=document.addElement(MENSAJE_ENTRADA);

	Element elem;

	elem=root.addElement(PETICION);
	elem.setText(VALIDAR_CERTIFICADO);

	elem=root.addElement(VERSION_MSG);
	elem.setText("1.0");

	elem=root.addElement(PARAMETROS);

	elem.addElement(CERTIFICADO).addCDATA(cert);
	elem.addElement(ID_APLICACION).addText(config.getIdAplicacion());
	// Compatibilidad con los ficheros de configuracion anteriores, por si no se actualizan,
	// en los que no existe el modo de validacion configurable, por lo que se mantiene 0 para
	// una validacion simple (caducidad, integridad y confianza del certificado)
	String modoValidacion = config.getModoValidacion();
	if (StringUtils.isBlank(modoValidacion)) {
		// Validacion simple
		modoValidacion = CERO;
	}
	elem.addElement(MODO_VALIDACION).addText(modoValidacion);
	elem.addElement(OBTENER_INFO).addText("true"); // XXX

	return document.asXML();
    }

    public static void tratarSalidaValidar(ValidarCertificado vc, String xml) throws DocumentException,  ExcepcionAFirma, Exception {
	xml=Util.cambiarCabeceraXml(xml);
	//java.io.InputStream is=new java.io.ByteArrayInputStream(new String(xml.getBytes(), "utf-8").getBytes());
	InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
	SAXReader reader=new SAXReader();

	Document document=reader.read(is);

	Node node=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+EXCEPCION);
	if(node!=null) { // esto indica que hay error, revisa esto, no esta bien
	    String desc=null, error=null, excepcion=null;
	    node=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+EXCEPCION+"/"+DESCRIPCION);
	    if(node!=null)
	    	desc=node.getStringValue();

	    node=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+EXCEPCION+"/"+CODIGO_ERROR);
	    if(node!=null)
	    	error=node.getStringValue();

	    document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+EXCEPCION+"/"+EXCEPCION_ASOCIADA);
	    if(node!=null)
	       excepcion=node.getStringValue();

	    throw new ExcepcionAFirma(desc, error, excepcion);
	}


	node=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+RESULTADO_PROCESAMIENTO+"/"+RESULTADO_VALIDACION+"/"+RESULTADO);
	ResultadoValidarCertificado resultado=new ResultadoValidarCertificado();

	// XXX aqui habra que obtener mas informacion
	String estado=node.getStringValue();

	if(CERO.equals(estado)) {
	    // la validacion de la firma es verdadera
	    resultado.setValida(new Valida("0")); // true
	    //InfoCertificado infoCertificado=new InfoCertificado();
	    InfoCertificado infoCertificado = obtenerDatosCertificado(document);
	    resultado.setInfoCertificado(infoCertificado);
	}
	else {
	    resultado.setValida(new Valida("-1"));
	    // XXX aqui se puede obtener mas informacion
	    String desc=document.selectSingleNode("/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+RESULTADO_PROCESAMIENTO+"/"+RESULTADO_VALIDACION+"/"+DESCRIPCION).getStringValue();
	    resultado.setInformacionAuxiliar(new InformacionAuxiliar(desc));
	    // throw new ExcepcionAFirma(desc, "?", "?");
	}

	vc.setResultado(resultado);

    }

    public static InfoCertificado obtenerDatosCertificado(Document document) {
    	InfoCertificado infoCertificado=new InfoCertificado();
    	try {
    		String xPath = "/"+MENSAJE_SALIDA+"/"+RESPUESTA+"/"+RESULTADO_PROCESAMIENTO+"/"+INFO_CERTIFICADO+"/"+CAMPO;
    		List nodes = document.selectNodes(xPath);
    		String idCampo = null, valorCampo = null;
    		for (int i=0; i<nodes.size(); i++) {

    			Node node = (Node)nodes.get(i);

    			idCampo = node.selectSingleNode(ID_CAMPO).getStringValue();
    			valorCampo = node.selectSingleNode(VALOR_CAMPO).getStringValue();

    			// Nombre completo
    			if (FIELD_NOMBRE_COMPLETO.equals(idCampo)) {
    				String nombreCompleto = valorCampo;
    				// DNI electronico -> eliminar en el nombre (AUTENTICACIÓN)
    				int idx = nombreCompleto.indexOf("(");
    				if (idx != -1) {
    					nombreCompleto = nombreCompleto.substring(0, idx);
    					// Eliminar la coma y pasar a nombre y apellidos
    					idx = nombreCompleto.indexOf(",");
    					if (idx != -1) {
    						String nombre = nombreCompleto.substring(idx + 1).trim();
    						String apellidos = nombreCompleto.substring(0, idx).trim();
    						nombreCompleto = nombre + " " + apellidos;
    					}
    				}
    				infoCertificado.setNombreCompleto(new NombreCompleto(nombreCompleto));
    			}
    			// Nombre segregado
    			else if (FIELD_APELLIDO1.equals(idCampo)) {
    				infoCertificado.setApellido1(new Apellido1(valorCampo));
    			}
				else if (FIELD_APELLIDO2.equals(idCampo)) {
				infoCertificado.setApellido2(new Apellido2(valorCampo));
				}
				else if (FIELD_NOMBRE.equals(idCampo)) {
				infoCertificado.setNombre(new Nombre(valorCampo));
				}
				else if (FIELD_RAZON_SOCIAL.equals(idCampo)) {
    				infoCertificado.setRazonSocial(new RazonSocial(valorCampo));
    			}
    			else if (FIELD_NIF.equals(idCampo)) {
    				infoCertificado.setNIF(new NIF(valorCampo));
    			}
    			else if (FIELD_CIF.equals(idCampo)) {
    				infoCertificado.setCIF(new CIF(valorCampo));
    			}
    			else if (FIELD_NUMERO_SERIE.equals(idCampo)) {
    				infoCertificado.setNumeroSerie(new NumeroSerie(valorCampo));
    			}
    			else if (FIELD_ASUNTO.equals(idCampo)) {
    				/*
    				if (valorCampo.length() > 32)
    					infoCertificado.setAsunto(new Asunto(valorCampo.substring(0,32)));
    				else
    				*/
    				infoCertificado.setAsunto(new Asunto(valorCampo));
    			}
    			else if (FIELD_EMISOR.equals(idCampo)) {
    				/*
    				if (valorCampo.length() > 32)
    					infoCertificado.setEmisor(new Emisor(valorCampo.substring(0, 32)));
    				else
    				*/
    				infoCertificado.setEmisor(new Emisor(valorCampo));
    			}
    		}
    	} catch(Exception e) { }
    	return infoCertificado;
    }

    public static final String FIELD_NIF = "NIFResponsable";
    public static final String FIELD_NUMERO_SERIE = "numeroSerie";
    public static final String FIELD_ASUNTO = "subject";
    public static final String FIELD_EMISOR = "idEmisor";
    public static final String FIELD_NOMBRE_COMPLETO = "NombreApellidosResponsable";
    public static final String FIELD_NOMBRE = "nombreResponsable";
    public static final String FIELD_APELLIDO1 = "primerApellidoResponsable";
    public static final String FIELD_APELLIDO2 = "segundoApellidoResponsable";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_RAZON_SOCIAL = "razonSocial";
    public static final String FIELD_CIF = "NIF-CIF";
}
