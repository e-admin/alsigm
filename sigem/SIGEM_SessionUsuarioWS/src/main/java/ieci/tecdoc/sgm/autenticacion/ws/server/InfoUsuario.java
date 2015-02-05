package ieci.tecdoc.sgm.autenticacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoUsuario extends RetornoServicio{

    private String id;
    private String name;
    private String firstName;
    private String surName;
    private String surName2;
    private String email;
    private String certificateIssuer;
    private String certificateSN;
    private String inQuality;
    private String socialName;
    private String CIF;

	public String getCertificateIssuer() {
		return certificateIssuer;
	}
	public void setCertificateIssuer(String certificateIssuer) {
		this.certificateIssuer = certificateIssuer;
	}
	public String getCertificateSN() {
		return certificateSN;
	}
	public void setCertificateSN(String certificateSN) {
		this.certificateSN = certificateSN;
	}
	public String getCIF() {
		return CIF;
	}
	public void setCIF(String cif) {
		CIF = cif;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInQuality() {
		return inQuality;
	}
	public void setInQuality(String inQuality) {
		this.inQuality = inQuality;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSocialName() {
		return socialName;
	}
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getSurName2() {
		return surName2;
	}
	public void setSurName2(String surName2) {
		this.surName2 = surName2;
	}
}
