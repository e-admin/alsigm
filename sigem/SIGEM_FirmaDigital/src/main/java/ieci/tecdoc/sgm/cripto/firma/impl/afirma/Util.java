package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

/**
  $Id: Util.java,v 1.1.2.1 2008/06/30 11:42:22 jnogales Exp $

  clase de funciones de utilidad genericas
*/

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.DocumentoIER;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Codigo;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Descripcion;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Detalle;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.InformacionError;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class Util {

    private static Logger logger=Logger.getLogger(Util.class);


    /**
     * comprobar si hay un error, si lo hay devolver true
     *
     * @param dier xml InformationError del que comprobar el error
     * @return true si hay error, sino false
     */
    public static boolean hayError(DocumentoIER dier) {
	InformacionError ie=dier.getInformacionError();
	return ie.getCodigo().get().equals("0") ? false : true;
    }

    /**
     * comprobar si hay un error, si lo hay lanzar una excepcion
     * XXX la excepcion deberia ser una clase especifica
     *
     * @param dier xml InformationError del que comprobar el error
     * @exception Exception
     */
    public static void hayErrorExcepcion(DocumentoIER dier) throws Excepcion {
	if(hayError(dier)) {
	    InformacionError ie=dier.getInformacionError();
	    Excepcion e=new Excepcion("el documento tiene un codigo de error: "+ie.getCodigo().get());
	    e.setCodigo(new Integer(ie.getCodigo().get()).intValue());
	    e.setDetalle(ie.getDetalle().get());
	    e.setDescripcion(ie.getDescripcion().get());
	    throw e;
	}
    }

    /**
     * introducir la informacion de la excepcion en ie
     *
     * @param ie objeto a rellenar
     * @param codigoError el codigo de error que se quiera establecer
     * @param e excepcion de la cual obtener la informacion
     */
    public static void llenarInformacionError(InformacionError ie, int codigoError, Exception e) {
	llenarInformacionError(ie, codigoError+"", e);
    }

    public static void llenarInformacionError(InformacionError ie, String codigoError, Exception e) {
	if(codigoError.equals("0") || codigoError.equals(""))
	    codigoError="1";

	ie.setCodigo(new Codigo(codigoError));
	ie.setDescripcion(new Descripcion(e.getMessage()));
	ie.setDetalle(new Detalle(Util.getStringDeStackTrace(e)));
    }

    /**
     * obtener una representacion de la pila de una excepcion en cadena
     * @param e la excepcion de la que queremos obtener la traza
     */
    public static String getStringDeStackTrace(Exception e) {
	ByteArrayOutputStream baos=new ByteArrayOutputStream();
	PrintWriter pw=new PrintWriter(baos);
	e.printStackTrace(pw);
	String s=null;
	try {
	    pw.flush();
	    baos.flush();
	    s=baos.toString();
	    pw.close();
	    baos.close();
	} catch(Exception ee) {
	    ee.printStackTrace();
	}
	return s;
    }

    final private static int LEN=128;
    /**
     * dom4j no lee correctamente el documento si la etiqueta raiz tiene el atributo 'xmlns="X"'
     * esta funcion quita el atributo
     */
    final public static String cambiarCabeceraXml(String s) {
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
	
		/* XXX la primera sentencia no hace lo que debiera, por eso utilizamo la segunda */
		// s=s.replaceFirst("(<[^!].*) xmlns=\".*\" ", "$1  ");
		s=s.replaceFirst("(<[^!].*) xmlns=\"http[s]?://afirmaws/ws/[a-z]*\" ", "$1  ");
		return s;
    }

    final public static String nombreCabecera=":cabecera-implementacion"; //System.getProperty()

    /**
     * obtiene el nombre de la implementacion de la cabecera de un contexto de  mensaje
     * llamado por getBean
     * @param msgContext contexto de mensaje del que se obtendra el nombre de la implementacion
     */
    final private static String getImplementacion(org.apache.axis.MessageContext msgContext) throws javax.xml.soap.SOAPException {
	org.apache.axis.Message msg=msgContext.getRequestMessage();
	javax.xml.soap.SOAPHeader header=msg.getSOAPHeader();
	for(java.util.Iterator it=header.examineAllHeaderElements();it.hasNext();) {
	    javax.xml.soap.SOAPHeaderElement elem=(javax.xml.soap.SOAPHeaderElement)it.next();
	    String s=elem.getTagName();
	    /*
	    System.out.println("tagName: "+elem.getTagName());
	    System.out.println("value: "+elem.getValue());
	    */
	    if(s.endsWith(nombreCabecera)) {
		return elem.getValue();
	    }
	}
	return null;
    }

    final public static String DEFECTO="defecto";  // XXX ?
    /**
     * obtener el bean desde un contexto de mensaje. esto es llamado desde la implementacion de los servicios AXIS para obtener la implementacion
     * solicitada por el cliente
     *
     * @param servicio
     * @param ctx contexto de aplicacion en la que se buscara el bean
     * @param msgContext contexto de mensaje del que se extraera el nombre del bean buscado
     */
    final public static Object getBean(String servicio, org.springframework.context.ApplicationContext ctx, org.apache.axis.MessageContext msgContext) {
	String impl;
	String serv;
	try {
	    impl=getImplementacion(msgContext);
	    if(impl==null)
		impl=DEFECTO;

	} catch (javax.xml.soap.SOAPException e) {
	    e.printStackTrace();
	    impl=DEFECTO;
	}

	String res=servicio+"."+impl;
	logger.info("impl: "+res);

	return ctx.getBean(res);
    }
}
