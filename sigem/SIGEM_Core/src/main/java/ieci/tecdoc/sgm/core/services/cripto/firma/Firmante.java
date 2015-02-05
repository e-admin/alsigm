package ieci.tecdoc.sgm.core.services.cripto.firma;

public class Firmante {

    private String name;
    private String nif;
    private Certificado certificate;
    
	public Certificado getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificado certificate) {
		this.certificate = certificate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}


}
