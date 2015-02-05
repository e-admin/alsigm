package ieci.tdw.ispac.api.errors.sign;

public class SameMinhapSignerException extends SignException {

	private static final long serialVersionUID = 1L;
	
	private String responsible = null;
	
	
	public SameMinhapSignerException() {
		super();
	}

	public SameMinhapSignerException(String responsible) {
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
