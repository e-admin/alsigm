package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.InformacionError;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Resultado;

/* 
   $Id: DocumentoIER.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $

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
