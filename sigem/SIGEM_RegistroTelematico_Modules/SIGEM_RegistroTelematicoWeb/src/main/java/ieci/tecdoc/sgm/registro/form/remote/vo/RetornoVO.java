package ieci.tecdoc.sgm.registro.form.remote.vo;

public class RetornoVO {

	protected boolean error;

	protected String message;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
