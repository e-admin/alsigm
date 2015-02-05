package ieci.tecdoc.sgm.autenticacion.ws.server;

public class LoginCertificado {

	private String procedureId;
	private String B64certificate;
	
	public String getB64certificate() {
		return B64certificate;
	}
	public void setB64certificate(String b64certificate) {
		B64certificate = b64certificate;
	}
	public String getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}
	
	
}
