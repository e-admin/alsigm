package ieci.tecdoc.sgm.core.services.cripto.validacion;

public class InfoCertificado {

    private String name;
    private String firstname;
    private String lastname1;
    private String lastname2;
    private String nif;
    private String cif;
    private String corporateName;
    private String serialNumber;
    private String subject;
    private String issuer;

	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname1() {
		return lastname1;
	}
	public void setLastname1(String lastname1) {
		this.lastname1 = lastname1;
	}
	public String getLastname2() {
		return lastname2;
	}
	public void setLastname2(String lastname2) {
		this.lastname2 = lastname2;
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
