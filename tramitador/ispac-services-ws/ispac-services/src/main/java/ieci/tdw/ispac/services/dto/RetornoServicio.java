package ieci.tdw.ispac.services.dto;

import java.io.Serializable;

public class RetornoServicio implements Serializable {

	private static final long serialVersionUID = -1851426878097174079L;
	
	public String returnCode;
	public String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
}
