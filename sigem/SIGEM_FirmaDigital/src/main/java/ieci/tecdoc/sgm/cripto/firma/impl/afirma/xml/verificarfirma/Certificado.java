package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma;

/* $Id: Certificado.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class Certificado {

    private Emisor emisor;
    private Asunto asunto;
    private NumeroSerie numeroSerie;

    public Emisor getEmisor() {
	return emisor;
    }
    public void setEmisor(Emisor emisor) {
	this.emisor=emisor;
    }

    public Asunto getAsunto() {
	return asunto;
    }
    public void setAsunto(Asunto asunto) {
	this.asunto=asunto;
    }

    public NumeroSerie getNumeroSerie() {
	return numeroSerie;
    }
    public void setNumeroSerie(NumeroSerie numeroSerie) {
	this.numeroSerie=numeroSerie;
    }


}
