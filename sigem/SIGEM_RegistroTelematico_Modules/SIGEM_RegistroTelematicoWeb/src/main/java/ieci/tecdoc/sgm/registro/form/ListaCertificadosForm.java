package ieci.tecdoc.sgm.registro.form;

import java.math.BigInteger;

import org.apache.struts.action.ActionForm;

public class ListaCertificadosForm extends ActionForm{
	private String tramiteId;
	private String dn;
	private String certificadoId;
	private BigInteger serialNumber;
	   
	public ListaCertificadosForm(){
		this.tramiteId = "";
		this.dn = "";
		this.certificadoId = "";
		this.serialNumber = new BigInteger("-1");
	}
	   
	public ListaCertificadosForm(String dn, BigInteger serialNumber){
		this.dn = dn;
		this.serialNumber = serialNumber;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getCertificadoId() {
		return certificadoId;
	}

	public void setCertificadoId(String certificadoId) {
		this.certificadoId = certificadoId;
	}

	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}
	
	private final static long serialVersionUID = 0;

}
