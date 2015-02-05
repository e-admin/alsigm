package ieci.tdw.ispac.api.errors.sign;


public class SignerMinhapAlreadyExistsException extends SignException {

	private static final long serialVersionUID = 1L;
	
	private String responsible = null;
	
	
	public SignerMinhapAlreadyExistsException() {
		super();
	}

	public SignerMinhapAlreadyExistsException(String responsible) {
		this();
		this.responsible = responsible;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

}
