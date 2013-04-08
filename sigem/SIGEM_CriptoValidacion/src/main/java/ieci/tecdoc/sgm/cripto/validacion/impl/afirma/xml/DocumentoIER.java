package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

/* 
   $Id: DocumentoIER.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $

   documento con elemento InformacionError y Resultado
*/

abstract public class DocumentoIER extends DocumentoTP {

    private InformacionError informacionError;
    private Resultado resultado;

    public InformacionError getInformacionError() {
	return informacionError;
    }

    public void setInformacionError(InformacionError informacionError) {
	this.informacionError=informacionError;
    }

    public Resultado getResultado() {
	return resultado;
    }

    public void setResultado(Resultado resultado) {
	this.resultado=resultado;
    }

}
