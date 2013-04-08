package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

/* $Id: InfoCertificado.java,v 1.1.2.1 2008/06/30 11:32:00 jnogales Exp $ */

public class InfoCertificado {

    static {

    }

    private NombreCompleto nombreCompleto;
    private Nombre nombre;
    private Apellido1 apellido1;
    private Apellido2 apellido2;
    private NIF nif;
    private CIF cif;
    private RazonSocial razonSocial;
    private NumeroSerie numeroSerie;
    private Asunto asunto;
    private Emisor emisor;

    public NombreCompleto getNombreCompleto() {
    return nombreCompleto;
    }
    public void setNombreCompleto(NombreCompleto nombreCompleto) {
    this.nombreCompleto=nombreCompleto;
    }

    public Nombre getNombre() {
	return nombre;
    }
    public void setNombre(Nombre nombre) {
	this.nombre=nombre;
    }

    public Apellido1 getApellido1() {
	return apellido1;
    }
    public void setApellido1(Apellido1 apellido1) {
	this.apellido1=apellido1;
    }

    public Apellido2 getApellido2() {
	return apellido2;
    }
    public void setApellido2(Apellido2 apellido2) {
	this.apellido2=apellido2;
    }

    public NIF getNIF() {
	return nif;
    }
    public void setNIF(NIF nif) {
	this.nif=nif;
    }

    public CIF getCIF() {
	return cif;
    }
    public void setCIF(CIF cif) {
	this.cif=cif;
    }

    public RazonSocial getRazonSocial() {
	return razonSocial;
    }
    public void setRazonSocial(RazonSocial razonSocial) {
	this.razonSocial=razonSocial;
    }

    public NumeroSerie getNumeroSerie() {
	return numeroSerie;
    }
    public void setNumeroSerie(NumeroSerie numeroSerie) {
	this.numeroSerie=numeroSerie;
    }

    public Asunto getAsunto() {
	return asunto;
    }
    public void setAsunto(Asunto asunto) {
	this.asunto=asunto;
    }

    public Emisor getEmisor() {
	return emisor;
    }
    public void setEmisor(Emisor emisor) {
	this.emisor=emisor;
    }

}
