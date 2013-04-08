package ieci.tdw.ispac.api.errors.sign;

import ieci.tdw.ispac.api.item.IResponsible;

public class SameSignerException extends SignException {

	private static final long serialVersionUID = 1L;
	
	private IResponsible responsible = null;
	
	
	public SameSignerException() {
		super();
	}

	public SameSignerException(IResponsible responsible) {
		this();
		this.responsible = responsible;
	}

	public IResponsible getResponsible() {
		return responsible;
	}

	public void setResponsible(IResponsible responsible) {
		this.responsible = responsible;
	}

}
