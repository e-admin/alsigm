package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

import com.thoughtworks.xstream.*;

/**
  $Id: Gestion.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $
  
  clase de gestion del conversor bean <-> xml
  aqui se tienen que registrar todos los nodos que se implementen (las clases que extiendan de ieci.tecdoc.sgm.xml.Nodo)

*/

public class Gestion {

    private static XStream xstream;
    static {
	xstream=new XStream(new com.thoughtworks.xstream.io.xml.DomDriver());


	/* esto se define en Documento automaticamente
	xstream.alias("VerificarFirma", ieci.tecdoc.sgm.xml.verificarfirma.VerificarFirma.class);
	xstream.alias("Firmante", ieci.tecdoc.sgm.xml.verificarfirma.Firmante.class);
	xstream.alias("FirmarDocumento", ieci.tecdoc.sgm.xml.firmardocumento.FirmarDocumento.class);
	*/


	/*
	 * para asegurarse de que la clase queda registrada hay que crear una instancia aqui
	 */
	

//	new ieci.tecdoc.sgm.xml.firmardocumento.FirmarDocumento();
//	// xstream.alias("resultado", ieci.tecdoc.sgm.xml.firmardocumento.ResultadoFirmarDocumento.class);
//
//	new ieci.tecdoc.sgm.xml.verificarfirma.VerificarFirma();

	new ValidarCertificado();

    }

    /**
     * obtener el gestor XStream
     */
    public static XStream getXStream() {
	return xstream;
    }

    /**
     * convertir xml a bean
     */
    public static Object xml(String xml) {
	Object o=null;
	try {
	    o=Gestion.getXStream().fromXML(xml);
	} catch(com.thoughtworks.xstream.mapper.CannotResolveClassException e) {
	    /*
	      esto ocurre cuando la maquina virtual 've' un documento de este tipo antes de que haya cargado la clase.
	      la cargamos manualmente para registrarla
	    */
	    System.out.println("excepcion: "+e.getMessage());
	    String nombre="";
	    String nombreClase="ieci.tecdoc.sgm.xml."+nombre.toLowerCase()+"."+nombre;
	    try {
		Class.forName(nombreClase).newInstance();
	    } catch(Exception ee) {
		System.out.println("excepcion: "+ee.getMessage());
	    }
	    o=Gestion.getXStream().fromXML(xml);
	}	    
	return o;
    }

    /**
     * convertir bean a xml
     */
    public static String xml(Object o) {
	return Gestion.getXStream().toXML(o);	
    }

}
