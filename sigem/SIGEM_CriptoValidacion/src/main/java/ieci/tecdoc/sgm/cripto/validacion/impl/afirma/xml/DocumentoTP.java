package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

/* 
   $Id: DocumentoTP.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $

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

