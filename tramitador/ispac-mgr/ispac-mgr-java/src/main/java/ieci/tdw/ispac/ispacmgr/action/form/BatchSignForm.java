package ieci.tdw.ispac.ispacmgr.action.form;

public class BatchSignForm extends BatchForm {
	
	private static final long serialVersionUID = 1L;
	
	private String[] hashs = null;
	private String signs = null;
	
	/**
	 * Certificado de usuario.
	 */
	private String signCertificate;
	
	/**
	 * Formato de firma.
	 */
	private String signFormat;
	
//	private String[] signs = null;
//
//	public String[] getSigns() {
//		return signs;
//	}
//
//	public void setSigns(String[] signs) {
//		this.signs = signs;
//	}

	public String[] getHashs() {
		return hashs;
	}

	public void setHashs(String[] hashs) {
		this.hashs = hashs;
	}

	public String getSigns() {
		return signs;
	}

	public void setSigns(String signs) {
		this.signs = signs;
	}
	
	public void clean(){
		hashs = null;
		signs = null;
		setMultibox(null);
	}
	
	/**
	 * 
	 * @return String base64 Con la clave publica del certificado firmante
	 * 
	 */
	public String getSignCertificate() {
		return signCertificate;
	}

	/**
	 * 
	 * @param signCertificate
	 */
	public void setSignCertificate(String signCertificate) {
		this.signCertificate = signCertificate;
	}

	public String getSignFormat() {
		return signFormat;
	}

	public void setSignFormat(String signFormat) {
		this.signFormat = signFormat;
	}
}
