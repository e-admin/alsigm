package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma;

/* $Id: Firmante.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class Firmante {
   
    private Nombre nombre;
    private NIF nif;
    private Certificado certificado;

    public Nombre getNombre() {
	return nombre;
    }
    public void setNombre(Nombre nombre) {
	this.nombre=nombre;
    }

    public NIF getNIF() {
	return nif;
    }
    public void setNIF(NIF nif) {
	this.nif=nif;
    }

    public Certificado getCertificado() {
	return certificado;
    }
    public void setCertificado(Certificado certificado) {
	this.certificado=certificado;
    }

}
