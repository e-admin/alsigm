package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Tipo;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Version;

/* 
   $Id: DocumentoTP.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $

   documento con atributos Tipo y Valor (TP)
*/

abstract public class DocumentoTP extends Documento {

    static {
	new Tipo("");
	new Version("");
    }

    private Tipo tipo;
    private Version version;

    public Tipo getTipo() {
	return tipo;
    }

    public void setTipo(Tipo tipo) {
	this.tipo=tipo;
    }

    public Version getVersion() {
	return version;
    }

    public void setVersion(Version version) {
	this.version=version;
    }

}

